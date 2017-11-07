package io.jenkins.plugins.analysis.core.graphs;

import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests the class {@link HealthSeriesBuilder}.
 *
 * @author Aykut Yilmaz
 */
class HealthSeriesBuilderTest {

    @Test
    void disabledHealthDescriptorWithThreeStaticAnalysisRunsWithTotalSizeOfZeroOneAndTwo() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        StaticAnalysisRun staticAnalysisRun = mock(StaticAnalysisRun.class);

        when(healthDescriptor.isEnabled()).thenReturn(false);
        when(staticAnalysisRun.getTotalSize()).thenReturn(0, 1, 2);
        HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);

        List<Integer> justZero = builder.computeSeries(staticAnalysisRun);
        List<Integer> justOne = builder.computeSeries(staticAnalysisRun);
        List<Integer> justTwo = builder.computeSeries(staticAnalysisRun);

        assertThat(justZero.get(0)).isEqualTo(0);
        assertThat(justOne.get(0)).isEqualTo(1);
        assertThat(justTwo.get(0)).isEqualTo(2);
    }

    @Test
    void remainderBiggerZero() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        StaticAnalysisRun staticAnalysisRun = mock(StaticAnalysisRun.class);

        when(healthDescriptor.isEnabled()).thenReturn(true);

        // range shall be 5
        // and remainder shall be 1
        when(staticAnalysisRun.getTotalSize()).thenReturn(6);
        when(healthDescriptor.getUnHealthy()).thenReturn(10);
        when(healthDescriptor.getHealthy()).thenReturn(5);

        HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);
        List<Integer> containsRemainder = builder.computeSeries(staticAnalysisRun);

        // remainder shall be 6
        when(staticAnalysisRun.getTotalSize()).thenReturn(11);

        List<Integer> containsRange = builder.computeSeries(staticAnalysisRun);
        assertThat(containsRemainder.get(1)).isEqualTo(1);
        assertThat(containsRange.get(1)).isEqualTo(5);
    }

    @Test
    void remainderNotBiggerZero() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        StaticAnalysisRun staticAnalysisRun = mock(StaticAnalysisRun.class);

        when(healthDescriptor.isEnabled()).thenReturn(true);

        when(staticAnalysisRun.getTotalSize()).thenReturn(0);
        when(healthDescriptor.getHealthy()).thenReturn(1);

        HealthSeriesBuilder builder = new HealthSeriesBuilder(healthDescriptor);
        List<Integer> ints = builder.computeSeries(staticAnalysisRun);

        assertThat(ints).containsExactly(0,0,0);
    }
}