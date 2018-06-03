package com.puresoltechnologies.streaming.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an abstract implementation of an iterator to be used as base class
 * for more complex iterators to assure the {@link Iterator} contract.
 * 
 * Because this is a stream iterator meant for complex streams,
 * <code>null</code> values are not supported.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <T>
 *            is the element type of the iterator.
 */
public abstract class AbstractStreamIterator<T> implements StreamIterator<T> {

    private T next = null;

    /**
     * This method looks for the next entry to be returned. If no further entry is
     * found, <code>null</code> is returned.
     * 
     * @return An object of T is returned representing the next entry to be returned
     *         by the iterator. <code>null</code> is returned in case no more
     *         entries are available.
     */
    protected abstract T findNext();

    @Override
    public final boolean hasNext() {
	if (next == null) {
	    next = findNext();
	}
	return next != null;
    }

    @Override
    public final T next() {
	if (!hasNext()) {
	    throw new NoSuchElementException("No more elements in this iterator.");
	}
	T result = next;
	next = null;
	return result;
    }

    @Override
    public final T peek() {
	if (!hasNext()) {
	    throw new NoSuchElementException("No more elements in this iterator.");
	}
	return next;
    }

}
