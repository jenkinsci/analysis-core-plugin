package io.jenkins.plugins.analysis.core.graphs;


import java.util.Calendar;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.jfree.data.category.CategoryDataset;
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
 *
 * @author Andreas Moser
 */
class HealthSeriesBuilderTest {
    private static final long MILLIS_OF_DAY = 1000 * 60 * 60 * 24;

    /** Verifies that an empty list of builds creates no data. */
    @Test
    void shouldCreateEmptyDataSetForEmptyIterator() {
        HealthSeriesBuilder builder = new HealthSeriesBuilder(new HealthDescriptor());
        GraphConfiguration configurationBuildDateAsDomain = createConfiguration();
        when(configurationBuildDateAsDomain.useBuildDateAsDomain()).thenReturn(true);

        CategoryDataset dataSet = builder.createDataSet(createConfiguration(), Lists.newArrayList());
        CategoryDataset dataSetBuildDateAsDomain = builder.createDataSet(configurationBuildDateAsDomain, Lists.newArrayList());

        assertThat(dataSet.getColumnCount()).isEqualTo(0);
        assertThat(dataSet.getRowCount()).isEqualTo(0);
        assertThat(dataSetBuildDateAsDomain.getColumnCount()).isEqualTo(0);
        assertThat(dataSetBuildDateAsDomain.getRowCount()).isEqualTo(0);
    }

    /** Verifies data set is empty for too old build result. */
    @Test
    void shouldCreateEmptyDataSetForTooOldBuild() {
        HealthSeriesBuilder builder = new HealthSeriesBuilder(new HealthDescriptor());
        GraphConfiguration configuration = createConfiguration();
        when(configuration.isDayCountDefined()).thenReturn(true);
        when(configuration.getDayCount()).thenReturn(0);
        StaticAnalysisRun resultWithTooOldBuild = createBuildResult(1, Calendar.getInstance().getTimeInMillis() - MILLIS_OF_DAY, 1, 2, 3);

        CategoryDataset dataSetTooOldBuild = builder.createDataSet(configuration, Lists.newArrayList(resultWithTooOldBuild));

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(dataSetTooOldBuild.getColumnCount()).isEqualTo(0);
        softly.assertThat(dataSetTooOldBuild.getRowCount()).isEqualTo(0);
        softly.assertAll();
    }

    /** Verifies data set with one build result and build count is set to 1. */
    @Test
    void createDataSetWithOneResultBuildCount1() {
        HealthSeriesBuilder builder = new HealthSeriesBuilder(new HealthDescriptor());
        GraphConfiguration configurationBuildCount0 = createConfiguration();
        when(configurationBuildCount0.isBuildCountDefined()).thenReturn(true);
        when(configurationBuildCount0.getBuildCount()).thenReturn(1);
        StaticAnalysisRun resultWithValidBuild = createBuildResult(1, 2, 2, 3);

        CategoryDataset dataSet = builder.createDataSet(configurationBuildCount0, Lists.newArrayList(resultWithValidBuild));

        assertDataSet1Column1Row(dataSet, "#1", 7);
    }

    /** Verifies data set with one build result and build count is set to 2. */
    @Test
    void createDataSetWithOneResultBuildCount2() {
        HealthSeriesBuilder builder = new HealthSeriesBuilder(new HealthDescriptor());
        GraphConfiguration configurationBuildCount2 = createConfiguration();
        when(configurationBuildCount2.isBuildCountDefined()).thenReturn(true);
        when(configurationBuildCount2.getBuildCount()).thenReturn(2);
        StaticAnalysisRun resultWithValidBuild = createBuildResult(1, 4, 3, 3);

        CategoryDataset dataSet = builder.createDataSet(configurationBuildCount2, Lists.newArrayList(resultWithValidBuild));

        assertDataSet1Column1Row(dataSet, "#1", 10);
    }

    /** Verifies data set with one build result and build count disabled. */
    @Test
    void createDataSetWithOneResultNoBuildCount() {
        HealthSeriesBuilder builder = new HealthSeriesBuilder(new HealthDescriptor());
        GraphConfiguration configurationBuildCountFalse = createConfiguration();
        StaticAnalysisRun resultWithValidBuild = createBuildResult(1, 1, 2, 3);

        CategoryDataset dataSet = builder.createDataSet(configurationBuildCountFalse, Lists.newArrayList(resultWithValidBuild));

        assertDataSet1Column1Row(dataSet, "#1", 6);
    }

