package hudson.plugins.analysis.graph;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import com.google.common.collect.Lists;

import hudson.model.ModelObject;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;

import hudson.plugins.analysis.core.AbstractHealthDescriptor;
import hudson.plugins.analysis.core.BuildHistory;

import hudson.util.Graph;

/**
 * Configuration properties of a trend graph.
 */
public abstract class GraphConfigurationView implements ModelObject {
    private static final Logger LOGGER = Logger.getLogger(GraphConfigurationView.class.getName());

    /** The root URL to return back when leaving this page. */
    private static final String ROOT_URL = "../../";
    /** The owning project to configure the graphs for. */
    private final AbstractProject<?, ?> project;

    private final String pluginName;
    private final BuildHistory buildHistory;
    private final AbstractHealthDescriptor healthDescriptor; // NOPMD
    private final GraphConfiguration configuration;

    /**
     * Creates a new instance of {@link GraphConfigurationView}.
     *
     * @param configuration
     *            the graph configuration
     * @param project
     *            the owning project to configure the graphs for
     * @param pluginName
     *            the name of the plug-in
     * @param buildHistory
     *            the build history for this project
     */
    public GraphConfigurationView(final GraphConfiguration configuration, final AbstractProject<?, ?> project, final String pluginName, final BuildHistory buildHistory) {
        this.configuration = configuration;
        this.project = project;
        this.pluginName = pluginName;

        this.buildHistory = buildHistory;
        healthDescriptor = null != buildHistory.getBaseline() ? buildHistory.getBaseline().getHealthDescriptor() : null;
    }

    /**
     * Creates a file with for the default values.
     *
     * @param project
     *            the project used as directory for the file
     * @param pluginName
     *            the name of the plug-in
     * @return the created file
     */
    protected static File createDefaultsFile(final AbstractProject<?, ?> project, final String pluginName) {
        return new File(project.getRootDir(), pluginName + ".txt");
    }

    /**
     * Returns the list of available graphs.
     *
     * @return the list of available graphs
     */
    public List<? extends BuildResultGraph> getAvailableGraphs() {
        ArrayList<BuildResultGraph> selectable = Lists.newArrayList();
        for (BuildResultGraph graph : configuration.getRegisteredGraphs()) {
            if (graph.isSelectable()) {
                selectable.add(graph);
            }
        }
        return selectable;
    }

    /**
     * Returns the project.
     *
     * @return the project
     */
    public AbstractProject<?, ?> getOwner() {
        return project;
    }

    /**
     * Returns the root URL of this object.
     *
     * @return the root URL of this object
     */
    public String getRootUrl() {
        return project.getAbsoluteUrl() + pluginName;
    }

    /**
     * Returns the plug-in name.
     *
     * @return the plug-in name
     */
    public String getPluginName() {
        return pluginName;
    }

    /**
     * Returns the description for this view.
     *
     * @return the description for this view
     */
    public abstract String getDescription();

