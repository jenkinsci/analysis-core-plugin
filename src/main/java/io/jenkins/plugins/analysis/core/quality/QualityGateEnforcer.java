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
        if (qualityGate.isTotalFailedAllPriorities()) {
            if (run.getTotalSize() >= qualityGate.getTotalFailedAllPriorities()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.isTotalFailedHighPriority()) {
            if (run.getTotalSize() >= qualityGate.getTotalFailedHighPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.isTotalFailedNormalPriority()) {
            if (run.getTotalSize() >= qualityGate.getTotalFailedNormalPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.isTotalFailedLowPriority()) {
            if (run.getTotalSize() >= qualityGate.getTotalFailedLowPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.isTotalUnstableAllPriorities()) {
            if (run.getTotalSize() >= qualityGate.getTotalUnstableAllPriorities()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.isTotalUnstableHighPriority()) {
            if (run.getTotalSize() >= qualityGate.getTotalUnstableHighPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.isTotalUnstableNormalPriority()) {
            if (run.getTotalSize() >= qualityGate.getTotalUnstableNormalPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.isTotalUnstableLowPriority()) {
            if (run.getTotalSize() >= qualityGate.getTotalUnstableLowPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.isNewUnstableAllPriorities()) {
            if (run.getTotalSize() >= qualityGate.getNewUnstableAllPriorities()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.isNewUnstableHighPriority()) {
            if (run.getTotalSize() >= qualityGate.getNewUnstableHighPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.isNewUnstableNormalPriority()) {
            if (run.getTotalSize() >= qualityGate.getNewUnstableNormalPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.isNewUnstableLowPriority()) {
            if (run.getTotalSize() >= qualityGate.getNewUnstableLowPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.isNewFailedAllPriorities()) {
            if (run.getTotalSize() >= qualityGate.getNewFailedAllPriorities()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.isNewFailedHighPriority()) {
            if (run.getTotalSize() >= qualityGate.getNewFailedHighPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.isNewFailedNormalPriority()) {
            if (run.getTotalSize() >= qualityGate.getNewFailedNormalPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.isNewFailedLowPriority()) {
            if (run.getTotalSize() >= qualityGate.getNewFailedLowPriority()) {
                return Result.FAILURE;
            }
        }
        return Result.SUCCESS;
    }
}
