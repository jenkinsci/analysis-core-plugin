package hudson.plugins.analysis.collector;

import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.graph.CategoryBuildResultGraph;
import hudson.plugins.analysis.util.ToolTipProvider;
import hudson.util.ColorPalette;
import hudson.util.StackedAreaRenderer2;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;

import com.google.common.collect.Lists;

/**
 * Builds a graph showing all warnings by their origin.
 *
 * @author Ulli Hafner
 */
public class OriginGraph extends CategoryBuildResultGraph {
    /** The origins to show in the graph. */
    private final ArrayList<String> origins;

    /**
     * Creates a new instance of {@link OriginGraph}.
     *            the configuration
     */
    public OriginGraph() {
        origins = Lists.newArrayList(
                hudson.plugins.checkstyle.parser.Warning.ORIGIN,
                hudson.plugins.dry.parser.DuplicateCode.ORIGIN,
                hudson.plugins.findbugs.parser.Bug.ORIGIN,
                hudson.plugins.pmd.parser.Bug.ORIGIN,
                hudson.plugins.tasks.parser.Task.ORIGIN,
                hudson.plugins.warnings.parser.Warning.ORIGIN);
    }

    /** {@inheritDoc} */
    @Override
    public String getId() {
        return "ORIGIN";
    }

    /** {@inheritDoc} */
    @Override
    public String getLabel() {
        return Messages.Trend_type_analysis();
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
        // TODO: filter number of plugins
        return new Color[] {
                ColorPalette.LINE_GRAPH.get(0),
                ColorPalette.LINE_GRAPH.get(1),
                ColorPalette.LINE_GRAPH.get(2),
                ColorPalette.LINE_GRAPH.get(3),
                ColorPalette.GREY,
                ColorPalette.BLUE};
    }

    // CHECKSTYLE:OFF
    /** {@inheritDoc} */
    @Override
    protected CategoryItemRenderer createRenderer(final String pluginName, final ToolTipProvider toolTipProvider) {
        return new StackedAreaRenderer2();
    }
    // CHECKSTYLE:ON
}

