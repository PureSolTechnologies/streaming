package com.puresoltechnologies.streaming.tsv;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the header information of the TSV.
 *
 * @author Rick-Rainer Ludwig
 */
public class TSVHeader {

    private static final char SEPARATOR = '\t';

    private final int columnCount;
    private final List<String> columnNames = new ArrayList<>();

    public TSVHeader(List<String> columnNames) {
	super();
	this.columnCount = columnNames.size();
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
		builder.append(SEPARATOR);
	    }
	    builder.append(s);
	});
	return builder.toString();
    }

}
