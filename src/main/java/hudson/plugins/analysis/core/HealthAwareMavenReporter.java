package hudson.plugins.analysis.core;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.apache.maven.project.MavenProject;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

import hudson.FilePath;
import hudson.maven.MavenBuild;
import hudson.maven.MavenBuildProxy;
import hudson.maven.MavenBuildProxy.BuildCallable;
import hudson.maven.MavenReporter;
import hudson.maven.MojoInfo;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.plugins.analysis.Messages;
import hudson.plugins.analysis.util.LoggerFactory;
import hudson.plugins.analysis.util.PluginLogger;
import hudson.plugins.analysis.util.model.AbstractAnnotation;
import hudson.plugins.analysis.util.model.AnnotationContainer;
import hudson.plugins.analysis.util.model.DefaultAnnotationContainer;
import hudson.plugins.analysis.util.model.FileAnnotation;
import hudson.plugins.analysis.util.model.Priority;
import hudson.plugins.analysis.util.model.WorkspaceFile;
import hudson.remoting.Channel;
import hudson.remoting.VirtualChannel;
import hudson.tasks.BuildStep;

/**
 * A base class for maven reporters with the following two characteristics:
 * <ul>
 * <li>It provides a unstable threshold, that could be enabled and set in the
 * configuration screen. If the number of annotations in a build exceeds this
 * value then the build is considered as {@link Result#UNSTABLE UNSTABLE}.
 * </li>
 * <li>It provides thresholds for the build health, that could be adjusted in
 * the configuration screen. These values are used by the
 * {@link HealthReportBuilder} to compute the health and the health trend graph.</li>
 * </ul>
 *
 * @author Ulli Hafner
 * @deprecated use {@link HealthAwareReporter}
 */
// CHECKSTYLE:COUPLING-OFF
@SuppressWarnings({"PMD.TooManyFields", "deprecation"})
@Deprecated
public abstract class HealthAwareMavenReporter extends MavenReporter implements HealthDescriptor {
    /** Default threshold priority limit. */
    private static final String DEFAULT_PRIORITY_THRESHOLD_LIMIT = "low";
    /** Unique identifier of this class. */
    private static final long serialVersionUID = 3003791883748835331L;
    /** Report health as 100% when the number of warnings is less than this value. */
    private final String healthy;
    /** Report health as 0% when the number of warnings is greater than this value. */
    private final String unHealthy;
    /** The name of the plug-in. */
    private final String pluginName;
    /** Determines which warning priorities should be considered when evaluating the build stability and health. */
    private String thresholdLimit;
    /** The default encoding to be used when reading and parsing files. */
    private String defaultEncoding;
    /** Determines whether the plug-in should run for failed builds, too. @since 1.6 */
    private final boolean canRunOnFailed;

    /**
     * Thresholds for build status unstable and failed, resp. and priorities
     * all, high, normal, and low, resp.
     *
     * @since 1.20
     */
    private Thresholds thresholds = new Thresholds();

    /**
     * Creates a new instance of <code>HealthReportingMavenReporter</code>.
     *
     * @param healthy
     *            Report health as 100% when the number of warnings is less than
     *            this value
     * @param unHealthy
     *            Report health as 0% when the number of warnings is greater
     *            than this value
     * @param thresholdLimit
     *            determines which warning priorities should be considered when
     *            evaluating the build stability and health
     * @param unstableTotalAll
     *            annotation threshold
     * @param unstableTotalHigh
     *            annotation threshold
     * @param unstableTotalNormal
     *            annotation threshold
     * @param unstableTotalLow
     *            annotation threshold
     * @param unstableNewAll
     *            annotation threshold
     * @param unstableNewHigh
     *            annotation threshold
     * @param unstableNewNormal
     *            annotation threshold
     * @param unstableNewLow
     *            annotation threshold
     * @param failedTotalAll
     *            annotation threshold
     * @param failedTotalHigh
     *            annotation threshold
     * @param failedTotalNormal
     *            annotation threshold
     * @param failedTotalLow
     *            annotation threshold
     * @param failedNewAll
     *            annotation threshold
     * @param failedNewHigh
     *            annotation threshold
     * @param failedNewNormal
     *            annotation threshold
     * @param failedNewLow
     *            annotation threshold
     * @param canRunOnFailed
     *            determines whether the plug-in can run for failed builds, too
     * @param pluginName
     *            the name of the plug-in
     */
    // CHECKSTYLE:OFF
    @SuppressWarnings("PMD.ExcessiveParameterList")
    public HealthAwareMavenReporter(final String healthy, final String unHealthy, final String thresholdLimit,
            final String unstableTotalAll, final String unstableTotalHigh, final String unstableTotalNormal, final String unstableTotalLow,
            final String unstableNewAll, final String unstableNewHigh, final String unstableNewNormal, final String unstableNewLow,
            final String failedTotalAll, final String failedTotalHigh, final String failedTotalNormal, final String failedTotalLow,
            final String failedNewAll, final String failedNewHigh, final String failedNewNormal, final String failedNewLow,
            final boolean canRunOnFailed, final String pluginName) {
        super();
        this.healthy = healthy;
        this.unHealthy = unHealthy;
        this.thresholdLimit = thresholdLimit;
        this.canRunOnFailed = canRunOnFailed;
        this.pluginName = "[" + pluginName + "] ";

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

        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Jdk14Logger");
    }
    // CHECKSTYLE:ON

