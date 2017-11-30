package io.jenkins.plugins.analysis.core.quality;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;

import static com.google.common.primitives.Ints.asList;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import hudson.model.Result;
import hudson.plugins.analysis.util.model.Priority;

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
                .as("No issues and no quality gate should always be a SUCCESS.")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndHighPriorityFailureQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setHighPriorityFailure(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and high priority quality gate failure should always be a SUCCESS.")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndNormalPriorityFailureQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setNormalPriorityFailure(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and normal priority quality gate failure should always be a SUCCESS.")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndLowPriorityFailureQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setLowPriorityFailure(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and low priority quality gate failure should always be a SUCCESS.")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndHighPriorityUnstableQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setHighPriorityUnstable(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and high priority quality gate unstable should always be a SUCCESS.")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndNormalPriorityUnstableQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setNormalPriorityUnstable(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and normal priority quality gate unstable should always be a SUCCESS.")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndLowPriorityUnstableQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setLowPriorityUnstable(1).build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and low priority quality gate unstable should always be a SUCCESS.")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndAllPrioritysQualityGateIsSet(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().setHighPriorityFailure(2).setHighPriorityUnstable(1)
                .setNormalPriorityFailure(2).setNormalPriorityUnstable(1)
                .setLowPriorityFailure(2).setLowPriorityUnstable(1).build();
        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and all priority gates are set should always be a SUCCESS.")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfHighQualityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setHighPriorityFailure(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One high failure issue should return FAILURE if high failure quality gate is set.")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldFailBuildIfNormalQualityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalNormalPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setNormalPriorityFailure(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One normal failure issue should return FAILURE if normal failure quality gate is set.")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldFailBuildIfLowQualityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalLowPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setLowPriorityFailure(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One low failure issue should return FAILURE if low failure quality gate is set.")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeUnstableBuildIfHighQualityUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setHighPriorityUnstable(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One high failure issue should return UNSTABLE if high unstable quality gate is set.")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeUnstableBuildIfNormalQualityUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalNormalPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setNormalPriorityUnstable(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One normal failure issue should return UNSTABLE if normal unstable quality gate is set.")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeUnstableBuildIfLowQualityUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalLowPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setLowPriorityUnstable(1).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One low failure issue should return UNSTABLE if low unstable quality gate is set.")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldSuccessIfNoFailureTresholdIsSet(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("One issue without threshold should return a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldSuccessIfWarningWithLowPriorityAndThresholdIsHighPriority(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);
        when(run.getTotalHighPrioritySize()).thenReturn(0);
        when(run.getTotalLowPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder().setHighPriorityFailure(1).build();

        Result success = enforcer.evaluate(run, qualityGate);
        assertThat(success)
                .as("One issue with low priority but threshold is high priority should return a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldReturnExceptionWhenHighPriorityFailureIsNegative(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        assertThatThrownBy(()->{new QualityGateBuilder().setHighPriorityFailure(-1).build();})
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldReturnExceptionWhenHighPriorityUnstableIsNegative(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        assertThatThrownBy(()->{new QualityGateBuilder().setHighPriorityUnstable(-1).build();})
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldReturnExceptionWhenNormalPriorityFailureIsNegative(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);
        when(run.getTotalNormalPrioritySize()).thenReturn(1);

        assertThatThrownBy(()->{new QualityGateBuilder().setNormalPriorityFailure(-1).build();})
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldReturnExceptionWhenNormalPriorityUnstableIsNegative(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);
        when(run.getTotalNormalPrioritySize()).thenReturn(1);

        assertThatThrownBy(()->{new QualityGateBuilder().setNormalPriorityUnstable(-1).build();})
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldReturnExceptionWhenLowPriorityFailureIsNegative(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);
        when(run.getTotalLowPrioritySize()).thenReturn(1);

        assertThatThrownBy(()->{new QualityGateBuilder().setLowPriorityFailure(-1).build();})
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void shouldReturnExceptionWhenLowPriorityUnstableIsNegative(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);
        when(run.getTotalLowPrioritySize()).thenReturn(1);

        assertThatThrownBy(()->{new QualityGateBuilder().setLowPriorityUnstable(-1).build();})
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void schouldReturnExceptionWhenHighPriorityFailureSmallerThanHighPriorityUnstable(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        assertThatThrownBy(()->{new QualityGateBuilder().setHighPriorityFailure(1).setHighPriorityUnstable(2).build();})
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void schouldReturnExceptionWhenNormalPriorityFailureSmallerThanNormalPriorityUnstable(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        assertThatThrownBy(()->{new QualityGateBuilder().setNormalPriorityFailure(1).setNormalPriorityUnstable(2).build();})
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void schouldReturnExceptionWhenLowPriorityFailureSmallerThanLowPriorityUnstable(){
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        assertThatThrownBy(()->{new QualityGateBuilder().setLowPriorityFailure(1).setLowPriorityUnstable(2).build();})
                .isInstanceOf(IllegalArgumentException.class);
    }
}
