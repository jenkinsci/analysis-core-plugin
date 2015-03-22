package hudson.plugins.ast.specific;

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import hudson.plugins.ast.factory.Ast;

/**
 * Creates the abstract syntax tree for the surrounding AST elements. It takes a specific number of lines before and
 * after the actual warning.
 *
 * @author Christian Möstl
 */
public class SurroundingElementsAst extends Ast {
    /** Number of lines before and after current line to consider. */
    private static final int LINES_LOOK_AHEAD = 3;

    private final int surroundingLines;

    /**
     * Creates a new instance of {@link SurroundingElementsAst}.
     *
     * @param fileName         the name of the Java file
     * @param lineNumber       the line number that contains the warning
     * @param surroundingLines the number of lines before and after the affected line
     */
    public SurroundingElementsAst(final String fileName, final int lineNumber, final int surroundingLines) {
        super(fileName, lineNumber);

        this.surroundingLines = surroundingLines;
    }

    @Override
    public List<DetailAST> chooseArea() {
        List<DetailAST> elementsInLine = getElementsInSameLine();
        List<DetailAST> tmp = new ArrayList<DetailAST>(elementsInLine);

        List<DetailAST> chosen = new ArrayList<DetailAST>();
        if (!elementsInLine.isEmpty()) {
            chosen.addAll(elementsInLine);
            chosen.addAll(elementsBefore(elementsInLine.get(0)));
            chosen.addAll(elementsAfter(tmp.get(tmp.size() - 1)));
        }

        return chosen;
    }

    private List<DetailAST> elementsBefore(final DetailAST start) {
        return calcEnvironment(start, true);
    }

    private List<DetailAST> elementsAfter(final DetailAST start) {
        return calcEnvironment(start, false);
    }

    private List<DetailAST> calcEnvironment(final DetailAST start, final boolean before) {
        int startLine = start.getLineNo();
        int nextLine;
        int counter = 0;
        DetailAST completeAst = getAbstractSyntaxTree();

        List<DetailAST> environment = new ArrayList<DetailAST>();

        int limit;
        if (before) {
            limit = startLine;
        }
        else {
            limit = getLastLineNumber() - startLine + 1;
        }
        for (int i = 1; i < limit; i++) {
            if (counter < surroundingLines) {
                if (before) {
                    nextLine = startLine - i;
                }
                else {
                    nextLine = startLine + i;
                }
                clearElementsInSameLine();
                runThroughAST(completeAst, nextLine);
                if (!getElementsInSameLine().isEmpty()) {
                    counter++;
                    environment.addAll(getElementsInSameLine());
                    clearElementsInSameLine();
                }
            }
            else {
                break;
            }
        }

        return environment;
    }
}