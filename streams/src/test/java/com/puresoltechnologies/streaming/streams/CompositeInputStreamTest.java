package com.puresoltechnologies.streaming.streams;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Test;

public class CompositeInputStreamTest {

    @Test
    public void testEmtyStreams() throws IOException {
	try (CompositeInputStream compositeInputStream = new CompositeInputStream()) {
	    assertEquals(-1, compositeInputStream.read());
	}
    }

    @Test
    public void testComposition() throws IOException {
	ByteArrayInputStream byteArrayInputStream1 = new ByteArrayInputStream(new byte[] { 0 });
	ByteArrayInputStream byteArrayInputStream2 = new ByteArrayInputStream(new byte[] {});
	ByteArrayInputStream byteArrayInputStream3 = new ByteArrayInputStream(new byte[] { -1, 1 });
	ByteArrayInputStream byteArrayInputStream4 = new ByteArrayInputStream(new byte[] { -2, 2 });

	try (CompositeInputStream inputStream = new CompositeInputStream(byteArrayInputStream1, byteArrayInputStream2,
		byteArrayInputStream3, byteArrayInputStream4)) {
	    assertEquals((byte) 0, (byte) inputStream.read());
	    assertEquals((byte) -1, (byte) inputStream.read());
	    assertEquals((byte) 1, (byte) inputStream.read());
	    assertEquals((byte) -2, (byte) inputStream.read());
	    assertEquals((byte) 2, (byte) inputStream.read());
	}
    }

}
