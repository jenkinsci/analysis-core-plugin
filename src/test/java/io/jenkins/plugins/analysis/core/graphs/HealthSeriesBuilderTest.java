package io.jenkins.plugins.analysis.core.graphs;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

import org.assertj.core.api.ListAssert;
import org.jfree.data.category.CategoryDataset;
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
     * result: (healty, 0, 0)
     */
    @Test
    void computeSeriesTotalEqualToHealthy() {
        assertThat(testComputeSeries(1, 3, 1)).containsExactly(1, 0, 0);
    }

    /**
     * healthy < unhealthy == total
     * result: (healty, unhealthy - healthy ,0)
     */
    @Test
    void computeSeriesTotalEqualToUnhealty() {
        assertThat(testComputeSeries(1, 3, 3)).containsExactly(1, 2, 0);
    }

    /**
     * healthy < unhealthy < total
     * result: (healty, unhealthy - healthy, total - unhealthy)
     */
    @Test
    void computeSeriesTotalBiggerThanUnhealthy() {
        assertThat(testComputeSeries(1, 3, 6)).containsExactly(1, 2, 3);
    }


    @Test
    void emptyStaticAnalysisRunIterable() {
        HealthDescriptor healthDescriptor = new HealthDescriptor(Integer.toString(1), Integer.toString(2), Priority.NORMAL);
        List<StaticAnalysisRun> runs = new ArrayList<>();

        GraphConfiguration buildDateAsDomainConfiguration = mock(GraphConfiguration.class);
        when(buildDateAsDomainConfiguration.useBuildDateAsDomain()).thenReturn(false);
        GraphConfiguration buildDateNotAsDomainConfiguration = mock(GraphConfiguration.class);
        when(buildDateNotAsDomainConfiguration.useBuildDateAsDomain()).thenReturn(true);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset buildDateAsDomainResult = sut.createDataSet(buildDateAsDomainConfiguration, runs);
        CategoryDataset buildDateNotAsDomainResult = sut.createDataSet(buildDateNotAsDomainConfiguration, runs);

        assertThat(buildDateAsDomainResult.getColumnCount()).isEqualTo(0);
        assertThat(buildDateNotAsDomainResult.getColumnCount()).isEqualTo(0);
    }

    @Test
    void t2() {
        //TODO Name, Configuration, Asserts, Refactoring
        HealthDescriptor healthDescriptor = new HealthDescriptor(Integer.toString(1), Integer.toString(2), Priority.NORMAL);
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(5, BUILD_THIS_MORNING));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(false);
        when(configuration.isDayCountDefined()).thenReturn(false);
        when(configuration.isBuildCountDefined()).thenReturn(false);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(1);
    }

    @Test
    void t3() {
        //TODO Name, Configuration, Asserts, Refactoring
        HealthDescriptor healthDescriptor = new HealthDescriptor(Integer.toString(1), Integer.toString(2), Priority.NORMAL);
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(3, BUILD_THIS_MIDDAY));
        runs.add(stubStaticAnalysisRun(5, BUILD_THIS_MORNING));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(false);
        when(configuration.isDayCountDefined()).thenReturn(false);
        when(configuration.isBuildCountDefined()).thenReturn(false);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(2);
    }

    @Test
    void t4() {
        //TODO Name, Configuration, Asserts, Refactoring
        HealthDescriptor healthDescriptor = new HealthDescriptor(Integer.toString(1), Integer.toString(2), Priority.NORMAL);
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(3, BUILD_THIS_MIDDAY));
        runs.add(stubStaticAnalysisRun(5, BUILD_THIS_MORNING));
        runs.add(stubStaticAnalysisRun(5, BUILD_THIS_MIDNIGHT));


        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(false);
        when(configuration.isDayCountDefined()).thenReturn(false);
        when(configuration.isBuildCountDefined()).thenReturn(true);
        when(configuration.getBuildCount()).thenReturn(2);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(2);
    }

    @Test
    void t5() {
        //TODO Name, Configuration, Asserts, Refactoring
        HealthDescriptor healthDescriptor = new HealthDescriptor(Integer.toString(1), Integer.toString(2), Priority.NORMAL);
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(5, BUILD_THIS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(3, BUILD_YESTERDAYS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(5, BUILD_BEFORE_YESTERDAYS_MIDNIGHT));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(false);
        when(configuration.isDayCountDefined()).thenReturn(true);
        when(configuration.getDayCount()).thenReturn(1);
        when(configuration.isBuildCountDefined()).thenReturn(false);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(1);
    }

    @Test
    void t6() {
        //TODO Name, Configuration, Asserts, Refactoring
        HealthDescriptor healthDescriptor = new HealthDescriptor(Integer.toString(1), Integer.toString(2), Priority.NORMAL);
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(5, BUILD_THIS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(3, BUILD_YESTERDAYS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(5, BUILD_BEFORE_YESTERDAYS_MIDNIGHT));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(false);
        when(configuration.isDayCountDefined()).thenReturn(true);
        when(configuration.getDayCount()).thenReturn(2);
        when(configuration.isBuildCountDefined()).thenReturn(false);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(2);
    }

    @Test
    void t7() {
        //TODO Name, Configuration, Asserts, Refactoring
        HealthDescriptor healthDescriptor = new HealthDescriptor(Integer.toString(1), Integer.toString(2), Priority.NORMAL);
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

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(1);
    }

    //t8, t9

    @Test
    void buildDateAsDomainOneDayThreeDifferentStaticAnalysisRuns() {
        //TODO Name, Configuration, Asserts, Refactoring
        HealthDescriptor healthDescriptor = new HealthDescriptor(Integer.toString(1), Integer.toString(2), Priority.NORMAL);
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(5, BUILD_THIS_MIDDAY));
        runs.add(stubStaticAnalysisRun(6, BUILD_THIS_MORNING));
        runs.add(stubStaticAnalysisRun(7, BUILD_THIS_MIDNIGHT));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(1);
    }

    @Test
    void buildDateAsDomainOneDayTwoBuildsWithAEqualStaticAnalysisRuns() {
        HealthDescriptor healthDescriptor = new HealthDescriptor(Integer.toString(2), Integer.toString(4), Priority.NORMAL);
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(6, BUILD_THIS_MIDDAY));
        runs.add(stubStaticAnalysisRun(6, BUILD_THIS_MORNING));
        runs.add(stubStaticAnalysisRun(24, BUILD_THIS_MIDNIGHT));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptor);
        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(1);
        assertThat(result.getValue(0,0)).isEqualTo(2);
        assertThat(result.getValue(1,0)).isEqualTo(2);;
        assertThat(result.getValue(2,0)).isEqualTo(10);;
    }

    @Test
    void t11() {
        //TODO Name, Configuration, Asserts, Refactoring
        HealthDescriptor healthDescriptor = new HealthDescriptor(Integer.toString(1), Integer.toString(2), Priority.NORMAL);
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(5, BUILD_THIS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(3, BUILD_YESTERDAYS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(5, BUILD_BEFORE_YESTERDAYS_MIDNIGHT));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(3);
    }

    @Test
    void t12() {
        //TODO Name, Configuration, Asserts, Refactoring
        HealthDescriptor healthDescriptor = new HealthDescriptor(Integer.toString(1), Integer.toString(2), Priority.NORMAL);
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(5, BUILD_THIS_MORNING));
        runs.add(stubStaticAnalysisRun(5, BUILD_THIS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(3, BUILD_YESTERDAYS_MORNING));
        runs.add(stubStaticAnalysisRun(3, BUILD_YESTERDAYS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(5, BUILD_BEFORE_YESTERDAYS_MORNING));
        runs.add(stubStaticAnalysisRun(5, BUILD_BEFORE_YESTERDAYS_MIDNIGHT));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(3);
    }

    @Test
    void t13() {
        //TODO Name, Configuration, Asserts, Refactoring
        HealthDescriptor healthDescriptor = new HealthDescriptor(Integer.toString(1), Integer.toString(2), Priority.NORMAL);
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(5, BUILD_THIS_MIDDAY));
        runs.add(stubStaticAnalysisRun(6, BUILD_THIS_MORNING));
        runs.add(stubStaticAnalysisRun(7, BUILD_THIS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(3, BUILD_YESTERDAYS_MIDDAY));
        runs.add(stubStaticAnalysisRun(4, BUILD_YESTERDAYS_MORNING));
        runs.add(stubStaticAnalysisRun(5, BUILD_YESTERDAYS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(5, BUILD_BEFORE_YESTERDAYS_MIDDAY));
        runs.add(stubStaticAnalysisRun(5, BUILD_BEFORE_YESTERDAYS_MORNING));
        runs.add(stubStaticAnalysisRun(5, BUILD_BEFORE_YESTERDAYS_MIDNIGHT));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(3);
    }

    @Test
    void t14() {
        //TODO Name, Configuration, Asserts, Refactoring
        HealthDescriptor healthDescriptor = new HealthDescriptor(Integer.toString(1), Integer.toString(2), Priority.NORMAL);
        List<StaticAnalysisRun> runs = new ArrayList<>();
        runs.add(stubStaticAnalysisRun(5, BUILD_THIS_MIDNIGHT));
        runs.add(stubStaticAnalysisRun(5, BUILD_BEFORE_YESTERDAYS_MIDNIGHT));

        GraphConfiguration configuration = mock(GraphConfiguration.class);
        when(configuration.useBuildDateAsDomain()).thenReturn(true);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptor);

        CategoryDataset result = sut.createDataSet(configuration, runs);

        assertThat(result.getColumnCount()).isEqualTo(2);
    }






    /**
     * Runs a parameterized test of computeSeriesGraphConfiguration
     * @param healthy healthy parameter
     * @param unHeahlthy unhealthy parameter
     * @param total total parameter
     * @return the list generated by computeSeries
     */
    private List<Integer> testComputeSeries(int healthy, int unHeahlthy, int total) {
        HealthDescriptor healthDescriptor = new HealthDescriptor(Integer.toString(healthy), Integer.toString(unHeahlthy), Priority.NORMAL);
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