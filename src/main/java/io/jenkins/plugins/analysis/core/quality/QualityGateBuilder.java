package io.jenkins.plugins.analysis.core.quality;

/**
 * Builds a QualityGate.
 *
 * @author Raphael Furch
 */
public class QualityGateBuilder {
    /**
     * Unstable threshold for total Builds .
     */
    private ThresholdSet totalBuildsUnstableThresholds = new ThresholdSet(-1,-1,-1,-1);
    /**
     * Failure threshold for total Builds .
     */
    private ThresholdSet totalBuildsFailureThresholds = new ThresholdSet(-1,-1,-1,-1);
    /**
     * Unstable threshold for new Builds .
     */
    private ThresholdSet newBuildsUnstableThresholds = new ThresholdSet(-1,-1,-1,-1);
    /**
     * Failure threshold for new Builds .
     */
    private ThresholdSet newBuildsFailureThresholds = new ThresholdSet(-1,-1,-1,-1);

    /**
     * Sets a totalBuildUnstableThreshold.
     * @param totalBuildsUnstableThresholds = threshold.
     * @return this.
     */
    public QualityGateBuilder setTotalBuildsUnstableThresholds(final ThresholdSet totalBuildsUnstableThresholds) {
        this.totalBuildsUnstableThresholds = totalBuildsUnstableThresholds;
        return this;
    }

    /**
     *  Sets a totalBuildsFailureThresholds.
     * @param totalBuildsFailureThresholds = threshold.
     * @return this.
     */
    public QualityGateBuilder setTotalBuildsFailureThresholds(final ThresholdSet totalBuildsFailureThresholds) {
        this.totalBuildsFailureThresholds = totalBuildsFailureThresholds;
        return this;
    }

    /**
     * Sets a newBuildsUnstableThresholds.
     * @param newBuildsUnstableThresholds = threshold.
     * @return this.
     */
    public QualityGateBuilder setNewBuildsUnstableThresholds(final ThresholdSet newBuildsUnstableThresholds) {
        this.newBuildsUnstableThresholds = newBuildsUnstableThresholds;
        return this;
    }

    /**
     * Sets a newBuildsFailureThresholds.
     * @param newBuildsFailureThresholds = threshold.
     * @return this.
     */
    public QualityGateBuilder setNewBuildsFailureThresholds(final ThresholdSet newBuildsFailureThresholds) {
        this.newBuildsFailureThresholds = newBuildsFailureThresholds;
        return this;
    }

    /**
     * Create a QualityGate.
     * @return a QualityGate with the added thresholds.
     */
    public QualityGate createQualityGate() {
        return new QualityGate(totalBuildsUnstableThresholds, totalBuildsFailureThresholds, newBuildsUnstableThresholds, newBuildsFailureThresholds);
    }
}