package io.jenkins.plugins.analysis.core.graphs;

import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link HealthSeriesBuilder}.
 *
 * @author Marcel Binder
 */
class HealthSeriesBuilderTest {
    private static final int HEALTHY_THRESHOLD = 2;
    private static final int UNHEALTHY_THRESHOLD = 8;

    @ParameterizedTest(name = "{0}")
    @MethodSource("computeSeriesArguments")
    void computeSeries(final String testDescription, final boolean reportingEnabled, final int warningCount, final List<Integer> expectedSeries) {
        HealthDescriptor healthDescriptor = createHealthDescriptor(reportingEnabled);
        HealthSeriesBuilder builder = createBuilder(healthDescriptor);
        StaticAnalysisRun run = createRunWithWarnings(warningCount);

        List<Integer> series = builder.computeSeries(run);

        assertThat(series).isEqualTo(expectedSeries);
    }

    private static Stream<Object> computeSeriesArguments() {
        return Stream.of(
                Arguments.of(
                        "reporting disabled",
                        false, 5, singletonList(5)),
                Arguments.of(
                        "issueCount == 0",
                        true, 0, asList(0, 0, 0)),
                Arguments.of(
                        "issueCount < HEALTHY_THRESHOLD",
                        true, HEALTHY_THRESHOLD - 1, asList(HEALTHY_THRESHOLD - 1, 0, 0)),
                Arguments.of(
                        "issueCount == HEALTHY_THRESHOLD",
                        true, HEALTHY_THRESHOLD, asList(HEALTHY_THRESHOLD, 0, 0)),
                Arguments.of(
                        "issueCount > HEALTHY_THRESHOLD",
                        true, HEALTHY_THRESHOLD + 1, asList(HEALTHY_THRESHOLD, 1, 0)),
                Arguments.of(
                        "issueCount < UNHEALTHY_THRESHOLD",
                        true, UNHEALTHY_THRESHOLD - 1, asList(HEALTHY_THRESHOLD, UNHEALTHY_THRESHOLD - HEALTHY_THRESHOLD - 1, 0)),
                Arguments.of(
                        "issueCount == UNHEALTHY_THRESHOLD",
                        true, UNHEALTHY_THRESHOLD, asList(HEALTHY_THRESHOLD, UNHEALTHY_THRESHOLD - HEALTHY_THRESHOLD, 0)),
                Arguments.of(
                        "issueCount > UNHEALTHY_COUNT",
                        true, UNHEALTHY_THRESHOLD + 1, asList(HEALTHY_THRESHOLD, UNHEALTHY_THRESHOLD - HEALTHY_THRESHOLD, 1))
        );
    }

    private HealthDescriptor createHealthDescriptor(final boolean reportingEnabled) {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(reportingEnabled);
        when(healthDescriptor.getHealthy()).thenReturn(HEALTHY_THRESHOLD);
        when(healthDescriptor.getUnHealthy()).thenReturn(UNHEALTHY_THRESHOLD);
        return healthDescriptor;
    }

    private HealthSeriesBuilder createBuilder(HealthDescriptor healthDescriptor) {
        return new HealthSeriesBuilder(healthDescriptor);
    }

    private StaticAnalysisRun createRunWithWarnings(final int totalSize) {
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(totalSize);
        return run;
    }
}