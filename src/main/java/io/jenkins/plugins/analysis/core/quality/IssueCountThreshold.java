package io.jenkins.plugins.analysis.core.quality;

import hudson.model.Result;

/**
 * A threshold for the issue count.
 *
 * @author Marcel Binder
 */
public abstract class IssueCountThreshold {
    private final int value;
    private final boolean onlyForNewIssues;
    private final Result result;

    IssueCountThreshold(final Builder builder) {
        this.value = builder.value;
        this.onlyForNewIssues = builder.onlyForNewIssues;
        this.result = builder.result;
    }

    public int getValue() {
        return value;
    }

    public boolean isOnlyForNewIssues() {
        return onlyForNewIssues;
    }

    public Result getResult() {
        return result;
    }

    static abstract class Builder<BuilderImpl extends Builder<BuilderImpl>> {
        private int value;
        private boolean onlyForNewIssues;
        private Result result;

        Builder() {
        }

        abstract BuilderImpl thisBuilder();

        public BuilderImpl withValue(final int value) {
            this.value = value;
            return thisBuilder();
        }

        public BuilderImpl withOnlyForNewIssues(final boolean onlyForNewIssues) {
            this.onlyForNewIssues = onlyForNewIssues;
            return thisBuilder();
        }

        public BuilderImpl withResult(final Result result) {
            this.result = result;
            return thisBuilder();
        }

        public abstract IssueCountThreshold build();
    }
}
