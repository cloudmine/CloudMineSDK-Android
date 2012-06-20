package com.cloudmine.api.rest.callbacks;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.cloudmine.api.rest.response.ResponseConstructor;

/**
 * A wrapper around WebServiceCallback that makes it work with the AsyncHttp library. Should not
 * be extended.
 * Copyright CloudMine LLC
 */
public final class AndroidWebServiceCallback<T> extends AsyncHttpResponseHandler<T> implements WebServiceCallback<T> {

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
