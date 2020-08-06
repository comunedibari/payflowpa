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


INSERT INTO mygov_classificazione_completezza(mygov_classificazione_codice, mygov_classificazione_descrizione) VALUES ('IUF_TES_DIV_IMP', 'Rendicontazioni con riversamento ma con importo differente');
INSERT INTO mygov_classificazione_completezza(mygov_classificazione_codice, mygov_classificazione_descrizione) VALUES ('RT_TES', 'Pagamenti con riversamento puntuale');
UPDATE mygov_classificazione_completezza SET mygov_classificazione_descrizione = 'Pagamenti riversati cumulativamente' WHERE mygov_classificazione_codice = 'RT_IUF_TES';

DELETE FROM mygov_anagrafica_stato WHERE de_tipo_stato = 'ACCERTAMENTO' AND cod_stato = 'CONFERMATO';