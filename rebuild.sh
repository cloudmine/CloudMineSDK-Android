#!/bin/bash

CLOUDMINE_JAVASDK_VERSION=0.6.5
CLOUDMINE_ANDROID_VERSION=0.6.6
ANDROID_HOME=.
JAVA_SDK_HOME=./cloudmine-javasdk
DEPLOY_DIR="cloudmine-android-v$CLOUDMINE_ANDROID_VERSION"
ARCHIVE_NAME="$DEPLOY_DIR.zip"

echo "Building Java Library..."
mvn package -DskipTests=true -f $JAVA_SDK_HOME/pom.xml;mvn install:install-file -DgroupId=com.cloudmine.api -DartifactId=cloudmine-javasdk -Dversion=$CLOUDMINE_JAVASDK_VERSION -Dpackaging=jar -DpomFile=$JAVA_SDK_HOME/pom.xml -Dfile=$JAVA_SDK_HOME/target/cloudmine-javasdk-$CLOUDMINE_JAVASDK_VERSION.jar -DgeneratePom=true

echo "Building Android Library..."
mvn package -DskipTests=true -f $ANDROID_HOME/pom.xml -U;mvn install:install-file -DgroupId=com.cloudmine.api -DartifactId=cloudmine-android -Dversion=$CLOUDMINE_ANDROID_VERSION -Dpackaging=jar -DpomFile=$ANDROID_HOME/pom.xml -Dfile=$ANDROID_HOME/target/cloudmine-android-$CLOUDMINE_ANDROID_VERSION.jar

echo "Rebuilding Java Library..."
mvn assembly:assembly -DdescriptorId=jar-with-dependencies -DbuildFor=android -DskipTests=true -f $JAVA_SDK_HOME/pom.xml -U


echo "Copying files"


cp "$JAVA_SDK_HOME/target/cloudmine-javasdk-$CLOUDMINE_JAVASDK_VERSION-jar-with-dependencies.jar" "../cloudmine-android-v$CLOUDMINE_ANDROID_VERSION.jar"
cp target/classes/*.java "./src/gen"
