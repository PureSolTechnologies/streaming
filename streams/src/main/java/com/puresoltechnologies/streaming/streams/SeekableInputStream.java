package com.puresoltechnologies.streaming.streams;

import java.io.IOException;
import java.io.InputStream;

public class SeekableInputStream<S extends InputStream> extends InputStream
	implements Comparable<SeekableInputStream<S>> {

    private final InputStreamCreator<?> supplier;
    private CountingInputStream inputStream = null;

    public SeekableInputStream(InputStreamCreator<S> supplier) throws IOException {
	super();
	this.supplier = supplier;
	inputStream = createNewStream();
    }

    private CountingInputStream createNewStream() throws IOException {
	return new CountingInputStream(supplier.create());
    }

    @Override
    public int read() throws IOException {
	return inputStream.read();
    }

    @Override
    public int read(byte[] b) throws IOException {
	return inputStream.read(b);
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
	return inputStream.read(b, off, len);
    }

    public long getCount() {
	return inputStream.getCount();
    }

    @Override
    public int available() throws IOException {
	return inputStream.available();
    }

    public int compareTo(CountingInputStream o) {
	return inputStream.compareTo(o);
    }

    /**
     * This method moved the current position to the new provided position. If this
     * position is pointing to a position before the current position, the input
     * stream is closed and newly created by the set {@link #supplier}.
     * 
     * @param position
     *            is the new position to point to.
     * @return
     * @throws IOException
     */
    public final long seek(long position) throws IOException {
	long currentPosition = inputStream.getCount();
	if (currentPosition < position) {
	    long skipped = inputStream.skip(position - currentPosition);
	    return currentPosition + skipped;
	} else if (currentPosition > position) {
	    inputStream.close();
	    inputStream = createNewStream();
	    return inputStream.skip(position);
	} else {
	    return getPosition();
	}
    }

    @Override
    public final long skip(long n) throws IOException {
	if (n > 0) {
	    return inputStream.skip(n);
	} else if (n < 0) {
	    long lastPosition = inputStream.getCount();
	    long newPosition = lastPosition + n;
	    if (newPosition < 0) {
		newPosition = 0;
	    }
	    inputStream.close();
	    inputStream = createNewStream();
	    return inputStream.skip(newPosition) - lastPosition;
	} else {
	    return 0;
	}
    }

    public final long getPosition() {
	return inputStream.getCount();
    }

    @Override
    public void close() throws IOException {
	inputStream.close();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((inputStream == null) ? 0 : inputStream.hashCode());
	result = prime * result + ((supplier == null) ? 0 : supplier.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	SeekableInputStream<?> other = (SeekableInputStream<?>) obj;
	if (inputStream == null) {
	    if (other.inputStream != null)
		return false;
	} else if (!inputStream.equals(other.inputStream))
	    return false;
	if (supplier == null) {
	    if (other.supplier != null)
		return false;
	} else if (!supplier.equals(other.supplier))
	    return false;
	return true;
    }

    @Override
    public int compareTo(SeekableInputStream<S> o) {
	return Long.compare(getPosition(), o.getPosition());
    }
}
