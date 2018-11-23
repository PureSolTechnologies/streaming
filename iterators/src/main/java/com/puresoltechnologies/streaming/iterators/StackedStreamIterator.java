package com.puresoltechnologies.streaming.iterators;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

/**
 * This decorator is used to stack multiple iterators, like iterating over
 * directories and then on each directory iterating over the files.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <T> is the original type of the base iterator.
 * @param <F> is the type of this iterator.
 */
public class StackedStreamIterator<T, F> extends AbstractStreamIterator<F> {

    private final Iterator<T> origin;
    private final Function<T, ? extends Iterator<F>> creator;
    private Iterator<F> currentIterator = null;

    public StackedStreamIterator(Iterator<T> origin, Function<T, ? extends Iterator<F>> creator) {
	Objects.requireNonNull(origin, "Origin iterator must not be null");
	Objects.requireNonNull(creator, "Creator must not be null");
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
	    currentIterator = creator.apply(origin.next());
	}
	return currentIterator.next();
    }

}
