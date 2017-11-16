package com.puresoltechnologies.streaming.csv.mapper;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.puresoltechnologies.streaming.common.mapper.AbstractMapper;
import com.puresoltechnologies.streaming.common.mapper.ElementDefinition;
import com.puresoltechnologies.streaming.common.mapper.MappingDefinition;
import com.puresoltechnologies.streaming.common.mapper.MappingException;
import com.puresoltechnologies.streaming.csv.CSVReader;
import com.puresoltechnologies.streaming.csv.CSVRecord;
import com.puresoltechnologies.streaming.csv.CSVWriter;
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
	super(CSVCreator.class, elementAnnotations, Charset.defaultCharset());
    }

    public <C> C read(CSVReader csvReader, Class<C> clazz) throws BinaryMappingException {
	try {
	    MappingDefinition<C> definition = generateMappingDefinition(clazz);
	    List<ElementDefinition<?>> elementDefinitions = definition.getElementDefinitionsOrdered();
	    CSVRecord record = csvReader.next();
	    int fieldCount = record.getFieldCount();
	    if (fieldCount != elementDefinitions.size()) {
		throw new BinaryMappingException(
			"The number of elements int the class '" + clazz.getName() + "' (" + elementDefinitions.size()
				+ ") do not match the number of columns in the current row (" + fieldCount + ").");
	    }
	    Class<?> types[] = new Class<?>[elementDefinitions.size()];
	    Object parameters[] = new Object[elementDefinitions.size()];
	    List<String> fields = record.getFields();
	    for (int i = 0; i < elementDefinitions.size(); i++) {
		ElementDefinition<?> elementDefinition = elementDefinitions.get(i);
		types[i] = elementDefinition.getType();
		parameters[i] = readValue(fields.get(i), elementDefinition);
	    }
	    C c = clazz.getConstructor(types).newInstance(parameters);
	    return c;
	} catch (MappingException | NoSuchMethodException | SecurityException | InstantiationException
		| IllegalAccessException | IllegalArgumentException | InvocationTargetException | IOException e) {
	    throw new BinaryMappingException("Could not read class " + clazz + " from binary input stream.", e);
	}
    }

    private <C> Object readValue(String element, ElementDefinition<?> elementDefinition) throws IOException {
	Class<? extends Annotation> type = elementDefinition.getAnnotation();
	if (type.equals(CSVBoolean.class)) {
	    return Boolean.valueOf(element);
	} else if (type.equals(CSVByte.class)) {
	    return Byte.valueOf(element);
	} else if (type.equals(CSVDouble.class)) {
	    return Double.valueOf(element);
	} else if (type.equals(CSVFloat.class)) {
	    return Float.valueOf(element);
	} else if (type.equals(CSVInt.class)) {
	    return Integer.valueOf(element);
	} else if (type.equals(CSVLong.class)) {
	    return Long.valueOf(element);
	} else if (type.equals(CSVShort.class)) {
	    return Short.valueOf(element);
	} else if (type.equals(CSVString.class)) {
	    return element;
	}
	throw new IOException("Element annotation '" + type.getSimpleName() + "' is not supported.");
    }

    public <C> void write(CSVWriter csvWriter, C object) throws BinaryMappingException, IOException {
	@SuppressWarnings("unchecked")
	Class<C> clazz = (Class<C>) object.getClass();
	try {
	    MappingDefinition<C> definition = generateMappingDefinition(clazz);
	    List<ElementDefinition<?>> elementDefinitions = definition.getElementDefinitionsOrdered();
	    for (int i = 0; i < elementDefinitions.size(); i++) {
		ElementDefinition<?> elementDefinition = elementDefinitions.get(i);
		writeValue(csvWriter, elementDefinition.getAnnotation(), elementDefinition.getGetter().invoke(object));
	    }
	} catch (MappingException | SecurityException | IllegalAccessException | IllegalArgumentException
		| InvocationTargetException e) {
	    throw new BinaryMappingException("Could not write class " + clazz + " to binary output stream.", e);
	}
    }

    private void writeValue(CSVWriter csvWriter, Class<? extends Annotation> type, Object value) throws IOException {
	if (type.equals(CSVBoolean.class)) {
	    csvWriter.write((Boolean) value);
	} else if (type.equals(CSVByte.class)) {
	    csvWriter.write((Byte) value);
	} else if (type.equals(CSVDouble.class)) {
	    csvWriter.write((Double) value);
	} else if (type.equals(CSVFloat.class)) {
	    csvWriter.write((Float) value);
	} else if (type.equals(CSVInt.class)) {
	    csvWriter.write((Integer) value);
	} else if (type.equals(CSVLong.class)) {
	    csvWriter.write((Long) value);
	} else if (type.equals(CSVShort.class)) {
	    csvWriter.write((Short) value);
	} else if (type.equals(CSVString.class)) {
	    csvWriter.write((String) value);
	} else {
	    throw new IOException("Element annotation '" + type.getSimpleName() + "' is not supported.");
	}
    }

}
