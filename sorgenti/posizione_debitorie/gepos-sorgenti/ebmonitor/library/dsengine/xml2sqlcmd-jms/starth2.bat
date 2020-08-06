@echo off
cd dbs
if errorlevel 1 goto makeFolders
:startDb
@set h2_version=1.1.114
@set h2_jar=%M2_REPO%\com\h2database\h2\%h2_version%\h2-%h2_version%.jar 
@start java -cp "%h2_jar%" org.h2.tools.Server -tcpPort 9082 
cd ..
goto finished

:makeFolders
echo creating folder dbs
md dbs
cd dbs
goto startDb

:finished
echo task executed