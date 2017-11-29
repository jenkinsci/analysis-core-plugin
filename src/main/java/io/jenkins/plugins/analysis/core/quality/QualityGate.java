package io.jenkins.plugins.analysis.core.quality;

import java.util.Collection;
import java.util.Collections;

import hudson.model.Result;

public interface QualityGate {

    /**
     * Checks whether the quality gate is enabled.
     *
     * @return gate enabled?
     */
    boolean isEnabled();

    /**
     * Retrieve the build result.
     *
     * @return build result
     */
    Result getResult(final StaticAnalysisRun run);

    /**
     * Combine gates, gates will be executed in-order. More important gates must be passed in, execution ends on first
     * hit. i.e. combine(FAILURE GATE, UNSTABLE GATE)
     *
     * @param gates
     *         to combine
     *
     * @return combined quality gate
     */
    static QualityGate combine(Collection<QualityGate> gates) {
        return new AggregateQualityGate(Collections.unmodifiableCollection(gates));
    }
}
