package com.puresoltechnologies.streaming.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import com.puresoltechnologies.streaming.csv.CSVHeader;
import com.puresoltechnologies.streaming.csv.CSVWriter;
import com.puresoltechnologies.streaming.csv.mapper.CSVMapper;
import com.puresoltechnologies.streaming.csv.mapper.CSVMappingException;
import com.puresoltechnologies.streaming.iterators.RunningStatistics;
import com.puresoltechnologies.streaming.streams.InputStreamCreator;

public class SequentialReadPerformanceTest {

	private static final File TEST_DIRECTORY = new File("target/test");
	private static final File TEST_FILE = new File(TEST_DIRECTORY, "sequential_read.bin");
	private static final File CSV_FILE = new File(TEST_DIRECTORY, "sequential_read.csv");
	private static final long FILE_SIZE = 1024 * 1024; // 1MB
	private static final int NUMBER_OF_RUNS = 3;

	private static final CSVMapper mapper = new CSVMapper();
	private static OutputStream csvOutputFile = null;
	private static CSVWriter csvWriter = null;
	private static final List<Arguments> arguments = new ArrayList<>();

	static class Parameters implements ArgumentsProvider {
		@Override
		public Stream<? extends Arguments> provideArguments(ExtensionContext context) throws Exception {
			return arguments.stream();
		}
	}

	@BeforeAll
	public static void createFile() throws IOException {
		System.out.println("================================");
		System.out.println("Sequential Read Performance Test");
		System.out.println("================================");
		if (!TEST_DIRECTORY.exists()) {
			assertTrue(TEST_DIRECTORY.mkdirs());
		}
		if (CSV_FILE.exists()) {
			assertTrue(CSV_FILE.delete());
		}
		csvOutputFile = new BufferedOutputStream(new FileOutputStream(CSV_FILE));
		csvWriter = new CSVWriter(csvOutputFile);
		csvWriter.writeHeader(new CSVHeader(Arrays.asList("Class", "BufferSize", "minTime", "maxTime", "meanTime",
				"sigmaTime", "minThroughput", "maxThroughput", "meanThroughput", "sigmaThroughput")));
	}

	@AfterAll
	public static void closeResultFile() throws IOException {
		csvOutputFile.close();
	}

	@BeforeAll
	public static void createTestFileAndParameters() throws Exception {
		if (!TEST_DIRECTORY.exists()) {
			assertTrue(TEST_DIRECTORY.mkdirs());
		}
		if (TEST_FILE.exists()) {
			assertTrue(TEST_FILE.delete());
		}
		try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(TEST_FILE))) {
			for (int i = 0; i < FILE_SIZE; ++i) {
				outputStream.write(i % 256);
			}
		}
		arguments.add(Arguments.of( //
				FileInputStream.class, //
				0, //
				(InputStreamCreator<InputStream>) () -> new FileInputStream(TEST_FILE)));
		for (int bufferSize : new int[] { 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536 }) {
			arguments.add( //
					Arguments.of( //
							BufferedInputStream.class, //
							bufferSize, //
							(InputStreamCreator<BufferedInputStream>) () -> new BufferedInputStream(
									new FileInputStream(TEST_FILE), bufferSize) //
					) //
			);
		}
	}

	@ParameterizedTest(name = "#{index}: [{arguments}]")
	@ArgumentsSource(Parameters.class)
	public void testSequentialUnbuffered1(Class<? extends InputStream> clazz, int size,
			InputStreamCreator<?> inputStreamCreator) throws IOException, CSVMappingException {
		RunningStatistics<Double> timeStatistics = new RunningStatistics<>();
		RunningStatistics<Double> throughputStatistics = new RunningStatistics<>();
		for (int run = 0; run < NUMBER_OF_RUNS; ++run) {
			long startTime = System.nanoTime();
			try (InputStream inputStream = inputStreamCreator.create()) {
				int b = inputStream.read();
				long count = 0;
				while (b != -1) {
					assertEquals(count % 256, b);
					b = inputStream.read();
					count++;
				}
			}
			long stopTime = System.nanoTime();
			double milliseconds = (stopTime - startTime) / 1_000_000d;
			double throughput = FILE_SIZE / milliseconds / 1000.0;
			timeStatistics.add(milliseconds);
			throughputStatistics.add(throughput);
			System.out.println(clazz.getSimpleName() + " with buffer of " + size + "  bytes: " + milliseconds + "ms / "
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
