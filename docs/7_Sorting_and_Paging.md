# Sorting and Paging

## Sorting

By default, loaded objects are returned in an undefined (and often inconsistent) order. If you care about the order of returned results, you can specify a field to sort by using [ObjectLoadRequestBuilder.sortBy](/docs/javadocs/com/cloudmine/api/rest/ObjectLoadRequestBuilder.html) method

```java
// create a new instance of ObjectLoadRequestBuilder, sort by name, and run the request
new ObjectLoadRequestBuilder(new Response.Listener<CMObjectResponse>() {
    @Override
    public void onResponse(CMObjectResponse objectResponse) {
 
    }
}).sortBy("name", CMSortOptions.SortDirection.ASCENDING).run(this);
```

## Paging

Paging is used to control the number of results returned from each request. Use the [ObjectLoadRequestBuilder.limit](/docs/javadocs/com/cloudmine/api/rest/ObjectLoadRequestBuilder.html#limit(int)) and [ObjectLoadRequestBuilder.startAt](/docs/javadocs/com/cloudmine/api/rest/ObjectLoadRequestBuilder.html#startAt(int)) methods to specify paging options. Make sure to specify [ObjectLoadRequestBuilder.getCount](/docs/javadocs/com/cloudmine/api/rest/ObjectLoadRequestBuilder.html#startAt(int)) in order to get the total number of objects. Then in the response listener, you may use [response.getCount()](/docs/javadocs/com/cloudmine/api/rest/response/CMObjectResponse.html#getCount()) to get the total count of matched objects on the server.

```java
new ObjectLoadRequestBuilder(new Response.Listener<CMObjectResponse>() {
    @Override
    public void onResponse(CMObjectResponse objectResponse) {
      objectResponse.getCount(); //total numbef or objects on server
    }
}).limit(10).startAt(30).getCount().run(this);
```
