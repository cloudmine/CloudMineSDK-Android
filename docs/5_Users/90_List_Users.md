# List All Users

Each user has a public profile associated with their account. These can be retrieved using the [CMUser.loadAllUserProfiles](/docs/javadocs/com/cloudmine/api/CMUser.html#loadAllUserProfiles(Context, , Response.ErrorListener)) method.

```java
CMUser.loadAllUserProfiles(this, new Response.Listener<CMObjectResponse>() {
    @Override
    public void onResponse(CMObjectResponse objectResponse) {
      for(CMObject obj : loadResponse.getObjects()) {
          CMUser user = (CMUser) obj; // all objects in this response will be CMUser
      }
         
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
         
    }
});
```
