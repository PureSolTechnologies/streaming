package com.puresoltechnologies.streaming.binary;

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
     * This method provides a simple empty value. This value is represented as a
     * byte array of length 0.
     * 
     * @return A zero length byte array is returned.
     */
    public byte[] empty();

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

    /**
     * This method writes a byte array into another array.
     * 
     * @param destination
     *            is the destination array to put the source bytes into.
     * @param destinationOffset
     *            is the offset of the destination array where to start writing the
     *            bytes.
     * @param source
     *            is the array of bytes to be written.
     * @return The length of the source is returned.
     * @throws IllegalArgumentException
     *             is thrown if the remaining destination bytes in the destination
     *             array is too small for the bytes from source.
     */
    public int putBytes(byte[] destination, int destinationOffset, byte[] source) throws IllegalArgumentException;

    /**
     * This method creates a byte array containing the value of the provided
     * boolean.
     * 
     * @param b
     *            is the boolean to be converted.
     * @return A byte array is returned containing the converted value.
     */
    public byte[] fromBoolean(boolean b);

    /**
     * This method writes the provided boolean value into the given byte array.
     * 
     * @param bytes
     *            is the byte array to write the converted value into.
     * @param b
     *            is the boolean to be converted.
     * @param offset
     *            is the offset where to write the converted value to.
     * @return The number of bytes (1 byte) which were written into the array are
     *         returned.
     */
    public int putBoolean(byte[] bytes, boolean b, int offset);

    public boolean toBoolean(byte[] bytes);

    public boolean toBoolean(byte[] bytes, int offset);

    /**
     * This method creates a byte array containing the value of the provided byte.
     * 
     * @param b
     *            is the byte to be converted.
     * @return A byte array is returned containing the converted value.
     */
    public byte[] fromByte(byte b);

    /**
     * This method writes the provided byte value into the given byte array.
     * 
     * @param bytes
     *            is the byte array to write the converted value into.
     * @param b
     *            is the byte to be converted.
     * @param offset
     *            is the offset where to write the converted value to.
     * @return The number of bytes (1 byte) which were written into the array are
     *         returned.
     */
    public int putByte(byte[] bytes, byte b, int offset);

    public byte toByte(byte[] bytes);

    public byte toByte(byte[] bytes, int offset);

    /**
     * This method creates a byte array containing the value of the provided
     * unsigned byte.
     * 
     * @param b
     *            is the unsigned byte to be converted.
     * @return A byte array is returned containing the converted value.
     */
    public byte[] fromUnsignedByte(int b);

    /**
     * This method writes the provided unsigned byte value into the given byte
     * array.
     * 
     * @param bytes
     *            is the byte array to write the converted value into.
     * @param b
     *            is the unsigned byte to be converted.
     * @param offset
     *            is the offset where to write the converted value to.
     * @return The number of bytes (1 byte) which were written into the array are
     *         returned.
     */
    public int putUnsignedByte(byte[] bytes, int b, int offset);

    public int toUnsignedByte(byte[] bytes);

    public int toUnsignedByte(byte[] bytes, int offset);

    /**
     * This method creates a byte array containing the value of the provided 16bit
     * short.
     * 
     * @param s
     *            is the short to be converted.
     * @return A byte array is returned containing the converted value.
     */
    public byte[] fromShort(short s);

    /**
     * This method writes the provided short value into the given byte array.
     * 
     * @param bytes
     *            is the byte array to write the converted value into.
     * @param s
     *            is the short to be converted.
     * @param offset
     *            is the offset where to write the converted value to.
     * @return The number of bytes (2 bytes) which were written into the array are
     *         returned.
     */
    public int putShort(byte[] bytes, short s, int offset);

    public short toShort(byte[] bytes);

    public short toShort(byte[] bytes, int offset);

    /**
     * This method creates a byte array containing the value of the provided
     * unsigned 16bit short.
     * 
     * @param s
     *            is the unsigned short to be converted.
     * @return A byte array is returned containing the converted value.
     */
    public byte[] fromUnsignedShort(int s);

    /**
     * This method writes the provided unsigned short value into the given byte
     * array.
     * 
     * @param bytes
     *            is the byte array to write the converted value into.
     * @param s
     *            is the unsigned short to be converted.
     * @param offset
     *            is the offset where to write the converted value to.
     * @return The number of bytes (2 bytes) which were written into the array are
     *         returned.
     */
    public int putUnsignedShort(byte[] bytes, int s, int offset);

    public int toUnsignedShort(byte[] bytes);

    public int toUnsignedShort(byte[] bytes, int offset);

    /**
     * This method creates a byte array containing the value of the provided 32bit
     * integer.
     * 
     * @param i
     *            is the integer to be converted.
     * @return A byte array is returned containing the converted value.
     */
    public byte[] fromInt(int i);

    /**
     * This method writes the provided integer value into the given byte array.
     * 
     * @param bytes
     *            is the byte array to write the converted value into.
     * @param i
     *            is the integer to be converted.
     * @param offset
     *            is the offset where to write the converted value to.
     * @return The number of bytes (4 bytes) which were written into the array are
     *         returned.
     */
    public int putInt(byte[] bytes, int i, int offset);

    public int toInt(byte[] bytes);

    public int toInt(byte[] bytes, int offset);

    /**
     * This method creates a byte array containing the value of the provided
     * unsigned 32bit integer.
     * 
     * @param i
     *            is the unsigned integer to be converted.
     * @return A byte array is returned containing the converted value.
     */
    public byte[] fromUnsignedInt(long i);

    /**
     * This method writes the provided unsigned integer value into the given byte
     * array.
     * 
     * @param bytes
     *            is the byte array to write the converted value into.
     * @param i
     *            is the unsigned integer to be converted.
     * @param offset
     *            is the offset where to write the converted value to.
     * @return The number of bytes (4 bytes) which were written into the array are
     *         returned.
     */
    public int putUnsignedInt(byte[] bytes, long i, int offset);

    public long toUnsignedInt(byte[] bytes);

    public long toUnsignedInt(byte[] bytes, int offset);

    /**
     * This method creates a byte array containing the value of the provided 64bit
     * long.
     * 
     * @param l
     *            is the long to be converted.
     * @return A byte array is returned containing the converted value.
     */
    public byte[] fromLong(long l);

    /**
     * This method writes the provided long value into the given byte array.
     * 
     * @param bytes
     *            is the byte array to write the converted value into.
     * @param l
     *            is the long to be converted.
     * @param offset
     *            is the offset where to write the converted value to.
     * @return The number of bytes (8 bytes) which were written into the array are
     *         returned.
     */
    public int putLong(byte[] bytes, long l, int offset);

    public long toLong(byte[] bytes);

    public long toLong(byte[] bytes, int offset);

    /**
     * This method creates a byte array containing the value of the provided 32bit
     * float.
     * 
     * @param f
     *            is the float to be converted.
     * @return A byte array is returned containing the converted value.
     */
    public byte[] fromFloat(float f);

    /**
     * This method writes the provided float value into the given byte array.
     * 
     * @param bytes
     *            is the byte array to write the converted value into.
     * @param f
     *            is the float to be converted.
     * @param offset
     *            is the offset where to write the converted value to.
     * @return The number of bytes (4 bytes) which were written into the array are
     *         returned.
     */
    public int putFloat(byte[] bytes, float f, int offset);

    public float toFloat(byte[] bytes);

    public float toFloat(byte[] bytes, int offset);

    /**
     * This method creates a byte array containing the value of the provided 64bit
     * double.
     * 
     * @param d
     *            is the double to be converted.
     * @return A byte array is returned containing the converted value.
     */
    public byte[] fromDouble(double d);

    /**
     * This method writes the provided double value into the given byte array.
     * 
     * @param bytes
     *            is the byte array to write the converted value into.
     * @param d
     *            is the double to be converted.
     * @param offset
     *            is the offset where to write the converted value to.
     * @return The number of bytes (8 bytes) which were written into the array are
     *         returned.
     */
    public int putDouble(byte[] bytes, double d, int offset);

    public double toDouble(byte[] bytes);

    public double toDouble(byte[] bytes, int offset);

    /**
     * This method creates a byte array containing the value of the provided string
     * using the charset provided.
     * 
     * @param string
     *            is the string to be converted.
     * @param charset
     *            is a {@link Charset} to be used for byte conversion.
     * @return A byte array is returned containing the converted value.
     */
    public byte[] fromString(String string, Charset charset);

    public String toString(byte[] bytes, Charset charset);

    public String toHumanReadableString(byte[] bytes);

    public String toHexString(byte[] bytes);

    public byte[] fromHexString(String hexString);

    /**
     * This method converts the provided {@link Instant} into an array of bytes.
     * 
     * @param instant
     *            is an {@link Instant} to be converted.
     * @return A byte array is returned containing the converted value.
     */
    public byte[] fromInstant(Instant instant);

    /**
     * This method writes the provided {@link Instant} value into the given byte
     * array.
     * 
     * @param bytes
     *            is the byte array to write the converted value into.
     * @param instant
     *            is the {@link Instant} to be converted.
     * @param offset
     *            is the offset where to write the converted value to.
     * @return The number of bytes (12 bytes) which were written into the array are
     *         returned.
     */
    public int putInstant(byte[] bytes, Instant instant, int offset);

    /**
     * Converts the provided array into a {@link Instant}.
     * 
     * @param bytes
     *            is the array to be converted.
     * @return A {@link Instant} is returned containing the value out of the array.
     */
    public Instant toInstant(byte[] bytes);

    /**
     * Converts the provided array into a {@link Instant}.
     * 
     * @param bytes
     *            is the array to be converted.
     * @param offset
     *            is the offset in the array to start reading from.
     * @return A {@link Instant} is returned containing the value out of the array.
     */
    public Instant toInstant(byte[] bytes, int offset);

    /**
     * This method converts the provided {@link LocalDate} into an array of bytes.
     * 
     * @param localDate
     *            is a {@link LocalDate} to be converted.
     * @return A byte array is returned containing the converted value.
     */
    public byte[] fromLocalDate(LocalDate localDate);

    /**
     * This method writes the provided {@link LocalDate} value into the given byte
     * array.
     * 
     * @param bytes
     *            is the byte array to write the converted value into.
     * @param localDate
     *            is the {@link LocalDate} to be converted.
     * @param offset
     *            is the offset where to write the converted value to.
     * @return The number of bytes (4 bytes) which were written into the array are
     *         returned.
     */
    public int putLocalDate(byte[] bytes, LocalDate localDate, int offset);

    /**
     * Converts the provided array into a {@link LocalDate}.
     * 
     * @param bytes
     *            is the array to be converted.
     * @return A {@link LocalDate} is returned containing the value out of the
     *         array.
     */
    public LocalDate toLocalDate(byte[] bytes);

    /**
     * Converts the provided array into a {@link LocalDate}.
     * 
     * @param bytes
     *            is the array to be converted.
     * @param offset
     *            is the offset in the array to start reading from.
     * @return A {@link LocalDate} is returned containing the value out of the
     *         array.
     */
    public LocalDate toLocalDate(byte[] bytes, int offset);

    /**
     * This method converts the provided {@link LocalTime} into an array of bytes.
     * 
     * @param localTime
     *            is a {@link LocalTime} to be converted.
     * @return A byte array is returned containing the converted value.
     */
    public byte[] fromLocalTime(LocalTime localTime);

    /**
     * This method writes the provided {@link LocalTime} value into the given byte
     * array.
     * 
     * @param bytes
     *            is the byte array to write the converted value into.
     * @param localTime
     *            is the {@link LocalTime} to be converted.
     * @param offset
     *            is the offset where to write the converted value to.
     * @return The number of bytes (7 bytes) which were written into the array are
     *         returned.
     */
    public int putLocalTime(byte[] bytes, LocalTime localTime, int offset);

    /**
     * Converts the provided array into a {@link LocalTime}.
     * 
     * @param bytes
     *            is the array to be converted.
     * @return A {@link LocalTime} is returned containing the value out of the
     *         array.
     */
    public LocalTime toLocalTime(byte[] bytes);

    /**
     * Converts the provided array into a {@link LocalTime}.
     * 
     * @param bytes
     *            is the array to be converted.
     * @param offset
     *            is the offset in the array to start reading from.
     * @return A {@link LocalTime} is returned containing the value out of the
     *         array.
     */
    public LocalTime toLocalTime(byte[] bytes, int offset);

    /**
     * This method converts the provided {@link LocalDateTime} into an array of
     * bytes.
     * 
     * @param localDateTime
     *            is a {@link LocalDateTime} to be converted.
     * @return A byte array is returned containing the converted value.
     */
    public byte[] fromLocalDateTime(LocalDateTime localDateTime);

    /**
     * This method writes the provided {@link LocalDateTime} value into the given
     * byte array.
     * 
     * @param bytes
     *            is the byte array to write the converted value into.
     * @param l
     *            is the {@link LocalDateTime} to be converted.
     * @param offset
     *            is the offset where to write the converted value to.
     * @return The number of bytes (11 bytes) which were written into the array are
     *         returned.
     */
    public int putLocalDateTime(byte[] bytes, LocalDateTime l, int offset);

    /**
     * Converts the provided array into a {@link LocalDateTime}.
     * 
     * @param bytes
     *            is the array to be converted.
     * @return A {@link LocalDateTime} is returned containing the value out of the
     *         array.
     */
    public LocalDateTime toLocalDateTime(byte[] bytes);

    /**
     * Converts the provided array into a {@link LocalDateTime}.
     * 
     * @param bytes
     *            is the array to be converted.
     * @param offset
     *            is the offset in the array to start reading from.
     * @return A {@link LocalDateTime} is returned containing the value out of the
     *         array.
     */
    public LocalDateTime toLocalDateTime(byte[] bytes, int offset);

}
