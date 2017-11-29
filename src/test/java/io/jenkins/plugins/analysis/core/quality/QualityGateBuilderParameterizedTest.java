package io.jenkins.plugins.analysis.core.quality;

import java.util.Arrays;
import java.util.function.Consumer;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static hudson.model.Result.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import hudson.model.Result;

/**
 * Parameterized test for {@link QualityGateBuilder}
 */
class QualityGateBuilderParameterizedTest {

    private static Iterable<Object> getTestData() {
        return Arrays.asList(
                Arguments.of(
                        "Should create gate for total",
                        QualityGateBuilder.createFailure()
                                .setTotalThreshold(5)
                                .build(),
                        createRun(cfg -> when(cfg.getTotalSize()).thenReturn(5)),
                        FAILURE
                ),
                Arguments.of(
                        "Should create gate for total high",
                        QualityGateBuilder.createUnstable()
                                .setTotalHighThreshold(2)
                                .build(),
                        createRun(cfg -> when(cfg.getTotalHighPrioritySize()).thenReturn(12)),
                        UNSTABLE
                ),
                Arguments.of(
                        "Should create gate for total normal",
                        QualityGateBuilder.createFailure()
                                .setTotalNormalThreshold(24)
                                .build(),
                        createRun(cfg -> when(cfg.getTotalNormalPrioritySize()).thenReturn(99)),
                        FAILURE
                ),
                Arguments.of(
                        "Should create gate for total low",
                        QualityGateBuilder.createUnstable()
                                .setTotalLowThreshold(3)
                                .build(),
                        createRun(cfg -> when(cfg.getTotalLowPrioritySize()).thenReturn(3)),
                        UNSTABLE
                ),
                Arguments.of(
                        "Should create gate for total new",
                        QualityGateBuilder.createFailure()
                                .setTotalNewThreshold(1)
                                .build(),
                        createRun(cfg -> when(cfg.getNewSize()).thenReturn(2)),
                        FAILURE
                ),
                Arguments.of(
                        "Should create gate for high new",
                        QualityGateBuilder.createUnstable()
                                .setNewHighTheshold(13)
                                .build(),
                        createRun(cfg -> when(cfg.getNewHighPrioritySize()).thenReturn(14)),
                        UNSTABLE
                ),
                Arguments.of(
                        "Should create gate for normal new",
                        QualityGateBuilder.createFailure()
                                .setNewNormalThreshold(2)
                                .build(),
                        createRun(cfg -> when(cfg.getNewNormalPrioritySize()).thenReturn(3)),
                        FAILURE
                ),
                Arguments.of(
                        "Should create gate for low new",
                        QualityGateBuilder.createUnstable()
                                .setNewLowThreshold(123)
                                .build(),
                        createRun(cfg -> when(cfg.getNewLowPrioritySize()).thenReturn(124)),
                        UNSTABLE
                )
        );

    }

    @ParameterizedTest(name = "{0}")
    @MethodSource("getTestData")
    void shouldCreateGateForTotal(final String name, final QualityGate gate, final StaticAnalysisRun run, final Result result) {

        assertThat(gate.isEnabled())
                .isTrue();

        assertThat(gate.getResult(run))
                .isEqualTo(result);
    }


    private static StaticAnalysisRun createRun(Consumer<StaticAnalysisRun> mockConfig) {
        StaticAnalysisRun run = mock(StaticAnalysisRun.class);
        mockConfig.accept(run);
        return run;
    }

}