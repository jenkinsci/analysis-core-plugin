package io.jenkins.plugins.analysis.core.quality;

import org.junit.jupiter.api.Test;

import static io.jenkins.plugins.analysis.core.quality.QualityGateSoftAssertion.*;

/**
 * Unit tests for {@link QualityGateBuilder}.
 *
 * @author Johannes Arzt
 */

class QualityGateBuilderTest {


    @Test
    void shouldBuiltQualityGateWithDefaultValues() {
        QualityGateBuilder builder = new QualityGateBuilder();

        QualityGate qualityGate = builder.build();
        assertSoftly(softly -> {
            softly.assertThat(qualityGate)
                    .hasFailureTotalThreshold(-1)
                    .hasFailureLowPriorityThreshold(-1)
                    .hasFailureNormalPriorityThreshold(-1)
                    .hasFailureHighPriorityThreshold(-1)

                    .hasFailureNewTotalThreshold(-1)
                    .hasFailureNewLowPriorityThreshold(-1)
                    .hasFailureNewNormalHighPriorityThreshold(-1)
                    .hasFailureNewHighPriorityThreshold(-1)

                    .hasUnstableTotalThreshold(-1)
                    .hasUnstableLowPriorityThreshold(-1)
                    .hasUnstableNormalPriorityThreshold(-1)
                    .hasUnstableHighPriorityThreshold(-1)

                    .hasUnstableNewTotalThreshold(-1)
                    .hasUnstableNewLowPriorityThreshold(-1)
                    .hasUnstableNewNormalPriorityThreshold(-1)
                    .hasUnstableNewHighPriorityThreshold(-1)

                    .isFailureTotalThresholdSet(false)
                    .isFailureLowPriorityThresholdSet(false)
                    .isFailureNormalPriorityThresholdSet(false)
                    .isFailureHighPriorityThresholdSet(false)

                    .isFailureNewTotalThresholdSet(false)
                    .isFailureNewLowPriorityThresholdSet(false)
                    .isFailureNewNormalPriorityThresholdSet(false)
                    .isFailureNewHighPriorityThresholdSet(false)

                    .isUnstableTotalThresholdSet(false)
                    .isUnstableLowPriorityThresholdSet(false)
                    .isUnstableNormalPriorityThresholdSet(false)
                    .isUnstableHighPriorityThresholdSet(false)

                    .isUnstableNewTotalThresholdSet(false)
                    .isUnstableNewLowPriorityThresholdSet(false)
                    .isUnstableNewNormalPriorityThresholdSet(false)
                    .isUnstableNewHighPriorityThresholdSet(false);
        });
    }

    @Test
    void shouldBuiltQualityGateWithZeroValues() {
        QualityGateBuilder builder = new QualityGateBuilder();

        builder.setFailureTotalThreshold(0)
                .setFailureLowPriorityThreshold(0)
                .setFailureNormalPriorityThreshold(0)
                .setFailureHighPriorityThreshold(0)

                .setFailureNewTotalThreshold(0)
                .setFailureNewLowPriorityThreshold(0)
                .setFailureNewNormalPriorityThreshold(0)
                .setFailureNewHighPriorityThreshold(0)

                .setUnstableTotalThreshold(0)
                .setUnstableLowPriorityThreshold(0)
                .setUnstableNormalPriorityThreshold(0)
                .setUnstableHighPriorityThreshold(0)

                .setUnstableNewTotalThreshold(0)
                .setUnstableNewLowPriorityThreshold(0)
                .setUnstableNewNormalPriorityThreshold(0)
                .setUnstableNewHighPriorityThreshold(0);


        QualityGate qualityGate = builder.build();

        assertSoftly(softly -> {
            softly.assertThat(qualityGate)
                    .hasFailureTotalThreshold(0)
                    .hasFailureLowPriorityThreshold(0)
                    .hasFailureNormalPriorityThreshold(0)
                    .hasFailureHighPriorityThreshold(0)

                    .hasFailureNewTotalThreshold(0)
                    .hasFailureNewLowPriorityThreshold(0)
                    .hasFailureNewNormalHighPriorityThreshold(0)
                    .hasFailureNewHighPriorityThreshold(0)

                    .hasUnstableTotalThreshold(0)
                    .hasUnstableLowPriorityThreshold(0)
                    .hasUnstableNormalPriorityThreshold(0)
                    .hasUnstableHighPriorityThreshold(0)

                    .hasUnstableNewTotalThreshold(0)
                    .hasUnstableNewLowPriorityThreshold(0)
                    .hasUnstableNewNormalPriorityThreshold(0)
                    .hasUnstableNewHighPriorityThreshold(0)

                    .isFailureTotalThresholdSet(true)
                    .isFailureLowPriorityThresholdSet(true)
                    .isFailureNormalPriorityThresholdSet(true)
                    .isFailureHighPriorityThresholdSet(true)

                    .isFailureNewTotalThresholdSet(true)
                    .isFailureNewLowPriorityThresholdSet(true)
                    .isFailureNewNormalPriorityThresholdSet(true)
                    .isFailureNewHighPriorityThresholdSet(true)

                    .isUnstableTotalThresholdSet(true)
                    .isUnstableLowPriorityThresholdSet(true)
                    .isUnstableNormalPriorityThresholdSet(true)
                    .isUnstableHighPriorityThresholdSet(true)

                    .isUnstableNewTotalThresholdSet(true)
                    .isUnstableNewLowPriorityThresholdSet(true)
                    .isUnstableNewNormalPriorityThresholdSet(true)
                    .isUnstableNewHighPriorityThresholdSet(true);


        });
    }

