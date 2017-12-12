package io.jenkins.plugins.analysis.core.quality;

import edu.hm.hafner.analysis.Priority;

import hudson.model.Result;

/**
 * Defines a quality gate for a {@link StaticAnalysisRun}. This class
 * represents the data the user has put into the GUI and is used
 * to express the different thresholds for the 3 different {@link Priority}
 * with their corresponding {@link Result}.
 *
 * @author Joscha Behrmann
 */
public class QualityGate {
    /**
     * The default value when a field is left empty.
     */
    public static final int UNSET = Integer.MAX_VALUE;

    /* Should 'new' thresholds be considered */
    private final boolean computeNewWarnings;

    /* The 'total' thresholds for the different priorities and their resulting states */
    private final int totalPriorityAllUnstable;
    private final int totalPriorityAllFailed;
    private final int totalPriorityHighUnstable;
    private final int totalPriorityHighFailed;
    private final int totalPriorityNormalUnstable;
    private final int totalPriorityNormalFailed;
    private final int totalPriorityLowUnstable;
    private final int totalPriorityLowFailed;

    /* The 'new' thresholds for the different priorities and their resulting states */
    private final int newPriorityAllUnstable;
    private final int newPriorityAllFailed;
    private final int newPriorityHighUnstable;
    private final int newPriorityHighFailed;
    private final int newPriorityNormalUnstable;
    private final int newPriorityNormalFailed;
    private final int newPriorityLowUnstable;
    private final int newPriorityLowFailed;

