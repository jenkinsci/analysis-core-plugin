package hudson.plugins.analysis.views;

import com.infradna.tool.bridge_method_injector.WithBridgeMethods;
import hudson.model.AbstractBuild;
import hudson.model.ModelObject;
import hudson.model.Run;
import hudson.plugins.analysis.util.*;
import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.analysis.util.model.LineRange;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Renders a source file containing an annotation for the whole file or a
 * specific line number.
 *
 * @author Ulli Hafner
 */
@SuppressWarnings("PMD.CyclomaticComplexity")
public class SourceDetail implements ModelObject {

    /**
     * The current build as owner of this object.
     */
    private final Run<?, ?> owner;

    /**
     * Stripped file name of this annotation without the path prefix.
     */
    private final String fileName;

    /**
     * The annotations to be shown.
     */
    private Set<FileAnnotation> annotations;

    /**
     * The rendered source file.
     */
    private String sourceCode = StringUtils.EMPTY;

    /**
     * The default encoding to be used when reading and parsing files.
     */
    private final String defaultEncoding;


    /**
     * Determines which color has to be used.
     */
    private boolean isFirstColor;


    /**
     * Creates a new instance of this source code object.
     *
     * @param owner           the current build as owner of this object
     * @param annotation      the warning to display in the source file
     * @param defaultEncoding the default encoding to be used when reading and parsing files
     */
    public SourceDetail(Run<?, ?> owner, FileAnnotation annotation, String defaultEncoding) {
        this.owner = owner;
        this.annotations = new TreeSet<FileAnnotation>();
        this.isFirstColor = true;
        this.annotations.add(annotation);
        this.defaultEncoding = defaultEncoding;
        fileName = StringUtils.substringAfterLast(annotation.getFileName(), SourceDetail.FILE_SEPARATOR);
        initializeContent();
    }

    /**
     * Creates a new instance of this source code object.
     *
     * @param owner           the current build as owner of this object
     * @param annotations     the warnings to display in the source file
     * @param defaultEncoding the default encoding to be used when reading and parsing files
     */
    public SourceDetail(Run<?, ?> owner, Set<FileAnnotation> annotations, String defaultEncoding) {
        this.owner = owner;
        this.annotations = annotations;
        this.defaultEncoding = defaultEncoding;
        this.isFirstColor=true;
        fileName = StringUtils.substringAfterLast(getFirstAnnotation(annotations).getFileName(), SourceDetail.FILE_SEPARATOR);
        initializeContent();
    }

    /**
     * Initializes the content of the source file: reads the file, colors it, and
     * splits it into three parts.
     */
    private void initializeContent() {
        BufferedReader file = null;
        try {
            File tempFile = new File(getFirstAnnotation(annotations).getTempName(owner));
            if (tempFile.exists()) {
                file = new BufferedReader(new FileReader(tempFile));
            } else {
                file = new BufferedReader(new FileReader(new File(getFirstAnnotation(annotations).getFileName())));
            }
            splitSourceFile(readFile(file));
        } catch (IOException exception) {
            sourceCode = "Can't read file: " + exception.getLocalizedMessage();
        } finally {
            IOUtils.closeQuietly(file);
        }
    }


    @Override
    public String getDisplayName() {
        return fileName;
    }

    /**
     * Reads source file, removes code tags and escapes strings enclosed with angle brackets.
     *
     * @param file source file
     * @return source file
     */
    private String readFile(BufferedReader file) throws IOException {
        int capacity = 50000;
        StringBuilder stringBuilder = new StringBuilder(capacity);
        String line = StringUtils.EMPTY;
        stringBuilder.append(HTML_PRE_TAG_START);

        while ((line = file.readLine()) != null) {

            if (containsPattern(line, codeTagPattern)) {
                line = replacePattern(line, codeTagPattern, "");
            }

            if (containsPattern(line, angleBracketPattern)) {
                outputEscaped(stringBuilder, line);
                stringBuilder.append(SourceDetail.LINE_SEPARATOR);
            } else {
                stringBuilder.append(line);
                stringBuilder.append(SourceDetail.LINE_SEPARATOR);
            }
        }
        stringBuilder.append(HTML_PRE_TAG_END);
        file.close();
        return stringBuilder.toString();
    }

