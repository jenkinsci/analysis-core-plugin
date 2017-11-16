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

    @Test
    void disabledHealthDiscrptorTest() {
        HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(false);

        StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);
        when(buildResult.getTotalSize()).thenReturn(5);

        HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        List<Integer> series = builder.computeSeries(buildResult);

        assertThat(series.size()).isEqualTo(1);
        assertThat(series.get(0)).isEqualTo(5);

    }

    @Test
    void onlyHealhyGraphTest() {
        HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);
        when(buildResult.getTotalSize()).thenReturn(9);

        HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        List<Integer> series = builder.computeSeries(buildResult);

        assertThat(series.size()).isEqualTo(3);
        assertThat(series.get(0)).isEqualTo(9);
        assertThat(series.get(1)).isEqualTo(0);
        assertThat(series.get(2)).isEqualTo(0);

    }

    @Test
    void notonlyHealthyTest() {
        HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);
        when(buildResult.getTotalSize()).thenReturn(11);

        HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        List<Integer> series = builder.computeSeries(buildResult);

        assertThat(series.size()).isEqualTo(3);
        assertThat(series.get(0)).isEqualTo(10);
        assertThat(series.get(1)).isEqualTo(1);
        assertThat(series.get(2)).isEqualTo(0);

    }

    @Test
    void unhealthyTest() {
        HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);

        StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);
        when(buildResult.getTotalSize()).thenReturn(21);

        HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        List<Integer> series = builder.computeSeries(buildResult);

        assertThat(series.size()).isEqualTo(3);
        assertThat(series.get(0)).isEqualTo(10);
        assertThat(series.get(1)).isEqualTo(10);
        assertThat(series.get(2)).isEqualTo(1);

    }

    @Test
    void daySeriesTest() {
        HealthDescriptor health = mock(HealthDescriptor.class);
        when(health.isEnabled()).thenReturn(true);
        when(health.getHealthy()).thenReturn(10);
        when(health.getUnHealthy()).thenReturn(20);


        HealthSeriesBuilder builder = new HealthSeriesBuilder(health);

        GraphConfiguration graphConfig = mock(GraphConfiguration.class);
        when(graphConfig.useBuildDateAsDomain()).thenReturn(false);

        List<StaticAnalysisRun> runs = new ArrayList<>();

        for (int i = 1; i <= 4; i++) {
            StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);
            when(buildResult.getTotalSize()).thenReturn(i * 10);
            AnalysisBuild build = mock(AnalysisBuild.class);
            when(build.getNumber()).thenReturn(i);
            when(buildResult.getBuild()).thenReturn(build);
            runs.add(buildResult);

        }


        CategoryDataset dataset = builder.createDataSet(graphConfig, runs);

       System.out.print("for debug");



    }


}