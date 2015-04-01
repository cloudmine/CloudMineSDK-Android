# USER DATA SECURITY

Ordinarily, only the user who created a user level object has access to it. Obviously, this is a problem for any site that wants to allow users to share information with their 'friends', or do any other kind of restrictive sharing. To help enable this sort of behavior, CloudMine supports the concept of Access Control Lists (ACLs).

ACLs consist of three pieces of information: an owning user, the permissions that are granted by the ACL, and a collection of users who are granted the permissions of the ACL. Every CloudMine object can have any number of ACLs attached to it, which will provide the ACL's users the permissions specified by the ACL on that object. Here is an example of how to create an ACL:

```java
//getLoggedInUser() doesn't exist, but should be self explanatory what it would do
CMUser user = getLoggedInUser();
AccessListController list = new AccessListController(user, CMAccessPermission.READ, CMAccessPermission.UPDATE);
 
//getObjectIdsToGrantAccessTo method doesn't exist, it would probably be populated by searching the user profiles and getting the object ids
Collection<String> userObjectIds = getObjectIdsToGrantAccessTo();
list.grantAccessTo(userObjectIds);
 
//anotherUser is some CMUser that we want to give access to
String anotherUserId = anotherUser.getObjectId();
list.grantAccessTo(anotherUserId);
list.grantPermissions(CMAccessPermission.DELETE);
 
list.save(this, new Response.Listener<CreationResponse>() {
    @Override
    public void onResponse(CreationResponse creationResponse) {
        Log.d(TAG, "We created the ACL!");                   
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
         
    }
});
```

First we create a new ACL, belonging to user, with `READ` and `UPDATE` permissions. Then we grant these permissions to a collection of `userObjectIds`, plus `anotherUser`. Finally, we add the permission to `DELETE` objects and save the ACL. Now that our ACL has been persisted, lets add it to a `CMObject` so that it is shared.

```java
SimpleCMObject anObject = new SimpleCMObject();
anObject.add("key", "value");
anObject.setSaveWith(user);
anObject.grantAccess(list);
anObject.save(); //Note this is still asynchronous, despite no callback being provided
```

Now `anObject` is a user level object that belongs to user and it is shared with `userObjectIds` and `anotherUser`. How do you (as one of those users) actually get access to it? Well, if you load it by `objectId`, it will be returned automatically:

```java
LocallySavableCMObject.loadObject(this, objectId, new Response.Listener<CMObjectResponse>() {
    @Override
    public void onResponse(CMObjectResponse objectResponse) {
 
    }
});
```

However, if you want to load it through a search or by loading all of the user objects, it will not be returned UNLESS you use the [ObjectLoadRequestBuilder](/docs/javadocs/com/cloudmine/api/rest/ObjectLoadRequestBuilder.html#) and call [getShared()](/docs/javadocs/com/cloudmine/api/rest/ObjectLoadRequestBuilder.html#getShared()) when creating your request.

```java
new ObjectLoadRequestBuilder(new Response.Listener<CMObjectResponse>() {
    @Override
    public void onResponse(CMObjectResponse objectResponse) {
         
    }
}).getShared().search(SearchQuery.filter(Car.class).searchQuery()).run(this);
```
