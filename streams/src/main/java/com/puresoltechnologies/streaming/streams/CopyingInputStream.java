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
public class CopyingInputStream extends InputStream {

    private final InputStream inputStream;
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
	super();
	this.inputStream = inputStream;
	this.outputStream = outputStream;
    }

    @Override
    public int read() throws IOException {
	int b = inputStream.read();
	if (b != -1) {
	    outputStream.write(b);
	}
	return b;
    }

    /**
     * This method closes this and the provided {@link #inputStream}.
     * 
     * <b>Attention: </b> As this is an input stream which is wrapped around another
     * input stream, this class is not responsible for the output stream. Therefore,
     * the {@link #outputStream} is not closed by this method.
     */
    @Override
    public void close() throws IOException {
	inputStream.close();
    }

}
