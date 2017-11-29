package io.jenkins.plugins.analysis.core.quality;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import hudson.model.Result;

/**
 * Tests the class {@link QualityGateEnforcer}.
 *
 * @author Ullrich Hafner
 */
class QualityGateEnforcerTest {

    @Test
    void shouldSucceedWhenGateDisabled() {
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate gate = mock(QualityGate.class);

        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        Result success = enforcer.evaluate(run, gate);

        assertThat(success).isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldUseGateResultWhenEnabled() {
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate gate = mock(QualityGate.class);

        when(gate.isEnabled()).thenReturn(true);
        when(gate.getResult(run)).thenReturn(Result.FAILURE);

        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        Result failure = enforcer.evaluate(run, gate);

        assertThat(failure).isEqualTo(Result.FAILURE);
    }
}
