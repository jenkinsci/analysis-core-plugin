package io.jenkins.plugins.analysis.core.quality;

import org.assertj.core.api.AbstractAssert;

/**
 * Custom assertions for {@link QualityGate}.
 */

public class QualityGateAssert extends AbstractAssert<QualityGateAssert, QualityGate> {

    /**
     * Creates a new {@link QualityGateAssert} to make assertions on actual {@link QualityGate}.
     *
     * @param actual
     *         the QualityGate we want to make assertions on
     */

    public QualityGateAssert(final QualityGate actual) {
        super(actual, QualityGateAssert.class);

    }

    /**
     * Checks whether a QualityGate has a specific failureTotalThreshold.
     *
     * @param failureTotalThreshold
     *         specifying failureTotalThreshold.
     *
     * @return this
     */

    public QualityGateAssert hasFailureTotalThreshold(int failureTotalThreshold) {
        isNotNull();
        if (actual.getFailureTotalThreshold() != failureTotalThreshold) {
            failWithMessage("Expected the failureTotalThreshold to be <%s> but was <%s>", failureTotalThreshold, actual.getFailureTotalThreshold());
        }
        return this;
    }

    /**
     * Creates a new {@link QualityGateAssert} to make assertions on actual {@link QualityGate}.
     *
     * @param actual
     *         the issue we want to make assertions on
     *
     * @return a new {@link QualityGateAssert}
     */

    public QualityGateAssert assertThat(final QualityGate actual) {
        return new QualityGateAssert(actual);
    }

    /**
     * Checks whether a QualityGate has a specific failureHighPriorityThreshold.
     *
     * @param failureHighPriorityThreshold
     *         specifying failureHighPriorityThreshold.
     *
     * @return this
     */

    public QualityGateAssert hasFailureHighPriorityThreshold(int failureHighPriorityThreshold) {
        isNotNull();
        if (actual.getFailureHighPriorityThreshold() != failureHighPriorityThreshold) {
            failWithMessage("Expected failureHighPriorityThreshold to be <%s> but was <%s>", failureHighPriorityThreshold, actual.getFailureHighPriorityThreshold());
        }
        return this;
    }

    /**
     * Checks whether a QualityGate has a specific failureNormalPriorityThreshold.
     *
     * @param failureNormalPriorityThreshold
     *         specifying failureNormalPriorityThreshold.
     *
     * @return this
     */

    public QualityGateAssert hasFailureNormalPriorityThreshold(int failureNormalPriorityThreshold) {
        isNotNull();

        if (actual.getFailureNormalPriorityThreshold() != failureNormalPriorityThreshold) {
            failWithMessage("Expected failureNormalPriorityThreshold to be <%s> but was <%s>", failureNormalPriorityThreshold, actual.getFailureNormalPriorityThreshold());
        }
        return this;

    }

    /**
     * Checks whether a QualityGate has a specific failureLowPriorityThreshold.
     *
     * @param failureLowPriorityThreshold
     *         specifying failureLowPriorityThreshold.
     *
     * @return this
     */

    public QualityGateAssert hasFailureLowPriorityThreshold(int failureLowPriorityThreshold) {
        isNotNull();

        if (actual.getFailureLowPriorityThreshold() != failureLowPriorityThreshold) {
            failWithMessage("Expected failureLowPriorityThreshold to be <%s> but was <%s>", failureLowPriorityThreshold, actual.getFailureLowPriorityThreshold());
        }
        return this;

    }

    /**
     * Checks whether a QualityGate has a specific failureNewTotalThreshold.
     *
     * @param failureNewTotalThreshold
     *         specifying failureNewTotalThreshold.
     *
     * @return this
     */

    public QualityGateAssert hasFailureNewTotalThreshold(int failureNewTotalThreshold) {
        isNotNull();

        if (actual.getFailureNewTotalThreshold() != failureNewTotalThreshold) {
            failWithMessage("Expected failureNewTotalThreshold to be <%s> but was <%s>", failureNewTotalThreshold, actual.getFailureNewTotalThreshold());
        }
        return this;

    }

    /**
     * Checks whether a QualityGate has a specific failureNewHighPriorityThreshold.
     *
     * @param failureNewHighPriorityThreshold
     *         specifying failureNewHighPriorityThreshold.
     *
     * @return this
     */


    public QualityGateAssert hasFailureNewHighPriorityThreshold(int failureNewHighPriorityThreshold) {
        isNotNull();

        if (actual.getFailureNewHighPriorityThreshold() != failureNewHighPriorityThreshold) {
            failWithMessage("Expected failureNewHighPriorityThreshold to be <%s> but was <%s>", failureNewHighPriorityThreshold, actual.getFailureNewHighPriorityThreshold());
        }
        return this;

    }

