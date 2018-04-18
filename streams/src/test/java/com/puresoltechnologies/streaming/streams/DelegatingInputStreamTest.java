package com.puresoltechnologies.streaming.streams;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

public class DelegatingInputStreamTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    /**
     * Test the fixed skip method for the BufferedInputStream.
     * 
     * @throws IOException
     */
    @Test
    public void testFixedSkip() throws IOException {

	try (GeneratingInputStream generatingInputStream = new GeneratingInputStream(
		(count, last) -> count < 1024 * 1024 ? (int) (count % 256) : -1);
		BufferedInputStream bufferedInputStream = new BufferedInputStream(generatingInputStream);
		DelegatingInputStream inputStream = new DelegatingInputStream(bufferedInputStream);) {
	    assertEquals(0, inputStream.read());
	    assertEquals(1, inputStream.read());
	    assertEquals(2, inputStream.read());
	    assertEquals(1024, inputStream.skip(1024));
	    assertEquals(3, inputStream.read());
	    assertEquals(8192, inputStream.skip(8192)); // will not work without delegate
	    assertEquals(4, inputStream.read());
	    assertEquals(0, inputStream.skip(0)); // for completeness
	    assertEquals(5, inputStream.read());
	}
    }

    @Test
    public void testIOExceptionForNegativeSkip() throws IOException {
	expectedException.expect(IOException.class);
	expectedException.expectMessage("Negative back skips are not supported by this stream implementaion.");
	try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(new byte[] {});
		DelegatingInputStream inputStream = new DelegatingInputStream(byteArrayInputStream)) {
	    inputStream.skip(-1);
	}
    }

}
