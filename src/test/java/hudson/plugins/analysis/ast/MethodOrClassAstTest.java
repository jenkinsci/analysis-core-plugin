package hudson.plugins.analysis.ast;

import org.junit.Test;

/**
 * Tests the class {@link MethodOrClassAstTest}.
 *
 * @author Christian Möstl
 * @author Ullrich Hafner
 */
public class MethodOrClassAstTest extends AbstractAstTest {
    /**
     * Verifies that the MethodOrClassAst works right.
     */
    @Test
    public void testMethodOrClassAstInMethodlevel() {
        String expectedResult = "METHOD_DEF MODIFIERS LITERAL_PUBLIC TYPE LITERAL_INT IDENT LPAREN PARAMETERS PARAMETER_DEF MODIFIERS TYPE LITERAL_INT IDENT COMMA PARAMETER_DEF MODIFIERS TYPE LITERAL_INT IDENT RPAREN SEMI ";

        Ast ast = new MethodOrClassAst(createJavaSourceTemporaryFile("RedundantModifier_Newline.java"), 25);

        assertThatAstIs(ast, expectedResult);
    }

    /**
     * Verifies that the MethodOrClassAst works right.
     */
    @Test
    public void testMethodOrClassAstInClasslevel() {
        String expectedResult = "PACKAGE_DEF ANNOTATIONS DOT DOT IDENT IDENT IDENT SEMI CLASS_DEF MODIFIERS LITERAL_PUBLIC LITERAL_CLASS IDENT OBJBLOCK LCURLY CTOR_DEF MODIFIERS LITERAL_PUBLIC IDENT LPAREN PARAMETERS RPAREN SLIST RCURLY RCURLY ";

        Ast ast = new MethodOrClassAst(createJavaSourceTemporaryFile("JavadocStyle_Newline.java"), 9);

        assertThatAstIs(ast, expectedResult);
    }
}