    /**
     * Checks whether a QualityGate has a specific failureNewNormalPriorityThreshold.
     *
     * @param failureNewNormalPriorityThreshold
     *         specifying failureNewNormalPriorityThreshold.
     *
     * @return this
     */

    public QualityGateAssert hasFailureNewNormalHighPriorityThreshold(int failureNewNormalPriorityThreshold) {
        isNotNull();

        if (actual.getFailureNewNormalPriorityThreshold() != failureNewNormalPriorityThreshold) {
            failWithMessage("Expected failureNewNormalPriorityThreshold to be <%s> but was <%s>", failureNewNormalPriorityThreshold, actual.getFailureNewNormalPriorityThreshold());
        }
        return this;

    }

    /**
     * Checks whether a QualityGate has a specific failureNewLowPriorityThreshold.
     *
     * @param failureNewLowPriorityThreshold
     *         specifying failureNewLowPriorityThreshold.
     *
     * @return this
     */

    public QualityGateAssert hasFailureNewLowPriorityThreshold(int failureNewLowPriorityThreshold) {
        isNotNull();

        if (actual.getFailureNewLowPriorityThreshold() != failureNewLowPriorityThreshold) {
            failWithMessage("Expected failureNewLowPriorityThreshold to be <%s> but was <%s>", failureNewLowPriorityThreshold, actual.getFailureNewLowPriorityThreshold());
        }
        return this;

    }

    /**
     * Checks whether a QualityGate has a specific unstableTotalThreshold.
     *
     * @param unstableTotalThreshold
     *         specifying unstableTotalThreshold.
     *
     * @return this
     */

    public QualityGateAssert hasUnstableTotalThreshold(int unstableTotalThreshold) {
        isNotNull();

        if (actual.getUnstableTotalThreshold() != unstableTotalThreshold) {
            failWithMessage("Expected unstableTotalThreshold to be <%s> but was <%s>", unstableTotalThreshold, actual.getUnstableTotalThreshold());
        }
        return this;

    }

    /**
     * Checks whether a QualityGate has a specific unstableHighPriorityThreshold.
     *
     * @param unstableHighPriorityThreshold
     *         specifying unstableHighPriorityThreshold.
     *
     * @return this
     */

    public QualityGateAssert hasUnstableHighPriorityThreshold(int unstableHighPriorityThreshold) {
        isNotNull();

        if (actual.getUnstableHighPriorityThreshold() != unstableHighPriorityThreshold) {
            failWithMessage("Expected unstableHighPriorityThreshold to be <%s> but was <%s>", unstableHighPriorityThreshold, actual.getUnstableHighPriorityThreshold());
        }
        return this;

    }

    /**
     * Checks whether a QualityGate has a specific unstableNormalPriorityThreshold.
     *
     * @param unstableNormalPriorityThreshold
     *         specifying unstableNormalPriorityThreshold.
     *
     * @return this
     */

    public QualityGateAssert hasUnstableNormalPriorityThreshold(int unstableNormalPriorityThreshold) {
        isNotNull();

        if (actual.getUnstableNormalPriorityThreshold() != unstableNormalPriorityThreshold) {
            failWithMessage("Expected unstableNormalPriorityThreshold to be <%s> but was <%s>", unstableNormalPriorityThreshold, actual.getUnstableNormalPriorityThreshold());
        }
        return this;

    }

    /**
     * Checks whether a QualityGate has a specific unstableLowPriorityThreshold.
     *
     * @param unstableLowPriorityThreshold
     *         specifying unstableLowPriorityThreshold.
     *
     * @return this
     */

    public QualityGateAssert hasUnstableLowPriorityThreshold(int unstableLowPriorityThreshold) {
        isNotNull();

        if (actual.getUnstableLowPriorityThreshold() != unstableLowPriorityThreshold) {
            failWithMessage("Expected unstableLowPriorityThreshold to be <%s> but was <%s>", unstableLowPriorityThreshold, actual.getUnstableLowPriorityThreshold());
        }
        return this;

    }

    /**
     * Checks whether a QualityGate has a specific unstableNewTotalThreshold.
     *
     * @param unstableNewTotalThreshold
     *         specifying unstableNewTotalThreshold.
     *
     * @return this
     */

    public QualityGateAssert hasUnstableNewTotalThreshold(int unstableNewTotalThreshold) {
        isNotNull();

        if (actual.getUnstableNewTotalThreshold() != unstableNewTotalThreshold) {
            failWithMessage("Expected unstableTotalThreshold to be <%s> but was <%s>", unstableNewTotalThreshold, actual.getUnstableNewTotalThreshold());
        }
        return this;

    }

