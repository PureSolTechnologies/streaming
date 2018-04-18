package com.puresoltechnologies.streaming;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class StackedStreamIteratorTest {

    @Test
    public void test() {
	List<File> directories = Arrays.asList(new File("dir1"), new File("dir2"), new File("dir3"));
	final List<String> files = Arrays.asList("file1", "file2", "file3");
	StackedStreamIterator<File, File> stackedIterator = new StackedStreamIterator<File, File>(
		directories.iterator(), //
		directory -> new ConvertingStreamIterator<String, File>(files.iterator(),
			(file) -> new File(directory, file)));
	assertTrue(stackedIterator.hasNext());
	assertEquals(new File("dir1", "file1"), stackedIterator.next());
	assertTrue(stackedIterator.hasNext());
	assertEquals(new File("dir1", "file2"), stackedIterator.next());
	assertTrue(stackedIterator.hasNext());
	assertEquals(new File("dir1", "file3"), stackedIterator.next());
	assertTrue(stackedIterator.hasNext());
	assertEquals(new File("dir2", "file1"), stackedIterator.next());
	assertTrue(stackedIterator.hasNext());
	assertEquals(new File("dir2", "file2"), stackedIterator.next());
	assertTrue(stackedIterator.hasNext());
	assertEquals(new File("dir2", "file3"), stackedIterator.next());
	assertTrue(stackedIterator.hasNext());
	assertEquals(new File("dir3", "file1"), stackedIterator.next());
	assertTrue(stackedIterator.hasNext());
	assertEquals(new File("dir3", "file2"), stackedIterator.next());
	assertTrue(stackedIterator.hasNext());
	assertEquals(new File("dir3", "file3"), stackedIterator.next());
	assertFalse(stackedIterator.hasNext());
    }

}
