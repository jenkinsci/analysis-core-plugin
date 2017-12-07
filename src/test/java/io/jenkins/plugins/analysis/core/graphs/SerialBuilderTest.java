package io.jenkins.plugins.analysis.core.graphs;


import java.util.ArrayList;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;

import org.apache.commons.lang3.ArrayUtils;
import org.jfree.data.category.CategoryDataset;
import org.junit.jupiter.api.Test;

import io.jenkins.plugins.analysis.core.quality.AnalysisBuild;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Test Class for SerialBuilder.
 */
abstract class SerialBuilderTest {
    /**
     * Generate a stub HealthDescriptor.
     *
     * @param enabled
     *         stub return this on isEnabled
     * @param healthy
     *         stub return this on getHealthy
     * @param unhealthy
     *         stub return this on getUnHealthy
     *
     * @return stub HealthDescriptor
     */
    static HealthDescriptor generateHealthDescriptor(boolean enabled, int healthy, int unhealthy) {
        HealthDescriptor healthDiscStub = mock(HealthDescriptor.class);
        when(healthDiscStub.isEnabled()).thenReturn(enabled);
        when(healthDiscStub.getHealthy()).thenReturn(healthy);
        when(healthDiscStub.getUnHealthy()).thenReturn(unhealthy);
        return healthDiscStub;
    }
    /**
     * Static count of the number of builds
     */
    private static int buildCounter = 0;

    /**
     * generate list of mocks.
     *
     * @param passedDays  list of the passwd days one per build
     * @param sizeOfBuild list of the size of each build
     * @return list of StaticAnalysisRun
     */
    private ArrayList<StaticAnalysisRun> getIterator(List<Integer> passedDays, List<Integer> sizeOfBuild) {
        if (passedDays.size() != sizeOfBuild.size()) {
            throw new IllegalArgumentException("Exepect, that SerialBuilderTest.getIterator() gets two list with the same size");
        }
        final int dayMS = 24 * 3600 * 1000;
        ArrayList<StaticAnalysisRun> list = new ArrayList<>();
        for (int i = 0; i < passedDays.size(); i++) {
            StaticAnalysisRun stub = mock(StaticAnalysisRun.class);

            Calendar today = new GregorianCalendar();
            AnalysisBuild build = mock(AnalysisBuild.class);
            when(build.getNumber()).thenReturn(passedDays.size() - i);

            when(build.getDisplayName()).thenReturn("magic" + (buildCounter += 2)); // some build are removed
            when(build.getTimeInMillis()).thenReturn(today.getTimeInMillis() - passedDays.get(i) * dayMS);
            when(stub.getBuild()).thenReturn(build);

            when(stub.getTotalSize()).thenReturn((int) sizeOfBuild.get(i));
            list.add(stub);
        }


        return list;
    }

    /**
     * Build a ArrayList with the given values.
     * @param parameter list of ints to add
     * @return arrayList with the values
     */
    private ArrayList<Integer> buildSet(int... parameter) {
        ArrayList<Integer> list = new ArrayList<>();
        for (int value : parameter) {
            list.add(value);
        }
        return list;
    }

    /**
     * Check if all values in dataset are correct.
     * @param data dataSet
     * @param healthy healthy value
     * @param unHealthy unhelaty value
     * @param list list of expected values
     */
    private void checkDataSet(CategoryDataset data, int healthy, int unHealthy, int... list) {
        String assertErrorMessage = "\nCurrent Build is the <%d> failed to check:";
        ArrayUtils.reverse(list);
        assertThat(data.getColumnCount()).isEqualTo(list.length);
        for (int i = 0; i < list.length; i++) {
            StaticAnalysisRun stub = mock(StaticAnalysisRun.class);
            when(stub.getTotalSize()).thenReturn(list[i]);
            List<Integer> result = new HealthSeriesBuilder(generateHealthDescriptor(true, healthy, unHealthy))
                    .computeSeries(stub);

            assertThat(data.getValue(0, i)).as(assertErrorMessage + " <healthy>\n", i)
                    .isEqualTo(result.get(0));
            assertThat(data.getValue(1, i)).as(assertErrorMessage + " <medium healthy>\n", i)
                    .isEqualTo(result.get(1));
            assertThat(data.getValue(2, i)).as(assertErrorMessage + " <unhealthy>\n", i)
                    .isEqualTo(result.get(2));
        }
    }

    /**
     * generate a GraphConfiguration stub.
     * @param asDomain should build as Domain
     * @param dayCount number of dayCount if 0 the daycount is disabled
     * @param buildCount number of build of 0 build count is disabled
     * @return GraphConfiguration stub
     */
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