    /** Verifies data set with two build results, build count is disabled and one build is too old. */
    @Test
    void createDataSetWithTwoResultsButOneTooOldBuildAndBuildCountDisabled() {
        HealthSeriesBuilder builder = new HealthSeriesBuilder(new HealthDescriptor());
        GraphConfiguration configuration = createConfiguration();
        when(configuration.isBuildCountDefined()).thenReturn(false);
        when(configuration.isDayCountDefined()).thenReturn(true);
        when(configuration.getDayCount()).thenReturn(1);

        StaticAnalysisRun resultWithTooOldBuild = createBuildResult(2, Calendar.getInstance().getTimeInMillis() - MILLIS_OF_DAY * 2, 1, 2, 3);
        StaticAnalysisRun resultWithValidBuild = createBuildResult(1, 5, 2, 3);

        CategoryDataset dataSetWithTwoResults = builder.createDataSet(configuration, Lists.newArrayList(resultWithValidBuild, resultWithTooOldBuild));

        assertDataSet1Column1Row(dataSetWithTwoResults, "#1", 10);
    }

    /** Verifies the data set with two build results and build count set to 2. */
    @Test
    void createDataSetWithTwoResultsBuildCount2() {
        HealthSeriesBuilder builder = new HealthSeriesBuilder(new HealthDescriptor());
        GraphConfiguration configuration = createConfiguration();
        when(configuration.isBuildCountDefined()).thenReturn(true);
        when(configuration.getBuildCount()).thenReturn(2);

        StaticAnalysisRun result1 = createBuildResult(1, 1, 5, 3);
        StaticAnalysisRun result2 = createBuildResult(2, 4, 1, 3);

        CategoryDataset dataSetWithTwoResults = builder.createDataSet(configuration, Lists.newArrayList(result1, result2));

        assertDataSet2Column1Row(dataSetWithTwoResults, "#1", "#2", 9, 8);
    }

    /** Verifies the data set using the build date as domain and builds on the same day. */
    @Test
    void createDataSetWithDateAsBuildDomainAtSameDay() {
        HealthSeriesBuilder builder = new HealthSeriesBuilder(new HealthDescriptor());
        GraphConfiguration configuration = createConfiguration();
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        Calendar buildTimeCalendar = Calendar.getInstance();
        buildTimeCalendar.set(2017, Calendar.NOVEMBER, 20);
        long buildTime = buildTimeCalendar.getTimeInMillis();
        StaticAnalysisRun result1 = createBuildResult(1, buildTime, 1, 2, 3);
        StaticAnalysisRun result2 = createBuildResult(2, buildTime, 1, 2, 3);

        CategoryDataset dataSetWithTwoResults = builder.createDataSet(configuration, Lists.newArrayList(result1, result2));

        assertDataSet1Column1Row(dataSetWithTwoResults, "11-20", 6);
    }

    /** Verifies the data set using the build date as domain and builds on different days. */
    @Test
    void createDataSetWithDateAsBuildDomainAtDifferentDays() {
        HealthSeriesBuilder builder = new HealthSeriesBuilder(new HealthDescriptor());
        GraphConfiguration configuration = createConfiguration();
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        Calendar buildTimeCalendar = Calendar.getInstance();
        buildTimeCalendar.set(2017, Calendar.NOVEMBER, 20);
        long buildTimeNov20 = buildTimeCalendar.getTimeInMillis();
        buildTimeCalendar.set(2017, Calendar.NOVEMBER, 19);
        long buildTimeNov19 = buildTimeCalendar.getTimeInMillis();

        StaticAnalysisRun result1 = createBuildResult(1, buildTimeNov19, 1, 2, 3);
        StaticAnalysisRun result2 = createBuildResult(2, buildTimeNov20, 1, 1, 1);

        CategoryDataset dataSetWithTwoResults = builder.createDataSet(configuration, Lists.newArrayList(result1, result2));

        assertDataSet2Column1Row(dataSetWithTwoResults, "11-19", "11-20", 6, 3);
    }

