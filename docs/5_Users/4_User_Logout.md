# User Logout
Once a user has a valid session token, it can be invalidated by logging the user out.

```java
// assume this user exists and has been logged in
user.logout(this, new Response.Listener<CMResponse>() {
      @Override
      public void onResponse(CMResponse cmResponse) {
          Log.d(TAG, "Session token invalidated? " + cmResponse.wasSuccess());
      }
  }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError volleyError) {
           
      }
  });
```
