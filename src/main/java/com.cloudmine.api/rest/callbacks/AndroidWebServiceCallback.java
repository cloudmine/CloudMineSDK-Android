package com.cloudmine.api.rest.callbacks;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.cloudmine.api.rest.response.ResponseConstructor;

/**
 * Copyright CloudMine LLC
 * CMUser: johnmccarthy
 * Date: 5/23/12, 3:51 PM
 */
public class AndroidWebServiceCallback<T> extends AsyncHttpResponseHandler<T> implements WebServiceCallback<T> {

    private final WebServiceCallback callback;

    public AndroidWebServiceCallback(ResponseConstructor<T> constructor) {
        this(WebServiceCallback.DO_NOTHING, constructor);
    }

    public AndroidWebServiceCallback(WebServiceCallback callback, ResponseConstructor<T> constructor) {
        super(constructor);
        this.callback = callback;

    }

    @Override
    public void onCompletion(T response) {
        callback.onCompletion(response);
    }

    @Override
    public void onFailure(Throwable error, String message) {
        callback.onFailure(error, message);
    }
}
