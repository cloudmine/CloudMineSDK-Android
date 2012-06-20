package com.cloudmine.api.rest;

import com.cloudmine.api.rest.callbacks.AndroidCallback;
import com.cloudmine.api.rest.callbacks.Callback;
import com.cloudmine.api.rest.response.ResponseConstructor;
import com.loopj.android.http.AsyncHttpClient;
import org.apache.http.client.methods.HttpUriRequest;

import java.util.concurrent.Future;

/**
 * Implementation of AsynchronousHttpClient for the Android platform.
 * Copyright CloudMine LLC
 */
public class AndroidAsynchronousHttpClient extends AsyncHttpClient implements AsynchronousHttpClient{
    @Override
    public <T> Future<T> executeCommand(HttpUriRequest command, Callback<T> callback, ResponseConstructor<T> constructor) {
        return execute(command, new AndroidCallback(callback, constructor));
    }
}
