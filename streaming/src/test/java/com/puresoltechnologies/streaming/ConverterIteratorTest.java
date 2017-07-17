package com.puresoltechnologies.streaming;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.puresoltechnologies.streaming.ConverterIterator.Converter;

public class ConverterIteratorTest {

    @Test
    public void testSimpleConversion() {
	List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
	ConverterIterator<Integer, String> converterIterator = new ConverterIterator<Integer, String>(
		numbers.iterator(), new Converter<Integer, String>() {

		    @Override
		    public String convert(Iterator<Integer> ierator, Integer element) {

			return "Element" + String.valueOf(element);
		    }
		});
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
