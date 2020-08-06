#!/bin/sh

export JAVA_HOME="__TAG_BATCH_JAVA_HOME_PATH__"
export ROOT_TALEND="__TAG_BATCH_ROOT_PATH__"
export JAVA="$JAVA_HOME/bin/java"
export ROOT_PATH="$ROOT_TALEND/jobs/BatchFlussiRendicontazioneWS/Main_WS_Flussi_Rendicontazione"

cd $ROOT_PATH

$JAVA -DHOSTNAME=$HOSTNAME -Xmx1024M -cp $ROOT_PATH/*:$ROOT_PATH/../lib/* manage_flussi_rendicontazione.main_ws_flussi_rendicontazione_0_1.Main_WS_Flussi_Rendicontazione --context_param directory_talend=$ROOT_TALEND 
