package com.puresoltechnologies.streaming.streams;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class SeekableInputStream extends InputStream {

    private final InputStreamCreator<?> supplier;
    private CountingInputStream inputStream = null;

    public SeekableInputStream(File file) throws IOException {
	this(() -> {
	    return new BufferedInputStream(new FileInputStream(file));
	});
    }

    public SeekableInputStream(InputStreamCreator<?> supplier) throws IOException {
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
	    seek(newPosition);
	    return newPosition - lastPosition;
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
}
