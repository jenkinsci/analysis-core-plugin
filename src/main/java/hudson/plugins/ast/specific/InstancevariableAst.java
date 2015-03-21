package hudson.plugins.ast.specific;

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import hudson.plugins.ast.factory.Ast;

/**
 * Depicts the elements from the abstract syntax tree which are object-variables.
 *
 * @author Christian Möstl
 */
public class InstanceVariableAst extends Ast {
    /**
     * Creates a new instance of {@link InstanceVariableAst}.
     *
     * @param filename
     *            The filename
     * @param lineNumber
     */
    public InstanceVariableAst(final String filename, final int lineNumber) {
        super(filename, lineNumber);
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