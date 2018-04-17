package io.jenkins.plugins.analysis.core.history;

import static org.junit.jupiter.api.Assertions.*;

import hudson.model.Run;

class PreviousRunReferenceTest extends ReferenceFinderTest {

    @Override
    ReferenceFinder getReferenceFinder(final Run baseline, final ResultSelector resultSelector) {
        return new PreviousRunReference(baseline, resultSelector,true);
    }
}