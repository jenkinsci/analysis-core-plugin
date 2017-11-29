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
        QualityGate qualityGate = new QualityGate(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and no quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndTotalFailureAndTotalUnstableQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate(1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0);

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfTotalFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue more than threshold allows should end in failure.")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldSucceedWhenLessIssuesThanTotalFailureThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(3);

        QualityGate qualityGate = new QualityGate(4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);

        Result success = enforcer.evaluate(run,qualityGate);

        assertThat(success)
                .as("One Issue less then threshold should return a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailIfMoreHighPriorityIssuesThanHighPriorityFailureThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalHighPrioritySize()).thenReturn(3);

        QualityGate qualityGate = new QualityGate(4,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0);

        Result failure = enforcer.evaluate(run,qualityGate);

        assertThat(failure)
                .as("One high priority issue more than threshold should end in failure.")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldSucceedIfNoHighPriorityIssuesAndSetHighPriorityFailureThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalHighPrioritySize()).thenReturn(0);

        QualityGate qualityGate = new QualityGate(4,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0);

        Result success = enforcer.evaluate(run,qualityGate);

        assertThat(success)
                .as("zero high priority issue and a set high-priority-threshold should end in success.")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldSucceedIfNoNormalPriorityIssuesAndSetNormalPriorityFailureThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalNormalPrioritySize()).thenReturn(0);

        QualityGate qualityGate = new QualityGate(4,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0);

        Result success = enforcer.evaluate(run,qualityGate);

        assertThat(success)
                .as("zero normal priority issue and a set normal-priority-threshold should end in success.")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailIfMoreNormalPriorityIssuesThanNormalPriorityFailureThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalNormalPrioritySize()).thenReturn(3);

        QualityGate qualityGate = new QualityGate(4,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0);

        Result failure = enforcer.evaluate(run,qualityGate);

        assertThat(failure)
                .as("One normal priority issue more than threshold should end in failure.")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldSucceedIfNoLowPriorityIssuesAndSetLowPriorityFailureThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalLowPrioritySize()).thenReturn(0);

        QualityGate qualityGate = new QualityGate(4,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0);

        Result success = enforcer.evaluate(run,qualityGate);

        assertThat(success)
                .as("zero low priority issue and a set low-priority-threshold should end in success.")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailIfMoreLowPriorityIssuesThanLowPriorityFailureThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalLowPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGate(4,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0);

        Result failure = enforcer.evaluate(run,qualityGate);

        assertThat(failure)
                .as("One low priority issue more than threshold should end in failure.")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeUnstableIfMoreIssuesThanTotalUnstableThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(2);

        QualityGate qualityGate = new QualityGate(3,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0);

        Result unstable = enforcer.evaluate(run,qualityGate);

        assertThat(unstable)
                .as("One issue more than unstable-threshold and one less than failure-threshold should end in unstable.")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeUnstableIfMoreHighPriorityIssuesThanThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalHighPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGate(3,3,0,0,2,2,0,0,0,0,0,0,0,0,0,0);

        Result unstable = enforcer.evaluate(run,qualityGate);

        assertThat(unstable)
                .as("One high priority issue more than unstable-threshold and one less than failure-threshold should end in unstable.")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeUnstableIfMoreNormalPriorityIssuesThanThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalNormalPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGate(3,0,3,0,2,0,2,0,0,0,0,0,0,0,0,0);

        Result unstable = enforcer.evaluate(run,qualityGate);

        assertThat(unstable)
                .as("One normal priority issue more than unstable-threshold and one less than failure-threshold should end in unstable.")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeUnstableIfMoreLowPriorityIssuesThanThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalLowPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGate(3,0,0,3,2,0,0,2,0,0,0,0,0,0,0,0);

        Result unstable = enforcer.evaluate(run,qualityGate);

        assertThat(unstable)
                .as("One high priority issue more than unstable-threshold and one less than failure-threshold should end in unstable.")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldFailIfMoreNewIssuesThanThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewSize()).thenReturn(2);

        QualityGate qualityGate = new QualityGate(0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0);

        Result failure = enforcer.evaluate(run,qualityGate);

        assertThat(failure)
                .as("One new issue more than threshold should end in failure.")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldFailIfMoreNewHighPriorityIssuesThanThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewHighPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGate(0,0,0,0,0,0,0,0,3,2,0,0,0,0,0,0);

        Result failure = enforcer.evaluate(run,qualityGate);

        assertThat(failure)
                .as("One new high priority issue more than threshold should end in failure.")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldFailIfMoreNewNormalPriorityIssuesThanThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewNormalPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGate(0,0,0,0,0,0,0,0,3,0,2,0,0,0,0,0);

        Result failure = enforcer.evaluate(run,qualityGate);

        assertThat(failure)
                .as("One new normal priority issue more than threshold should end in failure.")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldFailIfMoreNewLowPriorityIssuesThanThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewLowPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGate(0,0,0,0,0,0,0,0,3,0,0,2,0,0,0,0);

        Result failure = enforcer.evaluate(run,qualityGate);

        assertThat(failure)
                .as("One new low priority issue more than threshold should end in failure.")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeUnstableIfMoreNewIssuesThanThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewSize()).thenReturn(2);

        QualityGate qualityGate = new QualityGate(0,0,0,0,0,0,0,0,3,0,0,0,2,0,0,0);

        Result unstable = enforcer.evaluate(run,qualityGate);

        assertThat(unstable)
                .as("One issue more than unstable-threshold and one less than failure-threshold should end in unstable.")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeUnstableIfMoreNewHighPriorityIssuesThanThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewHighPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGate(0,0,0,0,0,0,0,0,4,0,0,0,3,2,0,0);

        Result unstable = enforcer.evaluate(run,qualityGate);

        assertThat(unstable)
                .as("One high priority issue more than unstable-threshold and one less than failure-threshold should end in unstable.")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeUnstableIfMoreNewNormalPriorityIssuesThanThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewNormalPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGate(0,0,0,0,0,0,0,0,4,0,0,0,3,0,2,0);

        Result unstable = enforcer.evaluate(run,qualityGate);

        assertThat(unstable)
                .as("One normal priority issue more than unstable-threshold and one less than failure-threshold should end in unstable.")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeUnstableIfMoreNewLowPriorityIssuesThanThreshold(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewLowPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGate(0,0,0,0,0,0,0,0,4,0,0,0,3,0,0,2);

        Result unstable = enforcer.evaluate(run,qualityGate);

        assertThat(unstable)
                .as("One low priority issue more than unstable-threshold and one less than failure-threshold should end in unstable.")
                .isEqualTo(Result.UNSTABLE);
    }

}
