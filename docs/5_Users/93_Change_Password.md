# Change User Password

If a user wishes to change their password and still know their current password, you can change it by using the [CMUser.changePassword](/docs/javadocs/com/cloudmine/api/CMUser.html#changePassword(Context, java.lang.String, java.lang.String, , Response.ErrorListener)) method.

If the user does not know their current password, use [Password Reset](#/rest_api#reset-user-password) instead.

```java
user.changePassword(this, "currentPassword", "newPassword", new Response.Listener<CMResponse>() {
    @Override
    public void onResponse(CMResponse cmResponse) {
        user.login(this, new Response.Listener<LoginResponse>() {
            @Override
            public void onResponse(LoginResponse loginResponse) {
                 
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                 
            }
        });                
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
 
    }
});
```
