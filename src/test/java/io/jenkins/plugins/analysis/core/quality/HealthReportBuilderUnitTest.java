package io.jenkins.plugins.analysis.core.quality;

import edu.hm.hafner.analysis.Priority;
import static io.jenkins.plugins.analysis.core.testutil.Assertions.assertThat;

import hudson.model.HealthReport;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HealthReportBuilderUnitTest {

    /**
     * HealthReportBuilder should not return a HealthReport if HealthDescriptor is disabled.
     */
    @Test
    public void shouldReturnNullWhenHealthDescriptorIsDisabled() {
        HealthReportBuilder builder = createHealthReportBuilderWithDisabledDescriptor(0, 0, Priority.LOW);
        HealthReport healthReport = builder.computeHealth(createPrioritySizeMap(0, 0, 0));

        assertThat(healthReport).isNull();
    }

    /**
     * HealthReportBuilder should return HealthReport with score 100 if: SUM of prioritySizeMap < healthy.
     * SUM of prioritySizeMap is calculated only with priorities higher than the BuilderPriority.
     */
    @Test
    public void shouldReturnScore100ForAllPriorities() {
        HealthReportBuilder lowPrioBuilder = createHealthReportBuilder(10, 20, Priority.LOW);
        HealthReport lowPrioHealthReport = lowPrioBuilder.computeHealth(createPrioritySizeMap(3, 3, 3));
        assertThat(lowPrioHealthReport.getScore()).isEqualTo(100);

        HealthReportBuilder normalPrioBuilder = createHealthReportBuilder(10, 20, Priority.NORMAL);
        HealthReport normalPrioHealthReport = normalPrioBuilder.computeHealth(createPrioritySizeMap(3, 3, 3));
        assertThat(normalPrioHealthReport.getScore()).isEqualTo(100);

        HealthReportBuilder highPrioBuilder = createHealthReportBuilder(10, 20, Priority.HIGH);
        HealthReport highPrioHealthReport = highPrioBuilder.computeHealth(createPrioritySizeMap(3, 3, 3));
        assertThat(highPrioHealthReport.getScore()).isEqualTo(100);
    }

    /**
     * HealthReportBuilder should return HealthReport with score 0 if: healthy < unhealthy < SUM of prioritySizeMap.
     * SUM of prioritySizeMap is calculated only with priorities higher than the BuilderPriority.
     */
    @Test
    public void shouldReturnScore0ForAllPriorities() {
        HealthReportBuilder lowPrioBuilder = createHealthReportBuilder(5, 10, Priority.LOW);
        HealthReport lowPrioHealthReport = lowPrioBuilder.computeHealth(createPrioritySizeMap(5, 5, 5));
        assertThat(lowPrioHealthReport.getScore()).isEqualTo(0);

        HealthReportBuilder normalPrioBuilder = createHealthReportBuilder(5, 10, Priority.NORMAL);
        HealthReport normalPrioHealthReport = normalPrioBuilder.computeHealth(createPrioritySizeMap(7, 7, 0));
        assertThat(normalPrioHealthReport.getScore()).isEqualTo(0);

        HealthReportBuilder highPrioBuilder = createHealthReportBuilder(5, 10, Priority.HIGH);
        HealthReport highPrioHealthReport = highPrioBuilder.computeHealth(createPrioritySizeMap(15, 0, 0));
        assertThat(highPrioHealthReport.getScore()).isEqualTo(0);
    }

    /**
     * HealthReport should have a score of 20.
     * Tests the calculate function inside computeHealth.
     */
    @Test
    public void shouldReturnScore20ForLowPriority() {
        HealthReportBuilder builder = createHealthReportBuilder(10, 20, Priority.LOW);
        HealthReport healthReport = builder.computeHealth(createPrioritySizeMap(5, 5, 8));
        assertThat(healthReport.getScore()).isEqualTo(20);
    }


    /**
     * HealthReport should have a score of 50.
     * Tests the calculate function inside computeHealth.
     */
    @Test
    public void shouldReturnScore50ForNormalPriority() {
        HealthReportBuilder builder = createHealthReportBuilder(10, 20, Priority.NORMAL);
        HealthReport healthReport = builder.computeHealth(createPrioritySizeMap(5, 10, 5));
        assertThat(healthReport.getScore()).isEqualTo(50);
    }


    /**
     * HealthReport should have a score of 80.
     * Tests the calculate function inside computeHealth.
     */
    @Test
    public void shouldReturnScore80ForHighPriority() {
        HealthReportBuilder builder = createHealthReportBuilder(10, 20, Priority.HIGH);
        HealthReport healthReport = builder.computeHealth(createPrioritySizeMap(12, 5, 5));
        assertThat(healthReport.getScore()).isEqualTo(80);
    }

    /**
     * Description of HealthReport should say no warnings if SUM of prioritySizeMap = 0.
     * SUM of prioritySizeMap is calculated only with priorities higher than the BuilderPriority.
     */
    @Test
    public void shouldReturnHealthReportNoItemDescription() {
        HealthReportBuilder builder = createHealthReportBuilder(10, 20, Priority.LOW);
        HealthReport healthReport = builder.computeHealth(createPrioritySizeMap(0, 0, 0));
        assertThat(healthReport.getDescription()).isEqualTo("Static Analysis: no warnings found.");
    }

    /**
     * Description of HealthReport should say 1 warning if SUM of prioritySizeMap = 1.
     * SUM of prioritySizeMap is calculated only with priorities higher than the BuilderPriority.
     */
    @Test
    public void shouldReturnHealthReportSingleItemDescription() {
        HealthReportBuilder builder = createHealthReportBuilder(10, 20, Priority.LOW);
        HealthReport healthReport = builder.computeHealth(createPrioritySizeMap(1, 0, 0));
        assertThat(healthReport.getDescription()).isEqualTo("Static Analysis: 1 warning found.");
    }

    /**
     * Description of HealthReport should say 2 warnings if SUM of prioritySizeMap = 2.
     * SUM of prioritySizeMap is calculated only with priorities higher than the BuilderPriority.
     */
    @Test
    public void shouldReturnHealthReportMultipleItemDescription() {
        HealthReportBuilder builder = createHealthReportBuilder(10, 20, Priority.LOW);
        HealthReport healthReport = builder.computeHealth(createPrioritySizeMap(2, 0, 0));
        assertThat(healthReport.getDescription()).isEqualTo("Static Analysis: 2 warnings found.");
    }

    /**
     * Creates a new HealthReportBuilder with an active descriptor.
     * @param healthy
     * @param unhealthy
     * @param priority
     * @return HealthReportBuilder
     */
    private HealthReportBuilder createHealthReportBuilder(int healthy, int unhealthy, Priority priority) {
        HealthDescriptor descriptor = new HealthDescriptor(healthy, unhealthy, priority);

        assertThat(descriptor.isEnabled()).isTrue();

        return new HealthReportBuilder(descriptor);
    }

    /**
     * Creates a new HealthReportBuilder with an inactive descriptor.
     * @param healthy
     * @param unhealthy
     * @param priority
     * @return
     */
    private HealthReportBuilder createHealthReportBuilderWithDisabledDescriptor(int healthy, int unhealthy, Priority priority) {
        HealthDescriptor descriptor = new HealthDescriptor(healthy, unhealthy, priority);

        assertThat(descriptor.isEnabled()).isFalse();

        return new HealthReportBuilder(descriptor);
    }

    /**
     * Create a new prioritySizeMap based upon the input sizes.
     * @param high
     * @param normal
     * @param low
     * @return
     */
    private Map<Priority, Integer> createPrioritySizeMap(int high, int normal, int low) {
        Map<Priority, Integer> sizePerPriority = new HashMap<>();
        sizePerPriority.put(Priority.HIGH, high);
        sizePerPriority.put(Priority.NORMAL, normal);
        sizePerPriority.put(Priority.LOW, low);
        return sizePerPriority;
    }
}