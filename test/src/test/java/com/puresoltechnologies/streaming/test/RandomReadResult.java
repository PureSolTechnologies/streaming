package com.puresoltechnologies.streaming.test;

import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVCreator;
import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVDouble;
import com.puresoltechnologies.streaming.csv.mapper.annotations.CSVInt;

public class RandomReadResult {

    private final int streamCount;
    private final int accessCount;
    private final double minTime;
    private final double maxTime;
    private final double meanTime;
    private final double sigmaTime;
    private final double minThroughput;
    private final double maxThroughput;
    private final double meanThroughput;
    private final double sigmaThroughput;

    @CSVCreator
    public RandomReadResult( //
	    @CSVInt("streamCount") int streamCount, //
	    @CSVInt("accessCount") int accessCount, //
	    @CSVDouble("minTime") double minTime, //
	    @CSVDouble("maxTime") double maxTime, //
	    @CSVDouble("meanTime") double meanTime, //
	    @CSVDouble("sigmaTime") double sigmaTime, //
	    @CSVDouble("minThroughput") double minThroughput, //
	    @CSVDouble("maxThroughput") double maxThroughput, //
	    @CSVDouble("meanThroughput") double meanThroughput, //
	    @CSVDouble("sigmaThroughput") double sigmaThroughput //
    ) {
	super();
	this.streamCount = streamCount;
	this.accessCount = accessCount;
	this.minTime = minTime;
	this.maxTime = maxTime;
	this.meanTime = meanTime;
	this.sigmaTime = sigmaTime;
	this.minThroughput = minThroughput;
	this.maxThroughput = maxThroughput;
	this.meanThroughput = meanThroughput;
	this.sigmaThroughput = sigmaThroughput;
    }

    public int getStreamCount() {
	return streamCount;
    }

    public int getAccessCount() {
	return accessCount;
    }

    public double getMinTime() {
	return minTime;
    }

    public double getMaxTime() {
	return maxTime;
    }

    public double getMeanTime() {
	return meanTime;
    }

    public double getSigmaTime() {
	return sigmaTime;
    }

    public double getMinThroughput() {
	return minThroughput;
    }

    public double getMaxThroughput() {
	return maxThroughput;
    }

    public double getMeanThroughput() {
	return meanThroughput;
    }

    public double getSigmaThroughput() {
	return sigmaThroughput;
    }

}
