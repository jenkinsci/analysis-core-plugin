package io.jenkins.plugins.analysis.core.graphs;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Test Class for HealthSeriesBuilder.
 */
class HealthSeriesBuilderTest {


    /**
     * generate a stub parameter.
     * @return stub parameter
     */
    private StaticAnalysisRun generateParamStub() {
        StaticAnalysisRun paramStub = mock(StaticAnalysisRun.class);
        when(paramStub.getTotalSize()).thenReturn(0).thenReturn(1).thenReturn(10).thenReturn(20);
        return paramStub;
    }
    @Test
    void healthReportWithDisabledHealthDescriptor() {
        HealthDescriptor healthDiscStub = GenerateHealthStub.generateHealthDescriptor(false, 0, 0);
        StaticAnalysisRun paramStub = generateParamStub();
        HealthSeriesBuilder b = new HealthSeriesBuilder(healthDiscStub);

        assertThat(b.computeSeries(paramStub)).containsExactly(0);
        assertThat(b.computeSeries(paramStub)).containsExactly(1);
        assertThat(b.computeSeries(paramStub)).containsExactly(10);
        assertThat(b.computeSeries(paramStub)).containsExactly(20);
    }
    @Test
    void healthReportWithDisabledHealthDescriptorAndHealthyDefinition() {
        HealthDescriptor healthDiscStub = GenerateHealthStub.generateHealthDescriptor(false, 9, 10);
        StaticAnalysisRun paramStub = generateParamStub();
        HealthSeriesBuilder b = new HealthSeriesBuilder(healthDiscStub);

        List<Integer> firstList = b.computeSeries(paramStub);
        List<Integer> secondList = b.computeSeries(paramStub);
        List<Integer> thirdList = b.computeSeries(paramStub);
        List<Integer> fourthList = b.computeSeries(paramStub);

        assertThat(firstList).containsExactly(0);
        assertThat(secondList).containsExactly(1);
        assertThat(thirdList).containsExactly(10);
        assertThat(fourthList).containsExactly(20);

    }
    @Test
    void healthReportWithNoPreviousHeath() {
        HealthDescriptor healthDiscStub = GenerateHealthStub.generateHealthDescriptor(true, 0, 0);
        StaticAnalysisRun paramStub = generateParamStub();
        HealthSeriesBuilder b = new HealthSeriesBuilder(healthDiscStub);

        List<Integer> firstList = b.computeSeries(paramStub);
        List<Integer> secondList = b.computeSeries(paramStub);
        List<Integer> thirdList = b.computeSeries(paramStub);
        List<Integer> fourthList = b.computeSeries(paramStub);

        assertThat(firstList).containsExactly(0, 0, 0);
        assertThat(secondList).containsExactly(0, 0, 1);
        assertThat(thirdList).containsExactly(0, 0, 10);
        assertThat(fourthList).containsExactly(0, 0, 20);
    }

    @Test
    void healthReportHealthyEqUnhealthy() {
        HealthDescriptor healthDiscStub = GenerateHealthStub.generateHealthDescriptor(true, 9, 9);
        StaticAnalysisRun paramStub = generateParamStub();
        HealthSeriesBuilder b = new HealthSeriesBuilder(healthDiscStub);

        List<Integer> firstList = b.computeSeries(paramStub);
        List<Integer> secondList = b.computeSeries(paramStub);
        List<Integer> thirdList = b.computeSeries(paramStub);
        List<Integer> fourthList = b.computeSeries(paramStub);

        assertThat(firstList).containsExactly(0, 0, 0);
        assertThat(secondList).containsExactly(1, 0, 0);
        assertThat(thirdList).containsExactly(9, 0, 1);
        assertThat(fourthList).containsExactly(9, 0, 11);

    }
    @Test
    void healthReportHealthyOneLessUnhealthy() {
        HealthDescriptor healthDiscStub = GenerateHealthStub.generateHealthDescriptor(true, 9, 10);
        StaticAnalysisRun paramStub = generateParamStub();
        HealthSeriesBuilder b = new HealthSeriesBuilder(healthDiscStub);

        List<Integer> firstList = b.computeSeries(paramStub);
        List<Integer> secondList = b.computeSeries(paramStub);
        List<Integer> thirdList = b.computeSeries(paramStub);
        List<Integer> fourthList = b.computeSeries(paramStub);

        assertThat(firstList).containsExactly(0, 0, 0);
        assertThat(secondList).containsExactly(1, 0, 0);
        assertThat(thirdList).containsExactly(9, 1, 0);
        assertThat(fourthList).containsExactly(9, 1, 10);

    }
    @Test
    void healthReportMoreUnhealthyThanHealthy() {
        HealthDescriptor healthDiscStub = GenerateHealthStub.generateHealthDescriptor(true, 9, 15);
        StaticAnalysisRun paramStub = generateParamStub();
        HealthSeriesBuilder b = new HealthSeriesBuilder(healthDiscStub);

        List<Integer> firstList = b.computeSeries(paramStub);
        List<Integer> secondList = b.computeSeries(paramStub);
        List<Integer> thirdList = b.computeSeries(paramStub);
        List<Integer> fourthList = b.computeSeries(paramStub);

        assertThat(firstList).containsExactly(0, 0, 0);
        assertThat(secondList).containsExactly(1, 0, 0);
        assertThat(thirdList).containsExactly(9, 1, 0);
        assertThat(fourthList).containsExactly(9, 6, 5);

    }




}