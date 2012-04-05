package hudson.plugins.analysis.collector;

import hudson.model.AbstractProject;
import hudson.plugins.analysis.core.AbstractProjectAction;
import hudson.plugins.analysis.graph.BuildResultGraph;
import hudson.plugins.analysis.graph.GraphConfigurationView;

import java.util.List;

import org.kohsuke.stapler.StaplerRequest;

/**
 * Entry point to visualize the trend graph in the project screen.
 * Drawing of the graph is delegated to the associated
 * {@link AnalysisResultAction}.
 *
 * @author Ulli Hafner
 */
public class AnalysisProjectAction extends AbstractProjectAction<AnalysisResultAction> {
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
    }

    @Override
    protected GraphConfigurationView createDefaultConfiguration() {
        return new AnalysisDefaultGraphConfigurationView(createConfiguration(), getProject(),
                getUrlName(), createBuildHistory());
    }

    @Override
    protected GraphConfigurationView createUserConfiguration(final StaplerRequest request) {
        return new AnalysisUserGraphConfigurationView(createConfiguration(), getProject(),
                getUrlName(), request.getCookies(), createBuildHistory());
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
}

