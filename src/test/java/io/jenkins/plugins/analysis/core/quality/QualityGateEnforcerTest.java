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

        QualityGate qualityGate = new QualityGateBuilder().build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and no quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndTotalFailureAllPrioritiesQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setTotalFailedAllPriorities(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfTotalFailureAllPrioritiesThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setTotalFailedAllPriorities(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndTotalFailureHighPriorityQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setTotalFailedHighPriority(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfTotalFailureHighPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setTotalFailedHighPriority(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndTotalFailureNormalPriorityQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setTotalFailedNormalPriority(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfTotalFailureNormalPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setTotalFailedNormalPriority(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndTotalFailureLowPriorityQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setTotalFailedLowPriority(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfTotalFailureLowPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setTotalFailedLowPriority(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndTotalUnstableAllPrioritiesQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setTotalUnstableAllPriorities(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfTotalUnstableAllPrioritiesThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setTotalUnstableAllPriorities(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndTotalUnstableHighPriorityQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setTotalUnstableHighPriority(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfTotalUnstableHighPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setTotalUnstableHighPriority(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndTotalUnstableNormalPriorityQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setTotalUnstableNormalPriority(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfTotalUnstableNormalPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setTotalUnstableNormalPriority(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndTotalUnstableLowPriorityQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setTotalUnstableLowPriority(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfTotalUnstableLowPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setTotalUnstableLowPriority(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }


    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndNewFailureAllPrioritiesQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setNewFailedAllPriorities(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfNewFailureAllPrioritiesThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setNewFailedAllPriorities(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndNewFailureHighPriorityQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setNewFailedHighPriority(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfNewFailureHighPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setNewFailedHighPriority(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndNewFailureNormalPriorityQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setNewFailedNormalPriority(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfNewFailureNormalPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setNewFailedNormalPriority(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndNewFailureLowPriorityQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setNewFailedLowPriority(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfNewFailureLowPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setNewFailedLowPriority(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndNewUnstableAllPrioritiesQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setNewUnstableAllPriorities(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfNewUnstableAllPrioritiesThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setNewUnstableAllPriorities(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndNewUnstableHighPriorityQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setNewUnstableHighPriority(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfNewUnstableHighPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setNewUnstableHighPriority(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndNewUnstableNormalPriorityQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setNewUnstableNormalPriority(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfNewUnstableNormalPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setNewUnstableNormalPriority(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndNewUnstableLowPriorityQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setNewUnstableLowPriority(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfNewUnstableLowPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setNewUnstableLowPriority(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }
}
