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
     * @param failureNewLowPriorityThreshold number of  new issues with low priority from when on the build is failed
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

    /**Getter of the number of new issues with high priority from when on the build is failed.
     *
     * @return number of new issues with high priority from when on the build is failed
     */

    public int getFailureNewHighPriorityThreshold() {
        return failureNewHighPriorityThreshold;
    }

    /**Getter of the number of new issues with normal priority from when on the build is failed.
     *
     * @return number of new issues with normal priority from when on the build is failed
     */

    public int getFailureNewNormalPriorityThreshold() {
        return failureNewNormalPriorityThreshold;
    }

    /**Getter of the number of  new issues with low priority from when on the build is failed.
     *
     * @return  number of  new issues with low priority from when on the build is failed
     */

    public int getFailureNewLowPriorityThreshold() {
        return failureNewLowPriorityThreshold;
    }

    /**Getter of the number of new total issues from when on the build is unstable.
     *
     * @return number of new total issues from when on the build is unstable
     */

    public int getUnstableNewTotalThreshold() {
        return unstableNewTotalThreshold;
    }

    /**Getter of the number of new issues with high priority from when on the build is unstable.
     *
     * @return number of new issues with high priority from when on the build is unstable s
     */

    public int getUnstableNewHighPriorityThreshold() {
        return unstableNewHighPriorityThreshold;
    }

    /**Getter of the number of new issues with normal priority from when on the build is unstable.
     *
     * @return number of new issues with normal priority from when on the build is unstable
     */

    public int getUnstableNewNormalPriorityThreshold() {
        return unstableNewNormalPriorityThreshold;
    }

    /**Getter of the number of new issues with low priority from when on the build is unstable.
     *
     * @return  number of new issues with low priority from when on the build is unstable
     */
    public int getUnstableNewLowPriorityThreshold() {
        return unstableNewLowPriorityThreshold;
    }

    /**If the failureTotalThreshold is bigger than -1 the failureTotalThreshold is set.
     *
     * @return true if the failureTotalThreshold is set otherwise false
     */

    public boolean hasFailureTotalThreshold() {
        return failureTotalThreshold >= 0;
    }

    /**If the failureLowPriorityThreshold is bigger than -1 the failureTotalThreshold is set.
     *
     * @return true if the failureLowPriorityThreshold is set otherwise false
     */


    public boolean hasFailureLowPriorityThreshold() {
        return failureLowPriorityThreshold >= 0;
    }

    /**If the failureNormalPriorityThreshold is bigger than -1 the failureTotalThreshold is set.
     *
     * @return return true if the failureNormalPriorityThreshold is set otherwise false.
     */

    public boolean hasFailureNormalPriorityThreshold() {
        return failureNormalPriorityThreshold >= 0;
    }

    /**If the failureHighPriorityThreshold is bigger than -1 the failureTotalThreshold is set.
     *
     * @return true if the failureHighPriorityThreshold is set otherwise false
     */

    public boolean hasFailureHighPriorityThreshold() {
        return failureHighPriorityThreshold >= 0;
    }

    /**If the unstableTotalThreshold is bigger than -1 the failureTotalThreshold is set.
     *
     * @return true if the  unstableTotalThreshold is set otherwise false
     */

    public boolean hasFailureNewTotalThreshold() {
        return failureNewTotalThreshold >= 0;
    }

    /** If the unstableLowPriorityThreshold is bigger than -1 the failureTotalThreshold is set.
     *
     * @return true if the unstableLowPriorityThreshold is set otherwise false
     */

    public boolean hasFailureNewLowPriorityThreshold() {
        return failureNewLowPriorityThreshold >= 0;
    }

    /**If the unstableNormalPriorityThreshold is bigger than -1 the failureTotalThreshold is set.
     *
     * @return true if the  unstableNormalPriorityThreshold is set otherwise false
     */

    public boolean hasFailureNewNormalPriorityThreshold() {
        return failureNewNormalPriorityThreshold >= 0;
    }

    /**If the unstableHighPriorityThreshold is bigger than -1 the failureTotalThreshold is set.
     *
     * @return true if the unstableHighPriorityThreshold is set otherwise false
     */

    public boolean hasFailureNewHighPriorityThreshold() {
        return failureNewHighPriorityThreshold >= 0;
    }

    /** If the failureNewTotalThreshold is bigger than -1 the failureTotalThreshold is set.
     *
     * @return true if the failureNewTotalThreshold is set otherwise false
     */

    public boolean hasUnstableTotalThreshold() {
        return unstableTotalThreshold >= 0;
    }

    /** If the failureNewLowPriorityThreshold is bigger than -1 the failureTotalThreshold is set.
     *
     * @return true if the failureNewLowPriorityThreshold is set otherwise false
     */

    public boolean hasUnstableLowPriorityThreshold() {
        return unstableLowPriorityThreshold >= 0;
    }

    /**If the failureNewNormalPriorityThreshold is bigger than -1 the failureTotalThreshold is set.
     *
     * @return true if the failureNewNormalPriorityThreshold is set otherwise false
     */

    public boolean hasUnstableNormalPriorityThreshold() {
        return unstableNormalPriorityThreshold >= 0;
    }

    /**If the failureNewHighPriorityThreshold is bigger than -1 the failureTotalThreshold is set.
     *
     * @return  true if the failureNewHighPriorityThreshold is set otherwise false
     */

    public boolean hasUnstableHighPriorityThreshold() {
        return unstableHighPriorityThreshold >= 0;
    }

    /**If the unstableNewTotalThreshold is bigger than -1 the failureTotalThreshold is set.
     *
     * @return true if  the unstableNewTotalThreshold is set otherwise false
     */

    public boolean hasUnstableNewTotalThreshold() {
        return unstableNewTotalThreshold >= 0;
    }

    /**If the unstableNewLowPriorityThreshold is bigger than -1 the failureTotalThreshold is set.
     *
     * @return true if the unstableNewLowPriorityThreshold is set otherwise false
     */

    public boolean hasUnstableNewLowPriorityThreshold() {
        return unstableNewLowPriorityThreshold >= 0;
    }

    /**If the unstableNewNormalPriorityThreshold is bigger than -1 the failureTotalThreshold is set.
     *
     * @return  true if the unstableNewNormalPriorityThreshold is set otherwise false
     */

    public boolean hasUnstableNewNormalPriorityThreshold() {
        return unstableNewNormalPriorityThreshold >= 0;
    }

    /**If the unstableNewHighPriorityThreshold is bigger than -1 the failureTotalThreshold is set.
     *
     * @return true if  the unstableNewHighPriorityThreshold is set otherwise false
     */

    public boolean hasUnstableNewHighPriorityThreshold() {
        return unstableNewHighPriorityThreshold >= 0;
    }
}


