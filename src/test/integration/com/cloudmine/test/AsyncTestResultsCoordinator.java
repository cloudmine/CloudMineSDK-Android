package com.cloudmine.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Copyright CloudMine LLC
 * CMUser: johnmccarthy
 * Date: 6/6/12, 3:25 PM
 */
public class AsyncTestResultsCoordinator {
    private static final List<AssertionError> errors = new ArrayList<AssertionError>();
    private static CountDownLatch latch;
    public static final int TIMEOUT = 5;

    public static void add(AssertionError error) {
        errors.add(error);
    }

    public static void reset() {
        reset(1);
    }

    public static void reset(int numberOfCallbacks) {
        latch = new CountDownLatch(numberOfCallbacks);
        errors.clear();
    }

    public static void waitThenAssertTestResults() {
        waitForTestResults();
        assertAsyncTaskResult();
        reset();
    }

    public static void waitForTestResults() {
        try {
            boolean timedOut = !latch.await(5, TimeUnit.SECONDS);
            if(timedOut) {
                throw new RuntimeException("Timedout!");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted", e);
        }
    }

    public static void done() {
        System.out.println("Done " + latch.getCount());
        latch.countDown();
    }

    public static void assertAsyncTaskResult() throws AssertionError {
        for(AssertionError error : errors) {
            throw error;
        }
    }
}
