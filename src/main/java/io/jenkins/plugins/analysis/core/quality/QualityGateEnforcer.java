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
        if (qualityGate.hasTotalFailedAllPriorities()) {
            if (run.getTotalSize() >= qualityGate.getTotalFailedAllPriorities()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.hasTotalFailedHighPriority()) {
            if (run.getTotalSize() >= qualityGate.getTotalFailedHighPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.hasTotalFailedNormalPriority()) {
            if (run.getTotalSize() >= qualityGate.getTotalFailedNormalPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.hasTotalFailedLowPriority()) {
            if (run.getTotalSize() >= qualityGate.getTotalFailedLowPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.hasTotalUnstableAllPriorities()) {
            if (run.getTotalSize() >= qualityGate.getTotalUnstableAllPriorities()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.hasTotalUnstableHighPriority()) {
            if (run.getTotalSize() >= qualityGate.getTotalUnstableHighPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.hasTotalUnstableNormalPriority()) {
            if (run.getTotalSize() >= qualityGate.getTotalUnstableNormalPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.hasTotalUnstableLowPriority()) {
            if (run.getTotalSize() >= qualityGate.getTotalUnstableLowPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.hasNewUnstableAllPriorities()) {
            if (run.getTotalSize() >= qualityGate.getNewUnstableAllPriorities()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.hasNewUnstableHighPriority()) {
            if (run.getTotalSize() >= qualityGate.getNewUnstableHighPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.hasNewUnstableNormalPriority()) {
            if (run.getTotalSize() >= qualityGate.getNewUnstableNormalPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.hasNewUnstableLowPriority()) {
            if (run.getTotalSize() >= qualityGate.getNewUnstableLowPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.hasNewFailedAllPriorities()) {
            if (run.getTotalSize() >= qualityGate.getNewFailedAllPriorities()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.hasNewFailedHighPriority()) {
            if (run.getTotalSize() >= qualityGate.getNewFailedHighPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.hasNewFailedNormalPriority()) {
            if (run.getTotalSize() >= qualityGate.getNewFailedNormalPriority()) {
                return Result.FAILURE;
            }
        }
        else if (qualityGate.hasNewFailedLowPriority()) {
            if (run.getTotalSize() >= qualityGate.getNewFailedLowPriority()) {
                return Result.FAILURE;
            }
        }
        return Result.SUCCESS;
    }
}
