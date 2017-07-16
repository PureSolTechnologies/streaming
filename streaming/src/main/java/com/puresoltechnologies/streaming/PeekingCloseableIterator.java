package com.puresoltechnologies.streaming;

/**
 * This interface is a combination of {@link CloseableIterator} and
 * {@link PeekingIterator}.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <T>
 */
public interface PeekingCloseableIterator<T> extends CloseableIterator<T>, PeekingIterator<T> {
}
