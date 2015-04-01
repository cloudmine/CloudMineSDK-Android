# Delete User File

Files are deleted by calling delete on the file object.

This example assumes that a CMFile object named file was previously created.

```java
CacheableCMFile file; // assume this was previously initialized
file.delete(this, sessionToken, new Response.Listener<ObjectModificationResponse>() {
    @Override
    public void onResponse(ObjectModificationResponse response) {
         
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
         
    }
});
```

