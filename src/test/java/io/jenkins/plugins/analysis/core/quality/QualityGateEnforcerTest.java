package io.jenkins.plugins.analysis.core.quality;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import edu.hm.hafner.analysis.Priority;
import static io.jenkins.plugins.analysis.core.quality.AllPrioritiesThreshold.Builder.*;
import static io.jenkins.plugins.analysis.core.quality.MockStaticAnalysisRunBuilder.*;
import static io.jenkins.plugins.analysis.core.quality.PriorityThreshold.Builder.*;
import static io.jenkins.plugins.analysis.core.quality.QualityGate.Builder.*;
import static org.assertj.core.api.Assertions.*;

import hudson.model.Result;

/**
 * Tests the class {@link QualityGateEnforcer}.
 *
 * @author Ullrich Hafner
 */
class QualityGateEnforcerTest {
    private static final int THRESHOLD_VALUE = 10;
    private static final int FAILURE_THRESHOLD_VALUE = 15;
    private static final IssueCountThreshold ALL_PRIORITIES_THRESHOLD = allPrioritiesThreshold()
            .withResult(Result.UNSTABLE)
            .withValue(THRESHOLD_VALUE)
            .build();
    private static final IssueCountThreshold HIGH_PRIORITY_THRESHOLD = priorityThreshold()
            .withPriority(Priority.HIGH)
            .withResult(Result.UNSTABLE)
            .withValue(THRESHOLD_VALUE)
            .build();
    private static final IssueCountThreshold NEW_ALL_PRIORITIES_THRESHOLD = allPrioritiesThreshold()
            .withOnlyForNewIssues(true)
            .withResult(Result.UNSTABLE)
            .withValue(THRESHOLD_VALUE)
            .build();
    private static final IssueCountThreshold NEW_HIGH_PRIORITY_THRESHOLD = priorityThreshold()
            .withPriority(Priority.HIGH)
            .withOnlyForNewIssues(true)
            .withResult(Result.UNSTABLE)
            .withValue(THRESHOLD_VALUE)
            .build();
    private static final IssueCountThreshold FAILURE_THRESHOLD = allPrioritiesThreshold()
            .withResult(Result.FAILURE)
            .withValue(FAILURE_THRESHOLD_VALUE)
            .build();
    private static final IssueCountThreshold LOW_PRIORITY_THRESHOLD = priorityThreshold()
            .withPriority(Priority.LOW)
            .withResult(Result.UNSTABLE)
            .withValue(THRESHOLD_VALUE)
            .build();
    private static final IssueCountThreshold NEW_LOW_PRIORITY_THRESHOLD = priorityThreshold()
            .withPriority(Priority.LOW)
            .withOnlyForNewIssues(true)
            .withResult(Result.UNSTABLE)
            .withValue(THRESHOLD_VALUE)
            .build();

    @ParameterizedTest
    @MethodSource("evaluateArguments")
    void evaluate(final String testDescription, final QualityGate qualityGate, final StaticAnalysisRun run, final Result expectedResult) {
        QualityGateEnforcer enforcer = new QualityGateEnforcer();

        Result result = enforcer.evaluate(run, qualityGate);

        assertThat(result).isEqualTo(expectedResult);
    }

