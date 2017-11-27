package io.jenkins.plugins.analysis.core.quality;

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

    public void setFailureTotalThreshold(final int failureTotalThreshold) {
        this.failureTotalThreshold = failureTotalThreshold;
    }

    public void setFailureHighPriorityThreshold(final int failureHighPriorityThreshold) {
        this.failureHighPriorityThreshold = failureHighPriorityThreshold;
    }

    public void setFailureNormalPriorityThreshold(final int failureNormalPriorityThreshold) {
        this.failureNormalPriorityThreshold = failureNormalPriorityThreshold;
    }

    public void setFailureLowPriorityThreshold(final int failureLowPriorityThreshold) {
        this.failureLowPriorityThreshold = failureLowPriorityThreshold;
    }

    public void setFailureNewTotalThreshold(final int failureNewTotalThreshold) {
        this.failureNewTotalThreshold = failureNewTotalThreshold;
    }

    public void setFailureNewHighPriorityThreshold(final int failureNewHighPriorityThreshold) {
        this.failureNewHighPriorityThreshold = failureNewHighPriorityThreshold;
    }

    public void setFailureNewNormalPriorityThreshold(final int failureNewNormalPriorityThreshold) {
        this.failureNewNormalPriorityThreshold = failureNewNormalPriorityThreshold;
    }

    public void setFailureNewLowPriorityThreshold(final int failureNewLowPriorityThreshold) {
        this.failureNewLowPriorityThreshold = failureNewLowPriorityThreshold;
    }

    public void setUnstableTotalThreshold(final int unstableTotalThreshold) {
        this.unstableTotalThreshold = unstableTotalThreshold;
    }

    public void setUnstableHighPriorityThreshold(final int unstableHighPriorityThreshold) {
        this.unstableHighPriorityThreshold = unstableHighPriorityThreshold;
    }

    public void setUnstableNormalPriorityThreshold(final int unstableNormalPriorityThreshold) {
        this.unstableNormalPriorityThreshold = unstableNormalPriorityThreshold;
    }

    public void setUnstableLowPriorityThreshold(final int unstableLowPriorityThreshold) {
        this.unstableLowPriorityThreshold = unstableLowPriorityThreshold;
    }

    public void setUnstableNewTotalThreshold(final int unstableNewTotalThreshold) {
        this.unstableNewTotalThreshold = unstableNewTotalThreshold;
    }

    public void setUnstableNewHighPriorityThreshold(final int unstableNewHighPriorityThreshold) {
        this.unstableNewHighPriorityThreshold = unstableNewHighPriorityThreshold;
    }

    public void setUnstableNewNormalPriorityThreshold(final int unstableNewNormalPriorityThreshold) {
        this.unstableNewNormalPriorityThreshold = unstableNewNormalPriorityThreshold;
    }

    public void setUnstableNewLowPriorityThreshold(final int unstableNewLowPriorityThreshold) {
        this.unstableNewLowPriorityThreshold = unstableNewLowPriorityThreshold;
    }

    public QualityGate build() {

        return new QualityGate(failureTotalThreshold, failureHighPriorityThreshold, failureNormalPriorityThreshold, failureLowPriorityThreshold,
                unstableTotalThreshold, unstableHighPriorityThreshold, unstableNormalPriorityThreshold, unstableLowPriorityThreshold,
                failureNewTotalThreshold,  failureNewHighPriorityThreshold, failureNewNormalPriorityThreshold, failureNewLowPriorityThreshold,
                unstableNewTotalThreshold, unstableNewHighPriorityThreshold, unstableNewNormalPriorityThreshold, unstableNewLowPriorityThreshold);
    }
}
