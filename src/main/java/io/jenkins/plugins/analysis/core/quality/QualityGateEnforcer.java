package io.jenkins.plugins.analysis.core.quality;

import hudson.model.Result;

/**
 * Enforces the defined quality gates.
 *
 * @author Joscha Behrmann
 */
public class QualityGateEnforcer {

    /**
     * Evaluates the quality of the run. If a threshold for a priority is matched by
     * both values for 'Failure' and 'Unstable', {@link Result#FAILURE} will be returned.
     *
     * @param run the {@link StaticAnalysisRun} to evaluate
     * @param qualityGate the {@link QualityGate} to evaluate by
     * @return {@link Result#FAILURE} if the run exceeded the qualityGates 'failure' criteria
     *         {@link Result#UNSTABLE} if the run exceeded the the qualityGates 'unstable' criteria
     *         {@link Result#SUCCESS} if the run passed the qualityGate
     */
    public Result evaluate(final StaticAnalysisRun run, final QualityGate qualityGate) {
        if (run.getTotalSize() > qualityGate.getTotalPriorityAllFailed()
                || run.getTotalHighPrioritySize() > qualityGate.getTotalPriorityHighFailed()
                || run.getTotalNormalPrioritySize() > qualityGate.getTotalPriorityNormalFailed()
                || run.getTotalLowPrioritySize() > qualityGate.getTotalPriorityLowFailed()) {
            return Result.FAILURE;
        }

        if (run.getTotalSize() > qualityGate.getTotalPriorityAllUnstable()
                || run.getTotalHighPrioritySize() > qualityGate.getTotalPriorityHighUnstable()
                || run.getTotalNormalPrioritySize() > qualityGate.getTotalPriorityNormalUnstable()
                || run.getTotalLowPrioritySize() > qualityGate.getTotalPriorityLowUnstable()) {
            return Result.UNSTABLE;
        }

        if (qualityGate.shouldComputeNewWarnings()) {
            if (run.getNewSize() > qualityGate.getNewPriorityAllFailed()
                    || run.getNewHighPrioritySize() > qualityGate.getNewPriorityHighFailed()
                    || run.getNewNormalPrioritySize() > qualityGate.getNewPriorityNormalFailed()
                    || run.getNewLowPrioritySize() > qualityGate.getNewPriorityLowFailed()) {
                return Result.FAILURE;
            }

            if (run.getNewSize() > qualityGate.getNewPriorityAllUnstable()
                    || run.getNewHighPrioritySize() > qualityGate.getNewPriorityHighUnstable()
                    || run.getNewNormalPrioritySize() > qualityGate.getNewPriorityNormalUnstable()
                    || run.getNewLowPrioritySize() > qualityGate.getNewPriorityLowUnstable()) {
                return Result.UNSTABLE;
            }
        }

        return Result.SUCCESS;
    }
}
