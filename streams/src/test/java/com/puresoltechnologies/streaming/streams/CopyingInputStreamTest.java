package com.puresoltechnologies.streaming.streams;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class CopyingInputStreamTest {

    @Test
    public void testSimpleCopying() throws IOException {
	try (ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[] { -2, -1, 0, 1, 2 });
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		CopyingInputStream copyingInputStream = new CopyingInputStream(inputStream, outputStream)) {
	    assertEquals(254, copyingInputStream.read());
	    assertEquals(255, copyingInputStream.read());
	    assertEquals(0, copyingInputStream.read());
	    assertEquals(1, copyingInputStream.read());
	    assertEquals(2, copyingInputStream.read());
	    assertEquals(-1, copyingInputStream.read());

	    byte[] byteArray = outputStream.toByteArray();
	    assertEquals(5, byteArray.length);
	    assertEquals(-2, byteArray[0]);
	    assertEquals(-1, byteArray[1]);
	    assertEquals(0, byteArray[2]);
	    assertEquals(1, byteArray[3]);
	    assertEquals(2, byteArray[4]);
	}
    }

    @Test
    public void testCopyingWithSkip() throws IOException {
	try (ByteArrayInputStream inputStream = new ByteArrayInputStream(new byte[] { -2, -1, 0, 1, 2 });
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		CopyingInputStream copyingInputStream = new CopyingInputStream(inputStream, outputStream)) {
	    assertEquals(254, copyingInputStream.read());
	    assertEquals(3, copyingInputStream.skip(3));
	    assertEquals(2, copyingInputStream.read());
	    assertEquals(-1, copyingInputStream.read());

	    byte[] byteArray = outputStream.toByteArray();
	    assertEquals(5, byteArray.length);
	    assertEquals(-2, byteArray[0]);
	    assertEquals(-1, byteArray[1]);
	    assertEquals(0, byteArray[2]);
	    assertEquals(1, byteArray[3]);
	    assertEquals(2, byteArray[4]);
	}
    }

}
