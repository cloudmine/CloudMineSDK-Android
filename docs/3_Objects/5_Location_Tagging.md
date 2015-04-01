# Geolocation Tagging and Searching

### Specifying a location field

CloudMine supports attaching location information to objects. To add location information to an object, instantiate a new [CMGeoPoint](/docs/javadocs/com/cloudmine/api/CMGeoPoint.html), then add it as a field to any CMObject.

```java
SimpleCMObject o = new SimpleCMObject();
 
CMGeoPoint location = new CMGeoPoint(55, 50); // lon, lat
o.add("location", location);
o.save();
```

Adding a location field to a `CMObject` is straightforward as well. Just add a getter and a setter for it.

```java
import com.cloudmine.api.db.LocallySavableCMObject;
import com.cloudmine.api.CMGeoPoint;
 
public class MyObject extends LocallySavableCMObject {
    private CMGeoPoint location;
 
    // A no-args constructor is required for deserialization
    public MyObject() { super(); }
 
    // Getters and Setters for location field
    public CMGeoPoint getLocation() {
        return location;
    }
    public void setLocation(CMGeoPoint location) {
        this.location = location;
    }
}
```

### Geo search

Once objects have a location field, you can construct a [search query](#/rest_api#overview) which will order objects by location.

```java
LocallySavableCMObject.searchObjects(this, SearchQuery.filter("location").near(37.28, 75.90).searchQuery(), 
        new Response.Listener<CMObjectResponse>() {
    @Override
    public void onResponse(CMObjectResponse response) {
         
    }
});
```

This will return all objects with a location field that contains geo information and match the supplied query. The objects will be returned in order based on their distance from the specified point. You can also modify the search to only return objects within a specific radius, for example:

```java
SearchQuery.filter("location").near(75.17, 39.95).within(5, DistanceUnits.km).searchQuery();
```

