@echo off

%~d0
cd %~dp0

set "ROOT_PATH=%cd%"

set "JAVA_HOME=C:/Program Files/Java/jdk1.8.0_211"

set "M2_HOME=C:/Program Files/apache-maven-3.6.3"


call "%M2_HOME%"\bin\mvn clean install
