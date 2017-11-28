package io.jenkins.plugins.analysis.core.quality;

/**
 * Defines quality gates for a static analysis run.
 *
 * @author Ullrich Hafner
 */
public class QualityGate {

    private final int failureTotalThreshold;
    private final int failureHighPriorityThreshold;
    private final int failureNormalPriorityThreshold;
    private final int failureLowPriorityThreshold;

    private final int unstableTotalThreshold;
    private final int unstableHighPriorityThreshold;
    private final int unstableNormalPriorityThreshold;
    private final int unstableLowPriorityThreshold;

    private final int failureNewTotalThreshold;
    private final int failureNewHighPriorityThreshold;
    private final int failureNewNormalPriorityThreshold;
    private final int failureNewLowPriorityThreshold;

    private final int unstableNewTotalThreshold;
    private final int unstableNewHighPriorityThreshold;
    private final int unstableNewNormalPriorityThreshold;
    private final int unstableNewLowPriorityThreshold;



    public QualityGate(final int failureTotalThreshold, final int failureHighPriorityThreshold, final int failureNormalPriorityThreshold,
            final int failureLowPriorityThreshold, final int unstableTotalThreshold, final int unstableHighPriorityThreshold,
            final int unstableNormalPriorityThreshold, final int unstableLowPriorityThreshold, final int failureNewTotalThreshold,
            final int failureNewHighPriorityThreshold, final int failureNewNormalPriorityThreshold, final int failureNewLowPriorityThreshold,
            final int unstableNewTotalThreshold,
            final int unstableNewHighPriorityThreshold, final int unstableNewNormalPriorityThreshold, final int unstableNewLowPriorityThreshold) {
        this.failureTotalThreshold = failureTotalThreshold;
        this.failureHighPriorityThreshold = failureHighPriorityThreshold;
        this.failureNormalPriorityThreshold = failureNormalPriorityThreshold;
        this.failureLowPriorityThreshold = failureLowPriorityThreshold;
        this.unstableTotalThreshold = unstableTotalThreshold;
        this.unstableHighPriorityThreshold = unstableHighPriorityThreshold;
        this.unstableNormalPriorityThreshold = unstableNormalPriorityThreshold;
        this.unstableLowPriorityThreshold = unstableLowPriorityThreshold;
        this.failureNewTotalThreshold = failureNewTotalThreshold;
        this.failureNewHighPriorityThreshold = failureNewHighPriorityThreshold;
        this.failureNewNormalPriorityThreshold = failureNewNormalPriorityThreshold;
        this.failureNewLowPriorityThreshold = failureNewLowPriorityThreshold;
        this.unstableNewTotalThreshold = unstableNewTotalThreshold;
        this.unstableNewHighPriorityThreshold = unstableNewHighPriorityThreshold;
        this.unstableNewNormalPriorityThreshold = unstableNewNormalPriorityThreshold;
        this.unstableNewLowPriorityThreshold = unstableNewLowPriorityThreshold;
    }

    //TODO default CTOR with -1


    public int getFailureTotalThreshold() {
        return failureTotalThreshold;
    }

    public int getFailureHighPriorityThreshold() {
        return failureHighPriorityThreshold;
    }

    public int getFailureNormalPriorityThreshold() {
        return failureNormalPriorityThreshold;
    }

    public int getFailureLowPriorityThreshold() {
        return failureLowPriorityThreshold;
    }

    public int getUnstableTotalThreshold() {
        return unstableTotalThreshold;
    }

    public int getUnstableHighPriorityThreshold() {
        return unstableHighPriorityThreshold;
    }

    public int getUnstableNormalPriorityThreshold() {
        return unstableNormalPriorityThreshold;
    }

    public int getUnstableLowPriorityThreshold() {
        return unstableLowPriorityThreshold;
    }

    public int getFailureNewTotalThreshold() {
        return failureNewTotalThreshold;
    }

    public int getFailureNewHighPriorityThreshold() {
        return failureNewHighPriorityThreshold;
    }

    public int getFailureNewNormalPriorityThreshold() {
        return failureNewNormalPriorityThreshold;
    }

    public int getFailureNewLowPriorityThreshold() {
        return failureNewLowPriorityThreshold;
    }

    public int getUnstableNewTotalThreshold() {
        return unstableNewTotalThreshold;
    }

    public int getUnstableNewHighPriorityThreshold() {
        return unstableNewHighPriorityThreshold;
    }

    public int getUnstableNewNormalPriorityThreshold() {
        return unstableNewNormalPriorityThreshold;
    }

    public int getUnstableNewLowPriorityThreshold() {
        return unstableNewLowPriorityThreshold;
    }

    public boolean hasFailureTotalThreshold() {
        return failureTotalThreshold >= 0;
    }

    public boolean hasFailureLowPriorityThreshold() {
        return failureLowPriorityThreshold >= 0;
    }

    public boolean hasFailureNormalPriorityThreshold() {
        return failureNormalPriorityThreshold >= 0;
    }

    public boolean hasFailureHighPriorityThreshold() {
        return failureHighPriorityThreshold >= 0;
    }

    public boolean hasFailureNewTotalThreshold() {
        return failureNewTotalThreshold >= 0;
    }

    public boolean hasFailureNewLowPriorityThreshold() {
        return failureNewLowPriorityThreshold >= 0;
    }

    public boolean hasFailureNewNormalPriorityThreshold() {
        return failureNewNormalPriorityThreshold >= 0;
    }

    public boolean hasFailureNewHighPriorityThreshold() {
        return failureNewHighPriorityThreshold >= 0;
    }

    public boolean hasUnstableTotalThreshold() {
        return unstableTotalThreshold >= 0;
    }

    public boolean hasUnstableLowPriorityThreshold() {
        return unstableLowPriorityThreshold >= 0;
    }

    public boolean hasUnstableNormalPriorityThreshold() {
        return unstableNormalPriorityThreshold >= 0;
    }

    public boolean hasUnstableHighPriorityThreshold() {
        return unstableHighPriorityThreshold >= 0;
    }

    public boolean hasUnstableNewTotalThreshold() {
        return unstableNewTotalThreshold >= 0;
    }

    public boolean hasUnstableNewLowPriorityThreshold() {
        return unstableNewLowPriorityThreshold >= 0;
    }

    public boolean hasUnstableNewNormalPriorityThreshold() {
        return unstableNewNormalPriorityThreshold >= 0;
    }

    public boolean hasUnstableNewHighPriorityThreshold() {
        return unstableNewHighPriorityThreshold >= 0;
    }
}


