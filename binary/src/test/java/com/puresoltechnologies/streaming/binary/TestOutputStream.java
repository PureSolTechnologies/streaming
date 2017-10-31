package com.puresoltechnologies.streaming.binary;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TestOutputStream extends OutputStream {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @Override
    public void write(int b) throws IOException {
	outputStream.write(b);
    }

}
