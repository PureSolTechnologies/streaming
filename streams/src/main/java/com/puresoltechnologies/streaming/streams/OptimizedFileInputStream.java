package com.puresoltechnologies.streaming.streams;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This stream is an optimized form of {@link FileOutputStream}.
 * 
 * {@value OptimizedFileOutputStream#DEFAULT_BUFFER_SIZE}
 * 
 * @author Rick-Rainer Ludwig
 */
public class OptimizedFileInputStream extends InputStream {

    private static final String DEFAULT_BUFFER_SIZE = "8192";
    private static final String BUFFER_SIZE_PROPERTY_NAME = "com.puresoltechnologies.streaming.streams.OptimizedFileInputStream.bufferSize";
    private static final int bufferSize = Integer
	    .parseInt(System.getProperty(BUFFER_SIZE_PROPERTY_NAME, DEFAULT_BUFFER_SIZE));

    private final BufferedInputStream bufferedInputStream;

    public OptimizedFileInputStream(File file) throws FileNotFoundException {
	this(new FileInputStream(file));
    }

    public OptimizedFileInputStream(String name) throws FileNotFoundException {
	this(new FileInputStream(name));
    }

    public OptimizedFileInputStream(FileDescriptor fileDescriptor) {
	this(new FileInputStream(fileDescriptor));
    }

    private OptimizedFileInputStream(FileInputStream fileInputStream) {
	this.bufferedInputStream = new BufferedInputStream(fileInputStream, bufferSize);
    }

    @Override
    public int read(byte[] b) throws IOException {
	return bufferedInputStream.read(b);
    }

    @Override
    public int read() throws IOException {
	return bufferedInputStream.read();
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
	return bufferedInputStream.read(b, off, len);
    }

    @Override
    public long skip(long n) throws IOException {
	return bufferedInputStream.skip(n);
    }

    @Override
    public int available() throws IOException {
	return bufferedInputStream.available();
    }

    @Override
    public void close() throws IOException {
	bufferedInputStream.close();
    }

}
