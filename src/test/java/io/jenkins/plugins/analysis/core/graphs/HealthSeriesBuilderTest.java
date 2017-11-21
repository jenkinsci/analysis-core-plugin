package io.jenkins.plugins.analysis.core.graphs;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.category.CategoryDataset;
import org.junit.jupiter.api.Test;

import io.jenkins.plugins.analysis.core.quality.AnalysisBuild;
import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tests the class {@link SeriesBuilder}.
 *
 * @author Tom Maier
 */
class HealthSeriesBuilderTest {

    private static final int A_DAY_IN_M_SEC = 24 * 3600 * 1000;

    @Test
    void healthDescriptorIsDisabled() {
        final HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);

        final StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);
        when(buildResult.getTotalSize()).thenReturn(10);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);
        final List<Integer> list = builder.computeSeries(buildResult);

        assertThat(list.get(0)).isEqualTo(10);
    }

    @Test
    void shouldComputeHealthySeries() {
        final HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        final StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(10);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);
        final List<Integer> list = builder.computeSeries(analysisRun);

        assertThat(list.get(0)).isEqualTo(10);
        assertThat(list.get(1)).isEqualTo(0);
        assertThat(list.get(2)).isEqualTo(0);
    }

    @Test
    void shouldComputeWithSomeErrorsSeries() {
        final HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        final StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(20);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);
        final List<Integer> list = builder.computeSeries(analysisRun);

        assertThat(list.get(0)).isEqualTo(10);
        assertThat(list.get(1)).isEqualTo(10);
        assertThat(list.get(2)).isEqualTo(0);
    }

    @Test
    void shouldComputeUnHealthySeries() {
        final HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        final StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(30);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);
        final List<Integer> list = builder.computeSeries(analysisRun);

        assertThat(list.get(0)).isEqualTo(10);
        assertThat(list.get(1)).isEqualTo(10);
        assertThat(list.get(2)).isEqualTo(10);
    }

    @Test
    void emptyBuild() {
        final HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        final GraphConfiguration configuration = mock(GraphConfiguration.class);

        final List<StaticAnalysisRun> analysisRuns = new ArrayList<>();

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);

        final CategoryDataset categoryDataset = builder.createDataSet(configuration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(0);
        assertThat(categoryDataset.getRowCount()).isEqualTo(0);

    }

    @Test
    void shouldCreateOneBuild() {
        final HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        final GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.isBuildCountDefined()).thenReturn(true);
        when(configuration.getBuildCount()).thenReturn(3);

        final StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(5);

        final AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
        when(analysisBuild.getNumber()).thenReturn(7);
        when(analysisRun.getBuild()).thenReturn(analysisBuild);

        final List<StaticAnalysisRun> analysisRuns = new ArrayList<>();
        analysisRuns.add(analysisRun);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);

        final CategoryDataset categoryDataset = builder.createDataSet(configuration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(1);
        assertThat(categoryDataset.getRowCount()).isEqualTo(3);

        assertThat(categoryDataset.getValue(0, 0)).isEqualTo(5);
        assertThat(categoryDataset.getValue(1, 0)).isEqualTo(0);
        assertThat(categoryDataset.getValue(2, 0)).isEqualTo(0);

    }

    @Test
    void shouldCreateOneAllowedBuild() {
        final HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        final GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.isBuildCountDefined()).thenReturn(true);
        when(configuration.getBuildCount()).thenReturn(1);

        final StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(5);

        final AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
        when(analysisBuild.getNumber()).thenReturn(7);
        when(analysisRun.getBuild()).thenReturn(analysisBuild);

        final List<StaticAnalysisRun> analysisRuns = new ArrayList<>();
        analysisRuns.add(analysisRun);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);

        final CategoryDataset categoryDataset = builder.createDataSet(configuration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(1);
        assertThat(categoryDataset.getRowCount()).isEqualTo(3);

        assertThat(categoryDataset.getValue(0, 0)).isEqualTo(5);
        assertThat(categoryDataset.getValue(1, 0)).isEqualTo(0);
        assertThat(categoryDataset.getValue(2, 0)).isEqualTo(0);

    }

    @Test
    void shouldCreateOneTooOldBuild() {
        final HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        final GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.isDayCountDefined()).thenReturn(true);
        when(configuration.getDayCount()).thenReturn(0);

        final StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(5);

        final AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
        when(analysisBuild.getTimeInMillis()).thenReturn(System.currentTimeMillis() - A_DAY_IN_M_SEC);
        when(analysisRun.getBuild()).thenReturn(analysisBuild);

        final List<StaticAnalysisRun> analysisRuns = new ArrayList<>();
        analysisRuns.add(analysisRun);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);

        final CategoryDataset categoryDataset = builder.createDataSet(configuration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(0);
        assertThat(categoryDataset.getRowCount()).isEqualTo(0);

    }

    @Test
    void shouldCreateTwoBuilds() {
        final HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        final GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.isBuildCountDefined()).thenReturn(true);
        when(configuration.getBuildCount()).thenReturn(2);

        final List<StaticAnalysisRun> analysisRuns = getStaticAnalysisRunsForOneDay();

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);

        final CategoryDataset categoryDataset = builder.createDataSet(configuration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(2);
        assertThat(categoryDataset.getRowCount()).isEqualTo(3);

        assertThat(categoryDataset.getValue(0, 0)).isEqualTo(5);
        assertThat(categoryDataset.getValue(1, 0)).isEqualTo(0);
        assertThat(categoryDataset.getValue(2, 0)).isEqualTo(0);

        assertThat(categoryDataset.getValue(0, 1)).isEqualTo(10);
        assertThat(categoryDataset.getValue(1, 1)).isEqualTo(0);
        assertThat(categoryDataset.getValue(2, 1)).isEqualTo(0);
    }

    @Test
    void shouldCreateTwoBuildsOneAllowedOneToOld() {
        final HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        final GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.isDayCountDefined()).thenReturn(true);
        when(configuration.getDayCount()).thenReturn(1);

        final List<StaticAnalysisRun> analysisRuns = getStaticAnalysisRunsByDays();

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);

        final CategoryDataset categoryDataset = builder.createDataSet(configuration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(1);
        assertThat(categoryDataset.getRowCount()).isEqualTo(3);

        assertThat(categoryDataset.getValue(0, 0)).isEqualTo(5);
        assertThat(categoryDataset.getValue(1, 0)).isEqualTo(0);
        assertThat(categoryDataset.getValue(2, 0)).isEqualTo(0);
    }

    @Test
    void shouldCreateOneBuildUsingBuildDateAsDomain() {
        final HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        final GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.isBuildCountDefined()).thenReturn(true);
        when(configuration.getBuildCount()).thenReturn(3);
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        final StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(5);

        final AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
        when(analysisBuild.getNumber()).thenReturn(7);
        when(analysisRun.getBuild()).thenReturn(analysisBuild);

        final List<StaticAnalysisRun> analysisRuns = new ArrayList<>();
        analysisRuns.add(analysisRun);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);

        final CategoryDataset categoryDataset = builder.createDataSet(configuration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(1);
        assertThat(categoryDataset.getRowCount()).isEqualTo(3);

        assertThat(categoryDataset.getValue(0, 0)).isEqualTo(5);
        assertThat(categoryDataset.getValue(1, 0)).isEqualTo(0);
        assertThat(categoryDataset.getValue(2, 0)).isEqualTo(0);

    }

    @Test
    void shouldCreateOneAllowedBuildUsingBuildDateAsDomain() {
        final HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        final GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.isBuildCountDefined()).thenReturn(true);
        when(configuration.getBuildCount()).thenReturn(1);
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        final StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(5);

        final AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
        when(analysisBuild.getNumber()).thenReturn(7);
        when(analysisRun.getBuild()).thenReturn(analysisBuild);

        final List<StaticAnalysisRun> analysisRuns = new ArrayList<>();
        analysisRuns.add(analysisRun);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);

        final CategoryDataset categoryDataset = builder.createDataSet(configuration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(1);
        assertThat(categoryDataset.getRowCount()).isEqualTo(3);

        assertThat(categoryDataset.getValue(0, 0)).isEqualTo(5);
        assertThat(categoryDataset.getValue(1, 0)).isEqualTo(0);
        assertThat(categoryDataset.getValue(2, 0)).isEqualTo(0);

    }

    @Test
    void shouldCreateOneTooOldBuildUsingBuildDateAsDomain() {
        final HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        final GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.isDayCountDefined()).thenReturn(true);
        when(configuration.getDayCount()).thenReturn(0);
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        final StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(5);

        final AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
        when(analysisBuild.getTimeInMillis()).thenReturn(System.currentTimeMillis() - A_DAY_IN_M_SEC);
        when(analysisRun.getBuild()).thenReturn(analysisBuild);

        final List<StaticAnalysisRun> analysisRuns = new ArrayList<>();
        analysisRuns.add(analysisRun);

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);

        final CategoryDataset categoryDataset = builder.createDataSet(configuration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(0);
        assertThat(categoryDataset.getRowCount()).isEqualTo(0);

    }

    @Test
    void shouldCreateTwoBuildsUsingBuildDateAsDomain() {
        final HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        final GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.isBuildCountDefined()).thenReturn(true);
        when(configuration.getBuildCount()).thenReturn(2);
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        final List<StaticAnalysisRun> analysisRuns = getStaticAnalysisRunsForOneDay();

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);

        final CategoryDataset categoryDataset = builder.createDataSet(configuration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(1);
        assertThat(categoryDataset.getRowCount()).isEqualTo(3);

        assertThat(categoryDataset.getValue(0, 0)).isEqualTo(7);
        assertThat(categoryDataset.getValue(1, 0)).isEqualTo(0);
        assertThat(categoryDataset.getValue(2, 0)).isEqualTo(0);
    }

    @Test
    void shouldCreateTwoBuildsOneAllowedOneToOldUsingBuildDateAsDomain() {
        final HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        final GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.isDayCountDefined()).thenReturn(true);
        when(configuration.getDayCount()).thenReturn(1);
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        final List<StaticAnalysisRun> analysisRuns = getStaticAnalysisRunsByDays();

        final HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);

        final CategoryDataset categoryDataset = builder.createDataSet(configuration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(1);
        assertThat(categoryDataset.getRowCount()).isEqualTo(3);

        assertThat(categoryDataset.getValue(0, 0)).isEqualTo(5);
        assertThat(categoryDataset.getValue(1, 0)).isEqualTo(0);
        assertThat(categoryDataset.getValue(2, 0)).isEqualTo(0);
    }

    private List<StaticAnalysisRun> getStaticAnalysisRunsByDays() {
        final List<StaticAnalysisRun> analysisRuns = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            final StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
            when(analysisRun.getTotalSize()).thenReturn(5 * i);

            final AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
            when(analysisBuild.getTimeInMillis()).thenReturn(System.currentTimeMillis() - (i - 1) * A_DAY_IN_M_SEC);
            when(analysisRun.getBuild()).thenReturn(analysisBuild);

            analysisRuns.add(analysisRun);
        }
        return analysisRuns;
    }


    private List<StaticAnalysisRun> getStaticAnalysisRunsForOneDay() {
        final List<StaticAnalysisRun> analysisRuns = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            final StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
            when(analysisRun.getTotalSize()).thenReturn(5 * i);

            final AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
            when(analysisBuild.getNumber()).thenReturn(i);
            when(analysisRun.getBuild()).thenReturn(analysisBuild);

            analysisRuns.add(analysisRun);
        }
        return analysisRuns;
    }
}