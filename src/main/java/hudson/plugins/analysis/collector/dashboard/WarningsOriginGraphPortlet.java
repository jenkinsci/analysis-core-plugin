package hudson.plugins.analysis.collector.dashboard;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.plugins.analysis.collector.AnalysisDescriptor;
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
public final class WarningsOriginGraphPortlet extends AbstractWarningsGraphPortlet {
    private final boolean isCheckStyleDeactivated;
    private final boolean isDryDeactivated;
    private final boolean isFindBugsDeactivated;
    private final boolean isPmdDeactivated;
    private final boolean isOpenTasksDeactivated;
    private final boolean isWarningsDeactivated;

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
     * @param isCheckStyleActivated
     *            determines whether to show the warnings from Checkstyle
     * @param isDryActivated
     *            determines whether to show the warnings from DRY
     * @param isFindBugsActivated
     *            determines whether to show the warnings from FindBugs
     * @param isPmdActivated
     *            determines whether to show the warnings from PMD
     * @param isOpenTasksActivated
     *            determines whether to show open tasks
     * @param isWarningsActivated
     *            determines whether to show compiler warnings
     */
    // CHECKSTYLE:OFF
    @DataBoundConstructor @SuppressWarnings("PMD.ExcessiveParameterList")
    public WarningsOriginGraphPortlet(final String name, final String width, final String height, final String dayCountString,
            final boolean isCheckStyleActivated, final boolean isDryActivated,
            final boolean isFindBugsActivated, final boolean isPmdActivated,
            final boolean isOpenTasksActivated, final boolean isWarningsActivated) {
        super(name, width, height, dayCountString);

        isDryDeactivated = !isDryActivated;
        isFindBugsDeactivated = !isFindBugsActivated;
        isPmdDeactivated = !isPmdActivated;
        isOpenTasksDeactivated = !isOpenTasksActivated;
        isWarningsDeactivated = !isWarningsActivated;
        isCheckStyleDeactivated = !isCheckStyleActivated;

        configureGraph(getGraphType());
    }
    // CHECKSTYLE:ON

    /**
     * Returns whether CheckStyle results should be collected.
     *
     * @return <code>true</code> if CheckStyle results should be collected, <code>false</code> otherwise
     */
    public boolean isCheckStyleActivated() {
        return !isCheckStyleDeactivated;
    }

    /**
     * Returns whether DRY results should be collected.
     *
     * @return <code>true</code> if DRY results should be collected, <code>false</code> otherwise
     */
    public boolean isDryActivated() {
        return !isDryDeactivated;
    }

    /**
     * Returns whether FindBugs results should be collected.
     *
     * @return <code>true</code> if FindBugs results should be collected, <code>false</code> otherwise
     */
    public boolean isFindBugsActivated() {
        return !isFindBugsDeactivated;
    }

    /**
     * Returns whether PMD results should be collected.
     *
     * @return <code>true</code> if PMD results should be collected, <code>false</code> otherwise
     */
    public boolean isPmdActivated() {
        return !isPmdDeactivated;
    }

    /**
     * Returns whether open tasks should be collected.
     *
     * @return <code>true</code> if open tasks should be collected, <code>false</code> otherwise
     */
    public boolean isOpenTasksActivated() {
        return !isOpenTasksDeactivated;
    }

    /**
     * Returns whether compiler warnings results should be collected.
     *
     * @return <code>true</code> if compiler warnings results should be collected, <code>false</code> otherwise
     */
    public boolean isWarningsActivated() {
        return !isWarningsDeactivated;
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
        return new OriginGraph(isCheckStyleActivated(), isDryActivated(), isFindBugsActivated(), isPmdActivated(), isOpenTasksActivated(), isWarningsActivated());
    }

    /**
     * Extension point registration.
     */
    @Extension(optional = true)
    public static class WarningsGraphDescriptor extends Descriptor<DashboardPortlet> {
        /**
         * Returns whether the Checkstyle plug-in is installed.
         *
         * @return <code>true</code> if the Checkstyle plug-in is installed,
         *         <code>false</code> if not.
         */
        public boolean isCheckStyleInstalled() {
            return AnalysisDescriptor.isCheckStyleInstalled();
        }

        /**
         * Returns whether the Dry plug-in is installed.
         *
         * @return <code>true</code> if the Dry plug-in is installed,
         *         <code>false</code> if not.
         */
        public boolean isDryInstalled() {
            return AnalysisDescriptor.isDryInstalled();
        }

        /**
         * Returns whether the FindBugs plug-in is installed.
         *
         * @return <code>true</code> if the FindBugs plug-in is installed,
         *         <code>false</code> if not.
         */
        public boolean isFindBugsInstalled() {
            return AnalysisDescriptor.isFindBugsInstalled();
        }

        /**
         * Returns whether the PMD plug-in is installed.
         *
         * @return <code>true</code> if the PMD plug-in is installed,
         *         <code>false</code> if not.
         */
        public boolean isPmdInstalled() {
            return AnalysisDescriptor.isPmdInstalled();
        }

        /**
         * Returns whether the Open Tasks plug-in is installed.
         *
         * @return <code>true</code> if the Open Tasks plug-in is installed,
         *         <code>false</code> if not.
         */
        public boolean isOpenTasksInstalled() {
            return AnalysisDescriptor.isOpenTasksInstalled();
        }

        /**
         * Returns whether the Warnings plug-in is installed.
         *
         * @return <code>true</code> if the Warnings plug-in is installed,
         *         <code>false</code> if not.
         */
        public boolean isWarningsInstalled() {
            return AnalysisDescriptor.isWarningsInstalled();
        }

        @Override
        public String getDisplayName() {
            return Messages.Portlet_WarningsOriginGraph();
        }
    }
}

