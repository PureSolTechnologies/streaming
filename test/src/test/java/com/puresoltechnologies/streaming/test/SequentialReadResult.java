package com.puresoltechnologies.streaming.test;

import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVCreator;
import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVDouble;
import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVInt;
import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVString;

public class SequentialReadResult {

    private final String className;
    private final int bufferSize;
    private final double milliseconds;
    private final double throughput;

    @CSVCreator
    public SequentialReadResult(@CSVString("className") String className, @CSVInt("bufferSize") int bufferSize,
	    @CSVDouble("milliseconds") double milliseconds, @CSVDouble("throughput") double throughput) {
	super();
	this.className = className;
	this.bufferSize = bufferSize;
	this.milliseconds = milliseconds;
	this.throughput = throughput;
    }

    public String getClassName() {
	return className;
    }

    public int getBufferSize() {
	return bufferSize;
    }

    public double getMilliseconds() {
	return milliseconds;
    }

    public double getThroughput() {
	return throughput;
    }

}
