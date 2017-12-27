package com.puresoltechnologies.streaming.streams;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This is a collection of helper method to handle streams.
 * 
 * @author Rick-Rainer Ludwig
 */
public class StreamUtils {

    /**
     * This method performs a simple copy from provided {@link InputStream} to
     * provided {@link OutputStream}.
     * 
     * @param inputStream
     *            is the source {@link InputStream}
     * @param outputStream
     *            is the target {@link OutputStream}.
     * @return The number of copied bytes is returned.
     * @throws IOException
     *             is thrown in any case o I/O exception.
     */
    public static long copy(InputStream inputStream, OutputStream outputStream) throws IOException {
	long count = 0;
	int b = inputStream.read();
	while (b >= 0) {
	    outputStream.write(b);
	    count++;
	    b = inputStream.read();
	}
	return count;
    }

    /**
     * Private default constructor to avoid instantiation.
     */
    private StreamUtils() {
    }
}
