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

        List<Boolean> failures = new ArrayList<>();
        List<Boolean> unstables = new ArrayList<>();

        unstables.add(qualityGate.hasUnstableThreshold() && run.getTotalSize() >= qualityGate.getUnstableThreshold());
        unstables.add(qualityGate.shouldCheckNewIssuesForUnstable() && run.getNewSize() >= qualityGate.getNewIssuesUnstableThreshold());
        unstables.add(qualityGate.hasLowPriorityUnstableThreshold() && run.getTotalLowPrioritySize() >= qualityGate.getUnstableThresholdLow());
        unstables.add(qualityGate.hasNormalPriorityUnstableThreshold() && run.getTotalNormalPrioritySize() >= qualityGate.getUnstableThresholdNormal());
        unstables.add(qualityGate.hasHighPriorityUnstableThreshold() && run.getTotalHighPrioritySize() >= qualityGate.getUnstableThresholdHigh());
        unstables.add(qualityGate.hasNewLowIssuesPriorityUnstableThreshold() && run.getNewLowPrioritySize() >= qualityGate.getNewIssuesUnstableThresholdLow());
        unstables.add(qualityGate.hasNewNormalIssuesPriorityUnstableThreshold() && run.getNewNormalPrioritySize() >= qualityGate.getNewIssuesUnstableThresholdNormal());
        unstables.add(qualityGate.hasNewHighIssuesPriorityUnstableThreshold() && run.getNewHighPrioritySize() >= qualityGate.getNewIssuesUnstableThresholdHigh());

        failures.add(qualityGate.hasFailureThreshold() && run.getTotalSize() >= qualityGate.getFailureThreshold());
        failures.add(qualityGate.shouldCheckNewIssuesForFailure() && run.getNewSize() >= qualityGate.getNewIssuesFailureThreshold());
        failures.add(qualityGate.hasLowPriorityFailureThreshold() && run.getTotalLowPrioritySize() >= qualityGate.getFailureThresholdLow());
        failures.add(qualityGate.hasNormalPriorityFailureThreshold() && run.getTotalNormalPrioritySize() >= qualityGate.getFailureThresholdNormal());
        failures.add(qualityGate.hasHighPriorityFailureThreshold() && run.getTotalHighPrioritySize() >= qualityGate.getFailureThresholdHigh());
        failures.add(qualityGate.hasNewLowIssuesPriorityFailureThreshold() && run.getNewLowPrioritySize() >= qualityGate.getNewIssuesFailureThresholdLow());
        failures.add(qualityGate.hasNewNormalIssuesPriorityFailureThreshold() && run.getNewNormalPrioritySize() >= qualityGate.getNewIssuesFailureThresholdNormal());
        failures.add(qualityGate.hasNewHighIssuesPriorityFailureThreshold() && run.getNewHighPrioritySize() >= qualityGate.getNewIssuesFailureThresholdHigh());

        for (Boolean unstable : unstables) {
            if (unstable) {
                result = Result.UNSTABLE;
                break;
            }
        }

        for (Boolean failure : failures) {
            if (failure) {
                result = Result.FAILURE;
                break;
            }
        }

        return result;

        //return null;
    }
}
