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



CREATE OR REPLACE FUNCTION get_count_import_export_rend_tes_function(
    _cod_fed_user_id character varying,
    _codice_ipa_ente character varying,
    _cod_iud character varying,
    _cod_iuv character varying,
    _denominazione_attestante character varying,
    _identificativo_univoco_riscossione character varying,
    _codice_identificativo_univoco_versante character varying,
    _anagrafica_versante character varying,
    _codice_identificativo_univoco_pagatore character varying,
    _anagrafica_pagatore character varying,
    _causale_versamento character varying,
    _data_esecuzione_singolo_pagamento_da date,
    _data_esecuzione_singolo_pagamento_a date,
    _data_esito_singolo_pagamento_da date,
    _data_esito_singolo_pagamento_a date,
    _identificativo_flusso_rendicontazione character varying,
    _identificativo_univoco_regolamento character varying,
    _data_regolamento_da date,
    _data_regolamento_a date,
    _dt_data_contabile_da date,
    _dt_data_contabile_a date,
    _dt_data_valuta_da date,
    _dt_data_valuta_a date,
    _dt_data_ultimo_aggiornamento_da date,
    _dt_data_ultimo_aggiornamento_a date,
    _cod_tipo_dovuto character varying,
    _is_cod_tipo_dovuto_present boolean,
    _importo character varying,
    _conto character varying,
    _codor1 character varying,
    _flagnascosto boolean,
    _classificazione_completezza character varying)
  RETURNS bigint AS
$BODY$
   SELECT 
      count(1)
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.cod_iud
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms                              
     ON   ms.cod_ipa_ente = tes.codice_ipa_ente 
     AND (ms.cod_iuf IS NULL 
     AND  tes.cod_iuf_key IS NULL 
     OR   ms.cod_iuf = tes.cod_iuf_key)
     AND (ms.cod_iuv IS NULL 
     AND  tes.cod_iuv_key IS NULL 
     OR   ms.cod_iuv = tes.cod_iuv_key)
     AND (ms.cod_iud IS NULL 
     AND  tes.cod_iud_key IS NULL 
     OR   ms.cod_iud = tes.cod_iud_key)
     
   WHERE CASE WHEN (_cod_tipo_dovuto <> '') IS TRUE AND _is_cod_tipo_dovuto_present THEN tes.tipo_dovuto = _cod_tipo_dovuto 
              WHEN (_cod_tipo_dovuto <> '') IS NOT TRUE AND _is_cod_tipo_dovuto_present THEN 
                  tes.tipo_dovuto in (SELECT DISTINCT(metd.cod_tipo)
                                      FROM   mygov_operatore_ente_tipo_dovuto as moetd, mygov_ente_tipo_dovuto as metd 
                                      WHERE  moetd.mygov_ente_tipo_dovuto_id = metd.mygov_ente_tipo_dovuto_id
                                      AND    moetd.cod_fed_user_id = _cod_fed_user_id 
                                      AND   moetd.flg_attivo = true)
              ELSE true      
         END  
   AND   CASE WHEN (_codice_ipa_ente <> '') IS TRUE THEN tes.codice_ipa_ente = _codice_ipa_ente ELSE true END 
   AND   CASE WHEN (_cod_iud <> '') IS TRUE THEN tes.codice_iud = _cod_iud ELSE true END  
   AND   CASE WHEN (_cod_iuv <> '') IS TRUE THEN tes.codice_iuv = _cod_iuv ELSE true END        
   AND   CASE WHEN (_denominazione_attestante <> '') IS TRUE THEN 
                    (upper(tes.denominazione_attestante) like '%' || upper(_denominazione_attestante) || '%' 
                 OR upper(tes.codice_identificativo_univoco_attestante) like '%' || upper(_denominazione_attestante) || '%') 
         ELSE true END     
   AND   CASE WHEN (_identificativo_univoco_riscossione <> '') IS TRUE THEN tes.identificativo_univoco_riscossione = _identificativo_univoco_riscossione ELSE true END        
   AND   CASE WHEN (_codice_identificativo_univoco_versante <> '') IS TRUE THEN (tes.codice_identificativo_univoco_versante = upper(_codice_identificativo_univoco_versante) OR tes.codice_identificativo_univoco_versante = lower(_codice_identificativo_univoco_versante)) ELSE true END        
   AND   CASE WHEN (_anagrafica_versante <> '') IS TRUE THEN upper(tes.anagrafica_versante) like '%' || upper(_anagrafica_versante) || '%' ELSE true END              
   AND   CASE WHEN (_codice_identificativo_univoco_pagatore <> '') IS TRUE THEN (tes.codice_identificativo_univoco_pagatore = upper(_codice_identificativo_univoco_pagatore) OR tes.codice_identificativo_univoco_pagatore = lower(_codice_identificativo_univoco_pagatore)) ELSE true END           
   AND   CASE WHEN (_anagrafica_pagatore <> '') IS TRUE THEN upper(tes.anagrafica_pagatore) like '%' || upper(_anagrafica_pagatore) || '%' ELSE true END                  
   AND   CASE WHEN (_causale_versamento <> '') IS TRUE THEN upper(tes.causale_versamento) like '%' || upper(_causale_versamento) || '%' ELSE true END                    
   AND   CASE WHEN _data_esecuzione_singolo_pagamento_da IS NOT NULL THEN tes.dt_data_esecuzione_pagamento >= _data_esecuzione_singolo_pagamento_da ELSE true END                       
   AND   CASE WHEN _data_esecuzione_singolo_pagamento_a IS NOT NULL THEN tes.dt_data_esecuzione_pagamento <= _data_esecuzione_singolo_pagamento_a ELSE true END                     
   AND   CASE WHEN _data_esito_singolo_pagamento_da IS NOT NULL THEN tes.dt_data_esito_singolo_pagamento >= _data_esito_singolo_pagamento_da ELSE true END                       
   AND   CASE WHEN _data_esito_singolo_pagamento_a IS NOT NULL THEN tes.dt_data_esito_singolo_pagamento <= _data_esito_singolo_pagamento_a ELSE true END             
   AND   CASE WHEN (_identificativo_flusso_rendicontazione <> '') IS TRUE THEN tes.identificativo_flusso_rendicontazione = _identificativo_flusso_rendicontazione ELSE true END           
   AND   CASE WHEN (_identificativo_univoco_regolamento <> '') IS TRUE THEN tes.identificativo_univoco_regolamento = _identificativo_univoco_regolamento ELSE true END           
   AND   CASE WHEN _data_regolamento_da IS NOT NULL THEN tes.dt_data_regolamento >= _data_regolamento_da ELSE true END                       
   AND   CASE WHEN _data_regolamento_a IS NOT NULL THEN tes.dt_data_regolamento <= _data_regolamento_a ELSE true END             
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN tes.dt_data_contabile >= _dt_data_contabile_da ELSE true END                       
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN tes.dt_data_contabile <= _dt_data_contabile_a ELSE true END             
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN tes.dt_data_valuta >= _dt_data_valuta_da ELSE true END                      
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN tes.dt_data_valuta <= _dt_data_valuta_a ELSE true END              
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END                       
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END             
   AND   CASE WHEN (_importo <> '') IS TRUE THEN tes.de_importo = _importo ELSE true END                   
   AND   CASE WHEN (_conto <> '') IS TRUE THEN tes.cod_conto = _conto ELSE true END                 
   AND   CASE WHEN (_codOr1 <> '') IS TRUE THEN upper(tes.cod_or1)  like '%' || upper(_codOr1) || '%' ELSE true END                                                            
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END                    
   AND   CASE WHEN (_classificazione_completezza <> '') IS TRUE THEN tes.classificazione_completezza = _classificazione_completezza ELSE true END;
$BODY$
  LANGUAGE sql STABLE
  COST 100;
  
CREATE OR REPLACE FUNCTION get_import_export_rend_tes_function(
    _cod_fed_user_id character varying,
    _codice_ipa_ente character varying,
    _cod_iud character varying,
    _cod_iuv character varying,
    _denominazione_attestante character varying,
    _identificativo_univoco_riscossione character varying,
    _codice_identificativo_univoco_versante character varying,
    _anagrafica_versante character varying,
    _codice_identificativo_univoco_pagatore character varying,
    _anagrafica_pagatore character varying,
    _causale_versamento character varying,
    _data_esecuzione_singolo_pagamento_da date,
    _data_esecuzione_singolo_pagamento_a date,
    _data_esito_singolo_pagamento_da date,
    _data_esito_singolo_pagamento_a date,
    _identificativo_flusso_rendicontazione character varying,
    _identificativo_univoco_regolamento character varying,
    _data_regolamento_da date,
    _data_regolamento_a date,
    _dt_data_contabile_da date,
    _dt_data_contabile_a date,
    _dt_data_valuta_da date,
    _dt_data_valuta_a date,
    _dt_data_ultimo_aggiornamento_da date,
    _dt_data_ultimo_aggiornamento_a date,
    _cod_tipo_dovuto character varying,
    _is_cod_tipo_dovuto_present boolean,
    _importo character varying,
    _conto character varying,
    _codor1 character varying,
    _flagnascosto boolean,
    _classificazione_completezza character varying,
    _page integer,
    _size integer)
  RETURNS SETOF mygov_import_export_rendicontazione_tesoreria AS
$BODY$
   SELECT 
      tes.*
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.cod_iud
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms                              
     ON   ms.cod_ipa_ente = tes.codice_ipa_ente 
     AND (ms.cod_iuf IS NULL 
     AND  tes.cod_iuf_key IS NULL 
     OR   ms.cod_iuf = tes.cod_iuf_key)
     AND (ms.cod_iuv IS NULL 
     AND  tes.cod_iuv_key IS NULL 
     OR   ms.cod_iuv = tes.cod_iuv_key)
     AND (ms.cod_iud IS NULL 
     AND  tes.cod_iud_key IS NULL 
     OR   ms.cod_iud = tes.cod_iud_key)
     
   WHERE CASE WHEN (_cod_tipo_dovuto <> '') IS TRUE AND _is_cod_tipo_dovuto_present THEN tes.tipo_dovuto = _cod_tipo_dovuto 
              WHEN (_cod_tipo_dovuto <> '') IS NOT TRUE AND _is_cod_tipo_dovuto_present THEN 
                  tes.tipo_dovuto in (SELECT DISTINCT(metd.cod_tipo)
                                      FROM   mygov_operatore_ente_tipo_dovuto as moetd, mygov_ente_tipo_dovuto as metd 
                                      WHERE  moetd.mygov_ente_tipo_dovuto_id = metd.mygov_ente_tipo_dovuto_id
                                      AND    moetd.cod_fed_user_id = _cod_fed_user_id 
                                      AND   moetd.flg_attivo = true)
              ELSE true      
         END  
   AND   CASE WHEN (_codice_ipa_ente <> '') IS TRUE THEN tes.codice_ipa_ente = _codice_ipa_ente ELSE true END 
   AND   CASE WHEN (_cod_iud <> '') IS TRUE THEN tes.codice_iud = _cod_iud ELSE true END  
   AND   CASE WHEN (_cod_iuv <> '') IS TRUE THEN tes.codice_iuv = _cod_iuv ELSE true END        
   AND   CASE WHEN (_denominazione_attestante <> '') IS TRUE THEN 
                    (upper(tes.denominazione_attestante) like '%' || upper(_denominazione_attestante) || '%' 
                 OR upper(tes.codice_identificativo_univoco_attestante) like '%' || upper(_denominazione_attestante) || '%') 
         ELSE true END     
   AND   CASE WHEN (_identificativo_univoco_riscossione <> '') IS TRUE THEN tes.identificativo_univoco_riscossione = _identificativo_univoco_riscossione ELSE true END        
   AND   CASE WHEN (_codice_identificativo_univoco_versante <> '') IS TRUE THEN (tes.codice_identificativo_univoco_versante = upper(_codice_identificativo_univoco_versante) OR tes.codice_identificativo_univoco_versante = lower(_codice_identificativo_univoco_versante)) ELSE true END        
   AND   CASE WHEN (_anagrafica_versante <> '') IS TRUE THEN upper(tes.anagrafica_versante) like '%' || upper(_anagrafica_versante) || '%' ELSE true END              
   AND   CASE WHEN (_codice_identificativo_univoco_pagatore <> '') IS TRUE THEN (tes.codice_identificativo_univoco_pagatore = upper(_codice_identificativo_univoco_pagatore) OR tes.codice_identificativo_univoco_pagatore = lower(_codice_identificativo_univoco_pagatore)) ELSE true END           
   AND   CASE WHEN (_anagrafica_pagatore <> '') IS TRUE THEN upper(tes.anagrafica_pagatore) like '%' || upper(_anagrafica_pagatore) || '%' ELSE true END                  
   AND   CASE WHEN (_causale_versamento <> '') IS TRUE THEN upper(tes.causale_versamento) like '%' || upper(_causale_versamento) || '%' ELSE true END                    
   AND   CASE WHEN _data_esecuzione_singolo_pagamento_da IS NOT NULL THEN tes.dt_data_esecuzione_pagamento >= _data_esecuzione_singolo_pagamento_da ELSE true END                       
   AND   CASE WHEN _data_esecuzione_singolo_pagamento_a IS NOT NULL THEN tes.dt_data_esecuzione_pagamento <= _data_esecuzione_singolo_pagamento_a ELSE true END                     
   AND   CASE WHEN _data_esito_singolo_pagamento_da IS NOT NULL THEN tes.dt_data_esito_singolo_pagamento >= _data_esito_singolo_pagamento_da ELSE true END                       
   AND   CASE WHEN _data_esito_singolo_pagamento_a IS NOT NULL THEN tes.dt_data_esito_singolo_pagamento <= _data_esito_singolo_pagamento_a ELSE true END             
   AND   CASE WHEN (_identificativo_flusso_rendicontazione <> '') IS TRUE THEN tes.identificativo_flusso_rendicontazione = _identificativo_flusso_rendicontazione ELSE true END           
   AND   CASE WHEN (_identificativo_univoco_regolamento <> '') IS TRUE THEN tes.identificativo_univoco_regolamento = _identificativo_univoco_regolamento ELSE true END           
   AND   CASE WHEN _data_regolamento_da IS NOT NULL THEN tes.dt_data_regolamento >= _data_regolamento_da ELSE true END                       
   AND   CASE WHEN _data_regolamento_a IS NOT NULL THEN tes.dt_data_regolamento <= _data_regolamento_a ELSE true END             
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN tes.dt_data_contabile >= _dt_data_contabile_da ELSE true END                       
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN tes.dt_data_contabile <= _dt_data_contabile_a ELSE true END             
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN tes.dt_data_valuta >= _dt_data_valuta_da ELSE true END                      
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN tes.dt_data_valuta <= _dt_data_valuta_a ELSE true END              
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END                       
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END             
   AND   CASE WHEN (_importo <> '') IS TRUE THEN tes.de_importo = _importo ELSE true END                   
   AND   CASE WHEN (_conto <> '') IS TRUE THEN tes.cod_conto = _conto ELSE true END                 
   AND   CASE WHEN (_codOr1 <> '') IS TRUE THEN upper(tes.cod_or1)  like '%' || upper(_codOr1) || '%' ELSE true END                                                            
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END                    
   AND   CASE WHEN (_classificazione_completezza <> '') IS TRUE THEN tes.classificazione_completezza = _classificazione_completezza ELSE true END     
         

   
   ORDER BY CASE WHEN _classificazione_completezza = 'IUD_RT_IUF_TES' OR 
                      _classificazione_completezza = 'RT_IUF_TES' OR
                      _classificazione_completezza = 'RT_IUF' OR
                      _classificazione_completezza = 'IUD_RT_IUF' OR 
                      _classificazione_completezza = 'RT_NO_IUF' OR
                      _classificazione_completezza = 'RT_NO_IUD' THEN (dt_data_esito_singolo_pagamento, codice_iuv, codice_iud)                         
                 WHEN _classificazione_completezza = 'IUD_NO_RT' THEN (dt_data_esecuzione_pagamento, codice_iud)  
                ELSE (dt_data_esito_singolo_pagamento, codice_iuv, codice_iud)
            END 

   OFFSET CASE WHEN (_page IS NOT NULL) THEN ((_page - 1)*_size) ELSE 0 END 
   LIMIT CASE WHEN (_size IS NOT NULL) THEN _size ELSE 5 END;
$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;
  
CREATE OR REPLACE FUNCTION get_count_tesoreria_subset_function(
    _codice_ipa_ente character varying,
    _dt_data_contabile_da date,
    _dt_data_contabile_a date,
    _dt_data_valuta_da date,
    _dt_data_valuta_a date,
    _dt_data_ultimo_aggiornamento_da date,
    _dt_data_ultimo_aggiornamento_a date,
    _importo character varying,
    _conto character varying,
    _codor1 character varying,
    _flagnascosto boolean,
    _classificazione_completezza character varying)
  RETURNS bigint AS
$BODY$
   SELECT COUNT( DISTINCT(tes.codice_ipa_ente))
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms                              
     ON   ms.cod_ipa_ente = tes.codice_ipa_ente 
    AND (ms.cod_iuf IS NULL 
    AND  tes.cod_iuf_key IS NULL 
    OR   ms.cod_iuf = tes.cod_iuf_key)
    AND (ms.cod_iuv IS NULL 
    AND  tes.cod_iuv_key IS NULL 
    OR   ms.cod_iuv = tes.cod_iuv_key)
     
   WHERE CASE WHEN (_codice_ipa_ente <> '') IS TRUE THEN tes.codice_ipa_ente = _codice_ipa_ente ELSE true END     
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN tes.dt_data_contabile >= _dt_data_contabile_da ELSE true END                       
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN tes.dt_data_contabile <= _dt_data_contabile_a ELSE true END         
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN tes.dt_data_valuta >= _dt_data_valuta_da ELSE true END                      
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN tes.dt_data_valuta <= _dt_data_valuta_a ELSE true END              
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END                       
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END             
   AND   CASE WHEN (_importo <> '') IS TRUE THEN tes.de_importo = _importo ELSE true END                   
   AND   CASE WHEN (_conto <> '') IS TRUE THEN tes.cod_conto = _conto ELSE true END                 
   AND   CASE WHEN (_codOr1 <> '') IS TRUE THEN upper(tes.cod_or1) like '%' || upper(_codOr1) || '%' ELSE true END                                                            
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END                    
   AND   CASE WHEN (_classificazione_completezza <> '') IS TRUE THEN tes.classificazione_completezza = _classificazione_completezza ELSE true END;
$BODY$
  LANGUAGE sql STABLE
  COST 100;
  
CREATE OR REPLACE FUNCTION get_tesoreria_subset_function(
    IN _codice_ipa_ente character varying,
    IN _dt_data_contabile_da date,
    IN _dt_data_contabile_a date,
    IN _dt_data_valuta_da date,
    IN _dt_data_valuta_a date,
    IN _dt_data_ultimo_aggiornamento_da date,
    IN _dt_data_ultimo_aggiornamento_a date,
    IN _importo character varying,
    IN _conto character varying,
    IN _codor1 character varying,
    IN _flagnascosto boolean,
    IN _classificazione_completezza character varying,
    IN _page integer,
    IN _size integer)
  RETURNS TABLE(codice_ipa_ente character varying, codice_iuv character varying, identificativo_flusso_rendicontazione character varying, dt_data_esecuzione_pagamento date, de_data_esecuzione_pagamento character varying, singolo_importo_commissione_carico_pa character varying, bilancio character varying, cod_conto character varying, dt_data_contabile date, de_data_contabile character varying, dt_data_valuta date, de_data_valuta character varying, num_importo numeric, de_importo character varying, cod_or1 text, de_anno_bolletta character varying, cod_bolletta character varying, cod_id_dominio character varying, dt_ricezione timestamp without time zone, de_data_ricezione character varying, de_anno_documento character varying, cod_documento character varying, de_anno_provvisorio character varying, cod_provvisorio character varying, classificazione_completezza character varying, dt_data_ultimo_aggiornamento date, de_data_ultimo_aggiornamento character varying, cod_iuf_key character varying, cod_iuv_key character varying) AS
$BODY$
   SELECT 
        DISTINCT (tes.codice_ipa_ente),
  tes.codice_iuv,
  tes.identificativo_flusso_rendicontazione,
  tes.dt_data_esecuzione_pagamento,
  tes.de_data_esecuzione_pagamento,
  tes.singolo_importo_commissione_carico_pa,
  tes.bilancio,
  tes.cod_conto,
  tes.dt_data_contabile,
  tes.de_data_contabile,
        tes.dt_data_valuta,
        tes.de_data_valuta,
  tes.num_importo,
  tes.de_importo,
  tes.cod_or1,
  tes.de_anno_bolletta,
  tes.cod_bolletta,
  tes.cod_id_dominio,
  tes.dt_ricezione,
  tes.de_data_ricezione,
  tes.de_anno_documento,
  tes.cod_documento,
  tes.de_anno_provvisorio,
  tes.cod_provvisorio,
  tes.classificazione_completezza,
  tes.dt_data_ultimo_aggiornamento,
  tes.de_data_ultimo_aggiornamento,
  tes.cod_iuf_key,
  tes.cod_iuv_key
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms                              
     ON   ms.cod_ipa_ente = tes.codice_ipa_ente 
    AND (ms.cod_iuf IS NULL 
    AND  tes.cod_iuf_key IS NULL 
    OR   ms.cod_iuf = tes.cod_iuf_key)
    AND (ms.cod_iuv IS NULL 
    AND  tes.cod_iuv_key IS NULL 
    OR   ms.cod_iuv = tes.cod_iuv_key)
     
   WHERE CASE WHEN (_codice_ipa_ente <> '') IS TRUE THEN tes.codice_ipa_ente = _codice_ipa_ente ELSE true END     
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN tes.dt_data_contabile >= _dt_data_contabile_da ELSE true END                       
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN tes.dt_data_contabile <= _dt_data_contabile_a ELSE true END         
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN tes.dt_data_valuta >= _dt_data_valuta_da ELSE true END                      
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN tes.dt_data_valuta <= _dt_data_valuta_a ELSE true END              
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END                       
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END             
   AND   CASE WHEN (_importo <> '') IS TRUE THEN tes.de_importo = _importo ELSE true END                   
   AND   CASE WHEN (_conto <> '') IS TRUE THEN tes.cod_conto = _conto ELSE true END                 
   AND   CASE WHEN (_codOr1 <> '') IS TRUE THEN upper(tes.cod_or1) like '%' || upper(_codOr1) || '%' ELSE true END                                                           
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END                    
   AND   CASE WHEN (_classificazione_completezza <> '') IS TRUE THEN tes.classificazione_completezza = _classificazione_completezza ELSE true END   
      
   ORDER BY tes.dt_data_valuta, tes.identificativo_flusso_rendicontazione, tes.codice_iuv
   OFFSET CASE WHEN (_page IS NOT NULL) THEN ((_page - 1)*_size) ELSE 0 END 
   LIMIT CASE WHEN (_size IS NOT NULL) THEN _size ELSE 5 END;
$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;
  

CREATE OR REPLACE FUNCTION get_count_tesoreria_no_match_subset_function(_codice_ipa_ente character varying, _dt_data_contabile_da date, _dt_data_contabile_a date, _dt_data_valuta_da date, _dt_data_valuta_a date, _dt_data_ultimo_aggiornamento_da date, _dt_data_ultimo_aggiornamento_a date, _importo character varying, _conto character varying, _codor1 character varying, _flagnascosto boolean, _classificazione_completezza character varying)
  RETURNS bigint AS
$BODY$
   SELECT COUNT( DISTINCT(tes.codice_ipa_ente, tes.de_anno_bolletta, tes.cod_bolletta))
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms                              
     ON   ms.cod_ipa_ente = tes.codice_ipa_ente 
    AND (ms.cod_iuf IS NULL 
    AND  tes.cod_iuf_key IS NULL 
    OR   ms.cod_iuf = tes.cod_iuf_key)
    AND (ms.cod_iuv IS NULL 
    AND  tes.cod_iuv_key IS NULL 
    OR   ms.cod_iuv = tes.cod_iuv_key)
     
   WHERE CASE WHEN (_codice_ipa_ente <> '') IS TRUE THEN tes.codice_ipa_ente = _codice_ipa_ente ELSE true END     
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN tes.dt_data_contabile >= _dt_data_contabile_da ELSE true END                       
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN tes.dt_data_contabile <= _dt_data_contabile_a ELSE true END         
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN tes.dt_data_valuta >= _dt_data_valuta_da ELSE true END                      
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN tes.dt_data_valuta <= _dt_data_valuta_a ELSE true END              
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END                       
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END             
   AND   CASE WHEN (_importo <> '') IS TRUE THEN tes.de_importo = _importo ELSE true END                   
   AND   CASE WHEN (_conto <> '') IS TRUE THEN tes.cod_conto = _conto ELSE true END                 
   AND   CASE WHEN (_codOr1 <> '') IS TRUE THEN upper(tes.cod_or1) like '%' || upper(_codOr1) || '%' ELSE true END                                                            
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END                    
   AND   CASE WHEN (_classificazione_completezza <> '') IS TRUE THEN tes.classificazione_completezza = _classificazione_completezza ELSE true END;
$BODY$
  LANGUAGE sql STABLE
  COST 100;


DROP FUNCTION get_count_import_export_rend_tes_function(character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, date, date, date, date, character varying, character varying, date, date, date, date, date, date, date, date, character varying, boolean, character varying, character varying, character varying, boolean, character varying);

DROP FUNCTION get_count_pagamenti_inseribili_in_accertamento(character varying, character varying, character varying, character varying, character varying, character varying, character varying, date, date, date, date, date, date, date, date, date, date);

DROP FUNCTION get_count_pagamenti_inseriti_in_accertamento(bigint, character varying, character varying, character varying, character varying, character varying, character varying, character varying, date, date, date, date, date, date, date, date, date, date);

DROP FUNCTION get_count_rendicontazione_subset_function(character varying, character varying, character varying, date, date, date, date, character varying, character varying, character varying, boolean);

DROP FUNCTION get_count_tesoreria_no_match_subset_function(character varying, date, date, date, date, date, date, character varying, character varying, character varying, boolean, character varying);

DROP FUNCTION get_count_tesoreria_subset_function(character varying, date, date, date, date, date, date, character varying, character varying, character varying, boolean, character varying);

DROP FUNCTION get_import_export_rend_tes_function(character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, date, date, date, date, character varying, character varying, date, date, date, date, date, date, date, date, character varying, boolean, character varying, character varying, character varying, boolean, character varying, integer, integer);

DROP FUNCTION get_pagamenti_inseribili_in_accertamento(character varying, character varying, character varying, character varying, character varying, character varying, character varying, date, date, date, date, date, date, date, date, date, date, boolean, integer, integer);

DROP FUNCTION get_pagamenti_inseriti_in_accertamento(bigint, character varying, character varying, character varying, character varying, character varying, character varying, character varying, date, date, date, date, date, date, date, date, date, date, boolean, integer, integer);

DROP FUNCTION get_rendicontazione_subset_function(character varying, character varying, character varying, date, date, date, date, character varying, character varying, character varying, boolean, integer, integer);

DROP FUNCTION get_tesoreria_subset_function(character varying, date, date, date, date, date, date, character varying, character varying, character varying, boolean, character varying, integer, integer);

DROP VIEW v_mygov_import_export_rendicontazione_tesoreria;

