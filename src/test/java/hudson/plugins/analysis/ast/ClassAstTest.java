package hudson.plugins.analysis.ast;

import org.junit.Test;

/**
 * Tests the class {@link ClassAst}.
 *
 * @author Christian Möstl
 * @author Ullrich Hafner
 */
public class ClassAstTest extends AbstractAstTest {
    /**
     * Verifies that the ClassAst works right.
     */
    @Test
    public void testClassAst() {
        String expectedResult = "PACKAGE_DEF ANNOTATIONS DOT DOT IDENT IDENT IDENT SEMI CLASS_DEF MODIFIERS LITERAL_PUBLIC LITERAL_CLASS IDENT OBJBLOCK LCURLY VARIABLE_DEF MODIFIERS LITERAL_PRIVATE TYPE LITERAL_INT IDENT SEMI CTOR_DEF MODIFIERS LITERAL_PRIVATE IDENT LPAREN PARAMETERS RPAREN SLIST RCURLY RCURLY ";

        Ast ast = new ClassAst(createJavaSourceTemporaryFile("FinalClass_Newline.java"), 13);

        checkAst(expectedResult, ast);
    }
}