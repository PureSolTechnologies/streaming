package com.puresoltechnologies.streaming.streams;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Iterator;

import org.junit.Test;

public class CountingInputStreamTest {

    @Test
    public void testCounting() {
	GeneratingInputStream generatingInputStream = new GeneratingInputStream((count, i) -> {
	    return count < 1024 ? (int) (count % 0xFF) : -1;
	});
	CountingInputStream countingInputStream = new CountingInputStream(generatingInputStream);
	InputStreamIterator<CountingInputStream, Integer> iterator = new InputStreamIterator<>(countingInputStream,
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
	assertEquals(count, countingInputStream.getCount());
	for (Integer i : new Iterable<Integer>() {
	    @Override
	    public Iterator<Integer> iterator() {
		return iterator;
	    }
	}) {
	    count++;
	    assertEquals(count, countingInputStream.getCount());
	    assertEquals((count - 1) % 255, i.intValue());
	}
	assertEquals(1024l, countingInputStream.getCount());
    }

}
