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

    /**
     * MOch generator for StaticAnalysisRun.
     *
     * @param valueTotal
     *         value of Size
     *
     * @return mock
     */
    private StaticAnalysisRun getStaticAnalysisRunWithTotalSize(int valueTotal) {
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(valueTotal);
        return run;
    }


    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndNoQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        Result success = enforcer.evaluate(run);

        assertThat(success)
                .as("No issues and no quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndEmptyQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        enforcer.addQualityGate(new QualityGate(0, Result.FAILURE, QualityGatePriority.All, false));
        Result success = enforcer.evaluate(run);

        assertThat(success)
                .as("No issues and no quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndFailureQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        enforcer.addQualityGate(new QualityGate(1, Result.FAILURE, QualityGatePriority.All, false));

        Result success = enforcer.evaluate(run);

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
        enforcer.addQualityGate(new QualityGate(5, Result.FAILURE, QualityGatePriority.All, false));

        Result failure = enforcer.evaluate(run);

        assertThat(failure)
                .as("same number of issue abd threshold should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldSuccessIfIssuesSmallerThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = getStaticAnalysisRunWithTotalSize(4);

        enforcer.addQualityGate(new QualityGate(5, Result.FAILURE, QualityGatePriority.All, false));

        Result failure = enforcer.evaluate(run);

        assertThat(failure)
                .as("same number of issue abd threshold should return a FAILURE")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailIfIssuesBiggerThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = getStaticAnalysisRunWithTotalSize(6);

        enforcer.addQualityGate(new QualityGate(5, Result.FAILURE, QualityGatePriority.All, false));

        Result failure = enforcer.evaluate(run);

        assertThat(failure).as("One issue should return FAILURE").isEqualTo(Result.FAILURE);

    }

    @Test
    void shouldFailIfAndReturnUnstable() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = getStaticAnalysisRunWithTotalSize(6);

        enforcer.addQualityGate(new QualityGate(5, Result.UNSTABLE, QualityGatePriority.All, false));

        Result failure = enforcer.evaluate(run);

        assertThat(failure).as("One issue should return Unstable").isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldSucceedAndReturnNot() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = getStaticAnalysisRunWithTotalSize(4);

        enforcer.addQualityGate(new QualityGate(5, Result.UNSTABLE, QualityGatePriority.All, false));

        Result failure = enforcer.evaluate(run);

        assertThat(failure).as("One issue should return Unstable").isEqualTo(Result.SUCCESS);
    }

    @Test
    void addMultipleQualityEnforcerAndSucceedAndNoneShouldFail() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = getStaticAnalysisRunWithTotalSize(4);

        enforcer.addQualityGate(new QualityGate(5, Result.FAILURE, QualityGatePriority.All, false));
        enforcer.addQualityGate(new QualityGate(10, Result.UNSTABLE, QualityGatePriority.All, false));

        Result failure = enforcer.evaluate(run);

        assertThat(failure).as("Two Tests and just none should fail").isEqualTo(Result.SUCCESS);

    }

    @Test
    void addMultipleQualityEnforcerAndSuccessIfUnstable() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = getStaticAnalysisRunWithTotalSize(5);

        enforcer.addQualityGate(new QualityGate(10, Result.FAILURE, QualityGatePriority.All, false));
        enforcer.addQualityGate(new QualityGate(5, Result.UNSTABLE, QualityGatePriority.All, false));

        Result failure = enforcer.evaluate(run);

        assertThat(failure).as("Two Tests and just none should fail").isEqualTo(Result.UNSTABLE);

    }

    @Test
    void addMultipleQualityEnforcerAndSuccessIfFailed() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = getStaticAnalysisRunWithTotalSize(10);

        enforcer.addQualityGate(new QualityGate(10, Result.FAILURE, QualityGatePriority.All, false));
        enforcer.addQualityGate(new QualityGate(5, Result.UNSTABLE, QualityGatePriority.All, false));

        Result failure = enforcer.evaluate(run);

        assertThat(failure).as("Two Tests and just none should fail").isEqualTo(Result.FAILURE);

    }

    @Test
    void inspectJustTheNewIssuesAndSuccess() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = getStaticAnalysisRunWithTotalSize(100);
        when(run.getNewSize()).thenReturn(9);

        enforcer.addQualityGate(new QualityGate(10, Result.FAILURE, QualityGatePriority.All, true));

        Result failure = enforcer.evaluate(run);

        assertThat(failure).as("Two Tests and just none should fail").isEqualTo(Result.SUCCESS);

    }

    @Test
    void inspectJustTheNewIssuesAndFail() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = getStaticAnalysisRunWithTotalSize(100);
        when(run.getNewSize()).thenReturn(10);

        enforcer.addQualityGate(new QualityGate(10, Result.FAILURE, QualityGatePriority.All, true));

        Result failure = enforcer.evaluate(run);

        assertThat(failure).as("Two Tests and just none should fail").isEqualTo(Result.FAILURE);

    }

    @Test
    void inspectJustTheLowPrioIssues() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = getStaticAnalysisRunWithTotalSize(100);
        when(run.getTotalLowPrioritySize()).thenReturn(9).thenReturn(10);
        when(run.getTotalNormalPrioritySize()).thenReturn(100);
        when(run.getTotalHighPrioritySize()).thenReturn(100);

        enforcer.addQualityGate(new QualityGate(10, Result.FAILURE, QualityGatePriority.Low, false));
        Result failure = enforcer.evaluate(run);
        assertThat(failure).as("Two Tests and just none should fail").isEqualTo(Result.SUCCESS);
        failure = enforcer.evaluate(run);
        assertThat(failure).as("Two Tests and just none should fail").isEqualTo(Result.FAILURE);

    }

    @Test
    void inspectJustTheNormalPrioIssues() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = getStaticAnalysisRunWithTotalSize(100);
        when(run.getTotalLowPrioritySize()).thenReturn(100);
        when(run.getTotalNormalPrioritySize()).thenReturn(9).thenReturn(10);
        when(run.getTotalHighPrioritySize()).thenReturn(100);

        enforcer.addQualityGate(new QualityGate(10, Result.FAILURE, QualityGatePriority.Normal, false));
        Result failure = enforcer.evaluate(run);
        assertThat(failure).as("Two Tests and just none should fail").isEqualTo(Result.SUCCESS);
        failure = enforcer.evaluate(run);
        assertThat(failure).as("Two Tests and just none should fail").isEqualTo(Result.FAILURE);

    }

    @Test
    void inspectJustTheHighPrioIssues() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = getStaticAnalysisRunWithTotalSize(100);
        when(run.getTotalLowPrioritySize()).thenReturn(100);
        when(run.getTotalNormalPrioritySize()).thenReturn(100);
        when(run.getTotalHighPrioritySize()).thenReturn(9).thenReturn(10);

        enforcer.addQualityGate(new QualityGate(10, Result.FAILURE, QualityGatePriority.High, false));
        Result failure = enforcer.evaluate(run);
        assertThat(failure).as("Two Tests and just none should fail").isEqualTo(Result.SUCCESS);
        failure = enforcer.evaluate(run);
        assertThat(failure).as("Two Tests and just none should fail").isEqualTo(Result.FAILURE);

    }

    @Test
    void inspectJustTheNewLowPrioIssues() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = getStaticAnalysisRunWithTotalSize(100);
        when(run.getNewLowPrioritySize()).thenReturn(9).thenReturn(10);
        when(run.getNewNormalPrioritySize()).thenReturn(100);
        when(run.getNewHighPrioritySize()).thenReturn(100);

        enforcer.addQualityGate(new QualityGate(10, Result.FAILURE, QualityGatePriority.Low, true));
        Result failure = enforcer.evaluate(run);
        assertThat(failure).as("Two Tests and just none should fail").isEqualTo(Result.SUCCESS);
        failure = enforcer.evaluate(run);
        assertThat(failure).as("Two Tests and just none should fail").isEqualTo(Result.FAILURE);

    }

    @Test
    void inspectJustTheNewNormalPrioIssues() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = getStaticAnalysisRunWithTotalSize(100);
        when(run.getNewLowPrioritySize()).thenReturn(100);
        when(run.getNewNormalPrioritySize()).thenReturn(9).thenReturn(10);
        when(run.getNewHighPrioritySize()).thenReturn(100);

        enforcer.addQualityGate(new QualityGate(10, Result.FAILURE, QualityGatePriority.Normal, true));
        Result failure = enforcer.evaluate(run);
        assertThat(failure).as("Two Tests and just none should fail").isEqualTo(Result.SUCCESS);
        failure = enforcer.evaluate(run);
        assertThat(failure).as("Two Tests and just none should fail").isEqualTo(Result.FAILURE);

    }

    @Test
    void inspectJustTheNewHighPrioIssues() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = getStaticAnalysisRunWithTotalSize(100);
        when(run.getNewLowPrioritySize()).thenReturn(100);
        when(run.getNewNormalPrioritySize()).thenReturn(100);
        when(run.getNewHighPrioritySize()).thenReturn(9).thenReturn(10);

        enforcer.addQualityGate(new QualityGate(10, Result.FAILURE, QualityGatePriority.High, true));
        Result failure = enforcer.evaluate(run);
        assertThat(failure).as("Two Tests and just none should fail").isEqualTo(Result.SUCCESS);
        failure = enforcer.evaluate(run);
        assertThat(failure).as("Two Tests and just none should fail").isEqualTo(Result.FAILURE);

    }

}
