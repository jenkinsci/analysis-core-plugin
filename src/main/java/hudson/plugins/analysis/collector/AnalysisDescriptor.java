package hudson.plugins.analysis.collector;

import hudson.Extension;
import hudson.model.AbstractProject;
import hudson.plugins.analysis.core.PluginDescriptor;

/**
 * Descriptor for the class {@link AnalysisPublisher}. Used as a singleton. The
 * class is marked as public so that it can be accessed from views.
 *
 * @author Ulli Hafner
 */
@Extension(ordinal = 1)
public final class AnalysisDescriptor extends PluginDescriptor {
    private static final String ICONS_PREFIX = "/plugin/analysis-collector/icons/";
    /** The ID of this plug-in is used as URL. */
    static final String PLUGIN_ID = "analysis";
    /** The URL of the result action. */
    static final String RESULT_URL = PluginDescriptor.createResultUrlName(PLUGIN_ID);
    /** Icon to use for the result and project action. */
    static final String ICON_URL = ICONS_PREFIX + "analysis-24x24.png";

    /**
     * Instantiates a new {@link AnalysisDescriptor}.
     */
    public AnalysisDescriptor() {
        super(AnalysisPublisher.class);
    }

    @Override
    public String getDisplayName() {
        return Messages.Analysis_Publisher_Name();
    }

    @Override
    public String getPluginRoot() {
        return "/plugin/analysis-collector/";
    }

    @Override
    public String getPluginName() {
        return PLUGIN_ID;
    }

    @Override
    public String getIconUrl() {
        return ICON_URL;
    }

    @Override
    public String getSummaryIconUrl() {
        return ICONS_PREFIX + "analysis-48x48.png";
    }

    @SuppressWarnings("rawtypes")
    @Override
    public boolean isApplicable(final Class<? extends AbstractProject> jobType) {
        return true;
    }
}