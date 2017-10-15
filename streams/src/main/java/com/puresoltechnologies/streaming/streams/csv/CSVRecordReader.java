package com.puresoltechnologies.streaming.streams.csv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

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
	    String line = readLine(inputStream);
	    if (line == null) {
		return null;
	    }
	    CSVRecord record = new CSVRecord();
	    String[] fields = line.split(",");
	    for (int fieldId = 0; fieldId < fields.length; ++fieldId) {
		record.addField(fields[fieldId]);
	    }
	    return record;
	} catch (IOException e) {
	    return null;
	}
    }

    private String readLine(InputStream inputStreamReader) throws IOException {
	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
	int quoteCount = 0;
	while (true) {
	    int c = inputStreamReader.read();
	    if (c == -1) {
		return null;
	    }
	    if ((c == '\n') || (c == '\r')) {
		if ((byteArrayOutputStream.size() == 0)) {
		    continue;
		}
		if (quoteCount % 2 == 0) {
		    break;
		}
	    }
	    byteArrayOutputStream.write(c);
	    if (c == '"') {
		++quoteCount;
	    }
	}
	return byteArrayOutputStream.toString();
    }

}
