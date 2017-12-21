// CHECKSTYLE:OFF

package hudson.plugins.analysis;

import org.jvnet.localizer.Localizable;
import org.jvnet.localizer.ResourceBundleHolder;


/**
 * Generated localization support class.
 * 
 */
@SuppressWarnings({
    "",
    "PMD",
    "all"
})
public class Messages {

    /**
     * The resource bundle reference
     * 
     */
    private final static ResourceBundleHolder holder = ResourceBundleHolder.get(Messages.class);

    /**
     * Key {@code ResultAction.OneNoHighScore}: {@code Still one day before
     * reaching the previous zero warnings highscore.}.
     * 
     * @return
     *     {@code Still one day before reaching the previous zero warnings
     *     highscore.}
     */
    public static String ResultAction_OneNoHighScore() {
        return holder.format("ResultAction.OneNoHighScore");
    }

    /**
     * Key {@code ResultAction.OneNoHighScore}: {@code Still one day before
     * reaching the previous zero warnings highscore.}.
     * 
     * @return
     *     {@code Still one day before reaching the previous zero warnings
     *     highscore.}
     */
    public static Localizable _ResultAction_OneNoHighScore() {
        return new Localizable(holder, "ResultAction.OneNoHighScore");
    }

    /**
     * Key {@code PackageDetail.title}: {@code Packages}.
     * 
     * @return
     *     {@code Packages}
     */
    public static String PackageDetail_title() {
        return holder.format("PackageDetail.title");
    }

    /**
     * Key {@code PackageDetail.title}: {@code Packages}.
     * 
     * @return
     *     {@code Packages}
     */
    public static Localizable _PackageDetail_title() {
        return new Localizable(holder, "PackageDetail.title");
    }

    /**
     * Key {@code NormalPriority}: {@code Normal Priority}.
     * 
     * @return
     *     {@code Normal Priority}
     */
    public static String NormalPriority() {
        return holder.format("NormalPriority");
    }

    /**
     * Key {@code NormalPriority}: {@code Normal Priority}.
     * 
     * @return
     *     {@code Normal Priority}
     */
    public static Localizable _NormalPriority() {
        return new Localizable(holder, "NormalPriority");
    }

    /**
     * Key {@code ResultAction.OneWarning}: {@code 1 warning}.
     * 
     * @return
     *     {@code 1 warning}
     */
    public static String ResultAction_OneWarning() {
        return holder.format("ResultAction.OneWarning");
    }

    /**
     * Key {@code ResultAction.OneWarning}: {@code 1 warning}.
     * 
     * @return
     *     {@code 1 warning}
     */
    public static Localizable _ResultAction_OneWarning() {
        return new Localizable(holder, "ResultAction.OneWarning");
    }

    /**
     * Key {@code FixedWarningsDetail.Name}: {@code Fixed Warnings}.
     * 
     * @return
     *     {@code Fixed Warnings}
     */
    public static String FixedWarningsDetail_Name() {
        return holder.format("FixedWarningsDetail.Name");
    }

    /**
     * Key {@code FixedWarningsDetail.Name}: {@code Fixed Warnings}.
     * 
     * @return
     *     {@code Fixed Warnings}
     */
    public static Localizable _FixedWarningsDetail_Name() {
        return new Localizable(holder, "FixedWarningsDetail.Name");
    }

    /**
     * Key {@code BuildResultEvaluator.unstable.one.new.priority}: {@code <a
     * href="{3}">1 new warning</a> of <a href="{4}">priority {2}</a> exceeds
     * the threshold of {0} by {1}}.
     * 
     * @param arg3
     *      4th format parameter, {@code {3}}, as {@link String#valueOf(Object)}.
     * @param arg2
     *      3rd format parameter, {@code {2}}, as {@link String#valueOf(Object)}.
     * @param arg4
     *      5th format parameter, {@code {4}}, as {@link String#valueOf(Object)}.
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code <a href="{3}">1 new warning</a> of <a href="{4}">priority
     *     {2}</a> exceeds the threshold of {0} by {1}}
     */
    public static String BuildResultEvaluator_unstable_one_new_priority(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
        return holder.format("BuildResultEvaluator.unstable.one.new.priority", arg0, arg1, arg2, arg3, arg4);
    }

    /**
     * Key {@code BuildResultEvaluator.unstable.one.new.priority}: {@code <a
     * href="{3}">1 new warning</a> of <a href="{4}">priority {2}</a> exceeds
     * the threshold of {0} by {1}}.
     * 
     * @param arg3
     *      4th format parameter, {@code {3}}, as {@link String#valueOf(Object)}.
     * @param arg2
     *      3rd format parameter, {@code {2}}, as {@link String#valueOf(Object)}.
     * @param arg4
     *      5th format parameter, {@code {4}}, as {@link String#valueOf(Object)}.
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code <a href="{3}">1 new warning</a> of <a href="{4}">priority
     *     {2}</a> exceeds the threshold of {0} by {1}}
     */
    public static Localizable _BuildResultEvaluator_unstable_one_new_priority(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
        return new Localizable(holder, "BuildResultEvaluator.unstable.one.new.priority", arg0, arg1, arg2, arg3, arg4);
    }

    /**
     * Key {@code ResultAction.OneNewWarning}: {@code 1 new warning}.
     * 
     * @return
     *     {@code 1 new warning}
     */
    public static String ResultAction_OneNewWarning() {
        return holder.format("ResultAction.OneNewWarning");
    }

    /**
     * Key {@code ResultAction.OneNewWarning}: {@code 1 new warning}.
     * 
     * @return
     *     {@code 1 new warning}
     */
    public static Localizable _ResultAction_OneNewWarning() {
        return new Localizable(holder, "ResultAction.OneNewWarning");
    }

    /**
     * Key {@code FilesParser.Error.EmptyFile}: {@code Skipping file {0} of
     * module {1} because it''s empty.}.
     * 
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code Skipping file {0} of module {1} because it''s empty.}
     */
    public static String FilesParser_Error_EmptyFile(Object arg0, Object arg1) {
        return holder.format("FilesParser.Error.EmptyFile", arg0, arg1);
    }

    /**
     * Key {@code FilesParser.Error.EmptyFile}: {@code Skipping file {0} of
     * module {1} because it''s empty.}.
     * 
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code Skipping file {0} of module {1} because it''s empty.}
     */
    public static Localizable _FilesParser_Error_EmptyFile(Object arg0, Object arg1) {
        return new Localizable(holder, "FilesParser.Error.EmptyFile", arg0, arg1);
    }

    /**
     * Key {@code UserGraphConfiguration.Name}: {@code Configure User Trend
     * Graph}.
     * 
     * @return
     *     {@code Configure User Trend Graph}
     */
    public static String UserGraphConfiguration_Name() {
        return holder.format("UserGraphConfiguration.Name");
    }

    /**
     * Key {@code UserGraphConfiguration.Name}: {@code Configure User Trend
     * Graph}.
     * 
     * @return
     *     {@code Configure User Trend Graph}
     */
    public static Localizable _UserGraphConfiguration_Name() {
        return new Localizable(holder, "UserGraphConfiguration.Name");
    }

    /**
     * Key {@code LowPriority}: {@code Low Priority}.
     * 
     * @return
     *     {@code Low Priority}
     */
    public static String LowPriority() {
        return holder.format("LowPriority");
    }

    /**
     * Key {@code LowPriority}: {@code Low Priority}.
     * 
     * @return
     *     {@code Low Priority}
     */
    public static Localizable _LowPriority() {
        return new Localizable(holder, "LowPriority");
    }

    /**
     * Key {@code ResultAction.MultipleWarnings}: {@code {0} warnings}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code {0} warnings}
     */
    public static String ResultAction_MultipleWarnings(Object arg0) {
        return holder.format("ResultAction.MultipleWarnings", arg0);
    }

