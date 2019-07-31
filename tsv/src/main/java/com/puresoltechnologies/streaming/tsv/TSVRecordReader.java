package com.puresoltechnologies.streaming.tsv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import com.puresoltechnologies.streaming.streams.InputStreamIterator.InputStreamPartReader;

/**
 * This method reads a single {@link TSVRecord} from the input stream.
 *
 * @author Rick-Rainer Ludwig
 */
public class TSVRecordReader implements InputStreamPartReader<InputStream, TSVRecord> {

    private static final String SEPARATOR = "\t";

    private final Charset charset;

    public TSVRecordReader() {
	this(Charset.defaultCharset());
    }

    public TSVRecordReader(Charset charset) {
	this.charset = charset;
    }

    @Override
    public TSVRecord readPart(InputStream inputStream) {
	try {
	    String line = readLine(inputStream);
	    if (line == null) {
		return null;
	    }
	    TSVRecord record = new TSVRecord();
	    String[] fields = line.split(SEPARATOR);
	    for (String field : fields) {
		record.addField(field);
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
		if ((quoteCount % 2) == 0) {
		    break;
		}
	    }
	    byteArrayOutputStream.write(c);
	    if (c == '"') {
		++quoteCount;
	    }
	}
	return byteArrayOutputStream.toString(charset.name());
    }

}