    /**
     * Splits source file in multiple parts
     *
     * @param sourceFile source file
     */
    public void splitSourceFile(String sourceFile) {
        LineIterator lineIterator = IOUtils.lineIterator(new StringReader(sourceFile));
        int lineNumber = 1;
        int capacity = 50000;
        StringBuilder output = new StringBuilder(capacity);
        Matrix warningMatrix = getWarningMatrix(getAnnotations());

        while (lineIterator.hasNext()) {
            List<RangeLimiter> lineEntries = warningMatrix.getLine(lineNumber);
            if(warningMatrix.hasDoubleWarnings()){
                output.append(warningButtons(warningMatrix.getDoubleWarnings(lineNumber)));
            }

            if (lineEntries.size() > 0) {
                String lineWithHTMLTags = lineWithHTMLTags(lineNumber, lineIterator.next(), lineEntries);
                output.append(lineWithHTMLTags);
                output.append(LINE_SEPARATOR);
                lineNumber++;
            } else {
                copyLine(output, lineIterator);
                lineNumber++;
            }
        }
        sourceCode = output.toString();
    }

    /**
     * This method inserts HTML elements into the lines of the source text.
     * These HTML-elements are required for coloring of the warning areas.
     *
     * @param lineNumber
     * @param line    a line from the source code file
     * @param columns a list with range limiter situated in the line with number=lineNumber
     * @return Mit HTML-Elementen versetzte Zeile des Quelltextes
     */
    private String lineWithHTMLTags(int lineNumber, String line, List columns) {
        Iterator iterator = columns.iterator();
        int lineLength = line.length();
        TokenSequence tokenSequence = new TokenSequence(line);

        while (iterator.hasNext()) {
            RangeLimiter limiter =(RangeLimiter) iterator.next();
            int columnNr=limiter.getColumn();

            // Zeichen fuer Zeilenende aufloesen.
            if (columnNr == Integer.MAX_VALUE) {
                columnNr = lineLength - 1;
            }
            // Adjust the index. Currently, indexes of warnings are equal to index-1 in the text line.
            else if (columnNr > 0) {
                columnNr = columnNr - 1;
            }
            /**
             * In the case that an FileNotFound Exception is thrown while loading the source file.
             */
            if (columnNr >= lineLength) {
                for (int i = -1; i < columnNr - lineLength; i++) {
                    tokenSequence.appendSequence(" ");
                }
            }
            if (limiter.isStart()) {
                tokenSequence.prependStringToToken(buildOpeningTag(limiter.getMessage(), limiter.getToolTip()), columnNr);
            } else {
                tokenSequence.appendStringToToken(HTML_SPAN_TAG_END, columnNr);
            }
        }
        return tokenSequence.toString();
    }

    /**
     * Writes the message to the output stream (with escaped HTML).
     *
     * @param output  the output to write to
     * @param message the message to write
     */
    private void outputEscaped(StringBuilder output, String message) {
        output.append(StringEscapeUtils.escapeHtml(message));
    }

    /**
     * Copies the next line of the input to the output.
     *
     * @param output       output
     * @param lineIterator input
     */
    private void copyLine(StringBuilder output, LineIterator lineIterator) {
        output.append(lineIterator.nextLine());
        output.append(SourceDetail.LINE_SEPARATOR);
    }

    /**
     * Gets the file name of this source file.
     *
     * @return the file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Returns the build as owner of this object.
     *
     * @return the build
     */
    @WithBridgeMethods(value = AbstractBuild.class, adapterMethod = "getAbstractBuild")
    public Run<?, ?> getOwner() {
        return owner;
    }

    /**
     * Added for backward compatibility. It generates <pre>AbstractBuild getOwner()</pre> bytecode during the build
     * process, so old implementations can use that signature.
     *
     * @see {@link WithBridgeMethods}
     */
    @Deprecated
    private Object getAbstractBuild(Run owner, Class targetClass) {
        //noinspection ReturnOfNull
        return (owner instanceof AbstractBuild) ? (AbstractBuild) owner : null;
    }

    /**
     * Returns the line that should be highlighted.
     *
     * @return the line to highlight
     */
    public String getSourceCode() {
        return sourceCode;
    }

    /**
     * Creates a new instance of this source code object.
     *
     * @param owner           the current build as owner of this object
     * @param annotation      the warning to display in the source file
     * @param defaultEncoding the default encoding to be used when reading and parsing files
     * @deprecated use {@link #SourceDetail(Run, FileAnnotation, String)} instead
     */
    @Deprecated
    public SourceDetail(AbstractBuild<?, ?> owner, FileAnnotation annotation, String defaultEncoding) {
        this((Run<?, ?>) owner, annotation, defaultEncoding);
    }

