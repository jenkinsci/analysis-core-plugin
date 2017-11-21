package io.jenkins.plugins.analysis.core.graphs;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Tests the overwriten compute series method from HealthSeriesBuilder. The base class is tested separately.
 */
class HealthSeriesBuilderTest {

    /**
     * Verifies, that in case of less values than the healty threshold, all values are counted as healthy.
     */
    @Test
    void shouldCountAllAsHealthy() {
        HealthDescriptor healthDescriptorStub = mock(HealthDescriptor.class);
        StaticAnalysisRun staticAnalysisRunStub = mock(StaticAnalysisRun.class);
        setupStubs(healthDescriptorStub, staticAnalysisRunStub, true, 5, 6, 5);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptorStub);
        List<Integer> list = sut.computeSeries(staticAnalysisRunStub);

        assertResultListSizeAndValues(list, 3, 5, 0, 0);
    }

    /**
     * Verifies, that in case of a healthy threshold of zero and a high unhealty threshold, all values are counted as
     * "warnings"(second value in the resulting list).
     */
    @Test
    void shouldCountAllAsWarning() {
        HealthDescriptor healthDescriptorStub = mock(HealthDescriptor.class);
        StaticAnalysisRun staticAnalysisRunStub = mock(StaticAnalysisRun.class);
        setupStubs(healthDescriptorStub, staticAnalysisRunStub, true, 0, 5, 3);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptorStub);
        List<Integer> list = sut.computeSeries(staticAnalysisRunStub);

        assertResultListSizeAndValues(list, 3, 0, 3, 0);
    }

    /**
     * Verifies, that in case of an unhealthy threshold of zero, all values are counted as unhealthy (third value in
     * resulting list).
     */
    @Test
    void shouldCountAllAsUnhealthy() {
        HealthDescriptor healthDescriptorStub = mock(HealthDescriptor.class);
        StaticAnalysisRun staticAnalysisRunStub = mock(StaticAnalysisRun.class);
        setupStubs(healthDescriptorStub, staticAnalysisRunStub, true, 0, 0, 3);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptorStub);
        List<Integer> list = sut.computeSeries(staticAnalysisRunStub);

        assertResultListSizeAndValues(list, 3, 0, 0, 3);
    }

    /**
     * Verifies, that in case of more values than healty threshold and unhealthy thresold, the values are counted to the
     * appropriate category.
     */
    @Test
    void shouldCountCorrectlyToAllCategories() {
        HealthDescriptor healthDescriptorStub = mock(HealthDescriptor.class);
        StaticAnalysisRun staticAnalysisRunStub = mock(StaticAnalysisRun.class);
        setupStubs(healthDescriptorStub, staticAnalysisRunStub, true, 1, 2, 3);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptorStub);
        List<Integer> list = sut.computeSeries(staticAnalysisRunStub);

        assertResultListSizeAndValues(list, 3, 1, 1, 1);
    }

    /**
     * Verifies, that in case of a disabled healthDescriptor, all values are counted to the same default category.
     */
    @Test
    void shouldCountAllToTheSameCategory() {
        HealthDescriptor healthDescriptorStub = mock(HealthDescriptor.class);
        StaticAnalysisRun staticAnalysisRunStub = mock(StaticAnalysisRun.class);
        setupStubs(healthDescriptorStub, staticAnalysisRunStub, false, 0, 0, 3);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptorStub);
        List<Integer> list = sut.computeSeries(staticAnalysisRunStub);

        assertResultListSizeAndValues(list, 1, 3, 0, 0);
    }

    private void setupStubs(HealthDescriptor healthDescriptorStub, StaticAnalysisRun staticAnalysisRunStub, boolean enabled, int healtyThreshold, int unhealthyThresold, int totalCount) {
        when(healthDescriptorStub.isEnabled()).thenReturn(enabled);
        when(healthDescriptorStub.getHealthy()).thenReturn(healtyThreshold);
        when(healthDescriptorStub.getUnHealthy()).thenReturn(unhealthyThresold);
        when(staticAnalysisRunStub.getTotalSize()).thenReturn(totalCount);
    }

    private void assertResultListSizeAndValues(List<Integer> list,
            int expectedListSize, int expectedHealthyCount, int expectedMediumCount, int expectedUnhealthyCount) {
        assertThat(list.size()).isEqualTo(expectedListSize);

        if (expectedListSize == 3) {
            assertThat(list.get(0)).isEqualTo(expectedHealthyCount);
            assertThat(list.get(1)).isEqualTo(expectedMediumCount);
            assertThat(list.get(2)).isEqualTo(expectedUnhealthyCount);
        }
        else if (expectedListSize == 1) {
            assertThat(list.get(0)).isEqualTo(expectedHealthyCount);
        }
        else {
            fail("Wrong listSize, only 1 or 3 are supported.");
        }
    }
}