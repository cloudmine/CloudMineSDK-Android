# Delete User Objects

### Deleting directly from CMObject

Much like save, objects can be deleted by calling delete directly on the object.

```java
//object is a LocallySavableCMObject, owned by a user, that has already been saved
object.delete(this, sessionToken, new Response.Listener<ObjectModificationResponse>() {
    @Override
    public void onResponse(ObjectModificationResponse response) {
         
    }
});
```

### Deleting multiple objects

Objects can also be deleted by passing their objectIds into the [delete](/docs/javadocs/com/cloudmine/api/db/BaseLocallySavableCMObject.html#delete(Context, java.util.Collection, com.cloudmine.api.CMSessionToken, com.cloudmine.api.rest.options.CMServerFunction, , Response.ErrorListener)) method on [LocallySavableCMObject](/docs/javadocs/com/cloudmine/api/db/BaseLocallySavableCMObject.html).

```java
LocallySavableCMObject.delete(this, Arrays.asList(object.getObjectId(), toyota.getObjectId(), honda.getObjectId()), sessionToken,  new Response.Listener<ObjectModificationResponse>() {
    @Override
    public void onResponse(ObjectModificationResponse response) {
         
    }
});
```
