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

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public String serializeToString() {
        return super.serializeToString() + SEPARATOR + serializeBoolean(canDeactivateGraphs);
    }
}