    /**
     * Key {@code ResultAction.MultipleWarnings}: {@code {0} warnings}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code {0} warnings}
     */
    public static Localizable _ResultAction_MultipleWarnings(Object arg0) {
        return new Localizable(holder, "ResultAction.MultipleWarnings", arg0);
    }

    /**
     * Key {@code ResultAction.MultipleHighScore}: {@code New zero warnings
     * highscore: no warnings for {0} days!}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code New zero warnings highscore: no warnings for {0} days!}
     */
    public static String ResultAction_MultipleHighScore(Object arg0) {
        return holder.format("ResultAction.MultipleHighScore", arg0);
    }

    /**
     * Key {@code ResultAction.MultipleHighScore}: {@code New zero warnings
     * highscore: no warnings for {0} days!}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code New zero warnings highscore: no warnings for {0} days!}
     */
    public static Localizable _ResultAction_MultipleHighScore(Object arg0) {
        return new Localizable(holder, "ResultAction.MultipleHighScore", arg0);
    }

    /**
     * Key {@code ResultAction.OneFixedWarning}: {@code 1 fixed warning}.
     * 
     * @return
     *     {@code 1 fixed warning}
     */
    public static String ResultAction_OneFixedWarning() {
        return holder.format("ResultAction.OneFixedWarning");
    }

    /**
     * Key {@code ResultAction.OneFixedWarning}: {@code 1 fixed warning}.
     * 
     * @return
     *     {@code 1 fixed warning}
     */
    public static Localizable _ResultAction_OneFixedWarning() {
        return new Localizable(holder, "ResultAction.OneFixedWarning");
    }

    /**
     * Key {@code Trend.type.priority}: {@code Priority distribution of all
     * warnings}.
     * 
     * @return
     *     {@code Priority distribution of all warnings}
     */
    public static String Trend_type_priority() {
        return holder.format("Trend.type.priority");
    }

    /**
     * Key {@code Trend.type.priority}: {@code Priority distribution of all
     * warnings}.
     * 
     * @return
     *     {@code Priority distribution of all warnings}
     */
    public static Localizable _Trend_type_priority() {
        return new Localizable(holder, "Trend.type.priority");
    }

    /**
     * Key {@code BuildResult.Tab.Low}: {@code Low}.
     * 
     * @return
     *     {@code Low}
     */
    public static String BuildResult_Tab_Low() {
        return holder.format("BuildResult.Tab.Low");
    }

    /**
     * Key {@code BuildResult.Tab.Low}: {@code Low}.
     * 
     * @return
     *     {@code Low}
     */
    public static Localizable _BuildResult_Tab_Low() {
        return new Localizable(holder, "BuildResult.Tab.Low");
    }

    /**
     * Key {@code BuildResult.Tab.Modules}: {@code Modules}.
     * 
     * @return
     *     {@code Modules}
     */
    public static String BuildResult_Tab_Modules() {
        return holder.format("BuildResult.Tab.Modules");
    }

    /**
     * Key {@code BuildResult.Tab.Modules}: {@code Modules}.
     * 
     * @return
     *     {@code Modules}
     */
    public static Localizable _BuildResult_Tab_Modules() {
        return new Localizable(holder, "BuildResult.Tab.Modules");
    }

    /**
     * Key {@code ResultAction.NoWarningsSince}: {@code No warnings since
     * build {0}.}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code No warnings since build {0}.}
     */
    public static String ResultAction_NoWarningsSince(Object arg0) {
        return holder.format("ResultAction.NoWarningsSince", arg0);
    }

    /**
     * Key {@code ResultAction.NoWarningsSince}: {@code No warnings since
     * build {0}.}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code No warnings since build {0}.}
     */
    public static Localizable _ResultAction_NoWarningsSince(Object arg0) {
        return new Localizable(holder, "ResultAction.NoWarningsSince", arg0);
    }

    /**
     * Key {@code BuildResult.Tab.Normal}: {@code Normal}.
     * 
     * @return
     *     {@code Normal}
     */
    public static String BuildResult_Tab_Normal() {
        return holder.format("BuildResult.Tab.Normal");
    }

    /**
     * Key {@code BuildResult.Tab.Normal}: {@code Normal}.
     * 
     * @return
     *     {@code Normal}
     */
    public static Localizable _BuildResult_Tab_Normal() {
        return new Localizable(holder, "BuildResult.Tab.Normal");
    }

    /**
     * Key {@code Trend.type.fixed}: {@code Distribution of new and fixed
     * warnings}.
     * 
     * @return
     *     {@code Distribution of new and fixed warnings}
     */
    public static String Trend_type_fixed() {
        return holder.format("Trend.type.fixed");
    }

    /**
     * Key {@code Trend.type.fixed}: {@code Distribution of new and fixed
     * warnings}.
     * 
     * @return
     *     {@code Distribution of new and fixed warnings}
     */
    public static Localizable _Trend_type_fixed() {
        return new Localizable(holder, "Trend.type.fixed");
    }

    /**
     * Key {@code Priority.Normal}: {@code Normal}.
     * 
     * @return
     *     {@code Normal}
     */
    public static String Priority_Normal() {
        return holder.format("Priority.Normal");
    }

    /**
     * Key {@code Priority.Normal}: {@code Normal}.
     * 
     * @return
     *     {@code Normal}
     */
    public static Localizable _Priority_Normal() {
        return new Localizable(holder, "Priority.Normal");
    }

    /**
     * Key {@code NamespaceDetail.header}: {@code Namespace}.
     * 
     * @return
     *     {@code Namespace}
     */
    public static String NamespaceDetail_header() {
        return holder.format("NamespaceDetail.header");
    }

    /**
     * Key {@code NamespaceDetail.header}: {@code Namespace}.
     * 
     * @return
     *     {@code Namespace}
     */
    public static Localizable _NamespaceDetail_header() {
        return new Localizable(holder, "NamespaceDetail.header");
    }

    /**
     * Key {@code ResultAction.MultipleNewWarnings}: {@code {0} new
     * warnings}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code {0} new warnings}
     */
    public static String ResultAction_MultipleNewWarnings(Object arg0) {
        return holder.format("ResultAction.MultipleNewWarnings", arg0);
    }

    /**
     * Key {@code ResultAction.MultipleNewWarnings}: {@code {0} new
     * warnings}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code {0} new warnings}
     */
    public static Localizable _ResultAction_MultipleNewWarnings(Object arg0) {
        return new Localizable(holder, "ResultAction.MultipleNewWarnings", arg0);
    }

    /**
     * Key {@code ResultAction.OneHighScore}: {@code New zero warnings
     * highscore: no warnings since yesterday!}.
     * 
     * @return
     *     {@code New zero warnings highscore: no warnings since yesterday!}
     */
    public static String ResultAction_OneHighScore() {
        return holder.format("ResultAction.OneHighScore");
    }

    /**
     * Key {@code ResultAction.OneHighScore}: {@code New zero warnings
     * highscore: no warnings since yesterday!}.
     * 
     * @return
     *     {@code New zero warnings highscore: no warnings since yesterday!}
     */
    public static Localizable _ResultAction_OneHighScore() {
        return new Localizable(holder, "ResultAction.OneHighScore");
    }

    /**
     * Key {@code BuildResultEvaluator.unstable.one.new}: {@code <a
     * href="{2}">1 new warning</a> exceeds the threshold of {0} by {1}}.
     * 
     * @param arg2
     *      3rd format parameter, {@code {2}}, as {@link String#valueOf(Object)}.
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code <a href="{2}">1 new warning</a> exceeds the threshold of {0} by
     *     {1}}
     */
    public static String BuildResultEvaluator_unstable_one_new(Object arg0, Object arg1, Object arg2) {
        return holder.format("BuildResultEvaluator.unstable.one.new", arg0, arg1, arg2);
    }

