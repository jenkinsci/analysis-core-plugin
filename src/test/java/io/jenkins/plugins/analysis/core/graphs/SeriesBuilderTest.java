package io.jenkins.plugins.analysis.core.graphs;

import java.util.ArrayList;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.jfree.data.category.CategoryDataset;
import org.junit.jupiter.api.Test;

import io.jenkins.plugins.analysis.core.quality.AnalysisBuild;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Verifies, that the abstract class SeriesBuilder works correctly.
 */
class SeriesBuilderTest {

    private static final long A_DAY_IN_MSEC = 24 * 3600 * 1000;

    /**
     * Verifies, that there is one DataSet for each of two days, when builds are done on different days. The datasets
     * contain the incrementing values, that are given from the inner class DummySeriesBuilder.
     */
    @Test
    void shouldBeTwoBuildsWithIncrementingValuesInTwoDaysWhenCreateDataSetPerDay() {
        GraphConfiguration graphConfigurationStub = stubGraphConfiguration(true);
        List<StaticAnalysisRun> staticAnalysisRuns = stubStaticAnalysRuns(0L, A_DAY_IN_MSEC);
        SeriesBuilder sut = new DummySeriesBuilder();

        CategoryDataset result = sut.createDataSet(graphConfigurationStub, staticAnalysisRuns);

        assertIncrementingValues(result, 2);
    }

    /**
     * Verifies, that there is one DataSet for one day, when a build are done on only one day. The dataset contain the
     * incrementing values, that are given from the inner class DummySeriesBuilder.
     */
    @Test
    void shouldBeOneBuildWithIncrementingValuesInOneDayWhenCreateDataSetPerDay() {
        GraphConfiguration graphConfigurationStub = stubGraphConfiguration(true);
        List<StaticAnalysisRun> staticAnalysisRuns = stubStaticAnalysRuns(0L);
        SeriesBuilder sut = new DummySeriesBuilder();

        CategoryDataset result = sut.createDataSet(graphConfigurationStub, staticAnalysisRuns);

        assertIncrementingValues(result, 1);
    }

    /**
     * Verifies, that there is no DataSet, when an empy list of builds is provided.
     */
    @Test
    void shouldBeZeroBuildsWhenCreateDataSetPerDay() {
        GraphConfiguration graphConfigurationStub = stubGraphConfiguration(true);
        List<StaticAnalysisRun> emptyList = new ArrayList<>();
        SeriesBuilder sut = new DummySeriesBuilder();

        CategoryDataset result = sut.createDataSet(graphConfigurationStub, emptyList);

        assertThat(result.getColumnCount()).isEqualTo(0);
    }

    /**
     * Verifies, that there is one DataSet, when two builds are done on the same day. The datasets contain the average
     * values of the two builds, that are given from the inner class DummySeriesBuilder.
     */
    @Test
    void shouldBeTwoBuildsInOneDayWithAverageValuesWhenCreateDataSetPerDay() {
        GraphConfiguration graphConfigurationStub = stubGraphConfiguration(true);
        List<StaticAnalysisRun> staticAnalysisRuns = stubStaticAnalysRuns(0L, 1L);
        SeriesBuilder sut = new DummySeriesBuilder();

        CategoryDataset result = sut.createDataSet(graphConfigurationStub, staticAnalysisRuns);

        assertAverageValues(result, 2);
    }

    /**
     * Verifies, that there are two DataSets, when two builds with different displayNames and numbers are provided. The
     * datasets contain the incrementing values, that are given from the inner class DummySeriesBuilder.
     */
    @Test
    void shouldBeTwoBuildsWithIncrementingValuesWhenCreateTestDataSetPerBuildNumber() {
        GraphConfiguration graphConfigurationStub = stubGraphConfiguration(false);
        List<StaticAnalysisRun> staticAnalysisRuns = stubStaticAnalysRuns(0L, 0L);
        SeriesBuilder sut = new DummySeriesBuilder();

        CategoryDataset result = sut.createDataSet(graphConfigurationStub, staticAnalysisRuns);

        assertIncrementingValues(result, 2);
    }

    /**
     * Verifies, that there is one DataSet, when one build is provided. The datasets contain the incrementing values,
     * that are given from the inner class DummySeriesBuilder.
     */
    @Test
    void shouldBeOneBuildsWithIncrementingValuesWhenCreateTestDataSetPerBuildNumber() {
        GraphConfiguration graphConfigurationStub = stubGraphConfiguration(false);
        List<StaticAnalysisRun> staticAnalysisRuns = stubStaticAnalysRuns(0L);
        SeriesBuilder sut = new DummySeriesBuilder();

        CategoryDataset result = sut.createDataSet(graphConfigurationStub, staticAnalysisRuns);

        assertIncrementingValues(result, 1);
    }

