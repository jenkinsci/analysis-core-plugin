package hudson.plugins.analysis.core; // NOPMD

import javax.annotation.CheckForNull;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collection;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import hudson.FilePath;
import hudson.Launcher;
import hudson.matrix.MatrixAggregatable;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.model.Project;
import hudson.model.Result;
import hudson.plugins.analysis.util.EncodingValidator;
import hudson.plugins.analysis.util.LoggerFactory;
import hudson.plugins.analysis.util.PluginLogger;
import hudson.plugins.analysis.util.model.AbstractAnnotation;
import hudson.plugins.analysis.util.model.AnnotationContainer;
import hudson.plugins.analysis.util.model.DefaultAnnotationContainer;
import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.analysis.util.model.Priority;
import hudson.plugins.analysis.util.model.WorkspaceFile;
import hudson.remoting.VirtualChannel;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Builder;
import hudson.tasks.Maven;
import hudson.tasks.Recorder;

/**
 * A base class for publishers that create a report of warnings from a static code analysis:
 * <ul>
 * <li>It provides thresholds, that could be enabled and set in the configuration screen. If the number of annotations
 * in a build exceeds this value then the build is considered as {@link Result#UNSTABLE UNSTABLE} or
 * {@link Result#FAILURE FAILURE}.</li>
 * <li>It provides thresholds for the build health, that could be adjusted in the configuration screen. These values are
 * used by the {@link HealthReportBuilder} to compute the health and the health trend graph.</li>
 * <li>It provides support for matrix build by implementing the {@link MatrixAggregatable} interface.</li>
 * </ul>
 *
 * @author Ulli Hafner
 */
// CHECKSTYLE:COUPLING-OFF
public abstract class HealthAwareRecorder extends Recorder implements HealthDescriptor, MatrixAggregatable {
    private static final long serialVersionUID = 8892994325541840827L;

    private static final String DEFAULT_PRIORITY_THRESHOLD_LIMIT = "low";
    private static final String SLASH = "/";

    private final String pluginName;
    /**
     * Determines whether relative paths in warnings should be resolved using a
     * time expensive operation that scans the whole workspace for matching
     * files.
     *
     * @since 1.43
     */
    private final boolean doNotResolveRelativePaths;
    /**
     * Reference to all other configuration values, either from a job local or Jenkins global configuration.
     *
     * @since 1.55
     */
    private ConfigurationReference configReference;

    /** Transient reference to speed up access to values. */
    private transient AnalysisConfiguration configuration;

    /**
     * Creates a new instance of {@link HealthAwareRecorder}.
     *
     * @param configuration
     *            the analysis configuration values
     * @param pluginName
     *            the name of the plug-in
     */
    // CHECKSTYLE:OFF
    public HealthAwareRecorder(final ConfigurationReference configuration, final String pluginName) {
        this(configuration, pluginName, false);
    }

    /**
     * Creates a new instance of {@link HealthAwareRecorder}.
     *
     * @param configuration
     *            the analysis configuration values
     * @param pluginName
     *            the name of the plug-in
     * @param canResolveRelativePaths
     *            determines whether relative paths in warnings should be resolved using a time expensive operation that
     *            scans the whole workspace for matching files.
     */
    // CHECKSTYLE:OFF
    public HealthAwareRecorder(final ConfigurationReference configuration, final String pluginName, final boolean canResolveRelativePaths) {
        this.pluginName = "[" + pluginName + "] ";

        doNotResolveRelativePaths = !canResolveRelativePaths;
        configReference = configuration;
        this.configuration = configuration.getConfiguration();
    }

    /**
     * Returns the analysis configuration values.
     *
     * @return the configuration
     */
    public ConfigurationReference getConfiguration() {
        return configReference;
    }

    /**
     * Returns whether relative paths in warnings should be resolved using a time expensive operation that scans the
     * whole workspace for matching files.
     *
     * @return <code>true</code> if relative paths can be resolved, <code>false</code> otherwise
     */
    public boolean canResolveRelativePaths() {
        return getCanResolveRelativePaths();
    }

