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
class HealthSeriesBuilderTest extends SerialBuilderTest{


    /**
     * generate a StaticAnalysisRun stub.
     * the stub returns for the method getTotalSize 0,1,10,20
     *
     * @return stub StaticAnalysisRun
     */
    private StaticAnalysisRun generateStaticAnalysisRunStub() {
        return generateStaticAnalysisRunStub(0, 1, 10, 20);
    }

    /**
     * generate a StaticAnalysisRun stub.
     * the stub returns for the method getTotalSize the given values
     *
     * @param firstSize  first return value of getTotalSize
     * @param additionalSize list of Integers with additional getTotalSize
     * @return stub StaticAnalysisRun
     */
    private StaticAnalysisRun generateStaticAnalysisRunStub(int firstSize, Integer... additionalSize) {
        StaticAnalysisRun paramStub = mock(StaticAnalysisRun.class);
        when(paramStub.getTotalSize()).thenReturn(firstSize, additionalSize);
        return paramStub;
    }

    /**
     * computeSeries with not enabled Health Descriptor.
     * @param healthy number of healthy
     * @param unhealthy number of unhealthy
     */
    private void shouldFindSingleResultsFor(int healthy, int unhealthy){
        HealthDescriptor healthDiscStub = generateHealthDescriptor(false,healthy,unhealthy);
        StaticAnalysisRun paramStub = generateStaticAnalysisRunStub();
        HealthSeriesBuilder b = new HealthSeriesBuilder(healthDiscStub);

        assertThat(b.computeSeries(paramStub)).containsExactly(0);
        assertThat(b.computeSeries(paramStub)).containsExactly(1);
        assertThat(b.computeSeries(paramStub)).containsExactly(10);
        assertThat(b.computeSeries(paramStub)).containsExactly(20);
    }
    @Test
    void healthReportWithDisabledHealthDescriptor() {
        shouldFindSingleResultsFor(0,0);
    }

    @Test
    void healthReportWithDisabledHealthDescriptorAndHealthyDefinition() {
        shouldFindSingleResultsFor(9,10);

    }

    @Test
    void healthReportWithNoPreviousHeath() {
        HealthDescriptor healthDiscStub = generateHealthDescriptor(true, 0, 0);
        StaticAnalysisRun paramStub = generateStaticAnalysisRunStub();
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
        HealthDescriptor healthDiscStub = generateHealthDescriptor(true, 8, 8);
        StaticAnalysisRun paramStub = generateStaticAnalysisRunStub();
        HealthSeriesBuilder b = new HealthSeriesBuilder(healthDiscStub);

        List<Integer> firstList = b.computeSeries(paramStub);
        List<Integer> secondList = b.computeSeries(paramStub);
        List<Integer> thirdList = b.computeSeries(paramStub);
        List<Integer> fourthList = b.computeSeries(paramStub);

        assertThat(firstList).containsExactly(0, 0, 0);
        assertThat(secondList).containsExactly(1, 0, 0);
        assertThat(thirdList).containsExactly(8, 0, 2);
        assertThat(fourthList).containsExactly(8, 0, 12);

    }

    @Test
    void healthReportWithUnhealthyBelowBorder() {
        HealthDescriptor healthDiscStub = generateHealthDescriptor(true, 8, 9);
        StaticAnalysisRun paramStub = generateStaticAnalysisRunStub();
        HealthSeriesBuilder b = new HealthSeriesBuilder(healthDiscStub);

        List<Integer> firstList = b.computeSeries(paramStub);
        List<Integer> secondList = b.computeSeries(paramStub);
        List<Integer> thirdList = b.computeSeries(paramStub);
        List<Integer> fourthList = b.computeSeries(paramStub);

        assertThat(firstList).containsExactly(0, 0, 0);
        assertThat(secondList).containsExactly(1, 0, 0);
        assertThat(thirdList).containsExactly(8, 1, 1);
        assertThat(fourthList).containsExactly(8, 1, 11);

    }

    @Test
    void healthReportUnHealthyOnBorder() {
        HealthDescriptor healthDiscStub = generateHealthDescriptor(true, 9, 10);
        StaticAnalysisRun paramStub = generateStaticAnalysisRunStub();
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
    void healthReportUnhealthyOverBorder() {
        HealthDescriptor healthDiscStub = generateHealthDescriptor(true, 9, 11);
        StaticAnalysisRun paramStub = generateStaticAnalysisRunStub();
        HealthSeriesBuilder b = new HealthSeriesBuilder(healthDiscStub);

        List<Integer> firstList = b.computeSeries(paramStub);
        List<Integer> secondList = b.computeSeries(paramStub);
        List<Integer> thirdList = b.computeSeries(paramStub);
        List<Integer> fourthList = b.computeSeries(paramStub);

        assertThat(firstList).containsExactly(0, 0, 0);
        assertThat(secondList).containsExactly(1, 0, 0);
        assertThat(thirdList).containsExactly(9, 1, 0);
        assertThat(fourthList).containsExactly(9, 2, 9);

    }
    @Test
    void healthReportHealthyOnBorder() {
        HealthDescriptor healthDiscStub = generateHealthDescriptor(true, 10, 13);
        StaticAnalysisRun paramStub = generateStaticAnalysisRunStub();
        HealthSeriesBuilder b = new HealthSeriesBuilder(healthDiscStub);

        List<Integer> firstList = b.computeSeries(paramStub);
        List<Integer> secondList = b.computeSeries(paramStub);
        List<Integer> thirdList = b.computeSeries(paramStub);
        List<Integer> fourthList = b.computeSeries(paramStub);

        assertThat(firstList).containsExactly(0, 0, 0);
        assertThat(secondList).containsExactly(1, 0, 0);
        assertThat(thirdList).containsExactly(10, 0, 0);
        assertThat(fourthList).containsExactly(10, 3, 7);

    }
    @Test
    void healthReportHealthyOverBorder() {
        HealthDescriptor healthDiscStub = generateHealthDescriptor(true, 11, 13);
        StaticAnalysisRun paramStub = generateStaticAnalysisRunStub();
        HealthSeriesBuilder b = new HealthSeriesBuilder(healthDiscStub);

        List<Integer> firstList = b.computeSeries(paramStub);
        List<Integer> secondList = b.computeSeries(paramStub);
        List<Integer> thirdList = b.computeSeries(paramStub);
        List<Integer> fourthList = b.computeSeries(paramStub);

        assertThat(firstList).containsExactly(0, 0, 0);
        assertThat(secondList).containsExactly(1, 0, 0);
        assertThat(thirdList).containsExactly(10, 0, 0);
        assertThat(fourthList).containsExactly(11, 2, 7);

    }
    @Test
    void GetFirstReminderBoarder() {
        HealthDescriptor healthDiscStub = generateHealthDescriptor(true, 1, 0);
        StaticAnalysisRun paramStub = mock(StaticAnalysisRun.class);
        when(paramStub.getTotalSize()).thenReturn(1);
        HealthSeriesBuilder b = new HealthSeriesBuilder(healthDiscStub);
        List<Integer> first = b.computeSeries(paramStub);

        assertThat(first).containsExactly(1, 0, 0);
        assertThat(first).hasSize(3);
    }


}