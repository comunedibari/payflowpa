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

CREATE SEQUENCE mygov_flusso_tesoreria_mygov_flusso_tesoreria_id_seq
  START WITH 1
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE TABLE mygov_flusso_tesoreria (
	mygov_flusso_tesoreria_id BIGINT NOT NULL,
	de_anno_bolletta CHARACTER(4) NOT NULL,
	cod_bolletta CHARACTER(7) NOT NULL,
	cod_conto CHARACTER(7) NOT NULL,
	cod_id_dominio CHARACTER(7) NOT NULL,
	cod_tipo_movimento CHARACTER(3) NOT NULL,
	cod_causale CHARACTER(3) NOT NULL,
	de_causale CHARACTER VARYING(2000) NOT NULL,
	num_ip_bolletta NUMERIC(17,2) NOT NULL,
	dt_bolletta TIMESTAMP WITHOUT TIME ZONE,
	dt_ricezione TIMESTAMP WITHOUT TIME ZONE,
	de_anno_documento CHARACTER(4) NOT NULL,
	cod_documento CHARACTER(7) NOT NULL,
	cod_bollo CHARACTER(6) NOT NULL,
	de_cognome CHARACTER(30) NOT NULL,
	de_nome CHARACTER(30) NOT NULL,
	de_via CHARACTER VARYING(50) NOT NULL,
	de_cap CHARACTER VARYING(5) NOT NULL,
	de_citta CHARACTER VARYING(40) NOT NULL,
	cod_codice_fiscale CHARACTER(16) NOT NULL,
	cod_partita_iva CHARACTER(12) NOT NULL, 
	cod_abi CHARACTER(5) NOT NULL,
	cod_cab CHARACTER(5) NOT NULL,
	cod_conto_anagrafica CHARACTER(25) NOT NULL,
	de_ae_provvisorio CHARACTER(4),
	cod_provvisorio CHARACTER(6),
	cod_iban CHARACTER VARYING(34),
	cod_tipo_conto CHARACTER(1),
	cod_processo CHARACTER(10),
	cod_pg_esecuzione CHARACTER(4),
	cod_pg_trasferimento CHARACTER(4),
	num_pg_processo NUMERIC(17,0),
	dt_data_valuta_regione TIMESTAMP WITHOUT TIME ZONE,
	mygov_ente_id BIGINT NOT NULL,
	cod_id_univoco_flusso CHARACTER VARYING(35),
	cod_id_univoco_versamento CHARACTER VARYING(35),
	dt_creazione TIMESTAMP WITHOUT TIME ZONE,
	dt_ultima_modifica TIMESTAMP WITHOUT TIME ZONE,
	CONSTRAINT mygov_flusso_tesoreria_pkey PRIMARY KEY (mygov_flusso_tesoreria_id),
	CONSTRAINT mygov_flusso_tesoreria_ukey UNIQUE (de_anno_bolletta, cod_bolletta),
	CONSTRAINT mygov_flusso_tesoreria_mygov_ente_fkey FOREIGN KEY (mygov_ente_id)
    	REFERENCES mygov_ente (mygov_ente_id) MATCH SIMPLE
    	ON UPDATE NO ACTION ON DELETE NO ACTION
);

DROP VIEW v_mygov_export_rendicontazione_completa;
DROP VIEW v_mygov_import_export_rendicontazione_tesoreria;
DROP VIEW v_mygov_import_export_rendicontazione_tesoreria_completa;

DROP INDEX idx_mygovimpexprendtes_tipo_dovuto;
DROP INDEX idx_mygovimpexprendtes_importo_totale_pagamenti;
DROP INDEX idx_mygovimpexprendtes_identificativo_univoco_versamento;
DROP INDEX idx_mygovimpexprendtes_identificativo_univoco_riscossione;
DROP INDEX idx_mygovimpexprendtes_identificativo_univoco_regolamento;
DROP INDEX idx_mygovimpexprendtes_identificativo_flusso_rendicontazione;
DROP INDEX idx_mygovimpexprendtes_dt_data_valuta;
DROP INDEX idx_mygovimpexprendtes_dt_data_regolamento;
DROP INDEX idx_mygovimpexprendtes_dt_data_esito_singolo_pagamento;
DROP INDEX idx_mygovimpexprendtes_dt_data_contabile;
DROP INDEX idx_mygovimpexprendtes_de_importo;
DROP INDEX idx_mygovimpexprendtes_codice_iud;
DROP INDEX idx_mygovimpexprendtes_classificazione_completezza;
DROP INDEX idx_mygovimpexprendtes_causale_versamento;

