package com.puresoltechnologies.streaming.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is an {@link Iterator} decorator to count the elements retrieved via
 * {@link #next()}.
 *
 * @author Rick-Rainer Ludwig
 *
 * @param <T> is the type of the elements.
 */
public class CountingStreamIterator<T> implements StreamIterator<T> {

	/**
	 * This method creates a nre {@link CountingStreamIterator} based on another
	 * {@link Iterator}. The elements retrieved are counted by the new iterator.
	 *
	 * @param streamIterator is the base iterator for which the elements are to be
	 *                       counted.
	 * @param <I>            is the type of the base iterator.
	 * @param <T>            is the element type of the iterators.
	 * @return An {@link CompositeStreamIterator} is returned based on the provided
	 *         base iterator.
	 */
	public static <T, I extends Iterator<T>> CountingStreamIterator<T> of(I streamIterator) {
		return new CountingStreamIterator<>(streamIterator);
	}

	private final StreamIterator<T> origin;
	private long count = 0;

	public CountingStreamIterator(Iterator<T> origin) {
		this.origin = StreamIterator.of(origin);
	}

	public CountingStreamIterator(StreamIterator<T> origin) {
		this.origin = origin;
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
