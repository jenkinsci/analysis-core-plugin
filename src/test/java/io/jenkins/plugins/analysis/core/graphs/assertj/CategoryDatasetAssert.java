package io.jenkins.plugins.analysis.core.graphs.assertj;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import org.assertj.core.api.AbstractAssert;
import org.jfree.data.category.CategoryDataset;

import static java.util.Collections.*;

/**
 * Assertions for {@link CategoryDataset}.
 *
 * @author Marcel Binder
 */
public class CategoryDatasetAssert extends AbstractAssert<CategoryDatasetAssert, CategoryDataset> {
    private static final String EXPECTED_BUT_WAS_MESSAGE = "%nExpecting %s of:%n <%s>%nto be:%n <%s>%nbut was:%n <%s>.";

    /**
     * Creates a new {@link CategoryDataset} to make assertions on actual {@link CategoryDataset}.
     *
     * @param actual
     *         the {@link CategoryDataset} we want to make assertions on
     */
    CategoryDatasetAssert(final CategoryDataset actual) {
        super(actual, CategoryDatasetAssert.class);
    }

    /**
     * Creates a new {@link CategoryDatasetAssert} to make assertions on actual {@link CategoryDataset}.
     *
     * @param actual
     *         the {@link CategoryDataset} we want to make assertions on
     *
     * @return a new {@link CategoryDatasetAssert}
     */
    public static CategoryDatasetAssert assertThat(final CategoryDataset actual) {
        return new CategoryDatasetAssert(actual);
    }

    public CategoryDatasetAssert hasValues(final List<List<Integer>> expectedValues) {
        isNotNull();

        List<List<Integer>> actualValues = valuesOf(actual);
        if (!Objects.equals(actualValues, expectedValues)) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, "values", actual, expectedValues, actualValues);
        }

        return this;
    }

    private static List<List<Integer>> valuesOf(final CategoryDataset dataset) {
        int columnCount = dataset.getColumnCount();
        int rowCount = dataset.getRowCount();

        if (columnCount == 0) {
            return emptyList();
        }

        List<List<Integer>> values = new ArrayList<>();
        for (int column = 0; column < columnCount; column++) {
            List<Integer> rowValues = new ArrayList<>();
            for (int row = 0; row < rowCount; row++) {
                rowValues.add(dataset.getValue(row, column).intValue());
            }
            values.add(rowValues);
        }
        return values;
    }

    public CategoryDatasetAssert hasColumnKeys(final String... expectedColumnKeys) {
        isNotNull();

        int columnCount = actual.getColumnCount();
        String[] actualColumnKeys = IntStream.range(0, columnCount)
                .mapToObj(actual::getColumnKey)
                .map(Object::toString)
                .toArray(String[]::new);
        if (!Arrays.equals(actualColumnKeys, expectedColumnKeys)) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, "column keys", Arrays.toString(actualColumnKeys), Arrays.toString(expectedColumnKeys));
        }

        return this;
    }

    public CategoryDatasetAssert hasRowKeys(final String... expectedRowKeys) {
        isNotNull();

        int rowCount = actual.getRowCount();
        String[] actualRowKeys = IntStream.range(0, rowCount)
                .mapToObj(actual::getRowKey)
                .map(Object::toString)
                .toArray(String[]::new);
        if (!Arrays.equals(actualRowKeys, expectedRowKeys)) {
            failWithMessage(EXPECTED_BUT_WAS_MESSAGE, "row keys", actualRowKeys, expectedRowKeys);
        }

        return this;
    }
}
