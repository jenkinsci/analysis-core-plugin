package io.jenkins.plugins.analysis.core.quality;

import hudson.model.Result;

/**
 * Enforces the defined quality gates.
 *
 * @author Ullrich Hafner
 */
public class QualityGateEnforcer {
    /**
     * Enforce that a {@link StaticAnalysisRun run} passes certain {@link QualityGate quality gate}.
     *
     * @param run
     *         the {@link StaticAnalysisRun run} to be validated
     * @param qualityGate
     *         the {@link QualityGate quality gate} against which the {@link StaticAnalysisRun run} should be validated
     *
     * @return the {@link Result result} describing whether the {@link StaticAnalysisRun run} passed the specified
     *         {@link QualityGate quality gate}
     */
    public Result evaluate(final StaticAnalysisRun run, final QualityGate qualityGate) {
        return qualityGate.getThresholds()
                .stream()
                .map(threshold -> evaluate(run, threshold))
                .reduce(Result.SUCCESS, Result::combine);
    }

    /**
     * Evaluate if a {@link StaticAnalysisRun run} passes a specific {@link IssueCountThreshold threshold}.
     *
     * @param run
     *         the {@link StaticAnalysisRun run} to be validated
     * @param threshold
     *         the {@link IssueCountThreshold threshold} against which the {@link StaticAnalysisRun run} should be
     *         validated
     *
     * @return the {@link Result result} describing whether the {@link StaticAnalysisRun run} passed the specified
     *         {@link IssueCountThreshold threshold}
     */
    private Result evaluate(final StaticAnalysisRun run, final IssueCountThreshold threshold) {
        int issueCount = getIssueCount(run, threshold);
        if (issueCount > threshold.getValue()) {
            return threshold.getResult();
        }
        return Result.SUCCESS;
    }

    /**
     * Determines the number of issues of a {@link StaticAnalysisRun run} that is used for validating against a certain
     * {@link IssueCountThreshold}.
     * <p>
     * Possible types of thresholds are possible: <ul> <li>threshold for the <strong>total</strong> number of issues of
     * <strong>all</strong> priorities</li> <li>threshold for the <strong>total</strong> number of issues of <strong>a
     * certain</strong> priorities</li> <li>threshold for the number of <strong>new</strong> issues of
     * <strong>all</strong> priorities</li> <li>threshold for the number of <strong>new</strong> issues of <strong>a
     * certain</strong> priorities</li> </ul>
     */
    private int getIssueCount(final StaticAnalysisRun run, final IssueCountThreshold threshold) {
        if (threshold instanceof PriorityThreshold) {
            return getIssueCount(run, (PriorityThreshold) threshold);
        }
        return getIssueCount(run, (AllPrioritiesThreshold) threshold);
    }

    private int getIssueCount(final StaticAnalysisRun run, final PriorityThreshold threshold) {
        return threshold.isOnlyForNewIssues()
                ? run.getNewSize(threshold.getPriority())
                : run.getTotalSize(threshold.getPriority());
    }

    private int getIssueCount(final StaticAnalysisRun run, final AllPrioritiesThreshold threshold) {
        return threshold.isOnlyForNewIssues()
                ? run.getNewSize()
                : run.getTotalSize();
    }
}
