package hudson.plugins.analysis.collector.dashboard;

import hudson.Extension;
import hudson.model.Descriptor;
import hudson.model.Job;
import hudson.plugins.analysis.collector.AnalysisDescriptor;
import hudson.plugins.analysis.collector.AnalysisProjectAction;
import hudson.plugins.analysis.collector.Messages;
import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.core.AbstractProjectAction;
import hudson.plugins.analysis.dashboard.AbstractWarningsTablePortlet;
import hudson.plugins.checkstyle.CheckStyleProjectAction;
import hudson.plugins.dry.DryProjectAction;
import hudson.plugins.findbugs.FindBugsProjectAction;
import hudson.plugins.pmd.PmdProjectAction;
import hudson.plugins.tasks.TasksProjectAction;
import hudson.plugins.view.dashboard.DashboardPortlet;
import hudson.plugins.warnings.WarningsProjectAction;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * A portlet that shows a table with the number of warnings in a job.
 *
 * @author Ulli Hafner
 */
public class WarningsTablePortlet extends AbstractWarningsTablePortlet {
    /** Message to be shown if no result action is found. */
    private static final String NO_RESULTS_FOUND = "-";
    /** Determines whether images should be used in the table header. */
    private final boolean useImages;

    /**
     * Creates a new instance of {@link WarningsTablePortlet}.
     *
     * @param name
     *            the name of the portlet
     * @param useImages
     *            determines whether images should be used in the table header.
     */
    @DataBoundConstructor
    public WarningsTablePortlet(final String name, final boolean useImages) {
        super(name);
        this.useImages = useImages;
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

    /**
     * Returns whether images should be used in the table header.
     *
     * @return <code>true</code> if images should be used, <code>false</code> if
     *         text shuld be used
     */
    public boolean getUseImages() {
        return useImages;
    }

    /**
     * Returns whether icons should be used in the table header.
     *
     * @return <code>true</code> if icons should be used, <code>false</code> if
     *         text shuld be used
     */
    public boolean useIcons() {
        return useImages;
    }

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
    public boolean isTasksInstalled() {
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

    /**
     * Returns the number of Checkstyle warnings for the specified job.
     *
     * @param job
     *            the job to get the warnings for
     * @return the number of Checkstyle warnings
     */
    public String getCheckStyle(final Job<?, ?> job) {
        if (AnalysisDescriptor.isCheckStyleInstalled()) {
            return getWarnings(job, CheckStyleProjectAction.class, "checkstyle");
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
            return getWarnings(job, DryProjectAction.class, "dry");
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
            return getWarnings(job, FindBugsProjectAction.class, "findbugs");
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
            return getWarnings(job, PmdProjectAction.class, "pmd");
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
            return getWarnings(job, TasksProjectAction.class, "tasks");
        }
        return NO_RESULTS_FOUND;
    }

    /**
     * Returns the total number of warnings for the specified job.
     *
     * @param job
     *            the job to get the warnings for
     * @return the number of compiler warnings
     */
    @Override
    public String getWarnings(final Job<?, ?> job) {
        if (AnalysisDescriptor.isWarningsInstalled()) {
            return getWarnings(job, WarningsProjectAction.class, "warnings");
        }
        return NO_RESULTS_FOUND;
    }

    /**
     * Returns the number of warnings for the specified job.
     *
     * @param job
     *            the job to get the warnings for
     * @return the number of warnings
     */
    public String getTotal(final Job<?, ?> job) {
        return String.valueOf(
                toInt(getCheckStyle(job))
                + toInt(getDry(job))
                + toInt(getFindBugs(job))
                + toInt(getPmd(job))
                + toInt(getTasks(job))
                + toInt(getWarnings(job)));
    }

    /**
     * Returns the number of Checkstyle warnings for the specified jobs.
     *
     * @param jobs
     *            the jobs to get the warnings for
     * @return the number of Checkstyle warnings
     */
    public String getCheckStyle(final Collection<Job<?, ?>> jobs) {
        int sum = 0;
        for (Job<?, ?> job : jobs) {
            sum += toInt(getCheckStyle(job));
        }
        return String.valueOf(sum);
    }

    /**
     * Returns the number of Dry warnings for the specified jobs.
     *
     * @param jobs
     *            the jobs to get the warnings for
     * @return the number of Dry warnings
     */
    public String getDry(final Collection<Job<?, ?>> jobs) {
        int sum = 0;
        for (Job<?, ?> job : jobs) {
            sum += toInt(getDry(job));
        }
        return String.valueOf(sum);
    }

    /**
     * Returns the number of FindBugs warnings for the specified jobs.
     *
     * @param jobs
     *            the jobs to get the warnings for
     * @return the number of FindBugs warnings
     */
    public String getFindBugs(final Collection<Job<?, ?>> jobs) {
        int sum = 0;
        for (Job<?, ?> job : jobs) {
            sum += toInt(getFindBugs(job));
        }
        return String.valueOf(sum);
    }

    /**
     * Returns the number of PMD warnings for the specified jobs.
     *
     * @param jobs
     *            the jobs to get the warnings for
     * @return the number of PMD warnings
     */
    public String getPmd(final Collection<Job<?, ?>> jobs) {
        int sum = 0;
        for (Job<?, ?> job : jobs) {
            sum += toInt(getPmd(job));
        }
        return String.valueOf(sum);
    }

    /**
     * Returns the number of open tasks for the specified jobs.
     *
     * @param jobs
     *            the jobs to get the warnings for
     * @return the number of open tasks warnings
     */
    public String getTasks(final Collection<Job<?, ?>> jobs) {
        int sum = 0;
        for (Job<?, ?> job : jobs) {
            sum += toInt(getTasks(job));
        }
        return String.valueOf(sum);
    }

    /**
     * Returns the number of compiler warnings for the specified jobs.
     *
     * @param jobs
     *            the jobs to get the warnings for
     * @return the number of compiler warnings
     */
    @Override
    public String getWarnings(final Collection<Job<?, ?>> jobs) {
        int sum = 0;
        for (Job<?, ?> job : jobs) {
            sum += toInt(getWarnings(job));
        }
        return String.valueOf(sum);
    }

    /**
     * Returns the total number of warnings for all jobs.
     *
     * @param jobs
     *            the jobs to get the warnings for
     * @return the total number of warnings
     */
    public String getTotal(final Collection<Job<?, ?>> jobs) {
        int sum = 0;
        for (Job<?, ?> job : jobs) {
            sum += Integer.parseInt(getTotal(job));
        }
        return String.valueOf(sum);

    }

    /**
     * Converts the string to an integer. If the string is not valid then 0
     * is returned.
     *
     * @param value
     *            the value to convert
     * @return the integer value or 0
     */
    private int toInt(final String value) {
        try {
            return Integer.parseInt(StringUtils.substringBetween(value, ">", "<"));
        }
        catch (NumberFormatException exception) {
            return 0;
        }
    }

    /**
     * Returns the warnings for the specified action.
     *
     * @param job
     *            the job to get the action from
     * @param actionType
     *            the type of the action
     * @param plugin
     *            the plug-in that is target of the link
     * @return the number of warnings
     */
    private String getWarnings(final Job<?, ?> job, final Class<? extends AbstractProjectAction<?>> actionType, final String plugin) {
        AbstractProjectAction<?> action = job.getAction(actionType);
        if (action != null && action.hasValidResults()) {
            BuildResult result = action.getLastAction().getResult();
            int numberOfAnnotations = result.getNumberOfAnnotations();
            String value;
            if (numberOfAnnotations > 0) {
                value = String.format("<a href=\"%s%s\">%d</a>", job.getShortUrl(), plugin, numberOfAnnotations);
            }
            else {
                value = String.valueOf(numberOfAnnotations);
            }
            if (result.isSuccessfulTouched() && !result.isSuccessful()) {
                return value + result.getResultIcon();
            }
            return value;
        }
        return NO_RESULTS_FOUND;
    }

    /**
     * Extension point registration.
     *
     * @author Ulli Hafner
     */
    @Extension(optional = true)
    public static class WarningsPerJobDescriptor extends Descriptor<DashboardPortlet> {
        @Override
        public String getDisplayName() {
            return Messages.Portlet_WarningsTable();
        }
    }
}

