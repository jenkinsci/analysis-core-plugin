package hudson.plugins.analysis.core;

import javax.annotation.CheckForNull;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jvnet.localizer.Localizable;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

import com.google.common.collect.Lists;

import jenkins.model.Jenkins;

import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Job;
import hudson.model.Action;
import hudson.model.Api;
import hudson.model.Run;
import hudson.plugins.analysis.graph.AnnotationsByUserGraph;
import hudson.plugins.analysis.graph.BuildResultGraph;
import hudson.plugins.analysis.graph.DefaultGraphConfigurationView;
import hudson.plugins.analysis.graph.DifferenceGraph;
import hudson.plugins.analysis.graph.EmptyGraph;
import hudson.plugins.analysis.graph.GraphConfiguration;
import hudson.plugins.analysis.graph.GraphConfigurationView;
import hudson.plugins.analysis.graph.HealthGraph;
import hudson.plugins.analysis.graph.NewVersusFixedGraph;
import hudson.plugins.analysis.graph.NullGraph;
import hudson.plugins.analysis.graph.PriorityGraph;
import hudson.plugins.analysis.graph.TotalsGraph;
import hudson.plugins.analysis.graph.TrendDetails;
import hudson.plugins.analysis.graph.UserGraphConfigurationView;
import hudson.util.Graph;

/**
 * A job action displays a link on the side panel of a job. This action
 * also is responsible to render the historical trend via its associated
 * 'floatingBox.jelly' view.
 *
 * @param <T>
 *            result action type
 * @author Ulli Hafner
 */
// CHECKSTYLE:COUPLING-OFF
@ExportedBean
public abstract class AbstractProjectAction<T extends ResultAction<?>> implements Action {
    private static final Logger LOGGER = Logger.getLogger(AbstractProjectAction.class.getName());

    /** Project that owns this action. */
    private final Job<?, ?> owner;
    /** The type of the result action.  */
    private final Class<? extends T> resultActionType;
    /** The icon URL of this action: it will be shown as soon as a result is available. */
    private final String iconUrl;
    /** Plug-in URL. */
    private final String pluginUrl;
    /** Plug-in results URL. */
    private final String resultUrl;
    /** Human readable name of this action. */
    private final Localizable name;
    /** Human readable title of the trend graph. */
    private final Localizable trendName;

    /**
     * Creates a new instance of {@link AbstractProjectAction}.
     *
     * @param job
     *            the job that owns this action
     * @param resultActionType
     *            the type of the result action
     * @param name
     *            the human readable name of this action
     * @param trendName
     *            the human readable name of the trend graph
     * @param pluginUrl
     *            the URL of the associated plug-in
     * @param iconUrl
     *            the icon to show
     * @param resultUrl
     *            the URL of the associated build results
     */
    public AbstractProjectAction(final Job<?, ?> job, final Class<? extends T> resultActionType,
            final Localizable name, final Localizable trendName, final String pluginUrl, final String iconUrl, final String resultUrl) {
        this.owner = job;
        this.resultActionType = resultActionType;
        this.name = name;
        this.trendName = trendName;
        this.pluginUrl = pluginUrl;
        this.iconUrl = iconUrl;
        this.resultUrl = resultUrl;
    }
    
    /**
     * Creates a new instance of {@link AbstractProjectAction}.
     *
     * @param project
     *            the project that owns this action
     * @param resultActionType
     *            the type of the result action
     * @param name
     *            the human readable name of this action
     * @param trendName
     *            the human readable name of the trend graph
     * @param pluginUrl
     *            the URL of the associated plug-in
     * @param iconUrl
     *            the icon to show
     * @param resultUrl
     *            the URL of the associated build results
     * @deprecated use
     *             {@link #AbstractProjectAction(Job, Class, Localizable, Localizable, String, String, String)}
     */
    @Deprecated
    public AbstractProjectAction(final AbstractProject<?, ?> project, final Class<? extends T> resultActionType,
            final Localizable name, final Localizable trendName, final String pluginUrl, final String iconUrl, final String resultUrl) {
        this((Job<?, ?>) project, resultActionType, name, trendName, pluginUrl, iconUrl, resultUrl);
    }

    /**
     * Gets the remote API for this action.
     *
     * @return the remote API
     * @since 1.69
     */
    public Api getApi() {
        return new Api(this);
    }

    @Override @Exported
    public String getDisplayName() {
        return asString(name);
    }

    private String asString(final Localizable localizable) {
        if (localizable == null) {
            return null;
        }
        else {
            return localizable.toString();
        }
    }

    /**
     * Returns the title of the trend graph.
     *
     * @return the title of the trend graph.
     */
    public String getTrendName() {
        return asString(trendName);
    }

    /**
     * Returns the owner this action belongs to.
     *
     * @return the owner
     */
    public final Job<?, ?> getOwner() {
        return owner;
    }

    /**
     * Returns the project this action belongs to.
     *
     * @return the project
     *
     * @deprecated use
     *             {@link #getOwner()}
     */
    @Deprecated
    public final AbstractProject<?, ?> getProject() {
        return owner instanceof AbstractProject ? (AbstractProject<?, ?>) owner : null;
    }

