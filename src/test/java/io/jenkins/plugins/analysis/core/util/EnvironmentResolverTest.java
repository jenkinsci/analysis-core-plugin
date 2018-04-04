package io.jenkins.plugins.analysis.core.util;

import hudson.EnvVars;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static edu.hm.hafner.analysis.assertj.Assertions.*;

/**
 * Tests the class {@link EnvironmentResolver}.
 *
 * @author Elvira Hauer <hauer@hm.edu>
 */
class EnvironmentResolverTest {
	
	private EnvironmentResolver sut;
	private String aString;
	
	@Before
    public void initialize() {
       sut = new EnvironmentResolver();
	   aString = "ein einfacher String";
    }
	
    @Test
    void testEnvVarsNull() {
        String result = sut.expandEnvironmentVariables(null, aString);
       
	    assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(aString);
        assertThat(result).hasSize(aString.length());
    }

    @Test
    void testEnvVarsEmpty() {
        String result = sut.expandEnvironmentVariables(new EnvVars(), aString);

        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo(aString);
        assertThat(result).hasSize(aString.length());
    }

    @Test
    void testNonExpandedIsBlank() {
        String result = sut.expandEnvironmentVariables(new EnvVars(), "");

        assertThat(result).isEmpty();
        assertThat(result).isEqualTo("");
    }

    @Test
    void testValidTest() {
	    Map aMap = new HashMap<String, String>();
	    aMap.put("das", "ist");
	    aMap.put("eine", "test");
	    aMap.put("EnvVars", "Map");
        EnvVars envVars = new EnvVars(aMap);
        String result = sut.expandEnvironmentVariables(envVars, "");
        //Keine ahnung was passiert
        assertThat(result).isEmpty();
        assertThat(result).isEqualTo("");
    }

}