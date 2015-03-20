package hudson.plugins.ast.specific;

/**
 * Depicts the warning types which need to include the name e.g. the methodname for calculating the hashcode.
 *
 * @author Christian M&ouml;stl
 */
public enum WarningType {
    /** The local final variablename. */
    LOCALFINALVARIABLENAME,

    /** The local variablename. */
    LOCALVARIABLENAME,

    /** The methodname. */
    METHODNAME,

    /** The packagename. */
    PACKAGENAME,

    /** The parametername. */
    PARAMETERNAME,

    /** the static variablename. */
    STATICVARIABLENAME,

    /** The typename. */
    TYPENAME,

    /** The classtype parametername. */
    CLASSTYPEPARAMETERNAME,

    /** The methodtype parametername. */
    METHODTYPEPARAMETERNAME,

    /** The length of the innerclass. */
    ANONINNERLENGTH;
}