package io.jenkins.plugins.analysis.core.quality;

/**
 * Defines quality gates for a static analysis run.
 */
public class QualityGate {
    /** How many issues does it take to fail. **/
    private final int totalFailureThreshold;
    /** How many high priority issues does it take to fail. **/
    private final int highPriorityFailureThreshold;
    /** How many normal priority issues does it take to fail. **/
    private final int normalPriorityFailureThreshold;
    /** How many low priority issues does it take to fail. **/
    private final int lowPriorityFailureThreshold;

    /** How many issues does it take to be unstable. **/
    private final int totalUnstableThreshold;
    /** How many high priority issues does it take to be unstable. **/
    private final int highPriorityUnstableThreshold;
    /** How many normal priority issues does it take to be unstable. **/
    private final int normalPriorityUnstableThreshold;
    /** How many low priority issues does it take to be unstable. **/
    private final int lowPriorityUnstableThreshold;

    /** How many new issues does it take to fail. **/
    private final int newTotalFailureThreshold;
    /** How many new high priority issues does it take to fail. **/
    private final int newHighPriorityFailureThreshold;
    /** How many new normal priority issues does it take to fail. **/
    private final int newNormalPriorityFailureThreshold;
    /** How many new low priority issues does it take to fail. **/
    private final int newLowPriorityFailureThreshold;

    /** How many new issues does it take to be unstable. **/
    private final int newTotalUnstableThreshold;
    /** How many new high priority issues does it take to be unstable. **/
    private final int newHighPriorityUnstableThreshold;
    /** How many new normal priority issues does it take to be unstable. **/
    private final int newNormalPriorityUnstableThreshold;
    /** How many new low priority issues does it take to be unstable. **/
    private final int newLowPriorityUnstableThreshold;

    /**
     * Creates new instance of {@link QualityGate}
     */
    public QualityGate(final int totalFailureThreshold,
            final int highPriorityFailureThreshold,
            final int normalPriorityFailureThreshold,
            final int lowPriorityFailureThreshold,
            final int totalUnstableThreshold,
            final int highPriorityUnstableThreshold,
            final int normalPriorityUnstableThreshold,
            final int lowPriorityUnstableThreshold,
            final int newTotalFailureThreshold,
            final int newHighPriorityFailureThreshold,
            final int newNormalPriorityFailureThreshold,
            final int newLowPriorityFailureThreshold,
            final int newTotalUnstableThreshold,
            final int newHighPriorityUnstableThreshold,
            final int newNormalPriorityUnstableThreshold,
            final int newLowPriorityUnstableThreshold) {
        this.totalFailureThreshold = totalFailureThreshold;
        this.highPriorityFailureThreshold = highPriorityFailureThreshold;
        this.normalPriorityFailureThreshold = normalPriorityFailureThreshold;
        this.lowPriorityFailureThreshold = lowPriorityFailureThreshold;

        this.totalUnstableThreshold = totalUnstableThreshold;
        this.highPriorityUnstableThreshold = highPriorityUnstableThreshold;
        this.normalPriorityUnstableThreshold = normalPriorityUnstableThreshold;
        this.lowPriorityUnstableThreshold = lowPriorityUnstableThreshold;

        this.newTotalFailureThreshold = newTotalFailureThreshold;
        this.newHighPriorityFailureThreshold = newHighPriorityFailureThreshold;
        this.newNormalPriorityFailureThreshold = newNormalPriorityFailureThreshold;
        this.newLowPriorityFailureThreshold = newLowPriorityFailureThreshold;

        this.newTotalUnstableThreshold = newTotalUnstableThreshold;
        this.newHighPriorityUnstableThreshold = newHighPriorityUnstableThreshold;
        this.newNormalPriorityUnstableThreshold = newNormalPriorityUnstableThreshold;
        this.newLowPriorityUnstableThreshold = newLowPriorityUnstableThreshold;
    }

    /**
     * Determines if a failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the failure threshold for the total number of issues is set
     */
    public boolean hasTotalFailureThreshold() {
        return totalFailureThreshold > 0;
    }

    /**
     * Determines if a failure threshold for the total number of high priority issues is set
     *
     * @return {@code true} if the failure threshold for the total number of high priority issues is set
     */
    public boolean hasHighFailureThreshold() {
        return highPriorityFailureThreshold > 0;
    }

    /**
     * Determines if a failure threshold for the total number of normal priority issues is set
     *
     * @return {@code true} if the failure threshold for the total number of normal priority issues is set
     */
    public boolean hasNormalFailureThreshold() {
        return normalPriorityFailureThreshold > 0;
    }

    /**
     * Determines if a failure threshold for the total number of low priority issues is set
     *
     * @return {@code true} if the failure threshold for the total number of low priority issues is set
     */
    public boolean hasLowFailureThreshold() {
        return lowPriorityFailureThreshold > 0;
    }

