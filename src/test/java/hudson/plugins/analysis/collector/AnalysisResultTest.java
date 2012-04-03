package hudson.plugins.analysis.collector;

import hudson.model.AbstractBuild;
import hudson.plugins.analysis.core.BuildHistory;
import hudson.plugins.analysis.core.ParserResult;
import hudson.plugins.analysis.test.BuildResultTest;

/**
 * Tests the class {@link AnalysisResult}.
 */
public class AnalysisResultTest extends BuildResultTest<AnalysisResult> {
    @Override
    protected AnalysisResult createBuildResult(final AbstractBuild<?, ?> build, final ParserResult project, final BuildHistory history) {
        return new AnalysisResult(build, history, project, "UTF8", false);
    }
}

