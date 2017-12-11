package com.puresoltechnologies.streaming;

/**
 * This class calculates the statistics of a provided series of dates on the
 * fly.
 * 
 * @see https://en.wikipedia.org/wiki/Standard_deviation#Rapid_calculation_methods
 * 
 * @author Rick-Rainer Ludwig
 */
public class RunningStatistics<T extends Number & Comparable<T>> {

    private long count = 0;
    private double min = Double.MAX_VALUE;
    private double max = Double.MIN_VALUE;
    private double mean = 0.0;
    private double q = 0.0;
    private double sigma = 0.0;
    private double s = 0.0;

    public void add(T value) {
	double d = value.doubleValue();
	if (min > d) {
	    min = d;
	}
	if (max < d) {
	    max = d;
	}
	count++;
	q += ((double) count - 1) / count * (d - mean) * (d - mean);
	mean += (d - mean) / count;
	sigma = Math.sqrt(q / count);
	s = Math.sqrt(q / (count - 1));
    }

    public long getCount() {
	return count;
    }

    public double getMin() {
	return min;
    }

    public double getMax() {
	return max;
    }

    public double getMean() {
	return mean;
    }

    public double getSigma() {
	return sigma;
    }

    public double getS() {
	return s;
    }
}
