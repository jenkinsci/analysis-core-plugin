package io.jenkins.plugins.analysis.core.graphs;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for {@link HealthSeriesBuilder}.
 *
 * @author Marcel Binder
 */
class HealthSeriesBuilderTest {
    private static final int HEALTHY_THRESHOLD = 2;
    private static final int UNHEALTHY_THRESHOLD = 8;

    @Test
    void healthReportingDisabled() {
        HealthSeriesBuilder builder = createBuilder(healthReportDisabled());
        StaticAnalysisRun run = createRunWithWarnings(5);

        List<Integer> series = builder.computeSeries(run);

        assertThat(series).containsExactly(5);
    }

    @Test
    void noIssues() {
        HealthSeriesBuilder builder = createBuilder(healthReportEnabled());
        StaticAnalysisRun run = createRunWithWarnings(0);

        List<Integer> series = builder.computeSeries(run);

        assertThat(series).containsExactly(0, 0, 0);
    }

    @Test
    void issuesHealthy() {
        HealthSeriesBuilder builder = createBuilder(healthReportEnabled());
        StaticAnalysisRun run = createRunWithWarnings(HEALTHY_THRESHOLD - 1);

        List<Integer> series = builder.computeSeries(run);

        assertThat(series).containsExactly(1, 0, 0);
    }

    @Test
    void issuesStillHealthy() {
        HealthSeriesBuilder builder = createBuilder(healthReportEnabled());
        StaticAnalysisRun run = createRunWithWarnings(HEALTHY_THRESHOLD);

        List<Integer> series = builder.computeSeries(run);

        assertThat(series).containsExactly(2, 0, 0);
    }

    @Test
    void issuesYellow() {
        HealthSeriesBuilder builder = createBuilder(healthReportEnabled());
        StaticAnalysisRun run = createRunWithWarnings(HEALTHY_THRESHOLD + 1);

        List<Integer> series = builder.computeSeries(run);

        assertThat(series).containsExactly(2, 1, 0);
    }

    @Test
    void issuesStillYellow() {
        HealthSeriesBuilder builder = createBuilder(healthReportEnabled());
        StaticAnalysisRun run = createRunWithWarnings(UNHEALTHY_THRESHOLD);

        List<Integer> series = builder.computeSeries(run);

        assertThat(series).containsExactly(2, 6, 0);
    }

    @Test
    void issuesUnhealthy() {
        HealthSeriesBuilder builder = createBuilder(healthReportEnabled());
        StaticAnalysisRun run = createRunWithWarnings(UNHEALTHY_THRESHOLD + 1);

        List<Integer> series = builder.computeSeries(run);

        assertThat(series).containsExactly(2, 6, 1);
    }
    
    private HealthDescriptor healthReportDisabled() {
        return mock(HealthDescriptor.class);
    }

    private HealthDescriptor healthReportEnabled() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(true);
        when(healthDescriptor.getHealthy()).thenReturn(HEALTHY_THRESHOLD);
        when(healthDescriptor.getUnHealthy()).thenReturn(UNHEALTHY_THRESHOLD);
        return healthDescriptor;
    }
    
    private HealthSeriesBuilder createBuilder(HealthDescriptor healthDescriptor) {
        return new HealthSeriesBuilder(healthDescriptor);
    }

    private StaticAnalysisRun createRunWithWarnings(final int totalSize) {
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        when(run.getTotalSize()).thenReturn(totalSize);
        return run;
    }
}