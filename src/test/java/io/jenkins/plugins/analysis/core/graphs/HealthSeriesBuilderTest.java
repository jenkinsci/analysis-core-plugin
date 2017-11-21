package io.jenkins.plugins.analysis.core.graphs;

import java.util.List;

import org.junit.jupiter.api.Test;

import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class HealthSeriesBuilderTest {

    @Test
    void testHealthyCategoryGetsAll() {
        HealthDescriptor healthDescriptorStub = mock(HealthDescriptor.class);
        StaticAnalysisRun staticAnalysisRunStub = mock(StaticAnalysisRun.class);
        setupStubs(healthDescriptorStub, staticAnalysisRunStub, true, 5, 6, 5);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptorStub);
        List<Integer> list = sut.computeSeries(staticAnalysisRunStub);

        assertResultListSizeAndValues(list, 3, 5, 0, 0);
    }

    @Test
    void testWarningsCategoryGetsAll() {
        HealthDescriptor healthDescriptorStub = mock(HealthDescriptor.class);
        StaticAnalysisRun staticAnalysisRunStub = mock(StaticAnalysisRun.class);
        setupStubs(healthDescriptorStub, staticAnalysisRunStub, true, 0, 5, 3);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptorStub);
        List<Integer> list = sut.computeSeries(staticAnalysisRunStub);

        assertResultListSizeAndValues(list, 3, 0, 3, 0);
    }

    @Test
    void testUnhealthyCategoryGetsAll() {
        HealthDescriptor healthDescriptorStub = mock(HealthDescriptor.class);
        StaticAnalysisRun staticAnalysisRunStub = mock(StaticAnalysisRun.class);
        setupStubs(healthDescriptorStub, staticAnalysisRunStub, true, 0, 0, 3);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptorStub);
        List<Integer> list = sut.computeSeries(staticAnalysisRunStub);

        assertResultListSizeAndValues(list, 3, 0, 0, 3);
    }

    @Test
    void testEachCategoryGetsOne() {
        HealthDescriptor healthDescriptorStub = mock(HealthDescriptor.class);
        StaticAnalysisRun staticAnalysisRunStub = mock(StaticAnalysisRun.class);
        setupStubs(healthDescriptorStub, staticAnalysisRunStub, true, 1, 2, 3);

        HealthSeriesBuilder sut = new HealthSeriesBuilder(healthDescriptorStub);
        List<Integer> list = sut.computeSeries(staticAnalysisRunStub);

        assertResultListSizeAndValues(list, 3, 1, 1, 1);
    }

    @Test
    void testHealthDescriptorDisabled() {
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