package io.jenkins.plugins.analysis.core.quality.thresholds;

/**
 * New warning threshold to determine a unstable build.
 */
public class NewUnstableThresholds extends WarningsThreshold {

    /**
     * Creates an instance of {@link NewUnstableThresholds}. Only the total threshold is set.
     */
    public NewUnstableThresholds() {
        super();
    }

    /**
     * Creates an instance of {@link NewUnstableThresholds}. Only the total threshold is set.
     * @param newTotalThreshold limit of issues in total
     */
    public NewUnstableThresholds(int newTotalThreshold) {
        super(newTotalThreshold);
    }

    /**
     * Creates an instance of {@link NewUnstableThresholds}. Only the total threshold is set.
     * @param high threshold for issues with priority high
     * @param normal threshold for issues with priority normal
     * @param low threshold for issues with priority low
     */
    public NewUnstableThresholds(int high, int normal, int low) {
        super(high, normal, low);
    }
}
