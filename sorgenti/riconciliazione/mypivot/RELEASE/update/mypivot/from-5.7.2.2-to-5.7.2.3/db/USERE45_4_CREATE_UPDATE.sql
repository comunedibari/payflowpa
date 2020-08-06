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

ALTER TABLE mygov_flusso_tesoreria ADD COLUMN mygov_manage_flusso_id bigint;

ALTER TABLE mygov_flusso_tesoreria ADD
  CONSTRAINT mygov_flusso_tesoreria_mygov_manage_flusso_id_fkey FOREIGN KEY (mygov_manage_flusso_id)
      REFERENCES mygov_manage_flusso (mygov_manage_flusso_id);
