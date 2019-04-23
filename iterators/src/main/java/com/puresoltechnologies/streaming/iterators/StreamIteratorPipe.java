package com.puresoltechnologies.streaming.iterators;

import java.io.Closeable;
import java.io.IOException;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class StreamIteratorPipe<T> extends AbstractStreamIterator<T> implements Closeable {

	private final BlockingDeque<T> pipe;
	private boolean closed = false;

	public StreamIteratorPipe() {
		this.pipe = new LinkedBlockingDeque<>();
	}

	public StreamIteratorPipe(int capacity) {
		this.pipe = new LinkedBlockingDeque<>(capacity);
	}

	public void push(T element) throws InterruptedException {
		pipe.putLast(element);
	}

	@Override
	protected T findNext() {
		if (closed) {
			return null;
		}
		try {
			return pipe.take();
		} catch (InterruptedException e) {
			return null;
		}
	}

	@Override
	public void close() throws IOException {
		closed = true;
	}
}
