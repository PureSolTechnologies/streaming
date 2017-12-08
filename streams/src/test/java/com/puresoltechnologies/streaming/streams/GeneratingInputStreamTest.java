package com.puresoltechnologies.streaming.streams;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

public class GeneratingInputStreamTest {

    @Test
    public void testSeries() throws IOException {
	try (GeneratingInputStream generatingInputStream = new GeneratingInputStream((count, lastValue) -> {
	    if (lastValue == null) {
		return 0;
	    }
	    if (lastValue == 0) {
		return 1;
	    }
	    if ((lastValue < 128) && (lastValue != -1)) {
		return lastValue * 2;
	    }
	    return -1;
	})) {
	    assertEquals(0, generatingInputStream.read());
	    assertEquals(1, generatingInputStream.read());
	    assertEquals(2, generatingInputStream.read());
	    assertEquals(4, generatingInputStream.read());
	    assertEquals(8, generatingInputStream.read());
	    assertEquals(16, generatingInputStream.read());
	    assertEquals(32, generatingInputStream.read());
	    assertEquals(64, generatingInputStream.read());
	    assertEquals(128, generatingInputStream.read());
	    assertEquals(-1, generatingInputStream.read());
	    assertEquals(-1, generatingInputStream.read());
	    assertEquals(-1, generatingInputStream.read());
	}
    }

}
