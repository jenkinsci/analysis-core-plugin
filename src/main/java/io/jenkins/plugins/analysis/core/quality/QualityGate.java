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
        this.failureThresholds = new FailureThresholds(0);
        this.unstableThresholds = new UnstableThresholds(0);
        this.newFailureThresholds = new NewFailureThresholds(0);
        this.newUnstableThresholds = new NewUnstableThresholds(0);
    }

    public QualityGate(UnstableThresholds unstableThresholds) {
        this.failureThresholds = new FailureThresholds(0);
        this.unstableThresholds = unstableThresholds;
        this.newFailureThresholds = new NewFailureThresholds(0);
        this.newUnstableThresholds = new NewUnstableThresholds(0);
    }

    public QualityGate(FailureThresholds failureThresholds) {
        this.failureThresholds = failureThresholds;
        this.unstableThresholds = new UnstableThresholds(0);
        this.newFailureThresholds = new NewFailureThresholds(0);
        this.newUnstableThresholds = new NewUnstableThresholds(0);
    }

    public QualityGate(NewFailureThresholds newFailureThresholds) {
        this.failureThresholds = new FailureThresholds(0);
        this.unstableThresholds = new UnstableThresholds(0);
        this.newFailureThresholds = newFailureThresholds;
        this.newUnstableThresholds = new NewUnstableThresholds(0);
    }

    public QualityGate(NewUnstableThresholds newUnstableThresholds) {
        this.failureThresholds = new FailureThresholds(0);
        this.unstableThresholds = new UnstableThresholds(0);
        this.newFailureThresholds = new NewFailureThresholds(0);
        this.newUnstableThresholds = newUnstableThresholds;
    }

    public QualityGate(FailureThresholds failureThresholds, UnstableThresholds unstableThresholds) {
        this.failureThresholds = failureThresholds;
        this.unstableThresholds = unstableThresholds;
        this.newFailureThresholds = new NewFailureThresholds(0);
        this.newUnstableThresholds = new NewUnstableThresholds(0);
    }

    /**
     * Determines if a failure threshold for the total number of issues is set.
     *
     * @return {@code true} if the failure threshold for the total number of issues is set
     */
    public boolean hasFailureThreshold() {
        return failureThresholds.hasThreshold();
    }

    public boolean hasHighFailureThreshold() {
        return failureThresholds.hasHighThreshold();
    }

    public boolean hasNormalFailureThreshold() {
        return failureThresholds.hasNormalThreshold();
    }

    public boolean hasLowFailureThreshold() {
        return failureThresholds.hasLowThreshold();
    }

    public boolean hasUnstableThreshold() {
        return unstableThresholds.hasThreshold();
    }

    /**
     * Returns the failure threshold for the total number of issues.
     *
     * @return the failure threshold for the total number of issues
     */
    public int getFailureThreshold() {
        return failureThresholds.getTotalThreshold();
    }

    public int getHighFailureThreshold() {
        return failureThresholds.getHighThreshold();
    }

    public int getNormalFailureThreshold() {
        return failureThresholds.getNormalThreshold();
    }

    public int getLowFailureThreshold() {
        return failureThresholds.getLowThreshold();
    }

    public int getUnstableThreshold() {
        return unstableThresholds.getTotalThreshold();
    }


    public boolean hasHighUnstableThreshold() {
        return unstableThresholds.hasHighThreshold();
    }

    public boolean hasNormalUnstableThreshold() {
        return unstableThresholds.hasNormalThreshold();
    }

    public boolean hasLowUnstableThreshold() {
        return unstableThresholds.hasLowThreshold();
    }

    public int getHighUnstableThreshold() {
        return unstableThresholds.getHighThreshold();
    }

    public int getNormalUnstableThreshold() {
        return unstableThresholds.getNormalThreshold();
    }

    public int getLowUnstableThreshold() {
        return unstableThresholds.getLowThreshold();
    }

    public boolean hasNewFailureThreshold() {
        return newFailureThresholds.hasThreshold();
    }

    public int getNewFailureThreshold() {
        return newFailureThresholds.getTotalThreshold();
    }

    public boolean hasNewUnstableThreshold() {
        return newUnstableThresholds.hasThreshold();
    }

    public int getNewUnstableThreshold() {
        return newUnstableThresholds.getTotalThreshold();
    }

    public boolean hasNewHighFailureThreshold() {
        return newFailureThresholds.hasHighThreshold();
    }

    public int getNewHighFailureThreshold() {
        return newFailureThresholds.getHighThreshold();
    }

    public boolean hasNewNormalFailureThreshold() {
        return newFailureThresholds.hasNormalThreshold();
    }

    public int getNewNormalFailureThreshold() {
        return newFailureThresholds.getNormalThreshold();
    }

    public boolean hasNewLowFailureThreshold() {
        return newFailureThresholds.hasLowThreshold();
    }

    public int getNewLowFailureThreshold() {
        return newFailureThresholds.getLowThreshold();
    }

    public boolean hasNewHighUnstableThreshold() {
        return newUnstableThresholds.hasHighThreshold();
    }

    public int getNewHighUnstableThreshold() {
        return newUnstableThresholds.getHighThreshold();
    }

    public boolean hasNewNormalUnstableThreshold() {
        return newUnstableThresholds.hasNormalThreshold();
    }

    public int getNewNormalUnstableThreshold() {
        return newUnstableThresholds.getNormalThreshold();
    }

    public boolean hasNewLowUnstableThreshold() {
        return newUnstableThresholds.hasLowThreshold();
    }

    public int getNewLowUnstableThreshold() {
        return newUnstableThresholds.getLowThreshold();
    }
}
