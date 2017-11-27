package io.jenkins.plugins.analysis.core.quality;

import edu.hm.hafner.analysis.Priority;

/**
 * A threshold for the issue count of a specific {@link Priority}.
 *
 * @author Marcel Binder
 */
public class PriorityThreshold extends IssueCountThreshold {
    private final Priority priority;

    public PriorityThreshold(final Builder builder) {
        super(builder);
        this.priority = builder.priority;
    }

    public Priority getPriority() {
        return priority;
    }

    public static class Builder extends IssueCountThreshold.Builder<Builder> {
        private Priority priority;

        public static Builder priorityThreshold() {
            return new Builder();
        }

        public Builder withPriority(final Priority priority) {
            this.priority = priority;
            return thisBuilder();
        }

        @Override
        Builder thisBuilder() {
            return this;
        }

        @Override
        public IssueCountThreshold build() {
            return new PriorityThreshold(thisBuilder());
        }
    }
}
