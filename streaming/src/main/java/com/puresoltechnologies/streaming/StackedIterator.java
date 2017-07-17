package com.puresoltechnologies.streaming;

import java.util.Iterator;

/**
 * This decorator is used to stack multiple iterators, like iterating over
 * directories and then on each directory iterating over the files.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <T>
 *            is the original type of the base iterator.
 * @param <F>
 *            is the type of this iterator.
 */
public class StackedIterator<T, F> extends AbstractIterator<F> {

    /**
     * This interface is used to create a new sub iterator.
     * 
     * @author Rick-Rainer Ludwig
     *
     * @param <T>
     *            is the original type.
     * @param <F>
     *            is the final type of the new iterator.
     */
    public static interface Creator<T, F> {

	/**
	 * This method creates a new iterator out of the element.
	 * 
	 * @param iterator
	 *            is the original iterator. It can be used to skip elements
	 *            if needed.
	 * @param element
	 *            is the element take off the iterator of which the new
	 *            iterator is to be created.
	 * @return A new {@link Iterator} of type F is returned.
	 */
	Iterator<F> create(Iterator<T> iterator, T element);

    }

    private final Iterator<T> origin;
    private final Creator<T, F> creator;
    private Iterator<F> currentIterator = null;

    public StackedIterator(Iterator<T> origin, Creator<T, F> creator) {
	this.origin = origin;
	this.creator = creator;
    }

    @Override
    protected F findNext() {
	while ((currentIterator == null) || (!currentIterator.hasNext())) {
	    if (!origin.hasNext()) {
		currentIterator = null;
		return null;
	    }
	    currentIterator = creator.create(origin, origin.next());
	}
	return currentIterator.next();
    }

}
