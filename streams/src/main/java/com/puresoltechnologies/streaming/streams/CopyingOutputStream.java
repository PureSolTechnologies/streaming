package com.puresoltechnologies.streaming.streams;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * This output stream object is ussed to copy bytes to different destinations in
 * parallel for auditing, parallel processing or logging.
 * 
 * @author Rick-Rainer Ludwig
 */
public class CopyingOutputStream extends OutputStream {

    private final List<OutputStream> outputStreams = new ArrayList<>();

    /**
     * This constructor instantiates the class based on an array of output streams.
     * 
     * @param outputStreams
     *            is the array of output streams to write each byte to.
     */
    public CopyingOutputStream(OutputStream... outputStreams) {
	for (OutputStream outputStream : outputStreams) {
	    Objects.requireNonNull(outputStreams, "Output streams must not be null");
	    this.outputStreams.add(outputStream);
	}
    }

    /**
     * This constructor instantiates the class based on a {@link Collection} of
     * output streams.
     * 
     * @param outputStreams
     *            is the array of output streams to write each byte to.
     */
    public CopyingOutputStream(Collection<OutputStream> outputStreams) {
	Objects.requireNonNull(outputStreams, "Output streams must not be null");
	this.outputStreams.addAll(outputStreams);
    }

    @Override
    public void write(int b) throws IOException {
	for (OutputStream outputStream : outputStreams) {
	    outputStream.write(b);
	}
    }

}
