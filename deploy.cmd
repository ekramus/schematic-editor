@echo off

rem set variables
set DEPLOY=deploy
set LIB=%DEPLOY%\lib
set JAR=%DEPLOY%\jar
set CONF=%DEPLOY%\config
set PLUGIN=%DEPLOY%\plugins
set M2_REPO="%USERPROFILE%\.m2\repository"

rem start deploy batch
del /Q %LIB%
del /Q %JAR%
del /Q %PLUGIN%
del /Q %DEPLOY%\*.log
del /Q %DEPLOY%\*.log.*
del /Q %DEPLOY%\*.xml

mkdir %JAR%
mkdir %LIB%
mkdir %CONF%
mkdir %PLUGIN%

copy %M2_REPO%\log4j\log4j\1.2.14\log4j-1.2.14.jar %LIB%
copy %M2_REPO%\jgoodies\looks\1.2.2\looks-1.2.2.jar %LIB%
copy %M2_REPO%\com\thoughtworks\xstream\xstream\1.3\xstream-1.3.jar %LIB%

copy Launchers\target\Launchers-1.0-SNAPSHOT.jar %JAR%
copy Gui\target\Gui-1.0-SNAPSHOT.jar %JAR%
copy VectorEditorEngine\target\VectorEditorEngine-1.0-SNAPSHOT.jar %JAR%
copy Manipulations\target\Manipulations-1.0-SNAPSHOT.jar %JAR%
copy GraphNodes\target\GraphNodes-1.0-SNAPSHOT.jar %JAR%
copy Elements\target\Elements-1.0-SNAPSHOT.jar %JAR%
copy Support\target\Support-1.0-SNAPSHOT.jar %JAR%
copy Configuration\target\Configuration-1.0-SNAPSHOT.jar %JAR%
copy Units\target\Units-1.0-SNAPSHOT.jar %JAR%

copy Launchers\log4j.xml %DEPLOY%

rem copy plugins
copy Plugins-ElementsCount\target\Plugins-ElementsCount-1.0-SNAPSHOT.jar %PLUGIN% 
copy Plugins-CheckNetlist\target\Plugins-CheckNetlist-1.0-SNAPSHOT.jar %PLUGIN%
copy Plugins-AutomaticPinNaming\target\Plugins\AutomaticPartNaming-1.0-SNAPSHOT.jar %PLUGIN%
