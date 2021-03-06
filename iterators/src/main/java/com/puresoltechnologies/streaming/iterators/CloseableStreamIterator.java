package com.puresoltechnologies.streaming.iterators;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * This is a special {@link StreamIterator} interface to also provide a
 * {@link #close()} method for iterators based on streams or other resources
 * which need to be closed.
 *
 * @author Rick-Rainer Ludwig
 *
 * @param <T> the element types of the iterator.
 */
public interface CloseableStreamIterator<T> extends StreamIterator<T>, AutoCloseable {

	/**
	 * This method creates an empty closable stream iterator. This is sometimes
	 * needed in case a <code>null</code> is to be avoided and an empty result is to
	 * be provided.
	 *
	 * @param <T> is the type of iterator elements.
	 * @return An empty {@link CloseableStreamIterator} is returned.
	 */
	public static <T> CloseableStreamIterator<T> empty() {
		return CloseableStreamIterator.of(StreamIterator.empty());
	}

	/**
	 * Converts an {@link Iterator} into a {@link CloseableStreamIterator} by
	 * utilizing {@link StreamIterator#of(Iterator)}. The result is an iterator
	 * without any <code>null</code> elements which also supports the close which is
	 * needed.
	 *
	 * @param iterator       is the iterator to be converter.
	 * @param autoCloseables is an array of resources to be closed during the close
	 *                       of the new stream iterator.
	 * @param <T>            is the element type of the iterator.
	 * @param <I>            is the iterator of with element type T and additional
	 *                       {@link AutoCloseable} interface.
	 * @return A {@link StreamIterator} object is return containing all
	 *         non-<code>null</code> elements in same order.
	 *
	 */
	public static <T, I extends Iterator<T>> CloseableStreamIterator<T> of(I iterator,
			AutoCloseable... autoCloseables) {
		StreamIterator<T> streamIterator = StreamIterator.of(iterator);
		return new CloseableStreamIterator<T>() {

			@Override
			public T next() throws NoSuchElementException {
				return streamIterator.next();
			}

			@Override
			public T peek() throws NoSuchElementException {
				return streamIterator.peek();
			}

			@Override
			public boolean hasNext() {
				return streamIterator.hasNext();
			}

			@Override
			public void close() throws Exception {
				for (AutoCloseable autoCloseable : autoCloseables) {
					autoCloseable.close();
				}
			}
		};
	}

	/**
	 * Converts an {@link Iterator} into a {@link CloseableStreamIterator} by
	 * utilizing {@link StreamIterator#of(Iterator)}. The result is an iterator
	 * without any <code>null</code> elements which also supports the close which is
	 * needed.
	 *
	 * @param iterator       is the iterator to be converter.
	 * @param autoCloseables is an array of resources to be closed during the close
	 *                       of the new stream iterator.
	 * @param <T>            is the element type of the iterator.
	 * @param <I>            is the iterator of with element type T and additional
	 *                       {@link AutoCloseable} interface.
	 * @return A {@link StreamIterator} object is return containing all
	 *         non-<code>null</code> elements in same order.
	 *
	 */
	public static <T, I extends Iterator<T>> CloseableStreamIterator<T> of(I iterator,
			Collection<AutoCloseable> autoCloseables) {
		StreamIterator<T> streamIterator = StreamIterator.of(iterator);
		return new CloseableStreamIterator<T>() {

			@Override
			public T next() throws NoSuchElementException {
				return streamIterator.next();
			}

			@Override
			public T peek() throws NoSuchElementException {
				return streamIterator.peek();
			}

			@Override
			public boolean hasNext() {
				return streamIterator.hasNext();
			}

			@Override
			public void close() throws Exception {
				for (AutoCloseable autoCloseable : autoCloseables) {
					autoCloseable.close();
				}
			}
		};
	}

	/**
	 * Converts an {@link Iterator} into a {@link CloseableStreamIterator} by
	 * utilizing {@link StreamIterator#of(Iterator)}. The result is an iterator
	 * without any <code>null</code> elements which also supports the close which is
	 * needed.
	 *
	 * @param iterator is the iterator to be converter.
	 * @param <T>      is the element type of the iterator.
	 * @param <I>      is the iterator of with element type T and additional
	 *                 {@link AutoCloseable} interface.
	 * @return A {@link StreamIterator} object is return containing all
	 *         non-<code>null</code> elements in same order.
	 *
	 */
	public static <T, I extends Iterator<T> & AutoCloseable> CloseableStreamIterator<T> of(I iterator) {
		StreamIterator<T> streamIterator = StreamIterator.of(iterator);
		return new CloseableStreamIterator<T>() {

			@Override
			public T next() throws NoSuchElementException {
				return streamIterator.next();
			}

			@Override
			public T peek() throws NoSuchElementException {
				return streamIterator.peek();
			}

			@Override
			public boolean hasNext() {
				return streamIterator.hasNext();
			}

			@Override
			public void close() throws Exception {
				iterator.close();
			}
		};
	}
}
