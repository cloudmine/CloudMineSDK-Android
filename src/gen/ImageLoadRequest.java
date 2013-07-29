package com.cloudmine.api.rest;

public class ImageLoadRequest extends com.cloudmine.api.rest.BaseImageLoadRequest {

    public ImageLoadRequest(java.lang.String fileId, android.graphics.Bitmap.Config decodeConfig, com.android.cloudmine.Response.Listener<android.graphics.Bitmap> listener) {
        super(fileId, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, 0, 0, decodeConfig, listener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ImageLoadRequest(java.lang.String fileId, com.cloudmine.api.CMSessionToken sessionToken, android.graphics.Bitmap.Config decodeConfig, com.android.cloudmine.Response.Listener<android.graphics.Bitmap> listener) {
        super(fileId, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, 0, 0, decodeConfig, listener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ImageLoadRequest(java.lang.String fileId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.graphics.Bitmap.Config decodeConfig, com.android.cloudmine.Response.Listener<android.graphics.Bitmap> listener) {
        super(fileId, (com.cloudmine.api.CMSessionToken)null, serverFunction, 0, 0, decodeConfig, listener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ImageLoadRequest(java.lang.String fileId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.graphics.Bitmap.Config decodeConfig, com.android.cloudmine.Response.Listener<android.graphics.Bitmap> listener) {
        super(fileId, sessionToken, serverFunction, 0, 0, decodeConfig, listener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ImageLoadRequest(java.lang.String fileId, int maxWidth, int maxHeight, android.graphics.Bitmap.Config decodeConfig, com.android.cloudmine.Response.Listener<android.graphics.Bitmap> listener) {
        super(fileId, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, maxWidth, maxHeight, decodeConfig, listener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ImageLoadRequest(java.lang.String fileId, com.cloudmine.api.CMSessionToken sessionToken, int maxWidth, int maxHeight, android.graphics.Bitmap.Config decodeConfig, com.android.cloudmine.Response.Listener<android.graphics.Bitmap> listener) {
        super(fileId, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, maxWidth, maxHeight, decodeConfig, listener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ImageLoadRequest(java.lang.String fileId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, int maxWidth, int maxHeight, android.graphics.Bitmap.Config decodeConfig, com.android.cloudmine.Response.Listener<android.graphics.Bitmap> listener) {
        super(fileId, (com.cloudmine.api.CMSessionToken)null, serverFunction, maxWidth, maxHeight, decodeConfig, listener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ImageLoadRequest(java.lang.String fileId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, int maxWidth, int maxHeight, android.graphics.Bitmap.Config decodeConfig, com.android.cloudmine.Response.Listener<android.graphics.Bitmap> listener) {
        super(fileId, sessionToken, serverFunction, maxWidth, maxHeight, decodeConfig, listener, (com.android.cloudmine.Response.ErrorListener)null);
    }

    public ImageLoadRequest(java.lang.String fileId, android.graphics.Bitmap.Config decodeConfig, com.android.cloudmine.Response.Listener<android.graphics.Bitmap> listener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(fileId, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, 0, 0, decodeConfig, listener, errorListener);
    }

    public ImageLoadRequest(java.lang.String fileId, com.cloudmine.api.CMSessionToken sessionToken, android.graphics.Bitmap.Config decodeConfig, com.android.cloudmine.Response.Listener<android.graphics.Bitmap> listener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(fileId, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, 0, 0, decodeConfig, listener, errorListener);
    }

    public ImageLoadRequest(java.lang.String fileId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.graphics.Bitmap.Config decodeConfig, com.android.cloudmine.Response.Listener<android.graphics.Bitmap> listener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(fileId, (com.cloudmine.api.CMSessionToken)null, serverFunction, 0, 0, decodeConfig, listener, errorListener);
    }

    public ImageLoadRequest(java.lang.String fileId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, android.graphics.Bitmap.Config decodeConfig, com.android.cloudmine.Response.Listener<android.graphics.Bitmap> listener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(fileId, sessionToken, serverFunction, 0, 0, decodeConfig, listener, errorListener);
    }

    public ImageLoadRequest(java.lang.String fileId, int maxWidth, int maxHeight, android.graphics.Bitmap.Config decodeConfig, com.android.cloudmine.Response.Listener<android.graphics.Bitmap> listener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(fileId, (com.cloudmine.api.CMSessionToken)null, (com.cloudmine.api.rest.options.CMServerFunction)null, maxWidth, maxHeight, decodeConfig, listener, errorListener);
    }

    public ImageLoadRequest(java.lang.String fileId, com.cloudmine.api.CMSessionToken sessionToken, int maxWidth, int maxHeight, android.graphics.Bitmap.Config decodeConfig, com.android.cloudmine.Response.Listener<android.graphics.Bitmap> listener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(fileId, sessionToken, (com.cloudmine.api.rest.options.CMServerFunction)null, maxWidth, maxHeight, decodeConfig, listener, errorListener);
    }

    public ImageLoadRequest(java.lang.String fileId, com.cloudmine.api.rest.options.CMServerFunction serverFunction, int maxWidth, int maxHeight, android.graphics.Bitmap.Config decodeConfig, com.android.cloudmine.Response.Listener<android.graphics.Bitmap> listener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(fileId, (com.cloudmine.api.CMSessionToken)null, serverFunction, maxWidth, maxHeight, decodeConfig, listener, errorListener);
    }

    public ImageLoadRequest(java.lang.String fileId, com.cloudmine.api.CMSessionToken sessionToken, com.cloudmine.api.rest.options.CMServerFunction serverFunction, int maxWidth, int maxHeight, android.graphics.Bitmap.Config decodeConfig, com.android.cloudmine.Response.Listener<android.graphics.Bitmap> listener, com.android.cloudmine.Response.ErrorListener errorListener) {
        super(fileId, sessionToken, serverFunction, maxWidth, maxHeight, decodeConfig, listener, errorListener);
    }

}