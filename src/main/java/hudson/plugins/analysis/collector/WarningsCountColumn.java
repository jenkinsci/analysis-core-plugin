package hudson.plugins.analysis.collector;

import java.util.List;

import org.kohsuke.stapler.DataBoundConstructor;

import jenkins.model.Jenkins;

import hudson.Extension;
import hudson.model.Job;
import hudson.plugins.analysis.util.HtmlPrinter;
import hudson.views.ListViewColumn;
import hudson.views.ListViewColumnDescriptor;

/**
 * A column that shows the total number of warnings in a job.
 *
 * @author Ulli Hafner
 */
// FIXME: readresolve
public class WarningsCountColumn extends ListViewColumn {
    private final WarningsAggregator warningsAggregator;
    private final String[] plugins;

    /**
     * Creates a new instance of {@link WarningsCountColumn}.
     * @param plugins the active plugins
     */
    @DataBoundConstructor
    public WarningsCountColumn(final String... plugins) {
        super();
        this.plugins = plugins;

        warningsAggregator = new WarningsAggregator(plugins);
    }

    /**
     * Returns whether the specified plug-in is activated.
     *
     * @return <code>true</code> if the results of the specified plug-in should be collected, <code>false</code>
     * otherwise
     */
    public boolean isActivated(final String name) {
        return true;
    }

    /**
     * Returns the total number of annotations for the selected job.
     *
     * @param project the selected project
     * @return the total number of annotations
     */
    public String getNumberOfAnnotations(final Job<?, ?> project) {
        return warningsAggregator.getTotal(project);
    }

    /**
     * Returns the number of warnings for the specified job separated by each plug-in.
     *
     * @param job the job to get the warnings for
     * @return the number of warnings, formatted as HTML string
     */
    public String getDetails(final Job<?, ?> job) {
        HtmlPrinter printer = new HtmlPrinter();
        printer.append("<table>");

        for (String name : plugins) {
            AnalysisPlugin plugin = AnalysisPlugin.getPlugin(name);
            printLine(printer,
                    warningsAggregator.getWarnings(job, plugin),
                    plugin);
        }
        printer.append("</table>");
        return printer.toString();
    }

    private void printLine(final HtmlPrinter printer, final String warnings,
                           final AnalysisPlugin plugin) {
        String image = "<img hspace=\"10\" align=\"absmiddle\" width=\"24\" height=\"24\" src=\""
                + Jenkins.RESOURCE_PATH + plugin.getIconUrl() + "\"/>";
        printer.append(printer.line(image + plugin.getDetailHeader() + ": " + warnings));
    }

    public List<AnalysisPlugin> getActivePlugins() {
        return AnalysisPlugin.all();
    }

    /**
     * Descriptor for the column.
     */
    @Extension
    public static class ColumnDescriptor extends ListViewColumnDescriptor {
        /** {@inheritDoc} */
        @Override
        public boolean shownByDefault() {
            return false;
        }

        @Override
        public String getDisplayName() {
            return Messages.Analysis_Warnings_Column();
        }
    }
}
