package hudson.plugins.analysis.collector; // NOPMD

import hudson.model.AbstractBuild;
import hudson.plugins.analysis.core.BuildHistory;
import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.core.ParserResult;
import hudson.plugins.analysis.core.ResultAction;
import hudson.plugins.analysis.util.model.FileAnnotation;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Stores the results of the analysis plug-ins. One instance of this class is
 * persisted for each build via an XML file.
 *
 * @author Ulli Hafner
 */
public class AnalysisResult extends BuildResult {
    /** Unique identifier of this class. */
    private static final long serialVersionUID = 847650789493429154L;

    /** Number of annotations by origin mapping. */
    private final Map<String, Integer> annotationsByOrigin = Maps.newHashMap();

    /**
     * Creates a new instance of {@link AnalysisResult}.
     *
     * @param build
     *            the current build as owner of this action
     * @param defaultEncoding
     *            the default encoding to be used when reading and parsing files
     * @param result
     *            the parsed result with all annotations
     * @param history
     *            the plug-in history
     */
    public AnalysisResult(final AbstractBuild<?, ?> build, final String defaultEncoding,
            final ParserResult result, final BuildHistory history) {
        super(build, defaultEncoding, result, history);

        countAnnotations();
    }

    /**
     * Creates a new instance of {@link AnalysisResult}.
     *
     * @param build
     *            the current build as owner of this action
     * @param defaultEncoding
     *            the default encoding to be used when reading and parsing files
     * @param result
     *            the parsed result with all annotations
     */
    public AnalysisResult(final AbstractBuild<?, ?> build, final String defaultEncoding,
            final ParserResult result) {
        super(build, defaultEncoding, result);

        countAnnotations();
    }

    /**
     * Count the annotations by origin.
     */
    private void countAnnotations() {
        for (FileAnnotation annotation : getAnnotations()) {
            if (!annotationsByOrigin.containsKey(annotation.getOrigin())) {
                annotationsByOrigin.put(annotation.getOrigin(), 0);
            }
            annotationsByOrigin.put(annotation.getOrigin(),
                    annotationsByOrigin.get(annotation.getOrigin()) + 1);
        }
    }

    /**
     * Returns a summary message for the summary.jelly file.
     *
     * @return the summary message
     */
    public String getSummary() {
        return AnalysisResultSummary.createSummary(this);
    }

    /** {@inheritDoc} */
    @Override
    protected String createDeltaMessage() {
        return AnalysisResultSummary.createDeltaMessage(this);
    }

    /** {@inheritDoc} */
    @Override
    protected String getSerializationFileName() {
        return "analysis.xml";
    }

    /** {@inheritDoc} */
    public String getDisplayName() {
        return Messages.Analysis_ProjectAction_Name();
    }

    /** {@inheritDoc} */
    @Override
    protected Class<? extends ResultAction<? extends BuildResult>> getResultActionType() {
        return AnalysisResultAction.class;
    }

    /**
     * Returns the number of annotations from the specified origin. If there are no anntoations
     *
     * @param origin
     *            the origin
     * @return the number of annotations from the specified origin
     */
    public int getNumberOfAnnotationsByOrigin(final String origin) {
        if (annotationsByOrigin.containsKey(origin)) {
            return annotationsByOrigin.get(origin);
        }
        return 0;
    }
}