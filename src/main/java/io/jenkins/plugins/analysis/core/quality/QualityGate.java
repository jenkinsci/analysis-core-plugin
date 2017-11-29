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

    public int getTotalUnstableAllPriorities() {
        return totalUnstableAllPriorities;
    }

    public int getTotalUnstableHighPriority() {
        return totalUnstableHighPriority;
    }

    public int getTotalUnstableNormalPriority() {
        return totalUnstableNormalPriority;
    }

    public int getTotalUnstableLowPriority() {
        return totalUnstableLowPriority;
    }

    public int getTotalFailedAllPriorities() {
        return totalFailedAllPriorities;
    }

    public int getTotalFailedHighPriority() {
        return totalFailedHighPriority;
    }

    public int getTotalFailedNormalPriority() {
        return totalFailedNormalPriority;
    }

    public int getTotalFailedLowPriority() {
        return totalFailedLowPriority;
    }

    public int getNewUnstableAllPriorities() {
        return newUnstableAllPriorities;
    }

    public int getNewUnstableHighPriority() {
        return newUnstableHighPriority;
    }

    public int getNewUnstableNormalPriority() {
        return newUnstableNormalPriority;
    }

    public int getNewUnstableLowPriority() {
        return newUnstableLowPriority;
    }

    public int getNewFailedAllPriorities() {
        return newFailedAllPriorities;
    }

    public int getNewFailedHighPriority() {
        return newFailedHighPriority;
    }

    public int getNewFailedNormalPriority() {
        return newFailedNormalPriority;
    }

    public int getNewFailedLowPriority() {
        return newFailedLowPriority;
    }

    /**
     * Checks if totalUnstableAllPriorities are >= 0.
     *
     * @return true if >= 0
     */
    public boolean hasTotalUnstableAllPriorities() {
        return totalUnstableAllPriorities >= 0;
    }

    /**
     * Checks if totalUnstableHighPriority are >= 0.
     *
     * @return true if >= 0
     */
    public boolean hasTotalUnstableHighPriority() {
        return totalUnstableHighPriority >= 0;
    }

    /**
     * Checks if totalUnstableNormalPriority are >= 0.
     *
     * @return true if >= 0
     */
    public boolean hasTotalUnstableNormalPriority() {
        return totalUnstableNormalPriority >= 0;
    }

    /**
     * Checks if totalUnstableLowPriority are >= 0.
     *
     * @return true if >= 0
     */
    public boolean hasTotalUnstableLowPriority() {
        return totalUnstableLowPriority >= 0;
    }

    /**
     * Checks if totalFailedAllPriorities are >= 0.
     *
     * @return true if >= 0
     */
    public boolean hasTotalFailedAllPriorities() {
        return totalFailedAllPriorities >= 0;
    }

    /**
     * Checks if totalFailedHighPriority are >= 0.
     *
     * @return true if >= 0
     */
    public boolean hasTotalFailedHighPriority() {
        return totalFailedHighPriority >= 0;
    }

    /**
     * Checks if totalFailedNormalPriority are >= 0.
     *
     * @return true if >= 0
     */
    public boolean hasTotalFailedNormalPriority() {
        return totalFailedNormalPriority >= 0;
    }

    /**
     * Checks if totalFailedLowPriority are >= 0.
     *
     * @return true if >= 0
     */
    public boolean hasTotalFailedLowPriority() {
        return totalFailedLowPriority >= 0;
    }

    /**
     * Checks if newUnstableAllPriorities are >= 0.
     *
     * @return true if >= 0
     */
    public boolean hasNewUnstableAllPriorities() {
        return newUnstableAllPriorities >= 0;
    }

    /**
     * Checks if newUnstableHighPriority are >= 0.
     *
     * @return true if >= 0
     */
    public boolean hasNewUnstableHighPriority() {
        return newUnstableHighPriority >= 0;
    }

    /**
     * Checks if newUnstableNormalPriority are >= 0.
     *
     * @return true if >= 0
     */
    public boolean hasNewUnstableNormalPriority() {
        return newUnstableNormalPriority >= 0;
    }

    /**
     * Checks if newUnstableLowPriority are >= 0.
     *
     * @return true if >= 0
     */
    public boolean hasNewUnstableLowPriority() {
        return newUnstableLowPriority >= 0;
    }

    /**
     * Checks if newFailedAllPriorities are >= 0.
     *
     * @return true if >= 0
     */
    public boolean hasNewFailedAllPriorities() {
        return newFailedAllPriorities >= 0;
    }

    /**
     * Checks if newFailedHighPriority are >= 0.
     *
     * @return true if >= 0
     */
    public boolean hasNewFailedHighPriority() {
        return newFailedHighPriority >= 0;
    }

    /**
     * Checks if newFailedNormalPriority are >= 0.
     *
     * @return true if >= 0
     */
    public boolean hasNewFailedNormalPriority() {
        return newFailedNormalPriority >= 0;
    }

    /**
     * Checks if newFailedLowPriority are >= 0.
     *
     * @return true if >= 0
     */
    public boolean hasNewFailedLowPriority() {
        return newFailedLowPriority >= 0;
    }

}
