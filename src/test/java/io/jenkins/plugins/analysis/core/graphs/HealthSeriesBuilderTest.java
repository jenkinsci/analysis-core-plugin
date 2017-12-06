package io.jenkins.plugins.analysis.core.graphs;

import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.util.Lists;
import org.jfree.data.category.CategoryDataset;
import org.junit.jupiter.api.Test;

import edu.hm.hafner.analysis.Priority;
import io.jenkins.plugins.analysis.core.quality.AnalysisBuild;
import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import static org.mockito.Mockito.*;

/**
 * Tests the class {@link HealthSeriesBuilder}.
 *
 * @author Joscha Behrmann
 */
@SuppressWarnings("Duplicates")
class HealthSeriesBuilderTest {

    /**
     * When given empty runs as input, no data should be generated when
     * using build-number as domain.
     */
    @Test
    void emptyAnalysisInputOnBuildNumberShouldReturnEmptyDataSet() {
        GraphConfiguration cfg = createGraphConfig();
        SeriesBuilder builder = new HealthSeriesBuilder(createDisabledHealthDescriptor());

        SoftAssertions.assertSoftly(softly -> {
            when(cfg.useBuildDateAsDomain()).thenReturn(false);
            CategoryDataset dataSet = builder.createDataSet(cfg, Lists.newArrayList());
            softly.assertThat(dataSet.getRowCount()).isEqualTo(0);
            softly.assertThat(dataSet.getColumnCount()).isEqualTo(0);

            CategoryDataset dataSet2 = builder.createDataSet(cfg, Lists.newArrayList());
            softly.assertThat(dataSet2.getColumnCount()).isEqualTo(0);
            softly.assertThat(dataSet2.getRowCount()).isEqualTo(0);
        });
    }

    /**
     * Results that are too old shouldn't be added to the data set.
     */
    @Test
    void oldResultsShouldNotBeAdded() {
        GraphConfiguration cfg = createGraphConfig();
        ResultTime resTime = mock(ResultTime.class);
        SeriesBuilder builder = new HealthSeriesBuilder(createDisabledHealthDescriptor(), resTime);

        // Only first 3 runs are new enough to be considered
        when(resTime.areResultsTooOld(any(), any())).thenReturn(false, false, false, true);

        SoftAssertions.assertSoftly(softly -> {
            CategoryDataset dataSet = builder.createDataSet(cfg, Arrays.asList(
                    createAnalysisRun(1, 2, 3, 4, 1),
                    createAnalysisRun(2, 5, 3, 4, 2),
                    createAnalysisRun(3, 7, 3, 4, 3),
                    createAnalysisRun(4, 2, 3, 4, 99),
                    createAnalysisRun(5, 2, 3, 4, 99)
            ));

            softly.assertThat(dataSet.getRowCount()).isOne();
            softly.assertThat(dataSet.getColumnCount())
                    .as("Old runs shouldn't be added to the dataset")
                    .isEqualTo(3);
            softly.assertThat(dataSet.getValue(0, 0)).isEqualTo(9);
            softly.assertThat(dataSet.getValue(0, 1)).isEqualTo(12);
            softly.assertThat(dataSet.getValue(0, 2)).isEqualTo(14);
        });
    }

    /**
     * The number of builds to create the data-set from shouldn't be exceeded.
     */
    @Test
    void buildCountShouldNotBeExceeded() {
        GraphConfiguration cfg = createGraphConfig();
        ResultTime resTime = mock(ResultTime.class);
        SeriesBuilder builder = new HealthSeriesBuilder(createDisabledHealthDescriptor(), resTime);

        // Limit count of runs
        when(cfg.isBuildCountDefined()).thenReturn(true);

        SoftAssertions.assertSoftly(softly -> {
            // Cap at 1, 7, 10 builds
            when(cfg.getBuildCount()).thenReturn(1, 7, 10);

            CategoryDataset dataSet = builder.createDataSet(cfg, createAnalysisRuns());
            softly.assertThat(dataSet.getRowCount()).isOne();
            softly.assertThat(dataSet.getColumnCount()).isOne();
            softly.assertThat(dataSet.getValue(0, 0)).isEqualTo(6);

            CategoryDataset dataSet1 = builder.createDataSet(cfg, createAnalysisRuns());
            softly.assertThat(dataSet1.getRowCount()).isOne();
            softly.assertThat(dataSet1.getColumnCount()).isEqualTo(5);
            softly.assertThat(dataSet1.getValue(0, 0)).isEqualTo(9);

            CategoryDataset dataSet2 = builder.createDataSet(cfg, createAnalysisRuns());
            softly.assertThat(dataSet2.getRowCount()).isOne();
            softly.assertThat(dataSet2.getColumnCount()).isEqualTo(5);
            softly.assertThat(dataSet2.getValue(0, 0)).isEqualTo(9);
        });
    }

