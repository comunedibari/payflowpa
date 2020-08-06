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

INSERT INTO mygov_classificazione_completezza(mygov_classificazione_codice, mygov_classificazione_descrizione) VALUES ('TES_NO_MATCH', 'Riversamenti non standard pagoPA');