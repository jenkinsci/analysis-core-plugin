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


    /**CTOR for the {@link QualityGateBuilder}.
     *
     * @param failureTotalThreshold number of total issues from when on the build is failed
     * @param failureHighPriorityThreshold number of issues with high priority from when on the build is failed
     * @param failureNormalPriorityThreshold number of issues with normal priority  from when on the build is failed
     * @param failureLowPriorityThreshold number of issues with low priority from when on the build is failed
     * @param unstableTotalThreshold number of total issues from when on the build is unstable
     * @param unstableHighPriorityThreshold number of issues with high priority from when on the build is unstable
     * @param unstableNormalPriorityThreshold number of issues with normal priority from when on the build is unstable
     * @param unstableLowPriorityThreshold number of issues with low priority from when on the build is unstable
     * @param failureNewTotalThreshold number of new total issues from when on the build is failed
     * @param failureNewHighPriorityThreshold number of new issues with high priority from when on the build is failed
     * @param failureNewNormalPriorityThreshold number of new issues with normal priority from when on the build is failed
     * @param failureNewLowPriorityThreshold number of  new issues with normal priority from when on the build is failed
     * @param unstableNewTotalThreshold number of new total issues from when on the build is unstable
     * @param unstableNewHighPriorityThreshold number of new issues with high priority from when on the build is unstable
     * @param unstableNewNormalPriorityThreshold number of new issues with normal priority from when on the build is unstable
     * @param unstableNewLowPriorityThreshold number of new issues with low priority from when on the build is unstable
     */

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

    /**Getter of the number of total issues from when on the build is failed.
     *
     * @return number of total issues from when on the build is failed
     */

    public int getFailureTotalThreshold() {
        return failureTotalThreshold;
    }

    /**Getter of the number of issues with high priority from when on the build is failed.
     *
     * @return number of issues with high priority from when on the build is failed
     */

    public int getFailureHighPriorityThreshold() {
        return failureHighPriorityThreshold;
    }

    /**Getter of the number of issues with normal priority  from when on the build is failed.
     *
     * @return number of issues with normal priority  from when on the build is failed
     */

    public int getFailureNormalPriorityThreshold() {
        return failureNormalPriorityThreshold;
    }

    /**Getter of the number of issues with low priority from when on the build is failed.
     *
     * @return number of issues with low priority from when on the build is failed.
     */
    public int getFailureLowPriorityThreshold() {
        return failureLowPriorityThreshold;
    }

    /**Getter of the number of total issues from when on the build is unstable.
     *
     * @return number of total issues from when on the build is unstable.
     */

    public int getUnstableTotalThreshold() {
        return unstableTotalThreshold;
    }

    /** Getter of the number of issues with high priority from when on the build is unstable.
     *
     * @return number of issues with high priority from when on the build is unstable
     */

    public int getUnstableHighPriorityThreshold() {
        return unstableHighPriorityThreshold;
    }

    /**Getter of the number of issues with normal priority from when on the build is unstable.
     *
     * @return number of issues with normal priority from when on the build is unstable
     */

    public int getUnstableNormalPriorityThreshold() {
        return unstableNormalPriorityThreshold;
    }

    /**Getter of the number of issues with low priority from when on the build is unstable.
     *
     * @return number of issues with low priority from when on the build is unstable
     */
    public int getUnstableLowPriorityThreshold() {
        return unstableLowPriorityThreshold;
    }

    /**Getter of the number of new total issues from when on the build is failed.
     *
     * @return number of new total issues from when on the build is failed
     */

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


