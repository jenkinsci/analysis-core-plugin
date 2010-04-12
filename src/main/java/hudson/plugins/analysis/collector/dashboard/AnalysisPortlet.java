package hudson.plugins.analysis.collector.dashboard;

import hudson.plugins.analysis.collector.AnalysisProjectAction;
import hudson.plugins.analysis.core.AbstractProjectAction;
import hudson.plugins.analysis.dashboard.AbstractWarningsGraphPortlet;

/**
 * A base class for portlets of the Checkstyle plug-in.
 *
 * @author Ulli Hafner
 */
public abstract class AnalysisPortlet extends AbstractWarningsGraphPortlet {
    /**
     * Creates a new instance of {@link AnalysisPortlet}.
     *
     * @param name
     *            the name of the portlet
     */
    public AnalysisPortlet(final String name) {
        super(name);
    }

    /** {@inheritDoc} */
    @Override
    protected Class<? extends AbstractProjectAction<?>> getAction() {
        return AnalysisProjectAction.class;
    }

    /** {@inheritDoc} */
    @Override
    protected String getPluginName() {
        return "analysis";
    }
}
