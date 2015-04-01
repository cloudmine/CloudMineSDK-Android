# Push Notifications

CloudMine supports receiving push notifications from the Google Cloud Messaging service (GCM). Before sending push notifications, you'll need to register your app with Google, as described [here](#/push_notifications#app-registration). Once your app is configured, you'll need to add code to your app that registers the device with Google. Google goes over this [here](http://developer.android.com/google/gcm/gs.html#android-app).

```java
GCMRegistrar.checkDevice(this);
GCMRegistrar.checkManifest(this);
final String regId = GCMRegistrar.getRegistrationId(this);
if (regId.equals("")) {
  GCMRegistrar.register(this, SENDER_ID);
} else {
  // Already Registered, sent regId to CloudMine, using the call below.
}
```

Device Registration

Once you have made the call to Google to register the device, you'll get a callback in the `onRegistered()` method of your GCMIntentService. In this method you'll need to send the token to CloudMine.

```java
@Override
protected void onRegistered(Context context, String regId) {
 
    // see note below - use UserCMWebService to associate with a user
    CMWebService service = CMWebService.getService();
 
    service.registerForGCM(regId, new TokenResponseCallback() {
 
        public void onCompletion(TokenUpdateResponse response) {
            if (response.wasSuccess()) {
                Log.i(TAG, "The registration was successful!");
            } else {
                Log.i(TAG, "There was an error!");
            }
        }
 
    });
}
```

If the registration was successful, your device is now ready to recieve push notifications.

{{note "When registering, CloudMine can associate a device with a user. If you want to make this association, make sure you use an instance of UserCMWebService. If you send up the token without a user, future calls to register this device will attempt to associate a user with the device."}}

### Unregistering

If you no longer want this device to receive push notifications, use the `unregisterForGCM(Callback callback)` method on `CMWebService`.

```java
@Override
protected void onUnregistered(Context context, String s) {
 
    CMWebService service = CMWebService.getService();
 
    service.unregisterForGCM(new TokenResponseCallback() {
 
        public void onCompletion(TokenUpdateResponse response) {
            if (response.wasSuccess()) {
                Log.i(TAG, "The un-registration was successful!");
            } else {
                Log.i(TAG, "There was an error!");
            }
        }
 
    });
}
```

After this, you will no longer recieve push notifications.
