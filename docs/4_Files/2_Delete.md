# Delete File

Files are deleted by calling delete on the file object, or by calling [CacheableCMFile.delete](/docs/javadocs/com/cloudmine/api/BaseCacheableCMFile.html#delete(Context, java.util.Collection, com.cloudmine.api.CMSessionToken, , Response.ErrorListener)) and passing in the id of the file you wish to delete.

This example assumes that a CMFile object named file was previously created.

```java
CacheableCMFile file; // assume this was previously initialized
file.delete(this, new Response.Listener<ObjectModificationResponse>() {
    @Override
    public void onResponse(ObjectModificationResponse response) {
        Log.d(TAG, "Did we delete it? " + response.wasDeleted(file.getFileId()));    
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
         
    }
});
//Delete it without having a file, just using the fileId
CacheableCMFile.delete(this, file.getFileId(), new Response.Listener<ObjectModificationResponse>() {
    @Override
    public void onResponse(ObjectModificationResponse response) {
        Log.d(TAG, "Did we delete it? " + response.wasDeleted(file.getFileId()));                  
    }
});
```
