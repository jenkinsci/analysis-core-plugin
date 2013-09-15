package hudson.plugins.analysis.core;

import java.io.Serializable;

/**
 * Serializable settings that could be transferred to slaves.
 *
 * @author Ulli Hafner
 */
public class SerializableSettings implements Settings, Serializable {
    private static final long serialVersionUID = 2078877884081589761L;

    private final boolean failOnCorrupt;
    private final boolean quietMode;
    private final AnalysisConfiguration[] configurations;

    /**
     * Creates a new instance of {@link SerializableSettings}.
     *
     * @param original
     *            the original settings
     */
    public SerializableSettings(final Settings original) {
        failOnCorrupt = original.getFailOnCorrupt();
        quietMode = original.getQuietMode();
        configurations = copy(original.getConfigurations());
    }

    private AnalysisConfiguration[] copy(final AnalysisConfiguration[] source) {
        AnalysisConfiguration[] copied = new AnalysisConfiguration[source.length];
        System.arraycopy(source, 0, copied, 0, source.length);
        return copied;
    }

    @Override
    public Boolean getQuietMode() {
        return quietMode;
    }

    @Override
    public Boolean getFailOnCorrupt() {
        return failOnCorrupt;
    }

    @Override
    public AnalysisConfiguration[] getConfigurations() {
        return copy(configurations);
    }
}

