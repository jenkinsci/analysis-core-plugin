package hudson.plugins.analysis.core;

import hudson.FilePath;
import hudson.Launcher;
import hudson.matrix.MatrixAggregator;
import hudson.matrix.MatrixBuild;
import hudson.model.BuildListener;
import hudson.model.Job;
import hudson.model.Run;
import hudson.plugins.analysis.util.PluginLogger;
import jenkins.model.Jenkins;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HealthAwareRecorderTest {

    final private StubbedHealthAwareRecorder recorder = new StubbedHealthAwareRecorder("");
    final private Job currentJob = mock(Job.class);
    final private Run currentBuild = mock(Run.class);
    final private Jenkins jenkins = mock(Jenkins.class);

    @Before
    public void setUp() {
        when(currentBuild.getParent()).thenReturn(currentJob);
    }

    @Test
    public void shouldUseLastBuildOfResolvedJobWhenOnlyJobNameIsProvided() throws Exception {
        final String jobName = "SomeFolder/SomeJob";
        final Job selectedJob = mock(Job.class);
        final Run lastBuild = mock(Run.class);
        when(selectedJob.getLastBuild()).thenReturn(lastBuild);
        when(jenkins.getItemByFullName(jobName)).thenReturn(selectedJob);

        recorder.setReferenceJobName(jobName);
        final Run referenceBuild = recorder.resolveReferenceBuild(currentBuild, jenkins);

        assertEquals(lastBuild, referenceBuild);
    }

    @Test
    public void shouldResolveBuildOfCurrentJobIfOnlyBuildNumberIsProvided() throws Exception {
        final String buildNumber = "999";
        final Run selectedBuild = mock(Run.class);
        when(currentJob.getBuildByNumber(Integer.valueOf(buildNumber))).thenReturn(selectedBuild);

        recorder.setReferenceBuildNumber(buildNumber);
        final Run referenceBuild = recorder.resolveReferenceBuild(currentBuild, jenkins);

        assertEquals(selectedBuild, referenceBuild);
    }

    @Test
    public void shouldResolveSpecificBuildOfSpecificJobWhenBothOptionsProvided() throws Exception {
        final String jobName = "SomeFolder/SomeJob";
        final String buildNumber = "999";
        final Run specificBuild = mock(Run.class);
        final Job specificJob = mock(Job.class);
        when(specificJob.getBuildByNumber(Integer.valueOf(buildNumber))).thenReturn(specificBuild);
        when(jenkins.getItemByFullName(jobName)).thenReturn(specificJob);

        recorder.setReferenceBuildNumber(buildNumber);
        recorder.setReferenceJobName(jobName);
        final Run referenceBuild = recorder.resolveReferenceBuild(currentBuild, jenkins);

        assertEquals(specificBuild, referenceBuild);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenProvidedJobNameCantBeResolved() {
        recorder.setReferenceJobName("NonExistingJobName");
        recorder.resolveReferenceBuild(currentBuild, jenkins);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowWhenProvidedBuildNumberCantBeResolved() {
        recorder.setReferenceBuildNumber("InvalidNumber");
        recorder.resolveReferenceBuild(currentBuild, jenkins);
    }

    static class StubbedHealthAwareRecorder extends HealthAwareRecorder {
        StubbedHealthAwareRecorder(String pluginName) {
            super(pluginName);
        }

        public Run resolveReferenceBuild(Run currentBuild, Jenkins jenkins) {
            return getReferenceBuild(currentBuild, jenkins);
        }

        @Override
        protected boolean perform(Run<?, ?> run, FilePath workspace, Launcher launcher, PluginLogger logger) throws InterruptedException, IOException {
            return false;
        }

        @Override
        public MatrixAggregator createAggregator(MatrixBuild matrixBuild, Launcher launcher, BuildListener buildListener) {
            return null;
        }
    }
}
