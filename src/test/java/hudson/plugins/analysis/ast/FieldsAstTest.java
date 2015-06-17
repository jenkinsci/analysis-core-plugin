package hudson.plugins.analysis.ast;

import org.junit.Test;

/**
 * Tests the class {@link FieldsAst}.
 *
 * @author Christian Möstl
 * @author Ullrich Hafner
 */
// FIXME: update test using the base class methods and resources
public class FieldsAstTest extends AbstractAstTest {
    @Override
    protected Ast createAst(final int lineNumber, final String fileName) {
        return new FieldsAst(fileName, lineNumber);
    }

    /**
     * Verifies that the InstancevariableAst works right.
     */
    @Test
    public void testInstancevariableAst() {
        String expectedResult = "OBJBLOCK VARIABLE_DEF MODIFIERS LITERAL_PRIVATE TYPE IDENT IDENT SEMI VARIABLE_DEF MODIFIERS LITERAL_PRIVATE TYPE LITERAL_INT IDENT ASSIGN EXPR NUM_INT SEMI VARIABLE_DEF MODIFIERS LITERAL_PRIVATE FINAL TYPE IDENT IDENT SEMI ";

        Ast ast = new FieldsAst(createJavaSourceTemporaryFile("ExplicitInitialization_Newline.java"), 17);

        assertThatAstIs(ast, expectedResult);
    }
}
