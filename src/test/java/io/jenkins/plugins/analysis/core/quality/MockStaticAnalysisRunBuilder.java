package io.jenkins.plugins.analysis.core.quality;

import edu.hm.hafner.analysis.Priority;
import static org.mockito.Mockito.*;

class MockStaticAnalysisRunBuilder {
    private final StaticAnalysisRun run;

    private MockStaticAnalysisRunBuilder() {
        run = mock(StaticAnalysisRun.class);
    }

    static MockStaticAnalysisRunBuilder analysisRun() {
        return new MockStaticAnalysisRunBuilder();
    }

    MockStaticAnalysisRunBuilder withTotalIssueCount(final int issueCount) {
        when(run.getTotalSize()).thenReturn(issueCount);
        return this;
    }

    MockStaticAnalysisRunBuilder withTotalIssueCount(final Priority priority, final int issueCount) {
        when(run.getTotalSize(priority)).thenReturn(issueCount);
        return this;
    }

    MockStaticAnalysisRunBuilder withNewIssueCount(final int issueCount) {
        when(run.getNewSize()).thenReturn(issueCount);
        return this;
    }

    MockStaticAnalysisRunBuilder withNewIssueCount(final Priority priority, final int issueCount) {
        when(run.getNewSize(priority)).thenReturn(issueCount);
        return this;
    }

    StaticAnalysisRun build() {
        return run;
    }
}
