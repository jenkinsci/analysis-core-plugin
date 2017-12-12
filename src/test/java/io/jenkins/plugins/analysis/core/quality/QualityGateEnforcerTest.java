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
    private static final String FAILURE_OVER_UNSTABLE = "Matching both 'unstable' and 'failure' thresholds should "
            + "should return FAILURE";
    private static final String NO_CONSIDER_NEW_WARNING = "Exceeding 'new'-thresholds when gate is set to ignore them "
            + "should return SUCCESS";
    private static final String NEGATIVE_THRESHOLD_SHOULD_DEFAULT = "Setting a negative threshold should "
            + "default to 0. Hence a run with 1 or more issues should be a fail or unstable.";

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

    /**
     * When the threshold of a priority for both 'fail' and 'unstable' are exceeded,
     * the build should be reported as a fail.
     */
    @Test
    void shouldPreferFailOverUnstableTotalTotal() {
        // Total runs fail and are unstable
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun exceedsTotalFails = mock(StaticAnalysisRun.class);
        when(exceedsTotalFails.getTotalSize()).thenReturn(2);
        QualityGate totalFailGate = new QualityGate.Builder()
                .setTotalPriorityAllFailed(1)
                .setTotalPriorityAllUnstable(1)
                .build();

        Result fail = enforcer.evaluate(exceedsTotalFails, totalFailGate);
        assertThat(fail).as(FAILURE_OVER_UNSTABLE).isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldPreferFailOverUnstableTotalNew() {
        // Total runs fail, new are unstable
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun exceedsNewFails = mock(StaticAnalysisRun.class);
        when(exceedsNewFails.getTotalSize()).thenReturn(2);
        when(exceedsNewFails.getNewSize()).thenReturn(2);
        QualityGate newFailGate = new QualityGate.Builder()
                .setTotalPriorityAllFailed(1)
                .setNewPriorityAllUnstable(1)
                .setComputeNewWarnings(true)
                .build();
        Result fail = enforcer.evaluate(exceedsNewFails, newFailGate);
        assertThat(fail).as(FAILURE_OVER_UNSTABLE).isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldPreferFailOverUnstableNewTotal() {
        // Total runs unstable, new are fail
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun exceedsNewFails = mock(StaticAnalysisRun.class);
        when(exceedsNewFails.getTotalSize()).thenReturn(2);
        when(exceedsNewFails.getNewSize()).thenReturn(2);
        QualityGate newFailGate = new QualityGate.Builder()
                .setTotalPriorityAllUnstable(1)
                .setNewPriorityAllFailed(1)
                .setComputeNewWarnings(true)
                .build();
        Result fail = enforcer.evaluate(exceedsNewFails, newFailGate);
        assertThat(fail).as(FAILURE_OVER_UNSTABLE).isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldPreferFailOverUnstableNewNew() {
        // New runs fail and are unstable
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun exceedsNewFails = mock(StaticAnalysisRun.class);
        when(exceedsNewFails.getNewSize()).thenReturn(2);
        QualityGate newFailGate = new QualityGate.Builder()
                .setNewPriorityAllFailed(1)
                .setNewPriorityAllUnstable(1)
                .setComputeNewWarnings(true)
                .build();
        Result fail = enforcer.evaluate(exceedsNewFails, newFailGate);
        assertThat(fail).as(FAILURE_OVER_UNSTABLE).isEqualTo(Result.FAILURE);
    }

    /**
     * When the gate is set to not consider 'new'-thresholds, exceeding them should
     * still return a success.
     */
    @Test
    void newWarningsShouldNotBeConsidered() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setNewPriorityAllUnstable(1)
                .setNewPriorityAllFailed(1)
                .setNewPriorityHighUnstable(1)
                .setNewPriorityHighFailed(1)
                .setNewPriorityNormalUnstable(1)
                .setNewPriorityNormalFailed(1)
                .setNewPriorityLowUnstable(1)
                .setNewPriorityLowFailed(1)
                .setComputeNewWarnings(false)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getNewSize()).thenReturn(2);
            Result success = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success).as(NO_CONSIDER_NEW_WARNING).isEqualTo(Result.SUCCESS);
            when(run.getNewSize()).thenReturn(0);

            when(run.getNewHighPrioritySize()).thenReturn(2);
            Result success2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success2).as(NO_CONSIDER_NEW_WARNING).isEqualTo(Result.SUCCESS);
            when(run.getNewHighPrioritySize()).thenReturn(0);

            when(run.getNewNormalPrioritySize()).thenReturn(2);
            Result success3 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success3).as(NO_CONSIDER_NEW_WARNING).isEqualTo(Result.SUCCESS);
            when(run.getNewNormalPrioritySize()).thenReturn(0);

            when(run.getNewLowPrioritySize()).thenReturn(2);
            Result success4 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(success4).as(NO_CONSIDER_NEW_WARNING).isEqualTo(Result.SUCCESS);
            when(run.getNewLowPrioritySize()).thenReturn(0);
        });
    }

    /**
     * When the user sets thresholds to negative numbers, their values should be
     * defaulted to zero. Hence a count of 1 issue will trigger a failure/unstable.
     */
    @Test
    void negativeUserInputShouldDefaultToZero() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGate qualityGate = new QualityGate.Builder()
                .setTotalPriorityAllUnstable(-1)
                .setTotalPriorityAllFailed(-2)
                .setTotalPriorityHighUnstable(-3)
                .setTotalPriorityHighFailed(-4)
                .setTotalPriorityNormalUnstable(-5)
                .setTotalPriorityNormalFailed(-6)
                .setTotalPriorityLowUnstable(-7)
                .setTotalPriorityLowFailed(-8)
                .setNewPriorityAllUnstable(-9)
                .setNewPriorityAllFailed(-10)
                .setNewPriorityHighUnstable(-11)
                .setNewPriorityHighFailed(-12)
                .setNewPriorityNormalUnstable(-13)
                .setNewPriorityNormalFailed(-14)
                .setNewPriorityLowUnstable(-15)
                .setNewPriorityLowFailed(-16)
                .setComputeNewWarnings(true)
                .build();

        SoftAssertions.assertSoftly(softly -> {
            when(run.getTotalSize()).thenReturn(1);
            Result fail = enforcer.evaluate(run, qualityGate);
            softly.assertThat(fail).as(NEGATIVE_THRESHOLD_SHOULD_DEFAULT).isEqualTo(Result.FAILURE);
            when(run.getTotalSize()).thenReturn(0);

            when(run.getTotalHighPrioritySize()).thenReturn(1);
            Result fail2 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(fail2).as(NEGATIVE_THRESHOLD_SHOULD_DEFAULT).isEqualTo(Result.FAILURE);
            when(run.getTotalHighPrioritySize()).thenReturn(0);

            when(run.getTotalNormalPrioritySize()).thenReturn(1);
            Result fail3 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(fail3).as(NEGATIVE_THRESHOLD_SHOULD_DEFAULT).isEqualTo(Result.FAILURE);
            when(run.getTotalNormalPrioritySize()).thenReturn(0);

            when(run.getTotalLowPrioritySize()).thenReturn(1);
            Result fail4 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(fail4).as(NEGATIVE_THRESHOLD_SHOULD_DEFAULT).isEqualTo(Result.FAILURE);
            when(run.getTotalLowPrioritySize()).thenReturn(0);

            when(run.getNewSize()).thenReturn(1);
            Result fail5 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(fail5).as(NEGATIVE_THRESHOLD_SHOULD_DEFAULT).isEqualTo(Result.FAILURE);
            when(run.getNewSize()).thenReturn(0);

            when(run.getNewHighPrioritySize()).thenReturn(1);
            Result fail6 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(fail6).as(NEGATIVE_THRESHOLD_SHOULD_DEFAULT).isEqualTo(Result.FAILURE);
            when(run.getNewHighPrioritySize()).thenReturn(0);

            when(run.getNewNormalPrioritySize()).thenReturn(1);
            Result fail7 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(fail7).as(NEGATIVE_THRESHOLD_SHOULD_DEFAULT).isEqualTo(Result.FAILURE);
            when(run.getNewNormalPrioritySize()).thenReturn(0);

            when(run.getNewLowPrioritySize()).thenReturn(1);
            Result fail8 = enforcer.evaluate(run, qualityGate);
            softly.assertThat(fail8).as(NEGATIVE_THRESHOLD_SHOULD_DEFAULT).isEqualTo(Result.FAILURE);
            when(run.getNewLowPrioritySize()).thenReturn(0);
        });
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
