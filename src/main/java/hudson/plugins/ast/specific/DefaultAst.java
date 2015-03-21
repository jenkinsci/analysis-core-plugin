package hudson.plugins.ast.specific;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import hudson.plugins.ast.factory.Ast;

/**
 * This class represents the default-AST.
 *
 * @author Christian Möstl
 */
public class DefaultAst extends Ast {

    /**
     * Creates a new instance of {@link DefaultAst}.
     *
     * @param filename
     *            The filename
     * @param lineNumber
     */
    public DefaultAst(final String filename, final int lineNumber) {
        super(filename, lineNumber);
    }

    @Override
    public List<DetailAST> chooseArea() {
        return new FileAst(getFileName(), getLineNumber()).chooseArea();
    }
}