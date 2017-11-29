package io.jenkins.plugins.analysis.core.quality;

import java.util.List;
import java.util.function.Consumer;

import org.assertj.core.api.AbstractStandardSoftAssertions;
import org.assertj.core.api.SoftAssertionError;

import static org.assertj.core.api.Assertions.*;

/**
 * Custom soft assertions for {@link QualityGate}.
 *
 * @author Ullrich Hafner
 */
public class QualityGateSoftAssertions extends AbstractStandardSoftAssertions {

    /**
     * Verifies that no proxied assertion methods have failed.
     *
     * @throws SoftAssertionError
     *         if any proxied assertion objects threw
     */
    public void assertAll() {
        List<Throwable> errors = errorsCollected();
        if (!errors.isEmpty()) {
            throw new SoftAssertionError(extractProperty("message", String.class).from(errors));
        }
    }

    /**
     * Creates a new {@link QualityGateAssert} to make assertions on actual {@link QualityGate}.
     *
     * @param actual
     *         the issue we want to make assertions on
     *
     * @return a new {@link QualityGateAssert}
     */
    public QualityGateAssert assertThat(final QualityGate actual) {
        return proxy(QualityGateAssert.class, QualityGate.class, actual);
    }

    /**
     * Use this to avoid having to call assertAll manually.
     * <p>
     * <pre><code class='java'> &#064;Test
     * public void host_dinner_party_where_nobody_dies() {
     *   Mansion mansion = new Mansion();
     *   mansion.hostPotentiallyMurderousDinnerParty();
     *   SoftAssertions.assertSoftly(softly -> {
     *     softly.assertThat(mansion.guests()).as(&quot;Living Guests&quot;).isEqualTo(7);
     *     softly.assertThat(mansion.kitchen()).as(&quot;Kitchen&quot;).isEqualTo(&quot;clean&quot;);
     *     softly.assertThat(mansion.library()).as(&quot;Library&quot;).isEqualTo(&quot;clean&quot;);
     *     softly.assertThat(mansion.revolverAmmo()).as(&quot;Revolver Ammo&quot;).isEqualTo(6);
     *     softly.assertThat(mansion.candlestick()).as(&quot;Candlestick&quot;).isEqualTo(&quot;pristine&quot;);
     *     softly.assertThat(mansion.colonel()).as(&quot;Colonel&quot;).isEqualTo(&quot;well kempt&quot;);
     *     softly.assertThat(mansion.professor()).as(&quot;Professor&quot;).isEqualTo(&quot;well kempt&quot;);
     *   });
     * }</code></pre>
     *
     * @param softly
     *         the SoftAssertions instance that you can call your own assertions on.
     *
     * @throws SoftAssertionError
     *         if any proxied assertion objects threw
     */
    public static void assertSoftly(final Consumer<QualityGateSoftAssertions> softly) {
        QualityGateSoftAssertions assertions = new QualityGateSoftAssertions();
        softly.accept(assertions);
        assertions.assertAll();
    }
}
