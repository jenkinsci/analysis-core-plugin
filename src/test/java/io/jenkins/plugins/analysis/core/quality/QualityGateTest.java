package io.jenkins.plugins.analysis.core.quality;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import hudson.model.Result;

class QualityGateTest {

    private static final StaticAnalysisRun RUN = mock(StaticAnalysisRun.class);
    private static final QualityGate FAILURE_GATE = createGate(Result.FAILURE);
    private static final QualityGate UNSTABLE_GATE = createGate(Result.UNSTABLE);

    @Test
    void shouldCombineGatesInOrder() {
        QualityGate failureFirst = QualityGate.combine(asList(FAILURE_GATE, UNSTABLE_GATE));

        assertThat(failureFirst.getResult(RUN))
                .isEqualTo(Result.FAILURE);

        QualityGate unstabledFirst = QualityGate.combine(asList(UNSTABLE_GATE, FAILURE_GATE));

        assertThat(unstabledFirst.getResult(RUN))
                .isEqualTo(Result.UNSTABLE);
    }

    private static QualityGate createGate(final Result result) {
        QualityGate gate = mock(QualityGate.class);
        when(gate.isEnabled()).thenReturn(true);
        when(gate.getResult(any(StaticAnalysisRun.class))).thenReturn(result);
        return gate;
    }
}
