# Search for User Objects

CloudMine's search query language gives you more fine-grained control over which objects you fetch. See the [query reference](#/rest_api#overview) for more detailed information.

### Providing a query

You can do more complex searches, such as loading all of the objects that have a top level field of 'name' with the value 'john' using [LocallySavableCMObject.searchObjects](/docs/javadocs/com/cloudmine/api/db/BaseLocallySavableCMObject.html#searchObjects(Context, java.lang.String, com.cloudmine.api.CMSessionToken, com.cloudmine.api.rest.options.CMServerFunction, , Response.ErrorListener)).

```java
LocallySavableCMObject.searchObjects(this, sessionToken, SearchQuery.filter("name").equal("john").searchQuery(), 
        new Response.Listener<CMObjectResponse>() {
    @Override
    public void onResponse(CMObjectResponse response) {
         
    }
});
```

For in-depth documentation and examples, see the [query language reference](#/rest_api#overview).

