package hudson.plugins.analysis.ast;

import org.junit.Test;

/**
 * Tests the class {@link NamePackageAst}.
 *
 * @author Christian Möstl
 * @author Ullrich Hafner
 */
// FIXME: update test using the base class methods and resources
public class NamePackageAstTest extends AbstractAstTest {
    @Override
    protected Ast createAst(final int lineNumber, final String fileName) {
        return new NamePackageAst(fileName, lineNumber);
    }

    /**
     * Verifies that the NamePackageAst works right.
     */
    @Test
    public void testNamePackageAst() {
        String expectedResult = "PACKAGE_DEF ANNOTATIONS DOT DOT IDENT IDENT IDENT SEMI ";

        Ast ast = new NamePackageAst(createJavaSourceTemporaryFile("PackageName_Newline.java"), 7);

        assertThatAstIs(ast, expectedResult);
    }
}
