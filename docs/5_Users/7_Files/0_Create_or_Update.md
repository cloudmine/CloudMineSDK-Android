# Upload New File to Application

In addition to object data, you are also able to store files in CloudMine. A [CacheableCMFile](/docs/javadocs/com/cloudmine/api/BaseCacheableCMFile.html) consists of the file contents, a file id, and an optional MIME content type. If no content type is specified, a default of "application/octet-stream" is used.

```java
File myFile; // assume that this is initialized to an existing file
CacheableCMFile newFile = new CacheableCMFile(new FileInputStream(myFile));
newFile.save(this, sessionToken, new Response.Listener<FileCreationResponse>() {
    @Override
    public void onResponse(FileCreationResponse fileCreationResponse) {
         
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
         
    }
});
```

{{warning "If the file id given already exists on the server, the existing file will be overwritten."}}

