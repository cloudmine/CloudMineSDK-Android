package com.cloudmine.api.rest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.ImageLoader;

import java.io.File;
import java.nio.ByteBuffer;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public  class DiskBitmapCache extends DiskBasedCache implements ImageLoader.ImageCache {

    public DiskBitmapCache(Context context) {
        this(context.getCacheDir());
    }

    public DiskBitmapCache(File rootDirectory, int maxCacheSizeInBytes) {
        super(rootDirectory, maxCacheSizeInBytes);
    }

    public DiskBitmapCache(File cacheDir) {
        super(cacheDir);
    }

    public Bitmap getBitmap(String url) {
        final Entry requestedItem = get(url);

        if (requestedItem == null)
            return null;

        return BitmapFactory.decodeByteArray(requestedItem.data, 0, requestedItem.data.length);
    }

    public void putBitmap(String url, Bitmap bitmap) {
        final Entry entry = new Entry();


        ByteBuffer buffer = ByteBuffer.allocate(getSizeInBytes(bitmap));
        bitmap.copyPixelsToBuffer(buffer);
        entry.data = buffer.array();

        put(url, entry);
    }

    //From: https://code.google.com/p/android-beryl/source/browse/beryl/src/org/beryl/app/AndroidVersion.java
    private static int getSizeInBytes(Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }
}