package com.puresoltechnologies.streaming.streams.binary;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteOrder;

/**
 * This class is an implementation for an {@link BinaryInputStream} to read
 * bytes in Big-Endian byte order.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public class BigEndianBinaryInputStream extends BinaryInputStream {

    public BigEndianBinaryInputStream(InputStream inputStream) {
	super(inputStream);
    }

    @Override
    public ByteOrder getByteOrder() {
	return ByteOrder.BIG_ENDIAN;
    }

    @Override
    public int readUnsignedShort() throws IOException {
	int[] bytes = readBytes(2);
	return ((bytes[0] & 0xFF) << 8) //
		| (bytes[1] & 0xFF);
    }

    @Override
    public short readSignedShort() throws IOException {
	int[] bytes = readBytes(2);
	return (short) (((bytes[0] & 0xFF) << 8) //
		| (bytes[1] & 0xFF));
    }

    @Override
    public long readUnsignedInt() throws IOException {
	int[] bytes = readBytes(4);
	return ((long) (bytes[0] & 0xFF) << 24) //
		| ((bytes[1] & 0xFF) << 16) //
		| ((bytes[2] & 0xFF) << 8) //
		| bytes[3] & 0xFF;
    }

    @Override
    public int readSignedInt() throws IOException {
	int[] bytes = readBytes(4);
	return ((bytes[0] & 0xFF) << 24) //
		| ((bytes[1] & 0xFF) << 16) //
		| ((bytes[2] & 0xFF) << 8) //
		| bytes[3] & 0xFF;
    }

    @Override
    public long readSignedLong() throws IOException {
	int[] bytes = readBytes(8);
	long result = ((long) bytes[0] & 0xFF) << 56;
	result |= (((long) bytes[1] & 0xFF) << 48);
	result |= (((long) bytes[2] & 0xFF) << 40);
	result |= (((long) bytes[3] & 0xFF) << 32);
	result |= (((long) bytes[4] & 0xFF) << 24);
	result |= (((long) bytes[5] & 0xFF) << 16);
	result |= (((long) bytes[6] & 0xFF) << 8);
	result |= ((long) bytes[7] & 0xFF);
	return result;
    }

    @Override
    public float readFloat() throws IOException {
	int bits = readSignedInt();
	return Float.intBitsToFloat(bits);
    }

    @Override
    public double readDouble() throws IOException {
	long bits = readSignedLong();
	return Double.longBitsToDouble(bits);
    }

}
