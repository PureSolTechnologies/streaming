package com.puresoltechnologies.streaming.streams;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class provides a simple buffered {@link InputStream} to support
 * buffering for optimized reads (e.g. from file system with FS block size).
 * 
 * This implementation is not supporting mark and reset like the original
 * implementation of {@link BufferedInputStream}. Therefore, this implementation
 * is faster.
 * 
 * @author ludwig
 *
 */
public class SimpleBufferedInputStream extends InputStream {

    /**
     * The default buffer size is defined as the default file system block size of
     * many Linux systems.
     */
    private static final int DEFAULT_BUFFER_SIZE = 8192; // 8kB

    private final int bufferSize;
    private final InputStream inputStream;
    private final byte[] buffer;
    private int bufferPointer = 0;
    private int bufferBytes = 0;

    public SimpleBufferedInputStream(InputStream inputStream) throws IOException {
	this(inputStream, DEFAULT_BUFFER_SIZE);
    }

    public SimpleBufferedInputStream(InputStream inputStream, int bufferSize) throws IOException {
	super();
	this.inputStream = inputStream;
	this.bufferSize = bufferSize;
	this.buffer = new byte[bufferSize];
    }

    private void fillBuffer() throws IOException {
	bufferBytes = inputStream.read(buffer, 0, bufferSize);
	bufferPointer = 0;
    }

    @Override
    public int read() throws IOException {
	if (bufferPointer >= bufferBytes) {
	    if (bufferBytes < 0) {
		return -1;
	    }
	    fillBuffer();
	    if (bufferBytes < 0) {
		return -1;
	    }
	}
	int b = buffer[bufferPointer] & 0xFF;
	bufferPointer++;
	return b;
    }

    @Override
    public void close() throws IOException {
	inputStream.close();
    }
}
