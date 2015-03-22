package hudson.plugins.ast.specific;

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import hudson.plugins.ast.factory.Ast;

/**
 * Is responsible for methods and constructors.
 *
 * @author Christian Möstl
 */
public class MethodAst extends Ast {
    /**
     * Creates a new instance of {@link MethodAst}.
     *
     * @param fileName   the name of the Java file
     * @param lineNumber the line number that contains the warning
     */
    public MethodAst(final String fileName, final int lineNumber) {
        super(fileName, lineNumber);
    }

    @Override
    public List<DetailAST> chooseArea() {
        List<DetailAST> elementsInLine = getElementsInSameLine();

        if (!elementsInLine.isEmpty()) {
            DetailAST methodStart = getRootOfMethod(elementsInLine.get(0));

            List<DetailAST> chosenArea = new ArrayList<DetailAST>();
            chosenArea.add(methodStart);
            chosenArea.addAll(calcAllChildren(methodStart.getFirstChild()));
            return chosenArea;
        }

        return elementsInLine;
    }

    private DetailAST getRootOfMethod(final DetailAST elementInMethod) {
        if (elementInMethod.getType() == TokenTypes.METHOD_DEF || elementInMethod.getType() == TokenTypes.CTOR_DEF) {
            return elementInMethod;
        }
        else {
            return getRootOfMethod(elementInMethod.getParent());
        }
    }
}