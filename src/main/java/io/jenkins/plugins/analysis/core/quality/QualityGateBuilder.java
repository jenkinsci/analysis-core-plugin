package io.jenkins.plugins.analysis.core.quality;

public class QualityGateBuilder {

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


    public QualityGateBuilder() {
        this.totalUnstableAllPriorities = -1;
        this.totalUnstableHighPriority = -1;
        this.totalUnstableNormalPriority = -1;
        this.totalUnstableLowPriority = -1;
        this.totalFailedAllPriorities = -1;
        this.totalFailedHighPriority = -1;
        this.totalFailedNormalPriority = -1;
        this.totalFailedLowPriority = -1;
        this.newUnstableAllPriorities = -1;
        this.newUnstableHighPriority = -1;
        this.newUnstableNormalPriority = -1;
        this.newUnstableLowPriority = -1;
        this.newFailedAllPriorities = -1;
        this.newFailedHighPriority = -1;
        this.newFailedNormalPriority = -1;
        this.newFailedLowPriority = -1;
    }

    public QualityGateBuilder setTotalUnstableAllPriorities(final int totalUnstableAllPriorities) {
        this.totalUnstableAllPriorities = totalUnstableAllPriorities;
        return this;
    }

    public QualityGateBuilder setTotalUnstableHighPriority(final int totalUnstableHighPriority) {
        this.totalUnstableHighPriority = totalUnstableHighPriority;
        return this;
    }

    public QualityGateBuilder setTotalUnstableNormalPriority(final int totalUnstableNormalPriority) {
        this.totalUnstableNormalPriority = totalUnstableNormalPriority;
        return this;
    }

    public QualityGateBuilder setTotalUnstableLowPriority(final int totalUnstableLowPriority) {
        this.totalUnstableLowPriority = totalUnstableLowPriority;
        return this;
    }

    public QualityGateBuilder setTotalFailedAllPriorities(final int totalFailedAllPriorities) {
        this.totalFailedAllPriorities = totalFailedAllPriorities;
        return this;
    }

    public QualityGateBuilder setTotalFailedHighPriority(final int totalFailedHighPriority) {
        this.totalFailedHighPriority = totalFailedHighPriority;
        return this;
    }

    public QualityGateBuilder setTotalFailedNormalPriority(final int totalFailedNormalPriority) {
        this.totalFailedNormalPriority = totalFailedNormalPriority;
        return this;
    }

    public QualityGateBuilder setTotalFailedLowPriority(final int totalFailedLowPriority) {
        this.totalFailedLowPriority = totalFailedLowPriority;
        return this;
    }

    public QualityGateBuilder setNewUnstableAllPriorities(final int newUnstableAllPriorities) {
        this.newUnstableAllPriorities = newUnstableAllPriorities;
        return this;
    }

    public QualityGateBuilder setNewUnstableHighPriority(final int newUnstableHighPriority) {
        this.newUnstableHighPriority = newUnstableHighPriority;
        return this;
    }

    public QualityGateBuilder setNewUnstableNormalPriority(final int newUnstableNormalPriority) {
        this.newUnstableNormalPriority = newUnstableNormalPriority;
        return this;
    }

    public QualityGateBuilder setNewUnstableLowPriority(final int newUnstableLowPriority) {
        this.newUnstableLowPriority = newUnstableLowPriority;
        return this;
    }

    public QualityGateBuilder setNewFailedAllPriorities(final int newFailedAllPriorities) {
        this.newFailedAllPriorities = newFailedAllPriorities;
        return this;
    }

    public QualityGateBuilder setNewFailedHighPriority(final int newFailedHighPriority) {
        this.newFailedHighPriority = newFailedHighPriority;
        return this;
    }

    public QualityGateBuilder setNewFailedNormalPriority(final int newFailedNormalPriority) {
        this.newFailedNormalPriority = newFailedNormalPriority;
        return this;
    }

    public QualityGateBuilder setNewFailedLowPriority(final int newFailedLowPriority) {
        this.newFailedLowPriority = newFailedLowPriority;
        return this;
    }


    public QualityGate build() {
        return new QualityGate(totalUnstableAllPriorities, totalUnstableHighPriority, totalUnstableNormalPriority, totalUnstableLowPriority, totalFailedAllPriorities, totalFailedHighPriority,
                totalFailedNormalPriority, totalFailedLowPriority, newUnstableAllPriorities, newUnstableHighPriority, newUnstableNormalPriority, newUnstableLowPriority, newFailedAllPriorities,
                newFailedHighPriority, newFailedNormalPriority, newFailedLowPriority);
    }
}
