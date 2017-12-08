package com.puresoltechnologies.streaming.binary;

import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This class is an implementation of {@link Bytes} interface providing the
 * {@link ByteOrder} independent functionality.
 * 
 * @author Rick-Rainer Ludwig
 */
public abstract class AbstractBytes implements Bytes {

    @Override
    public final byte[] empty() {
	return new byte[0];
    }

    @Override
    public final int putBytes(byte[] destination, int destinationOffset, byte[] source)
	    throws IllegalArgumentException {
	if (destination.length - destinationOffset < source.length) {
	    throw new IllegalArgumentException("The remaining " + (destination.length - destinationOffset)
		    + " bytes in destination are too small for source size of " + source.length + " bytes.");
	}
	for (int i = 0; i < source.length; ++i) {
	    destination[destinationOffset + i] = (source[i]);
	}
	return source.length;
    }

    @Override
    public final byte[] fromBoolean(boolean b) {
	byte[] bytes = new byte[1];
	bytes[0] = (byte) (b ? 1 : 0);
	return bytes;
    }

    @Override
    public final int putBoolean(byte[] bytes, boolean b, int offset) {
	bytes[offset] = (byte) (b ? 1 : 0);
	return 1;
    }

    @Override
    public final boolean toBoolean(byte[] bytes) {
	return bytes[0] != 0;
    }

    @Override
    public final boolean toBoolean(byte[] bytes, int offset) {
	return bytes[offset] != 0;
    }

    @Override
    public final byte[] fromByte(byte b) {
	byte[] bytes = new byte[1];
	bytes[0] = b;
	return bytes;
    }

    @Override
    public final int putByte(byte[] bytes, byte b, int offset) {
	bytes[offset] = b;
	return 1;
    }

    @Override
    public final byte toByte(byte[] bytes) {
	return bytes[0];
    }

    @Override
    public final byte toByte(byte[] bytes, int offset) {
	return bytes[offset];
    }

    @Override
    public final byte[] fromUnsignedByte(int b) {
	return new byte[] { (byte) b };
    }

    @Override
    public final int putUnsignedByte(byte[] bytes, int b, int offset) {
	bytes[offset] = (byte) b;
	return 1;
    }

    @Override
    public final int toUnsignedByte(byte[] bytes) {
	return bytes[0] & 0xFF;
    }

    @Override
    public final int toUnsignedByte(byte[] bytes, int offset) {
	return bytes[offset] & 0xFF;
    }

    @Override
    public final byte[] fromFloat(float f) {
	int bits = Float.floatToIntBits(f);
	return fromInt(bits);
    }

    @Override
    public final int putFloat(byte[] bytes, float f, int offset) {
	int bits = Float.floatToIntBits(f);
	return putInt(bytes, bits, offset);
    }

    @Override
    public final float toFloat(byte[] bytes) {
	int bits = toInt(bytes);
	return Float.intBitsToFloat(bits);
    }

    @Override
    public final float toFloat(byte[] bytes, int offset) {
	int bits = toInt(bytes, offset);
	return Float.intBitsToFloat(bits);
    }

    @Override
    public final byte[] fromDouble(double d) {
	long bits = Double.doubleToLongBits(d);
	return fromLong(bits);
    }

    @Override
    public final int putDouble(byte[] bytes, double d, int offset) {
	long bits = Double.doubleToLongBits(d);
	return putLong(bytes, bits, offset);
    }

    @Override
    public final double toDouble(byte[] bytes) {
	long bits = toLong(bytes);
	return Double.longBitsToDouble(bits);
    }

    @Override
    public final double toDouble(byte[] bytes, int offset) {
	long bits = toLong(bytes, offset);
	return Double.longBitsToDouble(bits);
    }

    @Override
    public final byte[] fromString(String string, Charset charset) {
	return string.getBytes(charset);
    }

    @Override
    public final String toString(byte[] bytes, Charset charset) {
	if (bytes == null) {
	    return null;
	}
	return new String(bytes, charset);
    }

