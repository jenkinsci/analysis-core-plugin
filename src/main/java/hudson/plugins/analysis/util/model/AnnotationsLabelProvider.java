package hudson.plugins.analysis.util.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import hudson.plugins.analysis.Messages;

/**
 * Provides localized labels for the different categories of annotations stored in an annotation container.
 *
 * @author Ullrich Hafner
 */
public class AnnotationsLabelProvider implements Serializable {
    private static final long serialVersionUID = -4942733658741742463L;

    private final String packageLabel;

    private final Map<String, String> priorityLabels = new HashMap<String, String>();

    public AnnotationsLabelProvider() {
        this(Messages.PackageDetail_title());
    }

    public AnnotationsLabelProvider(final String packageLabel) {
        this.packageLabel = packageLabel;
        initializePriorityLabels();
    }

    /**
     * This can be overridden to provide custom labels for Priority tabs
     *
     */
    protected void initializePriorityLabels(){
        priorityLabels.put(Priority.HIGH.getPriorityName(), Messages.BuildResult_Tab_High());
        priorityLabels.put(Priority.NORMAL.getPriorityName(), Messages.BuildResult_Tab_Normal());
        priorityLabels.put(Priority.LOW.getPriorityName(), Messages.BuildResult_Tab_Low());
    }

    public String getModules() {
        return Messages.BuildResult_Tab_Modules();
    }

    public String getWarnings() {
        return Messages.BuildResult_Tab_Warnings();
    }

    public String getPackages() { return packageLabel; }

    public String getFiles() {
        return Messages.BuildResult_Tab_Files();
    }

    public String getCategories() {
        return Messages.BuildResult_Tab_Categories();
    }

    public String getTypes() {
        return Messages.BuildResult_Tab_Types();
    }

    public String getDetails() {
        return Messages.BuildResult_Tab_Details();
    }

    public String getNew() {
        return Messages.BuildResult_Tab_New();
    }

    public String getFixed() {
        return Messages.BuildResult_Tab_Fixed();
    }

    public String getPriorityLabel(final String priority){
        return priorityLabels.get(priority);
    }


    public String getHigh() {
        return priorityLabels.get(Priority.HIGH.getPriorityName());
    }

    public String getNormal() {
        return priorityLabels.get(Priority.NORMAL.getPriorityName());
    }

    public String getLow() {
        return priorityLabels.get(Priority.LOW.getPriorityName());
    }
}
