package com.puresoltechnologies.streaming.binary;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.puresoltechnologies.streaming.StreamIterator;

public class TestInputStream extends InputStream {

    private final StreamIterator<Integer> iterator;

    public TestInputStream(int... bytes) {
	super();
	List<Integer> byteList = new ArrayList<>(bytes.length);
	for (int i = 0; i < bytes.length; ++i) {
	    byteList.add(bytes[i]);
	}
	this.iterator = StreamIterator.of(byteList.iterator());
    }

    public TestInputStream(StreamIterator<Integer> iterator) {
	super();
	this.iterator = iterator;
    }

    public TestInputStream(Collection<Integer> collection) {
	super();
	this.iterator = StreamIterator.of(collection.iterator());
    }

    @Override
    public int read() throws IOException {
	if (iterator.hasNext()) {
	    Integer next = iterator.next();
	    if ((next < 0) || (next > 255)) {
		throw new IOException("Illegal byte value '" + next + "' was provided for this test.");
	    }
	    return next;
	} else {
	    throw new IOException("End of stream reached.");
	}
    }

}
