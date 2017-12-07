package io.jenkins.plugins.analysis.core.quality;

public class QualityGateBuilder {

    private TresholdSet failureTresholdSet = new TresholdSet();
    private TresholdSet unstableTresholdSet = new TresholdSet();

    /**
     * Set the number of allowed issues before the build should evaluate to failure.
     *
     * @param any
     *         number of total issues
     * @param high
     *         number of high priority issues
     * @param medium
     *         number of normal priority issues
     * @param low
     *         number of low priority issues
     */
    public void setAllowedTotalBeforeFail(int any, int high, int medium, int low) {
        failureTresholdSet.setTotal(any, high, medium, low);
    }


    /**
     * Set the number of allowed new issues before the build should evaluate to failure.
     *
     * @param any
     *         number of new issues
     * @param high
     *         number of new high priority issues
     * @param medium
     *         number of new normal priority issues
     * @param low
     *         number of new low priority issues
     */
    public void setAllowedNewBeforeFail(int any, int high, int medium, int low) {
        failureTresholdSet.setNew(any, high, medium, low);
    }

    /**
     * Set the number of allowed issues before the build should evaluate to unstable.
     *
     * @param any
     *         number of total issues
     * @param high
     *         number of high priority issues
     * @param medium
     *         number of normal priority issues
     * @param low
     *         number of low priority issues
     */
    public void setAllowedTotalBeforeUnstable(int any, int high, int medium, int low) {
        unstableTresholdSet.setTotal(any, high, medium, low);
    }

    /**
     * Set the number of allowed new issues before the build should evaluate to unstable.
     *
     * @param any
     *         number of new issues
     * @param high
     *         number of new high priority issues
     * @param medium
     *         number of new normal priority issues
     * @param low
     *         number of new low priority issues
     */
    public void setAllowedNewBeforeUnstable(int any, int high, int medium, int low) {
        unstableTresholdSet.setNew(any, high, medium, low);
    }

    /**
     * Factory method.
     *
     * @return valid instance of {@link QualityGate}
     */
    public QualityGate build() {
        return new QualityGate(failureTresholdSet, unstableTresholdSet);
    }
}
