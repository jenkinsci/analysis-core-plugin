package io.jenkins.plugins.analysis.core.quality;

/**
 * A configuration for the QualityGate. Configuration values are thresholds for buildfailure and unstable build. Also
 * the thresholds for new issues can be set. Instatiation only by QualityGateConfigBuilder.
 */
public class QualityGateConfig {

    private final int failureThresholdAllPrios;
    private final int failureThresholdHighPrio;
    private final int failureThresholdNormalPrio;
    private final int failureThresholdLowPrio;
    private final int warningThresholdAllPrios;
    private final int warningThresholdHighPrio;
    private final int warningThresholdNormalPrio;
    private final int warningThresholdLowPrio;

    private final int newFailureThresholdAllPrios;
    private final int newFailureThresholdHighPrio;
    private final int newFailureThresholdNormalPrio;
    private final int newFailureThresholdLowPrio;
    private final int newWarningThresholdAllPrios;
    private final int newWarningThresholdHighPrio;
    private final int newWarningThresholdNormalPrio;
    private final int newWarningThresholdLowPrio;

    /**
     * Instatiates the Config with the values from the builder.
     *
     * @param builder
     *         builder for the config.
     */
    private QualityGateConfig(QualityGateConfigBuilder builder) {
        this.failureThresholdAllPrios = builder.failureThresholdAllPrios;
        this.failureThresholdHighPrio = builder.failureThresholdHighPrio;
        this.failureThresholdNormalPrio = builder.failureThresholdNormalPrio;
        this.failureThresholdLowPrio = builder.failureThresholdLowPrio;
        this.warningThresholdAllPrios = builder.warningThresholdAllPrios;
        this.warningThresholdHighPrio = builder.warningThresholdHighPrio;
        this.warningThresholdNormalPrio = builder.warningThresholdNormalPrio;
        this.warningThresholdLowPrio = builder.warningThresholdLowPrio;

        this.newFailureThresholdAllPrios = builder.newFailureThresholdAllPrios;
        this.newFailureThresholdHighPrio = builder.newFailureThresholdHighPrio;
        this.newFailureThresholdNormalPrio = builder.newFailureThresholdNormalPrio;
        this.newFailureThresholdLowPrio = builder.newFailureThresholdLowPrio;
        this.newWarningThresholdAllPrios = builder.newWarningThresholdAllPrios;
        this.newWarningThresholdHighPrio = builder.newWarningThresholdHighPrio;
        this.newWarningThresholdNormalPrio = builder.newWarningThresholdNormalPrio;
        this.newWarningThresholdLowPrio = builder.newWarningThresholdLowPrio;
    }

    public int getFailureThresholdAllPrios() {
        return failureThresholdAllPrios;
    }

    public int getFailureThresholdHighPrio() {
        return failureThresholdHighPrio;
    }

    public int getFailureThresholdNormalPrio() {
        return failureThresholdNormalPrio;
    }

    public int getFailureThresholdLowPrio() {
        return failureThresholdLowPrio;
    }

    public int getWarningThresholdAllPrios() {
        return warningThresholdAllPrios;
    }

    public int getWarningThresholdHighPrio() {
        return warningThresholdHighPrio;
    }

    public int getWarningThresholdNormalPrio() {
        return warningThresholdNormalPrio;
    }

    public int getWarningThresholdLowPrio() {
        return warningThresholdLowPrio;
    }

    public int getNewFailureThresholdAllPrios() {
        return newFailureThresholdAllPrios;
    }

    public int getNewFailureThresholdHighPrio() {
        return newFailureThresholdHighPrio;
    }

    public int getNewFailureThresholdNormalPrio() {
        return newFailureThresholdNormalPrio;
    }

    public int getNewFailureThresholdLowPrio() {
        return newFailureThresholdLowPrio;
    }

    public int getNewWarningThresholdAllPrios() {
        return newWarningThresholdAllPrios;
    }

    public int getNewWarningThresholdHighPrio() {
        return newWarningThresholdHighPrio;
    }

    public int getNewWarningThresholdNormalPrio() {
        return newWarningThresholdNormalPrio;
    }

    public int getNewWarningThresholdLowPrio() {
        return newWarningThresholdLowPrio;
    }

    /**
     * A Builder for the QualityGateConfig.
     */
    public static class QualityGateConfigBuilder {

        private int warningThresholdAllPrios = -1;
        private int warningThresholdHighPrio = -1;
        private int warningThresholdNormalPrio = -1;
        private int warningThresholdLowPrio = -1;
        private int failureThresholdAllPrios = -1;
        private int failureThresholdHighPrio = -1;
        private int failureThresholdNormalPrio = -1;
        private int failureThresholdLowPrio = -1;

        private int newWarningThresholdAllPrios = -1;
        private int newWarningThresholdHighPrio = -1;
        private int newWarningThresholdNormalPrio = -1;
        private int newWarningThresholdLowPrio = -1;
        private int newFailureThresholdAllPrios = -1;
        private int newFailureThresholdHighPrio = -1;
        private int newFailureThresholdNormalPrio = -1;
        private int newFailureThresholdLowPrio = -1;

        /**
         * Builds an immutable Instance of a QualtiGateConfig.
         *
         * @return instance of QualityGateConfig
         */
        public QualityGateConfig build() {
            return new QualityGateConfig(this);
        }

        /**
         * Sets the threshold of that property.
         *
         * @param failureThresholdAllPrios
         *         threshold for build.
         *
         * @return the builder
         */
        public QualityGateConfigBuilder failureThresholdAllPrios(final int failureThresholdAllPrios) {
            this.failureThresholdAllPrios = failureThresholdAllPrios;
            return this;
        }