    /**
     * Creates a new instance of <code>HealthReportingMavenReporter</code>.
     *
     * @param threshold
     *            Annotations threshold to be reached if a build should be
     *            considered as unstable.
     * @param newThreshold
     *            New annotations threshold to be reached if a build should be
     *            considered as unstable.
     * @param failureThreshold
     *            Annotation threshold to be reached if a build should be considered as
     *            failure.
     * @param newFailureThreshold
     *            New annotations threshold to be reached if a build should be
     *            considered as failure.
     * @param healthy
     *            Report health as 100% when the number of warnings is less than
     *            this value
     * @param unHealthy
     *            Report health as 0% when the number of warnings is greater
     *            than this value
     * @param thresholdLimit
     *            determines which warning priorities should be considered when
     *            evaluating the build stability and health
     * @param canRunOnFailed
     *            determines whether the plug-in can run for failed builds, too
     * @param pluginName
     *            the name of the plug-in
     * @deprecated replaced by {@link HealthAwareMavenReporter#HealthAwareMavenReporter(String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, boolean, String)}
     */
    // CHECKSTYLE:OFF
    @SuppressWarnings("deprecation")
    @Deprecated
    public HealthAwareMavenReporter(final String threshold, final String newThreshold,
            final String failureThreshold, final String newFailureThreshold,
            final String healthy, final String unHealthy,
            final String thresholdLimit, final boolean canRunOnFailed, final String pluginName) {
        this(healthy, unHealthy, thresholdLimit,
                threshold, "", "", "",
                newThreshold, "", "", "",
                failureThreshold, "", "", "",
                newFailureThreshold, "", "", "",
                canRunOnFailed, pluginName);
    }

    /**
     * Creates a new instance of <code>HealthReportingMavenReporter</code>.
     *
     * @param threshold
     *            Annotations threshold to be reached if a build should be
     *            considered as unstable.
     * @param newThreshold
     *            New annotations threshold to be reached if a build should be
     *            considered as unstable.
     * @param failureThreshold
     *            Annotation threshold to be reached if a build should be considered as
     *            failure.
     * @param newFailureThreshold
     *            New annotations threshold to be reached if a build should be
     *            considered as failure.
     * @param healthy
     *            Report health as 100% when the number of warnings is less than
     *            this value
     * @param unHealthy
     *            Report health as 0% when the number of warnings is greater
     *            than this value
     * @param thresholdLimit
     *            determines which warning priorities should be considered when
     *            evaluating the build stability and health
     * @param pluginName
     *            the name of the plug-in
     * @deprecated replaced by {@link HealthAwareMavenReporter#HealthAwareMavenReporter(String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, String, boolean, String)}
     */
    // CHECKSTYLE:OFF
    @Deprecated
    public HealthAwareMavenReporter(final String threshold, final String newThreshold,
            final String failureThreshold, final String newFailureThreshold,
            final String healthy, final String unHealthy,
            final String thresholdLimit, final String pluginName) {
        this(healthy, unHealthy, thresholdLimit,
                threshold, "", "", "",
                newThreshold, "", "", "",
                failureThreshold, "", "", "",
                newFailureThreshold, "", "", "",
                false, pluginName);
    }
    // CHECKSTYLE:ON

    @Override
    public Thresholds getThresholds() {
        return thresholds;
    }

