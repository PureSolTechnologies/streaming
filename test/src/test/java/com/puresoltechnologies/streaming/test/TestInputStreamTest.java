package com.puresoltechnologies.streaming.test;

import java.io.EOFException;
import java.io.IOException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.puresoltechnologies.streaming.streams.GeneratingInputStream;

public class TestInputStreamTest {

    @Test
    public void testPositionTgriggerIOException() throws IOException {
	GeneratingInputStream generatingInputStream = new GeneratingInputStream((pos, lastValue) -> {
	    if (pos < 10) {
		return pos.intValue();
	    } else {
		return null;
	    }
	});
	try (TestInputStream testInputStream = new TestInputStream(generatingInputStream, 10, new EOFException(""))) {
	    for (int i = 0; i < 9; i++) {
		testInputStream.read();
	    }
	    Assertions.assertThrows(EOFException.class, () -> {
		testInputStream.read();
	    });
	}
    }

    @Test
    public void testPositionTgriggerRuntimeException() throws IOException {
	GeneratingInputStream generatingInputStream = new GeneratingInputStream((pos, lastValue) -> {
	    if (pos < 10) {
		return pos.intValue();
	    } else {
		return null;
	    }
	});
	try (TestInputStream testInputStream = new TestInputStream(generatingInputStream, 10,
		new IllegalStateException(""))) {
	    for (int i = 0; i < 9; i++) {
		testInputStream.read();
	    }
	    Assertions.assertThrows(IllegalStateException.class, () -> {
		testInputStream.read();
	    });
	}
    }

}
