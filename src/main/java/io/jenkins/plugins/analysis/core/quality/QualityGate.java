package io.jenkins.plugins.analysis.core.quality;

import hudson.model.Result;

/**
 * Defines quality gates for a static analysis run.
 *
 * @author Benedikt Neuner
 */
public class QualityGate {

    private final TresholdSet failureTresholdSet;
    private final TresholdSet unstableTresholdSet;

    /**
     * Public constructor. Use {@link QualityGateBuilder} instead.
     */
    QualityGate(TresholdSet failureTresholdSet, TresholdSet unstableTresholdSet) {
        this.failureTresholdSet = failureTresholdSet;
        this.unstableTresholdSet = unstableTresholdSet;
    }

    /**
     * Evaluate the given run against this quality gate.
     *
     * @param run
     *         to evaluate against.
     *
     * @return {@link Result#SUCCESS} if no tresholds were exceeded otherwise {@link Result#FAILURE}
     */
    public Result evaluate(StaticAnalysisRun run) {
        if (failureTresholdSet.evalutate(run) != Result.SUCCESS) {
            return Result.FAILURE;
        }
        else if (unstableTresholdSet.evalutate(run) != Result.SUCCESS) {
            return Result.UNSTABLE;
        }
        return Result.SUCCESS;
    }

}
