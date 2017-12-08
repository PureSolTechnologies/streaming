package com.puresoltechnologies.streaming.test;

import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.puresoltechnologies.streaming.csv.CSVHeader;
import com.puresoltechnologies.streaming.csv.CSVWriter;
import com.puresoltechnologies.streaming.csv.mapper.CSVMapper;
import com.puresoltechnologies.streaming.csv.mapper.CSVMappingException;
import com.puresoltechnologies.streaming.streams.SimpleBufferedInputStream;

@RunWith(Parameterized.class)
public class SequentialReadPerformanceTest {

    private static final File TEST_DIRECTORY = new File("target/test");
    private static final File TEST_FILE = new File(TEST_DIRECTORY, "sequential_read.bin");
    private static final File CSV_FILE = new File(TEST_DIRECTORY, "sequential_read.csv");
    private static final long FILE_SIZE = 1024 * 1024; // 1MB

    private static final CSVMapper mapper = new CSVMapper();
    private static OutputStream csvOutputFile = null;
    private static CSVWriter csvWriter = null;

    @Parameters
    public static Collection<Object[]> data() throws IOException {
	if (!TEST_DIRECTORY.exists()) {
	    assertTrue("Could not create directory for test file.", TEST_DIRECTORY.mkdirs());
	}
	try (OutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(TEST_FILE))) {
	    for (int i = 0; i < FILE_SIZE; ++i) {
		fileOutputStream.write(i % 0xFF);
	    }
	}

	List<Object[]> list = new ArrayList<>();
	list.add(new Object[] { //
		FileInputStream.class, //
		0, //
		"Unbuffered read", //
		new FileInputStream(TEST_FILE) //
	});
	for (int size : new int[] { 1, 2, 4, 8, 12, 16, 32, 64 }) {
	    list.add(new Object[] { //
		    BufferedInputStream.class, //
		    size, //
		    "Buffered read " + size + "kB buffer", //
		    new BufferedInputStream(new FileInputStream(TEST_FILE), size * 1024) //
	    });
	}
	for (int size : new int[] { 1, 2, 4, 8, 12, 16, 32, 64 }) {
	    list.add(new Object[] { //
		    SimpleBufferedInputStream.class, //
		    size, //
		    "Enhanced buffered read " + size + "kB buffer", //
		    new SimpleBufferedInputStream(new FileInputStream(TEST_FILE), size * 1024)//
	    });
	}
	return list;
    }

    @BeforeClass
    public static void createFile() throws IOException {
	System.out.println("================================");
	System.out.println("Sequential Read Performance Test");
	System.out.println("================================");
	if (CSV_FILE.exists()) {
	    assertTrue(CSV_FILE.delete());
	}
	csvOutputFile = new BufferedOutputStream(new FileOutputStream(CSV_FILE));
	csvWriter = new CSVWriter(csvOutputFile);
	csvWriter.writeHeader(new CSVHeader(Arrays.asList("Class", "BufferSize", "ReadTime", "Throughput")));
    }

    @AfterClass
    public static void closeFiles() throws IOException {
	csvOutputFile.close();
    }

    private final Class<? extends InputStream> clazz;
    private final int size;
    private final String msg;
    private final InputStream inputStream;

    public SequentialReadPerformanceTest(Class<? extends InputStream> clazz, int size, String msg,
	    InputStream inputStream) {
	this.clazz = clazz;
	this.size = size;
	this.msg = msg;
	this.inputStream = inputStream;
    }

    @Test
    public void testSequentialUnbuffered1() throws IOException, CSVMappingException {
	long startTime = System.nanoTime();
	try (InputStream inputStream = this.inputStream) {
	    int b = inputStream.read();
	    while (b != -1) {
		b = inputStream.read();
	    }
	}
	long stopTime = System.nanoTime();
	double milliseconds = (stopTime - startTime) / 1_000_000d;
	double throughput = FILE_SIZE / milliseconds / 1000.0;
	System.out.println(msg + ": " + milliseconds + "ms / " + throughput + "MB/s");
	SequentialReadResult result = new SequentialReadResult(clazz.getName(), size, milliseconds, throughput);
	mapper.write(csvWriter, result);
    }
}
