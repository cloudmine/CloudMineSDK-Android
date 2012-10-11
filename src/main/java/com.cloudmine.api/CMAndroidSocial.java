package com.cloudmine.api;

import android.app.Activity;
import android.content.Context;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import com.cloudmine.api.gui.AuthenticationDialog;
import com.cloudmine.api.rest.CMSocial;
import com.cloudmine.api.rest.callbacks.CMCallback;
import com.cloudmine.api.rest.callbacks.Callback;
import com.cloudmine.api.rest.response.CMSocialLoginResponse;
import org.apache.http.impl.cookie.BasicClientCookie;

import java.util.Date;

/**
 * Social that requires an activity to authorize, so that the user can log in through a GUI
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class CMAndroidSocial extends CMSocial {

    private static final String EXPIRES = "; Expires=Thu, 01-Jan-1970 00:00:01 GMT";
    private static final String COOKIE_URL = "https://api.singly.com";


    public static void createNewUserThroughSocial(final Service service, Activity activity) {
        createNewUserThroughSocial(service, activity, CMCallback.<CMSocialLoginResponse>doNothing());
    }

    /**
     * Switch from the current Activity to the log in page for the given service. On completion, the current Activity
     * will be shown. The newly created user and their session token will be passed into the given callback
     * @param service
     * @param activity
     * @param callback expects a CMSocialLoginResponse
     */
    public static void createNewUserThroughSocial(final Service service, Activity activity, final Callback<CMSocialLoginResponse> callback) {
        try {
            String authURL = getAuthenticationUrl(service);
            AuthenticationDialog dialog = new AuthenticationDialog(activity, authURL, callback);
            dialog.show();
        }catch(Exception e) {
            callback.onFailure(e, "");
        }
    }

    public static void clearSocialCookie(Context context, Service... services) {
        CookieSyncManager syncManager = CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        for(Service service : services) {
            String url = service.getAuthenticationUrl();
            String cookie = cookieManager.getCookie(url);
            if(hasCookie(cookie)) {
                System.out.println("*****************" + service);
            }
            int isInfinite = 0;
            while(hasCookie(cookie) && isInfinite < 30) {
                System.out.println("OC: " + cookie);
                cookie += EXPIRES;
                cookieManager.setCookie(url, cookie);
                syncManager.sync();
                cookieManager.removeExpiredCookie();
                cookie = cookieManager.getCookie(url);
                System.out.println("NC: " + cookie);
                isInfinite++;
            }
        }
    }

    public static void clearAllSocialCookies(Context context) {
        clearSocialCookie(context, Service.values());
    }

    private static boolean hasCookie(String cookie) {
        return !Strings.isEmpty(cookie);
    }

    private static BasicClientCookie parseRawCookie(String rawCookie) throws RuntimeException {
        String[] rawCookieParams = rawCookie.split(";");

        String[] rawCookieNameAndValue = rawCookieParams[0].split("=");
        if (rawCookieNameAndValue.length != 2) {
            throw new RuntimeException("Invalid cookie: missing name and value.");
        }

        String cookieName = rawCookieNameAndValue[0].trim();
        String cookieValue = rawCookieNameAndValue[1].trim();
        BasicClientCookie cookie = new BasicClientCookie(cookieName, cookieValue);
        for (int i = 1; i < rawCookieParams.length; i++) {
            String rawCookieParamNameAndValue[] = rawCookieParams[i].trim().split("=");

            String paramName = rawCookieParamNameAndValue[0].trim();

            if (paramName.equalsIgnoreCase("secure")) {
                cookie.setSecure(true);
            } else {
                if (rawCookieParamNameAndValue.length != 2) {
                    throw new RuntimeException("Invalid cookie: attribute not a flag or missing value.");
                }

                String paramValue = rawCookieParamNameAndValue[1].trim();

                if (paramName.equalsIgnoreCase("expires")) {
                    Date expiryDate = new Date(0);
                    cookie.setExpiryDate(expiryDate);
                } else if (paramName.equalsIgnoreCase("domain")) {
                    cookie.setDomain(paramValue);
                } else if (paramName.equalsIgnoreCase("path")) {
                    cookie.setPath(paramValue);
                } else if (paramName.equalsIgnoreCase("comment")) {
                    cookie.setPath(paramValue);
                } else {
//                    throw new RuntimeException("Invalid cookie: invalid attribute name.");
                }
            }
        }

        return cookie;
    }

    private static String getAuthenticationUrl(Service service) {
        return getAuthenticationUrl(service, null);
    }

    private static String getAuthenticationUrl(Service service, String userObjectId) {
        String token = CMObject.generateUniqueObjectId();
        String challenge = CMObject.generateUniqueObjectId();
        String authenticationUrl = "http://api-beta.cloudmine.me/v1/app/" + CMApiCredentials.getApplicationIdentifier() + "/account/social/login/" + token + "?service=" + service.asUrlString() + "&apikey=" + CMApiCredentials.getApplicationApiKey() + "&challenge=" + challenge;
        if(!Strings.isEmpty(userObjectId)) {
            authenticationUrl = authenticationUrl + "&existing_user=" + userObjectId;
        }
        System.out.println("AUTHURL: " + authenticationUrl);
        return authenticationUrl;
    }

    public static void linkToUser(final Service service, final Activity activity, final String userObjectId, final Callback<CMSocialLoginResponse> callback) {
        String authURL = getAuthenticationUrl(service, userObjectId);
        AuthenticationDialog dialog = new AuthenticationDialog(activity, service, userObjectId, callback);
        dialog.show();
    }

    public static void linkToUser(final Service service, final Activity activity, final CMUser user, final Callback<CMSocialLoginResponse> callback) {
        linkToUser(service, activity, user.getObjectId(), callback);
    }
}
