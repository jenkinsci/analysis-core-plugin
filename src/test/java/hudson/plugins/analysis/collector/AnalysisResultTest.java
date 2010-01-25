package hudson.plugins.analysis.collector;

import static junit.framework.Assert.*;
import hudson.model.AbstractBuild;
import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.core.ParserResult;
import hudson.plugins.analysis.test.BuildResultTest;

/**
 * Tests the class {@link AnalysisResult}.
 */
public class AnalysisResultTest extends BuildResultTest<AnalysisResult> {
    /** {@inheritDoc} */
    @Override
    protected AnalysisResult createBuildResult(final AbstractBuild<?, ?> build, final ParserResult project) {
        return new AnalysisResult(build, null, project);
    }

    /** {@inheritDoc} */
    @Override
    protected AnalysisResult createBuildResult(final AbstractBuild<?, ?> build, final ParserResult project, final AnalysisResult previous) {
        return new AnalysisResult(build, null, project, previous);
    }

    /** {@inheritDoc} */
    @Override
    protected void verifyHighScoreMessage(final int expectedZeroWarningsBuildNumber, final boolean expectedIsNewHighScore, final long expectedHighScore, final long gap, final AnalysisResult result) {
        if (result.hasNoAnnotations() && result.getDelta() == 0) {
            assertTrue(result.getDetails().contains(Messages.Analysis_ResultAction_NoWarningsSince(expectedZeroWarningsBuildNumber)));
            if (expectedIsNewHighScore) {
                long days = BuildResult.getDays(expectedHighScore);
                if (days == 1) {
                    assertTrue(result.getDetails().contains(Messages.Analysis_ResultAction_OneHighScore()));
                }
                else {
                    assertTrue(result.getDetails().contains(Messages.Analysis_ResultAction_MultipleHighScore(days)));
                }
            }
            else {
                long days = BuildResult.getDays(gap);
                if (days == 1) {
                    assertTrue(result.getDetails().contains(Messages.Analysis_ResultAction_OneNoHighScore()));
                }
                else {
                    assertTrue(result.getDetails().contains(Messages.Analysis_ResultAction_MultipleNoHighScore(days)));
                }
            }
        }
    }
}

