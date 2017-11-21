package io.jenkins.plugins.analysis.core.graphs;

import java.util.ArrayList;
import java.util.List;

import org.jfree.data.category.CategoryDataset;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

import io.jenkins.plugins.analysis.core.quality.AnalysisBuild;
import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the class {@link SeriesBuilder}.
 *
 * @author Tom Maier
 */
class HealthSeriesBuilderTest {

    private static final int A_DAY_IN_MSEC = 24 * 3600 * 1000;

    @Test
    void healthDescriptorIsDisabled() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);

        StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);
        when(buildResult.getTotalSize()).thenReturn(10);

        HealthSeriesBuilder healthSeriesBuilder = new HealthSeriesBuilder(healthDescriptor);
        List<Integer> list = healthSeriesBuilder.computeSeries(buildResult);

        assertThat(list.get(0)).isEqualTo(10);
    }

    @Test
    void shouldComputeHealthySeries() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(10);

        HealthSeriesBuilder healthSeriesBuilder = new HealthSeriesBuilder(healthDescriptor);
        List<Integer> list = healthSeriesBuilder.computeSeries(analysisRun);

        assertThat(list.get(0)).isEqualTo(10);
        assertThat(list.get(1)).isEqualTo(0);
        assertThat(list.get(2)).isEqualTo(0);
    }

    @Test
    void shouldComputeWithSomeErrorsSeries() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(20);

        HealthSeriesBuilder healthSeriesBuilder = new HealthSeriesBuilder(healthDescriptor);
        List<Integer> list = healthSeriesBuilder.computeSeries(analysisRun);

        assertThat(list.get(0)).isEqualTo(10);
        assertThat(list.get(1)).isEqualTo(10);
        assertThat(list.get(2)).isEqualTo(0);
    }

    @Test
    void shouldComputeUnHealthySeries() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(30);

        HealthSeriesBuilder healthSeriesBuilder = new HealthSeriesBuilder(healthDescriptor);
        List<Integer> list = healthSeriesBuilder.computeSeries(analysisRun);

        assertThat(list.get(0)).isEqualTo(10);
        assertThat(list.get(1)).isEqualTo(10);
        assertThat(list.get(2)).isEqualTo(10);
    }


    @Test
    void emptyBuild() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        GraphConfiguration graphConfiguration = mock(GraphConfiguration.class);

        List<StaticAnalysisRun> analysisRuns = new ArrayList<>();

        HealthSeriesBuilder healthSeriesBuilder = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset categoryDataset = healthSeriesBuilder.createDataSet(graphConfiguration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(0);
        assertThat(categoryDataset.getRowCount()).isEqualTo(0);

    }

    @Test
    void shouldCreateOneBuild() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        GraphConfiguration graphConfiguration = mock(GraphConfiguration.class);
        when(graphConfiguration.isBuildCountDefined()).thenReturn(true);
        when(graphConfiguration.getBuildCount()).thenReturn(3);

        StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(5);

        AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
        when(analysisBuild.getNumber()).thenReturn(7);
        when(analysisRun.getBuild()).thenReturn(analysisBuild);

        List<StaticAnalysisRun> analysisRuns = new ArrayList<>();
        analysisRuns.add(analysisRun);

        HealthSeriesBuilder healthSeriesBuilder = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset categoryDataset = healthSeriesBuilder.createDataSet(graphConfiguration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(1);
        assertThat(categoryDataset.getRowCount()).isEqualTo(3);

        assertThat(categoryDataset.getValue(0, 0)).isEqualTo(5);
        assertThat(categoryDataset.getValue(1, 0)).isEqualTo(0);
        assertThat(categoryDataset.getValue(2, 0)).isEqualTo(0);

    }

    @Test
    void shouldCreateOneAllowedBuild() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        GraphConfiguration graphConfiguration = mock(GraphConfiguration.class);
        when(graphConfiguration.isBuildCountDefined()).thenReturn(true);
        when(graphConfiguration.getBuildCount()).thenReturn(1);

        StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(5);

        AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
        when(analysisBuild.getNumber()).thenReturn(7);
        when(analysisRun.getBuild()).thenReturn(analysisBuild);

        List<StaticAnalysisRun> analysisRuns = new ArrayList<>();
        analysisRuns.add(analysisRun);

        HealthSeriesBuilder healthSeriesBuilder = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset categoryDataset = healthSeriesBuilder.createDataSet(graphConfiguration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(1);
        assertThat(categoryDataset.getRowCount()).isEqualTo(3);

        assertThat(categoryDataset.getValue(0, 0)).isEqualTo(5);
        assertThat(categoryDataset.getValue(1, 0)).isEqualTo(0);
        assertThat(categoryDataset.getValue(2, 0)).isEqualTo(0);

    }

    @Test
    void shouldCreateOneTooOldBuild() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        GraphConfiguration graphConfiguration = mock(GraphConfiguration.class);
        when(graphConfiguration.isDayCountDefined()).thenReturn(true);
        when(graphConfiguration.getDayCount()).thenReturn(0);

        StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(5);

        AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
        when(analysisBuild.getTimeInMillis()).thenReturn(System.currentTimeMillis() - A_DAY_IN_MSEC);
        when(analysisRun.getBuild()).thenReturn(analysisBuild);

        List<StaticAnalysisRun> analysisRuns = new ArrayList<>();
        analysisRuns.add(analysisRun);

        HealthSeriesBuilder healthSeriesBuilder = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset categoryDataset = healthSeriesBuilder.createDataSet(graphConfiguration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(0);
        assertThat(categoryDataset.getRowCount()).isEqualTo(0);

    }

    @Test
    void shouldCreateTwoBuilds() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        GraphConfiguration graphConfiguration = mock(GraphConfiguration.class);
        when(graphConfiguration.isBuildCountDefined()).thenReturn(true);
        when(graphConfiguration.getBuildCount()).thenReturn(2);

        List<StaticAnalysisRun> analysisRuns = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            // anzahl der error in einem build
            StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
            when(analysisRun.getTotalSize()).thenReturn(5 * i);

            AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
            when(analysisBuild.getNumber()).thenReturn(i);
            when(analysisRun.getBuild()).thenReturn(analysisBuild);

            analysisRuns.add(analysisRun);
        }


        HealthSeriesBuilder healthSeriesBuilder = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset categoryDataset = healthSeriesBuilder.createDataSet(graphConfiguration, analysisRuns);

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
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        GraphConfiguration graphConfiguration = mock(GraphConfiguration.class);
        when(graphConfiguration.isDayCountDefined()).thenReturn(true);
        when(graphConfiguration.getDayCount()).thenReturn(1);

        List<StaticAnalysisRun> analysisRuns = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            // anzahl der error in einem build
            StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
            when(analysisRun.getTotalSize()).thenReturn(5 * i);


            AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
            when(analysisBuild.getTimeInMillis()).thenReturn(System.currentTimeMillis() - (i - 1) * A_DAY_IN_MSEC);
            when(analysisRun.getBuild()).thenReturn(analysisBuild);

            analysisRuns.add(analysisRun);
        }


        HealthSeriesBuilder healthSeriesBuilder = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset categoryDataset = healthSeriesBuilder.createDataSet(graphConfiguration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(1);
        assertThat(categoryDataset.getRowCount()).isEqualTo(3);

        assertThat(categoryDataset.getValue(0, 0)).isEqualTo(5);
        assertThat(categoryDataset.getValue(1, 0)).isEqualTo(0);
        assertThat(categoryDataset.getValue(2, 0)).isEqualTo(0);
    }

    @Test
    void shouldCreateOneBuildUsingBuildDateAsDomain() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        GraphConfiguration graphConfiguration = mock(GraphConfiguration.class);
        when(graphConfiguration.isBuildCountDefined()).thenReturn(true);
        when(graphConfiguration.getBuildCount()).thenReturn(3);
        when(graphConfiguration.useBuildDateAsDomain()).thenReturn(true);

        StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(5);

        AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
        when(analysisBuild.getNumber()).thenReturn(7);
        when(analysisRun.getBuild()).thenReturn(analysisBuild);

        List<StaticAnalysisRun> analysisRuns = new ArrayList<>();
        analysisRuns.add(analysisRun);

        HealthSeriesBuilder healthSeriesBuilder = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset categoryDataset = healthSeriesBuilder.createDataSet(graphConfiguration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(1);
        assertThat(categoryDataset.getRowCount()).isEqualTo(3);

        assertThat(categoryDataset.getValue(0, 0)).isEqualTo(5);
        assertThat(categoryDataset.getValue(1, 0)).isEqualTo(0);
        assertThat(categoryDataset.getValue(2, 0)).isEqualTo(0);

    }

    @Test
    void shouldCreateOneAllowedBuildUsingBuildDateAsDomain() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        GraphConfiguration graphConfiguration = mock(GraphConfiguration.class);
        when(graphConfiguration.isBuildCountDefined()).thenReturn(true);
        when(graphConfiguration.getBuildCount()).thenReturn(1);
        when(graphConfiguration.useBuildDateAsDomain()).thenReturn(true);

        StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(5);

        AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
        when(analysisBuild.getNumber()).thenReturn(7);
        when(analysisRun.getBuild()).thenReturn(analysisBuild);

        List<StaticAnalysisRun> analysisRuns = new ArrayList<>();
        analysisRuns.add(analysisRun);

        HealthSeriesBuilder healthSeriesBuilder = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset categoryDataset = healthSeriesBuilder.createDataSet(graphConfiguration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(1);
        assertThat(categoryDataset.getRowCount()).isEqualTo(3);

        assertThat(categoryDataset.getValue(0, 0)).isEqualTo(5);
        assertThat(categoryDataset.getValue(1, 0)).isEqualTo(0);
        assertThat(categoryDataset.getValue(2, 0)).isEqualTo(0);

    }

    @Test
    void shouldCreateOneTooOldBuildUsingBuildDateAsDomain() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        GraphConfiguration graphConfiguration = mock(GraphConfiguration.class);
        when(graphConfiguration.isDayCountDefined()).thenReturn(true);
        when(graphConfiguration.getDayCount()).thenReturn(0);
        when(graphConfiguration.useBuildDateAsDomain()).thenReturn(true);

        StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
        when(analysisRun.getTotalSize()).thenReturn(5);

        AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
        when(analysisBuild.getTimeInMillis()).thenReturn(System.currentTimeMillis() - A_DAY_IN_MSEC);
        when(analysisRun.getBuild()).thenReturn(analysisBuild);

        List<StaticAnalysisRun> analysisRuns = new ArrayList<>();
        analysisRuns.add(analysisRun);

        HealthSeriesBuilder healthSeriesBuilder = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset categoryDataset = healthSeriesBuilder.createDataSet(graphConfiguration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(0);
        assertThat(categoryDataset.getRowCount()).isEqualTo(0);

    }

    @Test
    void shouldCreateTwoBuildsUsingBuildDateAsDomain() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        GraphConfiguration graphConfiguration = mock(GraphConfiguration.class);
        when(graphConfiguration.isBuildCountDefined()).thenReturn(true);
        when(graphConfiguration.getBuildCount()).thenReturn(2);
        when(graphConfiguration.useBuildDateAsDomain()).thenReturn(true);

        List<StaticAnalysisRun> analysisRuns = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            // anzahl der error in einem build
            StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
            when(analysisRun.getTotalSize()).thenReturn(5 * i);

            AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
            when(analysisBuild.getNumber()).thenReturn(i);
            when(analysisRun.getBuild()).thenReturn(analysisBuild);

            analysisRuns.add(analysisRun);
        }


        HealthSeriesBuilder healthSeriesBuilder = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset categoryDataset = healthSeriesBuilder.createDataSet(graphConfiguration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(1);
        assertThat(categoryDataset.getRowCount()).isEqualTo(3);

        assertThat(categoryDataset.getValue(0, 0)).isEqualTo(7);
        assertThat(categoryDataset.getValue(1, 0)).isEqualTo(0);
        assertThat(categoryDataset.getValue(2, 0)).isEqualTo(0);
    }

    @Test
    void shouldCreateTwoBuildsOneAllowedOneToOldUsingBuildDateAsDomain() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(10);
        when(healthDescriptor.getUnHealthy()).thenReturn(20);

        GraphConfiguration graphConfiguration = mock(GraphConfiguration.class);
        when(graphConfiguration.isDayCountDefined()).thenReturn(true);
        when(graphConfiguration.getDayCount()).thenReturn(1);
        when(graphConfiguration.useBuildDateAsDomain()).thenReturn(true);

        List<StaticAnalysisRun> analysisRuns = new ArrayList<>();

        for (int i = 1; i <= 2; i++) {
            // anzahl der error in einem build
            StaticAnalysisRun analysisRun = mock(StaticAnalysisRun.class);
            when(analysisRun.getTotalSize()).thenReturn(5 * i);


            AnalysisBuild analysisBuild = mock(AnalysisBuild.class);
            when(analysisBuild.getTimeInMillis()).thenReturn(System.currentTimeMillis() - (i - 1) * A_DAY_IN_MSEC);
            when(analysisRun.getBuild()).thenReturn(analysisBuild);

            analysisRuns.add(analysisRun);
        }


        HealthSeriesBuilder healthSeriesBuilder = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset categoryDataset = healthSeriesBuilder.createDataSet(graphConfiguration, analysisRuns);

        assertThat(categoryDataset.getColumnCount()).isEqualTo(1);
        assertThat(categoryDataset.getRowCount()).isEqualTo(3);

        assertThat(categoryDataset.getValue(0, 0)).isEqualTo(5);
        assertThat(categoryDataset.getValue(1, 0)).isEqualTo(0);
        assertThat(categoryDataset.getValue(2, 0)).isEqualTo(0);
    }
}