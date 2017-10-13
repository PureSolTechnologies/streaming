package com.puresoltechnologies.streaming.streams;

import java.io.InputStream;

import com.puresoltechnologies.streaming.AbstractStreamIterator;

/**
 * This is a special stream iterator which takes in a simple {@link InputStream}
 * to convert it into an iterator.
 * 
 * @author Rick-Rainer Ludwig
 *
 * @param <T>
 *            is the type of the iterators elements.
 */
public class InputStreamIterator<I extends InputStream, T> extends AbstractStreamIterator<T> {

    /**
     * This interface is used to a part reader on an input stream. This reader is
     * just reading enough of the input stream to create the next element of type T
     * out of it.
     * 
     * @author Rick-Rainer Ludwig
     *
     * @param <T>
     *            is the type of the elements to be returned.
     */
    @FunctionalInterface
    public static interface InputStreamPartReader<I extends InputStream, T> {

	/**
	 * This method is implemented to read from inputStream to create on object of
	 * type T.
	 * 
	 * @param inputStream
	 *            is the {@link InputStream} to read the next object from.
	 * @return An object of T is returned.
	 */
	public T readPart(I inputStream);

    }

    private final I inputStream;
    private final InputStreamPartReader<I, T> partReader;

    public InputStreamIterator(I inputStream, InputStreamPartReader<I, T> partReader) {
	super();
	this.inputStream = inputStream;
	this.partReader = partReader;
    }

    @Override
    protected T findNext() {
	return partReader.readPart(inputStream);
    }

}
