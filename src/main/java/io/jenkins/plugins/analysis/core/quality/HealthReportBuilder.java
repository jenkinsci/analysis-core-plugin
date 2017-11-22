package io.jenkins.plugins.analysis.core.quality;

import java.io.Serializable;

import org.jvnet.localizer.Localizable;

import edu.hm.hafner.analysis.Priority;
import io.jenkins.plugins.analysis.core.steps.AnalysisResult;

import hudson.model.HealthReport;
import hudson.plugins.analysis.Messages;

/**
 * Creates a health report for integer values based on healthy and unhealthy thresholds.
 *
 * @author Ulli Hafner
 * @see HealthReport
 */
public class HealthReportBuilder implements Serializable {
    /** Unique identifier of this class. */
    private static final long serialVersionUID = 5191317904662711835L;
    /** Health descriptor. */
    private final HealthDescriptor healthDescriptor;

    /**
     * Creates a new instance of {@link HealthReportBuilder}.
     *
     * @param healthDescriptor
     *         health descriptor
     */
    public HealthReportBuilder(final HealthDescriptor healthDescriptor) {
        this.healthDescriptor = healthDescriptor;
    }

    /**
     * Computes the healthiness of a build based on the specified results. Reports a health of 100% when the specified
     * counter is less than {@link HealthDescriptor#getHealthy()}. Reports a health of 0% when the specified counter is
     * greater than {@link HealthDescriptor#getUnHealthy()}. The computation takes only annotations of the specified
     * severity into account.
     *
     * @param result
     *         annotations of the current build
     *
     * @return the healthiness of a build
     */
    public HealthReport computeHealth(final AnalysisResult result) {
        int numberOfAnnotations = 0;
        for (Priority priority : Priority.collectPrioritiesFrom(healthDescriptor.getMinimumPriority())) {
            numberOfAnnotations += result.getTotalSize(priority);
        }

        if (healthDescriptor.isEnabled()) {
            int percentage;
            int healthy = healthDescriptor.getHealthy();
            if (numberOfAnnotations < healthy) {
                percentage = 100;
            }
            else {
                int unHealthy = healthDescriptor.getUnHealthy();
                if (numberOfAnnotations > unHealthy) {
                    percentage = 0;
                }
                else {
                    percentage = 100 - ((numberOfAnnotations - healthy) * 100 / (unHealthy - healthy));
                }
            }

            return new HealthReport(percentage, getDescription(result));
        }
        return null;
    }


    private Localizable getDescription(final AnalysisResult result) {
        String name = "Static Analysis"; // FIXME: extract from IssueParser.find(id)
        if (result.getTotalSize() == 0) {
            return Messages._ResultAction_HealthReportNoItem(name);
        }
        else if (result.getTotalSize() == 1) {
            return Messages._ResultAction_HealthReportSingleItem(name);
        }
        else {
            return Messages._ResultAction_HealthReportMultipleItem(name, result.getTotalSize());
        }
    }
}

