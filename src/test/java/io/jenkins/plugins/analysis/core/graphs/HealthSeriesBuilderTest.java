package io.jenkins.plugins.analysis.core.graphs;

import java.util.List;

import org.jfree.data.category.CategoryDataset;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

import edu.hm.hafner.analysis.Priority;
import io.jenkins.plugins.analysis.core.quality.AnalysisBuild;
import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the class {@link HealthSeriesBuilder}.
 */
class HealthSeriesBuilderTest {

    private StaticAnalysisRun createSimpleBuildResult(final int totalSize) {
        StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);
        when(buildResult.getTotalSize()).thenReturn(totalSize);
        return buildResult;
    }

    private StaticAnalysisRun createBuildResult(final int buildNumber, final int totalSize, final long buildDateInMillis) {
        StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);

        when(buildResult.getTotalSize()).thenReturn(totalSize);
        AnalysisBuild build;
        build = createRun(buildNumber, buildDateInMillis);
        when(buildResult.getBuild()).thenReturn(build);

        return buildResult;
    }

    private AnalysisBuild createRun(final int number, final long buildDateInMillis) {
        AnalysisBuild run = mock(AnalysisBuild.class);
        when(run.getNumber()).thenReturn(number);
        when(run.getDisplayName()).thenReturn("#" + number);
        when(run.getTimeInMillis()).thenReturn(buildDateInMillis);
        return run;
    }

    private GraphConfiguration createGraphConfiguration() {
        GraphConfiguration configuration = mock(GraphConfiguration.class);
        return configuration;
    }

    /**
     * Verifies correct list output if no health report is required (more healthy than unhealthy). This constellation is
     * not possible in the productive environment.
     */
    @Test
    void nonEnabledHealthReport() {
        HealthDescriptor descriptor = new HealthDescriptor("2", "1", Priority.NORMAL);
        HealthSeriesBuilder builder = new HealthSeriesBuilder(descriptor);
        StaticAnalysisRun run = createSimpleBuildResult(3);
        List<Integer> computeResult = builder.computeSeries(run);

        assertThat(computeResult).hasSize(1);
        assertThat(computeResult).startsWith(3);
    }

    /**
     * Verifies correct list output of a health report with less warnings than healthy-border. This build-graph would be
     * all green.
     */
    @Test
    void fullyHealthyReport() {
        StaticAnalysisRun run = createSimpleBuildResult(4);
        HealthDescriptor descriptor = new HealthDescriptor("4", "5", Priority.NORMAL);
        HealthSeriesBuilder builder = new HealthSeriesBuilder(descriptor);

        List<Integer> computeResult = builder.computeSeries(run);
        assertThat(computeResult).hasSize(3);
        assertThat(computeResult).startsWith(4, 0, 0);
    }

    /**
     * Verifies correct list output of a health report with more warnings than healthy-border but less than
     * unhealthy-border. This build-graph would  be green and yellow.
     */
    @Test
    void mediocreHealthReport() {
        StaticAnalysisRun run = createSimpleBuildResult(5);
        HealthDescriptor descriptor = new HealthDescriptor("3", "5", Priority.NORMAL);
        HealthSeriesBuilder builder = new HealthSeriesBuilder(descriptor);

        List<Integer> computeResult = builder.computeSeries(run);
        assertThat(computeResult).hasSize(3);
        assertThat(computeResult).startsWith(3, 2, 0);
    }

    /**
     * Verifies that a per-build-report without any builds is empty.
     */
    @Test
    void perBuildReportGraphWithoutAnyBuildsTest() {
        HealthDescriptor descriptor = new HealthDescriptor("3", "5", Priority.NORMAL);
        HealthSeriesBuilder builder = new HealthSeriesBuilder(descriptor);
        List<StaticAnalysisRun> results = Lists.newArrayList();
        GraphConfiguration configuration = createGraphConfiguration();

        CategoryDataset dataSet = builder.createDataSet(configuration, results);

        assertThat(dataSet.getColumnCount()).isEqualTo(0);
        assertThat(dataSet.getRowCount()).isEqualTo(0);
    }

    /**
     * Verifies that a per-date-report without any builds is empty.
     */
    @Test
    void perDateReportGraphWithoutAnyBuildsTest() {
        HealthDescriptor descriptor = new HealthDescriptor("3", "5", Priority.NORMAL);
        HealthSeriesBuilder builder = new HealthSeriesBuilder(descriptor);
        List<StaticAnalysisRun> results = Lists.newArrayList();
        GraphConfiguration configuration = createGraphConfiguration();
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        CategoryDataset dataSet = builder.createDataSet(configuration, results);

        assertThat(dataSet.getColumnCount()).isEqualTo(0);
        assertThat(dataSet.getRowCount()).isEqualTo(0);
    }

    /**
     * Verifies the correct behaviour of a normal per-build-report-graph.
     */
    @Test
    void perBuildReportGraphTest() {
        HealthDescriptor descriptor = new HealthDescriptor("3", "5", Priority.NORMAL);
        HealthSeriesBuilder builder = new HealthSeriesBuilder(descriptor);
        StaticAnalysisRun resultBuildOne = createBuildResult(42, 6, System.currentTimeMillis());
        StaticAnalysisRun resultBuildTwo = createBuildResult(41, 8, System.currentTimeMillis());
        List<StaticAnalysisRun> results = Lists.newArrayList(resultBuildOne, resultBuildTwo);
        GraphConfiguration configuration = createGraphConfiguration();

        CategoryDataset dataSet = builder.createDataSet(configuration, results);

        assertThat(dataSet.getColumnCount()).isEqualTo(2);
        assertThat(dataSet.getRowCount()).isEqualTo(3);
        assertThat(dataSet.getColumnKey(1).toString()).isEqualTo("#42");
        assertThat(dataSet.getColumnKey(0).toString()).isEqualTo("#41");

        assertThat(dataSet.getValue(0, 0)).isEqualTo(3);
        assertThat(dataSet.getValue(1, 0)).isEqualTo(2);
        assertThat(dataSet.getValue(2, 0)).isEqualTo(3);

        assertThat(dataSet.getValue(0, 1)).isEqualTo(3);
        assertThat(dataSet.getValue(1, 1)).isEqualTo(2);
        assertThat(dataSet.getValue(2, 1)).isEqualTo(1);
    }

    /**
     * Verifies whether builds which happened way too far in the past (one week) are ignored in a build-report-graph.
     */
    @Test
    void reportGraphWithAncientBuild() {
        HealthDescriptor descriptor = new HealthDescriptor("3", "5", Priority.NORMAL);
        HealthSeriesBuilder builder = new HealthSeriesBuilder(descriptor);
        StaticAnalysisRun buildOfRecentTimes = createBuildResult(42, 8, System.currentTimeMillis());
        StaticAnalysisRun buildOfAncientTimes = createBuildResult(41, 8, 1L);
        List<StaticAnalysisRun> results = Lists.newArrayList(buildOfRecentTimes,buildOfAncientTimes);

        GraphConfiguration configuration = createGraphConfiguration();
        when(configuration.isDayCountDefined()).thenReturn(true);
        when(configuration.getDayCount()).thenReturn(1);

        CategoryDataset dataSet = builder.createDataSet(configuration, results);
        assertThat(dataSet.getRowCount()).isEqualTo(3);
        assertThat(dataSet.getColumnCount()).isEqualTo(1);
        assertThat(dataSet.getColumnKey(0).toString()).isEqualTo("#42");

    }

    /**
     * If you only want a specific amount of builds represented in your report-graph. Verifies whether unnecessary
     * builds are ignored.
     */
    @Test
    void reportGraphWithABuildTooMuch() {
        HealthDescriptor descriptor = new HealthDescriptor("3", "5", Priority.NORMAL);
        HealthSeriesBuilder builder = new HealthSeriesBuilder(descriptor);
        StaticAnalysisRun resultBuildOne = createBuildResult(42, 8, System.currentTimeMillis());
        StaticAnalysisRun resultBuildTwo = createBuildResult(41, 6, System.currentTimeMillis());
        StaticAnalysisRun aBuildTooMuch = createBuildResult(40, 10, System.currentTimeMillis());
        List<StaticAnalysisRun> results = Lists.newArrayList(resultBuildOne, resultBuildTwo, aBuildTooMuch);

        GraphConfiguration configuration = createGraphConfiguration();
        when(configuration.isBuildCountDefined()).thenReturn(true);
        when(configuration.getBuildCount()).thenReturn(2);

        CategoryDataset dataSet = builder.createDataSet(configuration, results);
        assertThat(dataSet.getRowCount()).isEqualTo(3);
        assertThat(dataSet.getColumnCount()).isEqualTo(2);

        assertThat(dataSet.getColumnKey(0).toString()).isEqualTo("#41");
        assertThat(dataSet.getColumnKey(1).toString()).isEqualTo("#42");
    }

    /**
     * Verifies the correct behaviour of a normal per-date-report-graph. This report-graph takes a look on the last
     * three days.
     */
    @Test
    void perDateReportGraph() {
        HealthDescriptor descriptor = new HealthDescriptor("3", "5", Priority.NORMAL);
        HealthSeriesBuilder builder = new HealthSeriesBuilder(descriptor);
        LocalDate today = new LocalDate(System.currentTimeMillis());
        Long yesterday = today.minusDays(1).toDate().getTime();
        Long beforeYesterday = today.minusDays(2).toDate().getTime();

        StaticAnalysisRun resultBuildTodayOne = createBuildResult(42, 8, System.currentTimeMillis());
        StaticAnalysisRun resultBuildTodayTwo = createBuildResult(41, 6, System.currentTimeMillis());
        StaticAnalysisRun resultBuildYesterday = createBuildResult(40, 8, yesterday);
        StaticAnalysisRun resultBuildBeforeYesterday = createBuildResult(39, 8, beforeYesterday);

        List<StaticAnalysisRun> results = Lists.newArrayList(
                resultBuildTodayOne,
                resultBuildTodayTwo,
                resultBuildYesterday,
                resultBuildBeforeYesterday);
        GraphConfiguration configuration = createGraphConfiguration();
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        CategoryDataset dataSet = builder.createDataSet(configuration, results);

        assertThat(dataSet.getColumnCount()).isEqualTo(3);
        assertThat(dataSet.getRowCount()).isEqualTo(3);

        LocalDateLabel expectedDateLabelToday = new LocalDateLabel(new LocalDate(System.currentTimeMillis()));
        LocalDateLabel expectedDateLabelYesterday = new LocalDateLabel(new LocalDate(yesterday));
        LocalDateLabel expectedDateLabelBeforeYesterday = new LocalDateLabel(new LocalDate(beforeYesterday));

        assertThat(dataSet.getColumnKey(2)).isEqualTo(expectedDateLabelToday);
        assertThat(dataSet.getColumnKey(1)).isEqualTo(expectedDateLabelYesterday);
        assertThat(dataSet.getColumnKey(0)).isEqualTo(expectedDateLabelBeforeYesterday);

        //Build of day before yesterday
        assertThat(dataSet.getValue(0, 0)).isEqualTo(3);
        assertThat(dataSet.getValue(1, 0)).isEqualTo(2);
        assertThat(dataSet.getValue(2, 0)).isEqualTo(3);
        //Build of Yesterday
        assertThat(dataSet.getValue(0, 1)).isEqualTo(3);
        assertThat(dataSet.getValue(1, 1)).isEqualTo(2);
        assertThat(dataSet.getValue(2, 1)).isEqualTo(3);
        //Build of Today
        assertThat(dataSet.getValue(0, 2)).isEqualTo(3);
        assertThat(dataSet.getValue(1, 2)).isEqualTo(2);
        assertThat(dataSet.getValue(2, 2)).isEqualTo(2);
    }

}