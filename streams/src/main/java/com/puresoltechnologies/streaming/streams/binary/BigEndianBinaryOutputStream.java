package com.puresoltechnologies.streaming.streams.binary;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteOrder;

/**
 * This class is an implementation for an {@link BinaryOutputStream} to write
 * bytes in Big-Endian byte order.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public class BigEndianBinaryOutputStream extends BinaryOutputStream {

    public BigEndianBinaryOutputStream(OutputStream outputStream) {
	super(outputStream);
    }

    @Override
    public ByteOrder getByteOrder() {
	return ByteOrder.BIG_ENDIAN;
    }

    @Override
    public void writeUnsignedShort(int unsignedShort) {
	// TODO Auto-generated method stub

    }

    @Override
    public void writeSignedShort(short signedShort) {
	// TODO Auto-generated method stub

    }

    @Override
    public void writeUnsignedInt(long unsignedInt) {
	// TODO Auto-generated method stub

    }

    @Override
    public void writeSignedInt(int signedInt) {
	// TODO Auto-generated method stub

    }

    @Override
    public void writeSignedLong(long signedLong) {
	// TODO Auto-generated method stub

    }

    @Override
    public void writeFloat(float f) throws IOException {
	int s = Float.floatToRawIntBits(f);
	int bits = 0;
	bits |= ((byte) s) << 24;
	s >>= 8;
	bits |= ((byte) s) << 16;
	s >>= 8;
	bits |= ((byte) s) << 8;
	s >>= 8;
	bits |= (byte) s;
	writeUnsignedInt(bits);
    }

    @Override
    public void writeDouble(double d) throws IOException {
	long s = Double.doubleToRawLongBits(d);
	long bits = 0;
	bits |= ((byte) s) << 56;
	s >>= 8;
	bits |= ((byte) s) << 48;
	s >>= 8;
	bits |= ((byte) s) << 40;
	s >>= 8;
	bits |= ((byte) s) << 32;
	s >>= 8;
	bits |= ((byte) s) << 24;
	s >>= 8;
	bits |= ((byte) s) << 16;
	s >>= 8;
	bits |= ((byte) s) << 8;
	s >>= 8;
	bits |= (byte) s;
	writeSignedLong(bits);
    }

}