    /**
     * When feeding multiple runs from the same day as input, these should be averaged out
     * and returned as a single entry in the data-set.
     */
    @Test
    void multipleSeriesPerDayShouldBeAveraged() {
        GraphConfiguration cfg = createGraphConfig();
        ResultTime resTime = mock(ResultTime.class);
        SeriesBuilder builder = new HealthSeriesBuilder(createDisabledHealthDescriptor(), resTime);

        SoftAssertions.assertSoftly(softly -> {
            CategoryDataset dataSet = builder.createDataSet(cfg, createAnalysisRuns());
            softly.assertThat(dataSet.getRowCount()).isOne();
            softly.assertThat(dataSet.getColumnCount()).isEqualTo(5);

            // Run 1 & 2 are on the same day
            softly.assertThat(dataSet.getValue(0, 0)).isEqualTo(9);

            // Run 3 is on single day
            softly.assertThat(dataSet.getValue(0, 1)).isEqualTo(12);

            // Run 4 & 5 are on the same day
            softly.assertThat(dataSet.getValue(0, 2)).isEqualTo(15);

            // Run 6, 7 are on single days
            softly.assertThat(dataSet.getValue(0, 3)).isEqualTo(9);
            softly.assertThat(dataSet.getValue(0, 4)).isEqualTo(18);
        });
    }

    /**
     * If healthDescriptor is disabled, only the total count of issues should be
     * contained in the resulting data set.
     */
    @Test
    void shouldAddRemainderIfNoHealthDescriptor() {
        GraphConfiguration cfg = createGraphConfig();
        ResultTime resTime = mock(ResultTime.class);
        SeriesBuilder builder = new HealthSeriesBuilder(createDisabledHealthDescriptor(), resTime);

        SoftAssertions.assertSoftly(softly-> {
            CategoryDataset dataSet = builder.createDataSet(cfg, createAnalysisRuns());
            softly.assertThat(dataSet.getRowCount()).isOne();
            softly.assertThat(dataSet.getColumnCount()).isEqualTo(5);
            softly.assertThat(dataSet.getValue(0, 0)).isEqualTo(9);
            softly.assertThat(dataSet.getValue(0, 1)).isEqualTo(12);
            softly.assertThat(dataSet.getValue(0, 2)).isEqualTo(15);
            softly.assertThat(dataSet.getValue(0, 3)).isEqualTo(9);
            softly.assertThat(dataSet.getValue(0, 4)).isEqualTo(18);
        });
    }

    /**
     * If healthDescriptor is enabled, the data-set triples should be
     * calculated accordingly.
     */
    @Test
    void shouldComputeSeriesOnHealthDescriptorEnabled() {
        GraphConfiguration cfg = createGraphConfig();
        ResultTime resTime = mock(ResultTime.class);
        SeriesBuilder builder = new HealthSeriesBuilder(createHealthDescriptor(), resTime);

        SoftAssertions.assertSoftly(softly-> {
            CategoryDataset dataSet = builder.createDataSet(cfg, createAnalysisRuns());
            softly.assertThat(dataSet.getRowCount()).isEqualTo(3);
            softly.assertThat(dataSet.getColumnCount()).isEqualTo(5);

            softly.assertThat(dataSet.getValue(0, 0)).isEqualTo(8);
            softly.assertThat(dataSet.getValue(0, 1)).isEqualTo(10);
            softly.assertThat(dataSet.getValue(0, 2)).isEqualTo(10);

            softly.assertThat(dataSet.getValue(1, 0)).isEqualTo(1);
            softly.assertThat(dataSet.getValue(1, 1)).isEqualTo(2);
            softly.assertThat(dataSet.getValue(1, 2)).isEqualTo(5);

            softly.assertThat(dataSet.getValue(2, 0)).isEqualTo(0);
            softly.assertThat(dataSet.getValue(2, 1)).isEqualTo(0);
            softly.assertThat(dataSet.getValue(2, 2)).isEqualTo(0);
        });
    }

