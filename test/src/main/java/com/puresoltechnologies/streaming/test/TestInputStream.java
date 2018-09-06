package com.puresoltechnologies.streaming.test;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.time.Instant;

import com.puresoltechnologies.streaming.streams.PositionInputStream;

public class TestInputStream extends PositionInputStream {

    private final Instant started;
    private final Duration duration;
    private final long triggerPosition;
    private final RuntimeException runtimeException;
    private final IOException ioException;

    public TestInputStream(InputStream inputStream, Duration duration, RuntimeException runtimeException) {
	super(inputStream);
	started = Instant.now();
	this.duration = duration;
	this.triggerPosition = -1;
	this.runtimeException = runtimeException;
	this.ioException = null;
    }

    public TestInputStream(InputStream inputStream, Duration duration, IOException ioException) {
	super(inputStream);
	started = Instant.now();
	this.duration = duration;
	this.triggerPosition = -1;
	this.runtimeException = null;
	this.ioException = ioException;
    }

    public TestInputStream(InputStream inputStream, long triggerPosition, RuntimeException runtimeException) {
	super(inputStream);
	started = Instant.now();
	this.duration = null;
	this.triggerPosition = triggerPosition;
	this.runtimeException = runtimeException;
	this.ioException = null;
    }

    public TestInputStream(InputStream inputStream, long triggerPosition, IOException ioException) {
	super(inputStream);
	started = Instant.now();
	this.duration = null;
	this.triggerPosition = triggerPosition;
	this.runtimeException = null;
	this.ioException = ioException;
    }

    public TestInputStream(InputStream inputStream, long positionOffset, Duration duration,
	    RuntimeException runtimeException) {
	super(inputStream, positionOffset);
	started = Instant.now();
	this.duration = duration;
	this.triggerPosition = -1;
	this.runtimeException = runtimeException;
	this.ioException = null;
    }

    public TestInputStream(InputStream inputStream, long positionOffset, Duration duration, IOException ioException) {
	super(inputStream, positionOffset);
	started = Instant.now();
	this.duration = duration;
	this.triggerPosition = -1;
	this.runtimeException = null;
	this.ioException = ioException;
    }

    public TestInputStream(InputStream inputStream, long positionOffset, long triggerPosition,
	    RuntimeException runtimeException) {
	super(inputStream, positionOffset);
	started = Instant.now();
	this.duration = null;
	this.triggerPosition = triggerPosition;
	this.runtimeException = runtimeException;
	this.ioException = null;
    }

    public TestInputStream(InputStream inputStream, long positionOffset, long triggerPosition,
	    IOException ioException) {
	super(inputStream, positionOffset);
	started = Instant.now();
	this.duration = null;
	this.triggerPosition = triggerPosition;
	this.runtimeException = null;
	this.ioException = ioException;
    }

    @Override
    public int read() throws IOException {
	int b = super.read();
	triggerInCase();
	return b;
    }

    @Override
    public int read(byte[] b) throws IOException {
	int readBytes = super.read(b);
	triggerInCase();
	return readBytes;
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
	int byteCount = super.read(b, off, len);
	triggerInCase();
	return byteCount;
    }

    @Override
    public long skip(long n) throws IOException {
	long position = super.skip(n);
	triggerInCase();
	return position;
    }

    private void triggerInCase() throws IOException {
	if (triggerPosition >= 0 && triggerPosition <= getPosition()) {
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
