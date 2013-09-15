package hudson.plugins.analysis.core;

import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.StaplerRequest;

import jenkins.model.Jenkins;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.Run;
import hudson.model.listeners.RunListener;
import hudson.util.CopyOnWriteList;

/**
 * Global settings common to all static analysis plug-ins. The actual extension point {@link RunListener} is not used
 * yet, this object is only used to provide a model for the view global.jelly.
 *
 * @author Ulli Hafner
 * @since 1.50
 */
@Extension
public class GlobalSettings extends RunListener<Run<?, ?>> implements Describable<GlobalSettings> {
    @Override
    public DescriptorImpl getDescriptor() {
        return findDescriptor();
    }

    private static DescriptorImpl findDescriptor() {
        return (DescriptorImpl)Jenkins.getInstance().getDescriptorOrDie(GlobalSettings.class);
    }

    /**
     * Returns the global settings of the static analysis plug-ins.
     *
     * @return the global settings
     */
    public static Settings instance() {
        return findDescriptor();
    }

    /**
     * Descriptor for {@link GlobalSettings}.
     *
     * @author Ulli Hafner
     */
    @Extension
    public static class DescriptorImpl extends Descriptor<GlobalSettings> implements Settings {
        private Boolean isQuiet;
        private Boolean failOnCorrupt;

        private final CopyOnWriteList<AnalysisConfiguration> configurations = new CopyOnWriteList<AnalysisConfiguration>();

        @Override
        public String getDisplayName() {
            return StringUtils.EMPTY;
        }

        /**
         * Creates a new instance of {@link GlobalSettings.DescriptorImpl}.
         */
        public DescriptorImpl() {
            this(true);

            load();
        }

        /**
         * Creates a new instance of {@link GlobalSettings.DescriptorImpl}.
         *
         * @param loadFromDisk
         *            determines whether the configurations should be loaded from disk
         */
        public DescriptorImpl(final boolean loadFromDisk) {
            super();

            if (loadFromDisk) {
                load();
            }
        }

        @Override
        public boolean configure(final StaplerRequest req, final JSONObject json) throws FormException {
            bind(req, json);
            save();

            return true;
        }

        /**
         * Converts the array of configurations to a list of {@link AnalysisConfiguration} instances. Then the remaining
         * values will be injected into this {@link Settings} instance.
         *
         * @param req
         *            the request
         * @param json
         *            the JSON representation
         */
        void bind(final StaplerRequest req, final JSONObject json) {
            Object array = json.remove("configurations");
            if (array instanceof JSONArray) {
                bindConfigurations(req, (JSONArray)array);
            }
            else if (array instanceof JSONObject) {
                bindConfiguration(req, (JSONObject)array);
            }
            req.bindJSON(this, json);
        }

        private void bindConfiguration(final StaplerRequest req, final JSONObject array) {
            JSONObject flat = PluginDescriptor.convertHierarchicalFormData(array);
            configurations.replaceBy(req.bindJSON(AnalysisConfiguration.class, flat));
        }

        private void bindConfigurations(final StaplerRequest req, final JSONArray array) {
            configurations.clear();
            for (int i = 0; i < array.size(); i++) {
                JSONObject flat = PluginDescriptor.convertHierarchicalFormData(array.getJSONObject(i));
                configurations.add(req.bindJSON(AnalysisConfiguration.class, flat));
            }
        }

        @Override
        public AnalysisConfiguration[] getConfigurations() {
            return configurations.toArray(new AnalysisConfiguration[configurations.size()]);
        }

        @Override
        public Boolean getQuietMode() {
            return getValidBoolean(isQuiet);
        }

        /**
         * Sets the value of the quiet boolean property.
         *
         * @param value
         *            the value to set
         */
        public void setQuietMode(final Boolean value) {
            isQuiet = value;
        }

        @Override
        public Boolean getFailOnCorrupt() {
            return getValidBoolean(failOnCorrupt);
        }

        /**
         * Sets the value of the failOnCorrupt boolean property.
         *
         * @param value
         *            the value to set
         */
        public void setFailOnCorrupt(final Boolean value) {
            failOnCorrupt = value;
        }

        private Boolean getValidBoolean(final Boolean value) {
            return value == null ? Boolean.FALSE : value;
        }
    }
}
