package com.puresoltechnologies.streaming.csv;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the header information of the CSV.
 * 
 * @author Rick-Rainer Ludwig
 */
public class CSVHeader {

    private final int columnCount;
    private final List<String> columnNames = new ArrayList<>();

    public CSVHeader(int columnCount, List<String> columnNames) {
	super();
	this.columnCount = columnCount;
	this.columnNames.addAll(columnNames);
    }

    public int getColumnCount() {
	return columnCount;
    }

    public List<String> getColumnNames() {
	return columnNames;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	columnNames.forEach((s) -> {
	    if (builder.length() > 0) {
		builder.append(',');
	    }
	    builder.append(s);
	});
	return builder.toString();
    }

}
