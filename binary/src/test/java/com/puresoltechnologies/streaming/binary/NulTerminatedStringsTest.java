package com.puresoltechnologies.streaming.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

public class NulTerminatedStringsTest {

    @Test
    public void test() throws IOException {
	String originalString = "Hello, world!";
	System.out.println(originalString);
	byte[] bytes = null;
	try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		BinaryOutputStream binaryOutputStream = new BinaryOutputStream(outputStream, ByteOrder.LITTLE_ENDIAN)) {

	    binaryOutputStream.writeNulTerminatedString(originalString, Charset.defaultCharset());
	    bytes = outputStream.toByteArray();
	}

	try (ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
		BinaryInputStream binaryInputStream = new BinaryInputStream(inputStream, ByteOrder.LITTLE_ENDIAN)) {
	    String readString = binaryInputStream.readNulTerminatedString(Charset.defaultCharset());
	    System.out.println(readString);
	    assertEquals(originalString, readString);
	}
    }

}
