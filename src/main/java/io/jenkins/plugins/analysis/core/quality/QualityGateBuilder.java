package io.jenkins.plugins.analysis.core.quality;

public class QualityGateBuilder {
    private ThresholdSet totalBuildsUnstableThresholds = new ThresholdSet(-1,-1,-1,-1);
    private ThresholdSet totalBuildsFailureThresholds = new ThresholdSet(-1,-1,-1,-1);
    private ThresholdSet newBuildsUnstableThresholds = new ThresholdSet(-1,-1,-1,-1);
    private ThresholdSet newBuildsFailureThresholds = new ThresholdSet(-1,-1,-1,-1);

    public QualityGateBuilder setTotalBuildsUnstableThresholds(final ThresholdSet totalBuildsUnstableThresholds) {
        this.totalBuildsUnstableThresholds = totalBuildsUnstableThresholds;
        return this;
    }

    public QualityGateBuilder setTotalBuildsFailureThresholds(final ThresholdSet totalBuildsFailureThresholds) {
        this.totalBuildsFailureThresholds = totalBuildsFailureThresholds;
        return this;
    }

    public QualityGateBuilder setNewBuildsUnstableThresholds(final ThresholdSet newBuildsUnstableThresholds) {
        this.newBuildsUnstableThresholds = newBuildsUnstableThresholds;
        return this;
    }

    public QualityGateBuilder setNewBuildsFailureThresholds(final ThresholdSet newBuildsFailureThresholds) {
        this.newBuildsFailureThresholds = newBuildsFailureThresholds;
        return this;
    }

    public QualityGate createQualityGate() {
        return new QualityGate(totalBuildsUnstableThresholds, totalBuildsFailureThresholds, newBuildsUnstableThresholds, newBuildsFailureThresholds);
    }
}