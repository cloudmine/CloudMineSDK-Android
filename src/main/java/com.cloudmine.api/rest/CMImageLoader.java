package com.cloudmine.api.rest;

import android.graphics.Bitmap;
import com.android.cloudmine.RequestQueue;
import com.android.cloudmine.Response;
import com.android.cloudmine.VolleyError;
import com.android.cloudmine.toolbox.ImageLoader;
import com.android.cloudmine.toolbox.ImageRequest;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class CMImageLoader extends ImageLoader {

    /**
     * Constructs a new ImageLoader.
     *
     * @param queue      The RequestQueue to use for making image requests.
     * @param imageCache The cache to use as an L1 cache.
     */
    public CMImageLoader(RequestQueue queue, ImageCache imageCache) {
        super(queue, imageCache);
    }

    protected ImageRequest getImageRequest(String requestUrl, int maxWidth, int maxHeight, final String cacheKey) {
        return new com.cloudmine.api.rest.ImageLoadRequest(requestUrl, maxWidth, maxHeight,
                Bitmap.Config.RGB_565, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {
                onGetImageSuccess(cacheKey, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                onGetImageError(cacheKey, error);
            }
        });
    }

}
