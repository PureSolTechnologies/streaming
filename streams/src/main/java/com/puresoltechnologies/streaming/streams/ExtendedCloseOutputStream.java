package com.puresoltechnologies.streaming.streams;

import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This is a special {@link OutputStream} delegate to support additional
 * dispose, close and shutdown procedures.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public class ExtendedCloseOutputStream extends OutputStream {

    private final OutputStream outputStream;
    private final Closeable[] closeables;
    private final CloseRunnable closeRunnable;

    public ExtendedCloseOutputStream(OutputStream outputStream, Closeable... closeables) {
	this.outputStream = outputStream;
	this.closeables = closeables;
	this.closeRunnable = null;
    }

    public ExtendedCloseOutputStream(OutputStream outputStream, CloseRunnable closeRunnable) {
	this.outputStream = outputStream;
	this.closeables = null;
	this.closeRunnable = closeRunnable;
    }

    public ExtendedCloseOutputStream(OutputStream outputStream, CloseRunnable closeRunnable, Closeable... closeables) {
	this.outputStream = outputStream;
	this.closeables = closeables;
	this.closeRunnable = closeRunnable;
    }

    @Override
    public void close() throws IOException {
	super.close();
	if (closeables != null) {
	    for (Closeable closeable : closeables) {
		closeable.close();
	    }
	}
	if (closeRunnable != null) {
	    closeRunnable.runClose();
	}
    }

    @Override
    public void write(int b) throws IOException {
	outputStream.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
	outputStream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
	outputStream.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
	outputStream.flush();
    }

}
