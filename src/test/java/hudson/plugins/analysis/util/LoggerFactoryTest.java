package hudson.plugins.analysis.util;

import io.jenkins.plugins.analysis.core.util.Logger;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import io.jenkins.plugins.analysis.core.util.LoggerFactory;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

import hudson.plugins.analysis.core.Settings;

/**
 * TestCases for {@link PluginLogger}.
 *
 * @author Sebastian Seidl
 */
public class LoggerFactoryTest {


    /**
     * Tests if a "true" PluginLogger is created, when the Quiet Mode is deactivated.
     */
    /*@Test
    public void quietModeDeactivated() {
        // Given
        Settings settings = mock(Settings.class);
        LoggerFactory loggerFactory = new LoggerFactory(settings);

        // When
        when(settings.getQuietMode()).thenReturn(false);
        PluginLogger logger = loggerFactory.createLogger(mock(PrintStream.class), "");

        // Then
        assertFalse("LogMode is not Quiet but LoggerFactory creates a NullLogger!", logger instanceof NullLogger);
    }*/

    /**
     * Tests if a NullLogger is created, when the Quiet Mode is active.
     */
    /*
    @Test
    public void quietModeActivated() {
        // Given
        Settings settings = mock(Settings.class);
        LoggerFactory lf = new LoggerFactory(settings);

        // When
        when(settings.getQuietMode()).thenReturn(true);
        PluginLogger logger = lf.createLogger(mock(PrintStream.class), "");

        // Then
        assertTrue("LogMode is Quiet but LoggerFactory creates not a NullLogger!", logger instanceof NullLogger);
    }
    */
    @Test
    public void shouldCreatePrintStreamLogger() {
        Settings settings = mock(Settings.class);
        when(settings.getQuietMode()).thenReturn(false);

        LoggerFactory loggerFactory = new LoggerFactory(settings);

        PrintStream mock = mock(PrintStream.class);

        Logger logger = loggerFactory.createLogger(mock, "tool name");
        logger.log("I'm a string");

        verify(mock).println("[tool name] I'm a string");
    }

    @Test
    public void shouldCreateNullLogger() {
        Settings settings = mock(Settings.class);
        when(settings.getQuietMode()).thenReturn(true);

        LoggerFactory loggerFactory = new LoggerFactory(settings);

        PrintStream mock = mock(PrintStream.class);

        Logger logger = loggerFactory.createLogger(mock, "tool name");
        logger.log("I'm a string");

        verifyZeroInteractions(mock);
    }

    @Test
    public void shouldLogEachLineInPrintStream() {

        List<String> lines = new ArrayList<>();
        lines.add("Test_1");
        lines.add("Test_2");

        Settings settings = mock(Settings.class);
        when(settings.getQuietMode()).thenReturn(false);

        LoggerFactory loggerFactory = new LoggerFactory(settings);

        PrintStream mock = mock(PrintStream.class);

        Logger logger = loggerFactory.createLogger(mock, "tool name");
        logger.logEachLine(lines);

        verify(mock).println("[tool name] Test_1");
        verify(mock).println("[tool name] Test_2");
    }
}