    /**
     * Returns whether relative paths in warnings should be resolved using a time expensive operation that scans the
     * whole workspace for matching files.
     *
     * @return <code>true</code> if relative paths can be resolved, <code>false</code> otherwise
     */
    public boolean getCanResolveRelativePaths() {
        return !doNotResolveRelativePaths;
    }

    /**
     * Determines whether only stable builds should be used as reference builds or not.
     *
     * @return <code>true</code> if only stable builds should be used
     */
    public boolean getUseStableBuildAsReference() {
        return configuration.useOnlyStableBuildsAsReference();
    }

    /**
     * Determines whether only stable builds should be used as reference builds or not.
     *
     * @return <code>true</code> if only stable builds should be used
     */
    public boolean useOnlyStableBuildsAsReference() {
        return getUseStableBuildAsReference();
    }

    /**
     * Returns whether new warnings should be computed (with respect to baseline).
     *
     * @return <code>true</code> if new warnings should be computed (with respect to baseline), <code>false</code>
     *         otherwise
     */
    public boolean getCanComputeNew() {
        return configuration.canComputeNew();
    }

    /**
     * Returns whether new warnings should be computed (with respect to baseline).
     *
     * @return <code>true</code> if new warnings should be computed (with respect to baseline), <code>false</code>
     *         otherwise
     */
    public boolean canComputeNew() {
        return getCanComputeNew();
    }

    /**
     * Returns whether this plug-in can run for failed builds, too.
     *
     * @return <code>true</code> if this plug-in can run for failed builds, <code>false</code> otherwise
     */
    public boolean getCanRunOnFailed() {
        return configuration.canRunOnFailed();
    }

    /**
     * Returns whether module names should be derived from Maven POM or Ant build files.
     *
     * @return the can run on failed
     */
    public boolean getShouldDetectModules() {
        return configuration.shouldDetectModules();
    }

    /**
     * Returns whether module names should be derived from Maven POM or Ant build files.
     *
     * @return the can run on failed
     */
    public boolean shouldDetectModules() {
        return getShouldDetectModules();
    }

    /**
     * Returns whether there is a health threshold enabled.
     *
     * @return <code>true</code> if at least one threshold is enabled, <code>false</code> otherwise
     */
    protected boolean isThresholdEnabled() {
        return new NullHealthDescriptor(this).isThresholdEnabled();
    }

    /**
     * Returns the threshold limit.
     *
     * @return the threshold limit
     */
    public String getThresholdLimit() {
        return configuration.getThresholdLimit();
    }

    /**
     * Returns whether absolute annotations delta or the actual annotations set difference should be used to evaluate
     * the build stability.
     *
     * @return <code>true</code> if the annotation count should be used, <code>false</code> if the actual (set)
     *         difference should be computed
     */
    public boolean getUseDeltaValues() {
        return configuration.useDeltaValues();
    }

    /**
     * Returns the healthy threshold, i.e. when health is reported as 100%.
     *
     * @return the 100% healthiness
     */
    public String getHealthy() {
        return configuration.getHealthy();
    }

    /**
     * Returns the unhealthy threshold, i.e. when health is reported as 0%.
     *
     * @return the 0% unhealthiness
     */
    public String getUnHealthy() {
        return configuration.getUnHealthy();
    }

    /**
     * Returns the defined default encoding.
     *
     * @return the default encoding
     */
    @CheckForNull
    public String getDefaultEncoding() {
        return configuration.getDefaultEncoding();
    }

    /** {@inheritDoc} */
    public Thresholds getThresholds() {
        return configuration.getThresholds();
    }

    /** {@inheritDoc} */
    @Override
    public final boolean perform(final AbstractBuild<?, ?> build, final Launcher launcher, final BuildListener listener)
            throws InterruptedException, IOException {
        PluginLogger logger = new LoggerFactory().createLogger(listener.getLogger(), pluginName);
        if (canContinue(build.getResult())) {
            return perform(build, launcher, logger);
        }
        else {
            logger.log("Skipping publisher since build result is " + build.getResult());
            return true;
        }
    }

