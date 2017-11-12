package com.puresoltechnologies.streaming.csv.mapper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import com.puresoltechnologies.streaming.common.mapper.AbstractMapper;
import com.puresoltechnologies.streaming.common.mapper.MappingDefinition;
import com.puresoltechnologies.streaming.common.mapper.MappingException;
import com.puresoltechnologies.streaming.csv.CSVReader;
import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVBoolean;
import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVByte;
import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVCreator;
import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVDouble;
import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVFloat;
import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVInt;
import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVLong;
import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVShort;
import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVString;

/**
 * This class is used to map from or to binary streams.
 * 
 * @author Rick-Rainer Ludwig
 */
public class CSVMapper extends AbstractMapper<CSVCreator> {

    private static final List<Class<? extends Annotation>> elementAnnotations = new ArrayList<>();
    static {
	elementAnnotations.add(CSVBoolean.class);
	elementAnnotations.add(CSVByte.class);
	elementAnnotations.add(CSVDouble.class);
	elementAnnotations.add(CSVFloat.class);
	elementAnnotations.add(CSVInt.class);
	elementAnnotations.add(CSVLong.class);
	elementAnnotations.add(CSVShort.class);
	elementAnnotations.add(CSVString.class);
    }

    public CSVMapper() {
	super(CSVCreator.class, elementAnnotations);
    }

    public <C> C read(CSVReader csvReader, Class<C> clazz) throws BinaryMappingException {
	try {
	    MappingDefinition<C> definition = generateMappingDefinition(clazz);
	    definition.getClass();
	    return null;
	} catch (MappingException e) {
	    throw new BinaryMappingException("Could not read class " + clazz + " from binary input stream.");
	}
    }

}
