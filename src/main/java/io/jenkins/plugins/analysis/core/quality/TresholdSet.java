package io.jenkins.plugins.analysis.core.quality;

import hudson.model.Result;

public class TresholdSet {

    private int any = Integer.MAX_VALUE;
    private int high = Integer.MAX_VALUE;
    private int medium = Integer.MAX_VALUE;
    private int low = Integer.MAX_VALUE;

    private int newAny = Integer.MAX_VALUE;
    private int newHigh = Integer.MAX_VALUE;
    private int newMedium = Integer.MAX_VALUE;
    private int newLow = Integer.MAX_VALUE;

    /**
     * Set the total ammount of issues allowed before this set evaluates to {@link Result#FAILURE}
     *
     * @param any
     *         number of total issues
     * @param high
     *         number of high priority issues
     * @param medium
     *         number of normal priority issues
     * @param low
     *         number of low priority issues
     */
    public void setTotal(int any, int high, int medium, int low) {
        this.any = Math.max(0, any);
        this.high = Math.max(0, high);
        this.medium = Math.max(0, medium);
        this.low = Math.max(0, low);
    }

    /**
     * Set the total ammount of new issues allowed before this set evaluates to {@link Result#FAILURE}
     *
     * @param any
     *         number of new issues
     * @param high
     *         number of high priority issues
     * @param medium
     *         number of normal priority issues
     * @param low
     *         number of low priority issues
     */
    public void setNew(int any, int high, int medium, int low) {
        this.newAny = Math.max(0, any);
        this.newHigh = Math.max(0, high);
        this.newMedium = Math.max(0, medium);
        this.newLow = Math.max(0, low);
    }

    /**
     * Evaluate this set to see if any thresholds were exceeded.
     *
     * @param run
     *         run to evaluate against.
     *
     * @return {@link Result#SUCCESS} if no tresholds were exceeded otherwise {@link Result#FAILURE}
     */
    public Result evalutate(StaticAnalysisRun run) {
        Result finalResult = Result.SUCCESS;
        if (run.getTotalSize() > this.any || run.getTotalHighPrioritySize() > this.high || run.getTotalNormalPrioritySize() > this.medium || run.getTotalLowPrioritySize() > this.low) {
            finalResult = Result.FAILURE;
        }
        if (run.getNewSize() > this.newAny || run.getNewHighPrioritySize() > this.newHigh || run.getNewNormalPrioritySize() > this.newMedium || run.getNewLowPrioritySize() > this.newLow) {
            finalResult = Result.FAILURE;
        }
        return finalResult;
    }
}
