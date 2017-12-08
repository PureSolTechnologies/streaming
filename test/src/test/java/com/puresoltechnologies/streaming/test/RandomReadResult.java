package com.puresoltechnologies.streaming.test;

import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVCreator;
import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVDouble;
import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVInt;

public class RandomReadResult {

    private final int streamCount;
    private final int accessCount;
    private final double milliseconds;
    private final double throughput;

    @CSVCreator
    public RandomReadResult( //
	    @CSVInt("streamCount") int streamCount, //
	    @CSVInt("accessCount") int accessCount, //
	    @CSVDouble("milliseconds") double milliseconds, //
	    @CSVDouble("throughput") double throughput //
    ) {
	super();
	this.streamCount = streamCount;
	this.accessCount = accessCount;
	this.milliseconds = milliseconds;
	this.throughput = throughput;
    }

    public int getStreamCount() {
	return streamCount;
    }

    public int getAccessCount() {
	return accessCount;
    }

    public double getMilliseconds() {
	return milliseconds;
    }

    public double getThroughput() {
	return throughput;
    }

}
