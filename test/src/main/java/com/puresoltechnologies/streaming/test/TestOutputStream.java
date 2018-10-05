package com.puresoltechnologies.streaming.test;

import java.io.IOException;
import java.io.OutputStream;
import java.time.Duration;
import java.time.Instant;

import com.puresoltechnologies.streaming.streams.PositionOutputStream;

public class TestOutputStream extends PositionOutputStream {

    private final Instant started;
    private final Duration duration;
    private final long triggerPosition;
    private final RuntimeException runtimeException;
    private final IOException ioException;

    public TestOutputStream(OutputStream outputStream, Duration duration, RuntimeException runtimeException) {
	super(outputStream);
	started = Instant.now();
	this.duration = duration;
	this.triggerPosition = -1;
	this.runtimeException = runtimeException;
	this.ioException = null;
    }

    public TestOutputStream(OutputStream outputStream, Duration duration, IOException ioException) {
	super(outputStream);
	started = Instant.now();
	this.duration = duration;
	this.triggerPosition = -1;
	this.runtimeException = null;
	this.ioException = ioException;
    }

    public TestOutputStream(OutputStream outputStream, long positionOffset, long triggerPosition,
	    RuntimeException runtimeException) {
	super(outputStream, positionOffset);
	started = Instant.now();
	this.duration = null;
	this.triggerPosition = triggerPosition;
	this.runtimeException = runtimeException;
	this.ioException = null;
    }

    public TestOutputStream(OutputStream outputStream, long positionOffset, long triggerPosition,
	    IOException ioException) {
	super(outputStream, positionOffset);
	started = Instant.now();
	this.duration = null;
	this.triggerPosition = triggerPosition;
	this.runtimeException = null;
	this.ioException = ioException;
    }

    @Override
    public void write(int b) throws IOException {
	super.write(b);
	triggerInCase();
    }

    @Override
    public void write(byte[] b) throws IOException {
	super.write(b);
	triggerInCase();
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
	super.write(b, off, len);
	triggerInCase();
    }

    private void triggerInCase() throws IOException {
	if (triggerPosition >= 0 && triggerPosition >= getPosition()) {
	    if (ioException != null) {
		throw ioException;
	    }
	    if (runtimeException != null) {
		throw runtimeException;
	    }
	}
	if (duration != null && Duration.between(started, Instant.now()).compareTo(duration) > 0) {
	    if (ioException != null) {
		throw ioException;
	    }
	    if (runtimeException != null) {
		throw runtimeException;
	    }
	}
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + ((duration == null) ? 0 : duration.hashCode());
	result = prime * result + ((ioException == null) ? 0 : ioException.hashCode());
	result = prime * result + ((runtimeException == null) ? 0 : runtimeException.hashCode());
	result = prime * result + ((started == null) ? 0 : started.hashCode());
	result = prime * result + (int) (triggerPosition ^ (triggerPosition >>> 32));
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
	TestOutputStream other = (TestOutputStream) obj;
	if (duration == null) {
	    if (other.duration != null)
		return false;
	} else if (!duration.equals(other.duration))
	    return false;
	if (ioException == null) {
	    if (other.ioException != null)
		return false;
	} else if (!ioException.equals(other.ioException))
	    return false;
	if (runtimeException == null) {
	    if (other.runtimeException != null)
		return false;
	} else if (!runtimeException.equals(other.runtimeException))
	    return false;
	if (started == null) {
	    if (other.started != null)
		return false;
	} else if (!started.equals(other.started))
	    return false;
	if (triggerPosition != other.triggerPosition)
	    return false;
	return true;
    }

}
