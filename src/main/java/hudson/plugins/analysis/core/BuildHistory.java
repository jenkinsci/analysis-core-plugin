package hudson.plugins.analysis.core;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.annotation.CheckForNull;

import hudson.model.Result;
import hudson.model.AbstractBuild;

import hudson.plugins.analysis.util.model.AnnotationContainer;
import hudson.plugins.analysis.util.model.DefaultAnnotationContainer;
import hudson.plugins.analysis.util.model.FileAnnotation;

/**
 * History of build results of a specific plug-in. The plug-in is identified by
 * the corresponding {@link ResultAction} type.
 *
 * @author Ulli Hafner
 */
public class BuildHistory {
    /** The build to start the history from. */
    private final AbstractBuild<?, ?> baseline;
    /** Type of the action that contains the build results. */
    private final Class<? extends ResultAction<? extends BuildResult>> type;
    /** Determines whether only stable builds should be used as reference builds or not */
    private final boolean useStableBuildAsReference;

    /**
     * Creates a new instance of {@link BuildHistory}.
     *
     * @param baseline
     *            the build to start the history from
     * @param type
     *            type of the action that contains the build results
     * @param useStableBuildAsReference
     *            determines whether only stable builds should be used as
     *            reference builds or not
     * @since 1.47
     */
    public BuildHistory(final AbstractBuild<?, ?> baseline, final Class<? extends ResultAction<? extends BuildResult>> type,
            final boolean useStableBuildAsReference) {
        this.baseline = baseline;
        this.type = type;
        this.useStableBuildAsReference = useStableBuildAsReference;
    }

    /**
     * Creates a new instance of {@link BuildHistory}.
     *
     * @param baseline
     *            the build to start the history from
     * @param type
     *            type of the action that contains the build results
     */
    @Deprecated
    public BuildHistory(final AbstractBuild<?, ?> baseline, final Class<? extends ResultAction<? extends BuildResult>> type) {
        this(baseline, type, false);
    }

    /**
     * Returns the time of the baseline build.
     *
     * @return the time
     */
    public Calendar getTimestamp() {
        return baseline.getTimestamp();
    }
    /**
     * Returns whether a reference build result exists.
     *
     * @return <code>true</code> if a reference build result exists.
     */
    private boolean hasReferenceResult() {
        return getReferenceAction() != null;
    }

    /**
     * Returns the annotations of the reference build.
     *
     * @return the annotations of the reference build
     */
    public AnnotationContainer getReferenceAnnotations() {
        ResultAction<? extends BuildResult> action = getReferenceAction();
        if (action != null) {
            return action.getResult().getContainer();
        }
        return new DefaultAnnotationContainer();
    }

    /**
     * Returns the action of the reference build.
     *
     * @return the action of the reference build, or <code>null</code> if no
     *         such build exists
     */
    private ResultAction<? extends BuildResult> getReferenceAction() {
        ResultAction<? extends BuildResult> action = getAction(true, useStableBuildAsReference);
        if (action == null) {
            return getPreviousAction(); // fallback, use action of previous build regardless of result
        }
        else {
            return action;
        }
    }

    private ResultAction<? extends BuildResult> getAction(final boolean isStatusRelevant) {
        return getAction(isStatusRelevant, false);
    }

    private ResultAction<? extends BuildResult> getAction(final boolean isStatusRelevant, final boolean mustBeStable) {
        for (AbstractBuild<?, ?> build = baseline.getPreviousBuild(); build != null; build = build.getPreviousBuild()) {
            if (hasValidResult(build, mustBeStable)) {
                ResultAction<? extends BuildResult> action = getResultAction(build);
                if (action != null && (action.isSuccessful() || !isStatusRelevant)) {
                    return action;
                }
            }
        }
        return null;
    }

    /**
     * Returns the result action of the specified build that should be used to
     * compute the history.
     *
     * @param build
     *            the build
     * @return the result action
     */
    @CheckForNull
    public ResultAction<? extends BuildResult> getResultAction(final AbstractBuild<?, ?> build) {
        return build.getAction(type);
    }

