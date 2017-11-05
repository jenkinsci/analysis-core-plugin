package io.jenkins.plugins.analysis.core.graphs;


import java.util.Arrays;
import java.util.List;

import org.assertj.core.api.ListAssert;
import org.junit.jupiter.api.Test;

import edu.hm.hafner.analysis.Priority;
import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class HealthSeriesBuilderTest {
    @Test
    void firstTest() {
        HealthDescriptor healthDescriptor = new HealthDescriptor("4", "6", Priority.LOW);
        StaticAnalysisRun staticAnalysisRun = mock(StaticAnalysisRun.class);
        when(staticAnalysisRun.getTotalSize()).thenReturn(10);
        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptor);
        List<Integer> result = sut.computeSeries(staticAnalysisRun);
        System.out.println(result);
    }

    @Test
    void reportingDisabled() {
        HealthDescriptor healthDescriptor = new HealthDescriptor("1", "0", Priority.NORMAL);
        StaticAnalysisRun staticAnalysisRun = mock(StaticAnalysisRun.class);
        when(staticAnalysisRun.getTotalSize()).thenReturn(1);
        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptor);
        List<Integer> result = sut.computeSeries(staticAnalysisRun);
        assertThat(result).containsExactly(1);
    }

    @Test
    void allHealthy() {
        HealthDescriptor healthDescriptor = new HealthDescriptor("9", "0", Priority.NORMAL);
        StaticAnalysisRun staticAnalysisRun = mock(StaticAnalysisRun.class);
        when(staticAnalysisRun.getTotalSize()).thenReturn(9);
        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptor);
        List<Integer> result = sut.computeSeries(staticAnalysisRun);
        assertThat(result).containsExactly(9, 0, 0);
    }

    /*
    healthDescriptor disables (healthy >= unhealthy)
    healthy > unhealty
    healthy == unhealty

    healthDescriptor enabled (healthy < unhealthy)
	total == healthy -> (healty, 0, 0)
	total == unhealthy -> (healty, unhealthy - healthy ,0)
	total > unhealthy -> (healty, unhealthy - healthy, total - unhealthy)
	unhealty > total > healthy -> (healthy, total - healthy, 0)
	total < healty -> (total, 0 ,0)
     */
}