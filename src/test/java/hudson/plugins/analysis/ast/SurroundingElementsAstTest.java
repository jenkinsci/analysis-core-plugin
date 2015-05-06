package hudson.plugins.analysis.ast;

import org.junit.Test;

/**
 * Tests the class {@link SurroundingElementsAst}.
 *
 * @author Christian Möstl
 * @author Ullrich Hafner
 */
public class SurroundingElementsAstTest extends AbstractAstTest {
    /**
     * Verifies that the EnvironmentAst works right.
     */
    @Test
    public void testEnvironmentAst() {
        String expectedResult = "LITERAL_IF LPAREN EXPR GE IDENT NUM_INT RPAREN METHOD_DEF MODIFIERS LITERAL_PUBLIC TYPE LITERAL_INT IDENT LPAREN PARAMETERS PARAMETER_DEF MODIFIERS FINAL TYPE LITERAL_INT IDENT RPAREN SLIST RCURLY EXPR ASSIGN DOT LITERAL_THIS IDENT IDENT SEMI LITERAL_RETURN EXPR IDENT SEMI LITERAL_ELSE SLIST LITERAL_RETURN EXPR UNARY_MINUS IDENT SEMI ";

        Ast ast = new SurroundingElementsAst(createJavaSourceTemporaryFile("NeedBraces_Newline.java"), 44);

        checkAst(expectedResult, ast);
    }
}
