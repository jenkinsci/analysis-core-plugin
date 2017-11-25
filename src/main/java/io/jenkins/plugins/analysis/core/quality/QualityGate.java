package io.jenkins.plugins.analysis.core.quality;

import hudson.model.Result;

/**
 * Defines quality gates for a static analysis run.
 *
 * @author Ullrich Hafner
 */
public class QualityGate {

    private final ThresholdSet totalBuildsUnstableThresholds;
    private final ThresholdSet totalBuildsFailureThresholds;
    private final ThresholdSet newBuildsUnstableThresholds;
    private final ThresholdSet newBuildsFailureThresholds;



    QualityGate(ThresholdSet totalBuildsUnstableThresholds, ThresholdSet totalBuildsFailureThresholds, ThresholdSet newBuildsUnstableThresholds, ThresholdSet newBuildsFailureThresholds) {
        this.totalBuildsUnstableThresholds = totalBuildsUnstableThresholds;
        this.totalBuildsFailureThresholds = totalBuildsFailureThresholds;
        this.newBuildsUnstableThresholds = newBuildsUnstableThresholds;
        this.newBuildsFailureThresholds = newBuildsFailureThresholds;
    }

    public Result evaluate(final StaticAnalysisRun run) {
        final int totalSize = run.getTotalSize();
        final int totalHighPriority = run.getTotalHighPrioritySize();
        final int totalNormalPriority = run.getTotalNormalPrioritySize();
        final int totalLowPriority = run.getTotalLowPrioritySize();

        final int newSize = run.getNewSize();
        final int newHighPriority = run.getNewHighPrioritySize();
        final int newNormalPriority = run.getNewNormalPrioritySize();
        final int newLowPriority = run.getNewLowPrioritySize();

        if(totalBuildsFailureThresholds.exceedAThreshold(totalSize, totalHighPriority, totalNormalPriority, totalLowPriority)){
            return Result.FAILURE;
        }
        else if(newBuildsFailureThresholds.exceedAThreshold(newSize, newHighPriority, newNormalPriority, newLowPriority)){
            return Result.FAILURE;
        }
        else if(totalBuildsUnstableThresholds.exceedAThreshold(totalSize, totalHighPriority, totalNormalPriority, totalLowPriority)){
            return Result.UNSTABLE;
        }
        else if(newBuildsUnstableThresholds.exceedAThreshold(newSize, newHighPriority, newNormalPriority, newLowPriority)){
            return Result.UNSTABLE;
        }
        else{
            return Result.SUCCESS;
        }

    }






}
