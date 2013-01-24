#!/bin/bash

CLOUDMINE_JAVASDK_VERSION=0.5
CLOUDMINE_ANDROID_VERSION=0.5
ANDROID_HOME=.
JAVA_SDK_HOME=./cloudmine-javasdk
DEPLOY_DIR="cloudmine-android-v$CLOUDMINE_ANDROID_VERSION"
ARCHIVE_NAME="$DEPLOY_DIR.tgz"

echo "Building Java Library..."
mvn package -DskipTests=true -f $JAVA_SDK_HOME/pom.xml;mvn install:install-file -DgroupId=com.cloudmine.api -DartifactId=cloudmine-javasdk -Dversion=$CLOUDMINE_JAVASDK_VERSION -Dpackaging=jar -DpomFile=$JAVA_SDK_HOME/pom.xml -Dfile=$JAVA_SDK_HOME/target/cloudmine-javasdk-$CLOUDMINE_JAVASDK_VERSION.jar -DgeneratePom=true

echo "Building Android Library..."
mvn package -DskipTests=true -f $ANDROID_HOME/pom.xml;mvn install:install-file -DgroupId=com.cloudmine.api -DartifactId=cloudmine-android -Dversion=$CLOUDMINE_ANDROID_VERSION -Dpackaging=jar -DpomFile=$ANDROID_HOME/pom.xml -Dfile=$ANDROID_HOME/target/cloudmine-android-$CLOUDMINE_ANDROID_VERSION.jar

echo "Rebuilding Java Library..."
mvn assembly:assembly -DdescriptorId=jar-with-dependencies -DbuildFor=android -DskipTests=true -f $JAVA_SDK_HOME/pom.xml

echo "Building Java docs..."
cd $JAVA_SDK_HOME
mvn javadoc:jar
cd ..

echo "Copying files to deploy..."
mkdir $DEPLOY_DIR
cp "$JAVA_SDK_HOME/target/cloudmine-javasdk-$CLOUDMINE_JAVASDK_VERSION-jar-with-dependencies.jar" $DEPLOY_DIR
mv "$DEPLOY_DIR/cloudmine-javasdk-$CLOUDMINE_JAVASDK_VERSION-jar-with-dependencies.jar" "$DEPLOY_DIR/cloudmine-android-v$CLOUDMINE_ANDROID_VERSION.jar"
cp "$JAVA_SDK_HOME/target/cloudmine-javasdk-$CLOUDMINE_JAVASDK_VERSION-javadoc.jar" $DEPLOY_DIR
mv "$DEPLOY_DIR/cloudmine-javasdk-$CLOUDMINE_JAVASDK_VERSION-javadoc.jar" "$DEPLOY_DIR/cloudmine-android-v$CLOUDMINE_ANDROID_VERSION-javadoc.jar"
cp "$JAVA_SDK_HOME/CHANGELOG.md" $DEPLOY_DIR
cp "$JAVA_SDK_HOME/LICENSE" $DEPLOY_DIR

echo "Tarballing dpeploy directory..."
if [ -e $ARCHIVE_NAME ]; then
    rm $ARCHIVE_NAME
fi

tar -cvf $ARCHIVE_NAME $DEPLOY_DIR
rm -rf $DEPLOY_DIR