    /**
     * Determines if a unstable threshold for the total number of issues is set
     *
     * @return {@code true} if the unstable threshold for the total number of issues is set
     */
    public boolean hasTotalUnstableThreshold() {
        return totalUnstableThreshold > 0;
    }

    /**
     * Determines if a unstable threshold for the total number of high priority issues is set
     *
     * @return {@code true} if the unstable threshold for the total number of high priority issues is set
     */
    public boolean hasHighUnstableThreshold() {
        return highPriorityUnstableThreshold > 0;
    }

    /**
     * Determines if a unstable threshold for the total number of normal priority issues is set
     *
     * @return {@code true} if the unstable threshold for the total number of normal priority issues is set
     */
    public boolean hasNormalUnstableThreshold() {
        return normalPriorityUnstableThreshold > 0;
    }

    /**
     * Determines if a unstable threshold for the total number of low priority issues is set
     *
     * @return {@code true} if the unstable threshold for the total number of low priority issues is set
     */
    public boolean hasLowUnstableThreshold() {
        return lowPriorityUnstableThreshold > 0;
    }

    /**
     * Determines if a failure threshold for the total number of new issues is set
     *
     * @return {@code true} if the failure threshold for the total number of new issues is set
     */
    public boolean hasNewTotalFailureThreshold() {
        return newTotalFailureThreshold > 0;
    }

    /**
     * Determines if a failure threshold for the total number of new high priority issues is set
     *
     * @return {@code true} if the failure threshold for the total number of new high priority issues is set
     */
    public boolean hasNewHighFailureThreshold() {
        return newHighPriorityFailureThreshold > 0;
    }

    /**
     * Determines if a failure threshold for the total number of new normal priority issues is set
     *
     * @return {@code true} if the failure threshold for the total number of new normal priority issues is set
     */
    public boolean hasNewNormalFailureThreshold() {
        return newNormalPriorityFailureThreshold > 0;
    }

    /**
     * Determines if a failure threshold for the total number of new low priority issues is set
     *
     * @return {@code true} if the failure threshold for the total number of new low priority issues is set
     */
    public boolean hasNewLowFailureThreshold() {
        return newLowPriorityFailureThreshold > 0;
    }

    /**
     * Determines if a unstable threshold for the total number of new issues is set
     *
     * @return {@code true} if the unstable threshold for the total number of new issues is set
     */
    public boolean hasNewTotalUnstableThreshold() {
        return newTotalUnstableThreshold > 0;
    }

    /**
     * Determines if a unstable threshold for the total number of new high priority issues is set
     *
     * @return {@code true} if the unstable threshold for the total number of new high priority issues is set
     */
    public boolean hasNewHighUnstableThreshold() {
        return newHighPriorityUnstableThreshold > 0;
    }

    /**
     * Determines if a unstable threshold for the total number of new normal priority issues is set
     *
     * @return {@code true} if the unstable threshold for the total number of new normal priority issues is set
     */
    public boolean hasNewNormalUnstableThreshold() {
        return newNormalPriorityUnstableThreshold > 0;
    }

    /**
     * Determines if a unstable threshold for the total number of new low priority issues is set
     *
     * @return {@code true} if the unstable threshold for the total number of new low priority issues is set
     */
    public boolean hasNewLowUnstableThreshold() {
        return newLowPriorityUnstableThreshold > 0;
    }


    public int getTotalFailureThreshold() {
        return totalFailureThreshold;
    }

    public int getHighPriorityFailureThreshold() {
        return highPriorityFailureThreshold;
    }

    public int getNormalPriorityFailureThreshold() {
        return normalPriorityFailureThreshold;
    }

    public int getLowPriorityFailureThreshold() {
        return lowPriorityFailureThreshold;
    }

    public int getTotalUnstableThreshold() {
        return totalUnstableThreshold;
    }

    public int getHighPriorityUnstableThreshold() {
        return highPriorityUnstableThreshold;
    }

    public int getNormalPriorityUnstableThreshold() {
        return normalPriorityUnstableThreshold;
    }

    public int getLowPriorityUnstableThreshold() {
        return lowPriorityUnstableThreshold;
    }

    public int getNewTotalFailureThreshold() {
        return newTotalFailureThreshold;
    }

    public int getNewHighPriorityFailureThreshold() {
        return newHighPriorityFailureThreshold;
    }

    public int getNewNormalPriorityFailureThreshold() {
        return newNormalPriorityFailureThreshold;
    }

    public int getNewLowPriorityFailureThreshold() {
        return newLowPriorityFailureThreshold;
    }

    public int getNewTotalUnstableThreshold() {
        return newTotalUnstableThreshold;
    }

    public int getNewHighPriorityUnstableThreshold() {
        return newHighPriorityUnstableThreshold;
    }

    public int getNewNormalPriorityUnstableThreshold() {
        return newNormalPriorityUnstableThreshold;
    }

    public int getNewLowPriorityUnstableThreshold() {
        return newLowPriorityUnstableThreshold;
    }
}
