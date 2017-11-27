package io.jenkins.plugins.analysis.core.quality;

/**
 * A threshold for the issues count of all priorities.
 *
 * @author Marcel Binder
 */
public class AllPrioritiesThreshold extends IssueCountThreshold {
    public AllPrioritiesThreshold(final Builder builder) {
        super(builder);
    }

    public static class Builder extends IssueCountThreshold.Builder<Builder> {
        public static Builder allPrioritiesThreshold() {
            return new Builder();
        }

        @Override
        Builder thisBuilder() {
            return this;
        }

        @Override
        public IssueCountThreshold build() {
            return new AllPrioritiesThreshold(thisBuilder());
        }
    }
}