    /**
     * Initializes new fields that are not serialized yet.
     *
     * @return the object
     */
    @SuppressWarnings("deprecation")
    @SuppressFBWarnings("Se")
    private Object readResolve() {
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
                failureThreshold = null; //NOPMD
            }
            if (newFailureThreshold != null) {
                thresholds.failedNewAll = newFailureThreshold;
                newFailureThreshold = null; // NOPMD
            }
        }
        return this;
    }

    @SuppressWarnings({"serial", "PMD.AvoidFinalLocalVariable"})
    @Override
    public final boolean postExecute(final MavenBuildProxy build, final MavenProject pom, final MojoInfo mojo,
            final BuildListener listener, final Throwable error) throws InterruptedException, IOException {
        PluginLogger logger = new LoggerFactory().createLogger(listener.getLogger(), pluginName);
        if (!acceptGoal(mojo.getGoal())) {
            return true;
        }
        Result currentResult = getCurrentResult(build);
        if (!canContinue(currentResult)) {
            logger.log("Skipping reporter since build result is " + currentResult);
            return true;
        }

        if (hasResultAction(build)) {
            logger.log("Skipping maven reporter: there is already a result available.");
            return true;
        }

        final ParserResult result = perform(build, pom, mojo, logger);

        defaultEncoding = pom.getProperties().getProperty("project.build.sourceEncoding");
        if (defaultEncoding == null) {
            logger.log(Messages.Reporter_Error_NoEncoding(Charset.defaultCharset().displayName()));
            result.addErrorMessage(pom.getName(), Messages.Reporter_Error_NoEncoding(Charset.defaultCharset().displayName()));
        }

        build.execute((BuildCallable<Void, IOException>) mavenBuild -> {
            persistResult(result, mavenBuild);

            return null;
        });

        copyFilesWithAnnotationsToBuildFolder(logger, build.getRootDir(), result.getAnnotations());

        return true;
    }

    /**
     * Returns the current result of the build.
     *
     * @param build
     *            the build proxy
     * @return the current result of the build
     * @throws IOException
     *             if the results could not be read
     * @throws InterruptedException
     *             if the user canceled the operation
     */
    private Result getCurrentResult(final MavenBuildProxy build) throws IOException, InterruptedException {
        return build.execute(new BuildResultCallable());
    }

    /**
     * Returns whether this plug-in can run for failed builds, too.
     *
     * @return the can run on failed
     */
    public boolean getCanRunOnFailed() {
        return canRunOnFailed;
    }

    /**
     * Returns whether this reporter can continue processing. This default
     * implementation returns <code>true</code> if the property
     * <code>canRunOnFailed</code> is set or if the build is not aborted or
     * failed.
     *
     * @param result
     *            build result
     * @return <code>true</code> if the build can continue
     */
    protected boolean canContinue(final Result result) {
        if (canRunOnFailed) {
            return result != Result.ABORTED;
        }
        else {
            return result != Result.ABORTED && result != Result.FAILURE;
        }
    }

    /**
     * Copies all files with annotations from the workspace to the build folder.
     *
     * @param logger
     *            logger to log any problems
     * @param buildRoot
     *            directory to store the copied files in
     * @param annotations
     *            annotations determining the actual files to copy
     * @throws IOException
     *             if the files could not be written
     * @throws FileNotFoundException
     *             if the files could not be written
     * @throws InterruptedException
     *             if the user cancels the processing
     */
    private void copyFilesWithAnnotationsToBuildFolder(final PluginLogger logger, final FilePath buildRoot, final Collection<FileAnnotation> annotations) throws IOException,
            FileNotFoundException, InterruptedException {
        FilePath directory = new FilePath(buildRoot, AbstractAnnotation.WORKSPACE_FILES);
        if (!directory.exists()) {
            directory.mkdirs();
        }
        AnnotationContainer container = new DefaultAnnotationContainer(annotations);
        for (WorkspaceFile file : container.getFiles()) {
            FilePath masterFile = new FilePath(directory, file.getTempName());
            if (!masterFile.exists()) {
                try {
                    new FilePath((Channel)null, file.getName()).copyTo(masterFile);
                }
                catch (IOException exception) {
                    String message = "Can't copy source file: source=" + file.getName() + ", destination=" + masterFile.getName();
                    logger.log(message);
                    logger.printStackTrace(exception);
                }
            }
        }
    }

    /**
     * Determines whether this plug-in will accept the specified goal. The
     * {@link #postExecute(MavenBuildProxy, MavenProject, MojoInfo,
     * BuildListener, Throwable)} will only by invoked if the plug-in returns
     * <code>true</code>.
     *
     * @param goal the maven goal
     * @return <code>true</code> if the plug-in accepts this goal
     */
    protected abstract boolean acceptGoal(final String goal);

    /**
     * Performs the publishing of the results of this plug-in.
     *
     * @param build
     *            the build proxy (on the slave)
     * @param pom
     *            the pom of the module
     * @param mojo
     *            the executed mojo
     * @param logger
     *            the logger to report the progress to
     * @return the java project containing the found annotations
     * @throws InterruptedException
     *             If the build is interrupted by the user (in an attempt to
     *             abort the build.) Normally the {@link BuildStep}
     *             implementations may simply forward the exception it got from
     *             its lower-level functions.
     * @throws IOException
     *             If the implementation wants to abort the processing when an
     *             {@link IOException} happens, it can simply propagate the
     *             exception to the caller. This will cause the build to fail,
     *             with the default error message. Implementations are
     *             encouraged to catch {@link IOException} on its own to provide
     *             a better error message, if it can do so, so that users have
     *             better understanding on why it failed.
     */
    protected abstract ParserResult perform(MavenBuildProxy build, MavenProject pom, MojoInfo mojo,
            PluginLogger logger) throws InterruptedException, IOException;

    /**
     * Persists the result in the build (on the master).
     *
     * @param project
     *            the created project
     * @param build
     *            the build (on the master)
     * @return the created result
     */
    protected abstract BuildResult persistResult(ParserResult project, MavenBuild build);

    /**
     * Returns the default encoding derived from the maven pom file.
     *
     * @return the default encoding
     */
    protected String getDefaultEncoding() {
        return defaultEncoding;
    }

    /**
     * Returns whether we already have a result for this build.
     *
     * @param build
     *            the current build.
     * @return <code>true</code> if we already have a task result action.
     * @throws IOException
     *             in case of an IO error
     * @throws InterruptedException
     *             if the call has been interrupted
     */
    @SuppressWarnings("serial")
    private Boolean hasResultAction(final MavenBuildProxy build) throws IOException, InterruptedException {
        return build.execute((BuildCallable<Boolean, IOException>) mavenBuild -> mavenBuild.getAction(getResultActionClass()) != null);
    }

    /**
     * Returns the type of the result action.
     *
     * @return the type of the result action
     */
    protected abstract Class<? extends Action> getResultActionClass();

    /**
     * Returns the path to the target folder.
     *
     * @param pom the maven pom
     * @return the path to the target folder
     */
    protected FilePath getTargetPath(final MavenProject pom) {
        return new FilePath((VirtualChannel)null, pom.getBuild().getDirectory());
    }

    /**
     * Returns the threshold of all annotations to be reached if a build should
     * be considered as unstable.
     *
     * @return the threshold of all annotations to be reached if a build should
     *         be considered as unstable.
     */
    public String getThreshold() {
        return threshold;
    }

    /**
     * Returns the threshold for new annotations to be reached if a build should
     * be considered as unstable.
     *
     * @return the threshold for new annotations to be reached if a build should
     *         be considered as unstable.
     */
    public String getNewThreshold() {
        return newThreshold;
    }

    /**
     * Returns the annotation threshold to be reached if a build should be
     * considered as failure.
     *
     * @return the annotation threshold to be reached if a build should be
     *         considered as failure.
     */
    public String getFailureThreshold() {
        return failureThreshold;
    }

    /**
     * Returns the threshold of new annotations to be reached if a build should
     * be considered as failure.
     *
     * @return the threshold of new annotations to be reached if a build should
     *         be considered as failure.
     */
    public String getNewFailureThreshold() {
        return newFailureThreshold;
    }

    /**
     * Returns the healthy threshold, i.e. when health is reported as 100%.
     *
     * @return the 100% healthiness
     */
    @Override
    public String getHealthy() {
        return healthy;
    }

    /**
     * Returns the unhealthy threshold, i.e. when health is reported as 0%.
     *
     * @return the 0% unhealthiness
     */
    @Override
    public String getUnHealthy() {
        return unHealthy;
    }

    @Override
    public Priority getMinimumPriority() {
        return Priority.valueOf(StringUtils.upperCase(getThresholdLimit()));
    }

    /**
     * Returns the threshold limit.
     *
     * @return the threshold limit
     */
    public String getThresholdLimit() {
        return thresholdLimit;
    }

    /**
     * Returns the name of the module.
     *
     * @param pom
     *            the pom
     * @return the name of the module
     */
    protected String getModuleName(final MavenProject pom) {
        return StringUtils.defaultString(pom.getName(), pom.getArtifactId());
    }


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
    private transient String threshold;
    /** Backward compatibility. @deprecated */
    @Deprecated
    private transient String failureThreshold;
    /** Backward compatibility. @deprecated */
    @Deprecated
    private transient String newFailureThreshold;
    /** Backward compatibility. @deprecated */
    @Deprecated
    private transient String newThreshold;

    /**
     * Gets the build result from the master.
     */
    private static final class BuildResultCallable implements BuildCallable<Result, IOException> {
        /** Unique ID. */
        private static final long serialVersionUID = -270795641776014760L;

            @Override
        public Result call(final MavenBuild mavenBuild) throws IOException, InterruptedException {
            return mavenBuild.getResult();
        }
    }
}

