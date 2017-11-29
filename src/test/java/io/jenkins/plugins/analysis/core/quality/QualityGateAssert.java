package io.jenkins.plugins.analysis.core.quality;

import org.assertj.core.api.AbstractAssert;

/**
 * Assertions for {@link QualityGate}.
 *
 * @author Tom Maier
 */
public class QualityGateAssert extends AbstractAssert<QualityGateAssert, QualityGate> {

    private static final String EXPECTED_BUT_WAS_MESSAGE = "Expected to be <%s> but was <%s>.";

    /**
     * Creates a new {@link QualityGateAssert} to make assertions on actual {@link QualityGate}.
     *
     * @param qualityGate
     *         the issue we want to make assertions on
     */
    public QualityGateAssert(final QualityGate qualityGate) {
        super(qualityGate, QualityGateAssert.class);
    }

    /**
     * Creates a new {@link QualityGateAssert} to make assertions on actual {@link QualityGate}.
     *
     * @param actual the issue we want to make assertions on
     * @return a new {@link QualityGateAssert}
     */
    public static QualityGateAssert assertThat(final QualityGate actual) {
        return new QualityGateAssert(actual);
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param totalFailedAllPriorities int specific priority.
     * @return this
     */
    public QualityGateAssert hasTotalFailedAllPriorities(final int totalFailedAllPriorities) {
        isNotNull();

        if (actual.getTotalFailedAllPriorities() != totalFailedAllPriorities) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, totalFailedAllPriorities, actual.getTotalFailedAllPriorities());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param totalFailedHighPriority int specific priority.
     * @return this
     */
    public QualityGateAssert hasTotalFailedHighPriority(final int totalFailedHighPriority) {
        isNotNull();

        if (actual.getTotalFailedHighPriority() != totalFailedHighPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, totalFailedHighPriority, actual.getTotalFailedHighPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param totalFailedNormalPriority int specific priority.
     * @return this
     */
    public QualityGateAssert hasTotalFailedNormalPriority(final int totalFailedNormalPriority) {
        isNotNull();

        if (actual.getTotalFailedNormalPriority() != totalFailedNormalPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, totalFailedNormalPriority, actual.getTotalFailedNormalPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param totalFailedLowPriority int specific priority.
     * @return this
     */
    public QualityGateAssert hasTotalFailedLowPriority(final int totalFailedLowPriority) {
        isNotNull();

        if (actual.getTotalFailedLowPriority() != totalFailedLowPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, totalFailedLowPriority, actual.getTotalFailedLowPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param totalUnstableAllPriorities int specific priority.
     * @return this
     */
    public QualityGateAssert hasTotalUnstableAllPriorities(final int totalUnstableAllPriorities) {
        isNotNull();

        if (actual.getTotalUnstableAllPriorities() != totalUnstableAllPriorities) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, totalUnstableAllPriorities, actual.getTotalUnstableAllPriorities());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param totalUnstableHighPriority int specific priority.
     * @return this
     */
    public QualityGateAssert hasTotalUnstableHighPriority(final int totalUnstableHighPriority) {
        isNotNull();

        if (actual.getTotalUnstableHighPriority() != totalUnstableHighPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, totalUnstableHighPriority, actual.getTotalUnstableHighPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param totalUnstableNormalPriority int specific priority.
     * @return this
     */
    public QualityGateAssert hasTotalUnstableNormalPriority(final int totalUnstableNormalPriority) {
        isNotNull();

        if (actual.getTotalUnstableNormalPriority() != totalUnstableNormalPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, totalUnstableNormalPriority, actual.getTotalUnstableNormalPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param totalUnstableLowPriority int specific priority.
     * @return this
     */
    public QualityGateAssert hasTotalUnstableLowPriority(final int totalUnstableLowPriority) {
        isNotNull();

        if (actual.getTotalUnstableLowPriority() != totalUnstableLowPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, totalUnstableLowPriority, actual.getTotalUnstableLowPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param newFailedAllPriorities int specific priority.
     * @return this
     */
    public QualityGateAssert hasNewFailedAllPriorities(final int newFailedAllPriorities) {
        isNotNull();

        if (actual.getNewFailedAllPriorities() != newFailedAllPriorities) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, newFailedAllPriorities, actual.getNewFailedAllPriorities());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param newFailedHighPriority int specific priority.
     * @return this
     */
    public QualityGateAssert hasNewFailedHighPriority(final int newFailedHighPriority) {
        isNotNull();

        if (actual.getNewFailedHighPriority() != newFailedHighPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, newFailedHighPriority, actual.getNewFailedHighPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param newFailedNormalPriority int specific priority.
     * @return this
     */
    public QualityGateAssert hasNewFailedNormalPriority(final int newFailedNormalPriority) {
        isNotNull();

        if (actual.getNewFailedNormalPriority() != newFailedNormalPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, newFailedNormalPriority, actual.getNewFailedNormalPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param newFailedLowPriority int specific priority.
     * @return this
     */
    public QualityGateAssert hasNewFailedLowPriority(final int newFailedLowPriority) {
        isNotNull();

        if (actual.getNewFailedLowPriority() != newFailedLowPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, newFailedLowPriority, actual.getNewFailedLowPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param newUnstableAllPriorities int specific priority.
     * @return this
     */
    public QualityGateAssert hasNewUnstableAllPriorities(final int newUnstableAllPriorities) {
        isNotNull();

        if (actual.getNewUnstableAllPriorities() != newUnstableAllPriorities) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, newUnstableAllPriorities, actual.getNewUnstableAllPriorities());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param newUnstableHighPriority int specific priority.
     * @return this
     */
    public QualityGateAssert hasNewUnstableHighPriority(final int newUnstableHighPriority) {
        isNotNull();

        if (actual.getNewUnstableHighPriority() != newUnstableHighPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, newUnstableHighPriority, actual.getNewUnstableHighPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param newUnstableNormalPriority int specific priority.
     * @return this
     */
    public QualityGateAssert hasNewUnstableNormalPriority(final int newUnstableNormalPriority) {
        isNotNull();

        if (actual.getNewUnstableNormalPriority() != newUnstableNormalPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, newUnstableNormalPriority, actual.getNewUnstableNormalPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param newUnstableLowPriority int specific priority.
     * @return this
     */
    public QualityGateAssert hasNewUnstableLowPriority(final int newUnstableLowPriority) {
        isNotNull();

        if (actual.getNewUnstableLowPriority() != newUnstableLowPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, newUnstableLowPriority, actual.getNewUnstableLowPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param isTotalFailedAllPriorities boolean specific priority exists.
     * @return this
     */
    public QualityGateAssert hasIsTotalFailedAllPriorities(final boolean isTotalFailedAllPriorities) {
        isNotNull();

        if (actual.hasTotalFailedAllPriorities() != isTotalFailedAllPriorities) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isTotalFailedAllPriorities, actual.hasTotalFailedAllPriorities());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param isTotalFailedHighPriority boolean specific priority exists.
     * @return this
     */
    public QualityGateAssert hasIsTotalFailedHighPriority(final boolean isTotalFailedHighPriority) {
        isNotNull();

        if (actual.hasTotalFailedHighPriority() != isTotalFailedHighPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isTotalFailedHighPriority, actual.hasTotalFailedHighPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param isTotalFailedNormalPriority boolean specific priority exists.
     * @return this
     */
    public QualityGateAssert hasIsTotalFailedNormalPriority(final boolean isTotalFailedNormalPriority) {
        isNotNull();

        if (actual.hasTotalFailedNormalPriority() != isTotalFailedNormalPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isTotalFailedNormalPriority, actual.hasTotalFailedNormalPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param isTotalFailedLowPriority boolean specific priority exists.
     * @return this
     */
    public QualityGateAssert hasIsTotalFailedLowPriority(final boolean isTotalFailedLowPriority) {
        isNotNull();

        if (actual.hasTotalFailedLowPriority() != isTotalFailedLowPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isTotalFailedLowPriority, actual.hasTotalFailedLowPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param isTotalUnstableAllPriorities boolean specific priority exists.
     * @return this
     */
    public QualityGateAssert hasIsTotalUnstableAllPriorities(final boolean isTotalUnstableAllPriorities) {
        isNotNull();

        if (actual.hasTotalUnstableAllPriorities() != isTotalUnstableAllPriorities) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isTotalUnstableAllPriorities, actual.hasTotalUnstableAllPriorities());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param isTotalUnstableHighPriority boolean specific priority exists.
     * @return this
     */
    public QualityGateAssert hasIsTotalUnstableHighPriority(final boolean isTotalUnstableHighPriority) {
        isNotNull();

        if (actual.hasTotalUnstableHighPriority() != isTotalUnstableHighPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isTotalUnstableHighPriority, actual.hasTotalUnstableHighPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param isTotalUnstableNormalPriority boolean specific priority exists.
     * @return this
     */
    public QualityGateAssert hasIsTotalUnstableNormalPriority(final boolean isTotalUnstableNormalPriority) {
        isNotNull();

        if (actual.hasTotalUnstableNormalPriority() != isTotalUnstableNormalPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isTotalUnstableNormalPriority, actual.hasTotalUnstableNormalPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param isTotalUnstableLowPriority boolean specific priority exists.
     * @return this
     */
    public QualityGateAssert hasIsTotalUnstableLowPriority(final boolean isTotalUnstableLowPriority) {
        isNotNull();

        if (actual.hasTotalUnstableLowPriority() != isTotalUnstableLowPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isTotalUnstableLowPriority, actual.hasTotalUnstableLowPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param isNewFailedAllPriorities boolean specific priority exists.
     * @return this
     */
    public QualityGateAssert hasIsNewFailedAllPriorities(final boolean isNewFailedAllPriorities) {
        isNotNull();

        if (actual.hasNewFailedAllPriorities() != isNewFailedAllPriorities) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isNewFailedAllPriorities, actual.hasNewFailedAllPriorities());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param isNewFailedHighPriority boolean specific priority exists.
     * @return this
     */
    public QualityGateAssert hasIsNewFailedHighPriority(final boolean isNewFailedHighPriority) {
        isNotNull();

        if (actual.hasNewFailedHighPriority() != isNewFailedHighPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isNewFailedHighPriority, actual.hasNewFailedHighPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param isNewFailedNormalPriority boolean specific priority exists.
     * @return this
     */
    public QualityGateAssert hasIsNewFailedNormalPriority(final boolean isNewFailedNormalPriority) {
        isNotNull();

        if (actual.hasNewFailedNormalPriority() != isNewFailedNormalPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isNewFailedNormalPriority, actual.hasNewFailedNormalPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param isNewFailedLowPriority boolean specific priority exists.
     * @return this
     */
    public QualityGateAssert hasIsNewFailedLowPriority(final boolean isNewFailedLowPriority) {
        isNotNull();

        if (actual.hasNewFailedLowPriority() != isNewFailedLowPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isNewFailedLowPriority, actual.hasNewFailedLowPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param isNewUnstableAllPriorities boolean specific priority exists.
     * @return this
     */
    public QualityGateAssert hasIsNewUnstableAllPriorities(final boolean isNewUnstableAllPriorities) {
        isNotNull();

        if (actual.hasNewUnstableAllPriorities() != isNewUnstableAllPriorities) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isNewUnstableAllPriorities, actual.hasNewUnstableAllPriorities());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param isNewUnstableHighPriority boolean specific priority exists.
     * @return this
     */
    public QualityGateAssert hasIsNewUnstableHighPriority(final boolean isNewUnstableHighPriority) {
        isNotNull();

        if (actual.hasNewUnstableHighPriority() != isNewUnstableHighPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isNewUnstableHighPriority, actual.hasNewUnstableHighPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param isNewUnstableNormalPriority boolean specific priority exists.
     * @return this
     */
    public QualityGateAssert hasIsNewUnstableNormalPriority(final boolean isNewUnstableNormalPriority) {
        isNotNull();

        if (actual.hasNewUnstableNormalPriority() != isNewUnstableNormalPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isNewUnstableNormalPriority, actual.hasNewUnstableNormalPriority());
        }
        return this;
    }

    /**
     * Checks whether an QualityGate has a specific priority.
     *
     * @param isNewUnstableLowPriority boolean specific priority exists.
     * @return this
     */
    public QualityGateAssert hasIsNewUnstableLowPriority(final boolean isNewUnstableLowPriority) {
        isNotNull();

        if (actual.hasNewUnstableLowPriority() != isNewUnstableLowPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isNewUnstableLowPriority, actual.hasNewUnstableLowPriority());
        }
        return this;
    }

}
