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
    void shouldBeSuccessfulWhenNoIssuesPresentAndNoQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and no quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndFailureQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate(1);

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        when(run.getTotalSize()).thenReturn(5);


        QualityGate qualityGate = new QualityGate(5);

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("same number of issue abd threshold should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldSuccessIfIssuesSamlerThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(4);

        QualityGate qualityGate = new QualityGate(5);

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("same number of issue abd threshold should return a FAILURE")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailIfIssuesBiggerThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(6);

        QualityGate qualityGate = new QualityGate(5);

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure).as("One issue should return FAILURE").isEqualTo(Result.FAILURE);

        assertThat(failure)
                .as("same number of issue abd threshold should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }
}
