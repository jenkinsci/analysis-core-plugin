package hudson.plugins.analysis.collector;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.model.Hudson;
import hudson.plugins.analysis.core.PluginDescriptor;

/**
 * Descriptor for the class {@link AnalysisPublisher}. Used as a singleton. The
 * class is marked as public so that it can be accessed from views.
 *
 * @author Ulli Hafner
 */
@Extension(ordinal = 1)
public final class AnalysisDescriptor extends PluginDescriptor {
    /** Plug-in name. */
    private static final String PLUGIN_NAME = "analysis";
    /** Icon to use for the result and project action. */
    private static final String ACTION_ICON = "/plugin/analysis-collector/icons/analysis-24x24.png";

    /**
     * Returns whether the Checkstyle plug-in is installed.
     *
     * @return <code>true</code> if the Checkstyle plug-in is installed,
     *         <code>false</code> if not.
     */
    public static boolean isCheckStyleInstalled() {
        return isPluginInstalled("checkstyle");
    }

    /**
     * Returns whether the Dry plug-in is installed.
     *
     * @return <code>true</code> if the Dry plug-in is installed,
     *         <code>false</code> if not.
     */
    public static boolean isDryInstalled() {
        return isPluginInstalled("dry");
    }

    /**
     * Returns whether the FindBugs plug-in is installed.
     *
     * @return <code>true</code> if the FindBugs plug-in is installed,
     *         <code>false</code> if not.
     */
    public static boolean isFindBugsInstalled() {
        return isPluginInstalled("findbugs");
    }

    /**
     * Returns whether the PMD plug-in is installed.
     *
     * @return <code>true</code> if the PMD plug-in is installed,
     *         <code>false</code> if not.
     */
    public static boolean isPmdInstalled() {
        return isPluginInstalled("pmd");
    }

    /**
     * Returns whether the Open Tasks plug-in is installed.
     *
     * @return <code>true</code> if the Open Tasks plug-in is installed,
     *         <code>false</code> if not.
     */
    public static boolean isOpenTasksInstalled() {
        return isPluginInstalled("tasks");
    }

    /**
     * Returns whether the Warnings plug-in is installed.
     *
     * @return <code>true</code> if the Warnings plug-in is installed,
     *         <code>false</code> if not.
     */
    public static boolean isWarningsInstalled() {
        return isPluginInstalled("warnings");
    }

    /**
     * Returns whether the specified plug-in is installed.
     *
     * @param shortName
     *            the plugin to check
     * @return <code>true</code> if the specified plug-in is installed,
     *         <code>false</code> if not.
     */
    private static boolean isPluginInstalled(final String shortName) {
        Hudson instance = Hudson.getInstance();
        if (instance != null) {
            return instance.getPlugin(shortName) != null;
        }
        return true;
    }

    /**
     * Instantiates a new {@link AnalysisDescriptor}.
     */
    public AnalysisDescriptor() {
        super(AnalysisPublisher.class);
    }

    /** {@inheritDoc} */
    @Override
    public String getDisplayName() {
        return Messages.Analysis_Publisher_Name();
    }

    /** {@inheritDoc} */
    @Override
    public String getPluginName() {
        return PLUGIN_NAME;
    }

    /** {@inheritDoc} */
    @Override
    public String getIconUrl() {
        return ACTION_ICON;
    }

    /** {@inheritDoc} */
    @SuppressWarnings("rawtypes")
    @Override
    public boolean isApplicable(final Class<? extends AbstractProject> jobType) {
        return true;
    }
}