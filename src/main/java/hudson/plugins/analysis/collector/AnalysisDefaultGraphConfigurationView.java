package hudson.plugins.analysis.collector;

import hudson.model.AbstractProject;
import hudson.plugins.analysis.core.ResultAction;
import hudson.plugins.analysis.graph.DefaultGraphConfigurationView;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

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
     */
    public AnalysisDefaultGraphConfigurationView(final AnalysisGraphConfiguration configuration, final AbstractProject<?, ?> project,
            final String pluginName) {
        super(configuration, project, pluginName);

        analysisConfiguration = configuration;
    }

    /**
     * Creates a new instance of {@link AnalysisDefaultGraphConfigurationView}.
     *
     * @param configuration
     *            the graph configuration
     * @param project
     *            the owning project to configure the graphs for
     * @param pluginName
     *            The name of the plug-in.
     * @param lastAction
     *            the last valid action for this project
     */
    public AnalysisDefaultGraphConfigurationView(final AnalysisGraphConfiguration configuration, final AbstractProject<?, ?> project,
            final String pluginName, final ResultAction<?> lastAction) {
        super(configuration, project, pluginName, lastAction);

        analysisConfiguration = configuration;
    }

    /** {@inheritDoc} */
    @Override
    protected void persistValue(final String value, final String pluginName, final StaplerRequest request,
            final StaplerResponse response) throws FileNotFoundException, IOException {
        super.persistValue(value, pluginName, request, response);

        if (analysisConfiguration.canDeacticateOtherTrendGraphs()) {
            for (String plugin : AnalysisDescriptor.getPlugins()) {
                super.persistValue(value, plugin, request, response);
            }
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

