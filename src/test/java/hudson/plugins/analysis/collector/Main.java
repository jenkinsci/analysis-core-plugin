package hudson.plugins.analysis.collector;

import hudson.util.DataSetBuilder;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * Plots a graph.
 *
 * @author Ulli Hafner
 */
public class Main extends ApplicationFrame {
    /** Unique ID. */
    private static final long serialVersionUID = -4097403148044461272L;

    /**
     * Creates a new instance of {@link Main}.
     */
    public Main() {
        super("Hello Graph");

        OriginGraph graph = new OriginGraph();
        DataSetBuilder<String, String> builder = new DataSetBuilder<String, String>();
        builder.add(100, "Checkstyle", "#1");
        builder.add(120, "Checkstyle", "#2");
        builder.add(50, "Checkstyle", "#3");

        builder.add(10, "PMD", "#1");
        builder.add(20, "PMD", "#2");
        builder.add(50, "PMD", "#3");

        builder.add(50, "FindBugs", "#1");
        builder.add(90, "FindBugs", "#2");
        builder.add(12, "FindBugs", "#3");

        JFreeChart chart = graph.createChart(builder.build());

        CategoryItemRenderer renderer = graph.createRenderer(null, "Hallo", null);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRenderer(renderer);
        graph.setColors(chart, graph.getColors());

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    /**
     * Shows the graph.
     *
     * @param args
     *            not used
     */
    public static void main(final String[] args) {
        Main chart = new Main();
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}