    /**
     * Callback method that is invoked after the build where this recorder can collect the results.
     *
     * @param build
     *            current build
     * @param launcher
     *            the launcher for this build
     * @param logger
     *            the logger
     * @return <code>true</code> if the build can continue, <code>false</code> otherwise
     * @throws IOException
     *             in case of problems during file copying
     * @throws InterruptedException
     *             if the user canceled the build
     */
    protected abstract boolean perform(AbstractBuild<?, ?> build, Launcher launcher, PluginLogger logger)
            throws InterruptedException, IOException;

    @Override
    public PluginDescriptor getDescriptor() {
        return (PluginDescriptor)super.getDescriptor();
    }

    /**
     * Copies all files with annotations from the workspace to the build folder.
     *
     * @param rootDir
     *            directory to store the copied files in
     * @param channel
     *            channel to get the files from
     * @param annotations
     *            annotations determining the actual files to copy
     * @throws IOException
     *             if the files could not be written
     * @throws FileNotFoundException
     *             if the files could not be written
     * @throws InterruptedException
     *             if the user cancels the processing
     */
    protected void copyFilesWithAnnotationsToBuildFolder(final File rootDir, final VirtualChannel channel,
            final Collection<FileAnnotation> annotations) throws IOException, FileNotFoundException,
            InterruptedException {
        File directory = new File(rootDir, AbstractAnnotation.WORKSPACE_FILES);
        if (!directory.exists() && !directory.mkdir()) {
            throw new IOException("Can't create directory for workspace files that contain annotations: "
                    + directory.getAbsolutePath());
        }
        AnnotationContainer container = new DefaultAnnotationContainer(annotations);
        for (WorkspaceFile file : container.getFiles()) {
            File masterFile = new File(directory, file.getTempName());
            if (!masterFile.exists()) {
                try {
                    FileOutputStream outputStream = new FileOutputStream(masterFile);

                    new FilePath(channel, file.getName()).copyTo(outputStream);
                }
                catch (IOException exception) {
                    logExceptionToFile(exception, masterFile, file.getName());
                }
            }
        }
    }

