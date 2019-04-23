package com.puresoltechnologies.streaming.streams;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * This composite input streams take multiple input streams and handles them as
 * one whole to the outside.
 *
 * @author Rick-Rainer Ludwig
 *
 */
public class CompositeInputStream extends InputStream {

	private final List<InputStream> inputStreams = new ArrayList<>();
	private InputStream currentInputStream = null;

	public CompositeInputStream(InputStream... inputStreams) {
		try {
			for (InputStream inputStream : inputStreams) {
				Objects.requireNonNull(inputStream, "Input streams must not be null");
				this.inputStreams.add(inputStream);
			}
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Input streams must not be null.", e);
		}
	}

	public CompositeInputStream(Collection<InputStream> inputStreams) {
		try {
			for (InputStream inputStream : inputStreams) {
				Objects.requireNonNull(inputStream, "Input streams must not be null");
				this.inputStreams.add(inputStream);
			}
		} catch (NullPointerException e) {
			throw new IllegalArgumentException("Input streams must not be null.", e);
		}
	}

	@Override
	public int read() throws IOException {
		int b = -1;
		while ((currentInputStream == null) || ((b = currentInputStream.read()) < 0)) {
			if (inputStreams.size() == 0) {
				currentInputStream = null;
				return b;
			}
			currentInputStream = inputStreams.remove(0);
		}
		return b;
	}

	/**
	 * This method closes all provided {@link #inputStreams}.
	 */
	@Override
	public void close() throws IOException {
		if (currentInputStream != null) {
			currentInputStream.close();
		}
		for (InputStream inputStream : inputStreams) {
			inputStream.close();
		}
	}

}
