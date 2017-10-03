package io.jenkins.plugins.analysis.core;

import java.util.Calendar;
import java.util.GregorianCalendar;

import io.jenkins.plugins.analysis.core.steps.BuildResult;

import hudson.model.Run;

/**
 * Empty build history.
 *
 * @author Ulli Hafner
 */
public class NullBuildHistory extends BuildHistory {
    /**
     * Creates a new instance of {@link NullBuildHistory}.
     */
    public NullBuildHistory() {
        super((Run<?, ?>)null, null);
    }

    @Override
    public Calendar getTimestamp() {
        return new GregorianCalendar();
    }

    @Override
    public boolean hasPreviousResult() {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public BuildResult getPreviousResult() {
        return null;
    }
}
