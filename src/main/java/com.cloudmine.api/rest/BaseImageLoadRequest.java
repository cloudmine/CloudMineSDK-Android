package com.cloudmine.api.rest;

import android.graphics.Bitmap;
import com.android.cloudmine.AuthFailureError;
import com.android.cloudmine.Response;
import com.android.cloudmine.toolbox.ImageRequest;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.options.CMServerFunction;
import me.cloudmine.annotations.Expand;
import me.cloudmine.annotations.Optional;

import java.util.Map;

/**
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
     * @param listener      Listener to receive the decoded bitmap
     * @param maxWidth      Maximum width to decode this bitmap to, or zero for none
     * @param maxHeight     Maximum height to decode this bitmap to, or zero for
     *                      none
     * @param decodeConfig  Format to decode the bitmap to
     * @param errorListener Error listener, or null to ignore errors
     */
    @Expand
    public BaseImageLoadRequest(String fileId, @Optional CMSessionToken sessionToken, @Optional CMServerFunction serverFunction, @Optional("height") int maxWidth, @Optional("height") int maxHeight, Bitmap.Config decodeConfig, Response.Listener<Bitmap> listener, @Optional Response.ErrorListener errorListener) {
        super(CloudMineRequest.getUrl(CloudMineRequest.addServerFunction(addUser(sessionToken) + "/binary/" + fileId, serverFunction)),
                listener, maxWidth, maxHeight, decodeConfig, errorListener);
        sessionTokenString = sessionToken == null ? "" : sessionToken.getSessionToken();
    }

    private static String addUser(CMSessionToken sessionToken) {
        return (sessionToken != null ? "/user" : "");
    }


    public Map<String, String> getHeaders() throws AuthFailureError {
        return AndroidHeaderFactory.getHeaderMapping(sessionTokenString);
    }

}
