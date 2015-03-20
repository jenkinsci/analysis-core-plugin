package hudson.plugins.ast.specific;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.ast.factory.Ast;

/**
 * Document type NameInstancevariableAst.
 *
 * @author Christian M&ouml;stl
 */
public class NameInstancevariableAst extends Ast {

    /**
     * Creates a new instance of {@link NameInstancevariableAst}.
     * @param filename
     * @param fileAnnotation
     */
    public NameInstancevariableAst(final String filename, final FileAnnotation fileAnnotation) {
        super(filename, fileAnnotation);
        // FIXME Auto-generated constructor stub
    }

    @Override
    public List<DetailAST> chooseArea() {
        // FIXME Auto-generated method stub
        return null;
    }

}

