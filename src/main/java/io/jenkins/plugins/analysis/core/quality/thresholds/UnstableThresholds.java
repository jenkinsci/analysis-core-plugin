package io.jenkins.plugins.analysis.core.quality.thresholds;

/**
 * Warning threshold to determine a unstable build.
 */
public class UnstableThresholds extends WarningsThreshold {

    /**
     * Creates an instance of {@link UnstableThresholds}. Only the total threshold is set.
     */
    public UnstableThresholds() {
        super();
    }

    /**
     * Creates an instance of {@link UnstableThresholds}. Only the total threshold is set.
     * @param unstableThreshold limit of issues in total
     */
    public UnstableThresholds(int unstableThreshold) {
        super(unstableThreshold);
    }

    /**
     * Creates an instance of {@link UnstableThresholds}. Only the total threshold is set.
     * @param high threshold for issues with priority high
     * @param normal threshold for issues with priority normal
     * @param low threshold for issues with priority low
     */
    public UnstableThresholds(int high, int normal, int low) {
        super(high, normal, low);
    }
}