    /**
     * Verifies, that there is no DataSet, when an empty list of builds is provided.
     */
    @Test
    void shouldBeZeroBuildsWhenCreateTestDataSetPerBuildNumber() {
        GraphConfiguration graphConfigurationStub = stubGraphConfiguration(false);
        List<StaticAnalysisRun> emptyList = new ArrayList<>();
        SeriesBuilder sut = new DummySeriesBuilder();

        CategoryDataset result = sut.createDataSet(graphConfigurationStub, emptyList);

        assertThat(result.getColumnCount()).isEqualTo(0);
    }

    /**
     * Verifies, that there is no DataSet, when one build is provided, that is too old.
     */
    @Test
    void shouldBeZeroBuildsWithBuildTooOld() {
        GraphConfiguration graphConfigurationStub = stubGraphConfiguration(false);
        List<StaticAnalysisRun> builds = stubStaticAnalysRuns(0L);
        ResultTime resultTimeStub = stubResultTime(true);
        SeriesBuilder sut = new DummySeriesBuilder(resultTimeStub);

        CategoryDataset result = sut.createDataSet(graphConfigurationStub, builds);

        assertThat(result.getColumnCount()).isEqualTo(0);
    }

    /**
     * Verifies, that there is one DataSet, when two builds are provided, but the build count is only one. The dataset
     * contain the incrementing values, that are given from the inner class DummySeriesBuilder.
     */
    @Test
    void shouldBeOneBuildWhenTwoBuildsAreDoneButBuildCountIsOne() {
        GraphConfiguration graphConfigurationStub = stubGraphConfiguration(false);
        when(graphConfigurationStub.isBuildCountDefined()).thenReturn(true);
        when(graphConfigurationStub.getBuildCount()).thenReturn(1);
        List<StaticAnalysisRun> builds = stubStaticAnalysRuns(0L, A_DAY_IN_MSEC);
        SeriesBuilder sut = new DummySeriesBuilder();

        CategoryDataset result = sut.createDataSet(graphConfigurationStub, builds);

        assertIncrementingValues(result, 1);
    }

    /**
     * Verifies, that there are two DataSets, when two builds are provided and the build count is two. The dataset
     * contains the incrementing values, that are given from the inner class DummySeriesBuilder.
     */
    @Test
    void shouldBeTwoBuildsWhenTwoBuildsAreDoneWithBuildCountIsTwo() {
        GraphConfiguration graphConfigurationStub = stubGraphConfiguration(false);
        when(graphConfigurationStub.isBuildCountDefined()).thenReturn(true);
        when(graphConfigurationStub.getBuildCount()).thenReturn(2);
        List<StaticAnalysisRun> builds = stubStaticAnalysRuns(0L, A_DAY_IN_MSEC);
        SeriesBuilder sut = new DummySeriesBuilder();

        CategoryDataset result = sut.createDataSet(graphConfigurationStub, builds);

        assertIncrementingValues(result, 2);
    }


    /**
     * Gives a stub for GraphConfiguration.
     *
     * @param useBuildDateAsDomain
     *         the value, the stub should be configured with.
     *
     * @return a stub for GraphConfiguration
     */
    private GraphConfiguration stubGraphConfiguration(final boolean useBuildDateAsDomain) {
        GraphConfiguration graphConfigurationStub = mock(GraphConfiguration.class);
        when(graphConfigurationStub.useBuildDateAsDomain()).thenReturn(useBuildDateAsDomain);
        return graphConfigurationStub;
    }

    /**
     * Gives a List of stubs for StaticAnalysisRun. One stub is added to the list per buildTimeStamp provided.
     *
     * @param buildTimeStamps
     *         an array of timeStamps, the stubs are supposed to be configured to.
     *
     * @return a list of stubs for StaticAnalysisRun
     */
    private List<StaticAnalysisRun> stubStaticAnalysRuns(final long... buildTimeStamps) {
        List<StaticAnalysisRun> staticAnalysisRuns = new ArrayList<>();
        int buildCount = 1;
        for (long currentTimeStamp : buildTimeStamps) {
            StaticAnalysisRun staticAnalysisRunStub = mock(StaticAnalysisRun.class);
            AnalysisBuild build = mock(AnalysisBuild.class);
            when(build.getTimeInMillis()).thenReturn(currentTimeStamp);
            when(build.getDisplayName()).thenReturn("Build" + Integer.toString(buildCount));
            when(build.getNumber()).thenReturn(buildCount);
            when(staticAnalysisRunStub.getBuild()).thenReturn(build);
            staticAnalysisRuns.add(staticAnalysisRunStub);
            buildCount++;
        }
        return staticAnalysisRuns;
    }

