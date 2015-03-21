package hudson.plugins.ast.factory;

/**
 * Creates a hash code using the AST of the source code that contains the warning.
 *
 * @author Ulli Hafner
 */
public class AstHashCode {
    /**
     * Creates a hash code using the AST of the source code that contains the warning.
     *
     * @param fileName the absolute path of the file to read
     * @param line     the line of the warning
     * @param encoding the encoding of the file, if <code>null</code> or empty then the default encoding of the platform
     *                 is used
     * @return a has code of the source code
     */
    public String create(final Ast fileName, final int line, final String encoding) {
        return null;
    }
}
