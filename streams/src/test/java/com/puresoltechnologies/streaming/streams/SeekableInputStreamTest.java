package com.puresoltechnologies.streaming.streams;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import com.puresoltechnologies.streaming.streams.SeekableInputStream.InputStreamCreator;

public class SeekableInputStreamTest {

    private final static InputStreamCreator creator = new InputStreamCreator() {
	@Override
	public CountingInputStream create() throws IOException {
	    GeneratingInputStream generatingInputStream = new GeneratingInputStream((count, i) -> {
		return count < 1024 ? (int) (count % 255) : -1;
	    });
	    return new CountingInputStream(generatingInputStream);
	}
    };

    @Test
    public void testForwardSkip() throws IOException {
	try (SeekableInputStream seekableInputStream = new SeekableInputStream(creator)) {
	    assertEquals(0l, seekableInputStream.getPosition());
	    assertEquals(0l, seekableInputStream.read());
	    assertEquals(0l, seekableInputStream.skip(0));
	    assertEquals(1l, seekableInputStream.getPosition());
	    assertEquals(3l, seekableInputStream.skip(3));
	    assertEquals(4l, seekableInputStream.getPosition());
	    assertEquals(4l, seekableInputStream.read());
	    assertEquals(1018l, seekableInputStream.skip(1018));
	    assertEquals(1023l, seekableInputStream.getPosition());
	    assertEquals(1l, seekableInputStream.skip(1024));
	    assertEquals(1024l, seekableInputStream.getPosition());
	}
    }

    @Test
    public void testBackwardSkip() throws IOException {
	try (SeekableInputStream seekableInputStream = new SeekableInputStream(creator)) {
	    assertEquals(1024l, seekableInputStream.seek(1024));
	    assertEquals(1024l, seekableInputStream.getPosition());
	    assertEquals(-1l, seekableInputStream.skip(-1l));
	    assertEquals(1023l, seekableInputStream.getPosition());
	    assertEquals(1023 % 0xFF, seekableInputStream.read() % 0xFF);
	    assertEquals(-1l, seekableInputStream.skip(-1l));
	    assertEquals(1023l, seekableInputStream.getPosition());
	    assertEquals(-1022, seekableInputStream.skip(-1022));
	    assertEquals(1l, seekableInputStream.getPosition());
	    assertEquals(-1, seekableInputStream.skip(-1024));
	    assertEquals(0l, seekableInputStream.getPosition());
	}
    }

}
