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

        builder.setFailureTotalThreshold(0);
        builder.setFailureLowPriorityThreshold(0);
        builder.setFailureNormalPriorityThreshold(0);
        builder.setFailureHighPriorityThreshold(0);

        builder.setFailureNewTotalThreshold(0);
        builder.setFailureNewLowPriorityThreshold(0);
        builder.setFailureNewNormalPriorityThreshold(0);
        builder.setFailureNewHighPriorityThreshold(0);

        builder.setUnstableTotalThreshold(0);
        builder.setUnstableLowPriorityThreshold(0);
        builder.setUnstableNormalPriorityThreshold(0);
        builder.setUnstableHighPriorityThreshold(0);

        builder.setUnstableNewTotalThreshold(0);
        builder.setUnstableNewLowPriorityThreshold(0);
        builder.setUnstableNewNormalPriorityThreshold(0);
        builder.setUnstableNewHighPriorityThreshold(0);


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

        builder.setFailureTotalThreshold(0);
        builder.setFailureLowPriorityThreshold(1);
        builder.setFailureNormalPriorityThreshold(2);
        builder.setFailureHighPriorityThreshold(3);

        builder.setFailureNewTotalThreshold(4);
        builder.setFailureNewLowPriorityThreshold(5);
        builder.setFailureNewNormalPriorityThreshold(6);
        builder.setFailureNewHighPriorityThreshold(7);

        builder.setUnstableTotalThreshold(8);
        builder.setUnstableLowPriorityThreshold(9);
        builder.setUnstableNormalPriorityThreshold(10);
        builder.setUnstableHighPriorityThreshold(11);

        builder.setUnstableNewTotalThreshold(12);
        builder.setUnstableNewLowPriorityThreshold(13);
        builder.setUnstableNewNormalPriorityThreshold(14);
        builder.setUnstableNewHighPriorityThreshold(15);


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