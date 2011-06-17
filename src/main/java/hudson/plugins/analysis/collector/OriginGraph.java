package hudson.plugins.analysis.collector;

import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.graph.CategoryBuildResultGraph;
import hudson.plugins.analysis.graph.GraphConfiguration;
import hudson.plugins.analysis.util.ToolTipProvider;
import hudson.util.ColorPalette;

import java.awt.Color;
import java.awt.Font;
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
    /** Number of colors to use from Hudson's color table. */
    private static final int HUDSON_GREEN_INDEX = 3;

    private static final Font LEGEND_FONT = new Font("SansSerif", Font.PLAIN, 10); // NOCHECKSTYLE

    private final List<String> originsKeys  = Lists.newArrayList();
    private final List<String> originLabels = Lists.newArrayList();
    private static final Color ORANGE = new Color(0xFF, 0xA5, 0x00);
    private static final Color GRAY = new Color(0x4D, 0x4D, 0x4D);
    private static final Color PINK = new Color(0xA0, 0x20, 0xF0);

    /**
     * Creates a new instance of {@link OriginGraph}.
     */
    public OriginGraph() {
        this(AnalysisDescriptor.isCheckStyleInstalled(), AnalysisDescriptor.isDryInstalled(),
                AnalysisDescriptor.isFindBugsInstalled(), AnalysisDescriptor.isPmdInstalled(),
                AnalysisDescriptor.isOpenTasksInstalled(), AnalysisDescriptor.isWarningsInstalled());
    }

    /**
     * Creates a new instance of {@link OriginGraph}.
     *
     * @param isCheckStyleActivated
     *            determines whether to show the warnings from Checkstyle
     * @param isDryActivated
     *            determines whether to show the warnings from DRY
     * @param isFindBugsActivated
     *            determines whether to show the warnings from FindBugs
     * @param isPmdActivated
     *            determines whether to show the warnings from PMD
     * @param isOpenTasksActivated
     *            determines whether to show open tasks
     * @param isWarningsActivated
     *            determines whether to show compiler warnings
     */
    public OriginGraph(final boolean isCheckStyleActivated, final boolean isDryActivated,
            final boolean isFindBugsActivated, final boolean isPmdActivated,
            final boolean isOpenTasksActivated, final boolean isWarningsActivated) {
        if (isCheckStyleActivated) {
            originsKeys.add(hudson.plugins.checkstyle.parser.Warning.ORIGIN);
            originLabels.add(Messages.Analysis_Checkstyle_Warning_Origin());
        }
        if (isDryActivated) {
            originsKeys.add(hudson.plugins.dry.parser.DuplicateCode.ORIGIN);
            originLabels.add(Messages.Analysis_Dry_Warning_Origin());
        }
        if (isFindBugsActivated) {
            originsKeys.add(hudson.plugins.findbugs.parser.Bug.ORIGIN);
            originLabels.add(Messages.Analysis_FindBugs_Warning_Origin());
        }
        if (isPmdActivated) {
            originsKeys.add(hudson.plugins.pmd.parser.Bug.ORIGIN);
            originLabels.add(Messages.Analysis_PMD_Warning_Origin());
        }
        if (isOpenTasksActivated) {
            originsKeys.add(hudson.plugins.tasks.parser.Task.ORIGIN);
            originLabels.add(Messages.Analysis_Tasks_Warning_Origin());
        }
        if (isWarningsActivated) {
            originsKeys.add(hudson.plugins.warnings.parser.Warning.ORIGIN);
            originLabels.add(Messages.Analysis_Warnings_Warning_Origin());
        }
    }

    /** {@inheritDoc} */
    @Override
    public String getId() {
        return "ORIGIN";
    }

    /**
     * Returns the plug-in that owns this graph and provides an example image.
     *
     * @return the plug-in that owns this graph and provides an example image
     */
    @Override
    protected String getPlugin() {
        return "analysis-collector";
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
            for (String origin : originsKeys) {
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
        chart.getLegend().setItemFont(LEGEND_FONT);

        return chart;
    }

    /** {@inheritDoc} */
    @Override
    protected Color[] getColors() {
        List<Color> colors = Lists.newArrayList(ColorPalette.LINE_GRAPH);
        while (colors.size() > HUDSON_GREEN_INDEX) {
            colors.remove(HUDSON_GREEN_INDEX);
        }
        colors.add(ORANGE);
        colors.add(GRAY);
        colors.add(PINK);
        colors.add(ColorPalette.RED);
        colors.add(ColorPalette.YELLOW);
        return colors.toArray(new Color[colors.size()]);
    }

    @Override
    protected String getRowId(final int level) {
        return originLabels.get(level);
    }

    @Override
    protected CategoryItemRenderer createRenderer(final GraphConfiguration configuration, final String pluginName, final ToolTipProvider toolTipProvider) {
        return createLineRenderer();
    }
}

