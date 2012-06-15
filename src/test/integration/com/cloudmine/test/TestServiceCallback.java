package com.cloudmine.test;

import com.cloudmine.api.rest.callbacks.CMWebServiceCallback;

/**
 * Copyright CloudMine LLC
 * CMUser: johnmccarthy
 * Date: 6/6/12, 3:36 PM
 */
public class TestServiceCallback<T> extends CMWebServiceCallback<T> {

    private CMWebServiceCallback<T> callback;

    public static <T> TestServiceCallback<T> testCallback(CMWebServiceCallback<T> callback) {
        return new TestServiceCallback<T>(callback);
    }

    public TestServiceCallback(CMWebServiceCallback<T> callback) {
        super(callback.constructor());
        this.callback = callback;
    }

    @Override
    public void onCompletion(T response) {
        try {
            callback.onCompletion(response);
        } catch(AssertionError t) {
            AsyncTestResultsCoordinator.add(t);
        } catch(Exception e){
            e.printStackTrace();
        }
        finally {
            AsyncTestResultsCoordinator.done();
        }
    }

    @Override
    public void onFailure(Throwable thrown, String message) {
        try {
            callback.onFailure(thrown, message);
        } catch(AssertionError t) {
            AsyncTestResultsCoordinator.add(t);
        }
    }
}
