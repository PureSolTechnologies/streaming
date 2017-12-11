package com.puresoltechnologies.streaming;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RunningStatisticsTest {

    @Test
    public void test() {
	RunningStatistics<Double> statistics = new RunningStatistics<>();
	statistics.add(1.0);
	assertEquals(1, statistics.getCount());
	assertEquals(1.0, statistics.getMax(), 1e-10);
	assertEquals(1.0, statistics.getMin(), 1e-10);
	assertEquals(1.0, statistics.getMean(), 1e-10);
	assertEquals(0.0, statistics.getSigma(), 1e-10);
	assertTrue(Double.isNaN(statistics.getS()));
	statistics.add(3.0);
	assertEquals(2, statistics.getCount());
	assertEquals(3.0, statistics.getMax(), 1e-10);
	assertEquals(1.0, statistics.getMin(), 1e-10);
	assertEquals(2.0, statistics.getMean(), 1e-10);
	assertEquals(1.0, statistics.getSigma(), 1e-10);
	assertEquals(Math.sqrt(2.0), statistics.getS(), 1e-10);
	statistics.add(2.0);
	assertEquals(3, statistics.getCount());
	assertEquals(3.0, statistics.getMax(), 1e-10);
	assertEquals(1.0, statistics.getMin(), 1e-10);
	assertEquals(2.0, statistics.getMean(), 1e-10);
	assertEquals(0.8164965809, statistics.getSigma(), 1e-10);
	assertEquals(1.0, statistics.getS(), 1e-10);
    }

}
