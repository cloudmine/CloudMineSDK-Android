# Sharing User Data

To share data between different users, we need to grant a [LocallySavableCMObject](/docs/javadocs/com/cloudmine/api/db/BaseLocallySavableCMObject.html) access to a [AccessListController](/docs/javadocs/com/cloudmine/api/AccessListController.html) This is done using the [grantAccess](/docs/javadocs/com/cloudmine/api/CMObject.html#grantAccess(com.cloudmine.api.CMAccessList)) method.

This assumes that we've already created an [AccessListController](#/android_and_java#create-access-list).

```java
// assume this access list has been previously created
AccessListController list;
 
// this user owns the access list
CMUser user;
 
// create a new object
SimpleCMObject anObject = new SimpleCMObject();
anObject.add("key", "value");
 
// make sure we save as the owner
anObject.setSaveWith(user);
// grant access to the access list
anObject.grantAccess(list);
anObject.save(); // Note this is still asynchronous, despite no callback being provided
```

Once the access list has been attached to an object, members of that list will have the permissions enumerated in the access list.
