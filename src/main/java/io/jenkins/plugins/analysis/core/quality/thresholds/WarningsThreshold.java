package io.jenkins.plugins.analysis.core.quality.thresholds;

/**
 * Warnings threshold to determine the quality of a build.
 */
public abstract class WarningsThreshold {

    private final int totalThreshold;
    private final int highThreshold;
    private final int normalThreshold;
    private final int lowThreshold;

    /**
     * Creates an instance of {@link WarningsThreshold}. Only the total threshold is set.
     */
    public WarningsThreshold() {
        this(0);
    }

    /**
     * Creates an instance of {@link WarningsThreshold}. Only the total threshold is set.
     * @param totalThreshold limit of issues in total
     */
    public WarningsThreshold(int totalThreshold) {
        this.totalThreshold = totalThreshold;
        this.highThreshold = 0;
        this.normalThreshold = 0;
        this.lowThreshold = 0;
    }

    /**
     * Creates an instance of {@link WarningsThreshold}. Only the total threshold is set.
     * @param high threshold for issues with priority high
     * @param normal threshold for issues with priority normal
     * @param low threshold for issues with priority low
     */
    public WarningsThreshold(int high, int normal, int low) {
        this.totalThreshold = high + normal + low;
        this.highThreshold = high;
        this.normalThreshold = normal;
        this.lowThreshold = low;
    }

    /**
     * Getter for totalThreshold.
     *
     * @return totalThreshold
     */
    public int getTotalThreshold() {
        return this.totalThreshold;
    }

    /**
     * Getter for highThreshold.
     *
     * @return highThreshold
     */
    public int getHighThreshold() {
        return this.highThreshold;
    }

    /**
     * Getter for normalThreshold.
     *
     * @return normalThreshold
     */
    public int getNormalThreshold() {
        return this.normalThreshold;
    }

    /**
     * Getter for lowThreshold.
     *
     * @return lowThreshold
     */
    public int getLowThreshold() {
        return this.lowThreshold;
    }

    /**
     * Tells if there is any threshold.
     *
     * @return {@code true} when totalThreshold is greater 0
     */
    public boolean hasThreshold() {
        return getTotalThreshold() > 0;
    }

    /**
     * Tells if there is any high threshold.
     *
     * @return {@code true} when highThreshold is greater 0
     */
    public boolean hasHighThreshold() {
        return getHighThreshold() > 0;
    }

    /**
     * Tells if there is any normal threshold.
     *
     * @return {@code true} when normalThreshold is greater 0
     */
    public boolean hasNormalThreshold() {
        return getNormalThreshold() > 0;
    }

    /**
     * Tells if there is any low threshold.
     *
     * @return {@code true} when lowThreshold is greater 0
     */
    public boolean hasLowThreshold() {
        return getLowThreshold() > 0;
    }
}
