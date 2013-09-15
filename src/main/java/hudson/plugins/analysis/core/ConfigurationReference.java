package hudson.plugins.analysis.core;

import javax.annotation.CheckForNull;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.DataBoundConstructor;

import com.google.common.collect.Lists;

import hudson.Extension;
import hudson.model.AbstractDescribableImpl;
import hudson.model.Descriptor;
import hudson.plugins.analysis.Messages;

/**
 * Reference to a configuration of the thresholds for the static analysis plug-in.
 *
 * @author Ulli Hafner
 * @since 2.0
 */
public abstract class ConfigurationReference extends AbstractDescribableImpl<ConfigurationReference> {
    /**
     * Returns the available configurations for the specified configurable object.
     *
     * @param reference
     *            the configuration reference instance (may be <code>null</code>)
     * @return the available configurations
     */
    public static Collection<ConfigurationReference> getConfigurations(@CheckForNull final LocalConfigurationReference reference) {
        return getConfigurations((ConfigurationReference)reference);
    }

    /**
     * Returns the available configurations for the specified configurable object.
     *
     * @param reference
     *            the configuration reference instance (may be <code>null</code>)
     * @return the available configurations
     */
    public static Collection<ConfigurationReference> getConfigurations(@CheckForNull final GlobalConfigurationReference reference) {
        return getConfigurations((ConfigurationReference)reference);
    }

    /**
     * Returns the available configurations for the specified configurable object.
     *
     * @param reference
     *            the configuration reference instance (may be <code>null</code>)
     * @return the available configurations
     */
    public static Collection<ConfigurationReference> getConfigurations(@CheckForNull final ConfigurationReference reference) {
        List<ConfigurationReference> references = Lists.newArrayList();
        AnalysisConfiguration[] analysisConfigurations = GlobalSettings.instance().getConfigurations();
        for (AnalysisConfiguration analysisConfiguration : analysisConfigurations) {
            references.add(new GlobalConfigurationReference(analysisConfiguration.getName()));
        }
        if (reference == null || reference instanceof GlobalConfigurationReference) {
            references.add(new LocalConfigurationReference());
        }
        else {
            references.add(reference);
        }
        return references;
    }

    /**
     * Returns the unique name of this configuration. This name is shown in the UI to select the configuration.
     *
     * @return the unique name
     */
    public abstract String getName();

    /**
     * Returns the configuration for the static analysis plug-in.
     *
     * @return the configuration
     */
    public abstract AnalysisConfiguration getConfiguration();

    /**
     * Global configuration: the values are configured Jenkins configuration screen and will be shared across different jobs.
     */
    public static class GlobalConfigurationReference extends ConfigurationReference {
        private final String name;

        /**
         * Creates a new instance of {@link ConfigurationReference.GlobalConfigurationReference}.
         *
         * @param name
         *            the name of the referenced configuration
         */
        @DataBoundConstructor
        public GlobalConfigurationReference(final String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public AnalysisConfiguration getConfiguration() {
            AnalysisConfiguration[] configurations = GlobalSettings.instance().getConfigurations();
            for (AnalysisConfiguration configuration : configurations) {
                if (StringUtils.equals(configuration.getName(), name)) {
                    return configuration;
                }
            }
            return new AnalysisConfiguration(name); // configuration has been deleted by an user
        }

        /**
         * Descriptor of {@link GlobalConfigurationReference}.
         */
        @Extension
        public static class DescriptorImpl extends Descriptor<ConfigurationReference> {
            @Override
            public String getDisplayName() {
                return StringUtils.EMPTY;
            }
        }
    }

    /**
     * Job local configuration: all values are configured in the job and not visible to others.
     */
    public static class LocalConfigurationReference extends ConfigurationReference {
        private final AnalysisConfiguration configuration;

        /**
         * Creates a new instance of {@link ConfigurationReference.LocalConfigurationReference}.
         *
         * @param healthy
         *            Report health as 100% when the number of open tasks is less than this value
         * @param unHealthy
         *            Report health as 0% when the number of open tasks is greater than this value
         * @param thresholdLimit
         *            determines which warning priorities should be considered when evaluating the build stability and
         *            health
         * @param useDeltaValues
         *            determines whether the absolute annotations delta or the actual annotations set difference should
         *            be used to evaluate the build stability
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
         * @param defaultEncoding
         *            the default encoding to be used when reading and parsing files
         * @param canRunOnFailed
         *            determines whether the plug-in can run for failed builds, too
         * @param useStableBuildAsReference
         *            determines whether only stable builds should be used as reference builds or not
         * @param shouldDetectModules
         *            determines whether module names should be derived from Maven POM or Ant build files
         * @param canComputeNew
         *            determines whether new warnings should be computed (with respect to baseline)
         */
        @DataBoundConstructor
        // CHECKSTYLE:OFF
        public LocalConfigurationReference(final String healthy, final String unHealthy, final String thresholdLimit,
                final boolean useDeltaValues, final String unstableTotalAll, final String unstableTotalHigh,
                final String unstableTotalNormal, final String unstableTotalLow, final String unstableNewAll,
                final String unstableNewHigh, final String unstableNewNormal, final String unstableNewLow,
                final String failedTotalAll, final String failedTotalHigh, final String failedTotalNormal,
                final String failedTotalLow, final String failedNewAll, final String failedNewHigh,
                final String failedNewNormal, final String failedNewLow, final boolean canRunOnFailed,
                final boolean useStableBuildAsReference, final boolean shouldDetectModules,
                final boolean canComputeNew, final String defaultEncoding) {
            // CHECKSTYLE:ON
            configuration = new AnalysisConfiguration(getName(), healthy, unHealthy, thresholdLimit, unstableTotalAll,
                    unstableTotalHigh, unstableTotalNormal, unstableTotalLow, unstableNewAll, unstableNewHigh,
                    unstableNewNormal, unstableNewLow, failedTotalAll, failedTotalHigh, failedTotalNormal,
                    failedTotalLow, failedNewAll, failedNewHigh, failedNewNormal, failedNewLow, useDeltaValues,
                    canRunOnFailed, useStableBuildAsReference, shouldDetectModules, canComputeNew, defaultEncoding);
        }

        /**
         * Creates a new instance of {@link LocalConfigurationReference}.
         */
        public LocalConfigurationReference() {
            configuration = new AnalysisConfiguration(getName());
        }

        /** {@inheritDoc} */
        @Override
        public String getName() {
            return Messages.LocalConfiguration();
        }

        /**
         * Returns the configuration.
         *
         * @return the configuration
         */
        @Override
        public AnalysisConfiguration getConfiguration() {
            return configuration;
        }

        /**
         * Descriptor of {@link LocalConfigurationReference}.
         */
        @Extension
        public static class DescriptorImpl extends Descriptor<ConfigurationReference> {
            @Override
            public String getDisplayName() {
                return StringUtils.EMPTY;
            }
        }
    }
}
