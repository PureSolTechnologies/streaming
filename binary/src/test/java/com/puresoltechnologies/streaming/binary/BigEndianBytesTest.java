package com.puresoltechnologies.streaming.binary;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.nio.ByteOrder;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class BigEndianBytesTest extends AbstractBytesTest {

    private static final Bytes bigEndianBytes = Bytes.forByteOrder(ByteOrder.BIG_ENDIAN);

    public BigEndianBytesTest() {
	super(bigEndianBytes);
    }

    @Test
    public void testLongConversionBigEndian() {
	long l = 1;
	byte[] encoded = bigEndianBytes.fromLong(l);
	assertEquals(8, encoded.length);
	assertEquals(0, encoded[0]);
	assertEquals(0, encoded[1]);
	assertEquals(0, encoded[2]);
	assertEquals(0, encoded[3]);
	assertEquals(0, encoded[4]);
	assertEquals(0, encoded[5]);
	assertEquals(0, encoded[6]);
	assertEquals(1, encoded[7]);
	long decoded = bigEndianBytes.toLong(encoded);
	assertEquals(l, decoded);
    }

    @Test
    public void testBoolean() {
	boolean b = true;
	byte[] bytes = bigEndianBytes.fromBoolean(b);
	assertEquals(1, bytes.length);
	assertEquals(1, bytes[0]);
	assertEquals(b, bigEndianBytes.toBoolean(bytes));

	b = false;
	bytes = bigEndianBytes.fromBoolean(b);
	assertEquals(1, bytes.length);
	assertEquals(0, bytes[0]);
	assertEquals(b, bigEndianBytes.toBoolean(bytes));
    }

    @Test
    public void testByte() {
	byte b = -128;
	for (;;) {
	    byte[] bytes = bigEndianBytes.fromByte(b);
	    assertEquals(1, bytes.length);
	    assertEquals(b, bigEndianBytes.toByte(bytes));
	    if (b == 127) {
		break;
	    }
	    b++;
	}
    }

    @Test
    public void testUnsignedByte() {
	int b = 0;
	for (;;) {
	    byte[] bytes = bigEndianBytes.fromUnsignedByte(b);
	    assertEquals(1, bytes.length);
	    assertEquals(b, bigEndianBytes.toUnsignedByte(bytes));
	    if (b == 255) {
		break;
	    }
	    b++;
	}
    }

    @Test
    public void testShort() {
	short s = -32_768;
	for (;;) {
	    byte[] bytes = bigEndianBytes.fromShort(s);
	    assertEquals(2, bytes.length);
	    assertEquals(s, bigEndianBytes.toShort(bytes));
	    if (s == 32_767) {
		break;
	    }
	    s++;
	}
    }

    @Test
    public void testUnsignedShort() {
	int s = 0;
	for (;;) {
	    byte[] bytes = bigEndianBytes.fromUnsignedShort(s);
	    assertEquals(2, bytes.length);
	    assertEquals(s, bigEndianBytes.toUnsignedShort(bytes));
	    if (s == 65_535) {
		break;
	    }
	    s++;
	}
    }

    @Test
    public void testInt() {
	int i = -2_147_483_648;
	for (;;) {
	    byte[] bytes = bigEndianBytes.fromInt(i);
	    assertEquals(4, bytes.length);
	    assertEquals(i, bigEndianBytes.toInt(bytes));
	    if (i == 2_147_483_647) {
		break;
	    }
	    i++;
	}
    }

    @Test
    public void testUnsignedInt() {
	long i = 0;
	for (;;) {
	    byte[] bytes = bigEndianBytes.fromUnsignedInt(i);
	    assertEquals(8, bytes.length);
	    assertEquals(i, bigEndianBytes.toUnsignedInt(bytes));
	    if (i == 4_294_967_295l) {
		break;
	    }
	    i++;
	}
    }

    @Test
    public void testLong() {
	List<Long> longs = new ArrayList<>();
	longs.add(Long.MIN_VALUE);
	longs.add(-1_000_000_000_000l);
	longs.add(-1l);
	longs.add(0l);
	longs.add(1l);
	longs.add(1_000_000_000_000l);
	longs.add(Long.MAX_VALUE);
	for (long l : longs) {
	    byte[] bytes = bigEndianBytes.fromLong(l);
	    assertEquals(8, bytes.length);
	    assertEquals(l, bigEndianBytes.toLong(bytes));
	}
    }

    @Test
    public void testFloat() {
	float b = (float) 1.23456e4;
	byte[] bytes = bigEndianBytes.fromFloat(b);
	assertEquals(b, bigEndianBytes.toFloat(bytes), 0.0);
    }

    @Test
    public void testDouble() {
	double b = 1.23456e4;
	byte[] bytes = bigEndianBytes.fromDouble(b);
	assertEquals(b, bigEndianBytes.toDouble(bytes), 0.0);
    }

    @Test
    public void testDouble2() {
	double b = 1;
	byte[] bytes = bigEndianBytes.fromDouble(b);
	assertEquals(0x3F, bytes[0] & 0xFF);
	assertEquals(0xF0, bytes[1] & 0xFF);
	assertEquals(0, bytes[2]);
	assertEquals(0, bytes[3]);
	assertEquals(0, bytes[4]);
	assertEquals(0, bytes[5]);
	assertEquals(0, bytes[6]);
	assertEquals(0, bytes[7]);
	assertEquals(b, bigEndianBytes.toDouble(bytes), 0.0);
    }

    @Test
    public void testInstantConversion() {
	Instant now = Instant.now();
	byte[] encoded = bigEndianBytes.fromInstant(now);
	assertNotNull(encoded);
	assertEquals(12, encoded.length);
	Instant decoded = bigEndianBytes.toInstant(encoded);
	assertEquals(now, decoded);
	assertEquals(now.getEpochSecond(), decoded.getEpochSecond());
	assertEquals(now.getNano(), decoded.getNano());
    }

    @Test
    public void testLocalTime() {
	LocalTime localTime = LocalTime.of(11, 12, 13, 123456789);
	byte[] bytes = bigEndianBytes.fromLocalTime(localTime);
	assertEquals(localTime, bigEndianBytes.toLocalTime(bytes));
    }

    @Test
    public void testLocalDate() {
	LocalDate localDate = LocalDate.of(2016, 10, 9);
	byte[] bytes = bigEndianBytes.fromLocalDate(localDate);
	assertEquals(localDate, bigEndianBytes.toLocalDate(bytes));
    }

    @Test
    public void testLocalDateTime() {
	LocalDateTime localDateTime = LocalDateTime.of(2016, 10, 9, 11, 12, 13, 123456789);
	byte[] bytes = bigEndianBytes.fromLocalDateTime(localDateTime);
	assertEquals(localDateTime, bigEndianBytes.toLocalDateTime(bytes));
    }
}
