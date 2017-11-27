package io.jenkins.plugins.analysis.core.quality;

import hudson.model.Result;

/**
 * Enforces the defined quality gates.
 *
 * @author Ullrich Hafner
 */
public class QualityGateEnforcer {
    public Result evaluate(final StaticAnalysisRun run, final QualityGate qualityGate) {
        if (qualityGate.hasFailureTotalThreshold()) {
            if (run.getTotalHighPrioritySize() >= qualityGate.getFailureTotalThreshold()) {
                return Result.FAILURE;
            }
        }
        return Result.SUCCESS;
    }
}
