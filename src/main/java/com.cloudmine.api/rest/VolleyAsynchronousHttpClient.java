package com.cloudmine.api.rest;

import android.content.Context;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cloudmine.api.rest.callbacks.Callback;
import com.cloudmine.api.rest.response.ResponseConstructor;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class VolleyAsynchronousHttpClient implements AsynchronousHttpClient {

    private RequestQueue queue;

    public VolleyAsynchronousHttpClient(Context context) {
        queue = Volley.newRequestQueue(context);
    }


    @Override
    public <T> void executeCommand(HttpUriRequest command, final Callback<T> callback, final ResponseConstructor<T> constructor) {
        try {
            String url = command.getURI().toString();
            int method = getMethod(command.getMethod());
            final Map<String, String> headers = getHeadersAsMap(command);
            final byte[] body = getBody(command, callback);
            queue.add(new Request<T>(method, url, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    NetworkResponse response = volleyError.networkResponse;
                    if(response != null) callback.onCompletion(constructor.construct(new String(response.data), response.statusCode));
                    else  callback.onFailure(volleyError, "Volley failed");
                }
            }) {
                @Override
                protected Response parseNetworkResponse(NetworkResponse networkResponse) {
                    String messageBody = new String(networkResponse.data);
                    int responseCode = networkResponse.statusCode;
                    T responseBody = constructor.construct(messageBody, responseCode);
                    return Response.success(responseBody, getCacheEntry());
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    return headers;
                }

                public byte[] getBody() {
                    return body;
                }

                @Override
                protected void deliverResponse(T response) {
                    callback.onCompletion(response);
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=" + getParamsEncoding();
                }
            });
        }catch (Throwable t) {
            callback.onFailure(t, "Failed creating request");
        }
    }

    private <T> byte[] getBody(HttpUriRequest command, Callback<T> callback) {
        final byte[] body;
        if(command instanceof HttpEntityEnclosingRequestBase) {
            HttpEntity entity = ((HttpEntityEnclosingRequestBase)command).getEntity();
            if(entity != null) {
                body = new byte[(int) entity.getContentLength()];
                try {
                    entity.getContent().read(body);
                } catch (IOException e) {
                    callback.onFailure(e, "Unable to extract message body");
                }
            } else {
                body = new byte[0];
            }
        }else {
            body = null;
        }
        return body;
    }

    private Map<String, String> getHeadersAsMap(HttpUriRequest command) {
        final Map<String, String> headers = new HashMap<String, String>();
        for(Header header : command.getAllHeaders()) {
            headers.put(header.getName(), header.getValue());
        }
        return headers;
    }

    private int getMethod(String methodName) {
        if("GET".equalsIgnoreCase(methodName))         return Request.Method.GET;
        else if("POST".equalsIgnoreCase(methodName))   return Request.Method.POST;
        else if("PUT".equalsIgnoreCase(methodName))    return Request.Method.PUT;
        else if("DELETE".equalsIgnoreCase(methodName)) return Request.Method.DELETE;
        else                                           return Request.Method.DEPRECATED_GET_OR_POST;
    }
}