    @Override
    public final String toHumanReadableString(byte[] bytes) {
	if (bytes == null) {
	    throw new IllegalArgumentException("Byte array must not be null!");
	}
	StringBuilder hexString = new StringBuilder();
	for (int i = 0; i < bytes.length; i++) {
	    if (hexString.length() > 0) {
		hexString.append(' ');
	    }
	    int digit = 0xFF & bytes[i];
	    String hexDigits = Integer.toHexString(digit);
	    if (hexDigits.length() < 2) {
		hexString.append("0");
	    }
	    hexString.append(hexDigits);
	}
	return hexString.toString();
    }

    @Override
    public final String toHexString(byte[] bytes) {
	if (bytes == null) {
	    throw new IllegalArgumentException("Byte array must not be null!");
	}
	StringBuilder hexString = new StringBuilder();
	for (int i = 0; i < bytes.length; i++) {
	    int digit = 0xFF & bytes[i];
	    String hexDigits = Integer.toHexString(digit);
	    if (hexDigits.length() < 2) {
		hexString.append("0");
	    }
	    hexString.append(hexDigits);
	}
	return hexString.toString();
    }

    @Override
    public final byte[] fromHexString(String identifier) {
	if (identifier == null) {
	    throw new IllegalArgumentException("identifier not be null!");
	}
	if (identifier.length() % 2 != 0) {
	    throw new IllegalArgumentException("The identifier needs to have a even number of digits.");
	}
	byte[] bytes = new byte[identifier.length() / 2];
	for (int i = 0; i < identifier.length() / 2; ++i) {
	    int highHalfByte = char2byte(identifier.charAt(2 * i)) << 4;
	    int lowHalfByte = char2byte(identifier.charAt(2 * i + 1));
	    bytes[i] = (byte) (highHalfByte + lowHalfByte);
	}
	return bytes;
    }

    private byte char2byte(char c) {
	if (('0' <= c) && ('9' >= c)) {
	    return (byte) (c - '0');
	}
	if (('A' <= c) && ('F' >= c)) {
	    return (byte) (c - 'A' + 10);
	}
	if (('a' <= c) && ('f' >= c)) {
	    return (byte) (c - 'a' + 10);
	}
	throw new IllegalArgumentException("Character '" + c + "' is not part of a hex number.");
    }

    @Override
    public final byte[] fromInstant(Instant instant) {
	long second = instant.getEpochSecond();
	int nano = instant.getNano();
	byte[] bytes = new byte[12];
	putLong(bytes, second, 0);
	putInt(bytes, nano, 8);
	return bytes;
    }

    @Override
    public final int putInstant(byte[] bytes, Instant instant, int offset) {
	long second = instant.getEpochSecond();
	int nano = instant.getNano();
	putLong(bytes, second, offset);
	putInt(bytes, nano, offset + 8);
	return 12;
    }

    @Override
    public final Instant toInstant(byte[] bytes) {
	long seconds = toLong(bytes);
	int nano = toInt(bytes, 8);
	return Instant.ofEpochSecond(seconds, nano);
    }

    @Override
    public final Instant toInstant(byte[] bytes, int offset) {
	long seconds = toLong(bytes, offset);
	int nano = toInt(bytes, offset + 8);
	return Instant.ofEpochSecond(seconds, nano);
    }

    @Override
    public final byte[] fromLocalDate(LocalDate localDate) {
	byte[] bytes = new byte[4];
	short year = (short) localDate.getYear();
	putShort(bytes, year, 0);
	byte month = (byte) localDate.getMonthValue();
	putByte(bytes, month, 2);
	byte day = (byte) localDate.getDayOfMonth();
	putByte(bytes, day, 3);
	return bytes;
    }

    @Override
    public final int putLocalDate(byte[] bytes, LocalDate localDate, int offset) {
	short year = (short) localDate.getYear();
	putShort(bytes, year, offset);
	byte month = (byte) localDate.getMonthValue();
	putByte(bytes, month, offset + 2);
	byte day = (byte) localDate.getDayOfMonth();
	putByte(bytes, day, offset + 3);
	return 4;
    }

    @Override
    public final LocalDate toLocalDate(byte[] bytes) {
	short year = toShort(bytes, 0);
	byte month = toByte(bytes, 2);
	byte day = toByte(bytes, 3);
	return LocalDate.of(year, month, day);
    }

    @Override
    public final LocalDate toLocalDate(byte[] bytes, int offset) {
	short year = toShort(bytes, offset);
	byte month = toByte(bytes, offset + 2);
	byte day = toByte(bytes, offset + 3);
	return LocalDate.of(year, month, day);
    }

