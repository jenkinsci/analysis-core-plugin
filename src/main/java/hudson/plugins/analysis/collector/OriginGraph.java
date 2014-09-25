package hudson.plugins.analysis.collector;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jvnet.localizer.Localizable;

import com.google.common.collect.Lists;

import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.graph.CategoryBuildResultGraph;
import hudson.plugins.analysis.graph.ColorPalette;
import hudson.plugins.analysis.graph.GraphConfiguration;
import hudson.plugins.analysis.util.ToolTipProvider;

/**
 * Builds a graph showing all warnings by their origin.
 *
 * @author Ulli Hafner
 */
public class OriginGraph extends CategoryBuildResultGraph {
    /** Number of colors to use from Hudson's color table. */
    private static final int HUDSON_GREEN_INDEX = 3;

    private final List<String> originsKeys  = Lists.newArrayList();
    private final List<Localizable> originLabels = Lists.newArrayList();

    private static final Color ORANGE = new Color(0xFF, 0xA5, 0x00);
    private static final Color GRAY = new Color(0x4D, 0x4D, 0x4D);
    private static final Color PINK = new Color(0xA0, 0x20, 0xF0);

    /**
     * Creates a new instance of {@link OriginGraph}.
     * @param plugins the active plugins
     */
    public OriginGraph(final String... plugins) {
        super();

        // FIXME: check that origin actually is the id
        for (String name : plugins) {
            AnalysisPlugin plugin = AnalysisPlugin.getPlugin(name);
            originsKeys.add(plugin.getId());
            originLabels.add(plugin.getDetailHeader());
        }
    }

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

    @Override
    public String getLabel() {
        return Messages.Trend_type_analysis();
    }

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

    @Override
    protected JFreeChart createChart(final CategoryDataset dataSet) {
        return createLineGraph(dataSet, true);
    }

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
        return originLabels.get(level).toString();
    }

    @Override
    protected CategoryItemRenderer createRenderer(final GraphConfiguration configuration, final String pluginName, final ToolTipProvider toolTipProvider) {
        return createLineRenderer();
    }
}

