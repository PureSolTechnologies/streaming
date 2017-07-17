package com.puresoltechnologies.streaming;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AbstractIteratorTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testSimple() {
	PeekingIterator<Integer> iterator = new AbstractIterator<Integer>() {

	    private int i = 0;

	    @Override
	    protected Integer findNext() {
		if (i >= 5) {
		    return null;
		}
		return ++i;
	    }
	};
	assertTrue(iterator.hasNext());
	assertEquals(1, (int) iterator.next());
	assertTrue(iterator.hasNext());
	assertEquals(2, (int) iterator.next());
	assertTrue(iterator.hasNext());
	assertEquals(3, (int) iterator.next());
	assertTrue(iterator.hasNext());
	assertEquals(4, (int) iterator.next());
	assertTrue(iterator.hasNext());
	assertEquals(5, (int) iterator.next());
	assertFalse(iterator.hasNext());
    }

    @Test
    public void testNoSuchElementExceptionForNext() {
	expectedException.expect(NoSuchElementException.class);
	PeekingIterator<Integer> iterator = new AbstractIterator<Integer>() {
	    @Override
	    protected Integer findNext() {
		return null;
	    }
	};
	assertFalse(iterator.hasNext());
	iterator.next();
    }

    @Test
    public void testNoSuchElementExceptionForPeek() {
	expectedException.expect(NoSuchElementException.class);
	PeekingIterator<Integer> iterator = new AbstractIterator<Integer>() {
	    @Override
	    protected Integer findNext() {
		return null;
	    }
	};
	assertFalse(iterator.hasNext());
	iterator.peek();
    }

}