    @Override
    public final byte[] fromLocalTime(LocalTime localTime) {
	byte[] bytes = new byte[7];
	byte hour = (byte) localTime.getHour();
	putByte(bytes, hour, 0);
	byte minute = (byte) localTime.getMinute();
	putByte(bytes, minute, 1);
	byte second = (byte) localTime.getSecond();
	putByte(bytes, second, 2);
	int nano = localTime.getNano();
	putInt(bytes, nano, 3);
	return bytes;
    }

    @Override
    public final int putLocalTime(byte[] bytes, LocalTime localTime, int offset) {
	byte hour = (byte) localTime.getHour();
	putByte(bytes, hour, offset);
	byte minute = (byte) localTime.getMinute();
	putByte(bytes, minute, offset + 1);
	byte second = (byte) localTime.getSecond();
	putByte(bytes, second, offset + 2);
	int nano = localTime.getNano();
	putInt(bytes, nano, offset + 3);
	return 7;
    }

    @Override
    public final LocalTime toLocalTime(byte[] bytes) {
	byte hour = toByte(bytes, 0);
	byte minute = toByte(bytes, 1);
	byte second = toByte(bytes, 2);
	int nano = toInt(bytes, 3);
	return LocalTime.of(hour, minute, second, nano);
    }

    @Override
    public final LocalTime toLocalTime(byte[] bytes, int offset) {
	byte hour = toByte(bytes, offset);
	byte minute = toByte(bytes, offset + 1);
	byte second = toByte(bytes, offset + 2);
	int nano = toInt(bytes, offset + 3);
	return LocalTime.of(hour, minute, second, nano);
    }

    @Override
    public final byte[] fromLocalDateTime(LocalDateTime localDateTime) {
	byte[] bytes = new byte[11];
	short year = (short) localDateTime.getYear();
	putShort(bytes, year, 0);
	byte month = (byte) localDateTime.getMonthValue();
	putByte(bytes, month, 2);
	byte day = (byte) localDateTime.getDayOfMonth();
	putByte(bytes, day, 3);
	byte hour = (byte) localDateTime.getHour();
	putByte(bytes, hour, 4);
	byte minute = (byte) localDateTime.getMinute();
	putByte(bytes, minute, 5);
	byte second = (byte) localDateTime.getSecond();
	putByte(bytes, second, 6);
	int nano = localDateTime.getNano();
	putInt(bytes, nano, 7);
	return bytes;
    }

    @Override
    public final int putLocalDateTime(byte[] bytes, LocalDateTime localDateTime, int offset) {
	short year = (short) localDateTime.getYear();
	putShort(bytes, year, offset);
	byte month = (byte) localDateTime.getMonthValue();
	putByte(bytes, month, offset + 2);
	byte day = (byte) localDateTime.getDayOfMonth();
	putByte(bytes, day, offset + 3);
	byte hour = (byte) localDateTime.getHour();
	putByte(bytes, hour, offset + 4);
	byte minute = (byte) localDateTime.getMinute();
	putByte(bytes, minute, offset + 5);
	byte second = (byte) localDateTime.getSecond();
	putByte(bytes, second, offset + 6);
	int nano = localDateTime.getNano();
	putInt(bytes, nano, offset + 7);
	return 11;
    }

    @Override
    public final LocalDateTime toLocalDateTime(byte[] bytes) {
	short year = toShort(bytes, 0);
	byte month = toByte(bytes, 2);
	byte day = toByte(bytes, 3);
	byte hour = toByte(bytes, 4);
	byte minute = toByte(bytes, 5);
	byte second = toByte(bytes, 6);
	int nano = toInt(bytes, 7);
	return LocalDateTime.of(year, month, day, hour, minute, second, nano);
    }

    @Override
    public final LocalDateTime toLocalDateTime(byte[] bytes, int offset) {
	short year = toShort(bytes, offset);
	byte month = toByte(bytes, offset + 2);
	byte day = toByte(bytes, offset + 3);
	byte hour = toByte(bytes, offset + 4);
	byte minute = toByte(bytes, offset + 5);
	byte second = toByte(bytes, offset + 6);
	int nano = toInt(bytes, offset + 7);
	return LocalDateTime.of(year, month, day, hour, minute, second, nano);
    }
}
