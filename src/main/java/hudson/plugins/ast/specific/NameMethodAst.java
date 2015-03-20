package hudson.plugins.ast.specific;

import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.ast.factory.Ast;

/**
 * Creates the abstract syntax tree (AST).
 *
 * @author Christian M&ouml;stl
 */
public class NameMethodAst extends Ast {

    private DetailAST ident;

    /**
     * Creates a new instance of {@link NameMethodAst}.
     *
     * @param filename
     *            The filename
     * @param fileAnnotation
     *            the fileAnnotation
     */
    public NameMethodAst(final String filename, final FileAnnotation fileAnnotation) {
        super(filename, fileAnnotation);
    }

    @Override
    public List<DetailAST> chooseArea() {
        Ast ast = new MethodAst(getFilename(), getFileAnnotation());
        List<DetailAST> chosenArea = ast.chooseArea();
        DetailAST first = chosenArea.get(0).getFirstChild();

        calcMethodName(first, 0);

        ast.setName(ident.getText());

        return chosenArea;
    }

    private void calcMethodName(final DetailAST method, int counter) {
        if (method != null) {
            if (method.getType() == TokenTypes.IDENT && counter == 0) {
                ident = method;
                counter++;
            }
            if (method.getFirstChild() != null) {
                calcMethodName(method.getFirstChild(), counter);
            }
            if (method.getNextSibling() != null) {
                calcMethodName(method.getNextSibling(), counter);
            }
        }
    }

    // private void executeMethod(final DetailAST method, final int counter) {
    // String type = getFileAnnotation().getType();
    // String checkstyleModulName = StringUtils.removeEnd(type, "Check");
    //
    // checkstyleModulName = checkstyleModulName.toUpperCase(Locale.GERMAN);
    //
    // if (checkstyleModulName.equals(WarningType.METHODNAME.toString())) {
    // calcMethodName(method, counter);
    // }
    // else if (checkstyleModulName.equals(WarningType.PARAMETERNAME.toString())) {
    //
    // }
    // else if (checkstyleModulName.equals(WarningType.METHODTYPEPARAMETERNAME.toString())) {
    //
    // }
    // }
}