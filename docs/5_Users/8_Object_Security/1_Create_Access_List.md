# Create Access List

Create a new access list by instantiating a [AccessListController](/docs/javadocs/com/cloudmine/api/AccessListController.html) You must have a previously created user to create a new access list.

Permissions are controlled by adding [CMAccessPermissions](/docs/javadocs/com/cloudmine/api/CMAccessPermission.html) to the access list object. This gives you the option to allow create, read, update, or delete permissions.

Add users to this access list by using the [CMAccessList.grantAccessTo](/docs/javadocs/com/cloudmine/api/CMAccessList.html#grantAccessTo(com.cloudmine.api.CMUser)) method.

```java
AccessListController list = new AccessListController(user, CMAccessPermission.READ, CMAccessPermission.UPDATE);
 
// getObjectIdsToGrantAccessTo method doesn't exist, it would probably be populated by searching the user profiles and getting the object ids
Collection<String> userObjectIds = getObjectIdsToGrantAccessTo();
list.grantAccessTo(userObjectIds);
 
// anotherUser is a different CMUser that we want to give access to
CMUser anotherUser;
 
list.grantAccessTo(anotherUser);
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
