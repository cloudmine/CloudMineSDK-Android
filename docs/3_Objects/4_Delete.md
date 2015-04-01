# Delete Objects

### Deleting directly from a CMObject

Much like `save`, objects can be deleted by calling `delete` directly on the object.

```java
//object is a CMObject that has already been saved
object.delete(this, new Response.Listener<ObjectModificationResponse>() {
    @Override
    public void onResponse(ObjectModificationResponse objectModificationResponse) {
         
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
         
    }
});
```

### Deleting multiple objects

Multiple objects can also be deleted by passing their objectIds into the [LocallySavableObject.delete](/docs/javadocs/com/cloudmine/api/db/BaseLocallySavableCMObject.html#delete(Context, java.util.Collection, com.cloudmine.api.CMSessionToken, com.cloudmine.api.rest.options.CMServerFunction, , Response.ErrorListener) method.

```java
//objectIds is a Collection of Strings containing objectIds to delete
LocallySavableCMObject.delete(this, objectIds, new Response.Listener<ObjectModificationResponse>() {
    @Override
    public void onResponse(ObjectModificationResponse objectModificationResponse) {
         
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
         
    }
});
```
