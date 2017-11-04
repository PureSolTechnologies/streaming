package com.puresoltechnologies.streaming.binary;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

import org.junit.Test;

public class RestCDataTest {

    @Test
    public void test() throws IOException {
	try (InputStream stream = RestCDataTest.class.getResourceAsStream("/testdata.bin");
		BinaryInputStream input = new BinaryInputStream(stream, ByteOrder.LITTLE_ENDIAN)) {
	    assertEquals(-128, input.readSignedByte());
	    assertEquals(-42, input.readSignedByte());
	    assertEquals(0, input.readSignedByte());
	    assertEquals(42, input.readSignedByte());
	    assertEquals(127, input.readSignedByte());

	    assertEquals(0, input.readUnsignedByte());
	    assertEquals(42, input.readUnsignedByte());
	    assertEquals(255, input.readUnsignedByte());

	    assertEquals(-32768, input.readSignedShort());
	    assertEquals(-42, input.readSignedShort());
	    assertEquals(0, input.readSignedShort());
	    assertEquals(42, input.readSignedShort());
	    assertEquals(32767, input.readSignedShort());

	    assertEquals(0, input.readUnsignedShort());
	    assertEquals(42, input.readUnsignedShort());
	    assertEquals(65535, input.readUnsignedShort());

	    assertEquals(-2147483648, input.readSignedInt());
	    assertEquals(-42, input.readSignedInt());
	    assertEquals(0, input.readSignedInt());
	    assertEquals(42, input.readSignedInt());
	    assertEquals(2147483647, input.readSignedInt());

	    assertEquals(0, input.readUnsignedInt());
	    assertEquals(42, input.readUnsignedInt());
	    assertEquals(4294967295l, input.readUnsignedInt());

	    assertEquals(-9223372036854775807l, input.readSignedLong());
	    assertEquals(-42, input.readSignedLong());
	    assertEquals(0, input.readSignedLong());
	    assertEquals(42, input.readSignedLong());
	    assertEquals(9223372036854775807l, input.readSignedLong());

	    assertEquals((float) -1.2345678, input.readFloat(), (float) 1e-7);
	    assertEquals((float) -42.0, input.readFloat(), (float) 1e-7);
	    assertEquals((float) 0.0, input.readFloat(), (float) 1e-7);
	    assertEquals((float) 42.0, input.readFloat(), (float) 1e-7);
	    assertEquals((float) 1.2345678, input.readFloat(), (float) 1e-7);

	    assertEquals(-1.234567890123, input.readDouble(), 1e-12);
	    assertEquals(-42.0, input.readDouble(), 1e-12);
	    assertEquals(0.0, input.readDouble(), 1e-12);
	    assertEquals(42.0, input.readDouble(), 1e-12);
	    assertEquals(1.234567890123, input.readDouble(), 1e-12);

	    assertEquals("Hello, world!", input.readNulTerminatedString(Charset.forName("ASCII")));
	}
    }

}
