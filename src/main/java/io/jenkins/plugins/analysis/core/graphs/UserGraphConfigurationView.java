package io.jenkins.plugins.analysis.core.graphs;

import javax.servlet.http.Cookie;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import io.jenkins.plugins.analysis.core.history.ResultHistory;

import hudson.model.Job;
import hudson.plugins.analysis.Messages;
import hudson.plugins.analysis.util.CookieHandler;
import hudson.plugins.analysis.util.ToolTipProvider;

/**
 * Configures the trend graph of this plug-in for the current user and job using a cookie.
 */
public class UserGraphConfigurationView extends GraphConfigurationView {
    /**
     * Creates a new instance of {@link hudson.plugins.analysis.graph.UserGraphConfigurationView}.
     *
     * @param configuration
     *         the graph configuration
     * @param job
     *         the owning job to configure the graphs for
     * @param jobActionUrl
     *         The URL of the job action (used as cookie ID)
     * @param cookies
     *         the cookies containing the graph configuration
     * @param buildHistory
     *         the build history for this job
     */
    public UserGraphConfigurationView(final GraphConfiguration configuration, final Job<?, ?> job,
            final String jobActionUrl, final Cookie[] cookies,
            final ResultHistory buildHistory, final ToolTipProvider toolTipProvider) {
        super(configuration, job, jobActionUrl, buildHistory);

        if (!configuration.initializeFrom(createCookieHandler(jobActionUrl).getValue(cookies))) {
            configuration.initializeFromFile(createDefaultsFile(job, jobActionUrl));
        }
        configuration.setToolTipProvider(toolTipProvider);
    }

    /**
     * Creates a new cookie handler to convert the cookie to a string value.
     *
     * @param cookieName
     *         the suffix of the cookie name that is used to persist the configuration per user
     *
     * @return the new cookie handler
     */
    protected static CookieHandler createCookieHandler(final String cookieName) {
        return new CookieHandler(cookieName);
    }

    @Override
    public String getDisplayName() {
        return Messages.UserGraphConfiguration_Name();
    }

    @Override
    public String getDescription() {
        return Messages.UserGraphConfiguration_Description();
    }

    /**
     * Returns the URL of this object.
     *
     * @return the URL of this object
     */
    public String getUrl() {
        return getOwner().getAbsoluteUrl() + "/" + getKey() + "/configure";
    }

    @Override
    protected void persistValue(final String value, final String pluginName, final StaplerRequest request, final StaplerResponse response) {
        Cookie cookie = createCookieHandler(pluginName).create(request.getAncestors(), value);
        response.addCookie(cookie);
    }
}