    /**
     * Checks whether a QualityGate has a specific unstableNewHighPriorityThreshold.
     *
     * @param unstableNewHighPriorityThreshold
     *         specifying unstableNewHighPriorityThreshold.
     *
     * @return this
     */

    public QualityGateAssert hasUnstableNewHighPriorityThreshold(int unstableNewHighPriorityThreshold) {
        isNotNull();

        if (actual.getUnstableNewHighPriorityThreshold() != unstableNewHighPriorityThreshold) {
            failWithMessage("Expected unstableHighPriorityThreshold to be <%s> but was <%s>", unstableNewHighPriorityThreshold, actual.getUnstableNewHighPriorityThreshold());
        }
        return this;

    }

    /**
     * Checks whether a QualityGate has a specific unstableNewNormalPriorityThreshold.
     *
     * @param unstableNewNormalPriorityThreshold
     *         specifying unstableNewNormalPriorityThreshold.
     *
     * @return this
     */

    public QualityGateAssert hasUnstableNewNormalPriorityThreshold(int unstableNewNormalPriorityThreshold) {
        isNotNull();

        if (actual.getUnstableNewNormalPriorityThreshold() != unstableNewNormalPriorityThreshold) {
            failWithMessage("Expected unstableNormalPriorityThreshold to be <%s> but was <%s>", unstableNewNormalPriorityThreshold, actual.getUnstableNewNormalPriorityThreshold());
        }
        return this;

    }

    /**
     * Checks whether a QualityGate has a specific unstableNewLowPriorityThreshold.
     *
     * @param unstableNewLowPriorityThreshold
     *         specifying unstableNewLowPriorityThreshold.
     *
     * @return this
     */

    public QualityGateAssert hasUnstableNewLowPriorityThreshold(int unstableNewLowPriorityThreshold) {
        isNotNull();

        if (actual.getUnstableNewLowPriorityThreshold() != unstableNewLowPriorityThreshold) {
            failWithMessage("Expected unstableLowPriorityThreshold to be <%s> but was <%s>", unstableNewLowPriorityThreshold, actual.getUnstableNewLowPriorityThreshold());
        }
        return this;

    }

    /**
     * Checks whether a QualityGate failureTotalThresholdSet is set.
     *
     * @param isFailureTotalThresholdSet
     *         specifying isFailureTotalThresholdSet.
     *
     * @return this
     */

    public QualityGateAssert isFailureTotalThresholdSet(boolean isFailureTotalThresholdSet) {
        isNotNull();

        if (actual.hasFailureTotalThreshold() != isFailureTotalThresholdSet) {

            failWithMessage("Expected hasFailureTotalThreshold to be <%s> but was <%s>", isFailureTotalThresholdSet, actual.hasFailureTotalThreshold());

        }

        return this;

    }

    /**
     * Checks whether a QualityGate failureLowPriorityThreshold is set.
     *
     * @param isFailureLowPriorityThresholdSet
     *         specifying isFailureLowPriorityThresholdSet.
     *
     * @return this
     */

    public QualityGateAssert isFailureLowPriorityThresholdSet(boolean isFailureLowPriorityThresholdSet) {
        isNotNull();

        if (actual.hasFailureLowPriorityThreshold() != isFailureLowPriorityThresholdSet) {

            failWithMessage("Expected FailureLowPriorityThreshold to be <%s> but was <%s>", isFailureLowPriorityThresholdSet, actual.hasFailureLowPriorityThreshold());

        }

        return this;

    }

    /**
     * Checks whether a QualityGate failureNormalPriorityThresholdSet is set.
     *
     * @param isFailureNormalPriorityThresholdSet
     *         specifying isFailureNormalPriorityThresholdSet.
     *
     * @return this
     */

    public QualityGateAssert isFailureNormalPriorityThresholdSet(boolean isFailureNormalPriorityThresholdSet) {
        isNotNull();

        if (actual.hasFailureNormalPriorityThreshold() != isFailureNormalPriorityThresholdSet) {

            failWithMessage("Expected hasailureNormalPriorityThresholdSet to be <%s> but was <%s>", isFailureNormalPriorityThresholdSet, actual.hasFailureNormalPriorityThreshold());

        }

        return this;

    }

    /**
     * Checks whether a QualityGate failureHighPriorityThreshold is set.
     *
     * @param isFailureHighPriorityThresholdSet
     *         specifying isFailureHighPriorityThresholdSet.
     *
     * @return this
     */

