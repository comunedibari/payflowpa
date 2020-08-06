#!/bin/sh

cd `dirname $0`

ROOT_PATH=`pwd`

JAVA_HOME="/opt/jdk-1.8.0"
export JAVA_HOME

M2_HOME="/opt/apache-maven-3.6.3"
export M2_HOME

"$M2_HOME"/bin/mvn clean install
