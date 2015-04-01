# APPLICATION DATA

When it comes to modeling your data in CloudMine, you have two options. You can make use of our [SimpleCMObject](/docs/javadocs/com/cloudmine/api/SimpleCMObject.html) class, which lets you quickly store data without having to create your own class, or you can create a class which extends from [LocallySavableCMObject](/docs/javadocs/com/cloudmine/api/db/BaseLocallySavableCMObject.html).

### SimpleCMObject

Add values by calling the `add` method, and specifying a key (which must be a String), and a value (which can be of any type). Several methods exist for extracting the data, depending on the desired type.

```java
SimpleCMObject o = new SimpleCMObject();
o.add("name", "John");
 
o.get("name");  //Returns an Object
o.getString("name"); //Returns a String
o.getValue("name", String.class); //Returns a String
```

If the key does not exist, or if it does but is of a different type, these methods will return null.

### LocallySavableCMObject

This approach is more powerful and saves you from having to add and extract fields manually. You start by defining a class that extends from LocallySavableCMObject. You must provide a no args constructor for this class, which is used for deserialization. Any property you want serialized must have associated `get` and `set` methods. The name of the property will be determined using these method names (for booleans, the get method can be replaced by an `is` method).

CloudMine objects are deserialized to a specific class based on their __class__ property. The Java SDK uses `getClass().getName()` as the default. If you are only developing an application for a single platform, you can ignore this value. However, if you already have objects that were persisted using something besides Java, they may be using a different __class__ naming scheme. To enable interoperability, you must do two things: first, override `getClassName()` in your CMObject to return the constant __class__ value that matches the existing objects.

```java
import com.cloudmine.api.CMObject;
 
public class MyObject extends LocallySavableCMObject {
    public static final String CLASS_NAME = "MyObject";
    private String name;
 
    // A no-args constructor is required for deserialization
    public MyObject() {
        super();
    }
    public MyObject(String name) {
        this();
        this.name = name;
    }
 
    // This changes the __class__ naming scheme, for interoperability
    // with existing data from another platform such as iOS
    @Override
    public String getClassName() {
        return CLASS_NAME;
    }
 
    // Getters and Setters are required for each serialized field
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
```

Second, in a static block in your main Application class you must register the new name. You need to add a call to ClassNameRegistry inside our main Activity (really it doesn't matter where you put this call, but it must be ran before any serialization or deserialization occurs, so the main Activity is a good place).

```java
static {
    ClassNameRegistry.register(MyObject.CLASS_NAME, MyObject.class);
}
```

Now that you know how to make your models, let's look at doing something with them.
