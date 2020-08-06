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

SELECT pg_catalog.setval('mygov_tipo_flusso_mygov_tipo_flusso_id_seq', (select coalesce(max(mygov_tipo_flusso_id), 1) from mygov_tipo_flusso), (select count(1) > 0 from mygov_tipo_flusso));
INSERT INTO mygov_tipo_flusso(mygov_tipo_flusso_id, version, cod_tipo, de_tipo, dt_creazione, dt_ultima_modifica) VALUES (nextval('mygov_tipo_flusso_mygov_tipo_flusso_id_seq'), 0, 'O', 'Flusso giornale di cassa OPI', '2017-12-11 10:00:00', '2017-12-11 10:00:00');