    @Test
    void shouldBuiltQualityGateWithIncrementValues() {
        QualityGateBuilder builder = new QualityGateBuilder();

        builder.setFailureTotalThreshold(0)
                .setFailureLowPriorityThreshold(1)
                .setFailureNormalPriorityThreshold(2)
                .setFailureHighPriorityThreshold(3)

                .setFailureNewTotalThreshold(4)
                .setFailureNewLowPriorityThreshold(5)
                .setFailureNewNormalPriorityThreshold(6)
                .setFailureNewHighPriorityThreshold(7)

                .setUnstableTotalThreshold(8)
                .setUnstableLowPriorityThreshold(9)
                .setUnstableNormalPriorityThreshold(10)
                .setUnstableHighPriorityThreshold(11)

                .setUnstableNewTotalThreshold(12)
                .setUnstableNewLowPriorityThreshold(13)
                .setUnstableNewNormalPriorityThreshold(14)
                .setUnstableNewHighPriorityThreshold(15);


        QualityGate qualityGate = builder.build();

        assertSoftly(softly -> {
            softly.assertThat(qualityGate)
                    .hasFailureTotalThreshold(0)
                    .hasFailureLowPriorityThreshold(1)
                    .hasFailureNormalPriorityThreshold(2)
                    .hasFailureHighPriorityThreshold(3)

                    .hasFailureNewTotalThreshold(4)
                    .hasFailureNewLowPriorityThreshold(5)
                    .hasFailureNewNormalHighPriorityThreshold(6)
                    .hasFailureNewHighPriorityThreshold(7)

                    .hasUnstableTotalThreshold(8)
                    .hasUnstableLowPriorityThreshold(9)
                    .hasUnstableNormalPriorityThreshold(10)
                    .hasUnstableHighPriorityThreshold(11)

                    .hasUnstableNewTotalThreshold(12)
                    .hasUnstableNewLowPriorityThreshold(13)
                    .hasUnstableNewNormalPriorityThreshold(14)
                    .hasUnstableNewHighPriorityThreshold(15)

                    .isFailureTotalThresholdSet(true)
                    .isFailureLowPriorityThresholdSet(true)
                    .isFailureNormalPriorityThresholdSet(true)
                    .isFailureHighPriorityThresholdSet(true)

                    .isFailureNewTotalThresholdSet(true)
                    .isFailureNewLowPriorityThresholdSet(true)
                    .isFailureNewNormalPriorityThresholdSet(true)
                    .isFailureNewHighPriorityThresholdSet(true)

                    .isUnstableTotalThresholdSet(true)
                    .isUnstableLowPriorityThresholdSet(true)
                    .isUnstableNormalPriorityThresholdSet(true)
                    .isUnstableHighPriorityThresholdSet(true)

                    .isUnstableNewTotalThresholdSet(true)
                    .isUnstableNewLowPriorityThresholdSet(true)
                    .isUnstableNewNormalPriorityThresholdSet(true)
                    .isUnstableNewHighPriorityThresholdSet(true);


        });
    }
}