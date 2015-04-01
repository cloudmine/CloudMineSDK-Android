# Reset User Password
If a user forgets their password, then can request to have it reset with the [CMUser.resetPassword](/docs/javadocs/com/cloudmine/api/CMUser.html#resetPasswordRequest(com.cloudmine.api.rest.callbacks.Callback)) method. In that case, the user will receive an email with a link to reset the password. The user will be responsible for clicking on the email link and providing the new password.

```java
user.resetPasswordRequest();
```
