package com.puresoltechnologies.streaming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * This composite iterators take multiple iterators and handles them as one
 * whole to the outside.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <T>
 *            is the element type of the iterator.
 */
public class CompositeStreamIterator<T> extends AbstractStreamIterator<T> {

    private final List<Iterator<T>> iterators = new ArrayList<>();
    private Iterator<T> currentIterator = null;

    public CompositeStreamIterator(Iterator<T>... iterators) {
	Objects.requireNonNull(iterators, "Iterators must not be null");
	for (Iterator<T> iterator : iterators) {
	    this.iterators.add(iterator);
	}
    }

    public CompositeStreamIterator(Collection<? extends Iterator<T>> iterators) {
	Objects.requireNonNull(iterators, "Iterators must not be null");
	this.iterators.addAll(iterators);
    }

    @Override
    protected T findNext() {
	while ((currentIterator == null) || (!currentIterator.hasNext())) {
	    if (iterators.size() == 0) {
		currentIterator = null;
		return null;
	    }
	    currentIterator = iterators.remove(0);
	}
	return currentIterator.next();
    }

}
