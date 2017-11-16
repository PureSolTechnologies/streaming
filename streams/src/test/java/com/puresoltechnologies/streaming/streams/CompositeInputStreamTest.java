package com.puresoltechnologies.streaming.streams;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.rules.ExpectedException;

public class CompositeInputStreamTest {

    private final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testIllegalArray() {
	expectedException.expect(IllegalArgumentException.class);
	CompositeInputStream copyingOutputStream = new CompositeInputStream(new ByteArrayInputStream(new byte[] {}),
		null, new ByteArrayInputStream(new byte[] {}));
	assertNotNull(copyingOutputStream);
    }

    @Test
    public void testIllegalCollection() {
	expectedException.expect(IllegalArgumentException.class);
	List<InputStream> inputStreams = new ArrayList<>();
	inputStreams.add(new ByteArrayInputStream(new byte[] {}));
	inputStreams.add(null);
	inputStreams.add(new ByteArrayInputStream(new byte[] {}));
	CompositeInputStream copyingOutputStream = new CompositeInputStream(inputStreams);
	assertNotNull(copyingOutputStream);
    }

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
