package hudson.plugins.analysis.util.model;

/**
 * Custom enums should also implement the same static classes and methods as in the Priority enum.
 */
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


    /**
     * Returns the css style color for this priority
     *
     * @return the css color string
     */
    public String getCssColor();

}