    /**
     * Returns the action of the previous build.
     *
     * @return the action of the previous build, or <code>null</code> if no
     *         such build exists
     */
    @CheckForNull
    private ResultAction<? extends BuildResult> getPreviousAction() {
        return getAction(false);
    }

    /**
     * Returns the reference build or <code>null</code> if there is no such
     * build.
     *
     * @return the reference build
     * @since 1.20
     * @see #hasReferenceBuild()
     */
    @CheckForNull
    public AbstractBuild<?, ?> getReferenceBuild() {
        ResultAction<? extends BuildResult> action = getReferenceAction();
        if (action != null) {
            AbstractBuild<?, ?> build = action.getBuild();
            if (hasValidResult(build)) {
                return build;
            }
        }
        return null;
    }

    private boolean hasValidResult(final AbstractBuild<?, ?> build) {
        return hasValidResult(build, false);
    }
    private boolean hasValidResult(final AbstractBuild<?, ?> build, final boolean mustBeStable) {
        Result result = build.getResult();

        if (result == null) {
            return false;
        }
        if (mustBeStable) {
            return result == Result.SUCCESS;
        }
        return build.getResult().isBetterThan(Result.FAILURE);
    }

    /**
     * Returns whether a reference build is available to compare the results
     * with.
     *
     * @return <code>true</code> if a reference build exists, <code>false</code>
     *         otherwise
     * @since 1.20
     */
    public boolean hasReferenceBuild() {
        return getReferenceBuild() != null;
    }

    /**
     * Returns whether a previous build result exists.
     *
     * @return <code>true</code> if a previous build result exists.
     * @see #isEmpty()
     */
    public boolean hasPreviousResult() {
        return getPreviousAction() != null;
    }

    /**
     * Returns whether there is no history available, i.e. the current build is
     * the first valid one.
     *
     * @return <code>true</code> if there is no previous build available
     * @see #hasPreviousResult()
     */
    public boolean isEmpty() {
        return !hasPreviousResult();
    }

    /**
     * Returns the baseline action.
     *
     * @return the baseline action
     * @see #hasPreviousResult()
     * @throws NoSuchElementException
     *             if there is no previous result
     */
    public ResultAction<? extends BuildResult> getBaseline() {
        return getResultAction(baseline);
    }

    /**
     * Returns the previous build result.
     *
     * @return the previous build result
     * @see #hasPreviousResult()
     * @throws NoSuchElementException
     *             if there is no previous result
     */
    public BuildResult getPreviousResult() {
        ResultAction<? extends BuildResult> action = getPreviousAction();
        if (action != null) {
            return action.getResult();
        }
        throw new NoSuchElementException("No previous result available");
    }

    /**
     * Returns the new warnings as a difference between the specified collection
     * of warnings and the warnings of the reference build.
     *
     * @param annotations
     *            the warnings in the current build
     * @return the difference "current build" - "reference build"
     */
    public Collection<FileAnnotation> getNewWarnings(final Set<FileAnnotation> annotations) {
        if (hasReferenceResult()) {
            return AnnotationDifferencer.getNewAnnotations(annotations, getReferenceAnnotations().getAnnotations());
        }
        else {
            return annotations;
        }
    }

    /**
     * Returns the fixed warnings as a difference between the warnings of the
     * reference build and the specified collection of warnings.
     *
     * @param annotations
     *            the warnings in the current build
     * @return the difference "reference build" - "current build"
     */
    public Collection<FileAnnotation> getFixedWarnings(final Set<FileAnnotation> annotations) {
        if (hasReferenceResult()) {
            return AnnotationDifferencer.getFixedAnnotations(annotations, getReferenceAnnotations().getAnnotations());
        }
        else {
            return Collections.emptyList();
        }
    }

    /**
     * Returns the health descriptor used for the builds.
     *
     * @return the health descriptor
     */
    public AbstractHealthDescriptor getHealthDescriptor() {
        return getBaseline().getHealthDescriptor();
    }
}

