package io.jenkins.plugins.analysis.core.quality;

/**
 * Defines quality gates for a static analysis run.
 *
 * @author Joscha Behrmann
 */
public class QualityGate {
    private final int totalPriorityAllUnstable;
    private final int totalPriorityAllFailed;
    private final int totalPriorityHighUnstable;
    private final int totalPriorityHighFailed;
    private final int totalPriorityNormalUnstable;
    private final int totalPriorityNormalFailed;
    private final int totalPriorityLowUnstable;
    private final int totalPriorityLowFailed;

    private final int newPriorityAllUnstable;
    private final int newPriorityAllFailed;
    private final int newPriorityHighUnstable;
    private final int newPriorityHighFailed;
    private final int newPriorityNormalUnstable;
    private final int newPriorityNormalFailed;
    private final int newPriorityLowUnstable;
    private final int newPriorityLowFailed;

    private QualityGate(Builder builder) {
        this.totalPriorityAllUnstable = builder.totalPriorityAllUnstable;
        this.totalPriorityAllFailed = builder.totalPriorityAllFailed;
        this.totalPriorityHighUnstable = builder.totalPriorityHighUnstable;
        this.totalPriorityHighFailed = builder.totalPriorityHighFailed;
        this.totalPriorityNormalUnstable = builder.totalPriorityNormalUnstable;
        this.totalPriorityNormalFailed = builder.totalPriorityNormalFailed;
        this.totalPriorityLowUnstable = builder.totalPriorityLowUnstable;
        this.totalPriorityLowFailed = builder.totalPriorityLowFailed;

        this.newPriorityAllUnstable = builder.newPriorityAllUnstable;
        this.newPriorityAllFailed = builder.newPriorityAllFailed;
        this.newPriorityHighUnstable = builder.newPriorityHighUnstable;
        this.newPriorityHighFailed = builder.newPriorityHighFailed;
        this.newPriorityNormalUnstable = builder.newPriorityNormalUnstable;
        this.newPriorityNormalFailed = builder.newPriorityNormalFailed;
        this.newPriorityLowUnstable = builder.newPriorityLowUnstable;
        this.newPriorityLowFailed = builder.newPriorityLowFailed;
    }

    /**
     * Builder to create a QualityGate.
     */
    public static class Builder {
        private int totalPriorityAllUnstable;
        private int totalPriorityAllFailed;
        private int totalPriorityHighUnstable;
        private int totalPriorityHighFailed;
        private int totalPriorityNormalUnstable;
        private int totalPriorityNormalFailed;
        private int totalPriorityLowUnstable;
        private int totalPriorityLowFailed;

        private int newPriorityAllUnstable;
        private int newPriorityAllFailed;
        private int newPriorityHighUnstable;
        private int newPriorityHighFailed;
        private int newPriorityNormalUnstable;
        private int newPriorityNormalFailed;
        private int newPriorityLowUnstable;
        private int newPriorityLowFailed;

        public QualityGate build() {
            return new QualityGate(this);
        }

        public Builder setTotalPriorityAllUnstable(final int totalPriorityAllUnstable) {
            this.totalPriorityAllUnstable = totalPriorityAllUnstable;
            return this;
        }

        public Builder setTotalPriorityAllFailed(final int totalPriorityAllFailed) {
            this.totalPriorityAllFailed = totalPriorityAllFailed;
            return this;
        }

        public Builder setTotalPriorityHighUnstable(final int totalPriorityHighUnstable) {
            this.totalPriorityHighUnstable = totalPriorityHighUnstable;
            return this;
        }

        public Builder setTotalPriorityHighFailed(final int totalPriorityHighFailed) {
            this.totalPriorityHighFailed = totalPriorityHighFailed;
            return this;
        }

        public Builder setTotalPriorityNormalUnstable(final int totalPriorityNormalUnstable) {
            this.totalPriorityNormalUnstable = totalPriorityNormalUnstable;
            return this;
        }

        public Builder setTotalPriorityNormalFailed(final int totalPriorityNormalFailed) {
            this.totalPriorityNormalFailed = totalPriorityNormalFailed;
            return this;
        }

        public Builder setTotalPriorityLowUnstable(final int totalPriorityLowUnstable) {
            this.totalPriorityLowUnstable = totalPriorityLowUnstable;
            return this;
        }

        public Builder setTotalPriorityLowFailed(final int totalPriorityLowFailed) {
            this.totalPriorityLowFailed = totalPriorityLowFailed;
            return this;
        }

        public Builder setNewPriorityAllUnstable(final int newPriorityAllUnstable) {
            this.newPriorityAllUnstable = newPriorityAllUnstable;
            return this;
        }

        public Builder setNewPriorityAllFailed(final int newPriorityAllFailed) {
            this.newPriorityAllFailed = newPriorityAllFailed;
            return this;
        }

        public Builder setNewPriorityHighUnstable(final int newPriorityHighUnstable) {
            this.newPriorityHighUnstable = newPriorityHighUnstable;
            return this;
        }

        public Builder setNewPriorityHighFailed(final int newPriorityHighFailed) {
            this.newPriorityHighFailed = newPriorityHighFailed;
            return this;
        }

        public Builder setNewPriorityNormalUnstable(final int newPriorityNormalUnstable) {
            this.newPriorityNormalUnstable = newPriorityNormalUnstable;
            return this;
        }

        public Builder setNewPriorityNormalFailed(final int newPriorityNormalFailed) {
            this.newPriorityNormalFailed = newPriorityNormalFailed;
            return this;
        }

        public Builder setNewPriorityLowUnstable(final int newPriorityLowUnstable) {
            this.newPriorityLowUnstable = newPriorityLowUnstable;
            return this;
        }

        public Builder setNewPriorityLowFailed(final int newPriorityLowFailed) {
            this.newPriorityLowFailed = newPriorityLowFailed;
            return this;
        }
    }

    public int getTotalPriorityAllUnstable() {
        return totalPriorityAllUnstable;
    }

    public int getTotalPriorityAllFailed() {
        return totalPriorityAllFailed;
    }

    public int getTotalPriorityHighUnstable() {
        return totalPriorityHighUnstable;
    }

    public int getTotalPriorityHighFailed() {
        return totalPriorityHighFailed;
    }

    public int getTotalPriorityNormalUnstable() {
        return totalPriorityNormalUnstable;
    }

    public int getTotalPriorityNormalFailed() {
        return totalPriorityNormalFailed;
    }

    public int getTotalPriorityLowUnstable() {
        return totalPriorityLowUnstable;
    }

    public int getTotalPriorityLowFailed() {
        return totalPriorityLowFailed;
    }

    public int getNewPriorityAllUnstable() {
        return newPriorityAllUnstable;
    }

    public int getNewPriorityAllFailed() {
        return newPriorityAllFailed;
    }

    public int getNewPriorityHighUnstable() {
        return newPriorityHighUnstable;
    }

    public int getNewPriorityHighFailed() {
        return newPriorityHighFailed;
    }

    public int getNewPriorityNormalUnstable() {
        return newPriorityNormalUnstable;
    }

    public int getNewPriorityNormalFailed() {
        return newPriorityNormalFailed;
    }

    public int getNewPriorityLowUnstable() {
        return newPriorityLowUnstable;
    }

    public int getNewPriorityLowFailed() {
        return newPriorityLowFailed;
    }
}
