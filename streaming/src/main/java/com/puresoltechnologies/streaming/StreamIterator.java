package com.puresoltechnologies.streaming;

import java.util.Iterator;
import java.util.NoSuchElementException;

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
 * @param <T>
 *            is the element type of the iterator.
 */
public interface StreamIterator<T> extends Iterator<T> {

    /**
     * This method overwrites {@link Iterator#next()} with a one change: The
     * resulting element is never <code>null</code>.
     * 
     * @throws NoSuchElementException
     *             is thrown in case the stream has reached its end.
     * @return An object of generic type T is returned. If the stream has reached
     *         its end, a {@link NoSuchElementException} is thrown.
     *         {@link #hasNext()} will returned <code>false</code> in this case and
     *         should be asked for the presence of more elements in advance.
     */
    @Override
    T next() throws NoSuchElementException;

    /**
     * This method returns the next element which will be returned by next(),
     * without moving forward.
     * 
     * @throws NoSuchElementException
     *             is thrown in case the stream has reached its end.
     * @return An object of generic type T is returned. If the stream has reached
     *         its end, a {@link NoSuchElementException} is thrown.
     *         {@link #hasNext()} will returned <code>false</code> in this case and
     *         should be asked for the presence of more elements in advance.
     */
    T peek() throws NoSuchElementException;

}
