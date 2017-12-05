package io.jenkins.plugins.analysis.core.graphs;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Stream;

import org.jfree.data.category.CategoryDataset;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static io.jenkins.plugins.analysis.core.graphs.assertj.Assertions.*;
import io.jenkins.plugins.analysis.core.quality.AnalysisBuild;
import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import static java.util.Arrays.*;
import static java.util.Collections.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link HealthSeriesBuilder}.
 *
 * @author Marcel Binder
 */
class HealthSeriesBuilderTest {
    private static final LocalDate TODAY = LocalDate.now();
    private static final LocalDate YESTERDAY = TODAY.minusDays(1);
    private static final LocalDate TWO_DAYS_AGO = TODAY.minusDays(2);
    private static final ResultTime RESULT_TIME_TODAY = new ResultTime(TODAY);
    private static final int HEALTHY_THRESHOLD = 2;
    private static final int UNHEALTHY_THRESHOLD = 8;
    private static final List<Integer> SERIES_BELOW_HEALTHY_THRESHOLD = asList(HEALTHY_THRESHOLD - 1, 0, 0);
    private static final List<Integer> SERIES_AT_HEALTHY_THRESHOLD = asList(HEALTHY_THRESHOLD, 0, 0);
    private static final List<Integer> SERIES_ABOVE_HEALTHY_THRESHOLD = asList(HEALTHY_THRESHOLD, HEALTHY_THRESHOLD + 1 - HEALTHY_THRESHOLD, 0);
    private static final List<Integer> SERIES_BELOW_UNHEALTHY_THRESHOLD = asList(HEALTHY_THRESHOLD, UNHEALTHY_THRESHOLD - 1 - HEALTHY_THRESHOLD, 0);
    private static final List<Integer> SERIES_AT_UNHEALTHY_THRESHOLD = asList(HEALTHY_THRESHOLD, UNHEALTHY_THRESHOLD - HEALTHY_THRESHOLD, 0);
    private static final List<Integer> SERIES_ABOVE_UNHEALTHY_THRESHOLD = asList(HEALTHY_THRESHOLD, UNHEALTHY_THRESHOLD - HEALTHY_THRESHOLD, UNHEALTHY_THRESHOLD + 1 - UNHEALTHY_THRESHOLD);

    @ParameterizedTest(name = "{0}")
    @MethodSource("computeSeriesArguments")
    void computeSeries(final String testDescription, final HealthDescriptor healthDescriptor, final StaticAnalysisRun result, final List<Integer> expectedSeries) {
        HealthSeriesBuilder builder = createBuilder(healthDescriptor);

        List<Integer> series = builder.computeSeries(result);

        assertThat(series).isEqualTo(expectedSeries);
    }

    private static Stream<Object> computeSeriesArguments() {
        return Stream.of(
                Arguments.of(
                        "reporting disabled",
                        reportingDisabled(),
                        result(UNHEALTHY_THRESHOLD + 1),
                        singletonList(UNHEALTHY_THRESHOLD + 1)),
                Arguments.of(
                        "issueCount = 0",
                        reportingEnabled(),
                        result(0),
                        asList(0, 0, 0)),
                Arguments.of(
                        "issueCount < HEALTHY_THRESHOLD",
                        reportingEnabled(),
                        result(HEALTHY_THRESHOLD - 1),
                        SERIES_BELOW_HEALTHY_THRESHOLD),
                Arguments.of(
                        "issueCount = HEALTHY_THRESHOLD",
                        reportingEnabled(),
                        result(HEALTHY_THRESHOLD),
                        SERIES_AT_HEALTHY_THRESHOLD),
                Arguments.of(
                        "issueCount > HEALTHY_THRESHOLD",
                        reportingEnabled(),
                        result(HEALTHY_THRESHOLD + 1),
                        SERIES_ABOVE_HEALTHY_THRESHOLD),
                Arguments.of(
                        "issueCount < UNHEALTHY_THRESHOLD",
                        reportingEnabled(),
                        result(UNHEALTHY_THRESHOLD - 1),
                        SERIES_BELOW_UNHEALTHY_THRESHOLD),
                Arguments.of(
                        "issueCount = UNHEALTHY_THRESHOLD",
                        reportingEnabled(),
                        result(UNHEALTHY_THRESHOLD),
                        SERIES_AT_UNHEALTHY_THRESHOLD),
                Arguments.of(
                        "issueCount > UNHEALTHY_COUNT",
                        reportingEnabled(),
                        result(UNHEALTHY_THRESHOLD + 1),
                        SERIES_ABOVE_UNHEALTHY_THRESHOLD)
        );
    }

