package com.puresoltechnologies.streaming;

import java.io.Closeable;
import java.util.Iterator;

/**
 * This interface is used to combine an {@link Iterator} with {@link Closeable}
 * to have an iterator over a closable resource like files.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <T>
 *            is the element type of the iterator.
 */
public interface CloseableIterator<T> extends Iterator<T>, Closeable {
}
