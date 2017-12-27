package com.puresoltechnologies.streaming.streams;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This is a simple stream to count the bytes read from it to determine the
 * current position.
 * 
 * @author Rick-Rainer Ludwig
 */
public class PositionOutputStream extends OutputStream implements Comparable<PositionOutputStream> {

    private long position = 0;
    private final OutputStream outputStream;

    public PositionOutputStream(OutputStream outputStream) {
	super();
	this.outputStream = outputStream;
    }

    public PositionOutputStream(OutputStream outputStream, long positionOffset) {
	super();
	this.outputStream = outputStream;
	this.position = positionOffset;
    }

    @Override
    public void write(int b) throws IOException {
	outputStream.write(b);
	position++;
    }

    @Override
    public void write(byte[] b) throws IOException {
	outputStream.write(b);
	position += b.length;
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
	outputStream.write(b, off, len);
	position += len;
    }

    public final long getPosition() {
	return position;
    }

    @Override
    public void flush() throws IOException {
	outputStream.flush();
    }

    @Override
    public void close() throws IOException {
	outputStream.close();
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((outputStream == null) ? 0 : outputStream.hashCode());
	result = prime * result + (int) (position ^ (position >>> 32));
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
	PositionOutputStream other = (PositionOutputStream) obj;
	if (outputStream == null) {
	    if (other.outputStream != null)
		return false;
	} else if (!outputStream.equals(other.outputStream))
	    return false;
	if (position != other.position)
	    return false;
	return true;
    }

    @Override
    public int compareTo(PositionOutputStream o) {
	return Long.compare(position, o.position);
    }

}
