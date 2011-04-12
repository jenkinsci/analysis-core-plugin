package hudson.plugins.analysis.collector.tokens;

import hudson.Extension;
import hudson.plugins.analysis.collector.AnalysisResultAction;
import hudson.plugins.analysis.tokens.AbstractResultTokenMacro;

/**
 * Provides a token that evaluates to the number of warnings.
 *
 * @author Ulli Hafner
 */
@Extension(optional = true)
public class WarningsCountTokenMacro extends AbstractResultTokenMacro {
    /**
     * Creates a new instance of {@link WarningsCountTokenMacro}.
     */
    public WarningsCountTokenMacro() {
        super(AnalysisResultAction.class, "ANALYSIS_COUNT");
    }
}

