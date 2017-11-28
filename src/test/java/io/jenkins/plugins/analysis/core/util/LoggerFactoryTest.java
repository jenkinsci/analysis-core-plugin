package io.jenkins.plugins.analysis.core.util;

import hudson.plugins.analysis.core.Settings;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import io.jenkins.plugins.analysis.core.util.LoggerFactory;
import io.jenkins.plugins.analysis.core.util.Logger;

import java.io.PrintStream;
import java.util.Arrays;

class LoggerFactoryTest {

    private static final PrintStream PRINT_STREAM = mock(PrintStream.class);
    private static final Settings SETTINGS = mock(Settings.class);

    @Test
    void captureLog() {
        when(SETTINGS.getQuietMode()).thenReturn(false);
        Logger logger = new LoggerFactory(SETTINGS).createLogger(PRINT_STREAM, "[mock]");
        logger.log("%s", "mock");
        logger.logEachLine(Arrays.asList("mock1", "mock2", "mock3"));
        verify(PRINT_STREAM, times(4)).println(anyString());
    }

    @Test
    void logOnNullLogger() {
        PrintStream printStream = mock(PrintStream.class);
        when(SETTINGS.getQuietMode()).thenReturn(true);
        Logger logger = new LoggerFactory(SETTINGS).createLogger(printStream, "mock");
        logger.log("%s", "mock");
        verify(printStream, times(0)).println(anyString());
        when(SETTINGS.getQuietMode()).thenReturn(false);
        logger = new LoggerFactory(SETTINGS).createLogger(null, "mock");
        logger.log("%s", "mock");
        verify(printStream, times(0)).println(anyString());
    }

    @Test
    void defaultLoggerFactoryThrowingNull() {
        assertThatThrownBy(() -> {
            new LoggerFactory().createLogger(PRINT_STREAM, "mock");
        }).isInstanceOf(NullPointerException.class);
    }
}