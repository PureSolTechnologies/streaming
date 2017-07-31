package com.puresoltechnologies.streaming;

import java.util.Iterator;
import java.util.Objects;

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

    /**
     * This is the converter interface for {@link ConvertingStreamIterator}.
     * 
     * @author Rick-Rainer Ludwig
     *
     * @param <T>
     *            the original type.
     * @param <F>
     *            the final type.
     */
    public static interface Converter<T, F> {

	/**
	 * Converts the next element of the iterator from T to F.
	 * 
	 * @param iterator
	 *            is the iterator where the element was taken from. It can be used
	 *            to read and skip more elements if the conversion needs to use
	 *            multiple elements.
	 * @param element
	 *            is the current element of the iterator which was retrieved
	 *            already.
	 * @return A new element of type F is returned.
	 */
	F convert(Iterator<T> iterator, T element);

    }

    private final Iterator<T> origin;
    private final Converter<T, F> converter;

    public ConvertingStreamIterator(Iterator<T> origin, Converter<T, F> converter) {
	Objects.requireNonNull(origin, "Origin iterator must not be null");
	Objects.requireNonNull(converter, "Converter must not be null");
	this.origin = origin;
	this.converter = converter;
    }

    @Override
    protected F findNext() {
	if (origin.hasNext()) {
	    T next = origin.next();
	    return converter.convert(origin, next);
	}
	return null;
    }

}
