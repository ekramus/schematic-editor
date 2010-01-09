#!/bin/sh

# set variables
DEPLOY="deploy"
LIB=$DEPLOY/lib
JAR=$DEPLOY/jar
CONF=$DEPLOY/config
PLUGIN=$DEPLOY/plugins
PARTS=$DEPLOY/parts
M2_REPO=~/.m2/repository

# start deploy batch
rm -fR $LIB
rm -fR $JAR
rm -fR $PLUGIN
rm -fR $PARTS
rm -fR $DEPLOY/*.log*
rm -fR $DEPLOY/*.xml

mkdir $JAR
mkdir $LIB
mkdir $CONF
mkdir $PLUGIN
mkdir $PARTS

cp "$M2_REPO/log4j/log4j/1.2.14/log4j-1.2.14.jar" "$LIB"
cp "$M2_REPO/jgoodies/looks/1.2.2/looks-1.2.2.jar" "$LIB"
cp "$M2_REPO/com/miglayout/miglayout/3.6.1/miglayout-3.6.1.jar" "$LIB"
cp "$M2_REPO/com/thoughtworks/xstream/xstream/1.3/xstream-1.3.jar" "$LIB"

cp "Launchers/target/Launchers-1.0-SNAPSHOT.jar" "$JAR"
cp "Gui/target/Gui-1.0-SNAPSHOT.jar" "$JAR"
cp "VectorEditorEngine/target/VectorEditorEngine-1.0-SNAPSHOT.jar" "$JAR"
cp "Manipulations/target/Manipulations-1.0-SNAPSHOT.jar" "$JAR"
cp "GraphNodes/target/GraphNodes-1.0-SNAPSHOT.jar" "$JAR"
cp "Elements/target/Elements-1.0-SNAPSHOT.jar" "$JAR"
cp "Support/target/Support-1.0-SNAPSHOT.jar" "$JAR"
cp "Configuration/target/Configuration-1.0-SNAPSHOT.jar" "$JAR"
cp "Units/target/Units-1.0-SNAPSHOT.jar" "$JAR"
cp "Parts/target/Parts-1.0-SNAPSHOT.jar" "$JAR"

# cp "Launchers/log4j.xml" "$DEPLOY"

# copy plugins
cp "Plugins-ElementsCount/target/Plugins-ElementsCount-1.0-SNAPSHOT.jar" "$PLUGIN"
cp "Plugins-CheckNetlist/target/Plugins-CheckNetlist-1.0-SNAPSHOT.jar" "$PLUGIN"
cp "Plugins-AutomaticPartNaming/target/Plugins-AutomaticPartNaming-1.0-SNAPSHOT.jar" "$PLUGIN"

# copy parts
cp -r "Launchers/parts" "$PARTS"


