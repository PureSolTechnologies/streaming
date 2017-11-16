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
	for (InputStream inputStream : inputStreams) {
	    Objects.requireNonNull(inputStreams, "Input streams must not be null");
	    this.inputStreams.add(inputStream);
	}
    }

    public CompositeInputStream(Collection<InputStream> inputStreams) {
	Objects.requireNonNull(inputStreams, "Input streams must not be null");
	this.inputStreams.addAll(inputStreams);
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

}
