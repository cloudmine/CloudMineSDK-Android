package com.cloudmine.test;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
import com.cloudmine.api.rest.response.ResponseBase;

import java.util.Arrays;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;


/**
 * <br>
 * Copyright CloudMine, Inc. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class ResponseCallbackTuple<SUCCESS_RESPONSE> implements Response.Listener<SUCCESS_RESPONSE>, Response.ErrorListener{

    public static final ResponseCallbackTuple<ResponseBase> defaultFailureListener = new ResponseCallbackTuple<ResponseBase>(null, null);

    public static final ResponseCallbackTuple<ResponseBase> hasSuccess = new ResponseCallbackTuple(new Response.Listener<ResponseBase>() {
        @Override
        public void onResponse(ResponseBase o) {
            assertTrue(o.wasSuccess());
        }
    });

    public static final <RT extends ResponseBase> ResponseCallbackTuple<RT> hasSuccess() {
        return new ResponseCallbackTuple<RT>(new Response.Listener<RT>() {
            @Override
            public void onResponse(RT rt) {
                assertTrue(rt.wasSuccess());
            }
        });
    }

    public static ResponseCallbackTuple<ObjectModificationResponse> wasCreated(final String... objectIds) {
        return new ResponseCallbackTuple<ObjectModificationResponse>(new Response.Listener<ObjectModificationResponse>() {
            @Override
            public void onResponse(ObjectModificationResponse modificationResponse) {
                assertTrue(modificationResponse.wasSuccess());
                assertEquals(objectIds.length, modificationResponse.getCreatedObjectIds().size() + modificationResponse.getUpdatedObjectIds().size());
                for(String objectId : objectIds) {
                    assertTrue(modificationResponse.wasModified(objectId));
                }
            }
        });
    }


    public static ResponseCallbackTuple<ObjectModificationResponse> wasCreatedOrUpdated(final String... objectIds) {
        return new ResponseCallbackTuple<ObjectModificationResponse>(new Response.Listener<ObjectModificationResponse>() {
            @Override
            public void onResponse(ObjectModificationResponse modificationResponse) {
                assertTrue(modificationResponse.wasSuccess());
                for(String objectId : objectIds) {
                    assertTrue(modificationResponse.wasModified(objectId));
                }
            }
        });
    }

    public static ResponseCallbackTuple<CMObjectResponse> wasLoaded(final List<CMObject> objects) {
        return new ResponseCallbackTuple<CMObjectResponse>(new Response.Listener<CMObjectResponse>() {
            @Override
            public void onResponse(CMObjectResponse objectResponse) {
                assertTrue(objectResponse.wasSuccess());
                assertEquals(objects.size(), objectResponse.getObjects().size());
                for(CMObject object : objects) {
                    assertEquals(object, objectResponse.getCMObject(object.getObjectId()));
                }
            }
        });
    }

    public static ResponseCallbackTuple<CMObjectResponse> wasLoaded(final CMObject... objects) {
        return wasLoaded(Arrays.asList(objects));
    }

    public static ResponseCallbackTuple<ObjectModificationResponse> wasDeleted(final String... objectIds) {
        return new ResponseCallbackTuple<ObjectModificationResponse>(new Response.Listener<ObjectModificationResponse>() {
            @Override
            public void onResponse(ObjectModificationResponse objectResponse) {
                assertTrue(objectResponse.wasSuccess());
                assertEquals(objectIds.length, objectResponse.getDeletedObjectIds().size());
                for(String objectId : objectIds){
                    assertTrue(objectResponse.wasDeleted(objectId));
                }
            }
        });
    }

    public static <SUCCESS_RESPONSE> ResponseCallbackTuple<SUCCESS_RESPONSE> testCallback(Response.Listener<SUCCESS_RESPONSE> successResponseListener) {
        return new ResponseCallbackTuple<SUCCESS_RESPONSE>(successResponseListener);
    }

    public static <SUCCESS_RESPONSE> ResponseCallbackTuple<SUCCESS_RESPONSE> testCallback(Response.Listener<SUCCESS_RESPONSE> successResponseListener, Response.ErrorListener errorListener) {
        return new ResponseCallbackTuple<SUCCESS_RESPONSE>(successResponseListener, errorListener);
    }

    private Response.Listener<SUCCESS_RESPONSE> successResponseListener;
    private Response.ErrorListener errorListener;

    public ResponseCallbackTuple(Response.Listener<SUCCESS_RESPONSE> successResponseListener) {
        this(successResponseListener, null);
    }

    public ResponseCallbackTuple(final Response.Listener<SUCCESS_RESPONSE> successResponseListener, final Response.ErrorListener errorListener) {
        this.successResponseListener = new Response.Listener<SUCCESS_RESPONSE>() {
            @Override
            public void onResponse(SUCCESS_RESPONSE success_response) {
                try {
                    if(successResponseListener != null) successResponseListener.onResponse(success_response);
                }catch(AssertionError t) {
                    AsyncTestResultsCoordinator.add(t);
                } catch(Exception e){
                    AsyncTestResultsCoordinator.add(new AssertionError(e));
                    e.printStackTrace();
                }
                finally {
                    AsyncTestResultsCoordinator.done();
                }
            }
        };

        this.errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                AsyncTestResultsCoordinator.add(volleyError);
                try {
                    if(errorListener != null) errorListener.onErrorResponse(volleyError);
                } catch(AssertionError t) {
                    AsyncTestResultsCoordinator.add(t);
                }finally {
                    AsyncTestResultsCoordinator.done();
                }
            }
        };
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        errorListener.onErrorResponse(volleyError);
    }

    @Override
    public void onResponse(SUCCESS_RESPONSE success_response) {
        successResponseListener.onResponse(success_response);
    }
}
