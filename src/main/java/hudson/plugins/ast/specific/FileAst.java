package hudson.plugins.ast.specific;

import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.ast.factory.Ast;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

/**
 * Document type FileAst.
 *
 * @author Christian M&ouml;stl
 */
public class FileAst extends Ast {

    /**
     * Creates a new instance of {@link FileAst}.
     *
     * @param filename
     *            The filename
     * @param fileAnnotation
     *            the fileAnnotation
     */
    public FileAst(final String filename, final FileAnnotation fileAnnotation) {
        super(filename, fileAnnotation);
    }

    @Override
    public List<DetailAST> chooseArea() {
        runThroughAST(getAbstractSyntaxTree());

        return getAllElements();
    }
}