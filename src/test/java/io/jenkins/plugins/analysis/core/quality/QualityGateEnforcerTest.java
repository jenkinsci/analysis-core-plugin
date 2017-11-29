package io.jenkins.plugins.analysis.core.quality;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import hudson.model.Result;

/**
 * Tests the class {@link QualityGateEnforcer}.
 *
 * @author Andreas Moser
 */
class QualityGateEnforcerTest {

    /** Verifies that a build is successful if no issues present and no quality gate is set. */
    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndNoQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        QualityGate qualityGate = qualityGateBuilder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and no quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    /** Verifies that a build is successful if no issues present and quality gate is set. */
    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndFailureQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setFailureThreshold(1);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("No issues and any failure quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    /** Verifies that a build is successful if issues are present but no quality gate is set. */
    @Test
    void shouldBeSuccessfulWithIssuesButNoQualityGateIsSet() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(2);
        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        QualityGate qualityGate = qualityGateBuilder.build();

        Result success = enforcer.evaluate(run, qualityGate);

        assertThat(success)
                .as("Issues but no quality gate should always be a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    /** Verifies that a build fails if the number of issues is equal the failure threshold. */
    @Test
    void shouldFailBuildIfFailureThresholdIsSetIssuesSizeEqualThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(2);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setFailureThreshold(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Two issues and failure threshold two should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /** Verifies that a build fails if the number of issues is greater than the failure threshold. */
    @Test
    void shouldFailBuildIfFailureThresholdIsSetIssuesSizeGreaterThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setFailureThreshold(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Three issues and failure threshold two should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /** Verifies that a build is successful if number of issues less than failure threshold. */
    @Test
    void shouldBeSuccessfulIfFailureThresholdIsSetIssuesSizeLessThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(1);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setFailureThreshold(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One issue and failure threshold two should return a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    //New warnings

    /** Verifies that a build fails if the number of new issues is equal the new issues failure threshold. */
    @Test
    void shouldFailBuildIfNewIssuesEqualNewIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewSize()).thenReturn(2);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesFailureThreshold(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Two new issues and new issue failure threshold two should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /** Verifies that a build fails if the number of new issues is greater than the new issues failure threshold. */
    @Test
    void shouldFailBuildIfNewIssuesGreaterThanNewIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewSize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesFailureThreshold(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Three new issues and new issue failure threshold two should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /** Verifies that a build is successful if number of new issues less than new issues failure threshold. */
    @Test
    void shouldBeSuccessfulIfNewIssuesLessThanNewIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewSize()).thenReturn(1);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesFailureThreshold(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One new issue and new issue failure threshold two should return a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    /**
     * Verifies that a build is successful if number of new issues greater than failure threshold but check new issues
     * is disabled.
     */
    @Test
    void shouldBeSuccessfulIfNewIssuesGreaterThanNewIssuesThresholdButCheckNewIssuesDisabled() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewSize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesFailureThreshold(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Check new issues disabled and total issues less than threshold should return a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    //Low

    /** Verifies that a build fails if the number of low priority issues is equal the failure threshold. */
    @Test
    void shouldFailBuildIfLowIssuesSizeEqualLowIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalLowPrioritySize()).thenReturn(2);


        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setFailureThresholdLow(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Two low priority issues and failure threshold two should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /** Verifies that a build fails if the number of low priority issues is greater than the failure threshold. */
    @Test
    void shouldFailBuildIfLowIssuesSizeGreaterLowIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalLowPrioritySize()).thenReturn(3);


        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setFailureThresholdLow(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Three low priority issues and failure threshold two should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /** Verifies that a build is successful if number of normal priority issues less than failure threshold. */
    @Test
    void shouldBeSuccessfulIfLowIssuesSizeLessLowIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalLowPrioritySize()).thenReturn(1);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setFailureThresholdLow(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One low priority issues and failure threshold two should return a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    /** Verifies that a build fails if the number of new low priority issues is equal the failure threshold. */
    @Test
    void shouldFailBuildIfNewLowIssuesSizeEqualNewLowIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewLowPrioritySize()).thenReturn(2);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesFailureThresholdLow(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Two new low priority issues and new low issues failure threshold two should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /** Verifies that a build fails if the number of new low priority issues is greater the failure threshold. */
    @Test
    void shouldFailBuildIfNewLowIssuesSizeGreaterNewLowIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewLowPrioritySize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesFailureThresholdLow(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Three new low priority issues and new low issues failure threshold two should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /** Verifies that a build is successful if number of new low priority issues less than failure threshold. */
    @Test
    void shouldBeSuccessfulIfNewLowIssuesSizeLessNewLowIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewLowPrioritySize()).thenReturn(1);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesFailureThresholdLow(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One new low priority issue and new low issues failure threshold two should return a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    /**
     * Verifies that a build is successful if number of new low priority issues greater than failure threshold but check
     * new issues is disabled.
     */
    @Test
    void shouldBeSuccessfulIfNewLowIssuesGreaterThanNewLowIssuesThresholdButCheckNewIssuesDisabled() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewLowPrioritySize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesFailureThresholdLow(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Check new issues disabled and total low issues less than low threshold should return a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    //Normal

    /** Verifies that a build fails if the number of normal priority issues is equal the failure threshold. */
    @Test
    void shouldFailBuildIfNormalIssuesSizeEqualNormalIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalNormalPrioritySize()).thenReturn(2);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setFailureThresholdNormal(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Two normal priority issues and failure threshold two should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /** Verifies that a build fails if the number of normal priority issues is greater than the failure threshold. */
    @Test
    void shouldFailBuildIfNormalIssuesSizeGreaterNormalIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalNormalPrioritySize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setFailureThresholdNormal(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Three normal priority issues and failure threshold two should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /** Verifies that a build is successful if number of normal priority issues less than failure threshold. */
    @Test
    void shouldBeSuccessfulIfNormalIssuesSizeLessNormalIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalNormalPrioritySize()).thenReturn(1);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setFailureThresholdNormal(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One normal priority issues and failure threshold two should return a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    /** Verifies that a build fails if the number of new normal priority issues is equal the failure threshold. */
    @Test
    void shouldFailBuildIfNewNormalIssuesSizeEqualNewNormalIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewNormalPrioritySize()).thenReturn(2);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesFailureThresholdNormal(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Two new normal priority issues and new normal issues failure threshold two should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /** Verifies that a build fails if the number of new normal priority issues is greater than the failure threshold. */
    @Test
    void shouldFailBuildIfNewNormalIssuesSizeGreaterNewNormalIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewNormalPrioritySize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesFailureThresholdNormal(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Three new normal priority issues and new normal issues failure threshold two should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /** Verifies that a build is successful if number of new normal priority issues less than failure threshold. */
    @Test
    void shouldBeSuccessfulIfNewNormalIssuesSizeLessNewNormalIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewNormalPrioritySize()).thenReturn(1);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesFailureThresholdNormal(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One new normal priority issue and new normal issues failure threshold two should return a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    /**
     * Verifies that a build is successful if number of new normal priority issues greater than failure threshold but
     * check new issues is disabled.
     */
    @Test
    void shouldBeSuccessfulIfNewNormalIssuesGreaterThanNewNormalIssuesThresholdButCheckNewIssuesDisabled() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewNormalPrioritySize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesFailureThresholdNormal(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Check new issues disabled and total normal issues less than normal threshold should return a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    //High

    /** Verifies that a build fails if the number of high priority issues is equal the failure threshold. */
    @Test
    void shouldFailBuildIfHighIssuesSizeEqualHighIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalHighPrioritySize()).thenReturn(2);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setFailureThresholdHigh(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Two high priority issues and failure threshold two should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /** Verifies that a build fails if the number of high priority issues is greater the failure threshold. */
    @Test
    void shouldFailBuildIfHighIssuesSizeGreaterHighIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalHighPrioritySize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setFailureThresholdHigh(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Three high priority issues and failure threshold two should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /** Verifies that a build is successful if number of high priority issues less than failure threshold. */
    @Test
    void shouldBeSuccessfulIfHighIssuesSizeLessHighIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalHighPrioritySize()).thenReturn(1);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setFailureThresholdHigh(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One high priority issues and failure threshold two should return a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    /** Verifies that a build fails if the number of new high priority issues is equal the failure threshold. */
    @Test
    void shouldFailBuildIfNewHighIssuesSizeEqualNewHighIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewHighPrioritySize()).thenReturn(2);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesFailureThresholdHigh(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Two new high priority issues and new high issues failure threshold two should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /** Verifies that a build fails if the number of new high priority issues is greater than the failure threshold. */
    @Test
    void shouldFailBuildIfNewHighIssuesSizeGreaterNewHighIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewHighPrioritySize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesFailureThresholdHigh(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Three new high priority issues and new high issues failure threshold two should return a FAILURE")
                .isEqualTo(Result.FAILURE);
    }

    /** Verifies that a build is successful if number of new high priority issues less than failure threshold. */
    @Test
    void shouldBeSuccessfulIfNewHighIssuesSizeLessNewHighIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewNormalPrioritySize()).thenReturn(1);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesFailureThresholdHigh(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();
        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("One new high priority issue and new high issues failure threshold two should return a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    /**
     * Verifies that a build is successful if number of new high priority issues greater than failure threshold but
     * check new issues is disabled.
     */
    @Test
    void shouldBeSuccessfulIfNewHighIssuesGreaterThanNewHighIssuesThresholdButCheckNewIssuesDisabled() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewHighPrioritySize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesFailureThresholdHigh(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Check new issues disabled and total high issues less than high threshold should return a SUCCESS")
                .isEqualTo(Result.SUCCESS);
    }

    //isUnstable

    /** Verifies that a build is unstable if the number of issues is equal the unstable threshold. */
    @Test
    void shouldBeUnstableBuildIfUnstableThresholdIsSetIssuesSizeEqualThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(2);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setUnstableThreshold(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Two issues and unstable threshold two should be UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    /** Verifies that a build is unstable if the number of issues is greater than the unstable threshold. */
    @Test
    void shouldBeUnstableBuildIfUnstableThresholdIsSetIssuesSizeGreaterThanThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setUnstableThreshold(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Three issues and unstable threshold two should be UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    /** Verifies that a build is unstable if the number of new issues is equal the new issues unstable threshold. */
    @Test
    void shouldBeUnstableBuildIfNewIssuesEqualNewIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewSize()).thenReturn(2);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesUnstableThreshold(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Two new issues and new issue unstable threshold two should be UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    /** Verifies that a build is unstable if the number of new issues is greater than the new issues unstable threshold. */
    @Test
    void shouldBeUnstableBuildIfNewIssuesGreaterThanNewIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewSize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesUnstableThreshold(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Three new issues and new issue unstable threshold two should be UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }


    /** Verifies that a build is unstable if the number of low priority issues is equal the unstable threshold. */
    @Test
    void shouldBeUnstableBuildIfLowIssuesSizeEqualLowIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalLowPrioritySize()).thenReturn(2);


        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setUnstableThresholdLow(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Two low priority issues and unstable threshold two should be UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    /** Verifies that a build is unstable if the number of low priority issues is greater than the unstable threshold. */
    @Test
    void shouldBeUnstableBuildIfLowIssuesSizeGreaterLowIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalLowPrioritySize()).thenReturn(3);


        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setUnstableThresholdLow(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Three low priority issues and unstable threshold two should be UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    /** Verifies that a build is unstable if the number of new low priority issues is equal the new unstable threshold. */
    @Test
    void shouldBeUnstableBuildIfNewLowIssuesSizeEqualNewLowIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewLowPrioritySize()).thenReturn(2);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesUnstableThresholdLow(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Two new low priority issues and new low issues unstable threshold two should be UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    /**
     * Verifies that a build is unstable if the number of new low priority issues is greater than the new unstable
     * threshold.
     */
    @Test
    void shouldBeUnstableBuildIfNewLowIssuesSizeGreaterNewLowIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewLowPrioritySize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesUnstableThresholdLow(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Three new low priority issues and new low issues unstable threshold two should be UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }


    /** Verifies that a build is unstable if the number of normal priority issues is equal the unstable threshold. */
    @Test
    void shouldBeUnstableBuildIfNormalIssuesSizeEqualNormalIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalNormalPrioritySize()).thenReturn(2);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setUnstableThresholdNormal(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Two normal priority issues and unstable threshold two should be UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    /**
     * Verifies that a build is unstable if the number of normal priority issues is greater than the unstable
     * threshold.
     */
    @Test
    void shouldBeUnstableBuildIfNormalIssuesSizeGreaterNormalIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalNormalPrioritySize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setUnstableThresholdNormal(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Three normal priority issues and unstable threshold two should be UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    /** Verifies that a build is unstable if the number of new normal priority issues is equal the new unstable threshold. */
    @Test
    void shouldBeUnstableBuildIfNewNormalIssuesSizeEqualNewNormalIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewNormalPrioritySize()).thenReturn(2);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesUnstableThresholdNormal(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Two new normal priority issues and new normal issues unstable threshold two should be UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    /** Verifies that a build is unstable if the number of new normal priority issues is greater than the new unstable threshold. */
    @Test
    void shouldBeUnstableBuildIfNewNormalIssuesSizeGreaterNewNormalIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewNormalPrioritySize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesUnstableThresholdNormal(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Three new normal priority issues and new normal issues unstable threshold two should be UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }


    /** Verifies that a build is unstable if the number of high priority issues is equal the unstable threshold. */
    @Test
    void shouldBeUnstableBuildIfHighIssuesSizeEqualHighIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalHighPrioritySize()).thenReturn(2);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setUnstableThresholdHigh(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Two high priority issues and unstable threshold two should be UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    /** Verifies that a build is unstable if the number of high priority issues is greater than the unstable threshold. */
    @Test
    void shouldBeUnstableBuildIfHighIssuesSizeGreaterHighIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalHighPrioritySize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setUnstableThresholdHigh(2);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Three high priority issues and unstable threshold two should be UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    /** Verifies that a build is unstable if the number of new high priority issues is equal the new unstable threshold. */
    @Test
    void shouldBeUnstableBuildIfNewHighIssuesSizeEqualNewHighIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewHighPrioritySize()).thenReturn(2);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesUnstableThresholdHigh(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Two new high priority issues and new high issues unstable threshold two should be UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

    /** Verifies that a build is unstable if the number of new high priority issues is greater than the new unstable threshold. */
    @Test
    void shouldBeUnstableBuildIfNewHighIssuesSizeGreaterNewHighIssuesThreshold() {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getNewHighPrioritySize()).thenReturn(3);

        QualityGateBuilder qualityGateBuilder = new QualityGateBuilder();
        qualityGateBuilder.setNewIssuesUnstableThresholdHigh(2).setCheckNewIssues(true);
        QualityGate qualityGate = qualityGateBuilder.build();

        Result failure = enforcer.evaluate(run, qualityGate);

        assertThat(failure)
                .as("Three new high priority issues and new high issues unstable threshold two should be UNSTABLE")
                .isEqualTo(Result.UNSTABLE);
    }

}
