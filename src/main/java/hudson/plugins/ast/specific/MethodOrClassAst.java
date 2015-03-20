package hudson.plugins.ast.specific;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.ast.factory.Ast;

/**
 * Depicts the MethodOrClassAst. If the warning is on method-level the {@link MethodAst#chooseArea()} would be called,
 * otherwise if it is on class-level the {@link ClassAst#chooseArea()}.
 *
 * @author Christian M&ouml;stl
 */
public class MethodOrClassAst extends Ast {

    private final int[] excludeTypes = new int[]{TokenTypes.CLASS_DEF, TokenTypes.INTERFACE_DEF, TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF};

    /**
     * Creates a new instance of {@link MethodOrClassAst}.
     *
     * @param filename
     *            The filename
     * @param fileAnnotation
     *            the fileAnnotation
     */
    public MethodOrClassAst(final String filename, final FileAnnotation fileAnnotation) {
        super(filename, fileAnnotation);
    }

    @Override
    public List<DetailAST> chooseArea() {
        if (getElementsInSameLine().size() != 0 && isLevelOfMethod(getElementsInSameLine().get(0))) {
            return new MethodAst(getFilename(), getFileAnnotation()).chooseArea();
        }
        else {
            return new ClassAst(getFilename(), getFileAnnotation()).chooseArea();
        }
    }

    private boolean isLevelOfMethod(final DetailAST element) {
        if (element != null) {
            if (element.getType() == TokenTypes.METHOD_DEF || element.getType() == TokenTypes.CTOR_DEF) {
                return true;
            }
            else if (!Arrays.asList(ArrayUtils.toObject(excludeTypes)).contains(element.getType())) {
                return false;
            }
            else {
                isLevelOfMethod(element.getParent());
            }
        }
        return false;
    }
}