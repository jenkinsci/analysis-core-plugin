package io.jenkins.plugins.analysis.core.quality.thresholds;

/**
 * Warning threshold to determine a failed build.
 */
public class FailureThresholds extends WarningsThreshold {

    /**
     * Creates an instance of {@link FailureThresholds}. Only the total threshold is set.
     */
    public FailureThresholds() {
        super();
    }

    /**
     * Creates an instance of {@link WarningsThreshold}. Only the total threshold is set.
     * @param failureThreshold limit of issues in total
     */
    public FailureThresholds(int failureThreshold) {
        super(failureThreshold);
    }

    /**
     * Creates an instance of {@link FailureThresholds}. Only the total threshold is set.
     * @param high threshold for issues with priority high
     * @param normal threshold for issues with priority normal
     * @param low threshold for issues with priority low
     */
    public FailureThresholds(int high, int normal, int low) {
        super(high, normal, low);
    }
}
