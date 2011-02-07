package hudson.plugins.analysis.collector.dashboard;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.plugins.analysis.collector.AnalysisProjectAction;
import hudson.plugins.analysis.collector.Messages;
import hudson.plugins.analysis.collector.OriginGraph;
import hudson.plugins.analysis.core.AbstractProjectAction;
import hudson.plugins.analysis.dashboard.AbstractWarningsGraphPortlet;
import hudson.plugins.analysis.graph.BuildResultGraph;
import hudson.plugins.view.dashboard.DashboardPortlet;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * A portlet that shows the warnings trend graph of warnings by type.
 *
 * @author Ulli Hafner
 */
public class WarningsOriginGraphPortlet extends AbstractWarningsGraphPortlet {
    /**
     * Creates a new instance of {@link WarningsOriginGraphPortlet}.
     *
     * @param name
     *            the name of the portlet
     * @param width
     *            width of the graph
     * @param height
     *            height of the graph
     * @param dayCountString
     *            number of days to consider
     */
    @DataBoundConstructor
    public WarningsOriginGraphPortlet(final String name, final String width, final String height, final String dayCountString) {
        super(name, width, height, dayCountString);
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

    /** {@inheritDoc} */
    @Override
    protected BuildResultGraph getGraphType() {
        return new OriginGraph();
    }

    /**
     * Extension point registration.
     *
     * @author Ulli Hafner
     */
    @Extension(optional = true)
    public static class WarningsGraphDescriptor extends Descriptor<DashboardPortlet> {
        @Override
        public String getDisplayName() {
            return Messages.Portlet_WarningsOriginGraph();
        }
    }
}

