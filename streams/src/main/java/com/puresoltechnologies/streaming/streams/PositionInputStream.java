package com.puresoltechnologies.streaming.streams;

import java.io.IOException;
import java.io.InputStream;

/**
 * This is a simple stream to count the bytes read from it to determine the
 * current position.
 * 
 * @author Rick-Rainer Ludwig
 */
public class PositionInputStream extends InputStream implements Comparable<PositionInputStream> {

    private long position = 0;
    private final InputStream inputStream;

    public PositionInputStream(InputStream inputStream) {
	super();
	this.inputStream = inputStream;
    }

    public PositionInputStream(InputStream inputStream, long positionOffset) {
	super();
	this.inputStream = inputStream;
	this.position = positionOffset;
    }

    @Override
    public int read() throws IOException {
	int read = inputStream.read();
	if (read != -1) {
	    position++;
	}
	return read;
    }

    @Override
    public int read(byte[] b) throws IOException {
	int number = inputStream.read(b);
	if (number != -1) {
	    position += number;
	}
	return number;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
	int number = inputStream.read(b, off, len);
	if (number != -1) {
	    position += number;
	}
	return number;
    }

    public final long getPosition() {
	return position;
    }

    public final long seek(long position) throws IOException {
	if (this.position > position) {
	    throw new IOException("The offset was already passed.");
	}
	long skipped = inputStream.skip(position - this.position);
	this.position += skipped;
	return skipped;
    }

    @Override
    public long skip(long n) throws IOException {
	long skipped = inputStream.skip(n);
	position += skipped;
	return skipped;
    }

    @Override
    public int compareTo(PositionInputStream o) {
	return Long.compare(position, o.position);
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (position ^ (position >>> 32));
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
	PositionInputStream other = (PositionInputStream) obj;
	if (position != other.position)
	    return false;
	if (inputStream == null) {
	    if (other.inputStream != null)
		return false;
	} else if (!inputStream.equals(other.inputStream))
	    return false;
	return true;
    }
}
