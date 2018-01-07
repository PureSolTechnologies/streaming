package com.puresoltechnologies.streaming;

/**
 * This is a special {@link StreamIterable} interface to also provide a
 * {@link #close()} method for iterables based on streams or other resources
 * which need to be closed.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <T>
 *            the element types of the iterable.
 */
public interface CloseableStreamIterable<T> extends StreamIterable<T>, AutoCloseable {

    /**
     * This method factory creates a new {@link StreamIterable} out of the provided
     * {@link StreamIterator}.
     * 
     * @param iterator
     *            is the iterator to use.
     * @return A newly create iterable is returned.
     */
    public static <S> CloseableStreamIterable<S> of(CloseableStreamIterator<S> iterator) {
	return new CloseableStreamIterable<S>() {

	    @Override
	    public CloseableStreamIterator<S> iterator() {
		return iterator;
	    }

	    @Override
	    public void close() throws Exception {
		iterator.close();
	    }
	};
    }

    @Override
    public StreamIterator<T> iterator();

}
