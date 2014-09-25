package hudson.plugins.analysis.collector;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.model.AbstractProject;
import hudson.plugins.analysis.core.BuildHistory;
import hudson.plugins.analysis.graph.DefaultGraphConfigurationView;
import hudson.plugins.analysis.graph.GraphConfiguration;

/**
 * Adds some more parameters to the configuration view.
 *
 * @author Ulli Hafner
 */
public class AnalysisDefaultGraphConfigurationView extends DefaultGraphConfigurationView {
    /** The graph configuration. */
    private final AnalysisGraphConfiguration analysisConfiguration;

    /**
     * Creates a new instance of {@link AnalysisDefaultGraphConfigurationView}.
     *
     * @param configuration
     *            the graph configuration
     * @param project
     *            the owning project to configure the graphs for
     * @param pluginName
     *            The name of the plug-in.
     * @param buildHistory
     *            the build history for this project
     */
    public AnalysisDefaultGraphConfigurationView(final AnalysisGraphConfiguration configuration, final AbstractProject<?, ?> project,
            final String pluginName, final BuildHistory buildHistory) {
        super(configuration, project, pluginName, buildHistory);

        analysisConfiguration = configuration;
    }

    @Override
    protected void persistValue(final String value, final String pluginName, final StaplerRequest request,
            final StaplerResponse response) throws FileNotFoundException, IOException {
        super.persistValue(value, pluginName, request, response);

        GraphConfiguration configuration;
        if (analysisConfiguration.canDeacticateOtherTrendGraphs()) {
            configuration = GraphConfiguration.createDeactivated();
        }
        else {
            configuration = GraphConfiguration.createDefault();
        }
        for (AnalysisPlugin plugin : AnalysisPlugin.all()) {
            super.persistValue(configuration.serializeToString(), plugin.getName(),
                    request, response);
        }
    }

    /**
     * Returns whether the trend graphs of the other plug-ins could be deactivated.
     *
     * @return <code>true</code> if the trend graphs of the other plug-ins could be deactivated
     */
    public boolean canDeacticateOtherTrendGraphs() {
        return analysisConfiguration.canDeacticateOtherTrendGraphs();
    }
}

