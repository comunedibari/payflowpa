#!/bin/bash
psql --user "__TAG_SYSTEM_MYPIVOT_DB_CRON_BATCH__" --host __TAG_MYPIVOT_DB_HOST__ "__TAG_MYPIVOT_DB_NAME__"<<EOSQL
select now();
BEGIN;
select now();
DELETE FROM "__TAG_MYPIVOT_DB_SCHEMA__".mygov_import_export_rendicontazione_tesoreria_completa;
select now();
INSERT INTO "__TAG_MYPIVOT_DB_SCHEMA__".mygov_import_export_rendicontazione_tesoreria_completa SELECT * from "__TAG_MYPIVOT_DB_SCHEMA__".v_mygov_import_export_rendicontazione_tesoreria_completa;
select now();
COMMIT;
select now();
ANALYZE "__TAG_MYPIVOT_DB_SCHEMA__".mygov_import_export_rendicontazione_tesoreria_completa;
select now();
EOSQL
t1=`date +"%s"`
dt=$((t1-t0))
echo -e "\nDurata Complessiva : $dt secondi\n"


t0=`date +"%s"`
psql --user "__TAG_SYSTEM_MYPIVOT_DB_CRON_BATCH__" --host __TAG_MYPIVOT_DB_HOST__ "__TAG_MYPIVOT_DB_NAME__"<<EOSQL
select now();
BEGIN;
DELETE FROM "__TAG_MYPIVOT_DB_SCHEMA__".mygov_export_rendicontazione_completa;
INSERT INTO "__TAG_MYPIVOT_DB_SCHEMA__".mygov_export_rendicontazione_completa SELECT * from "__TAG_MYPIVOT_DB_SCHEMA__".v_mygov_export_rendicontazione_completa;
COMMIT;
select now();
ANALYZE "__TAG_MYPIVOT_DB_SCHEMA__".mygov_export_rendicontazione_completa;
select now();
EOSQL
t1=`date +"%s"`
dt=$((t1-t0))
echo -e "\nDurata Complessiva : $dt secondi\n"


t0=`date +"%s"`
psql --user "__TAG_SYSTEM_MYPIVOT_DB_CRON_BATCH__" --host __TAG_MYPIVOT_DB_HOST__ "__TAG_MYPIVOT_DB_NAME__"<<EOSQL
select now();
BEGIN;
DELETE FROM "__TAG_MYPIVOT_DB_SCHEMA__".mygov_import_export_rendicontazione_tesoreria;
INSERT INTO "__TAG_MYPIVOT_DB_SCHEMA__".mygov_import_export_rendicontazione_tesoreria SELECT * from "__TAG_MYPIVOT_DB_SCHEMA__".v_mygov_import_export_rendicontazione_tesoreria;
COMMIT;
select now();
ANALYZE "__TAG_MYPIVOT_DB_SCHEMA__".mygov_import_export_rendicontazione_tesoreria;
select now();
EOSQL
t1=`date +"%s"`
dt=$((t1-t0))
echo -e "\nDurata Complessiva : $dt secondi\n"

