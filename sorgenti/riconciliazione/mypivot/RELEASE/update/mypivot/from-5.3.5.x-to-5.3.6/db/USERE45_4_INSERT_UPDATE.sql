SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;
SET search_path = public, pg_catalog;

SET SESSION AUTHORIZATION DEFAULT;

DELETE FROM mygov_ente_prenotazione;

SELECT pg_catalog.setval('mygov_ente_prenotazione_mygov_ente_prenotazione_id_seq', (select coalesce(max(mygov_ente_prenotazione_id), 1) from mygov_ente_prenotazione), (select count(1) > 0 from mygov_ente_prenotazione));

-- Tutti gli enti (NO RVE)
INSERT INTO mygov_ente_prenotazione(mygov_ente_prenotazione_id, "version", mygov_ente_id, mygov_anagrafica_stato_id, dt_prenota_date_from, dt_prenota_date_to, cod_prenota_identificativo_tipo_dovuto, dt_creazione, dt_ultima_modifica)
(SELECT NEXTVAL('mygov_ente_prenotazione_mygov_ente_prenotazione_id_seq'), 0, mygov_ente_id, (SELECT mygov_anagrafica_stato_id FROM mygov_anagrafica_stato WHERE cod_stato = 'INSERITO' AND de_tipo_stato = 'FLUSSO_EXPORT'), '2014-01-01 00:00:00', current_date, 'ALL', current_timestamp, current_timestamp
	FROM mygov_ente WHERE cod_ipa_ente <> 'R_VENETO');

-- Regione veneto
INSERT INTO mygov_ente_prenotazione(mygov_ente_prenotazione_id, "version", mygov_ente_id, mygov_anagrafica_stato_id, dt_prenota_date_from, dt_prenota_date_to, cod_prenota_identificativo_tipo_dovuto, dt_creazione, dt_ultima_modifica)
(SELECT NEXTVAL('mygov_ente_prenotazione_mygov_ente_prenotazione_id_seq'), 0, mygov_ente_id, (SELECT mygov_anagrafica_stato_id FROM mygov_anagrafica_stato WHERE cod_stato = 'INSERITO' AND de_tipo_stato = 'FLUSSO_EXPORT'), '2014-01-01 00:00:00', '2016-01-01 00:00:00', 'ALL', current_timestamp, current_timestamp
	FROM mygov_ente WHERE cod_ipa_ente = 'R_VENETO');

INSERT INTO mygov_ente_prenotazione(mygov_ente_prenotazione_id, "version", mygov_ente_id, mygov_anagrafica_stato_id, dt_prenota_date_from, dt_prenota_date_to, cod_prenota_identificativo_tipo_dovuto, dt_creazione, dt_ultima_modifica)
(SELECT NEXTVAL('mygov_ente_prenotazione_mygov_ente_prenotazione_id_seq'), 0, mygov_ente_id, (SELECT mygov_anagrafica_stato_id FROM mygov_anagrafica_stato WHERE cod_stato = 'INSERITO' AND de_tipo_stato = 'FLUSSO_EXPORT'), '2016-01-01 00:00:00', '2016-03-01 00:00:00', 'ALL', current_timestamp, current_timestamp
	FROM mygov_ente WHERE cod_ipa_ente = 'R_VENETO');

INSERT INTO mygov_ente_prenotazione(mygov_ente_prenotazione_id, "version", mygov_ente_id, mygov_anagrafica_stato_id, dt_prenota_date_from, dt_prenota_date_to, cod_prenota_identificativo_tipo_dovuto, dt_creazione, dt_ultima_modifica)
(SELECT NEXTVAL('mygov_ente_prenotazione_mygov_ente_prenotazione_id_seq'), 0, mygov_ente_id, (SELECT mygov_anagrafica_stato_id FROM mygov_anagrafica_stato WHERE cod_stato = 'INSERITO' AND de_tipo_stato = 'FLUSSO_EXPORT'), '2016-03-01 00:00:00', '2016-06-01 00:00:00', 'ALL', current_timestamp, current_timestamp
	FROM mygov_ente WHERE cod_ipa_ente = 'R_VENETO');

INSERT INTO mygov_ente_prenotazione(mygov_ente_prenotazione_id, "version", mygov_ente_id, mygov_anagrafica_stato_id, dt_prenota_date_from, dt_prenota_date_to, cod_prenota_identificativo_tipo_dovuto, dt_creazione, dt_ultima_modifica)
(SELECT NEXTVAL('mygov_ente_prenotazione_mygov_ente_prenotazione_id_seq'), 0, mygov_ente_id, (SELECT mygov_anagrafica_stato_id FROM mygov_anagrafica_stato WHERE cod_stato = 'INSERITO' AND de_tipo_stato = 'FLUSSO_EXPORT'), '2016-06-01 00:00:00', '2016-09-01 00:00:00', 'ALL', current_timestamp, current_timestamp
	FROM mygov_ente WHERE cod_ipa_ente = 'R_VENETO');

INSERT INTO mygov_ente_prenotazione(mygov_ente_prenotazione_id, "version", mygov_ente_id, mygov_anagrafica_stato_id, dt_prenota_date_from, dt_prenota_date_to, cod_prenota_identificativo_tipo_dovuto, dt_creazione, dt_ultima_modifica)
(SELECT NEXTVAL('mygov_ente_prenotazione_mygov_ente_prenotazione_id_seq'), 0, mygov_ente_id, (SELECT mygov_anagrafica_stato_id FROM mygov_anagrafica_stato WHERE cod_stato = 'INSERITO' AND de_tipo_stato = 'FLUSSO_EXPORT'), '2016-09-01 00:00:00', '2017-01-01 00:00:00', 'ALL', current_timestamp, current_timestamp
	FROM mygov_ente WHERE cod_ipa_ente = 'R_VENETO');

INSERT INTO mygov_ente_prenotazione(mygov_ente_prenotazione_id, "version", mygov_ente_id, mygov_anagrafica_stato_id, dt_prenota_date_from, dt_prenota_date_to, cod_prenota_identificativo_tipo_dovuto, dt_creazione, dt_ultima_modifica)
(SELECT NEXTVAL('mygov_ente_prenotazione_mygov_ente_prenotazione_id_seq'), 0, mygov_ente_id, (SELECT mygov_anagrafica_stato_id FROM mygov_anagrafica_stato WHERE cod_stato = 'INSERITO' AND de_tipo_stato = 'FLUSSO_EXPORT'), '2017-01-01 00:00:00', '2017-03-27 00:00:00', 'ALL', current_timestamp, current_timestamp
	FROM mygov_ente WHERE cod_ipa_ente = 'R_VENETO');