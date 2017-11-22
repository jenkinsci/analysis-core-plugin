package io.jenkins.plugins.analysis.core.graphs;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.category.CategoryDataset;
import org.junit.jupiter.api.Test;

import io.jenkins.plugins.analysis.core.quality.AnalysisBuild;
import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link HealthSeriesBuilder}.
 *
 * @author Johannes Arzt
 */

class HealthSeriesBuilderTest {

    private static final int A_DAY_IN_MSEC = 24 * 3600 * 1000;

    @Test
    void disabledHealthDescriptor() {
        final HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(false);

        final StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);
        when(buildResult.getTotalSize()).thenReturn(5);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        final List<Integer> series = builder.computeSeries(buildResult);

        assertThat(series.size()).isEqualTo(1);
        assertThat(series.get(0)).isEqualTo(5);

    }

    @Test
    void onlyHealthyGraph() {
        final HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        final StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);
        when(buildResult.getTotalSize()).thenReturn(10);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        final List<Integer> series = builder.computeSeries(buildResult);

        assertThat(series.size()).isEqualTo(3);
        assertThat(series.get(0)).isEqualTo(10);
        assertThat(series.get(1)).isEqualTo(0);
        assertThat(series.get(2)).isEqualTo(0);

    }

    @Test
    void notOnlyHealthyGraph() {
        final HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        final StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);
        when(buildResult.getTotalSize()).thenReturn(11);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        final List<Integer> series = builder.computeSeries(buildResult);

        assertThat(series.size()).isEqualTo(3);
        assertThat(series.get(0)).isEqualTo(10);
        assertThat(series.get(1)).isEqualTo(1);
        assertThat(series.get(2)).isEqualTo(0);

    }

    @Test
    void healthyAndUnhealthyGraph() {
        final HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        final StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);
        when(buildResult.getTotalSize()).thenReturn(21);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        final List<Integer> series = builder.computeSeries(buildResult);

        assertThat(series.size()).isEqualTo(3);
        assertThat(series.get(0)).isEqualTo(10);
        assertThat(series.get(1)).isEqualTo(10);
        assertThat(series.get(2)).isEqualTo(1);
    }

    @Test
    void doNoBuild() {
        final HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        final GraphConfiguration graphConfig = mock(GraphConfiguration.class);
        when(graphConfig.useBuildDateAsDomain()).thenReturn(false);

        final List<StaticAnalysisRun> runs = new ArrayList<>();
        final CategoryDataset dataset = builder.createDataSet(graphConfig, runs);
        assertThat(dataset.getRowCount()).isEqualTo(0);
        assertThat(dataset.getColumnCount()).isEqualTo(0);
    }

    @Test
    void doOneBuildWithoutConditions() {
        final HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        final GraphConfiguration graphConfig = mock(GraphConfiguration.class);
        when(graphConfig.useBuildDateAsDomain()).thenReturn(false);

        final StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(10);
        final AnalysisBuild build = mock(AnalysisBuild.class);
        when(run.getBuild()).thenReturn(build);

        final List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(run);
        final CategoryDataset dataset = builder.createDataSet(graphConfig, runs);
        assertThat(dataset.getRowCount()).isEqualTo(3);
        assertThat(dataset.getColumnCount()).isEqualTo(1);

    }

    @Test
    void doTwoBuildsInARowWithoutConditions() {
        final HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(health);
        final GraphConfiguration graphConfig = mock(GraphConfiguration.class);

        final List<StaticAnalysisRun> runs = createAnalyseRunWithBuildNumber(2);
        final CategoryDataset dataset = builder.createDataSet(graphConfig, runs);

        assertThat(dataset.getRowCount()).isEqualTo(3);
        assertThat(dataset.getColumnCount()).isEqualTo(2);

        assertThat(dataset.getValue(0, 0)).isEqualTo(10);
        assertThat(dataset.getValue(1, 0)).isEqualTo(0);
        assertThat(dataset.getValue(2, 0)).isEqualTo(0);

        assertThat(dataset.getValue(0, 1)).isEqualTo(10);
        assertThat(dataset.getValue(1, 1)).isEqualTo(10);
        assertThat(dataset.getValue(2, 1)).isEqualTo(0);
    }

    @Test
    void oneBuildPerDayOneDayLong() {
        final HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        final GraphConfiguration graphConfig = mock(GraphConfiguration.class);
        when(graphConfig.useBuildDateAsDomain()).thenReturn(true);

        final StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(10);
        final AnalysisBuild build = mock(AnalysisBuild.class);
        when(run.getBuild()).thenReturn(build);

        final List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(run);
        final CategoryDataset dataset = builder.createDataSet(graphConfig, runs);

        assertThat(dataset.getRowCount()).isEqualTo(3);
        assertThat(dataset.getColumnCount()).isEqualTo(1);
        assertThat(dataset.getValue(0, 0)).isEqualTo(10);
        assertThat(dataset.getValue(1, 0)).isEqualTo(0);
        assertThat(dataset.getValue(2, 0)).isEqualTo(0);

    }

    @Test
    void noBuildPerDayOneDayLong() {
        final HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        final GraphConfiguration graphConfig = mock(GraphConfiguration.class);
        when(graphConfig.useBuildDateAsDomain()).thenReturn(true);

        final List<StaticAnalysisRun> runs = new ArrayList<>();
        final CategoryDataset dataset = builder.createDataSet(graphConfig, runs);
        assertThat(dataset.getRowCount()).isEqualTo(0);
        assertThat(dataset.getColumnCount()).isEqualTo(0);


    }

    @Test
    void twoBuildsPerDayOneDayLong() {
        final HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        final GraphConfiguration graphConfig = mock(GraphConfiguration.class);
        when(graphConfig.useBuildDateAsDomain()).thenReturn(true);

        final List<StaticAnalysisRun> runs = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            final StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);
            when(buildResult.getTotalSize()).thenReturn(i * 10);
            final AnalysisBuild build = mock(AnalysisBuild.class);
            when(build.getTimeInMillis()).thenReturn(System.currentTimeMillis());
            when(build.getNumber()).thenReturn(i);
            when(buildResult.getBuild()).thenReturn(build);
            runs.add(buildResult);
        }
        final CategoryDataset dataset = builder.createDataSet(graphConfig, runs);

        assertThat(dataset.getRowCount()).isEqualTo(3);
        assertThat(dataset.getColumnCount()).isEqualTo(1);
        assertThat(dataset.getValue(0, 0)).isEqualTo(10);
        assertThat(dataset.getValue(1, 0)).isEqualTo(5);
        assertThat(dataset.getValue(2, 0)).isEqualTo(0);


    }

    @Test
    void oneBuildsPerDayTwoDayLong() {
        final HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        final GraphConfiguration graphConfig = mock(GraphConfiguration.class);
        when(graphConfig.useBuildDateAsDomain()).thenReturn(true);

        final List<StaticAnalysisRun> runs = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            final StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);
            when(buildResult.getTotalSize()).thenReturn(i * 10);
            final AnalysisBuild build = mock(AnalysisBuild.class);
            when(build.getTimeInMillis()).thenReturn(System.currentTimeMillis() * (i - 1));
            when(build.getNumber()).thenReturn(i);
            when(buildResult.getBuild()).thenReturn(build);
            runs.add(buildResult);
        }
        final CategoryDataset dataset = builder.createDataSet(graphConfig, runs);

        assertThat(dataset.getRowCount()).isEqualTo(3);
        assertThat(dataset.getColumnCount()).isEqualTo(2);
        assertThat(dataset.getValue(0, 0)).isEqualTo(10);
        assertThat(dataset.getValue(1, 0)).isEqualTo(0);
        assertThat(dataset.getValue(2, 0)).isEqualTo(0);

        assertThat(dataset.getValue(0, 1)).isEqualTo(10);
        assertThat(dataset.getValue(1, 1)).isEqualTo(10);
        assertThat(dataset.getValue(2, 1)).isEqualTo(0);
    }

    @Test
    void twoBuildsPerDayTwoDayLong() {
        final HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        final GraphConfiguration graphConfig = mock(GraphConfiguration.class);
        when(graphConfig.useBuildDateAsDomain()).thenReturn(true);

        final List<StaticAnalysisRun> runs = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            final StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);
            when(buildResult.getTotalSize()).thenReturn(i * 10);
            final AnalysisBuild build = mock(AnalysisBuild.class);
            when(build.getTimeInMillis()).thenReturn(System.currentTimeMillis() * (i % 2));
            when(build.getNumber()).thenReturn(i);
            when(buildResult.getBuild()).thenReturn(build);
            runs.add(buildResult);
        }
        final CategoryDataset dataset = builder.createDataSet(graphConfig, runs);

        assertThat(dataset.getRowCount()).isEqualTo(3);
        assertThat(dataset.getColumnCount()).isEqualTo(2);
        assertThat(dataset.getValue(0, 0)).isEqualTo(10);
        assertThat(dataset.getValue(1, 0)).isEqualTo(10);
        assertThat(dataset.getValue(2, 0)).isEqualTo(10);

        assertThat(dataset.getValue(0, 1)).isEqualTo(10);
        assertThat(dataset.getValue(1, 1)).isEqualTo(5);
        assertThat(dataset.getValue(2, 1)).isEqualTo(5);
    }

    @Test
    void twoBuildsInARowButOneIsToOld() {
        final HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        final GraphConfiguration graphConfig = mock(GraphConfiguration.class);
        when(graphConfig.useBuildDateAsDomain()).thenReturn(false);
        when(graphConfig.isDayCountDefined()).thenReturn(true);
        when(graphConfig.getDayCount()).thenReturn(1);

        final List<StaticAnalysisRun> runs = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            final StaticAnalysisRun run = mock(StaticAnalysisRun.class);
            when(run.getTotalSize()).thenReturn(10);
            final AnalysisBuild build = mock(AnalysisBuild.class);
            when(build.getTimeInMillis()).thenReturn(System.currentTimeMillis() - (i - 1) * A_DAY_IN_MSEC);
            when(run.getBuild()).thenReturn(build);
            runs.add(run);
        }

        final CategoryDataset dataset = builder.createDataSet(graphConfig, runs);

        assertThat(dataset.getRowCount()).isEqualTo(3);
        assertThat(dataset.getColumnCount()).isEqualTo(1);

        assertThat(dataset.getValue(0, 0)).isEqualTo(10);
        assertThat(dataset.getValue(1, 0)).isEqualTo(0);
        assertThat(dataset.getValue(2, 0)).isEqualTo(0);

    }

    @Test
    void threeBuildsInARowButOneBuildNumberIsToHigh() {
        final HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        final GraphConfiguration graphConfig = mock(GraphConfiguration.class);
        when(graphConfig.useBuildDateAsDomain()).thenReturn(false);
        when(graphConfig.isBuildCountDefined()).thenReturn(true);
        when(graphConfig.getBuildCount()).thenReturn(2);

        final List<StaticAnalysisRun> runs = createAnalyseRunWithBuildNumber(3);
        final CategoryDataset dataset = builder.createDataSet(graphConfig, runs);

        assertThat(dataset.getRowCount()).isEqualTo(3);
        assertThat(dataset.getColumnCount()).isEqualTo(2);

        assertThat(dataset.getValue(0, 0)).isEqualTo(10);
        assertThat(dataset.getValue(1, 0)).isEqualTo(0);
        assertThat(dataset.getValue(2, 0)).isEqualTo(0);

        assertThat(dataset.getValue(0, 1)).isEqualTo(10);
        assertThat(dataset.getValue(1, 1)).isEqualTo(10);
        assertThat(dataset.getValue(2, 1)).isEqualTo(0);


    }

    @Test
    void doOneBuildButThisIsToOld() {
        final HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        final GraphConfiguration graphConfig = mock(GraphConfiguration.class);
        when(graphConfig.useBuildDateAsDomain()).thenReturn(false);
        when(graphConfig.isDayCountDefined()).thenReturn(true);
        when(graphConfig.getDayCount()).thenReturn(1);

        final StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(10);
        final AnalysisBuild build = mock(AnalysisBuild.class);
        when(build.getTimeInMillis()).thenReturn(System.currentTimeMillis() - 2 * A_DAY_IN_MSEC);
        when(run.getBuild()).thenReturn(build);

        final List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(run);
        final CategoryDataset dataset = builder.createDataSet(graphConfig, runs);
        assertThat(dataset.getRowCount()).isEqualTo(0);
        assertThat(dataset.getColumnCount()).isEqualTo(0);

    }

    @Test
    void allowOneBuildAndDoOnlyOne() {
        final HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        final GraphConfiguration graphConfig = mock(GraphConfiguration.class);
        when(graphConfig.useBuildDateAsDomain()).thenReturn(false);
        when(graphConfig.isBuildCountDefined()).thenReturn(true);
        when(graphConfig.getBuildCount()).thenReturn(1);

        final StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(10);
        final AnalysisBuild build = mock(AnalysisBuild.class);
        when(run.getBuild()).thenReturn(build);

        final List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(run);
        final CategoryDataset dataset = builder.createDataSet(graphConfig, runs);

        assertThat(dataset.getRowCount()).isEqualTo(3);
        assertThat(dataset.getColumnCount()).isEqualTo(1);

        assertThat(dataset.getValue(0, 0)).isEqualTo(10);
        assertThat(dataset.getValue(1, 0)).isEqualTo(0);
        assertThat(dataset.getValue(2, 0)).isEqualTo(0);

    }

    @Test
    void allowTwoBuildsAndDoOnlyOne() {
        final HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        final GraphConfiguration graphConfig = mock(GraphConfiguration.class);
        when(graphConfig.useBuildDateAsDomain()).thenReturn(false);
        when(graphConfig.isBuildCountDefined()).thenReturn(true);
        when(graphConfig.getBuildCount()).thenReturn(2);

        final StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(10);
        final AnalysisBuild build = mock(AnalysisBuild.class);
        when(run.getBuild()).thenReturn(build);

        final List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(run);
        final CategoryDataset dataset = builder.createDataSet(graphConfig, runs);

        assertThat(dataset.getRowCount()).isEqualTo(3);
        assertThat(dataset.getColumnCount()).isEqualTo(1);

        assertThat(dataset.getValue(0, 0)).isEqualTo(10);
        assertThat(dataset.getValue(1, 0)).isEqualTo(0);
        assertThat(dataset.getValue(2, 0)).isEqualTo(0);

    }

    private List<StaticAnalysisRun> createAnalyseRunWithBuildNumber(final int count) {
        final List<StaticAnalysisRun> runs = new ArrayList<>();

        for (int i = 1; i <= count; i++) {
            final StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);
            when(buildResult.getTotalSize()).thenReturn(i * 10);
            final AnalysisBuild build = mock(AnalysisBuild.class);
            when(build.getNumber()).thenReturn(i);
            when(buildResult.getBuild()).thenReturn(build);
            runs.add(buildResult);
        }
        return runs;
    }


}