    /**
     * Key {@code BuildResultEvaluator.unstable.one.new}: {@code <a
     * href="{2}">1 new warning</a> exceeds the threshold of {0} by {1}}.
     * 
     * @param arg2
     *      3rd format parameter, {@code {2}}, as {@link String#valueOf(Object)}.
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code <a href="{2}">1 new warning</a> exceeds the threshold of {0} by
     *     {1}}
     */
    public static Localizable _BuildResultEvaluator_unstable_one_new(Object arg0, Object arg1, Object arg2) {
        return new Localizable(holder, "BuildResultEvaluator.unstable.one.new", arg0, arg1, arg2);
    }

    /**
     * Key {@code Trend.PriorityNormal}: {@code (normal priority)}.
     * 
     * @return
     *     {@code (normal priority)}
     */
    public static String Trend_PriorityNormal() {
        return holder.format("Trend.PriorityNormal");
    }

    /**
     * Key {@code Trend.PriorityNormal}: {@code (normal priority)}.
     * 
     * @return
     *     {@code (normal priority)}
     */
    public static Localizable _Trend_PriorityNormal() {
        return new Localizable(holder, "Trend.PriorityNormal");
    }

    /**
     * Key {@code BuildResult.Tab.Types}: {@code Types}.
     * 
     * @return
     *     {@code Types}
     */
    public static String BuildResult_Tab_Types() {
        return holder.format("BuildResult.Tab.Types");
    }

    /**
     * Key {@code BuildResult.Tab.Types}: {@code Types}.
     * 
     * @return
     *     {@code Types}
     */
    public static Localizable _BuildResult_Tab_Types() {
        return new Localizable(holder, "BuildResult.Tab.Types");
    }

    /**
     * Key {@code ResultAction.SuccessfulOneHighScore}: {@code New highscore:
     * only successful builds since yesterday!}.
     * 
     * @return
     *     {@code New highscore: only successful builds since yesterday!}
     */
    public static String ResultAction_SuccessfulOneHighScore() {
        return holder.format("ResultAction.SuccessfulOneHighScore");
    }

    /**
     * Key {@code ResultAction.SuccessfulOneHighScore}: {@code New highscore:
     * only successful builds since yesterday!}.
     * 
     * @return
     *     {@code New highscore: only successful builds since yesterday!}
     */
    public static Localizable _ResultAction_SuccessfulOneHighScore() {
        return new Localizable(holder, "ResultAction.SuccessfulOneHighScore");
    }

    /**
     * Key {@code Trend.Fixed}: {@code (fixed)}.
     * 
     * @return
     *     {@code (fixed)}
     */
    public static String Trend_Fixed() {
        return holder.format("Trend.Fixed");
    }

    /**
     * Key {@code Trend.Fixed}: {@code (fixed)}.
     * 
     * @return
     *     {@code (fixed)}
     */
    public static Localizable _Trend_Fixed() {
        return new Localizable(holder, "Trend.Fixed");
    }

    /**
     * Key {@code FieldValidator.Error.Threshold}: {@code Threshold must be
     * an integer value greater or equal 0.}.
     * 
     * @return
     *     {@code Threshold must be an integer value greater or equal 0.}
     */
    public static String FieldValidator_Error_Threshold() {
        return holder.format("FieldValidator.Error.Threshold");
    }

    /**
     * Key {@code FieldValidator.Error.Threshold}: {@code Threshold must be
     * an integer value greater or equal 0.}.
     * 
     * @return
     *     {@code Threshold must be an integer value greater or equal 0.}
     */
    public static Localizable _FieldValidator_Error_Threshold() {
        return new Localizable(holder, "FieldValidator.Error.Threshold");
    }

    /**
     * Key {@code BuildResult.Tab.Fixed}: {@code Fixed}.
     * 
     * @return
     *     {@code Fixed}
     */
    public static String BuildResult_Tab_Fixed() {
        return holder.format("BuildResult.Tab.Fixed");
    }

    /**
     * Key {@code BuildResult.Tab.Fixed}: {@code Fixed}.
     * 
     * @return
     *     {@code Fixed}
     */
    public static Localizable _BuildResult_Tab_Fixed() {
        return new Localizable(holder, "BuildResult.Tab.Fixed");
    }

    /**
     * Key {@code BuildResultEvaluator.unstable.new}: {@code <a
     * href="{3}">{0} new warnings</a> exceed the threshold of {1} by {2}}.
     * 
     * @param arg3
     *      4th format parameter, {@code {3}}, as {@link String#valueOf(Object)}.
     * @param arg2
     *      3rd format parameter, {@code {2}}, as {@link String#valueOf(Object)}.
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code <a href="{3}">{0} new warnings</a> exceed the threshold of {1}
     *     by {2}}
     */
    public static String BuildResultEvaluator_unstable_new(Object arg0, Object arg1, Object arg2, Object arg3) {
        return holder.format("BuildResultEvaluator.unstable.new", arg0, arg1, arg2, arg3);
    }

    /**
     * Key {@code BuildResultEvaluator.unstable.new}: {@code <a
     * href="{3}">{0} new warnings</a> exceed the threshold of {1} by {2}}.
     * 
     * @param arg3
     *      4th format parameter, {@code {3}}, as {@link String#valueOf(Object)}.
     * @param arg2
     *      3rd format parameter, {@code {2}}, as {@link String#valueOf(Object)}.
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code <a href="{3}">{0} new warnings</a> exceed the threshold of {1}
     *     by {2}}
     */
    public static Localizable _BuildResultEvaluator_unstable_new(Object arg0, Object arg1, Object arg2, Object arg3) {
        return new Localizable(holder, "BuildResultEvaluator.unstable.new", arg0, arg1, arg2, arg3);
    }

    /**
     * Key {@code CategoryDetail.header}: {@code Category}.
     * 
     * @return
     *     {@code Category}
     */
    public static String CategoryDetail_header() {
        return holder.format("CategoryDetail.header");
    }

    /**
     * Key {@code CategoryDetail.header}: {@code Category}.
     * 
     * @return
     *     {@code Category}
     */
    public static Localizable _CategoryDetail_header() {
        return new Localizable(holder, "CategoryDetail.header");
    }

    /**
     * Key {@code FilesParser.Error.NoPermission}: {@code Skipping file {0}
     * of module {1} because Jenkins has no permission to read the file.}.
     * 
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code Skipping file {0} of module {1} because Jenkins has no
     *     permission to read the file.}
     */
    public static String FilesParser_Error_NoPermission(Object arg0, Object arg1) {
        return holder.format("FilesParser.Error.NoPermission", arg0, arg1);
    }

    /**
     * Key {@code FilesParser.Error.NoPermission}: {@code Skipping file {0}
     * of module {1} because Jenkins has no permission to read the file.}.
     * 
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code Skipping file {0} of module {1} because Jenkins has no
     *     permission to read the file.}
     */
    public static Localizable _FilesParser_Error_NoPermission(Object arg0, Object arg1) {
        return new Localizable(holder, "FilesParser.Error.NoPermission", arg0, arg1);
    }

    /**
     * Key {@code TypeDetail.header}: {@code Type}.
     * 
     * @return
     *     {@code Type}
     */
    public static String TypeDetail_header() {
        return holder.format("TypeDetail.header");
    }

    /**
     * Key {@code TypeDetail.header}: {@code Type}.
     * 
     * @return
     *     {@code Type}
     */
    public static Localizable _TypeDetail_header() {
        return new Localizable(holder, "TypeDetail.header");
    }

