package io.jenkins.plugins.analysis.core.graphs;

import com.google.common.collect.Lists;
import hudson.util.DataSetBuilder;
import io.jenkins.plugins.analysis.core.quality.AnalysisBuild;
import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import org.jfree.data.category.CategoryDataset;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;

import org.joda.time.LocalDate;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the class {@link HealthSeriesBuilder}.
 *
 * @author Aykut Yilmaz
 */
class HealthSeriesBuilderTest {

    private static final int A_DAY_IN_MSEC = 24 * 3600 * 1000;

    private static final HealthDescriptor HEALTH_DESCRIPTOR = mock(HealthDescriptor.class);

    private static final StaticAnalysisRun STATIC_ANALYSIS_RUN = mock(StaticAnalysisRun.class);
    private static final StaticAnalysisRun STATIC_ANALYSIS_RUN_2 = mock(StaticAnalysisRun.class);
    private static final StaticAnalysisRun STATIC_ANALYSIS_RUN_3 = mock(StaticAnalysisRun.class);

    private static final AnalysisBuild ANALYSIS_BUILD = mock(AnalysisBuild.class);
    private static final AnalysisBuild ANALYSIS_BUILD_2 = mock(AnalysisBuild.class);
    private static final AnalysisBuild ANALYSIS_BUILD_3 = mock(AnalysisBuild.class);


    private static final GraphConfiguration CONFIGURATION = mock(GraphConfiguration.class);
    private static final Iterable<? extends StaticAnalysisRun> EMPTY_RESULTS = Collections.emptyList();

