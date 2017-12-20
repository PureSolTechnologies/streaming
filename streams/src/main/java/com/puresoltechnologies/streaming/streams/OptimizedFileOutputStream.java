package com.puresoltechnologies.streaming.streams;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * This stream is an optimized form of {@link FileOutputStream}.
 * 
 * {@value OptimizedFileOutputStream#DEFAULT_BUFFER_SIZE}
 * 
 * @author Rick-Rainer Ludwig
 */
public class OptimizedFileOutputStream extends OutputStream {

    private static final String DEFAULT_BUFFER_SIZE = "8192";
    private static final String BUFFER_SIZE_PROPERTY_NAME = "com.puresoltechnologies.streaming.streams.OptimizedFileOutputStream.bufferSize";
    private static final int bufferSize = Integer
	    .parseInt(System.getProperty(BUFFER_SIZE_PROPERTY_NAME, DEFAULT_BUFFER_SIZE));

    private final BufferedOutputStream bufferedOutputStream;

    public OptimizedFileOutputStream(File file) throws FileNotFoundException {
	this(new FileOutputStream(file));
    }

    public OptimizedFileOutputStream(String name) throws FileNotFoundException {
	this(new FileOutputStream(name));
    }

    public OptimizedFileOutputStream(FileDescriptor fileDescriptor) {
	this(new FileOutputStream(fileDescriptor));
    }

    private OptimizedFileOutputStream(FileOutputStream fileOutputStream) {
	this.bufferedOutputStream = new BufferedOutputStream(fileOutputStream, bufferSize);
    }

    @Override
    public void write(int b) throws IOException {
	bufferedOutputStream.write(b);
    }

    @Override
    public void write(byte[] b) throws IOException {
	bufferedOutputStream.write(b);
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
	bufferedOutputStream.write(b, off, len);
    }

    @Override
    public void flush() throws IOException {
	bufferedOutputStream.flush();
    }

    @Override
    public void close() throws IOException {
	bufferedOutputStream.close();
    }

}
