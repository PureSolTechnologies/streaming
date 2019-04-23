package com.puresoltechnologies.streaming.iterators;

/**
 * This interface is used as special {@link Iterable} for
 * {@link StreamIterator}s. A special factory method helps to create the an
 * iterable out of an iterator.
 *
 * @param <T> is the type of elements.
 */
public interface StreamIterable<T> extends Iterable<T> {

	/**
	 * This method factory creates a new {@link StreamIterable} out of the provided
	 * {@link StreamIterator}.
	 *
	 * @param iterator is the iterator to use.
	 * @param <T>      is the type of elements.
	 * @return A newly create iterable is returned.
	 */
	public static <T> StreamIterable<T> of(StreamIterator<T> iterator) {
		return () -> iterator;
	}

	@Override
	public StreamIterator<T> iterator();

}
