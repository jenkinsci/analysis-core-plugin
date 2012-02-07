package hudson.plugins.analysis.collector.dashboard;

import hudson.model.Descriptor;
import hudson.plugins.analysis.collector.AnalysisDescriptor;
import hudson.plugins.view.dashboard.DashboardPortlet;

/**
 * Descriptor with convenience methods for accessing the installed plug-ins.
 *
 * @author Ulli Hafner
 */
public abstract class AnalysisGraphDescriptor extends Descriptor<DashboardPortlet> {
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
}

