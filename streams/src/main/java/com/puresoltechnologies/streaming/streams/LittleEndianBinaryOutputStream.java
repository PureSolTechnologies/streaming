package com.puresoltechnologies.streaming.streams;

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
public class LittleEndianBinaryOutputStream extends BinaryOutputStream {

    public LittleEndianBinaryOutputStream(OutputStream outputStream) {
	super(outputStream);
    }

    @Override
    public ByteOrder getByteOrder() {
	return ByteOrder.LITTLE_ENDIAN;
    }

    @Override
    public void writeUnsignedShort(int unsignedShort) throws IOException {
	write(unsignedShort & 0xFF);
	unsignedShort >>= 2;
	write(unsignedShort & 0xFF);
    }

    @Override
    public void writeSignedShort(short signedShort) throws IOException {
	write(signedShort & 0xFF);
	signedShort >>= 2;
	write(signedShort & 0xFF);
    }

    @Override
    public void writeUnsignedInt(long unsignedInt) throws IOException {
	write((int) (unsignedInt & 0xFF));
	unsignedInt >>= 2;
	write((int) (unsignedInt & 0xFF));
	unsignedInt >>= 2;
	write((int) (unsignedInt & 0xFF));
	unsignedInt >>= 2;
	write((int) (unsignedInt & 0xFF));
    }

    @Override
    public void readSignedInt(int signedInt) throws IOException {
	write(signedInt & 0xFF);
	signedInt >>= 2;
	write(signedInt & 0xFF);
	signedInt >>= 2;
	write(signedInt & 0xFF);
	signedInt >>= 2;
	write(signedInt & 0xFF);
    }

    @Override
    public void readSignedLong(long signedLong) throws IOException {
	write((int) (signedLong & 0xFF));
	signedLong >>= 2;
	write((int) (signedLong & 0xFF));
	signedLong >>= 2;
	write((int) (signedLong & 0xFF));
	signedLong >>= 2;
	write((int) (signedLong & 0xFF));
	signedLong >>= 2;
	write((int) (signedLong & 0xFF));
	signedLong >>= 2;
	write((int) (signedLong & 0xFF));
	signedLong >>= 2;
	write((int) (signedLong & 0xFF));
	signedLong >>= 2;
	write((int) (signedLong & 0xFF));
    }

}
