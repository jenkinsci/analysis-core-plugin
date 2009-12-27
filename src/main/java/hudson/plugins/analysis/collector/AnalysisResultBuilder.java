package hudson.plugins.analysis.collector;

import hudson.model.AbstractBuild;
import hudson.plugins.analysis.core.ParserResult;

/**
 * Creates a new analysis result based on the values of a previous build and the
 * current project.
 *
 * @author Ulli Hafner
 */
public class AnalysisResultBuilder {
    /**
     * Creates a result that persists the analysis information for the specified
     * build.
     *
     * @param build
     *            the build to create the action for
     * @param result
     *            the result containing the annotations
     * @param defaultEncoding
     *            the default encoding to be used when reading and parsing files
     * @return the result action
     */
    public AnalysisResult build(final AbstractBuild<?, ?> build, final ParserResult result, final String defaultEncoding) {
        Object previous = build.getPreviousBuild();
        while (previous instanceof AbstractBuild<?, ?>) {
            AbstractBuild<?, ?> previousBuild = (AbstractBuild<?, ?>)previous;
            AnalysisResultAction previousAction = previousBuild.getAction(AnalysisResultAction.class);
            if (previousAction != null) {
                return new AnalysisResult(build, defaultEncoding, result, previousAction.getResult());
            }
            previous = previousBuild.getPreviousBuild();
        }
        return new AnalysisResult(build, defaultEncoding, result);
    }
}

