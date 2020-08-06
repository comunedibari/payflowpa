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

-- Sequence: mygov_accertamento_mygov_accertamento_id_seq

CREATE SEQUENCE mygov_accertamento_mygov_accertamento_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 6
  CACHE 1;


-- Sequence: mygov_accertamento_dettaglio_mygov_accertamento_dett_id_seq

CREATE SEQUENCE mygov_accertamento_dettaglio_mygov_accertamento_dett_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 35
  CACHE 1;


-- Table: mygov_accertamento

 CREATE TABLE mygov_accertamento
(
  mygov_accertamento_id bigint NOT NULL,
  mygov_ente_tipo_dovuto_id bigint NOT NULL,
  mygov_anagrafica_stato_id bigint NOT NULL,
  mygov_utente_id bigint NOT NULL,
  de_nome_accertamento character varying(255) NOT NULL,
  dt_creazione timestamp without time zone NOT NULL,
  dt_ultima_modifica timestamp without time zone NOT NULL,
  printed boolean NOT NULL DEFAULT false,
  CONSTRAINT mygov_accertamento_pkey PRIMARY KEY (mygov_accertamento_id),
  CONSTRAINT mygov_accertamento_mygov_anagrafica_stato_id_fkey FOREIGN KEY (mygov_anagrafica_stato_id)
      REFERENCES mygov_anagrafica_stato (mygov_anagrafica_stato_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT mygov_accertamento_mygov_ente_tipo_dovuto_id_fkey FOREIGN KEY (mygov_ente_tipo_dovuto_id)
      REFERENCES mygov_ente_tipo_dovuto (mygov_ente_tipo_dovuto_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT mygov_accertamento_mygov_utente_id_fkey FOREIGN KEY (mygov_utente_id)
      REFERENCES mygov_utente (mygov_utente_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);


-- Table: mygov_accertamento_dettaglio

CREATE TABLE mygov_accertamento_dettaglio
(
  mygov_accertamento_dettaglio_id bigint NOT NULL,
  mygov_accertamento_id bigint NOT NULL,
  cod_iuv character varying(35) NOT NULL,
  cod_iuf character varying(35),
  de_anno_bolletta character varying(4),
  cod_bolletta character varying(7),
  dt_data_inserimento timestamp without time zone NOT NULL,
  mygov_utente_id bigint NOT NULL,
  CONSTRAINT mygov_accertamento_dettaglio_pkey PRIMARY KEY (mygov_accertamento_dettaglio_id),
  CONSTRAINT mygov_accertamento_dettaglio_mygov_accertamento_id_fkey FOREIGN KEY (mygov_accertamento_id)
      REFERENCES mygov_accertamento (mygov_accertamento_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT mygov_accertamento_dettaglio_mygov_utente_id_fkey FOREIGN KEY (mygov_utente_id)
      REFERENCES mygov_utente (mygov_utente_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);

-- Index: mygov_accertamento_dettaglio_cod_iuv_cod_iuf_de_anno_bollet_idx 

CREATE INDEX mygov_accertamento_dettaglio_cod_iuv_cod_iuf_de_anno_bollet_idx
  ON mygov_accertamento_dettaglio
  USING btree
  (cod_iuv COLLATE pg_catalog."default", cod_iuf COLLATE pg_catalog."default", de_anno_bolletta COLLATE pg_catalog."default", cod_bolletta COLLATE pg_catalog."default");


-- ANAGRAFICA STATO

SELECT pg_catalog.setval('mygov_anagrafica_stato_mygov_anagrafica_stato_id_seq', (select coalesce(max(mygov_anagrafica_stato_id), 1) from mygov_anagrafica_stato), (select count(1) > 0 from mygov_anagrafica_stato));

ALTER TABLE mygov_anagrafica_stato DISABLE TRIGGER USER;

-- STATO "INSERITO" / "ACCERTAMENTO"
INSERT INTO mygov_anagrafica_stato(mygov_anagrafica_stato_id, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica)
    VALUES (nextval('mygov_anagrafica_stato_mygov_anagrafica_stato_id_seq'), 'INSERITO', 'Inserito un nuovo accertamento', 'ACCERTAMENTO', NOW(), NOW());

-- STATO "CONFERMATO" / "ACCERTAMENTO"
INSERT INTO mygov_anagrafica_stato(mygov_anagrafica_stato_id, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica)
    VALUES (nextval('mygov_anagrafica_stato_mygov_anagrafica_stato_id_seq'), 'CONFERMATO', 'L''accertamento è stato confermato, l''utente può procedere alla stampa del documento.', 'ACCERTAMENTO', NOW(), NOW());

-- STATO "CHIUSO" / "ACCERTAMENTO"
INSERT INTO mygov_anagrafica_stato(mygov_anagrafica_stato_id, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica)
    VALUES (nextval('mygov_anagrafica_stato_mygov_anagrafica_stato_id_seq'), 'CHIUSO', 'L''utente ha chiuso l''accertamento, il documento è stato firmato.', 'ACCERTAMENTO', NOW(), NOW());

-- STATO "ANNULLATO" / "ACCERTAMENTO"
INSERT INTO mygov_anagrafica_stato(mygov_anagrafica_stato_id, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica)
    VALUES (nextval('mygov_anagrafica_stato_mygov_anagrafica_stato_id_seq'), 'ANNULLATO', 'L''accertamento è stato annullato.', 'ACCERTAMENTO', NOW(), NOW());


ALTER TABLE mygov_anagrafica_stato ENABLE TRIGGER USER;


-- Function: get_count_pagamenti_inseribili_in_accertamento 
 
CREATE OR REPLACE FUNCTION get_count_pagamenti_inseribili_in_accertamento(_codice_ipa_ente character varying, _cod_tipo_dovuto character varying, _classificazione character varying, _codice_iud character varying, _codice_iuv character varying, _codice_identificativo_univoco_pagatore character varying, _identificativo_flusso_rendicontazione character varying, _data_esito_singolo_pagamento_da date, _data_esito_singolo_pagamento_a date, _data_regolamento_da date, _data_regolamento_a date, _data_contabile_da date, _data_contabile_a date, _data_valuta_da date, _data_valuta_a date, _data_ultimo_aggiornamento_da date, _data_ultimo_aggiornamento_a date)
  RETURNS bigint AS
$BODY$
 
  SELECT 
        count(*) 
  FROM 
      mygov_import_export_rendicontazione_tesoreria AS p
  WHERE 
      /* Escludo le righe gia in accertamento */
      (p.cod_iuf_key  || '-' || p.cod_iuv_key  || '-' || p.de_anno_bolletta  || '-' || p.cod_bolletta) 
	NOT IN 
      (
        SELECT 
	     ad.cod_iuf || '-' || ad.cod_iuv  || '-' || ad.de_anno_bolletta  || '-' || ad.cod_bolletta 
        FROM 
             mygov_accertamento_dettaglio ad INNER JOIN mygov_accertamento a ON ad.mygov_accertamento_id = a.mygov_accertamento_id
					     INNER JOIN mygov_anagrafica_stato st ON a.mygov_anagrafica_stato_id = st.mygov_anagrafica_stato_id
        WHERE 
             st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato <> 'ANNULLATO' 
      ) AND
  

      /* Condizioni obbligatorie */
      p.codice_ipa_ente = $1 AND p.tipo_dovuto = $2 AND p.classificazione_completezza = $3 AND
      /* IUD */
      CASE WHEN ($4 IS NOT NULL) THEN p.cod_iud_key = $4 ELSE true END AND
      /* IUV */
      CASE WHEN ($5 IS NOT NULL) THEN p.cod_iuv_key = $5 ELSE true END AND
      /* Identificativo univoco pagatore */
      CASE WHEN ($6 IS NOT NULL) THEN p.codice_identificativo_univoco_pagatore = $6 ELSE true END AND
      /* Indentificativo flusso rendicontazione */
      CASE WHEN ($7 IS NOT NULL) THEN p.identificativo_flusso_rendicontazione = $7 ELSE true END AND
      /* Data esito pagamento da */
      CASE WHEN ($8 IS NOT NULL) THEN p.dt_data_esito_singolo_pagamento >= $8 ELSE true END AND
      /* Data esito pagamento a */
      CASE WHEN ($9 IS NOT NULL) THEN p.dt_data_esito_singolo_pagamento <= $9 ELSE true END AND
      /* Data regolamento da */
      CASE WHEN ($10 IS NOT NULL) THEN p.dt_data_regolamento >= $10 ELSE true END AND
      /* Data regolamento a */
      CASE WHEN ($11 IS NOT NULL) THEN p.dt_data_regolamento <= $11 ELSE true END AND
      /* Data contabile da */
      CASE WHEN ($12 IS NOT NULL) THEN p.dt_data_contabile >= $12 ELSE true END AND
      /* Data contabile a */
      CASE WHEN ($13 IS NOT NULL) THEN p.dt_data_contabile <= $13 ELSE true END AND
      /* Data valuta da */
      CASE WHEN ($14 IS NOT NULL) THEN p.dt_data_valuta >= $14 ELSE true END AND
      /* Data valuta a */
      CASE WHEN ($15 IS NOT NULL) THEN p.dt_data_valuta <= $15 ELSE true END AND
      /* Data ultimo aggiornamento da */
      CASE WHEN ($16 IS NOT NULL) THEN p.dt_data_ultimo_aggiornamento >= $16 ELSE true END AND
      /* Data ultimo aggiornamento a */
      CASE WHEN ($17 IS NOT NULL) THEN p.dt_data_ultimo_aggiornamento <= $17 ELSE true END;
   
$BODY$
  LANGUAGE sql VOLATILE
  COST 100; 
COMMENT ON FUNCTION get_count_pagamenti_inseribili_in_accertamento(character varying, character varying, character varying, character varying, character varying, character varying, character varying, date, date, date, date, date, date, date, date, date, date) IS 'Pagamenti inseribili in accertamento';


-- Function: get_count_pagamenti_inseriti_in_accertamento 

CREATE OR REPLACE FUNCTION get_count_pagamenti_inseriti_in_accertamento(_accertamento_id bigint, _codice_ipa_ente character varying, _cod_tipo_dovuto character varying, _classificazione character varying, _codice_iud character varying, _codice_iuv character varying, _codice_identificativo_univoco_pagatore character varying, _identificativo_flusso_rendicontazione character varying, _data_esito_singolo_pagamento_da date, _data_esito_singolo_pagamento_a date, _data_regolamento_da date, _data_regolamento_a date, _data_contabile_da date, _data_contabile_a date, _data_valuta_da date, _data_valuta_a date, _data_ultimo_aggiornamento_da date, _data_ultimo_aggiornamento_a date)
  RETURNS bigint AS
$BODY$
  SELECT 
       count(*)
  FROM 
      mygov_import_export_rendicontazione_tesoreria AS p INNER JOIN mygov_accertamento_dettaglio AS a 
      ON p.cod_iuf_key = a.cod_iuf AND p.cod_iuv_key = a.cod_iuv AND p.de_anno_bolletta = a.de_anno_bolletta AND p.cod_bolletta = a.cod_bolletta
  WHERE
      /* Condizioni obbligatorie */
      a.mygov_accertamento_id = $1 AND p.codice_ipa_ente = $2 AND p.tipo_dovuto = $3 AND p.classificazione_completezza = $4 AND
      /* IUD */
      CASE WHEN ($5 IS NOT NULL) THEN p.cod_iud_key = $5 ELSE true END AND
      /* IUV */
      CASE WHEN ($6 IS NOT NULL) THEN p.cod_iuv_key = $6 ELSE true END AND
      /* Identificativo univoco pagatore */
      CASE WHEN ($7 IS NOT NULL) THEN p.codice_identificativo_univoco_pagatore = $7 ELSE true END AND
      /* Indentificativo flusso rendicontazione */
      CASE WHEN ($8 IS NOT NULL) THEN p.identificativo_flusso_rendicontazione = $8 ELSE true END AND
      /* Data esito pagamento da */
      CASE WHEN ($9 IS NOT NULL) THEN p.dt_data_esito_singolo_pagamento >= $9 ELSE true END AND
      /* Data esito pagamento a */
      CASE WHEN ($10 IS NOT NULL) THEN p.dt_data_esito_singolo_pagamento <= $10 ELSE true END AND
      /* Data regolamento da */
      CASE WHEN ($11 IS NOT NULL) THEN p.dt_data_regolamento >= $11 ELSE true END AND
      /* Data regolamento a */
      CASE WHEN ($12 IS NOT NULL) THEN p.dt_data_regolamento <= $12 ELSE true END AND
      /* Data contabile da */
      CASE WHEN ($13 IS NOT NULL) THEN p.dt_data_contabile >= $13 ELSE true END AND
      /* Data contabile a */
      CASE WHEN ($14 IS NOT NULL) THEN p.dt_data_contabile <= $14 ELSE true END AND
      /* Data valuta da */
      CASE WHEN ($15 IS NOT NULL) THEN p.dt_data_valuta >= $15 ELSE true END AND
      /* Data valuta a */
      CASE WHEN ($16 IS NOT NULL) THEN p.dt_data_valuta <= $16 ELSE true END AND
      /* Data ultimo aggiornamento da */
      CASE WHEN ($17 IS NOT NULL) THEN p.dt_data_ultimo_aggiornamento >= $17 ELSE true END AND
      /* Data ultimo aggiornamento a */
      CASE WHEN ($18 IS NOT NULL) THEN p.dt_data_ultimo_aggiornamento <= $18 ELSE true END;
   
$BODY$
  LANGUAGE sql VOLATILE
  COST 100;


-- Function: get_pagamenti_inseribili_in_accertamento 

CREATE OR REPLACE FUNCTION get_pagamenti_inseribili_in_accertamento(_codice_ipa_ente character varying, _cod_tipo_dovuto character varying, _classificazione character varying, _codice_iud character varying, _codice_iuv character varying, _codice_identificativo_univoco_pagatore character varying, _identificativo_flusso_rendicontazione character varying, _data_esito_singolo_pagamento_da date, _data_esito_singolo_pagamento_a date, _data_regolamento_da date, _data_regolamento_a date, _data_contabile_da date, _data_contabile_a date, _data_valuta_da date, _data_valuta_a date, _data_ultimo_aggiornamento_da date, _data_ultimo_aggiornamento_a date, _has_pagination boolean, _page integer, _page_size integer)
  RETURNS SETOF mygov_import_export_rendicontazione_tesoreria AS
$BODY$
  SELECT 
       codice_ipa_ente, dt_data_esecuzione_pagamento, de_data_esecuzione_pagamento, 
       singolo_importo_commissione_carico_pa, bilancio, nome_flusso_import_ente, 
       riga_flusso_import_ente, codice_iud, codice_iuv, versione_oggetto, 
       identificativo_dominio, identificativo_stazione_richiedente, 
       identificativo_messaggio_ricevuta, data_ora_messaggio_ricevuta, 
       riferimento_messaggio_richiesta, riferimento_data_richiesta, 
       tipo_identificativo_univoco_attestante, codice_identificativo_univoco_attestante, 
       denominazione_attestante, codice_unit_oper_attestante, denom_unit_oper_attestante, 
       indirizzo_attestante, civico_attestante, cap_attestante, localita_attestante, 
       provincia_attestante, nazione_attestante, tipo_identificativo_univoco_beneficiario, 
       codice_identificativo_univoco_beneficiario, denominazione_beneficiario, 
       codice_unit_oper_beneficiario, denom_unit_oper_beneficiario, 
       indirizzo_beneficiario, civico_beneficiario, cap_beneficiario, 
       localita_beneficiario, provincia_beneficiario, nazione_beneficiario, 
       tipo_identificativo_univoco_versante, codice_identificativo_univoco_versante, 
       anagrafica_versante, indirizzo_versante, civico_versante, cap_versante, 
       localita_versante, provincia_versante, nazione_versante, email_versante, 
       tipo_identificativo_univoco_pagatore, codice_identificativo_univoco_pagatore, 
       anagrafica_pagatore, indirizzo_pagatore, civico_pagatore, cap_pagatore, 
       localita_pagatore, provincia_pagatore, nazione_pagatore, email_pagatore, 
       codice_esito_pagamento, importo_totale_pagato, identificativo_univoco_versamento, 
       codice_contesto_pagamento, singolo_importo_pagato, esito_singolo_pagamento, 
       dt_data_esito_singolo_pagamento, de_data_esito_singolo_pagamento, 
       identificativo_univoco_riscossione, causale_versamento, dati_specifici_riscossione, 
       tipo_dovuto, identificativo_flusso_rendicontazione, data_ora_flusso_rendicontazione, 
       identificativo_univoco_regolamento, dt_data_regolamento, de_data_regolamento, 
       numero_totale_pagamenti, importo_totale_pagamenti, data_acquisizione, 
       cod_conto, dt_data_contabile, de_data_contabile, dt_data_valuta, 
       de_data_valuta, num_importo, de_importo, cod_or1, p.de_anno_bolletta, 
       p.cod_bolletta, cod_id_dominio, dt_ricezione, de_data_ricezione, 
       de_anno_documento, cod_documento, de_anno_provvisorio, cod_provvisorio, 
       verifica_totale, classificazione_completezza, dt_data_ultimo_aggiornamento, 
       de_data_ultimo_aggiornamento, indice_dati_singolo_pagamento, 
       cod_iuf_key, cod_iud_key, cod_iuv_key
  FROM 
      mygov_import_export_rendicontazione_tesoreria AS p 
  WHERE
      /* Escludo le righe gia in accertamento */
      (p.cod_iuf_key  || '-' || p.cod_iuv_key  || '-' || p.de_anno_bolletta  || '-' || p.cod_bolletta) 
	NOT IN 
      (
        SELECT 
	     ad.cod_iuf || '-' || ad.cod_iuv  || '-' || ad.de_anno_bolletta  || '-' || ad.cod_bolletta 
        FROM 
             mygov_accertamento_dettaglio ad INNER JOIN mygov_accertamento a ON ad.mygov_accertamento_id = a.mygov_accertamento_id
					     INNER JOIN mygov_anagrafica_stato st ON a.mygov_anagrafica_stato_id = st.mygov_anagrafica_stato_id
        WHERE 
             st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato <> 'ANNULLATO' 
      ) AND
  
      /* Condizioni obbligatorie */
      p.codice_ipa_ente = $1 AND p.tipo_dovuto = $2 AND p.classificazione_completezza = $3 AND
            
      /* IUD */
      CASE WHEN ($4 IS NOT NULL) THEN p.cod_iud_key = $4 ELSE true END AND
      /* IUV */
      CASE WHEN ($5 IS NOT NULL) THEN p.cod_iuv_key = $5 ELSE true END AND
      /* Identificativo univoco pagatore */
      CASE WHEN ($6 IS NOT NULL) THEN p.codice_identificativo_univoco_pagatore = $6 ELSE true END AND
      /* Indentificativo flusso rendicontazione */
      CASE WHEN ($7 IS NOT NULL) THEN p.identificativo_flusso_rendicontazione = $7 ELSE true END AND
      /* Data esito pagamento da */
      CASE WHEN ($8 IS NOT NULL) THEN p.dt_data_esito_singolo_pagamento >= $8 ELSE true END AND
      /* Data esito pagamento a */
      CASE WHEN ($9 IS NOT NULL) THEN p.dt_data_esito_singolo_pagamento <= $9 ELSE true END AND
      /* Data regolamento da */
      CASE WHEN ($10 IS NOT NULL) THEN p.dt_data_regolamento >= $10 ELSE true END AND
      /* Data regolamento a */
      CASE WHEN ($11 IS NOT NULL) THEN p.dt_data_regolamento <= $11 ELSE true END AND
      /* Data contabile da */
      CASE WHEN ($12 IS NOT NULL) THEN p.dt_data_contabile >= $12 ELSE true END AND
      /* Data contabile a */
      CASE WHEN ($13 IS NOT NULL) THEN p.dt_data_contabile <= $13 ELSE true END AND
      /* Data valuta da */
      CASE WHEN ($14 IS NOT NULL) THEN p.dt_data_valuta >= $14 ELSE true END AND
      /* Data valuta a */
      CASE WHEN ($15 IS NOT NULL) THEN p.dt_data_valuta <= $15 ELSE true END AND
      /* Data ultimo aggiornamento da */
      CASE WHEN ($16 IS NOT NULL) THEN p.dt_data_ultimo_aggiornamento >= $16 ELSE true END AND
      /* Data ultimo aggiornamento a */
      CASE WHEN ($17 IS NOT NULL) THEN p.dt_data_ultimo_aggiornamento <= $17 ELSE true END

   ORDER BY dt_data_esito_singolo_pagamento ASC, codice_iuv ASC, codice_iud asc

   OFFSET CASE WHEN ($18) THEN $19 ELSE 0 END 

   LIMIT CASE WHEN ($18) THEN $20 ELSE null END;
   
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000; 
COMMENT ON FUNCTION get_pagamenti_inseribili_in_accertamento(character varying, character varying, character varying, character varying, character varying, character varying, character varying, date, date, date, date, date, date, date, date, date, date, boolean, integer, integer) IS 'Pagamenti inseribili in accertamento';


-- Function: get_pagamenti_inseriti_in_accertamento 

CREATE OR REPLACE FUNCTION get_pagamenti_inseriti_in_accertamento(IN _accertamento_id bigint, IN _codice_ipa_ente character varying, IN _cod_tipo_dovuto character varying, IN _classificazione character varying, IN _codice_iud character varying, IN _codice_iuv character varying, IN _codice_identificativo_univoco_pagatore character varying, IN _identificativo_flusso_rendicontazione character varying, IN _data_esito_singolo_pagamento_da date, IN _data_esito_singolo_pagamento_a date, IN _data_regolamento_da date, IN _data_regolamento_a date, IN _data_contabile_da date, IN _data_contabile_a date, IN _data_valuta_da date, IN _data_valuta_a date, IN _data_ultimo_aggiornamento_da date, IN _data_ultimo_aggiornamento_a date, IN _has_pagination boolean, IN _page integer, IN _page_size integer)
  RETURNS TABLE(id_acc bigint, accertamento_id_acc bigint, codiuv_acc character varying, codiuf_acc character varying, de_anno_bolletta_acc character varying, cod_bolletta_acc character varying, dt_data_inserimento_acc timestamp without time zone, codiceipaente character varying, dtdataesecuzionepagamento date, dedataesecuzionepagamento character varying, singoloimportocommissionecaricopa character varying, bilancio character varying, nomeflussoimportente character varying, rigaflussoimportente character varying, codiceiud character varying, codiceiuv character varying, versioneoggetto character varying, identificativodominio character varying, identificativostazionerichiedente character varying, identificativomessaggioricevuta character varying, dataoramessaggioricevuta character varying, riferimentomessaggiorichiesta character varying, riferimentodatarichiesta character varying, tipoidentificativounivocoattestante character varying, codiceidentificativounivocoattestante character varying, denominazione_attestante character varying, codice_unit_oper_attestante character varying, denomunitoperattestante character varying, indirizzoattestante character varying, civicoattestante character varying, capattestante character varying, localitaattestante character varying, provinciaattestante character varying, nazioneattestante character varying, tipoidentificativounivocobeneficiario character varying, codiceidentificativounivocobeneficiario character varying, denominazionebeneficiario character varying, codiceunitoperbeneficiario character varying, denomunitoperbeneficiario character varying, indirizzobeneficiario character varying, civicobeneficiario character varying, capbeneficiario character varying, localitabeneficiario character varying, provinciabeneficiario character varying, nazionebeneficiario character varying, tipoidentificativounivocoversante character varying, codiceidentificativounivocoversante character varying, anagraficaversante character varying, indirizzoversante character varying, civicoversante character varying, capversante character varying, localitaversante character varying, provinciaversante character varying, nazioneversante character varying, emailversante character varying, tipoidentificativounivocopagatore character varying, codiceidentificativounivocopagatore character varying, anagraficapagatore character varying, indirizzopagatore character varying, civicopagatore character varying, cappagatore character varying, localitapagatore character varying, provinciapagatore character varying, nazionepagatore character varying, emailpagatore character varying, codiceesitopagamento character varying, importototalepagato character varying, identificativounivocoversamento character varying, codicecontestopagamento character varying, singoloimportopagato character varying, esitosingolopagamento character varying, dtdataesitosingolopagamento date, dedataesitosingolopagamento character varying, identificativounivocoriscossione character varying, causaleversamento character varying, datispecificiriscossione character varying, tipodovuto character varying, identificativoflussorendicontazione character varying, dataoraflussorendicontazione character varying, identificativounivocoregolamento character varying, dtdataregolamento date, dedataregolamento character varying, numerototalepagamenti character varying, importototalepagamenti character varying, dataacquisizione character varying, codconto character varying, dtdatacontabile date, dedatacontabile character varying, dtdatavaluta date, dedatavaluta character varying, numimporto numeric, deimporto character varying, codor1 text, deannobolletta character varying, codbolletta character varying, codiddominio character varying, dtricezione timestamp without time zone, dedataricezione character varying, deannodocumento character varying, coddocumento character varying, deannoprovvisorio character varying, codprovvisorio character varying, verificatotale character varying, classificazionecompletezza character varying, dtdataultimoaggiornamento date, dedataultimoaggiornamento character varying, codiufkey character varying, codiudkey character varying, codiuvkey character varying) AS
$BODY$
  SELECT 
        a.mygov_accertamento_dettaglio_id as id_acc,
	a.mygov_accertamento_id as accertamento_id_acc,
	a.cod_iuv as codIuv_acc,
	a.cod_iuf as codIuf_acc,
	a.de_anno_bolletta as de_anno_bolletta_acc,
	a.cod_bolletta as cod_bolletta_acc,
	a.dt_data_inserimento as dt_data_inserimento_acc,
	
        codice_ipa_ente as codiceIpaEnte, 
	dt_data_esecuzione_pagamento as dtDataEsecuzionePagamento,
	de_data_esecuzione_pagamento as deDataEsecuzionePagamento, 
	singolo_importo_commissione_carico_pa as singoloImportoCommissioneCaricoPa, 
	bilancio as bilancio, 
	nome_flusso_import_ente as nomeFlussoImportEnte, 
	riga_flusso_import_ente as rigaFlussoImportEnte, 
	codice_iud as codiceIud, 
	codice_iuv as codiceIuv, 
	versione_oggetto as versioneOggetto, 
	identificativo_dominio as identificativoDominio, 
	identificativo_stazione_richiedente as identificativoStazioneRichiedente, 
	identificativo_messaggio_ricevuta as identificativoMessaggioRicevuta, 
	data_ora_messaggio_ricevuta as dataOraMessaggioRicevuta, 
	riferimento_messaggio_richiesta as riferimentoMessaggioRichiesta, 
	riferimento_data_richiesta as riferimentoDataRichiesta, 
	tipo_identificativo_univoco_attestante as tipoIdentificativoUnivocoAttestante, 
	codice_identificativo_univoco_attestante as codiceIdentificativoUnivocoAttestante, 
	denominazione_attestante as denominazioneAttestante, 
	codice_unit_oper_attestante as codiceUnitOperAttestante, 
	denom_unit_oper_attestante as denomUnitOperAttestante, 
	indirizzo_attestante as indirizzoAttestante, 
	civico_attestante as civicoAttestante, 
	cap_attestante as capAttestante, 
	localita_attestante as localitaAttestante, 
	provincia_attestante as provinciaAttestante, 
	nazione_attestante as nazioneAttestante, 
	tipo_identificativo_univoco_beneficiario as tipoIdentificativoUnivocoBeneficiario, 
	codice_identificativo_univoco_beneficiario as codiceIdentificativoUnivocoBeneficiario, 
	denominazione_beneficiario as denominazioneBeneficiario, 
	codice_unit_oper_beneficiario as codiceUnitOperBeneficiario, 
	denom_unit_oper_beneficiario as denomUnitOperBeneficiario, 
	indirizzo_beneficiario as indirizzoBeneficiario, 
	civico_beneficiario as civicoBeneficiario, 
	cap_beneficiario as capBeneficiario, 
	localita_beneficiario as localitaBeneficiario, 
	provincia_beneficiario as provinciaBeneficiario, 
	nazione_beneficiario as nazioneBeneficiario, 
	tipo_identificativo_univoco_versante AS tipoIdentificativoUnivocoVersante, 
	codice_identificativo_univoco_versante as codiceIdentificativoUnivocoVersante, 
	anagrafica_versante as anagraficaVersante, 
	indirizzo_versante as indirizzoVersante, 
	civico_versante as civicoVersante, 
	cap_versante as capVersante, 
	localita_versante as localitaVersante, 
	provincia_versante as provinciaVersante, 
	nazione_versante as nazioneVersante, 
	email_versante as emailVersante, 
	tipo_identificativo_univoco_pagatore as tipoIdentificativoUnivocoPagatore, 
	codice_identificativo_univoco_pagatore as codiceIdentificativoUnivocoPagatore, 
	anagrafica_pagatore as anagraficaPagatore, 
	indirizzo_pagatore as indirizzoPagatore, 
	civico_pagatore as civicoPagatore, 
	cap_pagatore as capPagatore, 
	localita_pagatore as localitaPagatore,
	provincia_pagatore as provinciaPagatore, 
	nazione_pagatore as nazionePagatore, 
	email_pagatore as emailPagatore, 
	codice_esito_pagamento as codiceEsitoPagamento, 
	importo_totale_pagato as importoTotalePagato, 
	identificativo_univoco_versamento AS identificativoUnivocoVersamento, 
	codice_contesto_pagamento as codiceContestoPagamento, 
	singolo_importo_pagato as singoloImportoPagato, 
	esito_singolo_pagamento as esitoSingoloPagamento, 
	dt_data_esito_singolo_pagamento as dtDataEsitoSingoloPagamento, 
	de_data_esito_singolo_pagamento as deDataEsitoSingoloPagamento, 
	identificativo_univoco_riscossione as identificativoUnivocoRiscossione, 
	causale_versamento as causaleVersamento,
	dati_specifici_riscossione as datiSpecificiRiscossione, 
	tipo_dovuto as tipoDovuto, 
	identificativo_flusso_rendicontazione as identificativoFlussoRendicontazione, 
	data_ora_flusso_rendicontazione as dataOraFlussoRendicontazione, 
	identificativo_univoco_regolamento as identificativoUnivocoRegolamento, 
	dt_data_regolamento as dtDataRegolamento, 
	de_data_regolamento as deDataRegolamento, 
	numero_totale_pagamenti as numeroTotalePagamenti, 
	importo_totale_pagamenti as importoTotalePagamenti, 
	data_acquisizione as dataAcquisizione, 
	cod_conto as codConto, 
	dt_data_contabile as dtDataContabile, 
	de_data_contabile as deDataContabile,
	dt_data_valuta as dtDataValuta, 
	de_data_valuta as deDataValuta, 
	num_importo as numImporto, 
	de_importo as deImporto, 
	cod_or1 as codOr1, 
	p.de_anno_bolletta as deAnnoBolletta, 
	p.cod_bolletta as codBolletta, 
	cod_id_dominio as codIdDominio, 
	dt_ricezione as dtRicezione,
	de_data_ricezione as deDataRicezione, 
	de_anno_documento as deAnnoDocumento, 
	cod_documento as codDocumento, 
	de_anno_provvisorio as deAnnoProvvisorio, 
	cod_provvisorio as codProvvisorio, 
	verifica_totale as verificaTotale, 
	classificazione_completezza as classificazioneCompletezza, 
	dt_data_ultimo_aggiornamento as dtDataUltimoAggiornamento, 
	de_data_ultimo_aggiornamento as deDataUltimoAggiornamento,
	cod_iuf_key as codIufKey, 
	cod_iud_key as codIudKey, 
	cod_iuv_key as codIuvKey
  FROM 
      mygov_import_export_rendicontazione_tesoreria AS p INNER JOIN mygov_accertamento_dettaglio AS a 
      ON p.cod_iuf_key = a.cod_iuf AND p.cod_iuv_key = a.cod_iuv AND p.de_anno_bolletta = a.de_anno_bolletta AND p.cod_bolletta = a.cod_bolletta
  WHERE
      /* Condizioni obbligatorie */
      a.mygov_accertamento_id = $1 AND p.codice_ipa_ente = $2 AND p.tipo_dovuto = $3 AND p.classificazione_completezza = $4 AND
      /* IUD */
      CASE WHEN ($5 IS NOT NULL) THEN p.cod_iud_key = $5 ELSE true END AND
      /* IUV */
      CASE WHEN ($6 IS NOT NULL) THEN p.cod_iuv_key = $6 ELSE true END AND
      /* Identificativo univoco pagatore */
      CASE WHEN ($7 IS NOT NULL) THEN p.codice_identificativo_univoco_pagatore = $7 ELSE true END AND
      /* Indentificativo flusso rendicontazione */
      CASE WHEN ($8 IS NOT NULL) THEN p.identificativo_flusso_rendicontazione = $8 ELSE true END AND
      /* Data esito pagamento da */
      CASE WHEN ($9 IS NOT NULL) THEN p.dt_data_esito_singolo_pagamento >= $9 ELSE true END AND
      /* Data esito pagamento a */
      CASE WHEN ($10 IS NOT NULL) THEN p.dt_data_esito_singolo_pagamento <= $10 ELSE true END AND
      /* Data regolamento da */
      CASE WHEN ($11 IS NOT NULL) THEN p.dt_data_regolamento >= $11 ELSE true END AND
      /* Data regolamento a */
      CASE WHEN ($12 IS NOT NULL) THEN p.dt_data_regolamento <= $12 ELSE true END AND
      /* Data contabile da */
      CASE WHEN ($13 IS NOT NULL) THEN p.dt_data_contabile >= $13 ELSE true END AND
      /* Data contabile a */
      CASE WHEN ($14 IS NOT NULL) THEN p.dt_data_contabile <= $14 ELSE true END AND
      /* Data valuta da */
      CASE WHEN ($15 IS NOT NULL) THEN p.dt_data_valuta >= $15 ELSE true END AND
      /* Data valuta a */
      CASE WHEN ($16 IS NOT NULL) THEN p.dt_data_valuta <= $16 ELSE true END AND
      /* Data ultimo aggiornamento da */
      CASE WHEN ($17 IS NOT NULL) THEN p.dt_data_ultimo_aggiornamento >= $17 ELSE true END AND
      /* Data ultimo aggiornamento a */
      CASE WHEN ($18 IS NOT NULL) THEN p.dt_data_ultimo_aggiornamento <= $18 ELSE true END

   ORDER BY dt_data_esito_singolo_pagamento ASC, codice_iuv ASC, codice_iud asc

   OFFSET CASE WHEN ($19) THEN $20 ELSE 0 END 

   LIMIT CASE WHEN ($19) THEN $21 ELSE null END;
   
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;


