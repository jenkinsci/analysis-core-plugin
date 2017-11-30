package io.jenkins.plugins.analysis.core.quality;

public class QualityGateBuilder {
    private WarningsThreshold failureThreshold;
    private WarningsThreshold unstableThreshold;
    private WarningsThreshold newFailureThreshold;
    private WarningsThreshold newUnstableThreshold;

    public QualityGateBuilder setFailureThreshold(WarningsThreshold failureThreshold) {
        this.failureThreshold = failureThreshold;
        return this;
    }

    public QualityGateBuilder setUnstableThreshold(WarningsThreshold unstableThreshold) {
        this.unstableThreshold = unstableThreshold;
        return this;
    }

    public QualityGateBuilder setNewFailureThreshold(WarningsThreshold newFailureThreshold) {
        this.newFailureThreshold = newFailureThreshold;
        return this;
    }

    public QualityGateBuilder setNewUnstableThreshold(WarningsThreshold newUnstableThreshold) {
        this.newUnstableThreshold = newUnstableThreshold;
        return this;
    }

    public QualityGate build() {
        return new QualityGate(failureThreshold, unstableThreshold, newFailureThreshold, newUnstableThreshold);
    }
}