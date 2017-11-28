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

    public boolean isTotalUnstableAllPriorities() {
        return totalUnstableAllPriorities >= 0;
    }

    public boolean isTotalUnstableHighPriority() {
        return totalUnstableHighPriority >= 0;
    }

    public boolean isTotalUnstableNormalPriority() {
        return totalUnstableNormalPriority >= 0;
    }

    public boolean isTotalUnstableLowPriority() {
        return totalUnstableLowPriority >= 0;
    }

    public boolean isTotalFailedAllPriorities() {
        return totalFailedAllPriorities >= 0;
    }

    public boolean isTotalFailedHighPriority() {
        return totalFailedHighPriority >= 0;
    }

    public boolean isTotalFailedNormalPriority() {
        return totalFailedNormalPriority >= 0;
    }

    public boolean isTotalFailedLowPriority() {
        return totalFailedLowPriority >= 0;
    }

    public boolean isNewUnstableAllPriorities() {
        return newUnstableAllPriorities >= 0;
    }

    public boolean isNewUnstableHighPriority() {
        return newUnstableHighPriority >= 0;
    }

    public boolean isNewUnstableNormalPriority() {
        return newUnstableNormalPriority >= 0;
    }

    public boolean isNewUnstableLowPriority() {
        return newUnstableLowPriority >= 0;
    }

    public boolean isNewFailedAllPriorities() {
        return newFailedAllPriorities >= 0;
    }

    public boolean isNewFailedHighPriority() {
        return newFailedHighPriority >= 0;
    }

    public boolean isNewFailedNormalPriority() {
        return newFailedNormalPriority >= 0;
    }

    public boolean isNewFailedLowPriority() {
        return newFailedLowPriority >= 0;
    }

}
