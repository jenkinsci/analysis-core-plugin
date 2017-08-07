package hudson.plugins.analysis.core;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.junit.Test;
import org.kohsuke.stapler.RequestImpl;
import org.kohsuke.stapler.Stapler;
import org.kohsuke.stapler.StaplerRequest;

import hudson.plugins.analysis.core.GlobalSettings.DescriptorImpl;

/**
 * Tests the class {@link GlobalSettings}.
 *
 * @author Ulli Hafner
 */
public class GlobalSettingsTest {
    /**
     * Verifies that we can convert an array of configurations.
     */
    @Test
    public void verifyJsonConversionNoConfigurationElement() {
        String input = "{\"quietMode\":false,\"failOnCorrupt\":true}";
        DescriptorImpl settings = convert(input);

        AnalysisConfiguration[] configurations = settings.getConfigurations();
        assertEquals("Wrong number of configurations", 0, configurations.length);

        verifyBooleans(settings, false, true);
    }

    /**
     * Verifies that we can convert an array of configurations.
     */
    @Test
    public void verifyJsonConversionOneConfigurationElement() {
        String input = "{\"quietMode\":true,\"failOnCorrupt\":true,\"configurations\":"
                + "{\"name\":\"Eins\",\"canRunOnFailed\":false,\"defaultEncoding\":\"\",\"shouldDetectModules\":false,\"healthy\":\"42\",\"unHealthy\":\"\",\"thresholdLimit\":\"low\",\"unstableTotalAll\":\"\",\"unstableTotalHigh\":\"\",\"unstableTotalNormal\":\"\",\"unstableTotalLow\":\"\",\"failedTotalAll\":\"\",\"failedTotalHigh\":\"\",\"failedTotalNormal\":\"\",\"failedTotalLow\":\"\"}}";
        DescriptorImpl settings = convert(input);

        AnalysisConfiguration[] configurations = settings.getConfigurations();
        assertEquals("Wrong number of configurations", 1, configurations.length);

        assertEquals("Wrong name of configuration", "Eins", configurations[0].getName());
        assertEquals("Wrong value for healthy", "42", configurations[0].getHealthy());

        verifyBooleans(settings, true, true);
    }

    /**
     * Verifies that we can convert an array of configurations.
     */
    @Test
    public void verifyJsonConversionTwoConfigurationElements() {
        String input = "{\"quietMode\":true,\"failOnCorrupt\":false,\"emptyGraphByDefault\":true,\"configurations\":["
                + "{\"name\":\"Eins\",\"canRunOnFailed\":false,\"defaultEncoding\":\"\",\"shouldDetectModules\":false,\"healthy\":\"\",\"unHealthy\":\"\",\"thresholdLimit\":\"low\",\"unstableTotalAll\":\"\",\"unstableTotalHigh\":\"\",\"unstableTotalNormal\":\"\",\"unstableTotalLow\":\"\",\"failedTotalAll\":\"\",\"failedTotalHigh\":\"\",\"failedTotalNormal\":\"\",\"failedTotalLow\":\"\"},"
                + "{\"name\":\"Zwei\",\"canRunOnFailed\":false,\"defaultEncoding\":\"\",\"shouldDetectModules\":false,\"healthy\":\"\",\"unHealthy\":\"\",\"thresholdLimit\":\"low\",\"unstableTotalAll\":\"\",\"unstableTotalHigh\":\"\",\"unstableTotalNormal\":\"\",\"unstableTotalLow\":\"\",\"failedTotalAll\":\"\",\"failedTotalHigh\":\"\",\"failedTotalNormal\":\"\",\"failedTotalLow\":\"\",\"canComputeNew\":{\"unstableNewAll\":\"\",\"unstableNewHigh\":\"\",\"unstableNewNormal\":\"\",\"unstableNewLow\":\"\",\"failedNewAll\":\"\",\"failedNewHigh\":\"\",\"failedNewNormal\":\"\",\"failedNewLow\":\"\",\"useDeltaValues\":false,\"useStableBuildAsReference\":false}}]}}";
        DescriptorImpl settings = convert(input);

        AnalysisConfiguration[] configurations = settings.getConfigurations();
        assertEquals("Wrong number of configurations", 2, configurations.length);

        assertEquals("Wrong name of configuration", "Eins", configurations[0].getName());
        assertEquals("Wrong name of configuration", "Zwei", configurations[1].getName());

        verifyBooleans(settings, true, false);
        assertEquals("Wrong empty graph mode", true, settings.getEmptyGraphByDefault());
    }

    private void verifyBooleans(final DescriptorImpl settings, final boolean expectedQuiet, final boolean expectedCorrupt) {
        assertEquals("Wring quiet mode", expectedQuiet, settings.getQuietMode());
        assertEquals("Wrong failure mode", expectedCorrupt, settings.getFailOnCorrupt());
    }

    private DescriptorImpl convert(final String input) {
        JSONObject json = toJson(input);

        DescriptorImpl settings = new DescriptorImpl(false);
        StaplerRequest req = mockRequest();

        settings.bind(req, json);
        return settings;
    }

    private JSONObject toJson(final String input) {
        JSONObject json = JSONObject.fromObject(input);
        assertNotNull("JSON Conversion failed", json);
        return json;
    }

    @SuppressWarnings("unchecked")
    private RequestImpl mockRequest() {
        return new RequestImpl(mock(Stapler.class), mock(HttpServletRequest.class), Collections.EMPTY_LIST, null);
    }
}
