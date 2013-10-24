package com.cloudmine.api.rest;

import android.graphics.Bitmap;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageRequest;

/**
 * This class won't work for now because we can't access onGetImageSuccess in
 * ImageLoader. Should be able to work around it with reflection but don't want
 * to put something so breakable into the library yet.
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
@Deprecated
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
//                onGetImageSuccess(cacheKey, response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                onGetImageError(cacheKey, error);
            }
        });
    }

}
