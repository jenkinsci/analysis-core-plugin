package hudson.plugins.ast.specific;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.ast.factory.Ast;

/**
 * This class represents the default-AST.
 *
 * @author Christian M&ouml;stl
 */
public class DefaultAst extends Ast {

    /**
     * Creates a new instance of {@link DefaultAst}.
     *
     * @param filename
     *            The filename
     * @param fileAnnotation
     *            the fileAnnotation
     */
    public DefaultAst(final String filename, final FileAnnotation fileAnnotation) {
        super(filename, fileAnnotation);
    }

    @Override
    public List<DetailAST> chooseArea() {
        return new FileAst(getFilename(), getFileAnnotation()).chooseArea();
    }
}