    /**
     * Saves the configured values. Subclasses need to implement the actual persistence.
     *
     * @param request
     *            Stapler request
     * @param response
     *            Stapler response
     */
    public void doSave(final StaplerRequest request, final StaplerResponse response) {
        try {
            JSONObject formData = request.getSubmittedForm();

            if (configuration.initializeFrom(formData)) {
                persistValue(configuration.serializeToString(), getPluginName(), request, response);
            }
        }
        catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Can't save the form data: " + request, exception);
        }
        catch (ServletException exception) {
            LOGGER.log(Level.SEVERE, "Can't process the form data: " + request, exception);
        }
        finally {
            try {
                response.sendRedirect(ROOT_URL);
            }
            catch (IOException exception) {
                LOGGER.log(Level.SEVERE, "Can't redirect", exception);
            }
        }
    }

    /**
     * Checks whether a meaningful graph is available.
     *
     * @return <code>true</code>, if there is such a graph
     */
    public boolean hasMeaningfulGraph() {
        if (buildHistory.hasPreviousResult()) {
            if (configuration.isDayCountDefined()) {
                AbstractBuild<?, ?> baseline = buildHistory.getBaseline().getBuild();
                AbstractBuild<?, ?> previous = buildHistory.getPreviousResult().getOwner();

                return BuildResultGraph.computeDayDelta(baseline.getTimestamp(), previous.getTimestamp())
                        < configuration.getDayCount();
            }
            else {
                return true;
            }
        }
        return false;
    }

    /**
     * Persists the configured values.
     *
     * @param value
     *            the values configured by the user.
     * @param pluginName
     *            the name of the plug-in
     * @param request
     *            Stapler request
     * @param response
     *            Stapler response
     * @throws IOException
     *             if the values could not be persisted
     */
    protected abstract void persistValue(String value, String pluginName,
            StaplerRequest request, StaplerResponse response) throws IOException;

    /**
     * This method will be called by Stapler if an example image for the
     * specified graph should be rendered.
     *
     * @param graphId
     *            the ID of the graph to render
     * @param request
     *            Stapler request
     * @param response
     *            Stapler response
     * @return <code>null</code>
     */
    public Object getDynamic(final String graphId, final StaplerRequest request,
            final StaplerResponse response) {
        try {
            BuildResultGraph graph = configuration.getGraph(graphId);
            if (hasMeaningfulGraph()) {
                graph.setRootUrl(ROOT_URL);
                if (graph.isVisible()) {
                    return graph.getGraph(-1, configuration, pluginName, buildHistory.getBaseline());
                }
            }
            response.sendRedirect2(request.getContextPath() + graph.getExampleImage());
        }
        catch (IOException exception) {
            LOGGER.log(Level.SEVERE, "Can't create graph: " + request, exception);
        }

        return null;
    }

    /**
     * Returns the graph renderer of the specified graph.
     *
     * @param graph
     *            the graph
     * @return the graph renderer of the specified graph
     */
    public Graph getGraphRenderer(final BuildResultGraph graph) {
        return graph.getGraph(getTimestamp(), configuration, pluginName, buildHistory.getBaseline());
    }

    /**
     * Returns the graph renderer of the current graph.
     *
     * @return the graph renderer of the current graph
     */
    public Graph getGraphRenderer() {
        return getGraphRenderer(getGraphType());
    }

    /**
     * Checks if the health graph is available.
     *
     * @return <code>true</code>, if the health graph is available
     */
    public boolean isHealthGraphAvailable() {
        return null != healthDescriptor && healthDescriptor.isEnabled();
    }

    /**
     * Returns the height.
     *
     * @return the height
     */
    public int getHeight() {
        return configuration.getHeight();
    }

    /**
     * Returns the width.
     *
     * @return the width
     */
    public int getWidth() {
        return configuration.getWidth();
    }

    /**
     * Returns whether the build date or the build number should be used as domain.
     *
     * @return the build date or the build number should be used as domain
     */
    public boolean getUseBuildDateAsDomain() {
        return configuration.useBuildDateAsDomain();
    }

    /**
     * Returns the time stamp of the associated build.
     *
     * @return the time stamp of the associated build.
     */
    public long getTimestamp() {
        return buildHistory.getTimestamp().getTimeInMillis();
    }

    /**
     * Returns the number of builds to consider.
     *
     * @return the number of builds to consider
     */
    public int getBuildCount() {
        return configuration.getBuildCount();
    }

    /**
     * Returns the number of builds to consider.
     *
     * @return the number of builds to consider
     */
    public String getBuildCountString() {
        return getStringValue(getBuildCount());
    }

    /**
     * Returns the value as integer. If the value is 0, then an empty string is
     * returned.
     *
     * @param value
     *            the value to convert
     * @return string representation of <code>value</code>
     */
    private String getStringValue(final int value) {
        return value == 0 ? StringUtils.EMPTY : String.valueOf(value);
    }

    /**
     * Returns the number of days to consider.
     *
     * @return the number of days to consider
     */
    public int getDayCount() {
        return configuration.getDayCount();
    }

    /**
     * Returns the number of days to consider.
     *
     * @return the number of days to consider
     */
    public String getDayCountString() {
        return getStringValue(getDayCount());
    }

    /**
     * Returns the type of the graph.
     *
     * @return the type
     */
    public BuildResultGraph getGraphType() {
        BuildResultGraph graphType = configuration.getGraphType();
        graphType.setRootUrl(getOwner().getAbsoluteUrl());
        return graphType;
    }

    /**
     * Returns whether the trend graph is visible or not.
     *
     * @return <code>true</code>, if the trend graph is visible, <code>false</code> otherwise
     */
    public boolean isVisible() {
        return getGraphType().isVisible();
    }

    /**
     * Returns whether the trend graph completely is deactivated.
     *
     * @return <code>true</code>, if the trend graph is deactivated, <code>false</code> otherwise
     */
    public boolean isDeactivated() {
        return getGraphType().isDeactivated();
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return configuration.toString();
    }

    /**
     * Returns the current health descriptor.
     *
     * @return the health descriptor
     */
    public AbstractHealthDescriptor getHealthDescriptor() {
        return healthDescriptor;
    }
}

