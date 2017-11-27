package io.jenkins.plugins.analysis.core.quality;

import java.util.ArrayList;
import java.util.List;

import edu.hm.hafner.analysis.Issues;

import hudson.model.Result;

/**
 * Enforces the defined quality gates.
 *
 * @author Ullrich Hafner
 */
public class QualityGateEnforcer {

    private List<QualityGate> allChecks;

    QualityGateEnforcer() {
        allChecks = new ArrayList<>();

    }

    /**
     * Return the worser Result.
     * @param r1 result a
     * @param r2 result b
     * @return the worser one
     */
    private Result getWorserOne(Result r1, Result r2) {
        Result out;
        if (r1.isWorseOrEqualTo(r2)) {
            out = r1;
        }
        else {
            out = r2;
        }
        return out;

    }

    void addQualityGate(QualityGate toAdd) {
        allChecks.add(toAdd);
    }

    Result evaluate(final StaticAnalysisRun run) {
        Result output = Result.SUCCESS;

        for (QualityGate toCheck : allChecks) {
            output = getWorserOne(toCheck.evaluate(run), output);
        }
        return output;
    }


}
