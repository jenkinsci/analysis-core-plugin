package io.jenkins.plugins.analysis.core.quality.thresholds;

/**
 * New warning threshold to determine a failed build.
 */
public class NewFailureThresholds extends WarningsThreshold {

    /**
     * Creates an instance of {@link NewFailureThresholds}. Only the total threshold is set.
     */
    public NewFailureThresholds() {
        super();
    }

    /**
     * Creates an instance of {@link NewFailureThresholds}. Only the total threshold is set.
     * @param newTotalThreshold limit of issues in total
     */
    public NewFailureThresholds(int newTotalThreshold) {
        super(newTotalThreshold);
    }

    /**
     * Creates an instance of {@link NewFailureThresholds}. Only the total threshold is set.
     * @param high threshold for issues with priority high
     * @param normal threshold for issues with priority normal
     * @param low threshold for issues with priority low
     */
    public NewFailureThresholds(int high, int normal, int low) {
        super(high, normal, low);
    }
}
