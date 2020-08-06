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

ALTER TABLE mygov_flusso_tesoreria_iuf DISABLE TRIGGER USER;

ALTER TABLE mygov_flusso_tesoreria_iuf DROP CONSTRAINT mygov_flusso_tesoreria_psp_iuf_pkey;
ALTER TABLE mygov_flusso_tesoreria_iuf ADD CONSTRAINT mygov_flusso_tesoreria_psp_iuf_pkey PRIMARY KEY (cod_bi2, cod_id_univoco_flusso, mygov_ente_id);

ALTER TABLE mygov_flusso_tesoreria_iuf ENABLE TRIGGER USER;