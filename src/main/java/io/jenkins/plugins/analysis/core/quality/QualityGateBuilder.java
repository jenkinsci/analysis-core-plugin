package io.jenkins.plugins.analysis.core.quality;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Function;

import static java.util.Collections.*;

import hudson.model.Result;

/**
 * Builds an aggregated quality gate from the parameters supplied.
 */
public class QualityGateBuilder {

    /**
     * Creates a quality gate which returns a failure result on hit.
     *
     * @return builder building gate with failure result
     */
    public static QualityGateBuilder createFailure() {
        return new QualityGateBuilder(Result.FAILURE);
    }

    /**
     * Creates a quality gate which returns an unstable result on hit.
     *
     * @return builder building gate with unstable result
     */
    public static QualityGateBuilder createUnstable() {
        return new QualityGateBuilder(Result.UNSTABLE);
    }

    private final Collection<ThresholdQualityGate> gates = new LinkedList<ThresholdQualityGate>();

    private final Result result;

    private QualityGateBuilder(final Result result) {
        this.result = result;
    }

    /**
     * Add a quality gate for total threshold.
     *
     * @param threshold
     *         after which build fails.
     *
     * @return this
     */
    public QualityGateBuilder setTotalThreshold(final int threshold) {
        return createGate(StaticAnalysisRun::getTotalSize, threshold);
    }

    /**
     * Add a quality gate for total high threshold.
     *
     * @param threshold
     *         after which build fails.
     *
     * @return this
     */
    public QualityGateBuilder setTotalHighThreshold(final int threshold) {
        return createGate(StaticAnalysisRun::getTotalHighPrioritySize, threshold);
    }

    /**
     * Add a quality gate for total normal threshold.
     *
     * @param threshold
     *         after which build fails.
     *
     * @return this
     */
    public QualityGateBuilder setTotalNormalThreshold(final int threshold) {
        return createGate(StaticAnalysisRun::getTotalNormalPrioritySize, threshold);
    }

    /**
     * Add a quality gate for total low threshold.
     *
     * @param threshold
     *         after which build fails.
     *
     * @return this
     */
    public QualityGateBuilder setTotalLowThreshold(final int threshold) {
        return createGate(StaticAnalysisRun::getTotalLowPrioritySize, threshold);
    }

    /**
     * Add a quality gate for new threshold.
     *
     * @param threshold
     *         after which build fails.
     *
     * @return this
     */
    public QualityGateBuilder setTotalNewThreshold(final int threshold) {
        return createGate(StaticAnalysisRun::getNewSize, threshold);
    }

    /**
     * Add a quality gate for new high threshold.
     *
     * @param threshold
     *         after which build fails.
     *
     * @return this
     */
    public QualityGateBuilder setNewHighTheshold(final int threshold) {
        return createGate(StaticAnalysisRun::getNewHighPrioritySize, threshold);
    }

    /**
     * Add a quality gate for new normal threshold.
     *
     * @param threshold
     *         after which build fails.
     *
     * @return this
     */
    public QualityGateBuilder setNewNormalThreshold(final int threshold) {
        return createGate(StaticAnalysisRun::getNewNormalPrioritySize, threshold);
    }

    /**
     * Add a quality gate for new low threshold.
     *
     * @param threshold
     *         after which build fails.
     *
     * @return this
     */
    public QualityGateBuilder setNewLowThreshold(final int threshold) {
        return createGate(StaticAnalysisRun::getNewLowPrioritySize, threshold);
    }

    private QualityGateBuilder createGate(final Function<StaticAnalysisRun, Integer> propertySupplier, final int threshold) {
        gates.add(new ThresholdQualityGate(result, propertySupplier, threshold));

        return this;
    }

    /**
     * Constructs the quality gate instance from given params.
     *
     * @return quality gate instance
     */
    public QualityGate build() {
        return new AggregateQualityGate(unmodifiableCollection(gates));
    }
}
