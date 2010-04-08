package hudson.plugins.analysis.collector;

import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.graph.CategoryBuildResultGraph;
import hudson.plugins.analysis.graph.GraphConfiguration;
import hudson.plugins.analysis.util.ToolTipProvider;
import hudson.util.ColorPalette;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;

import com.google.common.collect.Lists;

/**
 * Builds a graph showing all warnings by their origin.
 *
 * @author Ulli Hafner
 */
public class OriginGraph extends CategoryBuildResultGraph {
    /** The origins to show in the graph. */
    private final List<String> origins  = Lists.newArrayList();
    /** The origins to show in the graph. */
    private final List<String> originLabels = Lists.newArrayList();
    /** Orange. */
    public static final Color ORANGE = new Color(0xFF, 0xA5, 0x00);
    /** Orange. */
    public static final Color GRAY = new Color(0x4D, 0x4D, 0x4D);
    /** Orange. */
    public static final Color PINK = new Color(0xA0, 0x20, 0xF0);

    /**
     * Creates a new instance of {@link OriginGraph}.
     */
    public OriginGraph() {
        super();

        if (AnalysisDescriptor.isCheckStyleInstalled()) {
            origins.add(hudson.plugins.checkstyle.parser.Warning.ORIGIN);
            originLabels.add(Messages.Analysis_Checkstyle_Warning_Origin());
        }
        if (AnalysisDescriptor.isDryInstalled()) {
            origins.add(hudson.plugins.dry.parser.DuplicateCode.ORIGIN);
            originLabels.add(Messages.Analysis_Dry_Warning_Origin());
        }
        if (AnalysisDescriptor.isFindBugsInstalled()) {
            origins.add(hudson.plugins.findbugs.parser.Bug.ORIGIN);
            originLabels.add(
                    Messages.Analysis_FindBugs_Warning_Origin());
        }
        if (AnalysisDescriptor.isPmdInstalled()) {
            origins.add(hudson.plugins.pmd.parser.Bug.ORIGIN);
            originLabels.add(Messages.Analysis_PMD_Warning_Origin());
        }
        if (AnalysisDescriptor.isOpenTasksInstalled()) {
            origins.add(hudson.plugins.tasks.parser.Task.ORIGIN);
            originLabels.add(Messages.Analysis_Tasks_Warning_Origin());
        }
        if (AnalysisDescriptor.isWarningsInstalled()) {
            origins.add(hudson.plugins.warnings.parser.Warning.ORIGIN);
            originLabels.add(Messages.Analysis_Warnings_Warning_Origin());
        }
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
                true,                      // include legend
                true,                      // tooltips
                false                      // urls
            );
        chart.setBackgroundPaint(Color.white);
        setCategoryPlotProperties(chart.getCategoryPlot());
        chart.getCategoryPlot().getDomainAxis().setCategoryMargin(0.0);
        chart.getLegend().setItemFont(new Font("SansSerif", Font.PLAIN, 10));

        return chart;
    }

    /** {@inheritDoc} */
    @Override
    protected Color[] getColors() {
        List<Color> colors = Lists.newArrayList(ColorPalette.LINE_GRAPH);
        if (colors.size() > 3) {
            colors.remove(3);
        }
        colors.add(ORANGE);
        colors.add(GRAY);
        colors.add(PINK);
        colors.add(ColorPalette.RED);
        colors.add(ColorPalette.YELLOW);
        return colors.toArray(new Color[colors.size()]);
    }

    /** {@inheritDoc} */
    @Override
    protected String getRowId(final int level) {
        return originLabels.get(level);
    }

    // CHECKSTYLE:OFF
    /** {@inheritDoc} */
    @Override
    protected CategoryItemRenderer createRenderer(final GraphConfiguration configuration, final String pluginName, final ToolTipProvider toolTipProvider) {
        LineAndShapeRenderer render = new LineAndShapeRenderer(true, false);
        render.setBaseStroke(new BasicStroke(2.0f));
        return render;
    }
    // CHECKSTYLE:ON
}

