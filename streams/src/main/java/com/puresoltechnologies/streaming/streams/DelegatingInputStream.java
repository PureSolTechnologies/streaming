package com.puresoltechnologies.streaming.streams;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class is used to create a clean, delegating and explicitly defined
 * {@link InputStream} implementation for all other input stream implementations
 * provided in this library.
 * 
 * Main reasons:
 * <ul>
 * <li>skip is not completely save to always skip the required amount of data.
 * This leads to ugly results. We assure here, that the amount is skipped, if
 * possible.</li>
 * <li>It is explicitly switched of to mark and reset.</li>
 * </ul>
 * 
 * @author Rick-Rainer Ludwig
 */
public class DelegatingInputStream extends InputStream {

    private final InputStream inputStream;

    public DelegatingInputStream(InputStream inputStream) {
	super();
	this.inputStream = inputStream;
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

    /**
     * This method overrides the {@link InputStream#skip(long)} method to extend the
     * contract of the method:
     * <ul>
     * <li>This method tries to skip n bytes until the end of the stream or until an
     * exception is thrown. This fixes the strange behavior, that some input streams
     * like {@link BufferedInputStream} only skip a certain amount of bytes (in this
     * case until the end of the current buffer) with the first call. Subsequent
     * calls skip more bytes later on. This is fixed here.</li>
     * <li>The contract tells, that a negative number of bytes are skipped as zero
     * bytes. This changes here and an IOException is thrown providing the hint,
     * that the current input stream is not supporting back skips.</li>
     * </ul>
     * 
     * @param n
     *            is the number bytes to skip.
     * @return The actual number of bytes skipped are returned.
     */
    @Override
    public long skip(long n) throws IOException {
	if (n <= 0) {
	    if (n == 0) {
		return 0;
	    }
	    throw new IOException("Negative back skips are not supported by this stream implementaion.");
	}
	long count = 0;
	long skipped = 0;
	do {
	    skipped = inputStream.skip(n);
	    count += skipped;
	    n -= skipped;
	} while ((n > 0) && (skipped > 0));
	return count;
    }

    @Override
    public int available() throws IOException {
	return inputStream.available();
    }

    @Override
    public void close() throws IOException {
	inputStream.close();
    }

    @Override
    public void mark(int readlimit) {
	/*
	 * Explicitly left blank, because mark and reset are not supported. Mark does
	 * not do any harm. So, it is inspired by original InputStream implementation to
	 * keep this method empty.
	 */
    }

    @Override
    public void reset() throws IOException {
	throw new IOException("mark/reset not supported");
    }

    @Override
    public boolean markSupported() {
	return false;
    }

    @Override
    public int hashCode() {
	return inputStream.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
	return inputStream.equals(obj);
    }

    @Override
    public String toString() {
	return inputStream.toString();
    }
}
