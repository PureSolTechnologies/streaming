package com.puresoltechnologies.streaming;

import java.util.Iterator;

/**
 * This is a special interface to extend the {@link Iterator} interface by a
 * functionality to peek for the next element without moving forward.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <T>
 *            is the element type of the iterator.
 */
public interface PeekingIterator<T> extends Iterator<T> {

    /**
     * This method returns the next element which will be returned by next(),
     * without moving forward.
     * 
     * @return An object of generic type T is returned. <code>null</code> is
     *         returned in case no element can be returned with next.
     *         {@link #hasNext()} will returned <code>false</code> in this case.
     */
    public T peek();

}
