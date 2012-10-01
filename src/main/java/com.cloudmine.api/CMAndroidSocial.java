package com.cloudmine.api;

import android.app.Activity;
import com.cloudmine.api.rest.CMSocial;
import com.cloudmine.api.rest.callbacks.Callback;

/**
 * Social that requires an activity to authorize, so that the user can log in through a GUI
 * <br>
 * Copyright CloudMine LLC. All rights reserved<br>
 * See LICENSE file included with SDK for details.
 */
public class CMAndroidSocial extends CMSocial {
    private final Singly singly = new Singly();



    /**
     * Switch from the current Activity to the log in page for the given service. On completion, the current Activity
     * will be shown. The OAuth token will be passed into the given callback
     * @param service
     * @param activity
     * @param callback
     */
    public void authorize(final Service service, Activity activity, final Callback<String> callback) {
        try {
            singly.authorize(activity,  service.asUrlString(), new Callback<String>() {
                @Override
                public void onCompletion(String response) {
                    storeToken(service, response);
                    callback.onCompletion(response);
                }

                @Override
                public void onFailure(Throwable error, String message) {
                    callback.onFailure(error, message);
                }

                @Override
                public void setStartTime(long startTime) {
                    callback.setStartTime(startTime);
                }

                @Override
                public long getStartTime() {
                    return callback.getStartTime();
                }
            });
        }catch(Exception e) {
            callback.onFailure(e, "");
        }
    }
}
