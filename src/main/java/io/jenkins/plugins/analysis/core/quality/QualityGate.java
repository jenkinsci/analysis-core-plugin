package io.jenkins.plugins.analysis.core.quality;

/**
 * Defines quality gates for a static analysis run.
 *
 * @author Ullrich Hafner
 */
public class QualityGate {
    private final int totalFailureThreshold;
    private final int highPriorityFailureThreshold;
    private final int normalPriorityFailureThreshold;
    private final int lowPriorityFailureThreshold;

    private final int totalUnstableThreshold;
    private final int highPriorityUnstableThreshold;
    private final int normalPriorityUnstableThreshold;
    private final int lowPriorityUnstableThreshold;

    private final int newTotalFailureThreshold;
    private final int newHighPriorityFailureThreshold;
    private final int newNormalPriorityFailureThreshold;
    private final int newLowPriorityFailureThreshold;

    private final int newTotalUnstableThreshold;
    private final int newHighPriorityUnstableThreshold;
    private final int newNormalPriorityUnstableThreshold;
    private final int newLowPriorityUnstableThreshold;

    /**
     * Creates a new instance of {@link QualityGate}.
     * TODO change comment
    **/
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
    public boolean hasHighFailureThreshold(){ return highPriorityFailureThreshold > 0;}
    public boolean hasNormalFailureThreshold(){ return normalPriorityFailureThreshold > 0;}
    public boolean hasLowFailureThreshold(){ return lowPriorityFailureThreshold > 0;}

    public boolean hasTotalUnstableThreshold(){ return totalUnstableThreshold > 0;}
    public boolean hasHighUnstableThreshold(){ return highPriorityUnstableThreshold > 0;}
    public boolean hasNormalUnstableThreshold(){ return normalPriorityUnstableThreshold > 0;}
    public boolean hasLowUnstableThreshold(){ return lowPriorityUnstableThreshold > 0;}

    public boolean hasNewTotalFailureThreshold() {
        return newTotalFailureThreshold > 0;
    }
    public boolean hasNewHighFailureThreshold(){ return newHighPriorityFailureThreshold > 0;}
    public boolean hasNewNormalFailureThreshold(){ return newNormalPriorityFailureThreshold > 0;}
    public boolean hasNewLowFailureThreshold(){ return newLowPriorityFailureThreshold > 0;}

    public boolean hasNewTotalUnstableThreshold(){ return newTotalUnstableThreshold > 0;}
    public boolean hasNewHighUnstableThreshold(){ return newHighPriorityUnstableThreshold > 0;}
    public boolean hasNewNormalUnstableThreshold(){ return newNormalPriorityUnstableThreshold > 0;}
    public boolean hasNewLowUnstableThreshold(){ return newLowPriorityUnstableThreshold > 0;}


    /**
     * Returns the failure threshold for the total number of issues.
     *
     * @return the failure threshold for the total number of issues
     */
    public int getTotalFailureThreshold() {
        return totalFailureThreshold;
    }

    public int getHighPriorityFailureThreshold() { return highPriorityFailureThreshold; }

    public int getNormalPriorityFailureThreshold() { return normalPriorityFailureThreshold; }

    public int getLowPriorityFailureThreshold() { return lowPriorityFailureThreshold; }

    public int getTotalUnstableThreshold() { return totalUnstableThreshold; }

    public int getHighPriorityUnstableThreshold() { return highPriorityUnstableThreshold; }

    public int getNormalPriorityUnstableThreshold() { return normalPriorityUnstableThreshold; }

    public int getLowPriorityUnstableThreshold() { return lowPriorityUnstableThreshold; }

    public int getNewTotalFailureThreshold() {
        return newTotalFailureThreshold;
    }

    public int getNewHighPriorityFailureThreshold() { return newHighPriorityFailureThreshold; }

    public int getNewNormalPriorityFailureThreshold() { return newNormalPriorityFailureThreshold; }

    public int getNewLowPriorityFailureThreshold() { return newLowPriorityFailureThreshold; }

    public int getNewTotalUnstableThreshold() { return newTotalUnstableThreshold; }

    public int getNewHighPriorityUnstableThreshold() { return newHighPriorityUnstableThreshold; }

    public int getNewNormalPriorityUnstableThreshold() { return newNormalPriorityUnstableThreshold; }

    public int getNewLowPriorityUnstableThreshold() { return newLowPriorityUnstableThreshold; }
}
