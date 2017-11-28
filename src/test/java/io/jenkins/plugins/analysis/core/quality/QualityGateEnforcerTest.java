package io.jenkins.plugins.analysis.core.quality;

import hudson.model.Result;
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

        assertSuccess(success, "No issues and no quality gate should always be a SUCCESS");
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndFailureQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate(new WarningsThreshold.FailureThresholds(1));

        Result success = enforcer.evaluate(run, qualityGate);

        assertSuccess(success, "No issues and any failure quality gate should always be a SUCCESS");
    }

    @Test
    void shouldFailBuildIfFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new WarningsThreshold.FailureThresholds(1));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertFailure(failure, "One issue should return a FAILURE");
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndUnstableQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate(new WarningsThreshold.UnstableThresholds(1));

        Result success = enforcer.evaluate(run, qualityGate);

        assertSuccess(success, "No issues and any unstable quality gate should always be a SUCCESS");
    }

    @Test
    void shouldBeUnstableIfUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new WarningsThreshold.UnstableThresholds(1));

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertUnstable(unstable, "One issue should return a UNSTABLE");
    }

    @Test
    void shouldFailBuildIfHighPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new WarningsThreshold.FailureThresholds(1, 0, 0));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertFailure(failure, "One high priority issue should return a FAILURE");
    }

    @Test
    void shouldFailBuildIfNormalPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalNormalPrioritySize()).thenReturn(1);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new WarningsThreshold.FailureThresholds(2, 1, 0));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertFailure(failure, "One high priority issue is allowed but one normal priority issue should return a FAILURE");
    }

    @Test
    void shouldFailBuildIfLowPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalLowPrioritySize()).thenReturn(1);
        when(run.getTotalNormalPrioritySize()).thenReturn(1);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new WarningsThreshold.FailureThresholds(2, 2, 1));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertFailure(failure, "One high and normal priority issue are allowed but one low priority issue should return a FAILURE");

    }

    @Test
    void shouldBeUnstableBuildIfHighPriorityUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new WarningsThreshold.UnstableThresholds(1, 0, 0));

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertUnstable(unstable, "One high priority issue should return a UNSTABLE");
    }

    @Test
    void shouldBeUnstableBuildIfNormalPriorityUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalNormalPrioritySize()).thenReturn(1);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new WarningsThreshold.UnstableThresholds(2, 1, 0));

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertUnstable(unstable, "One high priority issue is allowed but one normal priority issue should return a UNSTABLE");
    }

    @Test
    void shouldBeUnstableBuildIfLowPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalLowPrioritySize()).thenReturn(1);
        when(run.getTotalNormalPrioritySize()).thenReturn(1);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new WarningsThreshold.UnstableThresholds(2, 2, 1));

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertUnstable(unstable, "One high and normal priority issue are allowed but one low priority issue should return a UNSTABLE");
    }

    @Test
    void shouldBeSuccessfulWhenNoNewIssuesPresentAndNewFailureQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate(new WarningsThreshold.NewFailureThresholds(1));

        Result success = enforcer.evaluate(run, qualityGate);

        assertSuccess(success, "No issues and any failure quality gate should always be a SUCCESS");
    }

    @Test
    void shouldFailBuildWithNewWarningsIfNewFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new WarningsThreshold.NewFailureThresholds(1));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertFailure(failure, "One new issue should return a FAILURE");
    }

    @Test
    void shouldBeUnstableWhenNewIssuesPresentAndNewUnstableQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate(new WarningsThreshold.NewUnstableThresholds(1));

        when(run.getNewSize()).thenReturn(1);

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertUnstable(unstable, "No new issues should return UNSTABLE");
    }

    @Test
    void shouldFailBuildIfNewHighPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new WarningsThreshold.NewFailureThresholds(1, 0, 0));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertFailure(failure, "One new high priority issue should return a FAILURE");
    }

    @Test
    void shouldFailBuildIfNewNormalPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getNewNormalPrioritySize()).thenReturn(1);
        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new WarningsThreshold.NewFailureThresholds(2, 1, 0));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertFailure(failure, "One new high priority issue is allowed but one new normal priority issue should return a FAILURE");
    }

    @Test
    void shouldFailBuildIfNewLowPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getNewLowPrioritySize()).thenReturn(1);
        when(run.getNewNormalPrioritySize()).thenReturn(1);
        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new WarningsThreshold.NewFailureThresholds(2, 2, 1));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertFailure(failure, "One new high and normal priority issue are allowed but one new low priority issue should return a FAILURE");
    }

    @Test
    void shouldBeUnstableBuildIfNewHighPriorityUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new WarningsThreshold.NewUnstableThresholds(1, 0, 0));

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertUnstable(unstable, "One new high priority issue should return a UNSTABLE");
    }

    @Test
    void shouldBeUnstableBuildIfNewNormalPriorityUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getNewNormalPrioritySize()).thenReturn(1);
        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new WarningsThreshold.NewUnstableThresholds(2, 1, 0));

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertUnstable(unstable, "One new high priority issue is allowed but one new normal priority issue should return a UNSTABLE");
    }

    @Test
    void shouldBeUnstableBuildIfNewLowPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getNewLowPrioritySize()).thenReturn(1);
        when(run.getNewNormalPrioritySize()).thenReturn(1);
        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new WarningsThreshold.NewUnstableThresholds(2, 2, 1));

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertUnstable(unstable, "One new high and normal priority issue are allowed but one new low priority issue should return a UNSTABLE");
    }

    @Test
    void shouldBeFailureRatherThanUnstable() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGate(new WarningsThreshold.FailureThresholds(1), new WarningsThreshold.UnstableThresholds(1));

        Result failure = enforcer.evaluate(run, qualityGate);

        assertFailure(failure, "When FailureThresholds and UnstableThresholds are equal then it should rather fail");
    }

    private void assertSuccess(Result success, String description) {
        assertThat(success)
                .as(description)
                .isEqualTo(Result.SUCCESS);
    }

    private static void assertFailure(Result failure, String description) {
        assertThat(failure)
                .as(description)
                .isEqualTo(Result.FAILURE);
    }

    private static void assertUnstable(Result unstable, String description) {
        assertThat(unstable)
                .as(description)
                .isEqualTo(Result.UNSTABLE);
    }
}