CREATE OR REPLACE VIEW v_mygov_import_export_rendicontazione_tesoreria AS 
 SELECT ente.cod_ipa_ente AS codice_ipa_ente,
    import_export_rendicontazione_tesoreria.dt_rp_dati_vers_data_esecuzione_pagamento_i AS dt_data_esecuzione_pagamento,
    COALESCE(to_char(import_export_rendicontazione_tesoreria.dt_rp_dati_vers_data_esecuzione_pagamento_i::timestamp with time zone, 'dd-MM-yyyy'::text), 'n/a'::text)::character varying(10) AS de_data_esecuzione_pagamento,
    COALESCE(import_export_rendicontazione_tesoreria.num_rp_dati_vers_dati_sing_vers_commissione_carico_pa_i::character varying(15), ''::character varying)::character varying(15) AS singolo_importo_commissione_carico_pa,
    COALESCE(import_export_rendicontazione_tesoreria.bilancio_i, ''::character varying)::character varying(4096) AS bilancio,
    COALESCE(import_export_rendicontazione_tesoreria.de_nome_flusso_e, 'n/a'::character varying)::character varying(50) AS nome_flusso_import_ente,
    COALESCE(import_export_rendicontazione_tesoreria.num_riga_flusso_e::character varying(12), 'n/a'::character varying)::character varying(12) AS riga_flusso_import_ente,
    COALESCE(import_export_rendicontazione_tesoreria.cod_iud_i, import_export_rendicontazione_tesoreria.cod_iud_e, 'n/a'::character varying)::character varying(35) AS codice_iud,
    COALESCE(import_export_rendicontazione_tesoreria.cod_rp_silinviarp_id_univoco_versamento_i, import_export_rendicontazione_tesoreria.cod_rp_silinviarp_id_univoco_versamento_e, import_export_rendicontazione_tesoreria.cod_dati_sing_pagam_identificativo_univoco_versamento_r, import_export_rendicontazione_tesoreria.cod_id_univoco_versamento_t, 'n/a'::character varying)::character varying(35) AS codice_iuv,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_versione_oggetto_e, ''::character varying)::character varying(16) AS versione_oggetto,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_dom_id_dominio_e, ''::character varying)::character varying(35) AS identificativo_dominio,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_dom_id_stazione_richiedente_e, ''::character varying)::character varying(35) AS identificativo_stazione_richiedente,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_id_messaggio_ricevuta_e, ''::character varying)::character varying(35) AS identificativo_messaggio_ricevuta,
    COALESCE(to_char(import_export_rendicontazione_tesoreria.dt_e_data_ora_messaggio_ricevuta_e, 'dd-MM-yyyy HH:mm:ss'::text), ''::text)::character varying(19) AS data_ora_messaggio_ricevuta,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_riferimento_messaggio_richiesta_e, ''::character varying)::character varying(35) AS riferimento_messaggio_richiesta,
    COALESCE(to_char(import_export_rendicontazione_tesoreria.dt_e_riferimento_data_richiesta_e::timestamp with time zone, 'dd-MM-yyyy'::text), ''::text)::character varying(10) AS riferimento_data_richiesta,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_istit_att_id_univ_att_tipo_id_univoco_e, ''::bpchar)::character varying(1) AS tipo_identificativo_univoco_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_istit_att_id_univ_att_codice_id_univoco_e, ''::character varying)::character varying(35) AS codice_identificativo_univoco_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_denominazione_attestante_e, ''::character varying)::character varying(70) AS denominazione_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_istit_att_codice_unit_oper_attestante_e, ''::character varying)::character varying(35) AS codice_unit_oper_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_denom_unit_oper_attestante_e, ''::character varying)::character varying(70) AS denom_unit_oper_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_indirizzo_attestante_e, ''::character varying)::character varying(70) AS indirizzo_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_civico_attestante_e, ''::character varying)::character varying(16) AS civico_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_istit_att_cap_attestante_e, ''::character varying)::character varying(16) AS cap_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_localita_attestante_e, ''::character varying)::character varying(35) AS localita_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_provincia_attestante_e, ''::character varying)::character varying(35) AS provincia_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_istit_att_nazione_attestante_e, ''::character varying)::character varying(2) AS nazione_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_ente_benef_id_univ_benef_tipo_id_univoco_e, ''::bpchar)::character varying(1) AS tipo_identificativo_univoco_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_ente_benef_id_univ_benef_codice_id_univoco_e, ''::character varying)::character varying(35) AS codice_identificativo_univoco_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_denominazione_beneficiario_e, ''::character varying)::character varying(70) AS denominazione_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_ente_benef_codice_unit_oper_beneficiario_e, ''::character varying)::character varying(35) AS codice_unit_oper_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_denom_unit_oper_beneficiario_e, ''::character varying)::character varying(70) AS denom_unit_oper_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_indirizzo_beneficiario_e, ''::character varying)::character varying(70) AS indirizzo_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_civico_beneficiario_e, ''::character varying)::character varying(16) AS civico_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_ente_benef_cap_beneficiario_e, ''::character varying)::character varying(16) AS cap_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_localita_beneficiario_e, ''::character varying)::character varying(35) AS localita_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_provincia_beneficiario_e, ''::character varying)::character varying(35) AS provincia_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_ente_benef_nazione_beneficiario_e, ''::character varying)::character varying(2) AS nazione_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_sogg_vers_id_univ_vers_tipo_id_univoco_e, ''::bpchar)::character varying(1) AS tipo_identificativo_univoco_versante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_sogg_vers_id_univ_vers_codice_id_univoco_e, ''::character varying)::character varying(35) AS codice_identificativo_univoco_versante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_sogg_vers_anagrafica_versante_e, ''::character varying)::character varying(70) AS anagrafica_versante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_sogg_vers_indirizzo_versante_e, ''::character varying)::character varying(70) AS indirizzo_versante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_sogg_vers_civico_versante_e, ''::character varying)::character varying(16) AS civico_versante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_sogg_vers_cap_versante_e, ''::character varying)::character varying(16) AS cap_versante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_sogg_vers_localita_versante_e, ''::character varying)::character varying(35) AS localita_versante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_sogg_vers_provincia_versante_e, ''::character varying)::character varying(35) AS provincia_versante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_sogg_vers_nazione_versante_e, ''::character varying)::character varying(2) AS nazione_versante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_sogg_vers_email_versante_e, ''::character varying)::character varying(256) AS email_versante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_rp_sogg_pag_id_univ_pag_tipo_id_univoco_i, import_export_rendicontazione_tesoreria.cod_e_sogg_pag_id_univ_pag_tipo_id_univoco_e, ''::bpchar)::character varying(1) AS tipo_identificativo_univoco_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.cod_rp_sogg_pag_id_univ_pag_codice_id_univoco_i, import_export_rendicontazione_tesoreria.cod_e_sogg_pag_id_univ_pag_codice_id_univoco_e, ''::character varying)::character varying(35) AS codice_identificativo_univoco_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_anagrafica_pagatore_i, import_export_rendicontazione_tesoreria.cod_e_sogg_pag_anagrafica_pagatore_e, ''::character varying)::character varying(70) AS anagrafica_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_indirizzo_pagatore_i, import_export_rendicontazione_tesoreria.de_e_sogg_pag_indirizzo_pagatore_e, ''::character varying)::character varying(70) AS indirizzo_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_civico_pagatore_i, import_export_rendicontazione_tesoreria.de_e_sogg_pag_civico_pagatore_e, ''::character varying)::character varying(16) AS civico_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.cod_rp_sogg_pag_cap_pagatore_i, import_export_rendicontazione_tesoreria.cod_e_sogg_pag_cap_pagatore_e, ''::character varying)::character varying(16) AS cap_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_localita_pagatore_i, import_export_rendicontazione_tesoreria.de_e_sogg_pag_localita_pagatore_e, ''::character varying)::character varying(35) AS localita_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_provincia_pagatore_i, import_export_rendicontazione_tesoreria.de_e_sogg_pag_provincia_pagatore_e, ''::character varying)::character varying(35) AS provincia_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.cod_rp_sogg_pag_nazione_pagatore_i, import_export_rendicontazione_tesoreria.cod_e_sogg_pag_nazione_pagatore_e, ''::character varying)::character varying(2) AS nazione_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_email_pagatore_i, import_export_rendicontazione_tesoreria.de_e_sogg_pag_email_pagatore_e, ''::character varying)::character varying(256) AS email_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_dati_pag_codice_esito_pagamento_e, ''::bpchar)::character varying(1) AS codice_esito_pagamento,
    COALESCE(import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e::character varying(15), ''::character varying)::character varying(15) AS importo_totale_pagato,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_dati_pag_id_univoco_versamento_e, ''::character varying)::character varying(35) AS identificativo_univoco_versamento,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_dati_pag_codice_contesto_pagamento_e, ''::character varying)::character varying(35) AS codice_contesto_pagamento,
    COALESCE(import_export_rendicontazione_tesoreria.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento_i::character varying(15), import_export_rendicontazione_tesoreria.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato_e::character varying(15), import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r::character varying(15), ''::character varying)::character varying(15) AS singolo_importo_pagato,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_dati_pag_dati_sing_pag_esito_singolo_pagamento_e, import_export_rendicontazione_tesoreria.cod_dati_sing_pagam_codice_esito_singolo_pagamento_r, ''::character varying)::character varying(35) AS esito_singolo_pagamento,
    COALESCE(import_export_rendicontazione_tesoreria.dt_dati_sing_pagam_data_esito_singolo_pagamento_r, import_export_rendicontazione_tesoreria.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e) AS dt_data_esito_singolo_pagamento,
    COALESCE(to_char(COALESCE(import_export_rendicontazione_tesoreria.dt_dati_sing_pagam_data_esito_singolo_pagamento_r, import_export_rendicontazione_tesoreria.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e)::timestamp with time zone, 'dd-MM-yyyy'::text), 'n/a'::text)::character varying(10) AS de_data_esito_singolo_pagamento,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss_e, import_export_rendicontazione_tesoreria.cod_dati_sing_pagam_identificativo_univoco_riscossione_r, 'n/a'::character varying)::character varying(35) AS identificativo_univoco_riscossione,
    COALESCE(import_export_rendicontazione_tesoreria.de_rp_dati_vers_dati_sing_vers_causale_versamento_i, import_export_rendicontazione_tesoreria.de_e_dati_pag_dati_sing_pag_causale_versamento_e, ''::character varying)::character varying(1024) AS causale_versamento,
    COALESCE(import_export_rendicontazione_tesoreria.de_rp_dati_vers_dati_sing_vers_dati_specifici_riscossione_i, import_export_rendicontazione_tesoreria.de_e_dati_pag_dati_sing_pag_dati_specifici_riscossione_e, ''::character varying)::character varying(140) AS dati_specifici_riscossione,
    COALESCE(import_export_rendicontazione_tesoreria.cod_tipo_dovuto_i, import_export_rendicontazione_tesoreria.cod_tipo_dovuto_e, ''::character varying)::character varying(64) AS tipo_dovuto,
    COALESCE(import_export_rendicontazione_tesoreria.cod_identificativo_flusso_r, import_export_rendicontazione_tesoreria.cod_id_univoco_flusso_t, 'n/a'::character varying)::character varying(35) AS identificativo_flusso_rendicontazione,
    COALESCE(to_char(import_export_rendicontazione_tesoreria.dt_data_ora_flusso_r, 'dd-MM-yyyy HH:mm:ss'::text), 'n/a'::text)::character varying(19) AS data_ora_flusso_rendicontazione,
    COALESCE(import_export_rendicontazione_tesoreria.cod_identificativo_univoco_regolamento_r, 'n/a'::character varying)::character varying(35) AS identificativo_univoco_regolamento,
    COALESCE(import_export_rendicontazione_tesoreria.dt_data_regolamento_r, import_export_rendicontazione_tesoreria.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e + ente.num_giorni_pagamento_presunti) AS dt_data_regolamento,
    COALESCE(to_char(COALESCE(import_export_rendicontazione_tesoreria.dt_data_regolamento_r, import_export_rendicontazione_tesoreria.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e + ente.num_giorni_pagamento_presunti)::timestamp with time zone, 'dd-MM-yyyy'::text)::character varying(10), 'n/a'::character varying) AS de_data_regolamento,
    COALESCE(import_export_rendicontazione_tesoreria.num_numero_totale_pagamenti_r::character varying(15), 'n/a'::character varying)::character varying(15) AS numero_totale_pagamenti,
    COALESCE(import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r::character varying(15), 'n/a'::character varying)::character varying(21) AS importo_totale_pagamenti,
    to_char(GREATEST(import_export_rendicontazione_tesoreria.dt_acquisizione_r, import_export_rendicontazione_tesoreria.dt_acquisizione_e, import_export_rendicontazione_tesoreria.dt_acquisizione_t)::timestamp with time zone, 'dd-MM-yyyy'::text)::character varying(10) AS data_acquisizione,
    COALESCE(import_export_rendicontazione_tesoreria.cod_conto_t, ''::character varying::bpchar)::character varying(12) AS cod_conto,
    import_export_rendicontazione_tesoreria.dt_data_contabile_t AS dt_data_contabile,
    COALESCE(to_char(import_export_rendicontazione_tesoreria.dt_data_contabile_t::timestamp with time zone, 'dd-MM-yyyy'::text), ''::character varying::text)::character varying(10) AS de_data_contabile,
    import_export_rendicontazione_tesoreria.dt_data_valuta_t AS dt_data_valuta,
    COALESCE(to_char(import_export_rendicontazione_tesoreria.dt_data_valuta_t::timestamp with time zone, 'dd-MM-yyyy'::text), ''::character varying::text)::character varying(10) AS de_data_valuta,
    import_export_rendicontazione_tesoreria.num_importo_t AS num_importo,
    COALESCE(import_export_rendicontazione_tesoreria.num_importo_t::character(15), 'n/a'::character varying::bpchar)::character varying(15) AS de_importo,
    COALESCE(import_export_rendicontazione_tesoreria.cod_or1_t, ''::text) AS cod_or1,
    COALESCE(import_export_rendicontazione_tesoreria.de_anno_bolletta_t, 'n/a'::text::character varying) AS de_anno_bolletta,
    COALESCE(import_export_rendicontazione_tesoreria.cod_bolletta_t, 'n/a'::text::character varying) AS cod_bolletta,
    COALESCE(import_export_rendicontazione_tesoreria.cod_id_dominio_t, 'n/a'::text::character varying) AS cod_id_dominio,
    import_export_rendicontazione_tesoreria.dt_ricezione_t AS dt_ricezione,
    COALESCE(to_char(import_export_rendicontazione_tesoreria.dt_ricezione_t::timestamp with time zone, 'dd-MM-yyyy'::text), 'n/a'::character varying::text)::character varying(10) AS de_data_ricezione,
    COALESCE(import_export_rendicontazione_tesoreria.de_anno_documento_t, 'n/a'::text::character varying) AS de_anno_documento,
    COALESCE(import_export_rendicontazione_tesoreria.cod_documento_t, 'n/a'::text::character varying) AS cod_documento,
    COALESCE(import_export_rendicontazione_tesoreria.de_anno_provvisorio_t, 'n/a'::text::character varying) AS de_anno_provvisorio,
    COALESCE(import_export_rendicontazione_tesoreria.cod_provvisorio_t, 'n/a'::text::character varying) AS cod_provvisorio,
    COALESCE(import_export_rendicontazione_tesoreria.de_causale_t, 'n/a'::text::character varying) AS de_causale_t,
        CASE
            WHEN import_export_rendicontazione_tesoreria.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento_i = import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e OR (sum(import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r) OVER (PARTITION BY import_export_rendicontazione_tesoreria.cod_identificativo_flusso_r) - import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r) = 0::numeric AND import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e = import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r AND import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r = import_export_rendicontazione_tesoreria.num_importo_t THEN 'OK'::character varying(3)
            WHEN import_export_rendicontazione_tesoreria.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento_i <> import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e OR (sum(import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r) OVER (PARTITION BY import_export_rendicontazione_tesoreria.cod_identificativo_flusso_r) - import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r) <> 0::numeric OR import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e <> import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r OR import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r <> import_export_rendicontazione_tesoreria.num_importo_t THEN 'KO'::character varying(3)
            ELSE 'n/a'::character varying(3)
        END AS verifica_totale,
    COALESCE(import_export_rendicontazione_tesoreria.classificazione_completezza, 'OTHERS'::character varying)::character varying(20) AS classificazione_completezza,
    import_export_rendicontazione_tesoreria.dt_data_ultimo_aggiornamento,
    to_char(import_export_rendicontazione_tesoreria.dt_data_ultimo_aggiornamento::timestamp with time zone, 'dd-MM-yyyy'::text)::character varying(10) AS de_data_ultimo_aggiornamento,
    COALESCE(import_export_rendicontazione_tesoreria.indice_dati_singolo_pagamento_e, import_export_rendicontazione_tesoreria.indice_dati_singolo_pagamento_r) AS indice_dati_singolo_pagamento,
    COALESCE(import_export_rendicontazione_tesoreria.cod_identificativo_flusso_r, import_export_rendicontazione_tesoreria.cod_id_univoco_flusso_t) AS cod_iuf_key,
    COALESCE(import_export_rendicontazione_tesoreria.cod_iud_i, import_export_rendicontazione_tesoreria.cod_iud_e) AS cod_iud_key,
    COALESCE(import_export_rendicontazione_tesoreria.cod_rp_silinviarp_id_univoco_versamento_i, import_export_rendicontazione_tesoreria.cod_rp_silinviarp_id_univoco_versamento_e, import_export_rendicontazione_tesoreria.cod_dati_sing_pagam_identificativo_univoco_versamento_r, import_export_rendicontazione_tesoreria.cod_id_univoco_versamento_t) AS cod_iuv_key
   FROM mygov_import_export_rendicontazione_tesoreria_completa import_export_rendicontazione_tesoreria,
    mygov_ente ente
  WHERE COALESCE(import_export_rendicontazione_tesoreria.mygov_ente_id_i, import_export_rendicontazione_tesoreria.mygov_ente_id_e, import_export_rendicontazione_tesoreria.mygov_ente_id_r, import_export_rendicontazione_tesoreria.mygov_ente_id_t) = ente.mygov_ente_id;

DROP TABLE mygov_import_export_rendicontazione_tesoreria;

CREATE TABLE mygov_import_export_rendicontazione_tesoreria
(
  codice_ipa_ente character varying(80),
  dt_data_esecuzione_pagamento date,
  de_data_esecuzione_pagamento character varying(10),
  singolo_importo_commissione_carico_pa character varying(15),
  bilancio character varying(4096),
  nome_flusso_import_ente character varying(50),
  riga_flusso_import_ente character varying(12),
  codice_iud character varying(35),
  codice_iuv character varying(35),
  versione_oggetto character varying(16),
  identificativo_dominio character varying(35),
  identificativo_stazione_richiedente character varying(35),
  identificativo_messaggio_ricevuta character varying(35),
  data_ora_messaggio_ricevuta character varying(19),
  riferimento_messaggio_richiesta character varying(35),
  riferimento_data_richiesta character varying(10),
  tipo_identificativo_univoco_attestante character varying(1),
  codice_identificativo_univoco_attestante character varying(35),
  denominazione_attestante character varying(70),
  codice_unit_oper_attestante character varying(35),
  denom_unit_oper_attestante character varying(70),
  indirizzo_attestante character varying(70),
  civico_attestante character varying(16),
  cap_attestante character varying(16),
  localita_attestante character varying(35),
  provincia_attestante character varying(35),
  nazione_attestante character varying(2),
  tipo_identificativo_univoco_beneficiario character varying(1),
  codice_identificativo_univoco_beneficiario character varying(35),
  denominazione_beneficiario character varying(70),
  codice_unit_oper_beneficiario character varying(35),
  denom_unit_oper_beneficiario character varying(70),
  indirizzo_beneficiario character varying(70),
  civico_beneficiario character varying(16),
  cap_beneficiario character varying(16),
  localita_beneficiario character varying(35),
  provincia_beneficiario character varying(35),
  nazione_beneficiario character varying(2),
  tipo_identificativo_univoco_versante character varying(1),
  codice_identificativo_univoco_versante character varying(35),
  anagrafica_versante character varying(70),
  indirizzo_versante character varying(70),
  civico_versante character varying(16),
  cap_versante character varying(16),
  localita_versante character varying(35),
  provincia_versante character varying(35),
  nazione_versante character varying(2),
  email_versante character varying(256),
  tipo_identificativo_univoco_pagatore character varying(1),
  codice_identificativo_univoco_pagatore character varying(35),
  anagrafica_pagatore character varying(70),
  indirizzo_pagatore character varying(70),
  civico_pagatore character varying(16),
  cap_pagatore character varying(16),
  localita_pagatore character varying(35),
  provincia_pagatore character varying(35),
  nazione_pagatore character varying(2),
  email_pagatore character varying(256),
  codice_esito_pagamento character varying(1),
  importo_totale_pagato character varying(15),
  identificativo_univoco_versamento character varying(35),
  codice_contesto_pagamento character varying(35),
  singolo_importo_pagato character varying(15),
  esito_singolo_pagamento character varying(35),
  dt_data_esito_singolo_pagamento date,
  de_data_esito_singolo_pagamento character varying(10),
  identificativo_univoco_riscossione character varying(35),
  causale_versamento character varying(1024),
  dati_specifici_riscossione character varying(140),
  tipo_dovuto character varying(64),
  identificativo_flusso_rendicontazione character varying(35),
  data_ora_flusso_rendicontazione character varying(19),
  identificativo_univoco_regolamento character varying(35),
  dt_data_regolamento date,
  de_data_regolamento character varying,
  numero_totale_pagamenti character varying(15),
  importo_totale_pagamenti character varying(21),
  data_acquisizione character varying(10),
  cod_conto character varying(12),
  dt_data_contabile date,
  de_data_contabile character varying(10),
  dt_data_valuta date,
  de_data_valuta character varying(10),
  num_importo numeric(12,2),
  de_importo character varying(15),
  cod_or1 text,
  de_anno_bolletta character varying(4),
  cod_bolletta character varying(7),
  cod_id_dominio character varying(7),
  dt_ricezione timestamp without time zone,
  de_data_ricezione character varying(10),
  de_anno_documento character varying(4),
  cod_documento character varying(7),
  de_anno_provvisorio character varying(4),
  cod_provvisorio character varying(6),
  de_causale_t text,
  verifica_totale character varying(3),
  classificazione_completezza character varying(20),
  dt_data_ultimo_aggiornamento date,
  de_data_ultimo_aggiornamento character varying(10),
  indice_dati_singolo_pagamento integer,
  cod_iuf_key character varying(35),
  cod_iud_key character varying(35),
  cod_iuv_key character varying(35)
);


CREATE INDEX idx_mygovimpexprendtes_causale_versamento
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", causale_versamento COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_classificazione_completezza
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", classificazione_completezza COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_codice_iud
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", codice_iud COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_de_importo
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", de_importo COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_dt_data_contabile
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", dt_data_contabile);

CREATE INDEX idx_mygovimpexprendtes_dt_data_esito_singolo_pagamento
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", dt_data_esito_singolo_pagamento);

CREATE INDEX idx_mygovimpexprendtes_dt_data_regolamento
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", dt_data_regolamento);

CREATE INDEX idx_mygovimpexprendtes_dt_data_valuta
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", dt_data_valuta);

CREATE INDEX idx_mygovimpexprendtes_identificativo_flusso_rendicontazione
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", identificativo_flusso_rendicontazione COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_identificativo_univoco_regolamento
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", identificativo_univoco_regolamento COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_identificativo_univoco_riscossione
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", identificativo_univoco_riscossione COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_identificativo_univoco_versamento
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", identificativo_univoco_versamento COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_importo_totale_pagamenti
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", importo_totale_pagamenti COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_tipo_dovuto
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", tipo_dovuto COLLATE pg_catalog."default");


CREATE OR REPLACE FUNCTION get_tesoreria_subset_function(IN _codice_ipa_ente character varying, IN _dt_data_contabile_da date, IN _dt_data_contabile_a date, IN _dt_data_valuta_da date, IN _dt_data_valuta_a date, IN _dt_data_ultimo_aggiornamento_da date, IN _dt_data_ultimo_aggiornamento_a date, IN _de_causale_t text, IN _importo character varying, IN _conto character varying, IN _codor1 character varying, IN _flagnascosto boolean, IN _classificazione_completezza character varying, IN _cod_iuv character varying, IN _cod_iuf character varying, IN _page integer, IN _size integer)
  RETURNS TABLE(codice_ipa_ente character varying, codice_iuv character varying, identificativo_flusso_rendicontazione character varying, dt_data_esecuzione_pagamento date, de_data_esecuzione_pagamento character varying, singolo_importo_commissione_carico_pa character varying, bilancio character varying, cod_conto character varying, dt_data_contabile date, de_data_contabile character varying, dt_data_valuta date, de_data_valuta character varying, num_importo numeric, de_importo character varying, cod_or1 text, de_anno_bolletta character varying, cod_bolletta character varying, cod_id_dominio character varying, dt_ricezione timestamp without time zone, de_data_ricezione character varying, de_anno_documento character varying, cod_documento character varying, de_anno_provvisorio character varying, cod_provvisorio character varying, de_causale_t text, classificazione_completezza character varying, dt_data_ultimo_aggiornamento date, de_data_ultimo_aggiornamento character varying, cod_iuf_key character varying, cod_iuv_key character varying) AS
$BODY$
   SELECT 
        DISTINCT (tes.codice_ipa_ente),
  tes.codice_iuv,
  tes.identificativo_flusso_rendicontazione,
  tes.dt_data_esecuzione_pagamento,
  tes.de_data_esecuzione_pagamento,
  tes.singolo_importo_commissione_carico_pa,
  tes.bilancio,
  tes.cod_conto,
  tes.dt_data_contabile,
  tes.de_data_contabile,
        tes.dt_data_valuta,
        tes.de_data_valuta,
  tes.num_importo,
  tes.de_importo,
  tes.cod_or1,
  tes.de_anno_bolletta,
  tes.cod_bolletta,
  tes.cod_id_dominio,
  tes.dt_ricezione,
  tes.de_data_ricezione,
  tes.de_anno_documento,
  tes.cod_documento,
  tes.de_anno_provvisorio,
  tes.cod_provvisorio,
  tes.de_causale_t,
  tes.classificazione_completezza,
  tes.dt_data_ultimo_aggiornamento,
  tes.de_data_ultimo_aggiornamento,
  tes.cod_iuf_key,
  tes.cod_iuv_key
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms                              
     ON   ms.cod_ipa_ente = tes.codice_ipa_ente 
    AND (ms.cod_iuf IS NULL 
    AND  tes.cod_iuf_key IS NULL 
    OR   ms.cod_iuf = tes.cod_iuf_key)
    AND (ms.cod_iuv IS NULL 
    AND  tes.cod_iuv_key IS NULL 
    OR   ms.cod_iuv = tes.cod_iuv_key)
     
   WHERE CASE WHEN (_codice_ipa_ente <> '') IS TRUE THEN tes.codice_ipa_ente = _codice_ipa_ente ELSE true END     
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN tes.dt_data_contabile >= _dt_data_contabile_da ELSE true END                       
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN tes.dt_data_contabile <= _dt_data_contabile_a ELSE true END         
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN tes.dt_data_valuta >= _dt_data_valuta_da ELSE true END                      
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN tes.dt_data_valuta <= _dt_data_valuta_a ELSE true END              
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END                       
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END
   AND   CASE WHEN (_de_causale_t <> '') IS TRUE THEN upper(tes.de_causale_t) like '%' || upper(_de_causale_t) || '%' ELSE true END
   AND   CASE WHEN (_importo <> '') IS TRUE THEN tes.de_importo = _importo ELSE true END                   
   AND   CASE WHEN (_conto <> '') IS TRUE THEN tes.cod_conto = _conto ELSE true END                 
   AND   CASE WHEN (_codOr1 <> '') IS TRUE THEN upper(tes.cod_or1) like '%' || upper(_codOr1) || '%' ELSE true END                                                           
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END                    
   AND   CASE WHEN (_classificazione_completezza <> '') IS TRUE THEN tes.classificazione_completezza = _classificazione_completezza ELSE true END
   AND   CASE WHEN (_cod_iuv <> '') IS TRUE THEN tes.codice_iuv = _cod_iuv ELSE true END
   AND   CASE WHEN (_cod_iuf <> '') IS TRUE THEN tes.identificativo_flusso_rendicontazione = _cod_iuf ELSE true END
      
   ORDER BY tes.dt_data_valuta, tes.identificativo_flusso_rendicontazione, tes.codice_iuv
   OFFSET CASE WHEN (_page IS NOT NULL) THEN ((_page - 1)*_size) ELSE 0 END 
   LIMIT CASE WHEN (_size IS NOT NULL) THEN _size ELSE 5 END;
$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION get_rendicontazione_subset_function(IN _codice_ipa_ente character varying, IN _identificativo_flusso_rendicontazione character varying, IN _identificativo_univoco_regolamento character varying, IN _dt_data_regolamento_da date, IN _dt_data_regolamento_a date, IN dt_data_ultimo_aggiornamento_da date, IN dt_data_ultimo_aggiornamento_a date, IN _classificazione_completezza character varying, IN _cod_tipo_dovuto character varying, IN _cod_fed_user_id character varying, IN _flagnascosto boolean, IN _page integer, IN _size integer)
  RETURNS TABLE(identificativo_flusso_rendicontazione character varying, codice_ipa_ente character varying, singolo_importo_commissione_carico_pa character varying, bilancio character varying, data_ora_flusso_rendicontazione character varying, identificativo_univoco_regolamento character varying, dt_data_regolamento date, de_data_regolamento character varying, importo_totale_pagamenti character varying, de_anno_bolletta character varying, cod_bolletta character varying, cod_id_dominio character varying, dt_ricezione timestamp without time zone, de_data_ricezione character varying, de_anno_documento character varying, cod_documento character varying, de_anno_provvisorio character varying, cod_provvisorio character varying, classificazione_completezza character varying, dt_data_ultimo_aggiornamento date, de_data_ultimo_aggiornamento character varying, indice_dati_singolo_pagamento integer, cod_iuf_key character varying) AS
$BODY$
   SELECT 
      DISTINCT (tes.identificativo_flusso_rendicontazione),tes.codice_ipa_ente,tes.singolo_importo_commissione_carico_pa,tes.bilancio,tes.data_ora_flusso_rendicontazione,tes.identificativo_univoco_regolamento,tes.dt_data_regolamento,tes.de_data_regolamento,tes.importo_totale_pagamenti,tes.de_anno_bolletta,tes.cod_bolletta,tes.cod_id_dominio,tes.dt_ricezione,tes.de_data_ricezione,tes.de_anno_documento,tes.cod_documento,tes.de_anno_provvisorio,tes.cod_provvisorio,tes.classificazione_completezza, MAX(tes.dt_data_ultimo_aggiornamento)as dt_data_ultimo_aggiornamento, to_char(MAX(tes.dt_data_ultimo_aggiornamento), 'DD-MM-YYYY') as de_data_ultimo_aggiornamento, tes.indice_dati_singolo_pagamento,tes.cod_iuf_key
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT mseg.*, ment.* FROM mygov_segnalazione as mseg INNER JOIN mygov_ente as ment ON mseg.mygov_ente_id = ment.mygov_ente_id WHERE mseg.flg_attivo = true AND mseg.classificazione_completezza = $8) as ms 
     ON ms.cod_ipa_ente = tes.codice_ipa_ente AND (ms.cod_iuf = tes.cod_iuf_key OR (ms.cod_iuf IS NULL and tes.cod_iuf_key IS NULL))
   WHERE  
         CASE WHEN $1 IS NOT NULL AND $1!='' THEN tes.codice_ipa_ente = $1 ELSE true END
     AND CASE WHEN $2 IS NOT NULL AND $2!='' THEN tes.identificativo_flusso_rendicontazione = $2 ELSE true END
     AND (COALESCE($3, '') ='' OR tes.identificativo_univoco_regolamento = $3)
     AND CASE WHEN $4 IS NOT NULL THEN tes.dt_data_regolamento >= $4 ELSE true END
     AND CASE WHEN $5 IS NOT NULL THEN tes.dt_data_regolamento <= $5 ELSE true END
     AND CASE WHEN $6 IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= $6 ELSE true END
     AND CASE WHEN $7 IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento < $7 ELSE true END
     AND CASE WHEN $8 IS NOT NULL AND $8!='' THEN tes.classificazione_completezza = $8 ELSE true END
     AND CASE WHEN $9 IS NOT NULL AND $9!='' AND tes.classificazione_completezza <> 'IUV_NO_RT' THEN tes.tipo_dovuto = $9 
          WHEN ($9 IS NULL OR $9='') AND tes.classificazione_completezza <> 'IUV_NO_RT' THEN 
      tes.tipo_dovuto in (SELECT
           DISTINCT(metd.cod_tipo)
        FROM 
           mygov_operatore_ente_tipo_dovuto as moetd, mygov_ente_tipo_dovuto as metd 
        WHERE
           moetd.mygov_ente_tipo_dovuto_id = metd.mygov_ente_tipo_dovuto_id AND
           moetd.cod_fed_user_id = $10 AND 
           moetd.flg_attivo = true)
    ELSE true      
   END
     AND CASE WHEN $11 IS NOT NULL 
         THEN
       ms.flg_nascosto = $11
         ELSE
             (ms.flg_nascosto is null or ms.flg_nascosto = false)
         END
   GROUP BY tes.identificativo_flusso_rendicontazione,tes.codice_ipa_ente,tes.singolo_importo_commissione_carico_pa,tes.bilancio,tes.data_ora_flusso_rendicontazione,tes.identificativo_univoco_regolamento,tes.dt_data_regolamento,tes.de_data_regolamento,tes.importo_totale_pagamenti,tes.de_anno_bolletta,tes.cod_bolletta,tes.cod_id_dominio,tes.dt_ricezione,tes.de_data_ricezione,tes.de_anno_documento,tes.cod_documento,tes.de_anno_provvisorio,tes.cod_provvisorio,tes.classificazione_completezza, tes.indice_dati_singolo_pagamento,tes.cod_iuf_key
   ORDER BY dt_data_ultimo_aggiornamento DESC
   OFFSET CASE WHEN ($12 IS NOT NULL) THEN (($12 - 1)*$13) ELSE 0 END 
   LIMIT CASE WHEN ($13 IS NOT NULL) THEN $13 ELSE 5 END;
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;

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
       de_anno_documento, cod_documento, de_anno_provvisorio, cod_provvisorio, de_causale_t,
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

CREATE OR REPLACE FUNCTION get_import_export_rend_tes_function(_cod_fed_user_id character varying, _codice_ipa_ente character varying, _cod_iud character varying, _cod_iuv character varying, _denominazione_attestante character varying, _identificativo_univoco_riscossione character varying, _codice_identificativo_univoco_versante character varying, _anagrafica_versante character varying, _codice_identificativo_univoco_pagatore character varying, _anagrafica_pagatore character varying, _causale_versamento character varying, _data_esecuzione_singolo_pagamento_da date, _data_esecuzione_singolo_pagamento_a date, _data_esito_singolo_pagamento_da date, _data_esito_singolo_pagamento_a date, _identificativo_flusso_rendicontazione character varying, _identificativo_univoco_regolamento character varying, _data_regolamento_da date, _data_regolamento_a date, _dt_data_contabile_da date, _dt_data_contabile_a date, _dt_data_valuta_da date, _dt_data_valuta_a date, _dt_data_ultimo_aggiornamento_da date, _dt_data_ultimo_aggiornamento_a date, _cod_tipo_dovuto character varying, _is_cod_tipo_dovuto_present boolean, _importo character varying, _conto character varying, _codor1 character varying, _flagnascosto boolean, _classificazione_completezza character varying, _page integer, _size integer)
  RETURNS SETOF mygov_import_export_rendicontazione_tesoreria AS
$BODY$
   SELECT 
      tes.*
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.cod_iud
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms                              
     ON   ms.cod_ipa_ente = tes.codice_ipa_ente 
     AND (ms.cod_iuf IS NULL 
     AND  tes.cod_iuf_key IS NULL 
     OR   ms.cod_iuf = tes.cod_iuf_key)
     AND (ms.cod_iuv IS NULL 
     AND  tes.cod_iuv_key IS NULL 
     OR   ms.cod_iuv = tes.cod_iuv_key)
     AND (ms.cod_iud IS NULL 
     AND  tes.cod_iud_key IS NULL 
     OR   ms.cod_iud = tes.cod_iud_key)
     
   WHERE CASE WHEN (_cod_tipo_dovuto <> '') IS TRUE AND _is_cod_tipo_dovuto_present THEN tes.tipo_dovuto = _cod_tipo_dovuto 
              WHEN (_cod_tipo_dovuto <> '') IS NOT TRUE AND _is_cod_tipo_dovuto_present THEN 
                  tes.tipo_dovuto in (SELECT DISTINCT(metd.cod_tipo)
                                      FROM   mygov_operatore_ente_tipo_dovuto as moetd, mygov_ente_tipo_dovuto as metd 
                                      WHERE  moetd.mygov_ente_tipo_dovuto_id = metd.mygov_ente_tipo_dovuto_id
                                      AND    moetd.cod_fed_user_id = _cod_fed_user_id 
                                      AND   moetd.flg_attivo = true)
              ELSE true      
         END  
   AND   CASE WHEN (_codice_ipa_ente <> '') IS TRUE THEN tes.codice_ipa_ente = _codice_ipa_ente ELSE true END 
   AND   CASE WHEN (_cod_iud <> '') IS TRUE THEN tes.codice_iud = _cod_iud ELSE true END  
   AND   CASE WHEN (_cod_iuv <> '') IS TRUE THEN tes.codice_iuv = _cod_iuv ELSE true END        
   AND   CASE WHEN (_denominazione_attestante <> '') IS TRUE THEN 
                    (upper(tes.denominazione_attestante) like '%' || upper(_denominazione_attestante) || '%' 
                 OR upper(tes.codice_identificativo_univoco_attestante) like '%' || upper(_denominazione_attestante) || '%') 
         ELSE true END     
   AND   CASE WHEN (_identificativo_univoco_riscossione <> '') IS TRUE THEN tes.identificativo_univoco_riscossione = _identificativo_univoco_riscossione ELSE true END        
   AND   CASE WHEN (_codice_identificativo_univoco_versante <> '') IS TRUE THEN (tes.codice_identificativo_univoco_versante = upper(_codice_identificativo_univoco_versante) OR tes.codice_identificativo_univoco_versante = lower(_codice_identificativo_univoco_versante)) ELSE true END        
   AND   CASE WHEN (_anagrafica_versante <> '') IS TRUE THEN upper(tes.anagrafica_versante) like '%' || upper(_anagrafica_versante) || '%' ELSE true END              
   AND   CASE WHEN (_codice_identificativo_univoco_pagatore <> '') IS TRUE THEN (tes.codice_identificativo_univoco_pagatore = upper(_codice_identificativo_univoco_pagatore) OR tes.codice_identificativo_univoco_pagatore = lower(_codice_identificativo_univoco_pagatore)) ELSE true END           
   AND   CASE WHEN (_anagrafica_pagatore <> '') IS TRUE THEN upper(tes.anagrafica_pagatore) like '%' || upper(_anagrafica_pagatore) || '%' ELSE true END                  
   AND   CASE WHEN (_causale_versamento <> '') IS TRUE THEN upper(tes.causale_versamento) like '%' || upper(_causale_versamento) || '%' ELSE true END                    
   AND   CASE WHEN _data_esecuzione_singolo_pagamento_da IS NOT NULL THEN tes.dt_data_esecuzione_pagamento >= _data_esecuzione_singolo_pagamento_da ELSE true END                       
   AND   CASE WHEN _data_esecuzione_singolo_pagamento_a IS NOT NULL THEN tes.dt_data_esecuzione_pagamento <= _data_esecuzione_singolo_pagamento_a ELSE true END                     
   AND   CASE WHEN _data_esito_singolo_pagamento_da IS NOT NULL THEN tes.dt_data_esito_singolo_pagamento >= _data_esito_singolo_pagamento_da ELSE true END                       
   AND   CASE WHEN _data_esito_singolo_pagamento_a IS NOT NULL THEN tes.dt_data_esito_singolo_pagamento <= _data_esito_singolo_pagamento_a ELSE true END             
   AND   CASE WHEN (_identificativo_flusso_rendicontazione <> '') IS TRUE THEN tes.identificativo_flusso_rendicontazione = _identificativo_flusso_rendicontazione ELSE true END           
   AND   CASE WHEN (_identificativo_univoco_regolamento <> '') IS TRUE THEN tes.identificativo_univoco_regolamento = _identificativo_univoco_regolamento ELSE true END           
   AND   CASE WHEN _data_regolamento_da IS NOT NULL THEN tes.dt_data_regolamento >= _data_regolamento_da ELSE true END                       
   AND   CASE WHEN _data_regolamento_a IS NOT NULL THEN tes.dt_data_regolamento <= _data_regolamento_a ELSE true END             
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN tes.dt_data_contabile >= _dt_data_contabile_da ELSE true END                       
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN tes.dt_data_contabile <= _dt_data_contabile_a ELSE true END             
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN tes.dt_data_valuta >= _dt_data_valuta_da ELSE true END                      
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN tes.dt_data_valuta <= _dt_data_valuta_a ELSE true END              
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END                       
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END             
   AND   CASE WHEN (_importo <> '') IS TRUE THEN tes.de_importo = _importo ELSE true END                   
   AND   CASE WHEN (_conto <> '') IS TRUE THEN tes.cod_conto = _conto ELSE true END                 
   AND   CASE WHEN (_codOr1 <> '') IS TRUE THEN upper(tes.cod_or1)  like '%' || upper(_codOr1) || '%' ELSE true END                                                            
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END                    
   AND   CASE WHEN (_classificazione_completezza <> '') IS TRUE THEN tes.classificazione_completezza = _classificazione_completezza ELSE true END     
         

   
   ORDER BY CASE WHEN _classificazione_completezza = 'IUD_RT_IUF_TES' OR 
                      _classificazione_completezza = 'RT_IUF_TES' OR
                      _classificazione_completezza = 'RT_IUF' OR
                      _classificazione_completezza = 'IUD_RT_IUF' OR 
                      _classificazione_completezza = 'RT_NO_IUF' OR
                      _classificazione_completezza = 'RT_NO_IUD' THEN (dt_data_esito_singolo_pagamento, codice_iuv, codice_iud)                         
                 WHEN _classificazione_completezza = 'IUD_NO_RT' THEN (dt_data_esecuzione_pagamento, codice_iud)  
                ELSE (dt_data_esito_singolo_pagamento, codice_iuv, codice_iud)
            END 

   OFFSET CASE WHEN (_page IS NOT NULL) THEN ((_page - 1)*_size) ELSE 0 END 
   LIMIT CASE WHEN (_size IS NOT NULL) THEN _size ELSE 5 END;
