package hudson.plugins.analysis.ast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.FileUtils;

import static org.junit.Assert.*;

/**
 * Base class for all {@link Ast}  tests.
 *
 * @author Ullrich Hafner
 */
public abstract class AbstractAstTest {
    protected void assertThatAstIs(final Ast ast, final String expectedResult) {
        String realResult = ast.chosenAreaAsString(' ');

        compareString(expectedResult, realResult);
    }

    private void compareString(final String first, final String second) {
        assertNotNull("String isn't not null", first);
        assertEquals("Strings don't match: ", first, second);
    }

    protected String createJavaSourceTemporaryFile(final String fileName) {
        File warnings = createCopyInTemp(fileName);
        return warnings.getAbsolutePath();
    }

    private File createCopyInTemp(final String fileName) {
        try {
            File warnings = File.createTempFile("ast", ".java");
            warnings.deleteOnExit();

            InputStream stream = AbstractAstTest.class.getResourceAsStream(fileName);
            if (stream == null) {
                throw new IllegalArgumentException("File not found: " + fileName);
            }
            FileUtils.copyInputStreamToFile(stream, warnings);
            return warnings;
        }
        catch (IOException cause) {
            throw new IllegalArgumentException(cause);
        }
    }
}
