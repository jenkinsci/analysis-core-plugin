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
    /**
     * Creates a new instance of {@link Main}.
     */
    public Main() {
        super("Hello Graph");

        OriginGraph graph = new OriginGraph();
        DataSetBuilder<Integer, String> builder = new DataSetBuilder<Integer, String>();
        builder.add(100, 0, "#1");
        builder.add(120, 0, "#2");
        builder.add(50, 0, "#3");

        builder.add(10, 1, "#1");
        builder.add(20, 1, "#2");
        builder.add(50, 1, "#3");

        builder.add(50, 2, "#1");
        builder.add(90, 2, "#2");
        builder.add(12, 2, "#3");

        JFreeChart chart = graph.createChart(builder.build());

        CategoryItemRenderer renderer = graph.createRenderer("Hallo", null);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRenderer(renderer);
        graph.setColors(chart, graph.getColors());

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        setContentPane(chartPanel);
    }

    /**
     * TODO: Document method main
     * @param args
     */
    public static void main(final String[] args) {
        Main chart = new Main();
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }
}