    /**
     * When using build-numbers as the data-domain, builds should be
     * added accordingly.
     */
    @Test
    void shouldCreateDataSetPerBuildNumber() {
        GraphConfiguration cfg = createGraphConfig();
        SeriesBuilder builder = new HealthSeriesBuilder(createDisabledHealthDescriptor());

        when(cfg.useBuildDateAsDomain()).thenReturn(false);

        SoftAssertions.assertSoftly(softly -> {
            CategoryDataset dataSet = builder.createDataSet(cfg, createAnalysisRuns());
            softly.assertThat(dataSet.getRowCount()).isOne();
            softly.assertThat(dataSet.getColumnCount())
                    .as("There should be one data-entry for every build")
                    .isEqualTo(7);
            softly.assertThat(dataSet.getValue(0, 0)).isEqualTo(6);
            softly.assertThat(dataSet.getValue(0, 1)).isEqualTo(12);
            softly.assertThat(dataSet.getValue(0, 2)).isEqualTo(12);
            softly.assertThat(dataSet.getValue(0, 3)).isEqualTo(21);
        });
    }

    /**
     * When the getTotalSize() of a run is bigger than the healthy-threshold.
     */
    @Test
    void totalSizeOverHealthy() {
        GraphConfiguration cfg = createGraphConfig();
        HealthDescriptor healthDescriptor = createHealthDescriptor(10, 20, Priority.NORMAL, true);
        SeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);
        final StaticAnalysisRun run = createAnalysisRun(1, 7, 3, 5, 1);


