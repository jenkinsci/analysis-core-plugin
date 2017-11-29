package io.jenkins.plugins.analysis.core.quality;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import hudson.model.Result;

class ThresholdQualityGateTest {

    private static final int THRESHOLD = 10;
    private static final int BELOW_THESHOLD = 9;
    private static final int ABOVE_THREHOLD = 11;

    @Test
    public void shouldReturnSuccessWhenBelowThreshold() {
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewSize()).thenReturn(BELOW_THESHOLD);

        QualityGate gate = new ThresholdQualityGate(Result.UNSTABLE, StaticAnalysisRun::getNewSize, THRESHOLD);

        assertThat(gate.getResult(run))
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    public void shouldReturnPassedResultWhenAtThreshold() {
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewNormalPrioritySize()).thenReturn(THRESHOLD);

        QualityGate gate = new ThresholdQualityGate(Result.FAILURE, StaticAnalysisRun::getNewNormalPrioritySize, THRESHOLD);

        assertThat(gate.getResult(run))
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldReturnPassedResultWhenAboveThreshold() {
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalHighPrioritySize()).thenReturn(ABOVE_THREHOLD);

        QualityGate gate = new ThresholdQualityGate(Result.UNSTABLE, StaticAnalysisRun::getTotalHighPrioritySize, THRESHOLD);

        assertThat(gate.getResult(run))
                .isEqualTo(Result.UNSTABLE);
    }
}