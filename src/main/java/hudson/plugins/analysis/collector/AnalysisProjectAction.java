package hudson.plugins.analysis.collector;

import java.util.List;

import org.kohsuke.stapler.StaplerRequest;

import com.google.common.collect.Lists;

import hudson.model.AbstractProject;
import hudson.plugins.analysis.core.AbstractProjectAction;
import hudson.plugins.analysis.graph.BuildResultGraph;
import hudson.plugins.analysis.graph.GraphConfigurationView;

/**
 * Entry point to visualize the trend graph in the project screen. Drawing of the graph is delegated to the associated
 * {@link AnalysisResultAction}.
 *
 * @author Ulli Hafner
 */
public class AnalysisProjectAction extends AbstractProjectAction<AnalysisResultAction> {
    private transient WarningsAggregator warningsAggregator;

    /**
     * Instantiates a new {@link AnalysisProjectAction}.
     *
     * @param project
     *            the project that owns this action
     */
    public AnalysisProjectAction(final AbstractProject<?, ?> project) {
        this(project, AnalysisResultAction.class);
    }

    /**
     * Instantiates a new {@link AnalysisProjectAction}.
     *
     * @param project
     *            the project that owns this action
     * @param type
     *            the result action type
     */
    public AnalysisProjectAction(final AbstractProject<?, ?> project, final Class<? extends AnalysisResultAction> type) {
        super(project, type, Messages._Analysis_ProjectAction_Name(), Messages._Analysis_Trend_Name(),
                AnalysisDescriptor.PLUGIN_ID, AnalysisDescriptor.ICON_URL, AnalysisDescriptor.RESULT_URL);

        createAggregator();
    }

    private void createAggregator() {
        warningsAggregator = new WarningsAggregator();
        warningsAggregator.hideJobPrefix();
    }

    /**
     * Restores the transient fields.
     *
     * @return this
     */
    protected Object readResolve() {
        createAggregator();

        return this;
    }

    @Override
    protected GraphConfigurationView createDefaultConfiguration() {
        return new AnalysisDefaultGraphConfigurationView(createConfiguration(), getProject(), getUrlName(),
                createBuildHistory());
    }

    @Override
    protected GraphConfigurationView createUserConfiguration(final StaplerRequest request) {
        return new AnalysisUserGraphConfigurationView(createConfiguration(), getProject(), getUrlName(),
                request.getCookies(), createBuildHistory());
    }

    /**
     * Creates the graph configuration.
     *
     * @return the graph configuration
     */
    protected AnalysisGraphConfiguration createConfiguration() {
        List<BuildResultGraph> availableGraphs = getAvailableGraphs();
        availableGraphs.add(0, new OriginGraph());

        return new AnalysisGraphConfiguration(availableGraphs);
    }

    public List<AnalysisPlugin> getPlugins() {
        List<AnalysisPlugin> active = Lists.newArrayList();
        for (AnalysisPlugin plugin : AnalysisPlugin.all()) {
            if (plugin.hasResultsFor(getProject())) {
                active.add(plugin);
            }
        }
        return active;
    }

    public String getIconUrl(final String name) {
        return AnalysisPlugin.getPlugin(name).getIconUrl();
    }

    public String getWarnings(final AnalysisPlugin plugin) {
        return warningsAggregator.getWarnings(getProject(), plugin);
    }
}
