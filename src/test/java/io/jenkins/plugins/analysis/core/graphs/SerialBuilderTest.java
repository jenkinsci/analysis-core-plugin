package io.jenkins.plugins.analysis.core.graphs;


import java.util.ArrayList;

import java.util.Calendar;
import java.util.GregorianCalendar;


import org.jfree.data.category.CategoryDataset;
import org.junit.jupiter.api.Test;

import io.jenkins.plugins.analysis.core.quality.AnalysisBuild;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Test Class for SerialBuilder.
 */
class SerialBuilderTest {


    private static int buildCounter = 0;

    /**
     * generate list of mocks.
     *
     * @param buildTime
     *         (day passed ,Size)*x
     */
    private ArrayList<StaticAnalysisRun> getIterator(long... buildTime) {
        final int dayMS = 24 * 3600 * 1000;
        ArrayList<StaticAnalysisRun> list = new ArrayList<>();
        for (int i = 0; i < buildTime.length; i += 2) {
            StaticAnalysisRun stub = mock(StaticAnalysisRun.class);

            Calendar today = new GregorianCalendar();
            AnalysisBuild build = mock(AnalysisBuild.class);
            when(build.getNumber()).thenReturn(buildTime.length - i / 2);

            when(build.getDisplayName()).thenReturn("magic" + buildCounter++);
            when(build.getTimeInMillis()).thenReturn(today.getTimeInMillis() - buildTime[i] * dayMS);
            when(stub.getBuild()).thenReturn(build);

            when(stub.getTotalSize()).thenReturn((int) buildTime[i + 1]);
            list.add(stub);
        }


        return list;
    }

    private void check(CategoryDataset data, long healthy, long unHealthy, long... list) {
        assertThat(data.getColumnCount()).isEqualTo(list.length);
        for (int i = 0; i < list.length; i++) {
            long numberOfUnhealthy = list[list.length - i - 1] - unHealthy;
            if (numberOfUnhealthy < 0) {
                numberOfUnhealthy = 0;
            }
            assertThat(data.getValue(0, i).longValue()).isBetween((long) 0, healthy);
            assertThat(data.getValue(1, i).longValue()).isBetween((long) 0, unHealthy - healthy);
            assertThat(data.getValue(2, i).longValue()).isBetween((long) 0, numberOfUnhealthy);
        }
    }

    private GraphConfiguration generateGraphConfiguration(boolean asDomain, int dayCount, int buildCount) {
        GraphConfiguration stub = mock(GraphConfiguration.class);
        when(stub.useBuildDateAsDomain()).thenReturn(asDomain);
        when(stub.getDayCount()).thenReturn(dayCount);
        when(stub.isBuildCountDefined()).thenReturn(buildCount > 0);
        when(stub.getBuildCount()).thenReturn(buildCount);
        when(stub.isDayCountDefined()).thenReturn(dayCount > 0);
        return stub;
    }

    @Test
    void dataSetWithActiveDayCountAndBuildNotAsDomain() {
        GraphConfiguration stub = generateGraphConfiguration(false, 10, 0);

        HealthSeriesBuilder b = new HealthSeriesBuilder(GenerateHealthStub.generateHealthDescriptor(true, 5, 8));
        long[] values = {1, 0, 2, 8, 3, 10, 4, 16, 20, 99};
        long[] newValues = {0, 8, 10, 16};
        CategoryDataset t = b.createDataSet(stub, getIterator(values));
        check(t, 5, 8, newValues);

    }

    @Test
    void dataSetWithBuildNotAsDomainAndWithBuildCount() {
        GraphConfiguration stub = generateGraphConfiguration(false, 0, 3);

        HealthSeriesBuilder b = new HealthSeriesBuilder(GenerateHealthStub.generateHealthDescriptor(true, 10, 20));
        long[] values = {1, 0, 2, 8, 3, 10, 4, 16, 20, 99};
        long[] newValues = {0, 8, 10};
        CategoryDataset t = b.createDataSet(stub, getIterator(values));
        check(t, 10, 20, newValues);

    }

    @Test
    void dataSetWithBuildNotAsDomainAndWithBuildCountAndDayCount() {
        GraphConfiguration stub = generateGraphConfiguration(false, 10, 3);

        HealthSeriesBuilder b = new HealthSeriesBuilder(GenerateHealthStub.generateHealthDescriptor(true, 5, 8));
        long[] values = {1, 0, 2, 8, 3, 10, 4, 16, 20, 99};
        long[] newValues = {0, 8, 10};
        CategoryDataset t = b.createDataSet(stub, getIterator(values));
        check(t, 5, 8, newValues);

    }

    @Test
    void dataSetWithNoBuilds() {
        GraphConfiguration stub = generateGraphConfiguration(false, 0, 0);
        HealthSeriesBuilder b = new HealthSeriesBuilder(GenerateHealthStub.generateHealthDescriptor(true, 5, 8));
        long[] values = {};
        long[] newValues = {};
        CategoryDataset t = b.createDataSet(stub, getIterator(values));
        check(t, 5, 8, newValues);

    }


    @Test
    void dataSetAsDomainAndNoBuildLimitations() {
        GraphConfiguration stub = generateGraphConfiguration(true, 0, 0);
        HealthSeriesBuilder b = new HealthSeriesBuilder(GenerateHealthStub.generateHealthDescriptor(true, 5, 8));
        long[] values = {1, 10, 1, 16, 4, 20, 4, 22, 4, 24, 7, 50};
        long[] newValues = {13, 23, 50};
        CategoryDataset t = b.createDataSet(stub, getIterator(values));
        check(t, 5, 8, newValues);
    }

    @Test
    void dataSetAsDomainAndBuildLimitation() {
        GraphConfiguration stub = generateGraphConfiguration(true, 0, 4);
        HealthSeriesBuilder b = new HealthSeriesBuilder(GenerateHealthStub.generateHealthDescriptor(true, 5, 8));
        long[] values = {1, 10, 1, 16, 4, 20, 4, 22, 4, 24, 7, 50};
        long[] newValues = {13, 21};
        CategoryDataset t = b.createDataSet(stub, getIterator(values));
        check(t, 5, 8, newValues);
    }

    @Test
    void dataSetAsDomainAndTimeLimitation() {
        GraphConfiguration stub = generateGraphConfiguration(true, 3, 0);
        HealthSeriesBuilder b = new HealthSeriesBuilder(GenerateHealthStub.generateHealthDescriptor(true, 5, 8));
        long[] values = {1, 10, 1, 16, 4, 20, 4, 22, 4, 24, 7, 50};
        long[] newValues = {13};
        CategoryDataset t = b.createDataSet(stub, getIterator(values));
        check(t, 5, 8, newValues);
    }

    @Test
    void dataSetAsDomainNoBuilds() {
        GraphConfiguration stub = generateGraphConfiguration(true, 4, 5);
        HealthSeriesBuilder b = new HealthSeriesBuilder(GenerateHealthStub.generateHealthDescriptor(true, 5, 8));
        long[] values = {};
        long[] newValues = {};
        CategoryDataset t = b.createDataSet(stub, getIterator(values));
        check(t, 5, 8, newValues);
    }

}
