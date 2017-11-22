package com.puresoltechnologies.streaming.binary;

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
public class BinaryOutputStream extends OutputStream {

    private final OutputStream outputStream;
    private final Bytes byteConverter;

    /**
     * This is the default constructor.
     * 
     * @param outputStream
     *            this is the {@link OutputStream} to read binary data from.
     */
    public BinaryOutputStream(OutputStream outputStream, ByteOrder byteOrder) {
	super();
	this.outputStream = outputStream;
	this.byteConverter = Bytes.forByteOrder(byteOrder);
    }

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
     * This method provides the byte order used by this input stream.
     * 
     * @return A {@link ByteOrder} object is returned.
     */
    public ByteOrder getByteOrder() {
	return byteConverter.getByteOrder();
    }

    /**
     * Writes a single boolean value.
     * 
     * @param b
     *            to be written.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public final void writeBoolean(boolean b) throws IOException {
	write(b ? 1 : 0);
    }

    /**
     * Writes a single unsigned byte value.
     * 
     * @param unsignedByte
     *            to be written.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public final void writeUnsignedByte(int unsignedByte) throws IOException {
	write(byteConverter.fromUnsignedByte(unsignedByte));
    }

    /**
     * Writes a single signed byte value.
     * 
     * @param signedByte
     *            to be written.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public final void writeSignedByte(byte signedByte) throws IOException {
	write(byteConverter.fromByte(signedByte));
    }

    /**
     * Writes a single unsigned short value.
     * 
     * @param unsignedShort
     *            to be written.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public void writeUnsignedShort(int unsignedShort) throws IOException {
	write(byteConverter.fromUnsignedShort(unsignedShort));
    }

    /**
     * Writes a single signed short value.
     * 
     * @param signedShort
     *            to be written.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public void writeSignedShort(short signedShort) throws IOException {
	byte[] bytes = byteConverter.fromShort(signedShort);
	write(bytes);
    }

    /**
     * Writes a single unsigned integer value.
     * 
     * @param unsignedInt
     *            to be written.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public void writeUnsignedInt(long unsignedInt) throws IOException {
	byte[] bytes = byteConverter.fromUnsignedInt(unsignedInt);
	write(bytes);
    }

    /**
     * Writes a single signed integer value.
     * 
     * @param signedInt
     *            to be written.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public void writeSignedInt(int signedInt) throws IOException {
	byte[] bytes = byteConverter.fromInt(signedInt);
	write(bytes);
    }

    /**
     * Writes a single signed long value.
     * 
     * @param signedLong
     *            to be written.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public void writeSignedLong(long signedLong) throws IOException {
	byte[] bytes = byteConverter.fromLong(signedLong);
	write(bytes);
    }

    /**
     * Writes a single float value.
     * 
     * @param f
     *            to be written.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public void writeFloat(float f) throws IOException {
	byte[] bytes = byteConverter.fromFloat(f);
	write(bytes);
    }

    /**
     * Writes a single double value.
     * 
     * @param d
     *            to be written.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public void writeDouble(double d) throws IOException {
	byte[] bytes = byteConverter.fromDouble(d);
	write(bytes);
    }

    /**
     * Reads a NUL terminated string. There is no conversion provided with this
     * function.
     * 
     * @param string
     *            is the {@link String} to be written.
     * @param charset
     *            is the {@link Charset} to be used for conversion.
     * @throws IOException
     *             is thrown in case of I/O issues.
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
     *            to be written.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    protected void writeNulTerminatedString(ByteBuffer bytes) throws IOException {
	write(bytes.array());
	write(0);
    }

}