    private static Stream<Arguments> evaluateArguments() {
        return Stream.of(
                Arguments.of(
                        "empty quality gate, no issues",
                        qualityGate().build(),
                        analysisRun().build(),
                        Result.SUCCESS),
                Arguments.of(
                        "empty quality gate, issues",
                        qualityGate().build(),
                        analysisRun().withTotalIssueCount(THRESHOLD_VALUE).build(),
                        Result.SUCCESS),
                Arguments.of(
                        "threshold, no issues",
                        qualityGate().withThreshold(ALL_PRIORITIES_THRESHOLD).build(),
                        analysisRun().build(),
                        Result.SUCCESS),

                Arguments.of(
                        "issue count < threshold for total of all priorities",
                        qualityGate().withThreshold(ALL_PRIORITIES_THRESHOLD).build(),
                        analysisRun().withTotalIssueCount(THRESHOLD_VALUE - 1).build(),
                        Result.SUCCESS),
                Arguments.of(
                        "issue count = threshold for total of all priorities, other counts irrelevant",
                        qualityGate().withThreshold(ALL_PRIORITIES_THRESHOLD).build(),
                        analysisRun()
                                .withTotalIssueCount(THRESHOLD_VALUE)
                                .withTotalIssueCount(Priority.HIGH, THRESHOLD_VALUE + 1)
                                .withNewIssueCount(THRESHOLD_VALUE + 1)
                                .withNewIssueCount(Priority.HIGH, THRESHOLD_VALUE + 1)
                                .build(),
                        Result.SUCCESS),
                Arguments.of(
                        "issue count > threshold for total of all priorities",
                        qualityGate().withThreshold(ALL_PRIORITIES_THRESHOLD).build(),
                        analysisRun().withTotalIssueCount(THRESHOLD_VALUE + 1).build(),
                        Result.UNSTABLE),

                Arguments.of(
                        "issue count < threshold for total of high priority",
                        qualityGate().withThreshold(HIGH_PRIORITY_THRESHOLD).build(),
                        analysisRun().withTotalIssueCount(Priority.HIGH, THRESHOLD_VALUE - 1).build(),
                        Result.SUCCESS),
                Arguments.of(
                        "issue count = threshold for total of high priority, other counts irrelevant",
                        qualityGate().withThreshold(HIGH_PRIORITY_THRESHOLD).build(),
                        analysisRun()
                                .withTotalIssueCount(THRESHOLD_VALUE + 1)
                                .withTotalIssueCount(Priority.HIGH, THRESHOLD_VALUE)
                                .withTotalIssueCount(Priority.LOW, THRESHOLD_VALUE + 1)
                                .withNewIssueCount(THRESHOLD_VALUE + 1)
                                .withNewIssueCount(Priority.HIGH, THRESHOLD_VALUE + 1)
                                .build(),
                        Result.SUCCESS),
                Arguments.of(
                        "issue count > threshold for total of high priority",
                        qualityGate().withThreshold(HIGH_PRIORITY_THRESHOLD).build(),
                        analysisRun().withTotalIssueCount(Priority.HIGH, THRESHOLD_VALUE + 1).build(),
                        Result.UNSTABLE),

                Arguments.of(
                        "issue count < threshold for new of all priorities",
                        qualityGate().withThreshold(NEW_ALL_PRIORITIES_THRESHOLD).build(),
                        analysisRun().withNewIssueCount(THRESHOLD_VALUE - 1).build(),
                        Result.SUCCESS),
                Arguments.of(
                        "issue count = threshold for new of all priorities, other counts irrelevant",
                        qualityGate().withThreshold(NEW_ALL_PRIORITIES_THRESHOLD).build(),
                        analysisRun()
                                .withTotalIssueCount(THRESHOLD_VALUE + 1)
                                .withTotalIssueCount(Priority.HIGH, THRESHOLD_VALUE + 1)
                                .withNewIssueCount(THRESHOLD_VALUE)
                                .withNewIssueCount(Priority.HIGH, THRESHOLD_VALUE + 1)
                                .build(),
                        Result.SUCCESS),
                Arguments.of(
                        "issue count > threshold for new of all priorities",
                        qualityGate().withThreshold(NEW_ALL_PRIORITIES_THRESHOLD).build(),
                        analysisRun().withNewIssueCount(THRESHOLD_VALUE + 1).build(),
                        Result.UNSTABLE),

                Arguments.of(
                        "issue count < threshold for new of high priority",
                        qualityGate().withThreshold(NEW_HIGH_PRIORITY_THRESHOLD).build(),
                        analysisRun().withNewIssueCount(Priority.HIGH, THRESHOLD_VALUE - 1).build(),
                        Result.SUCCESS),
                Arguments.of(
                        "issue count = threshold for new of high priority, other counts irrelevant",
                        qualityGate().withThreshold(NEW_HIGH_PRIORITY_THRESHOLD).build(),
                        analysisRun()
                                .withTotalIssueCount(THRESHOLD_VALUE + 1)
                                .withTotalIssueCount(Priority.HIGH, THRESHOLD_VALUE + 1)
                                .withNewIssueCount(THRESHOLD_VALUE + 1)
                                .withNewIssueCount(Priority.HIGH, THRESHOLD_VALUE)
                                .withNewIssueCount(Priority.LOW, THRESHOLD_VALUE + 1)
                                .build(),
                        Result.SUCCESS),
                Arguments.of(
                        "issue count > threshold for new of high priority",
                        qualityGate().withThreshold(NEW_HIGH_PRIORITY_THRESHOLD).build(),
                        analysisRun().withNewIssueCount(Priority.HIGH, THRESHOLD_VALUE + 1).build(),
                        Result.UNSTABLE),

                Arguments.of(
                        "combination of unstable and failure thresholds, issue count below both thresholds",
                        qualityGate()
                                .withThreshold(ALL_PRIORITIES_THRESHOLD)
                                .withThreshold(FAILURE_THRESHOLD)
                                .build(),
                        analysisRun()
                                .withTotalIssueCount(THRESHOLD_VALUE)
                                .build(),
                        Result.SUCCESS),
                Arguments.of(
                        "combination of unstable and failure thresholds, issue count above unstable threshold",
                        qualityGate()
                                .withThreshold(ALL_PRIORITIES_THRESHOLD)
                                .withThreshold(FAILURE_THRESHOLD)
                                .build(),
                        analysisRun()
                                .withTotalIssueCount(FAILURE_THRESHOLD_VALUE)
                                .build(),
                        Result.UNSTABLE),
                Arguments.of(
                        "combination of unstable and failure thresholds, issue count above failure threshold",
                        qualityGate()
                                .withThreshold(ALL_PRIORITIES_THRESHOLD)
                                .withThreshold(FAILURE_THRESHOLD)
                                .build(),
                        analysisRun()
                                .withTotalIssueCount(FAILURE_THRESHOLD_VALUE + 1)
                                .build(),
                        Result.FAILURE),

                Arguments.of(
                        "combination of all priorities and specific priority thresholds, issue count below both threshold",
                        qualityGate()
                                .withThreshold(ALL_PRIORITIES_THRESHOLD)
                                .withThreshold(LOW_PRIORITY_THRESHOLD)
                                .build(),
                        analysisRun()
                                .withTotalIssueCount(THRESHOLD_VALUE)
                                .withTotalIssueCount(Priority.LOW, THRESHOLD_VALUE)
                                .build(),
                        Result.SUCCESS),
                Arguments.of(
                        "combination of all priorities and specific priority thresholds, issue count above all priorities threshold",
                        qualityGate()
                                .withThreshold(ALL_PRIORITIES_THRESHOLD)
                                .withThreshold(LOW_PRIORITY_THRESHOLD)
                                .build(),
                        analysisRun()
                                .withTotalIssueCount(THRESHOLD_VALUE + 1)
                                .withTotalIssueCount(Priority.LOW, THRESHOLD_VALUE)
                                .build(),
                        Result.UNSTABLE),
                Arguments.of(
                        "combination of all priorities and specific priority thresholds, issue count above specific priority threshold",
                        qualityGate()
                                .withThreshold(ALL_PRIORITIES_THRESHOLD)
                                .withThreshold(LOW_PRIORITY_THRESHOLD)
                                .build(),
                        analysisRun()
                                .withTotalIssueCount(THRESHOLD_VALUE)
                                .withTotalIssueCount(Priority.LOW, THRESHOLD_VALUE + 1)
                                .build(),
                        Result.UNSTABLE),

                Arguments.of(
                        "combination of total and new thresholds, issue count below both thresholds",
                        qualityGate()
                                .withThreshold(ALL_PRIORITIES_THRESHOLD)
                                .withThreshold(NEW_LOW_PRIORITY_THRESHOLD)
                                .build(),
                        analysisRun()
                                .withTotalIssueCount(THRESHOLD_VALUE)
                                .withNewIssueCount(Priority.LOW, THRESHOLD_VALUE)
                                .build(),
                        Result.SUCCESS),
                Arguments.of(
                        "combination of total and new thresholds, issue count above total threshold",
                        qualityGate()
                                .withThreshold(ALL_PRIORITIES_THRESHOLD)
                                .withThreshold(NEW_LOW_PRIORITY_THRESHOLD)
                                .build(),
                        analysisRun()
                                .withTotalIssueCount(THRESHOLD_VALUE + 1)
                                .withNewIssueCount(Priority.LOW, THRESHOLD_VALUE)
                                .build(),
                        Result.UNSTABLE),
                Arguments.of(
                        "combination of total and new thresholds, issue count above new threshold",
                        qualityGate()
                                .withThreshold(ALL_PRIORITIES_THRESHOLD)
                                .withThreshold(NEW_LOW_PRIORITY_THRESHOLD)
                                .build(),
                        analysisRun()
                                .withTotalIssueCount(THRESHOLD_VALUE)
                                .withNewIssueCount(Priority.LOW, THRESHOLD_VALUE + 1)
                                .build(),
                        Result.UNSTABLE)
        );
    }
}
