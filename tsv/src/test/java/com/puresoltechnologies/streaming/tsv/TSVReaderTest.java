package com.puresoltechnologies.streaming.tsv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import com.puresoltechnologies.streaming.iterators.CountingStreamIterator;

public class TSVReaderTest {

    @Test
    public void test() throws IOException {
	try (InputStream inputStream = TSVReaderTest.class.getResourceAsStream("test.tsv")) {
	    TSVReader reader = new TSVReader(inputStream, true);
	    TSVHeader header = reader.getHeader();
	    System.out.println(header);
	    assertEquals(6, header.getColumnCount());
	    CountingStreamIterator<TSVRecord> countingStreamIterator = new CountingStreamIterator<>(reader);
	    countingStreamIterator.forEachRemaining(
		    (record) -> System.out.println(countingStreamIterator.getCount() + ": " + record));
	}
    }

}