    /**
     * Determines which color has to be used to dye warning areas.
     * The areas are alternately colored with orange and yellow.
     *
     * @param isFirstColor
     * @return rangeColor
     */
    private String getRangeColor(boolean isFirstColor) {
        if (isFirstColor) {
            setFirstColor(!isFirstColor);
            return SourceDetail.FIRST_COLOR;
        } else {
            setFirstColor(!isFirstColor);
            return SourceDetail.OTHER_COLOR;
        }
    }

    public boolean isFirstColor() {
        return this.isFirstColor;
    }

    public void setFirstColor(boolean firstColor) {
        this.isFirstColor = firstColor;
    }

    /**
     * This method builds the matrix, which marks the individual warning areas and contains a warning message and tooltip for each area.
     * The information about the warnings are obtained from the file annotations which are passed to the method as parameter.
     * The matrix is used by the splitSourceFile method to mark the warning areas in the source text.
     *
     * @param annotations FileAnnotations
     * @return Object of the class Matrix containing warning ranges
     */
    private Matrix getWarningMatrix(Set<FileAnnotation> annotations) {
        Matrix matrix = new Matrix();
        ArrayList<Range> ranges = new ArrayList<Range>(50);
        int number = 1;

        for (FileAnnotation annotation : annotations) {

            Collection<LineRange> lineRanges = annotation.getLineRanges();
            //Informationen wie message, tooltip start- und end-column auslesen um sie im Range Limter abzulegen.
            String message = annotation.getMessage();
            String toolTip = annotation.getToolTip();

            if(message.isEmpty()){
                message=toolTip;
            }

            int startColumn = annotation.getColumnStart();
            int endColumn = annotation.getColumnEnd();
            boolean firstRange = true;

            for (LineRange lineRange : lineRanges) {
                int startLine = lineRange.getStart();
                int endLine = lineRange.getEnd();
                RangeLimiter start;
                RangeLimiter end;

                if(startLine<=0){
                    matrix.addButton(new RangeLimiter(true,message,toolTip,1,0,number));
                }
                if (firstRange) {
                    if ((startColumn == endColumn) && startColumn == 0) {
                        start = new RangeLimiter(true, message, toolTip, startLine, startColumn, number);
                        end = new RangeLimiter(false, message, toolTip, endLine, Integer.MAX_VALUE, number);
                    }
                    else {
                        start = new RangeLimiter(true, message, toolTip, startLine, startColumn, number);
                        end = new RangeLimiter(false, message, toolTip, endLine, endColumn, number);
                    }
                    firstRange = false;
                }
                else {
                    start = new RangeLimiter(true, message, toolTip, startLine, 0, number);
                    end = new RangeLimiter(false, message, toolTip, endLine, Integer.MAX_VALUE, number);
                }
                number++;
                ranges.add(new Range(start, end));
            }
        }
        Collections.sort(ranges);

        //Starting with the largest, add the ranges to the matrix.
        int rangesSize = ranges.size();
        for (int i = rangesSize - 1; i >= 0; i--) {

            Range range = ranges.get(i);
            RangeLimiter start = range.getStart();
            RangeLimiter end = range.getEnd();
            boolean insertSuceeded = true;

            if (matrix.addElement(start)) {
                insertSuceeded = matrix.addElement(end);
            }
            else{
                matrix.addButton(start);
            }

            if (!insertSuceeded) {
                matrix.removeElement(start);
                matrix.addButton(start);
            }

            // Resolve intersecions between Ranges.
            if(matrix.rangesIntersect()) {
                matrix.resolveIntersections();
            }
        }
        return matrix;
    }

    /**
     *
     * @param rangeLimiters
     * @return String HTML-Button to show warning message in case that two ranges have same start or end.
     */
    private String warningButtons(List<RangeLimiter> rangeLimiters){
        Iterator iterator=rangeLimiters.iterator();
        StringBuilder sb=new StringBuilder(5000);

        while (iterator.hasNext()){
            RangeLimiter tmp=(RangeLimiter)iterator.next();

            sb.append("<button onclick='dhtmlx.modalbox({title:\"");

            String message=tmp.getMessage();
            message = replacePattern(message, lineBreakPattern, "");
            message = replacePattern(message, apostrophesPattern, "");
            sb.append(message);

            sb.append("\", text:\"");

            String toolTip=tmp.getToolTip();
            toolTip = replacePattern(toolTip, lineBreakPattern, "</br>");
            toolTip = replacePattern(toolTip, apostrophesPattern, "");
            sb.append(toolTip);

            sb.append("\",buttons:[\"CLOSE\"]})' style=\"font-size:13px;padding:0px 6px; font-family: 'Times New Roman';  color: black;font-weight: bolder; border-width:1px;  border-radius:12px;  background-color: darkorange\">!</button> ");
        }
        return sb.toString();
    }

