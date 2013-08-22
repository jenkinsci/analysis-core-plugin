package hudson.plugins.analysis.collector;

import org.jvnet.localizer.Localizable;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;

import hudson.model.Job;

import hudson.plugins.analysis.util.HtmlPrinter;

import hudson.views.ListViewColumnDescriptor;
import hudson.views.ListViewColumn;

/**
 * A column that shows the total number of warnings in a job.
 *
 * @author Ulli Hafner
 */
public class WarningsCountColumn extends ListViewColumn {
    private final WarningsAggregator warningsAggregator;

    /**
     * Creates a new instance of {@link WarningsCountColumn}.
     *
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
    @DataBoundConstructor
    public WarningsCountColumn(final boolean isCheckStyleActivated,
            final boolean isDryActivated, final boolean isFindBugsActivated, final boolean isPmdActivated,
            final boolean isOpenTasksActivated, final boolean isWarningsActivated) {
        super();

        warningsAggregator = new WarningsAggregator(isCheckStyleActivated, isDryActivated,
                isFindBugsActivated, isPmdActivated, isOpenTasksActivated, isWarningsActivated);
    }

    /**
     * Returns whether CheckStyle results should be shown.
     *
     * @return <code>true</code> if CheckStyle results should be shown, <code>false</code> otherwise
     */
    public boolean isCheckStyleActivated() {
        return warningsAggregator.isCheckStyleActivated();
    }

    /**
     * Returns whether DRY results should be shown.
     *
     * @return <code>true</code> if DRY results should be shown, <code>false</code> otherwise
     */
    public boolean isDryActivated() {
        return warningsAggregator.isDryActivated();
    }

    /**
     * Returns whether FindBugs results should be shown.
     *
     * @return <code>true</code> if FindBugs results should be shown, <code>false</code> otherwise
     */
    public boolean isFindBugsActivated() {
        return warningsAggregator.isFindBugsActivated();
    }

    /**
     * Returns whether PMD results should be shown.
     *
     * @return <code>true</code> if PMD results should be shown, <code>false</code> otherwise
     */
    public boolean isPmdActivated() {
        return warningsAggregator.isPmdActivated();
    }

    /**
     * Returns whether open tasks should be shown.
     *
     * @return <code>true</code> if open tasks should be shown, <code>false</code> otherwise
     */
    public boolean isOpenTasksActivated() {
        return warningsAggregator.isOpenTasksActivated();
    }

    /**
     * Returns whether compiler warnings results should be shown.
     *
     * @return <code>true</code> if compiler warnings results should be shown, <code>false</code> otherwise
     */
    public boolean isWarningsActivated() {
        return warningsAggregator.isWarningsActivated();
    }

    /**
     * Returns the total number of annotations for the selected job.
     *
     * @param project
     *            the selected project
     * @return the total number of annotations
     */
    public String getNumberOfAnnotations(final Job<?, ?> project) {
        return warningsAggregator.getTotal(project);
    }

    /**
     * Returns the number of warnings for the specified job separated by each plug-in.
     *
     * @param job
     *            the job to get the warnings for
     * @return the number of warnings, formatted as HTML string
     */
    public String getDetails(final Job<?, ?> job) {
        HtmlPrinter printer = new HtmlPrinter();
        printer.append("<table>");
        if (isCheckStyleActivated()) {
            printLine(printer, hudson.plugins.checkstyle.Messages._Checkstyle_Detail_header(),
                    warningsAggregator.getCheckStyle(job));
        }
        if (isDryActivated()) {
            printLine(printer, hudson.plugins.dry.Messages._DRY_Detail_header(),
                    warningsAggregator.getDry(job));
        }
        if (isFindBugsActivated()) {
            printLine(printer, hudson.plugins.findbugs.Messages._FindBugs_Detail_header(),
                    warningsAggregator.getFindBugs(job));
        }
        if (isPmdActivated()) {
            printLine(printer, hudson.plugins.pmd.Messages._PMD_Detail_header(),
                    warningsAggregator.getPmd(job));
        }
        if (isOpenTasksActivated()) {
            printLine(printer, hudson.plugins.tasks.Messages._Tasks_ProjectAction_Name(),
                    warningsAggregator.getTasks(job));
        }
        if (isWarningsActivated()) {
            printLine(printer, hudson.plugins.warnings.Messages._Warnings_ProjectAction_Name(),
                    warningsAggregator.getCompilerWarnings(job));
        }
        printer.append("</table>");
        return printer.toString();
    }

    private void printLine(final HtmlPrinter printer, final Localizable header, final String warnings) {
        printer.append(printer.line(header + ": " + warnings));
    }

    /**
     * Descriptor for the column.
     */
    @Extension
    public static class ColumnDescriptor extends ListViewColumnDescriptor {
        /** {@inheritDoc} */
        @Override
        public boolean shownByDefault() {
            return false;
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
            return Messages.Analysis_Warnings_Column();
        }
    }
}
