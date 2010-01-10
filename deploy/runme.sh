#!/bin/sh

LIB_PATH="lib/log4j-1.2.14.jar:lib/xstream-1.3.jar:lib/miglayout-3.6.1.jar"

JAR_PATH="jar/Launchers-1.0-SNAPSHOT.jar"
JAR_PATH="$JAR_PATH:jar/Gui-1.0-SNAPSHOT.jar"
JAR_PATH="$JAR_PATH:jar/VectorEditorEngine-1.0-SNAPSHOT.jar"
JAR_PATH="$JAR_PATH:jar/Manipulations-1.0-SNAPSHOT.jar"
JAR_PATH="$JAR_PATH:jar/GraphNodes-1.0-SNAPSHOT.jar"
JAR_PATH="$JAR_PATH:jar/Elements-1.0-SNAPSHOT.jar"
JAR_PATH="$JAR_PATH:jar/Support-1.0-SNAPSHOT.jar"
JAR_PATH="$JAR_PATH:jar/Configuration-1.0-SNAPSHOT.jar"
JAR_PATH="$JAR_PATH:jar/Units-1.0-SNAPSHOT.jar"
JAR_PATH="$JAR_PATH:jar/Parts-1.0-SNAPSHOT.jar"

STANDALONE=cz.cvut.fel.schematicEditor.launcher.Standalone

export CLASSPATH="$LIB_PATH:$JAR_PATH"
java -Dsun.java2d.noddraw=true -Dsun.java2d.d3d=false -Dsun.java2d.ddforcedram=true -Dsun.java2d.ddblit=false -Xmx512M $STANDALONE
