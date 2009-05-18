#!/bin/sh

LIB_PATH=lib/log4j-1.2.14.jar:lib/looks-1.2.2.jar

JAR_PATH=jar/Elements-1.0-SNAPSHOT.jar
JAR_PATH=$JAR_PATH:jar/Launchers-1.0-SNAPSHOT.jar
JAR_PATH=$JAR_PATH:jar/Support-1.0-SNAPSHOT.jar
JAR_PATH=$JAR_PATH:jar/Units-1.0-SNAPSHOT.jar
JAR_PATH=$JAR_PATH:jar/VectorEditorEngine-1.0-SNAPSHOT.jar
JAR_PATH=$JAR_PATH:jar/Manipulations-1.0-SNAPSHOT.jar

STANDALONE=cz.cvut.fel.schematicEditor.launcher.Standalone

CLASSPATH=$LIB_PATH:$JAR_PATH
java -cp "$CLASSPATH" "$STANDALONE"