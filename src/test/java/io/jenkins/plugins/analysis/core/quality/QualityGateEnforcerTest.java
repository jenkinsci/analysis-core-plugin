package io.jenkins.plugins.analysis.core.quality;

import org.junit.experimental.max.MaxHistory;
import org.junit.jupiter.api.Test;

import jnr.ffi.annotations.In;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import hudson.model.Result;

/**
 * Tests the class {@link QualityGateEnforcer}.
 *
 * @author Ullrich Hafner
 * @author Benedikt Neuner
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
    void shouldBeSuccessfulWhenNoIssuesPresentAndFailureQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setAllowedTotalBeforeFail(1, 1, 1, 1);
        QualityGate qualityGate = builder.build();

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

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setAllowedTotalBeforeFail(0, 0, 0, 0);
        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /**
     * Test the qualityGates when setting the total ammount of allowed issues before failing {@link
     * QualityGateBuilder#setAllowedTotalBeforeFail(int, int, int, int)} with n = t - 1 n = t n = t + 1 where n = number
     * of issues and t = number of allowed issues (treshold)
     */
    @Test
    void testAnyFail() {
        int t = 5;
        int n = t - 1; // initial value of issues, will be incremented

        for (int i = 0; i < 3; i++) {
            QualityGateEnforcer enforcer = new QualityGateEnforcer();
            StaticAnalysisRun run = mock(StaticAnalysisRun.class);
            when(run.getTotalSize()).thenReturn(n);

            QualityGateBuilder builder = new QualityGateBuilder();
            builder.setAllowedTotalBeforeFail(t, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
            QualityGate qualityGate = builder.build();

            Result failure = enforcer.evaluate(run, qualityGate);

            if (n < t) {
                assertThat(failure)
                        .as("Less issues than allowed should be SUCCESS")
                        .isEqualTo(Result.SUCCESS);
            }
            else if (n == t) {
                assertThat(failure)
                        .as("Amount of issues equals allowed issues should be SUCCESS")
                        .isEqualTo(Result.SUCCESS);
            }
            else if (n > t) {
                assertThat(failure)
                        .as("More issues than allowed should be FAILURE")
                        .isEqualTo(Result.FAILURE);
            }

            n++;
        }
    }

    /**
     * Test the qualityGates when setting the total ammount of allowed issues before becoming unstable {@link
     * QualityGateBuilder#setAllowedTotalBeforeUnstable(int, int, int, int)} with n = t - 1 n = t n = t + 1 where n =
     * number of issues and t = number of allowed issues (treshold)
     */
    @Test
    void testAnyUnstable() {
        int t = 5;
        int n = t - 1; // initial value of issues, will be incremented

        for (int i = 0; i < 3; i++) {
            QualityGateEnforcer enforcer = new QualityGateEnforcer();
            StaticAnalysisRun run = mock(StaticAnalysisRun.class);
            when(run.getTotalSize()).thenReturn(n);

            QualityGateBuilder builder = new QualityGateBuilder();
            builder.setAllowedTotalBeforeUnstable(t, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
            QualityGate qualityGate = builder.build();

            Result failure = enforcer.evaluate(run, qualityGate);

            if (n < t) {
                assertThat(failure)
                        .as("Less issues than allowed should be SUCCESS")
                        .isEqualTo(Result.SUCCESS);
            }
            else if (n == t) {
                assertThat(failure)
                        .as("Amount of issues equals allowed issues should be SUCCESS")
                        .isEqualTo(Result.SUCCESS);
            }
            else if (n > t) {
                assertThat(failure)
                        .as("More issues than allowed should be FAILURE")
                        .isEqualTo(Result.UNSTABLE);
            }

            n++;
        }
    }

    @Test
    void shouldFailWhenUnstableAndFailureTresholdAreExceeded() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setAllowedTotalBeforeFail(0, 0, 0, 0);
        builder.setAllowedTotalBeforeUnstable(0, 0, 0, 0);
        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldBeUnstableWhenOnlyUnstableTresholdIsExceeded() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setAllowedTotalBeforeFail(1, 0, 0, 0);
        builder.setAllowedTotalBeforeUnstable(0, 0, 0, 0);
        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a Unstable")
                .isEqualTo(Result.UNSTABLE);
    }

    @Test
    void shouldFailWhenASingelFieldCriteriaFails() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);
        when(run.getTotalHighPrioritySize()).thenReturn(3);
        when(run.getTotalNormalPrioritySize()).thenReturn(1);
        when(run.getTotalLowPrioritySize()).thenReturn(11);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setAllowedTotalBeforeFail(1, 4, 1, 10);
        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Eleven issues should return a FAILURE when 10 allowed")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldHandleUpperBoundCorrect() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(Integer.MAX_VALUE);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setAllowedTotalBeforeFail(Integer.MAX_VALUE, 0, 0, 0);
        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Max ammount of issues should be allowed and return SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    @Test
    void shouldHandleMinValue() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalNormalPrioritySize()).thenReturn(3);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setAllowedTotalBeforeFail(0, 0, Integer.MIN_VALUE, 0);
        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Three issues should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldHandleNegativeValuesCorrect() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setAllowedTotalBeforeFail(-1, -80, -20, -9);
        builder.setAllowedTotalBeforeUnstable(-1, -239, -23, -1);
        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    @Test
    void shouldHandleAllZerosCorrect() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setAllowedTotalBeforeFail(0, 0, 0, 0);
        builder.setAllowedTotalBeforeUnstable(0, 0, 0, 0);
        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /**
     * Tests dedicated to {@link QualityGateBuilder#setAllowedNewBeforeFail(int, int, int, int)} and {@link
     * QualityGateBuilder#setAllowedNewBeforeUnstable(int, int, int, int)}
     */
    @Test
    void testAnyNewFail() {
        int t = 5;
        int n = t - 1;

        for (int i = 0; i < 3; i++) {
            QualityGateEnforcer enforcer = new QualityGateEnforcer();
            StaticAnalysisRun run = mock(StaticAnalysisRun.class);
            when(run.getNewSize()).thenReturn(n);

            QualityGateBuilder builder = new QualityGateBuilder();
            builder.setAllowedNewBeforeFail(t, Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE); // Changed Line
            QualityGate qualityGate = builder.build();

            Result failure = enforcer.evaluate(run, qualityGate);

            if (n < t) {
                assertThat(failure)
                        .as("Less issues than allowed should be SUCCESS")
                        .isEqualTo(Result.SUCCESS);
            }
            else if (n == t) {
                assertThat(failure)
                        .as("Amount of issues equals allowed issues should be SUCCESS")
                        .isEqualTo(Result.SUCCESS);
            }
            else if (n > t) {
                assertThat(failure)
                        .as("More issues than allowed should be FAILURE")
                        .isEqualTo(Result.FAILURE);
            }

            n++;
        }
    }

    @Test
    void shouldFailWhenASingelFieldCriteriaFailsWithNewValues() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);
        when(run.getTotalHighPrioritySize()).thenReturn(3);
        when(run.getTotalNormalPrioritySize()).thenReturn(1);
        when(run.getTotalLowPrioritySize()).thenReturn(11);

        when(run.getNewSize()).thenReturn(1);
        when(run.getNewHighPrioritySize()).thenReturn(2);
        when(run.getNewNormalPrioritySize()).thenReturn(3);
        when(run.getNewLowPrioritySize()).thenReturn(4);

        QualityGateBuilder builder = new QualityGateBuilder();
        builder.setAllowedTotalBeforeFail(1, 4, 1, 11);
        builder.setAllowedNewBeforeFail(1, 2, 3, 3);
        QualityGate qualityGate = builder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("3 New low prio issues allowed and 4 returned should fail")
                .isEqualTo(Result.FAILURE);
    }
}
