package io.jenkins.plugins.analysis.core.util;

import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import hudson.plugins.analysis.core.Settings;

class LoggerFactoryTest {
    @Test
    void t1() {
        Settings settings = mock(Settings.class);
        LoggerFactory factory = new LoggerFactory(settings);
        PrintStream printStream = mock(PrintStream.class);
        Logger logger = factory.createLogger(printStream, "Test");
        logger.log("%s", "Test Message");
        verify(printStream).println("[Test] Test Message");
    }
}