$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION get_count_tesoreria_subset_function(_codice_ipa_ente character varying, _dt_data_contabile_da date, _dt_data_contabile_a date, _dt_data_valuta_da date, _dt_data_valuta_a date, _dt_data_ultimo_aggiornamento_da date, _dt_data_ultimo_aggiornamento_a date, _de_causale_t text, _importo character varying, _conto character varying, _codor1 character varying, _flagnascosto boolean, _classificazione_completezza character varying, _cod_iuv character varying, _cod_iuf character varying)
  RETURNS bigint AS
$BODY$
   SELECT COUNT( DISTINCT(tes.codice_ipa_ente))
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms                              
     ON   ms.cod_ipa_ente = tes.codice_ipa_ente 
    AND (ms.cod_iuf IS NULL 
    AND  tes.cod_iuf_key IS NULL 
    OR   ms.cod_iuf = tes.cod_iuf_key)
    AND (ms.cod_iuv IS NULL 
    AND  tes.cod_iuv_key IS NULL 
    OR   ms.cod_iuv = tes.cod_iuv_key)
     
   WHERE CASE WHEN (_codice_ipa_ente <> '') IS TRUE THEN tes.codice_ipa_ente = _codice_ipa_ente ELSE true END     
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN tes.dt_data_contabile >= _dt_data_contabile_da ELSE true END                       
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN tes.dt_data_contabile <= _dt_data_contabile_a ELSE true END         
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN tes.dt_data_valuta >= _dt_data_valuta_da ELSE true END                      
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN tes.dt_data_valuta <= _dt_data_valuta_a ELSE true END              
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END                       
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END
   AND   CASE WHEN (_de_causale_t <> '') IS TRUE THEN upper(tes.de_causale_t) like '%' || upper(_de_causale_t) || '%' ELSE true END             
   AND   CASE WHEN (_importo <> '') IS TRUE THEN tes.de_importo = _importo ELSE true END                   
   AND   CASE WHEN (_conto <> '') IS TRUE THEN tes.cod_conto = _conto ELSE true END                 
   AND   CASE WHEN (_codOr1 <> '') IS TRUE THEN upper(tes.cod_or1) like '%' || upper(_codOr1) || '%' ELSE true END                                                            
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END                    
   AND   CASE WHEN (_classificazione_completezza <> '') IS TRUE THEN tes.classificazione_completezza = _classificazione_completezza ELSE true END
   AND   CASE WHEN (_cod_iuv <> '') IS TRUE THEN tes.codice_iuv = _cod_iuv ELSE true END
   AND   CASE WHEN (_cod_iuf <> '') IS TRUE THEN tes.identificativo_flusso_rendicontazione = _cod_iuf ELSE true END;
$BODY$
  LANGUAGE sql STABLE
  COST 100;

CREATE OR REPLACE FUNCTION get_count_tesoreria_no_match_subset_function(_codice_ipa_ente character varying, _dt_data_contabile_da date, _dt_data_contabile_a date, _dt_data_valuta_da date, _dt_data_valuta_a date, _dt_data_ultimo_aggiornamento_da date, _dt_data_ultimo_aggiornamento_a date, _de_causale_t text, _importo character varying, _conto character varying, _codor1 character varying, _flagnascosto boolean, _classificazione_completezza character varying)
  RETURNS bigint AS
$BODY$
   SELECT COUNT( DISTINCT(tes.codice_ipa_ente, tes.de_anno_bolletta, tes.cod_bolletta))
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms                              
     ON   ms.cod_ipa_ente = tes.codice_ipa_ente 
    AND (ms.cod_iuf IS NULL 
    AND  tes.cod_iuf_key IS NULL 
    OR   ms.cod_iuf = tes.cod_iuf_key)
    AND (ms.cod_iuv IS NULL 
    AND  tes.cod_iuv_key IS NULL 
    OR   ms.cod_iuv = tes.cod_iuv_key)
     
   WHERE CASE WHEN (_codice_ipa_ente <> '') IS TRUE THEN tes.codice_ipa_ente = _codice_ipa_ente ELSE true END     
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN tes.dt_data_contabile >= _dt_data_contabile_da ELSE true END                       
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN tes.dt_data_contabile <= _dt_data_contabile_a ELSE true END         
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN tes.dt_data_valuta >= _dt_data_valuta_da ELSE true END                      
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN tes.dt_data_valuta <= _dt_data_valuta_a ELSE true END              
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END                       
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END
   AND   CASE WHEN (_de_causale_t <> '') IS TRUE THEN upper(tes.de_causale_t) like '%' || upper(_de_causale_t) || '%' ELSE true END
   AND   CASE WHEN (_importo <> '') IS TRUE THEN tes.de_importo = _importo ELSE true END                   
   AND   CASE WHEN (_conto <> '') IS TRUE THEN tes.cod_conto = _conto ELSE true END                 
   AND   CASE WHEN (_codOr1 <> '') IS TRUE THEN upper(tes.cod_or1) like '%' || upper(_codOr1) || '%' ELSE true END                                                            
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END                    
   AND   CASE WHEN (_classificazione_completezza <> '') IS TRUE THEN tes.classificazione_completezza = _classificazione_completezza ELSE true END;
$BODY$
  LANGUAGE sql STABLE
  COST 100;

CREATE OR REPLACE FUNCTION get_count_rendicontazione_subset_function(_codice_ipa_ente character varying, _identificativo_flusso_rendicontazione character varying, _identificativo_univoco_regolamento character varying, _dt_data_regolamento_da date, _dt_data_regolamento_a date, _dt_data_ultimo_aggiornamento_da date, _dt_data_ultimo_aggiornamento_a date, _classificazione_completezza character varying, _cod_tipo_dovuto character varying, _cod_fed_user_id character varying, _flagnascosto boolean)
  RETURNS bigint AS
$BODY$
   SELECT 
         COUNT( DISTINCT(tes.identificativo_flusso_rendicontazione))
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT mseg.*, ment.* FROM mygov_segnalazione as mseg INNER JOIN mygov_ente as ment ON mseg.mygov_ente_id = ment.mygov_ente_id WHERE mseg.flg_attivo = true AND mseg.classificazione_completezza = $8) as ms 
     ON ms.cod_ipa_ente = tes.codice_ipa_ente AND (ms.cod_iuf = tes.cod_iuf_key OR (ms.cod_iuf IS NULL and tes.cod_iuf_key IS NULL))
   WHERE  
         CASE WHEN $1 IS NOT NULL AND $1!='' THEN tes.codice_ipa_ente = $1 ELSE true END
     AND CASE WHEN $2 IS NOT NULL AND $2!='' THEN tes.identificativo_flusso_rendicontazione = $2 ELSE true END
     AND (COALESCE($3, '') ='' OR tes.identificativo_univoco_regolamento = $3)
     AND CASE WHEN $4 IS NOT NULL THEN tes.dt_data_regolamento >= $4 ELSE true END
     AND CASE WHEN $5 IS NOT NULL THEN tes.dt_data_regolamento <= $5 ELSE true END
     AND CASE WHEN $6 IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= $6 ELSE true END
     AND CASE WHEN $7 IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento < $7 ELSE true END
     AND CASE WHEN $8 IS NOT NULL AND $8!='' THEN tes.classificazione_completezza = $8 ELSE true END
     AND CASE WHEN $9 IS NOT NULL AND $9!='' AND tes.classificazione_completezza <> 'IUV_NO_RT' THEN tes.tipo_dovuto = $9 
          WHEN ($9 IS NULL OR $9='') AND tes.classificazione_completezza <> 'IUV_NO_RT' THEN  
      tes.tipo_dovuto in (SELECT
           DISTINCT(metd.cod_tipo)
        FROM 
           mygov_operatore_ente_tipo_dovuto as moetd, mygov_ente_tipo_dovuto as metd 
        WHERE
           moetd.mygov_ente_tipo_dovuto_id = metd.mygov_ente_tipo_dovuto_id AND
           moetd.cod_fed_user_id = $10 AND 
           moetd.flg_attivo = true)
      ELSE true
   END
     AND CASE WHEN $11 IS NOT NULL 
         THEN
       ms.flg_nascosto = $11
         ELSE
             (ms.flg_nascosto is null or ms.flg_nascosto = false)
         END

$BODY$
  LANGUAGE sql VOLATILE
  COST 100;

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


CREATE OR REPLACE FUNCTION get_count_import_export_rend_tes_function(_cod_fed_user_id character varying, _codice_ipa_ente character varying, _cod_iud character varying, _cod_iuv character varying, _denominazione_attestante character varying, _identificativo_univoco_riscossione character varying, _codice_identificativo_univoco_versante character varying, _anagrafica_versante character varying, _codice_identificativo_univoco_pagatore character varying, _anagrafica_pagatore character varying, _causale_versamento character varying, _data_esecuzione_singolo_pagamento_da date, _data_esecuzione_singolo_pagamento_a date, _data_esito_singolo_pagamento_da date, _data_esito_singolo_pagamento_a date, _identificativo_flusso_rendicontazione character varying, _identificativo_univoco_regolamento character varying, _data_regolamento_da date, _data_regolamento_a date, _dt_data_contabile_da date, _dt_data_contabile_a date, _dt_data_valuta_da date, _dt_data_valuta_a date, _dt_data_ultimo_aggiornamento_da date, _dt_data_ultimo_aggiornamento_a date, _cod_tipo_dovuto character varying, _is_cod_tipo_dovuto_present boolean, _importo character varying, _conto character varying, _codor1 character varying, _flagnascosto boolean, _classificazione_completezza character varying)
  RETURNS bigint AS
$BODY$
   SELECT 
      count(1)
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
	LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.cod_iud
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms                              
	   ON   ms.cod_ipa_ente = tes.codice_ipa_ente 
     AND (ms.cod_iuf IS NULL 
     AND  tes.cod_iuf_key IS NULL 
     OR   ms.cod_iuf = tes.cod_iuf_key)
     AND (ms.cod_iuv IS NULL 
     AND  tes.cod_iuv_key IS NULL 
     OR   ms.cod_iuv = tes.cod_iuv_key)
     AND (ms.cod_iud IS NULL 
     AND  tes.cod_iud_key IS NULL 
     OR   ms.cod_iud = tes.cod_iud_key)
     
   WHERE CASE WHEN (_cod_tipo_dovuto <> '') IS TRUE AND _is_cod_tipo_dovuto_present THEN tes.tipo_dovuto = _cod_tipo_dovuto 
      	      WHEN (_cod_tipo_dovuto <> '') IS NOT TRUE AND _is_cod_tipo_dovuto_present THEN 
            	    tes.tipo_dovuto in (SELECT DISTINCT(metd.cod_tipo)
                              				FROM   mygov_operatore_ente_tipo_dovuto as moetd, mygov_ente_tipo_dovuto as metd 
                              				WHERE  moetd.mygov_ente_tipo_dovuto_id = metd.mygov_ente_tipo_dovuto_id
                              				AND    moetd.cod_fed_user_id = _cod_fed_user_id 
                              				AND   moetd.flg_attivo = true)
    		      ELSE true		   
    	   END	
   AND   CASE WHEN (_codice_ipa_ente <> '') IS TRUE THEN tes.codice_ipa_ente = _codice_ipa_ente ELSE true END 
   AND   CASE WHEN (_cod_iud <> '') IS TRUE THEN tes.codice_iud = _cod_iud ELSE true END  
   AND   CASE WHEN (_cod_iuv <> '') IS TRUE THEN tes.codice_iuv = _cod_iuv ELSE true END        
   AND   CASE WHEN (_denominazione_attestante <> '') IS TRUE THEN 
                    (upper(tes.denominazione_attestante) like '%' || upper(_denominazione_attestante) || '%' 
                 OR upper(tes.codice_identificativo_univoco_attestante) like '%' || upper(_denominazione_attestante) || '%') 
         ELSE true END     
   AND   CASE WHEN (_identificativo_univoco_riscossione <> '') IS TRUE THEN tes.identificativo_univoco_riscossione = _identificativo_univoco_riscossione ELSE true END        
   AND   CASE WHEN (_codice_identificativo_univoco_versante <> '') IS TRUE THEN (tes.codice_identificativo_univoco_versante = upper(_codice_identificativo_univoco_versante) OR tes.codice_identificativo_univoco_versante = lower(_codice_identificativo_univoco_versante)) ELSE true END        
   AND   CASE WHEN (_anagrafica_versante <> '') IS TRUE THEN upper(tes.anagrafica_versante) like '%' || upper(_anagrafica_versante) || '%' ELSE true END              
   AND   CASE WHEN (_codice_identificativo_univoco_pagatore <> '') IS TRUE THEN (tes.codice_identificativo_univoco_pagatore = upper(_codice_identificativo_univoco_pagatore) OR tes.codice_identificativo_univoco_pagatore = lower(_codice_identificativo_univoco_pagatore)) ELSE true END           
   AND   CASE WHEN (_anagrafica_pagatore <> '') IS TRUE THEN upper(tes.anagrafica_pagatore) like '%' || upper(_anagrafica_pagatore) || '%' ELSE true END                  
   AND   CASE WHEN (_causale_versamento <> '') IS TRUE THEN upper(tes.causale_versamento) like '%' || upper(_causale_versamento) || '%' ELSE true END                    
   AND   CASE WHEN _data_esecuzione_singolo_pagamento_da IS NOT NULL THEN tes.dt_data_esecuzione_pagamento >= _data_esecuzione_singolo_pagamento_da ELSE true END                       
   AND   CASE WHEN _data_esecuzione_singolo_pagamento_a IS NOT NULL THEN tes.dt_data_esecuzione_pagamento <= _data_esecuzione_singolo_pagamento_a ELSE true END                     
   AND   CASE WHEN _data_esito_singolo_pagamento_da IS NOT NULL THEN tes.dt_data_esito_singolo_pagamento >= _data_esito_singolo_pagamento_da ELSE true END                       
   AND   CASE WHEN _data_esito_singolo_pagamento_a IS NOT NULL THEN tes.dt_data_esito_singolo_pagamento <= _data_esito_singolo_pagamento_a ELSE true END             
   AND   CASE WHEN (_identificativo_flusso_rendicontazione <> '') IS TRUE THEN tes.identificativo_flusso_rendicontazione = _identificativo_flusso_rendicontazione ELSE true END           
   AND   CASE WHEN (_identificativo_univoco_regolamento <> '') IS TRUE THEN tes.identificativo_univoco_regolamento = _identificativo_univoco_regolamento ELSE true END           
   AND   CASE WHEN _data_regolamento_da IS NOT NULL THEN tes.dt_data_regolamento >= _data_regolamento_da ELSE true END                       
   AND   CASE WHEN _data_regolamento_a IS NOT NULL THEN tes.dt_data_regolamento <= _data_regolamento_a ELSE true END             
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN tes.dt_data_contabile >= _dt_data_contabile_da ELSE true END                       
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN tes.dt_data_contabile <= _dt_data_contabile_a ELSE true END             
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN tes.dt_data_valuta >= _dt_data_valuta_da ELSE true END                      
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN tes.dt_data_valuta <= _dt_data_valuta_a ELSE true END              
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END                       
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END             
   AND   CASE WHEN (_importo <> '') IS TRUE THEN tes.de_importo = _importo ELSE true END                   
   AND   CASE WHEN (_conto <> '') IS TRUE THEN tes.cod_conto = _conto ELSE true END                 
   AND   CASE WHEN (_codOr1 <> '') IS TRUE THEN upper(tes.cod_or1)  like '%' || upper(_codOr1) || '%' ELSE true END                                                            
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END                    
   AND   CASE WHEN (_classificazione_completezza <> '') IS TRUE THEN tes.classificazione_completezza = _classificazione_completezza ELSE true END;
$BODY$
  LANGUAGE sql STABLE
  COST 100;


-- allungamento campo classificazione
ALTER TABLE mygov_prenotazione_flusso_riconciliazione ALTER COLUMN cod_codice_classificazione TYPE TEXT;


ALTER TABLE mygov_flusso_export ADD COLUMN bilancio CHARACTER VARYING(4096);

DROP FUNCTION get_count_import_export_rend_tes_function(character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, date, date, date, date, character varying, character varying, date, date, date, date, date, date, date, date, character varying, boolean, character varying, character varying, character varying, boolean, character varying);

DROP FUNCTION get_count_pagamenti_inseribili_in_accertamento(character varying, character varying, character varying, character varying, character varying, character varying, character varying, date, date, date, date, date, date, date, date, date, date);

DROP FUNCTION get_count_pagamenti_inseriti_in_accertamento(bigint, character varying, character varying, character varying, character varying, character varying, character varying, character varying, date, date, date, date, date, date, date, date, date, date);

DROP FUNCTION get_count_rendicontazione_subset_function(character varying, character varying, character varying, date, date, date, date, character varying, character varying, character varying, boolean);

DROP FUNCTION get_count_tesoreria_no_match_subset_function(character varying, date, date, date, date, date, date, text, character varying, character varying, character varying, boolean, character varying);

DROP FUNCTION get_count_tesoreria_subset_function(character varying, date, date, date, date, date, date, text, character varying, character varying, character varying, boolean, character varying, character varying, character varying);

DROP FUNCTION get_import_export_rend_tes_function(character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, character varying, date, date, date, date, character varying, character varying, date, date, date, date, date, date, date, date, character varying, boolean, character varying, character varying, character varying, boolean, character varying, integer, integer);

DROP FUNCTION get_pagamenti_inseribili_in_accertamento(character varying, character varying, character varying, character varying, character varying, character varying, character varying, date, date, date, date, date, date, date, date, date, date, boolean, integer, integer);

DROP FUNCTION get_pagamenti_inseriti_in_accertamento(bigint, character varying, character varying, character varying, character varying, character varying, character varying, character varying, date, date, date, date, date, date, date, date, date, date, boolean, integer, integer);

DROP FUNCTION get_rendicontazione_subset_function(character varying, character varying, character varying, date, date, date, date, character varying, character varying, character varying, boolean, integer, integer);

DROP FUNCTION get_tesoreria_subset_function(character varying, date, date, date, date, date, date, text, character varying, character varying, character varying, boolean, character varying, character varying, character varying, integer, integer);

DROP VIEW v_mygov_import_export_rendicontazione_tesoreria;

DROP TABLE mygov_import_export_rendicontazione_tesoreria;

DROP VIEW v_mygov_import_export_rendicontazione_tesoreria_completa;

DROP TABLE mygov_import_export_rendicontazione_tesoreria_completa;


