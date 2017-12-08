package com.puresoltechnologies.streaming.streams;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is a simple stream to count the bytes read from it. It is used to
 * determine the position within the stream to create more sophisticated
 * streams.
 * 
 * @author Rick-Rainer Ludwig
 */
public class CountingInputStream extends InputStream {

    private final InputStream inputStream;
    private long count = 0;

    public CountingInputStream(InputStream inputStream) {
	super();
	this.inputStream = inputStream;
    }

    @Override
    public int read() throws IOException {
	int read = inputStream.read();
	if (read != -1) {
	    count++;
	}
	return read;
    }

    public final long getCount() {
	return count;
    }

    @Override
    public void close() throws IOException {
	inputStream.close();
    }

}
