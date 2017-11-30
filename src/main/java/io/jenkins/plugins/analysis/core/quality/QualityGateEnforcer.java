package io.jenkins.plugins.analysis.core.quality;

import java.util.ArrayList;
import java.util.List;

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
        Result result = Result.SUCCESS;

        List<Boolean> failureFlags = new ArrayList<>();
        List<Boolean> unstableFlags = new ArrayList<>();

        unstableFlags.add(qualityGate.hasUnstableThreshold() && run.getTotalSize() >= qualityGate.getUnstableThreshold());
        unstableFlags.add(qualityGate.shouldCheckNewIssuesForUnstable() && run.getNewSize() >= qualityGate.getNewIssuesUnstableThreshold());
        unstableFlags.add(qualityGate.hasLowPriorityUnstableThreshold() && run.getTotalLowPrioritySize() >= qualityGate.getUnstableThresholdLow());
        unstableFlags.add(qualityGate.hasNormalPriorityUnstableThreshold() && run.getTotalNormalPrioritySize() >= qualityGate.getUnstableThresholdNormal());
        unstableFlags.add(qualityGate.hasHighPriorityUnstableThreshold() && run.getTotalHighPrioritySize() >= qualityGate.getUnstableThresholdHigh());
        unstableFlags.add(qualityGate.hasNewLowIssuesPriorityUnstableThreshold() && run.getNewLowPrioritySize() >= qualityGate.getNewIssuesUnstableThresholdLow());
        unstableFlags.add(qualityGate.hasNewNormalIssuesPriorityUnstableThreshold() && run.getNewNormalPrioritySize() >= qualityGate.getNewIssuesUnstableThresholdNormal());
        unstableFlags.add(qualityGate.hasNewHighIssuesPriorityUnstableThreshold() && run.getNewHighPrioritySize() >= qualityGate.getNewIssuesUnstableThresholdHigh());

        failureFlags.add(qualityGate.hasFailureThreshold() && run.getTotalSize() >= qualityGate.getFailureThreshold());
        failureFlags.add(qualityGate.shouldCheckNewIssuesForFailure() && run.getNewSize() >= qualityGate.getNewIssuesFailureThreshold());
        failureFlags.add(qualityGate.hasLowPriorityFailureThreshold() && run.getTotalLowPrioritySize() >= qualityGate.getFailureThresholdLow());
        failureFlags.add(qualityGate.hasNormalPriorityFailureThreshold() && run.getTotalNormalPrioritySize() >= qualityGate.getFailureThresholdNormal());
        failureFlags.add(qualityGate.hasHighPriorityFailureThreshold() && run.getTotalHighPrioritySize() >= qualityGate.getFailureThresholdHigh());
        failureFlags.add(qualityGate.hasNewLowIssuesPriorityFailureThreshold() && run.getNewLowPrioritySize() >= qualityGate.getNewIssuesFailureThresholdLow());
        failureFlags.add(qualityGate.hasNewNormalIssuesPriorityFailureThreshold() && run.getNewNormalPrioritySize() >= qualityGate.getNewIssuesFailureThresholdNormal());
        failureFlags.add(qualityGate.hasNewHighIssuesPriorityFailureThreshold() && run.getNewHighPrioritySize() >= qualityGate.getNewIssuesFailureThresholdHigh());

        for (Boolean unstable : unstableFlags) {
            if (unstable) {
                result = Result.UNSTABLE;
                break;
            }
        }

        for (Boolean failure : failureFlags) {
            if (failure) {
                result = Result.FAILURE;
                break;
            }
        }

        return result;
    }
}
