package com.puresoltechnologies.streaming.binary.mapper;

import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;

import org.junit.Test;

import com.puresoltechnologies.streaming.binary.BinaryInputStream;
import com.puresoltechnologies.streaming.binary.BinaryOutputStream;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryCreator;
import com.puresoltechnologies.streaming.binary.mapper.annotations.BinaryUnsignedByte;

public class BinaryMapperTest {

    static class MapperExample {

	private final int ub;

	@BinaryCreator
	public MapperExample(@BinaryUnsignedByte(name = "ub") int ub) {
	    this.ub = ub;
	}

	public int getUb() {
	    return ub;
	}

    }

    @Test
    public void testDeserialization() throws BinaryMappingException, IOException {
	try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		BinaryOutputStream binaryOutputStream = new BinaryOutputStream(byteArrayOutputStream,
			ByteOrder.LITTLE_ENDIAN)) {
	    binaryOutputStream.writeUnsignedByte(250);
	    try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
		    byteArrayOutputStream.toByteArray());
		    BinaryInputStream binaryInputStream = new BinaryInputStream(byteArrayInputStream,
			    ByteOrder.LITTLE_ENDIAN)) {

		BinaryMapper mapper = new BinaryMapper();

		MapperExample structure = mapper.read(binaryInputStream, MapperExample.class);
		assertNotNull(structure);
	    }
	}
    }

}
