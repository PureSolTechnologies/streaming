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

}
