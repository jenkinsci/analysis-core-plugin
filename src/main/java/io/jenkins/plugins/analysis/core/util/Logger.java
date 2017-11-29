package io.jenkins.plugins.analysis.core.util;

import java.util.Collection;

/**
 * A logger that logs messages during the static analysis runs.
 *
 * @author Ullrich Hafner
 */
public interface Logger {
    /**
     * Logs the specified message.
     *
     * @param format
     *         A <a href="../util/Formatter.html#syntax">format string</a>
     * @param args
     *         Arguments referenced by the format specifiers in the format string.  If there are more arguments than
     *         format specifiers, the extra arguments are ignored.  The number of arguments is variable and may be
     *         zero.
     */
    void log(String format, Object... args);

    /**
     * Logs the specified messages.
     *
     * @param lines
     *         the messages to log
     */
    void logEachLine(Collection<String> lines);
}