        /**
         * Sets the threshold of that property.
         *
         * @param failureThresholdHighPrio
         *         threshold for build.
         *
         * @return the builder
         */
        public QualityGateConfigBuilder failureThresholdHighPrio(final int failureThresholdHighPrio) {
            this.failureThresholdHighPrio = failureThresholdHighPrio;
            return this;
        }

        /**
         * Sets the threshold of that property.
         *
         * @param failureThresholdNormalPrio
         *         threshold for build.
         *
         * @return the builder
         */
        public QualityGateConfigBuilder failureThresholdNormalPrio(final int failureThresholdNormalPrio) {
            this.failureThresholdNormalPrio = failureThresholdNormalPrio;
            return this;
        }

        /**
         * Sets the threshold of that property.
         *
         * @param failureThresholdLowPrio
         *         threshold for build.
         *
         * @return the builder
         */
        public QualityGateConfigBuilder failureThresholdLowPrio(final int failureThresholdLowPrio) {
            this.failureThresholdLowPrio = failureThresholdLowPrio;
            return this;
        }

        /**
         * Sets the threshold of that property.
         *
         * @param warningThresholdAllPrios
         *         threshold for build.
         *
         * @return the builder
         */
        public QualityGateConfigBuilder warningThresholdAllPrios(final int warningThresholdAllPrios) {
            this.warningThresholdAllPrios = warningThresholdAllPrios;
            return this;
        }

        /**
         * Sets the threshold of that property.
         *
         * @param warningThresholdHighPrio
         *         threshold for build.
         *
         * @return the builder
         */
        public QualityGateConfigBuilder warningThresholdHighPrio(final int warningThresholdHighPrio) {
            this.warningThresholdHighPrio = warningThresholdHighPrio;
            return this;
        }

        /**
         * Sets the threshold of that property.
         *
         * @param warningThresholdNormalPrio
         *         threshold for build.
         *
         * @return the builder
         */
        public QualityGateConfigBuilder warningThresholdNormalPrio(final int warningThresholdNormalPrio) {
            this.warningThresholdNormalPrio = warningThresholdNormalPrio;
            return this;
        }

        /**
         * Sets the threshold of that property.
         *
         * @param warningThresholdLowPrio
         *         threshold for build.
         *
         * @return the builder
         */
        public QualityGateConfigBuilder warningThresholdLowPrio(final int warningThresholdLowPrio) {
            this.warningThresholdLowPrio = warningThresholdLowPrio;
            return this;
        }

        /**
         * Sets the threshold of that property.
         *
         * @param newFailureThresholdAllPrios
         *         threshold for build.
         *
         * @return the builder
         */
        public QualityGateConfigBuilder newFailureThresholdAllPrios(final int newFailureThresholdAllPrios) {
            this.newFailureThresholdAllPrios = newFailureThresholdAllPrios;
            return this;
        }

        /**
         * Sets the threshold of that property.
         *
         * @param newFailureThresholdHighPrio
         *         threshold for build.
         *
         * @return the builder
         */
        public QualityGateConfigBuilder newFailureThresholdHighPrio(final int newFailureThresholdHighPrio) {
            this.newFailureThresholdHighPrio = newFailureThresholdHighPrio;
            return this;
        }

        /**
         * Sets the threshold of that property.
         *
         * @param newFailureThresholdNormalPrio
         *         threshold for build.
         *
         * @return the builder
         */
        public QualityGateConfigBuilder newFailureThresholdNormalPrio(final int newFailureThresholdNormalPrio) {
            this.newFailureThresholdNormalPrio = newFailureThresholdNormalPrio;
            return this;
        }

        /**
         * Sets the threshold of that property.
         *
         * @param newFailureThresholdLowPrio
         *         threshold for build.
         *
         * @return the builder
         */
        public QualityGateConfigBuilder newFailureThresholdLowPrio(final int newFailureThresholdLowPrio) {
            this.newFailureThresholdLowPrio = newFailureThresholdLowPrio;
            return this;
        }

        /**
         * Sets the threshold of that property.
         *
         * @param newWarningThresholdAllPrios
         *         threshold for build.
         *
         * @return the builder
         */
        public QualityGateConfigBuilder newWarningThresholdAllPrios(final int newWarningThresholdAllPrios) {
            this.newWarningThresholdAllPrios = newWarningThresholdAllPrios;
            return this;
        }

        /**
         * Sets the threshold of that property.
         *
         * @param newWarningThresholdHighPrio
         *         threshold for build.
         *
         * @return the builder
         */
        public QualityGateConfigBuilder newWarningThresholdHighPrio(final int newWarningThresholdHighPrio) {
            this.newWarningThresholdHighPrio = newWarningThresholdHighPrio;
            return this;
        }

        /**
         * Sets the threshold of that property.
         *
         * @param newWarningThresholdNormalPrio
         *         threshold for build.
         *
         * @return the builder
         */
        public QualityGateConfigBuilder newWarningThresholdNormalPrio(final int newWarningThresholdNormalPrio) {
            this.newWarningThresholdNormalPrio = newWarningThresholdNormalPrio;
            return this;
        }

        /**
         * Sets the threshold of that property.
         *
         * @param newWarningThresholdLowPrio
         *         threshold for build.
         *
         * @return the builder
         */
        public QualityGateConfigBuilder newWarningThresholdLowPrio(final int newWarningThresholdLowPrio) {
            this.newWarningThresholdLowPrio = newWarningThresholdLowPrio;
            return this;
        }
    }
}