    /**
     * Gives a stub of ResutTime. The stub returns the given bool params in the order they are given.
     *
     * @param tooOld
     *         array of booleans, that are to be returned from the stub by areResultsTooOld.
     *
     * @return the configured stub of ResultTime
     */
    private ResultTime stubResultTime(boolean... tooOld) {
        ResultTime resultTimeStub = mock(ResultTime.class);
        for (boolean current : tooOld) {
            when(resultTimeStub.areResultsTooOld(any(), any())).thenReturn(current);
        }
        return resultTimeStub;
    }

    /**
     * Verifies, that the values of several DataSets are correct. The DataSets should contain the incrementing values,
     * provided by the dummy implementation of computeSeries of DummySeriesBuilder.
     */
    private void assertIncrementingValues(CategoryDataset result, int columnCount) {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(result.getColumnCount()).isEqualTo(columnCount);
        int expectedValue = 1;
        for (int column = 0; column < columnCount; ++column) {
            softly.assertThat(result.getValue(0, column)).isEqualTo(expectedValue++);
            softly.assertThat(result.getValue(1, column)).isEqualTo(expectedValue++);
            softly.assertThat(result.getValue(2, column)).isEqualTo(expectedValue++);
        }
        softly.assertAll();
    }

    /**
     * Verifies, that the given CategoryDataset contains one DataSet as column. The DataSet is supposed to contain the
     * average values over several builds. As the builds contain incrementing values, the average is defined by the
     * number of builds.
     *
     * @param categoryDataset
     *         the DataSet, which should contain the average of one or more builds.
     * @param buildCount
     *         the number of builds, that contribute to the average.
     */
    private void assertAverageValues(final CategoryDataset categoryDataset, final int buildCount) {
        SoftAssertions softly = new SoftAssertions();
        // starting values of the first build.
        int firstExpected = 1;
        int secondExpected = 2;
        int thirdExpected = 3;
        for (int i = 1; i < buildCount; ++i) {
            // as every build contains three values which each increment, this number is the adding value per build.
            int incrementPerTurn = i * 3;
            firstExpected += (incrementPerTurn + 1);
            secondExpected += (incrementPerTurn + 2);
            thirdExpected += (incrementPerTurn + 3);
        }
        softly.assertThat(categoryDataset.getValue(0, 0)).isEqualTo(firstExpected / buildCount);
        softly.assertThat(categoryDataset.getValue(1, 0)).isEqualTo(secondExpected / buildCount);
        softly.assertThat(categoryDataset.getValue(2, 0)).isEqualTo(thirdExpected / buildCount);
        softly.assertAll();
    }

    /**
     * Dummy implementation of SeriesBuilder, just for testing. This implementation just overrides computeSeries, where
     * it produces dummy values. The dummy values are just imcremented by 1 with each call, starting from 1. So the
     * first serie will be (1,2,3), the second will be (4,5,6) and so on.
     */
    private class DummySeriesBuilder extends SeriesBuilder {

        /**
         * Ctor for tests, where the result time should be specified.
         *
         * @param resultTime
         *         the result time, which describes, when a build is too old.
         */
        private DummySeriesBuilder(ResultTime resultTime) {
            super(resultTime);
        }

        /**
         * Ctor for tests, where no result time is used.
         */
        private DummySeriesBuilder() {
            this(new ResultTime());
        }

        private int count = 1;

        /**
         * Provides dummy data for testing.
         *
         * @param current
         *         the current build result
         *
         * @return a list with incrementing values, starting with one. So first list will be (1,2,3), second one
         *         (4,5,6).
         */
        @Override
        protected List<Integer> computeSeries(final StaticAnalysisRun current) {
            List<Integer> dummyInts = new ArrayList<>();
            dummyInts.add(count++);
            dummyInts.add(count++);
            dummyInts.add(count++);
            return dummyInts;
        }
    }


}