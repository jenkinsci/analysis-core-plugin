package hudson.plugins.analysis.core;

import javax.annotation.CheckForNull;
import java.io.Serializable;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;

/**
 * Defines a named configuration of analysis thresholds.
 *
 * @author Ulli Hafner
 * @since 2.0
 */
public class AnalysisConfiguration extends AbstractDescribableImpl<AnalysisConfiguration> implements Serializable {
    private static final long serialVersionUID = 7582357597396710787L;

    private static final String DEFAULT = StringUtils.EMPTY;
    private static final String DEFAULT_PRIORITY_THRESHOLD_LIMIT = "low";

    private final String healthy;
    private final String unHealthy;
    private final String thresholdLimit;

    private final boolean useDeltaValues;
    private final Thresholds thresholds = new Thresholds();

    private final String defaultEncoding;

    private final boolean canComputeNew;
    private final boolean canRunOnFailed;
    private final boolean useStableBuildAsReference;
    private final boolean shouldDetectModules;

    private final String name;

    /**
     * Creates a new instance of {@link AnalysisConfiguration}.
     *
     * @param name
     *            the unique name of this configuration
     * @param healthy
     *            Report health as 100% when the number of open tasks is less than this value
     * @param unHealthy
     *            Report health as 0% when the number of open tasks is greater than this value
     * @param thresholdLimit
     *            determines which warning priorities should be considered when evaluating the build stability and
     *            health
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
     * @param useDeltaValues
     *            determines whether the absolute annotations delta or the actual annotations set difference should be
     *            used to evaluate the build stability
     * @param canRunOnFailed
     *            determines whether the plug-in can run for failed builds, too
     * @param useStableBuildAsReference
     *            determines whether only stable builds should be used as reference builds or not
     * @param shouldDetectModules
     *            determines whether module names should be derived from Maven POM or Ant build files
     * @param canComputeNew
     *            determines whether new warnings should be computed (with respect to baseline)
     * @param defaultEncoding
     *            the default encoding to be used when reading and parsing files
     */
    // CHECKSTYLE:OFF
    @DataBoundConstructor
    @SuppressWarnings("PMD")
    public AnalysisConfiguration(final String name, final String healthy, final String unHealthy, final String thresholdLimit,
            final String unstableTotalAll, final String unstableTotalHigh, final String unstableTotalNormal, final String unstableTotalLow,
            final String unstableNewAll, final String unstableNewHigh, final String unstableNewNormal, final String unstableNewLow,
            final String failedTotalAll, final String failedTotalHigh, final String failedTotalNormal, final String failedTotalLow,
            final String failedNewAll, final String failedNewHigh, final String failedNewNormal, final String failedNewLow,
            final boolean useDeltaValues, final boolean canRunOnFailed, final boolean useStableBuildAsReference,
            final boolean shouldDetectModules, final boolean canComputeNew, final String defaultEncoding) {
        super();

        this.name = name;

        this.healthy = healthy;
        this.unHealthy = unHealthy;
        this.thresholdLimit = StringUtils.defaultIfEmpty(thresholdLimit, DEFAULT_PRIORITY_THRESHOLD_LIMIT);

        this.useDeltaValues = useDeltaValues;

        this.defaultEncoding = defaultEncoding;
        this.canComputeNew = canComputeNew;
        this.canRunOnFailed = canRunOnFailed;
        this.useStableBuildAsReference = useStableBuildAsReference;
        this.shouldDetectModules = shouldDetectModules;

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
    }

    /**
     * Creates a new instance of {@link AnalysisConfiguration}. This instance will have all values set to their
     * defaults.
     *
     * @param name
     *            the unique name of this configuration
     */
    public AnalysisConfiguration(final String name) {
        this(name, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT,
                DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT, DEFAULT,
                false, false, false, true, true, DEFAULT);
    }

    /**
     * Returns the unique name of this configuration.
     *
     * @return the name
     */
    public String getName() {
        return name;
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
     * Determines whether only stable builds should be used as reference builds or not.
     *
     * @return <code>true</code> if only stable builds should be used
     */
    public boolean getUseStableBuildAsReference() {
        return useStableBuildAsReference;
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
     * Returns whether new warnings should be computed (with respect to baseline).
     *
     * @return <code>true</code> if new warnings should be computed (with respect to baseline), <code>false</code>
     *         otherwise
     */
    public boolean getCanComputeNew() {
        return canComputeNew;
    }

    /**
     * Returns whether this plug-in can run for failed builds, too.
     *
     * @return <code>true</code> if this plug-in can run for failed builds, <code>false</code> otherwise
     */
    public boolean canRunOnFailed() {
        return getCanRunOnFailed();
    }

    /**
     * Returns whether this plug-in can run for failed builds, too.
     *
     * @return <code>true</code> if this plug-in can run for failed builds, <code>false</code> otherwise
     */
    public boolean getCanRunOnFailed() {
        return canRunOnFailed;
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
     * Returns whether module names should be derived from Maven POM or Ant build files.
     *
     * @return the can run on failed
     */
    public boolean getShouldDetectModules() {
        return shouldDetectModules;
    }

    /**
     * Returns whether absolute annotations delta or the actual annotations set difference should be used to evaluate
     * the build stability.
     *
     * @return <code>true</code> if the annotation count should be used, <code>false</code> if the actual (set)
     *         difference should be computed
     */
    public boolean useDeltaValues() {
        return getUseDeltaValues();
    }

    /**
     * Returns whether absolute annotations delta or the actual annotations set difference should be used to evaluate
     * the build stability.
     *
     * @return <code>true</code> if the annotation count should be used, <code>false</code> if the actual (set)
     *         difference should be computed
     */
    public boolean getUseDeltaValues() {
        return useDeltaValues;
    }

    /**
     * Returns the thresholds to change the build status.
     *
     * @return the thresholds
     */
    public Thresholds getThresholds() {
        return thresholds;
    }

    /**
     * Returns the healthy threshold, i.e. when health is reported as 100%.
     *
     * @return the 100% healthiness
     */
    public String getHealthy() {
        return healthy;
    }

    /**
     * Returns the unhealthy threshold, i.e. when health is reported as 0%.
     *
     * @return the 0% unhealthiness
     */
    public String getUnHealthy() {
        return unHealthy;
    }

    /**
     * Returns the smallest priority that should be included in the threshold evaluation.
     *
     * @return the threshold limit
     */
    public String getThresholdLimit() {
        return thresholdLimit;
    }

    /**
     * Returns the defined default encoding.
     *
     * @return the default encoding
     */
    @CheckForNull
    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    /**
     * Descriptor of {@link AnalysisConfiguration}.
     */
    @Extension
    public static class DescriptorImpl extends Descriptor<AnalysisConfiguration> {
        @Override
        public String getDisplayName() {
            return DEFAULT;
        }
    }
}
