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

CREATE SEQUENCE mygov_op_ente_tipo_dovuto_mygov_op_ente_tipo_dovuto_id_seq
  START WITH 1
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;

CREATE TABLE mygov_operatore_ente_tipo_dovuto
(
  mygov_operatore_ente_tipo_dovuto_id BIGINT NOT NULL,
  cod_fed_user_id CHARACTER VARYING(128) NOT NULL,
  mygov_ente_tipo_dovuto_id BIGINT,
  flg_attivo BOOLEAN NOT NULL,
  CONSTRAINT mygov_operatore_ente_tipo_dovuto_pkey PRIMARY KEY (mygov_operatore_ente_tipo_dovuto_id),
  CONSTRAINT mygov_operatore_ente_tipo_dovuto_mygov_ente_tipo_dovuto_fkey FOREIGN KEY (mygov_ente_tipo_dovuto_id)
      REFERENCES mygov_ente_tipo_dovuto (mygov_ente_tipo_dovuto_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE CASCADE
)

CREATE TABLE mygov_segnalazione (
mygov_segnalazione_id BIGINT NOT NULL, --(PK)
mygov_ente_id BIGINT NOT NULL, --(FK a tabella mygov_ente)
mygov_utente_id BIGINT NOT NULL, --(FK a tabella mygov_utente)
classificazione_completezza character varying(20) NOT NULL, --(codice classificazione)
cod_iud character varying(35), --(IUD)
cod_iuv character varying(35), --(IUV)
cod_iuf character varying(35), --(IUF)
de_nota TEXT NOT NULL, --(campo libero di testo)
flg_nascosto boolean DEFAULT false NOT NULL, --(booleano defalut false, se true = nascosto)
flg_attivo boolean, --(true se da considerare riga in JOIN, false altrimenti)
dt_creazione TIMESTAMP WITHOUT TIME ZONE NOT NULL,
dt_ultima_modifica TIMESTAMP WITHOUT TIME ZONE NOT NULL,
version INTEGER NOT NULL,
CONSTRAINT mygov_segnalazione_pkey PRIMARY KEY(mygov_segnalazione_id),
CONSTRAINT mygov_segnalazione_mygov_ente_fkey FOREIGN KEY (mygov_ente_id)
	REFERENCES mygov_ente (mygov_ente_id) MATCH SIMPLE
	ON UPDATE NO ACTION ON DELETE NO ACTION,
CONSTRAINT mygov_segnalazione_mygov_utente_fkey FOREIGN KEY (mygov_utente_id)
	REFERENCES mygov_utente (mygov_utente_id) MATCH SIMPLE
	ON UPDATE NO ACTION ON DELETE NO ACTION
);

ALTER TABLE mygov_segnalazione ADD CONSTRAINT mygov_segnalazione_not_null_iuv_or_iuf CHECK (cod_iud IS NOT NULL OR cod_iuv IS NOT NULL OR cod_iuf IS NOT NULL);

CREATE SEQUENCE mygov_segnalazione_mygov_segnalazione_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;

DROP VIEW v_mygov_import_export_rendicontazione_tesoreria ;
CREATE VIEW v_mygov_import_export_rendicontazione_tesoreria AS
SELECT 
	ente.cod_ipa_ente AS codice_ipa_ente,
    import_export_rendicontazione_tesoreria.dt_rp_dati_vers_data_esecuzione_pagamento_i AS dt_data_esecuzione_pagamento,
    (COALESCE(to_char((import_export_rendicontazione_tesoreria.dt_rp_dati_vers_data_esecuzione_pagamento_i)::timestamp with time zone, 'dd-MM-yyyy'::text), 'n/a'::text))::character varying(10) AS de_data_esecuzione_pagamento,
    (COALESCE((import_export_rendicontazione_tesoreria.num_rp_dati_vers_dati_sing_vers_commissione_carico_pa_i)::character varying(15), ''::character varying))::character varying(15) AS singolo_importo_commissione_carico_pa,
    (COALESCE(import_export_rendicontazione_tesoreria.bilancio_i, ''::character varying))::character varying(4096) AS bilancio,
    (COALESCE(import_export_rendicontazione_tesoreria.de_nome_flusso_e, 'n/a'::character varying))::character varying(50) AS nome_flusso_import_ente,
    (COALESCE((import_export_rendicontazione_tesoreria.num_riga_flusso_e)::character varying(12), 'n/a'::character varying))::character varying(12) AS riga_flusso_import_ente,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_iud_i, import_export_rendicontazione_tesoreria.cod_iud_e, 'n/a'::character varying))::character varying(35) AS codice_iud,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_rp_silinviarp_id_univoco_versamento_i, import_export_rendicontazione_tesoreria.cod_rp_silinviarp_id_univoco_versamento_e, import_export_rendicontazione_tesoreria.cod_dati_sing_pagam_identificativo_univoco_versamento_r, import_export_rendicontazione_tesoreria.cod_id_univoco_versamento_t, 'n/a'::character varying))::character varying(35) AS codice_iuv,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_versione_oggetto_e, ''::character varying))::character varying(16) AS versione_oggetto,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_dom_id_dominio_e, ''::character varying))::character varying(35) AS identificativo_dominio,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_dom_id_stazione_richiedente_e, ''::character varying))::character varying(35) AS identificativo_stazione_richiedente,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_id_messaggio_ricevuta_e, ''::character varying))::character varying(35) AS identificativo_messaggio_ricevuta,
    (COALESCE(to_char(import_export_rendicontazione_tesoreria.dt_e_data_ora_messaggio_ricevuta_e, 'dd-MM-yyyy HH:mm:ss'::text), ''::text))::character varying(19) AS data_ora_messaggio_ricevuta,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_riferimento_messaggio_richiesta_e, ''::character varying))::character varying(35) AS riferimento_messaggio_richiesta,
    (COALESCE(to_char((import_export_rendicontazione_tesoreria.dt_e_riferimento_data_richiesta_e)::timestamp with time zone, 'dd-MM-yyyy'::text), ''::text))::character varying(10) AS riferimento_data_richiesta,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_istit_att_id_univ_att_tipo_id_univoco_e, ''::bpchar))::character varying(1) AS tipo_identificativo_univoco_attestante,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_istit_att_id_univ_att_codice_id_univoco_e, ''::character varying))::character varying(35) AS codice_identificativo_univoco_attestante,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_denominazione_attestante_e, ''::character varying))::character varying(70) AS denominazione_attestante,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_istit_att_codice_unit_oper_attestante_e, ''::character varying))::character varying(35) AS codice_unit_oper_attestante,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_denom_unit_oper_attestante_e, ''::character varying))::character varying(70) AS denom_unit_oper_attestante,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_indirizzo_attestante_e, ''::character varying))::character varying(70) AS indirizzo_attestante,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_civico_attestante_e, ''::character varying))::character varying(16) AS civico_attestante,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_istit_att_cap_attestante_e, ''::character varying))::character varying(16) AS cap_attestante,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_localita_attestante_e, ''::character varying))::character varying(35) AS localita_attestante,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_istit_att_provincia_attestante_e, ''::character varying))::character varying(35) AS provincia_attestante,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_istit_att_nazione_attestante_e, ''::character varying))::character varying(2) AS nazione_attestante,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_ente_benef_id_univ_benef_tipo_id_univoco_e, ''::bpchar))::character varying(1) AS tipo_identificativo_univoco_beneficiario,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_ente_benef_id_univ_benef_codice_id_univoco_e, ''::character varying))::character varying(35) AS codice_identificativo_univoco_beneficiario,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_denominazione_beneficiario_e, ''::character varying))::character varying(70) AS denominazione_beneficiario,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_ente_benef_codice_unit_oper_beneficiario_e, ''::character varying))::character varying(35) AS codice_unit_oper_beneficiario,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_denom_unit_oper_beneficiario_e, ''::character varying))::character varying(70) AS denom_unit_oper_beneficiario,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_indirizzo_beneficiario_e, ''::character varying))::character varying(70) AS indirizzo_beneficiario,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_civico_beneficiario_e, ''::character varying))::character varying(16) AS civico_beneficiario,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_ente_benef_cap_beneficiario_e, ''::character varying))::character varying(16) AS cap_beneficiario,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_localita_beneficiario_e, ''::character varying))::character varying(35) AS localita_beneficiario,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_ente_benef_provincia_beneficiario_e, ''::character varying))::character varying(35) AS provincia_beneficiario,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_ente_benef_nazione_beneficiario_e, ''::character varying))::character varying(2) AS nazione_beneficiario,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_sogg_vers_id_univ_vers_tipo_id_univoco_e, ''::bpchar))::character varying(1) AS tipo_identificativo_univoco_versante,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_sogg_vers_id_univ_vers_codice_id_univoco_e, ''::character varying))::character varying(35) AS codice_identificativo_univoco_versante,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_sogg_vers_anagrafica_versante_e, ''::character varying))::character varying(70) AS anagrafica_versante,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_sogg_vers_indirizzo_versante_e, ''::character varying))::character varying(70) AS indirizzo_versante,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_sogg_vers_civico_versante_e, ''::character varying))::character varying(16) AS civico_versante,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_sogg_vers_cap_versante_e, ''::character varying))::character varying(16) AS cap_versante,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_sogg_vers_localita_versante_e, ''::character varying))::character varying(35) AS localita_versante,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_sogg_vers_provincia_versante_e, ''::character varying))::character varying(35) AS provincia_versante,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_sogg_vers_nazione_versante_e, ''::character varying))::character varying(2) AS nazione_versante,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_sogg_vers_email_versante_e, ''::character varying))::character varying(256) AS email_versante,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_rp_sogg_pag_id_univ_pag_tipo_id_univoco_i, import_export_rendicontazione_tesoreria.cod_e_sogg_pag_id_univ_pag_tipo_id_univoco_e, ''::bpchar))::character varying(1) AS tipo_identificativo_univoco_pagatore,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_rp_sogg_pag_id_univ_pag_codice_id_univoco_i, import_export_rendicontazione_tesoreria.cod_e_sogg_pag_id_univ_pag_codice_id_univoco_e, ''::character varying))::character varying(35) AS codice_identificativo_univoco_pagatore,
    (COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_anagrafica_pagatore_i, import_export_rendicontazione_tesoreria.cod_e_sogg_pag_anagrafica_pagatore_e, ''::character varying))::character varying(70) AS anagrafica_pagatore,
    (COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_indirizzo_pagatore_i, import_export_rendicontazione_tesoreria.de_e_sogg_pag_indirizzo_pagatore_e, ''::character varying))::character varying(70) AS indirizzo_pagatore,
    (COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_civico_pagatore_i, import_export_rendicontazione_tesoreria.de_e_sogg_pag_civico_pagatore_e, ''::character varying))::character varying(16) AS civico_pagatore,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_rp_sogg_pag_cap_pagatore_i, import_export_rendicontazione_tesoreria.cod_e_sogg_pag_cap_pagatore_e, ''::character varying))::character varying(16) AS cap_pagatore,
    (COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_localita_pagatore_i, import_export_rendicontazione_tesoreria.de_e_sogg_pag_localita_pagatore_e, ''::character varying))::character varying(35) AS localita_pagatore,
    (COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_provincia_pagatore_i, import_export_rendicontazione_tesoreria.de_e_sogg_pag_provincia_pagatore_e, ''::character varying))::character varying(35) AS provincia_pagatore,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_rp_sogg_pag_nazione_pagatore_i, import_export_rendicontazione_tesoreria.cod_e_sogg_pag_nazione_pagatore_e, ''::character varying))::character varying(2) AS nazione_pagatore,
    (COALESCE(import_export_rendicontazione_tesoreria.de_rp_sogg_pag_email_pagatore_i, import_export_rendicontazione_tesoreria.de_e_sogg_pag_email_pagatore_e, ''::character varying))::character varying(256) AS email_pagatore,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_dati_pag_codice_esito_pagamento_e, ''::bpchar))::character varying(1) AS codice_esito_pagamento,
    (COALESCE((import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e)::character varying(15), ''::character varying))::character varying(15) AS importo_totale_pagato,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_dati_pag_id_univoco_versamento_e, ''::character varying))::character varying(35) AS identificativo_univoco_versamento,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_dati_pag_codice_contesto_pagamento_e, ''::character varying))::character varying(35) AS codice_contesto_pagamento,
    (COALESCE((import_export_rendicontazione_tesoreria.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento_i)::character varying(15), (import_export_rendicontazione_tesoreria.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato_e)::character varying(15), (import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r)::character varying(15), ''::character varying))::character varying(15) AS singolo_importo_pagato,
    (COALESCE(import_export_rendicontazione_tesoreria.de_e_dati_pag_dati_sing_pag_esito_singolo_pagamento_e, import_export_rendicontazione_tesoreria.cod_dati_sing_pagam_codice_esito_singolo_pagamento_r, ''::character varying))::character varying(35) AS esito_singolo_pagamento,
    COALESCE(import_export_rendicontazione_tesoreria.dt_dati_sing_pagam_data_esito_singolo_pagamento_r, import_export_rendicontazione_tesoreria.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e) AS dt_data_esito_singolo_pagamento,
    (COALESCE(to_char((COALESCE(import_export_rendicontazione_tesoreria.dt_dati_sing_pagam_data_esito_singolo_pagamento_r, import_export_rendicontazione_tesoreria.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e))::timestamp with time zone, 'dd-MM-yyyy'::text), 'n/a'::text))::character varying(10) AS de_data_esito_singolo_pagamento,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss_e, import_export_rendicontazione_tesoreria.cod_dati_sing_pagam_identificativo_univoco_riscossione_r, 'n/a'::character varying))::character varying(35) AS identificativo_univoco_riscossione,
    (COALESCE(import_export_rendicontazione_tesoreria.de_rp_dati_vers_dati_sing_vers_causale_versamento_i, import_export_rendicontazione_tesoreria.de_e_dati_pag_dati_sing_pag_causale_versamento_e, ''::character varying))::character varying(1024) AS causale_versamento,
    (COALESCE(import_export_rendicontazione_tesoreria.de_rp_dati_vers_dati_sing_vers_dati_specifici_riscossione_i, import_export_rendicontazione_tesoreria.de_e_dati_pag_dati_sing_pag_dati_specifici_riscossione_e, ''::character varying))::character varying(140) AS dati_specifici_riscossione,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_tipo_dovuto_i, import_export_rendicontazione_tesoreria.cod_tipo_dovuto_e, ''::character varying))::character varying(64) AS tipo_dovuto,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_identificativo_flusso_r, import_export_rendicontazione_tesoreria.cod_id_univoco_flusso_t, 'n/a'::character varying))::character varying(35) AS identificativo_flusso_rendicontazione,
    (COALESCE(to_char(import_export_rendicontazione_tesoreria.dt_data_ora_flusso_r, 'dd-MM-yyyy HH:mm:ss'::text), 'n/a'::text))::character varying(19) AS data_ora_flusso_rendicontazione,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_identificativo_univoco_regolamento_r, 'n/a'::character varying))::character varying(35) AS identificativo_univoco_regolamento,
    COALESCE(import_export_rendicontazione_tesoreria.dt_data_regolamento_r, (import_export_rendicontazione_tesoreria.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e + ente.num_giorni_pagamento_presunti)) AS dt_data_regolamento,
    COALESCE((to_char((COALESCE(import_export_rendicontazione_tesoreria.dt_data_regolamento_r, (import_export_rendicontazione_tesoreria.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e + ente.num_giorni_pagamento_presunti)))::timestamp with time zone, 'dd-MM-yyyy'::text))::character varying(10), 'n/a'::character varying) AS de_data_regolamento,
    (COALESCE((import_export_rendicontazione_tesoreria.num_numero_totale_pagamenti_r)::character varying(15), 'n/a'::character varying))::character varying(15) AS numero_totale_pagamenti,
    (COALESCE((import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r)::character varying(15), 'n/a'::character varying))::character varying(21) AS importo_totale_pagamenti,
    (to_char((GREATEST(import_export_rendicontazione_tesoreria.dt_acquisizione_r, import_export_rendicontazione_tesoreria.dt_acquisizione_e, import_export_rendicontazione_tesoreria.dt_acquisizione_t))::timestamp with time zone, 'dd-MM-yyyy'::text))::character varying(10) AS data_acquisizione,
    (COALESCE(import_export_rendicontazione_tesoreria.cod_conto_t, (''::character varying)::bpchar))::character varying(12) AS cod_conto,
    import_export_rendicontazione_tesoreria.dt_data_contabile_t AS dt_data_contabile,
    (COALESCE(to_char((import_export_rendicontazione_tesoreria.dt_data_contabile_t)::timestamp with time zone, 'dd-MM-yyyy'::text), (''::character varying)::text))::character varying(10) AS de_data_contabile,
    import_export_rendicontazione_tesoreria.dt_data_valuta_t AS dt_data_valuta,
    (COALESCE(to_char((import_export_rendicontazione_tesoreria.dt_data_valuta_t)::timestamp with time zone, 'dd-MM-yyyy'::text), (''::character varying)::text))::character varying(10) AS de_data_valuta,
    import_export_rendicontazione_tesoreria.num_importo_t AS num_importo,
    (COALESCE((import_export_rendicontazione_tesoreria.num_importo_t)::character(15), ('n/a'::character varying)::bpchar))::character varying(15) AS de_importo,
    COALESCE(import_export_rendicontazione_tesoreria.cod_or1_t, ''::text) AS cod_or1,
        CASE
            WHEN ((import_export_rendicontazione_tesoreria.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento_i = import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e) OR ((((sum(import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r) OVER (PARTITION BY import_export_rendicontazione_tesoreria.cod_identificativo_flusso_r) - import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r) = (0)::numeric) AND (import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e = import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r)) AND (import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r = import_export_rendicontazione_tesoreria.num_importo_t))) THEN 'OK'::character varying(3)
            WHEN ((((import_export_rendicontazione_tesoreria.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento_i <> import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e) OR ((sum(import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r) OVER (PARTITION BY import_export_rendicontazione_tesoreria.cod_identificativo_flusso_r) - import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r) <> (0)::numeric)) OR (import_export_rendicontazione_tesoreria.num_e_dati_pag_importo_totale_pagato_e <> import_export_rendicontazione_tesoreria.num_dati_sing_pagam_singolo_importo_pagato_r)) OR (import_export_rendicontazione_tesoreria.num_importo_totale_pagamenti_r <> import_export_rendicontazione_tesoreria.num_importo_t)) THEN 'KO'::character varying(3)
            ELSE 'n/a'::character varying(3)
        END AS verifica_totale,
    (COALESCE(import_export_rendicontazione_tesoreria.classificazione_completezza, 'OTHERS'::character varying))::character varying(20) AS classificazione_completezza,
    import_export_rendicontazione_tesoreria.dt_data_ultimo_aggiornamento,
    (to_char((import_export_rendicontazione_tesoreria.dt_data_ultimo_aggiornamento)::timestamp with time zone, 'dd-MM-yyyy'::text))::character varying(10) AS de_data_ultimo_aggiornamento,
	(COALESCE(
		import_export_rendicontazione_tesoreria.cod_identificativo_flusso_r, 
		import_export_rendicontazione_tesoreria.cod_id_univoco_flusso_t ::character varying)
	)::character varying(35) AS cod_iuf_key,
	(COALESCE(
		import_export_rendicontazione_tesoreria.cod_iud_i,
		import_export_rendicontazione_tesoreria.cod_iud_e ::character varying)
	)::character varying(35) AS cod_iud_key,
	(COALESCE(import_export_rendicontazione_tesoreria.cod_rp_silinviarp_id_univoco_versamento_i, import_export_rendicontazione_tesoreria.cod_rp_silinviarp_id_univoco_versamento_e, import_export_rendicontazione_tesoreria.cod_dati_sing_pagam_identificativo_univoco_versamento_r, import_export_rendicontazione_tesoreria.cod_id_univoco_versamento_t::character varying)
	)::character varying(35) AS cod_iuv_key