    public QualityGateAssert isFailureHighPriorityThresholdSet(boolean isFailureHighPriorityThresholdSet) {
        isNotNull();

        if (actual.hasFailureHighPriorityThreshold() != isFailureHighPriorityThresholdSet) {
            failWithMessage("Expected hasFailureHighPriorityThresholdSet to be <%s> but was <%s>", isFailureHighPriorityThresholdSet, actual.hasFailureHighPriorityThreshold());
        }

        return this;

    }

    /**
     * Checks whether a QualityGate FailureNewTotalThreshold is set.
     *
     * @param isFailureNewTotalThresholdSet
     *         specifying isFailureTotalThresholdSet.
     *
     * @return this
     */

    public QualityGateAssert isFailureNewTotalThresholdSet(boolean isFailureNewTotalThresholdSet) {
        isNotNull();

        if (actual.hasFailureNewTotalThreshold() != isFailureNewTotalThresholdSet) {
            failWithMessage("Expected hasFailureNewTotalThreshold to be <%s> but was <%s>", isFailureNewTotalThresholdSet, actual.hasFailureNewTotalThreshold());
        }

        return this;

    }

    /**
     * Checks whether a QualityGate failureTotalThresholdSet is set.
     *
     * @param isFailureNewLowPriorityThresholdSet
     *         specifying isFailureTotalThresholdSet.
     *
     * @return this
     */

    public QualityGateAssert isFailureNewLowPriorityThresholdSet(boolean isFailureNewLowPriorityThresholdSet) {
        isNotNull();

        if (actual.hasFailureNewLowPriorityThreshold() != isFailureNewLowPriorityThresholdSet) {
            failWithMessage("Expected isFailureNewLowPriorityThresholdSet to be <%s> but was <%s>", isFailureNewLowPriorityThresholdSet, actual.hasFailureNewLowPriorityThreshold());
        }

        return this;

    }

    /**
     * Checks whether a QualityGate failureNewNormalPriorityThreshold is set.
     *
     * @param isFailureNewNormalPriorityThresholdSet
     *         specifying isFailureNewNormalPriorityThresholdSet.
     *
     * @return this
     */

    public QualityGateAssert isFailureNewNormalPriorityThresholdSet(boolean isFailureNewNormalPriorityThresholdSet) {
        isNotNull();

        if (actual.hasFailureNewNormalPriorityThreshold() != isFailureNewNormalPriorityThresholdSet) {
            failWithMessage("Expected hasFailureNewNormalPriorityThreshold to be <%s> but was <%s>", isFailureNewNormalPriorityThresholdSet, actual.hasFailureNewNormalPriorityThreshold());
        }

        return this;

    }

    /**
     * Checks whether a QualityGate failureNewHighPriorityThreshold is set.
     *
     * @param isFailureNewHighPriorityThresholdSet
     *         specifying isFailureNewHighPriorityThresholdSet.
     *
     * @return this
     */

    public QualityGateAssert isFailureNewHighPriorityThresholdSet(boolean isFailureNewHighPriorityThresholdSet) {
        isNotNull();

        if (actual.hasFailureNewHighPriorityThreshold() != isFailureNewHighPriorityThresholdSet) {
            failWithMessage("Expected hasFailureNewHighPriorityThreshold to be <%s> but was <%s>", isFailureNewHighPriorityThresholdSet, actual.hasFailureNewHighPriorityThreshold());
        }
        return this;
    }

    /**
     * Checks whether a QualityGate failureTotalThresholdSet is set.
     *
     * @param isUnstableTotalThresholdSet
     *         specifying isFailureTotalThresholdSet.
     *
     * @return this
     */

    public QualityGateAssert isUnstableTotalThresholdSet(boolean isUnstableTotalThresholdSet) {
        isNotNull();

        if (actual.hasUnstableTotalThreshold() != isUnstableTotalThresholdSet) {
            failWithMessage("Expected hasUnstableLowPriorityThreshold to be <%s> but was <%s>", isUnstableTotalThresholdSet, actual.hasUnstableTotalThreshold());
        }
        return this;
    }

    /**
     * Checks whether a QualityGate unstableLowPriorityThreshold is set.
     *
     * @param isUnstableLowPriorityThresholdSet
     *         specifying isUnstableLowPriorityThresholdSet.
     *
     * @return this
     */

    public QualityGateAssert isUnstableLowPriorityThresholdSet(boolean isUnstableLowPriorityThresholdSet) {
        isNotNull();

        if (actual.hasUnstableLowPriorityThreshold() != isUnstableLowPriorityThresholdSet) {
            failWithMessage("Expected hasUnstableLowPriorityThreshold to be <%s> but was <%s>", isUnstableLowPriorityThresholdSet, actual.hasUnstableLowPriorityThreshold());
        }
        return this;
    }

