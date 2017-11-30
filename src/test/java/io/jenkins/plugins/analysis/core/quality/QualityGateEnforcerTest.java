package io.jenkins.plugins.analysis.core.quality;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import hudson.model.Result;

/**
 * Tests the class {@link QualityGateEnforcer}.
 *
 * @author Joscha Behrmann
 */
class QualityGateEnforcerTest {

    private static final String SUCCESS_BELOW = "Count of Issues below threshold should be a SUCCESS";
    private static final String SUCCESS_EQUAL = "Count of Issues equal to threshold should be a SUCCESS";
    private static final String UNSTABLE = "Count of Issues exceeding unstable-threshold should be UNSTABLE";
    private static final String FAILURE = "Count of Issues exceeding failure-threshold should be FAILURE";

    /*
     * The following tests test special cases
     */

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndNoQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder().build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and no quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndFailureQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setTotalPriorityAllFailed(0)
                .build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    /*
     * The following tests test the 8 different 'totals'-fields, one test for each
     * field. Each field is tested for its boundaries (e.g. one below, one equal to
     * and one above the corresponding threshold) and checked for the correct
     * result.
     */

    @Test
    void shouldEvaluateResultWhenUnstableTotalAllPrioritiesAreSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setTotalPriorityAllUnstable(1)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getTotalSize()).thenReturn(0);
            Result success = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success).as(SUCCESS_BELOW).isEqualTo(Result.SUCCESS);

            when(run.getTotalSize()).thenReturn(1);
            Result success2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success2).as(SUCCESS_EQUAL).isEqualTo(Result.SUCCESS);

            when(run.getTotalSize()).thenReturn(2);
            Result unstable = enforcer.evaluate(run, qualityGate);
            softly.assertThat(unstable).as(UNSTABLE).isEqualTo(Result.UNSTABLE);
        });
    }

    @Test
    void shouldEvaluateResultWhenFailureTotalAllPrioritiesAreSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setTotalPriorityAllFailed(1)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getTotalSize()).thenReturn(0);
            Result success = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success).as(SUCCESS_BELOW).isEqualTo(Result.SUCCESS);

            when(run.getTotalSize()).thenReturn(1);
            Result success2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success2).as(SUCCESS_EQUAL).isEqualTo(Result.SUCCESS);

            when(run.getTotalSize()).thenReturn(2);
            Result fail = enforcer.evaluate(run, qualityGate);
            softly.assertThat(fail).as(FAILURE).isEqualTo(Result.FAILURE);
        });
    }

    @Test
    void shouldEvaluateResultWhenUnstableTotalHighPrioritiesAreSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setTotalPriorityHighUnstable(1)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getTotalHighPrioritySize()).thenReturn(0);
            Result success = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success).as(SUCCESS_BELOW).isEqualTo(Result.SUCCESS);

            when(run.getTotalHighPrioritySize()).thenReturn(1);
            Result success2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success2).as(SUCCESS_EQUAL).isEqualTo(Result.SUCCESS);

            when(run.getTotalHighPrioritySize()).thenReturn(2);
            Result unstable = enforcer.evaluate(run, qualityGate);
            softly.assertThat(unstable).as(UNSTABLE).isEqualTo(Result.UNSTABLE);
        });
    }

    @Test
    void shouldEvaluateResultWhenFailureTotalHighPrioritiesAreSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setTotalPriorityHighFailed(1)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getTotalHighPrioritySize()).thenReturn(0);
            Result success = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success).as(SUCCESS_BELOW).isEqualTo(Result.SUCCESS);

            when(run.getTotalHighPrioritySize()).thenReturn(1);
            Result success2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success2).as(SUCCESS_EQUAL).isEqualTo(Result.SUCCESS);

            when(run.getTotalHighPrioritySize()).thenReturn(2);
            Result fail = enforcer.evaluate(run, qualityGate);
            softly.assertThat(fail).as(FAILURE).isEqualTo(Result.FAILURE);
        });
    }

    @Test
    void shouldEvaluateResultWhenUnstableTotalNormalPrioritiesAreSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setTotalPriorityNormalUnstable(1)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getTotalNormalPrioritySize()).thenReturn(0);
            Result success = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success).as(SUCCESS_BELOW).isEqualTo(Result.SUCCESS);

            when(run.getTotalNormalPrioritySize()).thenReturn(1);
            Result success2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success2).as(SUCCESS_EQUAL).isEqualTo(Result.SUCCESS);

            when(run.getTotalNormalPrioritySize()).thenReturn(2);
            Result unstable = enforcer.evaluate(run, qualityGate);
            softly.assertThat(unstable).as(UNSTABLE).isEqualTo(Result.UNSTABLE);
        });
    }

    @Test
    void shouldEvaluateResultWhenFailureTotalNormalPrioritiesAreSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setTotalPriorityNormalFailed(1)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getTotalNormalPrioritySize()).thenReturn(0);
            Result success = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success).as(SUCCESS_BELOW).isEqualTo(Result.SUCCESS);

            when(run.getTotalNormalPrioritySize()).thenReturn(1);
            Result success2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success2).as(SUCCESS_EQUAL).isEqualTo(Result.SUCCESS);

            when(run.getTotalNormalPrioritySize()).thenReturn(2);
            Result fail = enforcer.evaluate(run, qualityGate);
            softly.assertThat(fail).as(FAILURE).isEqualTo(Result.FAILURE);
        });
    }

    @Test
    void shouldEvaluateResultWhenUnstableTotalLowPrioritiesAreSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setTotalPriorityLowUnstable(1)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getTotalLowPrioritySize()).thenReturn(0);
            Result success = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success).as(SUCCESS_BELOW).isEqualTo(Result.SUCCESS);

            when(run.getTotalLowPrioritySize()).thenReturn(1);
            Result success2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success2).as(SUCCESS_EQUAL).isEqualTo(Result.SUCCESS);

            when(run.getTotalLowPrioritySize()).thenReturn(2);
            Result unstable = enforcer.evaluate(run, qualityGate);
            softly.assertThat(unstable).as(UNSTABLE).isEqualTo(Result.UNSTABLE);
        });
    }

    @Test
    void shouldEvaluateResultWhenFailureTotalLowPrioritiesAreSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setTotalPriorityLowFailed(1)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getTotalLowPrioritySize()).thenReturn(0);
            Result success = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success).as(SUCCESS_BELOW).isEqualTo(Result.SUCCESS);

            when(run.getTotalLowPrioritySize()).thenReturn(1);
            Result success2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success2).as(SUCCESS_EQUAL).isEqualTo(Result.SUCCESS);

            when(run.getTotalLowPrioritySize()).thenReturn(2);
            Result fail = enforcer.evaluate(run, qualityGate);
            softly.assertThat(fail).as(FAILURE).isEqualTo(Result.FAILURE);
        });
    }

    /*
     * The following tests test the 8 different 'new'-fields, one test for each
     * field. Each field is tested for its boundaries (e.g. one below, one equal to
     * and one above the corresponding threshold) and checked for the correct
     * result.
     */

    @Test
    void shouldEvaluateResultWhenUnstableNewAllPrioritiesAreSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setNewPriorityAllUnstable(1)
                .setComputeNewWarnings(true)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getNewSize()).thenReturn(0);
            Result success = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success).as(SUCCESS_BELOW).isEqualTo(Result.SUCCESS);

            when(run.getNewSize()).thenReturn(1);
            Result success2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success2).as(SUCCESS_EQUAL).isEqualTo(Result.SUCCESS);

            when(run.getNewSize()).thenReturn(2);
            Result unstable = enforcer.evaluate(run, qualityGate);
            softly.assertThat(unstable).as(UNSTABLE).isEqualTo(Result.UNSTABLE);
        });
    }

    @Test
    void shouldEvaluateResultWhenFailureNewAllPrioritiesAreSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setNewPriorityAllFailed(1)
                .setComputeNewWarnings(true)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getNewSize()).thenReturn(0);
            Result success = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success).as(SUCCESS_BELOW).isEqualTo(Result.SUCCESS);

            when(run.getNewSize()).thenReturn(1);
            Result success2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success2).as(SUCCESS_EQUAL).isEqualTo(Result.SUCCESS);

            when(run.getNewSize()).thenReturn(2);
            Result fail = enforcer.evaluate(run, qualityGate);
            softly.assertThat(fail).as(FAILURE).isEqualTo(Result.FAILURE);
        });
    }

    @Test
    void shouldEvaluateResultWhenUnstableNewHighPrioritiesAreSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setNewPriorityHighUnstable(1)
                .setComputeNewWarnings(true)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getNewHighPrioritySize()).thenReturn(0);
            Result success = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success).as(SUCCESS_BELOW).isEqualTo(Result.SUCCESS);

            when(run.getNewHighPrioritySize()).thenReturn(1);
            Result success2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success2).as(SUCCESS_EQUAL).isEqualTo(Result.SUCCESS);

            when(run.getNewHighPrioritySize()).thenReturn(2);
            Result unstable = enforcer.evaluate(run, qualityGate);
            softly.assertThat(unstable).as(UNSTABLE).isEqualTo(Result.UNSTABLE);
        });
    }

    @Test
    void shouldEvaluateResultWhenFailureNewHighPrioritiesAreSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setNewPriorityHighFailed(1)
                .setComputeNewWarnings(true)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getNewHighPrioritySize()).thenReturn(0);
            Result success = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success).as(SUCCESS_BELOW).isEqualTo(Result.SUCCESS);

            when(run.getNewHighPrioritySize()).thenReturn(1);
            Result success2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success2).as(SUCCESS_EQUAL).isEqualTo(Result.SUCCESS);

            when(run.getNewHighPrioritySize()).thenReturn(2);
            Result fail = enforcer.evaluate(run, qualityGate);
            softly.assertThat(fail).as(FAILURE).isEqualTo(Result.FAILURE);
        });
    }

    @Test
    void shouldEvaluateResultWhenUnstableNewNormalPrioritiesAreSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setNewPriorityNormalUnstable(1)
                .setComputeNewWarnings(true)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getNewNormalPrioritySize()).thenReturn(0);
            Result success = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success).as(SUCCESS_BELOW).isEqualTo(Result.SUCCESS);

            when(run.getNewNormalPrioritySize()).thenReturn(1);
            Result success2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success2).as(SUCCESS_EQUAL).isEqualTo(Result.SUCCESS);

            when(run.getNewNormalPrioritySize()).thenReturn(2);
            Result unstable = enforcer.evaluate(run, qualityGate);
            softly.assertThat(unstable).as(UNSTABLE).isEqualTo(Result.UNSTABLE);
        });
    }

    @Test
    void shouldEvaluateResultWhenFailureNewNormalPrioritiesAreSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setNewPriorityNormalFailed(1)
                .setComputeNewWarnings(true)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getNewNormalPrioritySize()).thenReturn(0);
            Result success = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success).as(SUCCESS_BELOW).isEqualTo(Result.SUCCESS);

            when(run.getNewNormalPrioritySize()).thenReturn(1);
            Result success2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success2).as(SUCCESS_EQUAL).isEqualTo(Result.SUCCESS);

            when(run.getNewNormalPrioritySize()).thenReturn(2);
            Result fail = enforcer.evaluate(run, qualityGate);
            softly.assertThat(fail).as(FAILURE).isEqualTo(Result.FAILURE);
        });
    }

    @Test
    void shouldEvaluateResultWhenUnstableNewLowPrioritiesAreSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setNewPriorityLowUnstable(1)
                .setComputeNewWarnings(true)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getNewLowPrioritySize()).thenReturn(0);
            Result success = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success).as(SUCCESS_BELOW).isEqualTo(Result.SUCCESS);

            when(run.getNewLowPrioritySize()).thenReturn(1);
            Result success2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success2).as(SUCCESS_EQUAL).isEqualTo(Result.SUCCESS);

            when(run.getNewLowPrioritySize()).thenReturn(2);
            Result unstable = enforcer.evaluate(run, qualityGate);
            softly.assertThat(unstable).as(UNSTABLE).isEqualTo(Result.UNSTABLE);
        });
    }

    @Test
    void shouldEvaluateResultWhenFailureNewLowPrioritiesAreSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setNewPriorityLowFailed(1)
                .setComputeNewWarnings(true)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getNewLowPrioritySize()).thenReturn(0);
            Result success = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success).as(SUCCESS_BELOW).isEqualTo(Result.SUCCESS);

            when(run.getNewLowPrioritySize()).thenReturn(1);
            Result success2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success2).as(SUCCESS_EQUAL).isEqualTo(Result.SUCCESS);

            when(run.getNewLowPrioritySize()).thenReturn(2);
            Result fail = enforcer.evaluate(run, qualityGate);
            softly.assertThat(fail).as(FAILURE).isEqualTo(Result.FAILURE);
        });
    }
}
