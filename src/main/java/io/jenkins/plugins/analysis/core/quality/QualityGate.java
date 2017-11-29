package io.jenkins.plugins.analysis.core.quality;

/**
 * Defines quality gates for a static analysis run.
 *
 * @author Ullrich Hafner
 */
public class QualityGate {
    private final int failureThreshold;
    private final int newIssuesFailureThreshold;

    private final int unstableThreshold;
    private final int newIssuesUnstableThreshold;

    private final int failureThresholdLow;
    private final int failureThresholdNormal;
    private final int failureThresholdHigh;
    private final int newIssuesFailureThresholdLow;
    private final int newIssuesFailureThresholdNormal;
    private final int newIssuesFailureThresholdHigh;

    private final int unstableThresholdLow;
    private final int unstableThresholdNormal;
    private final int unstableThresholdHigh;
    private final int newIssuesUnstableThresholdLow;
    private final int newIssuesUnstableThresholdNormal;
    private final int newIssuesUnstableThresholdHigh;

    private final boolean checkNewIssues;

    @SuppressWarnings("ParameterNumber")
    QualityGate(final int failureThreshold, final int newIssuesFailureThreshold, final int unstableThreshold,
            final int newIssuesUnstableThreshold, final int failureThresholdLow, final int failureThresholdNormal,
            final int failureThresholdHigh, final int newIssuesFailureThresholdLow,
            final int newIssuesFailureThresholdNormal, final int newIssuesFailureThresholdHigh,
            final int unstableThresholdLow, final int unstableThresholdNormal, final int unstableThresholdHigh,
            final int newIssuesUnstableThresholdLow, final int newIssuesUnstableThresholdNormal,
            final int newIssuesUnstableThresholdHigh, final boolean checkNewIssues) {
        this.failureThreshold = failureThreshold;
        this.newIssuesFailureThreshold = newIssuesFailureThreshold;
        this.unstableThreshold = unstableThreshold;
        this.newIssuesUnstableThreshold = newIssuesUnstableThreshold;
        this.failureThresholdLow = failureThresholdLow;
        this.failureThresholdNormal = failureThresholdNormal;
        this.failureThresholdHigh = failureThresholdHigh;
        this.newIssuesFailureThresholdLow = newIssuesFailureThresholdLow;
        this.newIssuesFailureThresholdNormal = newIssuesFailureThresholdNormal;
        this.newIssuesFailureThresholdHigh = newIssuesFailureThresholdHigh;
        this.unstableThresholdLow = unstableThresholdLow;
        this.unstableThresholdNormal = unstableThresholdNormal;
        this.unstableThresholdHigh = unstableThresholdHigh;
        this.newIssuesUnstableThresholdLow = newIssuesUnstableThresholdLow;
        this.newIssuesUnstableThresholdNormal = newIssuesUnstableThresholdNormal;
        this.newIssuesUnstableThresholdHigh = newIssuesUnstableThresholdHigh;
        this.checkNewIssues = checkNewIssues;
    }

    /**
     * Determines if a failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the failure threshold for the total number of issues is set
     */
    public boolean hasFailureThreshold() {
        return failureThreshold > 0;
    }

    public boolean hasUnstableThreshold() {
        return unstableThreshold > 0;
    }

    /**
     * Determines if new issues size should be checked and a new issues threshold is set.
     *
     * @return {@code true} if the failure threshold for number of new issues is set and the checkNewIssues flag is
     *         set.
     */
    public boolean shouldCheckNewIssuesForFailure() {
        return checkNewIssues && newIssuesFailureThreshold > 0;
    }

    public boolean shouldCheckNewIssuesForUnstable() {
        return checkNewIssues && newIssuesUnstableThreshold > 0;
    }

    /**
     * Determines if a failure threshold for the total number of low priority issues is set.
     *
     * @return {@code true} if the failure threshold for the total number of low priority issues is set
     */
    public boolean hasLowPriorityFailureThreshold() {
        return failureThresholdLow > 0;
    }

