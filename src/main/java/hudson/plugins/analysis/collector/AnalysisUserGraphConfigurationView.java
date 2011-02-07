package hudson.plugins.analysis.collector;

import hudson.model.AbstractProject;
import hudson.plugins.analysis.core.ResultAction;
import hudson.plugins.analysis.graph.GraphConfiguration;
import hudson.plugins.analysis.graph.UserGraphConfigurationView;

import javax.servlet.http.Cookie;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

/**
 * Adds some more parameters to the configuration view.
 *
 * @author Ulli Hafner
 */
public class AnalysisUserGraphConfigurationView extends UserGraphConfigurationView {
    /** The graph configuration. */
    private final AnalysisGraphConfiguration analysisConfiguration;

    /**
     * Creates a new instance of {@link AnalysisUserGraphConfigurationView}.
     *
     * @param configuration
     *            the graph configuration
     * @param project
     *            the owning project to configure the graphs for
     * @param pluginName
     *            The name of the plug-in. Also used as the suffix of the cookie
     *            name that is used to persist the configuration per user.
     * @param cookies
     *            the cookies containing the graph configuration
     */
    public AnalysisUserGraphConfigurationView(final AnalysisGraphConfiguration configuration,
            final AbstractProject<?, ?> project, final String pluginName, final Cookie[] cookies) {
        super(configuration, project, pluginName, cookies);

        analysisConfiguration = configuration;
    }

    /**
     * Creates a new instance of {@link AnalysisUserGraphConfigurationView}.
     *
     * @param configuration
     *            the graph configuration
     * @param project
     *            the owning project to configure the graphs for
     * @param pluginName
     *            The name of the plug-in. Also used as the suffix of the cookie
     *            name that is used to persist the configuration per user.
     * @param cookies
     *            the cookies containing the graph configuration
     * @param resultAction
     *            the last valid action for this project
     */
    public AnalysisUserGraphConfigurationView(final AnalysisGraphConfiguration configuration, final AbstractProject<?, ?> project,
            final String pluginName, final Cookie[] cookies, final ResultAction<?> resultAction) {
        super(configuration, project, pluginName, cookies, resultAction);

        analysisConfiguration = configuration;
    }

    /** {@inheritDoc} */
    @Override
    protected void persistValue(final String value, final String pluginName, final StaplerRequest request,
            final StaplerResponse response) {
        super.persistValue(value, pluginName, request, response);

        GraphConfiguration configuration;
        if (analysisConfiguration.canDeacticateOtherTrendGraphs()) {
            configuration = GraphConfiguration.createDeactivated();
        }
        else {
            configuration = GraphConfiguration.createDefault();
        }
        for (String plugin : AnalysisDescriptor.getPlugins()) {
            super.persistValue(configuration.serializeToString(), plugin, request, response);
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

