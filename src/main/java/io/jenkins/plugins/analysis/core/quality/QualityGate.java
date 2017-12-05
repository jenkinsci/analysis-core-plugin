package io.jenkins.plugins.analysis.core.quality;

/**
 * Defines quality gates for a static analysis run.
 *
 * @author Ullrich Hafner
 */
public class QualityGate {

    private int totalUnstableAllPriorities;
    private int totalUnstableHighPriority;
    private int totalUnstableNormalPriority;
    private int totalUnstableLowPriority;

    private int totalFailedAllPriorities;
    private int totalFailedHighPriority;
    private int totalFailedNormalPriority;
    private int totalFailedLowPriority;

    private int newUnstableAllPriorities;
    private int newUnstableHighPriority;
    private int newUnstableNormalPriority;
    private int newUnstableLowPriority;

    private int newFailedAllPriorities;
    private int newFailedHighPriority;
    private int newFailedNormalPriority;
    private int newFailedLowPriority;

    @SuppressWarnings("ParameterNumber")
    public QualityGate(final int totalUnstableAllPriorities, final int totalUnstableHighPriority, final int totalUnstableNormalPriority,
            final int totalUnstableLowPriority, final int totalFailedAllPriorities, final int totalFailedHighPriority, final int totalFailedNormalPriority,
            final int totalFailedLowPriority, final int newUnstableAllPriorities, final int newUnstableHighPriority, final int newUnstableNormalPriority,
            final int newUnstableLowPriority, final int newFailedAllPriorities, final int newFailedHighPriority, final int newFailedNormalPriority, final int newFailedLowPriority) {
        this.totalUnstableAllPriorities = totalUnstableAllPriorities;
        this.totalUnstableHighPriority = totalUnstableHighPriority;
        this.totalUnstableNormalPriority = totalUnstableNormalPriority;
        this.totalUnstableLowPriority = totalUnstableLowPriority;
        this.totalFailedAllPriorities = totalFailedAllPriorities;
        this.totalFailedHighPriority = totalFailedHighPriority;
        this.totalFailedNormalPriority = totalFailedNormalPriority;
        this.totalFailedLowPriority = totalFailedLowPriority;
        this.newUnstableAllPriorities = newUnstableAllPriorities;
        this.newUnstableHighPriority = newUnstableHighPriority;
        this.newUnstableNormalPriority = newUnstableNormalPriority;
        this.newUnstableLowPriority = newUnstableLowPriority;
        this.newFailedAllPriorities = newFailedAllPriorities;
        this.newFailedHighPriority = newFailedHighPriority;
        this.newFailedNormalPriority = newFailedNormalPriority;
        this.newFailedLowPriority = newFailedLowPriority;
    }


    /**
     * Get issues of total unstable priorities.
     *
     * @return number of total unstable priorities
     */
    public int getTotalUnstableAllPriorities() {
        return totalUnstableAllPriorities;
    }

    /**
     * Get issues of total unstable high priority.
     *
     * @return number of total unstable high priority
     */
    public int getTotalUnstableHighPriority() {
        return totalUnstableHighPriority;
    }

    /**
     * Get issues of total unstable normal priority.
     *
     * @return number of total unstable low priority
     */
    public int getTotalUnstableNormalPriority() {
        return totalUnstableNormalPriority;
    }

    /**
     * Get issues of total unstable normal priority.
     *
     * @return number of total unstable low priority
     */
    public int getTotalUnstableLowPriority() {
        return totalUnstableLowPriority;
    }

    /**
     * Get issues of total all failed priorities.
     *
     * @return number of all total failed priorities
     */
    public int getTotalFailedAllPriorities() {
        return totalFailedAllPriorities;
    }

    /**
     * Get issues of total failed high priority.
     *
     * @return number of total failed high priority
     */
    public int getTotalFailedHighPriority() {
        return totalFailedHighPriority;
    }

    /**
     * Get issues of total failed normal priority.
     *
     * @return number of total failed normal priority
     */
    public int getTotalFailedNormalPriority() {
        return totalFailedNormalPriority;
    }

    /**
     * Get issues of total failed low priority.
     *
     * @return number of total failed low priority
     */
    public int getTotalFailedLowPriority() {
        return totalFailedLowPriority;
    }

    /**
     * Get issues of new total unstable priorities.
     *
     * @return number of total unstable priorities
     */
    public int getNewUnstableAllPriorities() {
        return newUnstableAllPriorities;
    }

    /**
     * Get issues of new unstable high priority.
     *
     * @return number of total unstable high priority
     */
    public int getNewUnstableHighPriority() {
        return newUnstableHighPriority;
    }

    /**
     * Get issues of new unstable normal priority.
     *
     * @return number of total unstable normal priority
     */
    public int getNewUnstableNormalPriority() {
        return newUnstableNormalPriority;
    }

