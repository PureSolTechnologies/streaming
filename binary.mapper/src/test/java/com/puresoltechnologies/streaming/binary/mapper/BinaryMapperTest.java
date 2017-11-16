package com.puresoltechnologies.streaming.binary.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
