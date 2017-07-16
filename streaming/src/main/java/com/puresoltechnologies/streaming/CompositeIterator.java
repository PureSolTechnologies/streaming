package com.puresoltechnologies.streaming;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class CompositeIterator<T> extends AbstractIterator<T> {

    private final List<Iterator<T>> iterators = new ArrayList<Iterator<T>>();
    private Iterator<T> currentIterator = null;

    public CompositeIterator(Iterator<T>... iterators) {
	for (Iterator<T> iterator : iterators) {
	    this.iterators.add(iterator);
	}
    }

    public CompositeIterator(Collection<? extends Iterator<T>> iterators) {
	this.iterators.addAll(iterators);
    }

    @Override
    protected T findNext() {
	if (currentIterator == null) {
	    if (iterators.size() == 0) {
		return null;
	    }
	    currentIterator = iterators.remove(0);
	}
	if (!currentIterator.hasNext()) {
	    currentIterator = null;
	    return null;
	}
	return currentIterator.next();
    }

}