    private static HealthDescriptor reportingDisabled() {
        return mock(HealthDescriptor.class);
    }

    private static HealthDescriptor reportingEnabled() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(HEALTHY_THRESHOLD);
        when(healthDescriptor.getUnHealthy()).thenReturn(UNHEALTHY_THRESHOLD);
        return healthDescriptor;
    }

    private HealthSeriesBuilder createBuilder(HealthDescriptor healthDescriptor) {
        return new HealthSeriesBuilder(healthDescriptor, RESULT_TIME_TODAY);
    }

    private static StaticAnalysisRun result(final int totalSize) {
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(totalSize);
        return run;
    }

    private static StaticAnalysisRun result(final AnalysisBuild build, final int totalSize) {
        StaticAnalysisRun result = result(totalSize);
        when(result.getBuild()).thenReturn(build);
        return result;
    }

    private static AnalysisBuild build(final int buildNumber) {
        AnalysisBuild build = mock(AnalysisBuild.class);
        when(build.getNumber()).thenReturn(buildNumber);
        return build;
    }

    private static AnalysisBuild build(final int buildNumber, final LocalDate buildTime) {
        AnalysisBuild build = build(buildNumber);
        when(build.getTimeInMillis()).thenReturn(buildTime.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli());
        return build;
    }

    private static AnalysisBuild build(final LocalDate buildTime) {
        AnalysisBuild build = mock(AnalysisBuild.class);
        when(build.getTimeInMillis()).thenReturn(buildTime.atStartOfDay().toInstant(ZoneOffset.UTC).toEpochMilli());
        return build;
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("createDataSetArguments")
    void createDataSet(final String testDescription, final GraphConfiguration configuration, List<StaticAnalysisRun> results, List<List<Integer>> expectedSeries) {
        SeriesBuilder builder = createBuilder(reportingEnabled());

        CategoryDataset dataSet = builder.createDataSet(configuration, results);

        assertThat(dataSet).hasValues(expectedSeries);
    }

    private static Stream<Object> createDataSetArguments() {
        return Stream.of(
                Arguments.of(
                        "use build number, no results",
                        buildNumberDomain(),
                        emptyList(),
                        emptyList()),
                Arguments.of(
                        "use build number, single results",
                        buildNumberDomain(),
                        singletonList(result(build(1), HEALTHY_THRESHOLD)),
                        singletonList(SERIES_AT_HEALTHY_THRESHOLD)),
                Arguments.of(
                        "use build number, single results, day count, build not too old",
                        dayCountSingleDay(),
                        singletonList(result(build(1, YESTERDAY), HEALTHY_THRESHOLD)),
                        singletonList(SERIES_AT_HEALTHY_THRESHOLD)),
                Arguments.of(
                        "use build number, single results, day count, build too old",
                        dayCountSingleDay(),
                        singletonList(
                                result(build(1, TWO_DAYS_AGO), HEALTHY_THRESHOLD)
                        ),
                        emptyList()),
                Arguments.of(
                        "use build number, single results, build count = 0, not filtered",
                        buildCount(0),
                        singletonList(result(build(1), HEALTHY_THRESHOLD)),
                        singletonList(SERIES_AT_HEALTHY_THRESHOLD)),
                Arguments.of(
                        "use build number, single results, build count = 1, not filtered",
                        buildCount(1),
                        singletonList(result(build(1), HEALTHY_THRESHOLD)),
                        singletonList(SERIES_AT_HEALTHY_THRESHOLD)),
                Arguments.of(
                        "use build number, two results",
                        buildNumberDomain(),
                        asList(
                                result(build(2), HEALTHY_THRESHOLD - 1),
                                result(build(1), HEALTHY_THRESHOLD)
                        ),
                        asList(
                                SERIES_AT_HEALTHY_THRESHOLD,
                                SERIES_BELOW_HEALTHY_THRESHOLD
                        )),
                Arguments.of(
                        "use build number, two results on different days, day count = 1",
                        dayCountSingleDay(),
                        asList(
                                result(build(2, YESTERDAY), HEALTHY_THRESHOLD - 1),
                                result(build(1, TWO_DAYS_AGO), HEALTHY_THRESHOLD)
                        ),
                        singletonList(SERIES_BELOW_HEALTHY_THRESHOLD)),
                Arguments.of(
                        "use build number, three results, build count = 2",
                        buildCount(2),
                        asList(
                                result(build(3), HEALTHY_THRESHOLD - 1),
                                result(build(2), HEALTHY_THRESHOLD),
                                result(build(1), HEALTHY_THRESHOLD + 1)
                        ),
                        asList(
                                SERIES_AT_HEALTHY_THRESHOLD,
                                SERIES_BELOW_HEALTHY_THRESHOLD
                        )),
                Arguments.of(
                        "use build date, single day, single result",
                        buildDateDomain(),
                        singletonList(
                                result(build(TODAY), HEALTHY_THRESHOLD)
                        ),
                        singletonList(SERIES_AT_HEALTHY_THRESHOLD)),
                Arguments.of(
                        "use build date, single day, multiple results",
                        buildDateDomain(),
                        asList(
                                result(build(TODAY), HEALTHY_THRESHOLD - 1),
                                result(build(TODAY), HEALTHY_THRESHOLD)
                        ),
                        singletonList(
                                asList(3 / 2, 0, 0)
                        )),
                Arguments.of(
                        "use build date, multiple days, single result per day",
                        buildDateDomain(),
                        asList(
                                result(build(TODAY), HEALTHY_THRESHOLD - 1),
                                result(build(YESTERDAY), HEALTHY_THRESHOLD),
                                result(build(TWO_DAYS_AGO), HEALTHY_THRESHOLD + 1)
                        ),
                        asList(
                                SERIES_ABOVE_HEALTHY_THRESHOLD,
                                SERIES_AT_HEALTHY_THRESHOLD,
                                SERIES_BELOW_HEALTHY_THRESHOLD
                        )),
                Arguments.of(
                        "use build date, multiple days, multiple results per day",
                        buildDateDomain(),
                        asList(
                                result(build(TODAY), 1),
                                result(build(TODAY), 2),
                                result(build(YESTERDAY), 4),
                                result(build(TWO_DAYS_AGO), 5),
                                result(build(TWO_DAYS_AGO), 6),
                                result(build(TWO_DAYS_AGO), 9)
                        ),
                        asList(
                                asList(2, 13 / 3, 1 / 3),
                                asList(2, 2, 0),
                                asList(3 / 2, 0, 0)
                        ))
        );
    }

    private static GraphConfiguration buildNumberDomain() {
        return mock(GraphConfiguration.class);
    }

    private static GraphConfiguration buildDateDomain() {
        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(true);
        return configuration;
    }

    private static GraphConfiguration buildCount(final int buildCount) {
        GraphConfiguration configuration = buildNumberDomain();
        when(configuration.isBuildCountDefined()).thenReturn(true);
        when(configuration.getBuildCount()).thenReturn(buildCount);
        return configuration;
    }

    private static GraphConfiguration dayCountSingleDay() {
        GraphConfiguration configuration = buildNumberDomain();
        when(configuration.isDayCountDefined()).thenReturn(true);
        when(configuration.getDayCount()).thenReturn(1);
        return configuration;
    }
}