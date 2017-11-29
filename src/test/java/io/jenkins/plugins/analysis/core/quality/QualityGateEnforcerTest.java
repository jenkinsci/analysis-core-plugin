package io.jenkins.plugins.analysis.core.quality;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import hudson.model.Result;

/**
 * Tests the class {@link QualityGateEnforcer}.
 */
class QualityGateEnforcerTest {
    /**
     * Tests whether a build with no issues and no quality gate set is a SUCCESS.
     */
    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndNoQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().createQualityGate();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and no quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    /**
     * Tests whether a build with no issues and a quality gate set is a SUCCESS.
     */
    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndTotalFailureAndTotalUnstableQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder()
                .setTotalFailureThreshold(1)
                .setTotalUnstableThreshold(1)
                .createQualityGate();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    /**
     * Tests whether a build with threshold to failure set and the number of total issues set to the same value fails.
     */
    @Test
    void shouldFailBuildIfTotalFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder()
                .setTotalFailureThreshold(1)
                .createQualityGate();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue more than threshold allows should end in failure.")
                .isEqualTo(Result.FAILURE);
    }

    /**
     * Tests whether a build with a number of issues, smaller than the threshold set is a SUCCESS.
     */
    @Test
    void shouldSucceedWhenLessIssuesThanTotalFailureThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(3);

        QualityGate qualityGate = new QualityGateBuilder()
                .setTotalFailureThreshold(4)
                .createQualityGate();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("One Issue less then threshold should return a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    /**
     * Tests whether a build with more high priority issues than the value of the high priority threshold fails.
     */
    @Test
    void shouldFailIfMoreHighPriorityIssuesThanHighPriorityFailureThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalHighPrioritySize()).thenReturn(3);

        QualityGate qualityGate = new QualityGateBuilder()
                .setTotalFailureThreshold(4)
                .setHighPriorityFailureThreshold(2)
                .createQualityGate();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One high priority issue more than threshold should end in failure.")
                .isEqualTo(Result.FAILURE);
    }

    /**
     * Tests whether a build with more normal priority issues than the value of the normal priority threshold fails.
     */
    @Test
    void shouldFailIfMoreNormalPriorityIssuesThanNormalPriorityFailureThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalNormalPrioritySize()).thenReturn(3);

        QualityGate qualityGate = new QualityGateBuilder()
                .setTotalFailureThreshold(4)
                .setNormalPriorityFailureThreshold(2)
                .createQualityGate();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One normal priority issue more than threshold should end in failure.")
                .isEqualTo(Result.FAILURE);
    }

    /**
     * Tests whether a build with more low priority issues than the value of the low priority threshold fails.
     */
    @Test
    void shouldFailIfMoreLowPriorityIssuesThanLowPriorityFailureThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalLowPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGateBuilder()
                .setTotalFailureThreshold(4)
                .setLowPriorityFailureThreshold(1)
                .createQualityGate();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One low priority issue more than threshold should end in failure.")
                .isEqualTo(Result.FAILURE);
    }

    /**
     * Tests whether a build with more issues than the unstable threshold but less than the failure threshold is
     * UNSTABLE.
     */
    @Test
    void shouldBeUnstableIfMoreIssuesThanTotalUnstableThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(2);

        QualityGate qualityGate = new QualityGateBuilder()
                .setTotalFailureThreshold(3)
                .setTotalUnstableThreshold(2)
                .createQualityGate();

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertThat(unstable)
                .as("One issue more than unstable-threshold and one less than failure-threshold should end in unstable.")
                .isEqualTo(Result.UNSTABLE);
    }

    /**
     * Tests whether a build with more high priority issues than the unstable threshold of high priority issues is
     * UNSTABLE.
     */
    @Test
    void shouldBeUnstableIfMoreHighPriorityIssuesThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalHighPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGateBuilder()
                .setTotalFailureThreshold(3)
                .setHighPriorityFailureThreshold(3)
                .setTotalUnstableThreshold(2)
                .setHighPriorityUnstableThreshold(2)
                .createQualityGate();

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertThat(unstable)
                .as("One high priority issue more than unstable-threshold and one less than failure-threshold should end in unstable.")
                .isEqualTo(Result.UNSTABLE);
    }

    /**
     * Tests whether a build with more normal priority issues than the unstable threshold of normal priority issues is
     * UNSTABLE.
     */
    @Test
    void shouldBeUnstableIfMoreNormalPriorityIssuesThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalNormalPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGateBuilder()
                .setTotalFailureThreshold(3)
                .setNormalPriorityFailureThreshold(3)
                .setTotalUnstableThreshold(2)
                .setNormalPriorityUnstableThreshold(2)
                .createQualityGate();

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertThat(unstable)
                .as("One normal priority issue more than unstable-threshold and one less than failure-threshold should end in unstable.")
                .isEqualTo(Result.UNSTABLE);
    }

    /**
     * Tests whether a build with more low priority issues than the unstable threshold of low priority issues is
     * UNSTABLE.
     */
    @Test
    void shouldBeUnstableIfMoreLowPriorityIssuesThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalLowPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGateBuilder()
                .setTotalFailureThreshold(3)
                .setLowPriorityFailureThreshold(3)
                .setTotalUnstableThreshold(2)
                .setLowPriorityUnstableThreshold(2)
                .createQualityGate();

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertThat(unstable)
                .as("One high priority issue more than unstable-threshold and one less than failure-threshold should end in unstable.")
                .isEqualTo(Result.UNSTABLE);
    }

    /**
     * Tests whether a build with more new issues than the new issue threshold fails.
     */
    @Test
    void shouldFailIfMoreNewIssuesThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewSize()).thenReturn(2);

        QualityGate qualityGate = new QualityGateBuilder()
                .setTotalUnstableThreshold(0)
                .setNewTotalFailureThreshold(2)
                .createQualityGate();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One new issue more than threshold should end in failure.")
                .isEqualTo(Result.FAILURE);
    }

    /**
     * Tests whether a build with more new high priority issues than the new high priority issue threshold fails.
     */
    @Test
    void shouldFailIfMoreNewHighPriorityIssuesThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewHighPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGateBuilder()
                .setNewTotalFailureThreshold(3)
                .setNewHighPriorityFailureThreshold(2)
                .createQualityGate();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One new high priority issue more than threshold should end in failure.")
                .isEqualTo(Result.FAILURE);
    }

    /**
     * Tests whether a build with more new normal priority issues than the new normal priority issue threshold fails.
     */
    @Test
    void shouldFailIfMoreNewNormalPriorityIssuesThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewNormalPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGateBuilder()
                .setNewTotalFailureThreshold(3)
                .setNewNormalPriorityFailureThreshold(2)
                .createQualityGate();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One new normal priority issue more than threshold should end in failure.")
                .isEqualTo(Result.FAILURE);
    }

    /**
     * Tests whether a build with more low high priority issues than the new low priority issue threshold fails.
     */
    @Test
    void shouldFailIfMoreNewLowPriorityIssuesThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewLowPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGateBuilder()
                .setNewTotalFailureThreshold(3)
                .setNewLowPriorityFailureThreshold(2)
                .createQualityGate();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One new low priority issue more than threshold should end in failure.")
                .isEqualTo(Result.FAILURE);
    }

    /**
     * Tests whether a build with more new issues than the threshold is UNSTABLE.
     */
    @Test
    void shouldBeUnstableIfMoreNewIssuesThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewSize()).thenReturn(2);

        QualityGate qualityGate = new QualityGateBuilder()
                .setNewTotalFailureThreshold(3)
                .setNewTotalUnstableThreshold(2)
                .createQualityGate();

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertThat(unstable)
                .as("One issue more than unstable-threshold and one less than failure-threshold should end in unstable.")
                .isEqualTo(Result.UNSTABLE);
    }

    /**
     * Tests whether a build with more new high priority issues than the threshold of new high priority issues is
     * UNSTABLE.
     */
    @Test
    void shouldBeUnstableIfMoreNewHighPriorityIssuesThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewHighPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGateBuilder()
                .setNewTotalFailureThreshold(4)
                .setNewTotalUnstableThreshold(3)
                .setNewHighPriorityUnstableThreshold(2)
                .createQualityGate();

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertThat(unstable)
                .as("One high priority issue more than unstable-threshold and one less than failure-threshold should end in unstable.")
                .isEqualTo(Result.UNSTABLE);
    }

    /**
     * Tests whether a build with more new normal priority issues than the threshold of new normal priority issues is
     * UNSTABLE.
     */
    @Test
    void shouldBeUnstableIfMoreNewNormalPriorityIssuesThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewNormalPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGateBuilder()
                .setNewTotalFailureThreshold(4)
                .setNewTotalUnstableThreshold(3)
                .setNewNormalPriorityUnstableThreshold(2)
                .createQualityGate();

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertThat(unstable)
                .as("One normal priority issue more than unstable-threshold and one less than failure-threshold should end in unstable.")
                .isEqualTo(Result.UNSTABLE);
    }

    /**
     * Tests whether a build with more new low priority issues than the threshold of new low priority issues is
     * UNSTABLE.
     */
    @Test
    void shouldBeUnstableIfMoreNewLowPriorityIssuesThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewLowPrioritySize()).thenReturn(2);

        QualityGate qualityGate = new QualityGateBuilder()
                .setNewTotalFailureThreshold(4)
                .setNewTotalUnstableThreshold(3)
                .setNewLowPriorityUnstableThreshold(2)
                .createQualityGate();

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertThat(unstable)
                .as("One low priority issue more than unstable-threshold and one less than failure-threshold should end in unstable.")
                .isEqualTo(Result.UNSTABLE);
    }

}
