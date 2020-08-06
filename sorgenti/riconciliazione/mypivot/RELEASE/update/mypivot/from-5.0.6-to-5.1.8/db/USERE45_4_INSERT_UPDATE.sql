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


ALTER TABLE mygov_anagrafica_stato DISABLE TRIGGER USER;

INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (34, 0, 'FILE_DUPLICATO', 'Esiste un file già caricato con lo stesso nome', 'MANAGE', '2014-10-16 00:00:00', '2014-10-16 00:00:00');

ALTER TABLE mygov_anagrafica_stato ENABLE TRIGGER USER;


ALTER TABLE mygov_classificazione_completezza DISABLE TRIGGER USER;

INSERT INTO mygov_classificazione_completezza (mygov_classificazione_codice, mygov_classificazione_descrizione) VALUES ('RT_NO_IUF', 'Pagamenti non correttamente rendicontati');
INSERT INTO mygov_classificazione_completezza (mygov_classificazione_codice, mygov_classificazione_descrizione) VALUES ('IUF_NO_TES', 'Rendicontazioni non correttamente riversate');
INSERT INTO mygov_classificazione_completezza (mygov_classificazione_codice, mygov_classificazione_descrizione) VALUES ('TES_NO_IUF_OR_IUV', 'Riversamenti non rendicontati o di pagamenti non eseguiti');
INSERT INTO mygov_classificazione_completezza (mygov_classificazione_codice, mygov_classificazione_descrizione) VALUES ('IUV_NO_RT', 'Rendicontazioni di pagamenti non eseguiti');
INSERT INTO mygov_classificazione_completezza (mygov_classificazione_codice, mygov_classificazione_descrizione) VALUES ('IUD_NO_RT', 'Dovuti non correttamente pagati');
INSERT INTO mygov_classificazione_completezza (mygov_classificazione_codice, mygov_classificazione_descrizione) VALUES ('RT_NO_IUD', 'Pagamenti non correttamente notificati');
INSERT INTO mygov_classificazione_completezza (mygov_classificazione_codice, mygov_classificazione_descrizione) VALUES ('IUD_RT_IUF_TES', 'Pagamenti riconciliati e notificati');
INSERT INTO mygov_classificazione_completezza (mygov_classificazione_codice, mygov_classificazione_descrizione) VALUES ('RT_IUF_TES', 'Pagamenti riconciliati');
INSERT INTO mygov_classificazione_completezza (mygov_classificazione_codice, mygov_classificazione_descrizione) VALUES ('RT_IUF', 'Pagamenti rendicontati');
INSERT INTO mygov_classificazione_completezza (mygov_classificazione_codice, mygov_classificazione_descrizione) VALUES ('IUD_RT_IUF', 'Pagamenti Notificati e Riconciliati');


ALTER TABLE mygov_classificazione_completezza ENABLE TRIGGER USER;


ALTER TABLE mygov_tipo_flusso DISABLE TRIGGER USER;

INSERT INTO mygov_tipo_flusso (mygov_tipo_flusso_id, version, cod_tipo, de_tipo, dt_creazione, dt_ultima_modifica) VALUES (6, 0, 'D', 'Flusso dei dovuti', '2016-05-30 00:00:00', '2016-05-30 00:00:00');

ALTER TABLE mygov_tipo_flusso ENABLE TRIGGER USER;
