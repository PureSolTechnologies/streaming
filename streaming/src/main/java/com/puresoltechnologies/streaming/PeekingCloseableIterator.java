package com.puresoltechnologies.streaming;

/**
 * This interface is a combination of {@link CloseableIterator} and
 * {@link PeekingIterator}.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <T>
 *            is the element type of the iterator.
 */
public interface PeekingCloseableIterator<T> extends CloseableIterator<T>, PeekingIterator<T> {
}
