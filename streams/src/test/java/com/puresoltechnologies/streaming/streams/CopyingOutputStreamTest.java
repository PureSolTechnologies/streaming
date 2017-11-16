package com.puresoltechnologies.streaming.streams;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CopyingOutputStreamTest {

    private final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testIllegalArray() {
	expectedException.expect(IllegalArgumentException.class);
	CopyingOutputStream copyingOutputStream = new CopyingOutputStream(new ByteArrayOutputStream(), null,
		new ByteArrayOutputStream());
	assertNotNull(copyingOutputStream);
    }

    @Test
    public void testIllegalCollection() {
	expectedException.expect(IllegalArgumentException.class);
	List<OutputStream> outputStreams = new ArrayList<>();
	outputStreams.add(new ByteArrayOutputStream());
	outputStreams.add(null);
	outputStreams.add(new ByteArrayOutputStream());
	CopyingOutputStream copyingOutputStream = new CopyingOutputStream(outputStreams);
	assertNotNull(copyingOutputStream);
    }

    @Test
    public void testCopying() throws IOException {
	try (ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
		ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
		CopyingOutputStream copyingOutputStream = new CopyingOutputStream(outputStream1, outputStream2)) {

	    copyingOutputStream.write(-2);
	    copyingOutputStream.write(-1);
	    copyingOutputStream.write(0);
	    copyingOutputStream.write(1);
	    copyingOutputStream.write(2);

	    byte[] byteArray = outputStream1.toByteArray();
	    assertEquals(5, byteArray.length);
	    assertEquals(-2, byteArray[0]);
	    assertEquals(-1, byteArray[1]);
	    assertEquals(0, byteArray[2]);
	    assertEquals(1, byteArray[3]);
	    assertEquals(2, byteArray[4]);

	    byteArray = outputStream2.toByteArray();
	    assertEquals(5, byteArray.length);
	    assertEquals(-2, byteArray[0]);
	    assertEquals(-1, byteArray[1]);
	    assertEquals(0, byteArray[2]);
	    assertEquals(1, byteArray[3]);
	    assertEquals(2, byteArray[4]);
	}
    }

}