    /**
     * Instances of this class are build by {@link Builder}.
     *
     * @param builder to use
     */
    private QualityGate(Builder builder) {
        this.computeNewWarnings = builder.computeNewWarnings;

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
     * Whether new warnings should be considered.
     * @return true if yes, false if not
     */
    public boolean shouldComputeNewWarnings() {
        return computeNewWarnings;
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

    /**
     * Builder to create a QualityGate.
     */
    public static class Builder {
        private boolean computeNewWarnings;

        private int totalPriorityAllUnstable = UNSET;
        private int totalPriorityAllFailed = UNSET;
        private int totalPriorityHighUnstable = UNSET;
        private int totalPriorityHighFailed = UNSET;
        private int totalPriorityNormalUnstable = UNSET;
        private int totalPriorityNormalFailed = UNSET;
        private int totalPriorityLowUnstable = UNSET;
        private int totalPriorityLowFailed = UNSET;

        private int newPriorityAllUnstable = UNSET;
        private int newPriorityAllFailed = UNSET;
        private int newPriorityHighUnstable = UNSET;
        private int newPriorityHighFailed = UNSET;
        private int newPriorityNormalUnstable = UNSET;
        private int newPriorityNormalFailed = UNSET;
        private int newPriorityLowUnstable = UNSET;
        private int newPriorityLowFailed = UNSET;

        /**
         * Creates a new instance {@link QualityGate}.
         *
         * @return an instance of QualityGate with specified properties
         */
        public QualityGate build() {
            return new QualityGate(this);
        }

        /**
         * Whether new warnings should be considered, e.g. delta of issues from this build
         * to the previous build.
         *
         * @param computeNewWarnings should new warnings be considered
         * @return Builder this
         */
        public Builder setComputeNewWarnings(final boolean computeNewWarnings) {
            this.computeNewWarnings = computeNewWarnings;
            return this;
        }

        /**
         * Sets a threshold.
         *
         * @param totalPriorityAllUnstable threshold for the priority (>= 0, otherwise defaults to 0)
         * @return Builder this
         */
        public Builder setTotalPriorityAllUnstable(final int totalPriorityAllUnstable) {
            this.totalPriorityAllUnstable = totalPriorityAllUnstable < 0 ? 0 : totalPriorityAllUnstable;
            return this;
        }

        /**
         * Sets a threshold.
         *
         * @param totalPriorityAllFailed threshold for the priority (>= 0, otherwise defaults to 0)
         * @return Builder this
         */
        public Builder setTotalPriorityAllFailed(final int totalPriorityAllFailed) {
            this.totalPriorityAllFailed = totalPriorityAllFailed < 0 ? 0 : totalPriorityAllFailed;
            return this;
        }

        /**
         * Sets a threshold.
         *
         * @param totalPriorityHighUnstable threshold for the priority (>= 0, otherwise defaults to 0)
         * @return Builder this
         */
        public Builder setTotalPriorityHighUnstable(final int totalPriorityHighUnstable) {
            this.totalPriorityHighUnstable = totalPriorityHighUnstable < 0 ? 0 : totalPriorityHighUnstable;
            return this;
        }

        /**
         * Sets a threshold.
         *
         * @param totalPriorityHighFailed threshold for the priority (>= 0, otherwise defaults to 0)
         * @return Builder this
         */
        public Builder setTotalPriorityHighFailed(final int totalPriorityHighFailed) {
            this.totalPriorityHighFailed = totalPriorityHighFailed < 0 ? 0 : totalPriorityHighFailed;
            return this;
        }

        /**
         * Sets a threshold.
         *
         * @param totalPriorityNormalUnstable threshold for the priority (>= 0, otherwise defaults to 0)
         * @return Builder this
         */
        public Builder setTotalPriorityNormalUnstable(final int totalPriorityNormalUnstable) {
            this.totalPriorityNormalUnstable = totalPriorityNormalUnstable < 0 ? 0 : totalPriorityNormalUnstable;
            return this;
        }

        /**
         * Sets a threshold.
         *
         * @param totalPriorityNormalFailed threshold for the priority (>= 0, otherwise defaults to 0)
         * @return Builder this
         */
        public Builder setTotalPriorityNormalFailed(final int totalPriorityNormalFailed) {
            this.totalPriorityNormalFailed = totalPriorityNormalFailed < 0 ? 0 : totalPriorityNormalFailed;
            return this;
        }

        /**
         * Sets a threshold.
         *
         * @param totalPriorityLowUnstable threshold for the priority (>= 0, otherwise defaults to 0)
         * @return Builder this
         */
        public Builder setTotalPriorityLowUnstable(final int totalPriorityLowUnstable) {
            this.totalPriorityLowUnstable = totalPriorityLowUnstable < 0 ? 0 : totalPriorityLowUnstable;
            return this;
        }

        /**
         * Sets a threshold.
         *
         * @param totalPriorityLowFailed threshold for the priority (>= 0, otherwise defaults to 0)
         * @return Builder this
         */
        public Builder setTotalPriorityLowFailed(final int totalPriorityLowFailed) {
            this.totalPriorityLowFailed = totalPriorityLowFailed < 0 ? 0 : totalPriorityLowFailed;
            return this;
        }

        /**
         * Sets a threshold.
         *
         * @param newPriorityAllUnstable threshold for the priority (>= 0, otherwise defaults to 0)
         * @return Builder this
         */
        public Builder setNewPriorityAllUnstable(final int newPriorityAllUnstable) {
            this.newPriorityAllUnstable = newPriorityAllUnstable < 0 ? 0 : newPriorityAllUnstable;
            return this;
        }

        /**
         * Sets a threshold.
         *
         * @param newPriorityAllFailed threshold for the priority (>= 0, otherwise defaults to 0)
         * @return Builder this
         */
        public Builder setNewPriorityAllFailed(final int newPriorityAllFailed) {
            this.newPriorityAllFailed = newPriorityAllFailed < 0 ? 0 : newPriorityAllFailed;
            return this;
        }

        /**
         * Sets a threshold.
         *
         * @param newPriorityHighUnstable threshold for the priority (>= 0, otherwise defaults to 0)
         * @return Builder this
         */
        public Builder setNewPriorityHighUnstable(final int newPriorityHighUnstable) {
            this.newPriorityHighUnstable = newPriorityHighUnstable < 0 ? 0 : newPriorityHighUnstable;
            return this;
        }

        /**
         * Sets a threshold.
         *
         * @param newPriorityHighFailed threshold for the priority (>= 0, otherwise defaults to 0)
         * @return Builder this
         */
        public Builder setNewPriorityHighFailed(final int newPriorityHighFailed) {
            this.newPriorityHighFailed = newPriorityHighFailed < 0 ? 0 : newPriorityHighFailed;
            return this;
        }

        /**
         * Sets a threshold.
         *
         * @param newPriorityNormalUnstable threshold for the priority (>= 0, otherwise defaults to 0)
         * @return Builder this
         */
        public Builder setNewPriorityNormalUnstable(final int newPriorityNormalUnstable) {
            this.newPriorityNormalUnstable = newPriorityNormalUnstable < 0 ? 0 : newPriorityNormalUnstable;
            return this;
        }

        /**
         * Sets a threshold.
         *
         * @param newPriorityNormalFailed threshold for the priority (>= 0, otherwise defaults to 0)
         * @return Builder this
         */
        public Builder setNewPriorityNormalFailed(final int newPriorityNormalFailed) {
            this.newPriorityNormalFailed = newPriorityNormalFailed < 0 ? 0 : newPriorityNormalFailed;
            return this;
        }

        /**
         * Sets a threshold.
         *
         * @param newPriorityLowUnstable threshold for the priority (>= 0, otherwise defaults to 0)
         * @return Builder this
         */
        public Builder setNewPriorityLowUnstable(final int newPriorityLowUnstable) {
            this.newPriorityLowUnstable = newPriorityLowUnstable < 0 ? 0 : newPriorityLowUnstable;
            return this;
        }

        /**
         * Sets a threshold.
         *
         * @param newPriorityLowFailed threshold for the priority (>= 0, otherwise defaults to 0)
         * @return Builder this
         */
        public Builder setNewPriorityLowFailed(final int newPriorityLowFailed) {
            this.newPriorityLowFailed = newPriorityLowFailed < 0 ? 0 : newPriorityLowFailed;
            return this;
        }
    }
}
