package com.cloudmine.api.gui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import com.cloudmine.api.CMSessionToken;
import com.cloudmine.api.exceptions.NetworkException;
import com.cloudmine.api.rest.CMSocial;
import com.cloudmine.api.rest.CMURLBuilder;
import com.cloudmine.api.rest.CMWebService;
import com.cloudmine.api.rest.callbacks.Callback;
import com.cloudmine.api.rest.response.CMSocialLoginResponse;

import java.util.Map;
import java.util.UUID;

/**
 * A Dialog class that performs the authentication to a service, such as
 * facebook, through a WebView.
 *
 * From: https://raw.github.com/Singly/singly-android/a4cd750ebb255c0cb2879fc69828955569f43cc5/sdk/src/com/singly/android/client/AuthenticationDialog.java
 */
public class AuthenticationDialog
        extends Dialog {

    // components and layouts
    private static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    private ProgressDialog progressDialog;
    private String authUrl;
    private Callback<CMSocialLoginResponse> callback;
    private WebView webView;
    private Context context;
    private String challenge;

    private class AuthenticationWebViewClient
             extends WebViewClient {

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {

            // finish the activity on error
            super.onReceivedError(view, errorCode, description, failingUrl);
            callback.onFailure(new NetworkException("Webview received errorcode: " + errorCode + " with description: " + description + " while loading URL: " + failingUrl), failingUrl);
            // dismiss any progress dialog
            progressDialog.dismiss();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            // don't show the web view until the authentication page loaded
            super.onPageFinished(view, url);
            progressDialog.dismiss();
            webView.setVisibility(View.VISIBLE);

            if (needsManualCompletion(url)) {
                AuthenticationDialog.this.dismiss();
                completeAuthentication(challenge);
            }
        }
    }
    protected void completeAuthentication(String challenge) {
        try {
            CMWebService.getService().asyncCompleteSocialLogin(challenge, callback);
        } catch(Throwable t) {
            failureCallback(t, "Trouble making auth redirect");
        }
    }

    protected void failureCallback(Throwable throwable,  String msg) {
        callback.onFailure(throwable, msg);
    }

    private boolean needsManualCompletion(String url) {
        return url.startsWith(CMURLBuilder.CLOUD_MINE_URL) && url.contains("challenge=");
    }

    private class AuthenticationWebChromeClient
            extends WebChromeClient {
        public void onProgressChanged(WebView view, int progress) {
            progressDialog.setProgress(progress);
        }
    }

    public AuthenticationDialog(Context context, String authUrl,
                                Callback<CMSocialLoginResponse> callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.context = context;
        this.authUrl = authUrl;
        this.callback = callback;
    }
    public AuthenticationDialog(Context context, CMSocial.Service service, CMSessionToken userSessionToken,
                                Callback<CMSocialLoginResponse> callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.authUrl = getAuthenticationUrl(service, userSessionToken.getSessionToken());
        this.callback = callback;
    }
    public AuthenticationDialog(Context context, CMSocial.Service service,
                                Callback<CMSocialLoginResponse> callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.authUrl = getAuthenticationUrl(service);
        this.callback = callback;
    }

    public AuthenticationDialog(Context context, CMSocial.Service service, Map<String, Object> params, Callback<CMSocialLoginResponse> callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.authUrl = getAuthenticationUrl(service, null, params);
        this.callback = callback;
    }

    public AuthenticationDialog(Context context, CMSocial.Service service, CMSessionToken userSessionToken, Map<String, Object> params, Callback<CMSocialLoginResponse> callback) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.authUrl = getAuthenticationUrl(service, userSessionToken.getSessionToken(), params);
        this.callback = callback;
    }

    AuthenticationDialog(Context context, CMSocial.Service service, CMSessionToken userSessionToken, Map<String, Object> params) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.context = context;
        this.authUrl = getAuthenticationUrl(service, userSessionToken == null ? null : userSessionToken.getSessionToken(), params);
    }

    private String getAuthenticationUrl(CMSocial.Service service) {
        return getAuthenticationUrl(service, null);
    }

    private String getAuthenticationUrl(CMSocial.Service service, String userSessionToken) {
        return getAuthenticationUrl(service, userSessionToken, null);
    }

    private String getAuthenticationUrl(CMSocial.Service service, String userSessionToken, Map<String, Object> params) {
        challenge = UUID.randomUUID().toString();
        CMURLBuilder url = new CMURLBuilder().account().social().login().service(service).apikey().challenge(challenge).sessionToken(userSessionToken);
        if (params != null && !params.isEmpty()) {
            url = url.mapToQuery(params);
        }
        return url.asUrlString();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // no window title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        // the main web view container layout
        LinearLayout mainLayout = new LinearLayout(getContext());
        mainLayout.setPadding(0, 0, 0, 0);


        // progress dialog for loading web page
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading Authentication");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setProgress(0); // set percentage completed to 0%

        // the web view with the oauth web page
        webView = new WebView(getContext());
        webView.setVisibility(View.INVISIBLE);
        webView.setVerticalScrollBarEnabled(false);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setWebViewClient(new AuthenticationWebViewClient());
        webView.setWebChromeClient(new AuthenticationWebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setLayoutParams(FILL);
        webView.getSettings().setSavePassword(false);
        webView.loadUrl(authUrl);

        // add the web view to the main layout
        mainLayout.addView(webView);

        // set the main layout as the content view
        setContentView(mainLayout, new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT));
        // show the progress dialog
        progressDialog.show();
    }

    @Override
    public void cancel() {
        super.cancel();
        callback.onFailure(new IllegalStateException("Was cancelled!"), "Authentication was cancelled");
    }

}
