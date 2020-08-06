#!/bin/sh
nohup ./BatchAllineaDatabaseMyBox_run.sh > nohup_BatchAllineaDatabaseMyBox_run.sh.log &
nohup ./BatchExportFlussiRiconciliazione_run.sh > nohup_BatchExportFlussiRiconciliazione_run.sh.log &
nohup ./BatchLoadFlussiExport_run.sh > nohup_BatchLoadFlussiExport_run.sh.log &
nohup ./BatchLoadFlussiRendicontazione_run.sh > nohup_BatchLoadFlussiRendicontazione_run.sh.log &
nohup ./BatchLoadFlussiTesoreria_run.sh > nohup_BatchLoadFlussiTesoreria_run.sh.log &
