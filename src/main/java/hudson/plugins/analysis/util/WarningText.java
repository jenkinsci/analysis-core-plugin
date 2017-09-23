package hudson.plugins.analysis.util;

import java.io.IOException;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import hudson.plugins.analysis.ast.MethodAst;
import org.apache.commons.io.LineIterator;

import static org.apache.tools.ant.taskdefs.optional.jsp.JspNameMangler.keywords;

/**
 * Computes the text of the method for the given warning.
 */
public class WarningText {
    private static final int BUFFER_SIZE = 1000;

    public String create(final String fileName, final int line, final String encoding) throws IOException {
        if (fileName.contains(".java")) { // FIXME if the file is no java-file, it`s not possible to build an AST
            LineIterator lineIterator = EncodingValidator.readFile(fileName, encoding);
            StringBuilder context = new StringBuilder(BUFFER_SIZE);

            //get text with MethodAST
            MethodAst a = new MethodAst(fileName, line);
            DetailAST dA = a.findMethodStart();
            //get first and last line number of the method
            int firstLine = dA.getLineNo();
            int lastLine;
            if (dA.getNextSibling() != null) {
                lastLine = dA.getNextSibling().getLineNo() - 1;
            } else {
                lastLine = a.getLastLineNumber() - 1;
            }

            for (int i = 0; lineIterator.hasNext(); i++) {
                String currentLine = lineIterator.nextLine();
                //get the last five lines of Javadoc by scanning above the method start for lines with a "*".
                if (i >= firstLine - 5 && i < firstLine && currentLine.contains("*")) {
                    context.append(currentLine);
                }
                //get Method-text but not the Javadoc of the following method.
                if (i >= firstLine - 1 && i <= lastLine) {
                    if (!currentLine.contains("*") && !currentLine.contains("@")) {
                        context.append(currentLine);
                    }
                }
                if (i > lastLine) {
                    break;
                }
            }
            lineIterator.close();

            //remove java-specific keywords from the text
            String text = context.toString();
            for (String keyword : keywords)
            text = text.replaceAll(keyword, "");

            return text;
        }
        else {
            return "empty";
        }
    }
}
