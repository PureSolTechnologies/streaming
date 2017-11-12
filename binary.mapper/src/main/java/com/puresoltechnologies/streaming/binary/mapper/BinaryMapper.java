package com.puresoltechnologies.streaming.binary.mapper;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.puresoltechnologies.streaming.binary.BinaryInputStream;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryBoolean;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryCreator;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryDouble;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryFloat;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryNulTerminateString;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinarySignedByte;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinarySignedInt;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinarySignedLong;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinarySignedShort;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryUnsignedByte;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryUnsignedInt;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryUnsignedShort;
import com.puresoltechnologies.streaming.common.mapper.AbstractMapper;
import com.puresoltechnologies.streaming.common.mapper.MappingDefinition;
import com.puresoltechnologies.streaming.common.mapper.MappingException;

/**
 * This class is used to map from or to binary streams.
 * 
 * @author Rick-Rainer Ludwig
 */
public class BinaryMapper extends AbstractMapper<BinaryCreator> {

    private static final List<Class<? extends Annotation>> elementAnnotations = new ArrayList<>();
    static {
	elementAnnotations.add(BinaryBoolean.class);
	elementAnnotations.add(BinaryDouble.class);
	elementAnnotations.add(BinaryFloat.class);
	elementAnnotations.add(BinaryNulTerminateString.class);
	elementAnnotations.add(BinarySignedByte.class);
	elementAnnotations.add(BinarySignedInt.class);
	elementAnnotations.add(BinarySignedLong.class);
	elementAnnotations.add(BinarySignedShort.class);
	elementAnnotations.add(BinaryUnsignedByte.class);
	elementAnnotations.add(BinaryUnsignedInt.class);
	elementAnnotations.add(BinaryUnsignedShort.class);
    }

    private final Map<Class<?>, MappingDefinition<?>> definitions = new HashMap<>();

    public BinaryMapper() {
	super(BinaryCreator.class, elementAnnotations);
    }

    public <C> C read(BinaryInputStream binaryInputStream, Class<C> clazz) throws BinaryMappingException {
	try {
	    MappingDefinition<C> definition = generateMappingDefinition(clazz);
	    definition.getClass();
	    return null;
	} catch (MappingException e) {
	    throw new BinaryMappingException("Could not read class " + clazz + " from binary input stream.");
	}
    }

}
