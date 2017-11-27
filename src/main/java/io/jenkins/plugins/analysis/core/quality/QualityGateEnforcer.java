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
     * @param run         the run to evaluate
     * @param qualityGate the quality gate settings
     * @return the result of the evaluation
     */
    public Result evaluate(final StaticAnalysisRun run, final QualityGate qualityGate) {
        if (qualityGate.hasFailureThreshold() &&
                run.getTotalSize() >= qualityGate.getFailureThreshold()) {
            return Result.FAILURE;
        }

        if (qualityGate.hasHighFailureThreshold() &&
                run.getTotalHighPrioritySize() >= qualityGate.getHighFailureThreshold()) {
            return Result.FAILURE;
        }

        if (qualityGate.hasNormalFailureThreshold() &&
                run.getTotalNormalPrioritySize() >= qualityGate.getNormalFailureThreshold()) {
            return Result.FAILURE;
        }

        if (qualityGate.hasLowFailureThreshold() &&
                run.getTotalLowPrioritySize() >= qualityGate.getLowFailureThreshold()) {
            return Result.FAILURE;
        }

        if (qualityGate.hasUnstableThreshold() &&
                run.getTotalSize() >= qualityGate.getUnstableThreshold()) {
            return Result.UNSTABLE;
        }

        if (qualityGate.hasHighUnstableThreshold() &&
                run.getTotalHighPrioritySize() >= qualityGate.getHighUnstableThreshold()) {
            return Result.UNSTABLE;
        }

        if (qualityGate.hasNormalUnstableThreshold() &&
                run.getTotalNormalPrioritySize() >= qualityGate.getNormalUnstableThreshold()) {
            return Result.UNSTABLE;
        }

        if (qualityGate.hasLowUnstableThreshold() &&
                run.getTotalLowPrioritySize() >= qualityGate.getLowUnstableThreshold()) {
            return Result.UNSTABLE;
        }

        if (qualityGate.hasNewFailureThreshold() &&
                run.getNewSize() >= qualityGate.getNewFailureThreshold()) {
            return Result.FAILURE;
        }

        if (qualityGate.hasNewHighFailureThreshold() &&
                run.getNewHighPrioritySize() >= qualityGate.getNewHighFailureThreshold()) {
            return Result.FAILURE;
        }

        if (qualityGate.hasNewNormalFailureThreshold() &&
                run.getNewNormalPrioritySize() >= qualityGate.getNewNormalFailureThreshold()) {
            return Result.FAILURE;
        }

        if (qualityGate.hasNewLowFailureThreshold() &&
                run.getNewLowPrioritySize() >= qualityGate.getNewLowFailureThreshold()) {
            return Result.FAILURE;
        }

        if (qualityGate.hasNewUnstableThreshold() &&
                run.getNewSize() >= qualityGate.getNewUnstableThreshold()) {
            return Result.UNSTABLE;
        }

        if (qualityGate.hasNewHighUnstableThreshold() &&
                run.getNewHighPrioritySize() >= qualityGate.getNewHighUnstableThreshold()) {
            return Result.UNSTABLE;
        }

        if (qualityGate.hasNewNormalUnstableThreshold() &&
                run.getNewNormalPrioritySize() >= qualityGate.getNewNormalUnstableThreshold()) {
            return Result.UNSTABLE;
        }

        if (qualityGate.hasNewLowUnstableThreshold() &&
                run.getNewLowPrioritySize() >= qualityGate.getNewLowUnstableThreshold()) {
            return Result.UNSTABLE;
        }

        return Result.SUCCESS;
    }
}
