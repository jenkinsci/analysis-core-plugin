package hudson.plugins.ast.specific;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.ast.factory.Ast;

/**
 * FIXME: Document type NameEnvironmentAst.
 *
 * @author Christian M&ouml;stl
 */
public class NameEnvironmentAst extends Ast {

    private final int surrounding;

    /**
     * Creates a new instance of {@link NameEnvironmentAst}.
     *
     * @param filename
     *            The filename
     * @param fileAnnotation
     *            the fileAnnotation
     * @param surrounding
     *            The surrounded element each above and below.
     */
    public NameEnvironmentAst(final String filename, final FileAnnotation fileAnnotation, final int surrounding) {
        super(filename, fileAnnotation);
        this.surrounding = surrounding;
    }

    @Override
    public List<DetailAST> chooseArea() {
        // FIXME Auto-generated method stub
        return null;
    }


}
