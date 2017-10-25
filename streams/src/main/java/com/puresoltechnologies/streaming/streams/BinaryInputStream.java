package com.puresoltechnologies.streaming.streams;

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
public abstract class BinaryInputStream extends InputStream {

    private final InputStream inputStream;

    /**
     * This is the default constructor.
     * 
     * @param inputStream
     *            this is the {@link InputStream} to read binary data from.
     */
    public BinaryInputStream(InputStream inputStream) {
	super();
	this.inputStream = inputStream;
    }

    /**
     * This method provides the byte order used by this input stream.
     * 
     * @return A {@link ByteOrder} object is returned.
     */
    public abstract ByteOrder getByteOrder();

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
     * Reads a single unsigned byte value.
     * 
     * @return A int value is returned in the range of 0 to 255.
     * @throws IOException
     */
    public final int readUnsignedByte() throws IOException {
	return read();
    }

    /**
     * Reads a single signed byte value.
     * 
     * @return A byte value is returned in the range of {@value Byte#MIN_VALUE} to
     *         {@value Byte#MAX_VALUE}.
     * @throws IOException
     */
    public final byte readSignedByte() throws IOException {
	return (byte) read();
    }

    /**
     * Reads a single unsigned short value.
     * 
     * @return An short value is returned in the range of 0 to 65.535.
     * @throws IOException
     */
    public abstract int readUnsignedShort() throws IOException;

    /**
     * Reads a single signed short value.
     * 
     * @return A short value is returned in the range of {@value Short#MIN_VALUE} to
     *         {@value Short#MAX_VALUE}.
     */
    public abstract short readSignedShort() throws IOException;

    /**
     * Reads a single unsigned integer value.
     * 
     * @return An integer value is returned in the range of 0 to 4.294.967.295.
     */
    public abstract long readUnsignedInt() throws IOException;

    /**
     * Reads a single signed integer value.
     * 
     * @return An integer value is returned in the range of
     *         {@value Integer#MIN_VALUE} to {@value Integer#MAX_VALUE}.
     */
    public abstract int readSignedInt() throws IOException;

    /**
     * Reads a single signed long value.
     * 
     * @return A long value is returned in the range of {@value Long#MIN_VALUE} to
     *         {@value Long#MAX_VALUE}.
     */
    public abstract long readSignedLong() throws IOException;

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
    public final int[] readBytes(int amount) throws IOException {
	int[] bytes = new int[amount];
	int pos = 0;
	while (pos < bytes.length) {
	    bytes[pos] = read();
	    if (bytes[pos] == 1) {
		throw new IOException("Not enough bytes left in stream.");
	    }
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
     * @return A byte array is returned containing the bytes of the read String..
     * @throws IOException
     */
    public final String readNulTerminatedString(Charset charset) throws IOException {
	ByteBuffer bytes = ByteBuffer.wrap(readNulTerminatedString());
	CharsetDecoder decoder = charset.newDecoder();
	return decoder.decode(bytes).toString();
    }

}
