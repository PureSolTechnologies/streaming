package com.puresoltechnologies.streaming.streams;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

/**
 * This interface is used to read in binary streams.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public abstract class BinaryOutputStream extends OutputStream {

    private final OutputStream outputStream;

    /**
     * This is the default constructor.
     * 
     * @param outputStream
     *            this is the {@link OutputStream} to read binary data from.
     */
    public BinaryOutputStream(OutputStream outputStream) {
	super();
	this.outputStream = outputStream;
    }

    /**
     * This method provides the byte order used by this input stream.
     * 
     * @return A {@link ByteOrder} object is returned.
     */
    public abstract ByteOrder getByteOrder();

    @Override
    public void write(int b) throws IOException {
	outputStream.write(b);
    }

    @Override
    public void close() throws IOException {
	outputStream.close();
	super.close();
    }

    /**
     * Writes a single unsigned byte value.
     * 
     * @param unsignedByte
     * @throws IOException
     */
    public final void writeUnsignedByte(int unsignedByte) throws IOException {
	write(unsignedByte);
    }

    /**
     * Writes a single signed byte value.
     * 
     * @param signedByte
     * @throws IOException
     */
    public final void writeSignedByte(byte signedByte) throws IOException {
	write(signedByte & 0xFF);
    }

    /**
     * Writes a single unsigned short value.
     * 
     * @param unsignedShort
     * @throws IOException
     */
    public abstract void writeUnsignedShort(int unsignedShort) throws IOException;

    /**
     * Writes a single signed short value.
     * 
     * @param signedShort
     */
    public abstract void writeSignedShort(short signedShort) throws IOException;

    /**
     * Writes a single unsigned integer value.
     * 
     * @param unsignedInt
     */
    public abstract void writeUnsignedInt(long unsignedInt) throws IOException;

    /**
     * Writes a single signed integer value.
     * 
     * @param signedInt
     */
    public abstract void readSignedInt(int signedInt) throws IOException;

    /**
     * Writes a single signed long value.
     * 
     * @param signedLong
     */
    public abstract void readSignedLong(long signedLong) throws IOException;

    /**
     * Reads a NUL terminated string. There is no conversion provided with this
     * function.
     * 
     * @return A byte array is returned containing the bytes of the read String..
     * @throws IOException
     */
    public final void writeNulTerminatedString(String string, Charset charset) throws IOException {
	CharsetEncoder encoder = charset.newEncoder();
	ByteBuffer encoded = encoder.encode(CharBuffer.wrap(string.toCharArray()));
	writeNulTerminatedString(encoded);
    }

    /**
     * Writes a NUL terminated string. There is no conversion provided with this
     * function.
     * 
     * @param bytes
     * @throws IOException
     */
    protected void writeNulTerminatedString(ByteBuffer bytes) throws IOException {
	write(bytes.array());
	write(0);
    }

}
