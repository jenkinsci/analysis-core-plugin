package io.jenkins.plugins.analysis.core.quality;

import hudson.model.Result;

/**
 * Enforces the defined quality gates.
 *
 * @author Ullrich Hafner
 */
public class QualityGateEnforcer {
    /**
     * Evaluates the specified quality gate for the given run.
     *
     * @param run
     *         the run to evaluate
     * @param qualityGate
     *         the quality gate settings
     *
     * @return the result of the evaluation
     */
    public Result evaluate(final StaticAnalysisRun run, final QualityGate qualityGate) {
        //checking for failure with all issues
        if (qualityGate.hasTotalFailureThreshold() &&
                run.getTotalSize() >= qualityGate.getTotalFailureThreshold()) {
            return Result.FAILURE;
        }
        if (qualityGate.hasHighFailureThreshold() &&
                run.getTotalHighPrioritySize() >= qualityGate.getHighPriorityFailureThreshold()) {
            return Result.FAILURE;
        }
        if (qualityGate.hasNormalFailureThreshold() &&
                run.getTotalNormalPrioritySize() >= qualityGate.getNormalPriorityFailureThreshold()) {
            return Result.FAILURE;
        }
        if (qualityGate.hasLowFailureThreshold() &&
                run.getTotalLowPrioritySize() >= qualityGate.getLowPriorityFailureThreshold()) {
            return Result.FAILURE;
        }

        //checking for unstable build with all issues
        if (qualityGate.hasTotalUnstableThreshold() &&
                run.getTotalSize() >= qualityGate.getTotalUnstableThreshold()) {
            return Result.UNSTABLE;
        }
        if (qualityGate.hasHighUnstableThreshold() &&
                run.getTotalHighPrioritySize() >= qualityGate.getHighPriorityUnstableThreshold()) {
            return Result.UNSTABLE;
        }
        if (qualityGate.hasNormalUnstableThreshold() &&
                run.getTotalNormalPrioritySize() >= qualityGate.getNormalPriorityUnstableThreshold()) {
            return Result.UNSTABLE;
        }

        if (qualityGate.hasLowUnstableThreshold() &&
                run.getTotalLowPrioritySize() >= qualityGate.getLowPriorityUnstableThreshold()) {
            return Result.UNSTABLE;
        }

        //checking for failure with new issues
        if (qualityGate.hasNewTotalFailureThreshold() &&
                run.getNewSize() >= qualityGate.getNewTotalFailureThreshold()) {
            return Result.FAILURE;
        }
        if (qualityGate.hasNewHighFailureThreshold() &&
                run.getNewHighPrioritySize() >= qualityGate.getNewHighPriorityFailureThreshold()) {
            return Result.FAILURE;
        }
        if (qualityGate.hasNewNormalFailureThreshold() &&
                run.getNewNormalPrioritySize() >= qualityGate.getNewNormalPriorityFailureThreshold()) {
            return Result.FAILURE;
        }
        if (qualityGate.hasNewLowFailureThreshold() &&
                run.getNewLowPrioritySize() >= qualityGate.getNewLowPriorityFailureThreshold()) {
            return Result.FAILURE;
        }

        //checking for unstable build with new issues
        if (qualityGate.hasNewTotalUnstableThreshold() &&
                run.getNewSize() >= qualityGate.getNewTotalUnstableThreshold()) {
            return Result.UNSTABLE;
        }
        if (qualityGate.hasNewHighUnstableThreshold() &&
                run.getNewHighPrioritySize() >= qualityGate.getNewHighPriorityUnstableThreshold()) {
            return Result.UNSTABLE;
        }
        if (qualityGate.hasNewNormalUnstableThreshold() &&
                run.getNewNormalPrioritySize() >= qualityGate.getNewNormalPriorityUnstableThreshold()) {
            return Result.UNSTABLE;
        }
        if (qualityGate.hasNewLowUnstableThreshold() &&
                run.getNewLowPrioritySize() >= qualityGate.getNewLowPriorityUnstableThreshold()) {
            return Result.UNSTABLE;
        }

        return Result.SUCCESS;
    }
}
