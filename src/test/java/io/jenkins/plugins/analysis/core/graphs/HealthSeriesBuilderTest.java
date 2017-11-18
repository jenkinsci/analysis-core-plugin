package io.jenkins.plugins.analysis.core.graphs;


import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import org.jfree.data.category.CategoryDataset;
import org.joda.time.LocalDate;
import org.junit.jupiter.api.Test;

import edu.hm.hafner.analysis.Priority;
import io.jenkins.plugins.analysis.core.quality.AnalysisBuild;
import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class HealthSeriesBuilderTest {

    private final static long MILLISECONDS_OF_HOUR = 60 * 60 * 1000;
    private final static long MILLISECONDS_OF_DAY = 24 * MILLISECONDS_OF_HOUR;

    private final static HealthDescriptor HEALTH_DESCRIPTOR = new HealthDescriptor(Integer.toString(1), Integer.toString(3), Priority.NORMAL);

    private final static AnalysisBuild BUILD_BEFORE_YESTERDAYS_MIDNIGHT = stubAnalysisBuild(1, 2, 0);
    private final static AnalysisBuild BUILD_BEFORE_YESTERDAYS_MORNING = stubAnalysisBuild(2, 2, 8);
    private final static AnalysisBuild BUILD_BEFORE_YESTERDAYS_MIDDAY = stubAnalysisBuild(3, 2, 12);
    private final static AnalysisBuild BUILD_YESTERDAYS_MIDNIGHT = stubAnalysisBuild(5, 1, 0);
    private final static AnalysisBuild BUILD_YESTERDAYS_MORNING = stubAnalysisBuild(6, 1, 8);
    private final static AnalysisBuild BUILD_YESTERDAYS_MIDDAY = stubAnalysisBuild(7, 1, 12);
    private final static AnalysisBuild BUILD_THIS_MIDNIGHT = stubAnalysisBuild(9, 0, 0);
    private final static AnalysisBuild BUILD_THIS_MORNING = stubAnalysisBuild(10, 0, 8);
    private final static AnalysisBuild BUILD_THIS_MIDDAY = stubAnalysisBuild(11, 0, 12);

    /**
     * testComputeSeries should return a list with a zero, if the unhealthy is not bigger than healthy
     * healthy == unhealthy
     * result: (0)
     */
    @Test
    void computeSeriesHealthDescriptorDisabled() {
        assertThat(testComputeSeries(1, 1, 0)).containsExactly(0);
    }

    /**
     * total == healthy < unhealthy
     * result: (healthy, 0, 0)
     */
    @Test
    void computeSeriesTotalEqualToHealthy() {
        assertThat(testComputeSeries(1, 3, 1)).containsExactly(1, 0, 0);
    }

    /**
     * healthy < unhealthy == total
     * result: (healthy, unhealthy - healthy ,0)
     */
    @Test
    void computeSeriesTotalEqualToUnhealthy() {
        assertThat(testComputeSeries(1, 3, 3)).containsExactly(1, 2, 0);
    }

    /**
     * healthy < unhealthy < total
     * result: (healthy, unhealthy - healthy, total - unhealthy)
     */
    @Test
    void computeSeriesTotalBiggerThanUnhealthy() {
        assertThat(testComputeSeries(1, 3, 6)).containsExactly(1, 2, 3);
    }


    @Test
    void emptyStaticAnalysisRunIterable() {
        List<StaticAnalysisRun> runs = new ArrayList<>();

        GraphConfiguration buildDateAsDomainConfiguration = mock(GraphConfiguration.class);
        when(buildDateAsDomainConfiguration.useBuildDateAsDomain()).thenReturn(false);
        GraphConfiguration buildDateNotAsDomainConfiguration = mock(GraphConfiguration.class);
        when(buildDateNotAsDomainConfiguration.useBuildDateAsDomain()).thenReturn(true);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);

        CategoryDataset buildDateAsDomainResult = sut.createDataSet(buildDateAsDomainConfiguration, runs);
        CategoryDataset buildDateNotAsDomainResult = sut.createDataSet(buildDateNotAsDomainConfiguration, runs);

        assertThat(buildDateAsDomainResult.getColumnCount()).isEqualTo(0);
        assertThat(buildDateNotAsDomainResult.getColumnCount()).isEqualTo(0);
    }

    @Test
    void dataSetPerBuildOneBuild() {
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(6, BUILD_THIS_MORNING));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(false);
        when(configuration.isDayCountDefined()).thenReturn(false);
        when(configuration.isBuildCountDefined()).thenReturn(false);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(1);
        assertThat(result.getValue(0,0)).isEqualTo(1);
        assertThat(result.getValue(1,0)).isEqualTo(2);
        assertThat(result.getValue(2,0)).isEqualTo(3);
        assertThat(result.getColumnKey(0)).isEqualTo(new NumberOnlyBuildLabel(BUILD_THIS_MORNING));
    }

    @Test
    void dataSetPerBuildTwoBuilds() {
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(6, BUILD_THIS_MIDDAY));
        runs.add(stubStaticAnalysisRun(7, BUILD_THIS_MORNING));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(false);
        when(configuration.isDayCountDefined()).thenReturn(false);
        when(configuration.isBuildCountDefined()).thenReturn(false);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(2);
        assertThat(result.getValue(2, 0)).isEqualTo(4);
        assertThat(result.getValue(2, 1)).isEqualTo(3);
        assertThat(result.getColumnKey(0)).isEqualTo(new NumberOnlyBuildLabel(BUILD_THIS_MORNING));
        assertThat(result.getColumnKey(1)).isEqualTo(new NumberOnlyBuildLabel(BUILD_THIS_MIDDAY));
    }

    @Test
    void dataSetPerBuildThreeBuildsDefinedBuildCountTwo() {
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(6, BUILD_THIS_MIDDAY));
        runs.add(stubStaticAnalysisRun(7, BUILD_THIS_MORNING));
        runs.add(stubStaticAnalysisRun(8, BUILD_THIS_MIDNIGHT));


        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(false);
        when(configuration.isDayCountDefined()).thenReturn(false);
        when(configuration.isBuildCountDefined()).thenReturn(true);
        when(configuration.getBuildCount()).thenReturn(2);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(2);
        assertThat(result.getValue(2, 0)).isEqualTo(4);
        assertThat(result.getValue(2, 1)).isEqualTo(3);
        assertThat(result.getColumnKey(0)).isEqualTo(new NumberOnlyBuildLabel(BUILD_THIS_MORNING));
        assertThat(result.getColumnKey(1)).isEqualTo(new NumberOnlyBuildLabel(BUILD_THIS_MIDDAY));
    }

    @Test
    void dataSetPerBuildThreeBuildsOfThreeDaysDefinedDayCountOne() {
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(6, BUILD_THIS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(7, BUILD_YESTERDAYS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(8, BUILD_BEFORE_YESTERDAYS_MIDNIGHT));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(false);
        when(configuration.isDayCountDefined()).thenReturn(true);
        when(configuration.getDayCount()).thenReturn(1);
        when(configuration.isBuildCountDefined()).thenReturn(false);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(1);
        assertThat(result.getValue(2, 0)).isEqualTo(3);
        assertThat(result.getColumnKey(0)).isEqualTo(new NumberOnlyBuildLabel(BUILD_THIS_MIDNIGHT));
    }

    @Test
    void dataSetPerBuildThreeBuildsOfThreeDaysDefinedDayCountTwo() {
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(6, BUILD_THIS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(7, BUILD_YESTERDAYS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(8, BUILD_BEFORE_YESTERDAYS_MIDNIGHT));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(false);
        when(configuration.isDayCountDefined()).thenReturn(true);
        when(configuration.getDayCount()).thenReturn(2);
        when(configuration.isBuildCountDefined()).thenReturn(false);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(2);
        assertThat(result.getValue(2, 0)).isEqualTo(4);
        assertThat(result.getValue(2, 1)).isEqualTo(3);
        assertThat(result.getColumnKey(0)).isEqualTo(new NumberOnlyBuildLabel(BUILD_YESTERDAYS_MIDNIGHT));
        assertThat(result.getColumnKey(1)).isEqualTo(new NumberOnlyBuildLabel(BUILD_THIS_MIDNIGHT));
    }

    @Test
    void t7() {
        //TODO Name, Configuration, Asserts, Refactoring
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(5, BUILD_THIS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(3, BUILD_YESTERDAYS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(5, BUILD_BEFORE_YESTERDAYS_MIDNIGHT));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(false);
        when(configuration.isDayCountDefined()).thenReturn(true);
        when(configuration.getDayCount()).thenReturn(1);
        when(configuration.isBuildCountDefined()).thenReturn(true);
        when(configuration.getBuildCount()).thenReturn(2);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(1);
    }

    @Test
    void buildDateAsDomainOneDayThreeDifferentStaticAnalysisRuns() {
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(6, BUILD_THIS_MIDDAY));
        runs.add(stubStaticAnalysisRun(9, BUILD_THIS_MORNING));
        runs.add(stubStaticAnalysisRun(12, BUILD_THIS_MIDNIGHT));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(1);
        assertThat(result.getValue(0, 0)).isEqualTo(1);
        assertThat(result.getValue(1, 0)).isEqualTo(2);
        assertThat(result.getValue(2, 0)).isEqualTo(6);
    }

    @Test
    void buildDateAsDomainOneDayTwoBuildsWithEqualStaticAnalysisRuns() {
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(6, BUILD_THIS_MIDDAY));
        runs.add(stubStaticAnalysisRun(6, BUILD_THIS_MORNING));
        runs.add(stubStaticAnalysisRun(9, BUILD_THIS_MIDNIGHT));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);
        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(1);
        assertThat(result.getValue(0,0)).isEqualTo(1);
        assertThat(result.getValue(1,0)).isEqualTo(2);
        assertThat(result.getValue(2,0)).isEqualTo(4);
    }

    @Test
    void buildDateAsDomainTwoDayEachWithOneBuild() {
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(7, BUILD_THIS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(6, BUILD_YESTERDAYS_MIDNIGHT));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(2);
        assertThat(result.getValue(2, 0)).isEqualTo(3);
        assertThat(result.getValue(2, 1)).isEqualTo(4);
        assertThat(result.getColumnKey(0)).isEqualTo(new LocalDateLabel(new LocalDate(BUILD_YESTERDAYS_MIDNIGHT.getTimeInMillis())));
        assertThat(result.getColumnKey(1)).isEqualTo(new LocalDateLabel(new LocalDate(BUILD_THIS_MIDNIGHT.getTimeInMillis())));
    }

    @Test
    void buildDateAsDomainTwoDayEachWithTwoBuilds() {
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(6, BUILD_THIS_MORNING));
        runs.add(stubStaticAnalysisRun(6, BUILD_THIS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(6, BUILD_YESTERDAYS_MORNING));
        runs.add(stubStaticAnalysisRun(12, BUILD_YESTERDAYS_MIDNIGHT));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(2);
        assertThat(result.getValue(2, 0)).isEqualTo(6);
        assertThat(result.getValue(2, 1)).isEqualTo(3);
        assertThat(result.getColumnKey(0)).isEqualTo(new LocalDateLabel(new LocalDate(BUILD_YESTERDAYS_MORNING.getTimeInMillis())));
        assertThat(result.getColumnKey(1)).isEqualTo(new LocalDateLabel(new LocalDate(BUILD_THIS_MORNING.getTimeInMillis())));
    }

    @Test
    void buildDateAsDomainTwoDaysTheDayBetweenIsMissing() {
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(5, BUILD_THIS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(5, BUILD_BEFORE_YESTERDAYS_MIDNIGHT));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(HEALTH_DESCRIPTOR);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(2);
        assertThat(result.getColumnKey(0)).isEqualTo(new LocalDateLabel(new LocalDate(BUILD_BEFORE_YESTERDAYS_MIDNIGHT.getTimeInMillis())));
        assertThat(result.getColumnKey(1)).isEqualTo(new LocalDateLabel(new LocalDate(BUILD_THIS_MIDNIGHT.getTimeInMillis())));
    }






    /**
     * Runs a parameterized test of computeSeriesGraphConfiguration
     * @param healthy healthy parameter
     * @param unHealthy unhealthy parameter
     * @param total total parameter
     * @return the list generated by computeSeries
     */
    private List<Integer> testComputeSeries(int healthy, int unHealthy, int total) {
        HealthDescriptor healthDescriptor = new HealthDescriptor(Integer.toString(healthy), Integer.toString(unHealthy), Priority.NORMAL);
        StaticAnalysisRun staticAnalysisRun = mock(StaticAnalysisRun.class);
        when(staticAnalysisRun.getTotalSize()).thenReturn(total);
        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptor);
        return sut.computeSeries(staticAnalysisRun);
    }

    private static long calculatePastTimestamps(int daysAgo, int timestampHour) {
        long now = System.currentTimeMillis();
        long currentDay = now - now % MILLISECONDS_OF_DAY - TimeZone.getDefault().getOffset(now);
        return currentDay - daysAgo * MILLISECONDS_OF_DAY + timestampHour * MILLISECONDS_OF_HOUR;
    }

    private static AnalysisBuild stubAnalysisBuild(int buildNumber, int daysAgo, int timestampHour) {
        AnalysisBuild build = mock(AnalysisBuild.class);
        when(build.getNumber()).thenReturn(buildNumber);
        when(build.getTimeInMillis()).thenReturn(calculatePastTimestamps(daysAgo, timestampHour));
        return build;
    }

    private static StaticAnalysisRun stubStaticAnalysisRun(int totalSize, int buildNumber, int daysAgo, int timestampHour) {
        return stubStaticAnalysisRun(totalSize, stubAnalysisBuild(buildNumber, daysAgo, timestampHour));
    }

    private static StaticAnalysisRun stubStaticAnalysisRun(int totalSize, AnalysisBuild build) {
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(totalSize);
        when(run.getBuild()).thenReturn(build);
        return run;
    }

}