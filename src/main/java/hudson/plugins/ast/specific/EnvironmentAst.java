package hudson.plugins.ast.specific;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.ast.factory.Ast;

/**
 * Creates the abstract syntax tree for a specific environment. It takes a specific number of rows before and after the
 * warning.
 *
 * @author Christian M&ouml;stl
 */
public class EnvironmentAst extends Ast {

    private final int surrounding;

    /**
     * Creates a new instance of {@link EnvironmentAst}.
     *
     * @param filename
     *            The filename
     * @param fileAnnotation
     *            the fileAnnotation
     * @param surrounding
     *            The surrounded element each above and below.
     */
    public EnvironmentAst(final String filename, final FileAnnotation fileAnnotation, final int surrounding) {
        super(filename, fileAnnotation);
        this.surrounding = surrounding;
    }

    @Override
    public List<DetailAST> chooseArea() {
        List<DetailAST> elementsInLine = getElementsInSameLine();
        List<DetailAST> tmp = new ArrayList<DetailAST>(elementsInLine);
        Collections.copy(tmp, elementsInLine);

        List<DetailAST> chosen = new ArrayList<DetailAST>();

        chosen.addAll(elementsInLine);
        chosen.addAll(elementsBefore(elementsInLine.get(0)));
        chosen.addAll(elementsAfter(tmp.get(tmp.size() - 1)));

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
            if (counter < surrounding) {
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