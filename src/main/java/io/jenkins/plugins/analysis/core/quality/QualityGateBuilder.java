package io.jenkins.plugins.analysis.core.quality;

public class QualityGateBuilder {
    private int lowPriorityUnstable = 0;
    private int lowPriorityFailure = 0;
    private int normalPriorityUnstable = 0;
    private int normalPriorityFailure = 0;
    private int highPriorityUnstable = 0;
    private int highPriorityFailure = 0;

    public QualityGateBuilder setLowPriorityUnstable (int lowPriorityUnstable){
        this.lowPriorityUnstable = lowPriorityUnstable;
        return this;
    }

    public QualityGateBuilder setLowPriorityFailure (int lowPriorityFailure){
        this.lowPriorityFailure = lowPriorityFailure;
        return this;
    }

    public QualityGateBuilder setNormalPriorityUnstable (int normalPriorityUnstable){
        this.normalPriorityUnstable = normalPriorityUnstable;
        return this;
    }

    public QualityGateBuilder setNormalPriorityFailure (int normalPriorityFailure){
        this.normalPriorityFailure = normalPriorityFailure;
        return this;
    }

    public QualityGateBuilder setHighPriorityUnstable (int highPriorityUnstable){
        this.highPriorityUnstable = highPriorityUnstable;
        return this;
    }

    public QualityGateBuilder setHighPriorityFailure (int highPriorityFailure){
        this.highPriorityFailure = highPriorityFailure;
        return this;
    }

    public QualityGate build() {
        return new QualityGate(lowPriorityUnstable, lowPriorityFailure, normalPriorityUnstable, normalPriorityFailure, highPriorityUnstable, highPriorityFailure);
    }
}
