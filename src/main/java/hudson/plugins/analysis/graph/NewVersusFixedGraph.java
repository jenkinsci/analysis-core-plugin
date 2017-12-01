package hudson.plugins.analysis.graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;

import hudson.plugins.analysis.Messages;
import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.util.BoxRenderer;
import hudson.plugins.analysis.util.CategoryUrlBuilder;
import hudson.plugins.analysis.util.ToolTipBoxRenderer;
import hudson.plugins.analysis.util.ToolTipBuilder;
import hudson.plugins.analysis.util.ToolTipProvider;

/**
 * Builds a new versus fixed warnings graph for a specified result action.
 *
 * @author Ulli Hafner
 */
public class NewVersusFixedGraph extends CategoryBuildResultGraph {
    @Override
    public String getId() {
        return "FIXED";
    }

    @Override
    public String getLabel() {
        return Messages.Trend_type_fixed();
    }

    @Override
    protected List<Integer> computeSeries(final BuildResult current) {
        List<Integer> series = new ArrayList<>();
        series.add(current.getNumberOfNewWarnings());
        series.add(current.getNumberOfFixedWarnings());
        return series;
    }

    @Override
    protected JFreeChart createChart(final CategoryDataset dataSet) {
        return createBlockChart(dataSet);
    }

    @Override
    protected Color[] getColors() {
        return new Color[] {ColorPalette.RED, ColorPalette.BLUE};
    }

    @Override
    protected CategoryItemRenderer createRenderer(final GraphConfiguration configuration, final String pluginName, final ToolTipProvider toolTipProvider) {
        CategoryUrlBuilder url = new UrlBuilder(getRootUrl(), pluginName);
        ToolTipBuilder toolTip = new DescriptionBuilder(toolTipProvider);
        if (configuration.useBuildDateAsDomain()) {
            return new ToolTipBoxRenderer(toolTip);
        }
        else {
            return new BoxRenderer(url, toolTip);
        }
    }

    /**
     * Shows URLs for the graph.
     */
    private static final class UrlBuilder extends CategoryUrlBuilder {
        private static final long serialVersionUID = -513005297527489127L;

        UrlBuilder(final String rootUrl, final String pluginName) {
            super(rootUrl, pluginName);
        }

        @Override
        protected String getDetailUrl(final int row) {
            if (row == 1) {
                return "fixed";
            }
            else {
                return "new";
            }
        }
    }

    /**
     * Shows tooltips for the graph.
     */
    private static final class DescriptionBuilder extends ToolTipBuilder {
        private static final long serialVersionUID = 2680597046798379462L;

        DescriptionBuilder(final ToolTipProvider provider) {
            super(provider);
        }

        @Override
        protected String getShortDescription(final int row) {
            if (row == 1) {
                return Messages.Trend_Fixed();
            }
            else {
                return Messages.Trend_New();
            }
        }
    }
}

