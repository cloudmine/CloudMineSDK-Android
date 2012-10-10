package com.cloudmine.api;

import android.app.Activity;
import com.cloudmine.api.gui.AuthenticationDialog;
import com.cloudmine.api.rest.CMSocial;
import com.cloudmine.api.rest.callbacks.Callback;
import com.cloudmine.api.rest.response.CMObjectResponse;

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
    public void authorize(final Service service, Activity activity, final Callback<CMObjectResponse> callback) {
        try {
            String authURL = "http://api-beta.cloudmine.me/v1/app/" + CMApiCredentials.getApplicationIdentifier() +  "/account/loginSocial?service=" + service.asUrlString() + "&apikey=" + CMApiCredentials.getApplicationApiKey();
            AuthenticationDialog dialog = new AuthenticationDialog(activity, authURL, callback);
            dialog.show();
        }catch(Exception e) {
            callback.onFailure(e, "");
        }
    }
}
