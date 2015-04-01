# Create User

{{note 'If you want to create a user account using a social network such as Facebook, Twitter or Instagram you should head over to [User Login with Social Network](#/android_and_java#user-login-with-social-network) and ignore this section as it does not apply.'}}

You can create a new user by instantiating a new CMUser object and calling [createUser](/docs/javadocs/com/cloudmine/api/CMUser.html#create(Context, , Response.ErrorListener)) on it.

Creating a user with no custom data is a straightforward command.

```java
CMUser user = new CMUser("test@example.com", "the-password");
// OR
user = CMUser.CMUserWithUserName("username", "password");
user.create(this, new Response.Listener<CreationResponse>() {
      @Override
      public void onResponse(CreationResponse creationResponse) {
           
      }
  }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError volleyError) {
           
      }
  });
```
	  
Notice that we avoid the use of the `save()` method.

### Custom user classes

Custom user classes, such as the [ExtendedCMUser](#/android_and_java#user-data) created above, are persisted to the server using the same method shown above.
