package hudson.plugins.analysis.util.model;


public interface PriorityInt {


    /**
     * Returns a localized description of this annotation type.
     *
     * @return localized description of this annotation type
     */
    public String getLocalizedString();

    /**
     * Returns a long localized description of this annotation type.
     *
     * @return long localized description of this annotation type
     */
    public String getLongLocalizedString();

    /**
     * Returns the name of the Priority
     *
     * @return name of the Priority
     */
    public String getPriorityName();


}

