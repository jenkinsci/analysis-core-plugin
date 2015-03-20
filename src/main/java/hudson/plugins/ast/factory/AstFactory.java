package hudson.plugins.ast.factory;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.ast.specific.ClassAst;
import hudson.plugins.ast.specific.DefaultAst;
import hudson.plugins.ast.specific.EnvironmentAst;
import hudson.plugins.ast.specific.FileAst;
import hudson.plugins.ast.specific.InstancevariableAst;
import hudson.plugins.ast.specific.MethodAst;
import hudson.plugins.ast.specific.MethodOrClassAst;
import hudson.plugins.ast.specific.NamePackageAst;

/**
 * Factory for abstract syntax trees.
 *
 * @author Christian Möstl
 */
// CHECKSTYLE:COUPLING-OFF
public final class AstFactory {

    private AstFactory() {
        // prevents initialization
    }

    private static final String[] METHOD_AST = new String[]{"AnonInnerLength", "CovariantEquals", "EqualsAvoidNull",
            "EqualsHashCode", "HiddenField", "JavadocMethod", "MethodLength", "MethodName", "MethodParamPad",
            "MethodTypeParameterName", "ParameterAssignment", "ParameterName", "ParameterNumber", "SuperClone",
            "SuperFinalize", "ThrowsCount"};
    private static final String[] ENVIRONMENT_AST = new String[]{"ArrayTypeStyle", "AvoidNestedBlocks",
            "BooleanExpressionComplexity", "DefaultComesLast", "EmptyBlock", "EmptyForIteratorPad", "EmptyStatement",
            "FallThrough", "GenericWhitespace", "IllegalCatch", "IllegalThrows", "InnerAssignment", "JavaNCSS",
            "LeftCurly", "LocalFinalVariableName", "LocalVariableName", "MissingSwitchDefault",
            "ModifiedControlVariable", "MultipleVariableDeclarations", "NeedBraces", "NestedForDepth", "NestedIfDepth",
            "NestedTryDepth", "NoWhitespaceAfter", "NoWhitespaceBefore", "NPathComplexity", "OneStatementPerLine",
            "OperatorWrap", "ParenPad", "RightCurly", "SimplifyBooleanExpression", "SimplifyBooleanReturn",
            "StringLiteralEquality", "TypecastParenPad", "UnnecessaryParentheses", "UpperEll", "WhitespaceAfter",
            "WhitespaceAround"};
    private static final String[] FILE_AST = new String[]{"ClassDataAbstractionCoupling", "ClassFanOutComplexity",
            "FileLength", "IllegalImport", "InterfaceIsType", "OuterTypeFilename", "PackageDeclaration"};
    private static final String[] CLASS_AST = new String[]{"ClassTypeParameterName", "FinalClass",
            "HideUtilityClassConstructor", "InnerTypeLast", "JavadocType", "JUnitTestCase", "MultipleStringLiterals",
            "MutableException", "TypeName"};
    private static final String[] METHOD_OR_CLASS_AST = new String[]{"AnnotationUseStyle", "FileTabCharacter",
            "JavadocStyle", "MissingDeprecated", "ModifierOrder", "RedundantModifier", "VisibilityModifier"};
    private static final String[] INSTANCEVARIABLE_AST = new String[]{"ConstantName", "ExplicitInitialization",
            "JavadocVariable", "MemberName", "StaticVariableName"};
    private static final String[] NAME_PACKAGE_AST = new String[]{"PackageName"};

    // FIXME: Check before go-live.
    // private static final String[] NAME_ENVIRONMENT_AST = new String[]{"LocalFinalVariableName", "LocalVariableName"};
    // private static final String[] NAME_INSTANCEVARIABLE_AST = new String[]{"MemberName", "StaticVariableName"};
    // private static final String[] NAME_METHOD_AST = new String[]{"MethodName", "MethodTypeParameterName",
    // "ParameterName"};
    // private static final String[] NAME_CLASS_AST = new String[]{"ClassTypeParameterName", "TypeName"};
    // private static final String[] QUESTION = new String[]{"FileContentsHolder", "PackageAnnotation"};