    /**
     * Logs the specified exception in the specified file.
     *
     * @param exception
     *            the exception
     * @param masterFile
     *            the file on the master
     * @param slaveFileName
     *            the file name of the slave
     */
    private void logExceptionToFile(final IOException exception, final File masterFile, final String slaveFileName) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(masterFile);
            print(outputStream,
                    "Copying the source file '%s' from the workspace to the build folder '%s' on the Jenkins master failed.%n",
                    slaveFileName, masterFile.getAbsolutePath());
            if (!slaveFileName.startsWith(SLASH) && !slaveFileName.contains(":")) {
                print(outputStream,
                        "Seems that the path is relative, however an absolute path is required when copying the sources.%n");
                String base;
                if (slaveFileName.contains(SLASH)) {
                    base = StringUtils.substringAfterLast(slaveFileName, SLASH);
                }
                else {
                    base = slaveFileName;
                }
                print(outputStream, "Is the file '%s' contained more than once in your workspace?%n", base);
            }
            print(outputStream, "Is the file '%s' a valid filename?%n", slaveFileName);
            print(outputStream,
                    "If you are building on a slave: please check if the file is accessible under '$JENKINS_HOME/[job-name]/%s'%n",
                    slaveFileName);
            print(outputStream,
                    "If you are building on the master: please check if the file is accessible under '$JENKINS_HOME/[job-name]/workspace/%s'%n",
                    slaveFileName);
            exception.printStackTrace(new PrintStream(outputStream, false, getEncoding()));
        }
        catch (IOException error) {
            // ignore
        }
        finally {
            IOUtils.closeQuietly(outputStream);
        }
    }

    private void print(final FileOutputStream outputStream, final String message, final Object... arguments)
            throws IOException {
        IOUtils.write(String.format(message, arguments), outputStream, getEncoding());
    }

    private String getEncoding() {
        return EncodingValidator.getEncoding(getDefaultEncoding());
    }

    /**
     * Returns whether this publisher can continue processing. This default implementation returns <code>true</code> if
     * the property <code>canRunOnFailed</code> is set or if the build is not aborted or failed.
     *
     * @param result
     *            build result
     * @return <code>true</code> if the build can continue
     */
    protected boolean canContinue(final Result result) {
        if (getCanRunOnFailed()) {
            return result != Result.ABORTED;
        }
        else {
            return result != Result.ABORTED && result != Result.FAILURE;
        }
    }

    /**
     * Returns whether the current build uses maven.
     *
     * @param build
     *            the current build
     * @return <code>true</code> if the current build uses maven, <code>false</code> otherwise
     */
    protected boolean isMavenBuild(final AbstractBuild<?, ?> build) {
        if (build.getProject() instanceof Project) {
            Project<?, ?> project = (Project<?, ?>)build.getProject();
            for (Builder builder : project.getBuilders()) {
                if (builder instanceof Maven) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns whether the current build uses ant.
     *
     * @param build
     *            the current build
     * @return <code>true</code> if the current build uses ant, <code>false</code> otherwise
     */
    protected boolean isAntBuild(final AbstractBuild<?, ?> build) {
        try {
            return AntBuilderCheck.isAntBuild(build);
        }
        catch (Throwable exception) { // NOPMD NOCHECKSTYLE
            return false; // fallback if ant is not installed
        }
    }

    /** {@inheritDoc} */
    public Priority getMinimumPriority() {
        return Priority.valueOf(StringUtils.upperCase(getThresholdLimit()));
    }

    /** {@inheritDoc} */
    public BuildStepMonitor getRequiredMonitorService() {
        return canComputeNew() ? BuildStepMonitor.STEP : BuildStepMonitor.NONE;
    }

    /**
     * Preserves configurations from installations with old plug-in version: initializes new fields that are not
     * serialized yet.
     *
     * @return the object
     */
    protected Object readResolve() {
        if (thresholdLimit == null) {
            thresholdLimit = DEFAULT_PRIORITY_THRESHOLD_LIMIT;
        }
        if (thresholds == null) {
            thresholds = new Thresholds();

            if (threshold != null) {
                thresholds.unstableTotalAll = threshold;
                threshold = null; // NOPMD
            }
            if (newThreshold != null) {
                thresholds.unstableNewAll = newThreshold;
                newThreshold = null; // NOPMD
            }
            if (failureThreshold != null) {
                thresholds.failedTotalAll = failureThreshold;
                failureThreshold = null; // NOPMD
            }
            if (newFailureThreshold != null) {
                thresholds.failedNewAll = newFailureThreshold;
                newFailureThreshold = null; // NOPMD
            }
        }
        if (configReference == null) { // before 1.55
            Thresholds t = thresholds;
            configReference = new ConfigurationReference.LocalConfigurationReference(healthy, unHealthy,
                    thresholdLimit, useDeltaValues, t.unstableTotalAll, t.unstableTotalHigh, t.unstableTotalNormal,
                    t.unstableTotalLow, t.unstableNewAll, t.unstableNewHigh, t.unstableNewNormal, t.unstableNewLow,
                    t.failedTotalAll, t.failedTotalHigh, t.failedTotalNormal, t.failedTotalLow, t.failedNewAll,
                    t.failedNewHigh, t.failedNewNormal, t.failedNewLow, canRunOnFailed, useStableBuildAsReference,
                    shouldDetectModules, !dontComputeNew, defaultEncoding);
        }
        configuration = configReference.getConfiguration();
        return this;
    }

    // CHECKSTYLE:OFF
    /** Backward compatibility. @deprecated */
    @Deprecated
    private transient String threshold;
    /** Backward compatibility. @deprecated */
    @Deprecated
    private transient String newThreshold;
    /** Backward compatibility. @deprecated */
    @Deprecated
    private transient String failureThreshold;
    /** Backward compatibility. @deprecated */
    @Deprecated
    private transient String newFailureThreshold;
    /** Backward compatibility. @deprecated */
    @SuppressWarnings("unused")
    @Deprecated
    private transient boolean thresholdEnabled;
    /** Backward compatibility. @deprecated */
    @SuppressWarnings("unused")
    @Deprecated
    private transient int minimumAnnotations;
    /** Backward compatibility. @deprecated */
    @SuppressWarnings("unused")
    @Deprecated
    private transient int healthyAnnotations;
    /** Backward compatibility. @deprecated */
    @SuppressWarnings("unused")
    @Deprecated
    private transient int unHealthyAnnotations;
    /** Backward compatibility. @deprecated */
    @SuppressWarnings("unused")
    @Deprecated
    private transient boolean healthyReportEnabled;
    /** Backward compatibility. @deprecated */
    @SuppressWarnings("unused")
    @Deprecated
    private transient String height;
    /** Backward compatibility. @deprecated */
    @Deprecated
    private transient String defaultEncoding;
    /** Backward compatibility. @deprecated */
    @Deprecated
    private transient boolean canRunOnFailed;
    /** Backward compatibility. @deprecated */
    @Deprecated
    private transient boolean useStableBuildAsReference;
    /** Backward compatibility. @deprecated */
    @Deprecated
    private transient boolean useDeltaValues;
    /** Backward compatibility. @deprecated */
    @Deprecated
    private transient Thresholds thresholds = new Thresholds();
    /** Backward compatibility. @deprecated */
    @Deprecated
    private transient boolean shouldDetectModules;
    /** Backward compatibility. @deprecated */
    @Deprecated
    private transient boolean dontComputeNew;
    /** Backward compatibility. @deprecated */
    @Deprecated
    private transient String healthy;
    /** Backward compatibility. @deprecated */
    @Deprecated
    private transient String unHealthy;
    /** Backward compatibility. @deprecated */
    @Deprecated
    private transient String thresholdLimit;

    /** Backward compatibility. @deprecated */
    @SuppressWarnings({"PMD","javadoc"})
    @Deprecated
    public HealthAwareRecorder(final String threshold, final String newThreshold, final String failureThreshold,
            final String newFailureThreshold, final String healthy, final String unHealthy,
            final String thresholdLimit, final String defaultEncoding, final boolean useDeltaValues,
            final boolean canRunOnFailed, final String pluginName) {
        super();

        thresholds.unstableTotalAll = threshold;
        thresholds.unstableNewAll = newThreshold;
        thresholds.failedTotalAll = failureThreshold;
        thresholds.failedNewAll = newFailureThreshold;
        doNotResolveRelativePaths = false;

        this.healthy = healthy;
        this.unHealthy = unHealthy;
        this.thresholdLimit = thresholdLimit;
        this.defaultEncoding = defaultEncoding;
        this.useDeltaValues = useDeltaValues;
        this.canRunOnFailed = canRunOnFailed;
        useStableBuildAsReference = false;
        dontComputeNew = false;
        shouldDetectModules = false;
        this.pluginName = "[" + pluginName + "] ";
        Thresholds t = thresholds;
        configReference = new ConfigurationReference.LocalConfigurationReference(healthy, unHealthy,
                thresholdLimit, useDeltaValues, t.unstableTotalAll, t.unstableTotalHigh, t.unstableTotalNormal,
                t.unstableTotalLow, t.unstableNewAll, t.unstableNewHigh, t.unstableNewNormal, t.unstableNewLow,
                t.failedTotalAll, t.failedTotalHigh, t.failedTotalNormal, t.failedTotalLow, t.failedNewAll,
                t.failedNewHigh, t.failedNewNormal, t.failedNewLow, canRunOnFailed, useStableBuildAsReference,
                shouldDetectModules, !dontComputeNew, defaultEncoding);
        configuration = configReference.getConfiguration();
    }

    /** Backward compatibility. @deprecated */
    @SuppressWarnings({"PMD","javadoc"})
    @Deprecated
    public HealthAwareRecorder(final String healthy, final String unHealthy, final String thresholdLimit,
            final String defaultEncoding, final boolean useDeltaValues, final String unstableTotalAll,
            final String unstableTotalHigh, final String unstableTotalNormal, final String unstableTotalLow,
            final String unstableNewAll, final String unstableNewHigh, final String unstableNewNormal,
            final String unstableNewLow, final String failedTotalAll, final String failedTotalHigh,
            final String failedTotalNormal, final String failedTotalLow, final String failedNewAll,
            final String failedNewHigh, final String failedNewNormal, final String failedNewLow,
            final boolean canRunOnFailed, final boolean shouldDetectModules, final boolean canComputeNew,
            final boolean canResolveRelativePaths, final String pluginName) {
        this(healthy, unHealthy, thresholdLimit, defaultEncoding, useDeltaValues, unstableTotalAll, unstableTotalHigh,
                unstableTotalNormal, unstableTotalLow, unstableNewAll, unstableNewHigh, unstableNewNormal,
                unstableNewLow, failedTotalAll, failedTotalHigh, failedTotalNormal, failedTotalLow, failedNewAll,
                failedNewHigh, failedNewNormal, failedNewLow, canRunOnFailed, false, shouldDetectModules,
                canComputeNew, canResolveRelativePaths, pluginName);
    }

    /** Backward compatibility. @deprecated */
    @SuppressWarnings({"PMD","javadoc"})
    @Deprecated
    public HealthAwareRecorder(final String healthy, final String unHealthy, final String thresholdLimit,
            final String defaultEncoding, final boolean useDeltaValues, final String unstableTotalAll,
            final String unstableTotalHigh, final String unstableTotalNormal, final String unstableTotalLow,
            final String unstableNewAll, final String unstableNewHigh, final String unstableNewNormal,
            final String unstableNewLow, final String failedTotalAll, final String failedTotalHigh,
            final String failedTotalNormal, final String failedTotalLow, final String failedNewAll,
            final String failedNewHigh, final String failedNewNormal, final String failedNewLow,
            final boolean canRunOnFailed, final boolean useStableBuildAsReference, final boolean shouldDetectModules,
            final boolean canComputeNew, final boolean canResolveRelativePaths, final String pluginName) {
        super();
        this.healthy = healthy;
        this.unHealthy = unHealthy;
        this.thresholdLimit = StringUtils.defaultIfEmpty(thresholdLimit, DEFAULT_PRIORITY_THRESHOLD_LIMIT);
        this.defaultEncoding = defaultEncoding;
        this.useDeltaValues = useDeltaValues;

        doNotResolveRelativePaths = !canResolveRelativePaths;
        dontComputeNew = !canComputeNew;

        thresholds.unstableTotalAll = unstableTotalAll;
        thresholds.unstableTotalHigh = unstableTotalHigh;
        thresholds.unstableTotalNormal = unstableTotalNormal;
        thresholds.unstableTotalLow = unstableTotalLow;
        thresholds.unstableNewAll = unstableNewAll;
        thresholds.unstableNewHigh = unstableNewHigh;
        thresholds.unstableNewNormal = unstableNewNormal;
        thresholds.unstableNewLow = unstableNewLow;
        thresholds.failedTotalAll = failedTotalAll;
        thresholds.failedTotalHigh = failedTotalHigh;
        thresholds.failedTotalNormal = failedTotalNormal;
        thresholds.failedTotalLow = failedTotalLow;
        thresholds.failedNewAll = failedNewAll;
        thresholds.failedNewHigh = failedNewHigh;
        thresholds.failedNewNormal = failedNewNormal;
        thresholds.failedNewLow = failedNewLow;

        this.canRunOnFailed = canRunOnFailed;
        this.useStableBuildAsReference = useStableBuildAsReference;
        this.shouldDetectModules = shouldDetectModules;
        this.pluginName = "[" + pluginName + "] ";

        Thresholds t = thresholds;
        configReference = new ConfigurationReference.LocalConfigurationReference(healthy, unHealthy,
                thresholdLimit, useDeltaValues, t.unstableTotalAll, t.unstableTotalHigh, t.unstableTotalNormal,
                t.unstableTotalLow, t.unstableNewAll, t.unstableNewHigh, t.unstableNewNormal, t.unstableNewLow,
                t.failedTotalAll, t.failedTotalHigh, t.failedTotalNormal, t.failedTotalLow, t.failedNewAll,
                t.failedNewHigh, t.failedNewNormal, t.failedNewLow, canRunOnFailed, useStableBuildAsReference,
                shouldDetectModules, !dontComputeNew, defaultEncoding);
        configuration = configReference.getConfiguration();
    }
    // CHECKSTYLE:OFF
}