FROM 
	mygov_import_export_rendicontazione_tesoreria_completa import_export_rendicontazione_tesoreria,
	mygov_ente ente
WHERE 
	(COALESCE(import_export_rendicontazione_tesoreria.mygov_ente_id_i, import_export_rendicontazione_tesoreria.mygov_ente_id_e, import_export_rendicontazione_tesoreria.mygov_ente_id_r, import_export_rendicontazione_tesoreria.mygov_ente_id_t) = ente.mygov_ente_id)
;

/*
ALTER TABLE mygov_import_export_rendicontazione_tesoreria 
	ADD COLUMN cod_iuv_key character varying(35),
	ADD COLUMN cod_iuf_key character varying(35),
	ADD COLUMN cod_iud_key character varying(35)
;

*/

DROP TABLE mygov_import_export_rendicontazione_tesoreria;

CREATE TABLE mygov_import_export_rendicontazione_tesoreria (
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
    verifica_totale character varying(3),
    classificazione_completezza character varying(20),
    dt_data_ultimo_aggiornamento date,
    de_data_ultimo_aggiornamento character varying(10),
	cod_iuf_key character varying(35),
	cod_iud_key character varying(35),
    cod_iuv_key character varying(35)
);

CREATE INDEX idx_mygovimpexprendtes_id_key ON mygov_import_export_rendicontazione_tesoreria USING btree (codice_ipa_ente, classificazione_completezza, cod_iuv_key, cod_iuf_key, cod_iud_key);


