package hudson.plugins.analysis.collector;

import hudson.plugins.analysis.graph.BuildResultGraph;
import hudson.plugins.analysis.graph.GraphConfiguration;

import java.util.Collection;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

/**
 * A graph configuration that additionally handles the deactivation of the
 * individual trend graphs of the analysis plug-ins.
 *
 * @author Ulli Hafner
 */
public class AnalysisGraphConfiguration extends GraphConfiguration {
    /** Determines whether the trend graphs of the other plug-ins could be deactivated. */
    private boolean canDeactivateGraphs;

    /**
     * Creates a new instance of {@link AnalysisGraphConfiguration}.
     *
     * @param availableGraphs
     *            the available graphs
     */
    public AnalysisGraphConfiguration(final Collection<BuildResultGraph> availableGraphs) {
        super(availableGraphs);
    }

    @Override
    protected boolean initializeLocal(final String[] localConfiguration) {
        if (localConfiguration.length == 1) {
            if ("0".equals(localConfiguration[0])) {
                canDeactivateGraphs = false;
                return true;
            }
            else if ("1".equals(localConfiguration[0])) {
                canDeactivateGraphs = true;
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean initializeLocal(final JSONObject localConfiguration) {
        try {
            canDeactivateGraphs = localConfiguration.getBoolean("canDeacticateOtherTrendGraphs");

            return true;
        }
        catch (JSONException exception) {
            canDeactivateGraphs = false;

            return false;
        }
    }

    /**
     * Returns whether the trend graphs of the other plug-ins could be deactivated.
     *
     * @return <code>true</code> if the trend graphs of the other plug-ins could be deactivated
     */
    public boolean canDeacticateOtherTrendGraphs() {
        return canDeactivateGraphs;
    }

    /**
     * See {@link #canDeacticateOtherTrendGraphs()}.
     *
     * @return see {@link #canDeacticateOtherTrendGraphs()}.
     */
    public boolean getCanDeacticateOtherTrendGraphs() {
        return canDeactivateGraphs;
    }

    @Override
    public String serializeToString() {
        return super.serializeToString() + SEPARATOR + serializeBoolean(canDeactivateGraphs);
    }

    // CHECKSTYLE:OFF
    @Override
    public int hashCode() {
        int prime = 31;
        int result = super.hashCode();
        result = prime * result + (canDeactivateGraphs ? 1231 : 1237);
        return result;
    }
    // CHECKSTYLE-ON

    // CHECKSTYLE-OFF
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        AnalysisGraphConfiguration other = (AnalysisGraphConfiguration)obj;
        if (canDeactivateGraphs != other.canDeactivateGraphs) {
            return false;
        }
        return true;
    }
    // CHECKSTYLE-ON
}

