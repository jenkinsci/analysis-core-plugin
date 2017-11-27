package io.jenkins.plugins.analysis.core.quality.thresholds;

public class UnstableThresholds extends FailureThresholds {
    public UnstableThresholds(int high, int normal, int low) {
        super(high, normal, low);
    }

    public UnstableThresholds(int unstableThreshold) {
        super(unstableThreshold);
    }
}
