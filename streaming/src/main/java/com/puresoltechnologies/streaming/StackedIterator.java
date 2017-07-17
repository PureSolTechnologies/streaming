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
 *            is the type of the final iterator.
 */
public class StackedIterator<T, F> extends AbstractIterator<F> {

    public static interface Creator<T, F> {

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
