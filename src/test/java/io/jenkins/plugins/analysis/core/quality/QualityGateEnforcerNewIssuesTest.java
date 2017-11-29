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
 * Tests the class {@link QualityGateEnforcer} with thresholds for new issues.
 */
class QualityGateEnforcerNewIssuesTest {

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
                        "shouldBeFailureWhenBothTotalNewThresholdsAreHit",
                        "When both unstable and failure threshold are hit, should always be FAILURE",
                        new QualityGateConfigBuilder().newFailureThresholdAllPrios(1).newWarningThresholdAllPrios(2).build(),
                        stubStaticAnalysisRun(2, 0, 0, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldBeFailureWhenBothHighThresholdsAreHit",
                        "When both unstable and failure threshold are hit, should always be FAILURE",
                        new QualityGateConfigBuilder().newFailureThresholdHighPrio(1).newWarningThresholdHighPrio(2).build(),
                        stubStaticAnalysisRun(0, 2, 0, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldBeFailureWhenBothNormalThresholdsAreHit",
                        "When both unstable and failure threshold are hit, should always be FAILURE",
                        new QualityGateConfigBuilder().newFailureThresholdNormalPrio(1).newWarningThresholdNormalPrio(2).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldBeFailureWhenBothLowThresholdsAreHit",
                        "When both unstable and failure threshold are hit, should always be FAILURE",
                        new QualityGateConfigBuilder().newFailureThresholdLowPrio(1).newWarningThresholdLowPrio(2).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.FAILURE
                )
        );
    }

    private static Stream<Object> generateFailureTestData() {
        return Stream.of(
                Arguments.of(
                        "shouldBeSuccessfulWhenNoNewIssuesPresentAndNoQualityGateIsSet",
                        "No issues and no quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldBeSuccessfulWhenNoNewIssuesPresentAndFailureQualityGateIsSet",
                        "No issues and failure quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().newFailureThresholdAllPrios(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldFailBuildIfNewFailureThresholdIsSetAndNewIssueCountEquals",
                        "One issue with threshold one should return FAILURE",
                        new QualityGateConfigBuilder().newFailureThresholdAllPrios(1).build(),
                        stubStaticAnalysisRun(1, 0, 0, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldFailBuildIfNewFailureThresholdIsSetAndNewIssueCountIsAbove",
                        "Two issues with threshold one should return FAILURE",
                        new QualityGateConfigBuilder().newFailureThresholdAllPrios(1).build(),
                        stubStaticAnalysisRun(2, 0, 0, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldBeSuccessfulWhenNoNewIssuesPresentAndHighFailureQualityGateIsSet",
                        "No issues and high failure quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().newFailureThresholdHighPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldFailBuildIfNewHighFailureThresholdIsSetAndNewIssueCountEquals",
                        "One issue with high threshold one should return FAILURE",
                        new QualityGateConfigBuilder().newFailureThresholdHighPrio(1).build(),
                        stubStaticAnalysisRun(0, 1, 0, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldFailBuildIfNewHighFailureThresholdIsSetAndNewIssuesAreAbove",
                        "Two issues with high threshold one should return FAILURE",
                        new QualityGateConfigBuilder().newFailureThresholdHighPrio(1).build(),
                        stubStaticAnalysisRun(0, 2, 0, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldBeSuccessfulWhenNoNewIssuesPresentAndNewNormalFailureQualityGateIsSet",
                        "No issues and normal failure quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().newFailureThresholdNormalPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldFailBuildIfNewNormalFailureThresholdIsSetAndNewIssueCountEquals",
                        "One issue with normal threshold one should return FAILURE",
                        new QualityGateConfigBuilder().newFailureThresholdNormalPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 1, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldFailBuildIfNewNormalFailureThresholdIsSetAndNewIssueCountAbove",
                        "One issue with normal threshold one should return FAILURE",
                        new QualityGateConfigBuilder().newFailureThresholdNormalPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldBeSuccessfulWhenNoNewIssuesPresentAndNewLowFailureQualityGateIsSet",
                        "No issues and low failure quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().newFailureThresholdLowPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldFailBuildIfNewLowFailureThresholdIsSetAndNewIssueCountEquals",
                        "One issue with low threshold one should return FAILURE",
                        new QualityGateConfigBuilder().newFailureThresholdLowPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 1),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldFailBuildIfNewLowFailureThresholdIsSetAndNewIssueCountAbove",
                        "One issue with low threshold one should return FAILURE",
                        new QualityGateConfigBuilder().newFailureThresholdLowPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldNotAffectBuildWhenNewLowIssuesAreAboveNewHighThreshold",
                        "Two lowprio issues with high threshold one should return SUCCESS",
                        new QualityGateConfigBuilder().newFailureThresholdHighPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldNotAffectBuildWhenNewLowIssuesAreAboveNewNormalThreshold",
                        "Two lowprio issues with normal threshold one should return SUCCESS",
                        new QualityGateConfigBuilder().newFailureThresholdNormalPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldNotAffectBuildWhenNewNormalIssuesAreAboveNewLowThreshold",
                        "Two normalprio issues with low threshold one should return SUCCESS",
                        new QualityGateConfigBuilder().newFailureThresholdLowPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldAcceptNoNewLowIssuesWithNewLowThresholdZero",
                        "Zero Threshold and one issue should return FAILURE",
                        new QualityGateConfigBuilder().newFailureThresholdLowPrio(0).build(),
                        stubStaticAnalysisRun(0, 0, 0, 1),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldDeactivateNewLowThresholdWithCloseNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().newFailureThresholdLowPrio(-1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldDeactivateNewLowThresholdWithVeryNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().newFailureThresholdLowPrio(-10).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldAcceptNoNewNormalIssuesWithNewLowThresholdZero",
                        "Zero Threshold and one issue should return FAILURE",
                        new QualityGateConfigBuilder().newFailureThresholdLowPrio(0).build(),
                        stubStaticAnalysisRun(0, 0, 0, 1),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldDeactivateNewNormalThresholdWithCloseNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().newFailureThresholdNormalPrio(-1).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldDeactivateNewNormalThresholdWithVeryNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().newFailureThresholdNormalPrio(-10).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldAcceptNoNewHighIssuesWithNewLowThresholdZero",
                        "Zero Threshold and one issue should return FAILURE",
                        new QualityGateConfigBuilder().newFailureThresholdHighPrio(0).build(),
                        stubStaticAnalysisRun(0, 1, 0, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldDeactivateNewHighThresholdWithCloseNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().newFailureThresholdHighPrio(-1).build(),
                        stubStaticAnalysisRun(0, 2, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldDeactivateNewHighThresholdWithVeryNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().newFailureThresholdHighPrio(-10).build(),
                        stubStaticAnalysisRun(0, 2, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldAcceptNoNewTotalIssuesWithLowThresholdZero",
                        "Zero Threshold and one issue should return FAILURE",
                        new QualityGateConfigBuilder().newFailureThresholdAllPrios(0).build(),
                        stubStaticAnalysisRun(1, 0, 0, 0),
                        Result.FAILURE
                ),
                Arguments.of(
                        "shouldDeactivateNewTotalThresholdWithCloseNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().newFailureThresholdAllPrios(-1).build(),
                        stubStaticAnalysisRun(2, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldDeactivateNewTotalThresholdWithVeryNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().newFailureThresholdAllPrios(-10).build(),
                        stubStaticAnalysisRun(2, 0, 0, 0),
                        Result.SUCCESS
                )
        );
    }

    private static Stream<Object> generateUnstableTestData() {
        return Stream.of(
                Arguments.of(
                        "shouldBeSuccessfulWhenNoNewIssuesPresentAndNoNewWarningQualityGateIsSet",
                        "No issues and no quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldBeSuccessfulWhenNoNewIssuesPresentAndNewWarningQualityGateIsSet",
                        "No issues and failure quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().newWarningThresholdAllPrios(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldBeUnstableBuildIfNewWarningThresholdIsSetAndNewIssueCountEquals",
                        "One issue with threshold one should return UNSTABLE",
                        new QualityGateConfigBuilder().newWarningThresholdAllPrios(1).build(),
                        stubStaticAnalysisRun(1, 0, 0, 0),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldBeUnstableBuildIfNewWarningThresholdIsSetAndNewIssueCountIsAbove",
                        "One issue with threshold one should return UNSTABLE",
                        new QualityGateConfigBuilder().newWarningThresholdAllPrios(1).build(),
                        stubStaticAnalysisRun(2, 0, 0, 0),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldBeSuccessfulWhenNoNewIssuesPresentAndNewHighWarningQualityGateIsSet",
                        "No issues and high warning quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().newWarningThresholdHighPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldBeUnstableBuildIfNewHighWarningThresholdIsSetAndNewIssuesCountEquals",
                        "One issue with high threshold one should return UNSTABLE",
                        new QualityGateConfigBuilder().newWarningThresholdHighPrio(1).build(),
                        stubStaticAnalysisRun(0, 1, 0, 0),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldBeUnstableBuildIfNewHighWarningThresholdIsSetAndNewIssuesCountIsAbove",
                        "One issue with high threshold one should return UNSTABLE",
                        new QualityGateConfigBuilder().newWarningThresholdHighPrio(1).build(),
                        stubStaticAnalysisRun(0, 2, 0, 0),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldBeSuccessfulWhenNoNewIssuesPresentAndNewNormalWarningQualityGateIsSet",
                        "No issues and normal warning quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().newWarningThresholdNormalPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldBeUnstableBuildIfNewNormalWarningThresholdIsSetAndNewIssueCountEquals",
                        "One issue with normal threshold one should return UNSTABLE",
                        new QualityGateConfigBuilder().newWarningThresholdNormalPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 1, 0),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldBeUnstableBuildIfNewNormalWarningThresholdIsSetAndNewIssueCountIsAbove",
                        "One issue with normal threshold one should return UNSTABLE",
                        new QualityGateConfigBuilder().newWarningThresholdNormalPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldBeSuccessfulWhenNoNewIssuesPresentAndNewLowWarningQualityGateIsSet",
                        "No issues and low failure quality gate should always be a SUCCESS",
                        new QualityGateConfigBuilder().newWarningThresholdLowPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldBeUnstableBuildIfNewLowWarningThresholdIsSetAndNewIssueCountEquals",
                        "One issue with low threshold one should return UNSTABLE",
                        new QualityGateConfigBuilder().newWarningThresholdLowPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 1),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldBeUnstableBuildIfNewLowWarningThresholdIsSetAndNewIssueCountIsAbove",
                        "One issue with low threshold one should return UNSTABLE",
                        new QualityGateConfigBuilder().newWarningThresholdLowPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldNotAffectBuildWhenNewLowIssuesAreAboveNewHighThreshold",
                        "Two lowprio issues with high threshold one should return SUCCESS",
                        new QualityGateConfigBuilder().newWarningThresholdHighPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldNotAffectBuildWhenNewLowIssuesAreAboveNewNormalThreshold",
                        "Two lowprio issues with normal threshold one should return SUCCESS",
                        new QualityGateConfigBuilder().newWarningThresholdNormalPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldNotAffectBuildWhenNewNormalIssuesAreAboveNewLowThreshold",
                        "Two normalprio issues with low threshold one should return SUCCESS",
                        new QualityGateConfigBuilder().newWarningThresholdLowPrio(1).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldAcceptNoNewLowIssuesWithNewLowThresholdZero",
                        "Zero Threshold and one issue should return UNSTABLE",
                        new QualityGateConfigBuilder().newWarningThresholdLowPrio(0).build(),
                        stubStaticAnalysisRun(0, 0, 0, 1),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldDeactivateNewLowThresholdWithCloseNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().newWarningThresholdLowPrio(-1).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldDeactivateNewLowThresholdWithVeryNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().newWarningThresholdLowPrio(-10).build(),
                        stubStaticAnalysisRun(0, 0, 0, 2),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldAcceptNoNewNormalIssuesWithNewLowThresholdZero",
                        "Zero Threshold and one issue should return UNSTABLE",
                        new QualityGateConfigBuilder().newWarningThresholdLowPrio(0).build(),
                        stubStaticAnalysisRun(0, 0, 0, 1),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldDeactivateNewNormalThresholdWithCloseNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().newWarningThresholdNormalPrio(-1).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldDeactivateNewNormalThresholdWithVeryNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().newWarningThresholdNormalPrio(-10).build(),
                        stubStaticAnalysisRun(0, 0, 2, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldAcceptNoNewHighIssuesWithNewLowThresholdZero",
                        "Zero Threshold and one issue should return UNSTABLE",
                        new QualityGateConfigBuilder().newWarningThresholdHighPrio(0).build(),
                        stubStaticAnalysisRun(0, 1, 0, 0),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldDeactivateNewHighThresholdWithCloseNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().newWarningThresholdHighPrio(-1).build(),
                        stubStaticAnalysisRun(0, 2, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldDeactivateNewHighThresholdWithVeryNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().newWarningThresholdHighPrio(-10).build(),
                        stubStaticAnalysisRun(0, 2, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldAcceptNoNewTotalIssuesWithNewLowThresholdZero",
                        "Zero Threshold and one issue should return UNSTABLE",
                        new QualityGateConfigBuilder().newWarningThresholdAllPrios(0).build(),
                        stubStaticAnalysisRun(1, 0, 0, 0),
                        Result.UNSTABLE
                ),
                Arguments.of(
                        "shouldDeactivateNewTotalThresholdWithCloseNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().newWarningThresholdAllPrios(-1).build(),
                        stubStaticAnalysisRun(2, 0, 0, 0),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "shouldDeactivateNewTotalThresholdWithVeryNegativeInput",
                        "Negative Threshold should always return SUCCESS",
                        new QualityGateConfigBuilder().newWarningThresholdAllPrios(-10).build(),
                        stubStaticAnalysisRun(2, 0, 0, 0),
                        Result.SUCCESS
                )
        );
    }

    private static StaticAnalysisRun stubStaticAnalysisRun(final int totalSize, final int highSize, final int normalSize, final int lowSize) {
        StaticAnalysisRun staticAnalysisRun = mock(StaticAnalysisRun.class);
        when(staticAnalysisRun.getNewSize()).thenReturn(totalSize);
        when(staticAnalysisRun.getNewHighPrioritySize()).thenReturn(highSize);
        when(staticAnalysisRun.getNewNormalPrioritySize()).thenReturn(normalSize);
        when(staticAnalysisRun.getNewLowPrioritySize()).thenReturn(lowSize);
        return staticAnalysisRun;
    }
}
