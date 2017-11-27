package io.jenkins.plugins.analysis.core.quality;

import hudson.model.Result;
import io.jenkins.plugins.analysis.core.quality.thresholds.FailureThresholds;
import io.jenkins.plugins.analysis.core.quality.thresholds.NewFailureThresholds;
import io.jenkins.plugins.analysis.core.quality.thresholds.NewUnstableThresholds;
import io.jenkins.plugins.analysis.core.quality.thresholds.UnstableThresholds;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
        QualityGate qualityGate = new QualityGate(new FailureThresholds(1));

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildIfFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new FailureThresholds(1));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndUnstableQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate(new UnstableThresholds(1));

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any unstable quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldBeUnstableIfUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new UnstableThresholds(1));

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertThat(unstable)
                .as("One issue should return a UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldFailBuildIfHighPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new FailureThresholds(1, 0, 0));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One high priority issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldFailBuildIfNormalPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalNormalPrioritySize()).thenReturn(1);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new FailureThresholds(2, 1, 0));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One high priority issue is allowed but one normal priority issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldFailBuildIfLowPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalLowPrioritySize()).thenReturn(1);
        when(run.getTotalNormalPrioritySize()).thenReturn(1);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new FailureThresholds(2, 2, 1));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One high and normal priority issue are allowed but one low priority issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);

    }

    @Test
    void shouldBeUnstableBuildIfHighPriorityUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new UnstableThresholds(1, 0, 0));

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertThat(unstable)
                .as("One high priority issue should return a UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeUnstableBuildIfNormalPriorityUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalNormalPrioritySize()).thenReturn(1);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new UnstableThresholds(2, 1, 0));

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertThat(unstable)
                .as("One high priority issue is allowed but one normal priority issue should return a UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeUnstableBuildIfLowPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalLowPrioritySize()).thenReturn(1);
        when(run.getTotalNormalPrioritySize()).thenReturn(1);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new UnstableThresholds(2, 2, 1));

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertThat(unstable)
                .as("One high and normal priority issue are allowed but one low priority issue should return a UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeSuccessfulWhenNoNewIssuesPresentAndNewFailureQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate(new NewFailureThresholds(1));

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldFailBuildWithNewWarningsIfNewFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new NewFailureThresholds(1));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One new issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeUnstableWhenNewIssuesPresentAndNewUnstableQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate(new NewUnstableThresholds(1));

        when(run.getNewSize()).thenReturn(1);

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No new issues should return UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldFailBuildIfNewHighPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new NewFailureThresholds(1, 0, 0));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One new high priority issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldFailBuildIfNewNormalPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getNewNormalPrioritySize()).thenReturn(1);
        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new NewFailureThresholds(2, 1, 0));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One new high priority issue is allowed but one new normal priority issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldFailBuildIfNewLowPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getNewLowPrioritySize()).thenReturn(1);
        when(run.getNewNormalPrioritySize()).thenReturn(1);
        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new NewFailureThresholds(2, 2, 1));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One new high and normal priority issue are allowed but one new low priority issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeUnstableBuildIfNewHighPriorityUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new NewUnstableThresholds(1, 0, 0));

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertThat(unstable)
                .as("One new high priority issue should return a UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeUnstableBuildIfNewNormalPriorityUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getNewNormalPrioritySize()).thenReturn(1);
        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new NewUnstableThresholds(2, 1, 0));

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertThat(unstable)
                .as("One new high priority issue is allowed but one new normal priority issue should return a UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeUnstableBuildIfNewLowPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getNewLowPrioritySize()).thenReturn(1);
        when(run.getNewNormalPrioritySize()).thenReturn(1);
        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new NewUnstableThresholds(2, 2, 1));

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertThat(unstable)
                .as("One new high and normal priority issue are allowed but one new low priority issue should return a UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldBeFailureRatherThanUnstable() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new FailureThresholds(1), new UnstableThresholds(1));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("When FailureThresholds and UnstableThresholds are equal then it should rather fail")
                .isEqualTo(Result.FAILURE);
    }
}
