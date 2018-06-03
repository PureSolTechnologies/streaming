package com.puresoltechnologies.streaming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puresoltechnologies.streaming.iterators.CompositeStreamIterator;

public class CompositeStreamIteratorTest {

    @Test
    public void testSimpleConversion() {
	List<Integer> numbers = Arrays.asList(1, 2);
	List<Integer> numbers2 = Arrays.asList(3);
	List<Integer> numbers3 = Arrays.asList(4, 5);
	@SuppressWarnings("unchecked")
	CompositeStreamIterator<Integer> converterIterator = new CompositeStreamIterator<Integer>(numbers.iterator(),
		numbers2.iterator(), numbers3.iterator());
	assertTrue(converterIterator.hasNext());
	assertEquals(1, (int) converterIterator.next());
	assertTrue(converterIterator.hasNext());
	assertEquals(2, (int) converterIterator.next());
	assertTrue(converterIterator.hasNext());
	assertEquals(3, (int) converterIterator.next());
	assertTrue(converterIterator.hasNext());
	assertEquals(4, (int) converterIterator.next());
	assertTrue(converterIterator.hasNext());
	assertEquals(5, (int) converterIterator.next());
	assertFalse(converterIterator.hasNext());
    }

    @Test
    public void testEmptyConstructor() {
	@SuppressWarnings("unchecked")
	CompositeStreamIterator<Integer> converterIterator = new CompositeStreamIterator<Integer>();
	assertFalse(converterIterator.hasNext());
    }

    @Test
    public void testEmptyIterators() {
	List<Integer> numbers = new ArrayList<Integer>();
	List<Integer> numbers2 = new ArrayList<Integer>();
	@SuppressWarnings("unchecked")
	CompositeStreamIterator<Integer> converterIterator = new CompositeStreamIterator<Integer>(numbers.iterator(),
		numbers2.iterator());
	assertFalse(converterIterator.hasNext());
    }
}
