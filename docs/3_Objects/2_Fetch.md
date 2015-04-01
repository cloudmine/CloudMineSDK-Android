# Fetch Objects

### Fetching all objects

Fetching all objects stored for an application is easy. The [LocallySavableCMObject.loadAllObjects](/docs/javadocs/com/cloudmine/api/db/BaseLocallySavableCMObject.html#loadAllObjects(Context, com.cloudmine.api.CMSessionToken, com.cloudmine.api.rest.options.CMServerFunction, , Response.ErrorListener)) method returns all application-level objects without any filtering. If you specify a non null session token, all user-level objects will be loadaed instead.

```java
LocallySavableCMObject.loadAllObjects(this, new Response.Listener<CMObjectResponse>() {
    @Override
    public void onResponse(CMObjectResponse response) {
        for(CMObject object : response.getObjects()) {
            //do something
        }
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
         
    }
});
```

On load, this passes all the objects to the provided callback.

### Fetching specific keys

Specific objects can be loaded using by specifying their keys (IDs) by either passing in a single key or a list of keys.

```java
// loads object with key objectId
LocallySavableCMObject.loadObject(this, objectId, new Response.Listener<CMObjectResponse>() {
    @Override
    public void onResponse(CMObjectResponse response) {
      CMObject object = response.getCMObject(objectId);
    }
});
 
// or...
Collection<String> keys = Arrays.asList("object-1", "object-2", "object-3");
// loads objects with keys "object-1", "object-2", "object-3"
LocallySavableCMObject.loadObjects(this, keys, new Response.Listener<CMObjectResponse>() {
    @Override
    public void onResponse(CMObjectResponse response) {
 
    }
});
```

These methods load objects with the requested keys, and pass them into the callback after they have been loaded.
