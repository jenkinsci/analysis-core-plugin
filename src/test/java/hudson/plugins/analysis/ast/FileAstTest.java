package hudson.plugins.analysis.ast;

import org.junit.Test;

/**
 * Tests the class {@link FileAst}.
 *
 * @author Christian Möstl
 * @author Ullrich Hafner
 */
// FIXME: update test using the base class methods and resources
public class FileAstTest extends AbstractAstTest {
    @Override
    protected Ast createAst(final int lineNumber, final String fileName) {
        return new FileAst(fileName, lineNumber);
    }

    /**
     * Verifies that the FileAst works right.
     */
    @Test
    public void testFileAst() {
        String expectedResult = "PACKAGE_DEF ANNOTATIONS DOT DOT IDENT IDENT IDENT SEMI IMPORT DOT DOT IDENT IDENT IDENT SEMI INTERFACE_DEF MODIFIERS LITERAL_PUBLIC LITERAL_INTERFACE IDENT OBJBLOCK LCURLY VARIABLE_DEF MODIFIERS TYPE LITERAL_INT IDENT ASSIGN EXPR NUM_INT SEMI VARIABLE_DEF MODIFIERS TYPE IDENT IDENT ASSIGN EXPR STRING_LITERAL SEMI VARIABLE_DEF MODIFIERS TYPE IDENT IDENT ASSIGN EXPR LITERAL_NEW IDENT LPAREN ELIST EXPR NUM_INT RPAREN SEMI RCURLY ";

        Ast ast = new FileAst(createJavaSourceTemporaryFile("InterfaceIsType_Newline.java"), 15);

        assertThatAstIs(ast, expectedResult);
    }
}
