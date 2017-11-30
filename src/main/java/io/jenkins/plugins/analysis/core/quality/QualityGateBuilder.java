package io.jenkins.plugins.analysis.core.quality;

/**
 * Creates new {@link QualityGate} using the builder pattern. All properties that have not been set in the builder will
 * be set to their default value.
 *
 * @author Andreas Moser
 */
@SuppressWarnings("JavaDocMethod")
public class QualityGateBuilder {
    private int failureThreshold = 0;
    private int newIssuesFailureThreshold = 0;

    private int unstableThreshold = 0;
    private int newIssuesUnstableThreshold = 0;

    private int failureThresholdLow = 0;
    private int failureThresholdNormal = 0;
    private int failureThresholdHigh = 0;
    private int newIssuesFailureThresholdLow = 0;
    private int newIssuesFailureThresholdNormal = 0;
    private int newIssuesFailureThresholdHigh = 0;

    private int unstableThresholdLow = 0;
    private int unstableThresholdNormal = 0;
    private int unstableThresholdHigh = 0;
    private int newIssuesUnstableThresholdLow = 0;
    private int newIssuesUnstableThresholdNormal = 0;
    private int newIssuesUnstableThresholdHigh = 0;

    private boolean checkNewIssues = false;

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

    /**
     * Creates a new {@link QualityGate} based on the specified properties.
     *
     * @return the created quality gate.
     */
    public QualityGate build() {
        return new QualityGate(failureThreshold, newIssuesFailureThreshold, unstableThreshold, newIssuesUnstableThreshold,
                failureThresholdLow, failureThresholdNormal, failureThresholdHigh, newIssuesFailureThresholdLow,
                newIssuesFailureThresholdNormal, newIssuesFailureThresholdHigh, unstableThresholdLow,
                unstableThresholdNormal, unstableThresholdHigh, newIssuesUnstableThresholdLow,
                newIssuesUnstableThresholdNormal, newIssuesUnstableThresholdHigh, checkNewIssues);
    }
}