    /** Verifies the data set when using build date as domain and the health descriptor is enabled. */
    @Test
    void createDataSetWithDateAsBuildDomainHealthDescriptorEnabled() {
        HealthSeriesBuilder builder = new HealthSeriesBuilder(new HealthDescriptor("5", "10", Priority.LOW));
        GraphConfiguration configuration = createConfiguration();
        when(configuration.useBuildDateAsDomain()).thenReturn(true);


        Calendar buildTimeCalendar = Calendar.getInstance();
        buildTimeCalendar.set(2017, Calendar.NOVEMBER, 20);
        long buildTimeNov20 = buildTimeCalendar.getTimeInMillis();
        buildTimeCalendar.set(2017, Calendar.NOVEMBER, 18);
        long buildTimeNov18 = buildTimeCalendar.getTimeInMillis();

        StaticAnalysisRun result1 = createBuildResult(1, buildTimeNov18, 1, 2, 4);
        StaticAnalysisRun result2 = createBuildResult(2, buildTimeNov20, 1, 1, 1);

        CategoryDataset dataSetWithTwoResults = builder.createDataSet(configuration, Lists.newArrayList(result1, result2));

        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(dataSetWithTwoResults.getColumnCount()).isEqualTo(2);
        softly.assertThat(dataSetWithTwoResults.getRowCount()).isEqualTo(3);
        softly.assertThat(dataSetWithTwoResults.getColumnKey(0).toString()).isEqualTo("11-18");
        softly.assertThat(dataSetWithTwoResults.getColumnKey(1).toString()).isEqualTo("11-20");
        softly.assertThat(dataSetWithTwoResults.getValue(0, 0)).isEqualTo(5);
        softly.assertThat(dataSetWithTwoResults.getValue(1, 0)).isEqualTo(2);
        softly.assertThat(dataSetWithTwoResults.getValue(0, 1)).isEqualTo(3);
        softly.assertAll();
    }


    /** Verifies that a graph is displayed even the health descriptor is disabled. */
    @Test
    void shouldDisplayGraphIfHealthDescriptorIsDisabled() {
        HealthSeriesBuilder builder = new HealthSeriesBuilder(new HealthDescriptor());

        List<Integer> series = builder.computeSeries(createBuildResult(1, 1, 0, 0));

        assertThat(series.size()).isGreaterThan(0);
    }

    /** Verifies the graph for an unhealthy build. */
    @Test
    void unhealthyGraphIfIssuesCountGreaterThanUnhealthyThreshold() {
        HealthDescriptor healthDescriptor = new HealthDescriptor("5", "10", Priority.NORMAL);
        HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);

        List<Integer> series = builder.computeSeries(createBuildResult(1, 13, 0, 0));

