package com.puresoltechnologies.streaming.binary.mapper;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.puresoltechnologies.streaming.binary.BinaryInputStream;
import com.puresoltechnologies.streaming.binary.BinaryOutputStream;
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
import com.puresoltechnologies.streaming.common.mapper.ElementDefinition;
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
	super(BinaryCreator.class, elementAnnotations, Charset.defaultCharset());
    }

    public BinaryMapper(Charset charset) {
	super(BinaryCreator.class, elementAnnotations, charset);
    }

    public <C> C read(BinaryInputStream binaryInputStream, Class<C> clazz) throws BinaryMappingException, IOException {
	try {
	    MappingDefinition<C> definition = generateMappingDefinition(clazz);
	    List<ElementDefinition<?>> elementDefinitions = definition.getElementDefinitionsOrdered();
	    Class<?> types[] = new Class<?>[elementDefinitions.size()];
	    Object parameters[] = new Object[elementDefinitions.size()];
	    for (int i = 0; i < elementDefinitions.size(); i++) {
		ElementDefinition<?> elementDefinition = elementDefinitions.get(i);
		types[i] = elementDefinition.getType();
		parameters[i] = readValue(binaryInputStream, elementDefinition);
	    }
	    C c = clazz.getConstructor(types).newInstance(parameters);
	    return c;
	} catch (MappingException | NoSuchMethodException | SecurityException | InstantiationException
		| IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
	    throw new BinaryMappingException("Could not read class " + clazz + " from binary input stream.", e);
	}
    }

    private <C> Object readValue(BinaryInputStream binaryInputStream, ElementDefinition<?> elementDefinition)
	    throws IOException {
	Class<? extends Annotation> type = elementDefinition.getAnnotation();
	if (type.equals(BinaryBoolean.class)) {
	    return binaryInputStream.readBoolean();
	} else if (type.equals(BinaryDouble.class)) {
	    return binaryInputStream.readDouble();
	} else if (type.equals(BinaryFloat.class)) {
	    return binaryInputStream.readFloat();
	} else if (type.equals(BinaryNulTerminateString.class)) {
	    return binaryInputStream.readNulTerminatedString(getCharset());
	} else if (type.equals(BinarySignedByte.class)) {
	    return binaryInputStream.readSignedByte();
	} else if (type.equals(BinarySignedInt.class)) {
	    return binaryInputStream.readSignedInt();
	} else if (type.equals(BinarySignedLong.class)) {
	    return binaryInputStream.readSignedLong();
	} else if (type.equals(BinarySignedShort.class)) {
	    return binaryInputStream.readSignedShort();
	} else if (type.equals(BinaryUnsignedByte.class)) {
	    return binaryInputStream.readUnsignedByte();
	} else if (type.equals(BinaryUnsignedInt.class)) {
	    return binaryInputStream.readUnsignedInt();
	} else if (type.equals(BinaryUnsignedShort.class)) {
	    return binaryInputStream.readUnsignedShort();
	}
	throw new IOException("Element annotation '" + type.getSimpleName() + "' is not supported.");
    }

    public <C> void write(BinaryOutputStream binaryOutputStream, C object) throws BinaryMappingException, IOException {
	@SuppressWarnings("unchecked")
	Class<C> clazz = (Class<C>) object.getClass();
	try {
	    MappingDefinition<C> definition = generateMappingDefinition(clazz);
	    List<ElementDefinition<?>> elementDefinitions = definition.getElementDefinitionsOrdered();
	    for (int i = 0; i < elementDefinitions.size(); i++) {
		ElementDefinition<?> elementDefinition = elementDefinitions.get(i);
		writeValue(binaryOutputStream, elementDefinition.getAnnotation(),
			elementDefinition.getGetter().invoke(object));
	    }
	} catch (MappingException | SecurityException | IllegalAccessException | IllegalArgumentException
		| InvocationTargetException e) {
	    throw new BinaryMappingException("Could not write class " + clazz + " to binary output stream.", e);
	}
    }

    private void writeValue(BinaryOutputStream binaryOutputStream, Class<? extends Annotation> type, Object value)
	    throws IOException {
	if (type.equals(BinaryBoolean.class)) {
	    binaryOutputStream.writeBoolean((boolean) value);
	} else if (type.equals(BinaryDouble.class)) {
	    binaryOutputStream.writeDouble((double) value);
	} else if (type.equals(BinaryFloat.class)) {
	    binaryOutputStream.writeFloat((float) value);
	} else if (type.equals(BinaryNulTerminateString.class)) {
	    binaryOutputStream.writeNulTerminatedString((String) value, getCharset());
	} else if (type.equals(BinarySignedByte.class)) {
	    binaryOutputStream.writeSignedByte((byte) value);
	} else if (type.equals(BinarySignedInt.class)) {
	    binaryOutputStream.writeSignedInt((int) value);
	} else if (type.equals(BinarySignedLong.class)) {
	    binaryOutputStream.writeSignedLong((long) value);
	} else if (type.equals(BinarySignedShort.class)) {
	    binaryOutputStream.writeSignedShort((short) value);
	} else if (type.equals(BinaryUnsignedByte.class)) {
	    binaryOutputStream.writeUnsignedByte((int) value);
	} else if (type.equals(BinaryUnsignedInt.class)) {
	    binaryOutputStream.writeUnsignedInt((long) value);
	} else if (type.equals(BinaryUnsignedShort.class)) {
	    binaryOutputStream.writeUnsignedShort((int) value);
	}
	throw new IOException("Element annotation '" + type.getSimpleName() + "' is not supported.");
    }
}
