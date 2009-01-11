@echo off

set LIB_PATH=lib/log4j-1.2.14.jar;lib/looks-1.2.2.jar;lib/xstream-1.3.jar

set JAR_PATH=jar/Launchers-1.0-SNAPSHOT.jar
set JAR_PATH=%JAR_PATH%;jar/Gui-1.0-SNAPSHOT.jar
set JAR_PATH=%JAR_PATH%;jar/VectorEditorEngine-1.0-SNAPSHOT.jar
set JAR_PATH=%JAR_PATH%;jar/Manipulations-1.0-SNAPSHOT.jar
set JAR_PATH=%JAR_PATH%;jar/GraphNodes-1.0-SNAPSHOT.jar
set JAR_PATH=%JAR_PATH%;jar/Elements-1.0-SNAPSHOT.jar
set JAR_PATH=%JAR_PATH%;jar/Support-1.0-SNAPSHOT.jar
set JAR_PATH=%JAR_PATH%;jar/Configuration-1.0-SNAPSHOT.jar
set JAR_PATH=%JAR_PATH%;jar/Units-1.0-SNAPSHOT.jar

set STANDALONE=cz.cvut.fel.schematicEditor.launcher.Standalone

set CLASSPATH=%LIB_PATH%;%JAR_PATH%
java -Dsun.java2d.noddraw=true -Dsun.java2d.d3d=false -Dsun.java2d.ddforcedram=true -Dsun.java2d.ddblit=false %STANDALONE%