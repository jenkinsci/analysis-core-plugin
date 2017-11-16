package io.jenkins.plugins.analysis.core.graphs;

import java.util.List;

import org.jfree.data.category.CategoryDataset;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import io.jenkins.plugins.analysis.core.quality.StaticAnalysisRun;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HealthSeriesBuilderTest {


    /** Verifies that the health descriptor is disabled */
    @Test
    void healthDescriptorIsNotEnabled() {
        HealthDescriptor healthDescriptor = mock(HealthDescriptor.class);
        when(healthDescriptor.isEnabled()).thenReturn(false);

        StaticAnalysisRun buildResult = mock(StaticAnalysisRun.class);
        when(buildResult.getTotalSize()).thenReturn(3);

        HealthSeriesBuilder healthSeriesBuilder = new HealthSeriesBuilder(healthDescriptor);
        List<Integer> list = healthSeriesBuilder.computeSeries(buildResult);

        assertThat(list.get(0)).isEqualTo(3);
    }

    private GraphConfiguration createConfiguration() {
        return mock(GraphConfiguration.class);
    }




}