CREATE OR replace VIEW v_mygov_import_export_rendicontazione_tesoreria_completa  AS SELECT    
		COALESCE(import.mygov_ente_id, export.mygov_ente_id, rendicontazione.mygov_ente_id, tesoreria_iuf.mygov_ente_id, tesoreria_iuv.mygov_ente_id, tesoreria_f2k_iuf.mygov_ente_id, tesoreria_f2k_iuv.mygov_ente_id, tesoreria_f2k.mygov_ente_id)                                                                                                                                                                                     AS mygov_ente_id,
          COALESCE(import.cod_rp_silinviarp_id_univoco_versamento, export.cod_rp_silinviarp_id_univoco_versamento, rendicontazione.cod_dati_sing_pagam_identificativo_univoco_versamento, tesoreria_iuv.cod_id_univoco_versamento, tesoreria_f2k_iuv.cod_id_univoco_versamento, 'n/a'::character VARYING)::character VARYING(35)                                                                                                           AS codice_iuv,
          COALESCE(export.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss, rendicontazione.cod_dati_sing_pagam_identificativo_univoco_riscossione, 'n/a'::character VARYING)::character VARYING(35)                                                                                                                                                                                                        AS identificativo_univoco_riscossione,
          COALESCE(rendicontazione.cod_identificativo_flusso, tesoreria_iuf.cod_id_univoco_flusso, tesoreria_f2k_iuf.cod_id_univoco_flusso, 'n/a'::character VARYING)::character VARYING(35)                                                                                                                                                                                                        AS identificativo_flusso_rendicontazione,
          import.mygov_ente_id                                                                                                                                                                                                        AS mygov_ente_id_i,
          import.mygov_manage_flusso_id                                                                                                                                                                                                        AS mygov_manage_flusso_id_i,
          import.cod_iud                                                                                                                                                                                                        AS cod_iud_i,
          import.cod_rp_silinviarp_id_univoco_versamento                                                                                                                                                                                                        AS cod_rp_silinviarp_id_univoco_versamento_i,
          import.cod_rp_sogg_pag_id_univ_pag_tipo_id_univoco                                                                                                                                                                                                        AS cod_rp_sogg_pag_id_univ_pag_tipo_id_univoco_i,
          import.cod_rp_sogg_pag_id_univ_pag_codice_id_univoco                                                                                                                                                                                                        AS cod_rp_sogg_pag_id_univ_pag_codice_id_univoco_i,
          import.de_rp_sogg_pag_anagrafica_pagatore                                                                                                                                                                                                        AS de_rp_sogg_pag_anagrafica_pagatore_i,
          import.de_rp_sogg_pag_indirizzo_pagatore                                                                                                                                                                                                        AS de_rp_sogg_pag_indirizzo_pagatore_i,
          import.de_rp_sogg_pag_civico_pagatore                                                                                                                                                                                                        AS de_rp_sogg_pag_civico_pagatore_i,
          import.cod_rp_sogg_pag_cap_pagatore                                                                                                                                                                                                        AS cod_rp_sogg_pag_cap_pagatore_i,
          import.de_rp_sogg_pag_localita_pagatore                                                                                                                                                                                                        AS de_rp_sogg_pag_localita_pagatore_i,
          import.de_rp_sogg_pag_provincia_pagatore                                                                                                                                                                                                        AS de_rp_sogg_pag_provincia_pagatore_i,
          import.cod_rp_sogg_pag_nazione_pagatore                                                                                                                                                                                                        AS cod_rp_sogg_pag_nazione_pagatore_i,
          import.de_rp_sogg_pag_email_pagatore                                                                                                                                                                                                        AS de_rp_sogg_pag_email_pagatore_i,
          import.dt_rp_dati_vers_data_esecuzione_pagamento                                                                                                                                                                                                        AS dt_rp_dati_vers_data_esecuzione_pagamento_i,
          import.cod_rp_dati_vers_tipo_versamento                                                                                                                                                                                                        AS cod_rp_dati_vers_tipo_versamento_i,
          import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento                                                                                                                                                                                                        AS num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento_i,
          import.num_rp_dati_vers_dati_sing_vers_commissione_carico_pa                                                                                                                                                                                                        AS num_rp_dati_vers_dati_sing_vers_commissione_carico_pa_i,
          import.de_rp_dati_vers_dati_sing_vers_causale_versamento                                                                                                                                                                                                        AS de_rp_dati_vers_dati_sing_vers_causale_versamento_i,
          import.de_rp_dati_vers_dati_sing_vers_dati_specifici_riscossione                                                                                                                                                                                                        AS de_rp_dati_vers_dati_sing_vers_dati_specifici_riscossione_i,
          import.cod_tipo_dovuto                                                                                                                                                                                                        AS cod_tipo_dovuto_i,
          import.bilancio                                                                                                                                                                                                        AS bilancio_i,
          import.dt_acquisizione                                                                                                                                                                                                        AS dt_acquisizione_i,
          export.mygov_ente_id                                                                                                                                                                                                        AS mygov_ente_id_e,
          export.mygov_manage_flusso_id                                                                                                                                                                                                        AS mygov_manage_flusso_id_e,
          export.de_nome_flusso                                                                                                                                                                                                        AS de_nome_flusso_e,
          export.num_riga_flusso                                                                                                                                                                                                        AS num_riga_flusso_e,
          export.cod_iud                                                                                                                                                                                                        AS cod_iud_e,
          export.cod_rp_silinviarp_id_univoco_versamento                                                                                                                                                                                                        AS cod_rp_silinviarp_id_univoco_versamento_e,
          export.de_e_versione_oggetto                                                                                                                                                                                                        AS de_e_versione_oggetto_e,
          export.cod_e_dom_id_dominio                                                                                                                                                                                                        AS cod_e_dom_id_dominio_e,
          export.cod_e_dom_id_stazione_richiedente                                                                                                                                                                                                        AS cod_e_dom_id_stazione_richiedente_e,
          export.cod_e_id_messaggio_ricevuta                                                                                                                                                                                                        AS cod_e_id_messaggio_ricevuta_e,
          export.dt_e_data_ora_messaggio_ricevuta                                                                                                                                                                                                        AS dt_e_data_ora_messaggio_ricevuta_e,
          export.cod_e_riferimento_messaggio_richiesta                                                                                                                                                                                                        AS cod_e_riferimento_messaggio_richiesta_e,
          export.dt_e_riferimento_data_richiesta                                                                                                                                                                                                        AS dt_e_riferimento_data_richiesta_e,
          export.cod_e_istit_att_id_univ_att_tipo_id_univoco                                                                                                                                                                                                        AS cod_e_istit_att_id_univ_att_tipo_id_univoco_e,
          export.cod_e_istit_att_id_univ_att_codice_id_univoco                                                                                                                                                                                                        AS cod_e_istit_att_id_univ_att_codice_id_univoco_e,
          export.de_e_istit_att_denominazione_attestante                                                                                                                                                                                                        AS de_e_istit_att_denominazione_attestante_e,
          export.cod_e_istit_att_codice_unit_oper_attestante                                                                                                                                                                                                        AS cod_e_istit_att_codice_unit_oper_attestante_e,
          export.de_e_istit_att_denom_unit_oper_attestante                                                                                                                                                                                                        AS de_e_istit_att_denom_unit_oper_attestante_e,
          export.de_e_istit_att_indirizzo_attestante                                                                                                                                                                                                        AS de_e_istit_att_indirizzo_attestante_e,
          export.de_e_istit_att_civico_attestante                                                                                                                                                                                                        AS de_e_istit_att_civico_attestante_e,
          export.cod_e_istit_att_cap_attestante                                                                                                                                                                                                        AS cod_e_istit_att_cap_attestante_e,
          export.de_e_istit_att_localita_attestante                                                                                                                                                                                                        AS de_e_istit_att_localita_attestante_e,
          export.de_e_istit_att_provincia_attestante                                                                                                                                                                                                        AS de_e_istit_att_provincia_attestante_e,
          export.cod_e_istit_att_nazione_attestante                                                                                                                                                                                                        AS cod_e_istit_att_nazione_attestante_e,
          export.cod_e_ente_benef_id_univ_benef_tipo_id_univoco                                                                                                                                                                                                        AS cod_e_ente_benef_id_univ_benef_tipo_id_univoco_e,
          export.cod_e_ente_benef_id_univ_benef_codice_id_univoco                                                                                                                                                                                                        AS cod_e_ente_benef_id_univ_benef_codice_id_univoco_e,
          export.de_e_ente_benef_denominazione_beneficiario                                                                                                                                                                                                        AS de_e_ente_benef_denominazione_beneficiario_e,
          export.cod_e_ente_benef_codice_unit_oper_beneficiario                                                                                                                                                                                                        AS cod_e_ente_benef_codice_unit_oper_beneficiario_e,
          export.de_e_ente_benef_denom_unit_oper_beneficiario                                                                                                                                                                                                        AS de_e_ente_benef_denom_unit_oper_beneficiario_e,
          export.de_e_ente_benef_indirizzo_beneficiario                                                                                                                                                                                                        AS de_e_ente_benef_indirizzo_beneficiario_e,
          export.de_e_ente_benef_civico_beneficiario                                                                                                                                                                                                        AS de_e_ente_benef_civico_beneficiario_e,
          export.cod_e_ente_benef_cap_beneficiario                                                                                                                                                                                                        AS cod_e_ente_benef_cap_beneficiario_e,
          export.de_e_ente_benef_localita_beneficiario                                                                                                                                                                                                        AS de_e_ente_benef_localita_beneficiario_e,
          export.de_e_ente_benef_provincia_beneficiario                                                                                                                                                                                                        AS de_e_ente_benef_provincia_beneficiario_e,
          export.cod_e_ente_benef_nazione_beneficiario                                                                                                                                                                                                        AS cod_e_ente_benef_nazione_beneficiario_e,
          export.cod_e_sogg_vers_id_univ_vers_tipo_id_univoco                                                                                                                                                                                                        AS cod_e_sogg_vers_id_univ_vers_tipo_id_univoco_e,
          export.cod_e_sogg_vers_id_univ_vers_codice_id_univoco                                                                                                                                                                                                        AS cod_e_sogg_vers_id_univ_vers_codice_id_univoco_e,
          export.cod_e_sogg_vers_anagrafica_versante                                                                                                                                                                                                        AS cod_e_sogg_vers_anagrafica_versante_e,
          export.de_e_sogg_vers_indirizzo_versante                                                                                                                                                                                                        AS de_e_sogg_vers_indirizzo_versante_e,
          export.de_e_sogg_vers_civico_versante                                                                                                                                                                                                        AS de_e_sogg_vers_civico_versante_e,
          export.cod_e_sogg_vers_cap_versante                                                                                                                                                                                                        AS cod_e_sogg_vers_cap_versante_e,
          export.de_e_sogg_vers_localita_versante                                                                                                                                                                                                        AS de_e_sogg_vers_localita_versante_e,
          export.de_e_sogg_vers_provincia_versante                                                                                                                                                                                                        AS de_e_sogg_vers_provincia_versante_e,
          export.cod_e_sogg_vers_nazione_versante                                                                                                                                                                                                        AS cod_e_sogg_vers_nazione_versante_e,
          export.de_e_sogg_vers_email_versante                                                                                                                                                                                                        AS de_e_sogg_vers_email_versante_e,
          export.cod_e_sogg_pag_id_univ_pag_tipo_id_univoco                                                                                                                                                                                                        AS cod_e_sogg_pag_id_univ_pag_tipo_id_univoco_e,
          export.cod_e_sogg_pag_id_univ_pag_codice_id_univoco                                                                                                                                                                                                        AS cod_e_sogg_pag_id_univ_pag_codice_id_univoco_e,
          export.cod_e_sogg_pag_anagrafica_pagatore                                                                                                                                                                                                        AS cod_e_sogg_pag_anagrafica_pagatore_e,
          export.de_e_sogg_pag_indirizzo_pagatore                                                                                                                                                                                                        AS de_e_sogg_pag_indirizzo_pagatore_e,
          export.de_e_sogg_pag_civico_pagatore                                                                                                                                                                                                        AS de_e_sogg_pag_civico_pagatore_e,
          export.cod_e_sogg_pag_cap_pagatore                                                                                                                                                                                                        AS cod_e_sogg_pag_cap_pagatore_e,
          export.de_e_sogg_pag_localita_pagatore                                                                                                                                                                                                        AS de_e_sogg_pag_localita_pagatore_e,
          export.de_e_sogg_pag_provincia_pagatore                                                                                                                                                                                                        AS de_e_sogg_pag_provincia_pagatore_e,
          export.cod_e_sogg_pag_nazione_pagatore                                                                                                                                                                                                        AS cod_e_sogg_pag_nazione_pagatore_e,
          export.de_e_sogg_pag_email_pagatore                                                                                                                                                                                                        AS de_e_sogg_pag_email_pagatore_e,
          export.cod_e_dati_pag_codice_esito_pagamento                                                                                                                                                                                                        AS cod_e_dati_pag_codice_esito_pagamento_e,
          export.num_e_dati_pag_importo_totale_pagato                                                                                                                                                                                                        AS num_e_dati_pag_importo_totale_pagato_e,
          export.cod_e_dati_pag_id_univoco_versamento                                                                                                                                                                                                        AS cod_e_dati_pag_id_univoco_versamento_e,
          export.cod_e_dati_pag_codice_contesto_pagamento                                                                                                                                                                                                        AS cod_e_dati_pag_codice_contesto_pagamento_e,
          export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato                                                                                                                                                                                                        AS num_e_dati_pag_dati_sing_pag_singolo_importo_pagato_e,
          export.de_e_dati_pag_dati_sing_pag_esito_singolo_pagamento                                                                                                                                                                                                        AS de_e_dati_pag_dati_sing_pag_esito_singolo_pagamento_e,
          export.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento                                                                                                                                                                                                        AS dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e,
          export.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss                                                                                                                                                                                                        AS cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss_e,
          export.de_e_dati_pag_dati_sing_pag_causale_versamento                                                                                                                                                                                                        AS de_e_dati_pag_dati_sing_pag_causale_versamento_e,
          export.de_e_dati_pag_dati_sing_pag_dati_specifici_riscossione                                                                                                                                                                                                        AS de_e_dati_pag_dati_sing_pag_dati_specifici_riscossione_e,
          export.cod_tipo_dovuto                                                                                                                                                                                                        AS cod_tipo_dovuto_e,
          export.dt_acquisizione                                                                                                                                                                                                        AS dt_acquisizione_e,
          export.indice_dati_singolo_pagamento                                                                                                                                                                                                        AS indice_dati_singolo_pagamento_e,

          export.bilancio AS bilancio_e,

          rendicontazione.mygov_ente_id                                                                                                                                                                                                        AS mygov_ente_id_r,
          rendicontazione.mygov_manage_flusso_id                                                                                                                                                                                                        AS mygov_manage_flusso_id_r,
          rendicontazione.versione_oggetto                                                                                                                                                                                                        AS versione_oggetto_r,
          rendicontazione.cod_identificativo_flusso                                                                                                                                                                                                        AS cod_identificativo_flusso_r,
          rendicontazione.dt_data_ora_flusso                                                                                                                                                                                                        AS dt_data_ora_flusso_r,
          rendicontazione.cod_identificativo_univoco_regolamento                                                                                                                                                                                                        AS cod_identificativo_univoco_regolamento_r,
          rendicontazione.dt_data_regolamento                                                                                                                                                                                                        AS dt_data_regolamento_r,
          rendicontazione.cod_ist_mitt_id_univ_mitt_tipo_identificativo_univoco                                                                                                                                                                                                        AS cod_ist_mitt_id_univ_mitt_tipo_identificativo_univoco_r,
          rendicontazione.cod_ist_mitt_id_univ_mitt_codice_identificativo_univoco                                                                                                                                                                                                        AS cod_ist_mitt_id_univ_mitt_codice_identificativo_univoco_r,
          rendicontazione.de_ist_mitt_denominazione_mittente                                                                                                                                                                                                        AS de_ist_mitt_denominazione_mittente_r,
          rendicontazione.cod_ist_ricev_id_univ_ricev_tipo_identificativo_univoco                                                                                                                                                                                                        AS cod_ist_ricev_id_univ_ricev_tipo_identificativo_univoco_r,
          rendicontazione.cod_ist_ricev_id_univ_ricev_codice_identificativo_univoco                                                                                                                                                                                                        AS cod_ist_ricev_id_univ_ricev_codice_identificativo_univoco_r,
          rendicontazione.de_ist_ricev_denominazione_ricevente                                                                                                                                                                                                        AS de_ist_ricev_denominazione_ricevente_r,
          rendicontazione.num_numero_totale_pagamenti                                                                                                                                                                                                        AS num_numero_totale_pagamenti_r,
          rendicontazione.num_importo_totale_pagamenti                                                                                                                                                                                                        AS num_importo_totale_pagamenti_r,
          rendicontazione.cod_dati_sing_pagam_identificativo_univoco_versamento                                                                                                                                                                                                        AS cod_dati_sing_pagam_identificativo_univoco_versamento_r,
          rendicontazione.cod_dati_sing_pagam_identificativo_univoco_riscossione                                                                                                                                                                                                        AS cod_dati_sing_pagam_identificativo_univoco_riscossione_r,
          rendicontazione.num_dati_sing_pagam_singolo_importo_pagato                                                                                                                                                                                                        AS num_dati_sing_pagam_singolo_importo_pagato_r,
          rendicontazione.cod_dati_sing_pagam_codice_esito_singolo_pagamento                                                                                                                                                                                                        AS cod_dati_sing_pagam_codice_esito_singolo_pagamento_r,
          rendicontazione.dt_dati_sing_pagam_data_esito_singolo_pagamento                                                                                                                                                                                                        AS dt_dati_sing_pagam_data_esito_singolo_pagamento_r,
          rendicontazione.dt_acquisizione                                                                                                                                                                                                        AS dt_acquisizione_r,
          rendicontazione.indice_dati_singolo_pagamento                                                                                                                                                                                                        AS indice_dati_singolo_pagamento_r,
          COALESCE(tesoreria_iuf.mygov_ente_id, tesoreria_iuv.mygov_ente_id, tesoreria_f2k_iuf.mygov_ente_id, tesoreria_f2k_iuv.mygov_ente_id, tesoreria_f2k.mygov_ente_id)                                                                                                                                                                                                        AS mygov_ente_id_t,
          COALESCE(tesoreria_iuf.mygov_manage_flusso_id, tesoreria_iuv.mygov_manage_flusso_id)                                                                                                                                                                                                        AS mygov_manage_flusso_id_t,
          COALESCE(tesoreria_iuf.cod_abi, tesoreria_iuv.cod_abi, tesoreria_f2k_iuf.cod_abi, tesoreria_f2k_iuv.cod_abi, tesoreria_f2k.cod_abi)                                                                                                                                                                                                        AS cod_abi_t,
          COALESCE(tesoreria_iuf.cod_cab, tesoreria_iuv.cod_cab, tesoreria_f2k_iuf.cod_cab, tesoreria_f2k_iuv.cod_cab, tesoreria_f2k.cod_cab)                                                                                                                                                                                                        AS cod_cab_t,
          COALESCE(tesoreria_iuf.cod_conto, tesoreria_iuv.cod_conto, tesoreria_f2k_iuf.cod_conto, tesoreria_f2k_iuv.cod_conto, tesoreria_f2k.cod_conto)                                                                                                                                                                                                        AS cod_conto_t,
          COALESCE(tesoreria_iuf.cod_divisa, tesoreria_iuv.cod_divisa)                                                                                                                                                                                                        AS cod_divisa_t,
          COALESCE(tesoreria_iuf.dt_data_contabile::timestamp without time zone, tesoreria_iuv.dt_data_contabile::timestamp without time zone, tesoreria_f2k_iuf.dt_bolletta, tesoreria_f2k_iuv.dt_bolletta, tesoreria_f2k.dt_bolletta)                                                                                                                                                                                                    AS dt_data_contabile_t,
          COALESCE(tesoreria_iuf.dt_data_valuta::timestamp without    time zone, tesoreria_iuv.dt_data_valuta::timestamp without time zone, tesoreria_f2k_iuf.dt_data_valuta_regione, tesoreria_f2k_iuv.dt_data_valuta_regione, tesoreria_f2k.dt_data_valuta_regione)                                                                                                                                                                      AS dt_data_valuta_t,
          COALESCE(tesoreria_iuf.num_importo, tesoreria_iuv.num_importo, tesoreria_f2k_iuf.num_ip_bolletta, tesoreria_f2k_iuv.num_ip_bolletta, tesoreria_f2k.num_ip_bolletta)                                                                                                                                                                                                        AS num_importo_t,
          COALESCE(tesoreria_iuf.cod_segno, tesoreria_iuv.cod_segno)                                                                                                                                                                                                        AS cod_segno_t,
          COALESCE(tesoreria_iuf.de_causale, tesoreria_iuv.de_causale, tesoreria_f2k_iuf.de_causale::text, tesoreria_f2k_iuv.de_causale::text, tesoreria_f2k.de_causale::text)                                                                                                                                                                                                        AS de_causale_t,
          COALESCE(tesoreria_iuf.cod_numero_assegno, tesoreria_iuv.cod_numero_assegno)                                                                                                                                                                                                        AS cod_numero_assegno_t,
          COALESCE(tesoreria_iuf.cod_riferimento_banca, tesoreria_iuv.cod_riferimento_banca)                                                                                                                                                                                                        AS cod_riferimento_banca_t,
          COALESCE(tesoreria_iuf.cod_riferimento_cliente, tesoreria_iuv.cod_riferimento_cliente)                                                                                                                                                                                                        AS cod_riferimento_cliente_t,
          COALESCE(tesoreria_iuf.dt_data_ordine, tesoreria_iuv.dt_data_ordine)                                                                                                                                                                                                        AS dt_data_ordine_t,
          COALESCE(tesoreria_iuf.de_descrizione_ordinante, tesoreria_iuv.de_descrizione_ordinante, tesoreria_f2k_iuf.de_cognome::text, tesoreria_f2k_iuv.de_cognome::text, tesoreria_f2k.de_cognome::text)                                                                                                                                                                                                        AS de_descrizione_ordinante_t,
          COALESCE(tesoreria_iuf.cod_bi2, tesoreria_iuv.cod_bi2)                                                                                                                                                                                                        AS cod_bi2_t,
          COALESCE(tesoreria_iuf.cod_be1, tesoreria_iuv.cod_be1)                                                                                                                                                                                                        AS cod_be1_t,
          COALESCE(tesoreria_iuf.cod_ib1, tesoreria_iuv.cod_ib1)                                                                                                                                                                                                        AS cod_ib1_t,
          COALESCE(tesoreria_iuf.cod_ib2, tesoreria_iuv.cod_ib2)                                                                                                                                                                                                        AS cod_ib2_t,
          COALESCE(tesoreria_iuf.cod_ib4, tesoreria_iuv.cod_ib4)                                                                                                                                                                                                        AS cod_ib4_t,
          COALESCE(tesoreria_iuf.cod_tid, tesoreria_iuv.cod_tid)                                                                                                                                                                                                        AS cod_tid_t,
          COALESCE(tesoreria_iuf.cod_dte, tesoreria_iuv.cod_dte)                                                                                                                                                                                                        AS cod_dte_t,
          COALESCE(tesoreria_iuf.cod_dtn, tesoreria_iuv.cod_dtn)                                                                                                                                                                                                        AS cod_dtn_t,
          COALESCE(tesoreria_iuf.cod_eri, tesoreria_iuv.cod_eri)                                                                                                                                                                                                        AS cod_eri_t,
          COALESCE(tesoreria_iuf.cod_im2, tesoreria_iuv.cod_im2)                                                                                                                                                                                                        AS cod_im2_t,
          COALESCE(tesoreria_iuf.cod_ma2, tesoreria_iuv.cod_ma2)                                                                                                                                                                                                        AS cod_ma2_t,
          COALESCE(tesoreria_iuf.cod_ri3, tesoreria_iuv.cod_ri3)                                                                                                                                                                                                        AS cod_ri3_t,
          COALESCE(tesoreria_iuf.cod_or1, tesoreria_iuv.cod_or1, tesoreria_f2k_iuf.de_cognome::text, tesoreria_f2k_iuv.de_cognome::text, tesoreria_f2k.de_cognome::text)                                                                                                                                                                                                        AS cod_or1_t,
          COALESCE(tesoreria_iuf.cod_sc2, tesoreria_iuv.cod_sc2)                                                                                                                                                                                                        AS cod_sc2_t,
          COALESCE(tesoreria_iuf.cod_tr1, tesoreria_iuv.cod_tr1)                                                                                                                                                                                                        AS cod_tr1_t,
          COALESCE(tesoreria_iuf.cod_sec, tesoreria_iuv.cod_sec)                                                                                                                                                                                                        AS cod_sec_t,
          COALESCE(tesoreria_iuf.cod_ior, tesoreria_iuv.cod_ior)                                                                                                                                                                                                        AS cod_ior_t,
          COALESCE(tesoreria_iuf.cod_id_univoco_flusso, tesoreria_f2k_iuf.cod_id_univoco_flusso, tesoreria_f2k_iuv.cod_id_univoco_flusso, tesoreria_f2k.cod_id_univoco_flusso)                                                                                                                                                                                                        AS cod_id_univoco_flusso_t,
          COALESCE(tesoreria_iuv.cod_id_univoco_versamento, tesoreria_f2k_iuf.cod_id_univoco_versamento, tesoreria_f2k_iuv.cod_id_univoco_versamento, tesoreria_f2k.cod_id_univoco_versamento)                                                                                                                                                                                                        AS cod_id_univoco_versamento_t,
          COALESCE(tesoreria_iuf.dt_acquisizione::timestamp without time zone, tesoreria_iuv.dt_acquisizione::timestamp without time zone, tesoreria_f2k_iuf.dt_ultima_modifica, tesoreria_f2k_iuv.dt_ultima_modifica, tesoreria_f2k.dt_ultima_modifica)                                                                                                                                                                                   AS dt_acquisizione_t,
          COALESCE(tesoreria_f2k_iuf.de_anno_bolletta, tesoreria_f2k_iuv.de_anno_bolletta, tesoreria_f2k.de_anno_bolletta)                                                                                                                                                                                                        AS de_anno_bolletta_t,
          COALESCE(tesoreria_f2k_iuf.cod_bolletta, tesoreria_f2k_iuv.cod_bolletta, tesoreria_f2k.cod_bolletta)                                                                                                                                                                                                        AS cod_bolletta_t,
          COALESCE(tesoreria_f2k_iuf.cod_id_dominio, tesoreria_f2k_iuv.cod_id_dominio, tesoreria_f2k.cod_id_dominio)                                                                                                                                                                                                        AS cod_id_dominio_t,
          COALESCE(tesoreria_f2k_iuf.dt_ricezione, tesoreria_f2k_iuv.dt_ricezione, tesoreria_f2k.dt_ricezione)                                                                                                                                                                                                        AS dt_ricezione_t,
          COALESCE(tesoreria_f2k_iuf.de_anno_documento, tesoreria_f2k_iuv.de_anno_documento, tesoreria_f2k.de_anno_documento)                                                                                                                                                                                                        AS de_anno_documento_t,
          COALESCE(tesoreria_f2k_iuf.cod_documento, tesoreria_f2k_iuv.cod_documento, tesoreria_f2k.cod_documento)                                                                                                                                                                                                        AS cod_documento_t,
          COALESCE(tesoreria_f2k_iuf.de_ae_provvisorio, tesoreria_f2k_iuv.de_ae_provvisorio, tesoreria_f2k.de_ae_provvisorio)                                                                                                                                                                                                        AS de_anno_provvisorio_t,
          COALESCE(tesoreria_f2k_iuf.cod_provvisorio, tesoreria_f2k_iuv.cod_provvisorio, tesoreria_f2k.cod_provvisorio)                                                                                                                                                                                                        AS cod_provvisorio_t,
          classificazione.mygov_classificazione_codice                                                                                                                                                                                                        AS classificazione_completezza,
          greatest(import.dt_acquisizione::timestamp without time zone, export.dt_acquisizione::timestamp without time zone, rendicontazione.dt_acquisizione::timestamp without time zone, COALESCE(tesoreria_iuf.dt_acquisizione::timestamp without time zone, tesoreria_iuv.dt_acquisizione::timestamp without time zone, tesoreria_f2k_iuf.dt_ultima_modifica, tesoreria_f2k_iuv.dt_ultima_modifica, tesoreria_f2k.dt_ultima_modifica)) AS dt_data_ultimo_aggiornamento
FROM      mygov_flusso_import import
FULL JOIN mygov_flusso_export export
ON        import.mygov_ente_id = export.mygov_ente_id
AND       import.cod_iud::text = export.cod_iud::text
FULL JOIN
          (
                 SELECT mygov_flusso_rendicontazione.version,
                        mygov_flusso_rendicontazione.dt_creazione,
                        mygov_flusso_rendicontazione.dt_ultima_modifica,
                        mygov_flusso_rendicontazione.mygov_ente_id,
                        mygov_flusso_rendicontazione.mygov_manage_flusso_id,
                        mygov_flusso_rendicontazione.identificativo_psp,
                        mygov_flusso_rendicontazione.versione_oggetto,
                        mygov_flusso_rendicontazione.cod_identificativo_flusso,
                        mygov_flusso_rendicontazione.dt_data_ora_flusso,
                        mygov_flusso_rendicontazione.cod_identificativo_univoco_regolamento,
                        mygov_flusso_rendicontazione.dt_data_regolamento,
                        mygov_flusso_rendicontazione.cod_ist_mitt_id_univ_mitt_tipo_identificativo_univoco,
                        mygov_flusso_rendicontazione.cod_ist_mitt_id_univ_mitt_codice_identificativo_univoco,
                        mygov_flusso_rendicontazione.de_ist_mitt_denominazione_mittente,
                        mygov_flusso_rendicontazione.cod_ist_ricev_id_univ_ricev_tipo_identificativo_univoco,
                        mygov_flusso_rendicontazione.cod_ist_ricev_id_univ_ricev_codice_identificativo_univoco,
                        mygov_flusso_rendicontazione.de_ist_ricev_denominazione_ricevente,
                        mygov_flusso_rendicontazione.num_numero_totale_pagamenti,
                        mygov_flusso_rendicontazione.num_importo_totale_pagamenti,
                        mygov_flusso_rendicontazione.cod_dati_sing_pagam_identificativo_univoco_versamento,
                        mygov_flusso_rendicontazione.cod_dati_sing_pagam_identificativo_univoco_riscossione,
                        mygov_flusso_rendicontazione.num_dati_sing_pagam_singolo_importo_pagato,
                        mygov_flusso_rendicontazione.cod_dati_sing_pagam_codice_esito_singolo_pagamento,
                        mygov_flusso_rendicontazione.dt_dati_sing_pagam_data_esito_singolo_pagamento,
                        mygov_flusso_rendicontazione.dt_acquisizione,
                        mygov_flusso_rendicontazione.indice_dati_singolo_pagamento,
                        mygov_flusso_rendicontazione.codice_bic_banca_di_riversamento
                 FROM   mygov_flusso_rendicontazione
                 WHERE  mygov_flusso_rendicontazione.cod_dati_sing_pagam_codice_esito_singolo_pagamento::text <> '3'::text) rendicontazione
ON        export.mygov_ente_id = rendicontazione.mygov_ente_id
AND       export.cod_rp_silinviarp_id_univoco_versamento::text = rendicontazione.cod_dati_sing_pagam_identificativo_univoco_versamento::text
AND       export.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss::text = rendicontazione.cod_dati_sing_pagam_identificativo_univoco_riscossione::text
AND       export.indice_dati_singolo_pagamento = rendicontazione.indice_dati_singolo_pagamento
FULL JOIN
          (
                 SELECT mygov_flusso_tesoreria_iuf.mygov_ente_id,
                        mygov_flusso_tesoreria_iuf.mygov_manage_flusso_id,
                        mygov_flusso_tesoreria_iuf.cod_abi,
                        mygov_flusso_tesoreria_iuf.cod_cab,
                        mygov_flusso_tesoreria_iuf.cod_conto,
                        mygov_flusso_tesoreria_iuf.cod_divisa,
                        mygov_flusso_tesoreria_iuf.dt_data_contabile,
                        mygov_flusso_tesoreria_iuf.dt_data_valuta,
                        mygov_flusso_tesoreria_iuf.num_importo,
                        mygov_flusso_tesoreria_iuf.cod_segno,
                        mygov_flusso_tesoreria_iuf.de_causale,
                        mygov_flusso_tesoreria_iuf.cod_numero_assegno,
                        mygov_flusso_tesoreria_iuf.cod_riferimento_banca,
                        mygov_flusso_tesoreria_iuf.cod_riferimento_cliente,
                        mygov_flusso_tesoreria_iuf.dt_data_ordine,
                        mygov_flusso_tesoreria_iuf.de_descrizione_ordinante,
                        mygov_flusso_tesoreria_iuf.cod_bi2,
                        mygov_flusso_tesoreria_iuf.cod_be1,
                        mygov_flusso_tesoreria_iuf.cod_ib1,
                        mygov_flusso_tesoreria_iuf.cod_ib2,
                        mygov_flusso_tesoreria_iuf.cod_ib4,
                        mygov_flusso_tesoreria_iuf.cod_tid,
                        mygov_flusso_tesoreria_iuf.cod_dte,
                        mygov_flusso_tesoreria_iuf.cod_dtn,
                        mygov_flusso_tesoreria_iuf.cod_eri,
                        mygov_flusso_tesoreria_iuf.cod_im2,
                        mygov_flusso_tesoreria_iuf.cod_ma2,
                        mygov_flusso_tesoreria_iuf.cod_ri3,
                        mygov_flusso_tesoreria_iuf.cod_or1,
                        mygov_flusso_tesoreria_iuf.cod_sc2,
                        mygov_flusso_tesoreria_iuf.cod_tr1,
                        mygov_flusso_tesoreria_iuf.cod_sec,
                        mygov_flusso_tesoreria_iuf.cod_ior,
                        mygov_flusso_tesoreria_iuf.cod_id_univoco_flusso,
                        NULL::character VARYING AS cod_id_univoco_versamento,
                        mygov_flusso_tesoreria_iuf.dt_acquisizione
                 FROM   mygov_flusso_tesoreria_iuf) tesoreria_iuf
ON        tesoreria_iuf.cod_id_univoco_flusso::text = rendicontazione.cod_identificativo_flusso::text
FULL JOIN
          (
                 SELECT mygov_flusso_tesoreria_iuv.mygov_ente_id,
                        mygov_flusso_tesoreria_iuv.mygov_manage_flusso_id,
                        mygov_flusso_tesoreria_iuv.cod_abi,
                        mygov_flusso_tesoreria_iuv.cod_cab,
                        mygov_flusso_tesoreria_iuv.cod_conto,
                        mygov_flusso_tesoreria_iuv.cod_divisa,
                        mygov_flusso_tesoreria_iuv.dt_data_contabile,
                        mygov_flusso_tesoreria_iuv.dt_data_valuta,
                        mygov_flusso_tesoreria_iuv.num_importo,
                        mygov_flusso_tesoreria_iuv.cod_segno,
                        mygov_flusso_tesoreria_iuv.de_causale,
                        mygov_flusso_tesoreria_iuv.cod_numero_assegno,
                        mygov_flusso_tesoreria_iuv.cod_riferimento_banca,
                        mygov_flusso_tesoreria_iuv.cod_riferimento_cliente,
                        mygov_flusso_tesoreria_iuv.dt_data_ordine,
                        mygov_flusso_tesoreria_iuv.de_descrizione_ordinante,
                        mygov_flusso_tesoreria_iuv.cod_bi2,
                        mygov_flusso_tesoreria_iuv.cod_be1,
                        mygov_flusso_tesoreria_iuv.cod_ib1,
                        mygov_flusso_tesoreria_iuv.cod_ib2,
                        mygov_flusso_tesoreria_iuv.cod_ib4,
                        mygov_flusso_tesoreria_iuv.cod_tid,
                        mygov_flusso_tesoreria_iuv.cod_dte,
                        mygov_flusso_tesoreria_iuv.cod_dtn,
                        mygov_flusso_tesoreria_iuv.cod_eri,
                        mygov_flusso_tesoreria_iuv.cod_im2,
                        mygov_flusso_tesoreria_iuv.cod_ma2,
                        mygov_flusso_tesoreria_iuv.cod_ri3,
                        mygov_flusso_tesoreria_iuv.cod_or1,
                        mygov_flusso_tesoreria_iuv.cod_sc2,
                        mygov_flusso_tesoreria_iuv.cod_tr1,
                        mygov_flusso_tesoreria_iuv.cod_sec,
                        mygov_flusso_tesoreria_iuv.cod_ior,
                        NULL::character VARYING AS cod_id_univoco_flusso,
                        mygov_flusso_tesoreria_iuv.cod_id_univoco_versamento,
                        mygov_flusso_tesoreria_iuv.dt_acquisizione
                 FROM   mygov_flusso_tesoreria_iuv) tesoreria_iuv
ON        COALESCE(export.mygov_ente_id, rendicontazione.mygov_ente_id) = tesoreria_iuv.mygov_ente_id
AND       COALESCE(export.cod_rp_silinviarp_id_univoco_versamento, rendicontazione.cod_dati_sing_pagam_identificativo_univoco_versamento)::text = tesoreria_iuv.cod_id_univoco_versamento::text
FULL JOIN
          (
                 SELECT mygov_flusso_tesoreria.de_anno_bolletta,
                        mygov_flusso_tesoreria.cod_bolletta,
                        mygov_flusso_tesoreria.cod_conto,
                        mygov_flusso_tesoreria.cod_id_dominio,
                        mygov_flusso_tesoreria.de_causale,
                        mygov_flusso_tesoreria.num_ip_bolletta,
                        mygov_flusso_tesoreria.dt_bolletta,
                        mygov_flusso_tesoreria.dt_ricezione,
                        mygov_flusso_tesoreria.de_anno_documento,
                        mygov_flusso_tesoreria.cod_documento,
                        mygov_flusso_tesoreria.de_cognome,
                        mygov_flusso_tesoreria.cod_abi,
                        mygov_flusso_tesoreria.cod_cab,
                        mygov_flusso_tesoreria.de_ae_provvisorio,
                        mygov_flusso_tesoreria.cod_provvisorio,
                        mygov_flusso_tesoreria.dt_data_valuta_regione,
                        mygov_flusso_tesoreria.mygov_ente_id,
                        mygov_flusso_tesoreria.cod_id_univoco_flusso,
                        mygov_flusso_tesoreria.cod_id_univoco_versamento,
                        mygov_flusso_tesoreria.dt_creazione,
                        mygov_flusso_tesoreria.dt_ultima_modifica
                 FROM   mygov_flusso_tesoreria
                 WHERE  mygov_flusso_tesoreria.cod_id_univoco_flusso IS NOT NULL
                 AND    mygov_flusso_tesoreria.cod_id_univoco_versamento IS NULL) tesoreria_f2k_iuf
ON        COALESCE(export.mygov_ente_id, rendicontazione.mygov_ente_id) = tesoreria_f2k_iuf.mygov_ente_id
AND       rendicontazione.cod_identificativo_flusso::text = tesoreria_f2k_iuf.cod_id_univoco_flusso::text
FULL JOIN
          (
                 SELECT mygov_flusso_tesoreria.de_anno_bolletta,
                        mygov_flusso_tesoreria.cod_bolletta,
                        mygov_flusso_tesoreria.cod_conto,
                        mygov_flusso_tesoreria.cod_id_dominio,
                        mygov_flusso_tesoreria.de_causale,
                        mygov_flusso_tesoreria.num_ip_bolletta,
                        mygov_flusso_tesoreria.dt_bolletta,
                        mygov_flusso_tesoreria.dt_ricezione,
                        mygov_flusso_tesoreria.de_anno_documento,
                        mygov_flusso_tesoreria.cod_documento,
                        mygov_flusso_tesoreria.de_cognome,
                        mygov_flusso_tesoreria.cod_abi,
                        mygov_flusso_tesoreria.cod_cab,
                        mygov_flusso_tesoreria.de_ae_provvisorio,
                        mygov_flusso_tesoreria.cod_provvisorio,
                        mygov_flusso_tesoreria.dt_data_valuta_regione,
                        mygov_flusso_tesoreria.mygov_ente_id,
                        mygov_flusso_tesoreria.cod_id_univoco_flusso,
                        mygov_flusso_tesoreria.cod_id_univoco_versamento,
                        mygov_flusso_tesoreria.dt_creazione,
                        mygov_flusso_tesoreria.dt_ultima_modifica
                 FROM   mygov_flusso_tesoreria
                 WHERE  mygov_flusso_tesoreria.cod_id_univoco_flusso IS NULL
                 AND    mygov_flusso_tesoreria.cod_id_univoco_versamento IS NOT NULL) tesoreria_f2k_iuv
ON        COALESCE(export.mygov_ente_id, rendicontazione.mygov_ente_id) = tesoreria_f2k_iuv.mygov_ente_id
AND       COALESCE(export.cod_rp_silinviarp_id_univoco_versamento, rendicontazione.cod_dati_sing_pagam_identificativo_univoco_versamento)::text = tesoreria_f2k_iuv.cod_id_univoco_versamento::text
FULL JOIN
          (
                 SELECT mygov_flusso_tesoreria.de_anno_bolletta,
                        mygov_flusso_tesoreria.cod_bolletta,
                        mygov_flusso_tesoreria.cod_conto,
                        mygov_flusso_tesoreria.cod_id_dominio,
                        mygov_flusso_tesoreria.de_causale,
                        mygov_flusso_tesoreria.num_ip_bolletta,
                        mygov_flusso_tesoreria.dt_bolletta,
                        mygov_flusso_tesoreria.dt_ricezione,
                        mygov_flusso_tesoreria.de_anno_documento,
                        mygov_flusso_tesoreria.cod_documento,
                        mygov_flusso_tesoreria.de_cognome,
                        mygov_flusso_tesoreria.cod_abi,
                        mygov_flusso_tesoreria.cod_cab,
                        mygov_flusso_tesoreria.de_ae_provvisorio,
                        mygov_flusso_tesoreria.cod_provvisorio,
                        mygov_flusso_tesoreria.dt_data_valuta_regione,
                        mygov_flusso_tesoreria.mygov_ente_id,
                        mygov_flusso_tesoreria.cod_id_univoco_flusso,
                        mygov_flusso_tesoreria.cod_id_univoco_versamento,
                        mygov_flusso_tesoreria.dt_creazione,
                        mygov_flusso_tesoreria.dt_ultima_modifica
                 FROM   mygov_flusso_tesoreria
                 WHERE  mygov_flusso_tesoreria.cod_id_univoco_flusso IS NULL
                 AND    mygov_flusso_tesoreria.cod_id_univoco_versamento IS NULL) tesoreria_f2k
ON        1 = 0
LEFT JOIN mygov_classificazione_completezza classificazione
ON        classificazione.mygov_classificazione_codice::text = 'IUD_RT_IUF_TES'::text
AND       import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento IS NOT NULL
AND       export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL
AND       import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento = export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato
AND       (
                    rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NOT NULL
          AND       export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato = rendicontazione.num_dati_sing_pagam_singolo_importo_pagato
          AND       rendicontazione.num_importo_totale_pagamenti IS NOT NULL
          AND       (
                              tesoreria_iuf.num_importo IS NOT NULL
                    AND       rendicontazione.num_importo_totale_pagamenti = tesoreria_iuf.num_importo
                    OR        tesoreria_f2k_iuf.num_ip_bolletta IS NOT NULL
                    AND       rendicontazione.num_importo_totale_pagamenti = tesoreria_f2k_iuf.num_ip_bolletta)
          OR        tesoreria_iuv.num_importo IS NOT NULL
          AND       export.num_e_dati_pag_importo_totale_pagato = tesoreria_iuv.num_importo
          OR        tesoreria_f2k_iuv.num_ip_bolletta IS NOT NULL
          AND       export.num_e_dati_pag_importo_totale_pagato = tesoreria_f2k_iuv.num_ip_bolletta)
OR        classificazione.mygov_classificazione_codice::text = 'IUD_RT_IUF'::text
AND       import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento IS NOT NULL
AND       export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL
AND       import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento = export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato
AND       rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NOT NULL
AND       export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato = rendicontazione.num_dati_sing_pagam_singolo_importo_pagato
OR        classificazione.mygov_classificazione_codice::text = 'RT_IUF_TES'::text
AND       export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL
AND       (
                    rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NOT NULL
          AND       export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato = rendicontazione.num_dati_sing_pagam_singolo_importo_pagato
          AND       rendicontazione.num_importo_totale_pagamenti IS NOT NULL
          AND       (
                              tesoreria_iuf.num_importo IS NOT NULL
                    AND       rendicontazione.num_importo_totale_pagamenti = tesoreria_iuf.num_importo
                    OR        tesoreria_f2k_iuf.num_ip_bolletta IS NOT NULL
                    AND       rendicontazione.num_importo_totale_pagamenti = tesoreria_f2k_iuf.num_ip_bolletta)
          )
OR        classificazione.mygov_classificazione_codice::text = 'RT_IUF'::text
AND       export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL
AND       rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NOT NULL
AND       export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato = rendicontazione.num_dati_sing_pagam_singolo_importo_pagato
OR        classificazione.mygov_classificazione_codice::text = 'RT_NO_IUF'::text
AND       export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL
AND       (
          NOT       (tesoreria_iuv.num_importo IS NOT NULL
                    AND       export.num_e_dati_pag_importo_totale_pagato = tesoreria_iuv.num_importo
                    OR        tesoreria_f2k_iuv.num_ip_bolletta IS NOT NULL
                    AND       export.num_e_dati_pag_importo_totale_pagato = tesoreria_f2k_iuv.num_ip_bolletta
                    OR        tesoreria_iuv.num_importo IS NOT NULL
                    AND       export.num_e_dati_pag_importo_totale_pagato <> tesoreria_iuv.num_importo
                    OR        tesoreria_f2k_iuv.num_ip_bolletta IS NOT NULL
                    AND       export.num_e_dati_pag_importo_totale_pagato <> tesoreria_f2k_iuv.num_ip_bolletta)  
          AND       (rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NULL
                    OR        rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NOT NULL
                    AND       export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato <> rendicontazione.num_dati_sing_pagam_singolo_importo_pagato
                    )
          )
OR        classificazione.mygov_classificazione_codice::text = 'IUF_NO_TES'::text
AND       rendicontazione.num_importo_totale_pagamenti IS NOT NULL
AND       (
                    tesoreria_iuf.num_importo IS NULL
          AND       tesoreria_iuv.num_importo IS NULL
          AND       tesoreria_f2k_iuf.num_ip_bolletta IS NULL
          AND       tesoreria_f2k_iuv.num_ip_bolletta IS NULL)
