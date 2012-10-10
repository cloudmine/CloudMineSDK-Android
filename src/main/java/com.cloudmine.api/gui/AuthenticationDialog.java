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
import com.cloudmine.api.LibrarySpecificClassCreator;
import com.cloudmine.api.rest.AsynchronousHttpClient;
import com.cloudmine.api.rest.callbacks.Callback;
import com.cloudmine.api.rest.callbacks.StringCallback;
import com.cloudmine.api.rest.response.CMSocialLoginResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;

import java.io.UnsupportedEncodingException;

/**
 * A Dialog class that performs the authentication to a service, such as
 * facebook, through a WebView.
 *
 * From: https://raw.github.com/Singly/singly-android/a4cd750ebb255c0cb2879fc69828955569f43cc5/sdk/src/com/singly/android/client/AuthenticationDialog.java
 */
public class AuthenticationDialog
        extends Dialog {

    // components and layouts

    protected static final String SUCCESS_REDIRECT = "https://api.singly.com/oauth/authorize";
    private static final FrameLayout.LayoutParams FILL = new FrameLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

    private ProgressDialog progressDialog;
    private String authUrl;
    private Callback<CMSocialLoginResponse> callback;
    private WebView webView;
    private Context context;

    private class AuthenticationWebViewClient
             extends WebViewClient {

        protected void completeAuthentication(final String url,
                                              final Callback<CMSocialLoginResponse> callback) {
            final AsynchronousHttpClient client = LibrarySpecificClassCreator.getCreator().getAsynchronousHttpClient();

            HttpGet get = new HttpGet(url);
            try {
                client.executeCommand(get, new StringCallback() {
                    @Override
                    public void onCompletion(String messageBody) {
                        String npRedirectUrl = npCreateRedirectUrl(messageBody);
                        if(npRedirectUrl == null) {
                            callback.onFailure(new IllegalStateException("Couldn't get redirect URL"), "Couldn't get redirect URL");
                        }
                        HttpPost post = createRedirectPost(npRedirectUrl);
                        client.executeCommand(post, callback, CMSocialLoginResponse.CONSTRUCTOR);
                    }

                    private HttpPost createRedirectPost(String npRedirectUrl) {
                        HttpPost post = new HttpPost(npRedirectUrl);
                        MultipartEntity entity = new MultipartEntity();
                        ContentBody body = null;
                        try {
                            body = new StringBody("true");
                        } catch (UnsupportedEncodingException e) {
                            callback.onFailure(e, "Unable to encode body");
                        }
                        entity.addPart("allow", body);
                        post.setEntity(entity);
                        return post;
                    }

                    private String npCreateRedirectUrl(String messageBody) {
                        String[] actionSplit = messageBody.split("action=\"");
                        if(actionSplit.length > 1) {
                            String[] endOfInputSplit = actionSplit[1].split("\"><input name");
                            if(endOfInputSplit.length > 0) {
                                return "https://api.singly.com" + endOfInputSplit[0];
                            }
                        }
                        return null;
                    }

                    @Override
                    public void onFailure(Throwable t, String msg) {
                        callback.onFailure(t, msg);
                    }
                }, StringCallback.CONSTRUCTOR);
            } catch(Throwable t) {
                callback.onFailure(t, "Trouble making auth redirect");
            }

        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            System.out.println("Hitting: " + url);
            // on successful authentication we should get the redirect url
            if (needsManualCompletion(url)) {
                AuthenticationDialog.this.dismiss();
                completeAuthentication(url, callback);

                // we handled the url ourselves, don't load the page in the web view
                return true;
            }

            // any other page, load it into the web view
            return false;
        }

        @Override
        public void onReceivedError(WebView view, int errorCode,
                                    String description, String failingUrl) {

            // finish the activity on error
            super.onReceivedError(view, errorCode, description, failingUrl);

            // dismiss any progress dialog
            progressDialog.dismiss();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            System.out.println("Finished: " + url);
            // don't show the web view until the authentication page loaded
            super.onPageFinished(view, url);
            progressDialog.dismiss();
            webView.setVisibility(View.VISIBLE);

            if (needsManualCompletion(url)) {
                AuthenticationDialog.this.dismiss();
                completeAuthentication(url, callback);
            }
        }
    }

    private boolean needsManualCompletion(String url) {
        return url.startsWith(SUCCESS_REDIRECT) && url.contains("authed");
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
        System.out.println("Loading: " + authUrl);
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