package com.cloudmine.api;

import android.content.Context;
import android.content.SharedPreferences;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.UUID;

/**
 * Copyright CloudMine LLC
 * User: johnmccarthy
 * Date: 6/12/12, 1:22 PM
 */
public class DeviceIdentifier {

    public static final String UNIQUE_ID_KEY = "uniqueId";

    private static String uniqueId;
    public static final String DEVICE_HEADER_KEY = "X-CloudMine-UT";

    public static void initialize(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("CLOUDMINE_PREFERENCES", Context.MODE_WORLD_WRITEABLE);
        boolean isNotSet = !preferences.contains(UNIQUE_ID_KEY);
        if(isNotSet) {
            String uniqueId = generateUniqueDeviceIdentifier();
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString(UNIQUE_ID_KEY, uniqueId);
            editor.apply();
        }
        uniqueId = preferences.getString(UNIQUE_ID_KEY, null); //null here so if we aren't getting the unique key, we fail hard
    }

    public static String uniqueId() throws RuntimeException {
        if(uniqueId == null) {
            throw new RuntimeException("You must call DeviceIdentifier.initialize before using the cloudmine api");
        }
        return uniqueId;
    }

    public static Header deviceIdentifierHeader() throws RuntimeException {
        return new BasicHeader(DEVICE_HEADER_KEY, uniqueId);
    }

    private static String generateUniqueDeviceIdentifier() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
