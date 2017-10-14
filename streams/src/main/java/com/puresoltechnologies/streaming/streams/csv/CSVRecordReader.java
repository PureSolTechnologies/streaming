package com.puresoltechnologies.streaming.streams.csv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import com.puresoltechnologies.streaming.streams.InputStreamIterator.InputStreamPartReader;

/**
 * This method reads a single CSVRecord from the input stream.
 * 
 * @author Rick-Rainer Ludwig
 */
public class CSVRecordReader implements InputStreamPartReader<InputStream, CSVRecord> {

    @Override
    public CSVRecord readPart(InputStream inputStream) {
	try {
	    CSVRecord record = new CSVRecord();
	    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	    int c = inputStream.read();
	    while (c != -1) {
		if (c == ',') {
		    record.addField(byteArrayOutputStream.toString(Charset.defaultCharset().name()));
		    byteArrayOutputStream = new ByteArrayOutputStream();
		} else if ((c == '\n') || (c == '\r')) {
		    if ((byteArrayOutputStream.size() > 0) || (record.getFieldCount() > 0)) {
			record.addField(byteArrayOutputStream.toString(Charset.defaultCharset().name()));
			return record;
		    }
		} else {
		    byteArrayOutputStream.write(c);
		}
		c = inputStream.read();
	    }
	    if ((byteArrayOutputStream.size() > 0) || (record.getFieldCount() > 0)) {
		record.addField(byteArrayOutputStream.toString(Charset.defaultCharset().name()));
		return record;
	    }
	    return null;
	} catch (IOException e) {
	    return null;
	}
    }

}
