package io.jenkins.plugins.analysis.core.graphs;

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
 * Tests the class {@link HealthSeriesBuilder}
 *
 * @author Joscha Behrmann
 */
// TODO: custom assertions? CHECKSTYLE
class HealthSeriesBuilderTest {

    /**
     * Covers both branches of createDataSet() when given an empty list as input.
     * This should result in empty output.
     */
    @Test
    void emptyAnalysisInputShouldReturnEmptyDataSet() {
        GraphConfiguration cfg = createGraphConfig();
        SeriesBuilder builder = new HealthSeriesBuilder(createDisabledHealthDescriptor());

        SoftAssertions.assertSoftly(softly -> {
            // createDataSetPerBuildNumber
            when(cfg.useBuildDateAsDomain()).thenReturn(false);
            CategoryDataset dataSet = builder.createDataSet(cfg, Lists.newArrayList());
            softly.assertThat(dataSet.getColumnCount()).isEqualTo(0);
            softly.assertThat(dataSet.getColumnCount()).isEqualTo(0);

            // createDataSetPerDay
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

        // Last two runs are too old to be added
        when(resTime.areResultsTooOld(any(), any())).thenReturn(false, false, true, true);

        SoftAssertions.assertSoftly(softly -> {
            CategoryDataset dataSet = builder.createDataSet(cfg, Lists.newArrayList(getInputAnalysisRuns()));
            softly.assertThat(dataSet.getRowCount()).isOne();
            softly.assertThat(dataSet.getColumnCount()).isOne();
            softly.assertThat(dataSet.getValue(0, 0)).isEqualTo(9);
        });
    }

    /**
     * The number of builds to create the dataset from shouldn't be exceeded.
     */
    @Test
    void buildCountShouldNotBeExceeded() {
        GraphConfiguration cfg = createGraphConfig();
        ResultTime resTime = mock(ResultTime.class);
        SeriesBuilder builder = new HealthSeriesBuilder(createDisabledHealthDescriptor(), resTime);

        // Limit count of runs
        when(cfg.isBuildCountDefined()).thenReturn(true);

        SoftAssertions.assertSoftly(softly -> {
            // Number of builds to add is capped at 1, only add first run
            when(cfg.getBuildCount()).thenReturn(1);
            CategoryDataset dataSet = builder.createDataSet(cfg, getInputAnalysisRuns());
            softly.assertThat(dataSet.getRowCount()).isOne();
            softly.assertThat(dataSet.getColumnCount()).isOne();
            softly.assertThat(dataSet.getValue(0, 0)).isEqualTo(6);

            // Number of builds to add is capped at 7, add all runs
            when(cfg.getBuildCount()).thenReturn(7);
            CategoryDataset dataSet1 = builder.createDataSet(cfg, getInputAnalysisRuns());
            softly.assertThat(dataSet1.getRowCount()).isOne();
            softly.assertThat(dataSet1.getColumnCount()).isEqualTo(5);
            softly.assertThat(dataSet1.getValue(0, 0)).isEqualTo(9);

            // Number of builds to add is capped at 10, add all runs
            when(cfg.getBuildCount()).thenReturn(10);
            CategoryDataset dataSet2 = builder.createDataSet(cfg, getInputAnalysisRuns());
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
            CategoryDataset dataSet = builder.createDataSet(cfg, getInputAnalysisRuns());
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
     * If healthDescriptor is disabled, the total count of issues should be contained
     * in the resulting data set.
     */
    @Test
    void shouldAddRemainderIfNoHealthDescriptor() {
        GraphConfiguration cfg = createGraphConfig();
        ResultTime resTime = mock(ResultTime.class);
        SeriesBuilder builder = new HealthSeriesBuilder(createDisabledHealthDescriptor(), resTime);

        SoftAssertions.assertSoftly(softly-> {
            CategoryDataset dataSet = builder.createDataSet(cfg, getInputAnalysisRuns());
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
     * A series containing triplets should be computed when the health
     * descriptor is enabled.
     */
    @Test
    void shouldComputeTripleOnHealthDescriptorEnabled() {
        GraphConfiguration cfg = createGraphConfig();
        ResultTime resTime = mock(ResultTime.class);
        SeriesBuilder builder = new HealthSeriesBuilder(createHealthDescriptor(), resTime);

        SoftAssertions.assertSoftly(softly-> {
            CategoryDataset dataSet = builder.createDataSet(cfg, getInputAnalysisRuns());
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
     * Provides a set of sample input data:
     *  build 1-2, day 1, avg. 9 issues
     *  build 3, day 2, 12 issues
     *  build 4-5, day 3, avg. 15 issues
     *  build 6, day 4, 9 issues
     *  build 7, day 5, 18 issues
     */
    List<StaticAnalysisRun> getInputAnalysisRuns() {
        StaticAnalysisRun r1 = createBuildResult(1, 1, 2, 3, 1);
        StaticAnalysisRun r2 = createBuildResult(2, 2, 3, 7, 1);
        StaticAnalysisRun r3 = createBuildResult(3, 3, 4, 5, 2);
        StaticAnalysisRun r4 = createBuildResult(4, 6, 7, 8, 3);
        StaticAnalysisRun r5 = createBuildResult(5, 9, 0, 1, 3);
        StaticAnalysisRun r6 = createBuildResult(6, 2, 3, 4, 4);
        StaticAnalysisRun r7 = createBuildResult(7, 5, 6, 7, 5);
        return Lists.newArrayList(r1, r2, r3, r4, r5, r6, r7);
    }

    /**
     * Mocks a GraphConfiguration whose useBuildDateDomain is set to true.
     */
    private GraphConfiguration createGraphConfig() {
        GraphConfiguration graphConfiguration = mock(GraphConfiguration.class);
        when(graphConfiguration.useBuildDateAsDomain()).thenReturn(true);
        return graphConfiguration;
    }

    private HealthDescriptor createDisabledHealthDescriptor() {
        return createHealthDescriptor(10, 20, Priority.NORMAL, false);
    }

    private HealthDescriptor createHealthDescriptor() {
        return createHealthDescriptor(10, 20, Priority.NORMAL, true);
    }

    private HealthDescriptor createHealthDescriptor(int healthy, int unhealthy, Priority minPriority, boolean enabled) {
        HealthDescriptor hc = mock(HealthDescriptor.class);
        when(hc.getHealthy()).thenReturn(healthy);
        when(hc.getUnHealthy()).thenReturn(unhealthy);
        when(hc.getMinimumPriority()).thenReturn(minPriority);
        when(hc.isEnabled()).thenReturn(enabled);
        return hc;
    }

    private StaticAnalysisRun createBuildResult(final int buildNumber, final int numHighPrio,
            final int numNormalPrio, final int numLowPrio, final int day) {
        StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);

        // Priorities
        when(buildResult.getTotalHighPrioritySize()).thenReturn(numHighPrio);
        when(buildResult.getTotalNormalPrioritySize()).thenReturn(numNormalPrio);
        when(buildResult.getTotalLowPrioritySize()).thenReturn(numLowPrio);

        // Total size
        when(buildResult.getTotalSize()).thenReturn(numHighPrio +
                numNormalPrio + numLowPrio);

        AnalysisBuild build = createBuild(buildNumber, day*24*60*60*1000);
        when(buildResult.getBuild()).thenReturn(build);

        return buildResult;
    }

    private AnalysisBuild createBuild(final int number, final long time) {
        AnalysisBuild run = mock(AnalysisBuild.class);
        when(run.getNumber()).thenReturn(number);
        when(run.getDisplayName()).thenReturn("#" + number);
        when(run.getTimeInMillis()).thenReturn(time);
        return run;
    }
}