package com.puresoltechnologies.streaming.streams;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is a special {@link InputStream} delegate to support additional dispose,
 * close and shutdown procedures.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public class ExtendedCloseInputStream extends DelegatingInputStream {

    private final Closeable[] closeables;
    private final CloseRunnable closeRunnable;

    public ExtendedCloseInputStream(InputStream inputStream, Closeable... closeables) {
	super(inputStream);
	this.closeables = closeables;
	this.closeRunnable = null;
    }

    public ExtendedCloseInputStream(InputStream inputStream, CloseRunnable closeRunnable) {
	super(inputStream);
	this.closeables = null;
	this.closeRunnable = closeRunnable;
    }

    public ExtendedCloseInputStream(InputStream inputStream, CloseRunnable closeRunnable, Closeable... closeables) {
	super(inputStream);
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

}
