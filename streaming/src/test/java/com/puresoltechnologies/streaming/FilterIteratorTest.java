package com.puresoltechnologies.streaming;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.puresoltechnologies.streaming.FilterIterator.Filter;

public class FilterIteratorTest {

    @Test
    public void simpleFiltering() {
	List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
	FilterIterator<Integer> filterIterator = new FilterIterator<Integer>(numbers.iterator(), new Filter<Integer>() {
	    @Override
	    public boolean valid(Iterator<Integer> ierator, Integer element) {
		return element % 2 == 0;
	    }
	});
	assertTrue(filterIterator.hasNext());
	assertEquals(2, (int) filterIterator.next());
	assertTrue(filterIterator.hasNext());
	assertEquals(4, (int) filterIterator.next());
	assertTrue(filterIterator.hasNext());
	assertEquals(6, (int) filterIterator.next());
	assertTrue(filterIterator.hasNext());
	assertEquals(8, (int) filterIterator.next());
	assertTrue(filterIterator.hasNext());
	assertEquals(10, (int) filterIterator.next());
	assertFalse(filterIterator.hasNext());
    }

}
