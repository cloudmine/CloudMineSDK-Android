# Fetch User Objects

Use the [LocallySavableCMObject.loadObjects](/docs/javadocs/com/cloudmine/api/db/BaseLocallySavableCMObject.html#loadObjects(Context, java.util.Collection, com.cloudmine.api.CMSessionToken, com.cloudmine.api.rest.options.CMServerFunction, , Response.ErrorListener)) method to fetch objects that exist on the server. Prior to loading user objects, a user must be logged in.

### Fetching all objects

If you don't want to fetch specific keys, the simplest way is to use [LocallySavableCMObject.loadAllObjects](/docs/javadocs/com/cloudmine/api/db/BaseLocallySavableCMObject.html#loadAllObjects(Context, com.cloudmine.api.CMSessionToken, com.cloudmine.api.rest.options.CMServerFunction, , Response.ErrorListener)) method, which returns all of the user's data without any filtering.

```java
LocallySavableCMObject.loadAllObjects(this, sessionToken, new Response.Listener<CMObjectResponse>() {
    @Override
    public void onResponse(CMObjectResponse objectResponse) {
         
    }
});
```

On load, this passes all the objects to the provided callback.

### Fetching specific keys

Specific objects can be loaded using by specifying their keys (IDs) by either passing in a single key or a list of keys.

```java
LocallySavableCMObject.loadObject(this, objectId, sessionToken, new Response.Listener<CMObjectResponse>() {
    @Override
    public void onResponse(CMObjectResponse objectResponse) {
         
    }
});
Collection<String> objectIds = Arrays.asList("object-1", "object-2", "object-3");
LocallySavableCMObject.loadObjects(this, objectIds, sessionToken, new Response.Listener<CMObjectResponse>() {
    @Override
    public void onResponse(CMObjectResponse objectResponse) {
 
    }
});
```

These methods load objects with the requested keys, and pass them into the callback after they have been loaded.
