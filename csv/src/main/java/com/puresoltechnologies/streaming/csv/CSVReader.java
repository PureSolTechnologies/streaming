package com.puresoltechnologies.streaming.csv;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import com.puresoltechnologies.streaming.StreamIterator;
import com.puresoltechnologies.streaming.streams.InputStreamIterator;

/**
 * This class provides an CSV reader. The default settings read the provided
 * stream as RFC4180 (https://tools.ietf.org/html/rfc4180) compliant file.
 * 
 * @author Rick-Rainer Ludwig
 */
public class CSVReader implements StreamIterator<CSVRecord> {

    private final InputStream inputStream;
    private final InputStreamIterator<InputStream, CSVRecord> streamIterator;
    private final boolean hasHeader;
    private final CSVHeader header;

    public CSVReader(InputStream inputStream) {
	this(inputStream, false);
    }

    public CSVReader(InputStream inputStream, boolean hasHeader) {
	super();
	this.inputStream = inputStream;
	streamIterator = new InputStreamIterator<InputStream, CSVRecord>(inputStream, new CSVRecordReader());
	this.hasHeader = hasHeader;
	header = readHeader();
    }

    private CSVHeader readHeader() {
	if (hasHeader && streamIterator.hasNext()) {
	    CSVRecord record = streamIterator.next();
	    return new CSVHeader(record.getFieldCount(), record.getFields());
	} else {
	    return new CSVHeader(0, new ArrayList<>());
	}
    }

    public CSVHeader getHeader() {
	return header;
    }

    @Override
    public boolean hasNext() {
	return streamIterator.hasNext();
    }

    @Override
    public CSVRecord next() throws NoSuchElementException {
	return streamIterator.next();
    }

    @Override
    public CSVRecord peek() throws NoSuchElementException {
	return streamIterator.peek();
    }

}
