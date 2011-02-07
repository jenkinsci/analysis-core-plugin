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
     * Instantiates a new find bugs project action.
     *
     * @param project
     *            the project that owns this action
     */
    public AnalysisProjectAction(final AbstractProject<?, ?> project) {
        super(project, AnalysisResultAction.class, new AnalysisDescriptor());
    }

    /** {@inheritDoc} */
    public String getDisplayName() {
        return Messages.Analysis_ProjectAction_Name();
    }

    /** {@inheritDoc} */
    @Override
    public String getTrendName() {
        return Messages.Analysis_Trend_Name();
    }

    /** {@inheritDoc} */
    @Override
    protected GraphConfigurationView createDefaultConfiguration() {
        if (hasValidResults()) {
            return new AnalysisDefaultGraphConfigurationView(createConfiguration(), getProject(),
                    getUrlName(), getLastAction());
        }
        else {
            return new AnalysisDefaultGraphConfigurationView(createConfiguration(), getProject(),
                    getUrlName());
        }
    }

    /** {@inheritDoc} */
    @Override
    protected GraphConfigurationView createUserConfiguration(final StaplerRequest request) {
        if (hasValidResults()) {
            return new AnalysisUserGraphConfigurationView(createConfiguration(), getProject(),
                    getUrlName(), request.getCookies(), getLastAction());
        }
        else {
            return new AnalysisUserGraphConfigurationView(createConfiguration(), getProject(),
                    getUrlName(), request.getCookies());
        }
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