OR        classificazione.mygov_classificazione_codice::text = 'IUF_TES_DIV_IMP'::text
AND       rendicontazione.num_importo_totale_pagamenti IS NOT NULL
AND       (
                    tesoreria_iuf.num_importo IS NOT NULL
          AND       rendicontazione.num_importo_totale_pagamenti <> tesoreria_iuf.num_importo
          OR        tesoreria_iuv.num_importo IS NOT NULL
          AND       rendicontazione.num_importo_totale_pagamenti <> tesoreria_iuv.num_importo
          OR        tesoreria_f2k_iuf.num_ip_bolletta IS NOT NULL
          AND       rendicontazione.num_importo_totale_pagamenti <> tesoreria_f2k_iuf.num_ip_bolletta
          OR        tesoreria_f2k_iuv.num_ip_bolletta IS NOT NULL
          AND       rendicontazione.num_importo_totale_pagamenti <> tesoreria_f2k_iuv.num_ip_bolletta)
OR        classificazione.mygov_classificazione_codice::text = 'TES_NO_IUF_OR_IUV'::text
AND       (
                    tesoreria_iuf.num_importo IS NOT NULL
          OR        tesoreria_iuv.num_importo IS NOT NULL
          OR        tesoreria_f2k_iuf.num_ip_bolletta IS NOT NULL
          AND       tesoreria_f2k_iuf.cod_id_univoco_flusso IS NOT NULL
          AND       tesoreria_f2k_iuf.cod_id_univoco_versamento IS NULL
          OR        tesoreria_f2k_iuv.num_ip_bolletta IS NOT NULL
          AND       tesoreria_f2k_iuv.cod_id_univoco_flusso IS NULL
          AND       tesoreria_f2k_iuv.cod_id_univoco_versamento IS NOT NULL)
AND       rendicontazione.num_importo_totale_pagamenti IS NULL
AND       export.num_e_dati_pag_importo_totale_pagato IS NULL
AND       import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento IS NULL
OR        classificazione.mygov_classificazione_codice::text = 'IUV_NO_RT'::text
AND       rendicontazione.num_importo_totale_pagamenti IS NOT NULL
AND       export.num_e_dati_pag_importo_totale_pagato IS NULL
OR        classificazione.mygov_classificazione_codice::text = 'IUD_NO_RT'::text
AND       import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento IS NOT NULL
AND       (
                    export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NULL
          OR        export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL
          AND       import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento <> export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato)
OR        classificazione.mygov_classificazione_codice::text = 'RT_NO_IUD'::text
AND       export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL
AND       import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento IS NULL
OR        classificazione.mygov_classificazione_codice::text = 'TES_NO_MATCH'::text
AND       import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento IS NULL
AND       export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NULL
AND       rendicontazione.num_importo_totale_pagamenti IS NULL
AND       tesoreria_iuf.num_importo IS NULL
AND       tesoreria_iuv.num_importo IS NULL
AND       tesoreria_f2k_iuf.cod_id_univoco_flusso IS NULL
AND       tesoreria_f2k_iuf.cod_id_univoco_versamento IS NULL
AND       tesoreria_f2k_iuv.cod_id_univoco_flusso IS NULL
AND       tesoreria_f2k_iuv.cod_id_univoco_versamento IS NULL
AND       tesoreria_f2k.cod_id_univoco_flusso IS NULL
AND       tesoreria_f2k.cod_id_univoco_versamento IS NULL
OR        classificazione.mygov_classificazione_codice::text = 'RT_TES'::text
AND       export.num_e_dati_pag_importo_totale_pagato IS NOT NULL
AND       rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NULL
AND       rendicontazione.num_importo_totale_pagamenti IS NULL
AND       (
                    tesoreria_iuv.num_importo IS NOT NULL
          AND       export.num_e_dati_pag_importo_totale_pagato = tesoreria_iuv.num_importo
          OR        tesoreria_f2k_iuv.num_ip_bolletta IS NOT NULL
          AND       export.num_e_dati_pag_importo_totale_pagato = tesoreria_f2k_iuv.num_ip_bolletta);

CREATE TABLE mygov_import_export_rendicontazione_tesoreria_completa
(
  mygov_ente_id bigint,
  codice_iuv character varying(35),
  identificativo_univoco_riscossione character varying(35),
  identificativo_flusso_rendicontazione character varying(35),
  mygov_ente_id_i bigint,
  mygov_manage_flusso_id_i bigint,
  cod_iud_i character varying(35),
  cod_rp_silinviarp_id_univoco_versamento_i character varying(35),
  cod_rp_sogg_pag_id_univ_pag_tipo_id_univoco_i character(1),
  cod_rp_sogg_pag_id_univ_pag_codice_id_univoco_i character varying(35),
  de_rp_sogg_pag_anagrafica_pagatore_i character varying(70),
  de_rp_sogg_pag_indirizzo_pagatore_i character varying(70),
  de_rp_sogg_pag_civico_pagatore_i character varying(16),
  cod_rp_sogg_pag_cap_pagatore_i character varying(16),
  de_rp_sogg_pag_localita_pagatore_i character varying(35),
  de_rp_sogg_pag_provincia_pagatore_i character varying(2),
  cod_rp_sogg_pag_nazione_pagatore_i character varying(2),
  de_rp_sogg_pag_email_pagatore_i character varying(256),
  dt_rp_dati_vers_data_esecuzione_pagamento_i date,
  cod_rp_dati_vers_tipo_versamento_i character varying(32),
  num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento_i numeric(12,2),
  num_rp_dati_vers_dati_sing_vers_commissione_carico_pa_i numeric(12,2),
  de_rp_dati_vers_dati_sing_vers_causale_versamento_i character varying(140),
  de_rp_dati_vers_dati_sing_vers_dati_specifici_riscossione_i character varying(140),
  cod_tipo_dovuto_i character varying(64),
  bilancio_i character varying(4096),
  dt_acquisizione_i date,
  mygov_ente_id_e bigint,
  mygov_manage_flusso_id_e bigint,
  de_nome_flusso_e character varying(50),
  num_riga_flusso_e integer,
  cod_iud_e character varying(35),
  cod_rp_silinviarp_id_univoco_versamento_e character varying(35),
  de_e_versione_oggetto_e character varying(16),
  cod_e_dom_id_dominio_e character varying(35),
  cod_e_dom_id_stazione_richiedente_e character varying(35),
  cod_e_id_messaggio_ricevuta_e character varying(35),
  dt_e_data_ora_messaggio_ricevuta_e timestamp without time zone,
  cod_e_riferimento_messaggio_richiesta_e character varying(35),
  dt_e_riferimento_data_richiesta_e date,
  cod_e_istit_att_id_univ_att_tipo_id_univoco_e character(1),
  cod_e_istit_att_id_univ_att_codice_id_univoco_e character varying(35),
  de_e_istit_att_denominazione_attestante_e character varying(70),
  cod_e_istit_att_codice_unit_oper_attestante_e character varying(35),
  de_e_istit_att_denom_unit_oper_attestante_e character varying(70),
  de_e_istit_att_indirizzo_attestante_e character varying(70),
  de_e_istit_att_civico_attestante_e character varying(16),
  cod_e_istit_att_cap_attestante_e character varying(16),
  de_e_istit_att_localita_attestante_e character varying(35),
  de_e_istit_att_provincia_attestante_e character varying(35),
  cod_e_istit_att_nazione_attestante_e character varying(2),
  cod_e_ente_benef_id_univ_benef_tipo_id_univoco_e character(1),
  cod_e_ente_benef_id_univ_benef_codice_id_univoco_e character varying(35),
  de_e_ente_benef_denominazione_beneficiario_e character varying(70),
  cod_e_ente_benef_codice_unit_oper_beneficiario_e character varying(35),
  de_e_ente_benef_denom_unit_oper_beneficiario_e character varying(70),
  de_e_ente_benef_indirizzo_beneficiario_e character varying(70),
  de_e_ente_benef_civico_beneficiario_e character varying(16),
  cod_e_ente_benef_cap_beneficiario_e character varying(16),
  de_e_ente_benef_localita_beneficiario_e character varying(35),
  de_e_ente_benef_provincia_beneficiario_e character varying(35),
  cod_e_ente_benef_nazione_beneficiario_e character varying(2),
  cod_e_sogg_vers_id_univ_vers_tipo_id_univoco_e character(1),
  cod_e_sogg_vers_id_univ_vers_codice_id_univoco_e character varying(35),
  cod_e_sogg_vers_anagrafica_versante_e character varying(70),
  de_e_sogg_vers_indirizzo_versante_e character varying(70),
  de_e_sogg_vers_civico_versante_e character varying(16),
  cod_e_sogg_vers_cap_versante_e character varying(16),
  de_e_sogg_vers_localita_versante_e character varying(35),
  de_e_sogg_vers_provincia_versante_e character varying(35),
  cod_e_sogg_vers_nazione_versante_e character varying(2),
  de_e_sogg_vers_email_versante_e character varying(256),
  cod_e_sogg_pag_id_univ_pag_tipo_id_univoco_e character(1),
  cod_e_sogg_pag_id_univ_pag_codice_id_univoco_e character varying(35),
  cod_e_sogg_pag_anagrafica_pagatore_e character varying(70),
  de_e_sogg_pag_indirizzo_pagatore_e character varying(70),
  de_e_sogg_pag_civico_pagatore_e character varying(16),
  cod_e_sogg_pag_cap_pagatore_e character varying(16),
  de_e_sogg_pag_localita_pagatore_e character varying(35),
  de_e_sogg_pag_provincia_pagatore_e character varying(35),
  cod_e_sogg_pag_nazione_pagatore_e character varying(2),
  de_e_sogg_pag_email_pagatore_e character varying(256),
  cod_e_dati_pag_codice_esito_pagamento_e character(1),
  num_e_dati_pag_importo_totale_pagato_e numeric(12,2),
  cod_e_dati_pag_id_univoco_versamento_e character varying(35),
  cod_e_dati_pag_codice_contesto_pagamento_e character varying(35),
  num_e_dati_pag_dati_sing_pag_singolo_importo_pagato_e numeric(12,2),
  de_e_dati_pag_dati_sing_pag_esito_singolo_pagamento_e character varying(35),
  dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e date,
  cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss_e character varying(35),
  de_e_dati_pag_dati_sing_pag_causale_versamento_e character varying(1024),
  de_e_dati_pag_dati_sing_pag_dati_specifici_riscossione_e character varying(140),
  cod_tipo_dovuto_e character varying(64),
  dt_acquisizione_e date,
  indice_dati_singolo_pagamento_e integer,
  bilancio_e CHARACTER VARYING(4096),
  mygov_ente_id_r bigint,
  mygov_manage_flusso_id_r bigint,
  versione_oggetto_r character varying(16),
  cod_identificativo_flusso_r character varying(35),
  dt_data_ora_flusso_r timestamp without time zone,
  cod_identificativo_univoco_regolamento_r character varying(35),
  dt_data_regolamento_r date,
  cod_ist_mitt_id_univ_mitt_tipo_identificativo_univoco_r character(1),
  cod_ist_mitt_id_univ_mitt_codice_identificativo_univoco_r character varying(35),
  de_ist_mitt_denominazione_mittente_r character varying(70),
  cod_ist_ricev_id_univ_ricev_tipo_identificativo_univoco_r character(1),
  cod_ist_ricev_id_univ_ricev_codice_identificativo_univoco_r character varying(35),
  de_ist_ricev_denominazione_ricevente_r character varying(70),
  num_numero_totale_pagamenti_r numeric(15,0),
  num_importo_totale_pagamenti_r numeric(18,2),
  cod_dati_sing_pagam_identificativo_univoco_versamento_r character varying(35),
  cod_dati_sing_pagam_identificativo_univoco_riscossione_r character varying(35),
  num_dati_sing_pagam_singolo_importo_pagato_r numeric(12,2),
  cod_dati_sing_pagam_codice_esito_singolo_pagamento_r character varying(1),
  dt_dati_sing_pagam_data_esito_singolo_pagamento_r date,
  dt_acquisizione_r date,
  indice_dati_singolo_pagamento_r integer,
  mygov_ente_id_t bigint,
  mygov_manage_flusso_id_t bigint,
  cod_abi_t character(5),
  cod_cab_t character(5),
  cod_conto_t character(12),
  cod_divisa_t character varying(10),
  dt_data_contabile_t date,
  dt_data_valuta_t date,
  num_importo_t numeric(12,2),
  cod_segno_t character(1),
  de_causale_t text,
  cod_numero_assegno_t text,
  cod_riferimento_banca_t text,
  cod_riferimento_cliente_t text,
  dt_data_ordine_t date,
  de_descrizione_ordinante_t text,
  cod_bi2_t text,
  cod_be1_t text,
  cod_ib1_t text,
  cod_ib2_t text,
  cod_ib4_t text,
  cod_tid_t text,
  cod_dte_t text,
  cod_dtn_t text,
  cod_eri_t text,
  cod_im2_t text,
  cod_ma2_t text,
  cod_ri3_t text,
  cod_or1_t text,
  cod_sc2_t text,
  cod_tr1_t text,
  cod_sec_t text,
  cod_ior_t text,
  cod_id_univoco_flusso_t character varying(35),
  cod_id_univoco_versamento_t character varying(35),
  dt_acquisizione_t date,
  de_anno_bolletta_t character varying(4),
  cod_bolletta_t character varying(7),
  cod_id_dominio_t character varying(7),
  dt_ricezione_t timestamp without time zone,
  de_anno_documento_t character varying(4),
  cod_documento_t character varying(7),
  de_anno_provvisorio_t character varying(4),
  cod_provvisorio_t character varying(6),
  classificazione_completezza character varying(20),
  dt_data_ultimo_aggiornamento date
);

CREATE INDEX idx_mygovimpexprendtescompl_data_aggiornamento
  ON mygov_import_export_rendicontazione_tesoreria_completa
  USING btree
  (mygov_ente_id, classificazione_completezza COLLATE pg_catalog."default", dt_data_ultimo_aggiornamento);

CREATE INDEX idx_mygovimpexprendtescompl_tipo_dovuto
  ON mygov_import_export_rendicontazione_tesoreria_completa
  USING btree
  (mygov_ente_id, classificazione_completezza COLLATE pg_catalog."default", cod_tipo_dovuto_i COLLATE pg_catalog."default", cod_tipo_dovuto_e COLLATE pg_catalog."default");


CREATE OR REPLACE VIEW v_mygov_import_export_rendicontazione_tesoreria AS 
 SELECT ente.cod_ipa_ente AS codice_ipa_ente,
    import_export_rendicontazione_tesoreria.dt_rp_dati_vers_data_esecuzione_pagamento_i AS dt_data_esecuzione_pagamento,
    COALESCE(to_char(import_export_rendicontazione_tesoreria.dt_rp_dati_vers_data_esecuzione_pagamento_i::timestamp with time zone, 'dd-MM-yyyy'::text), 'n/a'::text)::character varying(10) AS de_data_esecuzione_pagamento,
    COALESCE(import_export_rendicontazione_tesoreria.num_rp_dati_vers_dati_sing_vers_commissione_carico_pa_i::character varying(15), ''::character varying)::character varying(15) AS singolo_importo_commissione_carico_pa,
    COALESCE(import_export_rendicontazione_tesoreria.bilancio_i, ''::character varying)::character varying(4096) AS bilancio,
    COALESCE(import_export_rendicontazione_tesoreria.de_nome_flusso_e, 'n/a'::character varying)::character varying(50) AS nome_flusso_import_ente,
    COALESCE(import_export_rendicontazione_tesoreria.num_riga_flusso_e::character varying(12), 'n/a'::character varying)::character varying(12) AS riga_flusso_import_ente,
    COALESCE(import_export_rendicontazione_tesoreria.cod_iud_i, import_export_rendicontazione_tesoreria.cod_iud_e, 'n/a'::character varying)::character varying(35) AS codice_iud,
    COALESCE(import_export_rendicontazione_tesoreria.cod_rp_silinviarp_id_univoco_versamento_i, import_export_rendicontazione_tesoreria.cod_rp_silinviarp_id_univoco_versamento_e, import_export_rendicontazione_tesoreria.cod_dati_sing_pagam_identificativo_univoco_versamento_r, import_export_rendicontazione_tesoreria.cod_id_univoco_versamento_t, 'n/a'::character varying)::character varying(35) AS codice_iuv,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_versione_oggetto_e, ''::character varying)::character varying(16) AS versione_oggetto,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_dom_id_dominio_e, ''::character varying)::character varying(35) AS identificativo_dominio,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_dom_id_stazione_richiedente_e, ''::character varying)::character varying(35) AS identificativo_stazione_richiedente,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_id_messaggio_ricevuta_e, ''::character varying)::character varying(35) AS identificativo_messaggio_ricevuta,
    COALESCE(to_char(import_export_rendicontazione_tesoreria.dt_e_data_ora_messaggio_ricevuta_e, 'dd-MM-yyyy HH:mm:ss'::text), ''::text)::character varying(19) AS data_ora_messaggio_ricevuta,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_riferimento_messaggio_richiesta_e, ''::character varying)::character varying(35) AS riferimento_messaggio_richiesta,
    COALESCE(to_char(import_export_rendicontazione_tesoreria.dt_e_riferimento_data_richiesta_e::timestamp with time zone, 'dd-MM-yyyy'::text), ''::text)::character varying(10) AS riferimento_data_richiesta,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_istit_att_id_univ_att_tipo_id_univoco_e, ''::bpchar)::character varying(1) AS tipo_identificativo_univoco_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_istit_att_id_univ_att_codice_id_univoco_e, ''::character varying)::character varying(35) AS codice_identificativo_univoco_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_denominazione_attestante_e, ''::character varying)::character varying(70) AS denominazione_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_istit_att_codice_unit_oper_attestante_e, ''::character varying)::character varying(35) AS codice_unit_oper_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_denom_unit_oper_attestante_e, ''::character varying)::character varying(70) AS denom_unit_oper_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_indirizzo_attestante_e, ''::character varying)::character varying(70) AS indirizzo_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_civico_attestante_e, ''::character varying)::character varying(16) AS civico_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_istit_att_cap_attestante_e, ''::character varying)::character varying(16) AS cap_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_localita_attestante_e, ''::character varying)::character varying(35) AS localita_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_provincia_attestante_e, ''::character varying)::character varying(35) AS provincia_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_istit_att_nazione_attestante_e, ''::character varying)::character varying(2) AS nazione_attestante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_ente_benef_id_univ_benef_tipo_id_univoco_e, ''::bpchar)::character varying(1) AS tipo_identificativo_univoco_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_ente_benef_id_univ_benef_codice_id_univoco_e, ''::character varying)::character varying(35) AS codice_identificativo_univoco_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_denominazione_beneficiario_e, ''::character varying)::character varying(70) AS denominazione_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_ente_benef_codice_unit_oper_beneficiario_e, ''::character varying)::character varying(35) AS codice_unit_oper_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_denom_unit_oper_beneficiario_e, ''::character varying)::character varying(70) AS denom_unit_oper_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_indirizzo_beneficiario_e, ''::character varying)::character varying(70) AS indirizzo_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_civico_beneficiario_e, ''::character varying)::character varying(16) AS civico_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_ente_benef_cap_beneficiario_e, ''::character varying)::character varying(16) AS cap_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_localita_beneficiario_e, ''::character varying)::character varying(35) AS localita_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_provincia_beneficiario_e, ''::character varying)::character varying(35) AS provincia_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_ente_benef_nazione_beneficiario_e, ''::character varying)::character varying(2) AS nazione_beneficiario,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_sogg_vers_id_univ_vers_tipo_id_univoco_e, ''::bpchar)::character varying(1) AS tipo_identificativo_univoco_versante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_sogg_vers_id_univ_vers_codice_id_univoco_e, ''::character varying)::character varying(35) AS codice_identificativo_univoco_versante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_sogg_vers_anagrafica_versante_e, ''::character varying)::character varying(70) AS anagrafica_versante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_sogg_vers_indirizzo_versante_e, ''::character varying)::character varying(70) AS indirizzo_versante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_sogg_vers_civico_versante_e, ''::character varying)::character varying(16) AS civico_versante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_sogg_vers_cap_versante_e, ''::character varying)::character varying(16) AS cap_versante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_sogg_vers_localita_versante_e, ''::character varying)::character varying(35) AS localita_versante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_sogg_vers_provincia_versante_e, ''::character varying)::character varying(35) AS provincia_versante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_sogg_vers_nazione_versante_e, ''::character varying)::character varying(2) AS nazione_versante,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_sogg_vers_email_versante_e, ''::character varying)::character varying(256) AS email_versante,
    COALESCE(import_export_rendicontazione_tesoreria.cod_rp_sogg_pag_id_univ_pag_tipo_id_univoco_i, import_export_rendicontazione_tesoreria.cod_e_sogg_pag_id_univ_pag_tipo_id_univoco_e, ''::bpchar)::character varying(1) AS tipo_identificativo_univoco_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.cod_rp_sogg_pag_id_univ_pag_codice_id_univoco_i, import_export_rendicontazione_tesoreria.cod_e_sogg_pag_id_univ_pag_codice_id_univoco_e, ''::character varying)::character varying(35) AS codice_identificativo_univoco_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_anagrafica_pagatore_i, import_export_rendicontazione_tesoreria.cod_e_sogg_pag_anagrafica_pagatore_e, ''::character varying)::character varying(70) AS anagrafica_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_indirizzo_pagatore_i, import_export_rendicontazione_tesoreria.de_e_sogg_pag_indirizzo_pagatore_e, ''::character varying)::character varying(70) AS indirizzo_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_civico_pagatore_i, import_export_rendicontazione_tesoreria.de_e_sogg_pag_civico_pagatore_e, ''::character varying)::character varying(16) AS civico_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.cod_rp_sogg_pag_cap_pagatore_i, import_export_rendicontazione_tesoreria.cod_e_sogg_pag_cap_pagatore_e, ''::character varying)::character varying(16) AS cap_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_localita_pagatore_i, import_export_rendicontazione_tesoreria.de_e_sogg_pag_localita_pagatore_e, ''::character varying)::character varying(35) AS localita_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_provincia_pagatore_i, import_export_rendicontazione_tesoreria.de_e_sogg_pag_provincia_pagatore_e, ''::character varying)::character varying(35) AS provincia_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.cod_rp_sogg_pag_nazione_pagatore_i, import_export_rendicontazione_tesoreria.cod_e_sogg_pag_nazione_pagatore_e, ''::character varying)::character varying(2) AS nazione_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_email_pagatore_i, import_export_rendicontazione_tesoreria.de_e_sogg_pag_email_pagatore_e, ''::character varying)::character varying(256) AS email_pagatore,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_dati_pag_codice_esito_pagamento_e, ''::bpchar)::character varying(1) AS codice_esito_pagamento,
    COALESCE(import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e::character varying(15), ''::character varying)::character varying(15) AS importo_totale_pagato,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_dati_pag_id_univoco_versamento_e, ''::character varying)::character varying(35) AS identificativo_univoco_versamento,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_dati_pag_codice_contesto_pagamento_e, ''::character varying)::character varying(35) AS codice_contesto_pagamento,
    COALESCE(import_export_rendicontazione_tesoreria.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento_i::character varying(15), import_export_rendicontazione_tesoreria.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato_e::character varying(15), import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r::character varying(15), ''::character varying)::character varying(15) AS singolo_importo_pagato,
    COALESCE(import_export_rendicontazione_tesoreria.de_e_dati_pag_dati_sing_pag_esito_singolo_pagamento_e, import_export_rendicontazione_tesoreria.cod_dati_sing_pagam_codice_esito_singolo_pagamento_r, ''::character varying)::character varying(35) AS esito_singolo_pagamento,
    COALESCE(import_export_rendicontazione_tesoreria.dt_dati_sing_pagam_data_esito_singolo_pagamento_r, import_export_rendicontazione_tesoreria.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e) AS dt_data_esito_singolo_pagamento,
    COALESCE(to_char(COALESCE(import_export_rendicontazione_tesoreria.dt_dati_sing_pagam_data_esito_singolo_pagamento_r, import_export_rendicontazione_tesoreria.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e)::timestamp with time zone, 'dd-MM-yyyy'::text), 'n/a'::text)::character varying(10) AS de_data_esito_singolo_pagamento,
    COALESCE(import_export_rendicontazione_tesoreria.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss_e, import_export_rendicontazione_tesoreria.cod_dati_sing_pagam_identificativo_univoco_riscossione_r, 'n/a'::character varying)::character varying(35) AS identificativo_univoco_riscossione,
    COALESCE(import_export_rendicontazione_tesoreria.de_rp_dati_vers_dati_sing_vers_causale_versamento_i, import_export_rendicontazione_tesoreria.de_e_dati_pag_dati_sing_pag_causale_versamento_e, ''::character varying)::character varying(1024) AS causale_versamento,
    COALESCE(import_export_rendicontazione_tesoreria.de_rp_dati_vers_dati_sing_vers_dati_specifici_riscossione_i, import_export_rendicontazione_tesoreria.de_e_dati_pag_dati_sing_pag_dati_specifici_riscossione_e, ''::character varying)::character varying(140) AS dati_specifici_riscossione,
    COALESCE(import_export_rendicontazione_tesoreria.cod_tipo_dovuto_i, import_export_rendicontazione_tesoreria.cod_tipo_dovuto_e, ''::character varying)::character varying(64) AS tipo_dovuto,
    COALESCE(import_export_rendicontazione_tesoreria.cod_identificativo_flusso_r, import_export_rendicontazione_tesoreria.cod_id_univoco_flusso_t, 'n/a'::character varying)::character varying(35) AS identificativo_flusso_rendicontazione,
    COALESCE(to_char(import_export_rendicontazione_tesoreria.dt_data_ora_flusso_r, 'dd-MM-yyyy HH:mm:ss'::text), 'n/a'::text)::character varying(19) AS data_ora_flusso_rendicontazione,
    COALESCE(import_export_rendicontazione_tesoreria.cod_identificativo_univoco_regolamento_r, 'n/a'::character varying)::character varying(35) AS identificativo_univoco_regolamento,
    COALESCE(import_export_rendicontazione_tesoreria.dt_data_regolamento_r, import_export_rendicontazione_tesoreria.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e + ente.num_giorni_pagamento_presunti) AS dt_data_regolamento,
    COALESCE(to_char(COALESCE(import_export_rendicontazione_tesoreria.dt_data_regolamento_r, import_export_rendicontazione_tesoreria.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e + ente.num_giorni_pagamento_presunti)::timestamp with time zone, 'dd-MM-yyyy'::text)::character varying(10), 'n/a'::character varying) AS de_data_regolamento,
    COALESCE(import_export_rendicontazione_tesoreria.num_numero_totale_pagamenti_r::character varying(15), 'n/a'::character varying)::character varying(15) AS numero_totale_pagamenti,
    COALESCE(import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r::character varying(15), 'n/a'::character varying)::character varying(21) AS importo_totale_pagamenti,
    to_char(GREATEST(import_export_rendicontazione_tesoreria.dt_acquisizione_r, import_export_rendicontazione_tesoreria.dt_acquisizione_e, import_export_rendicontazione_tesoreria.dt_acquisizione_t)::timestamp with time zone, 'dd-MM-yyyy'::text)::character varying(10) AS data_acquisizione,
    COALESCE(import_export_rendicontazione_tesoreria.cod_conto_t, ''::character varying::bpchar)::character varying(12) AS cod_conto,
    import_export_rendicontazione_tesoreria.dt_data_contabile_t AS dt_data_contabile,
    COALESCE(to_char(import_export_rendicontazione_tesoreria.dt_data_contabile_t::timestamp with time zone, 'dd-MM-yyyy'::text), ''::character varying::text)::character varying(10) AS de_data_contabile,
    import_export_rendicontazione_tesoreria.dt_data_valuta_t AS dt_data_valuta,
    COALESCE(to_char(import_export_rendicontazione_tesoreria.dt_data_valuta_t::timestamp with time zone, 'dd-MM-yyyy'::text), ''::character varying::text)::character varying(10) AS de_data_valuta,
    import_export_rendicontazione_tesoreria.num_importo_t AS num_importo,
    COALESCE(import_export_rendicontazione_tesoreria.num_importo_t::character(15), 'n/a'::character varying::bpchar)::character varying(15) AS de_importo,
    COALESCE(import_export_rendicontazione_tesoreria.cod_or1_t, ''::text) AS cod_or1,
    COALESCE(import_export_rendicontazione_tesoreria.de_anno_bolletta_t, 'n/a'::text::character varying) AS de_anno_bolletta,
    COALESCE(import_export_rendicontazione_tesoreria.cod_bolletta_t, 'n/a'::text::character varying) AS cod_bolletta,
    COALESCE(import_export_rendicontazione_tesoreria.cod_id_dominio_t, 'n/a'::text::character varying) AS cod_id_dominio,
    import_export_rendicontazione_tesoreria.dt_ricezione_t AS dt_ricezione,
    COALESCE(to_char(import_export_rendicontazione_tesoreria.dt_ricezione_t::timestamp with time zone, 'dd-MM-yyyy'::text), 'n/a'::character varying::text)::character varying(10) AS de_data_ricezione,
    COALESCE(import_export_rendicontazione_tesoreria.de_anno_documento_t, 'n/a'::text::character varying) AS de_anno_documento,
    COALESCE(import_export_rendicontazione_tesoreria.cod_documento_t, 'n/a'::text::character varying) AS cod_documento,
    COALESCE(import_export_rendicontazione_tesoreria.de_anno_provvisorio_t, 'n/a'::text::character varying) AS de_anno_provvisorio,
    COALESCE(import_export_rendicontazione_tesoreria.cod_provvisorio_t, 'n/a'::text::character varying) AS cod_provvisorio,
    COALESCE(import_export_rendicontazione_tesoreria.de_causale_t, 'n/a'::text::character varying) AS de_causale_t,
        CASE
            WHEN import_export_rendicontazione_tesoreria.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento_i = import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e OR (sum(import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r) OVER (PARTITION BY import_export_rendicontazione_tesoreria.cod_identificativo_flusso_r) - import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r) = 0::numeric AND import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e = import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r AND import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r = import_export_rendicontazione_tesoreria.num_importo_t THEN 'OK'::character varying(3)
            WHEN import_export_rendicontazione_tesoreria.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento_i <> import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e OR (sum(import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r) OVER (PARTITION BY import_export_rendicontazione_tesoreria.cod_identificativo_flusso_r) - import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r) <> 0::numeric OR import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e <> import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r OR import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r <> import_export_rendicontazione_tesoreria.num_importo_t THEN 'KO'::character varying(3)
            ELSE 'n/a'::character varying(3)
        END AS verifica_totale,
    COALESCE(import_export_rendicontazione_tesoreria.classificazione_completezza, 'OTHERS'::character varying)::character varying(20) AS classificazione_completezza,
    import_export_rendicontazione_tesoreria.dt_data_ultimo_aggiornamento,
    to_char(import_export_rendicontazione_tesoreria.dt_data_ultimo_aggiornamento::timestamp with time zone, 'dd-MM-yyyy'::text)::character varying(10) AS de_data_ultimo_aggiornamento,
    COALESCE(import_export_rendicontazione_tesoreria.indice_dati_singolo_pagamento_e, import_export_rendicontazione_tesoreria.indice_dati_singolo_pagamento_r) AS indice_dati_singolo_pagamento,
    COALESCE(import_export_rendicontazione_tesoreria.cod_identificativo_flusso_r, import_export_rendicontazione_tesoreria.cod_id_univoco_flusso_t) AS cod_iuf_key,
    COALESCE(import_export_rendicontazione_tesoreria.cod_iud_i, import_export_rendicontazione_tesoreria.cod_iud_e) AS cod_iud_key,
    COALESCE(import_export_rendicontazione_tesoreria.cod_rp_silinviarp_id_univoco_versamento_i, import_export_rendicontazione_tesoreria.cod_rp_silinviarp_id_univoco_versamento_e, import_export_rendicontazione_tesoreria.cod_dati_sing_pagam_identificativo_univoco_versamento_r, import_export_rendicontazione_tesoreria.cod_id_univoco_versamento_t) AS cod_iuv_key,
    COALESCE(import_export_rendicontazione_tesoreria.bilancio_e, 'n/a'::text::character varying) AS bilancio_e
   FROM mygov_import_export_rendicontazione_tesoreria_completa import_export_rendicontazione_tesoreria,
    mygov_ente ente
  WHERE COALESCE(import_export_rendicontazione_tesoreria.mygov_ente_id_i, import_export_rendicontazione_tesoreria.mygov_ente_id_e, import_export_rendicontazione_tesoreria.mygov_ente_id_r, import_export_rendicontazione_tesoreria.mygov_ente_id_t) = ente.mygov_ente_id;

