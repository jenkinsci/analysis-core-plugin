package io.jenkins.plugins.analysis.core.quality;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import edu.hm.hafner.analysis.Priority;
import static io.jenkins.plugins.analysis.core.quality.AllPrioritiesThreshold.Builder.allPrioritiesThreshold;
import static io.jenkins.plugins.analysis.core.quality.MockStaticAnalysisRunBuilder.*;
import static io.jenkins.plugins.analysis.core.quality.QualityGate.Builder.*;
import static org.assertj.core.api.Assertions.*;

import hudson.model.Result;

/**
 * Tests the class {@link QualityGateEnforcer}.
 *
 * @author Ullrich Hafner
 */
class QualityGateEnforcerTest {
    @ParameterizedTest(name = "{0}")
    @MethodSource("evaluateArguments")
    void evaluate(final String testDescription, final QualityGate qualityGate, final StaticAnalysisRun run, final Result expectedResult) {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();

        Result result = enforcer.evaluate(run, qualityGate);

        assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> evaluateArguments() {
        return Stream.of(
                Arguments.of(
                        "quality gate without thresholds, run with issues",
                        qualityGate().build(),
                        analysisRun()
                                .withTotalIssueCount(Priority.LOW, 10)
                                .withTotalIssueCount(Priority.NORMAL, 5)
                                .withTotalIssueCount(Priority.HIGH, 1)
                                .withTotalIssueCount(16)
                                .withNewIssueCount(Priority.LOW, 1)
                                .withNewIssueCount(Priority.NORMAL, 1)
                                .withNewIssueCount(Priority.HIGH, 1)
                                .withNewIssueCount(3)
                                .build(),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "quality gate totalAllPriorities = 0, totalIssueCount = 0",
                        qualityGate()
                                .withThreshold(allPrioritiesThreshold()
                                        .withResult(Result.FAILURE)
                                        .withValue(0)
                                        .build())
                                .build(),
                        analysisRun()
                                .withTotalIssueCount(0)
                                .build(),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "quality gate totalAllPriorities = 0, totalIssueCount > 0",
                        qualityGate()
                                .withThreshold(allPrioritiesThreshold()
                                        .withResult(Result.FAILURE)
                                        .withValue(0)
                                        .build())
                                .build(),
                        analysisRun()
                                .withTotalIssueCount(1)
                                .build(),
                        Result.FAILURE
                ),
                Arguments.of(
                        "quality gate totalAllPriorities = 3, totalIssueCount < 3, newIssueCount irrelevant",
                        qualityGate()
                                .withThreshold(allPrioritiesThreshold()
                                        .withResult(Result.FAILURE)
                                        .withValue(3)
                                        .build())
                                .build(),
                        analysisRun()
                                .withTotalIssueCount(2)
                                .withNewIssueCount(4)
                                .build(),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "quality gate totalAllPriorities = 3, totalIssueCount = 3",
                        qualityGate()
                                .withThreshold(allPrioritiesThreshold()
                                        .withResult(Result.FAILURE)
                                        .withValue(3)
                                        .build())
                                .build(),
                        analysisRun()
                                .withTotalIssueCount(3)
                                .build(),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "quality gate totalAllPriorities = 3, totalIssueCount > 3",
                        qualityGate()
                                .withThreshold(allPrioritiesThreshold()
                                        .withResult(Result.FAILURE)
                                        .withValue(3)
                                        .build())
                                .build(),
                        analysisRun()
                                .withTotalIssueCount(4)
                                .build(),
                        Result.FAILURE
                ),
                Arguments.of(
                        "quality gate newAllPriorities = 0, newIssueCount = 0",
                        qualityGate()
                                .withThreshold(allPrioritiesThreshold()
                                        .withOnlyForNewIssues(true)
                                        .withResult(Result.FAILURE)
                                        .withValue(0)
                                        .build())
                                .build(),
                        analysisRun()
                                .withNewIssueCount(0)
                                .build(),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "quality gate newAllPriorities = 0, newIssueCount > 0",
                        qualityGate()
                                .withThreshold(allPrioritiesThreshold()
                                        .withResult(Result.FAILURE)
                                        .withOnlyForNewIssues(true)
                                        .withValue(0)
                                        .build())
                                .build(),
                        analysisRun()
                                .withNewIssueCount(1)
                                .build(),
                        Result.FAILURE
                ),
                Arguments.of(
                        "quality gate newAllPriorities = 3, newIssueCount < 3, totalIssueCount irrelevant",
                        qualityGate()
                                .withThreshold(allPrioritiesThreshold()
                                        .withResult(Result.FAILURE)
                                        .withOnlyForNewIssues(true)
                                        .withValue(3)
                                        .build())
                                .build(),
                        analysisRun()
                                .withNewIssueCount(2)
                                .withTotalIssueCount(4)
                                .build(),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "quality gate newAllPriorities = 3, newIssueCount = 3",
                        qualityGate()
                                .withThreshold(allPrioritiesThreshold()
                                        .withResult(Result.FAILURE)
                                        .withOnlyForNewIssues(true)
                                        .withValue(3)
                                        .build())
                                .build(),
                        analysisRun()
                                .withNewIssueCount(3)
                                .build(),
                        Result.SUCCESS
                ),
                Arguments.of(
                        "quality gate newAllPriorities = 3, newIssueCount > 3",
                        qualityGate()
                                .withThreshold(allPrioritiesThreshold()
                                        .withResult(Result.FAILURE)
                                        .withOnlyForNewIssues(true)
                                        .withValue(3)
                                        .build())
                                .build(),
                        analysisRun()
                                .withNewIssueCount(4)
                                .build(),
                        Result.FAILURE
                ),
                Arguments.of(
                        "quality gate newAllPriorities = 3, newIssueCount > 3",
                        qualityGate()
                                .withThreshold(allPrioritiesThreshold()
                                        .withResult(Result.FAILURE)
                                        .withOnlyForNewIssues(true)
                                        .withValue(3)
                                        .build())
                                .build(),
                        analysisRun()
                                .withNewIssueCount(4)
                                .build(),
                        Result.FAILURE
                )
        );
    }
}
