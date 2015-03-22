package hudson.plugins.analysis.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Creates the abstract syntax tree for the whole class except inner types.
 *
 * @author Christian Möstl
 */
// FIXME: Regard concerned java-class (at OBJBLOCK) and not inherently the top of all classes!
public class ClassAst extends Ast {

    private final List<DetailAST> siblings = new ArrayList<DetailAST>();
    private final int[] excludeTypes = new int[]{TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF, TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF};

    /**
     * Creates a new instance of {@link ClassAst}.
     *
     * @param fileName   the name of the Java file
     * @param lineNumber the line number that contains the warning
     */
    public ClassAst(final String fileName, final int lineNumber) {
        super(fileName, lineNumber);
    }

    @Override
    public List<DetailAST> chooseArea() {
        DetailAST objBlock = getObjBlock(getAbstractSyntaxTree());

        getSpecialSiblings(objBlock.getFirstChild());

        List<DetailAST> chosenArea = new ArrayList<DetailAST>();

        chosenArea.addAll(elementsToObjBlock(getAbstractSyntaxTree()));
        chosenArea.add(objBlock);

        for (int i = 0; i < siblings.size(); i++) {
            clear();
            chosenArea.add(siblings.get(i));
            chosenArea.addAll(calcAllChildren(siblings.get(i).getFirstChild()));
        }

        return chosenArea;
    }

    private void getSpecialSiblings(final DetailAST element) {
        if (!Arrays.asList(ArrayUtils.toObject(excludeTypes)).contains(element.getType())) {
            siblings.add(element);
        }
        if (element.getNextSibling() != null) {
            getSpecialSiblings(element.getNextSibling());
        }
    }

    private List<DetailAST> elementsToObjBlock(final DetailAST root) {
        List<DetailAST> elementsToObjBlock = new ArrayList<DetailAST>();

        elementsToObjBlock(elementsToObjBlock, root);

        return elementsToObjBlock;
    }

    private void elementsToObjBlock(final List<DetailAST> list, final DetailAST root) {
        if (root.getType() != TokenTypes.OBJBLOCK) {
            list.add(root);
            if (root.getFirstChild() != null) {
                elementsToObjBlock(list, root.getFirstChild());
            }
            if (root.getNextSibling() != null) {
                elementsToObjBlock(list, root.getNextSibling());
            }
        }
    }
}