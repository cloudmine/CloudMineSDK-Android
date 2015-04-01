# Fetch Files

Unlike objects, files can only be loaded one at a time.

Load file with callback

Use [CacheableCMFile.loadFile](/docs/javadocs/com/cloudmine/api/BaseCacheableCMFile.html#loadFile(Context, java.lang.String, com.cloudmine.api.CMSessionToken, , Response.ErrorListener)) to load a file by its fileId.

```java
CacheableCMFile.loadFile(this, "fileId", new Response.Listener<FileLoadResponse>() {
    @Override
    public void onResponse(FileLoadResponse fileLoadResponse) {
        fileLoadResponse.getFile();
    }
});
```

