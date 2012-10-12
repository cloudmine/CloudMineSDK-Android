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
            AuthenticationDialog dialog = new AuthenticationDialog(activity, service, callback);
            dialog.show();
        }catch(Exception e) {
            callback.onFailure(e, "");
        }
    }

    public static void clearSocialCookie(Context context, Service... services) {
        CookieSyncManager syncManager = CookieSyncManager.createInstance(context);
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.removeAllCookie();

        for(Service service : services) {
            String url = service.getAuthenticationUrl();
            String cookie = cookieManager.getCookie(url);
            if(hasCookie(cookie)) {
                System.out.println("*****************" + service);
            }
            int isInfinite = 0;
            while(hasCookie(cookie) && isInfinite < 30) {
                System.out.println("OC: " + cookie);
                cookie = "Set-Cookie: " + cookie + EXPIRES;
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


    public static void linkToUser(final Service service, final Activity activity, final String userObjectId, final Callback<CMSocialLoginResponse> callback) {
        AuthenticationDialog dialog = new AuthenticationDialog(activity, service, userObjectId, callback);
        dialog.show();
    }

    public static void linkToUser(final Service service, final Activity activity, final CMUser user, final Callback<CMSocialLoginResponse> callback) {
//        linkToUser(service, activity, user.getObjectId(), callback);
    }
}
