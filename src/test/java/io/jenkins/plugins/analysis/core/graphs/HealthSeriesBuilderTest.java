package io.jenkins.plugins.analysis.core.graphs;

import hudson.util.DataSetBuilder;
import io.jenkins.plugins.analysis.core.history.ResultHistory;
import io.jenkins.plugins.analysis.core.quality.AnalysisBuild;
import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import io.jenkins.plugins.analysis.core.steps.AnalysisResult;
import org.jfree.data.category.CategoryDataset;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    private static final Iterable<? extends StaticAnalysisRun> RESULTS = Arrays.asList(
            STATIC_ANALYSIS_RUN,
            STATIC_ANALYSIS_RUN_2,
            STATIC_ANALYSIS_RUN_3
    );

    @Test
    void disabledHealthDescriptorWhereBuilderReturnsZeroOneOrTwo() {

        when(HEALTH_DESCRIPTOR.isEnabled()).thenReturn(false);
        when(STATIC_ANALYSIS_RUN.getTotalSize()).thenReturn(0, 1, 2);
        HealthSeriesBuilder builder = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);

        List<Integer> justZero = builder.computeSeries(STATIC_ANALYSIS_RUN);
        List<Integer> justOne = builder.computeSeries(STATIC_ANALYSIS_RUN);
        List<Integer> justTwo = builder.computeSeries(STATIC_ANALYSIS_RUN);

        assertThat(justZero.get(0)).isEqualTo(0);
        assertThat(justOne.get(0)).isEqualTo(1);
        assertThat(justTwo.get(0)).isEqualTo(2);
    }

    @Test
    void remainderBiggerZero() {
        when(HEALTH_DESCRIPTOR.isEnabled()).thenReturn(true);

        when(STATIC_ANALYSIS_RUN.getTotalSize()).thenReturn(6);
        when(HEALTH_DESCRIPTOR.getUnHealthy()).thenReturn(10);
        when(HEALTH_DESCRIPTOR.getHealthy()).thenReturn(5);

        HealthSeriesBuilder builder = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);
        List<Integer> containsRemainder = builder.computeSeries(STATIC_ANALYSIS_RUN);

        when(STATIC_ANALYSIS_RUN.getTotalSize()).thenReturn(11);

        List<Integer> containsRange = builder.computeSeries(STATIC_ANALYSIS_RUN);
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
    void createDataSetWithEmptyResultsEqualsEmptyDataSet() {
        CategoryDataset dataSet = new DataSetBuilder<String, LocalDateLabel>().build();
        SeriesBuilder builder = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);

        when(CONFIGURATION.useBuildDateAsDomain()).thenReturn(false, true);

        assertThat(builder.createDataSet(CONFIGURATION, EMPTY_RESULTS))
                .isEqualTo(dataSet);
        assertThat(builder.createDataSet(CONFIGURATION, EMPTY_RESULTS))
                .isEqualTo(dataSet);
    }

    @Test
    void createDataSetWithSampleResults() {
        ResultTime resultTime = mock(ResultTime.class);
        when(resultTime.areResultsTooOld(CONFIGURATION, STATIC_ANALYSIS_RUN)).thenReturn(false);

        when(CONFIGURATION.useBuildDateAsDomain()).thenReturn(false);
        when(CONFIGURATION.isBuildCountDefined()).thenReturn(false);

        when(STATIC_ANALYSIS_RUN.getBuild()).thenReturn(ANALYSIS_BUILD);
        when(STATIC_ANALYSIS_RUN_2.getBuild()).thenReturn(ANALYSIS_BUILD_2);
        when(STATIC_ANALYSIS_RUN_3.getBuild()).thenReturn(ANALYSIS_BUILD_3);

        when(STATIC_ANALYSIS_RUN.getTotalSize()).thenReturn(0);
        when(STATIC_ANALYSIS_RUN_2.getTotalSize()).thenReturn(1);
        when(STATIC_ANALYSIS_RUN_3.getTotalSize()).thenReturn(2);

        when(ANALYSIS_BUILD.compareTo(ANALYSIS_BUILD_2)).thenReturn(-1);
        when(ANALYSIS_BUILD.compareTo(ANALYSIS_BUILD_3)).thenReturn(-1);

        when(ANALYSIS_BUILD_2.compareTo(ANALYSIS_BUILD)).thenReturn(1);
        when(ANALYSIS_BUILD_2.compareTo(ANALYSIS_BUILD_3)).thenReturn(-1);

        when(ANALYSIS_BUILD_3.compareTo(ANALYSIS_BUILD)).thenReturn(1);
        when(ANALYSIS_BUILD_3.compareTo(ANALYSIS_BUILD_2)).thenReturn(1);

        when(HEALTH_DESCRIPTOR.isEnabled()).thenReturn(false);

        SeriesBuilder builder = new HealthSeriesBuilder(HEALTH_DESCRIPTOR, resultTime);
        CategoryDataset dataSet = builder.createDataSet(CONFIGURATION, RESULTS);

        int columnCount = dataSet.getColumnCount();
        int rowCount = dataSet.getRowCount();

        assertThat(columnCount).isEqualTo(3);
        assertThat(rowCount).isEqualTo(1);

        int zero = dataSet.getValue(0, 0).intValue();
        int one = dataSet.getValue(0, 1).intValue();
        int two = dataSet.getValue(0, 2).intValue();

        assertThat(zero).isEqualTo(0);
        assertThat(one).isEqualTo(1);
        assertThat(two).isEqualTo(2);
    }

    @Test
    void createEmptyDataSetWithSampleResultsWhereFirstBuildIsTooOld() {
        ResultTime resultTime = mock(ResultTime.class);
        when(resultTime.areResultsTooOld(CONFIGURATION, STATIC_ANALYSIS_RUN)).thenReturn(true, false);
        when(resultTime.areResultsTooOld(CONFIGURATION, STATIC_ANALYSIS_RUN_2)).thenReturn(false);
        when(resultTime.areResultsTooOld(CONFIGURATION, STATIC_ANALYSIS_RUN_3)).thenReturn(false);

        when(CONFIGURATION.useBuildDateAsDomain()).thenReturn(false);
        when(CONFIGURATION.isBuildCountDefined()).thenReturn(false);

        when(STATIC_ANALYSIS_RUN.getBuild()).thenReturn(ANALYSIS_BUILD);
        when(STATIC_ANALYSIS_RUN_2.getBuild()).thenReturn(ANALYSIS_BUILD_2);
        when(STATIC_ANALYSIS_RUN_3.getBuild()).thenReturn(ANALYSIS_BUILD_3);

        when(STATIC_ANALYSIS_RUN.getTotalSize()).thenReturn(0);
        when(STATIC_ANALYSIS_RUN_2.getTotalSize()).thenReturn(1);
        when(STATIC_ANALYSIS_RUN_3.getTotalSize()).thenReturn(2);

        SeriesBuilder builder = new HealthSeriesBuilder(HEALTH_DESCRIPTOR, resultTime);
        CategoryDataset dataSet = builder.createDataSet(CONFIGURATION, RESULTS);

        List<?> rowKeys = dataSet.getRowKeys();
        List<?> colKeys = dataSet.getColumnKeys();
        int rc = dataSet.getRowCount();
        int cc = dataSet.getColumnCount();

        assertThat(rc).isEqualTo(0);
        assertThat(cc).isEqualTo(0);

        assertThat(rowKeys.size()).isEqualTo(0);
        assertThat(colKeys.size()).isEqualTo(0);
    }

    @Test
    void createDataSetWithSampleResultsWhereConfigurationBuildCountDefinedIsTrueAndBuildCountIsTwo() {
        ResultTime resultTime = mock(ResultTime.class);
        when(resultTime.areResultsTooOld(CONFIGURATION, STATIC_ANALYSIS_RUN)).thenReturn(false);

        when(CONFIGURATION.useBuildDateAsDomain()).thenReturn(false);
        when(CONFIGURATION.isBuildCountDefined()).thenReturn(true);
        when(CONFIGURATION.getBuildCount()).thenReturn(2);

        when(STATIC_ANALYSIS_RUN.getBuild()).thenReturn(ANALYSIS_BUILD);
        when(STATIC_ANALYSIS_RUN_2.getBuild()).thenReturn(ANALYSIS_BUILD_2);
        when(STATIC_ANALYSIS_RUN_3.getBuild()).thenReturn(ANALYSIS_BUILD_3);

        when(STATIC_ANALYSIS_RUN.getTotalSize()).thenReturn(0);
        when(STATIC_ANALYSIS_RUN_2.getTotalSize()).thenReturn(1);
        when(STATIC_ANALYSIS_RUN_3.getTotalSize()).thenReturn(2);

        when(ANALYSIS_BUILD.compareTo(ANALYSIS_BUILD_2)).thenReturn(-1);
        when(ANALYSIS_BUILD.compareTo(ANALYSIS_BUILD_3)).thenReturn(-1);

        when(ANALYSIS_BUILD_2.compareTo(ANALYSIS_BUILD)).thenReturn(1);
        when(ANALYSIS_BUILD_2.compareTo(ANALYSIS_BUILD_3)).thenReturn(-1);

        when(ANALYSIS_BUILD_3.compareTo(ANALYSIS_BUILD)).thenReturn(1);
        when(ANALYSIS_BUILD_3.compareTo(ANALYSIS_BUILD_2)).thenReturn(1);

        when(HEALTH_DESCRIPTOR.isEnabled()).thenReturn(false);

        SeriesBuilder builder = new HealthSeriesBuilder(HEALTH_DESCRIPTOR, resultTime);
        CategoryDataset dataSet = builder.createDataSet(CONFIGURATION, RESULTS);

        List<?> rowKeys = dataSet.getRowKeys();
        List<?> colKeys = dataSet.getColumnKeys();

        assertThat(rowKeys.size()).isEqualTo(1);
        assertThat(colKeys.size()).isEqualTo(2);

        int zero = dataSet.getValue(0, 0).intValue();
        int one = dataSet.getValue(0, 1).intValue();

        assertThat(zero).isEqualTo(0);
        assertThat(one).isEqualTo(1);
        assertThatThrownBy(() -> dataSet.getValue(0, 2)).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void createDataSetWithSampleResultsWhereConfigurationUseBuildDateAsDomainIsTrue() {
        SeriesBuilder builder = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);

        when(CONFIGURATION.useBuildDateAsDomain()).thenReturn(true);

        when(STATIC_ANALYSIS_RUN.getBuild()).thenReturn(ANALYSIS_BUILD);
        when(STATIC_ANALYSIS_RUN_2.getBuild()).thenReturn(ANALYSIS_BUILD_2);
        when(STATIC_ANALYSIS_RUN_3.getBuild()).thenReturn(ANALYSIS_BUILD_3);

        when(STATIC_ANALYSIS_RUN.getTotalSize()).thenReturn(0);
        when(STATIC_ANALYSIS_RUN_2.getTotalSize()).thenReturn(1);
        when(STATIC_ANALYSIS_RUN_3.getTotalSize()).thenReturn(2);

        when(ANALYSIS_BUILD.compareTo(ANALYSIS_BUILD_2)).thenReturn(-1);
        when(ANALYSIS_BUILD.compareTo(ANALYSIS_BUILD_3)).thenReturn(-1);

        when(ANALYSIS_BUILD_2.compareTo(ANALYSIS_BUILD)).thenReturn(1);
        when(ANALYSIS_BUILD_2.compareTo(ANALYSIS_BUILD_3)).thenReturn(-1);

        when(ANALYSIS_BUILD_3.compareTo(ANALYSIS_BUILD)).thenReturn(1);
        when(ANALYSIS_BUILD_3.compareTo(ANALYSIS_BUILD_2)).thenReturn(1);

        CategoryDataset dataSet = builder.createDataSet(CONFIGURATION, RESULTS);

        List<?> rowKeys = dataSet.getRowKeys();
        List<?> colKeys = dataSet.getColumnKeys();

        assertThat(rowKeys.size()).isEqualTo(3);
        assertThat(colKeys.size()).isEqualTo(1);

        int one = dataSet.getValue(0, 0).intValue();
        int zero1 = dataSet.getValue(1, 0).intValue();
        int zero2 = dataSet.getValue(2, 0).intValue();

        assertThat(one).isEqualTo(1);
        assertThat(zero1).isEqualTo(0);
        assertThat(zero2).isEqualTo(0);
    }

    @Test
    void createAggregationWithThreeResultActions() {
        GraphConfiguration configuration = mock(GraphConfiguration.class);

        ResultHistory resultHistory = mock(ResultHistory.class);
        ResultHistory resultHistory2 = mock(ResultHistory.class);
        ResultHistory resultHistory3 = mock(ResultHistory.class);

        AnalysisResult analysisResult = mock(AnalysisResult.class);
        AnalysisResult analysisResult2 = mock(AnalysisResult.class);
        AnalysisResult analysisResult3 = mock(AnalysisResult.class);

        when(resultHistory.getBaseline()).thenReturn(Optional.of(analysisResult));
        when(resultHistory2.getBaseline()).thenReturn(Optional.of(analysisResult2));
        when(resultHistory3.getBaseline()).thenReturn(Optional.of(analysisResult3));

        when(resultHistory.getPreviousResult()).thenReturn(Optional.of(analysisResult));
        when(resultHistory2.getPreviousResult()).thenReturn(Optional.of(analysisResult2));
        when(resultHistory3.getPreviousResult()).thenReturn(Optional.of(analysisResult3));

        when(resultHistory.iterator()).thenReturn(Collections.singletonList(analysisResult).iterator());
        when(resultHistory2.iterator()).thenReturn(Collections.singletonList(analysisResult2).iterator());
        when(resultHistory3.iterator()).thenReturn(Collections.singletonList(analysisResult3).iterator());

        when(analysisResult.getBuild()).thenReturn(ANALYSIS_BUILD);
        when(analysisResult2.getBuild()).thenReturn(ANALYSIS_BUILD_2);
        when(analysisResult3.getBuild()).thenReturn(ANALYSIS_BUILD_3);

        when(ANALYSIS_BUILD.getTimeInMillis()).thenReturn(1L);
        when(ANALYSIS_BUILD_2.getTimeInMillis()).thenReturn(1_000L);
        when(ANALYSIS_BUILD_3.getTimeInMillis()).thenReturn(1_000_000L);

        Collection<ResultHistory> resultActions = Arrays.asList(resultHistory, resultHistory2, resultHistory2);

        ResultTime resultTime = mock(ResultTime.class);
        when(resultTime.areResultsTooOld(configuration, analysisResult)).thenReturn(false);
        when(resultTime.areResultsTooOld(configuration, analysisResult2)).thenReturn(false);
        when(resultTime.areResultsTooOld(configuration, analysisResult3)).thenReturn(false);

        SeriesBuilder seriesBuilder = new HealthSeriesBuilder(HEALTH_DESCRIPTOR, resultTime);
        CategoryDataset dataset = seriesBuilder.createAggregation(configuration, resultActions);

        List<?> rowKeys = dataset.getRowKeys();
        List<?> colKeys = dataset.getRowKeys();

        assertThat(rowKeys.size()).isEqualTo(1);
        assertThat(colKeys.size()).isEqualTo(1);

        int zero = dataset.getValue(0, 0).intValue();

        assertThat(zero).isEqualTo(0);
    }
}















