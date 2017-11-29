package io.jenkins.plugins.analysis.core.quality;

/**Builder class for the {@link QualityGate}.
 * Not all values have to been set because all values set to deactivate (-1) per default.
 *
 * @author Johannes Arzt
 */


@SuppressWarnings("JavaDocMethod")
public class QualityGateBuilder {

    private int failureTotalThreshold;
    private int failureHighPriorityThreshold;
    private int failureNormalPriorityThreshold;
    private int failureLowPriorityThreshold;

    private int failureNewTotalThreshold;
    private int failureNewHighPriorityThreshold;
    private int failureNewNormalPriorityThreshold;
    private int failureNewLowPriorityThreshold;

    private int unstableTotalThreshold;
    private int unstableHighPriorityThreshold;
    private int unstableNormalPriorityThreshold;
    private int unstableLowPriorityThreshold;

    private int unstableNewTotalThreshold;
    private int unstableNewHighPriorityThreshold;
    private int unstableNewNormalPriorityThreshold;
    private int unstableNewLowPriorityThreshold;

    public QualityGateBuilder() {


        failureTotalThreshold = -1;
        failureHighPriorityThreshold = -1;
        failureNormalPriorityThreshold = -1;
        failureLowPriorityThreshold = -1;

        unstableTotalThreshold = -1;
        unstableHighPriorityThreshold = -1;
        unstableNormalPriorityThreshold = -1;
        unstableLowPriorityThreshold = -1;

        failureNewTotalThreshold = -1;
        failureNewHighPriorityThreshold = -1;
        failureNewNormalPriorityThreshold = -1;
        failureNewLowPriorityThreshold = -1;

        unstableNewTotalThreshold = -1;
        unstableNewHighPriorityThreshold = -1;
        unstableNewNormalPriorityThreshold = -1;
        unstableNewLowPriorityThreshold = -1;


    }

    public QualityGateBuilder setFailureTotalThreshold(final int failureTotalThreshold) {
        this.failureTotalThreshold = failureTotalThreshold;
        return this;
    }

    public QualityGateBuilder setFailureHighPriorityThreshold(final int failureHighPriorityThreshold) {
        this.failureHighPriorityThreshold = failureHighPriorityThreshold;
        return this;
    }

    public QualityGateBuilder setFailureNormalPriorityThreshold(final int failureNormalPriorityThreshold) {
        this.failureNormalPriorityThreshold = failureNormalPriorityThreshold;
        return this;
    }

    public QualityGateBuilder setFailureLowPriorityThreshold(final int failureLowPriorityThreshold) {
        this.failureLowPriorityThreshold = failureLowPriorityThreshold;
        return this;
    }

    public QualityGateBuilder setFailureNewTotalThreshold(final int failureNewTotalThreshold) {
        this.failureNewTotalThreshold = failureNewTotalThreshold;
        return this;
    }

    public QualityGateBuilder setFailureNewHighPriorityThreshold(final int failureNewHighPriorityThreshold) {
        this.failureNewHighPriorityThreshold = failureNewHighPriorityThreshold;
        return this;
    }

    public QualityGateBuilder setFailureNewNormalPriorityThreshold(final int failureNewNormalPriorityThreshold) {
        this.failureNewNormalPriorityThreshold = failureNewNormalPriorityThreshold;
        return this;
    }

    public QualityGateBuilder setFailureNewLowPriorityThreshold(final int failureNewLowPriorityThreshold) {
        this.failureNewLowPriorityThreshold = failureNewLowPriorityThreshold;
        return this;
    }

    public QualityGateBuilder setUnstableTotalThreshold(final int unstableTotalThreshold) {
        this.unstableTotalThreshold = unstableTotalThreshold;
        return this;
    }

    public QualityGateBuilder setUnstableHighPriorityThreshold(final int unstableHighPriorityThreshold) {
        this.unstableHighPriorityThreshold = unstableHighPriorityThreshold;
        return this;
    }

    public QualityGateBuilder setUnstableNormalPriorityThreshold(final int unstableNormalPriorityThreshold) {
        this.unstableNormalPriorityThreshold = unstableNormalPriorityThreshold;
        return this;
    }

    public QualityGateBuilder setUnstableLowPriorityThreshold(final int unstableLowPriorityThreshold) {
        this.unstableLowPriorityThreshold = unstableLowPriorityThreshold;
        return this;
    }

    public QualityGateBuilder setUnstableNewTotalThreshold(final int unstableNewTotalThreshold) {
        this.unstableNewTotalThreshold = unstableNewTotalThreshold;
        return this;
    }

    public QualityGateBuilder setUnstableNewHighPriorityThreshold(final int unstableNewHighPriorityThreshold) {
        this.unstableNewHighPriorityThreshold = unstableNewHighPriorityThreshold;
        return this;
    }

    public QualityGateBuilder setUnstableNewNormalPriorityThreshold(final int unstableNewNormalPriorityThreshold) {
        this.unstableNewNormalPriorityThreshold = unstableNewNormalPriorityThreshold;
        return this;
    }

    public QualityGateBuilder setUnstableNewLowPriorityThreshold(final int unstableNewLowPriorityThreshold) {
        this.unstableNewLowPriorityThreshold = unstableNewLowPriorityThreshold;
        return this;
    }

    public QualityGate build() {

        return new QualityGate(failureTotalThreshold, failureHighPriorityThreshold, failureNormalPriorityThreshold, failureLowPriorityThreshold,
                unstableTotalThreshold, unstableHighPriorityThreshold, unstableNormalPriorityThreshold, unstableLowPriorityThreshold,
                failureNewTotalThreshold, failureNewHighPriorityThreshold, failureNewNormalPriorityThreshold, failureNewLowPriorityThreshold,
                unstableNewTotalThreshold, unstableNewHighPriorityThreshold, unstableNewNormalPriorityThreshold, unstableNewLowPriorityThreshold);
    }
}
