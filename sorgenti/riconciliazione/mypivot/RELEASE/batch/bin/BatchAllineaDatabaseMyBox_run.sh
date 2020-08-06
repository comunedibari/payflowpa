#!/bin/sh

export JAVA_HOME="/opt/mypay/java"
export ROOT_TALEND="/opt/mypay/shared/batch"
export JAVA="$JAVA_HOME/bin/java"
export ROOT_PATH="$ROOT_TALEND/jobs/BatchAllineaDatabaseMyBox/Main_Allinea_Database"

cd $ROOT_PATH

$JAVA -DHOSTNAME=$HOSTNAME -Xmx1024M -cp $ROOT_PATH/*:$ROOT_PATH/../lib/* allinea_database_mybox.main_allinea_database_0_1.Main_Allinea_Database --context_param directory_talend=$ROOT_TALEND 
