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

    public static final WarningsThreshold THRESHOLD_SET = new WarningsThreshold(1);
    public static final WarningsThreshold THRESHOLD_FOR_HIGH = new WarningsThreshold(1, 0, 0);
    public static final WarningsThreshold THRESHOLD_FOR_NORMAL = new WarningsThreshold(2, 1, 0);
    public static final WarningsThreshold THRESHOLD_FOR_LOW = new WarningsThreshold(3, 2, 1);

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndNoQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder().build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertSuccess(success, "No issues and no quality gate should always be a SUCCESS");
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndFailureQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder()
                .setFailureThreshold(THRESHOLD_SET)
                .build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertSuccess(success, "No issues and any failure quality gate should always be a SUCCESS");
    }

    @Test
    void shouldFailBuildIfFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder()
                .setFailureThreshold(THRESHOLD_SET).build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertFailure(failure, "One issue should return a FAILURE");
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndUnstableQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder()
                .setUnstableThreshold(THRESHOLD_SET)
                .build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertSuccess(success, "No issues and any unstable quality gate should always be a SUCCESS");
    }

    @Test
    void shouldBeUnstableIfUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder()
                .setUnstableThreshold(THRESHOLD_SET)
                .build();

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertUnstable(unstable, "One issue should return a UNSTABLE");
    }

    @Test
    void shouldFailBuildIfHighPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder()
                .setFailureThreshold(THRESHOLD_FOR_HIGH)
                .build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertFailure(failure, "One high priority issue should return a FAILURE");
    }

    @Test
    void shouldFailBuildIfNormalPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalNormalPrioritySize()).thenReturn(1);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder()
                .setFailureThreshold(THRESHOLD_FOR_NORMAL)
                .build();

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

        QualityGate qualityGate = new QualityGateBuilder()
                .setFailureThreshold(THRESHOLD_FOR_LOW)
                .build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertFailure(failure, "One high and normal priority issue are allowed but one low priority issue should return a FAILURE");

    }

    @Test
    void shouldBeUnstableBuildIfHighPriorityUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder()
                .setUnstableThreshold(THRESHOLD_FOR_HIGH)
                .build();

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertUnstable(unstable, "One high priority issue should return a UNSTABLE");
    }

    @Test
    void shouldBeUnstableBuildIfNormalPriorityUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalNormalPrioritySize()).thenReturn(1);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder()
                .setUnstableThreshold(THRESHOLD_FOR_NORMAL)
                .build();

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

        QualityGate qualityGate = new QualityGateBuilder()
                .setUnstableThreshold(THRESHOLD_FOR_LOW)
                .build();

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertUnstable(unstable, "One high and normal priority issue are allowed but one low priority issue should return a UNSTABLE");
    }

    @Test
    void shouldBeSuccessfulWhenNoNewIssuesPresentAndNewFailureQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder()
                .setNewFailureThreshold(THRESHOLD_SET)
                .build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertSuccess(success, "No issues and any failure quality gate should always be a SUCCESS");
    }

    @Test
    void shouldFailBuildWithNewWarningsIfNewFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder()
                .setNewFailureThreshold(THRESHOLD_SET)
                .build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertFailure(failure, "One new issue should return a FAILURE");
    }

    @Test
    void shouldBeUnstableWhenNewIssuesPresentAndNewUnstableQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGateBuilder()
                .setNewUnstableThreshold(THRESHOLD_SET)
                .build();

        when(run.getNewSize()).thenReturn(1);

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertUnstable(unstable, "No new issues should return UNSTABLE");
    }

    @Test
    void shouldFailBuildIfNewHighPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder()
                .setNewFailureThreshold(THRESHOLD_FOR_HIGH)
                .build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertFailure(failure, "One new high priority issue should return a FAILURE");
    }

    @Test
    void shouldFailBuildIfNewNormalPriorityFailureThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getNewNormalPrioritySize()).thenReturn(1);
        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder()
                .setNewFailureThreshold(THRESHOLD_FOR_NORMAL)
                .build();

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

        QualityGate qualityGate = new QualityGateBuilder()
                .setNewFailureThreshold(THRESHOLD_FOR_LOW)
                .build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertFailure(failure, "One new high and normal priority issue are allowed but one new low priority issue should return a FAILURE");
    }

    @Test
    void shouldBeUnstableBuildIfNewHighPriorityUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder()
                .setNewUnstableThreshold(THRESHOLD_FOR_HIGH)
                .build();

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertUnstable(unstable, "One new high priority issue should return a UNSTABLE");
    }

    @Test
    void shouldBeUnstableBuildIfNewNormalPriorityUnstableThresholdIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getNewNormalPrioritySize()).thenReturn(1);
        when(run.getNewHighPrioritySize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder()
                .setNewUnstableThreshold(THRESHOLD_FOR_NORMAL)
                .build();

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

        QualityGate qualityGate = new QualityGateBuilder()
                .setNewUnstableThreshold(THRESHOLD_FOR_LOW)
                .build();

        Result unstable = enforcer.evaluate(run, qualityGate);

        assertUnstable(unstable, "One new high and normal priority issue are allowed but one new low priority issue should return a UNSTABLE");
    }

    @Test
    void shouldBeFailureRatherThanUnstable() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        when(run.getTotalSize()).thenReturn(1);

        QualityGate qualityGate = new QualityGateBuilder()
                .setFailureThreshold(THRESHOLD_SET)
                .setUnstableThreshold(THRESHOLD_SET)
                .build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertFailure(failure, "When FailureThresholds and UnstableThresholds are equal then it should rather fail");
    }

    private static void assertSuccess(Result success, String description) {
        assertResult(success, Result.SUCCESS, description);
    }

    private static void assertFailure(Result failure, String description) {
        assertResult(failure, Result.FAILURE, description);
    }

    private static void assertUnstable(Result unstable, String description) {
        assertResult(unstable, Result.UNSTABLE, description);
    }

    private static void assertResult(Result actual, Result expected, String description) {
        assertThat(actual)
                .as(description)
                .isEqualTo(expected);
    }
}
