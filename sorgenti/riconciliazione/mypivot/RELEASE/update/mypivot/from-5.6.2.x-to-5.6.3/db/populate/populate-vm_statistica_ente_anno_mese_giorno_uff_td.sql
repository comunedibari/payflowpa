SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;
SET role '__TAG_MYPIVOT_DB_USERNAME__';

-- SET search_path = public, pg_catalog;
SET search_path = '__TAG_MYPIVOT_DB_SCHEMA__';


BEGIN;
REFRESH MATERIALIZED VIEW vm_statistica_ente_anno_mese_giorno_uff_td;
COMMIT;        

VACUUM ANALYZE vm_statistica_ente_anno_mese_giorno_uff_td;