    /**
     * Checks whether a QualityGate unstableNormalPriorityThreshold is set.
     *
     * @param isUnstableNormalPriorityThresholdSet
     *         specifying isUnstableNormalPriorityThresholdSet.
     *
     * @return this
     */

    public QualityGateAssert isUnstableNormalPriorityThresholdSet(boolean isUnstableNormalPriorityThresholdSet) {
        isNotNull();

        if (actual.hasUnstableNormalPriorityThreshold() != isUnstableNormalPriorityThresholdSet) {
            failWithMessage("Expected hasUnstableNormalPriorityThreshold to be <%s> but was <%s>", isUnstableNormalPriorityThresholdSet, actual.hasUnstableNormalPriorityThreshold());
        }
        return this;
    }

    /**
     * Checks whether a QualityGate unstableHighPriorityThreshold is set.
     *
     * @param isUnstableHighPriorityThresholdSet
     *         specifying isUnstableHighPriorityThresholdSet.
     *
     * @return this
     */

    public QualityGateAssert isUnstableHighPriorityThresholdSet(boolean isUnstableHighPriorityThresholdSet) {
        isNotNull();

        if (actual.hasUnstableHighPriorityThreshold() != isUnstableHighPriorityThresholdSet) {
            failWithMessage("Expected hasUnstableHighPriorityThreshold to be <%s> but was <%s>", isUnstableHighPriorityThresholdSet, actual.hasUnstableHighPriorityThreshold());
        }
        return this;
    }

    /**
     * Checks whether a QualityGate unstableNewTotalThreshold is set.
     *
     * @param isUnstableNewTotalThresholdSet
     *         specifying isUnstableNewTotalThresholdSet.
     *
     * @return this
     */

    public QualityGateAssert isUnstableNewTotalThresholdSet(boolean isUnstableNewTotalThresholdSet) {
        isNotNull();

        if (actual.hasUnstableNewTotalThreshold() != isUnstableNewTotalThresholdSet) {
            failWithMessage("Expected hasUnstableNewTotalThreshold to be <%s> but was <%s>", isUnstableNewTotalThresholdSet, actual.hasUnstableNewTotalThreshold());
        }
        return this;
    }

    /**
     * Checks whether a QualityGate unstableNewLowPriorityThreshold is set.
     *
     * @param isUnstableNewLowPriorityThresholdSet
     *         specifying isUnstableNewLowPriorityThresholdSet.
     *
     * @return this
     */

    public QualityGateAssert isUnstableNewLowPriorityThresholdSet(boolean isUnstableNewLowPriorityThresholdSet) {
        isNotNull();

        if (actual.hasUnstableNewLowPriorityThreshold() != isUnstableNewLowPriorityThresholdSet) {
            failWithMessage("Expected hasUnstableNewLowPriorityThreshold to be <%s> but was <%s>", isUnstableNewLowPriorityThresholdSet, actual.hasUnstableNewLowPriorityThreshold());
        }
        return this;
    }

    /**
     * Checks whether a QualityGate unstableNewNormalPriorityThreshold is set.
     *
     * @param isUnstableNewNormalPriorityThresholdSet
     *         specifying isUnstableNewNormalPriorityThresholdSet.
     *
     * @return this
     */

    public QualityGateAssert isUnstableNewNormalPriorityThresholdSet(boolean isUnstableNewNormalPriorityThresholdSet) {
        isNotNull();

        if (actual.hasUnstableNewNormalPriorityThreshold() != isUnstableNewNormalPriorityThresholdSet) {
            failWithMessage("Expected hasUnstableNewNormalPriorityThreshold to be <%s> but was <%s>", isUnstableNewNormalPriorityThresholdSet, actual.hasUnstableNewNormalPriorityThreshold());
        }
        return this;
    }

    /**
     * Checks whether a QualityGate unstableNewHighPriorityThreshold is set.
     *
     * @param isUnstableNewHighPriorityThresholdSet
     *         specifying isUnstableNewHighPriorityThresholdSet.
     *
     * @return this
     */

    public QualityGateAssert isUnstableNewHighPriorityThresholdSet(boolean isUnstableNewHighPriorityThresholdSet) {
        isNotNull();

        if (actual.hasUnstableNewHighPriorityThreshold() != isUnstableNewHighPriorityThresholdSet) {
            failWithMessage("Expected hasUnstableNewHighPriorityThreshold to be <%s> but was <%s>", isUnstableNewHighPriorityThresholdSet, actual.hasUnstableNewHighPriorityThreshold());
        }
        return this;
    }


}
