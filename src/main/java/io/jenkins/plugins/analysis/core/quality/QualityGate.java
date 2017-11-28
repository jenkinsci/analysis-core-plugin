package io.jenkins.plugins.analysis.core.quality;

import io.jenkins.plugins.analysis.core.quality.thresholds.FailureThresholds;
import io.jenkins.plugins.analysis.core.quality.thresholds.NewFailureThresholds;
import io.jenkins.plugins.analysis.core.quality.thresholds.NewUnstableThresholds;
import io.jenkins.plugins.analysis.core.quality.thresholds.UnstableThresholds;

/**
 * Defines quality gates for a static analysis run.
 *
 * @author Ullrich Hafner
 */
public class QualityGate {
    private final FailureThresholds failureThresholds;
    private final UnstableThresholds unstableThresholds;
    private final NewFailureThresholds newFailureThresholds;
    private final NewUnstableThresholds newUnstableThresholds;

    /**
     * Creates a new instance of {@link QualityGate}. No thresholds are set.
     */
    public QualityGate() {
        this.failureThresholds = new FailureThresholds();
        this.unstableThresholds = new UnstableThresholds();
        this.newFailureThresholds = new NewFailureThresholds();
        this.newUnstableThresholds = new NewUnstableThresholds();
    }

    /**
     * Creates a new instance of {@link QualityGate}. Only failure thresholds are set.
     *
     * @param failureThresholds to determine a failed build
     */
    public QualityGate(FailureThresholds failureThresholds) {
        this.failureThresholds = failureThresholds;
        this.unstableThresholds = new UnstableThresholds();
        this.newFailureThresholds = new NewFailureThresholds();
        this.newUnstableThresholds = new NewUnstableThresholds();
    }

    /**
     * Creates a new instance of {@link QualityGate}. Only unstable thresholds are set.
     *
     * @param unstableThresholds to determine an unstable build
     */
    public QualityGate(UnstableThresholds unstableThresholds) {
        this.failureThresholds = new FailureThresholds();
        this.unstableThresholds = unstableThresholds;
        this.newFailureThresholds = new NewFailureThresholds();
        this.newUnstableThresholds = new NewUnstableThresholds();
    }

    /**
     * Creates a new instance of {@link QualityGate}. Only failure thresholds for new build are set.
     *
     * @param newFailureThresholds to determine a failed build
     */
    public QualityGate(NewFailureThresholds newFailureThresholds) {
        this.failureThresholds = new FailureThresholds();
        this.unstableThresholds = new UnstableThresholds();
        this.newFailureThresholds = newFailureThresholds;
        this.newUnstableThresholds = new NewUnstableThresholds();
    }

    /**
     * Creates a new instance of {@link QualityGate}. Only unstable threshold for new build are set.
     *
     * @param newUnstableThresholds to determine an unstable build
     */
    public QualityGate(NewUnstableThresholds newUnstableThresholds) {
        this.failureThresholds = new FailureThresholds();
        this.unstableThresholds = new UnstableThresholds();
        this.newFailureThresholds = new NewFailureThresholds();
        this.newUnstableThresholds = newUnstableThresholds;
    }

    /**
     * Creates a new instance of {@link QualityGate}. Only failure and unstable thresholds are set.
     *
     * @param failureThresholds  to determine a failed build
     * @param unstableThresholds to determine an unstable build
     */
    public QualityGate(FailureThresholds failureThresholds, UnstableThresholds unstableThresholds) {
        this.failureThresholds = failureThresholds;
        this.unstableThresholds = unstableThresholds;
        this.newFailureThresholds = new NewFailureThresholds();
        this.newUnstableThresholds = new NewUnstableThresholds();
    }

    /**
     * Determines if a failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the failure threshold for the total number of issues is set
     */
    public boolean hasFailureThreshold() {
        return failureThresholds.hasThreshold();
    }

    /**
     * Determines if a high failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the high failure threshold for the total number of issues is set
     */
    public boolean hasHighFailureThreshold() {
        return failureThresholds.hasHighThreshold();
    }

    /**
     * Determines if a normal failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the normal failure threshold for the total number of issues is set
     */
    public boolean hasNormalFailureThreshold() {
        return failureThresholds.hasNormalThreshold();
    }

    /**
     * Determines if a low failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the low failure threshold for the total number of issues is set
     */
    public boolean hasLowFailureThreshold() {
        return failureThresholds.hasLowThreshold();
    }

    /**
     * Returns the failure threshold for the total number of issues.
     *
     * @return the failure threshold for the total number of issues
     */
    public int getFailureThreshold() {
        return failureThresholds.getTotalThreshold();
    }

    /**
     * Returns the failure threshold for the total number of issues of category high.
     *
     * @return high failure threshold
     */
    public int getHighFailureThreshold() {
        return failureThresholds.getHighThreshold();
    }

    /**
     * Returns the failure threshold for the total number of issues of category normal.
     *
     * @return normal failure threshold
     */
    public int getNormalFailureThreshold() {
        return failureThresholds.getNormalThreshold();
    }

    /**
     * Returns the failure threshold for the total number of issues of category low.
     *
     * @return low failure threshold
     */
    public int getLowFailureThreshold() {
        return failureThresholds.getLowThreshold();
    }

    /**
     * Determines if a unstable threshold for the total number of issues is set.
     *
     * @return {@code true} if the unstable threshold for the total number of issues is set
     */
    public boolean hasUnstableThreshold() {
        return unstableThresholds.hasThreshold();
    }

