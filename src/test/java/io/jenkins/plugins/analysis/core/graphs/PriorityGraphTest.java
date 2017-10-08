package io.jenkins.plugins.analysis.core.graphs;

import java.util.List;

import org.jfree.data.category.CategoryDataset;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

import io.jenkins.plugins.analysis.core.steps.AnalysisResult;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import hudson.model.Run;
import hudson.plugins.analysis.util.model.Priority;

/**
 * Tests the class {@link PriorityGraph}.
 *
 * @author Ullrich Hafner
 */
class PriorityGraphTest {
    /** Verifies that an empty list of runs produces no data. */
    @Test
    void shouldHaveEmptyDataSetForEmptyIterator() {
        PrioritySeriesBuilder builder = new PrioritySeriesBuilder();

        CategoryDataset dataSet = builder.createDataSet(createConfiguration(), Lists.newArrayList());

        assertThat(dataSet.getColumnCount()).isEqualTo(0);
        assertThat(dataSet.getRowCount()).isEqualTo(0);
    }

    private GraphConfiguration createConfiguration() {
        return mock(GraphConfiguration.class);
    }

    /** Verifies that a list with one run produces one column with columns containing the issues per priority. */
    @Test
    void shouldHaveThreeValuesForSingleBuild() {
        PrioritySeriesBuilder builder = new PrioritySeriesBuilder();

        AnalysisResult singleResult = createBuildResult(1, 1, 2, 3);
        List<AnalysisResult> results = Lists.newArrayList(singleResult);
        CategoryDataset dataSet = builder.createDataSet(createConfiguration(), results);

        assertThat(dataSet.getColumnCount()).isEqualTo(1);
        assertThat(dataSet.getRowCount()).isEqualTo(3);

        assertThat(dataSet.getColumnKey(0).toString()).isEqualTo("#1");

        assertThat(dataSet.getValue(0, 0)).isEqualTo(3);
        assertThat(dataSet.getValue(1, 0)).isEqualTo(2);
        assertThat(dataSet.getValue(2, 0)).isEqualTo(1);
    }

    private AnalysisResult createBuildResult(final int buildNumber, final int numberOfHighPriorityIssues,
                                          final int numberOfNormalPriorityIssues, final int numberOfLowPriorityIssues) {
        AnalysisResult buildResult = mock(AnalysisResult.class);

        when(buildResult.getNumberOfAnnotations(Priority.HIGH)).thenReturn(numberOfHighPriorityIssues);
        when(buildResult.getNumberOfAnnotations(Priority.NORMAL)).thenReturn(numberOfNormalPriorityIssues);
        when(buildResult.getNumberOfAnnotations(Priority.LOW)).thenReturn(numberOfLowPriorityIssues);

        Run run = createRun(buildNumber);
        when(buildResult.getRun()).thenReturn(run);

        return buildResult;
    }

    private Run<?, ?> createRun(final int number) {
        Run run = mock(Run.class);
        when(run.getNumber()).thenReturn(number);
        when(run.getDisplayName()).thenReturn("#" + number);
        return run;
    }
}