    /**
     * Key {@code Trend.PriorityHigh}: {@code (high priority)}.
     * 
     * @return
     *     {@code (high priority)}
     */
    public static String Trend_PriorityHigh() {
        return holder.format("Trend.PriorityHigh");
    }

    /**
     * Key {@code Trend.PriorityHigh}: {@code (high priority)}.
     * 
     * @return
     *     {@code (high priority)}
     */
    public static Localizable _Trend_PriorityHigh() {
        return new Localizable(holder, "Trend.PriorityHigh");
    }

    /**
     * Key {@code BuildResultEvaluator.unstable.all.priority}: {@code <a
     * href="{4}">{0} warnings</a> of <a href="{5}">priority {3}</a> exceed
     * the threshold of {1} by {2}}.
     * 
     * @param arg3
     *      4th format parameter, {@code {3}}, as {@link String#valueOf(Object)}.
     * @param arg2
     *      3rd format parameter, {@code {2}}, as {@link String#valueOf(Object)}.
     * @param arg5
     *      6th format parameter, {@code {5}}, as {@link String#valueOf(Object)}.
     * @param arg4
     *      5th format parameter, {@code {4}}, as {@link String#valueOf(Object)}.
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code <a href="{4}">{0} warnings</a> of <a href="{5}">priority
     *     {3}</a> exceed the threshold of {1} by {2}}
     */
    public static String BuildResultEvaluator_unstable_all_priority(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        return holder.format("BuildResultEvaluator.unstable.all.priority", arg0, arg1, arg2, arg3, arg4, arg5);
    }

    /**
     * Key {@code BuildResultEvaluator.unstable.all.priority}: {@code <a
     * href="{4}">{0} warnings</a> of <a href="{5}">priority {3}</a> exceed
     * the threshold of {1} by {2}}.
     * 
     * @param arg3
     *      4th format parameter, {@code {3}}, as {@link String#valueOf(Object)}.
     * @param arg2
     *      3rd format parameter, {@code {2}}, as {@link String#valueOf(Object)}.
     * @param arg5
     *      6th format parameter, {@code {5}}, as {@link String#valueOf(Object)}.
     * @param arg4
     *      5th format parameter, {@code {4}}, as {@link String#valueOf(Object)}.
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code <a href="{4}">{0} warnings</a> of <a href="{5}">priority
     *     {3}</a> exceed the threshold of {1} by {2}}
     */
    public static Localizable _BuildResultEvaluator_unstable_all_priority(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        return new Localizable(holder, "BuildResultEvaluator.unstable.all.priority", arg0, arg1, arg2, arg3, arg4, arg5);
    }

    /**
     * Key {@code ResultAction.SuccessfulMultipleNoHighScore}: {@code Still
     * {0} days before reaching the previous successful builds highscore.}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code Still {0} days before reaching the previous successful builds
     *     highscore.}
     */
    public static String ResultAction_SuccessfulMultipleNoHighScore(Object arg0) {
        return holder.format("ResultAction.SuccessfulMultipleNoHighScore", arg0);
    }

    /**
     * Key {@code ResultAction.SuccessfulMultipleNoHighScore}: {@code Still
     * {0} days before reaching the previous successful builds highscore.}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code Still {0} days before reaching the previous successful builds
     *     highscore.}
     */
    public static Localizable _ResultAction_SuccessfulMultipleNoHighScore(Object arg0) {
        return new Localizable(holder, "ResultAction.SuccessfulMultipleNoHighScore", arg0);
    }

    /**
     * Key {@code ResultAction.MultipleNoHighScore}: {@code Still {0} days
     * before reaching the previous zero warnings highscore.}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code Still {0} days before reaching the previous zero warnings
     *     highscore.}
     */
    public static String ResultAction_MultipleNoHighScore(Object arg0) {
        return holder.format("ResultAction.MultipleNoHighScore", arg0);
    }

    /**
     * Key {@code ResultAction.MultipleNoHighScore}: {@code Still {0} days
     * before reaching the previous zero warnings highscore.}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code Still {0} days before reaching the previous zero warnings
     *     highscore.}
     */
    public static Localizable _ResultAction_MultipleNoHighScore(Object arg0) {
        return new Localizable(holder, "ResultAction.MultipleNoHighScore", arg0);
    }

    /**
     * Key {@code Trend.type.authors}: {@code Warnings by author}.
     * 
     * @return
     *     {@code Warnings by author}
     */
    public static String Trend_type_authors() {
        return holder.format("Trend.type.authors");
    }

    /**
     * Key {@code Trend.type.authors}: {@code Warnings by author}.
     * 
     * @return
     *     {@code Warnings by author}
     */
    public static Localizable _Trend_type_authors() {
        return new Localizable(holder, "Trend.type.authors");
    }

    /**
     * Key {@code PackageDetail.header}: {@code Package}.
     * 
     * @return
     *     {@code Package}
     */
    public static String PackageDetail_header() {
        return holder.format("PackageDetail.header");
    }

    /**
     * Key {@code PackageDetail.header}: {@code Package}.
     * 
     * @return
     *     {@code Package}
     */
    public static Localizable _PackageDetail_header() {
        return new Localizable(holder, "PackageDetail.header");
    }

    /**
     * Key {@code BuildResult.Tab.Origin}: {@code Origin}.
     * 
     * @return
     *     {@code Origin}
     */
    public static String BuildResult_Tab_Origin() {
        return holder.format("BuildResult.Tab.Origin");
    }

    /**
     * Key {@code BuildResult.Tab.Origin}: {@code Origin}.
     * 
     * @return
     *     {@code Origin}
     */
    public static Localizable _BuildResult_Tab_Origin() {
        return new Localizable(holder, "BuildResult.Tab.Origin");
    }

    /**
     * Key {@code Trend.type.difference}: {@code Difference between new and
     * fixed warnings (cumulative)}.
     * 
     * @return
     *     {@code Difference between new and fixed warnings (cumulative)}
     */
    public static String Trend_type_difference() {
        return holder.format("Trend.type.difference");
    }

    /**
     * Key {@code Trend.type.difference}: {@code Difference between new and
     * fixed warnings (cumulative)}.
     * 
     * @return
     *     {@code Difference between new and fixed warnings (cumulative)}
     */
    public static Localizable _Trend_type_difference() {
        return new Localizable(holder, "Trend.type.difference");
    }

    /**
     * Key {@code Trend.type.none}: {@code No trend graph}.
     * 
     * @return
     *     {@code No trend graph}
     */
    public static String Trend_type_none() {
        return holder.format("Trend.type.none");
    }

    /**
     * Key {@code Trend.type.none}: {@code No trend graph}.
     * 
     * @return
     *     {@code No trend graph}
     */
    public static Localizable _Trend_type_none() {
        return new Localizable(holder, "Trend.type.none");
    }

    /**
     * Key {@code FilesParser.Error.Exception}: {@code Parsing of file {0}
     * failed due to an exception:}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code Parsing of file {0} failed due to an exception:}
     */
    public static String FilesParser_Error_Exception(Object arg0) {
        return holder.format("FilesParser.Error.Exception", arg0);
    }

    /**
     * Key {@code FilesParser.Error.Exception}: {@code Parsing of file {0}
     * failed due to an exception:}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code Parsing of file {0} failed due to an exception:}
     */
    public static Localizable _FilesParser_Error_Exception(Object arg0) {
        return new Localizable(holder, "FilesParser.Error.Exception", arg0);
    }

    /**
     * Key {@code Trend.New}: {@code (new)}.
     * 
     * @return
     *     {@code (new)}
     */
    public static String Trend_New() {
        return holder.format("Trend.New");
    }

    /**
     * Key {@code Trend.New}: {@code (new)}.
     * 
     * @return
     *     {@code (new)}
     */
    public static Localizable _Trend_New() {
        return new Localizable(holder, "Trend.New");
    }

