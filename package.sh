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
ANDROID_DOCS=android-javadocs
DOCS_ARCHIVE="$ANDROID_DOCS-$CLOUDMINE_ANDROID_VERSION.tgz"
rm -rf $ANDROID_HOME/target/$ANDROID_DOCS
mkdir $ANDROID_HOME/target/$ANDROID_DOCS

javadoc $ANDROID_HOME/src/main/java/com.cloudmine.api/*.java $ANDROID_HOME/src/main/java/com.cloudmine.api/gui/*.java $ANDROID_HOME/src/main/java/com.cloudmine.api/loopj/*.java $ANDROID_HOME/src/main/java/com.cloudmine.api/rest/*.java $ANDROID_HOME/src/main/java/com.cloudmine.api/rest/callbacks/*.java $JAVA_SDK_HOME/src/main/java/com/cloudmine/api/*.java $JAVA_SDK_HOME/src/main/java/com/cloudmine/api/rest/*.java $JAVA_SDK_HOME/src/main/java/com/cloudmine/api/rest/callbacks/*.java $JAVA_SDK_HOME/src/main/java/com/cloudmine/api/rest/options/*.java $JAVA_SDK_HOME/src/main/java/com/cloudmine/api/rest/response/*.java $JAVA_SDK_HOME/src/main/java/com/cloudmine/api/rest/response/code/*.java $JAVA_SDK_HOME/src/main/java/com/cloudmine/api/persistance/*.java $JAVA_SDK_HOME/src/main/java/com/cloudmine/api/exceptions/*.java -d $ANDROID_HOME/target/$ANDROID_DOCS -doctitle "CloudMine Android $CLOUDMINE_ANDROID_VERSION API" -windowtitle "CloudMine Android API" -use -classpath $JAVA_SDK_HOME/target/lib/jackson-annotations-2.0.0-RC2.jar:$JAVA_SDK_HOME/target/lib/jackson-core-2.0.0-RC2.jar:$JAVA_SDK_HOME/target/lib/jackson-databind-2.0.0-RC2.jar
cd "$ANDROID_HOME/target"
tar -cvf "../$DOCS_ARCHIVE" $ANDROID_DOCS 
cd ..

echo "Copying files to deploy..."
mkdir $DEPLOY_DIR
cp "$JAVA_SDK_HOME/target/cloudmine-javasdk-$CLOUDMINE_JAVASDK_VERSION-jar-with-dependencies.jar" "$DEPLOY_DIR/cloudmine-android-v$CLOUDMINE_ANDROID_VERSION.jar"
cp $DOCS_ARCHIVE $DEPLOY_DIR
cp "$JAVA_SDK_HOME/CHANGELOG.md" $DEPLOY_DIR
cp "$JAVA_SDK_HOME/LICENSE" $DEPLOY_DIR

echo "Tarballing dpeploy directory..."
if [ -e $ARCHIVE_NAME ]; then
    rm $ARCHIVE_NAME
fi

tar -cvf $ARCHIVE_NAME $DEPLOY_DIR
rm -rf $DEPLOY_DIR
rm $DOCS_ARCHIVE
