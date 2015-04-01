# Save User Objects

Individual [LocallySavableCMObjects](/docs/javadocs/com/cloudmine/api/BaseLocallySavableCMObject.html) can be saved by calling [save](/docs/javadocs/com/cloudmine/api/db/BaseLocallySavableCMObject.html#save(Context, com.cloudmine.api.CMSessionToken, , Response.ErrorListener)) directly on the object and passing in a valid sessionToken.

### Single object

```java
// assume this user has been created and logged in
CMSessionToken sessionToken = user.getSessionToken();
LocallySavableCMObject car = new Car("Toyota", "Camry");
car.save(this, sessionToken, new Response.Listener<ObjectModificationResponse>() {
    @Override
    public void onResponse(ObjectModificationResponse response) {
        Log.d(TAG, "Car was saved? " + response.wasModified(car.getObjectId()));     
    }
});
```

### Multiple objects

If you'd like to save multiple objects using one method call, you need to make use of the [LocallySavableCMObject.saveObjects](/docs/javadocs/com/cloudmine/api/db/BaseLocallySavableCMObject.html#saveObjects(Context, java.util.Collection, com.cloudmine.api.CMSessionToken, com.cloudmine.api.rest.options.CMServerFunction, , Response.ErrorListener)) method with a sessionToken.

```java
CMUser user;  // assume this user has been created and logged in
Car honda= new Car("honda", "civic");
Car toyota = new Car("toyota", "corolla");
 
LocallySavableCMObject.saveObjects(MyActivity.this, Arrays.asList(honda, toyota), user.getSessionToken(), new Response.Listener<ObjectModificationResponse>() {
    @Override
    public void onResponse(ObjectModificationResponse objectModificationResponse) {
 
    }
});
```
