package io.jenkins.plugins.analysis.core.quality;

import java.util.List;
import java.util.function.Consumer;

import org.assertj.core.api.AbstractStandardSoftAssertions;
import org.assertj.core.api.SoftAssertionError;

import static org.assertj.core.api.Assertions.extractProperty;


public class QualityGateSoftAssertion extends AbstractStandardSoftAssertions {

    public QualityGateAssert assertThat(QualityGate actual) {
        return proxy(QualityGateAssert.class, QualityGate.class, actual);
    }

    public void assertAll() {
        List<Throwable> errors = errorsCollected();
        if (!errors.isEmpty()) {
            throw new SoftAssertionError(extractProperty("message", String.class).from(errors));
        }
    }

    public static void assertSoftly(final Consumer<QualityGateSoftAssertion> softly) {
        QualityGateSoftAssertion assertions = new QualityGateSoftAssertion();
        softly.accept(assertions);
        assertions.assertAll();
    }

}
