package io.jenkins.plugins.analysis.core.quality;

public class QualityGateBuilder {
    private int failureThreshold;
    private int newIssuesFailureThreshold;

    private int unstableThreshold;
    private int newIssuesUnstableThreshold;

    private int failureThresholdLow;
    private int failureThresholdNormal;
    private int failureThresholdHigh;
    private int newIssuesFailureThresholdLow;
    private int newIssuesFailureThresholdNormal;
    private int newIssuesFailureThresholdHigh;

    private int unstableThresholdLow;
    private int unstableThresholdNormal;
    private int unstableThresholdHigh;
    private int newIssuesUnstableThresholdLow;
    private int newIssuesUnstableThresholdNormal;
    private int newIssuesUnstableThresholdHigh;

    private boolean checkNewIssues;

    public QualityGateBuilder setFailureThreshold(final int failureThreshold) {
        this.failureThreshold = failureThreshold;
        return this;
    }

    public QualityGateBuilder setNewIssuesFailureThreshold(final int newIssuesFailureThreshold) {
        this.newIssuesFailureThreshold = newIssuesFailureThreshold;
        return this;
    }

    public QualityGateBuilder setUnstableThreshold(final int unstableThreshold) {
        this.unstableThreshold = unstableThreshold;
        return this;
    }

    public QualityGateBuilder setNewIssuesUnstableThreshold(final int newIssuesUnstableThreshold) {
        this.newIssuesUnstableThreshold = newIssuesUnstableThreshold;
        return this;
    }

    public QualityGateBuilder setFailureThresholdLow(final int failureThresholdLow) {
        this.failureThresholdLow = failureThresholdLow;
        return this;
    }

    public QualityGateBuilder setFailureThresholdNormal(final int failureThresholdNormal) {
        this.failureThresholdNormal = failureThresholdNormal;
        return this;
    }

    public QualityGateBuilder setFailureThresholdHigh(final int failureThresholdHigh) {
        this.failureThresholdHigh = failureThresholdHigh;
        return this;
    }

    public QualityGateBuilder setNewIssuesFailureThresholdLow(final int newIssuesFailureThresholdLow) {
        this.newIssuesFailureThresholdLow = newIssuesFailureThresholdLow;
        return this;
    }

    public QualityGateBuilder setNewIssuesFailureThresholdNormal(final int newIssuesFailureThresholdNormal) {
        this.newIssuesFailureThresholdNormal = newIssuesFailureThresholdNormal;
        return this;
    }

    public QualityGateBuilder setNewIssuesFailureThresholdHigh(final int newIssuesFailureThresholdHigh) {
        this.newIssuesFailureThresholdHigh = newIssuesFailureThresholdHigh;
        return this;
    }

    public QualityGateBuilder setUnstableThresholdLow(final int unstableThresholdLow) {
        this.unstableThresholdLow = unstableThresholdLow;
        return this;
    }

    public QualityGateBuilder setUnstableThresholdNormal(final int unstableThresholdNormal) {
        this.unstableThresholdNormal = unstableThresholdNormal;
        return this;
    }

    public QualityGateBuilder setUnstableThresholdHigh(final int unstableThresholdHigh) {
        this.unstableThresholdHigh = unstableThresholdHigh;
        return this;
    }

    public QualityGateBuilder setNewIssuesUnstableThresholdLow(final int newIssuesUnstableThresholdLow) {
        this.newIssuesUnstableThresholdLow = newIssuesUnstableThresholdLow;
        return this;
    }

    public QualityGateBuilder setNewIssuesUnstableThresholdNormal(final int newIssuesUnstableThresholdNormal) {
        this.newIssuesUnstableThresholdNormal = newIssuesUnstableThresholdNormal;
        return this;
    }

    public QualityGateBuilder setNewIssuesUnstableThresholdHigh(final int newIssuesUnstableThresholdHigh) {
        this.newIssuesUnstableThresholdHigh = newIssuesUnstableThresholdHigh;
        return this;
    }

    public QualityGateBuilder setCheckNewIssues(final boolean checkNewIssues) {
        this.checkNewIssues = checkNewIssues;
        return this;
    }

    public QualityGate build() {
        return new QualityGate(failureThreshold, newIssuesFailureThreshold, unstableThreshold, newIssuesUnstableThreshold,
                failureThresholdLow, failureThresholdNormal, failureThresholdHigh, newIssuesFailureThresholdLow,
                newIssuesFailureThresholdNormal, newIssuesFailureThresholdHigh, unstableThresholdLow,
                unstableThresholdNormal, unstableThresholdHigh, newIssuesUnstableThresholdLow,
                newIssuesUnstableThresholdNormal, newIssuesUnstableThresholdHigh, checkNewIssues);
    }
}
