#!/bin/sh

export JAVA_HOME="__TAG_BATCH_JAVA_HOME_PATH__"
export ROOT_TALEND="__TAG_BATCH_ROOT_PATH__"
export JAVA="$JAVA_HOME/bin/java"
export ROOT_PATH="$ROOT_TALEND/jobs/BatchExportFlussiRiconciliazione/Main_Export_Flussi_Riconciliazione"

cd $ROOT_PATH

$JAVA -DHOSTNAME=$HOSTNAME -Xmx1024M -cp $ROOT_PATH/*:$ROOT_PATH/../lib/* manage_flussi_riconciliazione.main_export_flussi_riconciliazione_0_1.Main_Export_Flussi_Riconciliazione --context_param directory_talend=$ROOT_TALEND
