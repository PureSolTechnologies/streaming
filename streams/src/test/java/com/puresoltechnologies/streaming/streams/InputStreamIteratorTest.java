package com.puresoltechnologies.streaming.streams;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class InputStreamIteratorTest {

    @Test
    public void testIteratorConversion() throws IOException {
	ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[] { 0, -1, 1, -2, 2 });

	try (InputStreamIterator<InputStream, Byte> inputStreamIterator = new InputStreamIterator<>(
		byteArrayInputStream, (inputStream) -> {
		    try {
			int b = inputStream.read();
			return b != -1 ? (byte) (b * b * b) : null;
		    } catch (IOException e) {
			throw new RuntimeException(e);
		    }
		})) {
	    assertEquals((byte) 0, (byte) inputStreamIterator.next());
	    assertEquals((byte) -1, (byte) inputStreamIterator.next());
	    assertEquals((byte) 1, (byte) inputStreamIterator.next());
	    assertEquals((byte) -8, (byte) inputStreamIterator.next());
	    assertEquals((byte) 8, (byte) inputStreamIterator.next());
	}
    }

}
