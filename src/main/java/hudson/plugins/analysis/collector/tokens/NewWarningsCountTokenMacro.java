package hudson.plugins.analysis.collector.tokens;

import hudson.Extension;
import hudson.plugins.analysis.collector.AnalysisResultAction;
import hudson.plugins.analysis.tokens.AbstractNewAnnotationsTokenMacro;

/**
 * Provides a token that evaluates to the number of new warnings.
 *
 * @author Ulli Hafner
 */
@Extension(optional = true)
public class NewWarningsCountTokenMacro extends AbstractNewAnnotationsTokenMacro {
    /**
     * Creates a new instance of {@link NewWarningsCountTokenMacro}.
     */
    @SuppressWarnings("unchecked")
    public NewWarningsCountTokenMacro() {
        super("ANALYSIS_NEW", AnalysisResultAction.class);
    }
}

