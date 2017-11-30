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
        QualityGateBuilder builder = new QualityGateBuilder();

        QualityGate qualityGate = builder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success).as("No issues and no quality gate should always be a SUCCESS").isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndFailureTotalQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setFailureTotalThreshold(1);

        QualityGate qualityGate = builder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success).as("No issues and failure quality gate should always be a SUCCESS").isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfFailureTotalThresholdQuantityIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setFailureTotalThreshold(1);

        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure).as("One issue should return FAILURE").isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndFailureLowPriorityQuantityIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setFailureLowPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success).as("No issues and failure low priority quality gate is set should always be a SUCCESS").isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfFailureLowPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalLowPrioritySize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setFailureLowPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure).as("One issue  with low priority should return FAILURE").isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndFailureNormalPriorityQuantityIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setFailureNormalPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success).as("No issues and failure normal priority quality gate is set should always be a SUCCESS").isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfFailureNormalPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalNormalPrioritySize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setFailureNormalPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure).as("One issue with normal priority  should return FAILURE").isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndFailureHighPriorityQuantityIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setFailureHighPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success).as("No issues and failure high priority quality gate is set should always be a SUCCESS").isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfFailureHighPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setFailureHighPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure).as("One issue  with high priority should return FAILURE").isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndFailureNewTotalQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setFailureNewTotalThreshold(1);

        QualityGate qualityGate = builder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success).as("No issues and failure quality gate should always be a SUCCESS").isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfFailureNewTotalThresholdQuantityIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewSize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setFailureNewTotalThreshold(1);

        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure).as("One new issue should return FAILURE").isEqualTo(Result.FAILURE);

    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndFailureNewLowPriorityQuantityIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setFailureNewLowPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success).as("No issues and failure new low priority quality gate is set should always be a SUCCESS").isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfFailureNewLowPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewLowPrioritySize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setFailureNewLowPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure).as("One new issue with low priority should return FAILURE").isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndFailureNewNormalPriorityQuantityIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setFailureNewNormalPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success).as("No issues and failure normal priority quality gate is set should always be a SUCCESS").isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfFailureNewNormalPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewNormalPrioritySize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setFailureNewNormalPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure).as("One issue with normal priority  should return FAILURE").isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndFailureNewHighPriorityQuantityIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setFailureNewHighPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success).as("No issues and failure high priority quality gate is set should always be a SUCCESS").isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfFailureNewHighPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setFailureNewHighPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure).as("One issue  with high priority should return FAILURE").isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndUnstableTotalQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setUnstableTotalThreshold(1);

        QualityGate qualityGate = builder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success).as("No issues and unstable quality gate should always be a SUCCESS").isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfUnstableTotalThresholdQuantityIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setUnstableTotalThreshold(1);

        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure).as("One issue should return UNSTABLE").isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndUnstableLowPriorityQuantityIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setUnstableNewHighPriorityThreshold(1);
        QualityGate qualityGate = builder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success).as("No issues and unstable low priority quality gate is set should always be a SUCCESS").isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfUnstableLowPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalLowPrioritySize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setUnstableLowPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure).as("One issue with low priority should return UNSTABLE").isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndUnstableNormalPriorityQuantityIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setUnstableNormalPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success).as("No issues and failure normal priority quality gate is set should always be a SUCCESS").isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfUnstableNormalPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalNormalPrioritySize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setUnstableNormalPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure).as("One issue with normal priority  should return UNSTABLE").isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndUnstableHighPriorityQuantityIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setUnstableHighPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success).as("No issues and unstable high priority quality gate is set should always be a SUCCESS").isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfUnstableHighPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setUnstableHighPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);
        assertThat(failure).as("One issue  with high priority should return UNSTABLE").isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndUnstableNewTotalQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setUnstableNewTotalThreshold(1);

        QualityGate qualityGate = builder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success).as("No issues and  new unstable quality gate should always be a SUCCESS").isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfUnstableNewTotalThresholdQuantityIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewSize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setUnstableNewTotalThreshold(1);

        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure).as("One new issue should return UNSTABLE").isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndUnstableNewLowPriorityQuantityIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setUnstableNewLowPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success).as("No issues and unstable new low priority quality gate is set should always be a SUCCESS").isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfUnstableNewLowPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewLowPrioritySize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setUnstableNewLowPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure).as("One new issue with low priority should return UNSTABLE").isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndUnstableNewNormalPriorityQuantityIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setUnstableNewNormalPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success).as("No issues and unstable normal priority quality gate is set should always be a SUCCESS").isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfUnstableNewNormalPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewNormalPrioritySize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setUnstableNewNormalPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure).as("One issue with normal priority  should return UNSTABLE").isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndUnstableNewHighPriorityQuantityIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setUnstableNewHighPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success).as("No issues and unstable high priority quality gate is set should always be a SUCCESS").isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfUnstableNewHighPriorityThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setUnstableNewHighPriorityThreshold(1);

        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure).as("One issue with high priority should return UNSTABLE").isEqualTo(Result.UNSTABLE);
    }
}
