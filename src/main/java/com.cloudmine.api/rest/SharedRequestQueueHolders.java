package com.cloudmine.api.rest;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class SharedRequestQueueHolders {
    private static final String DEFAULT_CACHE_DIR = "volley";
    //We can use this as the RequestQueue/DiskBasedCache do not keep a reference to the context. If that changes in the future rework will be needed
    private static Map<Context, RequestQueue> queueMap = new WeakHashMap<Context, RequestQueue>();

    public static RequestQueue getRequestQueue(Context context) {
        RequestQueue queue = queueMap.get(context);
        if(queue == null) {
            queue = newRequestQueue(context, new OkHttpStack());
            queueMap.put(context, queue);
        }
        return queue;
    }

    /**
     * This code is copy/pasted out of Volley.java, so we can replace BasicNetwork with CMNetwork
     * @param context
     * @param stack
     * @return
     */
    public static RequestQueue newRequestQueue(Context context, HttpStack stack) {
        File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);

        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
        }

        if (stack == null) {
            if (Build.VERSION.SDK_INT >= 9) {
                stack = new HurlStack();
            } else {
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            }
        }

        Network network = new CMNetwork(stack);

        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir), network);
        queue.start();

        return queue;
    }
}