    /**
     * Get issues of new unstable low priority.
     *
     * @return number of total unstable low priority
     */
    public int getNewUnstableLowPriority() {
        return newUnstableLowPriority;
    }

    /**
     * Get issues of new failed all priorities.
     *
     * @return number of total failed all priorities
     */
    public int getNewFailedAllPriorities() {
        return newFailedAllPriorities;
    }

    /**
     * Get issues of new failed high priority.
     *
     * @return number of total failed high priority
     */
    public int getNewFailedHighPriority() {
        return newFailedHighPriority;
    }

    /**
     * Get issues of new failed normal priority.
     *
     * @return number of total failed normal priority
     */
    public int getNewFailedNormalPriority() {
        return newFailedNormalPriority;
    }

    /**
     * Get issues of new failed low priority.
     *
     * @return number of total failed low priority
     */
    public int getNewFailedLowPriority() {
        return newFailedLowPriority;
    }

    /**
     * Checks if totalUnstableAllPriorities >= 0.
     *
     * @return true if totalUnstableAllPriorities is set
     */
    public boolean isTotalUnstableAllPriorities() {
        return totalUnstableAllPriorities >= 0;
    }

    /**
     * Checks if totalUnstableHighPriority >= 0.
     *
     * @return true if totalUnstableHighPriority is set
     */
    public boolean isTotalUnstableHighPriority() {
        return totalUnstableHighPriority >= 0;
    }

    /**
     * Checks if totalUnstableNormalPriority >= 0.
     *
     * @return true if totalUnstableNormalPriority is set
     */
    public boolean isTotalUnstableNormalPriority() {
        return totalUnstableNormalPriority >= 0;
    }

    /**
     * Checks if totalUnstableLowPriority >= 0.
     *
     * @return true if totalUnstableLowPriority is set
     */
    public boolean isTotalUnstableLowPriority() {
        return totalUnstableLowPriority >= 0;
    }

    /**
     * Checks if totalFailedAllPriorities >= 0.
     *
     * @return true if totalFailedAllPriorities is set
     */
    public boolean isTotalFailedAllPriorities() {
        return totalFailedAllPriorities >= 0;
    }

    /**
     * Checks if totalFailedHighPriority >= 0.
     *
     * @return true if totalFailedHighPriority is set
     */
    public boolean isTotalFailedHighPriority() {
        return totalFailedHighPriority >= 0;
    }

    /**
     * Checks if totalFailedNormalPriority >= 0.
     *
     * @return true if totalFailedNormalPriority is set
     */
    public boolean isTotalFailedNormalPriority() {
        return totalFailedNormalPriority >= 0;
    }

    /**
     * Checks if totalFailedLowPriority >= 0.
     *
     * @return true if totalFailedLowPriority is set
     */
    public boolean isTotalFailedLowPriority() {
        return totalFailedLowPriority >= 0;
    }

    /**
     * Checks if newUnstableAllPriorities >= 0.
     *
     * @return true if newUnstableAllPriorities is set
     */
    public boolean isNewUnstableAllPriorities() {
        return newUnstableAllPriorities >= 0;
    }

    /**
     * Checks if newUnstableHighPriority >= 0.
     *
     * @return true if newUnstableHighPriority is set
     */
    public boolean isNewUnstableHighPriority() {
        return newUnstableHighPriority >= 0;
    }

    /**
     * Checks if newUnstableNormalPriority >= 0.
     *
     * @return true if newUnstableNormalPriority is set
     */
    public boolean isNewUnstableNormalPriority() {
        return newUnstableNormalPriority >= 0;
    }

    /**
     * Checks if newUnstableLowPriority >= 0.
     *
     * @return true if newUnstableLowPriority is set
     */
    public boolean isNewUnstableLowPriority() {
        return newUnstableLowPriority >= 0;
    }

    /**
     * Checks if newFailedAllPriorities >= 0.
     *
     * @return true if newFailedAllPriorities is set
     */
    public boolean isNewFailedAllPriorities() {
        return newFailedAllPriorities >= 0;
    }

    /**
     * Checks if newFailedHighPriority >= 0.
     *
     * @return true if newFailedHighPriority is set
     */
    public boolean isNewFailedHighPriority() {
        return newFailedHighPriority >= 0;
    }

    /**
     * Checks if newFailedNormalPriority >= 0.
     *
     * @return true if newFailedNormalPriority is set
     */
    public boolean isNewFailedNormalPriority() {
        return newFailedNormalPriority >= 0;
    }

    /**
     * Checks if newFailedLowPriority >= 0.
     *
     * @return true if newFailedLowPriority is set
     */
    public boolean isNewFailedLowPriority() {
        return newFailedLowPriority >= 0;
    }

}
