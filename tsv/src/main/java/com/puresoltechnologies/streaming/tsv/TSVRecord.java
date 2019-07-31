package com.puresoltechnologies.streaming.tsv;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a single TSV record (a single row from a TSV file).
 *
 * @author Rick-Rainer Ludwig
 *
 */
public class TSVRecord {

    private final List<String> fields = new ArrayList<>();

    void addField(String string) {
	fields.add(string);
    }

    public int getFieldCount() {
	return fields.size();
    }

    public List<String> getFields() {
	return fields;
    }

    @Override
    public String toString() {
	StringBuilder builder = new StringBuilder();
	fields.forEach((s) -> {
	    if (builder.length() > 0) {
		builder.append(',');
	    }
	    builder.append(s);
	});
	return builder.toString();
    }

}
