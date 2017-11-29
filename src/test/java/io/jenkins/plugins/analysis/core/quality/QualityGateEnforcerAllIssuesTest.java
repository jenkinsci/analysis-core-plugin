package io.jenkins.plugins.analysis.core.quality;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.jenkins.plugins.analysis.core.quality.QualityGateConfig.QualityGateConfigBuilder;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import hudson.model.Result;

/**
 * Tests the class {@link QualityGateEnforcer} with thresholds for old issues.
 */
class QualityGateEnforcerAllIssuesTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("generateBothThresholdTestData")
    void testUnstableAndFailureThresholdAreHit(final String testName, final String failMessage, final QualityGateConfig config, final StaticAnalysisRun run, final Result expectedResult) {
        QualityGateEnforcer sut = new QualityGateEnforcer(config);

        Result result = sut.evaluate(run);

        assertThat(result).withFailMessage(failMessage)
                .isEqualTo(expectedResult);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("generateFailureTestData")
    void testEvaluateWithFailure(final String testName, final String failMessage, final QualityGateConfig config, final StaticAnalysisRun run, final Result expectedResult) {
        QualityGateEnforcer sut = new QualityGateEnforcer(config);

        Result result = sut.evaluate(run);

        assertThat(result).withFailMessage(failMessage)
                .isEqualTo(expectedResult);
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("generateUnstableTestData")
    void testEvaluateWithUnstable(final String testName, final String failMessage, final QualityGateConfig config, final StaticAnalysisRun run, final Result expectedResult) {
        QualityGateEnforcer sut = new QualityGateEnforcer(config);

        Result result = sut.evaluate(run);

        assertThat(result).withFailMessage(failMessage)
                .isEqualTo(expectedResult);
    }

    private static Stream<Object> generateBothThresholdTestData() {
        return Stream.of(
                Arguments.of(
                        "shouldBeFailureWhenBothTotalThresholdsAreHit",
                        "When both unstable and failure threshold are hit, should always be FAILURE",
                        new QualityGateConfigBuilder().failureThresholdAllPrios(1).warningThresholdAllPrios(2).build(),
                        stubStaticAnalysisRun(2, 0, 0, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldBeFailureWhenBothHighThresholdsAreHit",
                        "When both unstable and failure threshold are hit, should always be FAILURE",
                        new QualityGateConfigBuilder().failureThresholdHighPrio(1).warningThresholdHighPrio(2).build(),
                        stubStaticAnalysisRun(0, 2, 0, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldBeFailureWhenBothNormalThresholdsAreHit",
                        "When both unstable and failure threshold are hit, should always be FAILURE",
                        new QualityGateConfigBuilder().failureThresholdNormalPrio(1).warningThresholdNormalPrio(2).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldBeFailureWhenBothLowThresholdsAreHit",
                        "When both unstable and failure threshold are hit, should always be FAILURE",
                        new QualityGateConfigBuilder().failureThresholdLowPrio(1).warningThresholdLowPrio(2).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.FAILURE
                )
        );
    }

    private static Stream<Object> generateFailureTestData() {
        return Stream.of(
                Arguments.of(
                        "shouldBeSuccessfulWhenNoIssuesPresentAndNoQualityGateIsSet",
                        "No issues and no quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldBeSuccessfulWhenNoIssuesPresentAndFailureQualityGateIsSet",
                        "No issues and failure quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().failureThresholdAllPrios(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldFailBuildIfFailureThresholdIsSetAndBuildFailureEquals",
                        "One issue with threshold one should return FAILURE",
                        new QualityGateConfigBuilder().failureThresholdAllPrios(1).build(),
                        stubStaticAnalysisRun(1, 0, 0, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldFailBuildIfFailureThresholdIsSetAndBuildFailureAreAbove",
                        "Two issues with threshold one should return FAILURE",
                        new QualityGateConfigBuilder().failureThresholdAllPrios(1).build(),
                        stubStaticAnalysisRun(2, 0, 0, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldBeSuccessfulWhenNoIssuesPresentAndHighFailureQualityGateIsSet",
                        "No issues and high failure quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().failureThresholdHighPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldFailBuildIfHighFailureThresholdIsSetAndIssueCountEquals",
                        "One issue with high threshold one should return FAILURE",
                        new QualityGateConfigBuilder().failureThresholdHighPrio(1).build(),
                        stubStaticAnalysisRun(0, 1, 0, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldFailBuildIfHighFailureThresholdIsSetAndIssuesAreAbove",
                        "Two issues with high threshold one should return FAILURE",
                        new QualityGateConfigBuilder().failureThresholdHighPrio(1).build(),
                        stubStaticAnalysisRun(0, 2, 0, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldBeSuccessfulWhenNoIssuesPresentAndNormalFailureQualityGateIsSet",
                        "No issues and normal failure quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().failureThresholdNormalPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldFailBuildIfNormalFailureThresholdIsSetAndIssueCountEquals",
                        "One issue with normal threshold one should return FAILURE",
                        new QualityGateConfigBuilder().failureThresholdNormalPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 1, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldFailBuildIfNormalFailureThresholdIsSetAndIssueCountAbove",
                        "One issue with normal threshold one should return FAILURE",
                        new QualityGateConfigBuilder().failureThresholdNormalPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldBeSuccessfulWhenNoIssuesPresentAndLowFailureQualityGateIsSet",
                        "No issues and low failure quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().failureThresholdLowPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldFailBuildIfLowFailureThresholdIsSetAndIssueCountEquals",
                        "One issue with low threshold one should return FAILURE",
                        new QualityGateConfigBuilder().failureThresholdLowPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 1),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldFailBuildIfLowFailureThresholdIsSetAndIssueCountAbove",
                        "One issue with low threshold one should return FAILURE",
                        new QualityGateConfigBuilder().failureThresholdLowPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldNotAffectBuildWhenLowIssuesAreAboveHighThreshold",
                        "Two lowprio issues with high threshold one should return SUCCESS",
                        new QualityGateConfigBuilder().failureThresholdHighPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldNotAffectBuildWhenLowIssuesAreAboveNormalThreshold",
                        "Two lowprio issues with normal threshold one should return SUCCESS",
                        new QualityGateConfigBuilder().failureThresholdNormalPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldNotAffectBuildWhenNormalIssuesAreAboveLowThreshold",
                        "Two normalprio issues with low threshold one should return SUCCESS",
                        new QualityGateConfigBuilder().failureThresholdLowPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldAcceptNoLowIssuesWithLowThresholdZero",
                        "Zero Threshold and one issue should return FAILURE",
                        new QualityGateConfigBuilder().failureThresholdLowPrio(0).build(),
                        stubStaticAnalysisRun(0, 0, 0, 1),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldDeactivateLowThresholdWithCloseNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().failureThresholdLowPrio(-1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldDeactivateLowThresholdWithVeryNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().failureThresholdLowPrio(-10).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldAcceptNoNormalIssuesWithLowThresholdZero",
                        "Zero Threshold and one issue should return FAILURE",
                        new QualityGateConfigBuilder().failureThresholdLowPrio(0).build(),
                        stubStaticAnalysisRun(0, 0, 0, 1),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldDeactivateNormalThresholdWithCloseNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().failureThresholdNormalPrio(-1).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldDeactivateNormalThresholdWithVeryNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().failureThresholdNormalPrio(-10).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldAcceptNoHighIssuesWithLowThresholdZero",
                        "Zero Threshold and one issue should return FAILURE",
                        new QualityGateConfigBuilder().failureThresholdHighPrio(0).build(),
                        stubStaticAnalysisRun(0, 1, 0, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldDeactivateHighThresholdWithCloseNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().failureThresholdHighPrio(-1).build(),
                        stubStaticAnalysisRun(0, 2, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldDeactivateHighThresholdWithVeryNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().failureThresholdHighPrio(-10).build(),
                        stubStaticAnalysisRun(0, 2, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldAcceptNoTotalIssuesWithLowThresholdZero",
                        "Zero Threshold and one issue should return FAILURE",
                        new QualityGateConfigBuilder().failureThresholdAllPrios(0).build(),
                        stubStaticAnalysisRun(1, 0, 0, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldDeactivateTotalThresholdWithCloseNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().failureThresholdAllPrios(-1).build(),
                        stubStaticAnalysisRun(2, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldDeactivateTotalThresholdWithVeryNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().failureThresholdAllPrios(-10).build(),
                        stubStaticAnalysisRun(2, 0, 0, 0),
                        Result.SUCCESS
                )
        );
    }

    private static Stream<Object> generateUnstableTestData() {
        return Stream.of(
                Arguments.of(
                        "shouldBeSuccessfulWhenNoIssuesPresentAndNoWarningQualityGateIsSet",
                        "No issues and no quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldBeSuccessfulWhenNoIssuesPresentAndWarningQualityGateIsSet",
                        "No issues and failure quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().warningThresholdAllPrios(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldBeUnstableBuildIfWarningThresholdIsSetAndIssueCountEquals",
                        "One issue with threshold one should return UNSTABLE",
                        new QualityGateConfigBuilder().warningThresholdAllPrios(1).build(),
                        stubStaticAnalysisRun(1, 0, 0, 0),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldBeUnstableBuildIfWarningThresholdIsSetAndIssueCountIsAbove",
                        "One issue with threshold one should return UNSTABLE",
                        new QualityGateConfigBuilder().warningThresholdAllPrios(1).build(),
                        stubStaticAnalysisRun(2, 0, 0, 0),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldBeSuccessfulWhenNoIssuesPresentAndHighWarningQualityGateIsSet",
                        "No issues and high warning quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().warningThresholdHighPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldBeUnstableBuildIfHighWarningThresholdIsSetAndIssuesCountEquals",
                        "One issue with high threshold one should return UNSTABLE",
                        new QualityGateConfigBuilder().warningThresholdHighPrio(1).build(),
                        stubStaticAnalysisRun(0, 1, 0, 0),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldBeUnstableBuildIfHighWarningThresholdIsSetAndIssuesCountIsAbove",
                        "One issue with high threshold one should return UNSTABLE",
                        new QualityGateConfigBuilder().warningThresholdHighPrio(1).build(),
                        stubStaticAnalysisRun(0, 2, 0, 0),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldBeSuccessfulWhenNoIssuesPresentAndNormalWarningQualityGateIsSet",
                        "No issues and normal warning quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().warningThresholdNormalPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldBeUnstableBuildIfNormalWarningThresholdIsSetAndIssueCountEquals",
                        "One issue with normal threshold one should return UNSTABLE",
                        new QualityGateConfigBuilder().warningThresholdNormalPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 1, 0),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldBeUnstableBuildIfNormalWarningThresholdIsSetAndIssueCountIsAbove",
                        "One issue with normal threshold one should return UNSTABLE",
                        new QualityGateConfigBuilder().warningThresholdNormalPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldBeSuccessfulWhenNoIssuesPresentAndLowWarningQualityGateIsSet",
                        "No issues and low failure quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().warningThresholdLowPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldBeUnstableBuildIfLowWarningThresholdIsSetAndIssueCountEquals",
                        "One issue with low threshold one should return UNSTABLE",
                        new QualityGateConfigBuilder().warningThresholdLowPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 1),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldBeUnstableBuildIfLowWarningThresholdIsSetAndIssueCountIsAbove",
                        "One issue with low threshold one should return UNSTABLE",
                        new QualityGateConfigBuilder().warningThresholdLowPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldNotAffectBuildWhenLowIssuesAreAboveHighThreshold",
                        "Two lowprio issues with high threshold one should return SUCCESS",
                        new QualityGateConfigBuilder().warningThresholdHighPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldNotAffectBuildWhenLowIssuesAreAboveNormalThreshold",
                        "Two lowprio issues with normal threshold one should return SUCCESS",
                        new QualityGateConfigBuilder().warningThresholdNormalPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldNotAffectBuildWhenNormalIssuesAreAboveLowThreshold",
                        "Two normalprio issues with low threshold one should return SUCCESS",
                        new QualityGateConfigBuilder().warningThresholdLowPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldAcceptNoLowIssuesWithLowThresholdZero",
                        "Zero Threshold and one issue should return UNSTABLE",
                        new QualityGateConfigBuilder().warningThresholdLowPrio(0).build(),
                        stubStaticAnalysisRun(0, 0, 0, 1),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldDeactivateLowThresholdWithCloseNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().warningThresholdLowPrio(-1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldDeactivateLowThresholdWithVeryNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().warningThresholdLowPrio(-10).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldAcceptNoNormalIssuesWithLowThresholdZero",
                        "Zero Threshold and one issue should return UNSTABLE",
                        new QualityGateConfigBuilder().warningThresholdLowPrio(0).build(),
                        stubStaticAnalysisRun(0, 0, 0, 1),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldDeactivateNormalThresholdWithCloseNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().warningThresholdNormalPrio(-1).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldDeactivateNormalThresholdWithVeryNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().warningThresholdNormalPrio(-10).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldAcceptNoHighIssuesWithLowThresholdZero",
                        "Zero Threshold and one issue should return UNSTABLE",
                        new QualityGateConfigBuilder().warningThresholdHighPrio(0).build(),
                        stubStaticAnalysisRun(0, 1, 0, 0),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldDeactivateHighThresholdWithCloseNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().warningThresholdHighPrio(-1).build(),
                        stubStaticAnalysisRun(0, 2, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldDeactivateHighThresholdWithVeryNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().warningThresholdHighPrio(-10).build(),
                        stubStaticAnalysisRun(0, 2, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldAcceptNoTotalIssuesWithLowThresholdZero",
                        "Zero Threshold and one issue should return UNSTABLE",
                        new QualityGateConfigBuilder().warningThresholdAllPrios(0).build(),
                        stubStaticAnalysisRun(1, 0, 0, 0),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldDeactivateTotalThresholdWithCloseNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().warningThresholdAllPrios(-1).build(),
                        stubStaticAnalysisRun(2, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldDeactivateTotalThresholdWithVeryNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().warningThresholdAllPrios(-10).build(),
                        stubStaticAnalysisRun(2, 0, 0, 0),
                        Result.SUCCESS
                )
        );
    }

    private static StaticAnalysisRun stubStaticAnalysisRun(final int totalSize, final int highSize, final int normalSize, final int lowSize) {
        StaticAnalysisRun staticAnalysisRun = mock(StaticAnalysisRun.class);
        when(staticAnalysisRun.getTotalSize()).thenReturn(totalSize);
        when(staticAnalysisRun.getTotalHighPrioritySize()).thenReturn(highSize);
        when(staticAnalysisRun.getTotalNormalPrioritySize()).thenReturn(normalSize);
        when(staticAnalysisRun.getTotalLowPrioritySize()).thenReturn(lowSize);
        return staticAnalysisRun;
    }
}
