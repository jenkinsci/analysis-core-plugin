package hudson.plugins.analysis.collector;

import hudson.plugins.analysis.util.model.AbstractAnnotation;

import org.apache.commons.lang.StringUtils;

/**
 * A serializable Java Bean class representing a warning.
 * <p>
 * Note: this class has a natural ordering that is inconsistent with equals.
 * </p>
 *
 * @author Ulli Hafner
 */
public class Warning extends AbstractAnnotation {
    /** Unique identifier of this class. */
    private static final long serialVersionUID = 6006949676687744594L;
    /** The plug-in that created this warning. */
    private final String origin;

    /** Unique identifier of this class. */

    /**
     * Creates a new {@link Warning} based on the specified warning.
     *
     * @param warning
     *            the warning to copy
     * @param origin
     *            the plug-in that created the warning
     */
    public Warning(final AbstractAnnotation warning, final String origin) {
        super(warning);
        this.origin = origin;
    }

    /** {@inheritDoc} */
    public String getToolTip() {
        return StringUtils.EMPTY;
    }
}