CREATE TABLE mygov_import_export_rendicontazione_tesoreria
(
  codice_ipa_ente character varying(80),
  dt_data_esecuzione_pagamento date,
  de_data_esecuzione_pagamento character varying(10),
  singolo_importo_commissione_carico_pa character varying(15),
  bilancio character varying(4096),
  nome_flusso_import_ente character varying(50),
  riga_flusso_import_ente character varying(12),
  codice_iud character varying(35),
  codice_iuv character varying(35),
  versione_oggetto character varying(16),
  identificativo_dominio character varying(35),
  identificativo_stazione_richiedente character varying(35),
  identificativo_messaggio_ricevuta character varying(35),
  data_ora_messaggio_ricevuta character varying(19),
  riferimento_messaggio_richiesta character varying(35),
  riferimento_data_richiesta character varying(10),
  tipo_identificativo_univoco_attestante character varying(1),
  codice_identificativo_univoco_attestante character varying(35),
  denominazione_attestante character varying(70),
  codice_unit_oper_attestante character varying(35),
  denom_unit_oper_attestante character varying(70),
  indirizzo_attestante character varying(70),
  civico_attestante character varying(16),
  cap_attestante character varying(16),
  localita_attestante character varying(35),
  provincia_attestante character varying(35),
  nazione_attestante character varying(2),
  tipo_identificativo_univoco_beneficiario character varying(1),
  codice_identificativo_univoco_beneficiario character varying(35),
  denominazione_beneficiario character varying(70),
  codice_unit_oper_beneficiario character varying(35),
  denom_unit_oper_beneficiario character varying(70),
  indirizzo_beneficiario character varying(70),
  civico_beneficiario character varying(16),
  cap_beneficiario character varying(16),
  localita_beneficiario character varying(35),
  provincia_beneficiario character varying(35),
  nazione_beneficiario character varying(2),
  tipo_identificativo_univoco_versante character varying(1),
  codice_identificativo_univoco_versante character varying(35),
  anagrafica_versante character varying(70),
  indirizzo_versante character varying(70),
  civico_versante character varying(16),
  cap_versante character varying(16),
  localita_versante character varying(35),
  provincia_versante character varying(35),
  nazione_versante character varying(2),
  email_versante character varying(256),
  tipo_identificativo_univoco_pagatore character varying(1),
  codice_identificativo_univoco_pagatore character varying(35),
  anagrafica_pagatore character varying(70),
  indirizzo_pagatore character varying(70),
  civico_pagatore character varying(16),
  cap_pagatore character varying(16),
  localita_pagatore character varying(35),
  provincia_pagatore character varying(35),
  nazione_pagatore character varying(2),
  email_pagatore character varying(256),
  codice_esito_pagamento character varying(1),
  importo_totale_pagato character varying(15),
  identificativo_univoco_versamento character varying(35),
  codice_contesto_pagamento character varying(35),
  singolo_importo_pagato character varying(15),
  esito_singolo_pagamento character varying(35),
  dt_data_esito_singolo_pagamento date,
  de_data_esito_singolo_pagamento character varying(10),
  identificativo_univoco_riscossione character varying(35),
  causale_versamento character varying(1024),
  dati_specifici_riscossione character varying(140),
  tipo_dovuto character varying(64),
  identificativo_flusso_rendicontazione character varying(35),
  data_ora_flusso_rendicontazione character varying(19),
  identificativo_univoco_regolamento character varying(35),
  dt_data_regolamento date,
  de_data_regolamento character varying,
  numero_totale_pagamenti character varying(15),
  importo_totale_pagamenti character varying(21),
  data_acquisizione character varying(10),
  cod_conto character varying(12),
  dt_data_contabile date,
  de_data_contabile character varying(10),
  dt_data_valuta date,
  de_data_valuta character varying(10),
  num_importo numeric(12,2),
  de_importo character varying(15),
  cod_or1 text,
  de_anno_bolletta character varying(4),
  cod_bolletta character varying(7),
  cod_id_dominio character varying(7),
  dt_ricezione timestamp without time zone,
  de_data_ricezione character varying(10),
  de_anno_documento character varying(4),
  cod_documento character varying(7),
  de_anno_provvisorio character varying(4),
  cod_provvisorio character varying(6),
  de_causale_t text,
  verifica_totale character varying(3),
  classificazione_completezza character varying(20),
  dt_data_ultimo_aggiornamento date,
  de_data_ultimo_aggiornamento character varying(10),
  indice_dati_singolo_pagamento integer,
  cod_iuf_key character varying(35),
  cod_iud_key character varying(35),
  cod_iuv_key character varying(35),
  bilancio_e CHARACTER VARYING(4096)
);


CREATE INDEX idx_mygovimpexprendtes_causale_versamento
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", causale_versamento COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_classificazione_completezza
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", classificazione_completezza COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_codice_iud
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", codice_iud COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_de_importo
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", de_importo COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_dt_data_contabile
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", dt_data_contabile);

CREATE INDEX idx_mygovimpexprendtes_dt_data_esito_singolo_pagamento
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", dt_data_esito_singolo_pagamento);

CREATE INDEX idx_mygovimpexprendtes_dt_data_regolamento
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", dt_data_regolamento);

CREATE INDEX idx_mygovimpexprendtes_dt_data_valuta
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", dt_data_valuta);

CREATE INDEX idx_mygovimpexprendtes_identificativo_flusso_rendicontazione
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", identificativo_flusso_rendicontazione COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_identificativo_univoco_regolamento
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", identificativo_univoco_regolamento COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_identificativo_univoco_riscossione
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", identificativo_univoco_riscossione COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_identificativo_univoco_versamento
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", identificativo_univoco_versamento COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_importo_totale_pagamenti
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", importo_totale_pagamenti COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_tipo_dovuto
  ON mygov_import_export_rendicontazione_tesoreria
  USING btree
  (codice_ipa_ente COLLATE pg_catalog."default", tipo_dovuto COLLATE pg_catalog."default");

CREATE INDEX idx_mygovimpexprendtes_id_key 
	ON mygov_import_export_rendicontazione_tesoreria 
	USING btree 
	(codice_ipa_ente COLLATE pg_catalog."default", classificazione_completezza COLLATE pg_catalog."default", cod_iuv_key COLLATE pg_catalog."default", cod_iuf_key COLLATE pg_catalog."default", cod_iud_key COLLATE pg_catalog."default");


ALTER TABLE mygov_flusso_tesoreria ADD COLUMN flg_regolarizzata BOOLEAN DEFAULT false;
UPDATE mygov_flusso_tesoreria SET flg_regolarizzata = false;
ALTER TABLE mygov_flusso_tesoreria ALTER COLUMN flg_regolarizzata SET NOT NULL;


