package io.jenkins.plugins.analysis.core.quality;

import java.util.Collection;

import hudson.model.Result;

/**
 * Wraps multiple quality gates for easier consumption.
 */
class AggregateQualityGate implements QualityGate {

    private final Collection<QualityGate> gates;

    /**
     * Creates a new instance which uses the passed gates.
     *
     * @param gates
     *         used by the instance.
     */
    AggregateQualityGate(Collection<QualityGate> gates) {
        this.gates = gates;
    }

    @Override
    public Result getResult(final StaticAnalysisRun run) {
        return gates.stream()
                .filter(QualityGate::isEnabled)
                .map(gate -> gate.getResult(run))
                .filter(result -> !result.equals(Result.SUCCESS))
                .findFirst()
                .orElse(Result.SUCCESS);
    }

    @Override
    public boolean isEnabled() {
        return gates.stream().anyMatch(QualityGate::isEnabled);
    }
}
