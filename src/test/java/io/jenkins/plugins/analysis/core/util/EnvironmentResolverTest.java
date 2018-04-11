package io.jenkins.plugins.analysis.core.util;

import hudson.EnvVars;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

import static edu.hm.hafner.analysis.assertj.Assertions.*;

/**
 * Tests the class {@link EnvironmentResolver}.
 *
 * @author Elvira Hauer
 */
class EnvironmentResolverTest {
	
	private EnvironmentResolver sut;
	private String aString;

	@BeforeEach
    void initialize(){
        sut = new EnvironmentResolver();
        aString  = "a simple String";
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
    void testReplaceInNonExpanded() {
	    Map<String, String> aMap = new HashMap<>();
	    aMap.put("this", "is");
	    aMap.put("a", "test");
	    aMap.put("EnvVars", "Map");
        EnvVars envVars = new EnvVars(aMap);
        String result = sut.expandEnvironmentVariables(envVars, "Hello replace the $this with is.");
        assertThat(result).isNotEmpty();
        assertThat(result).isEqualTo("Hello replace the is with is.");
    }

}