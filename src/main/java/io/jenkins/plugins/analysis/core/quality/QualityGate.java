package io.jenkins.plugins.analysis.core.quality;

/**
 * Defines quality gates for a static analysis run.
 *
 * @author Andreas Moser
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

    /**
     * Creates a new instance using the specified properties.
     *
     * @param failureThreshold
     *         The failure threshold of total issues.
     * @param newIssuesFailureThreshold
     *         The failure threshold of new issues.
     * @param unstableThreshold
     *         The unstable threshold of total issues.
     * @param newIssuesUnstableThreshold
     *         The unstable threshold of new issues.
     * @param failureThresholdLow
     *         The failure threshold for total low priority issues.
     * @param failureThresholdNormal
     *         The failure threshold for total normal priority issues.
     * @param failureThresholdHigh
     *         The failure threshold for total high priority issues.
     * @param newIssuesFailureThresholdLow
     *         The failure threshold for new low priority issues.
     * @param newIssuesFailureThresholdNormal
     *         The failure threshold for new normal priority issues.
     * @param newIssuesFailureThresholdHigh
     *         The failure threshold for new high priority issues.
     * @param unstableThresholdLow
     *         The unstable threshold for low priority issues.
     * @param unstableThresholdNormal
     *         The unstable threshold for normal priority issues.
     * @param unstableThresholdHigh
     *         The unstable threshold for high priority issues.
     * @param newIssuesUnstableThresholdLow
     *         The unstable threshold for new low priority issues.
     * @param newIssuesUnstableThresholdNormal
     *         The unstable threshold for new normal priority issues.
     * @param newIssuesUnstableThresholdHigh
     *         The unstable threshold for new high priority issues.
     * @param checkNewIssues
     *         A flag indicating if the gate should check number of new issues.
     */
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
        return getFailureThreshold() > 0;
    }

    /**
     * Determines if a unstable threshold for the total number of issues is set.
     *
     * @return {@code true} if the unstable threshold for the total number of issues is set
     */
    public boolean hasUnstableThreshold() {
        return getUnstableThreshold() > 0;
    }

    /**
     * Determines if new issues size should be checked for failure and a new issues failure threshold is set.
     *
     * @return {@code true} if the failure threshold for number of new issues is set and the checkNewIssues flag is
     *         set.
     */
    public boolean shouldCheckNewIssuesForFailure() {
        return checkNewIssues && getNewIssuesFailureThreshold() > 0;
    }

    /**
     * Determines if new issues size should be checked for unstable and a new issues unstable threshold is set.
     *
     * @return {@code true} if the unstable threshold for number of new issues is set and the checkNewIssues flag is
     *         set.
     */
    public boolean shouldCheckNewIssuesForUnstable() {
        return checkNewIssues && getNewIssuesUnstableThreshold() > 0;
    }

    /**
     * Determines if a failure threshold for the total number of low priority issues is set.
     *
     * @return {@code true} if the failure threshold for the total number of low priority issues is set
     */
    public boolean hasLowPriorityFailureThreshold() {
        return getFailureThresholdLow() > 0;
    }

    /**
     * Determines if a unstable threshold for the total number of low priority issues is set.
     *
     * @return {@code true} if the unstable threshold for the total number of low priority issues is set
     */
    public boolean hasLowPriorityUnstableThreshold() {
        return getUnstableThresholdLow() > 0;
    }

    /**
     * Determines if a failure threshold for the total number of normal priority issues is set.
     *
     * @return {@code true} if the failure threshold for the total number of normal priority issues is set
     */
    public boolean hasNormalPriorityFailureThreshold() {
        return getFailureThresholdNormal() > 0;
    }

    /**
     * Determines if a unstable threshold for the total number of normal priority issues is set.
     *
     * @return {@code true} if the unstable threshold for the total number of normal priority issues is set
     */
    public boolean hasNormalPriorityUnstableThreshold() {
        return getUnstableThresholdNormal() > 0;
    }

    /**
     * Determines if a failure threshold for the total number of high priority issues is set.
     *
     * @return {@code true} if the failure threshold for the total number of high priority issues is set
     */
    public boolean hasHighPriorityFailureThreshold() {
        return getFailureThresholdHigh() > 0;
    }

    /**
     * Determines if a unstable threshold for the total number of high priority issues is set.
     *
     * @return {@code true} if the unstable threshold for the total number of high priority issues is set
     */
    public boolean hasHighPriorityUnstableThreshold() {
        return getUnstableThresholdHigh() > 0;
    }

    /**
     * Determines if new issues size should be checked for failure and a failure threshold for the number of new low
     * priority issues is set.
     *
     * @return {@code true} if the failure threshold for the number of new low priority issues and the checkNewIssues
     *         flag is set.
     */
    public boolean hasNewLowIssuesPriorityFailureThreshold() {
        return getNewIssuesFailureThresholdLow() > 0 && checkNewIssues;
    }

    /**
     * Determines if new issues size should be checked for unstable and a unstable threshold for the number of new low
     * priority issues is set.
     *
     * @return {@code true} if the unstable threshold for the number of new low priority issues and the checkNewIssues
     *         flag is set.
     */
    public boolean hasNewLowIssuesPriorityUnstableThreshold() {
        return getNewIssuesUnstableThresholdLow() > 0 && checkNewIssues;
    }

    /**
     * Determines if new issues size should be checked for failure and a failure threshold for the number of new normal
     * priority issues is set.
     *
     * @return {@code true} if the failure threshold for the number of new normal priority issues and the checkNewIssues
     *         flag is set.
     */
    public boolean hasNewNormalIssuesPriorityFailureThreshold() {
        return getNewIssuesFailureThresholdNormal() > 0 && checkNewIssues;
    }

    /**
     * Determines if new issues size should be checked for unstable and a unstable threshold for the number of new
     * normal priority issues is set.
     *
     * @return {@code true} if the unstable threshold for the number of new normal priority issues and the
     *         checkNewIssues flag is set.
     */
    public boolean hasNewNormalIssuesPriorityUnstableThreshold() {
        return getNewIssuesUnstableThresholdNormal() > 0 && checkNewIssues;
    }

    /**
     * Determines if new issues size should be checked for failure and a failure threshold for the number of new high
     * priority issues is set.
     *
     * @return {@code true} if the failure threshold for the number of new high priority issues and the checkNewIssues
     *         flag is set.
     */
    public boolean hasNewHighIssuesPriorityFailureThreshold() {
        return getNewIssuesFailureThresholdHigh() > 0 && checkNewIssues;
    }

    /**
     * Determines if new issues size should be checked for unstable and a unstable threshold for the number of new high
     * priority issues is set.
     *
     * @return {@code true} if the unstable threshold for the number of new high priority issues and the checkNewIssues
     *         flag is set.
     */
    public boolean hasNewHighIssuesPriorityUnstableThreshold() {
        return getNewIssuesUnstableThresholdHigh() > 0 && checkNewIssues;
    }


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
