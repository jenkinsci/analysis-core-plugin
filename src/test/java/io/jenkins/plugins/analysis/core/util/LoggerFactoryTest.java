package io.jenkins.plugins.analysis.core.util;

import java.io.PrintStream;
import java.util.Collection;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.*;
import static org.mockito.Mockito.*;

import hudson.plugins.analysis.core.Settings;

/**
 * Unit test for {@link LoggerFactory}.
 */
class LoggerFactoryTest {
    private static final String TOOL_NAME = "tool-name";
    private static final String TOOL_NAME_STARTING_WITH_BRACKET = "[" + TOOL_NAME;
    private static final String TOOL_NAME_IN_BRACKETS = "[" + TOOL_NAME + "]";
    private static final String LOG_MESSAGE = "log-message";
    private static final String ANOTHER_LOG_MESSAGE = "another-log-message";
    private static final Collection<String> LOG_MESSAGES = asList(LOG_MESSAGE, ANOTHER_LOG_MESSAGE);
    private static final String SPACE = " ";

    @Test
    void logWithToolName() {
        PrintStream printStream = mock(PrintStream.class);
        LoggerFactory loggerFactory = createLoggerFactory();

        Logger logger = loggerFactory.createLogger(printStream, TOOL_NAME);
        logger.log(LOG_MESSAGE);

        verify(printStream).println(TOOL_NAME_IN_BRACKETS + SPACE + LOG_MESSAGE);
    }

    @Test
    void logEachLinesWithToolName() {
        PrintStream printStream = mock(PrintStream.class);
        LoggerFactory loggerFactory = createLoggerFactory();

        Logger logger = loggerFactory.createLogger(printStream, TOOL_NAME);
        logger.logEachLine(LOG_MESSAGES);

        verify(printStream).println(TOOL_NAME_IN_BRACKETS + SPACE + LOG_MESSAGE);
        verify(printStream).println(TOOL_NAME_IN_BRACKETS + SPACE + ANOTHER_LOG_MESSAGE);
    }

    @Test
    void doNotPutBracketsAroundToolNameStartingWithBracket() {
        PrintStream printStream = mock(PrintStream.class);
        LoggerFactory loggerFactory = createLoggerFactory();

        Logger logger = loggerFactory.createLogger(printStream, TOOL_NAME_STARTING_WITH_BRACKET);
        logger.log(LOG_MESSAGE);

        verify(printStream).println(TOOL_NAME_STARTING_WITH_BRACKET + SPACE + LOG_MESSAGE);
    }

    @Test
    void logQuietly() {
        PrintStream printStream = mock(PrintStream.class);
        LoggerFactory loggerFactory = createQuietLoggerFactory();

        Logger logger = loggerFactory.createLogger(printStream, TOOL_NAME);
        logger.log(LOG_MESSAGE);

        verifyZeroInteractions(printStream);
    }

    @Test
    void logEachLineQuietly() {
        PrintStream printStream = mock(PrintStream.class);
        LoggerFactory loggerFactory = createQuietLoggerFactory();

        Logger logger = loggerFactory.createLogger(printStream, TOOL_NAME);
        logger.logEachLine(LOG_MESSAGES);

        verifyZeroInteractions(printStream);
    }

    private LoggerFactory createLoggerFactory() {
        Settings settings = mock(Settings.class);
        return new LoggerFactory(settings);
    }

    private LoggerFactory createQuietLoggerFactory() {
        Settings settings = mock(Settings.class);
        when(settings.getQuietMode()).thenReturn(true);
        return new LoggerFactory(settings);
    }
}