        HealthSeriesBuilder b = new HealthSeriesBuilder(generateHealthDescriptor(true, 5, 8));
        ArrayList<Integer> days = buildSet(1,2,3,4,10,11);
        ArrayList<Integer> buildSize = buildSet(0,8,10,16,20,99);
        int[] newValues = {0, 8, 10, 16,20};
        CategoryDataset t = b.createDataSet(stub, getIterator(days,buildSize));
        checkDataSet(t, 5, 8, newValues);

    }

    @Test
    void dataSetWithBuildNotAsDomainAndWithBuildCount() {
        GraphConfiguration stub = generateGraphConfiguration(false, 0, 3);

        HealthSeriesBuilder b = new HealthSeriesBuilder(generateHealthDescriptor(true, 10, 20));
        ArrayList<Integer> days = buildSet(1,2,3,4,11);
        ArrayList<Integer> buildSize = buildSet(0,8,10,16,99);
        int[] newValues = {0, 8, 10};
        CategoryDataset t = b.createDataSet(stub, getIterator(days,buildSize));
        checkDataSet(t, 10, 20, newValues);

    }

    @Test
    void dataSetWithBuildNotAsDomainAndWithBuildCountAndDayCount() {
        GraphConfiguration stub = generateGraphConfiguration(false, 10, 3);

        HealthSeriesBuilder b = new HealthSeriesBuilder(generateHealthDescriptor(true, 5, 8));
        ArrayList<Integer> days = buildSet(1,2,3,4,20);
        ArrayList<Integer> buildSize = buildSet(0,8,10,16,99);
        int[] newValues = {0, 8, 10};
        CategoryDataset t = b.createDataSet(stub, getIterator(days,buildSize));
        checkDataSet(t, 5, 8, newValues);

    }

    @Test
    void dataSetWithNoBuilds() {
        GraphConfiguration stub = generateGraphConfiguration(false, 0, 0);
        HealthSeriesBuilder b = new HealthSeriesBuilder(generateHealthDescriptor(true, 5, 8));
        ArrayList<Integer> days = buildSet();
        ArrayList<Integer> buildSize = buildSet();
        int[] newValues = {};
        CategoryDataset t = b.createDataSet(stub, getIterator(days,buildSize));
        checkDataSet(t, 5, 8, newValues);

    }


    @Test
    void dataSetAsDomainAndNoBuildLimitations() {
        GraphConfiguration stub = generateGraphConfiguration(true, 0, 0);
        HealthSeriesBuilder b = new HealthSeriesBuilder(generateHealthDescriptor(true, 5, 8));
        ArrayList<Integer> days = buildSet(1,1,4,4,4,7);
        ArrayList<Integer> buildSize = buildSet(10,16,20,22,24,50);
        int[] newValues = {13, 22, 50};
        CategoryDataset t = b.createDataSet(stub, getIterator(days,buildSize));
        checkDataSet(t, 5, 8, newValues);
    }

    @Test
    void dataSetAsDomainAndBuildLimitation() {
        GraphConfiguration stub = generateGraphConfiguration(true, 0, 4);
        HealthSeriesBuilder b = new HealthSeriesBuilder(generateHealthDescriptor(true, 5, 8));
        long[] values = {1, 10, 1, 16, 4, 20, 4, 22, 4, 24, 7, 50};
        ArrayList<Integer> days = buildSet(1,1,4,4,4,7);
        ArrayList<Integer> buildSize = buildSet(10,16,20,22,24,50);
        int[] newValues = {13, 21};
        CategoryDataset t = b.createDataSet(stub, getIterator(days,buildSize));
        checkDataSet(t, 5, 8, newValues);
    }

    @Test
    void dataSetAsDomainAndTimeLimitation() {
        GraphConfiguration stub = generateGraphConfiguration(true, 3, 0);
        HealthSeriesBuilder b = new HealthSeriesBuilder(generateHealthDescriptor(true, 5, 8));
        ArrayList<Integer> days = buildSet(1,1,4,4,4,7);
        ArrayList<Integer> buildSize = buildSet(10,16,20,22,24,50);
        int[] newValues = {13};
        CategoryDataset t = b.createDataSet(stub, getIterator(days,buildSize));
        checkDataSet(t, 5, 8, newValues);
    }

    @Test
    void dataSetAsDomainNoBuilds() {
        GraphConfiguration stub = generateGraphConfiguration(true, 4, 5);
        HealthSeriesBuilder b = new HealthSeriesBuilder(generateHealthDescriptor(true, 5, 8));
        ArrayList<Integer> days = buildSet();
        ArrayList<Integer> buildSize = buildSet();
        int[] newValues = {};
        CategoryDataset t = b.createDataSet(stub, getIterator(days,buildSize));
        checkDataSet(t, 5, 8, newValues);
    }

    @Test
    public void dataSetAsDomainAndNotTimeLimitationButSetInverse() {
        GraphConfiguration stub = generateGraphConfiguration(true, 3, 0);
        HealthSeriesBuilder b = new HealthSeriesBuilder(generateHealthDescriptor(true, 5, 8));
        ArrayList<Integer> days = buildSet(7,4,4,4,1,1);
        ArrayList<Integer> buildSize = buildSet(50,24,22,20,16,10);
        int[] newValues = {};
        CategoryDataset t = b.createDataSet(stub, getIterator(days,buildSize));
        checkDataSet(t, 5, 8, newValues);
    }


    @Test
    public void dataSetAsDomainAndTimeLimitationWithTimeBoundary() {
        GraphConfiguration stub = generateGraphConfiguration(true, 3, 0);
        HealthSeriesBuilder b = new HealthSeriesBuilder(generateHealthDescriptor(true, 5, 8));
        ArrayList<Integer> days = buildSet(1,1,3,3,3,7);
        ArrayList<Integer> buildSize = buildSet(10,16,20,22,24,50);
        int[] newValues = {13, 22};
        CategoryDataset t = b.createDataSet(stub, getIterator(days,buildSize));
        checkDataSet(t, 5, 8, newValues);
    }

}
