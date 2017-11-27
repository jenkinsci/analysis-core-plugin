package io.jenkins.plugins.analysis.core.graphs.assertj;

import org.jfree.data.category.CategoryDataset;

/**
 * Custom assertions for {@link CategoryDatasetAssert}.
 *
 * @author Marcel Binder
 */
public class Assertions extends org.assertj.core.api.Assertions {
    /**
     * Creates a new {@link CategoryDatasetAssert} to make assertions on actual {@link CategoryDataset}.
     *
     * @param actual
     *         the issue we want to make assertions on
     *
     * @return a new {@link CategoryDatasetAssert}
     */
    public static CategoryDatasetAssert assertThat(final CategoryDataset actual) {
        return new CategoryDatasetAssert(actual);
    }
}
