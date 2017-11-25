package io.jenkins.plugins.analysis.core.quality;

public class ThresholdSet{
    private final int allPrioritiesThreshold;
    private final int highPriorityThreshold;
    private final int normalPriorityThreshold;
    private final int lowPriorityThreshold;

    public ThresholdSet(final int allPrioritiesThreshold, final int highPriorityThreshold, final int normalPriorityThreshold, final int lowPriorityThreshold){
        this.allPrioritiesThreshold = allPrioritiesThreshold;
        this.highPriorityThreshold = highPriorityThreshold;
        this.normalPriorityThreshold = normalPriorityThreshold;
        this.lowPriorityThreshold = lowPriorityThreshold;
    }

    public boolean exceedAllPrioritiesThreshold(final int current){
        return this.allPrioritiesThreshold > 0 && current > allPrioritiesThreshold;
    }
    public boolean exceedHighPriorityThreshold(final int current){
        return this.highPriorityThreshold > 0 && current > highPriorityThreshold;
    }
    public boolean exceedNormalPriorityThreshold(final int current){
        return this.normalPriorityThreshold > 0 && current > normalPriorityThreshold;
    }
    public boolean exceedLowPriorityThreshold(final int current){
        return this.lowPriorityThreshold > 0 && current > lowPriorityThreshold;
    }
    public boolean exceedAThreshold(final int all, final int high, final int normal, final int low){
        return exceedAllPrioritiesThreshold(all)
                || exceedHighPriorityThreshold(high)
                || exceedNormalPriorityThreshold(normal)
                || exceedLowPriorityThreshold(low);
    }
}
