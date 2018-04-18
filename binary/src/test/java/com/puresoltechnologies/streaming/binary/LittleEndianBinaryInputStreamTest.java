package com.puresoltechnologies.streaming.binary;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

import org.junit.jupiter.api.Test;

public class LittleEndianBinaryInputStreamTest {

    @Test
    public void testSignedByte() throws IOException {
	TestInputStream inputStream = new TestInputStream(0xFF, 0x00, 0x7F, 0x80, 0xD6);
	try (BinaryInputStream lebInputStream = new BinaryInputStream(inputStream, ByteOrder.LITTLE_ENDIAN)) {
	    assertEquals(-1, lebInputStream.readSignedByte());
	    assertEquals(0, lebInputStream.readSignedByte());
	    assertEquals(127, lebInputStream.readSignedByte());
	    assertEquals(-128, lebInputStream.readSignedByte());
	    assertEquals(-42, lebInputStream.readSignedByte());
	}
    }

    @Test
    public void testUnsignedByte() throws IOException {
	TestInputStream inputStream = new TestInputStream(0xFF, 0x00, 0x7F, 0x80, 0xD6);
	try (BinaryInputStream lebInputStream = new BinaryInputStream(inputStream, ByteOrder.LITTLE_ENDIAN)) {
	    assertEquals(255, lebInputStream.readUnsignedByte());
	    assertEquals(0, lebInputStream.readUnsignedByte());
	    assertEquals(127, lebInputStream.readUnsignedByte());
	    assertEquals(128, lebInputStream.readUnsignedByte());
	    assertEquals(214, lebInputStream.readUnsignedByte());
	}
    }

    @Test
    public void testSignedShort() throws IOException {
	TestInputStream inputStream = new TestInputStream( //
		0xFF, 0xFF, //
		0x00, 0x00, //
		0xFF, 0x7F, //
		0x00, 0x80, //
		0x00, 0xD6);
	try (BinaryInputStream lebInputStream = new BinaryInputStream(inputStream, ByteOrder.LITTLE_ENDIAN)) {
	    assertEquals(-1, lebInputStream.readSignedShort());
	    assertEquals(0, lebInputStream.readSignedShort());
	    assertEquals(32767, lebInputStream.readSignedShort());
	    assertEquals(-32768, lebInputStream.readSignedShort());
	    assertEquals(-10752, lebInputStream.readSignedShort());
	}
    }

    @Test
    public void testUnsignedShort() throws IOException {
	TestInputStream inputStream = new TestInputStream( //
		0xFF, 0xFF, //
		0x00, 0x00, //
		0xFF, 0x7F, //
		0x00, 0x80, //
		0xD6, 0x00);
	try (BinaryInputStream lebInputStream = new BinaryInputStream(inputStream, ByteOrder.LITTLE_ENDIAN)) {
	    assertEquals(65535, lebInputStream.readUnsignedShort());
	    assertEquals(0, lebInputStream.readUnsignedShort());
	    assertEquals(32767, lebInputStream.readUnsignedShort());
	    assertEquals(32768, lebInputStream.readUnsignedShort());
	    assertEquals(214, lebInputStream.readUnsignedShort());
	}
    }

    @Test
    public void testSignedInt() throws IOException {
	TestInputStream inputStream = new TestInputStream( //
		0xFF, 0xFF, 0xFF, 0xFF, //
		0x00, 0x00, 0x00, 0x00, //
		0xFF, 0xFF, 0xFF, 0x7F, //
		0x00, 0x00, 0x00, 0x80, //
		0x00, 0x00, 0x00, 0xD6);
	try (BinaryInputStream lebInputStream = new BinaryInputStream(inputStream, ByteOrder.LITTLE_ENDIAN)) {
	    assertEquals(-1, lebInputStream.readSignedInt());
	    assertEquals(0, lebInputStream.readSignedInt());
	    assertEquals(2147483647, lebInputStream.readSignedInt());
	    assertEquals(-2147483648, lebInputStream.readSignedInt());
	    assertEquals(-704643072, lebInputStream.readSignedInt());
	}
    }

    @Test
    public void testUnsignedInt() throws IOException {
	TestInputStream inputStream = new TestInputStream( //
		0xFF, 0xFF, 0xFF, 0xFF, //
		0x00, 0x00, 0x00, 0x00, //
		0xFF, 0xFF, 0xFF, 0x7F, //
		0x00, 0x00, 0x00, 0x80, //
		0xD6, 0x00, 0x00, 0x00);
	try (BinaryInputStream lebInputStream = new BinaryInputStream(inputStream, ByteOrder.LITTLE_ENDIAN)) {
	    assertEquals(4294967295l, lebInputStream.readUnsignedInt());
	    assertEquals(0l, lebInputStream.readUnsignedInt());
	    assertEquals(2147483647l, lebInputStream.readUnsignedInt());
	    assertEquals(2147483648l, lebInputStream.readUnsignedInt());
	    assertEquals(214l, lebInputStream.readUnsignedInt());
	}
    }

    @Test
    public void testSignedLong() throws IOException {
	TestInputStream inputStream = new TestInputStream( //
		0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, //
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, //
		0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0xFF, 0x7F, //
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x80, //
		0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0xD6);
	try (BinaryInputStream lebInputStream = new BinaryInputStream(inputStream, ByteOrder.LITTLE_ENDIAN)) {
	    assertEquals(-1, lebInputStream.readSignedLong());
	    assertEquals(0, lebInputStream.readSignedLong());
	    assertEquals(9223372036854775807l, lebInputStream.readSignedLong());
	    assertEquals(-9223372036854775808l, lebInputStream.readSignedLong());
	    assertEquals(-3026418949592973312l, lebInputStream.readSignedLong());
	}
    }

    @Test
    public void testReadNulTerminatedString() throws IOException {
	TestInputStream inputStream = new TestInputStream( //
		'A', 'B', 'C', 0);
	try (BinaryInputStream lebInputStream = new BinaryInputStream(inputStream, ByteOrder.LITTLE_ENDIAN)) {
	    String string = lebInputStream.readNulTerminatedString(Charset.forName("ASCII"));
	    assertEquals("ABC", string);
	}
    }
}
