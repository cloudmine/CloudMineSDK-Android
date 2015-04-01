# User Login with Social Network

{{note 'You will need to configure each social network you plan on using in your app using each network\'s website.'}}

In addition to logging in as a CloudMine user with an email address and password, you can also allow your users to login via a social network such as Facebook or Twitter. This can either be the primary way a user logs into your application, or a user may link their existing CloudMine user account to a social service. Either way, a [CMSessionToken](/docs/javadocs/com/cloudmine/api/CMSessionToken.html) will be returned if the login was successful. The [CMUser](/docs/javadocs/com/cloudmine/api/CMUser.html) is also returned, which contains the user's profile. This includes the [CMSocial.Services](/docs/javadocs/com/cloudmine/api/rest/CMSocial.Service) the user has logged in to.

First let's look at logging in using social, without having an existing `CMUser`. The call to `loginThroughSocial` will pop up a loading dialog, which will begin the authentication process in the background. If the user has already logged into the service, the process will complete and the callback will be called. If they have not, a login web page will be displayed, and once they enter correct credentials the callback will be executed.

{{warning "All of these login methods go through a web view, which may save cookies to the device that will automatically log the user in on any subsequent social login attempts."}}

```java
//'this' is the Activity
CMAndroidSocial.loginThroughSocial(CMSocial.Service.TWITTER, this, new CMSocialLoginResponseCallback() {
    public void onCompletion(CMSocialLoginResponse response) {
        System.out.println("Authenticated to: " + response.getUser().getAuthenticatedServices());
        System.out.println("Logged in? " + response.getSessionToken().isValid());
    }
 
    @Override
    public void onFailure(Throwable t, String msg) {
        System.out.println("Oh no we failed! " + t.getMessage());
    }
});
```

You can link an existing user to a social service. Once this is done, the user can login either through their `CMUser` credentials or through a linked social site. This linking process requires the `CMUser` to be logged in; if the request is made with a `CMUser` who is not logged in, a call to login will be made; if it fails, the social authentication will not occur, and the callback's onFailure method will be invoked.

```java
final CMUser user = new CMUser("test@gmail.com", "whatever");
user.create(this, new Response.Listener<CreationResponse>() {
    public void onResponse(CreationResponse creationResponse) {
        CMAndroidSocial.linkToUser(CMSocial.Service.TWITTER, MyActivity.this, user, new CMSocialLoginResponseCallback() {
            public void onCompletion(CMSocialLoginResponse response) {
                Log.d(TAG,"Authenticated to: " + response.getUser().getAuthenticatedServices());
                Log.d(TAG, "Logged in? " + response.getSessionToken().isValid());
            }
 
            @Override
            public void onFailure(Throwable t, String msg) {
                Log.e(TAG, "Oh no we failed! " + t.getMessage(), t);
            }
        });
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Log.e(TAG,  "Failed creating user", volleyError);
    }
});
```

One last thing - remember that warning about web views and cookies? If a user has logged out, and another user wants to log into the same social service, you need to clear the cookies first. Note that this will clear ALL of the session cookies for the application, not just the social login cookies.

```java
CMAndroidSocial.clearSessionCookies(this);
```
