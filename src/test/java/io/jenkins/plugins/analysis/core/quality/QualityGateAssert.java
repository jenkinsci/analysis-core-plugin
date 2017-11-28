package io.jenkins.plugins.analysis.core.quality;

import org.assertj.core.api.AbstractAssert;

public class QualityGateAssert extends AbstractAssert<QualityGateAssert, QualityGate> {

    private static final String EXPECTED_BUT_WAS_MESSAGE = "Expected to be <%s> but was <%s>.";

    public QualityGateAssert(final QualityGate qualityGate) {
        super(qualityGate, QualityGateAssert.class);
    }

    public static QualityGateAssert assertThat(final QualityGate actual) {
        return new QualityGateAssert(actual);
    }

    public QualityGateAssert hasTotalFailedAllPriorities(final int totalFailedAllPriorities) {
        isNotNull();

        if (actual.getTotalFailedAllPriorities() != totalFailedAllPriorities) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, totalFailedAllPriorities, actual.getTotalFailedAllPriorities());
        }
        return this;
    }

    public QualityGateAssert hasTotalFailedHighPriority(final int totalFailedHighPriority) {
        isNotNull();

        if (actual.getTotalFailedHighPriority() != totalFailedHighPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, totalFailedHighPriority, actual.getTotalFailedHighPriority());
        }
        return this;
    }

    public QualityGateAssert hasTotalFailedNormalPriority(final int totalFailedNormalPriority) {
        isNotNull();

        if (actual.getTotalFailedNormalPriority() != totalFailedNormalPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, totalFailedNormalPriority, actual.getTotalFailedNormalPriority());
        }
        return this;
    }

    public QualityGateAssert hasTotalFailedLowPriority(final int totalFailedLowPriority) {
        isNotNull();

        if (actual.getTotalFailedLowPriority() != totalFailedLowPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, totalFailedLowPriority, actual.getTotalFailedLowPriority());
        }
        return this;
    }

    public QualityGateAssert hasTotalUnstableAllPriorities(final int totalUnstableAllPriorities) {
        isNotNull();

        if (actual.getTotalUnstableAllPriorities() != totalUnstableAllPriorities) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, totalUnstableAllPriorities, actual.getTotalUnstableAllPriorities());
        }
        return this;
    }

    public QualityGateAssert hasTotalUnstableHighPriority(final int totalUnstableHighPriority) {
        isNotNull();

        if (actual.getTotalUnstableHighPriority() != totalUnstableHighPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, totalUnstableHighPriority, actual.getTotalUnstableHighPriority());
        }
        return this;
    }

    public QualityGateAssert hasTotalUnstableNormalPriority(final int totalUnstableNormalPriority) {
        isNotNull();

        if (actual.getTotalUnstableNormalPriority() != totalUnstableNormalPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, totalUnstableNormalPriority, actual.getTotalUnstableNormalPriority());
        }
        return this;
    }

    public QualityGateAssert hasTotalUnstableLowPriority(final int totalUnstableLowPriority) {
        isNotNull();

        if (actual.getTotalUnstableLowPriority() != totalUnstableLowPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, totalUnstableLowPriority, actual.getTotalUnstableLowPriority());
        }
        return this;
    }

    public QualityGateAssert hasNewFailedAllPriorities(final int newFailedAllPriorities) {
        isNotNull();

        if (actual.getNewFailedAllPriorities() != newFailedAllPriorities) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, newFailedAllPriorities, actual.getNewFailedAllPriorities());
        }
        return this;
    }

    public QualityGateAssert hasNewFailedHighPriority(final int newFailedHighPriority) {
        isNotNull();

        if (actual.getNewFailedHighPriority() != newFailedHighPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, newFailedHighPriority, actual.getNewFailedHighPriority());
        }
        return this;
    }

    public QualityGateAssert hasNewFailedNormalPriority(final int newFailedNormalPriority) {
        isNotNull();

        if (actual.getNewFailedNormalPriority() != newFailedNormalPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, newFailedNormalPriority, actual.getNewFailedNormalPriority());
        }
        return this;
    }

    public QualityGateAssert hasNewFailedLowPriority(final int newFailedLowPriority) {
        isNotNull();

        if (actual.getNewFailedLowPriority() != newFailedLowPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, newFailedLowPriority, actual.getNewFailedLowPriority());
        }
        return this;
    }

    public QualityGateAssert hasNewUnstableAllPriorities(final int newUnstableAllPriorities) {
        isNotNull();

        if (actual.getNewUnstableAllPriorities() != newUnstableAllPriorities) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, newUnstableAllPriorities, actual.getNewUnstableAllPriorities());
        }
        return this;
    }

    public QualityGateAssert hasNewUnstableHighPriority(final int newUnstableHighPriority) {
        isNotNull();

        if (actual.getNewUnstableHighPriority() != newUnstableHighPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, newUnstableHighPriority, actual.getNewUnstableHighPriority());
        }
        return this;
    }

    public QualityGateAssert hasNewUnstableNormalPriority(final int newUnstableNormalPriority) {
        isNotNull();

        if (actual.getNewUnstableNormalPriority() != newUnstableNormalPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, newUnstableNormalPriority, actual.getNewUnstableNormalPriority());
        }
        return this;
    }

    public QualityGateAssert hasNewUnstableLowPriority(final int newUnstableLowPriority) {
        isNotNull();

        if (actual.getNewUnstableLowPriority() != newUnstableLowPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, newUnstableLowPriority, actual.getNewUnstableLowPriority());
        }
        return this;
    }

    public QualityGateAssert hasIsTotalFailedAllPriorities(final boolean isTotalFailedAllPriorities) {
        isNotNull();

        if (actual.isTotalFailedAllPriorities() != isTotalFailedAllPriorities) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isTotalFailedAllPriorities, actual.isTotalFailedAllPriorities());
        }
        return this;
    }

    public QualityGateAssert hasIsTotalFailedHighPriority(final boolean isTotalFailedHighPriority) {
        isNotNull();

        if (actual.isTotalFailedHighPriority() != isTotalFailedHighPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isTotalFailedHighPriority, actual.isTotalFailedHighPriority());
        }
        return this;
    }

    public QualityGateAssert hasIsTotalFailedNormalPriority(final boolean isTotalFailedNormalPriority) {
        isNotNull();

        if (actual.isTotalFailedNormalPriority() != isTotalFailedNormalPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isTotalFailedNormalPriority, actual.isTotalFailedNormalPriority());
        }
        return this;
    }

    public QualityGateAssert hasIsTotalFailedLowPriority(final boolean isTotalFailedLowPriority) {
        isNotNull();

        if (actual.isTotalFailedLowPriority() != isTotalFailedLowPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isTotalFailedLowPriority, actual.isTotalFailedLowPriority());
        }
        return this;
    }

    public QualityGateAssert hasIsTotalUnstableAllPriorities(final boolean isTotalUnstableAllPriorities) {
        isNotNull();

        if (actual.isTotalUnstableAllPriorities() != isTotalUnstableAllPriorities) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isTotalUnstableAllPriorities, actual.isTotalUnstableAllPriorities());
        }
        return this;
    }

    public QualityGateAssert hasIsTotalUnstableHighPriority(final boolean isTotalUnstableHighPriority) {
        isNotNull();

        if (actual.isTotalUnstableHighPriority() != isTotalUnstableHighPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isTotalUnstableHighPriority, actual.isTotalUnstableHighPriority());
        }
        return this;
    }

    public QualityGateAssert hasIsTotalUnstableNormalPriority(final boolean isTotalUnstableNormalPriority) {
        isNotNull();

        if (actual.isTotalUnstableNormalPriority() != isTotalUnstableNormalPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isTotalUnstableNormalPriority, actual.isTotalUnstableNormalPriority());
        }
        return this;
    }

    public QualityGateAssert hasIsTotalUnstableLowPriority(final boolean isTotalUnstableLowPriority) {
        isNotNull();

        if (actual.isTotalUnstableLowPriority() != isTotalUnstableLowPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isTotalUnstableLowPriority, actual.isTotalUnstableLowPriority());
        }
        return this;
    }

    public QualityGateAssert hasIsNewFailedAllPriorities(final boolean isNewFailedAllPriorities) {
        isNotNull();

        if (actual.isNewFailedAllPriorities() != isNewFailedAllPriorities) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isNewFailedAllPriorities, actual.isNewFailedAllPriorities());
        }
        return this;
    }

    public QualityGateAssert hasIsNewFailedHighPriority(final boolean isNewFailedHighPriority) {
        isNotNull();

        if (actual.isNewFailedHighPriority() != isNewFailedHighPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isNewFailedHighPriority, actual.isNewFailedHighPriority());
        }
        return this;
    }

    public QualityGateAssert hasIsNewFailedNormalPriority(final boolean isNewFailedNormalPriority) {
        isNotNull();

        if (actual.isNewFailedNormalPriority() != isNewFailedNormalPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isNewFailedNormalPriority, actual.isNewFailedNormalPriority());
        }
        return this;
    }

    public QualityGateAssert hasIsNewFailedLowPriority(final boolean isNewFailedLowPriority) {
        isNotNull();

        if (actual.isNewFailedLowPriority() != isNewFailedLowPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isNewFailedLowPriority, actual.isNewFailedLowPriority());
        }
        return this;
    }

    public QualityGateAssert hasIsNewUnstableAllPriorities(final boolean isNewUnstableAllPriorities) {
        isNotNull();

        if (actual.isNewUnstableAllPriorities() != isNewUnstableAllPriorities) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isNewUnstableAllPriorities, actual.isNewUnstableAllPriorities());
        }
        return this;
    }

    public QualityGateAssert hasIsNewUnstableHighPriority(final boolean isNewUnstableHighPriority) {
        isNotNull();

        if (actual.isNewUnstableHighPriority() != isNewUnstableHighPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isNewUnstableHighPriority, actual.isNewUnstableHighPriority());
        }
        return this;
    }

    public QualityGateAssert hasIsNewUnstableNormalPriority(final boolean isNewUnstableNormalPriority) {
        isNotNull();

        if (actual.isNewUnstableNormalPriority() != isNewUnstableNormalPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isNewUnstableNormalPriority, actual.isNewUnstableNormalPriority());
        }
        return this;
    }

    public QualityGateAssert hasIsNewUnstableLowPriority(final boolean isNewUnstableLowPriority) {
        isNotNull();

        if (actual.isNewUnstableLowPriority() != isNewUnstableLowPriority) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, isNewUnstableLowPriority, actual.isNewUnstableLowPriority());
        }
        return this;
    }


}
