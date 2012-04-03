package hudson.plugins.analysis.collector; // NOPMD

import hudson.model.AbstractBuild;
import hudson.plugins.analysis.core.BuildHistory;
import hudson.plugins.analysis.core.ParserResult;
import hudson.plugins.analysis.core.ResultAction;
import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.util.model.FileAnnotation;

import java.io.IOException;
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

    /** Number of annotations by origin mapping. Serialized @since 1.20. */
    private Map<String, Integer> annotationsByOrigin;

    private transient Object mappingLock = new Object();

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
    public AnalysisResult(final AbstractBuild<?, ?> build, final String defaultEncoding, final ParserResult result) {
        this(build, new BuildHistory(build, AnalysisResultAction.class), result, defaultEncoding, true);
    }

    AnalysisResult(final AbstractBuild<?, ?> build, final BuildHistory history,
            final ParserResult result, final String defaultEncoding, final boolean canSerialize) {
        super(build, history, result, defaultEncoding);

        annotationsByOrigin = countAnnotations();
        if (canSerialize) {
            serializeAnnotations(result.getAnnotations());
        }
    }

    @Override
    public String getHeader() {
        return Messages.Analysis_ResultAction_Header();
    }

    @Override
    protected Object readResolve() {
        super.readResolve();

        mappingLock = new Object();

        return this;
    }

    /**
     * Count the annotations by origin.
     *
     * @return the mapping
     */
    private Map<String, Integer> countAnnotations() {
        Map<String, Integer> mapping = Maps.newHashMap();
        for (FileAnnotation annotation : getAnnotations()) {
            if (!mapping.containsKey(annotation.getOrigin())) {
                mapping.put(annotation.getOrigin(), 0);
            }
            mapping.put(annotation.getOrigin(),
                    mapping.get(annotation.getOrigin()) + 1);
        }
        return mapping;
    }

    @Override
    public String getSummary() {
        return AnalysisResultSummary.createSummary(this);
    }

    @Override
    protected String createDeltaMessage() {
        return AnalysisResultSummary.createDeltaMessage(this);
    }

    @Override
    protected String getSerializationFileName() {
        return "analysis.xml";
    }

    /** {@inheritDoc} */
    public String getDisplayName() {
        return Messages.Analysis_ProjectAction_Name();
    }

    @Override
    protected Class<? extends ResultAction<? extends BuildResult>> getResultActionType() {
        return AnalysisResultAction.class;
    }

    /**
     * Returns the number of annotations from the specified origin. If there are
     * no annotations, then 0 is returned.
     *
     * @param origin
     *            the origin
     * @return the number of annotations from the specified origin
     */
    public int getNumberOfAnnotationsByOrigin(final String origin) {
        synchronized (mappingLock) {
            if (annotationsByOrigin == null) {
                annotationsByOrigin = countAnnotations();
                try {
                    getOwner().save();
                }
                catch (IOException exception) {
                    // ignore
                }
            }
        }
        if (annotationsByOrigin.containsKey(origin)) {
            return annotationsByOrigin.get(origin);
        }
        return 0;
    }
}