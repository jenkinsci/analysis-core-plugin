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
        int highFail = qualityGate.getHighPriorityFailure();;
        if (highFail >0 && run.getTotalHighPrioritySize() >= highFail) {
            return Result.FAILURE;
        }
        int highUnstab = qualityGate.getHighPriorityUnstable();
        if (highUnstab>0 && run.getTotalHighPrioritySize() >= highUnstab){
            return Result.UNSTABLE;
        }
        int normFail = qualityGate.getNormalPriorityFailure();
        if (normFail>0 && run.getTotalNormalPrioritySize() >= normFail){
            return Result.FAILURE;
        }
        int normUnstab = qualityGate.getNormalPriorityUnstable();
        if (normUnstab>0 && run.getTotalNormalPrioritySize() >= normUnstab){
            return Result.UNSTABLE;
        }
        int lowFail = qualityGate.getLowPriorityFailure();
        if (lowFail>0 && run.getTotalLowPrioritySize() >= lowFail){
            return Result.FAILURE;
        }
        int lowUnstab = qualityGate.getLowPriorityUnstable();
        if (lowUnstab>0 && run.getTotalLowPrioritySize() >= lowUnstab){
            return Result.UNSTABLE;
        }

        return Result.SUCCESS;
    }
}
