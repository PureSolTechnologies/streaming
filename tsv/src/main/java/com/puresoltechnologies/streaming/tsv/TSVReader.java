package com.puresoltechnologies.streaming.tsv;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.NoSuchElementException;

import com.puresoltechnologies.streaming.iterators.StreamIterator;
import com.puresoltechnologies.streaming.streams.InputStreamIterator;

/**
 * This class provides an TSV reader. The default settings read the provided
 * stream as RFC4180 (https://tools.ietf.org/html/rfc4180) compliant file. The
 * only change is that separation of columns is done with tabs '\t'.
 *
 * @author Rick-Rainer Ludwig
 */
public class TSVReader implements StreamIterator<TSVRecord> {

    private final InputStreamIterator<InputStream, TSVRecord> streamIterator;
    private final boolean hasHeader;
    private final TSVHeader header;

    public TSVReader(InputStream inputStream) {
	this(inputStream, Charset.defaultCharset(), false);
    }

    public TSVReader(InputStream inputStream, Charset charset) {
	this(inputStream, charset, false);
    }

    public TSVReader(InputStream inputStream, boolean hasHeader) {
	this(inputStream, Charset.defaultCharset(), hasHeader);
    }

    public TSVReader(InputStream inputStream, Charset charset, boolean hasHeader) {
	super();
	streamIterator = new InputStreamIterator<>(inputStream, new TSVRecordReader(charset));
	this.hasHeader = hasHeader;
	header = readHeader();
    }

    private TSVHeader readHeader() {
	if (hasHeader && streamIterator.hasNext()) {
	    TSVRecord record = streamIterator.next();
	    return new TSVHeader(record.getFields());
	} else {
	    return new TSVHeader(new ArrayList<>());
	}
    }

    public TSVHeader getHeader() {
	return header;
    }

    @Override
    public boolean hasNext() {
	return streamIterator.hasNext();
    }

    @Override
    public TSVRecord next() throws NoSuchElementException {
	return streamIterator.next();
    }

    @Override
    public TSVRecord peek() throws NoSuchElementException {
	return streamIterator.peek();
    }

}