DROP TABLE mygov_import_export_rendicontazione_tesoreria;

DROP INDEX idx_mygovimpexprendtescompl_tipo_dovuto;
DROP INDEX idx_mygovimpexprendtescompl_data_aggiornamento;
DROP TABLE mygov_import_export_rendicontazione_tesoreria_completa;

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
  de_anno_bolletta_t CHARACTER VARYING(4),
  cod_bolletta_t CHARACTER VARYING(7),
  cod_id_dominio_t CHARACTER VARYING(7),
  dt_ricezione_t TIMESTAMP WITHOUT TIME ZONE,
  de_anno_documento_t CHARACTER VARYING(4),
  cod_documento_t CHARACTER VARYING(7),
  de_anno_provvisorio_t CHARACTER VARYING(4),
  cod_provvisorio_t CHARACTER VARYING(6),
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
  de_anno_bolletta CHARACTER VARYING(4),
  cod_bolletta CHARACTER VARYING(7),
  cod_id_dominio CHARACTER VARYING(7),
  dt_ricezione TIMESTAMP WITHOUT TIME ZONE,
  de_data_ricezione CHARACTER VARYING(10),
  de_anno_documento CHARACTER VARYING(4),
  cod_documento CHARACTER VARYING(7),
  de_anno_provvisorio CHARACTER VARYING(4),
  cod_provvisorio CHARACTER VARYING(6),
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