    /**
     * Puts annotation in a set which is used in method splitSourceFile.
     *
     * @return set with one FileAnnotation.
     */
    private Set<FileAnnotation> getTempAnnotationSet() {
        Set<FileAnnotation> tempSet = new TreeSet<FileAnnotation>();
        tempSet.add(getFirstAnnotation(annotations));
        return tempSet;
    }

    /**
     * @param line    a line of source file
     * @param pattern pattern to search
     * @return true if line contains given pattern
     */
    public boolean containsPattern(CharSequence line, Pattern pattern) {
        return pattern.matcher(line).find();
    }

    /**
     * Removes substrings from string coressponding to given pattern.
     *
     * @param string
     * @param pattern Pattern to remove.
     * @return String without substrings correspoding to pattern.
     */
    public String replacePattern(CharSequence string, Pattern pattern, String replacement) {
        Matcher matcher = pattern.matcher(string);
        int capacity = 1000;
        StringBuffer buffer = new StringBuffer(capacity);

        while (matcher.find()) {
            matcher.appendReplacement(buffer, replacement);
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    /**
     * Builds a string containing HTML span-tag surrounding annotation message and tooltip.
     * Annotation message and tooltip are shown in a popup message when user clicks on colored field
     * that marks a warning.
     *
     * @return prependString
     */
    private String buildOpeningTag(String message, String toolTip) {

        message = replacePattern(message, lineBreakPattern, "</br>");
        message = replacePattern(message, apostrophesPattern, "");

        toolTip = replacePattern(toolTip, lineBreakPattern, "</br>");
        toolTip = replacePattern(toolTip, apostrophesPattern, "");

        return "<span title=\"For more information click on orange or yellow colored area.\"   style=\"background-color:"
                + getRangeColor(isFirstColor())
                + '"'
                + " onclick='dhtmlx.modalbox({title:\""
                + message
                + "\",text:\""
                + toolTip
                + "\",buttons:[\"CLOSE\"]})'>";
    }

    /**
     * Returns the first annotation in annotations set. Needed to initialize content.
     *
     * @param annotations FileAnnotations.
     * @return first annotation from annotations set.
     */
    private FileAnnotation getFirstAnnotation(Collection<FileAnnotation> annotations) {
        Iterator iterator = annotations.iterator();
        return (FileAnnotation) iterator.next();
    }

    public Set<FileAnnotation> getAnnotations() {
        return this.annotations;
    }


    /**
     * Pattern needed to detect line breaks.
     */
    private static final Pattern lineBreakPattern = Pattern.compile(SourceDetail.LINE_BREAK);
    /**
     * Pattern needed to detect apostrophes.
     */
    private static final Pattern apostrophesPattern = Pattern.compile(SourceDetail.APOSTROPHES_REGEX);

    /**
     * Pattern needed to detect HTML brackets in the source file.
     */
    private static final Pattern angleBracketPattern = Pattern.compile(SourceDetail.ANGLE_BRACKET_REGEX);

    /**
     * Pattern needed to detect HTML-code tags in the source file.
     */
    private static final Pattern codeTagPattern = Pattern.compile(SourceDetail.HTML_CODE_TAGS);

    /**
     * Represents a line break.
     */
    private static final String LINE_BREAK = "\n";

    /**
     * Represents apostrophes.
     */
    private static final String APOSTROPHES_REGEX = "'|\"";

    /**
     * Represents any string enclosed in angle brackets.
     */
    private static final String ANGLE_BRACKET_REGEX = "<.*>|<|>";

    /**
     * Represents HTML-code tags.
     */
    private static final String HTML_CODE_TAGS = "<code>|</code>";

    /**
     * First warning color.Two different colors are needed to separate warnings optically.
     */
    private static final String FIRST_COLOR = "#F7DB60";

    /**
     * Second warning color. Two different colors are needed for optical separation of warnings.
     */
    private static final String OTHER_COLOR = "#FE9A65";

    /**
     * Represents a line separator.
     */
    private static final char LINE_SEPARATOR = '\n';

    /**
     * Represents a file separator.
     */
    private static final String FILE_SEPARATOR = "/";

    /**
     * HTML Pre-tag with Google Code Prettify specific options.
     */
    private static final String HTML_PRE_TAG_START = "<pre class=\"prettyprint linenums\">";

    /**
     * HTML Pre-tag end.
     */
    private static final String HTML_PRE_TAG_END = "</pre>";

    /**
     * HTML Span-tag end.
     */
    private static final String HTML_SPAN_TAG_END = "</span>";

}