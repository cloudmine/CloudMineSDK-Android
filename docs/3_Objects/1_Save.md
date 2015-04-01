# Save Objects

Individual [LocallySavableCMObjects](/docs/javadocs/com/cloudmine/api/db/BaseLocallySavableCMObject.html) can be saved by calling save directly on the object.

### Single object

```java
Car car = new Car("honda", "civic");
car.save(this, new Response.Listener<ObjectModificationResponse>() {
    @Override
    public void onResponse(ObjectModificationResponse modificationResponse) {
        Log.d(TAG, "Honda was saved: " + modificationResponse.getCreatedObjectIds());
    }
}, new Response.ErrorListener() {
    @Override
    public void onErrorResponse(VolleyError volleyError) {
        Log.e(TAG, "Failed saving car", volleyError);
    }
});
```

### Multiple objects

If you'd like to save multiple objects using one method call, you need to make use of the [method](/docs/javadocs/com/cloudmine/api/db/BaseLocallySavableCMObject.html#saveObjects(Context)).

```java
Car honda= new Car("honda", "civic");
Car toyota = new Car("toyota", "corolla");
 
LocallySavableCMObject.saveObjects(MyActivity.this, Arrays.asList(honda, toyota), new Response.Listener<ObjectModificationResponse>() {
    @Override
    public void onResponse(ObjectModificationResponse objectModificationResponse) {
 
    }
});
```
