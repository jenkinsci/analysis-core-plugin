package hudson.plugins.analysis.collector;

import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import org.jvnet.localizer.Localizable;

import com.google.common.collect.Lists;

import jenkins.model.Jenkins;

import hudson.Extension;
import hudson.model.Job;
import hudson.plugins.analysis.core.AbstractProjectAction;
import hudson.plugins.analysis.core.BuildResult;
import hudson.plugins.analysis.core.PluginDescriptor;
import hudson.plugins.analysis.core.ResultAction;

/**
 * Base class of a static analysis plug-in.
 *
 * @author Ullrich Hafner
 */
@Extension
public abstract class AnalysisPlugin {
    /** Message to be shown if no result action is found. */
    private static final String NO_RESULTS_FOUND = "-";

    private Localizable detailHeader;
    private Class<? extends PluginDescriptor> descriptor;

    /**
     * Returns all static analysis plugins that are registered by the extension point {@link AnalysisPlugin}.
     *
     * @return the extension list
     */
    public static List<AnalysisPlugin> all() {
        Jenkins jenkins = Jenkins.getInstance();
        if (jenkins == null) { // during unit tests
            return Lists.newArrayList();
        }

        return Lists.newArrayList(jenkins.getExtensionList(AnalysisPlugin.class));
    }

    /**
     * Returns the analysis plug-in with the specified name.
     *
     * @param name the name of the plug-in
     * @return the plugin with the given name
     * @throws NoSuchElementException if there is no such plug-in
     */
    public static AnalysisPlugin getPlugin(final String name) {
        for (AnalysisPlugin plugin : all()) {
            if (plugin.getId().equals(name)) {
                return plugin;
            }
        }
        throw new NoSuchElementException("There is no plugin with name " + name);
    }

    public abstract String getName();

    public abstract Collection<? extends Class<? extends ResultAction<? extends BuildResult>>> getActions();

    public abstract String getId();

    public abstract Localizable getDetailHeader();

    public abstract Class<? extends PluginDescriptor> getDescriptor();

    public abstract Class<? extends AbstractProjectAction<?>> getProjectAction();

    public boolean hasResultsFor(final Job<?, ?> job) {
        AbstractProjectAction<?> action = job.getAction(getProjectAction());

        return action != null && action.hasValidResults();
    }

    public String getIconUrl() {
        PluginDescriptor pluginDescriptor = Jenkins.getInstance().getDescriptorByType(descriptor);

        return pluginDescriptor.getIconUrl();
    }
}
