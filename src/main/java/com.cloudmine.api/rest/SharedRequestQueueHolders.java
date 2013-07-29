package com.cloudmine.api.rest;

import android.content.Context;
import com.android.cloudmine.RequestQueue;
import com.android.cloudmine.toolbox.Volley;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class SharedRequestQueueHolders {
    //We can use this as the RequestQueue/DiskBasedCache do not keep a reference to the context. If that changes in the future rework will be needed
    private static Map<Context, RequestQueue> queueMap = new WeakHashMap<Context, RequestQueue>();

    public static RequestQueue getRequestQueue(Context context) {
        RequestQueue queue = queueMap.get(context);
        if(queue == null) {
            queue = Volley.newRequestQueue(context);
            queueMap.put(context, queue);
        }
        return queue;
    }
}
