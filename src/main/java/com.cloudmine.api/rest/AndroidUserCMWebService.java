package com.cloudmine.api.rest;

import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.LibrarySpecificClassCreator;
import com.cloudmine.api.rest.callbacks.Callback;
import com.cloudmine.api.rest.callbacks.PaymentResponseCallback;
import com.cloudmine.api.rest.response.PaymentResponse;
import org.apache.http.client.methods.HttpPost;

/**
 * <br>Copyright CloudMine LLC. All rights reserved
 * <br> See LICENSE file included with SDK for details.
 */
public class AndroidUserCMWebService extends UserCMWebService {




    public static AndroidUserCMWebService getService(CMSessionToken token) {
        return getService(CMApiCredentials.getApplicationIdentifier(), CMApiCredentials.getApplicationApiKey(), token);
    }

    public static AndroidUserCMWebService getService(String appId, String apiKey, CMSessionToken token) {
        return new AndroidUserCMWebService(new CMURLBuilder(appId).user(), token, LibrarySpecificClassCreator.getCreator().getAsynchronousHttpClient());
    }


    /**
     * Provides access to a specific users data.
     *
     * @param baseUrl                the base URL to hit; this should be something like https://api.cloudmine.me/v1/app/{appid}/user
     * @param token                  the users token that represents a logged in session; acquire by using CMWebService.login
     * @param asynchronousHttpClient This should probably be the AndroidAsynchronousHttpClient, but you may provide your own implementation of the interface
     */
    AndroidUserCMWebService(CMURLBuilder baseUrl, CMSessionToken token, AsynchronousHttpClient asynchronousHttpClient) {
        super(baseUrl, token, asynchronousHttpClient);
    }



    public void asyncChargeCardRequest(int cardPosition, String cartJson, Callback<PaymentResponse> callback){
        String url = baseUrl.copy().notUser().addAction("payments").addAction("transaction").addAction("charge").asUrlString();

        HttpPost paymentPost = new HttpPost(url);
        addCloudMineHeader(paymentPost);
        addJson(paymentPost, "{ \"cart\":" + cartJson + ", \"paymentInfo\":{\"index\":" + cardPosition + ", \"type\":\"card\"}}");
        executeAsyncCommand(paymentPost, callback, PaymentResponseCallback.CONSTRUCTOR);
    }
}
