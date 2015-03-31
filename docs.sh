#!/bin/bash


CLOUDMINE_JAVASDK_VERSION=0.6.3
CLOUDMINE_ANDROID_VERSION=0.6.3
ANDROID_HOME=.
JAVA_SDK_HOME=./cloudmine-javasdk
DEPLOY_DIR="cloudmine-android-v$CLOUDMINE_ANDROID_VERSION"
ARCHIVE_NAME="$DEPLOY_DIR.zip"

echo "Building Java docs..."
ANDROID_DOCS=android-javadocs
DOCS_ARCHIVE="$ANDROID_DOCS-$CLOUDMINE_ANDROID_VERSION.zip"
rm -rf $ANDROID_HOME/target/$ANDROID_DOCS
mkdir $ANDROID_HOME/target/$ANDROID_DOCS

javadoc $ANDROID_HOME/src/main/java/com.cloudmine.api/*.java $ANDROID_HOME/src/main/java/com.cloudmine.api/gui/*.java $ANDROID_HOME/src/main/java/com.cloudmine.api/db/*.java $ANDROID_HOME/src/main/java/com/cloudmine/api/rest/response/*.java $ANDROID_HOME/src/main/java/com.cloudmine.api/rest/*.java $ANDROID_HOME/src/main/java/com.cloudmine.api/rest/callbacks/*.java $JAVA_SDK_HOME/src/main/java/com/cloudmine/api/*.java $JAVA_SDK_HOME/src/main/java/com/cloudmine/api/rest/*.java $JAVA_SDK_HOME/src/main/java/com/cloudmine/api/rest/callbacks/*.java $JAVA_SDK_HOME/src/main/java/com/cloudmine/api/rest/options/*.java $JAVA_SDK_HOME/src/main/java/com/cloudmine/api/rest/response/*.java $JAVA_SDK_HOME/src/main/java/com/cloudmine/api/rest/response/code/*.java $JAVA_SDK_HOME/src/main/java/com/cloudmine/api/persistance/*.java $JAVA_SDK_HOME/src/main/java/com/cloudmine/api/exceptions/*.java -d $ANDROID_HOME/target/$ANDROID_DOCS -doctitle "CloudMine Android $CLOUDMINE_ANDROID_VERSION API" -windowtitle "CloudMine Android API" -use -classpath $JAVA_SDK_HOME/target/lib/jackson-annotations-2.2.1.jar:$JAVA_SDK_HOME/target/lib/jackson-core-2.2.1.jar:$JAVA_SDK_HOME/target/lib/jackson-databind-2.2.1.jar:$JAVA_SDK_HOME/target/lib/junit-4.10.jar:$ANDROID_HOME/lib/cloudmine-annotations-1.0-SNAPSHOT.jar:$ANDROID_HOME/libs/volley-1.0.jar
