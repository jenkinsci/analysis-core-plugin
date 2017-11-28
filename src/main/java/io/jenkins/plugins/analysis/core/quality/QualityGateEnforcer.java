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
        if (checkForFailureThresholds(run, qualityGate)) {
            return Result.FAILURE;
        }

        if (checkForUnstableThresholds(run, qualityGate)) {
            return Result.UNSTABLE;
        }

        if (checkForNewFailureThresholds(run, qualityGate)) {
            return Result.FAILURE;
        }

        if (checkForNewUnstableThresholds(run, qualityGate)) {
            return Result.UNSTABLE;
        }

        return Result.SUCCESS;
    }

    private boolean checkForFailureThresholds(StaticAnalysisRun run, QualityGate qualityGate) {
        if (qualityGate.hasFailureThreshold()
                && run.getTotalSize() >= qualityGate.getFailureThreshold()) {
            return true;
        }

        if (qualityGate.hasHighFailureThreshold()
                && run.getTotalHighPrioritySize() >= qualityGate.getHighFailureThreshold()) {
            return true;
        }

        if (qualityGate.hasNormalFailureThreshold()
                && run.getTotalNormalPrioritySize() >= qualityGate.getNormalFailureThreshold()) {
            return true;
        }

        return qualityGate.hasLowFailureThreshold()
                && run.getTotalLowPrioritySize() >= qualityGate.getLowFailureThreshold();
    }

    private boolean checkForNewFailureThresholds(StaticAnalysisRun run, QualityGate qualityGate) {
        if (qualityGate.hasNewFailureThreshold()
                && run.getNewSize() >= qualityGate.getNewFailureThreshold()) {
            return true;
        }

        if (qualityGate.hasNewHighFailureThreshold()
                && run.getNewHighPrioritySize() >= qualityGate.getNewHighFailureThreshold()) {
            return true;
        }

        if (qualityGate.hasNewNormalFailureThreshold()
                && run.getNewNormalPrioritySize() >= qualityGate.getNewNormalFailureThreshold()) {
            return true;
        }

        return qualityGate.hasNewLowFailureThreshold()
                && run.getNewLowPrioritySize() >= qualityGate.getNewLowFailureThreshold();
    }

    private boolean checkForUnstableThresholds(StaticAnalysisRun run, QualityGate qualityGate) {
        if (qualityGate.hasUnstableThreshold()
                && run.getTotalSize() >= qualityGate.getUnstableThreshold()) {
            return true;
        }

        if (qualityGate.hasHighUnstableThreshold()
                && run.getTotalHighPrioritySize() >= qualityGate.getHighUnstableThreshold()) {
            return true;
        }

        if (qualityGate.hasNormalUnstableThreshold()
                && run.getTotalNormalPrioritySize() >= qualityGate.getNormalUnstableThreshold()) {
            return true;
        }

        return qualityGate.hasLowUnstableThreshold()
                && run.getTotalLowPrioritySize() >= qualityGate.getLowUnstableThreshold();
    }

    private boolean checkForNewUnstableThresholds(StaticAnalysisRun run, QualityGate qualityGate) {
        if (qualityGate.hasNewUnstableThreshold()
                && run.getNewSize() >= qualityGate.getNewUnstableThreshold()) {
            return true;
        }

        if (qualityGate.hasNewHighUnstableThreshold()
                && run.getNewHighPrioritySize() >= qualityGate.getNewHighUnstableThreshold()) {
            return true;
        }

        if (qualityGate.hasNewNormalUnstableThreshold()
                && run.getNewNormalPrioritySize() >= qualityGate.getNewNormalUnstableThreshold()) {
            return true;
        }

        return qualityGate.hasNewLowUnstableThreshold()
                && run.getNewLowPrioritySize() >= qualityGate.getNewLowUnstableThreshold();
    }
}
