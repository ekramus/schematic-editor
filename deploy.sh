#!/bin/sh

DEPLOY="deploy"
LIB=$DEPLOY/lib
JAR=$DEPLOY/jar
M2_REPO=~/.m2/repository

rm -fR $LIB
rm -fR $JAR
rm -fR $DEPLOY/*.log*
rm -fR $DEPLOY/*.xml

mkdir $JAR
mkdir $LIB

cp "$M2_REPO/log4j/log4j/1.2.14/log4j-1.2.14.jar" "$LIB"
cp "$M2_REPO/jgoodies/looks/1.2.2/looks-1.2.2.jar" "$LIB"

cp "Elements/target/Elements-1.0-SNAPSHOT.jar" "$JAR"
cp "Launchers/target/Launchers-1.0-SNAPSHOT.jar" "$JAR"
cp "Support/target/Support-1.0-SNAPSHOT.jar" "$JAR"
cp "Units/target/Units-1.0-SNAPSHOT.jar" "$JAR"
cp "VectorEditorEngine/target/VectorEditorEngine-1.0-SNAPSHOT.jar" "$JAR"
cp "Manipulations/target/VectorEditorEngine-1.0-SNAPSHOT.jar" "$JAR"

cp "Launchers/log4j.xml" "$DEPLOY"
cp "Launchers/properties.xml" "$DEPLOY"
