# User Login with Password

Once we have an instance of a user, and that instance has been created server-side, we can log in as that user. This will give us a [CMSessionToken](/docs/javadocs/com/cloudmine/api/CMSessionToken.html) if the e-mail/username and password were correct.

This request clears the password field on the user regardless of whether or not login was successful. A new session token is generated every time a user logs in, and remains valid for up to six months of inactivity.

```java
// instantiate a CMUser instance with an email and password (presumably from the UI)
CMUser user = new CMUser("test@example.com", "the-password");
 
user.login(this, new Response.Listener<LoginResponse>() {
    @Override
    public void onResponse(LoginResponse loginResponse) {
         
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
         
    }
});
```
