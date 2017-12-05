package io.jenkins.plugins.analysis.core.quality;

import org.junit.jupiter.api.Test;

import static io.jenkins.plugins.analysis.core.quality.QualityGateSoftAssertions.*;

/**
 * Unit test for {@link QualityGateBuilder}.
 *
 * @author Tom Maier
 */
class QualityGateBuilderTest {

    @Test
    void shouldCreateFalseQualityGate() {

        QualityGate qualityGate = new QualityGateBuilder()
                .setTotalFailedAllPriorities(-1)
                .setTotalFailedHighPriority(-1)
                .setTotalFailedNormalPriority(-1)
                .setTotalFailedLowPriority(-1)

                .setTotalUnstableAllPriorities(-1)
                .setTotalUnstableHighPriority(-1)
                .setTotalUnstableNormalPriority(-1)
                .setTotalUnstableLowPriority(-1)

                .setNewFailedAllPriorities(-1)
                .setNewFailedHighPriority(-1)
                .setNewFailedNormalPriority(-1)
                .setNewFailedLowPriority(-1)

                .setNewUnstableAllPriorities(-1)
                .setNewUnstableHighPriority(-1)
                .setNewUnstableNormalPriority(-1)
                .setNewUnstableLowPriority(-1)
                .build();

        assertSoftly(softly ->
                softly.assertThat(qualityGate)

                        .hasTotalFailedAllPriorities(-1)
                        .hasTotalFailedHighPriority(-1)
                        .hasTotalFailedNormalPriority(-1)
                        .hasTotalFailedLowPriority(-1)

                        .hasTotalUnstableAllPriorities(-1)
                        .hasTotalUnstableHighPriority(-1)
                        .hasTotalUnstableNormalPriority(-1)
                        .hasTotalUnstableLowPriority(-1)

                        .hasNewFailedAllPriorities(-1)
                        .hasNewFailedHighPriority(-1)
                        .hasNewFailedNormalPriority(-1)
                        .hasNewFailedLowPriority(-1)

                        .hasNewUnstableAllPriorities(-1)
                        .hasNewUnstableHighPriority(-1)
                        .hasNewUnstableNormalPriority(-1)
                        .hasNewUnstableLowPriority(-1)

                        .hasIsTotalFailedAllPriorities(false)
                        .hasIsTotalFailedHighPriority(false)
                        .hasIsTotalFailedNormalPriority(false)
                        .hasIsTotalFailedLowPriority(false)

                        .hasIsTotalUnstableAllPriorities(false)
                        .hasIsTotalUnstableHighPriority(false)
                        .hasIsTotalUnstableNormalPriority(false)
                        .hasIsTotalUnstableLowPriority(false)

                        .hasIsNewFailedAllPriorities(false)
                        .hasIsNewFailedHighPriority(false)
                        .hasIsNewFailedNormalPriority(false)
                        .hasIsNewFailedLowPriority(false)

                        .hasIsNewUnstableAllPriorities(false)
                        .hasIsNewUnstableHighPriority(false)
                        .hasIsNewUnstableNormalPriority(false)
                        .hasIsNewUnstableLowPriority(false)
        );
    }

