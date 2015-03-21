package hudson.plugins.ast.specific;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import hudson.plugins.ast.factory.Ast;

/**
 * Document type FileAst.
 *
 * @author Christian Möstl
 */
public class FileAst extends Ast {

    /**
     * Creates a new instance of {@link FileAst}.
     *
     * @param filename
     *            The filename
     * @param lineNumber
     */
    public FileAst(final String filename, final int lineNumber) {
        super(filename, lineNumber);
    }

    @Override
    public List<DetailAST> chooseArea() {
        runThroughAST(getAbstractSyntaxTree());

        return getAllElements();
    }
}