    /**
     * Key {@code ResultAction.SuccessfulMultipleHighScore}: {@code New
     * highscore: only successful builds for {0} days!}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code New highscore: only successful builds for {0} days!}
     */
    public static String ResultAction_SuccessfulMultipleHighScore(Object arg0) {
        return holder.format("ResultAction.SuccessfulMultipleHighScore", arg0);
    }

    /**
     * Key {@code ResultAction.SuccessfulMultipleHighScore}: {@code New
     * highscore: only successful builds for {0} days!}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code New highscore: only successful builds for {0} days!}
     */
    public static Localizable _ResultAction_SuccessfulMultipleHighScore(Object arg0) {
        return new Localizable(holder, "ResultAction.SuccessfulMultipleHighScore", arg0);
    }

    /**
     * Key {@code Trend.type.totals}: {@code Total number of warnings}.
     * 
     * @return
     *     {@code Total number of warnings}
     */
    public static String Trend_type_totals() {
        return holder.format("Trend.type.totals");
    }

    /**
     * Key {@code Trend.type.totals}: {@code Total number of warnings}.
     * 
     * @return
     *     {@code Total number of warnings}
     */
    public static Localizable _Trend_type_totals() {
        return new Localizable(holder, "Trend.type.totals");
    }

    /**
     * Key {@code BuildResult.Tab.Categories}: {@code Categories}.
     * 
     * @return
     *     {@code Categories}
     */
    public static String BuildResult_Tab_Categories() {
        return holder.format("BuildResult.Tab.Categories");
    }

    /**
     * Key {@code BuildResult.Tab.Categories}: {@code Categories}.
     * 
     * @return
     *     {@code Categories}
     */
    public static Localizable _BuildResult_Tab_Categories() {
        return new Localizable(holder, "BuildResult.Tab.Categories");
    }

    /**
     * Key {@code Author.NoResult}: {@code Unknown authors}.
     * 
     * @return
     *     {@code Unknown authors}
     */
    public static String Author_NoResult() {
        return holder.format("Author.NoResult");
    }

    /**
     * Key {@code Author.NoResult}: {@code Unknown authors}.
     * 
     * @return
     *     {@code Unknown authors}
     */
    public static Localizable _Author_NoResult() {
        return new Localizable(holder, "Author.NoResult");
    }

    /**
     * Key {@code BuildResultEvaluator.unstable.new.priority}: {@code <a
     * href="{4}">{0} new warnings</a> of <a href="{5}">priority {3}</a>
     * exceed the threshold of {1} by {2}}.
     * 
     * @param arg3
     *      4th format parameter, {@code {3}}, as {@link String#valueOf(Object)}.
     * @param arg2
     *      3rd format parameter, {@code {2}}, as {@link String#valueOf(Object)}.
     * @param arg5
     *      6th format parameter, {@code {5}}, as {@link String#valueOf(Object)}.
     * @param arg4
     *      5th format parameter, {@code {4}}, as {@link String#valueOf(Object)}.
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code <a href="{4}">{0} new warnings</a> of <a href="{5}">priority
     *     {3}</a> exceed the threshold of {1} by {2}}
     */
    public static String BuildResultEvaluator_unstable_new_priority(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        return holder.format("BuildResultEvaluator.unstable.new.priority", arg0, arg1, arg2, arg3, arg4, arg5);
    }

    /**
     * Key {@code BuildResultEvaluator.unstable.new.priority}: {@code <a
     * href="{4}">{0} new warnings</a> of <a href="{5}">priority {3}</a>
     * exceed the threshold of {1} by {2}}.
     * 
     * @param arg3
     *      4th format parameter, {@code {3}}, as {@link String#valueOf(Object)}.
     * @param arg2
     *      3rd format parameter, {@code {2}}, as {@link String#valueOf(Object)}.
     * @param arg5
     *      6th format parameter, {@code {5}}, as {@link String#valueOf(Object)}.
     * @param arg4
     *      5th format parameter, {@code {4}}, as {@link String#valueOf(Object)}.
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code <a href="{4}">{0} new warnings</a> of <a href="{5}">priority
     *     {3}</a> exceed the threshold of {1} by {2}}
     */
    public static Localizable _BuildResultEvaluator_unstable_new_priority(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        return new Localizable(holder, "BuildResultEvaluator.unstable.new.priority", arg0, arg1, arg2, arg3, arg4, arg5);
    }

    /**
     * Key {@code ResultAction.OneFile}: {@code from one analysis.}.
     * 
     * @return
     *     {@code from one analysis.}
     */
    public static String ResultAction_OneFile() {
        return holder.format("ResultAction.OneFile");
    }

    /**
     * Key {@code ResultAction.OneFile}: {@code from one analysis.}.
     * 
     * @return
     *     {@code from one analysis.}
     */
    public static Localizable _ResultAction_OneFile() {
        return new Localizable(holder, "ResultAction.OneFile");
    }

    /**
     * Key {@code Trend.type.health}: {@code Number of warnings colored
     * according to the health report thresholds}.
     * 
     * @return
     *     {@code Number of warnings colored according to the health report
     *     thresholds}
     */
    public static String Trend_type_health() {
        return holder.format("Trend.type.health");
    }

    /**
     * Key {@code Trend.type.health}: {@code Number of warnings colored
     * according to the health report thresholds}.
     * 
     * @return
     *     {@code Number of warnings colored according to the health report
     *     thresholds}
     */
    public static Localizable _Trend_type_health() {
        return new Localizable(holder, "Trend.type.health");
    }

    /**
     * Key {@code PathDetail.header}: {@code Source Folder}.
     * 
     * @return
     *     {@code Source Folder}
     */
    public static String PathDetail_header() {
        return holder.format("PathDetail.header");
    }

    /**
     * Key {@code PathDetail.header}: {@code Source Folder}.
     * 
     * @return
     *     {@code Source Folder}
     */
    public static Localizable _PathDetail_header() {
        return new Localizable(holder, "PathDetail.header");
    }

    /**
     * Key {@code Trend.PriorityLow}: {@code (low priority)}.
     * 
     * @return
     *     {@code (low priority)}
     */
    public static String Trend_PriorityLow() {
        return holder.format("Trend.PriorityLow");
    }

    /**
     * Key {@code Trend.PriorityLow}: {@code (low priority)}.
     * 
     * @return
     *     {@code (low priority)}
     */
    public static Localizable _Trend_PriorityLow() {
        return new Localizable(holder, "Trend.PriorityLow");
    }

    /**
     * Key {@code HighPriority}: {@code High Priority}.
     * 
     * @return
     *     {@code High Priority}
     */
    public static String HighPriority() {
        return holder.format("HighPriority");
    }

    /**
     * Key {@code HighPriority}: {@code High Priority}.
     * 
     * @return
     *     {@code High Priority}
     */
    public static Localizable _HighPriority() {
        return new Localizable(holder, "HighPriority");
    }

    /**
     * Key {@code NamespaceDetail.title}: {@code Namespaces}.
     * 
     * @return
     *     {@code Namespaces}
     */
    public static String NamespaceDetail_title() {
        return holder.format("NamespaceDetail.title");
    }

    /**
     * Key {@code NamespaceDetail.title}: {@code Namespaces}.
     * 
     * @return
     *     {@code Namespaces}
     */
    public static Localizable _NamespaceDetail_title() {
        return new Localizable(holder, "NamespaceDetail.title");
    }

    /**
     * Key {@code FieldValidator.Error.DefaultEncoding}: {@code Encoding must
     * be a supported encoding of the Java platform (see
     * java.nio.charset.Charset).}.
     * 
     * @return
     *     {@code Encoding must be a supported encoding of the Java platform (see
     *     java.nio.charset.Charset).}
     */
    public static String FieldValidator_Error_DefaultEncoding() {
        return holder.format("FieldValidator.Error.DefaultEncoding");
    }

