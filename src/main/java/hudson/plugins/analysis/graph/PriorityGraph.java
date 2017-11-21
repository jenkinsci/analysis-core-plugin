package hudson.plugins.analysis.graph;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import hudson.plugins.analysis.Messages;
import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.util.AreaRenderer;
import hudson.plugins.analysis.util.CategoryUrlBuilder;
import hudson.plugins.analysis.util.ToolTipAreaRenderer;
import hudson.plugins.analysis.util.ToolTipBuilder;
import hudson.plugins.analysis.util.ToolTipProvider;
import hudson.plugins.analysis.util.model.Priority;

/**
 * Builds a graph showing all warnings by priority.
 *
 * @author Ulli Hafner
 */
public class PriorityGraph extends CategoryBuildResultGraph {
    @Override
    public String getId() {
        return "PRIORITY";
    }

    @Override
    public String getLabel() {
        return Messages.Trend_type_priority();
    }

    @Override
    protected List<Integer> computeSeries(final BuildResult current) {
        List<Integer> series = new ArrayList<>();
        series.add(current.getNumberOfAnnotations(Priority.LOW));
        series.add(current.getNumberOfAnnotations(Priority.NORMAL));
        series.add(current.getNumberOfAnnotations(Priority.HIGH));
        return series;
    }

    @Override
    protected JFreeChart createChart(final CategoryDataset dataSet) {
        return createAreaChart(dataSet);
    }

    @Override
    protected Color[] getColors() {
        return new Color[] {ColorPalette.BLUE, ColorPalette.YELLOW, ColorPalette.RED};
    }

    // CHECKSTYLE:OFF
    @SuppressWarnings("serial")
    @SuppressFBWarnings("SIC")
    @Override
    protected CategoryItemRenderer createRenderer(final GraphConfiguration configuration, final String pluginName, final ToolTipProvider toolTipProvider) {
        CategoryUrlBuilder url = new UrlBuilder(getRootUrl(), pluginName);
        ToolTipBuilder toolTip = new ToolTipBuilder(toolTipProvider) {
                    @Override
            protected String getShortDescription(final int row) {
                if (row == 0) {
                    return Messages.Trend_PriorityLow();
                }
                else if (row == 1) {
                    return Messages.Trend_PriorityNormal();
                }
                else {
                    return Messages.Trend_PriorityHigh();
                }
            }
        };
        if (configuration.useBuildDateAsDomain()) {
            return new ToolTipAreaRenderer(toolTip);
        }
        else {
            return new AreaRenderer(url, toolTip);
        }
    }
    // CHECKSTYLE:ON

    /**
     * Provides URLs for the graph.
     */
    private static final class UrlBuilder extends CategoryUrlBuilder {
        private static final long serialVersionUID = 3049511502830320036L;

        protected UrlBuilder(final String rootUrl, final String pluginName) {
            super(rootUrl, pluginName);
        }

        @Override
        protected String getDetailUrl(final int row) {
            if (row == 0) {
                return Priority.LOW.name();
            }
            else if (row == 1) {
                return Priority.NORMAL.name();
            }
            else {
                return Priority.HIGH.name();
            }
        }
    }
}

