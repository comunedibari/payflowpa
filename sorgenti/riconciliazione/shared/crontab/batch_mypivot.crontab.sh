#!/bin/bash
export PGPASSWORD="mypivot"
psql --user "mypivot" --host localhost "mypivot"<<EOSQL
select now();
BEGIN;
select now();
DELETE FROM "public".mygov_import_export_rendicontazione_tesoreria_completa;
select now();
INSERT INTO "public".mygov_import_export_rendicontazione_tesoreria_completa SELECT * from "public".v_mygov_import_export_rendicontazione_tesoreria_completa;
select now();
COMMIT;
select now();
ANALYZE "public".mygov_import_export_rendicontazione_tesoreria_completa;
select now();
EOSQL
t1=`date +"%s"`
dt=$((t1-t0))
echo -e "\nDurata Complessiva : $dt secondi\n"


t0=`date +"%s"`
psql --user "mypivot" --host localhost "mypivot"<<EOSQL
select now();
BEGIN;
DELETE FROM "public".mygov_export_rendicontazione_completa;
INSERT INTO "public".mygov_export_rendicontazione_completa SELECT * from "public".v_mygov_export_rendicontazione_completa;
COMMIT;
select now();
ANALYZE "public".mygov_export_rendicontazione_completa;
select now();
EOSQL
t1=`date +"%s"`
dt=$((t1-t0))
echo -e "\nDurata Complessiva : $dt secondi\n"


t0=`date +"%s"`
psql --user "mypivot" --host localhost "mypivot"<<EOSQL
select now();
BEGIN;
DELETE FROM "public".mygov_import_export_rendicontazione_tesoreria;
INSERT INTO "public".mygov_import_export_rendicontazione_tesoreria SELECT * from "public".v_mygov_import_export_rendicontazione_tesoreria;
COMMIT;
select now();
ANALYZE "public".mygov_import_export_rendicontazione_tesoreria;
select now();
EOSQL
t1=`date +"%s"`
dt=$((t1-t0))
echo -e "\nDurata Complessiva : $dt secondi\n"


t0=`date +"%s"`
psql --user "mypivot" --host localhost "mypivot"<<EOSQL
select now();
BEGIN;
REFRESH MATERIALIZED VIEW vm_statistica_ente_anno_mese;
COMMIT;
select now();
VACUUM ANALYZE vm_statistica_ente_anno_mese;
select now();
EOSQL
t1=`date +"%s"`
dt=$((t1-t0))
echo -e "\nDurata Complessiva : $dt secondi\n"


t0=`date +"%s"`
psql --user "mypivot" --host localhost "mypivot"<<EOSQL
select now();
BEGIN;
REFRESH MATERIALIZED VIEW vm_statistica_ente_anno_mese_giorno;
COMMIT;
select now();
VACUUM ANALYZE vm_statistica_ente_anno_mese_giorno;
select now();
EOSQL
t1=`date +"%s"`
dt=$((t1-t0))
echo -e "\nDurata Complessiva : $dt secondi\n"


t0=`date +"%s"`
psql --user "mypivot" --host localhost "mypivot"<<EOSQL
select now();
BEGIN;
REFRESH MATERIALIZED VIEW vm_statistica_ente_anno_mese_giorno_uff_td;
COMMIT;
select now();
VACUUM ANALYZE vm_statistica_ente_anno_mese_giorno_uff_td;
select now();
EOSQL
t1=`date +"%s"`
dt=$((t1-t0))
echo -e "\nDurata Complessiva : $dt secondi\n"


t0=`date +"%s"`
psql --user "mypivot" --host localhost "mypivot"<<EOSQL
select now();
BEGIN;
REFRESH MATERIALIZED VIEW vm_statistica_ente_anno_mese_giorno_uff_td_cap;
COMMIT;
select now();
VACUUM ANALYZE vm_statistica_ente_anno_mese_giorno_uff_td_cap;
select now();
EOSQL
t1=`date +"%s"`
dt=$((t1-t0))
echo -e "\nDurata Complessiva : $dt secondi\n"


t0=`date +"%s"`
psql --user "mypivot" --host localhost "mypivot"<<EOSQL
select now();
BEGIN;
REFRESH MATERIALIZED VIEW vm_statistica_ente_anno_mese_giorno_uff_td_cap_acc;
COMMIT;
select now();
VACUUM ANALYZE vm_statistica_ente_anno_mese_giorno_uff_td_cap_acc;
select now();
EOSQL
t1=`date +"%s"`
dt=$((t1-t0))
echo -e "\nDurata Complessiva : $dt secondi\n"


t0=`date +"%s"`
psql --user "mypivot" --host localhost "mypivot"<<EOSQL
select now();
BEGIN;
REFRESH MATERIALIZED VIEW vm_statistica_ente_anno_mese_uff_td;
COMMIT;
select now();
VACUUM ANALYZE vm_statistica_ente_anno_mese_uff_td;
select now();
EOSQL
t1=`date +"%s"`
dt=$((t1-t0))
echo -e "\nDurata Complessiva : $dt secondi\n"


t0=`date +"%s"`
psql --user "mypivot" --host localhost "mypivot"<<EOSQL
select now();
BEGIN;
REFRESH MATERIALIZED VIEW vm_statistica_ente_anno_mese_uff_td_cap;
COMMIT;
select now();
VACUUM ANALYZE vm_statistica_ente_anno_mese_uff_td_cap;
select now();
EOSQL
t1=`date +"%s"`
dt=$((t1-t0))
echo -e "\nDurata Complessiva : $dt secondi\n"


t0=`date +"%s"`
psql --user "mypivot" --host localhost "mypivot"<<EOSQL
select now();
BEGIN;
REFRESH MATERIALIZED VIEW vm_statistica_ente_anno_mese_uff_td_cap_acc;
COMMIT;
select now();
VACUUM ANALYZE vm_statistica_ente_anno_mese_uff_td_cap_acc;
select now();
EOSQL
t1=`date +"%s"`
dt=$((t1-t0))
echo -e "\nDurata Complessiva : $dt secondi\n"


