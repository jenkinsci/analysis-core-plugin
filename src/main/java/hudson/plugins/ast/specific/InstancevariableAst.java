package hudson.plugins.ast.specific;

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.ast.factory.Ast;

/**
 * Depicts the Elements from the abstract syntax tree which are object-variables.
 *
 * @author Christian M&ouml;stl
 */
public class InstancevariableAst extends Ast {

    /**
     * Creates a new instance of {@link InstancevariableAst}.
     *
     * @param filename
     *            The filename
     * @param fileAnnotation
     *            the fileAnnotation
     */
    public InstancevariableAst(final String filename, final FileAnnotation fileAnnotation) {
        super(filename, fileAnnotation);
    }

    @Override
    public List<DetailAST> chooseArea() {
        List<DetailAST> elementsInSameLine = getElementsInSameLine();
        DetailAST objBlock = getObjBlockAsParent(elementsInSameLine.get(0));

        getInstanceVariables(objBlock.getFirstChild());
        List<DetailAST> chosenArea = new ArrayList<DetailAST>();

        chosenArea.add(objBlock);
        for (int i = 0; i < instanceVariables.size(); i++) {
            clear();
            if (!isConstant(instanceVariables.get(i))) {
                chosenArea.add(instanceVariables.get(i));
                chosenArea.addAll(calcAllChildren(instanceVariables.get(i).getFirstChild()));
            }
        }

        return chosenArea;
    }

    private DetailAST getObjBlockAsParent(final DetailAST ast) {
        if (ast.getType() == TokenTypes.OBJBLOCK) {
            return ast;
        }
        else {
            return getObjBlockAsParent(ast.getParent());
        }
    }

    private final List<DetailAST> instanceVariables = new ArrayList<DetailAST>();

    private void getInstanceVariables(final DetailAST element) {
        if (element != null) {
            if (element.getType() == TokenTypes.VARIABLE_DEF) {
                instanceVariables.add(element);
            }
            if (element.getNextSibling() != null) {
                getInstanceVariables(element.getNextSibling());
            }
        }
    }
}