    private static final SeriesBuilder SERIES_BUILDER = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);

    private static final Iterable<? extends StaticAnalysisRun> RESULTS = Arrays.asList(
            STATIC_ANALYSIS_RUN,
            STATIC_ANALYSIS_RUN_2,
            STATIC_ANALYSIS_RUN_3
    );


    @Test
    void disabledHealthDescriptorWhereBuilderReturnsZeroOneOrTwo() {
        StaticAnalysisRun staticAnalysisRun = mock(StaticAnalysisRun.class);

        when(HEALTH_DESCRIPTOR.isEnabled()).thenReturn(false);
        when(staticAnalysisRun.getTotalSize()).thenReturn(0, 1, 2);
        HealthSeriesBuilder builder = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);

        List<Integer> justZero = builder.computeSeries(staticAnalysisRun);
        List<Integer> justOne = builder.computeSeries(staticAnalysisRun);
        List<Integer> justTwo = builder.computeSeries(staticAnalysisRun);

        assertThat(justZero.get(0)).isEqualTo(0);
        assertThat(justOne.get(0)).isEqualTo(1);
        assertThat(justTwo.get(0)).isEqualTo(2);
    }

    @Test
    void remainderBiggerZero() {
        StaticAnalysisRun staticAnalysisRun = mock(StaticAnalysisRun.class);

        when(HEALTH_DESCRIPTOR.isEnabled()).thenReturn(true);

        when(staticAnalysisRun.getTotalSize()).thenReturn(6);
        when(HEALTH_DESCRIPTOR.getUnHealthy()).thenReturn(10);
        when(HEALTH_DESCRIPTOR.getHealthy()).thenReturn(5);

        HealthSeriesBuilder builder = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);
        List<Integer> containsRemainder = builder.computeSeries(staticAnalysisRun);

        when(staticAnalysisRun.getTotalSize()).thenReturn(11);

        List<Integer> containsRange = builder.computeSeries(staticAnalysisRun);
        assertThat(containsRemainder.get(1)).isEqualTo(1);
        assertThat(containsRange.get(1)).isEqualTo(5);
    }

    @Test
    void remainderNotBiggerZero() {
        when(HEALTH_DESCRIPTOR.isEnabled()).thenReturn(true);

        when(STATIC_ANALYSIS_RUN.getTotalSize()).thenReturn(0);
        when(HEALTH_DESCRIPTOR.getHealthy()).thenReturn(1);

        HealthSeriesBuilder builder = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);
        List<Integer> ints = builder.computeSeries(STATIC_ANALYSIS_RUN);

        assertThat(ints).containsExactly(0, 0, 0);
    }

    ///////////////////////////////////////////////////////////////////////////////
    // SeriesBuilder tests
    ///////////////////////////////////////////////////////////////////////////////

    @Test
    void createEmptyDataSetWithEmptyResults() {
        CategoryDataset emptyDataset = new DataSetBuilder<String, LocalDateLabel>().build();

        when(CONFIGURATION.useBuildDateAsDomain()).thenReturn(false, true);

        assertThat(SERIES_BUILDER.createDataSet(CONFIGURATION, EMPTY_RESULTS))
                .isEqualTo(emptyDataset);
        assertThat(SERIES_BUILDER.createDataSet(CONFIGURATION, EMPTY_RESULTS))
                .isEqualTo(emptyDataset);
    }


    @Ignore
    void createDataSetWithSampleResults() {

        when(CONFIGURATION.useBuildDateAsDomain()).thenReturn(false, true);

        when(STATIC_ANALYSIS_RUN.getBuild()).thenReturn(ANALYSIS_BUILD);
        when(STATIC_ANALYSIS_RUN_2.getBuild()).thenReturn(ANALYSIS_BUILD_2);
        when(STATIC_ANALYSIS_RUN_3.getBuild()).thenReturn(ANALYSIS_BUILD_3);

        final long zeroMillis = 0L;
        final long thousandMillis = 1_000L;
        final long millionMillis = 1_000_000L;

        when(ANALYSIS_BUILD.getTimeInMillis()).thenReturn(zeroMillis);
        when(ANALYSIS_BUILD_2.getTimeInMillis()).thenReturn(thousandMillis);
        when(ANALYSIS_BUILD_3.getTimeInMillis()).thenReturn(millionMillis);

        LocalDate localDate = new LocalDate(zeroMillis);
        LocalDate localDate1 = new LocalDate(thousandMillis);
        LocalDate localDate2 = new LocalDate(millionMillis);


        CategoryDataset dataSet = SERIES_BUILDER.createDataSet(CONFIGURATION, RESULTS);
        //assertThat(dataSet)


    }

    @Test
    void checkComputeDayDelta() {
        Calendar calendar = mock(Calendar.class);

        when(calendar.getTimeInMillis()).thenReturn(0L, 0L, (long) A_DAY_IN_MSEC);
        when(STATIC_ANALYSIS_RUN.getBuild()).thenReturn(ANALYSIS_BUILD);
        when(ANALYSIS_BUILD.getTimeInMillis()).thenReturn(0L, (long) A_DAY_IN_MSEC);

        assertThat(SeriesBuilder.computeDayDelta(calendar, 0L))
                .isEqualTo(0L);

        assertThat(SeriesBuilder.computeDayDelta(calendar, A_DAY_IN_MSEC))
                .isEqualTo(1L);

        assertThat(SeriesBuilder.computeDayDelta(calendar, STATIC_ANALYSIS_RUN))
                .isEqualTo(1L);

        assertThat(SeriesBuilder.computeDayDelta(calendar, STATIC_ANALYSIS_RUN))
                .isEqualTo(0L);
    }

    @Test
    void buildIsTooOldAndNotTooOld() {
        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.isDayCountDefined()).thenReturn(true, false);
        when(configuration.getDayCount()).thenReturn(0, 1, Integer.MAX_VALUE);
        when(STATIC_ANALYSIS_RUN.getBuild()).thenReturn(ANALYSIS_BUILD);
        when(ANALYSIS_BUILD.getTimeInMillis()).thenReturn((long) A_DAY_IN_MSEC, Long.MIN_VALUE);

        assertThat(SeriesBuilder.isBuildTooOld(configuration, STATIC_ANALYSIS_RUN))
                .isTrue();

        assertThat(SeriesBuilder.isBuildTooOld(configuration, STATIC_ANALYSIS_RUN))
                .isFalse();

        assertThat(SeriesBuilder.isBuildTooOld(configuration, STATIC_ANALYSIS_RUN))
                .isFalse();
    }
}
