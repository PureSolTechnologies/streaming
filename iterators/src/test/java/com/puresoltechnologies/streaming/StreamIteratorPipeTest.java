package com.puresoltechnologies.streaming;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;

import com.puresoltechnologies.streaming.iterators.StreamIteratorPipe;

public class StreamIteratorPipeTest {

    @Test
    public void test() throws IOException, InterruptedException, ExecutionException {
	System.out.println("TEST");
	try (StreamIteratorPipe<Integer> pipe = new StreamIteratorPipe<>()) {
	    ExecutorService threadPool = Executors.newFixedThreadPool(2);
	    int number = 1000;
	    Future<Integer> writeFuture = threadPool.submit(() -> {
		int i;
		for (i = 0; i < number; i++) {
		    pipe.push(i);
		}
		return i;
	    });
	    Future<Integer> readFuture = threadPool.submit(() -> {
		int i;
		for (i = 0; i < number; i++) {
		    pipe.next();
		}
		return i;
	    });
	    threadPool.shutdown();
	    threadPool.awaitTermination(10, TimeUnit.SECONDS);
	    assertEquals(number, (int) writeFuture.get());
	    assertEquals(number, (int) readFuture.get());
	}
    }

}
