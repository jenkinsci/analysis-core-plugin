package hudson.plugins.analysis.collector;

import hudson.plugins.analysis.core.AbstractHealthDescriptor;
import hudson.plugins.analysis.core.HealthDescriptor;
import hudson.plugins.analysis.util.model.AnnotationProvider;

import org.jvnet.localizer.Localizable;

/**
 * A health descriptor for the collected analysis results.
 *
 * @author Ulli Hafner
 */
public class AnalysisHealthDescriptor extends AbstractHealthDescriptor {
    /** Unique ID of this class. */
    private static final long serialVersionUID = -4517492804694055774L;

    /**
     * Creates a new instance of {@link AnalysisHealthDescriptor} based on the
     * values of the specified descriptor.
     *
     * @param healthDescriptor the descriptor to copy the values from
     */
    public AnalysisHealthDescriptor(final HealthDescriptor healthDescriptor) {
        super(healthDescriptor);
    }

    /** {@inheritDoc} */
    @Override
    protected Localizable createDescription(final AnnotationProvider result) {
        if (result.getNumberOfAnnotations() == 0) {
            return Messages._Analysis_ResultAction_HealthReportNoItem();
        }
        else if (result.getNumberOfAnnotations() == 1) {
            return Messages._Analysis_ResultAction_HealthReportSingleItem();
        }
        else {
            return Messages._Analysis_ResultAction_HealthReportMultipleItem(result.getNumberOfAnnotations());
        }
    }
}

