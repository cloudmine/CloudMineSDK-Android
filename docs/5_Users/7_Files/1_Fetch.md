# Fetch User File

Unlike objects, files can only be loaded one at a time.

### Load file

Use [CacheableCMFile.loadFile](/docs/javadocs/com/cloudmine/api/BaseCacheableCMFile.html#loadFile(Context, java.lang.String, com.cloudmine.api.CMSessionToken, , Response.ErrorListener)) to load a file.

```java
CacheableCMFile.loadFile(this, fileId, sessionToken, new Response.Listener<FileLoadResponse>() {
    @Override
    public void onResponse(FileLoadResponse fileLoadResponse) {
         
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
         
    }
});
```	