CREATE OR replace VIEW v_mygov_import_export_rendicontazione_tesoreria_completa                                                                                                                                                                                                        AS SELECT    COALESCE(import.mygov_ente_id, export.mygov_ente_id, rendicontazione.mygov_ente_id, tesoreria_iuf.mygov_ente_id, tesoreria_iuv.mygov_ente_id, tesoreria_f2k_iuf.mygov_ente_id, tesoreria_f2k_iuv.mygov_ente_id, tesoreria_f2k.mygov_ente_id)                                                                                                                                         AS mygov_ente_id,
          COALESCE(import.cod_rp_silinviarp_id_univoco_versamento, export.cod_rp_silinviarp_id_univoco_versamento, rendicontazione.cod_dati_sing_pagam_identificativo_univoco_versamento, tesoreria_iuv.cod_id_univoco_versamento, tesoreria_f2k_iuv.cod_id_univoco_versamento, 'n/a'::character VARYING)::character VARYING(35) AS codice_iuv,
          COALESCE(export.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss, rendicontazione.cod_dati_sing_pagam_identificativo_univoco_riscossione, 'n/a'::character VARYING)::character VARYING(35)                                                                                                                      AS identificativo_univoco_riscossione,
          COALESCE(rendicontazione.cod_identificativo_flusso, tesoreria_iuf.cod_id_univoco_flusso, tesoreria_f2k_iuf.cod_id_univoco_flusso, 'n/a'::character VARYING)::character VARYING(35)                                                                                                                                     AS identificativo_flusso_rendicontazione,
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
          COALESCE(tesoreria_iuf.dt_data_contabile, tesoreria_iuv.dt_data_contabile, tesoreria_f2k_iuf.dt_bolletta, tesoreria_f2k_iuv.dt_bolletta, tesoreria_f2k.dt_bolletta)                                                                                                                                                                                                        AS dt_data_contabile_t,
          COALESCE(tesoreria_iuf.dt_data_valuta, tesoreria_iuv.dt_data_valuta, tesoreria_f2k_iuf.dt_data_valuta_regione, tesoreria_f2k_iuv.dt_data_valuta_regione, tesoreria_f2k.dt_data_valuta_regione)                                                                                                                                                                                                        AS dt_data_valuta_t,
          COALESCE(tesoreria_iuf.num_importo, tesoreria_iuv.num_importo, tesoreria_f2k_iuf.num_ip_bolletta, tesoreria_f2k_iuv.num_ip_bolletta, tesoreria_f2k.num_ip_bolletta)                                                                                                                                                                                                        AS num_importo_t,
          COALESCE(tesoreria_iuf.cod_segno, tesoreria_iuv.cod_segno)                                                                                                                                                                                                        AS cod_segno_t,
          COALESCE(tesoreria_iuf.de_causale, tesoreria_iuv.de_causale, tesoreria_f2k_iuf.de_causale, tesoreria_f2k_iuv.de_causale, tesoreria_f2k.de_causale)                                                                                                                                                                                                        AS de_causale_t,
          COALESCE(tesoreria_iuf.cod_numero_assegno, tesoreria_iuv.cod_numero_assegno)                                                                                                                                                                                                        AS cod_numero_assegno_t,
          COALESCE(tesoreria_iuf.cod_riferimento_banca, tesoreria_iuv.cod_riferimento_banca)                                                                                                                                                                                                        AS cod_riferimento_banca_t,
          COALESCE(tesoreria_iuf.cod_riferimento_cliente, tesoreria_iuv.cod_riferimento_cliente)                                                                                                                                                                                                        AS cod_riferimento_cliente_t,
          COALESCE(tesoreria_iuf.dt_data_ordine, tesoreria_iuv.dt_data_ordine)                                                                                                                                                                                                        AS dt_data_ordine_t,
          COALESCE(tesoreria_iuf.de_descrizione_ordinante, tesoreria_iuv.de_descrizione_ordinante, tesoreria_f2k_iuf.de_cognome, tesoreria_f2k_iuv.de_cognome, tesoreria_f2k.de_cognome)                                                                                                                                                                                                 AS de_descrizione_ordinante_t,
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
          COALESCE(tesoreria_iuf.cod_or1, tesoreria_iuv.cod_or1, tesoreria_f2k_iuf.de_cognome, tesoreria_f2k_iuv.de_cognome, tesoreria_f2k.de_cognome)                                                                                                                                                                                                        AS cod_or1_t,
          COALESCE(tesoreria_iuf.cod_sc2, tesoreria_iuv.cod_sc2)                                                                                                                                                                                                        AS cod_sc2_t,
          COALESCE(tesoreria_iuf.cod_tr1, tesoreria_iuv.cod_tr1)                                                                                                                                                                                                        AS cod_tr1_t,
          COALESCE(tesoreria_iuf.cod_sec, tesoreria_iuv.cod_sec)                                                                                                                                                                                                        AS cod_sec_t,
          COALESCE(tesoreria_iuf.cod_ior, tesoreria_iuv.cod_ior)                                                                                                                                                                                                        AS cod_ior_t,
          COALESCE(tesoreria_iuf.cod_id_univoco_flusso, tesoreria_f2k_iuf.cod_id_univoco_flusso, tesoreria_f2k_iuv.cod_id_univoco_flusso, tesoreria_f2k.cod_id_univoco_flusso)                                                                                                                                                                                                        AS cod_id_univoco_flusso_t,
          COALESCE(tesoreria_iuv.cod_id_univoco_versamento, tesoreria_f2k_iuf.cod_id_univoco_versamento, tesoreria_f2k_iuv.cod_id_univoco_versamento, tesoreria_f2k.cod_id_univoco_versamento)                                                                                                                                                                                                        AS cod_id_univoco_versamento_t,
          COALESCE(tesoreria_iuf.dt_acquisizione, tesoreria_iuv.dt_acquisizione, tesoreria_f2k_iuf.dt_ultima_modifica, tesoreria_f2k_iuv.dt_ultima_modifica, tesoreria_f2k.dt_ultima_modifica)                                                                                                                                                                                                        AS dt_acquisizione_t,
          COALESCE(tesoreria_f2k_iuf.de_anno_bolletta, tesoreria_f2k_iuv.de_anno_bolletta, tesoreria_f2k.de_anno_bolletta)                                                                                                                                                                                                        AS de_anno_bolletta_t,
          COALESCE(tesoreria_f2k_iuf.cod_bolletta, tesoreria_f2k_iuv.cod_bolletta, tesoreria_f2k.cod_bolletta)                                                                                                                                                                                                        AS cod_bolletta_t,
          COALESCE(tesoreria_f2k_iuf.cod_id_dominio, tesoreria_f2k_iuv.cod_id_dominio, tesoreria_f2k.cod_id_dominio)                                                                                                                                                                                                        AS cod_id_dominio_t,
          COALESCE(tesoreria_f2k_iuf.dt_ricezione, tesoreria_f2k_iuv.dt_ricezione, tesoreria_f2k.dt_ricezione)                                                                                                                                                                                                        AS dt_ricezione_t,
          COALESCE(tesoreria_f2k_iuf.de_anno_documento, tesoreria_f2k_iuv.de_anno_documento, tesoreria_f2k.de_anno_documento)                                                                                                                                                                                                        AS de_anno_documento_t,
          COALESCE(tesoreria_f2k_iuf.cod_documento, tesoreria_f2k_iuv.cod_documento, tesoreria_f2k.cod_documento)                                                                                                                                                                                                        AS cod_documento_t,
          COALESCE(tesoreria_f2k_iuf.de_ae_provvisorio, tesoreria_f2k_iuv.de_ae_provvisorio, tesoreria_f2k.de_ae_provvisorio)                                                                                                                                                                                                        AS de_anno_provvisorio_t,
          COALESCE(tesoreria_f2k_iuf.cod_provvisorio, tesoreria_f2k_iuv.cod_provvisorio, tesoreria_f2k.cod_provvisorio)                                                                                                                                                                                                        AS cod_provvisorio_t,
          classificazione.mygov_classificazione_codice                                                                                                                                                                                                        AS classificazione_completezza,
          greatest(import.dt_acquisizione, export.dt_acquisizione, rendicontazione.dt_acquisizione, COALESCE(tesoreria_iuf.dt_acquisizione, tesoreria_iuv.dt_acquisizione, tesoreria_f2k_iuf.dt_ultima_modifica, tesoreria_f2k_iuv.dt_ultima_modifica, tesoreria_f2k.dt_ultima_modifica))                                                                                                                AS dt_data_ultimo_aggiornamento
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
                 AND    mygov_flusso_tesoreria.cod_id_univoco_versamento IS NULL
               ) tesoreria_f2k_iuf