    /**
     * Returns the graph configuration view for this owner. If the requested
     * link is neither the user graph configuration nor the default
     * configuration then <code>null</code> is returned.
     *
     * @param link
     *            the requested link
     * @param request
     *            Stapler request
     * @param response
     *            Stapler response
     * @return the dynamic result of the analysis (detail page).
     */
    public Object getDynamic(final String link, final StaplerRequest request, final StaplerResponse response) {
        if ("configureDefaults".equals(link)) {
            return createDefaultConfiguration();
        }
        else if ("configure".equals(link)) {
            return createUserConfiguration(request);
        }
        else {
            return null;
        }
    }

    /**
     * Returns the trend graph details.
     *
     * @return the details
     */
    public Object getTrendDetails() {
        return getTrendDetails(Stapler.getCurrentRequest(), Stapler.getCurrentResponse());
    }

    /**
     * Returns the trend graph details.
     *
     * @param request
     *            Stapler request
     * @param response
     *            Stapler response
     * @return the details
     */
    public Object getTrendDetails(final StaplerRequest request, final StaplerResponse response) {
        return new TrendDetails(getOwner(), getTrendGraph(request, response, "../../"), getTrendGraphId());
    }

    /**
     * Returns the trend graph.
     *
     * @return the current trend graph
     */
    public Object getTrendGraph() {
        return getTrendGraph(Stapler.getCurrentRequest(), Stapler.getCurrentResponse());
    }

    /**
     * Returns the configured trend graph.
     *
     * @param request
     *            Stapler request
     * @param response
     *            Stapler response
     * @return the trend graph
     */
    public Graph getTrendGraph(final StaplerRequest request, final StaplerResponse response) {
        return getTrendGraph(request, response, "");
    }

    private Graph getTrendGraph(final StaplerRequest request, final StaplerResponse response, final String urlPrefix) {
        GraphConfigurationView configuration = createUserConfiguration(request);
        if (configuration.hasMeaningfulGraph()) {
            configuration.setUrlPrefix(urlPrefix);
            return configuration.getGraphRenderer(getUrlName());
        }
        else {
            BuildResultGraph graphType = configuration.getGraphType();
            try {
                response.sendRedirect2(request.getContextPath() + graphType.getExampleImage());
            }
            catch (IOException exception) {
                LOGGER.log(Level.SEVERE, "Can't create graph: " + graphType, exception);
            }

            return null;
        }
    }

    /**
     * Returns whether the trend graph is visible.
     *
     * @param request
     *            the request to get the cookie from
     * @return <code>true</code> if the trend is visible
     */
    public boolean isTrendVisible(final StaplerRequest request) {
        GraphConfigurationView configuration = createUserConfiguration(request);

        return configuration.isVisible() && configuration.hasMeaningfulGraph();
    }

    /**
     * Returns the ID of the selected trend graph.
     *
     * @return ID of the selected trend graph
     */
    public String getTrendGraphId() {
        GraphConfigurationView configuration = createUserConfiguration(Stapler.getCurrentRequest());

        return configuration.getGraphType().getId();
    }

    /**
     * Returns whether the trend graph is deactivated.
     *
     * @param request
     *            the request to get the cookie from
     * @return <code>true</code> if the trend is deactivated
     */
    public boolean isTrendDeactivated(final StaplerRequest request) {
        return createUserConfiguration(request).isDeactivated();
    }

    /**
     * Returns whether the enable trend graph link should be shown.
     *
     * @param request
     *            the request to get the cookie from
     * @return the graph configuration
     */
    public boolean canShowEnableTrendLink(final StaplerRequest request) {
        GraphConfigurationView configuration = createUserConfiguration(request);
        if (configuration.hasMeaningfulGraph()) {
            return !configuration.isDeactivated() && !configuration.isVisible();
        }
        return false;
    }

    /**
     * Creates a view to configure the trend graph for the current user.
     *
     * @param request
     *            Stapler request
     * @return a view to configure the trend graph for the current user
     */
    protected GraphConfigurationView createUserConfiguration(final StaplerRequest request) {
        return new UserGraphConfigurationView(createConfiguration(), getOwner(),
                getUrlName(), request.getCookies(), createBuildHistory());
    }

    /**
     * Creates a view to configure the trend graph defaults.
     *
     * @return a view to configure the trend graph defaults
     */
    protected GraphConfigurationView createDefaultConfiguration() {
        return new DefaultGraphConfigurationView(createConfiguration(), getOwner(),
                getUrlName(), createBuildHistory());
    }

    /**
     * Creates the build history.
     *
     * @return build history
     */
    protected BuildHistory createBuildHistory() {
        Run<?, ?> lastFinishedRun = getLastFinishedRun();
        if (lastFinishedRun == null) {
            return new NullBuildHistory();
        }
        else {
            return new BuildHistory(lastFinishedRun, resultActionType, false, false);
        }
    }

