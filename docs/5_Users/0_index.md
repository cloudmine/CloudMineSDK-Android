# USER DATA

Up until this point, we have been focusing on Application level objects. Application objects can be seen and modified by anyone using the application. CloudMine also supports the concept of users, and user level objects. User level objects can only be accessed by a logged in user, and typically only by the user which created the object. The exception to this is a user may explicitly grant specific users access to their data; this is covered in more detail below. For now, lets just start with what users are and how to work with them.

A user consists of a method of logging in and an optional profile. There are two ways for a user to log in; either with an e-mail or a username and password, or through a social service, such as Facebook or Twitter. The former requires that the user be created before they are allowed to log in, while the latter will create the CMUser upon log in if the account does not already exist. A CMUser works just like a CMObject when it comes to serialization, so you may also extend CMUser. This allows you to add additional information, such as the user's birthday or favorite color. The difference between a CMUser and a CMObject is that the e-mail, username, and password fields for a CMUser are not changed by setting it on the user. For this reason, it is not advised to use the save method on CMUser; instead, either create or saveProfile should be used, depending on the desired action. To modify the username, e-mail, or password, make use of changeEmail, changeUsername, and changePassword methods.

{{caution "Note that user object IDs are populated after either the user is created or the user logs in."}}

By default, user objects do not have any profile information, so we'll create a new class that does have profile information.

```java
class ExtendedCMUser extends CMUser {
    private Date birthday;
    /**
     * Need a no-args constructor for proper deserialization
     */
    public ExtendedCMUser() {
        super();
    }
    public ExtendedCMUser(String email, String password, Date date) {
        super(email, password);
        this.birthday = date;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
```

Notice that just like CMObject, we need a no args constructor as well as getters and setters for the fields we would like to expose.
