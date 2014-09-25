package hudson.plugins.analysis.collector;

import org.apache.commons.lang.StringUtils;

import hudson.model.Job;
import hudson.plugins.analysis.core.AbstractProjectAction;
import hudson.plugins.analysis.core.BuildResult;

/**
 * Aggregates the warnings of the analysis plug-in from a single job.
 *
 * @author Ulli Hafner
 */
public class WarningsAggregator {
    /** Message to be shown if no result action is found. */
    private static final String NO_RESULTS_FOUND = "-";
    private static final String CLOSE_TAG = ">";
    private static final String OPEN_TAG = "<";
    private final String[] plugins;

    private boolean hideJobPrefix;

    /**
     * Creates a new instance of {@link WarningsAggregator}.
     *
     * @param plugins the active plugins
     */
    public WarningsAggregator(final String... plugins) {
        this.plugins = plugins;
    }

    private int toInt(final String value) {
        try {
            if (value.contains(OPEN_TAG)) {
                return Integer.parseInt(StringUtils.substringBetween(value, CLOSE_TAG, OPEN_TAG));
            }
            else {
                return Integer.parseInt(value);
            }
        }
        catch (NumberFormatException exception) {
            return 0;
        }
    }

    /**
     * Returns the number of warnings for the specified job.
     *
     * @param job the job to get the warnings for
     * @return the number of warnings
     */
    public String getTotal(final Job<?, ?> job) {
        int sum = 0;
        for (String name : plugins) {
            AnalysisPlugin plugin = AnalysisPlugin.getPlugin(name);
            sum += toInt(getWarnings(job, plugin));
        }
        return String.valueOf(sum);
    }

    /**
     * Returns the warnings for the specified plugin.
     *
     * @param job        the job to get the action from
     * @param plugin the plugin of the action
     * @return the number of warnings
     */
    public String getWarnings(final Job<?, ?> job,
                               final AnalysisPlugin plugin) {
        AbstractProjectAction<?> action = job.getAction(plugin.getProjectAction());
        if (action != null && action.hasValidResults()) {
            BuildResult result = action.getLastAction().getResult();
            int numberOfAnnotations = result.getNumberOfAnnotations();
            String value;
            if (numberOfAnnotations > 0) {
                value = String.format("<a href=\"%s%s\">%d</a>", getJobPrefix(job), plugin, numberOfAnnotations);
            }
            else {
                value = String.valueOf(numberOfAnnotations);
            }
            if (result.isSuccessfulTouched() && !result.isSuccessful()) {
                return value + result.getResultIcon();
            }
            return value;
        }
        return NO_RESULTS_FOUND;
    }

    private String getJobPrefix(final Job<?, ?> job) {
        return hideJobPrefix ? StringUtils.EMPTY : job.getShortUrl();
    }

    public boolean hasAction(final Job<?, ?> job, final Class<? extends AbstractProjectAction<?>> actionType) {
        AbstractProjectAction<?> action = job.getAction(actionType);

        return action != null && action.hasValidResults();
    }

    /**
     * Removes the job/job-name prefix in the URL.
     */
    public void hideJobPrefix() {
        hideJobPrefix = true;
    }
}
