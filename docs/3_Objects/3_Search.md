# Search for Objects in Application

CloudMine's search query language gives you more fine-grained control over which objects you fetch. See the [query reference](#/rest_api#overview) for more detailed information.

# Building a search query

You can use CloudMine's [SearchQuery](/docs/javadocs/com/cloudmine/api/SearchQuery.html) object to create complex SearchQueries. Some examples:

```java
// Searches for Cars made by Toyota built after 2007
SearchQuery.filter(Car.class).and("model").equal("Toyota").and("year").greaterThan(2007).searchQuery()
// Searches for objects with a home subobject that have geo information stored in home.location, near Philadelphia
SearchQuery.subObject("home").filter("location").near(75.17, 39.95).searchQuery();
```

### Using a query

You can do more searches, such as loading all of the objects that have a top level field of 'name' with the value 'john' using [searchObjects](/docs/javadocs/com/cloudmine/api/db/BaseLocallySavableCMObject.html#searchObjects(Context, java.lang.String, com.cloudmine.api.CMSessionToken, com.cloudmine.api.rest.options.CMServerFunction, , Response.ErrorListener)).

```java
LocallySavableCMObject.searchObjects(this, SearchQuery.filter("name").equal("john").searchQuery(), 
        new Response.Listener<CMObjectResponse>() {
    @Override
    public void onResponse(CMObjectResponse response) {
         
    }
});
```

For in-depth documentation and examples, see the [query language reference](#/rest_api#overview).
