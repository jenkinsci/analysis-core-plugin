package io.jenkins.plugins.analysis.core.util;

import java.io.PrintStream;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import hudson.plugins.analysis.core.Settings;

class LoggerFactoryTest {

    @Test
    void shouldCreatePrintStreamLogger(){
        Settings settingsMock = mock(Settings.class);
        PrintStream printStreamMock = mock(PrintStream.class);
        LoggerFactory loggerFactory = new LoggerFactory(settingsMock);
        Logger printStreamLogger = loggerFactory.createLogger(printStreamMock, "test Tool");

        printStreamLogger.log("Test String: %s", "logTest");
        printStreamLogger.logEachLine(Lists.newArrayList("logTestCollection", "logTestCollection", "logTestCollection"));

        verify(printStreamMock, times(1)).println("[test Tool] Test String: logTest");
        verify(printStreamMock, times(3)).println("[test Tool] logTestCollection");
    }

    @Test
    void shouldCreateNullLogger(){
        Settings settingsMock = mock(Settings.class);
        when(settingsMock.getQuietMode()).thenReturn(true);
        PrintStream printStreamMock = mock(PrintStream.class);
        LoggerFactory loggerFactory = new LoggerFactory(settingsMock);
        Logger nullLogger = loggerFactory.createLogger(printStreamMock, "test Tool");

        nullLogger.log("Test String: %s", "logTest");
        nullLogger.logEachLine(Lists.newArrayList("logTestCollection", "logTestCollection", "logTestCollection"));

        verify(printStreamMock, never()).println(anyString());
    }
}