package hudson.plugins.analysis.core;

import java.io.IOException;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import jenkins.model.Jenkins;

import hudson.Extension;
import hudson.model.AdministrativeMonitor;

/**
 * Notifies administrators that the old analysis plugins are end-of-life and that the
 * new warnings plugin must be installed.
 *
 * @author Ullrich Hafner
 */
@Extension
public class UpgradeNotifier extends AdministrativeMonitor {
    @Override
    public boolean isActivated() {
        Jenkins instance = Jenkins.getInstance();

        return instance == null || instance.getPlugin("warnings-ng") == null;
    }

    /**
     * Depending on whether the user said "yes" or "no", send him to the right place.
     */
    public void doAct(StaplerRequest request, StaplerResponse response) throws IOException {
        if (request.hasParameter("dismiss")) {
            disable(true);
            response.sendRedirect(request.getContextPath() + "/manage");
        }
        else if (request.hasParameter("help")) {
            response.sendRedirect("https://plugins.jenkins.io/warnings");
        }
    }
}
