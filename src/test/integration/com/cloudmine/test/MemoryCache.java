package com.cloudmine.test;

import com.android.volley.Cache;

import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class MemoryCache implements Cache {

    private final Map<String, Entry> cache = new HashMap<String, Entry>();

    @Override
    public Entry get(String s) {
        return cache.get(s);
    }

    @Override
    public void put(String s, Entry entry) {
        cache.put(s, entry);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void invalidate(String key, boolean fullExpire) {
        Entry entry = get(key);
        if (entry != null) {
            entry.softTtl = 0;
            if (fullExpire) {
                entry.ttl = 0;
            }
            put(key, entry);
        }
    }

    @Override
    public void remove(String s) {
        cache.remove(s);
    }

    @Override
    public void clear() {
        cache.clear();
    }
}
