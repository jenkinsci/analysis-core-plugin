package hudson.plugins.analysis.collector.tokens;

import hudson.Extension;
import hudson.plugins.analysis.collector.AnalysisResultAction;
import hudson.plugins.analysis.tokens.AbstractFixedAnnotationsTokenMacro;

/**
 * Provides a token that evaluates to the number of new warnings.
 *
 * @author Ulli Hafner
 */
@Extension(optional = true)
public class FixedWarningsCountTokenMacro extends AbstractFixedAnnotationsTokenMacro {
    /**
     * Creates a new instance of {@link FixedWarningsCountTokenMacro}.
     */
    @SuppressWarnings("unchecked")
    public FixedWarningsCountTokenMacro() {
        super("ANALYSIS_FIXED", AnalysisResultAction.class);
    }
}

