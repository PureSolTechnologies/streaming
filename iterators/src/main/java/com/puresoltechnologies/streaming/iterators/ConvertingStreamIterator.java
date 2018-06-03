package com.puresoltechnologies.streaming.iterators;

import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;

/**
 * This is a special decorator for {@link Iterator} to convert the iterator
 * elements into a new type.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <T>
 *            is the element type of the base iterator.
 * @param <F>
 *            is the element type of this iterator.
 */
public class ConvertingStreamIterator<T, F> extends AbstractStreamIterator<F> {

    private final Iterator<T> origin;
    private final Function<T, F> converter;

    public ConvertingStreamIterator(Iterator<T> origin, Function<T, F> converter) {
	Objects.requireNonNull(origin, "Origin iterator must not be null");
	Objects.requireNonNull(converter, "Converter must not be null");
	this.origin = origin;
	this.converter = converter;
    }

    @Override
    protected F findNext() {
	if (origin.hasNext()) {
	    T next = origin.next();
	    return converter.apply(next);
	}
	return null;
    }

}
