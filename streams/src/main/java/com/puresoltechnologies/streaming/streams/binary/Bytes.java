package com.puresoltechnologies.streaming.streams.binary;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This interface is used to define a byte converter for all basic types and
 * some advanced types used in JDK.
 * 
 * @author Rick-Rainer Ludwig
 */
public interface Bytes {

    /**
     * This method creates a new {@link Bytes} object for a given {@link ByteOrder}.
     * 
     * 
     * @param byteOrder
     *            is the byte order to be used for reading the input stream.
     * @return Returns a new {@link Bytes} for the byte order provided.
     */
    public static Bytes forByteOrder(ByteOrder byteOrder) {
	if (byteOrder == ByteOrder.LITTLE_ENDIAN) {
	    return new LittleEndianBytes();
	} else if (byteOrder == ByteOrder.BIG_ENDIAN) {
	    return new BigEndianBytes();
	} else {
	    throw new IllegalArgumentException("Byte order '" + byteOrder.toString() + "' is not supported.");
	}
    }

    /**
     * This method returns the used byte order.
     * 
     * @return A {@link ByteOrder} object is returned.
     */
    public ByteOrder getByteOrder();

    public int putBytes(byte[] destination, int destinationOffset, byte[] source);

    public byte[] fromBoolean(boolean b);

    public int putBoolean(byte[] bytes, boolean b, int offset);

    public boolean toBoolean(byte[] bytes);

    public boolean toBoolean(byte[] bytes, int offset);

    public byte[] fromByte(byte b);

    public int putByte(byte[] bytes, byte b, int offset);

    public byte toByte(byte[] bytes);

    public byte toByte(byte[] bytes, int offset);

    public byte[] fromUnsignedByte(int b);

    public int putUnsignedByte(byte[] bytes, int b, int offset);

    public int toUnsignedByte(byte[] bytes);

    public int toUnsignedByte(byte[] bytes, int offset);

    public byte[] fromShort(short i);

    public int putShort(byte[] bytes, short s, int offset);

    public short toShort(byte[] bytes);

    public short toShort(byte[] bytes, int offset);

    public byte[] fromUnsignedShort(int i);

    public int putUnsignedShort(byte[] bytes, int s, int offset);

    public int toUnsignedShort(byte[] bytes);

    public int toUnsignedShort(byte[] bytes, int offset);

    public byte[] fromInt(int i);

    public int putInt(byte[] bytes, int i, int offset);

    public int toInt(byte[] bytes);

    public int toInt(byte[] bytes, int offset);

    public byte[] fromUnsignedInt(long i);

    public int putUnsignedInt(byte[] bytes, long i, int offset);

    public long toUnsignedInt(byte[] bytes);

    public long toUnsignedInt(byte[] bytes, int offset);

    public byte[] fromLong(long l);

    public int putLong(byte[] bytes, long l, int offset);

    public long toLong(byte[] bytes);

    public long toLong(byte[] bytes, int offset);

    public byte[] fromFloat(float f);

    public int putFloat(byte[] bytes, float f, int offset);

    public float toFloat(byte[] bytes);

    public float toFloat(byte[] bytes, int offset);

    public byte[] fromDouble(double d);

    public int putDouble(byte[] bytes, double d, int offset);

    public double toDouble(byte[] bytes);

    public double toDouble(byte[] bytes, int offset);

    public byte[] fromString(String string, Charset charset);

    public String toString(byte[] bytes, Charset charset);

    public String toHumanReadableString(byte[] bytes);

    public String toHexString(byte[] bytes);

    public byte[] fromHexString(String identifier);

    public byte[] fromInstant(Instant timestamp);

    public Instant toInstant(byte[] bytes);

    public byte[] fromLocalDate(LocalDate localDate);

    public LocalDate toLocalDate(byte[] bytes);

    public byte[] fromLocalTime(LocalTime localTime);

    public LocalTime toLocalTime(byte[] bytes);

    public byte[] fromLocalDateTime(LocalDateTime localDateTime);

    public LocalDateTime toLocalDateTime(byte[] bytes);

    public Instant toTombstone(byte[] bytes);

}