        SoftAssertions.assertSoftly(softly -> {
            CategoryDataset dataSet = builder.createDataSet(cfg, Arrays.asList(run));
            softly.assertThat(dataSet.getValue(0, 0)).isEqualTo(10);
            softly.assertThat(dataSet.getValue(1, 0)).isEqualTo(5);
            softly.assertThat(dataSet.getValue(2, 0)).isEqualTo(0);
        });
    }

    /**
     * When the getTotalSize() of a run is equal to the healthy-threshold, the last two rows should be zero.
     */
    @Test
    void totalSizeEqualsHealthy() {
        GraphConfiguration cfg = createGraphConfig();
        HealthDescriptor healthDescriptor = createHealthDescriptor(19, 30, Priority.NORMAL, true);
        SeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);
        final StaticAnalysisRun run = createAnalysisRun(1, 8, 2, 9, 1);


        SoftAssertions.assertSoftly(softly -> {
            CategoryDataset dataSet = builder.createDataSet(cfg, Arrays.asList(run));
            softly.assertThat(dataSet.getValue(0, 0)).isEqualTo(19);
            softly.assertThat(dataSet.getValue(1, 0)).isEqualTo(0);
            softly.assertThat(dataSet.getValue(2, 0)).isEqualTo(0);
        });
    }

    @Test
    void totalSizeOneAboveHealthy() {
        GraphConfiguration cfg = createGraphConfig();
        HealthDescriptor healthDescriptor = createHealthDescriptor(12, 30, Priority.NORMAL, true);
        SeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);
        final StaticAnalysisRun run = createAnalysisRun(1, 7, 4, 2, 1);


        SoftAssertions.assertSoftly(softly -> {
            CategoryDataset dataSet = builder.createDataSet(cfg, Arrays.asList(run));
            softly.assertThat(dataSet.getValue(0, 0)).isEqualTo(12);
            softly.assertThat(dataSet.getValue(1, 0)).isEqualTo(1);
            softly.assertThat(dataSet.getValue(2, 0)).isEqualTo(0);
        });
    }

    @Test
    void totalSizeEqualsUnhealthy() {
        GraphConfiguration cfg = createGraphConfig();
        HealthDescriptor healthDescriptor = createHealthDescriptor(20, 30, Priority.NORMAL, true);
        SeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);
        final StaticAnalysisRun run = createAnalysisRun(1, 20, 8, 2, 1);


        SoftAssertions.assertSoftly(softly -> {
            CategoryDataset dataSet = builder.createDataSet(cfg, Arrays.asList(run));
            softly.assertThat(dataSet.getValue(0, 0)).isEqualTo(20);
            softly.assertThat(dataSet.getValue(1, 0)).isEqualTo(10);
            softly.assertThat(dataSet.getValue(2, 0)).isEqualTo(0);
        });
    }

    @Test
    void totalSizeOneAboveUnhealthy() {
        GraphConfiguration cfg = createGraphConfig();
        HealthDescriptor healthDescriptor = createHealthDescriptor(20, 30, Priority.NORMAL, true);
        SeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);
        final StaticAnalysisRun run = createAnalysisRun(1, 12, 8, 11, 1);


        SoftAssertions.assertSoftly(softly -> {
            CategoryDataset dataSet = builder.createDataSet(cfg, Arrays.asList(run));
            softly.assertThat(dataSet.getValue(0, 0)).isEqualTo(20);
            softly.assertThat(dataSet.getValue(1, 0)).isEqualTo(10);
            softly.assertThat(dataSet.getValue(2, 0)).isEqualTo(1);
        });
    }

    @Test
    void healthyEqualsUnhealthy() {
        GraphConfiguration cfg = createGraphConfig();
        HealthDescriptor healthDescriptor = createHealthDescriptor(20, 20, Priority.NORMAL, true);
        SeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);
        final StaticAnalysisRun run = createAnalysisRun(1, 12, 8, 11, 1);


        SoftAssertions.assertSoftly(softly -> {
            CategoryDataset dataSet = builder.createDataSet(cfg, Arrays.asList(run));
            softly.assertThat(dataSet.getValue(0, 0)).isEqualTo(20);
            softly.assertThat(dataSet.getValue(1, 0)).isEqualTo(0);
            softly.assertThat(dataSet.getValue(2, 0)).isEqualTo(11);
        });
    }

    /**
     * Explicitly ensures C2a (boundary interior) coverage of createSeriesPerBuild().
     * This tests every path that can be reached with 1 iteration.
     */
    @Test
    void createSeriesPerBuildBoundaryInteriorEnterOnce() {
        GraphConfiguration cfg = createGraphConfig();
        ResultTime resTime = mock(ResultTime.class);
        SeriesBuilder builder = new HealthSeriesBuilder(createDisabledHealthDescriptor(), resTime);

        List<StaticAnalysisRun> oneRun = Lists.newArrayList();
        oneRun.add(createAnalysisRun(1, 1, 2, 3, 1));

        SoftAssertions.assertSoftly(softly -> {
            // empty data
            CategoryDataset dataSet1 = builder.createDataSet(cfg, Lists.emptyList());
            softly.assertThat(dataSet1.getRowCount()).isZero();
            softly.assertThat(dataSet1.getColumnCount()).isZero();

            // enter once, break on resultTime
            when(resTime.areResultsTooOld(any(), any())).thenReturn(true);
            CategoryDataset dataSet2 = builder.createDataSet(cfg, oneRun);
            softly.assertThat(dataSet2.getRowCount()).isZero();
            softly.assertThat(dataSet2.getColumnCount()).isZero();

            // enter once, no build count defined
            when(resTime.areResultsTooOld(any(), any())).thenReturn(false);
            CategoryDataset dataSet3 = builder.createDataSet(cfg, oneRun);
            softly.assertThat(dataSet3.getRowCount()).isOne();
            softly.assertThat(dataSet3.getColumnCount()).isOne();

            // enter once, build count is defined but not reached
            when(cfg.isBuildCountDefined()).thenReturn(true);
            when(cfg.getBuildCount()).thenReturn(2);
            CategoryDataset dataSet4 = builder.createDataSet(cfg, oneRun);
            softly.assertThat(dataSet4.getRowCount()).isOne();
            softly.assertThat(dataSet4.getColumnCount()).isOne();

            // enter once, build count is defined and reached
            when(cfg.isBuildCountDefined()).thenReturn(true);
            when(cfg.getBuildCount()).thenReturn(-1);
            CategoryDataset dataSet5 = builder.createDataSet(cfg, oneRun);
            softly.assertThat(dataSet5.getRowCount()).isOne();
            softly.assertThat(dataSet5.getColumnCount()).isOne();
        });
    }

    /**
     * Explicitly ensures C2a (boundary interior) coverage of createSeriesPerBuild().
     * This tests every path that can be reached with 2 iteration.
     */
    @Test
    void createSeriesPerBuildBoundaryInteriorEnterTwice() {
        GraphConfiguration cfg = createGraphConfig();
        ResultTime resTime = mock(ResultTime.class);
        SeriesBuilder builder = new HealthSeriesBuilder(createDisabledHealthDescriptor(), resTime);

        List<StaticAnalysisRun> twoRuns = Lists.newArrayList();
        twoRuns.add(createAnalysisRun(1, 1, 2, 3, 1));
        twoRuns.add(createAnalysisRun(2, 2, 3, 4, 2));

        SoftAssertions.assertSoftly(softly -> {
            // enter twice, no resultTime, no buildCount defined
            when(resTime.areResultsTooOld(any(), any())).thenReturn(false);
            when(cfg.isBuildCountDefined()).thenReturn(false);
            CategoryDataset dataSet6 = builder.createDataSet(cfg, twoRuns);
            softly.assertThat(dataSet6.getRowCount()).isOne();
            softly.assertThat(dataSet6.getColumnCount()).isEqualTo(2);

            // enter twice, no resultTime, buildCount is defined but not reached
            when(resTime.areResultsTooOld(any(), any())).thenReturn(false);
            when(cfg.isBuildCountDefined()).thenReturn(true);
            when(cfg.getBuildCount()).thenReturn(3);
            CategoryDataset dataSet7 = builder.createDataSet(cfg, twoRuns);
            softly.assertThat(dataSet7.getRowCount()).isOne();
            softly.assertThat(dataSet7.getColumnCount()).isEqualTo(2);

            // enter twice, no resultTime, buildCount is defined and reached on first
            when(resTime.areResultsTooOld(any(), any())).thenReturn(false);
            when(cfg.isBuildCountDefined()).thenReturn(true);
            when(cfg.getBuildCount()).thenReturn(1);
            CategoryDataset dataSet8 = builder.createDataSet(cfg, twoRuns);
            softly.assertThat(dataSet8.getRowCount()).isOne();
            softly.assertThat(dataSet8.getColumnCount()).isEqualTo(1);

            // enter twice, no resultTime, buildCount is defined and reached on second
            when(resTime.areResultsTooOld(any(), any())).thenReturn(false);
            when(cfg.isBuildCountDefined()).thenReturn(true);
            when(cfg.getBuildCount()).thenReturn(2);
            CategoryDataset dataSet9 = builder.createDataSet(cfg, twoRuns);
            softly.assertThat(dataSet9.getRowCount()).isOne();
            softly.assertThat(dataSet9.getColumnCount()).isEqualTo(2);

            // enter twice, resultTime on second, buildCount is not defined
            when(resTime.areResultsTooOld(any(), any())).thenReturn(false, true);
            when(cfg.isBuildCountDefined()).thenReturn(false);
            CategoryDataset dataSet10 = builder.createDataSet(cfg, twoRuns);
            softly.assertThat(dataSet10.getRowCount()).isOne();
            softly.assertThat(dataSet10.getColumnCount()).isOne();

            // enter twice, resultTime on second, buildCount is defined but not reached
            when(resTime.areResultsTooOld(any(), any())).thenReturn(false, true);
            when(cfg.isBuildCountDefined()).thenReturn(true);
            when(cfg.getBuildCount()).thenReturn(3);
            CategoryDataset dataSet11 = builder.createDataSet(cfg, twoRuns);
            softly.assertThat(dataSet11.getRowCount()).isOne();
            softly.assertThat(dataSet11.getColumnCount()).isOne();

            // enter twice, resultTime on second, buildCount is defined and reached after first
            when(resTime.areResultsTooOld(any(), any())).thenReturn(false, true);
            when(cfg.isBuildCountDefined()).thenReturn(true);
            when(cfg.getBuildCount()).thenReturn(1);
            CategoryDataset dataSet12 = builder.createDataSet(cfg, twoRuns);
            softly.assertThat(dataSet12.getRowCount()).isOne();
            softly.assertThat(dataSet12.getColumnCount()).isOne();
        });
    }

    /*
     * Provides a set of sample input data (build number, build date, issues):
     *  build 1-2, day 1, avg. 9 issues
     *  build 3, day 2, 12 issues
     *  build 4-5, day 3, avg. 15 issues
     *  build 6, day 4, 9 issues
     *  build 7, day 5, 18 issues
     */
    private List<StaticAnalysisRun> createAnalysisRuns() {
        StaticAnalysisRun r1 = createAnalysisRun(1, 1, 2, 3, 1);
        StaticAnalysisRun r2 = createAnalysisRun(2, 2, 3, 7, 1);
        StaticAnalysisRun r3 = createAnalysisRun(3, 3, 4, 5, 2);
        StaticAnalysisRun r4 = createAnalysisRun(4, 6, 7, 8, 3);
        StaticAnalysisRun r5 = createAnalysisRun(5, 9, 0, 1, 3);
        StaticAnalysisRun r6 = createAnalysisRun(6, 2, 3, 4, 4);
        StaticAnalysisRun r7 = createAnalysisRun(7, 5, 6, 7, 5);
        return Lists.newArrayList(r1, r2, r3, r4, r5, r6, r7);
    }

    /*
     * Mocks a GraphConfiguration whose useBuildDateDomain is set to true.
     */
    private GraphConfiguration createGraphConfig() {
        GraphConfiguration graphConfiguration = mock(GraphConfiguration.class);
        when(graphConfiguration.useBuildDateAsDomain()).thenReturn(true);
        return graphConfiguration;
    }

    /*
     * Creates a mocked HealthDescriptor which is disabled
     */
    private HealthDescriptor createDisabledHealthDescriptor() {
        return createHealthDescriptor(10, 20, Priority.NORMAL, false);
    }

    /*
     * Creates a HealthDescriptor with default-settings
     */
    private HealthDescriptor createHealthDescriptor() {
        return createHealthDescriptor(10, 20, Priority.NORMAL, true);
    }

    /*
     * Creates a HealthDescriptor with given parameters
     */
    private HealthDescriptor createHealthDescriptor(int healthy, int unhealthy, Priority minPriority, boolean enabled) {
        HealthDescriptor hc = mock(HealthDescriptor.class);
        when(hc.getHealthy()).thenReturn(healthy);
        when(hc.getUnHealthy()).thenReturn(unhealthy);
        when(hc.getMinimumPriority()).thenReturn(minPriority);
        when(hc.isEnabled()).thenReturn(enabled);
        return hc;
    }

    /*
     * Creates a StaticAnalysisRun with given parameters
     */
    private StaticAnalysisRun createAnalysisRun(final int buildNumber, final int numHighPrio,
            final int numNormalPrio, final int numLowPrio, final int day) {
        StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);

        // Priorities
        when(buildResult.getTotalHighPrioritySize()).thenReturn(numHighPrio);
        when(buildResult.getTotalNormalPrioritySize()).thenReturn(numNormalPrio);
        when(buildResult.getTotalLowPrioritySize()).thenReturn(numLowPrio);

        // Total size
        when(buildResult.getTotalSize()).thenReturn(numHighPrio
                + numNormalPrio + numLowPrio);

        AnalysisBuild build = createAnalysisBuild(buildNumber, day * 24 * 60 * 60 * 1000);
        when(buildResult.getBuild()).thenReturn(build);

        return buildResult;
    }

    /*
     * Creates an AnalysisBuild with given parameters
     */
    private AnalysisBuild createAnalysisBuild(final int number, final long time) {
        AnalysisBuild run = mock(AnalysisBuild.class);
        when(run.getNumber()).thenReturn(number);
        when(run.getDisplayName()).thenReturn("#" + number);
        when(run.getTimeInMillis()).thenReturn(time);
        return run;
    }
}