ON        COALESCE(export.mygov_ente_id, rendicontazione.mygov_ente_id) = tesoreria_f2k_iuf.mygov_ente_id
AND       rendicontazione.cod_identificativo_flusso = tesoreria_f2k_iuf.cod_id_univoco_flusso
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
                 AND    mygov_flusso_tesoreria.cod_id_univoco_versamento IS NOT NULL
               ) tesoreria_f2k_iuv
ON        COALESCE(export.mygov_ente_id, rendicontazione.mygov_ente_id) = tesoreria_f2k_iuv.mygov_ente_id
AND       COALESCE(export.cod_rp_silinviarp_id_univoco_versamento, rendicontazione.cod_dati_sing_pagam_identificativo_univoco_versamento)::text = tesoreria_f2k_iuv.cod_id_univoco_versamento
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
                 AND    mygov_flusso_tesoreria.cod_id_univoco_versamento IS NULL
               ) tesoreria_f2k
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
                    AND       rendicontazione.num_importo_totale_pagamenti = tesoreria_f2k_iuf.num_ip_bolletta
                    )
          OR        (
                              tesoreria_iuv.num_importo IS NOT NULL
                    AND       export.num_e_dati_pag_importo_totale_pagato = tesoreria_iuv.num_importo
                    OR        tesoreria_f2k_iuv.num_ip_bolletta IS NOT NULL
                    AND       export.num_e_dati_pag_importo_totale_pagato = tesoreria_f2k_iuv.num_ip_bolletta
                    )
          )   
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
                    AND       rendicontazione.num_importo_totale_pagamenti = tesoreria_f2k_iuf.num_ip_bolletta
                    )
          OR        (
                              tesoreria_iuv.num_importo IS NOT NULL
                    AND       export.num_e_dati_pag_importo_totale_pagato = tesoreria_iuv.num_importo
                    OR        tesoreria_f2k_iuv.num_ip_bolletta IS NOT NULL
                    AND       export.num_e_dati_pag_importo_totale_pagato = tesoreria_f2k_iuv.num_ip_bolletta
                    )
          )
OR        classificazione.mygov_classificazione_codice::text = 'RT_IUF'::text
AND       export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL
AND       rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NOT NULL
AND       export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato = rendicontazione.num_dati_sing_pagam_singolo_importo_pagato
OR        classificazione.mygov_classificazione_codice::text = 'RT_NO_IUF'::text
AND       export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL
AND       (
                    rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NULL
          OR        rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NOT NULL
          AND       export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato <> rendicontazione.num_dati_sing_pagam_singolo_importo_pagato)
OR        classificazione.mygov_classificazione_codice::text = 'IUF_NO_TES'::text
AND       rendicontazione.num_importo_totale_pagamenti IS NOT NULL
AND       (
                    tesoreria_iuf.num_importo IS NULL
          AND       tesoreria_iuv.num_importo IS NULL
          AND       tesoreria_f2k_iuf.num_ip_bolletta IS NULL
          AND       tesoreria_f2k_iuv.num_ip_bolletta IS NULL
          OR        tesoreria_iuf.num_importo IS NOT NULL
          AND       rendicontazione.num_importo_totale_pagamenti <> tesoreria_iuf.num_importo
          OR        tesoreria_iuv.num_importo IS NOT NULL
          AND       rendicontazione.num_importo_totale_pagamenti <> tesoreria_iuv.num_importo
          OR        tesoreria_f2k_iuf.num_ip_bolletta IS NOT NULL
          AND       rendicontazione.num_importo_totale_pagamenti <> tesoreria_f2k_iuf.num_ip_bolletta
          OR        tesoreria_f2k_iuv.num_ip_bolletta IS NOT NULL
          AND       rendicontazione.num_importo_totale_pagamenti <> tesoreria_f2k_iuv.num_ip_bolletta
          )
