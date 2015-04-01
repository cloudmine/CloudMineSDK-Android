# Starting a New Project

After you've downloaded the Library, follow these steps to include it in your Android dev environment.

### Eclipse

(This assumes you already have Google's Android Library and Eclipse both installed, and the ADT Eclipse plug in installed.)

1. Go to File -> New -> Android Application Project.
2. Enter a project name. Under Minimum Required Library, select API 10 (2.3.3).
3. Enter your package name and click Finish to complete the setup.
4. In Project Explorer, right click your project and select "Properties."
5. Select Java Build Path, then select Libraries.
6. Select Add External Jars and add the CloudMine android Library jar
7. Select the Order and Export tab and check the checkbox next to the cloudmine-android jar

### IntelliJ

(This assumes you already have Google's Android Library and IntelliJ both installed.)

1. Go to File -> New Project
2. Select Create Project from Scratch and click next
3. Enter your project name, module name, and select Android Module and hit Next
4. Keep hitting next until the project is created. If intelliJ asks for you to identify the Android Library Module, locate the root folder on your filesystem that you unzipped from the download online.
5. Right click your module and select "Open Module Settings"
6. Select Project Settings -> Modules -> Your Module -> Dependencies
7. Click (+) Add, Jars or Directories, and select the CloudMine android Library jar, and click OK

### Eclipse and IntelliJ

8. Open your AndroidManifest.xml add the following below the <manifest> tag:

```xml
<uses-permission android:name="android.permission.INTERNET"/>
```

9. Open your main Activity class and add

```java
// Find this in your developer console
private static final String APP_ID = "c1a....";
// Find this in your developer console
private static final String API_KEY = "3fc...";
```

at the top of the class definition.

10. In onCreate, add the line of code to initial the library with your app's credentials. This code must be ran before any calls to CloudMine occur; because of this, you may want to add it to your Application class if you have one, or make all of your Activities extend a single parent Activity, and put the initialization code in that Activitie's onCreate.

```java
// This will initialize your credentials
CMApiCredentials.initialize(APP_ID, API_KEY, getApplicationContext());
```

11. Enjoy developing without worrying about the backend
