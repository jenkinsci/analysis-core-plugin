package io.jenkins.plugins.analysis.core.quality;

import hudson.model.Result;

/**
 * Checks, if a StaticAnalysisRun is within the configuered QualityGates. Otherwise the Build result is set to u stable
 * or failed.
 */
public class QualityGateEnforcer {

    private final QualityGateConfig config;

    /**
     * Ctor, that initializes with a final config.
     *
     * @param config
     *         configurates the quality boundaries
     */
    public QualityGateEnforcer(QualityGateConfig config) {
        this.config = config;
    }

    /**
     * Evaluates, if the issue count of the given run is within the quality gates.
     *
     * @param run
     *         the run to be evaluated.
     *
     * @return the build result. Possible values are SUCCESS, UNSTABLE and FAILUR
     */
    public Result evaluate(final StaticAnalysisRun run) {
        if (isBuildFailure(run)) {
            return Result.FAILURE;
        }
        if (isBuildUnstable(run)) {
            return Result.UNSTABLE;
        }
        return Result.SUCCESS;
    }

    private boolean isBuildFailure(final StaticAnalysisRun run) {
        boolean totalAbove = isAboveThreshold(config.getFailureThresholdAllPrios(), run.getTotalSize());
        boolean highAbove = isAboveThreshold(config.getFailureThresholdHighPrio(), run.getTotalHighPrioritySize());
        boolean normalAbove = isAboveThreshold(config.getFailureThresholdNormalPrio(), run.getTotalNormalPrioritySize());
        boolean lowAbove = isAboveThreshold(config.getFailureThresholdLowPrio(), run.getTotalLowPrioritySize());

        boolean totalNewAbove = isAboveThreshold(config.getNewFailureThresholdAllPrios(), run.getNewSize());
        boolean highNewAbove = isAboveThreshold(config.getNewFailureThresholdHighPrio(), run.getNewHighPrioritySize());
        boolean normalNewAbove = isAboveThreshold(config.getNewFailureThresholdNormalPrio(), run.getNewNormalPrioritySize());
        boolean lowNewAbove = isAboveThreshold(config.getNewFailureThresholdLowPrio(), run.getNewLowPrioritySize());
        return totalAbove || highAbove || normalAbove || lowAbove || totalNewAbove || highNewAbove || normalNewAbove || lowNewAbove;
    }

    private boolean isBuildUnstable(final StaticAnalysisRun run) {
        boolean totalAbove = isAboveThreshold(config.getWarningThresholdAllPrios(), run.getTotalSize());
        boolean highAbove = isAboveThreshold(config.getWarningThresholdHighPrio(), run.getTotalHighPrioritySize());
        boolean normalAbove = isAboveThreshold(config.getWarningThresholdNormalPrio(), run.getTotalNormalPrioritySize());
        boolean lowAbove = isAboveThreshold(config.getWarningThresholdLowPrio(), run.getTotalLowPrioritySize());

        boolean totalNewAbove = isAboveThreshold(config.getNewWarningThresholdAllPrios(), run.getNewSize());
        boolean highNewAbove = isAboveThreshold(config.getNewWarningThresholdHighPrio(), run.getNewHighPrioritySize());
        boolean normalNewAbove = isAboveThreshold(config.getNewWarningThresholdNormalPrio(), run.getNewNormalPrioritySize());
        boolean lowNewAbove = isAboveThreshold(config.getNewWarningThresholdLowPrio(), run.getNewLowPrioritySize());
        return totalAbove || highAbove || normalAbove || lowAbove || totalNewAbove || highNewAbove || normalNewAbove || lowNewAbove;
    }

    private boolean isAboveThreshold(final int threshold, final int sizeToCheck) {
        if (threshold >= 0) {
            if (sizeToCheck >= threshold) {
                return true;
            }
        }
        return false;
    }
}
