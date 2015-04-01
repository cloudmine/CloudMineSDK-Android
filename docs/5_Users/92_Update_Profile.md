# Update User Profiles

When using a custom user class, fields can be added which will be persisted as that user's "profile". The main purpose of this profile is discoverability. Using this profile, users of your app can find other users to share their data with.

It's important to remember that profile information is public. For this reason, by default, we don't save any fields on the user's profile except for their unique identifier.

To add fields to a user's profile, first create a custom user class.

```java
class ExtendedCMUser extends CMUser {
  private Date birthday;
  private String name;
  /**
   * Need a no-args constructor for proper deserialization
   */
  public ExtendedCMUser() {
      super();
  }
  public ExtendedCMUser(String email, String password, Date date, String name) {
      super(email, password);
      this.birthday = date;
      this.name = name;
  }
  public Date getBirthday() {
      return birthday;
  }
  public void setBirthday(Date birthday) {
      this.birthday = birthday;
  }
  public String getName() {
      return name;
  }
  public void setName(String name) {
      this.name = name;
  }
}
```

To save updates to fields on the user object, use the [CMUser.saveProfile](/docs/javadocs/com/cloudmine/api/CMUser.html#saveProfile(Context, , Response.ErrorListener)) method.

```java
user.saveProfile(this, new Response.Listener<CreationResponse>() {
    @Override
    public void onResponse(CreationResponse creationResponse) {
         
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
         
    }
});
```

