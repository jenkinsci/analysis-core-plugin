package io.jenkins.plugins.analysis.core.quality;

/**
 * A set of 4 Thresholds.
 * One for all high, normal and low priorities.
 *
 * @author Raphael Furch
 */
public class ThresholdSet{
    /**
     * Threshold for all priorities.
     */
    private final int allPrioritiesThreshold;
    /**
     * Threshold for high priority.
     */
    private final int highPriorityThreshold;
    /**
     * Threshold for normal priority.
     */
    private final int normalPriorityThreshold;
    /**
     * Threshold for low priority.
     */
    private final int lowPriorityThreshold;

    /**
     * C.
     * If a values is negative, the threshold will be ignored.
     * @param allPrioritiesThreshold =  Threshold for all priorities.
     * @param highPriorityThreshold =  Threshold for high priority.
     * @param normalPriorityThreshold =  Threshold for normal priority.
     * @param lowPriorityThreshold =  Threshold for low priority.
     */
    public ThresholdSet(final int allPrioritiesThreshold, final int highPriorityThreshold, final int normalPriorityThreshold, final int lowPriorityThreshold){
        this.allPrioritiesThreshold = allPrioritiesThreshold;
        this.highPriorityThreshold = highPriorityThreshold;
        this.normalPriorityThreshold = normalPriorityThreshold;
        this.lowPriorityThreshold = lowPriorityThreshold;
    }

    /**
     * Check if current is smaller then allThreshold.
     * @param current = value to check.
     * @return exceeds threshold.
     */
    public boolean exceedAllPrioritiesThreshold(final int current){
        return this.allPrioritiesThreshold > 0 && current > allPrioritiesThreshold;
    }

    /**
     * Check if current is smaller then highThreshold.
     * @param current = value to check.
     * @return exceeds threshold.
     */
    public boolean exceedHighPriorityThreshold(final int current){
        return this.highPriorityThreshold > 0 && current > highPriorityThreshold;
    }
    /**
     * Check if current is smaller then normalThreshold.
     * @param current = value to check.
     * @return exceeds threshold.
     */
    public boolean exceedNormalPriorityThreshold(final int current){
        return this.normalPriorityThreshold > 0 && current > normalPriorityThreshold;
    }

    /**
     * Check if current is smaller then lowThreshold.
     * @param current = value to check.
     * @return exceeds threshold.
     */
    public boolean exceedLowPriorityThreshold(final int current){
        return this.lowPriorityThreshold > 0 && current > lowPriorityThreshold;
    }

    /**
     * Check if any threshold is exceeded.
     * @param all = value to check.
     * @param high = value to check.
     * @param normal = value to check.
     * @param low = value to check.
     * @return a value exceeds its threshold.
     */
    public boolean exceedAnyThreshold(final int all, final int high, final int normal, final int low){
        return exceedAllPrioritiesThreshold(all)
                || exceedHighPriorityThreshold(high)
                || exceedNormalPriorityThreshold(normal)
                || exceedLowPriorityThreshold(low);
    }
}
