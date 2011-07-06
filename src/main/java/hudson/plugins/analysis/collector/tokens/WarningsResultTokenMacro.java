package hudson.plugins.analysis.collector.tokens;

import hudson.Extension;
import hudson.plugins.analysis.collector.AnalysisResultAction;
import hudson.plugins.analysis.tokens.AbstractResultTokenMacro;

/**
 * Provides a token that evaluates to the warnings collector build result.
 *
 * @author Ulli Hafner
 */
@Extension(optional = true)
public class WarningsResultTokenMacro extends AbstractResultTokenMacro {
    /**
     * Creates a new instance of {@link WarningsResultTokenMacro}.
     */
    @SuppressWarnings("unchecked")
    public WarningsResultTokenMacro() {
        super("ANALYSIS_RESULT", AnalysisResultAction.class);
    }
}

