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

SET default_with_oids = false;


BEGIN;
DELETE FROM mygov_import_export_rendicontazione_tesoreria;
INSERT INTO mygov_import_export_rendicontazione_tesoreria SELECT * from v_mygov_import_export_rendicontazione_tesoreria;
COMMIT;

VACUUM ANALYZE mygov_import_export_rendicontazione_tesoreria;

BEGIN;
DELETE FROM mygov_export_rendicontazione_tesoreria;
INSERT INTO mygov_export_rendicontazione_tesoreria SELECT * from v_mygov_export_rendicontazione_tesoreria;
COMMIT;

VACUUM ANALYZE mygov_export_rendicontazione_tesoreria;

BEGIN;
DELETE FROM mygov_export_rendicontazione;
INSERT INTO mygov_export_rendicontazione SELECT * from v_mygov_export_rendicontazione;
COMMIT;

VACUUM ANALYZE mygov_export_rendicontazione;