    /**
     * Key {@code FieldValidator.Error.DefaultEncoding}: {@code Encoding must
     * be a supported encoding of the Java platform (see
     * java.nio.charset.Charset).}.
     * 
     * @return
     *     {@code Encoding must be a supported encoding of the Java platform (see
     *     java.nio.charset.Charset).}
     */
    public static Localizable _FieldValidator_Error_DefaultEncoding() {
        return new Localizable(holder, "FieldValidator.Error.DefaultEncoding");
    }

    /**
     * Key {@code BuildResultEvaluator.unstable.one.all}: {@code <a
     * href="{2}">1 warning</a> exceeds the threshold of {0} by {1}}.
     * 
     * @param arg2
     *      3rd format parameter, {@code {2}}, as {@link String#valueOf(Object)}.
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code <a href="{2}">1 warning</a> exceeds the threshold of {0} by
     *     {1}}
     */
    public static String BuildResultEvaluator_unstable_one_all(Object arg0, Object arg1, Object arg2) {
        return holder.format("BuildResultEvaluator.unstable.one.all", arg0, arg1, arg2);
    }

    /**
     * Key {@code BuildResultEvaluator.unstable.one.all}: {@code <a
     * href="{2}">1 warning</a> exceeds the threshold of {0} by {1}}.
     * 
     * @param arg2
     *      3rd format parameter, {@code {2}}, as {@link String#valueOf(Object)}.
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code <a href="{2}">1 warning</a> exceeds the threshold of {0} by
     *     {1}}
     */
    public static Localizable _BuildResultEvaluator_unstable_one_all(Object arg0, Object arg1, Object arg2) {
        return new Localizable(holder, "BuildResultEvaluator.unstable.one.all", arg0, arg1, arg2);
    }

    /**
     * Key {@code Priority.Low}: {@code Low}.
     * 
     * @return
     *     {@code Low}
     */
    public static String Priority_Low() {
        return holder.format("Priority.Low");
    }

    /**
     * Key {@code Priority.Low}: {@code Low}.
     * 
     * @return
     *     {@code Low}
     */
    public static Localizable _Priority_Low() {
        return new Localizable(holder, "Priority.Low");
    }

    /**
     * Key {@code BuildResult.Tab.High}: {@code High}.
     * 
     * @return
     *     {@code High}
     */
    public static String BuildResult_Tab_High() {
        return holder.format("BuildResult.Tab.High");
    }

    /**
     * Key {@code BuildResult.Tab.High}: {@code High}.
     * 
     * @return
     *     {@code High}
     */
    public static Localizable _BuildResult_Tab_High() {
        return new Localizable(holder, "BuildResult.Tab.High");
    }

    /**
     * Key {@code ResultAction.SuccessfulOneNoHighScore}: {@code Still one
     * day before reaching the previous successful builds highscore.}.
     * 
     * @return
     *     {@code Still one day before reaching the previous successful builds
     *     highscore.}
     */
    public static String ResultAction_SuccessfulOneNoHighScore() {
        return holder.format("ResultAction.SuccessfulOneNoHighScore");
    }

    /**
     * Key {@code ResultAction.SuccessfulOneNoHighScore}: {@code Still one
     * day before reaching the previous successful builds highscore.}.
     * 
     * @return
     *     {@code Still one day before reaching the previous successful builds
     *     highscore.}
     */
    public static Localizable _ResultAction_SuccessfulOneNoHighScore() {
        return new Localizable(holder, "ResultAction.SuccessfulOneNoHighScore");
    }

    /**
     * Key {@code BuildResult.Tab.Files}: {@code Files}.
     * 
     * @return
     *     {@code Files}
     */
    public static String BuildResult_Tab_Files() {
        return holder.format("BuildResult.Tab.Files");
    }

    /**
     * Key {@code BuildResult.Tab.Files}: {@code Files}.
     * 
     * @return
     *     {@code Files}
     */
    public static Localizable _BuildResult_Tab_Files() {
        return new Localizable(holder, "BuildResult.Tab.Files");
    }

    /**
     * Key {@code UserGraphConfiguration.Description}: {@code Configure the
     * trend graph of this plug-in for the current job and user. These values
     * are persisted in a cookie, so please make sure that cookies are
     * enabled in your browser.}.
     * 
     * @return
     *     {@code Configure the trend graph of this plug-in for the current job
     *     and user. These values are persisted in a cookie, so please make sure
     *     that cookies are enabled in your browser.}
     */
    public static String UserGraphConfiguration_Description() {
        return holder.format("UserGraphConfiguration.Description");
    }

    /**
     * Key {@code UserGraphConfiguration.Description}: {@code Configure the
     * trend graph of this plug-in for the current job and user. These values
     * are persisted in a cookie, so please make sure that cookies are
     * enabled in your browser.}.
     * 
     * @return
     *     {@code Configure the trend graph of this plug-in for the current job
     *     and user. These values are persisted in a cookie, so please make sure
     *     that cookies are enabled in your browser.}
     */
    public static Localizable _UserGraphConfiguration_Description() {
        return new Localizable(holder, "UserGraphConfiguration.Description");
    }

    /**
     * Key {@code Reporter.Error.NoEncoding}: {@code File encoding has not
     * been set in pom.xml, using platform encoding {0}, i.e. build is
     * platform dependent (see <a
     * href="http://docs.codehaus.org/display/MAVENUSER/POM+Element+for+Source+File+Encoding">Maven
     * FAQ</a>).}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code File encoding has not been set in pom.xml, using platform
     *     encoding {0}, i.e. build is platform dependent (see <a
     *     href="http://docs.codehaus.org/display/MAVENUSER/POM+Element+for+Source+File+Encoding">Maven
     *     FAQ</a>).}
     */
    public static String Reporter_Error_NoEncoding(Object arg0) {
        return holder.format("Reporter.Error.NoEncoding", arg0);
    }

    /**
     * Key {@code Reporter.Error.NoEncoding}: {@code File encoding has not
     * been set in pom.xml, using platform encoding {0}, i.e. build is
     * platform dependent (see <a
     * href="http://docs.codehaus.org/display/MAVENUSER/POM+Element+for+Source+File+Encoding">Maven
     * FAQ</a>).}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code File encoding has not been set in pom.xml, using platform
     *     encoding {0}, i.e. build is platform dependent (see <a
     *     href="http://docs.codehaus.org/display/MAVENUSER/POM+Element+for+Source+File+Encoding">Maven
     *     FAQ</a>).}
     */
    public static Localizable _Reporter_Error_NoEncoding(Object arg0) {
        return new Localizable(holder, "Reporter.Error.NoEncoding", arg0);
    }

    /**
     * Key {@code ModuleDetail.header}: {@code Module}.
     * 
     * @return
     *     {@code Module}
     */
    public static String ModuleDetail_header() {
        return holder.format("ModuleDetail.header");
    }

    /**
     * Key {@code ModuleDetail.header}: {@code Module}.
     * 
     * @return
     *     {@code Module}
     */
    public static Localizable _ModuleDetail_header() {
        return new Localizable(holder, "ModuleDetail.header");
    }

    /**
     * Key {@code Column.NoResults}: {@code No valid results available.}.
     * 
     * @return
     *     {@code No valid results available.}
     */
    public static String Column_NoResults() {
        return holder.format("Column.NoResults");
    }

    /**
     * Key {@code Column.NoResults}: {@code No valid results available.}.
     * 
     * @return
     *     {@code No valid results available.}
     */
    public static Localizable _Column_NoResults() {
        return new Localizable(holder, "Column.NoResults");
    }

