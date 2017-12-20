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
public class CountingInputStream extends DelegatingInputStream implements Comparable<CountingInputStream> {

    private long count = 0;

    public CountingInputStream(InputStream inputStream) {
	super(inputStream);
    }

    @Override
    public int read() throws IOException {
	int read = super.read();
	if (read != -1) {
	    count++;
	}
	return read;
    }

    @Override
    public int read(byte[] b) throws IOException {
	int number = super.read(b);
	if (number != -1) {
	    count += number;
	}
	return number;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
	int number = super.read(b, off, len);
	if (number != -1) {
	    count += number;
	}
	return number;
    }

    public final long getCount() {
	return count;
    }

    public final long seek(long count) throws IOException {
	if (this.count > count) {
	    throw new IOException("The offset was already passed.");
	}
	long skipped = super.skip(count - this.count);
	this.count += skipped;
	return skipped;
    }

    @Override
    public long skip(long n) throws IOException {
	long skipped = super.skip(n);
	count += skipped;
	return skipped;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + (int) (count ^ (count >>> 32));
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
	CountingInputStream other = (CountingInputStream) obj;
	if (count != other.count)
	    return false;
	return true;
    }

    @Override
    public int compareTo(CountingInputStream o) {
	return Long.compare(count, o.count);
    }
}
