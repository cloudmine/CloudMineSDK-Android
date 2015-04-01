# Download the Android Library

We have an Android Library available for you to download to make native development on Android even easier.


### Download Android Library
[v.0.6.5 (3.6mb, September 29th, 2014)](http://staticweb.cloudmine.me/cloudmine-android-v0.6.5.zip)
 

### Download Java Library
[v.0.6.3 (1.3mb, August 11th, 2014)](http://staticweb.cloudmine.me/cloudmine-java-v0.6.3.zip)
 

Check out the [Android Library documentation](/docs/javadocs/index.html).

The Library is open-source and under the MIT license.

Maven

You can also add the Android library as a Maven repository. This requires you to add the CloudMine repository to your pom.xml file

```xml
<repositories>
    <repository>
        <id>cloudmine-releases</id>
        <url>https://github.com/cloudmine/maven/raw/master/releases</url>
    </repository>
</repositories>
```

And then in your dependencies section, you need to add

```xml
<dependencies>
  <dependency>
      <groupId>com.cloudmine.api</groupId>
      <artifactId>cloudmine-android</artifactId>
      <version>0.6.5</version>
  </dependency>
</dependencies>
```

### Github

We're actively developing and invite you to fork and send pull requests on GitHub.

* [cloudmine/cloudmine-android](https://github.com/cloudmine/cloudmine-android)

* [cloudmine/cloudmine-java](https://github.com/cloudmine/cloudmine-javasdk)

Before you can begin using the Android / Java Library, you must first [create an application](/dashboard/app/create) in the CloudMine dashboard.