    /**
     * Key {@code NewWarningsDetail.Name}: {@code New Warnings}.
     * 
     * @return
     *     {@code New Warnings}
     */
    public static String NewWarningsDetail_Name() {
        return holder.format("NewWarningsDetail.Name");
    }

    /**
     * Key {@code NewWarningsDetail.Name}: {@code New Warnings}.
     * 
     * @return
     *     {@code New Warnings}
     */
    public static Localizable _NewWarningsDetail_Name() {
        return new Localizable(holder, "NewWarningsDetail.Name");
    }

    /**
     * Key {@code BuildResultEvaluator.success}: {@code no threshold has been
     * exceeded}.
     * 
     * @return
     *     {@code no threshold has been exceeded}
     */
    public static String BuildResultEvaluator_success() {
        return holder.format("BuildResultEvaluator.success");
    }

    /**
     * Key {@code BuildResultEvaluator.success}: {@code no threshold has been
     * exceeded}.
     * 
     * @return
     *     {@code no threshold has been exceeded}
     */
    public static Localizable _BuildResultEvaluator_success() {
        return new Localizable(holder, "BuildResultEvaluator.success");
    }

    /**
     * Key {@code ConsoleLog.Name}: {@code Console output}.
     * 
     * @return
     *     {@code Console output}
     */
    public static String ConsoleLog_Name() {
        return holder.format("ConsoleLog.Name");
    }

    /**
     * Key {@code ConsoleLog.Name}: {@code Console output}.
     * 
     * @return
     *     {@code Console output}
     */
    public static Localizable _ConsoleLog_Name() {
        return new Localizable(holder, "ConsoleLog.Name");
    }

    /**
     * Key {@code ConsoleLog.Title}: {@code Console output (lines {0}-{1})}.
     * 
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code Console output (lines {0}-{1})}
     */
    public static String ConsoleLog_Title(Object arg0, Object arg1) {
        return holder.format("ConsoleLog.Title", arg0, arg1);
    }

    /**
     * Key {@code ConsoleLog.Title}: {@code Console output (lines {0}-{1})}.
     * 
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code Console output (lines {0}-{1})}
     */
    public static Localizable _ConsoleLog_Title(Object arg0, Object arg1) {
        return new Localizable(holder, "ConsoleLog.Title", arg0, arg1);
    }

    /**
     * Key {@code Errors}: {@code Errors}.
     * 
     * @return
     *     {@code Errors}
     */
    public static String Errors() {
        return holder.format("Errors");
    }

    /**
     * Key {@code Errors}: {@code Errors}.
     * 
     * @return
     *     {@code Errors}
     */
    public static Localizable _Errors() {
        return new Localizable(holder, "Errors");
    }

    /**
     * Key {@code BuildResult.Tab.Details}: {@code Details}.
     * 
     * @return
     *     {@code Details}
     */
    public static String BuildResult_Tab_Details() {
        return holder.format("BuildResult.Tab.Details");
    }

    /**
     * Key {@code BuildResult.Tab.Details}: {@code Details}.
     * 
     * @return
     *     {@code Details}
     */
    public static Localizable _BuildResult_Tab_Details() {
        return new Localizable(holder, "BuildResult.Tab.Details");
    }

    /**
     * Key {@code PathDetail.title}: {@code Folders}.
     * 
     * @return
     *     {@code Folders}
     */
    public static String PathDetail_title() {
        return holder.format("PathDetail.title");
    }

    /**
     * Key {@code PathDetail.title}: {@code Folders}.
     * 
     * @return
     *     {@code Folders}
     */
    public static Localizable _PathDetail_title() {
        return new Localizable(holder, "PathDetail.title");
    }

    /**
     * Key {@code Trend.yAxisLabel}: {@code count}.
     * 
     * @return
     *     {@code count}
     */
    public static String Trend_yAxisLabel() {
        return holder.format("Trend.yAxisLabel");
    }

    /**
     * Key {@code Trend.yAxisLabel}: {@code count}.
     * 
     * @return
     *     {@code count}
     */
    public static Localizable _Trend_yAxisLabel() {
        return new Localizable(holder, "Trend.yAxisLabel");
    }

    /**
     * Key {@code BuildResultEvaluator.unstable.all}: {@code <a
     * href="{3}">{0} warnings</a> exceed the threshold of {1} by {2}}.
     * 
     * @param arg3
     *      4th format parameter, {@code {3}}, as {@link String#valueOf(Object)}.
     * @param arg2
     *      3rd format parameter, {@code {2}}, as {@link String#valueOf(Object)}.
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code <a href="{3}">{0} warnings</a> exceed the threshold of {1} by
     *     {2}}
     */
    public static String BuildResultEvaluator_unstable_all(Object arg0, Object arg1, Object arg2, Object arg3) {
        return holder.format("BuildResultEvaluator.unstable.all", arg0, arg1, arg2, arg3);
    }

    /**
     * Key {@code BuildResultEvaluator.unstable.all}: {@code <a
     * href="{3}">{0} warnings</a> exceed the threshold of {1} by {2}}.
     * 
     * @param arg3
     *      4th format parameter, {@code {3}}, as {@link String#valueOf(Object)}.
     * @param arg2
     *      3rd format parameter, {@code {2}}, as {@link String#valueOf(Object)}.
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code <a href="{3}">{0} warnings</a> exceed the threshold of {1} by
     *     {2}}
     */
    public static Localizable _BuildResultEvaluator_unstable_all(Object arg0, Object arg1, Object arg2, Object arg3) {
        return new Localizable(holder, "BuildResultEvaluator.unstable.all", arg0, arg1, arg2, arg3);
    }

    /**
     * Key {@code ResultAction.MultipleFiles}: {@code from {0} analyses.}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code from {0} analyses.}
     */
    public static String ResultAction_MultipleFiles(Object arg0) {
        return holder.format("ResultAction.MultipleFiles", arg0);
    }

    /**
     * Key {@code ResultAction.MultipleFiles}: {@code from {0} analyses.}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code from {0} analyses.}
     */
    public static Localizable _ResultAction_MultipleFiles(Object arg0) {
        return new Localizable(holder, "ResultAction.MultipleFiles", arg0);
    }

    /**
     * Key {@code BuildResult.Tab.Authors}: {@code People}.
     * 
     * @return
     *     {@code People}
     */
    public static String BuildResult_Tab_Authors() {
        return holder.format("BuildResult.Tab.Authors");
    }

    /**
     * Key {@code BuildResult.Tab.Authors}: {@code People}.
     * 
     * @return
     *     {@code People}
     */
    public static Localizable _BuildResult_Tab_Authors() {
        return new Localizable(holder, "BuildResult.Tab.Authors");
    }

    /**
     * Key {@code Priority.High}: {@code High}.
     * 
     * @return
     *     {@code High}
     */
    public static String Priority_High() {
        return holder.format("Priority.High");
    }

    /**
     * Key {@code Priority.High}: {@code High}.
     * 
     * @return
     *     {@code High}
     */
    public static Localizable _Priority_High() {
        return new Localizable(holder, "Priority.High");
    }

    /**
     * Key {@code ResultAction.MultipleFixedWarnings}: {@code {0} fixed
     * warnings}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code {0} fixed warnings}
     */
    public static String ResultAction_MultipleFixedWarnings(Object arg0) {
        return holder.format("ResultAction.MultipleFixedWarnings", arg0);
    }

    /**
     * Key {@code ResultAction.MultipleFixedWarnings}: {@code {0} fixed
     * warnings}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code {0} fixed warnings}
     */
    public static Localizable _ResultAction_MultipleFixedWarnings(Object arg0) {
        return new Localizable(holder, "ResultAction.MultipleFixedWarnings", arg0);
    }

    /**
     * Key {@code BuildResult.Tab.New}: {@code New}.
     * 
     * @return
     *     {@code New}
     */
    public static String BuildResult_Tab_New() {
        return holder.format("BuildResult.Tab.New");
    }

