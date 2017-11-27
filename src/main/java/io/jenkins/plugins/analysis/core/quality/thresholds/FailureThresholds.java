package io.jenkins.plugins.analysis.core.quality.thresholds;

public class FailureThresholds extends WarningsThreshold {
    public FailureThresholds(int failureThreshold) {
        super(failureThreshold);
    }

    public FailureThresholds(int high, int normal, int low) {
        super(high, normal, low);
    }
}
