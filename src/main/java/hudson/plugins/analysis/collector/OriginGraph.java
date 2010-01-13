package hudson.plugins.analysis.collector;

import hudson.plugins.analysis.Messages;
import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.graph.CategoryBuildResultGraph;
import hudson.plugins.analysis.util.AreaRenderer;
import hudson.plugins.analysis.util.CategoryUrlBuilder;
import hudson.plugins.analysis.util.ToolTipBuilder;
import hudson.plugins.analysis.util.ToolTipProvider;
import hudson.plugins.analysis.util.model.Priority;
import hudson.util.ColorPalette;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;

import com.google.common.collect.Lists;

import edu.umd.cs.findbugs.annotations.SuppressWarnings;

/**
 * TODO: Document type OriginGraph.
 *
 * @author Ulli Hafner
 */
public class OriginGraph extends CategoryBuildResultGraph {
    private final ArrayList<String> origins;

    /**
     * Creates a new instance of {@link OriginGraph}.
     */
    public OriginGraph() {
        origins = Lists.newArrayList("checkstyle", "findbugs");
    }

    /** {@inheritDoc} */
    @Override
    protected List<Integer> computeSeries(final BuildResult current) {
        List<Integer> series = new ArrayList<Integer>();
        if (current instanceof AnalysisResult) {
            AnalysisResult result = (AnalysisResult)current;
            for (String origin : origins) {
                series.add(result.getNumberOfAnnotationsByOrigin(origin));
            }
        }
        return series;
    }

    /** {@inheritDoc} */
    @Override
    protected JFreeChart createChart(final CategoryDataset dataSet) {
        JFreeChart chart = ChartFactory.createLineChart(
                null,                      // chart title
                null,                      // unused
                "count",                   // range axis label
                dataSet,                   // data
                PlotOrientation.VERTICAL,  // orientation
                false,                     // include legend
                true,                      // tooltips
                false                      // urls
            );
        chart.setBackgroundPaint(Color.white);
        setCategoryPlotProperties(chart.getCategoryPlot());
        chart.getCategoryPlot().getDomainAxis().setCategoryMargin(0.0);

        return chart;
    }

    /** {@inheritDoc} */
    @Override
    protected Color[] getColors() {
        return new Color[] {ColorPalette.BLUE, ColorPalette.YELLOW, ColorPalette.RED};
    }

    // CHECKSTYLE:OFF
    /** {@inheritDoc} */
    @java.lang.SuppressWarnings("serial")
    @SuppressWarnings("SIC")
    @Override
    protected CategoryItemRenderer createRenderer(final String pluginName, final ToolTipProvider toolTipProvider) {
        CategoryUrlBuilder url = new CategoryUrlBuilder(getRootUrl(), pluginName) {
            /** {@inheritDoc} */
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
        };
        ToolTipBuilder toolTip = new ToolTipBuilder(toolTipProvider) {
            /** {@inheritDoc} */
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
        return new AreaRenderer(url, toolTip);
    }
    // CHECKSTYLE:ON
}

