package com.puresoltechnologies.streaming.streams;

import java.io.InputStream;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * This input stream is used to generate a sequence of bytes into a stream. This
 * can be used to generate artificial series of bytes for testing or
 * mathematical formulas.
 * 
 * @author Rick-Rainer Ludwig
 *
 */
public class GeneratingInputStream extends InputStream {

    private final BiFunction<Long, Integer, Integer> function;
    private Integer lastValue = null;
    private long count = 0;

    /**
     * This constructor instantiates the class by providing a function to generate
     * the bytes.
     * 
     * @param function
     *            is a {@link Function} to provide the bytes to be read in. The
     *            values need to be in the range of 0 to 255. -1 signals the end of
     *            the generated data and all following bytes need to be -1 as well.
     *            The provided input values into the function are the number of
     *            already generated values and the last value generated. The very
     *            first last value is <code>null</code>;
     */
    public GeneratingInputStream(BiFunction<Long, Integer, Integer> function) {
	super();
	this.function = function;
    }

    @Override
    public int read() {
	int newValue = function.apply(count, lastValue);
	lastValue = newValue;
	count++;
	return newValue;
    }

}
