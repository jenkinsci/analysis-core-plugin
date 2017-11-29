package io.jenkins.plugins.analysis.core.quality;

import hudson.model.Result;

/**
 * Defines quality gates for a static analysis run.
 *
 * @author Raphael Furch
 */
public class QualityGate {

    /**
     * Unstable threshold for total Builds .
     */
    private final ThresholdSet totalBuildsUnstableThresholds;
    /**
     * Failure threshold for total Builds .
     */
    private final ThresholdSet totalBuildsFailureThresholds;
    /**
     * Unstable threshold for new Builds .
     */
    private final ThresholdSet newBuildsUnstableThresholds;
    /**
     * Failure threshold for new Builds .
     */
    private final ThresholdSet newBuildsFailureThresholds;


    /**
     * C.
     * @param totalBuildsUnstableThresholds = Unstable threshold for total Builds .
     * @param totalBuildsFailureThresholds = Failure threshold for total Builds .
     * @param newBuildsUnstableThresholds = Unstable threshold for new Builds .
     * @param newBuildsFailureThresholds = Failure threshold for new Builds .
     */
    QualityGate(ThresholdSet totalBuildsUnstableThresholds, ThresholdSet totalBuildsFailureThresholds, ThresholdSet newBuildsUnstableThresholds, ThresholdSet newBuildsFailureThresholds) {
        this.totalBuildsUnstableThresholds = totalBuildsUnstableThresholds;
        this.totalBuildsFailureThresholds = totalBuildsFailureThresholds;
        this.newBuildsUnstableThresholds = newBuildsUnstableThresholds;
        this.newBuildsFailureThresholds = newBuildsFailureThresholds;
    }

    /**
     * Evaluate a StaticAnalysisRun.
     * If a Threshold is exceeded the result will show it.
     * IF no Threshold is exceeded the result will be SUCCESS.
     * @param run = StaticAnalysisRun to check.
     * @return the result of the evaluation.
     */
    public Result evaluate(final StaticAnalysisRun run) {
        // Extract total values.
        final int totalSize = run.getTotalSize();
        final int totalHighPriority = run.getTotalHighPrioritySize();
        final int totalNormalPriority = run.getTotalNormalPrioritySize();
        final int totalLowPriority = run.getTotalLowPrioritySize();

        // Extract new values
        final int newSize = run.getNewSize();
        final int newHighPriority = run.getNewHighPrioritySize();
        final int newNormalPriority = run.getNewNormalPrioritySize();
        final int newLowPriority = run.getNewLowPrioritySize();

        // Check if values exceeding the thresholds
        if(totalBuildsFailureThresholds.exceedAnyThreshold(totalSize, totalHighPriority, totalNormalPriority, totalLowPriority)){
            return Result.FAILURE;
        }
        else if(newBuildsFailureThresholds.exceedAnyThreshold(newSize, newHighPriority, newNormalPriority, newLowPriority)){
            return Result.FAILURE;
        }
        else if(totalBuildsUnstableThresholds.exceedAnyThreshold(totalSize, totalHighPriority, totalNormalPriority, totalLowPriority)){
            return Result.UNSTABLE;
        }
        else if(newBuildsUnstableThresholds.exceedAnyThreshold(newSize, newHighPriority, newNormalPriority, newLowPriority)){
            return Result.UNSTABLE;
        }
        else{
            return Result.SUCCESS;
        }

    }






}
