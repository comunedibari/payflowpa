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