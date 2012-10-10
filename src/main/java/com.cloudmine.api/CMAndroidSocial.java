package com.cloudmine.api;

import android.app.Activity;
import com.cloudmine.api.gui.AuthenticationDialog;
import com.cloudmine.api.rest.CMSocial;
import com.cloudmine.api.rest.callbacks.Callback;
import com.cloudmine.api.rest.response.CMSocialLoginResponse;

/**
 * Social that requires an activity to authorize, so that the user can log in through a GUI
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class CMAndroidSocial extends CMSocial {


    /**
     * Switch from the current Activity to the log in page for the given service. On completion, the current Activity
     * will be shown. The OAuth token will be passed into the given callback
     * @param service
     * @param activity
     * @param callback
     */
    public static void authorize(final Service service, Activity activity, final Callback<CMSocialLoginResponse> callback) {
        try {
            String authURL = getAuthenticationUrl(service);
            AuthenticationDialog dialog = new AuthenticationDialog(activity, authURL, callback);
            dialog.show();
        }catch(Exception e) {
            callback.onFailure(e, "");
        }
    }

    private static String getAuthenticationUrl(Service service) {
        return getAuthenticationUrl(service, null);
    }

    private static String getAuthenticationUrl(Service service, String userObjectId) {
        String authenticationUrl = "http://api-beta.cloudmine.me/v1/app/" + CMApiCredentials.getApplicationIdentifier() + "/account/loginSocial?service=" + service.asUrlString() + "&apikey=" + CMApiCredentials.getApplicationApiKey();
        if(userObjectId != null) {
            authenticationUrl = authenticationUrl + "&existing_user=" + userObjectId;
        }
        return authenticationUrl;
    }

    public static void linkToUser(final Service service, final Activity activity, final String userObjectId, final Callback<CMSocialLoginResponse> callback) {
        String authURL = getAuthenticationUrl(service, userObjectId);
        AuthenticationDialog dialog = new AuthenticationDialog(activity, authURL, callback);
        dialog.show();
    }

    public static void linkToUser(final Service service, final Activity activity, final CMUser user, final Callback<CMSocialLoginResponse> callback) {
        linkToUser(service, activity, user.getObjectId(), callback);
    }
}
