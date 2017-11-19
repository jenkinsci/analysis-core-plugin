package io.jenkins.plugins.analysis.core.graphs;

import io.jenkins.plugins.analysis.core.quality.HealthDescriptor;
import static org.mockito.Mockito.*;

/**
 * Generator for a Healthy Stub.
 */
class GenerateHealthStub {
    /**
     * Generate a stub HealthDescriptor.
     *
     * @param enabled
     *         stub return this on isEnabled
     * @param healthy
     *         stub return this on getHealthy
     * @param unhealthy
     *         stub return this on getUnHealthy
     *
     * @return stub HealthDescriptor
     */
    static HealthDescriptor generateHealthDescriptor(boolean enabled, int healthy, int unhealthy) {
        HealthDescriptor healthDiscStub = mock(HealthDescriptor.class);
        when(healthDiscStub.isEnabled()).thenReturn(enabled);
        when(healthDiscStub.getHealthy()).thenReturn(healthy);
        when(healthDiscStub.getUnHealthy()).thenReturn(unhealthy);
        return healthDiscStub;
    }
}
