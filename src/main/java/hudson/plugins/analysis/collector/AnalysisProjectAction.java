package hudson.plugins.analysis.collector;

import java.util.List;

import org.kohsuke.stapler.StaplerRequest;

import hudson.model.AbstractProject;

import hudson.plugins.analysis.core.AbstractProjectAction;
import hudson.plugins.analysis.graph.BuildResultGraph;
import hudson.plugins.analysis.graph.GraphConfigurationView;

/**
 * Entry point to visualize the trend graph in the project screen. Drawing of the graph is delegated to the associated
 * {@link AnalysisResultAction}.
 *
 * @author Ulli Hafner
 */
public class AnalysisProjectAction extends AbstractProjectAction<AnalysisResultAction> {
    private transient WarningsAggregator warningsAggregator;

    /**
     * Instantiates a new {@link AnalysisProjectAction}.
     *
     * @param project
     *            the project that owns this action
     */
    public AnalysisProjectAction(final AbstractProject<?, ?> project) {
        this(project, AnalysisResultAction.class);
    }

    /**
     * Instantiates a new {@link AnalysisProjectAction}.
     *
     * @param project
     *            the project that owns this action
     * @param type
     *            the result action type
     */
    public AnalysisProjectAction(final AbstractProject<?, ?> project, final Class<? extends AnalysisResultAction> type) {
        super(project, type, Messages._Analysis_ProjectAction_Name(), Messages._Analysis_Trend_Name(),
                AnalysisDescriptor.PLUGIN_ID, AnalysisDescriptor.ICON_URL, AnalysisDescriptor.RESULT_URL);

        createAggregator();
    }

    private void createAggregator() {
        warningsAggregator = new WarningsAggregator(true, true, true, true, true, true);
        warningsAggregator.hideJobPrefix();
    }

    /**
     * Restores the transient fields.
     *
     * @return this
     */
    protected Object readResolve() {
        createAggregator();

        return this;
    }

    @Override
    protected GraphConfigurationView createDefaultConfiguration() {
        return new AnalysisDefaultGraphConfigurationView(createConfiguration(), getProject(), getUrlName(),
                createBuildHistory());
    }

    @Override
    protected GraphConfigurationView createUserConfiguration(final StaplerRequest request) {
        return new AnalysisUserGraphConfigurationView(createConfiguration(), getProject(), getUrlName(),
                request.getCookies(), createBuildHistory());
    }

    /**
     * Creates the graph configuration.
     *
     * @return the graph configuration
     */
    protected AnalysisGraphConfiguration createConfiguration() {
        List<BuildResultGraph> availableGraphs = getAvailableGraphs();
        availableGraphs.add(0, new OriginGraph());

        return new AnalysisGraphConfiguration(availableGraphs);
    }

    /**
     * Returns whether CheckStyle results should be shown.
     *
     * @return <code>true</code> if CheckStyle results should be shown, <code>false</code> otherwise
     */
    public boolean isCheckStyleActivated() {
        return warningsAggregator.hasCheckStyle(getProject());
    }

    /**
     * Returns whether DRY results should be shown.
     *
     * @return <code>true</code> if DRY results should be shown, <code>false</code> otherwise
     */
    public boolean isDryActivated() {
        return warningsAggregator.hasDry(getProject());
    }

    /**
     * Returns whether FindBugs results should be shown.
     *
     * @return <code>true</code> if FindBugs results should be shown, <code>false</code> otherwise
     */
    public boolean isFindBugsActivated() {
        return warningsAggregator.hasFindBugs(getProject());
    }

    /**
     * Returns whether PMD results should be shown.
     *
     * @return <code>true</code> if PMD results should be shown, <code>false</code> otherwise
     */
    public boolean isPmdActivated() {
        return warningsAggregator.hasPmd(getProject());
    }

    /**
     * Returns whether open tasks should be shown.
     *
     * @return <code>true</code> if open tasks should be shown, <code>false</code> otherwise
     */
    public boolean isOpenTasksActivated() {
        return warningsAggregator.hasPmd(getProject());
    }

    /**
     * Returns whether compiler warnings results should be shown.
     *
     * @return <code>true</code> if compiler warnings results should be shown, <code>false</code> otherwise
     */
    public boolean isWarningsActivated() {
        return warningsAggregator.hasCompilerWarnings(getProject());
    }

    /**
     * Returns the number of Checkstyle warnings for this action.
     *
     * @return the number of Checkstyle warnings
     */
    public String getCheckStyle() {
        return warningsAggregator.getCheckStyle(getProject());
    }

    /**
     * Returns the number of duplicate code warnings for this action.
     *
     * @return the number of duplicate code warnings
     */
    public String getDry() {
        return warningsAggregator.getDry(getProject());
    }

    /**
     * Returns the number of FindBugs warnings for this action.
     *
     * @return the number of FindBugs warnings
     */
    public String getFindBugs() {
        return warningsAggregator.getFindBugs(getProject());
    }

    /**
     * Returns the number of PMD warnings for this action.
     *
     * @return the number of PMD warnings
     */
    public String getPmd() {
        return warningsAggregator.getPmd(getProject());
    }

    /**
     * Returns the number of open tasks for this action.
     *
     * @return the number of open tasks
     */
    public String getTasks() {
        return warningsAggregator.getTasks(getProject());
    }

    /**
     * Returns the total number of warnings for this action.
     *
     * @return the number of compiler warnings
     */
    public String getCompilerWarnings() {
        return warningsAggregator.getCompilerWarnings(getProject());
    }
}
