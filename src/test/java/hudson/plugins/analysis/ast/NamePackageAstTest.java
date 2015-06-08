package hudson.plugins.analysis.ast;

import org.junit.Test;

/**
 * Tests the class {@link NamePackageAst}.
 *
 * @author Christian Möstl
 * @author Ullrich Hafner
 */
public class NamePackageAstTest extends AbstractAstTest {
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
