package hudson.plugins.analysis.ast;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import static org.junit.Assert.*;

/**
 * Base class for all {@link Ast}  tests.
 *
 * @author Ullrich Hafner
 */
public abstract class AbstractAstTest {
    protected void checkAst(final String expectedResult, final Ast ast) {
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

            FileUtils.copyInputStreamToFile(AbstractAstTest.class.getResourceAsStream(fileName), warnings);
            return warnings;
        }
        catch (IOException cause) {
            throw new IllegalArgumentException(cause);
        }
    }
}
