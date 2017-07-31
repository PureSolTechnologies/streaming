package com.puresoltechnologies.streaming;

import java.util.Iterator;
import java.util.Objects;

/**
 * This iterator is a special decorator for {@link Iterator} which adds a
 * filtering capability.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <T>
 *            is the element type of the iterator.
 */
public class FilteringStreamIterator<T> extends AbstractStreamIterator<T> {

    /**
     * Interface for filter for {@link FilteringStreamIterator}.
     * 
     * @author Rick-Rainer Ludwig
     *
     * @param <T>
     *            is the element type of the iterator.
     */
    public static interface Filter<T> {

	/**
	 * Tests whether a certain element is valid and should be present in final
	 * iterator.
	 * 
	 * @param iterator
	 *            is the iterator where the element was taken from. It can be used
	 *            to skip more elements if the filters needs to use filter multiple
	 *            elements.
	 * @param element
	 *            is the current element of the iterator which was retrieved
	 *            already.
	 * @return <code>true</code> is return in case the element is to be returned by
	 *         the iterator. <code>false</code> is returned otherwise.
	 */
	boolean valid(Iterator<T> iterator, T element);

    }

    private final Iterator<T> origin;
    private final Filter<T> filter;

    public FilteringStreamIterator(Iterator<T> origin, Filter<T> filter) {
	Objects.requireNonNull(origin, "Origin iterator must not be null");
	Objects.requireNonNull(filter, "Filter must not be null");
	this.origin = origin;
	this.filter = filter;
    }

    @Override
    protected T findNext() {
	while (origin.hasNext()) {
	    T next = origin.next();
	    if (filter.valid(origin, next)) {
		return next;
	    }
	}
	return null;
    }

}
