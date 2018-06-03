package com.puresoltechnologies.streaming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puresoltechnologies.streaming.iterators.ConvertingStreamIterator;

public class ConvertingStreamIteratorTest {

    @Test
    public void testSimpleConversion() {
	List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
	ConvertingStreamIterator<Integer, String> converterIterator = new ConvertingStreamIterator<Integer, String>(
		numbers.iterator(), //
		i -> "Element" + String.valueOf(i));
	assertTrue(converterIterator.hasNext());
	assertEquals("Element1", converterIterator.next());
	assertTrue(converterIterator.hasNext());
	assertEquals("Element2", converterIterator.next());
	assertTrue(converterIterator.hasNext());
	assertEquals("Element3", converterIterator.next());
	assertTrue(converterIterator.hasNext());
	assertEquals("Element4", converterIterator.next());
	assertTrue(converterIterator.hasNext());
	assertEquals("Element5", converterIterator.next());
	assertFalse(converterIterator.hasNext());
    }
}
