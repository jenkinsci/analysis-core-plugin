package hudson.plugins.analysis.ast;

import org.junit.Test;

/**
 * Tests the class {@link MethodAst}.
 *
 * @author Christian Möstl
 * @author Ullrich Hafner
 */
public class MethodAstTest extends AbstractAstTest {
    /**
     * Verifies that the MethodAst works right.
     */
    @Test
    public void testMethodAst() {
        String expectedResult = "METHOD_DEF MODIFIERS LITERAL_PUBLIC LITERAL_STATIC TYPE LITERAL_VOID IDENT LPAREN PARAMETERS RPAREN SLIST EXPR METHOD_CALL DOT DOT IDENT IDENT IDENT ELIST EXPR STRING_LITERAL RPAREN SEMI RCURLY ";

        Ast ast = new MethodAst(createJavaSourceTemporaryFile("MethodName_Newline.java"), 21);

        assertThatAstIs(ast, expectedResult);
    }
}
