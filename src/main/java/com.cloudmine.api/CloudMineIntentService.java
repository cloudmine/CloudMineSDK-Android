package com.cloudmine.api;

import android.content.Context;
import android.util.Log;
import com.cloudmine.api.exceptions.NotLoggedInException;
import com.cloudmine.api.rest.CMWebService;
import com.cloudmine.api.rest.callbacks.CMCallback;
import com.cloudmine.api.rest.callbacks.Callback;
import com.cloudmine.api.rest.callbacks.LoginResponseCallback;
import com.cloudmine.api.rest.callbacks.TokenResponseCallback;
import com.cloudmine.api.rest.response.LoginResponse;
import com.cloudmine.api.rest.response.TokenUpdateResponse;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.android.gcm.GCMRegistrar;
import sun.security.krb5.internal.crypto.CksumType;

/**
 * Created with IntelliJ IDEA.
 * User: ethan
 * Date: 1/15/13
 * Time: 4:19 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class CloudMineIntentService extends GCMBaseIntentService {

    private CMWebService service = null;

    private Callback<TokenUpdateResponse> callback = null;

    public CloudMineIntentService(String senderId) {
        super(senderId);
    }

    /**
     * When implementing Push Notifications you MUST subclass this class, and
     * you MUST call super in your onRegistered method. This sends the token
     * to CloudMine and ensures your device is registered.
     *
     * @param context The context
     * @param s The Token Received from GCM that will be sent to CloudMine.
     */
    @Override
    protected void onRegistered(Context context, String s) {
        Log.i("MY_ACTIVITY", "ON REGISTERED CALLED");
        if (service != null) {
            Log.i("MY_ACTIVITY", "ON REGISTERED CALLED 2");
            service.registerForGCM(s, callback);
        }
    }

    /**
     * When implementing Push Notifications you MUST subclass this class and
     * you MUST c all super in your onUnregistered method. This unRegisters
     * the token from CloudMine, and stops Push Notifications from being
     * sent to the device.
     * @param context
     * @param s
     */
    @Override
    protected void onUnregistered(Context context, String s) {
        if (service != null) {
            service.unregisterForGCM(callback);
        }
    }


    public void registerForGCM(String senderId, Context context) {
        registerForGCM(senderId, context, CMCallback.<TokenUpdateResponse>doNothing() );
    }

    public void registerForGCM(String senderId, Context context, Callback<TokenUpdateResponse> callback) {
        registerForGCM(senderId, context, null, callback);
    }

    public  void registerForGCM(String senderId, Context context, CMUser user) {
        this.registerForGCM(senderId, context, user, CMCallback.<TokenUpdateResponse>doNothing() );
    }

    public  void registerForGCM(final String senderID, final Context context, final CMUser user, final Callback<TokenUpdateResponse> callback) throws UnsupportedOperationException {
        this.callback = callback;

        if (user != null && !user.isLoggedIn()) {
            user.login(new LoginResponseCallback() {
                public void onCompletion(LoginResponse response) {
                    if (response.wasSuccess()) {
                        sendRegisterRequest(senderID, context, user);
                    } else {
                        callback.onFailure(new NotLoggedInException("*** CloudMine - Error: The user is not logged in and cannot register the token!"), "Logging in the use to register the token was unsuccessful!");
                    }
                }

                public void onFailure(Throwable t, String msg) {
                   callback.onFailure(t, msg + "  In Addition, the token was not registered with CloudMine.");
                }
            });
        } else {
            sendRegisterRequest(senderID, context, user);
        }
    }

    public void unRegisterForGCM(Context context) {
        unRegisterForGCM(context, CMCallback.<TokenUpdateResponse>doNothing() );
    }

    public void unRegisterForGCM(Context context, Callback<TokenUpdateResponse> callback) {
        this.callback = callback;
        this.service = CMWebService.getService();
        GCMRegistrar.unregister(context);
    }

    private void sendRegisterRequest(String senderID, Context context, CMUser user) throws UnsupportedOperationException {
        service = (user != null && user.isLoggedIn()) ? CMWebService.getService().setLoggedInUser(user.getSessionToken()) : CMWebService.getService();
        GCMRegistrar.checkDevice(context);
        final String regId = GCMRegistrar.getRegistrationId(context);
        if (regId.equals("")) {
            GCMRegistrar.register(context, senderID);
        } else {
            service.registerForGCM(regId, callback);
        }
    }
}