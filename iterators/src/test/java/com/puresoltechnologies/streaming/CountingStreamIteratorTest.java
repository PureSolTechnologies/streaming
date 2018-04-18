package com.puresoltechnologies.streaming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class CountingStreamIteratorTest {

    @Test
    public void test() {
	List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
	CountingStreamIterator<Integer> countingIterator = new CountingStreamIterator<Integer>(numbers.iterator());
	assertEquals(0, countingIterator.getCount());
	assertTrue(countingIterator.hasNext());
	assertEquals(1, (int) countingIterator.next());
	assertEquals(1, countingIterator.getCount());
	assertTrue(countingIterator.hasNext());
	assertEquals(2, (int) countingIterator.next());
	assertEquals(2, countingIterator.getCount());
	assertTrue(countingIterator.hasNext());
	assertEquals(3, (int) countingIterator.next());
	assertEquals(3, countingIterator.getCount());
	assertTrue(countingIterator.hasNext());
	assertEquals(4, (int) countingIterator.next());
	assertEquals(4, countingIterator.getCount());
	assertTrue(countingIterator.hasNext());
	assertEquals(5, (int) countingIterator.next());
	assertEquals(5, countingIterator.getCount());
	assertFalse(countingIterator.hasNext());
	assertEquals(5, countingIterator.getCount());

    }

}
