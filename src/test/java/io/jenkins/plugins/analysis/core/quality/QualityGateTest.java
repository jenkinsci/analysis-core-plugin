package io.jenkins.plugins.analysis.core.quality;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import hudson.model.Result;

/**
 * Tests the classes {@link QualityGate}, {@link QualityGateBuilder}.
 * @author Raphael Furch
 */
class QualityGateTest {


    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndNoQualityGateIsSet() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) -> new QualityGateBuilder();
        testWithAllPriorities(builder, 3,3,0,0, Result.SUCCESS, Result.SUCCESS);
    }

    @Test
    void shouldBeSuccessfulWhenNoIssuesPresentAndFailureQualityGateIsSet() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                .setTotalBuildsUnstableThresholds(totals)
                .setTotalBuildsFailureThresholds(totals)
                .setNewBuildsUnstableThresholds(news)
                .setNewBuildsFailureThresholds(news);
        testWithAllPriorities(builder, 0,0, 10,10, Result.SUCCESS, Result.SUCCESS);
    }


    //<editor-fold desc="Unstable">
    //<editor-fold desc="Greater then size">
    @Test
    void shouldBeUnstableWhenTotalWarningThresholdIsSmallerThenTotalSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsUnstableThresholds(totals)
                        .setNewBuildsUnstableThresholds(news);
        testWithAllPriorities(builder, 3,0, 2,-1, Result.UNSTABLE, Result.SUCCESS);
    }

    @Test
    void shouldBeUnstableWhenNewsWarningThresholdIsSmallerThenTheirsSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsUnstableThresholds(totals)
                        .setNewBuildsUnstableThresholds(news);
        testWithAllPriorities(builder, 0,5, -1,4, Result.SUCCESS, Result.UNSTABLE);
    }

    @Test
    void shouldBeUnstableWhenTotalAndNewsWarningThresholdIsSmallerThenNewsSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsUnstableThresholds(totals)
                        .setNewBuildsUnstableThresholds(news);
        testWithAllPriorities(builder, 4,3, 3,2, Result.UNSTABLE, Result.UNSTABLE);
    }
    //</editor-fold>

    //<editor-fold desc="Equals then size">
    @Test
    void shouldStableWhenTotalWarningThresholdIsEqualThenTotalSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsUnstableThresholds(totals)
                        .setNewBuildsUnstableThresholds(news);
        testWithAllPriorities(builder, 3,0, 3,-1, Result.SUCCESS, Result.SUCCESS);
    }

    @Test
    void shouldStableWhenNewsWarningThresholdIsEqualThenTheirsSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsUnstableThresholds(totals)
                        .setNewBuildsUnstableThresholds(news);
        testWithAllPriorities(builder, 0,3, -1,3, Result.SUCCESS, Result.SUCCESS);
    }

    @Test
    void shouldStableWhenTotalAndNewsWarningThresholdIsEqualThenNewsSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsUnstableThresholds(totals)
                        .setNewBuildsUnstableThresholds(news);
        testWithAllPriorities(builder, 4,3, 4,3, Result.SUCCESS, Result.SUCCESS);
    }
    //</editor-fold>

    //<editor-fold desc="Smaller then size">
    @Test
    void shouldStableWhenTotalWarningThresholdIsGreaterThenTotalSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsUnstableThresholds(totals)
                        .setNewBuildsUnstableThresholds(news);
        testWithAllPriorities(builder, 2,0, 3,-1, Result.SUCCESS, Result.SUCCESS);
    }

    @Test
    void shouldStableWhenNewsWarningThresholdIsGreaterThenTheirsSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsUnstableThresholds(totals)
                        .setNewBuildsUnstableThresholds(news);
        testWithAllPriorities(builder, 0,2, -1,3, Result.SUCCESS, Result.SUCCESS);
    }

    @Test
    void shouldStableWhenTotalAndNewsWarningThresholdIsGreaterThenNewsSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsUnstableThresholds(totals)
                        .setNewBuildsUnstableThresholds(news);
        testWithAllPriorities(builder, 2,3, 4,5, Result.SUCCESS, Result.SUCCESS);
    }
    //</editor-fold>
    //</editor-fold>

    //<editor-fold desc="Failure">
    //<editor-fold desc="Greater then size">
    @Test
    void shouldFailWhenTotalWarningThresholdIsSmallerThenTotalSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsFailureThresholds(totals)
                        .setNewBuildsFailureThresholds(news);
        testWithAllPriorities(builder, 3,0, 2,-1, Result.FAILURE, Result.SUCCESS);
    }

    @Test
    void shouldFailWhenNewsWarningThresholdIsSmallerThenTheirsSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsFailureThresholds(totals)
                        .setNewBuildsFailureThresholds(news);
        testWithAllPriorities(builder, 0,5, -1,4, Result.SUCCESS, Result.FAILURE);
    }

    @Test
    void shouldFailWhenTotalAndNewsWarningThresholdIsSmallerThenNewsSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsFailureThresholds(totals)
                        .setNewBuildsFailureThresholds(news);
        testWithAllPriorities(builder, 4,3, 3,2, Result.FAILURE, Result.FAILURE);
    }
    //</editor-fold>

    //<editor-fold desc="Equals then size">
    @Test
    void shouldSuccessfulWhenTotalWarningThresholdIsEqualThenTotalSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsFailureThresholds(totals)
                        .setNewBuildsFailureThresholds(news);
        testWithAllPriorities(builder, 3,0, 3,-1, Result.SUCCESS, Result.SUCCESS);
    }

    @Test
    void shouldSuccessfulWhenNewsWarningThresholdIsEqualThenTheirsSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsFailureThresholds(totals)
                        .setNewBuildsFailureThresholds(news);
        testWithAllPriorities(builder, 0,3, -1,3, Result.SUCCESS, Result.SUCCESS);
    }

    @Test
    void shouldSuccessfulWhenTotalAndNewsWarningThresholdIsEqualThenNewsSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsFailureThresholds(totals)
                        .setNewBuildsFailureThresholds(news);
        testWithAllPriorities(builder, 4,3, 4,3, Result.SUCCESS, Result.SUCCESS);
    }
    //</editor-fold>

    //<editor-fold desc="Smaller then size">
    @Test
    void shouldSucessfulWhenTotalWarningThresholdIsGreaterThenTotalSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsFailureThresholds(totals)
                        .setNewBuildsFailureThresholds(news);
        testWithAllPriorities(builder, 2,0, 3,-1, Result.SUCCESS, Result.SUCCESS);
    }

    @Test
    void shouldSuccessfulWhenNewsWarningThresholdIsGreaterThenTheirsSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsFailureThresholds(totals)
                        .setNewBuildsFailureThresholds(news);
        testWithAllPriorities(builder, 0,2, -1,3, Result.SUCCESS, Result.SUCCESS);
    }

    @Test
    void shouldSuccessfulWhenTotalAndNewsWarningThresholdIsGreaterThenNewsSize() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsFailureThresholds(totals)
                        .setNewBuildsFailureThresholds(news);
        testWithAllPriorities(builder, 2,3, 4,5, Result.SUCCESS, Result.SUCCESS);
    }
    //</editor-fold>
    //</editor-fold>


    @Test
    void failShouldDefeatsUnstableWhenBothTotalThresholdsExceeded() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setTotalBuildsUnstableThresholds(totals)
                        .setTotalBuildsFailureThresholds(totals);
        testWithAllPriorities(builder, 3,0, 2,-1, Result.FAILURE, Result.SUCCESS);
    }

    @Test
    void failShouldDefeatsUnstableWhenBothNewsThresholdsExceeded() {
        BiFunction<ThresholdSet, ThresholdSet, QualityGateBuilder> builder = (totals, news) ->
                new QualityGateBuilder()
                        .setNewBuildsUnstableThresholds(news)
                        .setNewBuildsFailureThresholds(news);
        testWithAllPriorities(builder, 0,3, -1,2, Result.SUCCESS, Result.FAILURE);
    }

    @Test
    void failFromTotalShouldDefeatsUnstableFromNewsWhenBothExceededThresholds() {
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(4);
        when(run.getTotalHighPrioritySize()).thenReturn(4);
        when(run.getTotalNormalPrioritySize()).thenReturn(4);
        when(run.getTotalLowPrioritySize()).thenReturn(4);
        when(run.getNewSize()).thenReturn(5);
        when(run.getNewHighPrioritySize()).thenReturn(5);
        when(run.getNewNormalPrioritySize()).thenReturn(5);
        when(run.getNewLowPrioritySize()).thenReturn(5);

        Result fail = new QualityGateBuilder()
                .setTotalBuildsFailureThresholds(new ThresholdSet(2,2,2,2))
                .setNewBuildsUnstableThresholds(new ThresholdSet(3,3,3,3))
                .createQualityGate()
                .evaluate(run);

        assertThat(fail).isEqualTo(Result.FAILURE);
    }

    @Test
    void failFromNewShouldDefeatsUnstableFromTotalWhenBothExceededThresholds() {
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(4);
        when(run.getTotalHighPrioritySize()).thenReturn(4);
        when(run.getTotalNormalPrioritySize()).thenReturn(4);
        when(run.getTotalLowPrioritySize()).thenReturn(4);
        when(run.getNewSize()).thenReturn(5);
        when(run.getNewHighPrioritySize()).thenReturn(5);
        when(run.getNewNormalPrioritySize()).thenReturn(5);
        when(run.getNewLowPrioritySize()).thenReturn(5);

        Result fail = new QualityGateBuilder()
                .setTotalBuildsUnstableThresholds(new ThresholdSet(2,2,2,2))
                .setNewBuildsFailureThresholds(new ThresholdSet(3,3,3,3))
                .createQualityGate()
                .evaluate(run);

        assertThat(fail).isEqualTo(Result.FAILURE);
    }

    /**
     * Tests testcase for all priorities.
     * @param fillBuilderTemplate = template for needed thresholds.
     * @param totalValue = count of totalBuilds with warnings.
     * @param newsValue = count of newBuilds with warnings.
     * @param totalWarningThreshold = warningThreshold for all total priorities.
     * @param newWarningThreshold = warningThreshold for all new priorities.
     * @param assertedResultTotal = asserted result for total.
     * @param assertedResultNew = asserted resulted for new.
     */
    private void testWithAllPriorities(final BiFunction<ThresholdSet,ThresholdSet, QualityGateBuilder> fillBuilderTemplate, int totalValue, int newsValue, int totalWarningThreshold, int newWarningThreshold, Result assertedResultTotal, Result assertedResultNew){

        // Create a quality gate for all priority group.
        QualityGate allPriorities = fillBuilderTemplate.apply(  new ThresholdSet(totalWarningThreshold,-1,-1,-1),
                                                                new ThresholdSet(newWarningThreshold,-1,-1,-1))
                                                                .createQualityGate();

        QualityGate highPriority = fillBuilderTemplate.apply(   new ThresholdSet(-1,totalWarningThreshold,-1,-1),
                                                                new ThresholdSet(-1,newWarningThreshold,-1,-1))
                                                                .createQualityGate();

        QualityGate normalPriority = fillBuilderTemplate.apply( new ThresholdSet(-1,-1,totalWarningThreshold,-1),
                                                                new ThresholdSet(-1,-1,newWarningThreshold,-1))
                                                                .createQualityGate();

        QualityGate lowPriority = fillBuilderTemplate.apply(    new ThresholdSet(-1,-1,-1,totalWarningThreshold),
                                                                new ThresholdSet(-1,-1,-1, newWarningThreshold))
                                                                .createQualityGate();

        // Create a AnalysisRun for all priority group total and new.
        // Total
        StaticAnalysisRun runTotalSize = mock(StaticAnalysisRun.class);
        when(runTotalSize.getTotalSize()).thenReturn(totalValue);

        StaticAnalysisRun runTotalHighPriority = mock(StaticAnalysisRun.class);
        when(runTotalHighPriority.getTotalHighPrioritySize()).thenReturn(totalValue);

        StaticAnalysisRun runTotalNormalPriority = mock(StaticAnalysisRun.class);
        when(runTotalNormalPriority.getTotalNormalPrioritySize()).thenReturn(totalValue);

        StaticAnalysisRun runTotalLowPriority = mock(StaticAnalysisRun.class);
        when(runTotalLowPriority.getTotalLowPrioritySize()).thenReturn(totalValue);

        // New
        StaticAnalysisRun runNewSize = mock(StaticAnalysisRun.class);
        when(runNewSize.getNewSize()).thenReturn(newsValue);

        StaticAnalysisRun runNewHighPriority = mock(StaticAnalysisRun.class);
        when(runNewHighPriority.getNewHighPrioritySize()).thenReturn(newsValue);

        StaticAnalysisRun runNewNormalPriority = mock(StaticAnalysisRun.class);
        when(runNewNormalPriority.getNewNormalPrioritySize()).thenReturn(newsValue);

        StaticAnalysisRun runNewLowPriority = mock(StaticAnalysisRun.class);
        when(runNewLowPriority.getNewLowPrioritySize()).thenReturn(newsValue);


        // Check
        SoftAssertions softly = new SoftAssertions();
        // Total Size
        softly.assertThat(allPriorities.evaluate(runTotalSize))
                .as("TotalSize was <%d> and TotalSizeThreshold <%d> thus ",totalValue, totalWarningThreshold)
                .isEqualTo(assertedResultTotal);
        // New Size
        softly.assertThat(allPriorities.evaluate(runNewSize))
                .as("NewSize was <%d> and NewSizeThreshold <%d> thus ",newsValue, newWarningThreshold)
                .isEqualTo(assertedResultNew);
        // Total high priority
        softly.assertThat(highPriority.evaluate(runTotalHighPriority))
                .as("TotalHighPrioritySize was <%d> and TotalSizeThreshold <%d> thus ",totalValue, totalWarningThreshold)
                .isEqualTo(assertedResultTotal);
        // New high priority
        softly.assertThat(highPriority.evaluate(runNewHighPriority))
                .as("NewHighPrioritySize was <%d> and NewSizeThreshold <%d> thus ",newsValue, newWarningThreshold)
                .isEqualTo(assertedResultNew);
        // Total normal priority
        softly.assertThat(normalPriority.evaluate(runTotalNormalPriority))
                .as("TotalNormalPrioritySize was <%d> and TotalSizeThreshold <%d> thus ",totalValue, totalWarningThreshold)
                .isEqualTo(assertedResultTotal);
        // New normal priority
        softly.assertThat(normalPriority.evaluate(runNewNormalPriority))
                .as("NewNormalPrioritySize was <%d> and NewSizeThreshold <%d> thus ",newsValue, newWarningThreshold)
                .isEqualTo(assertedResultNew);
        // Total low priority
        softly.assertThat(lowPriority.evaluate(runTotalLowPriority))
                .as("TotalLowPrioritySize was <%d> and TotalSizeThreshold <%d> thus ",totalValue, totalWarningThreshold)
                .isEqualTo(assertedResultTotal);
        // New low priority
        softly.assertThat(lowPriority.evaluate(runNewLowPriority))
                .as("NewLowPrioritySize was <%d> and NewSizeThreshold <%d> thus the ",newsValue, newWarningThreshold)
                .isEqualTo(assertedResultNew);

        softly.assertAll();
    }


}
