package hudson.plugins.analysis.util;

import java.util.Collections;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Provides a simple access to the element of a singleton collection.
 *
 * @see Collections#singleton(Object)
 * @see Collections#singletonList(Object)
 * @author Ulli Hafner
 */
public final class Singleton {
    /**
     * Returns the element of the singleton collection.
     *
     * @param collection
     *            the singleton collection to get the element from
     * @return the element of the singleton collection
     * @param <T> the type of the element
     * @throws IllegalArgumentException
     *             if the collection does not contain exactly one non-null element
     */
    public static <T> T get(final Iterable<T> collection) {
        Iterator<T> iterator = collection.iterator();
        if (!iterator.hasNext()) {
            throw new NoSuchElementException("Collection is empty");
        }
        T element = iterator.next();
        if (element == null) {
            throw new NullPointerException("Collection element is null"); // NOPMD
        }
        if (iterator.hasNext()) {
            throw new IllegalArgumentException("Collection size != 1");
        }

        return element;
    }

    private Singleton() {
        // prevents instantiation
    }
}

