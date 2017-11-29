package io.jenkins.plugins.analysis.core.quality;

public class QualityGateBuilder {

    private int totalFailureThreshold = 0;
    private int highPriorityFailureThreshold = 0;
    private int normalPriorityFailureThreshold = 0;
    private int lowPriorityFailureThreshold = 0;
    private int totalUnstableThreshold = 0;
    private int highPriorityUnstableThreshold = 0;
    private int normalPriorityUnstableThreshold = 0;
    private int lowPriorityUnstableThreshold = 0;
    private int newTotalFailureThreshold = 0;
    private int newHighPriorityFailureThreshold = 0;
    private int newNormalPriorityFailureThreshold = 0;
    private int newLowPriorityFailureThreshold = 0;
    private int newTotalUnstableThreshold = 0;
    private int newHighPriorityUnstableThreshold = 0;
    private int newNormalPriorityUnstableThreshold = 0;
    private int newLowPriorityUnstableThreshold = 0;

    public QualityGateBuilder setTotalFailureThreshold(final int totalFailureThreshold) {
        this.totalFailureThreshold = totalFailureThreshold;
        return this;
    }

    public QualityGateBuilder setHighPriorityFailureThreshold(final int highPriorityFailureThreshold) {
        this.highPriorityFailureThreshold = highPriorityFailureThreshold;
        return this;
    }

    public QualityGateBuilder setNormalPriorityFailureThreshold(final int normalPriorityFailureThreshold) {
        this.normalPriorityFailureThreshold = normalPriorityFailureThreshold;
        return this;
    }

    public QualityGateBuilder setLowPriorityFailureThreshold(final int lowPriorityFailureThreshold) {
        this.lowPriorityFailureThreshold = lowPriorityFailureThreshold;
        return this;
    }

    public QualityGateBuilder setTotalUnstableThreshold(final int totalUnstableThreshold) {
        this.totalUnstableThreshold = totalUnstableThreshold;
        return this;
    }

    public QualityGateBuilder setHighPriorityUnstableThreshold(final int highPriorityUnstableThreshold) {
        this.highPriorityUnstableThreshold = highPriorityUnstableThreshold;
        return this;
    }

    public QualityGateBuilder setNormalPriorityUnstableThreshold(final int normalPriorityUnstableThreshold) {
        this.normalPriorityUnstableThreshold = normalPriorityUnstableThreshold;
        return this;
    }

    public QualityGateBuilder setLowPriorityUnstableThreshold(final int lowPriorityUnstableThreshold) {
        this.lowPriorityUnstableThreshold = lowPriorityUnstableThreshold;
        return this;
    }

    public QualityGateBuilder setNewTotalFailureThreshold(final int newTotalFailureThreshold) {
        this.newTotalFailureThreshold = newTotalFailureThreshold;
        return this;
    }

    public QualityGateBuilder setNewHighPriorityFailureThreshold(final int newHighPriorityFailureThreshold) {
        this.newHighPriorityFailureThreshold = newHighPriorityFailureThreshold;
        return this;
    }

    public QualityGateBuilder setNewNormalPriorityFailureThreshold(final int newNormalPriorityFailureThreshold) {
        this.newNormalPriorityFailureThreshold = newNormalPriorityFailureThreshold;
        return this;
    }

    public QualityGateBuilder setNewLowPriorityFailureThreshold(final int newLowPriorityFailureThreshold) {
        this.newLowPriorityFailureThreshold = newLowPriorityFailureThreshold;
        return this;
    }

    public QualityGateBuilder setNewTotalUnstableThreshold(final int newTotalUnstableThreshold) {
        this.newTotalUnstableThreshold = newTotalUnstableThreshold;
        return this;
    }

    public QualityGateBuilder setNewHighPriorityUnstableThreshold(final int newHighPriorityUnstableThreshold) {
        this.newHighPriorityUnstableThreshold = newHighPriorityUnstableThreshold;
        return this;
    }

    public QualityGateBuilder setNewNormalPriorityUnstableThreshold(final int newNormalPriorityUnstableThreshold) {
        this.newNormalPriorityUnstableThreshold = newNormalPriorityUnstableThreshold;
        return this;
    }

    public QualityGateBuilder setNewLowPriorityUnstableThreshold(final int newLowPriorityUnstableThreshold) {
        this.newLowPriorityUnstableThreshold = newLowPriorityUnstableThreshold;
        return this;
    }

    public QualityGate createQualityGate() {
        return new QualityGate(totalFailureThreshold, highPriorityFailureThreshold, normalPriorityFailureThreshold, lowPriorityFailureThreshold, totalUnstableThreshold, highPriorityUnstableThreshold, normalPriorityUnstableThreshold, lowPriorityUnstableThreshold, newTotalFailureThreshold, newHighPriorityFailureThreshold, newNormalPriorityFailureThreshold, newLowPriorityFailureThreshold, newTotalUnstableThreshold, newHighPriorityUnstableThreshold, newNormalPriorityUnstableThreshold, newLowPriorityUnstableThreshold);
    }
}