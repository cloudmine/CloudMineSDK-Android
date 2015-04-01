#CUSTOM SERVER CODE

## Code Snippet Usage

Custom code execution allows you to write custom JavaScript code snippets that run on our servers to perform processing and post-processing operations that are inappropriate to run on a mobile device or to offload certain business logic to the server. Your code runs in a sandboxed server-side JavaScript environment that has access to the CloudMine [JavaScript API](http://github.com/cloudmine/cloudmine-js) and a simple HTTP client. All snippets are killed after 30 seconds of execution.

In addition to JavaScript, you can also write custom Java applications. For more information on implementing this, see the [Java application documentation](#/android_and_java).

Server-side snippets are invoked in Java by creating an instance of [CMServerFunction](/docs/javadocs/com/cloudmine/api/rest/options/CMServerFunction.html). This indicates the name of the snippet and parameters to pass to it.

```java
LocallySavableCMObject.loadObject(this, objectId, function, new Response.Listener<CMObjectResponse>() {
    @Override
    public void onResponse(CMObjectResponse objectResponse) {
      // get the object we requested
      CMObject obj = response.getCMObject("object-id");
 
      // get the snippet results
      Object results = response.getResults();
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
         
    }
});
```

## Code Snippet Options

[CMServerFunction](/docs/javadocs/com/cloudmine/api/rest/options/CMServerFunction.html) has several options to control code snippet execution.

### CMServerFunction options

* boolean resultOnly 
  * Only include the results of the snippet call in the response.

* boolean async 
  * Don't wait for the snippet execution to complete before returning.

* NSDictionary *extraParameters 
  * These will be passed into the snippet as parameters.
