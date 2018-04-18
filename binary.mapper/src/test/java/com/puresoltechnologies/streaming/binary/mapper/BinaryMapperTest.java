package com.puresoltechnologies.streaming.binary.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.puresoltechnologies.streaming.binary.BinaryInputStream;
import com.puresoltechnologies.streaming.binary.BinaryOutputStream;

@RunWith(Parameterized.class)
public class BinaryMapperTest<T> {

    @Parameters(name = "{0}")
    public static Iterable<Object[]> data() {
	return Arrays.asList(new Object[][] { //
		{ ByteExample.class, new ByteExample(250, (byte) -42) }, //
		{ ShortExample.class, new ShortExample(65000, (short) -32000) }, //
		{ IntExample.class, new IntExample(4000000000l, -2000000000) }, //
		{ LongExample.class, new LongExample(123456789000l) }, //
		{ FloatingPointExample.class, new FloatingPointExample((float) 1.234e12, 1.23456e135) }, //
		{ BooleanStringExample.class, new BooleanStringExample(true, "Hello, world!") }, //
	});
    }

    private final Class<T> clazz;
    private final BinaryMapper mapper = new BinaryMapper();
    private final Object original;

    public BinaryMapperTest(Class<T> clazz, Object o) {
	super();
	this.clazz = clazz;
	this.original = o;
    }

    @Test
    public void testDeserialization() throws BinaryMappingException, IOException {

	try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		BinaryOutputStream binaryOutputStream = new BinaryOutputStream(byteArrayOutputStream,
			ByteOrder.LITTLE_ENDIAN)) {
	    mapper.write(binaryOutputStream, original);
	    try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
		    byteArrayOutputStream.toByteArray());
		    BinaryInputStream binaryInputStream = new BinaryInputStream(byteArrayInputStream,
			    ByteOrder.LITTLE_ENDIAN)) {
		T deserialized = mapper.read(binaryInputStream, clazz);
		assertNotNull(deserialized);
		assertEquals(original, deserialized);
	    }
	}
    }

}
