package io.jenkins.plugins.analysis.core.quality;

import java.util.function.Function;

import hudson.model.Result;


/**
 * Quality gate which is reached after a certain threshold is reached.
 */
class ThresholdQualityGate implements QualityGate {

    private final Function<StaticAnalysisRun, Integer> propertySupplier;

    private final int threshold;

    private final Result result;


    /**
     * Creates a new instance of @link{ThresholdQualityGate}
     *
     * @param propertySupplier
     *         supplies the property the gate acts upon.
     * @param threshold
     *         value after which gate is reached
     */
    ThresholdQualityGate(final Result result, final Function<StaticAnalysisRun, Integer> propertySupplier, final int threshold) {
        this.propertySupplier = propertySupplier;
        this.threshold = threshold;
        this.result = result;
    }

    @Override
    public boolean isEnabled() {
        return threshold > 0;
    }

    @Override
    public Result getResult(final StaticAnalysisRun run) {
        return propertySupplier.apply(run) >= threshold ? result : Result.SUCCESS;
    }
}
