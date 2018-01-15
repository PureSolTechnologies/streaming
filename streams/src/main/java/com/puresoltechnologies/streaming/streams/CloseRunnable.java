package com.puresoltechnologies.streaming.streams;

import java.io.IOException;

/**
 * This is a special interface to provide a closure for generic close, shutdown
 * and dispose methods.
 * 
 * @author Rick-Rainer Ludwig
 */
@FunctionalInterface
public interface CloseRunnable {

    public void runClose() throws IOException;

}
