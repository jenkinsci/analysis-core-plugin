package io.jenkins.plugins.analysis.core.quality;

import hudson.model.Result;

/**
 * Enforces the defined quality gates.
 *
 * @author Ullrich Hafner
 */
public class QualityGateEnforcer {
    public Result evaluate(final StaticAnalysisRun run, final QualityGate qualityGate) {
        if (qualityGate.hasFailureTotalThreshold() && run.getTotalSize() >= qualityGate.getFailureTotalThreshold()) {
            return Result.FAILURE;
        }
        else if (qualityGate.hasFailureLowPriorityThreshold() && run.getTotalLowPrioritySize() >= qualityGate.getFailureLowPriorityThreshold()) {
            return Result.FAILURE;
        }
        else if (qualityGate.hasFailureNormalPriorityThreshold() && run.getTotalNormalPrioritySize() >= qualityGate.getFailureNormalPriorityThreshold()) {
            return Result.FAILURE;
        }
        else if (qualityGate.hasFailureHighPriorityThreshold() && run.getTotalHighPrioritySize() >= qualityGate.getFailureHighPriorityThreshold()) {
            return Result.FAILURE;
        }

        else if (qualityGate.hasFailureNewTotalThreshold() && run.getNewSize() >= qualityGate.getFailureNewTotalThreshold()) {
            return Result.FAILURE;
        }
        else if (qualityGate.hasFailureNewLowPriorityThreshold() && run.getNewLowPrioritySize() >= qualityGate.getFailureNewLowPriorityThreshold()) {
            return Result.FAILURE;
        }
        else if (qualityGate.hasFailureNewNormalPriorityThreshold() && run.getNewNormalPrioritySize() >= qualityGate.getFailureNewNormalPriorityThreshold()) {
            return Result.FAILURE;
        }
        else if (qualityGate.hasFailureNewHighPriorityThreshold() && run.getNewHighPrioritySize() >= qualityGate.getFailureNewHighPriorityThreshold()) {
            return Result.FAILURE;
        }

        else if (qualityGate.hasUnstableTotalThreshold() && run.getTotalSize() >= qualityGate.getUnstableTotalThreshold()) {
            return Result.UNSTABLE;
        }
        else if (qualityGate.hasUnstableLowPriorityThreshold() && run.getTotalLowPrioritySize() >= qualityGate.getUnstableLowPriorityThreshold()) {
            return Result.UNSTABLE;
        }
        else if (qualityGate.hasUnstableNormalPriorityThreshold() && run.getTotalNormalPrioritySize() >= qualityGate.getUnstableNormalPriorityThreshold()) {
            return Result.UNSTABLE;
        }
        else if (qualityGate.hasUnstableHighPriorityThreshold() && run.getTotalHighPrioritySize() >= qualityGate.getUnstableHighPriorityThreshold()) {
            return Result.UNSTABLE;
        }

        else if (qualityGate.hasUnstableNewTotalThreshold() && run.getNewSize() >= qualityGate.getUnstableNewTotalThreshold()) {
            return Result.UNSTABLE;
        }
        else if (qualityGate.hasUnstableNewLowPriorityThreshold() && run.getNewLowPrioritySize() >= qualityGate.getUnstableNewLowPriorityThreshold()) {
            return Result.UNSTABLE;
        }
        else if (qualityGate.hasUnstableNewNormalPriorityThreshold() && run.getNewNormalPrioritySize() >= qualityGate.getUnstableNewNormalPriorityThreshold()) {
            return Result.UNSTABLE;
        }
        else if (qualityGate.hasUnstableNewHighPriorityThreshold() && run.getNewHighPrioritySize() >= qualityGate.getUnstableNewHighPriorityThreshold()) {
            return Result.UNSTABLE;
        }
        else {
            return Result.SUCCESS;
        }
    }

}






