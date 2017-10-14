package com.puresoltechnologies.streaming.streams.csv;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class CSVReaderTest {

    @Test
    public void test() throws IOException {
	try (InputStream inputStream = CSVReaderTest.class.getResourceAsStream("/csv/test.csv")) {
	    CSVReader reader = new CSVReader(inputStream, true);
	    CSVHeader header = reader.getHeader();
	    System.out.println(header);
	    reader.forEachRemaining((record) -> System.out.println(record));
	}
    }

}
