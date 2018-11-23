package com.puresoltechnologies.streaming.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * This is the interface for a iterator over a stream. It extends the
 * {@link Iterator} interface by a functionality to peek for the next element
 * without moving forward. But, the main difference is that no <code>null</code>
 * elements can be returned by {@link #next()} and {@link #peek()}. Streams
 * cannot have <code>null</code> (empty) elements, because then they are already
 * at their end. In this case, a {@link NoSuchElementException} is thrown.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <T> is the element type of the iterator.
 */
public interface StreamIterator<T> extends Iterator<T> {

    /**
     * This method creates an empty stream iterator. This is sometimes needed in
     * case a <code>null</code> is to be avoided and an empty result is to be
     * provided.
     * 
     * @return An empty {@link StreamIterator} is returned.
     */
    public static <T> StreamIterator<T> empty() {
	return new AbstractStreamIterator<T>() {
	    @Override
	    protected T findNext() {
		return null;
	    }
	};
    }

    /**
     * Converts an {@link Iterator} into a {@link StreamIterator}. The result is an
     * iterator without any <code>null</code> elements.
     * 
     * @param iterator is the iterator to be converter.
     * @return A {@link StreamIterator} object is return containing all
     *         non-<code>null</code> elements in same order.
     */
    public static <T> StreamIterator<T> of(Iterator<T> iterator) {
	return new AbstractStreamIterator<T>() {
	    @Override
	    protected T findNext() {
		while (iterator.hasNext()) {
		    T next = iterator.next();
		    if (next != null) {
			return next;
		    }
		}
		return null;
	    }
	};
    }

    /**
     * This method overwrites {@link Iterator#next()} with a one change: The
     * resulting element is never <code>null</code>.
     * 
     * @throws NoSuchElementException is thrown in case the stream has reached its
     *                                end.
     * @return An object of generic type T is returned. If the stream has reached
     *         its end, a {@link NoSuchElementException} is thrown.
     *         {@link #hasNext()} will returned <code>false</code> in this case and
     *         should be asked for the presence of more elements in advance. The
     *         result is never <code>null</code>.
     */
    @Override
    T next() throws NoSuchElementException;

    /**
     * This method returns the next element which will be returned by next(),
     * without moving forward.
     * 
     * @throws NoSuchElementException is thrown in case the stream has reached its
     *                                end.
     * @return An object of generic type T is returned. If the stream has reached
     *         its end, a {@link NoSuchElementException} is thrown.
     *         {@link #hasNext()} will returned <code>false</code> in this case and
     *         should be asked for the presence of more elements in advance. The
     *         result is never <code>null</code>.
     */
    T peek() throws NoSuchElementException;

    /**
     * This method provides a {@link Spliterator} for this {@link StreamIterator}.
     * The Spliterator has an unknown size and is guaranteed to have non-null
     * values.
     * 
     * @return A {@link Spliterator} is returned.
     */
    default Spliterator<T> spliterator() {
	return Spliterators.spliteratorUnknownSize(this, Spliterator.NONNULL);
    }

    /**
     * This method creates a {@link Stream} based on this {@link StreamIterator}.
     * The stream has an unknown size and non-null values.
     * 
     * @return A {@link Stream} is returned.
     */
    default Stream<T> stream() {
	return StreamSupport.stream(spliterator(), false);
    }

    /**
     * This method maps the elements of this {@link StreamIterator} to elements of a
     * new type.
     * 
     * @param mapper is the {@link Function} to convert the elements into new type.
     * @return A new {@link StreamIterator} is returned.
     */
    default <R> StreamIterator<R> map(Function<T, R> mapper) {
	return new ConvertingStreamIterator<>(this, mapper);
    }

    /**
     * This method maps the elements of this {@link StreamIterator} to more elements
     * of another type.
     * 
     * @param mapper is the {@link Function} to convert the elements into new
     *               elements.
     * @return A new {@link StreamIterator} is returned.
     */
    default <R> StreamIterator<R> flatMap(Function<T, ? extends Iterator<R>> mapper) {
	return new StackedStreamIterator<>(this, mapper);
    }

    /**
     * This method filters the elements of this {@link StreamIterator}.
     * 
     * @param filter is the {@link Predicate} to signal the elements to be filtered
     *               out.
     * @return A new {@link StreamIterator} is returned.
     */
    default StreamIterator<T> filter(Predicate<T> filter) {
	return new FilteringStreamIterator<>(this, filter);
    }

}
