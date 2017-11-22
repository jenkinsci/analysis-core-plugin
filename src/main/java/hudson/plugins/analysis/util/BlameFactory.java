package hudson.plugins.analysis.util;

import jenkins.model.Jenkins;

import hudson.FilePath;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.scm.SCM;

/**
 * Selects a matching SCM blamer for the specified job.
 *
 * @author Lukas Krose
 */
public class BlameFactory {
    /**
     * Selects a matching SCM blamer for the specified job.
     *
     * @param run       the run to get the SCM from
     * @param workspace the path to the workspace
     * @param listener  task listener
     * @return the blamer
     */
    public static Blamer createBlamer(Run<?, ?> run, FilePath workspace, final TaskListener listener) {
        if (run instanceof AbstractBuild) { // pipelines do not have an SCM link
            AbstractBuild build = (AbstractBuild) run;
            AbstractProject project = build.getProject();
            SCM scm = project.getScm();
            if (scm == null) {
                scm = project.getRootProject().getScm();
            }
            Jenkins instance = Jenkins.getInstance();
            if (instance.getPlugin("git") != null) {
                GitChecker gitChecker = new GitChecker();
                if (gitChecker.isGit(scm)) {
                    return gitChecker.createBlamer(build, scm, workspace, listener);
                }
                else {
                    logOnlyGitSupported(listener);
                }
            }
            else {
                logOnlyGitSupported(listener);
            }
        }
        else {
            log(listener, "Skipping warnings blame since pipelines do not have an SCM link.\n");
        }
        return new NullBlamer();
    }

    private static void logOnlyGitSupported(final TaskListener listener) {
        log(listener, "Skipping warnings blame since Git is the only supported SCM up to now.\n");
    }

    private static void log(final TaskListener listener, final String message) {
        listener.getLogger().println(message);
    }
}
