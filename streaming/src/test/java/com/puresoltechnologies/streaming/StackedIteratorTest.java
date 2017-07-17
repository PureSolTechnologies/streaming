package com.puresoltechnologies.streaming;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.puresoltechnologies.streaming.ConverterIterator.Converter;
import com.puresoltechnologies.streaming.StackedIterator.Creator;

public class StackedIteratorTest {

    @Test
    public void test() {
	List<File> directories = Arrays.asList(new File("dir1"), new File("dir2"), new File("dir3"));
	final List<String> files = Arrays.asList("file1", "file2", "file3");
	StackedIterator<File, File> stackedIterator = new StackedIterator<File, File>(directories.iterator(),
		new Creator<File, File>() {
		    @Override
		    public Iterator<File> create(Iterator<File> ierator, final File directory) {
			return new ConverterIterator<String, File>(files.iterator(), new Converter<String, File>() {

			    @Override
			    public File convert(Iterator<String> iterator, String file) {
				return new File(directory, file);
			    }
			});
		    }
		});
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