    @Test
    void shouldCreateTrueQualityGate() {

        QualityGate qualityGate = new QualityGateBuilder()
                .setTotalFailedAllPriorities(0)
                .setTotalFailedHighPriority(0)
                .setTotalFailedNormalPriority(0)
                .setTotalFailedLowPriority(0)

                .setTotalUnstableAllPriorities(0)
                .setTotalUnstableHighPriority(0)
                .setTotalUnstableNormalPriority(0)
                .setTotalUnstableLowPriority(0)

                .setNewFailedAllPriorities(0)
                .setNewFailedHighPriority(0)
                .setNewFailedNormalPriority(0)
                .setNewFailedLowPriority(0)

                .setNewUnstableAllPriorities(0)
                .setNewUnstableHighPriority(0)
                .setNewUnstableNormalPriority(0)
                .setNewUnstableLowPriority(0)
                .build();

        assertSoftly(softly ->
                softly.assertThat(qualityGate)

                        .hasTotalFailedAllPriorities(0)
                        .hasTotalFailedHighPriority(0)
                        .hasTotalFailedNormalPriority(0)
                        .hasTotalFailedLowPriority(0)

                        .hasTotalUnstableAllPriorities(0)
                        .hasTotalUnstableHighPriority(0)
                        .hasTotalUnstableNormalPriority(0)
                        .hasTotalUnstableLowPriority(0)

                        .hasNewFailedAllPriorities(0)
                        .hasNewFailedHighPriority(0)
                        .hasNewFailedNormalPriority(0)
                        .hasNewFailedLowPriority(0)

                        .hasNewUnstableAllPriorities(0)
                        .hasNewUnstableHighPriority(0)
                        .hasNewUnstableNormalPriority(0)
                        .hasNewUnstableLowPriority(0)

                        .hasIsTotalFailedAllPriorities(true)
                        .hasIsTotalFailedHighPriority(true)
                        .hasIsTotalFailedNormalPriority(true)
                        .hasIsTotalFailedLowPriority(true)

                        .hasIsTotalUnstableAllPriorities(true)
                        .hasIsTotalUnstableHighPriority(true)
                        .hasIsTotalUnstableNormalPriority(true)
                        .hasIsTotalUnstableLowPriority(true)

                        .hasIsNewFailedAllPriorities(true)
                        .hasIsNewFailedHighPriority(true)
                        .hasIsNewFailedNormalPriority(true)
                        .hasIsNewFailedLowPriority(true)

                        .hasIsNewUnstableAllPriorities(true)
                        .hasIsNewUnstableHighPriority(true)
                        .hasIsNewUnstableNormalPriority(true)
                        .hasIsNewUnstableLowPriority(true)
        );
    }

    @Test
    void shouldCreateTrue2QualityGate() {

        QualityGate qualityGate = new QualityGateBuilder()
                .setTotalFailedAllPriorities(1)
                .setTotalFailedHighPriority(2)
                .setTotalFailedNormalPriority(3)
                .setTotalFailedLowPriority(4)

                .setTotalUnstableAllPriorities(5)
                .setTotalUnstableHighPriority(6)
                .setTotalUnstableNormalPriority(7)
                .setTotalUnstableLowPriority(8)

                .setNewFailedAllPriorities(9)
                .setNewFailedHighPriority(10)
                .setNewFailedNormalPriority(11)
                .setNewFailedLowPriority(12)

                .setNewUnstableAllPriorities(13)
                .setNewUnstableHighPriority(14)
                .setNewUnstableNormalPriority(15)
                .setNewUnstableLowPriority(16)
                .build();

        assertSoftly(softly ->
                softly.assertThat(qualityGate)

                        .hasTotalFailedAllPriorities(1)
                        .hasTotalFailedHighPriority(2)
                        .hasTotalFailedNormalPriority(3)
                        .hasTotalFailedLowPriority(4)

                        .hasTotalUnstableAllPriorities(5)
                        .hasTotalUnstableHighPriority(6)
                        .hasTotalUnstableNormalPriority(7)
                        .hasTotalUnstableLowPriority(8)

                        .hasNewFailedAllPriorities(9)
                        .hasNewFailedHighPriority(10)
                        .hasNewFailedNormalPriority(11)
                        .hasNewFailedLowPriority(12)

                        .hasNewUnstableAllPriorities(13)
                        .hasNewUnstableHighPriority(14)
                        .hasNewUnstableNormalPriority(15)
                        .hasNewUnstableLowPriority(16)

                        .hasIsTotalFailedAllPriorities(true)
                        .hasIsTotalFailedHighPriority(true)
                        .hasIsTotalFailedNormalPriority(true)
                        .hasIsTotalFailedLowPriority(true)

                        .hasIsTotalUnstableAllPriorities(true)
                        .hasIsTotalUnstableHighPriority(true)
                        .hasIsTotalUnstableNormalPriority(true)
                        .hasIsTotalUnstableLowPriority(true)

                        .hasIsNewFailedAllPriorities(true)
                        .hasIsNewFailedHighPriority(true)
                        .hasIsNewFailedNormalPriority(true)
                        .hasIsNewFailedLowPriority(true)

                        .hasIsNewUnstableAllPriorities(true)
                        .hasIsNewUnstableHighPriority(true)
                        .hasIsNewUnstableNormalPriority(true)
                        .hasIsNewUnstableLowPriority(true)
        );
    }
}