CREATE TABLE mygov_anagrafica_uff_cap_acc (
	mygov_anagrafica_uff_cap_acc_id BIGINT NOT NULL,
	mygov_ente_id BIGINT NOT NULL,
	cod_tipo_dovuto character varying(64),
	cod_ufficio CHARACTER VARYING(64) NOT NULL,
	de_ufficio CHARACTER VARYING(512) NOT NULL,
	flg_attivo BOOLEAN NOT NULL,
	cod_capitolo CHARACTER VARYING(64) NOT NULL,
	de_capitolo CHARACTER VARYING(512) NOT NULL,
	de_anno_esercizio CHARACTER VARYING(4) NOT NULL,
	cod_accertamento CHARACTER VARYING(64) NOT NULL,
	de_accertamento CHARACTER VARYING(512) NOT NULL,
	dt_creazione TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	dt_ultima_modifica TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT mygov_anagrafica_uff_cap_acc_pkey PRIMARY KEY (mygov_anagrafica_uff_cap_acc_id),
	CONSTRAINT mygov_anagrafica_uff_cap_acc_mygov_ente_fkey FOREIGN KEY(mygov_ente_id)
		REFERENCES mygov_ente (mygov_ente_id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE mygov_anag_uff_cap_acc_mygov_anag_uff_cap_acc_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 35
  CACHE 1;


DROP TABLE mygov_accertamento_dettaglio;
DROP SEQUENCE mygov_accertamento_dettaglio_mygov_accertamento_dett_id_seq;

TRUNCATE TABLE mygov_accertamento;

CREATE TABLE mygov_accertamento_dettaglio
(
	mygov_accertamento_dettaglio_id BIGINT NOT NULL,
	mygov_accertamento_id BIGINT NOT NULL,
	cod_ipa_ente CHARACTER VARYING(35) NOT NULL,
    cod_tipo_dovuto character varying(64) NOT NULL,
	cod_iud CHARACTER VARYING(35) NOT NULL,
	cod_iuv CHARACTER VARYING(35) NOT NULL,
	cod_ufficio CHARACTER VARYING(64) NOT NULL,
	cod_capitolo CHARACTER VARYING(64) NOT NULL,
	cod_accertamento CHARACTER VARYING(64) NOT NULL,
	num_importo NUMERIC(17,2) NOT NULL,
	flg_importo_inserito BOOLEAN NOT NULL DEFAULT TRUE,
	dt_ultima_modifica TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	dt_data_inserimento TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	mygov_utente_id BIGINT NOT NULL,
	CONSTRAINT mygov_accertamento_dettaglio_pkey PRIMARY KEY (mygov_accertamento_dettaglio_id),
	CONSTRAINT mygov_accertamento_dettaglio_mygov_accertamento_fkey FOREIGN KEY (mygov_accertamento_id)
		REFERENCES mygov_accertamento (mygov_accertamento_id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION,
	CONSTRAINT mygov_accertamento_dettaglio_mygov_utente_fkey FOREIGN KEY (mygov_utente_id)
		REFERENCES mygov_utente (mygov_utente_id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE mygov_accertamento_dettaglio_mygov_accertamento_dett_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 35
  CACHE 1;

CREATE TABLE mygov_info_mapping_tesoreria (
	mygov_info_mapping_tesoreria_id BIGINT NOT NULL,
	mygov_manage_flusso_id BIGINT NOT NULL,
	pos_de_anno_bolletta INT,
	pos_cod_bolletta INT,
	pos_dt_contabile INT,
	pos_de_denominazione INT,
	pos_de_causale INT,
	pos_num_importo INT,
	pos_dt_valuta INT,
	dt_creazione TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	dt_ultima_modifica TIMESTAMP WITHOUT TIME ZONE NOT NULL,
	CONSTRAINT mygov_info_mapping_tesoreria_pkey PRIMARY KEY (mygov_info_mapping_tesoreria_id),
	CONSTRAINT mygov_info_mapping_tesoreria_mygov_manage_flusso_fkey FOREIGN KEY (mygov_manage_flusso_id)
		REFERENCES mygov_manage_flusso (mygov_manage_flusso_id) MATCH SIMPLE
		ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE SEQUENCE mygov_info_mapping_tesoreria_mygov_info_mapping_tes_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 35
  CACHE 1;


CREATE OR REPLACE FUNCTION get_tesoreria_subset_function(IN _codice_ipa_ente character varying, IN _dt_data_contabile_da date, IN _dt_data_contabile_a date, IN _dt_data_valuta_da date, IN _dt_data_valuta_a date, IN _dt_data_ultimo_aggiornamento_da date, IN _dt_data_ultimo_aggiornamento_a date, IN _de_causale_t text, IN _importo character varying, IN _conto character varying, IN _codor1 character varying, IN _flagnascosto boolean, IN _classificazione_completezza character varying, IN _cod_iuv character varying, IN _cod_iuf character varying, IN _page integer, IN _size integer)
  RETURNS TABLE(codice_ipa_ente character varying, codice_iuv character varying, identificativo_flusso_rendicontazione character varying, dt_data_esecuzione_pagamento date, de_data_esecuzione_pagamento character varying, singolo_importo_commissione_carico_pa character varying, bilancio character varying, cod_conto character varying, dt_data_contabile date, de_data_contabile character varying, dt_data_valuta date, de_data_valuta character varying, num_importo numeric, de_importo character varying, cod_or1 text, de_anno_bolletta character varying, cod_bolletta character varying, cod_id_dominio character varying, dt_ricezione timestamp without time zone, de_data_ricezione character varying, de_anno_documento character varying, cod_documento character varying, de_anno_provvisorio character varying, cod_provvisorio character varying, de_causale_t text, classificazione_completezza character varying, dt_data_ultimo_aggiornamento date, de_data_ultimo_aggiornamento character varying, cod_iuf_key character varying, cod_iuv_key character varying) AS
$BODY$
   SELECT 
        DISTINCT (tes.codice_ipa_ente),
  tes.codice_iuv,
  tes.identificativo_flusso_rendicontazione,
  tes.dt_data_esecuzione_pagamento,
  tes.de_data_esecuzione_pagamento,
  tes.singolo_importo_commissione_carico_pa,
  tes.bilancio,
  tes.cod_conto,
  tes.dt_data_contabile,
  tes.de_data_contabile,
        tes.dt_data_valuta,
        tes.de_data_valuta,
  tes.num_importo,
  tes.de_importo,
  tes.cod_or1,
  tes.de_anno_bolletta,
  tes.cod_bolletta,
  tes.cod_id_dominio,
  tes.dt_ricezione,
  tes.de_data_ricezione,
  tes.de_anno_documento,
  tes.cod_documento,
  tes.de_anno_provvisorio,
  tes.cod_provvisorio,
  tes.de_causale_t,
  tes.classificazione_completezza,
  tes.dt_data_ultimo_aggiornamento,
  tes.de_data_ultimo_aggiornamento,
  tes.cod_iuf_key,
  tes.cod_iuv_key
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms                              
     ON   ms.cod_ipa_ente = tes.codice_ipa_ente 
    AND (ms.cod_iuf IS NULL 
    AND  tes.cod_iuf_key IS NULL 
    OR   ms.cod_iuf = tes.cod_iuf_key)
    AND (ms.cod_iuv IS NULL 
    AND  tes.cod_iuv_key IS NULL 
    OR   ms.cod_iuv = tes.cod_iuv_key)
     
   WHERE CASE WHEN (_codice_ipa_ente <> '') IS TRUE THEN tes.codice_ipa_ente = _codice_ipa_ente ELSE true END     
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN tes.dt_data_contabile >= _dt_data_contabile_da ELSE true END                       
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN tes.dt_data_contabile <= _dt_data_contabile_a ELSE true END         
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN tes.dt_data_valuta >= _dt_data_valuta_da ELSE true END                      
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN tes.dt_data_valuta <= _dt_data_valuta_a ELSE true END              
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END                       
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END
   AND   CASE WHEN (_de_causale_t <> '') IS TRUE THEN upper(tes.de_causale_t) like '%' || upper(_de_causale_t) || '%' ELSE true END
   AND   CASE WHEN (_importo <> '') IS TRUE THEN tes.de_importo = _importo ELSE true END                   
   AND   CASE WHEN (_conto <> '') IS TRUE THEN tes.cod_conto = _conto ELSE true END                 
   AND   CASE WHEN (_codOr1 <> '') IS TRUE THEN upper(tes.cod_or1) like '%' || upper(_codOr1) || '%' ELSE true END                                                           
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END                    
   AND   CASE WHEN (_classificazione_completezza <> '') IS TRUE THEN tes.classificazione_completezza = _classificazione_completezza ELSE true END
   AND   CASE WHEN (_cod_iuv <> '') IS TRUE THEN tes.codice_iuv = _cod_iuv ELSE true END
   AND   CASE WHEN (_cod_iuf <> '') IS TRUE THEN tes.identificativo_flusso_rendicontazione = _cod_iuf ELSE true END
      
   ORDER BY tes.dt_data_valuta, tes.identificativo_flusso_rendicontazione, tes.codice_iuv
   OFFSET CASE WHEN (_page IS NOT NULL) THEN ((_page - 1)*_size) ELSE 0 END 
   LIMIT CASE WHEN (_size IS NOT NULL) THEN _size ELSE 5 END;
$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION get_rendicontazione_subset_function(IN _codice_ipa_ente character varying, IN _identificativo_flusso_rendicontazione character varying, IN _identificativo_univoco_regolamento character varying, IN _dt_data_regolamento_da date, IN _dt_data_regolamento_a date, IN dt_data_ultimo_aggiornamento_da date, IN dt_data_ultimo_aggiornamento_a date, IN _classificazione_completezza character varying, IN _cod_tipo_dovuto character varying, IN _cod_fed_user_id character varying, IN _flagnascosto boolean, IN _page integer, IN _size integer)
  RETURNS TABLE(identificativo_flusso_rendicontazione character varying, codice_ipa_ente character varying, singolo_importo_commissione_carico_pa character varying, bilancio character varying, data_ora_flusso_rendicontazione character varying, identificativo_univoco_regolamento character varying, dt_data_regolamento date, de_data_regolamento character varying, importo_totale_pagamenti character varying, de_anno_bolletta character varying, cod_bolletta character varying, cod_id_dominio character varying, dt_ricezione timestamp without time zone, de_data_ricezione character varying, de_anno_documento character varying, cod_documento character varying, de_anno_provvisorio character varying, cod_provvisorio character varying, classificazione_completezza character varying, dt_data_ultimo_aggiornamento date, de_data_ultimo_aggiornamento character varying, indice_dati_singolo_pagamento integer, cod_iuf_key character varying) AS
$BODY$
   SELECT 
      DISTINCT (tes.identificativo_flusso_rendicontazione),tes.codice_ipa_ente,tes.singolo_importo_commissione_carico_pa,tes.bilancio,tes.data_ora_flusso_rendicontazione,tes.identificativo_univoco_regolamento,tes.dt_data_regolamento,tes.de_data_regolamento,tes.importo_totale_pagamenti,tes.de_anno_bolletta,tes.cod_bolletta,tes.cod_id_dominio,tes.dt_ricezione,tes.de_data_ricezione,tes.de_anno_documento,tes.cod_documento,tes.de_anno_provvisorio,tes.cod_provvisorio,tes.classificazione_completezza, MAX(tes.dt_data_ultimo_aggiornamento)as dt_data_ultimo_aggiornamento, to_char(MAX(tes.dt_data_ultimo_aggiornamento), 'DD-MM-YYYY') as de_data_ultimo_aggiornamento, tes.indice_dati_singolo_pagamento,tes.cod_iuf_key
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT mseg.*, ment.* FROM mygov_segnalazione as mseg INNER JOIN mygov_ente as ment ON mseg.mygov_ente_id = ment.mygov_ente_id WHERE mseg.flg_attivo = true AND mseg.classificazione_completezza = $8) as ms 
     ON ms.cod_ipa_ente = tes.codice_ipa_ente AND (ms.cod_iuf = tes.cod_iuf_key OR (ms.cod_iuf IS NULL and tes.cod_iuf_key IS NULL))
   WHERE  
         CASE WHEN $1 IS NOT NULL AND $1!='' THEN tes.codice_ipa_ente = $1 ELSE true END
     AND CASE WHEN $2 IS NOT NULL AND $2!='' THEN tes.identificativo_flusso_rendicontazione = $2 ELSE true END
     AND (COALESCE($3, '') ='' OR tes.identificativo_univoco_regolamento = $3)
     AND CASE WHEN $4 IS NOT NULL THEN tes.dt_data_regolamento >= $4 ELSE true END
     AND CASE WHEN $5 IS NOT NULL THEN tes.dt_data_regolamento <= $5 ELSE true END
     AND CASE WHEN $6 IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= $6 ELSE true END
     AND CASE WHEN $7 IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento < $7 ELSE true END
     AND CASE WHEN $8 IS NOT NULL AND $8!='' THEN tes.classificazione_completezza = $8 ELSE true END
     AND CASE WHEN $9 IS NOT NULL AND $9!='' AND tes.classificazione_completezza <> 'IUV_NO_RT' THEN tes.tipo_dovuto = $9 
          WHEN ($9 IS NULL OR $9='') AND tes.classificazione_completezza <> 'IUV_NO_RT' THEN 
      tes.tipo_dovuto in (SELECT
           DISTINCT(metd.cod_tipo)
        FROM 
           mygov_operatore_ente_tipo_dovuto as moetd, mygov_ente_tipo_dovuto as metd 
        WHERE
           moetd.mygov_ente_tipo_dovuto_id = metd.mygov_ente_tipo_dovuto_id AND
           moetd.cod_fed_user_id = $10 AND 
           moetd.flg_attivo = true)
    ELSE true      
   END
     AND CASE WHEN $11 IS NOT NULL 
         THEN
       ms.flg_nascosto = $11
         ELSE
             (ms.flg_nascosto is null or ms.flg_nascosto = false)
         END
   GROUP BY tes.identificativo_flusso_rendicontazione,tes.codice_ipa_ente,tes.singolo_importo_commissione_carico_pa,tes.bilancio,tes.data_ora_flusso_rendicontazione,tes.identificativo_univoco_regolamento,tes.dt_data_regolamento,tes.de_data_regolamento,tes.importo_totale_pagamenti,tes.de_anno_bolletta,tes.cod_bolletta,tes.cod_id_dominio,tes.dt_ricezione,tes.de_data_ricezione,tes.de_anno_documento,tes.cod_documento,tes.de_anno_provvisorio,tes.cod_provvisorio,tes.classificazione_completezza, tes.indice_dati_singolo_pagamento,tes.cod_iuf_key
   ORDER BY dt_data_ultimo_aggiornamento DESC
   OFFSET CASE WHEN ($12 IS NOT NULL) THEN (($12 - 1)*$13) ELSE 0 END 
   LIMIT CASE WHEN ($13 IS NOT NULL) THEN $13 ELSE 5 END;
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION get_pagamenti_inseriti_in_accertamento(IN _accertamento_id bigint, IN _ente_id bigint, IN _cod_tipo_dovuto character varying, IN _codice_iud character varying, IN _codice_iuv character varying, IN _codice_identificativo_univoco_pagatore character varying, IN _data_esito_singolo_pagamento_da date, IN _data_esito_singolo_pagamento_a date, IN _data_ultimo_aggiornamento_da date, IN _data_ultimo_aggiornamento_a date, IN _has_pagination boolean, IN _page integer, IN _page_size integer)
  RETURNS TABLE(mygov_accertamento_dettaglio_id_acc bigint, mygov_accertamento_id_acc bigint, cod_ipa_ente_acc character varying, cod_tipo_dovuto_acc character varying, cod_iud_acc character varying, cod_iuv_acc character varying, dt_ultima_modifica_acc timestamp without time zone, dt_data_inserimento_acc timestamp without time zone, cod_tipo_dovuto character varying, de_tipo_dovuto character varying, cod_iud character varying, cod_e_dati_pag_id_univoco_versamento character varying, cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss character varying, de_e_istit_att_denominazione_attestante character varying, cod_e_istit_att_id_univ_att_codice_id_univoco character varying, cod_e_istit_att_id_univ_att_tipo_id_univoco character, cod_e_sogg_vers_anagrafica_versante character varying, cod_e_sogg_vers_id_univ_vers_codice_id_univoco character varying, cod_e_sogg_vers_id_univ_vers_tipo_id_univoco character, cod_e_sogg_pag_anagrafica_pagatore character varying, cod_e_sogg_pag_id_univ_pag_codice_id_univoco character varying, cod_e_sogg_pag_id_univ_pag_tipo_id_univoco character, dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento date, dt_ultima_modifica timestamp without time zone, num_e_dati_pag_dati_sing_pag_singolo_importo_pagato numeric, de_e_dati_pag_dati_sing_pag_causale_versamento character varying) AS
$BODY$
    SELECT
     /* Accertamento dettaglio */
     mygov_accertamento_dettaglio_id_acc, 
     mygov_accertamento_id_acc, cod_ipa_ente_acc, 
     cod_tipo_dovuto_acc, 
     cod_iud_acc, 
     cod_iuv_acc,
     dt_ultima_modifica_acc, 
     dt_data_inserimento_acc,

     /* Flusso export */
     cod_tipo_dovuto, de_tipo_dovuto, cod_iud, cod_e_dati_pag_id_univoco_versamento,           cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss, 
     de_e_istit_att_denominazione_attestante,  cod_e_istit_att_id_univ_att_codice_id_univoco,  cod_e_istit_att_id_univ_att_tipo_id_univoco, 
     cod_e_sogg_vers_anagrafica_versante,      cod_e_sogg_vers_id_univ_vers_codice_id_univoco, cod_e_sogg_vers_id_univ_vers_tipo_id_univoco, 
     cod_e_sogg_pag_anagrafica_pagatore,       cod_e_sogg_pag_id_univ_pag_codice_id_univoco,   cod_e_sogg_pag_id_univ_pag_tipo_id_univoco, 
     dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento, dt_ultima_modifica,             num_e_dati_pag_dati_sing_pag_singolo_importo_pagato, 
     de_e_dati_pag_dati_sing_pag_causale_versamento
    FROM
     (
       SELECT
           DISTINCT ON(a.cod_iuv || '-' || a.cod_iuv) cod_iuv,
           /* Accertamento dettaglio */
           a.mygov_accertamento_dettaglio_id AS mygov_accertamento_dettaglio_id_acc, a.mygov_accertamento_id AS mygov_accertamento_id_acc, 
           a.cod_ipa_ente AS cod_ipa_ente_acc, a.cod_tipo_dovuto AS cod_tipo_dovuto_acc, a.cod_iud AS cod_iud_acc, a.cod_iuv AS cod_iuv_acc,
           a.dt_ultima_modifica AS dt_ultima_modifica_acc, a.dt_data_inserimento AS dt_data_inserimento_acc,

           /* Flusso export */
           p.cod_tipo_dovuto, td.de_tipo as de_tipo_dovuto, p.cod_iud, p.cod_e_dati_pag_id_univoco_versamento, p.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss, 
           p.de_e_istit_att_denominazione_attestante, p.cod_e_istit_att_id_univ_att_codice_id_univoco, p.cod_e_istit_att_id_univ_att_tipo_id_univoco, 
           p.cod_e_sogg_vers_anagrafica_versante, p.cod_e_sogg_vers_id_univ_vers_codice_id_univoco, p.cod_e_sogg_vers_id_univ_vers_tipo_id_univoco, 
           p.cod_e_sogg_pag_anagrafica_pagatore, p.cod_e_sogg_pag_id_univ_pag_codice_id_univoco, p.cod_e_sogg_pag_id_univ_pag_tipo_id_univoco, 
           p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento, p.dt_ultima_modifica, p.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato, 
           p.de_e_dati_pag_dati_sing_pag_causale_versamento  
       FROM 
          mygov_flusso_export AS p 
          INNER JOIN mygov_accertamento_dettaglio AS a ON p.cod_iud = a.cod_iud AND p.cod_e_dati_pag_id_univoco_versamento = a.cod_iuv
          INNER JOIN mygov_ente_tipo_dovuto AS td ON p.cod_tipo_dovuto = td.cod_tipo AND td.mygov_ente_id = p.mygov_ente_id
       WHERE
           /* Condizioni obbligatorie */
           a.mygov_accertamento_id = $1 AND p.mygov_ente_id = $2 AND p.cod_tipo_dovuto = $3 AND p.bilancio IS NULL AND
              
           /* IUD */
           CASE WHEN ($4 IS NOT NULL) THEN p.cod_iud = $4 ELSE true END AND
           /* IUV */
           CASE WHEN ($5 IS NOT NULL) THEN p.cod_e_dati_pag_id_univoco_versamento = $5 ELSE true END AND
           /* Identificativo univoco pagatore */
           CASE WHEN ($6 IS NOT NULL) THEN p.cod_e_sogg_pag_id_univ_pag_codice_id_univoco = $6 ELSE true END AND
           /* Data esito pagamento da */
           CASE WHEN ($7 IS NOT NULL) THEN p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento >= $7 ELSE true END AND
           /* Data esito pagamento a */
           CASE WHEN ($8 IS NOT NULL) THEN p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento <= $8 ELSE true END AND
           /* Data ultimo aggiornamento da */
           CASE WHEN ($9 IS NOT NULL) THEN p.dt_ultima_modifica >= $9 ELSE true END AND
           /* Data ultimo aggiornamento a */
           CASE WHEN ($10 IS NOT NULL) THEN p.dt_ultima_modifica <= $10 ELSE true END
      
        OFFSET CASE WHEN ($11) THEN $12 ELSE 0 END 

        LIMIT CASE WHEN ($11) THEN $13 ELSE null END
     ) as subq
    ORDER BY dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento ASC, cod_e_dati_pag_id_univoco_versamento ASC, cod_iud ASC;

   
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
COMMENT ON FUNCTION get_pagamenti_inseriti_in_accertamento(bigint, bigint, character varying, character varying, character varying, character varying, date, date, date, date, boolean, integer, integer) IS 'Pagamenti inseriti in accertamento';

CREATE OR REPLACE FUNCTION get_pagamenti_inseribili_in_accertamento(IN _ente_id bigint, IN _cod_tipo_dovuto character varying, IN _codice_iud character varying, IN _codice_iuv character varying, IN _codice_identificativo_univoco_pagatore character varying, IN _data_esito_singolo_pagamento_da date, IN _data_esito_singolo_pagamento_a date, IN _data_ultimo_aggiornamento_da date, IN _data_ultimo_aggiornamento_a date, IN _has_pagination boolean, IN _page integer, IN _page_size integer)
  RETURNS TABLE(cod_tipo_dovuto character varying, de_tipo_dovuto character varying, cod_iud character varying, cod_e_dati_pag_id_univoco_versamento character varying, cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss character varying, de_e_istit_att_denominazione_attestante character varying, cod_e_istit_att_id_univ_att_codice_id_univoco character varying, cod_e_istit_att_id_univ_att_tipo_id_univoco character, cod_e_sogg_vers_anagrafica_versante character varying, cod_e_sogg_vers_id_univ_vers_codice_id_univoco character varying, cod_e_sogg_vers_id_univ_vers_tipo_id_univoco character, cod_e_sogg_pag_anagrafica_pagatore character varying, cod_e_sogg_pag_id_univ_pag_codice_id_univoco character varying, cod_e_sogg_pag_id_univ_pag_tipo_id_univoco character, dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento date, dt_ultima_modifica timestamp without time zone, num_e_dati_pag_dati_sing_pag_singolo_importo_pagato numeric, de_e_dati_pag_dati_sing_pag_causale_versamento character varying) AS
$BODY$
  SELECT 
        cod_tipo_dovuto, td.de_tipo as de_tipo_dovuto, cod_iud, cod_e_dati_pag_id_univoco_versamento, cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss, de_e_istit_att_denominazione_attestante,
        cod_e_istit_att_id_univ_att_codice_id_univoco, cod_e_istit_att_id_univ_att_tipo_id_univoco, cod_e_sogg_vers_anagrafica_versante, 
        cod_e_sogg_vers_id_univ_vers_codice_id_univoco, cod_e_sogg_vers_id_univ_vers_tipo_id_univoco, cod_e_sogg_pag_anagrafica_pagatore, 
        cod_e_sogg_pag_id_univ_pag_codice_id_univoco, cod_e_sogg_pag_id_univ_pag_tipo_id_univoco, dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento,
        dt_ultima_modifica, num_e_dati_pag_dati_sing_pag_singolo_importo_pagato, de_e_dati_pag_dati_sing_pag_causale_versamento
  FROM 
      mygov_flusso_export AS p INNER JOIN mygov_ente_tipo_dovuto AS td ON p.cod_tipo_dovuto = td.cod_tipo AND td.mygov_ente_id = p.mygov_ente_id
  WHERE
      /* Escludo le righe gia in accertamento */
      (p.cod_iud  || '-' || p.cod_e_dati_pag_id_univoco_versamento) 
  NOT IN 
      (
        SELECT 
           ad.cod_iud || '-' || ad.cod_iuv 
        FROM 
           mygov_accertamento_dettaglio ad 
              INNER JOIN mygov_accertamento a ON ad.mygov_accertamento_id = a.mygov_accertamento_id
              INNER JOIN mygov_anagrafica_stato st ON a.mygov_anagrafica_stato_id = st.mygov_anagrafica_stato_id
        WHERE 
             st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato <> 'ANNULLATO' 
      ) AND
  
      /* Condizioni obbligatorie */
      p.mygov_ente_id = $1 AND p.cod_tipo_dovuto = $2 AND p.bilancio IS NULL AND
            
      /* IUD */
      CASE WHEN ($3 IS NOT NULL) THEN p.cod_iud = $3 ELSE true END AND
      /* IUV */
      CASE WHEN ($4 IS NOT NULL) THEN p.cod_e_dati_pag_id_univoco_versamento = $4 ELSE true END AND
      /* Identificativo univoco pagatore */
      CASE WHEN ($5 IS NOT NULL) THEN p.cod_e_sogg_pag_id_univ_pag_codice_id_univoco = $5 ELSE true END AND
      /* Data esito pagamento da */
      CASE WHEN ($6 IS NOT NULL) THEN p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento >= $6 ELSE true END AND
      /* Data esito pagamento a */
      CASE WHEN ($7 IS NOT NULL) THEN p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento <= $7 ELSE true END AND
      /* Data ultimo aggiornamento da */
      CASE WHEN ($8 IS NOT NULL) THEN p.dt_ultima_modifica >= $8 ELSE true END AND
      /* Data ultimo aggiornamento a */
      CASE WHEN ($9 IS NOT NULL) THEN p.dt_ultima_modifica <= $9 ELSE true END

   ORDER BY dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento ASC, cod_e_dati_pag_id_univoco_versamento ASC, cod_iud ASC

   OFFSET CASE WHEN ($10) THEN $11 ELSE 0 END 

   LIMIT CASE WHEN ($10) THEN $12 ELSE null END;
   
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
COMMENT ON FUNCTION get_pagamenti_inseribili_in_accertamento(bigint, character varying, character varying, character varying, character varying, date, date, date, date, boolean, integer, integer) IS 'Pagamenti inseribili in accertamento';

CREATE OR REPLACE FUNCTION get_import_export_rend_tes_function(_cod_fed_user_id character varying, _codice_ipa_ente character varying, _cod_iud character varying, _cod_iuv character varying, _denominazione_attestante character varying, _identificativo_univoco_riscossione character varying, _codice_identificativo_univoco_versante character varying, _anagrafica_versante character varying, _codice_identificativo_univoco_pagatore character varying, _anagrafica_pagatore character varying, _causale_versamento character varying, _data_esecuzione_singolo_pagamento_da date, _data_esecuzione_singolo_pagamento_a date, _data_esito_singolo_pagamento_da date, _data_esito_singolo_pagamento_a date, _identificativo_flusso_rendicontazione character varying, _identificativo_univoco_regolamento character varying, _data_regolamento_da date, _data_regolamento_a date, _dt_data_contabile_da date, _dt_data_contabile_a date, _dt_data_valuta_da date, _dt_data_valuta_a date, _dt_data_ultimo_aggiornamento_da date, _dt_data_ultimo_aggiornamento_a date, _cod_tipo_dovuto character varying, _is_cod_tipo_dovuto_present boolean, _importo character varying, _conto character varying, _codor1 character varying, _flagnascosto boolean, _classificazione_completezza character varying, _page integer, _size integer)
  RETURNS SETOF mygov_import_export_rendicontazione_tesoreria AS
$BODY$
   SELECT 
      tes.*
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.cod_iud
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms                              
     ON   ms.cod_ipa_ente = tes.codice_ipa_ente 
     AND (ms.cod_iuf IS NULL 
     AND  tes.cod_iuf_key IS NULL 
     OR   ms.cod_iuf = tes.cod_iuf_key)
     AND (ms.cod_iuv IS NULL 
     AND  tes.cod_iuv_key IS NULL 
     OR   ms.cod_iuv = tes.cod_iuv_key)
     AND (ms.cod_iud IS NULL 
     AND  tes.cod_iud_key IS NULL 
     OR   ms.cod_iud = tes.cod_iud_key)
     
   WHERE CASE WHEN (_cod_tipo_dovuto <> '') IS TRUE AND _is_cod_tipo_dovuto_present THEN tes.tipo_dovuto = _cod_tipo_dovuto 
              WHEN (_cod_tipo_dovuto <> '') IS NOT TRUE AND _is_cod_tipo_dovuto_present THEN 
                  tes.tipo_dovuto in (SELECT DISTINCT(metd.cod_tipo)
                                      FROM   mygov_operatore_ente_tipo_dovuto as moetd, mygov_ente_tipo_dovuto as metd 
                                      WHERE  moetd.mygov_ente_tipo_dovuto_id = metd.mygov_ente_tipo_dovuto_id
                                      AND    moetd.cod_fed_user_id = _cod_fed_user_id 
                                      AND   moetd.flg_attivo = true)
              ELSE true      
         END  
   AND   CASE WHEN (_codice_ipa_ente <> '') IS TRUE THEN tes.codice_ipa_ente = _codice_ipa_ente ELSE true END 
   AND   CASE WHEN (_cod_iud <> '') IS TRUE THEN tes.codice_iud = _cod_iud ELSE true END  
   AND   CASE WHEN (_cod_iuv <> '') IS TRUE THEN tes.codice_iuv = _cod_iuv ELSE true END        
   AND   CASE WHEN (_denominazione_attestante <> '') IS TRUE THEN 
                    (upper(tes.denominazione_attestante) like '%' || upper(_denominazione_attestante) || '%' 
                 OR upper(tes.codice_identificativo_univoco_attestante) like '%' || upper(_denominazione_attestante) || '%') 
         ELSE true END     
   AND   CASE WHEN (_identificativo_univoco_riscossione <> '') IS TRUE THEN tes.identificativo_univoco_riscossione = _identificativo_univoco_riscossione ELSE true END        
   AND   CASE WHEN (_codice_identificativo_univoco_versante <> '') IS TRUE THEN (tes.codice_identificativo_univoco_versante = upper(_codice_identificativo_univoco_versante) OR tes.codice_identificativo_univoco_versante = lower(_codice_identificativo_univoco_versante)) ELSE true END        
   AND   CASE WHEN (_anagrafica_versante <> '') IS TRUE THEN upper(tes.anagrafica_versante) like '%' || upper(_anagrafica_versante) || '%' ELSE true END              
   AND   CASE WHEN (_codice_identificativo_univoco_pagatore <> '') IS TRUE THEN (tes.codice_identificativo_univoco_pagatore = upper(_codice_identificativo_univoco_pagatore) OR tes.codice_identificativo_univoco_pagatore = lower(_codice_identificativo_univoco_pagatore)) ELSE true END           
   AND   CASE WHEN (_anagrafica_pagatore <> '') IS TRUE THEN upper(tes.anagrafica_pagatore) like '%' || upper(_anagrafica_pagatore) || '%' ELSE true END                  
   AND   CASE WHEN (_causale_versamento <> '') IS TRUE THEN upper(tes.causale_versamento) like '%' || upper(_causale_versamento) || '%' ELSE true END                    
   AND   CASE WHEN _data_esecuzione_singolo_pagamento_da IS NOT NULL THEN tes.dt_data_esecuzione_pagamento >= _data_esecuzione_singolo_pagamento_da ELSE true END                       
   AND   CASE WHEN _data_esecuzione_singolo_pagamento_a IS NOT NULL THEN tes.dt_data_esecuzione_pagamento <= _data_esecuzione_singolo_pagamento_a ELSE true END                     
   AND   CASE WHEN _data_esito_singolo_pagamento_da IS NOT NULL THEN tes.dt_data_esito_singolo_pagamento >= _data_esito_singolo_pagamento_da ELSE true END                       
   AND   CASE WHEN _data_esito_singolo_pagamento_a IS NOT NULL THEN tes.dt_data_esito_singolo_pagamento <= _data_esito_singolo_pagamento_a ELSE true END             
   AND   CASE WHEN (_identificativo_flusso_rendicontazione <> '') IS TRUE THEN tes.identificativo_flusso_rendicontazione = _identificativo_flusso_rendicontazione ELSE true END           
   AND   CASE WHEN (_identificativo_univoco_regolamento <> '') IS TRUE THEN tes.identificativo_univoco_regolamento = _identificativo_univoco_regolamento ELSE true END           
   AND   CASE WHEN _data_regolamento_da IS NOT NULL THEN tes.dt_data_regolamento >= _data_regolamento_da ELSE true END                       
   AND   CASE WHEN _data_regolamento_a IS NOT NULL THEN tes.dt_data_regolamento <= _data_regolamento_a ELSE true END             
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN tes.dt_data_contabile >= _dt_data_contabile_da ELSE true END                       
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN tes.dt_data_contabile <= _dt_data_contabile_a ELSE true END             
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN tes.dt_data_valuta >= _dt_data_valuta_da ELSE true END                      
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN tes.dt_data_valuta <= _dt_data_valuta_a ELSE true END              
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END                       
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END             
   AND   CASE WHEN (_importo <> '') IS TRUE THEN tes.de_importo = _importo ELSE true END                   
   AND   CASE WHEN (_conto <> '') IS TRUE THEN tes.cod_conto = _conto ELSE true END                 
   AND   CASE WHEN (_codOr1 <> '') IS TRUE THEN upper(tes.cod_or1)  like '%' || upper(_codOr1) || '%' ELSE true END                                                            
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END                    
   AND   CASE WHEN (_classificazione_completezza <> '') IS TRUE THEN tes.classificazione_completezza = _classificazione_completezza ELSE true END     
         

   
   ORDER BY CASE WHEN _classificazione_completezza = 'IUD_RT_IUF_TES' OR 
                      _classificazione_completezza = 'RT_IUF_TES' OR
                      _classificazione_completezza = 'RT_IUF' OR
                      _classificazione_completezza = 'IUD_RT_IUF' OR 
                      _classificazione_completezza = 'RT_NO_IUF' OR
                      _classificazione_completezza = 'RT_NO_IUD' OR
                      _classificazione_completezza = 'RT_TES' THEN (dt_data_esito_singolo_pagamento, codice_iuv, codice_iud)                         
                 WHEN _classificazione_completezza = 'IUD_NO_RT' THEN (dt_data_esecuzione_pagamento, codice_iud)  
                ELSE (dt_data_esito_singolo_pagamento, codice_iuv, codice_iud)
            END 

   OFFSET CASE WHEN (_page IS NOT NULL) THEN ((_page - 1)*_size) ELSE 0 END 
   LIMIT CASE WHEN (_size IS NOT NULL) THEN _size ELSE 5 END;
$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION get_count_tesoreria_subset_function(_codice_ipa_ente character varying, _dt_data_contabile_da date, _dt_data_contabile_a date, _dt_data_valuta_da date, _dt_data_valuta_a date, _dt_data_ultimo_aggiornamento_da date, _dt_data_ultimo_aggiornamento_a date, _de_causale_t text, _importo character varying, _conto character varying, _codor1 character varying, _flagnascosto boolean, _classificazione_completezza character varying, _cod_iuv character varying, _cod_iuf character varying)
  RETURNS bigint AS
$BODY$
   SELECT COUNT( DISTINCT(tes.codice_ipa_ente))
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms                              
     ON   ms.cod_ipa_ente = tes.codice_ipa_ente 
    AND (ms.cod_iuf IS NULL 
    AND  tes.cod_iuf_key IS NULL 
    OR   ms.cod_iuf = tes.cod_iuf_key)
    AND (ms.cod_iuv IS NULL 
    AND  tes.cod_iuv_key IS NULL 
    OR   ms.cod_iuv = tes.cod_iuv_key)
     
   WHERE CASE WHEN (_codice_ipa_ente <> '') IS TRUE THEN tes.codice_ipa_ente = _codice_ipa_ente ELSE true END     
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN tes.dt_data_contabile >= _dt_data_contabile_da ELSE true END                       
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN tes.dt_data_contabile <= _dt_data_contabile_a ELSE true END         
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN tes.dt_data_valuta >= _dt_data_valuta_da ELSE true END                      
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN tes.dt_data_valuta <= _dt_data_valuta_a ELSE true END              
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END                       
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END
   AND   CASE WHEN (_de_causale_t <> '') IS TRUE THEN upper(tes.de_causale_t) like '%' || upper(_de_causale_t) || '%' ELSE true END             
   AND   CASE WHEN (_importo <> '') IS TRUE THEN tes.de_importo = _importo ELSE true END                   
   AND   CASE WHEN (_conto <> '') IS TRUE THEN tes.cod_conto = _conto ELSE true END                 
   AND   CASE WHEN (_codOr1 <> '') IS TRUE THEN upper(tes.cod_or1) like '%' || upper(_codOr1) || '%' ELSE true END                                                            
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END                    
   AND   CASE WHEN (_classificazione_completezza <> '') IS TRUE THEN tes.classificazione_completezza = _classificazione_completezza ELSE true END
   AND   CASE WHEN (_cod_iuv <> '') IS TRUE THEN tes.codice_iuv = _cod_iuv ELSE true END
   AND   CASE WHEN (_cod_iuf <> '') IS TRUE THEN tes.identificativo_flusso_rendicontazione = _cod_iuf ELSE true END;
$BODY$
  LANGUAGE sql STABLE
  COST 100;

CREATE OR REPLACE FUNCTION get_count_tesoreria_no_match_subset_function(_codice_ipa_ente character varying, _dt_data_contabile_da date, _dt_data_contabile_a date, _dt_data_valuta_da date, _dt_data_valuta_a date, _dt_data_ultimo_aggiornamento_da date, _dt_data_ultimo_aggiornamento_a date, _de_causale_t text, _importo character varying, _conto character varying, _codor1 character varying, _flagnascosto boolean, _classificazione_completezza character varying)
  RETURNS bigint AS
$BODY$
   SELECT COUNT( DISTINCT(tes.codice_ipa_ente, tes.de_anno_bolletta, tes.cod_bolletta))
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms                              
     ON   ms.cod_ipa_ente = tes.codice_ipa_ente 
    AND (ms.cod_iuf IS NULL 
    AND  tes.cod_iuf_key IS NULL 
    OR   ms.cod_iuf = tes.cod_iuf_key)
    AND (ms.cod_iuv IS NULL 
    AND  tes.cod_iuv_key IS NULL 
    OR   ms.cod_iuv = tes.cod_iuv_key)
     
   WHERE CASE WHEN (_codice_ipa_ente <> '') IS TRUE THEN tes.codice_ipa_ente = _codice_ipa_ente ELSE true END     
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN tes.dt_data_contabile >= _dt_data_contabile_da ELSE true END                       
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN tes.dt_data_contabile <= _dt_data_contabile_a ELSE true END         
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN tes.dt_data_valuta >= _dt_data_valuta_da ELSE true END                      
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN tes.dt_data_valuta <= _dt_data_valuta_a ELSE true END              
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END                       
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END
   AND   CASE WHEN (_de_causale_t <> '') IS TRUE THEN upper(tes.de_causale_t) like '%' || upper(_de_causale_t) || '%' ELSE true END
   AND   CASE WHEN (_importo <> '') IS TRUE THEN tes.de_importo = _importo ELSE true END                   
   AND   CASE WHEN (_conto <> '') IS TRUE THEN tes.cod_conto = _conto ELSE true END                 
   AND   CASE WHEN (_codOr1 <> '') IS TRUE THEN upper(tes.cod_or1) like '%' || upper(_codOr1) || '%' ELSE true END                                                            
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END                    
   AND   CASE WHEN (_classificazione_completezza <> '') IS TRUE THEN tes.classificazione_completezza = _classificazione_completezza ELSE true END;
$BODY$
  LANGUAGE sql STABLE
  COST 100;

CREATE OR REPLACE FUNCTION get_count_rendicontazione_subset_function(_codice_ipa_ente character varying, _identificativo_flusso_rendicontazione character varying, _identificativo_univoco_regolamento character varying, _dt_data_regolamento_da date, _dt_data_regolamento_a date, _dt_data_ultimo_aggiornamento_da date, _dt_data_ultimo_aggiornamento_a date, _classificazione_completezza character varying, _cod_tipo_dovuto character varying, _cod_fed_user_id character varying, _flagnascosto boolean)
  RETURNS bigint AS
$BODY$
   SELECT 
         COUNT( DISTINCT(tes.identificativo_flusso_rendicontazione))
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT mseg.*, ment.* FROM mygov_segnalazione as mseg INNER JOIN mygov_ente as ment ON mseg.mygov_ente_id = ment.mygov_ente_id WHERE mseg.flg_attivo = true AND mseg.classificazione_completezza = $8) as ms 
     ON ms.cod_ipa_ente = tes.codice_ipa_ente AND (ms.cod_iuf = tes.cod_iuf_key OR (ms.cod_iuf IS NULL and tes.cod_iuf_key IS NULL))
   WHERE  
         CASE WHEN $1 IS NOT NULL AND $1!='' THEN tes.codice_ipa_ente = $1 ELSE true END
     AND CASE WHEN $2 IS NOT NULL AND $2!='' THEN tes.identificativo_flusso_rendicontazione = $2 ELSE true END
     AND (COALESCE($3, '') ='' OR tes.identificativo_univoco_regolamento = $3)
     AND CASE WHEN $4 IS NOT NULL THEN tes.dt_data_regolamento >= $4 ELSE true END
     AND CASE WHEN $5 IS NOT NULL THEN tes.dt_data_regolamento <= $5 ELSE true END
     AND CASE WHEN $6 IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= $6 ELSE true END
     AND CASE WHEN $7 IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento < $7 ELSE true END
     AND CASE WHEN $8 IS NOT NULL AND $8!='' THEN tes.classificazione_completezza = $8 ELSE true END
     AND CASE WHEN $9 IS NOT NULL AND $9!='' AND tes.classificazione_completezza <> 'IUV_NO_RT' THEN tes.tipo_dovuto = $9 
          WHEN ($9 IS NULL OR $9='') AND tes.classificazione_completezza <> 'IUV_NO_RT' THEN  
      tes.tipo_dovuto in (SELECT
           DISTINCT(metd.cod_tipo)
        FROM 
           mygov_operatore_ente_tipo_dovuto as moetd, mygov_ente_tipo_dovuto as metd 
        WHERE
           moetd.mygov_ente_tipo_dovuto_id = metd.mygov_ente_tipo_dovuto_id AND
           moetd.cod_fed_user_id = $10 AND 
           moetd.flg_attivo = true)
      ELSE true
   END
     AND CASE WHEN $11 IS NOT NULL 
         THEN
       ms.flg_nascosto = $11
         ELSE
             (ms.flg_nascosto is null or ms.flg_nascosto = false)
         END

$BODY$
  LANGUAGE sql VOLATILE
  COST 100;

CREATE OR REPLACE FUNCTION get_count_pagamenti_inseriti_in_accertamento(_accertamento_id bigint, _ente_id bigint, _cod_tipo_dovuto character varying, _codice_iud character varying, _codice_iuv character varying, _codice_identificativo_univoco_pagatore character varying, _data_esito_singolo_pagamento_da date, _data_esito_singolo_pagamento_a date, _data_ultimo_aggiornamento_da date, _data_ultimo_aggiornamento_a date)
  RETURNS bigint AS
$BODY$
  SELECT 
       count(DISTINCT(a.cod_iud || '-' || a.cod_iuv))
  FROM 
      mygov_flusso_export AS p INNER JOIN mygov_accertamento_dettaglio AS a ON p.cod_iud = a.cod_iud AND p.cod_e_dati_pag_id_univoco_versamento = a.cod_iuv
  WHERE
      /* Condizioni obbligatorie */
      a.mygov_accertamento_id = $1 AND p.mygov_ente_id = $2 AND p.cod_tipo_dovuto = $3 AND p.bilancio IS NULL AND
            
      /* IUD */
      CASE WHEN ($4 IS NOT NULL) THEN p.cod_iud = $4 ELSE true END AND
      /* IUV */
      CASE WHEN ($5 IS NOT NULL) THEN p.cod_e_dati_pag_id_univoco_versamento = $5 ELSE true END AND
      /* Identificativo univoco pagatore */
      CASE WHEN ($6 IS NOT NULL) THEN p.cod_e_sogg_pag_id_univ_pag_codice_id_univoco = $6 ELSE true END AND
      /* Data esito pagamento da */
      CASE WHEN ($7 IS NOT NULL) THEN p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento >= $7 ELSE true END AND
      /* Data esito pagamento a */
      CASE WHEN ($8 IS NOT NULL) THEN p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento <= $8 ELSE true END AND
      /* Data ultimo aggiornamento da */
      CASE WHEN ($9 IS NOT NULL) THEN p.dt_ultima_modifica >= $9 ELSE true END AND
      /* Data ultimo aggiornamento a */
      CASE WHEN ($10 IS NOT NULL) THEN p.dt_ultima_modifica <= $10 ELSE true END;
   
$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
COMMENT ON FUNCTION get_count_pagamenti_inseriti_in_accertamento(bigint, bigint, character varying, character varying, character varying, character varying, date, date, date, date) IS 'Count dei pagamenti inseriti in accertamento';

CREATE OR REPLACE FUNCTION get_count_pagamenti_inseribili_in_accertamento(_ente_id bigint, _cod_tipo_dovuto character varying, _codice_iud character varying, _codice_iuv character varying, _codice_identificativo_univoco_pagatore character varying, _data_esito_singolo_pagamento_da date, _data_esito_singolo_pagamento_a date, _data_ultimo_aggiornamento_da date, _data_ultimo_aggiornamento_a date)
  RETURNS bigint AS
$BODY$
 
  SELECT 
        count(*) 
  FROM 
      mygov_flusso_export AS p
  WHERE 
      /* Escludo le righe gia in accertamento */
      (p.cod_iud  || '-' || p.cod_e_dati_pag_id_univoco_versamento) 
  NOT IN 
      (
        SELECT 
           ad.cod_iud || '-' || ad.cod_iuv 
        FROM 
           mygov_accertamento_dettaglio ad 
              INNER JOIN mygov_accertamento a ON ad.mygov_accertamento_id = a.mygov_accertamento_id
              INNER JOIN mygov_anagrafica_stato st ON a.mygov_anagrafica_stato_id = st.mygov_anagrafica_stato_id
        WHERE 
             st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato <> 'ANNULLATO' 
      ) AND
  
      /* Condizioni obbligatorie */
      p.mygov_ente_id = $1 AND p.cod_tipo_dovuto = $2 AND p.bilancio IS NULL AND
            
      /* IUD */
      CASE WHEN ($3 IS NOT NULL) THEN p.cod_iud = $3 ELSE true END AND
      /* IUV */
      CASE WHEN ($4 IS NOT NULL) THEN p.cod_e_dati_pag_id_univoco_versamento = $4 ELSE true END AND
      /* Identificativo univoco pagatore */
      CASE WHEN ($5 IS NOT NULL) THEN p.cod_e_sogg_pag_id_univ_pag_codice_id_univoco = $5 ELSE true END AND
      /* Data esito pagamento da */
      CASE WHEN ($6 IS NOT NULL) THEN p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento >= $6 ELSE true END AND
      /* Data esito pagamento a */
      CASE WHEN ($7 IS NOT NULL) THEN p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento <= $7 ELSE true END AND
      /* Data ultimo aggiornamento da */
      CASE WHEN ($8 IS NOT NULL) THEN p.dt_ultima_modifica >= $8 ELSE true END AND
      /* Data ultimo aggiornamento a */
      CASE WHEN ($9 IS NOT NULL) THEN p.dt_ultima_modifica <= $9 ELSE true END;
   
$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
COMMENT ON FUNCTION get_count_pagamenti_inseribili_in_accertamento(bigint, character varying, character varying, character varying, character varying, date, date, date, date) IS 'Count dei Pagamenti inseribili in accertamento';



CREATE OR REPLACE FUNCTION get_count_import_export_rend_tes_function(_cod_fed_user_id character varying, _codice_ipa_ente character varying, _cod_iud character varying, _cod_iuv character varying, _denominazione_attestante character varying, _identificativo_univoco_riscossione character varying, _codice_identificativo_univoco_versante character varying, _anagrafica_versante character varying, _codice_identificativo_univoco_pagatore character varying, _anagrafica_pagatore character varying, _causale_versamento character varying, _data_esecuzione_singolo_pagamento_da date, _data_esecuzione_singolo_pagamento_a date, _data_esito_singolo_pagamento_da date, _data_esito_singolo_pagamento_a date, _identificativo_flusso_rendicontazione character varying, _identificativo_univoco_regolamento character varying, _data_regolamento_da date, _data_regolamento_a date, _dt_data_contabile_da date, _dt_data_contabile_a date, _dt_data_valuta_da date, _dt_data_valuta_a date, _dt_data_ultimo_aggiornamento_da date, _dt_data_ultimo_aggiornamento_a date, _cod_tipo_dovuto character varying, _is_cod_tipo_dovuto_present boolean, _importo character varying, _conto character varying, _codor1 character varying, _flagnascosto boolean, _classificazione_completezza character varying)
  RETURNS bigint AS
$BODY$
   SELECT 
      count(1)
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
	LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.cod_iud
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms                              
	   ON   ms.cod_ipa_ente = tes.codice_ipa_ente 
     AND (ms.cod_iuf IS NULL 
     AND  tes.cod_iuf_key IS NULL 
     OR   ms.cod_iuf = tes.cod_iuf_key)
     AND (ms.cod_iuv IS NULL 
     AND  tes.cod_iuv_key IS NULL 
     OR   ms.cod_iuv = tes.cod_iuv_key)
     AND (ms.cod_iud IS NULL 
     AND  tes.cod_iud_key IS NULL 
     OR   ms.cod_iud = tes.cod_iud_key)
     
   WHERE CASE WHEN (_cod_tipo_dovuto <> '') IS TRUE AND _is_cod_tipo_dovuto_present THEN tes.tipo_dovuto = _cod_tipo_dovuto 
      	      WHEN (_cod_tipo_dovuto <> '') IS NOT TRUE AND _is_cod_tipo_dovuto_present THEN 
            	    tes.tipo_dovuto in (SELECT DISTINCT(metd.cod_tipo)
                              				FROM   mygov_operatore_ente_tipo_dovuto as moetd, mygov_ente_tipo_dovuto as metd 
                              				WHERE  moetd.mygov_ente_tipo_dovuto_id = metd.mygov_ente_tipo_dovuto_id
                              				AND    moetd.cod_fed_user_id = _cod_fed_user_id 
                              				AND   moetd.flg_attivo = true)
    		      ELSE true		   
    	   END	
   AND   CASE WHEN (_codice_ipa_ente <> '') IS TRUE THEN tes.codice_ipa_ente = _codice_ipa_ente ELSE true END 
   AND   CASE WHEN (_cod_iud <> '') IS TRUE THEN tes.codice_iud = _cod_iud ELSE true END  
   AND   CASE WHEN (_cod_iuv <> '') IS TRUE THEN tes.codice_iuv = _cod_iuv ELSE true END        
   AND   CASE WHEN (_denominazione_attestante <> '') IS TRUE THEN 
                    (upper(tes.denominazione_attestante) like '%' || upper(_denominazione_attestante) || '%' 
                 OR upper(tes.codice_identificativo_univoco_attestante) like '%' || upper(_denominazione_attestante) || '%') 
         ELSE true END     
   AND   CASE WHEN (_identificativo_univoco_riscossione <> '') IS TRUE THEN tes.identificativo_univoco_riscossione = _identificativo_univoco_riscossione ELSE true END        
   AND   CASE WHEN (_codice_identificativo_univoco_versante <> '') IS TRUE THEN (tes.codice_identificativo_univoco_versante = upper(_codice_identificativo_univoco_versante) OR tes.codice_identificativo_univoco_versante = lower(_codice_identificativo_univoco_versante)) ELSE true END        
   AND   CASE WHEN (_anagrafica_versante <> '') IS TRUE THEN upper(tes.anagrafica_versante) like '%' || upper(_anagrafica_versante) || '%' ELSE true END              
   AND   CASE WHEN (_codice_identificativo_univoco_pagatore <> '') IS TRUE THEN (tes.codice_identificativo_univoco_pagatore = upper(_codice_identificativo_univoco_pagatore) OR tes.codice_identificativo_univoco_pagatore = lower(_codice_identificativo_univoco_pagatore)) ELSE true END           
   AND   CASE WHEN (_anagrafica_pagatore <> '') IS TRUE THEN upper(tes.anagrafica_pagatore) like '%' || upper(_anagrafica_pagatore) || '%' ELSE true END                  
   AND   CASE WHEN (_causale_versamento <> '') IS TRUE THEN upper(tes.causale_versamento) like '%' || upper(_causale_versamento) || '%' ELSE true END                    
   AND   CASE WHEN _data_esecuzione_singolo_pagamento_da IS NOT NULL THEN tes.dt_data_esecuzione_pagamento >= _data_esecuzione_singolo_pagamento_da ELSE true END                       
   AND   CASE WHEN _data_esecuzione_singolo_pagamento_a IS NOT NULL THEN tes.dt_data_esecuzione_pagamento <= _data_esecuzione_singolo_pagamento_a ELSE true END                     
   AND   CASE WHEN _data_esito_singolo_pagamento_da IS NOT NULL THEN tes.dt_data_esito_singolo_pagamento >= _data_esito_singolo_pagamento_da ELSE true END                       
   AND   CASE WHEN _data_esito_singolo_pagamento_a IS NOT NULL THEN tes.dt_data_esito_singolo_pagamento <= _data_esito_singolo_pagamento_a ELSE true END             
   AND   CASE WHEN (_identificativo_flusso_rendicontazione <> '') IS TRUE THEN tes.identificativo_flusso_rendicontazione = _identificativo_flusso_rendicontazione ELSE true END           
   AND   CASE WHEN (_identificativo_univoco_regolamento <> '') IS TRUE THEN tes.identificativo_univoco_regolamento = _identificativo_univoco_regolamento ELSE true END           
   AND   CASE WHEN _data_regolamento_da IS NOT NULL THEN tes.dt_data_regolamento >= _data_regolamento_da ELSE true END                       
   AND   CASE WHEN _data_regolamento_a IS NOT NULL THEN tes.dt_data_regolamento <= _data_regolamento_a ELSE true END             
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN tes.dt_data_contabile >= _dt_data_contabile_da ELSE true END                       
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN tes.dt_data_contabile <= _dt_data_contabile_a ELSE true END             
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN tes.dt_data_valuta >= _dt_data_valuta_da ELSE true END                      
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN tes.dt_data_valuta <= _dt_data_valuta_a ELSE true END              
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END                       
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END             
   AND   CASE WHEN (_importo <> '') IS TRUE THEN tes.de_importo = _importo ELSE true END                   
   AND   CASE WHEN (_conto <> '') IS TRUE THEN tes.cod_conto = _conto ELSE true END                 
   AND   CASE WHEN (_codOr1 <> '') IS TRUE THEN upper(tes.cod_or1)  like '%' || upper(_codOr1) || '%' ELSE true END                                                            
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END                    
   AND   CASE WHEN (_classificazione_completezza <> '') IS TRUE THEN tes.classificazione_completezza = _classificazione_completezza ELSE true END;
$BODY$
  LANGUAGE sql STABLE
  COST 100;


CREATE INDEX idx_mygovaccdett_ipa_iud
  ON mygov_accertamento_dettaglio
  USING btree
  (cod_ipa_ente COLLATE pg_catalog."default", cod_iud COLLATE pg_catalog."default");


CREATE OR REPLACE FUNCTION get_rendicontazione_tesoreria_subset_function(IN _codice_ipa_ente character varying, IN _identificativo_flusso_rendicontazione character varying, IN _identificativo_univoco_regolamento character varying, IN _dt_data_regolamento_da date, IN _dt_data_regolamento_a date, IN _dt_data_contabile_da date, IN _dt_data_contabile_a date, IN _dt_data_valuta_da date, IN _dt_data_valuta_a date, IN _dt_data_ultimo_aggiornamento_da date, IN _dt_data_ultimo_aggiornamento_a date, IN _de_causale_t text, IN _importo character varying, IN _conto character varying, IN _codor1 character varying, IN _cod_tipo_dovuto character varying, IN _is_cod_tipo_dovuto_present boolean, IN _cod_fed_user_id character varying, IN _flagnascosto boolean, IN _classificazione_completezza character varying, IN _page integer, IN _size integer)
  RETURNS TABLE(identificativo_flusso_rendicontazione character varying, codice_ipa_ente character varying, dt_data_esecuzione_pagamento date, de_data_esecuzione_pagamento character varying, singolo_importo_commissione_carico_pa character varying, cod_conto character varying, dt_data_contabile date, de_data_contabile character varying, dt_data_valuta date, de_data_valuta character varying, num_importo numeric, de_importo character varying, cod_or1 text, de_anno_bolletta character varying, cod_bolletta character varying, cod_id_dominio character varying, dt_ricezione timestamp without time zone, de_data_ricezione character varying, de_anno_documento character varying, cod_documento character varying, de_anno_provvisorio character varying, cod_provvisorio character varying, de_causale_t text, classificazione_completezza character varying, dt_data_ultimo_aggiornamento date, de_data_ultimo_aggiornamento character varying, data_ora_flusso_rendicontazione character varying, identificativo_univoco_regolamento character varying, dt_data_regolamento date, de_data_regolamento character varying, importo_totale_pagamenti character varying, cod_iuf_key character varying) AS
$BODY$
   SELECT 
        DISTINCT (iert.identificativo_flusso_rendicontazione),
        iert.codice_ipa_ente,
		iert.dt_data_esecuzione_pagamento,
		iert.de_data_esecuzione_pagamento,
		iert.singolo_importo_commissione_carico_pa,
		iert.cod_conto,
		iert.dt_data_contabile,
		iert.de_data_contabile,
		iert.dt_data_valuta,
		iert.de_data_valuta,
		iert.num_importo,
		iert.de_importo,
		iert.cod_or1,
		iert.de_anno_bolletta,
		iert.cod_bolletta,
		iert.cod_id_dominio,
		iert.dt_ricezione,
		iert.de_data_ricezione,
		iert.de_anno_documento,
		iert.cod_documento,
		iert.de_anno_provvisorio,
		iert.cod_provvisorio,
		iert.de_causale_t,
		iert.classificazione_completezza,
		iert.dt_data_ultimo_aggiornamento,
		iert.de_data_ultimo_aggiornamento,
		iert.data_ora_flusso_rendicontazione,
		iert.identificativo_univoco_regolamento,
		iert.dt_data_regolamento,
		iert.de_data_regolamento,
		iert.importo_totale_pagamenti,
		iert.cod_iuf_key
   FROM 
      mygov_import_export_rendicontazione_tesoreria as iert 
  LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms
     ON   ms.cod_ipa_ente = iert.codice_ipa_ente 
    AND (ms.cod_iuf IS NULL 
    AND  iert.cod_iuf_key IS NULL 
    OR   ms.cod_iuf = iert.cod_iuf_key)
    AND (ms.cod_iuv IS NULL 
    AND  iert.cod_iuv_key IS NULL 
    OR   ms.cod_iuv = iert.cod_iuv_key)
     
   WHERE CASE WHEN _codice_ipa_ente IS NOT NULL THEN iert.codice_ipa_ente = _codice_ipa_ente ELSE true END
   AND   CASE WHEN _identificativo_flusso_rendicontazione IS NOT NULL THEN iert.identificativo_flusso_rendicontazione = _identificativo_flusso_rendicontazione ELSE true END
   AND   CASE WHEN _identificativo_univoco_regolamento IS NOT NULL THEN iert.identificativo_univoco_regolamento = _identificativo_univoco_regolamento ELSE true END
   AND   CASE WHEN _dt_data_regolamento_da IS NOT NULL THEN iert.dt_data_regolamento >= _dt_data_regolamento_da ELSE true END
   AND   CASE WHEN _dt_data_regolamento_a IS NOT NULL THEN iert.dt_data_regolamento <= _dt_data_regolamento_a ELSE true END
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN iert.dt_data_contabile >= _dt_data_contabile_da ELSE true END
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN iert.dt_data_contabile <= _dt_data_contabile_a ELSE true END
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN iert.dt_data_valuta >= _dt_data_valuta_da ELSE true END
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN iert.dt_data_valuta <= _dt_data_valuta_a ELSE true END
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN iert.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN iert.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END
   AND   CASE WHEN _de_causale_t IS NOT NULL THEN upper(iert.de_causale_t) like '%' || upper(_de_causale_t) || '%' ELSE true END
   AND   CASE WHEN _importo IS NOT NULL THEN iert.de_importo = _importo ELSE true END
   AND   CASE WHEN _conto IS NOT NULL THEN iert.cod_conto = _conto ELSE true END
   AND   CASE WHEN _codOr1 IS NOT NULL THEN upper(iert.cod_or1) like '%' || upper(_codOr1) || '%' ELSE true END
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END
   AND	 CASE WHEN _cod_tipo_dovuto IS NOT NULL AND _is_cod_tipo_dovuto_present THEN iert.tipo_dovuto = _cod_tipo_dovuto
              WHEN _cod_tipo_dovuto IS NULL AND _is_cod_tipo_dovuto_present THEN
                  iert.tipo_dovuto in (SELECT DISTINCT(metd.cod_tipo)
                                      FROM   mygov_operatore_ente_tipo_dovuto as moetd, mygov_ente_tipo_dovuto as metd
                                      WHERE  moetd.mygov_ente_tipo_dovuto_id = metd.mygov_ente_tipo_dovuto_id
                                      AND    moetd.cod_fed_user_id = _cod_fed_user_id 
                                      AND   moetd.flg_attivo = true)
              ELSE true
         END
   AND   CASE WHEN _classificazione_completezza IS NOT NULL THEN iert.classificazione_completezza = _classificazione_completezza ELSE true END
   
   ORDER BY iert.dt_data_regolamento, iert.dt_data_contabile, iert.dt_data_valuta, iert.dt_data_ultimo_aggiornamento
   OFFSET CASE WHEN (_page IS NOT NULL) THEN ((_page - 1)*_size) ELSE 0 END 
   LIMIT CASE WHEN (_size IS NOT NULL) THEN _size ELSE 5 END;
$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;


CREATE OR REPLACE FUNCTION get_count_rendicontazione_tesoreria_subset_function(IN _codice_ipa_ente character varying, IN _identificativo_flusso_rendicontazione character varying, IN _identificativo_univoco_regolamento character varying, IN _dt_data_regolamento_da date, IN _dt_data_regolamento_a date, IN _dt_data_contabile_da date, IN _dt_data_contabile_a date, IN _dt_data_valuta_da date, IN _dt_data_valuta_a date, IN _dt_data_ultimo_aggiornamento_da date, IN _dt_data_ultimo_aggiornamento_a date, IN _de_causale_t text, IN _importo character varying, IN _conto character varying, IN _codor1 character varying, IN _cod_tipo_dovuto character varying, IN _is_cod_tipo_dovuto_present boolean, IN _cod_fed_user_id character varying, IN _flagnascosto boolean, IN _classificazione_completezza character varying)
  RETURNS bigint AS
$BODY$
   SELECT 
        count(DISTINCT(iert.codice_ipa_ente, iert.identificativo_flusso_rendicontazione))
   FROM 
      mygov_import_export_rendicontazione_tesoreria as iert 
  LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms
     ON   ms.cod_ipa_ente = iert.codice_ipa_ente 
    AND (ms.cod_iuf IS NULL 
    AND  iert.cod_iuf_key IS NULL 
    OR   ms.cod_iuf = iert.cod_iuf_key)
    AND (ms.cod_iuv IS NULL 
    AND  iert.cod_iuv_key IS NULL 
    OR   ms.cod_iuv = iert.cod_iuv_key)
     
   WHERE CASE WHEN _codice_ipa_ente IS NOT NULL THEN iert.codice_ipa_ente = _codice_ipa_ente ELSE true END
   AND   CASE WHEN _identificativo_flusso_rendicontazione IS NOT NULL THEN iert.identificativo_flusso_rendicontazione = _identificativo_flusso_rendicontazione ELSE true END
   AND   CASE WHEN _identificativo_univoco_regolamento IS NOT NULL THEN iert.identificativo_univoco_regolamento = _identificativo_univoco_regolamento ELSE true END
   AND   CASE WHEN _dt_data_regolamento_da IS NOT NULL THEN iert.dt_data_regolamento >= _dt_data_regolamento_da ELSE true END
   AND   CASE WHEN _dt_data_regolamento_a IS NOT NULL THEN iert.dt_data_regolamento <= _dt_data_regolamento_a ELSE true END
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN iert.dt_data_contabile >= _dt_data_contabile_da ELSE true END
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN iert.dt_data_contabile <= _dt_data_contabile_a ELSE true END
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN iert.dt_data_valuta >= _dt_data_valuta_da ELSE true END
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN iert.dt_data_valuta <= _dt_data_valuta_a ELSE true END
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN iert.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN iert.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END
   AND   CASE WHEN _de_causale_t IS NOT NULL THEN upper(iert.de_causale_t) like '%' || upper(_de_causale_t) || '%' ELSE true END
   AND   CASE WHEN _importo IS NOT NULL THEN iert.de_importo = _importo ELSE true END
   AND   CASE WHEN _conto IS NOT NULL THEN iert.cod_conto = _conto ELSE true END
   AND   CASE WHEN _codOr1 IS NOT NULL THEN upper(iert.cod_or1) like '%' || upper(_codOr1) || '%' ELSE true END
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END
   AND	 CASE WHEN _cod_tipo_dovuto IS NOT NULL AND _is_cod_tipo_dovuto_present THEN iert.tipo_dovuto = _cod_tipo_dovuto
              WHEN _cod_tipo_dovuto IS NULL AND _is_cod_tipo_dovuto_present THEN
                  iert.tipo_dovuto in (SELECT DISTINCT(metd.cod_tipo)
                                      FROM   mygov_operatore_ente_tipo_dovuto as moetd, mygov_ente_tipo_dovuto as metd
                                      WHERE  moetd.mygov_ente_tipo_dovuto_id = metd.mygov_ente_tipo_dovuto_id
                                      AND    moetd.cod_fed_user_id = _cod_fed_user_id 
                                      AND   moetd.flg_attivo = true)
              ELSE true
         END
   AND   CASE WHEN _classificazione_completezza IS NOT NULL THEN iert.classificazione_completezza = _classificazione_completezza ELSE true END;
$BODY$
  LANGUAGE sql STABLE
  COST 100;


CREATE INDEX idx_mygov_flusso_export_mygov_ente_id_iud
  ON mygov_flusso_export
  USING btree
  (mygov_ente_id, cod_iud COLLATE pg_catalog."default");


-- funzioni per la visualizzazione raggruppata del flusso di rendicontazione
CREATE OR REPLACE FUNCTION get_flusso_rendicontazione_function(IN _mygov_ente_id bigint, 
  IN _dt_data_regolamento_da date, IN _dt_data_regolamento_a date, 
  IN _cod_iuf character varying, IN _identificativo_univoco_regolamento character varying, 
  IN _page integer, IN _page_size integer)
  RETURNS TABLE(cod_identificativo_flusso character varying,
                mygov_ente_id bigint,
                mygov_manage_flusso_id bigint,
                identificativo_psp character varying,
                dt_data_ora_flusso timestamp without time zone,
                cod_identificativo_univoco_regolamento character varying,
                dt_data_regolamento date,
                cod_ist_mitt_id_univ_mitt_tipo_identificativo_univoco character,
                cod_ist_mitt_id_univ_mitt_codice_identificativo_univoco character varying,
                de_ist_mitt_denominazione_mittente character varying,
                cod_ist_ricev_id_univ_ricev_tipo_identificativo_univoco character,
                cod_ist_ricev_id_univ_ricev_codice_identificativo_univoco character varying,
                de_ist_ricev_denominazione_ricevente character varying,
                num_numero_totale_pagamenti numeric,
                num_importo_totale_pagamenti numeric,
                dt_acquisizione date,
                codice_bic_banca_di_riversamento character varying) AS
$BODY$
  SELECT 
    DISTINCT(rend.cod_identificativo_flusso),
    rend.mygov_ente_id,
    rend.mygov_manage_flusso_id,
    rend.identificativo_psp,
    rend.dt_data_ora_flusso,
    rend.cod_identificativo_univoco_regolamento,
    rend.dt_data_regolamento,
    rend.cod_ist_mitt_id_univ_mitt_tipo_identificativo_univoco,
    rend.cod_ist_mitt_id_univ_mitt_codice_identificativo_univoco,
    rend.de_ist_mitt_denominazione_mittente,
    rend.cod_ist_ricev_id_univ_ricev_tipo_identificativo_univoco,
    rend.cod_ist_ricev_id_univ_ricev_codice_identificativo_univoco,
    rend.de_ist_ricev_denominazione_ricevente,
    rend.num_numero_totale_pagamenti,
    rend.num_importo_totale_pagamenti,
    rend.dt_acquisizione,
    rend.codice_bic_banca_di_riversamento
  FROM mygov_flusso_rendicontazione rend
     
  WHERE CASE WHEN _mygov_ente_id IS NOT NULL THEN rend.mygov_ente_id = _mygov_ente_id ELSE true END
  AND   CASE WHEN _dt_data_regolamento_da IS NOT NULL THEN rend.dt_data_regolamento >= _dt_data_regolamento_da ELSE true END
  AND   CASE WHEN _dt_data_regolamento_a IS NOT NULL THEN rend.dt_data_regolamento <= _dt_data_regolamento_a ELSE true END
  AND   CASE WHEN (_cod_iuf <> '') IS TRUE THEN rend.cod_identificativo_flusso = _cod_iuf ELSE true END
  AND   CASE WHEN (_identificativo_univoco_regolamento <> '') IS TRUE THEN rend.cod_identificativo_univoco_regolamento = _identificativo_univoco_regolamento ELSE true END
      
  ORDER BY rend.dt_data_regolamento DESC, rend.dt_data_ora_flusso DESC, rend.cod_identificativo_flusso DESC
  OFFSET CASE WHEN (_page IS NOT NULL) THEN ((_page - 1) * _page_size) ELSE 0 END 
  LIMIT CASE WHEN (_page_size IS NOT NULL) THEN _page_size ELSE 5 END;
$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;

CREATE OR REPLACE FUNCTION get_count_flusso_rendicontazione_function(IN _mygov_ente_id bigint, 
  IN _dt_data_regolamento_da date, IN _dt_data_regolamento_a date, 
  IN _cod_iuf character varying, IN _identificativo_univoco_regolamento character varying)
  RETURNS BIGINT AS
$BODY$
  SELECT count(DISTINCT(rend.cod_identificativo_flusso))
  FROM mygov_flusso_rendicontazione rend
     
  WHERE CASE WHEN _mygov_ente_id IS NOT NULL THEN rend.mygov_ente_id = _mygov_ente_id ELSE true END
  AND   CASE WHEN _dt_data_regolamento_da IS NOT NULL THEN rend.dt_data_regolamento >= _dt_data_regolamento_da ELSE true END
  AND   CASE WHEN _dt_data_regolamento_a IS NOT NULL THEN rend.dt_data_regolamento <= _dt_data_regolamento_a ELSE true END
  AND   CASE WHEN (_cod_iuf <> '') IS TRUE THEN rend.cod_identificativo_flusso = _cod_iuf ELSE true END
  AND   CASE WHEN (_identificativo_univoco_regolamento <> '') IS TRUE THEN rend.cod_identificativo_univoco_regolamento = _identificativo_univoco_regolamento ELSE true END;
$BODY$
  LANGUAGE sql STABLE
  COST 100;

CREATE INDEX idx_mygov_flusso_rendicontazione_mygov_ente_id_dt_reg_fkey
  ON mygov_flusso_rendicontazione
  USING btree
  (mygov_ente_id, dt_data_regolamento);

ALTER TABLE ONLY mygov_flusso_tesoreria
    DROP CONSTRAINT mygov_flusso_tesoreria_ukey;

ALTER TABLE ONLY mygov_flusso_tesoreria
    ADD CONSTRAINT mygov_flusso_tesoreria_ukey UNIQUE (mygov_ente_id, de_anno_bolletta, cod_bolletta);


CREATE INDEX mygov_accertamento_dettaglio_cod_iud_cod_iuv_idx
  ON mygov_accertamento_dettaglio
  USING btree
  (cod_iud COLLATE pg_catalog."default", cod_iuv COLLATE pg_catalog."default");

CREATE INDEX idxu_mygov_anagrafica_uff_cap_acc
  ON mygov_anagrafica_uff_cap_acc
  USING btree
  (mygov_ente_id, cod_ufficio COLLATE pg_catalog."default", cod_capitolo COLLATE pg_catalog."default", cod_accertamento COLLATE pg_catalog."default");


DROP FUNCTION get_count_tesoreria_subset_function(character varying, date, date, date, date, date, date, text, character varying, character varying, character varying, boolean, character varying, character varying, character varying);

CREATE OR REPLACE FUNCTION get_count_tesoreria_subset_function(_codice_ipa_ente character varying, _dt_data_contabile_da date, _dt_data_contabile_a date, _dt_data_valuta_da date, _dt_data_valuta_a date, _dt_data_ultimo_aggiornamento_da date, _dt_data_ultimo_aggiornamento_a date, _de_causale_t text, _importo character varying, _conto character varying, _codor1 character varying, _flagnascosto boolean, _classificazione_completezza character varying, _cod_iuv character varying, _cod_iuf character varying)
  RETURNS bigint AS
$BODY$
   SELECT COUNT(1)
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT ment.cod_ipa_ente
                        , mseg.cod_iuf
                        , mseg.cod_iuv
                        , mseg.flg_nascosto
                     FROM mygov_segnalazione as mseg 
                             INNER JOIN   mygov_ente as ment 
                             ON           mseg.mygov_ente_id = ment.mygov_ente_id 
                             WHERE        mseg.flg_attivo = true 
                             AND          mseg.classificazione_completezza = _classificazione_completezza) as ms                              
     ON   ms.cod_ipa_ente = tes.codice_ipa_ente 
    AND (ms.cod_iuf IS NULL 
    AND  tes.cod_iuf_key IS NULL 
    OR   ms.cod_iuf = tes.cod_iuf_key)
    AND (ms.cod_iuv IS NULL 
    AND  tes.cod_iuv_key IS NULL 
    OR   ms.cod_iuv = tes.cod_iuv_key)
     
   WHERE CASE WHEN (_codice_ipa_ente <> '') IS TRUE THEN tes.codice_ipa_ente = _codice_ipa_ente ELSE true END     
   AND   CASE WHEN _dt_data_contabile_da IS NOT NULL THEN tes.dt_data_contabile >= _dt_data_contabile_da ELSE true END                       
   AND   CASE WHEN _dt_data_contabile_a IS NOT NULL THEN tes.dt_data_contabile <= _dt_data_contabile_a ELSE true END         
   AND   CASE WHEN _dt_data_valuta_da IS NOT NULL THEN tes.dt_data_valuta >= _dt_data_valuta_da ELSE true END                      
   AND   CASE WHEN _dt_data_valuta_a IS NOT NULL THEN tes.dt_data_valuta <= _dt_data_valuta_a ELSE true END              
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_da IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento >= _dt_data_ultimo_aggiornamento_da ELSE true END                       
   AND   CASE WHEN _dt_data_ultimo_aggiornamento_a IS NOT NULL THEN tes.dt_data_ultimo_aggiornamento <= _dt_data_ultimo_aggiornamento_a ELSE true END
   AND   CASE WHEN (_de_causale_t <> '') IS TRUE THEN upper(tes.de_causale_t) like '%' || upper(_de_causale_t) || '%' ELSE true END             
   AND   CASE WHEN (_importo <> '') IS TRUE THEN tes.de_importo = _importo ELSE true END                   
   AND   CASE WHEN (_conto <> '') IS TRUE THEN tes.cod_conto = _conto ELSE true END                 
   AND   CASE WHEN (_codOr1 <> '') IS TRUE THEN upper(tes.cod_or1) like '%' || upper(_codOr1) || '%' ELSE true END                                                            
   AND   CASE WHEN _flagnascosto IS NOT NULL THEN ms.flg_nascosto = _flagnascosto ELSE (ms.flg_nascosto is null or ms.flg_nascosto = false) END                    
   AND   CASE WHEN (_classificazione_completezza <> '') IS TRUE THEN tes.classificazione_completezza = _classificazione_completezza ELSE true END
   AND   CASE WHEN (_cod_iuv <> '') IS TRUE THEN tes.codice_iuv = _cod_iuv ELSE true END
   AND   CASE WHEN (_cod_iuf <> '') IS TRUE THEN tes.identificativo_flusso_rendicontazione = _cod_iuf ELSE true END;
$BODY$
  LANGUAGE sql STABLE
  COST 100;


DROP INDEX idxu_mygov_anagrafica_uff_cap_acc;

CREATE INDEX idxu_mygov_anagrafica_uff_cap_acc_anno_eser
  ON mygov_anagrafica_uff_cap_acc
  USING btree
  (mygov_ente_id, cod_ufficio COLLATE pg_catalog."default", cod_capitolo COLLATE pg_catalog."default", cod_accertamento COLLATE pg_catalog."default", de_anno_esercizio COLLATE pg_catalog."default");


DROP FUNCTION get_count_pagamenti_inseribili_in_accertamento(bigint, character varying, character varying, character varying, character varying, date, date, date, date);

CREATE OR REPLACE FUNCTION get_count_pagamenti_inseribili_in_accertamento(_ente_id bigint, _cod_tipo_dovuto character varying, _codice_iud character varying, _codice_iuv character varying, _codice_identificativo_univoco_pagatore character varying, _data_esito_singolo_pagamento_da date, _data_esito_singolo_pagamento_a date, _data_ultimo_aggiornamento_da date, _data_ultimo_aggiornamento_a date)
  RETURNS bigint AS
$BODY$
 
  SELECT 
        count(*) 
  FROM 
      mygov_flusso_export AS p
  WHERE 
      /* Escludo le righe gia in accertamento */
      (p.cod_iud  || '-' || p.cod_rp_silinviarp_id_univoco_versamento) 
  NOT IN 
      (
        SELECT 
           ad.cod_iud || '-' || ad.cod_iuv 
        FROM 
           mygov_accertamento_dettaglio ad 
              INNER JOIN mygov_accertamento a ON ad.mygov_accertamento_id = a.mygov_accertamento_id
              INNER JOIN mygov_anagrafica_stato st ON a.mygov_anagrafica_stato_id = st.mygov_anagrafica_stato_id
        WHERE 
             st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato <> 'ANNULLATO' 
      ) AND
  
      /* Condizioni obbligatorie */
      p.mygov_ente_id = $1 AND p.cod_tipo_dovuto = $2 AND p.bilancio IS NULL AND
            
      /* IUD */
      CASE WHEN ($3 IS NOT NULL) THEN p.cod_iud = $3 ELSE true END AND
      /* IUV */
      CASE WHEN ($4 IS NOT NULL) THEN p.cod_rp_silinviarp_id_univoco_versamento = $4 ELSE true END AND
      /* Identificativo univoco pagatore */
      CASE WHEN ($5 IS NOT NULL) THEN p.cod_e_sogg_pag_id_univ_pag_codice_id_univoco = $5 ELSE true END AND
      /* Data esito pagamento da */
      CASE WHEN ($6 IS NOT NULL) THEN p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento >= $6 ELSE true END AND
      /* Data esito pagamento a */
      CASE WHEN ($7 IS NOT NULL) THEN p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento <= $7 ELSE true END AND
      /* Data ultimo aggiornamento da */
      CASE WHEN ($8 IS NOT NULL) THEN p.dt_ultima_modifica >= $8 ELSE true END AND
      /* Data ultimo aggiornamento a */
      CASE WHEN ($9 IS NOT NULL) THEN p.dt_ultima_modifica <= $9 ELSE true END;
   
$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
COMMENT ON FUNCTION get_count_pagamenti_inseribili_in_accertamento(bigint, character varying, character varying, character varying, character varying, date, date, date, date) IS 'Count dei Pagamenti inseribili in accertamento';

DROP FUNCTION get_count_pagamenti_inseriti_in_accertamento(bigint, bigint, character varying, character varying, character varying, character varying, date, date, date, date);

CREATE OR REPLACE FUNCTION get_count_pagamenti_inseriti_in_accertamento(_accertamento_id bigint, _ente_id bigint, _cod_tipo_dovuto character varying, _codice_iud character varying, _codice_iuv character varying, _codice_identificativo_univoco_pagatore character varying, _data_esito_singolo_pagamento_da date, _data_esito_singolo_pagamento_a date, _data_ultimo_aggiornamento_da date, _data_ultimo_aggiornamento_a date)
  RETURNS bigint AS
$BODY$
  SELECT 
       count(DISTINCT(a.cod_iud || '-' || a.cod_iuv))
  FROM 
      mygov_flusso_export AS p INNER JOIN mygov_accertamento_dettaglio AS a ON p.cod_iud = a.cod_iud AND p.cod_rp_silinviarp_id_univoco_versamento = a.cod_iuv
  WHERE
      /* Condizioni obbligatorie */
      a.mygov_accertamento_id = $1 AND p.mygov_ente_id = $2 AND p.cod_tipo_dovuto = $3 AND
            
      /* IUD */
      CASE WHEN ($4 IS NOT NULL) THEN p.cod_iud = $4 ELSE true END AND
      /* IUV */
      CASE WHEN ($5 IS NOT NULL) THEN p.cod_rp_silinviarp_id_univoco_versamento = $5 ELSE true END AND
      /* Identificativo univoco pagatore */
      CASE WHEN ($6 IS NOT NULL) THEN p.cod_e_sogg_pag_id_univ_pag_codice_id_univoco = $6 ELSE true END AND
      /* Data esito pagamento da */
      CASE WHEN ($7 IS NOT NULL) THEN p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento >= $7 ELSE true END AND
      /* Data esito pagamento a */
      CASE WHEN ($8 IS NOT NULL) THEN p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento <= $8 ELSE true END AND
      /* Data ultimo aggiornamento da */
      CASE WHEN ($9 IS NOT NULL) THEN p.dt_ultima_modifica >= $9 ELSE true END AND
      /* Data ultimo aggiornamento a */
      CASE WHEN ($10 IS NOT NULL) THEN p.dt_ultima_modifica <= $10 ELSE true END;
   
$BODY$
  LANGUAGE sql VOLATILE
  COST 100;
COMMENT ON FUNCTION get_count_pagamenti_inseriti_in_accertamento(bigint, bigint, character varying, character varying, character varying, character varying, date, date, date, date) IS 'Count dei pagamenti inseriti in accertamento';

DROP FUNCTION get_pagamenti_inseribili_in_accertamento(bigint, character varying, character varying, character varying, character varying, date, date, date, date, boolean, integer, integer);

CREATE OR REPLACE FUNCTION get_pagamenti_inseribili_in_accertamento(IN _ente_id bigint, IN _cod_tipo_dovuto character varying, IN _codice_iud character varying, IN _codice_iuv character varying, IN _codice_identificativo_univoco_pagatore character varying, IN _data_esito_singolo_pagamento_da date, IN _data_esito_singolo_pagamento_a date, IN _data_ultimo_aggiornamento_da date, IN _data_ultimo_aggiornamento_a date, IN _has_pagination boolean, IN _page integer, IN _page_size integer)
  RETURNS TABLE(cod_tipo_dovuto character varying, de_tipo_dovuto character varying, cod_iud character varying, cod_rp_silinviarp_id_univoco_versamento character varying, cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss character varying, de_e_istit_att_denominazione_attestante character varying, cod_e_istit_att_id_univ_att_codice_id_univoco character varying, cod_e_istit_att_id_univ_att_tipo_id_univoco character, cod_e_sogg_vers_anagrafica_versante character varying, cod_e_sogg_vers_id_univ_vers_codice_id_univoco character varying, cod_e_sogg_vers_id_univ_vers_tipo_id_univoco character, cod_e_sogg_pag_anagrafica_pagatore character varying, cod_e_sogg_pag_id_univ_pag_codice_id_univoco character varying, cod_e_sogg_pag_id_univ_pag_tipo_id_univoco character, dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento date, dt_ultima_modifica timestamp without time zone, num_e_dati_pag_dati_sing_pag_singolo_importo_pagato numeric, de_e_dati_pag_dati_sing_pag_causale_versamento character varying) AS
$BODY$
  SELECT 
        cod_tipo_dovuto, td.de_tipo as de_tipo_dovuto, cod_iud, cod_rp_silinviarp_id_univoco_versamento, cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss, de_e_istit_att_denominazione_attestante,
        cod_e_istit_att_id_univ_att_codice_id_univoco, cod_e_istit_att_id_univ_att_tipo_id_univoco, cod_e_sogg_vers_anagrafica_versante, 
        cod_e_sogg_vers_id_univ_vers_codice_id_univoco, cod_e_sogg_vers_id_univ_vers_tipo_id_univoco, cod_e_sogg_pag_anagrafica_pagatore, 
        cod_e_sogg_pag_id_univ_pag_codice_id_univoco, cod_e_sogg_pag_id_univ_pag_tipo_id_univoco, dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento,
        dt_ultima_modifica, num_e_dati_pag_dati_sing_pag_singolo_importo_pagato, de_e_dati_pag_dati_sing_pag_causale_versamento
  FROM 
      mygov_flusso_export AS p INNER JOIN mygov_ente_tipo_dovuto AS td ON p.cod_tipo_dovuto = td.cod_tipo AND td.mygov_ente_id = p.mygov_ente_id
  WHERE
      /* Escludo le righe gia in accertamento */
      (p.cod_iud  || '-' || p.cod_rp_silinviarp_id_univoco_versamento) 
  NOT IN 
      (
        SELECT 
           ad.cod_iud || '-' || ad.cod_iuv 
        FROM 
           mygov_accertamento_dettaglio ad 
              INNER JOIN mygov_accertamento a ON ad.mygov_accertamento_id = a.mygov_accertamento_id
              INNER JOIN mygov_anagrafica_stato st ON a.mygov_anagrafica_stato_id = st.mygov_anagrafica_stato_id
        WHERE 
             st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato <> 'ANNULLATO' 
      ) AND
  
      /* Condizioni obbligatorie */
      p.mygov_ente_id = $1 AND p.cod_tipo_dovuto = $2 AND p.bilancio IS NULL AND
            
      /* IUD */
      CASE WHEN ($3 IS NOT NULL) THEN p.cod_iud = $3 ELSE true END AND
      /* IUV */
      CASE WHEN ($4 IS NOT NULL) THEN p.cod_rp_silinviarp_id_univoco_versamento = $4 ELSE true END AND
      /* Identificativo univoco pagatore */
      CASE WHEN ($5 IS NOT NULL) THEN p.cod_e_sogg_pag_id_univ_pag_codice_id_univoco = $5 ELSE true END AND
      /* Data esito pagamento da */
      CASE WHEN ($6 IS NOT NULL) THEN p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento >= $6 ELSE true END AND
      /* Data esito pagamento a */
      CASE WHEN ($7 IS NOT NULL) THEN p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento <= $7 ELSE true END AND
      /* Data ultimo aggiornamento da */
      CASE WHEN ($8 IS NOT NULL) THEN p.dt_ultima_modifica >= $8 ELSE true END AND
      /* Data ultimo aggiornamento a */
      CASE WHEN ($9 IS NOT NULL) THEN p.dt_ultima_modifica <= $9 ELSE true END

   ORDER BY dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento ASC, cod_rp_silinviarp_id_univoco_versamento ASC, cod_iud ASC

   OFFSET CASE WHEN ($10) THEN $11 ELSE 0 END 

   LIMIT CASE WHEN ($10) THEN $12 ELSE null END;
   
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
COMMENT ON FUNCTION get_pagamenti_inseribili_in_accertamento(bigint, character varying, character varying, character varying, character varying, date, date, date, date, boolean, integer, integer) IS 'Pagamenti inseribili in accertamento';

DROP FUNCTION get_pagamenti_inseriti_in_accertamento(bigint, bigint, character varying, character varying, character varying, character varying, date, date, date, date, boolean, integer, integer);

CREATE OR REPLACE FUNCTION get_pagamenti_inseriti_in_accertamento(IN _accertamento_id bigint, IN _ente_id bigint, IN _cod_tipo_dovuto character varying, IN _codice_iud character varying, IN _codice_iuv character varying, IN _codice_identificativo_univoco_pagatore character varying, IN _data_esito_singolo_pagamento_da date, IN _data_esito_singolo_pagamento_a date, IN _data_ultimo_aggiornamento_da date, IN _data_ultimo_aggiornamento_a date, IN _has_pagination boolean, IN _page integer, IN _page_size integer)
  RETURNS TABLE(mygov_accertamento_dettaglio_id_acc bigint, mygov_accertamento_id_acc bigint, cod_ipa_ente_acc character varying, cod_tipo_dovuto_acc character varying, cod_iud_acc character varying, cod_iuv_acc character varying, dt_ultima_modifica_acc timestamp without time zone, dt_data_inserimento_acc timestamp without time zone, cod_tipo_dovuto character varying, de_tipo_dovuto character varying, cod_iud character varying, cod_rp_silinviarp_id_univoco_versamento character varying, cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss character varying, de_e_istit_att_denominazione_attestante character varying, cod_e_istit_att_id_univ_att_codice_id_univoco character varying, cod_e_istit_att_id_univ_att_tipo_id_univoco character, cod_e_sogg_vers_anagrafica_versante character varying, cod_e_sogg_vers_id_univ_vers_codice_id_univoco character varying, cod_e_sogg_vers_id_univ_vers_tipo_id_univoco character, cod_e_sogg_pag_anagrafica_pagatore character varying, cod_e_sogg_pag_id_univ_pag_codice_id_univoco character varying, cod_e_sogg_pag_id_univ_pag_tipo_id_univoco character, dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento date, dt_ultima_modifica timestamp without time zone, num_e_dati_pag_dati_sing_pag_singolo_importo_pagato numeric, de_e_dati_pag_dati_sing_pag_causale_versamento character varying) AS
$BODY$
    SELECT
	/* Accertamento dettaglio */
	mygov_accertamento_dettaglio_id_acc, 
	mygov_accertamento_id_acc, cod_ipa_ente_acc, 
	cod_tipo_dovuto_acc, 
	cod_iud_acc, 
	cod_iuv_acc,
	dt_ultima_modifica_acc, 
	dt_data_inserimento_acc,

	/* Flusso export */
	cod_tipo_dovuto, de_tipo_dovuto, cod_iud, cod_rp_silinviarp_id_univoco_versamento,        cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss, 
	de_e_istit_att_denominazione_attestante,  cod_e_istit_att_id_univ_att_codice_id_univoco,  cod_e_istit_att_id_univ_att_tipo_id_univoco, 
	cod_e_sogg_vers_anagrafica_versante,      cod_e_sogg_vers_id_univ_vers_codice_id_univoco, cod_e_sogg_vers_id_univ_vers_tipo_id_univoco, 
	cod_e_sogg_pag_anagrafica_pagatore,       cod_e_sogg_pag_id_univ_pag_codice_id_univoco,   cod_e_sogg_pag_id_univ_pag_tipo_id_univoco, 
	dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento, dt_ultima_modifica,             num_e_dati_pag_dati_sing_pag_singolo_importo_pagato, 
	de_e_dati_pag_dati_sing_pag_causale_versamento
    FROM
	(
	  SELECT
	      DISTINCT ON(a.cod_iud || '-' || a.cod_iuv) cod_iuv,
	      /* Accertamento dettaglio */
	      a.mygov_accertamento_dettaglio_id AS mygov_accertamento_dettaglio_id_acc, a.mygov_accertamento_id AS mygov_accertamento_id_acc, 
	      a.cod_ipa_ente AS cod_ipa_ente_acc, a.cod_tipo_dovuto AS cod_tipo_dovuto_acc, a.cod_iud AS cod_iud_acc, a.cod_iuv AS cod_iuv_acc,
	      a.dt_ultima_modifica AS dt_ultima_modifica_acc, a.dt_data_inserimento AS dt_data_inserimento_acc,

	      /* Flusso export */
	      p.cod_tipo_dovuto, td.de_tipo as de_tipo_dovuto, p.cod_iud, p.cod_rp_silinviarp_id_univoco_versamento, p.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss, 
	      p.de_e_istit_att_denominazione_attestante, p.cod_e_istit_att_id_univ_att_codice_id_univoco, p.cod_e_istit_att_id_univ_att_tipo_id_univoco, 
	      p.cod_e_sogg_vers_anagrafica_versante, p.cod_e_sogg_vers_id_univ_vers_codice_id_univoco, p.cod_e_sogg_vers_id_univ_vers_tipo_id_univoco, 
	      p.cod_e_sogg_pag_anagrafica_pagatore, p.cod_e_sogg_pag_id_univ_pag_codice_id_univoco, p.cod_e_sogg_pag_id_univ_pag_tipo_id_univoco, 
	      p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento, p.dt_ultima_modifica, p.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato, 
	      p.de_e_dati_pag_dati_sing_pag_causale_versamento  
	  FROM 
	     mygov_flusso_export AS p 
		INNER JOIN mygov_accertamento_dettaglio AS a ON p.cod_iud = a.cod_iud AND p.cod_rp_silinviarp_id_univoco_versamento = a.cod_iuv
		INNER JOIN mygov_ente_tipo_dovuto AS td ON p.cod_tipo_dovuto = td.cod_tipo AND td.mygov_ente_id = p.mygov_ente_id
	  WHERE
	      /* Condizioni obbligatorie */
	      a.mygov_accertamento_id = $1 AND p.mygov_ente_id = $2 AND p.cod_tipo_dovuto = $3 AND
		    
	      /* IUD */
	      CASE WHEN ($4 IS NOT NULL) THEN p.cod_iud = $4 ELSE true END AND
	      /* IUV */
	      CASE WHEN ($5 IS NOT NULL) THEN p.cod_rp_silinviarp_id_univoco_versamento = $5 ELSE true END AND
	      /* Identificativo univoco pagatore */
	      CASE WHEN ($6 IS NOT NULL) THEN p.cod_e_sogg_pag_id_univ_pag_codice_id_univoco = $6 ELSE true END AND
	      /* Data esito pagamento da */
	      CASE WHEN ($7 IS NOT NULL) THEN p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento >= $7 ELSE true END AND
	      /* Data esito pagamento a */
	      CASE WHEN ($8 IS NOT NULL) THEN p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento <= $8 ELSE true END AND
	      /* Data ultimo aggiornamento da */
	      CASE WHEN ($9 IS NOT NULL) THEN p.dt_ultima_modifica >= $9 ELSE true END AND
	      /* Data ultimo aggiornamento a */
	      CASE WHEN ($10 IS NOT NULL) THEN p.dt_ultima_modifica <= $10 ELSE true END
	 
	   OFFSET CASE WHEN ($11) THEN $12 ELSE 0 END 

	   LIMIT CASE WHEN ($11) THEN $13 ELSE null END
	) as subq
    ORDER BY dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento ASC, cod_rp_silinviarp_id_univoco_versamento ASC, cod_iud ASC;

   
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;
COMMENT ON FUNCTION get_pagamenti_inseriti_in_accertamento(bigint, bigint, character varying, character varying, character varying, character varying, date, date, date, date, boolean, integer, integer) IS 'Pagamenti inseriti in accertamento';
