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

import com.puresoltechnologies.streaming.binary.Bytes;

public class LittleEndianBytesTest extends AbstractBytesTest {

    private static final Bytes littleEndianBytes = Bytes.forByteOrder(ByteOrder.LITTLE_ENDIAN);

    public LittleEndianBytesTest() {
	super(littleEndianBytes);

    }

    @Test
    public void testLongConversionBigEndian() {
	long l = 1;
	byte[] encoded = littleEndianBytes.fromLong(l);
	assertEquals(8, encoded.length);
	assertEquals(1, encoded[0]);
	assertEquals(0, encoded[1]);
	assertEquals(0, encoded[2]);
	assertEquals(0, encoded[3]);
	assertEquals(0, encoded[4]);
	assertEquals(0, encoded[5]);
	assertEquals(0, encoded[6]);
	assertEquals(0, encoded[7]);
	long decoded = littleEndianBytes.toLong(encoded);
	assertEquals(l, decoded);
    }

    @Test
    public void testBoolean() {
	boolean b = true;
	byte[] bytes = littleEndianBytes.fromBoolean(b);
	assertEquals(1, bytes.length);
	assertEquals(1, bytes[0]);
	assertEquals(b, littleEndianBytes.toBoolean(bytes));

	b = false;
	bytes = littleEndianBytes.fromBoolean(b);
	assertEquals(1, bytes.length);
	assertEquals(0, bytes[0]);
	assertEquals(b, littleEndianBytes.toBoolean(bytes));
    }

    @Test
    public void testByte() {
	byte b = -128;
	for (;;) {
	    byte[] bytes = littleEndianBytes.fromByte(b);
	    assertEquals(1, bytes.length);
	    assertEquals(b, littleEndianBytes.toByte(bytes));
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
	    byte[] bytes = littleEndianBytes.fromUnsignedByte(b);
	    assertEquals(1, bytes.length);
	    assertEquals(b, littleEndianBytes.toUnsignedByte(bytes));
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
	    byte[] bytes = littleEndianBytes.fromShort(s);
	    assertEquals(2, bytes.length);
	    assertEquals(s, littleEndianBytes.toShort(bytes));
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
	    byte[] bytes = littleEndianBytes.fromUnsignedShort(s);
	    assertEquals(2, bytes.length);
	    assertEquals(s, littleEndianBytes.toUnsignedShort(bytes));
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
	    byte[] bytes = littleEndianBytes.fromInt(i);
	    assertEquals(4, bytes.length);
	    assertEquals(i, littleEndianBytes.toInt(bytes));
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
	    byte[] bytes = littleEndianBytes.fromUnsignedInt(i);
	    assertEquals(8, bytes.length);
	    assertEquals(i, littleEndianBytes.toUnsignedInt(bytes));
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
	    byte[] bytes = littleEndianBytes.fromLong(l);
	    assertEquals(8, bytes.length);
	    assertEquals(l, littleEndianBytes.toLong(bytes));
	}
    }

    @Test
    public void testFloat() {
	float b = (float) 1.23456e4;
	byte[] bytes = littleEndianBytes.fromFloat(b);
	assertEquals(b, littleEndianBytes.toFloat(bytes), 0.0);
    }

    @Test
    public void testDouble() {
	double b = 1.23456e4;
	byte[] bytes = littleEndianBytes.fromDouble(b);
	assertEquals(b, littleEndianBytes.toDouble(bytes), 0.0);
    }

    @Test
    public void testInstantConversion() {
	Instant now = Instant.now();
	byte[] encoded = littleEndianBytes.fromInstant(now);
	assertNotNull(encoded);
	assertEquals(12, encoded.length);
	Instant decoded = littleEndianBytes.toInstant(encoded);
	assertEquals(now, decoded);
	assertEquals(now.getEpochSecond(), decoded.getEpochSecond());
	assertEquals(now.getNano(), decoded.getNano());
    }

    @Test
    public void testLocalTime() {
	LocalTime localTime = LocalTime.of(11, 12, 13, 123456789);
	byte[] bytes = littleEndianBytes.fromLocalTime(localTime);
	assertEquals(localTime, littleEndianBytes.toLocalTime(bytes));
    }

    @Test
    public void testLocalDate() {
	LocalDate localDate = LocalDate.of(2016, 10, 9);
	byte[] bytes = littleEndianBytes.fromLocalDate(localDate);
	assertEquals(localDate, littleEndianBytes.toLocalDate(bytes));
    }

    @Test
    public void testLocalDateTime() {
	LocalDateTime localDateTime = LocalDateTime.of(2016, 10, 9, 11, 12, 13, 123456789);
	byte[] bytes = littleEndianBytes.fromLocalDateTime(localDateTime);
	assertEquals(localDateTime, littleEndianBytes.toLocalDateTime(bytes));
    }
}