OR        classificazione.mygov_classificazione_codice::text = 'TES_NO_IUF_OR_IUV'::text
AND       (
                    tesoreria_iuf.num_importo IS NOT NULL
          OR        tesoreria_iuv.num_importo IS NOT NULL
          OR        tesoreria_f2k_iuf.num_ip_bolletta IS NOT NULL
          AND       tesoreria_f2k_iuf.cod_id_univoco_flusso IS NOT NULL
          AND       tesoreria_f2k_iuf.cod_id_univoco_versamento IS NULL
          OR        tesoreria_f2k_iuv.num_ip_bolletta IS NOT NULL
          AND       tesoreria_f2k_iuv.cod_id_univoco_flusso IS NULL
          AND       tesoreria_f2k_iuv.cod_id_univoco_versamento IS NOT NULL
          )
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
AND       tesoreria_f2k.cod_id_univoco_versamento IS NULL;

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
    COALESCE(import_export_rendicontazione_tesoreria.de_anno_bolletta_t, 'n/a'::text) AS de_anno_bolletta,
    COALESCE(import_export_rendicontazione_tesoreria.cod_bolletta_t, 'n/a'::text) as cod_bolletta,
    COALESCE(import_export_rendicontazione_tesoreria.cod_id_dominio_t, 'n/a'::text) AS cod_id_dominio,
    import_export_rendicontazione_tesoreria.dt_ricezione_t AS dt_ricezione,
    COALESCE(to_char(import_export_rendicontazione_tesoreria.dt_ricezione_t::timestamp with time zone, 'dd-MM-yyyy'::text), 'n/a'::character varying::text)::character varying(10) AS de_data_ricezione,
    COALESCE(import_export_rendicontazione_tesoreria.de_anno_documento_t, 'n/a'::text) AS de_anno_documento,
    COALESCE(import_export_rendicontazione_tesoreria.cod_documento_t, 'n/a'::text) AS cod_documento,
    COALESCE(import_export_rendicontazione_tesoreria.de_anno_provvisorio_t, 'n/a'::text) AS de_anno_provvisorio,
    COALESCE(import_export_rendicontazione_tesoreria.cod_provvisorio_t, 'n/a'::text) AS cod_provvisorio,
        CASE
            WHEN import_export_rendicontazione_tesoreria.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento_i = import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e OR (sum(import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r) OVER (PARTITION BY import_export_rendicontazione_tesoreria.cod_identificativo_flusso_r) - import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r) = 0::numeric AND import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e = import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r AND import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r = import_export_rendicontazione_tesoreria.num_importo_t THEN 'OK'::character varying(3)
            WHEN import_export_rendicontazione_tesoreria.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento_i <> import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e OR (sum(import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r) OVER (PARTITION BY import_export_rendicontazione_tesoreria.cod_identificativo_flusso_r) - import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r) <> 0::numeric OR import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e <> import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r OR import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r <> import_export_rendicontazione_tesoreria.num_importo_t THEN 'KO'::character varying(3)
            ELSE 'n/a'::character varying(3)
        END AS verifica_totale,
    COALESCE(import_export_rendicontazione_tesoreria.classificazione_completezza, 'OTHERS'::character varying)::character varying(20) AS classificazione_completezza,
    import_export_rendicontazione_tesoreria.dt_data_ultimo_aggiornamento,
    to_char(import_export_rendicontazione_tesoreria.dt_data_ultimo_aggiornamento::timestamp with time zone, 'dd-MM-yyyy'::text)::character varying(10) AS de_data_ultimo_aggiornamento,
    COALESCE(import_export_rendicontazione_tesoreria.indice_dati_singolo_pagamento_e, import_export_rendicontazione_tesoreria.indice_dati_singolo_pagamento_r) as indice_dati_singolo_pagamento,
    COALESCE(import_export_rendicontazione_tesoreria.cod_identificativo_flusso_r, import_export_rendicontazione_tesoreria.cod_id_univoco_flusso_t) AS cod_iuf_key,
	COALESCE(import_export_rendicontazione_tesoreria.cod_iud_i, import_export_rendicontazione_tesoreria.cod_iud_e) AS cod_iud_key,
	COALESCE(import_export_rendicontazione_tesoreria.cod_rp_silinviarp_id_univoco_versamento_i, import_export_rendicontazione_tesoreria.cod_rp_silinviarp_id_univoco_versamento_e, import_export_rendicontazione_tesoreria.cod_dati_sing_pagam_identificativo_univoco_versamento_r, import_export_rendicontazione_tesoreria.cod_id_univoco_versamento_t) AS cod_iuv_key
   FROM mygov_import_export_rendicontazione_tesoreria_completa import_export_rendicontazione_tesoreria,
    mygov_ente ente
  WHERE COALESCE(import_export_rendicontazione_tesoreria.mygov_ente_id_i, import_export_rendicontazione_tesoreria.mygov_ente_id_e, import_export_rendicontazione_tesoreria.mygov_ente_id_r, import_export_rendicontazione_tesoreria.mygov_ente_id_t) = ente.mygov_ente_id;

