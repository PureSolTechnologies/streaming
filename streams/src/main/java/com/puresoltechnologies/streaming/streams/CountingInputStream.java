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
public class CountingInputStream extends InputStream implements Comparable<CountingInputStream> {

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

    @Override
    public int read(byte[] b) throws IOException {
	int number = inputStream.read(b);
	if (number != -1) {
	    count += number;
	}
	return number;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
	int number = inputStream.read(b, off, len);
	if (number != -1) {
	    count += number;
	}
	return number;
    }

    public final long getCount() {
	return count;
    }

    @Override
    public void close() throws IOException {
	inputStream.close();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (count ^ (count >>> 32));
	result = prime * result + ((inputStream == null) ? 0 : inputStream.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	CountingInputStream other = (CountingInputStream) obj;
	if (count != other.count)
	    return false;
	if (inputStream == null) {
	    if (other.inputStream != null)
		return false;
	} else if (!inputStream.equals(other.inputStream))
	    return false;
	return true;
    }

    @Override
    public int compareTo(CountingInputStream o) {
	return Long.compare(count, o.count);
    }
}