CREATE INDEX idx_mygovimpexprendtes_causale_versamento ON mygov_import_export_rendicontazione_tesoreria USING btree (codice_ipa_ente, causale_versamento);

CREATE INDEX idx_mygovimpexprendtes_classificazione_completezza ON mygov_import_export_rendicontazione_tesoreria USING btree (codice_ipa_ente, classificazione_completezza);

CREATE INDEX idx_mygovimpexprendtes_codice_iud ON mygov_import_export_rendicontazione_tesoreria USING btree (codice_ipa_ente, codice_iud);

CREATE INDEX idx_mygovimpexprendtes_de_importo ON mygov_import_export_rendicontazione_tesoreria USING btree (codice_ipa_ente, de_importo);

CREATE INDEX idx_mygovimpexprendtes_dt_data_contabile ON mygov_import_export_rendicontazione_tesoreria USING btree (codice_ipa_ente, dt_data_contabile);

CREATE INDEX idx_mygovimpexprendtes_dt_data_esito_singolo_pagamento ON mygov_import_export_rendicontazione_tesoreria USING btree (codice_ipa_ente, dt_data_esito_singolo_pagamento);

CREATE INDEX idx_mygovimpexprendtes_dt_data_regolamento ON mygov_import_export_rendicontazione_tesoreria USING btree (codice_ipa_ente, dt_data_regolamento);