        assertThat(series).containsExactly(5, 5, 3);
    }

    /** Verifies the graph for an healthy build. */
    @Test
    void healthyGraphIfIssuesCountLessOrEqualThanHealthyThreshold() {
        HealthDescriptor healthDescriptor = new HealthDescriptor("5", "10", Priority.NORMAL);
        HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);

        List<Integer> seriesEqualToHealthy = builder.computeSeries(createBuildResult(1, 5, 0, 0));
        List<Integer> seriesLessThanHealthy = builder.computeSeries(createBuildResult(2, 2, 0, 0));

        assertThat(seriesEqualToHealthy).containsExactly(5, 0, 0);
        assertThat(seriesLessThanHealthy).containsExactly(2, 0, 0);
    }

    /** Verifies the graph for an build with more issues than healthy build but less or equal than unhealthy threshold. */
    @Test
    void graphIfIssuesCountGreaterThanHealthyButLessOrEqualThanUnhealthyThreshold() {
        HealthDescriptor healthDescriptor = new HealthDescriptor("5", "10", Priority.NORMAL);
        HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);

        List<Integer> seriesEqualToHealthy = builder.computeSeries(createBuildResult(1, 9, 0, 0));
        List<Integer> seriesLessThanHealthy = builder.computeSeries(createBuildResult(2, 10, 0, 0));

        assertThat(seriesEqualToHealthy).containsExactly(5, 4, 0);
        assertThat(seriesLessThanHealthy).containsExactly(5, 5, 0);
    }


    /** Creates a mocked graph configuration. */
    private GraphConfiguration createConfiguration() {
        return mock(GraphConfiguration.class);
    }


    private StaticAnalysisRun createBuildResult(final int buildNumber, final int numberOfHighPriorityIssues,
            final int numberOfNormalPriorityIssues, final int numberOfLowPriorityIssues) {
        return createBuildResult(buildNumber, Calendar.getInstance().getTimeInMillis(), numberOfHighPriorityIssues, numberOfNormalPriorityIssues, numberOfLowPriorityIssues);
    }

    /**
     * Creates a mocked build result with the given parameters.
     *
     * @param buildNumber
     *         The number of the build.
     * @param buildTime
     *         The time the build was created.
     * @param numberOfHighPriorityIssues
     *         The number of high priority issues of the build.
     * @param numberOfNormalPriorityIssues
     *         The number of normal priority issues of the build.
     * @param numberOfLowPriorityIssues
     *         The number of low priority issues of the build.
     *
     * @return buildResult The mocked build result with the given parameters.
     */
    private StaticAnalysisRun createBuildResult(final int buildNumber, final long buildTime, final int numberOfHighPriorityIssues,
            final int numberOfNormalPriorityIssues, final int numberOfLowPriorityIssues) {
        AnalysisBuild build = createRun(buildNumber, buildTime);
        StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);

        when(buildResult.getTotalSize()).thenReturn(numberOfHighPriorityIssues + numberOfNormalPriorityIssues + numberOfLowPriorityIssues);
        when(buildResult.getTotalHighPrioritySize()).thenReturn(numberOfHighPriorityIssues);
        when(buildResult.getTotalNormalPrioritySize()).thenReturn(numberOfNormalPriorityIssues);
        when(buildResult.getTotalLowPrioritySize()).thenReturn(numberOfLowPriorityIssues);

        when(buildResult.getBuild()).thenReturn(build);

        return buildResult;
    }

    /**
     * Creates a run with the given parameters.
     *
     * @param number
     *         The build number.
     * @param buildTime
     *         The time in milliseconds the build was created.
     *
     * @return run The mocked run with the given parameters.
     */
    private AnalysisBuild createRun(final int number, final long buildTime) {
        AnalysisBuild run = mock(AnalysisBuild.class);
        when(run.getNumber()).thenReturn(number);
        when(run.getDisplayName()).thenReturn("#" + number);
        when(run.getTimeInMillis()).thenReturn(buildTime);
        return run;
    }

    /**
     * Checks the data sets values. The data set must contain 1 column and 1 row.
     *
     * @param dataSet
     *         The data set to check.
     * @param columnKey
     *         The column kex of the data set.
     * @param rowValue
     *         The value of the row.
     */
    private void assertDataSet1Column1Row(final CategoryDataset dataSet, final String columnKey, final int rowValue) {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(dataSet.getColumnCount()).isEqualTo(1);
        softly.assertThat(dataSet.getRowCount()).isEqualTo(1);
        softly.assertThat(dataSet.getColumnKey(0).toString()).isEqualTo(columnKey);
        softly.assertThat(dataSet.getValue(0, 0)).isEqualTo(rowValue);
        softly.assertAll();
    }

    /**
     * Checks the data sets values. The data set must contain 2 column and 1 row.
     *
     * @param dataSet
     *         The data set to check.
     * @param column1Key
     *         The key of the first column.
     * @param column2Key
     *         The key of the second column.
     * @param column1RowValue
     *         The value of the first columns row.
     * @param column2RowValue
     *         The value of the second columns row.
     */
    private void assertDataSet2Column1Row(final CategoryDataset dataSet, final String column1Key, final String column2Key, final int column1RowValue, final int column2RowValue) {
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(dataSet.getColumnCount()).isEqualTo(2);
        softly.assertThat(dataSet.getRowCount()).isEqualTo(1);
        softly.assertThat(dataSet.getColumnKey(0).toString()).isEqualTo(column1Key);
        softly.assertThat(dataSet.getColumnKey(1).toString()).isEqualTo(column2Key);
        softly.assertThat(dataSet.getValue(0, 0)).isEqualTo(column1RowValue);
        softly.assertThat(dataSet.getValue(0, 1)).isEqualTo(column2RowValue);
        softly.assertAll();
    }
}