package com.puresoltechnologies.streaming;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Predicate;

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

    private final Iterator<T> origin;
    private final Predicate<T> filter;

    public FilteringStreamIterator(Iterator<T> origin, Predicate<T> filter) {
	Objects.requireNonNull(origin, "Origin iterator must not be null");
	Objects.requireNonNull(filter, "Filter must not be null");
	this.origin = origin;
	this.filter = filter;
    }

    @Override
    protected T findNext() {
	while (origin.hasNext()) {
	    T next = origin.next();
	    if (filter.test(next)) {
		return next;
	    }
	}
	return null;
    }

}
