package com.puresoltechnologies.streaming.binary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;

/**
 * This interface is used to read in binary streams.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public class BinaryInputStream extends InputStream {

    private final InputStream inputStream;
    private final Bytes byteConverter;

    /**
     * This is the default constructor.
     * 
     * @param inputStream
     *            this is the {@link InputStream} to read binary data from.
     */
    public BinaryInputStream(InputStream inputStream, ByteOrder byteOrder) {
	super();
	this.inputStream = inputStream;
	this.byteConverter = Bytes.forByteOrder(byteOrder);
    }

    @Override
    public final int read() throws IOException {
	return inputStream.read();
    }

    @Override
    public final void close() throws IOException {
	inputStream.close();
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
     * Reads a single boolean value.
     * 
     * @return A boolean value is returned .
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public final boolean readBoolean() throws IOException {
	return readSignedByte() != 0;
    }

    /**
     * Reads a single unsigned byte value.
     * 
     * @return A int value is returned in the range of 0 to 255.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public final int readUnsignedByte() throws IOException {
	byte[] bytes = readBytes(1);
	return byteConverter.toUnsignedByte(bytes);
    }

    /**
     * Reads a single signed byte value.
     * 
     * @return A byte value is returned in the range of {@value Byte#MIN_VALUE} to
     *         {@value Byte#MAX_VALUE}.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public final byte readSignedByte() throws IOException {
	byte[] bytes = readBytes(1);
	return byteConverter.toByte(bytes);
    }

    /**
     * Reads a single unsigned short value.
     * 
     * @return An short value is returned in the range of 0 to 65.535.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public int readUnsignedShort() throws IOException {
	byte[] bytes = readBytes(2);
	return byteConverter.toUnsignedShort(bytes);
    }

    /**
     * Reads a single signed short value.
     * 
     * @return A short value is returned in the range of {@value Short#MIN_VALUE} to
     *         {@value Short#MAX_VALUE}.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public short readSignedShort() throws IOException {
	byte[] bytes = readBytes(2);
	return byteConverter.toShort(bytes);
    }

    /**
     * Reads a single unsigned integer value.
     * 
     * @return An integer value is returned in the range of 0 to 4.294.967.295.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public long readUnsignedInt() throws IOException {
	byte[] bytes = readBytes(4);
	return byteConverter.toUnsignedInt(bytes);
    }

    /**
     * Reads a single signed integer value.
     * 
     * @return An integer value is returned in the range of
     *         {@value Integer#MIN_VALUE} to {@value Integer#MAX_VALUE}.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public int readSignedInt() throws IOException {
	byte[] bytes = readBytes(4);
	return byteConverter.toInt(bytes);
    }

    /**
     * Reads a single signed long value.
     * 
     * @return A long value is returned in the range of {@value Long#MIN_VALUE} to
     *         {@value Long#MAX_VALUE}.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public long readSignedLong() throws IOException {
	byte[] bytes = readBytes(8);
	return byteConverter.toLong(bytes);
    }

    /**
     * Reads a single float value.
     * 
     * @return A float value is returned.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public float readFloat() throws IOException {
	byte[] bytes = readBytes(4);
	return byteConverter.toFloat(bytes);
    }

    /**
     * Reads a single double value.
     * 
     * @return A double value is returned.
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public double readDouble() throws IOException {
	byte[] bytes = readBytes(8);
	return byteConverter.toDouble(bytes);
    }

    /**
     * This method reads the specified amount of bytes. If not enough bytes can be
     * read, a {@link StreamCorruptedException} is thrown.
     * 
     * @param amount
     *            is the number of bytes to be read from the stream.
     * @return A byte array is returned with the length of amount containing the
     *         read bytes.
     * @throws IOException
     *             is thrown in case of not enough bytes available or any other I/O
     *             issue.
     */
    public final byte[] readBytes(int amount) throws IOException {
	byte[] bytes = new byte[amount];
	int pos = 0;
	while (pos < bytes.length) {
	    int i = read();
	    if (i == -1) {
		throw new IOException("Not enough bytes left in stream.");
	    }
	    bytes[pos] = (byte) i;
	    ++pos;
	}
	return bytes;
    }

    /**
     * Reads a NUL terminated string. There is not conversion provided with this
     * function.
     * 
     * @return A {@link ByteBuffer} is returned containing the bytes of the read
     *         String..
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public final byte[] readNulTerminatedString() throws IOException {
	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(256);
	int c;
	while ((c = read()) > 0) {
	    byteArrayOutputStream.write(c);
	}
	return byteArrayOutputStream.toByteArray();
    }

    /**
     * Reads a NUL terminated string. There is not conversion provided with this
     * function.
     *
     * @param charset
     *            is the {@link Charset} to use for string conversion.
     * @return A byte array is returned containing the bytes of the read String..
     * @throws IOException
     *             is thrown in case of I/O issues.
     */
    public final String readNulTerminatedString(Charset charset) throws IOException {
	ByteBuffer bytes = ByteBuffer.wrap(readNulTerminatedString());
	CharsetDecoder decoder = charset.newDecoder();
	return decoder.decode(bytes).toString();
    }

}