CREATE OR replace VIEW v_mygov_export_rendicontazione_completa
AS
  SELECT export.mygov_ente_id                                                      AS mygov_ente_id_e,
         export.mygov_manage_flusso_id                                             AS mygov_manage_flusso_id_e,
         export.de_nome_flusso                                                     AS de_nome_flusso_e,
         export.num_riga_flusso                                                    AS num_riga_flusso_e,
         export.cod_iud                                                            AS cod_iud_e,
         export.cod_rp_silinviarp_id_univoco_versamento                            AS cod_rp_silinviarp_id_univoco_versamento_e,
         export.de_e_versione_oggetto                                              AS de_e_versione_oggetto_e,
         export.cod_e_dom_id_dominio                                               AS cod_e_dom_id_dominio_e,
         export.cod_e_dom_id_stazione_richiedente                                  AS cod_e_dom_id_stazione_richiedente_e,
         export.cod_e_id_messaggio_ricevuta                                        AS cod_e_id_messaggio_ricevuta_e,
         export.dt_e_data_ora_messaggio_ricevuta                                   AS dt_e_data_ora_messaggio_ricevuta_e,
         export.cod_e_riferimento_messaggio_richiesta                              AS cod_e_riferimento_messaggio_richiesta_e,
         export.dt_e_riferimento_data_richiesta                                    AS dt_e_riferimento_data_richiesta_e,
         export.cod_e_istit_att_id_univ_att_tipo_id_univoco                        AS cod_e_istit_att_id_univ_att_tipo_id_univoco_e,
         export.cod_e_istit_att_id_univ_att_codice_id_univoco                      AS cod_e_istit_att_id_univ_att_codice_id_univoco_e,
         export.de_e_istit_att_denominazione_attestante                            AS de_e_istit_att_denominazione_attestante_e,
         export.cod_e_istit_att_codice_unit_oper_attestante                        AS cod_e_istit_att_codice_unit_oper_attestante_e,
         export.de_e_istit_att_denom_unit_oper_attestante                          AS de_e_istit_att_denom_unit_oper_attestante_e,
         export.de_e_istit_att_indirizzo_attestante                                AS de_e_istit_att_indirizzo_attestante_e,
         export.de_e_istit_att_civico_attestante                                   AS de_e_istit_att_civico_attestante_e,
         export.cod_e_istit_att_cap_attestante                                     AS cod_e_istit_att_cap_attestante_e,
         export.de_e_istit_att_localita_attestante                                 AS de_e_istit_att_localita_attestante_e,
         export.de_e_istit_att_provincia_attestante                                AS de_e_istit_att_provincia_attestante_e,
         export.cod_e_istit_att_nazione_attestante                                 AS cod_e_istit_att_nazione_attestante_e,
         export.cod_e_ente_benef_id_univ_benef_tipo_id_univoco                     AS cod_e_ente_benef_id_univ_benef_tipo_id_univoco_e,
         export.cod_e_ente_benef_id_univ_benef_codice_id_univoco                   AS cod_e_ente_benef_id_univ_benef_codice_id_univoco_e,
         export.de_e_ente_benef_denominazione_beneficiario                         AS de_e_ente_benef_denominazione_beneficiario_e,
         export.cod_e_ente_benef_codice_unit_oper_beneficiario                     AS cod_e_ente_benef_codice_unit_oper_beneficiario_e,
         export.de_e_ente_benef_denom_unit_oper_beneficiario                       AS de_e_ente_benef_denom_unit_oper_beneficiario_e,
         export.de_e_ente_benef_indirizzo_beneficiario                             AS de_e_ente_benef_indirizzo_beneficiario_e,
         export.de_e_ente_benef_civico_beneficiario                                AS de_e_ente_benef_civico_beneficiario_e,
         export.cod_e_ente_benef_cap_beneficiario                                  AS cod_e_ente_benef_cap_beneficiario_e,
         export.de_e_ente_benef_localita_beneficiario                              AS de_e_ente_benef_localita_beneficiario_e,
         export.de_e_ente_benef_provincia_beneficiario                             AS de_e_ente_benef_provincia_beneficiario_e,
         export.cod_e_ente_benef_nazione_beneficiario                              AS cod_e_ente_benef_nazione_beneficiario_e,
         export.cod_e_sogg_vers_id_univ_vers_tipo_id_univoco                       AS cod_e_sogg_vers_id_univ_vers_tipo_id_univoco_e,
         export.cod_e_sogg_vers_id_univ_vers_codice_id_univoco                     AS cod_e_sogg_vers_id_univ_vers_codice_id_univoco_e,
         export.cod_e_sogg_vers_anagrafica_versante                                AS cod_e_sogg_vers_anagrafica_versante_e,
         export.de_e_sogg_vers_indirizzo_versante                                  AS de_e_sogg_vers_indirizzo_versante_e,
         export.de_e_sogg_vers_civico_versante                                     AS de_e_sogg_vers_civico_versante_e,
         export.cod_e_sogg_vers_cap_versante                                       AS cod_e_sogg_vers_cap_versante_e,
         export.de_e_sogg_vers_localita_versante                                   AS de_e_sogg_vers_localita_versante_e,
         export.de_e_sogg_vers_provincia_versante                                  AS de_e_sogg_vers_provincia_versante_e,
         export.cod_e_sogg_vers_nazione_versante                                   AS cod_e_sogg_vers_nazione_versante_e,
         export.de_e_sogg_vers_email_versante                                      AS de_e_sogg_vers_email_versante_e,
         export.cod_e_sogg_pag_id_univ_pag_tipo_id_univoco                         AS cod_e_sogg_pag_id_univ_pag_tipo_id_univoco_e,
         export.cod_e_sogg_pag_id_univ_pag_codice_id_univoco                       AS cod_e_sogg_pag_id_univ_pag_codice_id_univoco_e,
         export.cod_e_sogg_pag_anagrafica_pagatore                                 AS cod_e_sogg_pag_anagrafica_pagatore_e,
         export.de_e_sogg_pag_indirizzo_pagatore                                   AS de_e_sogg_pag_indirizzo_pagatore_e,
         export.de_e_sogg_pag_civico_pagatore                                      AS de_e_sogg_pag_civico_pagatore_e,
         export.cod_e_sogg_pag_cap_pagatore                                        AS cod_e_sogg_pag_cap_pagatore_e,
         export.de_e_sogg_pag_localita_pagatore                                    AS de_e_sogg_pag_localita_pagatore_e,
         export.de_e_sogg_pag_provincia_pagatore                                   AS de_e_sogg_pag_provincia_pagatore_e,
         export.cod_e_sogg_pag_nazione_pagatore                                    AS cod_e_sogg_pag_nazione_pagatore_e,
         export.de_e_sogg_pag_email_pagatore                                       AS de_e_sogg_pag_email_pagatore_e,
         export.cod_e_dati_pag_codice_esito_pagamento                              AS cod_e_dati_pag_codice_esito_pagamento_e,
         export.num_e_dati_pag_importo_totale_pagato                               AS num_e_dati_pag_importo_totale_pagato_e,
         export.cod_e_dati_pag_id_univoco_versamento                               AS cod_e_dati_pag_id_univoco_versamento_e,
         export.cod_e_dati_pag_codice_contesto_pagamento                           AS cod_e_dati_pag_codice_contesto_pagamento_e,
         export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato                AS num_e_dati_pag_dati_sing_pag_singolo_importo_pagato_e,
         export.de_e_dati_pag_dati_sing_pag_esito_singolo_pagamento                AS de_e_dati_pag_dati_sing_pag_esito_singolo_pagamento_e,
         export.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento           AS dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e,
         export.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss                    AS cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss_e,
         export.de_e_dati_pag_dati_sing_pag_causale_versamento                     AS de_e_dati_pag_dati_sing_pag_causale_versamento_e,
         export.de_e_dati_pag_dati_sing_pag_dati_specifici_riscossione             AS de_e_dati_pag_dati_sing_pag_dati_specifici_riscossione_e,
         export.cod_tipo_dovuto                                                    AS cod_tipo_dovuto_e,
         export.dt_acquisizione                                                    AS dt_acquisizione_e,
         rendicontazione.mygov_ente_id                                             AS mygov_ente_id_r,
         rendicontazione.mygov_manage_flusso_id                                    AS mygov_manage_flusso_id_r,
         rendicontazione.versione_oggetto                                          AS versione_oggetto_r,
         rendicontazione.cod_identificativo_flusso                                 AS cod_identificativo_flusso_r,
         rendicontazione.dt_data_ora_flusso                                        AS dt_data_ora_flusso_r,
         rendicontazione.cod_identificativo_univoco_regolamento                    AS cod_identificativo_univoco_regolamento_r,
         rendicontazione.dt_data_regolamento                                       AS dt_data_regolamento_r,
         rendicontazione.cod_ist_mitt_id_univ_mitt_tipo_identificativo_univoco     AS cod_ist_mitt_id_univ_mitt_tipo_identificativo_univoco_r,
         rendicontazione.cod_ist_mitt_id_univ_mitt_codice_identificativo_univoco   AS cod_ist_mitt_id_univ_mitt_codice_identificativo_univoco_r,
         rendicontazione.de_ist_mitt_denominazione_mittente                        AS de_ist_mitt_denominazione_mittente_r,
         rendicontazione.cod_ist_ricev_id_univ_ricev_tipo_identificativo_univoco   AS cod_ist_ricev_id_univ_ricev_tipo_identificativo_univoco_r,
         rendicontazione.cod_ist_ricev_id_univ_ricev_codice_identificativo_univoco AS cod_ist_ricev_id_univ_ricev_codice_identificativo_univoco_r,
         rendicontazione.de_ist_ricev_denominazione_ricevente                      AS de_ist_ricev_denominazione_ricevente_r,
         rendicontazione.num_numero_totale_pagamenti                               AS num_numero_totale_pagamenti_r,
         rendicontazione.num_importo_totale_pagamenti                              AS num_importo_totale_pagamenti_r,
         rendicontazione.cod_dati_sing_pagam_identificativo_univoco_versamento     AS cod_dati_sing_pagam_identificativo_univoco_versamento_r,
         rendicontazione.cod_dati_sing_pagam_identificativo_univoco_riscossione    AS cod_dati_sing_pagam_identificativo_univoco_riscossione_r,
         rendicontazione.num_dati_sing_pagam_singolo_importo_pagato                AS num_dati_sing_pagam_singolo_importo_pagato_r,
         rendicontazione.cod_dati_sing_pagam_codice_esito_singolo_pagamento        AS cod_dati_sing_pagam_codice_esito_singolo_pagamento_r,
         rendicontazione.dt_dati_sing_pagam_data_esito_singolo_pagamento           AS dt_dati_sing_pagam_data_esito_singolo_pagamento_r,
         rendicontazione.dt_acquisizione                                           AS dt_acquisizione_r,
         classificazione.mygov_classificazione_codice                              AS classificazione_completezza
  FROM   mygov_flusso_export export
         full join (SELECT mygov_flusso_rendicontazione.version,
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
                    WHERE  mygov_flusso_rendicontazione.cod_dati_sing_pagam_codice_esito_singolo_pagamento :: text <> '3' :: text) rendicontazione
                ON export.mygov_ente_id = rendicontazione.mygov_ente_id
                   AND export.cod_rp_silinviarp_id_univoco_versamento :: text = rendicontazione.cod_dati_sing_pagam_identificativo_univoco_versamento :: text
                   AND export.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss :: text = rendicontazione.cod_dati_sing_pagam_identificativo_univoco_riscossione :: text
                   AND export.indice_dati_singolo_pagamento = rendicontazione.indice_dati_singolo_pagamento
         left join mygov_classificazione_completezza classificazione
                ON classificazione.mygov_classificazione_codice :: text = 'RT_IUF' :: text
                   AND export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL
                   AND rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NOT NULL
                   AND export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato = rendicontazione.num_dati_sing_pagam_singolo_importo_pagato
                    OR classificazione.mygov_classificazione_codice :: text = 'RT_NO_IUF' :: text
                       AND export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL
                       AND ( rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NULL
                              OR rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NOT NULL
                                 AND export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato <> rendicontazione.num_dati_sing_pagam_singolo_importo_pagato );