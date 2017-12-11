package com.puresoltechnologies.streaming.test;

import static org.junit.Assert.assertEquals;
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

import com.puresoltechnologies.streaming.RunningStatistics;
import com.puresoltechnologies.streaming.csv.CSVHeader;
import com.puresoltechnologies.streaming.csv.CSVWriter;
import com.puresoltechnologies.streaming.csv.mapper.CSVMapper;
import com.puresoltechnologies.streaming.csv.mapper.CSVMappingException;
import com.puresoltechnologies.streaming.streams.InputStreamCreator;

@RunWith(Parameterized.class)
public class SequentialReadPerformanceTest {

    private static final File TEST_DIRECTORY = new File("target/test");
    private static final File TEST_FILE = new File(TEST_DIRECTORY, "sequential_read.bin");
    private static final File CSV_FILE = new File(TEST_DIRECTORY, "sequential_read.csv");
    private static final long FILE_SIZE = 1024 * 1024; // 1MB
    private static final int NUMBER_OF_RUNS = 10;

    private static final CSVMapper mapper = new CSVMapper();
    private static OutputStream csvOutputFile = null;
    private static CSVWriter csvWriter = null;

    @Parameters
    public static Collection<Object[]> data() throws IOException {
	if (!TEST_DIRECTORY.exists()) {
	    assertTrue("Could not create directory for test file.", TEST_DIRECTORY.mkdirs());
	}
	try (OutputStream fileOutputStream = new BufferedOutputStream(new FileOutputStream(TEST_FILE))) {
	    for (int i = 0; i < FILE_SIZE; i++) {
		fileOutputStream.write(i % 0xFF);
	    }
	}

	List<Object[]> list = new ArrayList<>();
	list.add(new Object[] { //
		FileInputStream.class, //
		0, //
		new InputStreamCreator<InputStream>() {
		    @Override
		    public InputStream create() throws IOException {
			return new FileInputStream(TEST_FILE);
		    }
		} //
	});
	for (int size : new int[] { 1, 2, 4, 8, 12, 16, 24, 32, 40, 48, 56, 64, 128, 256 }) {
	    int blockSize = size * 1024;
	    list.add(new Object[] { //
		    BufferedInputStream.class, //
		    size, //
		    new InputStreamCreator<BufferedInputStream>() {
			@Override
			public BufferedInputStream create() throws IOException {
			    return new BufferedInputStream(new FileInputStream(TEST_FILE), blockSize);
			}
		    } //
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
	csvWriter.writeHeader(new CSVHeader(Arrays.asList("Class", "BufferSize", "minTime", "maxTime", "meanTime",
		"sigmaTime", "minThroughput", "maxThroughput", "meanThroughput", "sigmaThroughput")));
    }

    @AfterClass
    public static void closeFiles() throws IOException {
	csvOutputFile.close();
    }

    private final Class<? extends InputStream> clazz;
    private final int size;
    private final InputStreamCreator<?> inputStreamCreator;

    public SequentialReadPerformanceTest(Class<? extends InputStream> clazz, int size,
	    InputStreamCreator<?> inputStreamCreator) {
	this.clazz = clazz;
	this.size = size;
	this.inputStreamCreator = inputStreamCreator;
    }

    @Test
    public void testSequentialUnbuffered1() throws IOException, CSVMappingException {
	RunningStatistics<Double> timeStatistics = new RunningStatistics<>();
	RunningStatistics<Double> throughputStatistics = new RunningStatistics<>();
	for (int run = 0; run < NUMBER_OF_RUNS; ++run) {
	    long startTime = System.nanoTime();
	    try (InputStream inputStream = inputStreamCreator.create()) {
		int b = inputStream.read();
		long count = 0;
		while (b != -1) {
		    assertEquals(count % 255, b);
		    b = inputStream.read();
		    count++;
		}
	    }
	    long stopTime = System.nanoTime();
	    double milliseconds = (stopTime - startTime) / 1_000_000d;
	    double throughput = FILE_SIZE / milliseconds / 1000.0;
	    timeStatistics.add(milliseconds);
	    throughputStatistics.add(throughput);
	    System.out.println(clazz.getSimpleName() + " with buffer of " + size + "kB: " + milliseconds + "ms / "
		    + throughput + "MB/s");
	}
	SequentialReadResult result = new SequentialReadResult(clazz.getName(), size, timeStatistics.getMin(),
		timeStatistics.getMax(), //
		timeStatistics.getMean(), timeStatistics.getSigma(), //
		throughputStatistics.getMin(), throughputStatistics.getMax(), //
		throughputStatistics.getMean(), throughputStatistics.getSigma());
	mapper.write(csvWriter, result);
    }
}
