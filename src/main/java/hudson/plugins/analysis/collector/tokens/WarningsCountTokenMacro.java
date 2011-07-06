package hudson.plugins.analysis.collector.tokens;

import hudson.Extension;
import hudson.plugins.analysis.collector.AnalysisResultAction;
import hudson.plugins.analysis.tokens.AbstractAnnotationsCountTokenMacro;

/**
 * Provides a token that evaluates to the number of warnings.
 *
 * @author Ulli Hafner
 */
@Extension(optional = true)
public class WarningsCountTokenMacro extends AbstractAnnotationsCountTokenMacro {
    /**
     * Creates a new instance of {@link WarningsCountTokenMacro}.
     */
    @SuppressWarnings("unchecked")
    public WarningsCountTokenMacro() {
        super("ANALYSIS_COUNT", AnalysisResultAction.class);
    }
}

