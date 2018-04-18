package com.puresoltechnologies.streaming.streams;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

public class PositionOutputStreamTest {

    @Test
    public void testCounting() throws IOException {
	GeneratingInputStream generatingInputStream = new GeneratingInputStream((count, i) -> {
	    return count < 1024 ? (int) (count % 0xFF) : -1;
	});
	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	try (PositionOutputStream countingOutputStream = new PositionOutputStream(byteArrayOutputStream)) {
	    int count = 0;
	    assertEquals(count, countingOutputStream.getPosition());

	    StreamUtils.copy(generatingInputStream, countingOutputStream);

	    assertEquals(1024l, countingOutputStream.getPosition());
	}
    }

}
