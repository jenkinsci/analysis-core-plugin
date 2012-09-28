package hudson.plugins.analysis.collector;

import hudson.model.AbstractBuild;
import hudson.plugins.analysis.core.HealthDescriptor;
import hudson.plugins.analysis.core.PluginDescriptor;
import hudson.plugins.analysis.core.AbstractResultAction;

/**
 * Controls the live cycle of the analysis results. This action persists the
 * results of the static analysis tools of a build and displays the results on the
 * build page. The actual visualization of the results is defined in the
 * matching <code>summary.jelly</code> file.
 * <p>
 * Moreover, this class renders the warnings result trend.
 * </p>
 *
 * @author Ulli Hafner
 */
public class AnalysisResultAction extends AbstractResultAction<AnalysisResult> {
    /**
     * Creates a new instance of {@link AbstractResultAction}.
     *
     * @param owner
     *            the associated build of this action
     * @param healthDescriptor
     *            health descriptor to use
     * @param result
     *            the result of this build
     */
    public AnalysisResultAction(final AbstractBuild<?, ?> owner, final HealthDescriptor healthDescriptor, final AnalysisResult result) {
        super(owner, new AnalysisHealthDescriptor(healthDescriptor), result);
    }

    /** {@inheritDoc} */
    public String getDisplayName() {
        return Messages.Analysis_ProjectAction_Name();
    }

    @Override
    protected PluginDescriptor getDescriptor() {
        return new AnalysisDescriptor();
    }
}
