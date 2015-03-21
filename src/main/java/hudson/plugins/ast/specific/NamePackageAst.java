package hudson.plugins.ast.specific;

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import hudson.plugins.ast.factory.Ast;

/**
 * Displays all informations about the package in the abstract syntax tree.
 *
 * @author Christian Möstl
 */
public class NamePackageAst extends Ast {

    /**
     * Creates a new instance of {@link NamePackageAst}.
     *
     * @param filename
     *            The filename
     * @param lineNumber
     */
    public NamePackageAst(final String filename, final int lineNumber) {
        super(filename, lineNumber);
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