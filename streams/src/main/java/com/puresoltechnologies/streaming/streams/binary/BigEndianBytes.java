package com.puresoltechnologies.streaming.streams.binary;

import java.nio.ByteOrder;

/**
 * This is an utility class to support converting from and to bytes arrays. This
 * class is converting into BigEndian encoding (Motorola format).
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public final class BigEndianBytes extends AbstractBytes {

    @Override
    public final ByteOrder getByteOrder() {
	return ByteOrder.BIG_ENDIAN;
    }

    @Override
    public byte[] fromShort(short i) {
	byte[] bytes = new byte[2];
	bytes[1] = (byte) (i);
	bytes[0] = (byte) (i >>> 8);
	return bytes;
    }

    @Override
    public int putShort(byte[] bytes, short s, int offset) {
	bytes[offset + 1] = (byte) s;
	bytes[offset] = (byte) (s >>> 8);
	return 2;
    }

    @Override
    public short toShort(byte[] bytes) {
	return (short) (((bytes[1] & 0xFF)) //
		| ((bytes[0] & 0xFF) << 8));
    }

    @Override
    public short toShort(byte[] bytes, int offset) {
	return (short) (((bytes[offset + 1] & 0xFF)) //
		| ((bytes[offset] & 0xFF) << 8));
    }

    @Override
    public byte[] fromUnsignedShort(int i) {
	byte[] bytes = new byte[2];
	bytes[1] = (byte) (i);
	bytes[0] = (byte) (i >>> 8);
	return bytes;
    }

    @Override
    public int putUnsignedShort(byte[] bytes, int s, int offset) {
	bytes[offset] = (byte) (s);
	bytes[offset + 1] = (byte) (s >>> 8);
	return 2;
    }

    @Override
    public int toUnsignedShort(byte[] bytes) {
	return ((bytes[1] & 0xFF)) //
		| ((bytes[0] & 0xFF) << 8);
    }

    @Override
    public int toUnsignedShort(byte[] bytes, int offset) {
	return ((bytes[offset + 1] & 0xFF)) //
		| ((bytes[offset] & 0xFF) << 8);
    }

    @Override
    public byte[] fromInt(int i) {
	byte[] bytes = new byte[4];
	bytes[3] = (byte) (i);
	bytes[2] = (byte) (i >>> 8);
	bytes[1] = (byte) (i >>> 16);
	bytes[0] = (byte) (i >>> 24);
	return bytes;
    }

    @Override
    public int putInt(byte[] bytes, int i, int offset) {
	bytes[offset + 3] = (byte) (i);
	bytes[offset + 2] = (byte) (i >>> 8);
	bytes[offset + 1] = (byte) (i >>> 16);
	bytes[offset] = (byte) (i >>> 24);
	return 4;
    }

    @Override
    public int toInt(byte[] bytes) {
	return ((bytes[3] & 0xFF)) //
		| ((bytes[2] & 0xFF) << 8) //
		| ((bytes[1] & 0xFF) << 16) //
		| ((bytes[0]) << 24);
    }

    @Override
    public int toInt(byte[] bytes, int offset) {
	return ((bytes[offset + 3] & 0xFF)) //
		| ((bytes[offset + 2] & 0xFF) << 8) //
		| ((bytes[offset + 1] & 0xFF) << 16) //
		| ((bytes[offset]) << 24);
    }

    @Override
    public byte[] fromUnsignedInt(long i) {
	byte[] bytes = new byte[8];
	bytes[3] = (byte) (i);
	bytes[2] = (byte) (i >>> 8);
	bytes[1] = (byte) (i >>> 16);
	bytes[0] = (byte) (i >>> 24);
	return bytes;
    }

    @Override
    public int putUnsignedInt(byte[] bytes, long i, int offset) {
	bytes[offset + 3] = (byte) (i);
	bytes[offset + 2] = (byte) (i >>> 8);
	bytes[offset + 1] = (byte) (i >>> 16);
	bytes[offset] = (byte) (i >>> 24);
	return 4;
    }

    @Override
    public long toUnsignedInt(byte[] bytes) {
	return (bytes[3] & 0xFF) //
		| (bytes[2] & 0xFFl) << 8 //
		| (bytes[1] & 0xFFl) << 16 //
		| (bytes[0] & 0xFFl) << 24;
    }

    @Override
    public long toUnsignedInt(byte[] bytes, int offset) {
	return (bytes[offset + 3] & 0xFFl) //
		| (bytes[offset + 2] & 0xFFl) << 8 //
		| (bytes[offset + 1] & 0xFFl) << 16 //
		| (bytes[offset] & 0xFFl) << 24;
    }

    @Override
    public byte[] fromLong(long l) {
	byte[] bytes = new byte[8];
	bytes[7] = (byte) (l);
	bytes[6] = (byte) (l >>> 8);
	bytes[5] = (byte) (l >>> 16);
	bytes[4] = (byte) (l >>> 24);
	bytes[3] = (byte) (l >>> 32);
	bytes[2] = (byte) (l >>> 40);
	bytes[1] = (byte) (l >>> 48);
	bytes[0] = (byte) (l >>> 56);
	return bytes;
    }

    @Override
    public int putLong(byte[] bytes, long l, int offset) {
	bytes[offset + 7] = (byte) (l);
	bytes[offset + 6] = (byte) (l >>> 8);
	bytes[offset + 5] = (byte) (l >>> 16);
	bytes[offset + 4] = (byte) (l >>> 24);
	bytes[offset + 3] = (byte) (l >>> 32);
	bytes[offset + 2] = (byte) (l >>> 40);
	bytes[offset + 1] = (byte) (l >>> 48);
	bytes[offset] = (byte) (l >>> 56);
	return 8;
    }

    @Override
    public long toLong(byte[] bytes) {
	return (bytes[7] & 0xFFl) //
		| (bytes[6] & 0xFFl) << 8 //
		| (bytes[5] & 0xFFl) << 16 //
		| (bytes[4] & 0xFFl) << 24 //
		| (bytes[3] & 0xFFl) << 32 //
		| (bytes[2] & 0xFFl) << 40 //
		| (bytes[1] & 0xFFl) << 48 //
		| (bytes[0] & 0xFFl) << 56;
    }

    @Override
    public long toLong(byte[] bytes, int offset) {
	return ((bytes[offset + 7] & 0xFFl)) //
		| (bytes[offset + 6] & 0xFFl) << 8 //
		| (bytes[offset + 5] & 0xFFl) << 16 //
		| (bytes[offset + 4] & 0xFFl) << 24 //
		| (bytes[offset + 3] & 0xFFl) << 32 //
		| (bytes[offset + 2] & 0xFFl) << 40 //
		| (bytes[offset + 1] & 0xFFl) << 48 //
		| (bytes[offset] & 0xFFl) << 56;
    }
}