    /**
     * Creates the graph configuration.
     *
     * @return the graph configuration
     */
    private GraphConfiguration createConfiguration() {
        return createConfiguration(getAvailableGraphs());
    }

    /**
     * Returns the sorted list of available graphs.
     *
     * @return the available graphs
     */
    @SuppressWarnings("NP")
    protected List<BuildResultGraph> getAvailableGraphs() {
        List<BuildResultGraph> availableGraphs = Lists.newArrayList();

        availableGraphs.add(new NewVersusFixedGraph());
        availableGraphs.add(new PriorityGraph());
        availableGraphs.add(new TotalsGraph());
        if (hasValidResults()) {
            availableGraphs.add(new HealthGraph(getLastAction().getHealthDescriptor()));
        }
        else {
            availableGraphs.add(new HealthGraph(new NullHealthDescriptor()));
        }
        availableGraphs.add(new DifferenceGraph());
        availableGraphs.add(new EmptyGraph());
        availableGraphs.add(new NullGraph());
        availableGraphs.add(new AnnotationsByUserGraph());

        return availableGraphs;
    }

    /**
     * Creates the graph configuration.
     *
     * @param availableGraphs
     *            the available graphs
     * @return the graph configuration.
     */
    protected GraphConfiguration createConfiguration(final List<BuildResultGraph> availableGraphs) {
        return new GraphConfiguration(availableGraphs);
    }

    /**
     * Returns the icon URL for the side-panel in the owner screen. If there
     * is no valid result yet, then <code>null</code> is returned.
     *
     * @return the icon URL for the side-panel in the owner screen
     */
    @Override
    public String getIconFileName() {
        ResultAction<?> lastAction = getLastAction();
        if (lastAction != null && lastAction.getResult().hasAnnotations()) {
            return Jenkins.RESOURCE_PATH + iconUrl;
        }
        return null;
    }

    @Override
    public final String getUrlName() {
        return pluginUrl;
    }

    /**
     * Returns whether this owner has a valid result action attached.
     *
     * @return <code>true</code> if the results are valid
     */
    public final boolean hasValidResults() {
        return getLastAction() != null;
    }

    /**
     * Returns the last valid result action.
     *
     * @return the last valid result action, or <code>null</code> if no such
     *         action is found
     */
    @CheckForNull
    public ResultAction<?> getLastAction() {
        Run<?, ?> lastRun = getLastFinishedRun();
        if (lastRun == null) {
            return null;
        }
        else {
            return getResultAction(lastRun);
        }
    }

    /**
     * Returns the result action for the specified build.
     *
     * @param lastRun
     *            the build to get the action for
     * @return the action or <code>null</code> if there is no such action
     */
    @CheckForNull
    protected T getResultAction(final Run<?, ?> lastRun) {
        return lastRun.getAction(resultActionType);
    }

    /**
     * Returns the last finished run.
     *
     * @return the last finished run or <code>null</code> if there is no
     *         such run
     */
    @CheckForNull @Exported
    public Run<?, ?> getLastFinishedRun() {
        if (owner == null) {
            return null;
        }
        Run<?, ?> lastRun = owner.getLastBuild();
        while (lastRun != null && (lastRun.isBuilding() || getResultAction(lastRun) == null)) {
            lastRun = lastRun.getPreviousBuild();
        }
        return lastRun;
    }

    /**
     * Returns the last finished build.
     *
     * @return the last finished build or <code>null</code> if there is no
     *         such build
     * @deprecated use
     *             {@link #getLastFinishedRun()}
     */
    @Deprecated @CheckForNull @Exported
    public AbstractBuild<?, ?> getLastFinishedBuild() {
        Run<?, ?> lastFinishedRun = getLastFinishedRun();
        return lastFinishedRun instanceof AbstractBuild ? (AbstractBuild<?, ?>) lastFinishedRun : null;
    }

    /**
     * Redirects the index page to the last result.
     *
     * @param request
     *            Stapler request
     * @param response
     *            Stapler response
     * @throws IOException
     *             in case of an error
     */
    public void doIndex(final StaplerRequest request, final StaplerResponse response) throws IOException {
        Run<?, ?> lastRun = getLastFinishedRun();
        if (lastRun != null) {
            response.sendRedirect2(String.format("../%d/%s", lastRun.getNumber(), resultUrl));
        }
    }
 
    /**
     * Creates a new instance of {@link AbstractProjectAction}.
     *
     * @param project
     *            the project that owns this action
     * @param resultActionType
     *            the type of the result action
     * @param plugin
     *            the plug-in that owns this action
     * @deprecated use
     *             {@link #AbstractProjectAction(Job, Class, Localizable, Localizable, String, String, String)}
     */
    @Deprecated
    public AbstractProjectAction(final AbstractProject<?, ?> project, final Class<? extends T> resultActionType, final PluginDescriptor plugin) {
        this((Job<?, ?>) project, resultActionType, null, null, plugin.getPluginName(), plugin.getIconUrl(), plugin.getPluginResultUrlName());
    }
}
