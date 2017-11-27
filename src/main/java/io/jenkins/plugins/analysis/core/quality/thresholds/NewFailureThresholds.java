package io.jenkins.plugins.analysis.core.quality.thresholds;

public class NewFailureThresholds extends WarningsThreshold {

    public NewFailureThresholds(int newTotalThreshold) {
        super(newTotalThreshold);
    }

    public NewFailureThresholds(int high, int normal, int low) {
        super(high, normal, low);
    }
}
