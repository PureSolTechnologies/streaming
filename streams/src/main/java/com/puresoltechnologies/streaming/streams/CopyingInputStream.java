package com.puresoltechnologies.streaming.streams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This is a special input stream, which copies all bytes read into a new output
 * stream. This can be used to create buffers in the background, logs and other
 * useful things.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public class CopyingInputStream extends DelegatingInputStream {

    private static final int MAX_SKIP_BUFFER_SIZE = 2048;
    private final OutputStream outputStream;

    /**
     * This constructor is used to initialize the copying input stream.
     * 
     * @param inputStream
     *            is the input stream to read from.
     * @param outputStream
     *            is the output stream to copy every byte read from input stream to.
     */
    public CopyingInputStream(InputStream inputStream, OutputStream outputStream) {
	super(inputStream);
	this.outputStream = outputStream;
    }

    @Override
    public int read() throws IOException {
	int b = super.read();
	if (b != -1) {
	    outputStream.write(b);
	}
	return b;
    }

    @Override
    public int read(byte[] buffer) throws IOException {
	int b = super.read(buffer);
	if (b != -1) {
	    outputStream.write(buffer, 0, b);
	}
	return b;
    }

    @Override
    public int read(byte[] buffer, int off, int len) throws IOException {
	int b = super.read(buffer, off, len);
	if (b != -1) {
	    outputStream.write(buffer, off, b);
	}
	return b;
    }

    @Override
    public long skip(long n) throws IOException {
	if (n <= 0) {
	    return 0;
	}
	long remaining = n;
	int bufferSize = (int) Math.min(MAX_SKIP_BUFFER_SIZE, remaining);
	byte[] skipBuffer = new byte[bufferSize];
	while (remaining > 0) {
	    int nr = super.read(skipBuffer, 0, (int) Math.min(bufferSize, remaining));
	    if (nr < 0) {
		break;
	    }
	    outputStream.write(skipBuffer, 0, nr);
	    remaining -= nr;
	}
	return n - remaining;
    }

    @Override
    public void close() throws IOException {
	super.close();
	outputStream.flush();
	outputStream.close();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((outputStream == null) ? 0 : outputStream.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	CopyingInputStream other = (CopyingInputStream) obj;
	if (outputStream == null) {
	    if (other.outputStream != null)
		return false;
	} else if (!outputStream.equals(other.outputStream))
	    return false;
	return true;
    }

}
