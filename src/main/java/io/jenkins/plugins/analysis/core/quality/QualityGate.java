package io.jenkins.plugins.analysis.core.quality;

/**
 * Defines quality gates for a static analysis run.
 *
 * @author Ullrich Hafner
 * @author Aykut Yilmaz
 */
public class QualityGate {
    private final WarningsThreshold failureThreshold;
    private final WarningsThreshold unstableThreshold;
    private final WarningsThreshold newFailureThreshold;
    private final WarningsThreshold newUnstableThreshold;

    /**
     * Creates a new instance of {@link QualityGate}. No thresholds are set.
     */
    public QualityGate() {
        this.failureThreshold = new WarningsThreshold();
        this.unstableThreshold = new WarningsThreshold();
        this.newFailureThreshold = new WarningsThreshold();
        this.newUnstableThreshold = new WarningsThreshold();
    }

    public QualityGate(WarningsThreshold failureThreshold,
                       WarningsThreshold unstableThreshold,
                       WarningsThreshold newFailureThreshold,
                       WarningsThreshold newUnstableThreshold) {
        this.failureThreshold = defaultIfAbsent(failureThreshold);
        this.unstableThreshold = defaultIfAbsent(unstableThreshold);
        this.newFailureThreshold = defaultIfAbsent(newFailureThreshold);
        this.newUnstableThreshold = defaultIfAbsent(newUnstableThreshold);
    }

    private WarningsThreshold defaultIfAbsent(WarningsThreshold threshold) {
        return threshold == null ? new WarningsThreshold() : threshold;
    }

    /**
     * Determines if a failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the failure threshold for the total number of issues is set
     */
    public boolean hasFailureThreshold() {
        return failureThreshold.hasThreshold();
    }

    /**
     * Determines if a high failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the high failure threshold for the total number of issues is set
     */
    public boolean hasHighFailureThreshold() {
        return failureThreshold.hasHighThreshold();
    }

    /**
     * Determines if a normal failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the normal failure threshold for the total number of issues is set
     */
    public boolean hasNormalFailureThreshold() {
        return failureThreshold.hasNormalThreshold();
    }

    /**
     * Determines if a low failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the low failure threshold for the total number of issues is set
     */
    public boolean hasLowFailureThreshold() {
        return failureThreshold.hasLowThreshold();
    }

    /**
     * Returns the failure threshold for the total number of issues.
     *
     * @return the failure threshold for the total number of issues
     */
    public int getFailureThreshold() {
        return failureThreshold.getTotalThreshold();
    }

    /**
     * Returns the failure threshold for the total number of issues of category high.
     *
     * @return high failure threshold
     */
    public int getHighFailureThreshold() {
        return failureThreshold.getHighThreshold();
    }

    /**
     * Returns the failure threshold for the total number of issues of category normal.
     *
     * @return normal failure threshold
     */
    public int getNormalFailureThreshold() {
        return failureThreshold.getNormalThreshold();
    }

    /**
     * Returns the failure threshold for the total number of issues of category low.
     *
     * @return low failure threshold
     */
    public int getLowFailureThreshold() {
        return failureThreshold.getLowThreshold();
    }

    /**
     * Determines if a unstable threshold for the total number of issues is set.
     *
     * @return {@code true} if the unstable threshold for the total number of issues is set
     */
    public boolean hasUnstableThreshold() {
        return unstableThreshold.hasThreshold();
    }

    /**
     * Determines if a high unstable threshold for the total number of issues is set.
     *
     * @return {@code true} if the high unstable threshold for the total number of issues is set
     */
    public boolean hasHighUnstableThreshold() {
        return unstableThreshold.hasHighThreshold();
    }

    /**
     * Determines if a normal unstable threshold for the total number of issues is set.
     *
     * @return {@code true} if the normal unstable threshold for the total number of issues is set
     */
    public boolean hasNormalUnstableThreshold() {
        return unstableThreshold.hasNormalThreshold();
    }

    /**
     * Determines if a low unstable threshold for the total number of issues is set.
     *
     * @return {@code true} if the low unstable threshold for the total number of issues is set
     */
    public boolean hasLowUnstableThreshold() {
        return unstableThreshold.hasLowThreshold();
    }

    /**
     * Returns the unstable threshold for the total number of issues.
     *
     * @return the unstable threshold for the total number of issues
     */
    public int getUnstableThreshold() {
        return unstableThreshold.getTotalThreshold();
    }

