package io.jenkins.plugins.analysis.core.quality.thresholds;

public abstract class WarningsThreshold {

    private final int totalThreshold;
    private final int highThreshold;
    private final int normalThreshold;
    private final int lowThreshold;

    public WarningsThreshold(int totalThreshold) {
        this.totalThreshold = totalThreshold;
        this.highThreshold = 0;
        this.normalThreshold = 0;
        this.lowThreshold = 0;
    }

    public WarningsThreshold(int high, int normal, int low) {
        this.totalThreshold = high + normal + low;
        this.highThreshold = high;
        this.normalThreshold = normal;
        this.lowThreshold = low;
    }

    public int getTotalThreshold() {
        return this.totalThreshold;
    }

    public int getHighThreshold() {
        return this.highThreshold;
    }

    public int getNormalThreshold() {
        return this.normalThreshold;
    }

    public int getLowThreshold() {
        return this.lowThreshold;
    }

    public boolean hasThreshold() {
        return getTotalThreshold() > 0;
    }

    public boolean hasHighThreshold() {
        return getHighThreshold() > 0;
    }

    public boolean hasNormalThreshold() {
        return getNormalThreshold() > 0;
    }

    public boolean hasLowThreshold() {
        return getLowThreshold() > 0;
    }
}
