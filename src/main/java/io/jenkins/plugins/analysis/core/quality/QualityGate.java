package io.jenkins.plugins.analysis.core.quality;

import java.util.Collections;
import java.util.List;

import static com.google.common.collect.Lists.*;

/**
 * Defines quality gates for a static analysis run.
 *
 * @author Ullrich Hafner
 */
public class QualityGate {
    private final List<IssueCountThreshold> thresholds;

    QualityGate(final Builder builder) {
        this.thresholds = newArrayList(builder.thresholds);
    }

    /**
     * @return the {@link IssueCountThreshold thresholds} of this {@link QualityGate quality gate}
     */
    List<IssueCountThreshold> getThresholds() {
        return Collections.unmodifiableList(thresholds);
    }

    /**
     * A builder for a {@link QualityGate}.
     */
    public static class Builder {
        private final List<IssueCountThreshold> thresholds;

        Builder() {
            this.thresholds = newArrayList();
        }

        /**
         * Create a new {@link Builder builder} for a {@link QualityGate quality gate}.
         *
         * @return the newly created {@link Builder builder}
         */
        public static Builder qualityGate() {
            return new Builder();
        }

        public Builder withThreshold(final IssueCountThreshold threshold) {
            thresholds.add(threshold);
            return this;
        }

        /**
         * Build a new {@link QualityGate} with the provided thresholds.
         *
         * @return the newly created {@link QualityGate}
         */
        public QualityGate build() {
            return new QualityGate(this);
        }
    }
}
