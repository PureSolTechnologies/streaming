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
import java.util.Random;

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
import com.puresoltechnologies.streaming.streams.MultiStreamSeekableInputStream;

@RunWith(Parameterized.class)
public class RandomReadPerformanceTest {

    private static final File TEST_DIRECTORY = new File("target/test");
    private static final File TEST_FILE = new File(TEST_DIRECTORY, "random_read.bin");
    private static final File CSV_FILE = new File(TEST_DIRECTORY, "random_read.csv");
    private static final long FILE_SIZE = 10 * 1024 * 1024; // 1MB
    private static final int NUMBER_OF_RUNS = 10;

    private static final CSVMapper mapper = new CSVMapper();
    private static OutputStream csvOutputFile = null;
    private static CSVWriter csvWriter = null;

    private static InputStreamCreator<?> creator;

    @Parameters
    public static Collection<Object[]> data() throws IOException {
	if (!TEST_DIRECTORY.exists()) {
	    assertTrue("Could not create directory for test file.", TEST_DIRECTORY.mkdirs());
	}
	if (TEST_FILE.exists()) {
	    assertTrue("Could not delete old file", TEST_FILE.delete());
	}
	try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(TEST_FILE))) {
	    for (int i = 0; i < FILE_SIZE; ++i) {
		outputStream.write(i % 0xFF);
	    }
	}
	List<Object[]> list = new ArrayList<>();
	for (int streamCount : new int[] { 8, 16, 32, 64 }) {
	    for (int accessCount : new int[] { 10, 50, 100 }) {
		list.add(new Object[] { //
			streamCount, //
			accessCount, //
		});
	    }
	}
	return list;
    }

    @BeforeClass
    public static void createFile() throws IOException {
	System.out.println("============================");
	System.out.println("Random Read Performance Test");
	System.out.println("============================");
	creator = new InputStreamCreator<InputStream>() {
	    @Override
	    public InputStream create() throws IOException {
		return new BufferedInputStream(new FileInputStream(TEST_FILE));
	    }
	};
	if (CSV_FILE.exists()) {
	    assertTrue(CSV_FILE.delete());
	}
	csvOutputFile = new BufferedOutputStream(new FileOutputStream(CSV_FILE));
	csvWriter = new CSVWriter(csvOutputFile);
	csvWriter.writeHeader(new CSVHeader(Arrays.asList("StreamCount", "AccessCount", "minTime", "maxTime",
		"meanTime", "sigmaTime", "minThroughput", "maxThroughput", "meanThroughput", "sigmaThroughput")));
    }

    @AfterClass
    public static void closeFiles() throws IOException {
	csvOutputFile.close();
    }

    private final int streamCount;
    private final int accessCount;

    public RandomReadPerformanceTest(int streamCount, int accessCount) {
	super();
	this.streamCount = streamCount;
	this.accessCount = accessCount;
    }

    @Test
    public void testRandomRead1Streams() throws IOException, CSVMappingException {
	Random random = new Random(42);
	RunningStatistics<Double> timeStatistics = new RunningStatistics<>();
	RunningStatistics<Double> throughputStatistics = new RunningStatistics<>();
	for (int run = 0; run < NUMBER_OF_RUNS; ++run) {
	    long startTime = System.nanoTime();
	    try (MultiStreamSeekableInputStream fileInputStream = new MultiStreamSeekableInputStream(streamCount,
		    creator)) {
		for (int i = 0; i < accessCount; ++i) {
		    int pos = random.nextInt((int) FILE_SIZE);
		    fileInputStream.seek(pos);
		    int b = fileInputStream.read();
		    assertEquals(pos % 255, b);
		}
	    }
	    long stopTime = System.nanoTime();
	    double milliseconds = (stopTime - startTime) / 1_000_000d;
	    double throughput = FILE_SIZE / milliseconds / 1000.0;
	    timeStatistics.add(milliseconds);
	    throughputStatistics.add(throughput);
	    System.out.println("Read 1 stream: " + milliseconds + "ms / " + throughput + "MB/s");
	}
	RandomReadResult result = new RandomReadResult(streamCount, accessCount, //
		timeStatistics.getMin(), timeStatistics.getMax(), //
		timeStatistics.getMean(), timeStatistics.getSigma(), //
		throughputStatistics.getMin(), throughputStatistics.getMax(), //
		throughputStatistics.getMean(), throughputStatistics.getSigma());
	mapper.write(csvWriter, result);
    }

}
