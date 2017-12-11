package com.puresoltechnologies.streaming;

import java.util.NoSuchElementException;

public class StatisticsIterator<T extends Number & Comparable<T>> implements StreamIterator<T> {

    private final StreamIterator<T> iterator;
    private final RunningStatistics<T> statistics = new RunningStatistics<>();

    public StatisticsIterator(StreamIterator<T> iterator) {
	super();
	this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
	return iterator.hasNext();
    }

    @Override
    public T next() throws NoSuchElementException {
	T next = iterator.next();
	statistics.add(next);
	return next;
    }

    @Override
    public T peek() throws NoSuchElementException {
	return iterator.peek();
    }

    public long getCount() {
	return statistics.getCount();
    }

    public double getMin() {
	return statistics.getMin();
    }

    public double getMax() {
	return statistics.getMax();
    }

    public double getMean() {
	return statistics.getMean();
    }

    public double getSigma() {
	return statistics.getSigma();
    }

    public double getS() {
	return statistics.getS();
    }

}
