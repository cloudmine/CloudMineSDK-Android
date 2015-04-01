# Tutorial

This tutorial will walk you through building a car management app. It will let you organize your cars, search through them and get details about each one.

### Initialize the Library

The tutorial assumes that you already have your development environment set up. If not, you may want to read the [Getting Started](#/android_and_java#starting-a-new-project) section before proceeding.

Let's start by telling the library about our App ID and API key, which are found on [the dashboard](/dashboard).

Open your main Activity class and add the App ID and an API key at the top of the class definition.

```java
// Find this in your developer console
private static final String APP_ID = "....";
// Find this in your developer console
private static final String API_KEY = "...";
```

In `onCreate`, add the line of code to initial the library with your app's credentials

```java
@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
 
    // initialize CloudMine library
    CMApiCredentials.initialize(APP_ID, API_KEY, getApplicationContext());
}
```

### Store Cars

First we need to create our Car class. Cars consist of a make and a model:

```java
import com.cloudmine.api.db.LocallySavableCMObject;
 
public class Car extends LocallySavableCMObject {
    private String make;
    private String model;
 
    //there must be a no-args constructor for deserializing to work
    Car(){}
    public Car(String make, String model) {
        this.make = make;
        this.model = model;
    }
 
    //Your getter and setters determine what gets serialized and what doesn't
    public String getMake() {return make;}
    public void setMake(String make) {this.make = make;}
    public String getModel() {return model;}
    public void setModel(String model) {this.model = model;}
}
```

In the `onCreate` method of your main Activity lets create and store a few cars (to the cloud).

```java
@Override
public void onCreate(Bundle savedInstanceState) {
    // .. initialization code ...
    Car honda= new Car("honda", "civic");
    Car toyota = new Car("toyota", "corolla");
 
    LocallySavableCMObject.saveObjects(MyActivity.this, Arrays.asList(honda, toyota), new Response.Listener<ObjectModificationResponse>() {
        @Override
        public void onResponse(ObjectModificationResponse objectModificationResponse) {
 
        }
    });
}
```

Now if you look at your [dashboard](/dashboard), you'll see the two cars that were saved.

{{note "A unique ID will be generated for each of the saved objects every time the code runs. So if you run the above code multiple times, you'll end up with several hondas and toyotas."}}

### Search for Cars

Now that we've created and saved a few cars, let's see how to get them back from the cloud. To make things interesting, we will only request Hondas to be returned using the search query `[make = "honda"]`.

```java
@Override
public void onCreate(Bundle savedInstanceState) {
    // .. initialization code ...
    // ... car creation and saving code ...
 
    //Creates search query: "[make = \"honda\"]"
    String searchString = SearchQuery.filter("make").equal("honda").searchQuery();
    LocallySavableCMObject.searchObjects(this, searchString, new Response.Listener<CMObjectResponse>() {
        @Override
        public void onResponse(CMObjectResponse objectResponse) {
            List<Car> hondas = objectResponse.getObjects (Car.class);
            //do something
        }
    });
}
```

And that's it. You are now saving and retrieving data from the cloud in a few lines of code!

{{note "The library uses callbacks so that responses are handled correctly and do not block the calling (and most frequently, the UI) thread. Callbacks are run in the original thread that the request was called from while all the networking operations and blocking occurs on a background thread."}}

### Putting it all together

Here's all the code in one complete example.

```java
// MainActivity.java
package com.example.cars;
 
// CloudMine library imports
import com.cloudmine.api.CMApiCredentials;
import com.cloudmine.api.CMObject;
import com.cloudmine.api.rest.callbacks.CMObjectResponseCallback;
import com.cloudmine.api.rest.callbacks.ObjectModificationResponseCallback;
import com.cloudmine.api.db.LocallySavableCMObject;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.cloudmine.api.rest.response.CMObjectResponse;
import com.cloudmine.api.rest.response.ObjectModificationResponse;
 
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
 
public class MainActivity extends Activity {
 
  // Find this in your developer console
  private static final String APP_ID = "...";
  // Find this in your developer console
  private static final String API_KEY = "...";
 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        CMApiCredentials.initialize(APP_ID, API_KEY, getApplicationContext());
 
        // create a honda civic
        Car honda= new Car("honda", "civic");
        Car toyota = new Car("toyota", "corolla");
 
        LocallySavableCMObject.saveObjects(MyActivity.this, Arrays.asList(honda, toyota), new Response.Listener<ObjectModificationResponse>() {
            @Override
            public void onResponse(ObjectModificationResponse objectModificationResponse) {
 
            }
        });
 
        String searchString = SearchQuery.filter(Car.class).and("make").equal("toyota").searchQuery();
        LocallySavableCMObject.searchObjects(MyActivity.this, searchString, new Response.Listener<CMObjectResponse>() {
            @Override
            public void onResponse(CMObjectResponse objectResponse) {
                List<Car> toyotas = objectResponse.getObjects (Car.class);
                //do something
            }
        });
      }
}
```

### Next Steps

Now that you've seen the basics, start coding up your app! The [Android / Java Library](#/android_and_java) Reference has plenty of examples to help you accomplish what you need to using CloudMine.

You can also check out our sample application that makes extensive use of the various CloudMine features [right here](https://github.com/cloudmine/CloudMine-CloudClicker-Android).

Also, don't forget that if you can't find what you're looking for in these docs, you are always welcome to [email CloudMine support](mailto:support@cloudmine.me).

Happy coding!
