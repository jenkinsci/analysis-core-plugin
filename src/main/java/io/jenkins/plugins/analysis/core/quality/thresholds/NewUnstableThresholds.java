package io.jenkins.plugins.analysis.core.quality.thresholds;

public class NewUnstableThresholds extends WarningsThreshold {

    public NewUnstableThresholds(int newTotalThreshold) {
        super(newTotalThreshold);
    }

    public NewUnstableThresholds(int high, int normal, int low) {
        super(high, normal, low);
    }
}
