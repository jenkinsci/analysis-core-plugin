package io.jenkins.plugins.analysis.core.quality;

import hudson.model.Result;

/**
 * Defines quality gates for a static analysis run.
 *
 * @author Ullrich Hafner
 */
public class QualityGate {
    private final int failureThreshold;
    boolean checkJustNew;
    hudson.model.Result ifFail;
    QualityGatePriority inspect;

    /**
     * Creates a new instance of {@link QualityGate}.
     *
     * @param failureThreshold
     *         the number of issues that will fail a build
     * @param ifFail
     *         define the Result of evaluate in case of a failed run
     * @param inspect
     *         define the priories witch get inspected
     * @param checkNew
     *         check just the new Issues
     */
    public QualityGate(final int failureThreshold, Result ifFail, QualityGatePriority inspect, Boolean checkNew) {
        this.inspect = inspect;
        this.failureThreshold = failureThreshold;
        this.ifFail = ifFail;
        this.checkJustNew = checkNew;
    }

    /**
     * Creates a new instance of {@link QualityGate}. No thresholds are set.
     */
    public QualityGate() {
        this(0, Result.FAILURE, QualityGatePriority.All, false);
    }

    /**
     * Determines if a failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the failure threshold for the total number of issues is set
     */
    public boolean hasFailureThreshold() {
        return failureThreshold > 0;
    }

    /**
     * Returns the failure threshold for the total number of issues.
     *
     * @return the failure threshold for the total number of issues
     */
    public int getFailureThreshold() {
        return failureThreshold;
    }


    /**
     * Evaluates the specified quality gate for the given run.
     *
     * @param run
     *         the run to evaluate
     *
     * @return the result of the evaluation
     */

    // Failed Success Unstable
    public Result evaluate(final StaticAnalysisRun run) {
        Boolean hasFailed = false;
        if (hasFailureThreshold()) {
            if (checkJustNew) {
                switch (inspect) {
                    case All:
                        hasFailed = run.getNewSize() >= getFailureThreshold();
                        break;
                    case High:
                        hasFailed = run.getNewHighPrioritySize() >= getFailureThreshold();
                        break;
                    case Normal:
                        hasFailed = run.getNewNormalPrioritySize() >= getFailureThreshold();
                        break;
                    case Low:
                        hasFailed = run.getNewLowPrioritySize() >= getFailureThreshold();
                        break;
                }
            }
            else {
                switch (inspect) {
                    case All:
                        hasFailed = run.getTotalSize() >= getFailureThreshold();
                        break;
                    case High:
                        hasFailed = run.getTotalHighPrioritySize() >= getFailureThreshold();
                        break;
                    case Normal:
                        hasFailed = run.getTotalNormalPrioritySize() >= getFailureThreshold();
                        break;
                    case Low:
                        hasFailed = run.getTotalLowPrioritySize() >= getFailureThreshold();
                        break;
                }
            }
        }


        if (!hasFailed) {
            return Result.SUCCESS;
        }
        else {
            return ifFail;
        }
    }
}