    /**
     * Creates an instance of a specific {@link Ast}.
     *
     * @param filename
     *            the file
     * @param fileAnnotation
     *            the {@link FileAnnotation}
     * @return the specific ast.
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public static Ast getInstance(final String filename, final FileAnnotation fileAnnotation) {
        String type = fileAnnotation.getType();
        Ast ast;
        String checkstyleModulName = StringUtils.removeEnd(type, "Check");

        if (Arrays.asList(METHOD_AST).contains(checkstyleModulName)) {
            ast = new MethodAst(filename, fileAnnotation);
        }
        else if (Arrays.asList(ENVIRONMENT_AST).contains(checkstyleModulName)) {
            ast = new EnvironmentAst(filename, fileAnnotation, 3);
        }
        else if (Arrays.asList(FILE_AST).contains(checkstyleModulName)) {
            ast = new FileAst(filename, fileAnnotation);
        }
        else if (Arrays.asList(CLASS_AST).contains(checkstyleModulName)) {
            ast = new ClassAst(filename, fileAnnotation);
        }
        else if (Arrays.asList(METHOD_OR_CLASS_AST).contains(checkstyleModulName)) {
            ast = new MethodOrClassAst(filename, fileAnnotation);
        }
        else if (Arrays.asList(INSTANCEVARIABLE_AST).contains(checkstyleModulName)) {
            ast = new InstancevariableAst(filename, fileAnnotation);
        }
        // else if (Arrays.asList(NAME_ENVIRONMENT_AST).contains(checkstyleModulName)) {
        // ast = new NameEnvironmentAst(filename, fileAnnotation, 3);
        // }
        // else if (Arrays.asList(NAME_INSTANCEVARIABLE_AST).contains(checkstyleModulName)) {
        // ast = new NameInstancevariableAst(filename, fileAnnotation);
        // }
        // else if (Arrays.asList(NAME_METHOD_AST).contains(checkstyleModulName)) {
        // ast = new NameMethodAst(filename, fileAnnotation);
        // }
        // else if (Arrays.asList(NAME_CLASS_AST).contains(checkstyleModulName)) {
        // ast = new NameClassAst(filename, fileAnnotation);
        // }
        else if (Arrays.asList(NAME_PACKAGE_AST).contains(checkstyleModulName)) {
            ast = new NamePackageAst(filename, fileAnnotation);
        }
        else {
            ast = new DefaultAst(filename, fileAnnotation);
        }

        return ast;
    }

    /**
     * Returns the methodAst.
     *
     * @return the methodAst
     */
    public static String[] getMethodAst() {
        return METHOD_AST;
    }

    /**
     * Returns the environmentAst.
     *
     * @return the environmentAst
     */
    public static String[] getEnvironmentAst() {
        return ENVIRONMENT_AST;
    }

    /**
     * Returns the fileAst.
     *
     * @return the fileAst
     */
    public static String[] getFileAst() {
        return FILE_AST;
    }

    /**
     * Returns the classAst.
     *
     * @return the classAst
     */
    public static String[] getClassAst() {
        return CLASS_AST;
    }

    /**
     * Returns the methodOrClassAst.
     *
     * @return the methodOrClassAst
     */
    public static String[] getMethodOrClassAst() {
        return METHOD_OR_CLASS_AST;
    }

    /**
     * Returns the namePackageAst.
     *
     * @return the namePackageAst
     */
    public static String[] getNamePackageAst() {
        return NAME_PACKAGE_AST;
    }

    /**
     * Returns the instancevariableAst.
     *
     * @return the instancevariableAst
     */
    public static String[] getInstancevariableAst() {
        return INSTANCEVARIABLE_AST;
    }
}