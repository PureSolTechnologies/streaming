package com.puresoltechnologies.streaming.csv;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.puresoltechnologies.streaming.CountingStreamIterator;
import com.puresoltechnologies.streaming.csv.CSVHeader;
import com.puresoltechnologies.streaming.csv.CSVReader;
import com.puresoltechnologies.streaming.csv.CSVRecord;

public class CSVReaderTest {

    @Test
    public void test() throws IOException {
	try (InputStream inputStream = CSVReaderTest.class.getResourceAsStream("/csv/test.csv")) {
	    CSVReader reader = new CSVReader(inputStream, true);
	    CSVHeader header = reader.getHeader();
	    System.out.println(header);
	    CountingStreamIterator<CSVRecord> countingStreamIterator = new CountingStreamIterator<>(reader);
	    countingStreamIterator.forEachRemaining(
		    (record) -> System.out.println(countingStreamIterator.getCount() + ": " + record));
	}
    }

}
