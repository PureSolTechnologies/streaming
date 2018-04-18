package com.puresoltechnologies.streaming.streams;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

public class PositionInputStreamTest {

    @Test
    public void testCountingSingleByte() throws IOException {
	GeneratingInputStream generatingInputStream = new GeneratingInputStream((count, i) -> {
	    return count < 1024 ? (int) (count % 0xFF) : -1;
	});
	try (PositionInputStream countingInputStream = new PositionInputStream(generatingInputStream)) {
	    InputStreamIterator<PositionInputStream, Integer> iterator = new InputStreamIterator<>(countingInputStream,
		    (stream) -> {
			try {
			    int i = stream.read();
			    if (i == -1) {
				return null;
			    }
			    return i;
			} catch (IOException e) {
			    throw new RuntimeException(e);
			}
		    });
	    int count = 0;
	    assertEquals(count, countingInputStream.getPosition());
	    for (Integer i : new Iterable<Integer>() {
		@Override
		public Iterator<Integer> iterator() {
		    return iterator;
		}
	    }) {
		count++;
		assertEquals(count, countingInputStream.getPosition());
		assertEquals((count - 1) % 255, i.intValue());
	    }
	    assertEquals(1024l, countingInputStream.getPosition());
	}
    }

    @Test
    public void testCountingReadArray() throws IOException {
	GeneratingInputStream generatingInputStream = new GeneratingInputStream((count, i) -> {
	    return count < 1024 ? (int) (count % 0xFF) : -1;
	});
	try (PositionInputStream countingInputStream = new PositionInputStream(generatingInputStream)) {
	    int count = 0;
	    assertEquals(count, countingInputStream.getPosition());

	    byte[] bytes = new byte[128];
	    int number = countingInputStream.read(bytes);
	    while (number >= 0) {
		for (int i = 0; i < number; i++) {
		    byte b = bytes[i];
		    count++;
		    assertEquals((count - 1) % 255, b & 0xFF);
		}
		assertEquals(count, countingInputStream.getPosition());
		number = countingInputStream.read(bytes);
	    }
	    assertEquals(1024l, countingInputStream.getPosition());
	}
    }

    @Test
    public void testCountingReadSubArray() throws IOException {
	GeneratingInputStream generatingInputStream = new GeneratingInputStream((count, i) -> {
	    return count < 1024 ? (int) (count % 0xFF) : -1;
	});
	try (PositionInputStream countingInputStream = new PositionInputStream(generatingInputStream)) {
	    int count = 0;
	    assertEquals(count, countingInputStream.getPosition());

	    byte[] bytes = new byte[1024];
	    int number = countingInputStream.read(bytes, 24, 128);
	    while (number >= 0) {
		for (int i = 24; i < 24 + number; i++) {
		    byte b = bytes[i];
		    count++;
		    assertEquals((count - 1) % 255, b & 0xFF);
		}
		assertEquals(count, countingInputStream.getPosition());
		number = countingInputStream.read(bytes, 24, 128);
	    }
	    assertEquals(1024l, countingInputStream.getPosition());
	}
    }

}
