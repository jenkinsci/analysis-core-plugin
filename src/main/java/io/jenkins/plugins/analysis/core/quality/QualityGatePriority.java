package io.jenkins.plugins.analysis.core.quality;

/**
 * define witch Priority the QualityGage shout check. this is just a simple enum class with no other functionality
 */
enum QualityGatePriority {
    /**
     * Check just the high priority.
     */
    High,
    /**
     * Check just the Normal priority.
     */
    Normal,

    /**
     * Check just the Low priority.
     */
    Low,
    /**
     * Check all priority.
     */
    All
}