CREATE INDEX idx_mygovimpexprendtes_dt_data_valuta ON mygov_import_export_rendicontazione_tesoreria USING btree (codice_ipa_ente, dt_data_valuta);

CREATE INDEX idx_mygovimpexprendtes_identificativo_flusso_rendicontazione ON mygov_import_export_rendicontazione_tesoreria USING btree (codice_ipa_ente, identificativo_flusso_rendicontazione);

CREATE INDEX idx_mygovimpexprendtes_identificativo_univoco_regolamento ON mygov_import_export_rendicontazione_tesoreria USING btree (codice_ipa_ente, identificativo_univoco_regolamento);

CREATE INDEX idx_mygovimpexprendtes_identificativo_univoco_riscossione ON mygov_import_export_rendicontazione_tesoreria USING btree (codice_ipa_ente, identificativo_univoco_riscossione);

CREATE INDEX idx_mygovimpexprendtes_identificativo_univoco_versamento ON mygov_import_export_rendicontazione_tesoreria USING btree (codice_ipa_ente, identificativo_univoco_versamento);

CREATE INDEX idx_mygovimpexprendtes_importo_totale_pagamenti ON mygov_import_export_rendicontazione_tesoreria USING btree (codice_ipa_ente, importo_totale_pagamenti);

CREATE INDEX idx_mygovimpexprendtes_tipo_dovuto ON mygov_import_export_rendicontazione_tesoreria USING btree (codice_ipa_ente, tipo_dovuto);
