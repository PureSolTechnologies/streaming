package com.puresoltechnologies.streaming.csv;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.List;

/**
 * This class provides an CSV writer. The default settings write toe the
 * provided stream as RFC4180 (https://tools.ietf.org/html/rfc4180) compliant
 * file.
 * 
 * @author Rick-Rainer Ludwig
 */
public class CSVWriter {

    private final OutputStream outputStream;
    private final Charset charset;

    public CSVWriter(OutputStream outputStream) {
	this(outputStream, Charset.defaultCharset());
    }

    public CSVWriter(OutputStream outputStream, Charset charset) {
	this.outputStream = outputStream;
	this.charset = charset;
    }

    public void writeHeader(CSVHeader header) throws IOException {
	int columnCount = header.getColumnCount();
	List<String> columnNames = header.getColumnNames();
	for (int i = 0; i < columnCount; ++i) {
	    write(columnNames.get(i));
	    if (i == columnCount - 1) {
		writeEndOfLine();
	    } else {
		writeSeparator();
	    }
	}
    }

    public void writeSeparator() throws IOException {
	outputStream.write(',');
    }

    public void writeEndOfLine() throws IOException {
	outputStream.write('\r');
	outputStream.write('\n');
    }

    public void write(boolean value) throws IOException {
	writeValue(value ? "true" : "false");
    }

    public void write(Byte value) throws IOException {
	writeValue(value != null ? value.toString() : "");
    }

    public void write(Short value) throws IOException {
	writeValue(value != null ? value.toString() : "");
    }

    public void write(Integer value) throws IOException {
	writeValue(value != null ? value.toString() : "");
    }

    public void write(Long value) throws IOException {
	writeValue(value != null ? value.toString() : "");
    }

    public void write(Float value) throws IOException {
	writeValue(value != null ? value.toString() : "");
    }

    public void write(Double value) throws IOException {
	writeValue(value != null ? value.toString() : "");
    }

    public void write(String value) throws IOException {
	writeValue("\"" + value.replaceAll("\"", "\"\"") + "\"");
    }

    private void writeValue(String value) throws IOException {
	outputStream.write(value.getBytes(charset));
    }

}
