package com.puresoltechnologies.streaming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.puresoltechnologies.streaming.iterators.CloseableStreamIterator;
import com.puresoltechnologies.streaming.iterators.StreamIterator;

public class CloseableStreamIteratorTest {

    @Test
    public void testEmpty() {
	CloseableStreamIterator<Object> empty = CloseableStreamIterator.empty();
	assertNotNull(empty);
	assertFalse(empty.hasNext());
    }

    @Test
    public void testOfArray() throws Exception {
	List<Integer> original = new ArrayList<>();
	original.add(null);
	original.add(0);
	original.add(1);
	original.add(null);
	original.add(null);
	original.add(2);
	original.add(null);
	AutoCloseable autoCloseable = mock(AutoCloseable.class);
	StreamIterator<Integer> streamIterator = StreamIterator.of(original.iterator());
	CloseableStreamIterator<Integer> closeableStreamIterator = CloseableStreamIterator.of(streamIterator,
		autoCloseable);

	assertTrue(streamIterator.hasNext());
	assertEquals(0, (int) streamIterator.next());
	assertTrue(streamIterator.hasNext());
	assertEquals(1, (int) streamIterator.next());
	assertTrue(streamIterator.hasNext());
	assertEquals(2, (int) streamIterator.next());
	assertFalse(streamIterator.hasNext());

	closeableStreamIterator.close();

	Mockito.verify(autoCloseable, times(1)).close();

    }

    @Test
    public void testOfCollection() throws Exception {
	List<Integer> original = new ArrayList<>();
	original.add(null);
	original.add(0);
	original.add(1);
	original.add(null);
	original.add(null);
	original.add(2);
	original.add(null);
	AutoCloseable autoCloseable = mock(AutoCloseable.class);
	List<AutoCloseable> autoCloseableList = new ArrayList<>();
	autoCloseableList.add(autoCloseable);
	StreamIterator<Integer> streamIterator = StreamIterator.of(original.iterator());
	CloseableStreamIterator<Integer> closeableStreamIterator = CloseableStreamIterator.of(streamIterator,
		autoCloseableList);

	assertTrue(streamIterator.hasNext());
	assertEquals(0, (int) streamIterator.next());
	assertTrue(streamIterator.hasNext());
	assertEquals(1, (int) streamIterator.next());
	assertTrue(streamIterator.hasNext());
	assertEquals(2, (int) streamIterator.next());
	assertFalse(streamIterator.hasNext());

	closeableStreamIterator.close();

	Mockito.verify(autoCloseable, times(1)).close();

    }

}
