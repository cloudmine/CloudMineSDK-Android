package com.cloudmine.api.rest;

import android.graphics.Bitmap;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

import java.util.Map;

/**
 * A request specifically for loading an image from CloudMine. Allows for resizing the image
 * once loaded
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class BaseImageLoadRequest extends ImageRequest {
    private String sessionTokenString;

    /**
     * Creates a new image request, decoding to a maximum specified width and
     * height. If both width and height are zero, the image will be decoded to
     * its natural size. If one of the two is nonzero, that dimension will be
     * clamped and the other one will be set to preserve the image's aspect
     * ratio. If both width and height are nonzero, the image will be decoded to
     * be fit in the rectangle of dimensions width x height while keeping its
     * aspect ratio.
     *
     * @param fileId the id of the file
     * @param sessionToken optional; if specified, it is assumed the file is user level
     * @param serverFunction
     * @param maxWidth if nonzero, image will attempt to have this width while maintaining aspect ratio
     * @param maxHeight if nonzero, image will attempt to have this height while maintaining aspect ratio
     * @param decodeConfig details on how to decode the image
     * @param listener
     * @param errorListener
     */
    @Expand
    public BaseImageLoadRequest(String fileId, @Optional CMSessionToken sessionToken, @Optional CMApiCredentials credentials, @Optional CMServerFunction serverFunction, @Optional("sizing") int maxWidth, @Optional("sizing") int maxHeight, Bitmap.Config decodeConfig, Response.Listener<Bitmap> listener, @Optional Response.ErrorListener errorListener) {
        super(CloudMineRequest.getUrl((credentials == null ? CMApiCredentials.getCredentials() : credentials), CloudMineRequest.addServerFunction(addUser(sessionToken) + "/binary/" + fileId, serverFunction)),
                listener, maxWidth, maxHeight, decodeConfig, errorListener);
        sessionTokenString = sessionToken == null ? "" : sessionToken.getSessionToken();
    }

    private static String addUser(CMSessionToken sessionToken) {
        return (sessionToken != null ? "/user" : "");
    }


    public Map<String, String> getHeaders() throws AuthFailureError {
        return AndroidHeaderFactory.getHeaderMapping(sessionTokenString, CMApiCredentials.getApplicationApiKey());
    }

}
