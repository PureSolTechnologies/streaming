package com.puresoltechnologies.streaming.csv;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Test;

import com.puresoltechnologies.streaming.iterators.CountingStreamIterator;

public class CSVReaderTest {

    @Test
    public void test() throws IOException {
	try (InputStream inputStream = CSVReaderTest.class.getResourceAsStream("test.csv")) {
	    CSVReader reader = new CSVReader(inputStream, true);
	    CSVHeader header = reader.getHeader();
	    assertEquals(6, header.getColumnCount());
	    System.out.println(header);
	    CountingStreamIterator<CSVRecord> countingStreamIterator = new CountingStreamIterator<>(reader);
	    countingStreamIterator.forEachRemaining(
		    (record) -> System.out.println(countingStreamIterator.getCount() + ": " + record));
	}
    }

}
