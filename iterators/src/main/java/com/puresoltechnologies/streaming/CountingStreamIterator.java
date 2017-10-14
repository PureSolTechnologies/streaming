package com.puresoltechnologies.streaming;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an {@link Iterator} decorator to count the elements retrieved via
 * {@link #next()}.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <T>
 */
public class CountingStreamIterator<T> implements StreamIterator<T> {

    private final StreamIterator<T> origin;
    private long count = 0;

    public CountingStreamIterator(Iterator<T> origin) {
	this.origin = StreamIterator.of(origin);
    }

    /**
     * Returns the count of the elements retrieved by {@link #next()} so far.
     * 
     * @return A long is returned providing the current count.
     */
    public final long getCount() {
	return count;
    }

    @Override
    public final boolean hasNext() {
	return origin.hasNext();
    }

    @Override
    public final T next() throws NoSuchElementException {
	T next = origin.next();
	count++;
	return next;
    }

    @Override
    public final T peek() throws NoSuchElementException {
	return origin.peek();
    }

}
