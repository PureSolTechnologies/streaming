package com.puresoltechnologies.streaming.streams.binary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.charset.Charset;

import org.junit.Test;

public abstract class AbstractBytesTest {

    private final Bytes converter;

    public AbstractBytesTest(Bytes bytes) {
	super();
	this.converter = bytes;
    }

    @Test
    public void testStringConversion() {
	String string = "TestString";
	byte[] encoded = converter.fromString(string, Charset.defaultCharset());
	assertNotNull(encoded);
	assertEquals(10, encoded.length);
	String decoded = converter.toString(encoded, Charset.defaultCharset());
	assertEquals(string, decoded);
    }

    @Test
    public void testEncodeAndDecodeOfString() {
	String string = "TestString!";
	byte[] bytes = converter.fromString(string, Charset.defaultCharset());
	assertEquals(string, converter.toString(bytes, Charset.defaultCharset()));
    }

    @Test
    public void testEncodeAndDecodeOfHexString() {
	String string = "00010203040506070809101112131415fdfeff";
	byte[] bytes = converter.fromHexString(string);
	assertEquals(0x00, bytes[0]);
	assertEquals(0x01, bytes[1]);
	assertEquals(0x02, bytes[2]);
	assertEquals(0x03, bytes[3]);
	assertEquals(0x04, bytes[4]);
	assertEquals(0x05, bytes[5]);
	assertEquals(0x06, bytes[6]);
	assertEquals(0x07, bytes[7]);
	assertEquals(0x08, bytes[8]);
	assertEquals(0x09, bytes[9]);
	assertEquals(0x10, bytes[10]);
	assertEquals(0x11, bytes[11]);
	assertEquals(0x12, bytes[12]);
	assertEquals(0x13, bytes[13]);
	assertEquals(0x14, bytes[14]);
	assertEquals(0x15, bytes[15]);
	assertEquals(0xfd, 0xff & bytes[16]);
	assertEquals(0xfe, 0xff & bytes[17]);
	assertEquals(0xff, 0xff & bytes[18]);
	assertEquals(string, converter.toHexString(bytes));
    }

}
