package com.puresoltechnologies.streaming;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class StreamIteratorTest {

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

}
