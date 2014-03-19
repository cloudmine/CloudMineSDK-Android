package com.cloudmine.api.rest;

import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.DeviceIdentifier;
import com.cloudmine.api.Strings;
import org.apache.http.Header;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class AndroidHeaderFactory extends JavaHeaderFactory {
    public static final String CLOUD_MINE_AGENT = "Android/0.6";

    private static Map<String, String> DEFAULT_HEADERS = new HashMap<String, String>();
    static {
        DEFAULT_HEADERS.put(AGENT_HEADER_KEY, CLOUD_MINE_AGENT);
    }

    public static Map<String, String> getHeaderMapping(String sessionTokenString, String apiKey) {
        Map<String, String> headerMap = new HashMap<String, String>(DEFAULT_HEADERS);
        headerMap.put(HeaderFactory.DEVICE_HEADER_KEY, getDeviceHeaderValue());
        headerMap.put(HeaderFactory.API_HEADER_KEY, apiKey); //worry about sync issues? not now but could be an issue

        if(Strings.isNotEmpty(sessionTokenString)) headerMap.put(HeaderFactory.SESSION_TOKEN_HEADER_KEY, sessionTokenString);
        return headerMap;
    }

    public static Map<String, String> getHeaderMapping(CMSessionToken sessionToken, String apiKey) {
        String sessionTokenString = sessionToken == null ? null : sessionToken.getSessionToken();
        return getHeaderMapping(sessionTokenString, apiKey);
    }


    private static String getDeviceHeaderValue() {
        String deviceId = DeviceIdentifier.getUniqueId();
        String timingHeaders = ResponseTimeDataStore.getContentsAsStringAndClearMap();
        String deviceHeaderValue = deviceId;
        if(Strings.isNotEmpty(timingHeaders)) deviceHeaderValue += "; " + timingHeaders;
        return deviceHeaderValue;
    }

    @Override
    public Set<Header> getCloudMineHeaders(String apiKey) {
        Set<Header> headers = super.getCloudMineHeaders(apiKey);
        return headers;
    }

    protected String getDeviceIdentifier() {
        return DeviceIdentifier.getUniqueId();
    }

    @Override
    public String getCloudMineAgent() {
        return CLOUD_MINE_AGENT;
    }
}
