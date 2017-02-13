package hudson.plugins.analysis.views;

import hudson.model.AbstractBuild;
import hudson.model.Run;
import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.analysis.util.model.LineRange;
import hudson.util.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.internal.util.collections.Sets;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static org.easymock.EasyMock.*;
import static org.mockito.Matchers.anyObject;

/**
 *  Tests the class {@link SourceDetail}.
 */
public class SourceDetailTest {

    /**
     *Checks if a warning over two whole lines is correctly rendered.
     */
    @Test
    public void renderOneWarningOverSeveralLines(){
        List<LineRange> lineRanges=new ArrayList<LineRange>();
        lineRanges.add(new LineRange(17,18));

        FileAnnotation annotation=createMock(FileAnnotation.class);
        expect(annotation.getLineRanges()).andReturn(lineRanges).anyTimes();
        expect(annotation.getFileName()).andReturn("/Users/gogic/IdeaProjects/analysis-core-plugin/src/test/resources/hudson/plugins/analysis/views/AbortException.txt").anyTimes();
        expect(annotation.getTempName((AbstractBuild<?, ?>)anyObject())).andReturn("/Users/gogic/IdeaProjects/analysis-core-plugin/src/test/resources/hudson/plugins/analysis/views/AbortException.txt").anyTimes();
        expect(annotation.getMessage()).andReturn("Message").anyTimes();
        expect(annotation.getToolTip()).andReturn("Tooltip").anyTimes();
        expect(annotation.getColumnStart()).andReturn(0).anyTimes();
        expect(annotation.getColumnEnd()).andReturn(0).anyTimes();

        replay(annotation);

        Set<FileAnnotation> annotations=Sets.newSet();
        annotations.add(annotation);

        try {
            split("ExpectedRenderingTwoWholeLines.html",annotations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if two warnings without intersections are correctly rendered.
     */
    @Test
    public void renderTwoWarningsWithoutIntesection(){
        List<LineRange> lineRanges1=new ArrayList<LineRange>();
        lineRanges1.add(new LineRange(17));

        FileAnnotation annotation1=createMock(FileAnnotation.class);
        expect(annotation1.getLineRanges()).andReturn(lineRanges1).anyTimes();
        expect(annotation1.getFileName()).andReturn("/Users/gogic/IdeaProjects/analysis-core-plugin/src/test/resources/hudson/plugins/analysis/views/AbortException.txt").anyTimes();
        expect(annotation1.getTempName((AbstractBuild<?, ?>)anyObject())).andReturn("/Users/gogic/IdeaProjects/analysis-core-plugin/src/test/resources/hudson/plugins/analysis/views/AbortException.txt").anyTimes();
        expect(annotation1.getMessage()).andReturn("Message").anyTimes();
        expect(annotation1.getToolTip()).andReturn("Tooltip").anyTimes();
        expect(annotation1.getColumnStart()).andReturn(4).anyTimes();
        expect(annotation1.getColumnEnd()).andReturn(10).anyTimes();

        replay(annotation1);

        List<LineRange> lineRanges2=new ArrayList<LineRange>();
        lineRanges2.add(new LineRange(33));

        FileAnnotation annotation2=createMock(FileAnnotation.class);
        expect(annotation2.getLineRanges()).andReturn(lineRanges2).anyTimes();
        expect(annotation2.getFileName()).andReturn("/Users/gogic/IdeaProjects/analysis-core-plugin/src/test/resources/hudson/plugins/analysis/views/AbortException.txt").anyTimes();
        expect(annotation2.getTempName((AbstractBuild<?, ?>)anyObject())).andReturn("/Users/gogic/IdeaProjects/analysis-core-plugin/src/test/resources/hudson/plugins/analysis/views/AbortException.txt").anyTimes();
        expect(annotation2.getMessage()).andReturn("Message").anyTimes();
        expect(annotation2.getToolTip()).andReturn("Tooltip").anyTimes();
        expect(annotation2.getColumnStart()).andReturn(4).anyTimes();
        expect(annotation2.getColumnEnd()).andReturn(10).anyTimes();

        replay(annotation2);

        Set<FileAnnotation> annotations=Sets.newSet();
        annotations.add(annotation1);
        annotations.add(annotation2);

        try {
            split("ExpectedRenderingTwoWarningsNoIntersections.html",annotations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void renderTwoWarningsWithIntersections(){

        List<LineRange> lineRanges1=new ArrayList<LineRange>();
        lineRanges1.add(new LineRange(17,33));

        FileAnnotation annotation1=createMock(FileAnnotation.class);
        expect(annotation1.getLineRanges()).andReturn(lineRanges1).anyTimes();
        expect(annotation1.getFileName()).andReturn("/Users/gogic/IdeaProjects/analysis-core-plugin/src/test/resources/hudson/plugins/analysis/views/AbortException.txt").anyTimes();
        expect(annotation1.getTempName((AbstractBuild<?, ?>)anyObject())).andReturn("/Users/gogic/IdeaProjects/analysis-core-plugin/src/test/resources/hudson/plugins/analysis/views/AbortException.txt").anyTimes();
        expect(annotation1.getMessage()).andReturn("Message").anyTimes();
        expect(annotation1.getToolTip()).andReturn("Tooltip").anyTimes();
        expect(annotation1.getColumnStart()).andReturn(0).anyTimes();
        expect(annotation1.getColumnEnd()).andReturn(0).anyTimes();

        replay(annotation1);

        List<LineRange> lineRanges2=new ArrayList<LineRange>();
        lineRanges2.add(new LineRange(24));

        FileAnnotation annotation2=createMock(FileAnnotation.class);
        expect(annotation2.getLineRanges()).andReturn(lineRanges2).anyTimes();
        expect(annotation2.getFileName()).andReturn("/Users/gogic/IdeaProjects/analysis-core-plugin/src/test/resources/hudson/plugins/analysis/views/AbortException.txt").anyTimes();
        expect(annotation2.getTempName((AbstractBuild<?, ?>)anyObject())).andReturn("/Users/gogic/IdeaProjects/analysis-core-plugin/src/test/resources/hudson/plugins/analysis/views/AbortException.txt").anyTimes();
        expect(annotation2.getMessage()).andReturn("Message").anyTimes();
        expect(annotation2.getToolTip()).andReturn("Tooltip").anyTimes();
        expect(annotation2.getColumnStart()).andReturn(0).anyTimes();
        expect(annotation2.getColumnEnd()).andReturn(0).anyTimes();

        replay(annotation2);

        Set<FileAnnotation> annotations=Sets.newSet();
        annotations.add(annotation1);
        annotations.add(annotation2);

        try {
            split("ExpectedRenderingWithRangeIntersection.html",annotations);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks whether we correctly split the source into several warning ranges.
     *
     * @param fileName
     *            the filename of the expected result
     * @param annotations
     *            annotations to test
     * @throws IOException
     *             in case of an IO error
     */
    @SuppressWarnings("unchecked")
    private void split(final String fileName, final  Set<FileAnnotation> annotations) throws IOException {

        SourceDetail source=createSourceDetail(annotations);

        List<String> expected = IOUtils.readLines(SourceDetailTest.class.getResourceAsStream(fileName));
        List<String> actual = IOUtils.readLines(new StringReader(source.getSourceCode()));

        Iterator<String> expectedIterator = expected.iterator();
        Iterator<String> actualIterator = actual.iterator();
        while (actualIterator.hasNext()) {
            String expectedLine = expectedIterator.next().trim();
            String actualLine = actualIterator.next().trim();

            Assert.assertEquals(expectedLine, actualLine);
        }

        //verify(annotations);
    }

    @SuppressWarnings("unchecked")
    private SourceDetail createSourceDetail(final Set<FileAnnotation> annotation) {
        return new SourceDetail((Run<?, ?>)null, annotation, null);
    }

}