    /**
     * Determines if a high unstable threshold for the total number of issues is set.
     *
     * @return {@code true} if the high unstable threshold for the total number of issues is set
     */
    public boolean hasHighUnstableThreshold() {
        return unstableThresholds.hasHighThreshold();
    }

    /**
     * Determines if a normal unstable threshold for the total number of issues is set.
     *
     * @return {@code true} if the normal unstable threshold for the total number of issues is set
     */
    public boolean hasNormalUnstableThreshold() {
        return unstableThresholds.hasNormalThreshold();
    }

    /**
     * Determines if a low unstable threshold for the total number of issues is set.
     *
     * @return {@code true} if the low unstable threshold for the total number of issues is set
     */
    public boolean hasLowUnstableThreshold() {
        return unstableThresholds.hasLowThreshold();
    }

    /**
     * Returns the unstable threshold for the total number of issues.
     *
     * @return the unstable threshold for the total number of issues
     */
    public int getUnstableThreshold() {
        return unstableThresholds.getTotalThreshold();
    }

    /**
     * Returns the high unstable threshold for the total number of issues.
     *
     * @return the high unstable threshold for the total number of issues
     */
    public int getHighUnstableThreshold() {
        return unstableThresholds.getHighThreshold();
    }

    /**
     * Returns the normal unstable threshold for the total number of issues.
     *
     * @return the normal unstable threshold for the total number of issues
     */
    public int getNormalUnstableThreshold() {
        return unstableThresholds.getNormalThreshold();
    }

    /**
     * Returns the low unstable threshold for the total number of issues.
     *
     * @return the low unstable threshold for the total number of issues
     */
    public int getLowUnstableThreshold() {
        return unstableThresholds.getLowThreshold();
    }

    /**
     * Determines if a new failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the new failure threshold for the total number of issues is set
     */
    public boolean hasNewFailureThreshold() {
        return newFailureThresholds.hasThreshold();
    }

    /**
     * Determines if a new high failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the new high failure threshold for the total number of issues is set
     */
    public boolean hasNewHighFailureThreshold() {
        return newFailureThresholds.hasHighThreshold();
    }

    /**
     * Determines if a new normal failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the normal high failure threshold for the total number of issues is set
     */
    public boolean hasNewNormalFailureThreshold() {
        return newFailureThresholds.hasNormalThreshold();
    }

    /**
     * Determines if a new low failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the low high failure threshold for the total number of issues is set
     */
    public boolean hasNewLowFailureThreshold() {
        return newFailureThresholds.hasLowThreshold();
    }

    /**
     * Returns the new failure threshold for the total number of issues.
     *
     * @return the new failure threshold for the total number of issues
     */
    public int getNewFailureThreshold() {
        return newFailureThresholds.getTotalThreshold();
    }

    /**
     * Returns the new high failure threshold for the total number of issues.
     *
     * @return the new high failure threshold for the total number of issues
     */
    public int getNewHighFailureThreshold() {
        return newFailureThresholds.getHighThreshold();
    }

    /**
     * Returns the new normal failure threshold for the total number of issues.
     *
     * @return the new normal failure threshold for the total number of issues
     */
    public int getNewNormalFailureThreshold() {
        return newFailureThresholds.getNormalThreshold();
    }

    /**
     * Returns the new low failure threshold for the total number of issues.
     *
     * @return the new low failure threshold for the total number of issues
     */
    public int getNewLowFailureThreshold() {
        return newFailureThresholds.getLowThreshold();
    }

    /**
     * Determines if a new unstable threshold for the total number of issues is set.
     *
     * @return {@code true} if the new unstable threshold for the total number of issues is set
     */
    public boolean hasNewUnstableThreshold() {
        return newUnstableThresholds.hasThreshold();
    }

    /**
     * Determines if a new high unstable threshold for the total number of issues is set.
     *
     * @return {@code true} if the new high unstable threshold for the total number of issues is set
     */
    public boolean hasNewHighUnstableThreshold() {
        return newUnstableThresholds.hasHighThreshold();
    }

    /**
     * Determines if a new normal unstable threshold for the total number of issues is set.
     *
     * @return {@code true} if the new normal unstable threshold for the total number of issues is set
     */
    public boolean hasNewNormalUnstableThreshold() {
        return newUnstableThresholds.hasNormalThreshold();
    }

    /**
     * Determines if a new low unstable threshold for the total number of issues is set.
     *
     * @return {@code true} if the new low unstable threshold for the total number of issues is set
     */
    public boolean hasNewLowUnstableThreshold() {
        return newUnstableThresholds.hasLowThreshold();
    }

    /**
     * Returns the new unstable threshold for the total number of issues.
     *
     * @return the new unstable threshold for the total number of issues
     */
    public int getNewUnstableThreshold() {
        return newUnstableThresholds.getTotalThreshold();
    }

    /**
     * Returns the new high unstable threshold for the total number of issues.
     *
     * @return the new high unstable threshold for the total number of issues
     */
    public int getNewHighUnstableThreshold() {
        return newUnstableThresholds.getHighThreshold();
    }

    /**
     * Returns the new normal unstable threshold for the total number of issues.
     *
     * @return the new normal unstable threshold for the total number of issues
     */
    public int getNewNormalUnstableThreshold() {
        return newUnstableThresholds.getNormalThreshold();
    }

    /**
     * Returns the new low unstable threshold for the total number of issues.
     *
     * @return the new low unstable threshold for the total number of issues
     */
    public int getNewLowUnstableThreshold() {
        return newUnstableThresholds.getLowThreshold();
    }
}
