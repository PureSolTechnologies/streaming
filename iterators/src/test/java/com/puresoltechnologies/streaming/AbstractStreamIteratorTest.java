package com.puresoltechnologies.streaming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;

import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;

public class AbstractStreamIteratorTest {

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testSimple() {
	StreamIterator<Integer> iterator = new AbstractStreamIterator<Integer>() {

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
	StreamIterator<Integer> iterator = new AbstractStreamIterator<Integer>() {
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
	StreamIterator<Integer> iterator = new AbstractStreamIterator<Integer>() {
	    @Override
	    protected Integer findNext() {
		return null;
	    }
	};
	assertFalse(iterator.hasNext());
	iterator.peek();
    }

}