    public boolean hasLowPriorityUnstableThreshold() {
        return unstableThresholdLow > 0;
    }

    /**
     * Determines if a failure threshold for the total number of normal priority issues is set.
     *
     * @return {@code true} if the failure threshold for the total number of normal priority issues is set
     */
    public boolean hasNormalPriorityFailureThreshold() {
        return failureThresholdNormal > 0;
    }

    public boolean hasNormalPriorityUnstableThreshold() {
        return unstableThresholdNormal > 0;
    }

    /**
     * Determines if a failure threshold for the total number of high priority issues is set.
     *
     * @return {@code true} if the failure threshold for the total number of high priority issues is set
     */
    public boolean hasHighPriorityFailureThreshold() {
        return failureThresholdHigh > 0;
    }

    public boolean hasHighPriorityUnstableThreshold() {
        return unstableThresholdHigh > 0;
    }

    /**
     * Determines if a failure threshold for the number of new low priority issues is set.
     *
     * @return {@code true} if the failure threshold for the number of new low priority issues is set
     */
    public boolean hasNewLowIssuesPriorityFailureThreshold() {
        return newIssuesFailureThresholdLow > 0 && checkNewIssues;
    }

    public boolean hasNewLowIssuesPriorityUnstableThreshold() {
        return newIssuesUnstableThresholdLow > 0 && checkNewIssues;
    }

    /**
     * Determines if a failure threshold for the number of new normal priority issues is set.
     *
     * @return {@code true} if the failure threshold for the number of new normal priority issues is set
     */
    public boolean hasNewNormalIssuesPriorityFailureThreshold() {
        return newIssuesFailureThresholdNormal > 0 && checkNewIssues;
    }

    public boolean hasNewNormalIssuesPriorityUnstableThreshold() {
        return newIssuesUnstableThresholdNormal > 0 && checkNewIssues;
    }

    /**
     * Determines if a failure threshold for the number of new high priority issues is set.
     *
     * @return {@code true} if the failure threshold for the number of new high priority issues is set
     */
    public boolean hasNewHighIssuesPriorityFailureThreshold() {
        return newIssuesFailureThresholdHigh > 0 && checkNewIssues;
    }

    public boolean hasNewHighIssuesPriorityUnstableThreshold() {
        return newIssuesUnstableThresholdHigh > 0 && checkNewIssues;
    }




    /**
     * Returns the failure threshold for the total number of issues.
     *
     * @return the failure threshold for the total number of issues
     */
    public int getFailureThreshold() {
        return failureThreshold;
    }

    public int getNewIssuesFailureThreshold() {
        return newIssuesFailureThreshold;
    }

    public int getFailureThresholdLow() {
        return failureThresholdLow;
    }

    public int getFailureThresholdNormal() {
        return failureThresholdNormal;
    }

    public int getFailureThresholdHigh() {
        return failureThresholdHigh;
    }

    public int getNewIssuesFailureThresholdLow() {
        return newIssuesFailureThresholdLow;
    }

    public int getNewIssuesFailureThresholdNormal() {
        return newIssuesFailureThresholdNormal;
    }

    public int getNewIssuesFailureThresholdHigh() {
        return newIssuesFailureThresholdHigh;
    }

    public int getUnstableThreshold() {
        return unstableThreshold;
    }

    public int getNewIssuesUnstableThreshold() {
        return newIssuesUnstableThreshold;
    }

    public int getUnstableThresholdLow() {
        return unstableThresholdLow;
    }

    public int getUnstableThresholdNormal() {
        return unstableThresholdNormal;
    }

    public int getUnstableThresholdHigh() {
        return unstableThresholdHigh;
    }

    public int getNewIssuesUnstableThresholdLow() {
        return newIssuesUnstableThresholdLow;
    }

    public int getNewIssuesUnstableThresholdNormal() {
        return newIssuesUnstableThresholdNormal;
    }

    public int getNewIssuesUnstableThresholdHigh() {
        return newIssuesUnstableThresholdHigh;
    }
}
