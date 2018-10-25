package com.puresoltechnologies.streaming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puresoltechnologies.streaming.iterators.StreamIterator;

public class StreamIteratorTest {

    @Test
    public void testEmpty() {
	StreamIterator<Object> empty = StreamIterator.empty();
	assertNotNull(empty);
	assertFalse(empty.hasNext());
    }

    @Test
    public void testOf() {
	List<Integer> original = new ArrayList<>();
	original.add(null);
	original.add(0);
	original.add(1);
	original.add(null);
	original.add(null);
	original.add(2);
	original.add(null);
	StreamIterator<Integer> streamIterator = StreamIterator.of(original.iterator());
	assertTrue(streamIterator.hasNext());
	assertEquals(0, (int) streamIterator.next());
	assertTrue(streamIterator.hasNext());
	assertEquals(1, (int) streamIterator.next());
	assertTrue(streamIterator.hasNext());
	assertEquals(2, (int) streamIterator.next());
	assertFalse(streamIterator.hasNext());
    }

    @Test
    public void testSpliterator() {
	Iterator<Integer> iterator = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9).iterator();
	StreamIterator<Integer> streamIterator = StreamIterator.of(iterator);
	assertNotNull(streamIterator.spliterator());
    }

    @Test
    public void testSteamForEach() {
	Iterator<Integer> iterator = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9).iterator();
	StreamIterator<Integer> streamIterator = StreamIterator.of(iterator);
	assertEquals(9, streamIterator.stream().count());
    }

}
