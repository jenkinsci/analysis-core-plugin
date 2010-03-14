package hudson.plugins.analysis.collector.dashboard;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.model.Hudson;
import hudson.model.Job;
import hudson.plugins.analysis.collector.AnalysisDescriptor;
import hudson.plugins.analysis.collector.Messages;
import hudson.plugins.analysis.core.AbstractProjectAction;
import hudson.plugins.checkstyle.CheckStyleProjectAction;
import hudson.plugins.dry.DryProjectAction;
import hudson.plugins.findbugs.FindBugsProjectAction;
import hudson.plugins.pmd.PmdProjectAction;
import hudson.plugins.tasks.TasksProjectAction;
import hudson.plugins.view.dashboard.DashboardPortlet;
import hudson.plugins.warnings.WarningsProjectAction;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * A dashboard that shows a table with the number of warnings in a job.
 *
 * @author Ulli Hafner
 */
public class WarningsTablePortlet extends DashboardPortlet {
    /** Message to be shown if no result action is found. */
    private static final String NO_RESULTS_FOUND = "-";

    /**
     * Creates a new instance of {@link WarningsTablePortlet}.
     *
     * @param name
     *            the name of the dashboard
     */
    @DataBoundConstructor
    public WarningsTablePortlet(final String name) {
        super(name);
    }

    /**
     * Returns the number of Checkstyle warnings for the specified job.
     *
     * @param job
     *            the job to get the warnings for
     * @return the number of Checkstyle warnings
     */
    public String getCheckStyle(final Job<?, ?> job) {
        if (AnalysisDescriptor.isCheckStyleInstalled()) {
            return getWarnings(job, CheckStyleProjectAction.class);
        }
        return NO_RESULTS_FOUND;
    }

    /**
     * Returns the number of duplicate code warnings for the specified job.
     *
     * @param job
     *            the job to get the warnings for
     * @return the number of duplicate code warnings
     */
    public String getDry(final Job<?, ?> job) {
        if (AnalysisDescriptor.isDryInstalled()) {
            return getWarnings(job, DryProjectAction.class);
        }
        return NO_RESULTS_FOUND;
    }

    /**
     * Returns the number of FindBugs warnings for the specified job.
     *
     * @param job
     *            the job to get the warnings for
     * @return the number of FindBugs warnings
     */
    public String getFindBugs(final Job<?, ?> job) {
        if (AnalysisDescriptor.isFindBugsInstalled()) {
            return getWarnings(job, FindBugsProjectAction.class);
        }
        return NO_RESULTS_FOUND;
    }

    /**
     * Returns the number of PMD warnings for the specified job.
     *
     * @param job
     *            the job to get the warnings for
     * @return the number of PMD warnings
     */
    public String getPmd(final Job<?, ?> job) {
        if (AnalysisDescriptor.isPmdInstalled()) {
            return getWarnings(job, PmdProjectAction.class);
        }
        return NO_RESULTS_FOUND;
    }

    /**
     * Returns the number of open tasks for the specified job.
     *
     * @param job
     *            the job to get the tasks for
     * @return the number of open tasks
     */
    public String getTasks(final Job<?, ?> job) {
        if (AnalysisDescriptor.isOpenTasksInstalled()) {
            return getWarnings(job, TasksProjectAction.class);
        }
        return NO_RESULTS_FOUND;
    }

    /**
     * Returns the number of compiler warnings for the specified job.
     *
     * @param job
     *            the job to get the warnings for
     * @return the number of compiler warnings
     */
    public String getWarnings(final Job<?, ?> job) {
        if (AnalysisDescriptor.isWarningsInstalled()) {
            return getWarnings(job, WarningsProjectAction.class);
        }
        return NO_RESULTS_FOUND;
    }

    /**
     * Returns the warnings for the specified action.
     *
     * @param job
     *            the job to get the action from
     * @param actionType
     *            the type of the action
     * @return the number of warnings
     */
    private String getWarnings(final Job<?, ?> job, final Class<? extends AbstractProjectAction<?>> actionType) {
        AbstractProjectAction<?> action = job.getAction(actionType);
        if (action != null && action.hasValidResults()) {
            return Integer.toString(action.getLastAction().getResult().getNumberOfAnnotations());
        }
        return NO_RESULTS_FOUND;
    }

    /**
     * Extension point registration.
     *
     * @author Ulli Hafner
     */
    public static class WarningsPerJobDescriptor extends Descriptor<DashboardPortlet> {
        /**
         * Creates a new descriptor if the dashboard-view plug-in is installed.
         *
         * @return the descriptor or <code>null</code> if the dashboard view is not installed
         */
        @Extension
        public static WarningsPerJobDescriptor newInstance() {
            if (Hudson.getInstance().getPlugin("dashboard-view") != null) {
                return new WarningsPerJobDescriptor();
            }
            return null;
        }

        @Override
        public String getDisplayName() {
            return Messages.Portlet_WarningsTable();
        }
    }
}

