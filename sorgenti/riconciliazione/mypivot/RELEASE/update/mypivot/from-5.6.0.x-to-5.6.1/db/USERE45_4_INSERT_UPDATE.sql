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

SELECT pg_catalog.setval('mygov_anagrafica_stato_mygov_anagrafica_stato_id_seq', (select coalesce(max(mygov_anagrafica_stato_id), 1) from mygov_anagrafica_stato), (select count(1) > 0 from mygov_anagrafica_stato));

INSERT INTO mygov_anagrafica_stato VALUES(NEXTVAL('mygov_anagrafica_stato_mygov_anagrafica_stato_id_seq'), 'CLASSIFICAZIONE_COMPLETEZZA_NON_VALIDA', 'Classificazione Completezza non valida per la versione del tracciato di export selezionata', 'EXPORT_FLUSSO_RICONCILIAZIONE', NOW(), NOW());