package com.cloudmine.api.gui;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.rest.CMSocial;
import com.cloudmine.api.rest.CompleteSocialLoginRequest;
import com.cloudmine.api.rest.SharedRequestQueueHolders;
import com.cloudmine.api.rest.response.CMSocialLoginResponse;

import java.util.Map;

/**
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class VolleyAuthenticationDialog extends AuthenticationDialog {

    private Response.Listener<CMSocialLoginResponse> successListener;
    private Response.ErrorListener errorListener;

    public VolleyAuthenticationDialog(Context context, CMSocial.Service service, CMSessionToken userSessionToken, Map<String, Object> params, Response.Listener<CMSocialLoginResponse> successListener, Response.ErrorListener errorListener) {
        super(context, service, userSessionToken, params);
        this.successListener = successListener;
        this.errorListener = errorListener;
    }

    @Override
    protected void completeAuthentication(String challenge) {
        RequestQueue queue = SharedRequestQueueHolders.getRequestQueue(getContext());
        CompleteSocialLoginRequest request = new CompleteSocialLoginRequest(challenge, successListener, errorListener);
        queue.add(request);
    }
}
