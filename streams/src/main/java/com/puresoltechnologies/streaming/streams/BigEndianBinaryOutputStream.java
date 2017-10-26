package com.puresoltechnologies.streaming.streams;

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
    public void readSignedInt(int signedInt) {
	// TODO Auto-generated method stub

    }

    @Override
    public void readSignedLong(long signedLong) {
	// TODO Auto-generated method stub

    }

}