    /**
     * Returns the high unstable threshold for the total number of issues.
     *
     * @return the high unstable threshold for the total number of issues
     */
    public int getHighUnstableThreshold() {
        return unstableThreshold.getHighThreshold();
    }

    /**
     * Returns the normal unstable threshold for the total number of issues.
     *
     * @return the normal unstable threshold for the total number of issues
     */
    public int getNormalUnstableThreshold() {
        return unstableThreshold.getNormalThreshold();
    }

    /**
     * Returns the low unstable threshold for the total number of issues.
     *
     * @return the low unstable threshold for the total number of issues
     */
    public int getLowUnstableThreshold() {
        return unstableThreshold.getLowThreshold();
    }

    /**
     * Determines if a new failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the new failure threshold for the total number of issues is set
     */
    public boolean hasNewFailureThreshold() {
        return newFailureThreshold.hasThreshold();
    }

    /**
     * Determines if a new high failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the new high failure threshold for the total number of issues is set
     */
    public boolean hasNewHighFailureThreshold() {
        return newFailureThreshold.hasHighThreshold();
    }

    /**
     * Determines if a new normal failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the normal high failure threshold for the total number of issues is set
     */
    public boolean hasNewNormalFailureThreshold() {
        return newFailureThreshold.hasNormalThreshold();
    }

    /**
     * Determines if a new low failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the low high failure threshold for the total number of issues is set
     */
    public boolean hasNewLowFailureThreshold() {
        return newFailureThreshold.hasLowThreshold();
    }

    /**
     * Returns the new failure threshold for the total number of issues.
     *
     * @return the new failure threshold for the total number of issues
     */
    public int getNewFailureThreshold() {
        return newFailureThreshold.getTotalThreshold();
    }

    /**
     * Returns the new high failure threshold for the total number of issues.
     *
     * @return the new high failure threshold for the total number of issues
     */
    public int getNewHighFailureThreshold() {
        return newFailureThreshold.getHighThreshold();
    }

    /**
     * Returns the new normal failure threshold for the total number of issues.
     *
     * @return the new normal failure threshold for the total number of issues
     */
    public int getNewNormalFailureThreshold() {
        return newFailureThreshold.getNormalThreshold();
    }

    /**
     * Returns the new low failure threshold for the total number of issues.
     *
     * @return the new low failure threshold for the total number of issues
     */
    public int getNewLowFailureThreshold() {
        return newFailureThreshold.getLowThreshold();
    }

    /**
     * Determines if a new unstable threshold for the total number of issues is set.
     *
     * @return {@code true} if the new unstable threshold for the total number of issues is set
     */
    public boolean hasNewUnstableThreshold() {
        return newUnstableThreshold.hasThreshold();
    }

    /**
     * Determines if a new high unstable threshold for the total number of issues is set.
     *
     * @return {@code true} if the new high unstable threshold for the total number of issues is set
     */
    public boolean hasNewHighUnstableThreshold() {
        return newUnstableThreshold.hasHighThreshold();
    }

    /**
     * Determines if a new normal unstable threshold for the total number of issues is set.
     *
     * @return {@code true} if the new normal unstable threshold for the total number of issues is set
     */
    public boolean hasNewNormalUnstableThreshold() {
        return newUnstableThreshold.hasNormalThreshold();
    }

    /**
     * Determines if a new low unstable threshold for the total number of issues is set.
     *
     * @return {@code true} if the new low unstable threshold for the total number of issues is set
     */
    public boolean hasNewLowUnstableThreshold() {
        return newUnstableThreshold.hasLowThreshold();
    }

    /**
     * Returns the new unstable threshold for the total number of issues.
     *
     * @return the new unstable threshold for the total number of issues
     */
    public int getNewUnstableThreshold() {
        return newUnstableThreshold.getTotalThreshold();
    }

    /**
     * Returns the new high unstable threshold for the total number of issues.
     *
     * @return the new high unstable threshold for the total number of issues
     */
    public int getNewHighUnstableThreshold() {
        return newUnstableThreshold.getHighThreshold();
    }

    /**
     * Returns the new normal unstable threshold for the total number of issues.
     *
     * @return the new normal unstable threshold for the total number of issues
     */
    public int getNewNormalUnstableThreshold() {
        return newUnstableThreshold.getNormalThreshold();
    }

    /**
     * Returns the new low unstable threshold for the total number of issues.
     *
     * @return the new low unstable threshold for the total number of issues
     */
    public int getNewLowUnstableThreshold() {
        return newUnstableThreshold.getLowThreshold();
    }
}
