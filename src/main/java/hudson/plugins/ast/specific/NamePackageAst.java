package hudson.plugins.ast.specific;

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.ast.factory.Ast;

/**
 * Displays all informations about the package in the abstract syntax tree.
 *
 * @author Christian M&ouml;stl
 */
public class NamePackageAst extends Ast {

    /**
     * Creates a new instance of {@link NamePackageAst}.
     *
     * @param filename
     *            The filename
     * @param fileAnnotation
     *            the fileAnnotation
     */
    public NamePackageAst(final String filename, final FileAnnotation fileAnnotation) {
        super(filename, fileAnnotation);
    }

    @Override
    public List<DetailAST> chooseArea() {
        List<DetailAST> chosen = new ArrayList<DetailAST>();

        chosen.add(getAbstractSyntaxTree());
        chosen.addAll(calcAllChildren(getAbstractSyntaxTree().getFirstChild()));

        StringBuilder stringBuilder = new StringBuilder();
        for (DetailAST element : chosen) {
            stringBuilder.append(element.getText());
        }
        setName(stringBuilder.toString());

        return chosen;
    }
}