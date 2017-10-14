package io.jenkins.plugins.analysis.core.graphs;


import javax.annotation.CheckForNull;
import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;

import io.jenkins.plugins.analysis.core.history.RunResultHistory;
import io.jenkins.plugins.analysis.core.steps.AnalysisResult;

import hudson.plugins.analysis.Messages;
import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.util.DataSetBuilder;

/**
 * A build result graph that can create a graph based on authors of annotations.
 *
 * @author Lukas Krose
 */
// TODO: Refactor so that duplicate methods with CategoryBuildResultGraphs are part of BuildResultGraph
// TODO: For each graph, different properties could be set, e.g. show "-" author
public class AnnotationsByUserGraph extends BuildResultGraph {
    @Override
    public JFreeChart create(final GraphConfiguration configuration,
            final RunResultHistory history, @CheckForNull final String pluginName) {
        Map<String, Integer[]> annotationCountByUser = new HashMap<>();

        AnalysisResult result = history.getBaseline();
        mergeResults(result, annotationCountByUser);

        return createGraphFromUserMapping(configuration, pluginName, annotationCountByUser);
    }

    private JFreeChart createGraphFromUserMapping(final GraphConfiguration configuration,
            final @CheckForNull String pluginName, final Map<String, Integer[]> annotationCountByUser) {
        JFreeChart chart = createBlockChart(buildDataSet(annotationCountByUser));

        attachRenderer(configuration, pluginName, chart);

        return chart;
    }

    @Override
    public JFreeChart createAggregation(final GraphConfiguration configuration, final Collection<RunResultHistory> resultActions, final String pluginName) {
        Map<String, Integer[]> annotationCountByUser = new HashMap<>();

        for (RunResultHistory history : resultActions) {
            AnalysisResult result = history.getBaseline();
            mergeResults(result, annotationCountByUser);
        }

        return createGraphFromUserMapping(configuration, pluginName, annotationCountByUser);
    }

    /**
     * Attach the renderers to the created graph.
     *
     * @param configuration
     *         the configuration parameters
     * @param pluginName
     *         the name of the plug-in
     * @param chart
     *         the graph to attach the renderer to
     */
    private void attachRenderer(final GraphConfiguration configuration, final String pluginName, final JFreeChart chart) {
        CategoryItemRenderer renderer = new StackedBarRenderer();
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setRenderer(renderer);
        setColors(chart, new Color[]{ColorPalette.BLUE, ColorPalette.YELLOW, ColorPalette.RED});
    }

    private CategoryDataset buildDataSet(final Map<String, Integer[]> annotationCountByUser) {
        DataSetBuilder<String, String> builder = new DataSetBuilder<>();
        for (Entry<String, Integer[]> entry : annotationCountByUser.entrySet()) {
            String userName = entry.getKey();
            Integer[] countsPerPriority = entry.getValue();
            for (int i = 0; i < countsPerPriority.length; i++) {
                builder.add(countsPerPriority[i], Integer.toString(i), userName);
            }
        }

        return builder.build();
    }

    private void mergeResults(final AnalysisResult current, final Map<String, Integer[]> annotationCountByUser) {
        Collection<FileAnnotation> annotations = current.getAnnotations();
        for (FileAnnotation annotation : annotations) {
            String author = annotation.getAuthor();
            if (StringUtils.isNotBlank(author) && !"-".equals(author)) {
                annotationCountByUser.computeIfAbsent(author, k -> new Integer[]{0, 0, 0});
                Integer[] priorities = annotationCountByUser.get(author);
                int index = annotation.getPriority().ordinal();
                priorities[index]++;
            }
        }
    }

    @Override
    public String getId() {
        return "USERS";
    }

    @Override
    public String getLabel() {
        return Messages.Trend_type_authors();
    }

}
