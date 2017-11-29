package io.jenkins.plugins.analysis.core.quality;


import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import hudson.model.Result;

class AggregateQualityGateTest {

    private static QualityGate createGate(final Result result) {
        QualityGate gate = createGate();
        when(gate.isEnabled()).thenReturn(true);
        when(gate.getResult(any(StaticAnalysisRun.class))).thenReturn(result);
        return gate;
    }


    private static QualityGate createGate() {
        return mock(QualityGate.class);
    }

    private static QualityGate SUCCESS_GATE = createGate(Result.SUCCESS);
    private static QualityGate FAILURE_GATE = createGate(Result.FAILURE);
    private static QualityGate UNSTABLE_GATE = createGate(Result.UNSTABLE);
    private static QualityGate DISABLED_GATE = createGate();


    private static final StaticAnalysisRun RUN = mock(StaticAnalysisRun.class);

    @Test
    void shouldBeEnabledWhenOneOfInnerGatesEnabled() {
        QualityGate gate = new AggregateQualityGate(asList(DISABLED_GATE, SUCCESS_GATE, DISABLED_GATE));
        assertThat(gate.isEnabled())
                .isTrue();
    }

    @Test
    void shouldBeDisabledWhenAllInnerGatesDisabled() {
        QualityGate gate = new AggregateQualityGate(asList(DISABLED_GATE, DISABLED_GATE));
        assertThat(gate.isEnabled())
                .isFalse();
    }

    @Test
    void shouldSucceedWhenNoGateFails() {
        QualityGate gate = new AggregateQualityGate(asList(SUCCESS_GATE, DISABLED_GATE, SUCCESS_GATE));

        assertThat(gate.getResult(RUN))
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldReturnUnstableWhenUnstableGateFails() {
        QualityGate gate = new AggregateQualityGate(asList(SUCCESS_GATE, DISABLED_GATE, UNSTABLE_GATE, FAILURE_GATE));

        assertThat(gate.getResult(RUN))
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldReturnFailureWhenFailureGateFails() {
        QualityGate gate = new AggregateQualityGate(asList(SUCCESS_GATE, FAILURE_GATE, UNSTABLE_GATE));

        assertThat(gate.getResult(RUN))
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeDisabledWhenNoInnerGatePresent() {
        QualityGate gate = new AggregateQualityGate(Lists.emptyList());
        assertThat(gate.isEnabled())
                .isFalse();
    }
}