    /**
     * Key {@code BuildResult.Tab.New}: {@code New}.
     * 
     * @return
     *     {@code New}
     */
    public static Localizable _BuildResult_Tab_New() {
        return new Localizable(holder, "BuildResult.Tab.New");
    }

    /**
     * Key {@code BuildResult.Tab.Warnings}: {@code Warnings}.
     * 
     * @return
     *     {@code Warnings}
     */
    public static String BuildResult_Tab_Warnings() {
        return holder.format("BuildResult.Tab.Warnings");
    }

    /**
     * Key {@code BuildResult.Tab.Warnings}: {@code Warnings}.
     * 
     * @return
     *     {@code Warnings}
     */
    public static Localizable _BuildResult_Tab_Warnings() {
        return new Localizable(holder, "BuildResult.Tab.Warnings");
    }

    /**
     * Key {@code Result.Error.ModuleErrorMessage}: {@code Module {0}: {1}}.
     * 
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code Module {0}: {1}}
     */
    public static String Result_Error_ModuleErrorMessage(Object arg0, Object arg1) {
        return holder.format("Result.Error.ModuleErrorMessage", arg0, arg1);
    }

    /**
     * Key {@code Result.Error.ModuleErrorMessage}: {@code Module {0}: {1}}.
     * 
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code Module {0}: {1}}
     */
    public static Localizable _Result_Error_ModuleErrorMessage(Object arg0, Object arg1) {
        return new Localizable(holder, "Result.Error.ModuleErrorMessage", arg0, arg1);
    }

    /**
     * Key {@code ReferenceBuild}: {@code Reference build}.
     * 
     * @return
     *     {@code Reference build}
     */
    public static String ReferenceBuild() {
        return holder.format("ReferenceBuild");
    }

    /**
     * Key {@code ReferenceBuild}: {@code Reference build}.
     * 
     * @return
     *     {@code Reference build}
     */
    public static Localizable _ReferenceBuild() {
        return new Localizable(holder, "ReferenceBuild");
    }

    /**
     * Key {@code ResultAction.Status}: {@code Plug-in Result:}.
     * 
     * @return
     *     {@code Plug-in Result:}
     */
    public static String ResultAction_Status() {
        return holder.format("ResultAction.Status");
    }

    /**
     * Key {@code ResultAction.Status}: {@code Plug-in Result:}.
     * 
     * @return
     *     {@code Plug-in Result:}
     */
    public static Localizable _ResultAction_Status() {
        return new Localizable(holder, "ResultAction.Status");
    }

    /**
     * Key {@code DefaultGraphConfiguration.Description}: {@code Configure
     * the trend graph of this plug-in. This default configuration can be
     * overwritten by each user.}.
     * 
     * @return
     *     {@code Configure the trend graph of this plug-in. This default
     *     configuration can be overwritten by each user.}
     */
    public static String DefaultGraphConfiguration_Description() {
        return holder.format("DefaultGraphConfiguration.Description");
    }

    /**
     * Key {@code DefaultGraphConfiguration.Description}: {@code Configure
     * the trend graph of this plug-in. This default configuration can be
     * overwritten by each user.}.
     * 
     * @return
     *     {@code Configure the trend graph of this plug-in. This default
     *     configuration can be overwritten by each user.}
     */
    public static Localizable _DefaultGraphConfiguration_Description() {
        return new Localizable(holder, "DefaultGraphConfiguration.Description");
    }

    /**
     * Key {@code FieldValidator.Error.TrendHeight}: {@code Trend graph
     * height must be an integer value greater or equal {0}.}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code Trend graph height must be an integer value greater or equal
     *     {0}.}
     */
    public static String FieldValidator_Error_TrendHeight(Object arg0) {
        return holder.format("FieldValidator.Error.TrendHeight", arg0);
    }

    /**
     * Key {@code FieldValidator.Error.TrendHeight}: {@code Trend graph
     * height must be an integer value greater or equal {0}.}.
     * 
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code Trend graph height must be an integer value greater or equal
     *     {0}.}
     */
    public static Localizable _FieldValidator_Error_TrendHeight(Object arg0) {
        return new Localizable(holder, "FieldValidator.Error.TrendHeight", arg0);
    }

    /**
     * Key {@code FilesParser.Error.NoFiles}: {@code No report files were
     * found. Configuration error?}.
     * 
     * @return
     *     {@code No report files were found. Configuration error?}
     */
    public static String FilesParser_Error_NoFiles() {
        return holder.format("FilesParser.Error.NoFiles");
    }

    /**
     * Key {@code FilesParser.Error.NoFiles}: {@code No report files were
     * found. Configuration error?}.
     * 
     * @return
     *     {@code No report files were found. Configuration error?}
     */
    public static Localizable _FilesParser_Error_NoFiles() {
        return new Localizable(holder, "FilesParser.Error.NoFiles");
    }

    /**
     * Key {@code DefaultGraphConfiguration.Name}: {@code Configure Default
     * Trend Graph}.
     * 
     * @return
     *     {@code Configure Default Trend Graph}
     */
    public static String DefaultGraphConfiguration_Name() {
        return holder.format("DefaultGraphConfiguration.Name");
    }

    /**
     * Key {@code DefaultGraphConfiguration.Name}: {@code Configure Default
     * Trend Graph}.
     * 
     * @return
     *     {@code Configure Default Trend Graph}
     */
    public static Localizable _DefaultGraphConfiguration_Name() {
        return new Localizable(holder, "DefaultGraphConfiguration.Name");
    }

    /**
     * Key {@code BuildResultEvaluator.unstable.one.all.priority}: {@code <a
     * href="{3}">1 warning</a> of <a href="{4}">priority {2}</a> exceeds the
     * threshold of {0} by {1}}.
     * 
     * @param arg3
     *      4th format parameter, {@code {3}}, as {@link String#valueOf(Object)}.
     * @param arg2
     *      3rd format parameter, {@code {2}}, as {@link String#valueOf(Object)}.
     * @param arg4
     *      5th format parameter, {@code {4}}, as {@link String#valueOf(Object)}.
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code <a href="{3}">1 warning</a> of <a href="{4}">priority {2}</a>
     *     exceeds the threshold of {0} by {1}}
     */
    public static String BuildResultEvaluator_unstable_one_all_priority(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
        return holder.format("BuildResultEvaluator.unstable.one.all.priority", arg0, arg1, arg2, arg3, arg4);
    }

    /**
     * Key {@code BuildResultEvaluator.unstable.one.all.priority}: {@code <a
     * href="{3}">1 warning</a> of <a href="{4}">priority {2}</a> exceeds the
     * threshold of {0} by {1}}.
     * 
     * @param arg3
     *      4th format parameter, {@code {3}}, as {@link String#valueOf(Object)}.
     * @param arg2
     *      3rd format parameter, {@code {2}}, as {@link String#valueOf(Object)}.
     * @param arg4
     *      5th format parameter, {@code {4}}, as {@link String#valueOf(Object)}.
     * @param arg1
     *      2nd format parameter, {@code {1}}, as {@link String#valueOf(Object)}.
     * @param arg0
     *      1st format parameter, {@code {0}}, as {@link String#valueOf(Object)}.
     * @return
     *     {@code <a href="{3}">1 warning</a> of <a href="{4}">priority {2}</a>
     *     exceeds the threshold of {0} by {1}}
     */
    public static Localizable _BuildResultEvaluator_unstable_one_all_priority(Object arg0, Object arg1, Object arg2, Object arg3, Object arg4) {
        return new Localizable(holder, "BuildResultEvaluator.unstable.one.all.priority", arg0, arg1, arg2, arg3, arg4);
    }

}
