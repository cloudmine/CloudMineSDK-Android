# Fetching Shared User Data

Once you've [granted access to an object](#/android_and_java#sharing-user-data) with an access list, other users with permission can fetch the shared object.

If you are requesting the shared object with its ID explicitly, the object will be automatically fetched.

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
