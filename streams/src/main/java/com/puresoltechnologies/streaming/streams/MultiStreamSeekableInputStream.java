package com.puresoltechnologies.streaming.streams;

import java.io.IOException;
import java.io.InputStream;

import com.puresoltechnologies.streaming.streams.SeekableInputStream.InputStreamCreator;

public class MultiStreamSeekableInputStream extends InputStream {

    private final InputStreamCreator supplier;
    private final SeekableInputStream[] streams;
    private int currentStream;
    private int openedStreams;

    public MultiStreamSeekableInputStream(int streamNum, InputStreamCreator supplier) throws IOException {
	super();
	this.supplier = supplier;
	this.streams = new SeekableInputStream[streamNum];
	currentStream = 0;
	openedStreams = 1;
	streams[currentStream] = new SeekableInputStream(supplier);
    }

    @Override
    public int read() throws IOException {
	return streams[currentStream].read();
    }

    @Override
    public void close() throws IOException {
	for (int i = 0; i < openedStreams; ++i) {
	    SeekableInputStream stream = streams[i];
	    stream.close();
	    streams[i] = null;
	}
	openedStreams = 0;
    }

    /**
     * This method moved the current position to the new provided position. If this
     * position is pointing to a position before the current position, the input
     * stream is closed and newly created by the set {@link #supplier}.
     * 
     * @param position
     *            is the new position to point to.
     * @return
     * @throws IOException
     */
    public final long seek(long position) throws IOException {
	SeekableInputStream inputStream = streams[currentStream];
	if (inputStream.getPosition() == position) {
	    return position;
	}
	int maxId = 0;
	long maxPos = streams[0].getPosition();
	int foundId = -1;
	long foundPos = -1;

	for (int id = 0; id < openedStreams; ++id) {
	    SeekableInputStream stream = streams[id];
	    long streamPosition = stream.getPosition();
	    if (streamPosition == position) {
		currentStream = id;
		return position;
	    }
	    if (streamPosition < position) {
		if (streamPosition > foundPos) {
		    foundPos = streamPosition;
		    foundId = id;
		}
	    } else {
		if (streamPosition > maxPos) {
		    maxPos = streamPosition;
		    maxId = id;
		}
	    }
	}
	if (foundId >= 0) {
	    currentStream = foundId;
	} else if (openedStreams < streams.length) {
	    currentStream = openedStreams;
	    streams[currentStream] = new SeekableInputStream(supplier);
	    openedStreams++;
	} else {
	    currentStream = maxId;
	    streams[currentStream].close();
	    streams[currentStream] = new SeekableInputStream(supplier);
	}
	return streams[currentStream].seek(position);
    }

    @Override
    public final long skip(long n) throws IOException {
	long currentPosition = getPosition();
	long newPosition = seek(currentPosition + n);
	return newPosition - currentPosition;
    }

    public final long getPosition() {
	return streams[currentStream].getPosition();
    }
}
