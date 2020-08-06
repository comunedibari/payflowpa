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


-- vista che popola mygov_import_export_rendicontazione_tesoreria_completa

CREATE OR REPLACE VIEW v_mygov_import_export_rendicontazione_tesoreria_completa AS 
 SELECT COALESCE(import.mygov_ente_id, export.mygov_ente_id, rendicontazione.mygov_ente_id, tesoreria_iuf.mygov_ente_id, tesoreria_iuv.mygov_ente_id, tesoreria_f2k_iuf.mygov_ente_id, tesoreria_f2k_iuv.mygov_ente_id, tesoreria_f2k.mygov_ente_id) AS mygov_ente_id,
    COALESCE(import.cod_rp_silinviarp_id_univoco_versamento, export.cod_rp_silinviarp_id_univoco_versamento, rendicontazione.cod_dati_sing_pagam_identificativo_univoco_versamento, tesoreria_iuv.cod_id_univoco_versamento, tesoreria_f2k_iuv.cod_id_univoco_versamento, 'n/a'::character varying)::character varying(35) AS codice_iuv,
    COALESCE(export.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss, rendicontazione.cod_dati_sing_pagam_identificativo_univoco_riscossione, 'n/a'::character varying)::character varying(35) AS identificativo_univoco_riscossione,
    COALESCE(rendicontazione.cod_identificativo_flusso, tesoreria_iuf.cod_id_univoco_flusso, tesoreria_f2k_iuf.cod_id_univoco_flusso, 'n/a'::character varying)::character varying(35) AS identificativo_flusso_rendicontazione,
    import.mygov_ente_id AS mygov_ente_id_i,
    import.mygov_manage_flusso_id AS mygov_manage_flusso_id_i,
    import.cod_iud AS cod_iud_i,
    import.cod_rp_silinviarp_id_univoco_versamento AS cod_rp_silinviarp_id_univoco_versamento_i,
    import.cod_rp_sogg_pag_id_univ_pag_tipo_id_univoco AS cod_rp_sogg_pag_id_univ_pag_tipo_id_univoco_i,
    import.cod_rp_sogg_pag_id_univ_pag_codice_id_univoco AS cod_rp_sogg_pag_id_univ_pag_codice_id_univoco_i,
    import.de_rp_sogg_pag_anagrafica_pagatore AS de_rp_sogg_pag_anagrafica_pagatore_i,
    import.de_rp_sogg_pag_indirizzo_pagatore AS de_rp_sogg_pag_indirizzo_pagatore_i,
    import.de_rp_sogg_pag_civico_pagatore AS de_rp_sogg_pag_civico_pagatore_i,
    import.cod_rp_sogg_pag_cap_pagatore AS cod_rp_sogg_pag_cap_pagatore_i,
    import.de_rp_sogg_pag_localita_pagatore AS de_rp_sogg_pag_localita_pagatore_i,
    import.de_rp_sogg_pag_provincia_pagatore AS de_rp_sogg_pag_provincia_pagatore_i,
    import.cod_rp_sogg_pag_nazione_pagatore AS cod_rp_sogg_pag_nazione_pagatore_i,
    import.de_rp_sogg_pag_email_pagatore AS de_rp_sogg_pag_email_pagatore_i,
    import.dt_rp_dati_vers_data_esecuzione_pagamento AS dt_rp_dati_vers_data_esecuzione_pagamento_i,
    import.cod_rp_dati_vers_tipo_versamento AS cod_rp_dati_vers_tipo_versamento_i,
    import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento AS num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento_i,
    import.num_rp_dati_vers_dati_sing_vers_commissione_carico_pa AS num_rp_dati_vers_dati_sing_vers_commissione_carico_pa_i,
    import.de_rp_dati_vers_dati_sing_vers_causale_versamento AS de_rp_dati_vers_dati_sing_vers_causale_versamento_i,
    import.de_rp_dati_vers_dati_sing_vers_dati_specifici_riscossione AS de_rp_dati_vers_dati_sing_vers_dati_specifici_riscossione_i,
    import.cod_tipo_dovuto AS cod_tipo_dovuto_i,
    import.bilancio AS bilancio_i,
    import.dt_acquisizione AS dt_acquisizione_i,
    export.mygov_ente_id AS mygov_ente_id_e,
    export.mygov_manage_flusso_id AS mygov_manage_flusso_id_e,
    export.de_nome_flusso AS de_nome_flusso_e,
    export.num_riga_flusso AS num_riga_flusso_e,
    export.cod_iud AS cod_iud_e,
    export.cod_rp_silinviarp_id_univoco_versamento AS cod_rp_silinviarp_id_univoco_versamento_e,
    export.de_e_versione_oggetto AS de_e_versione_oggetto_e,
    export.cod_e_dom_id_dominio AS cod_e_dom_id_dominio_e,
    export.cod_e_dom_id_stazione_richiedente AS cod_e_dom_id_stazione_richiedente_e,
    export.cod_e_id_messaggio_ricevuta AS cod_e_id_messaggio_ricevuta_e,
    export.dt_e_data_ora_messaggio_ricevuta AS dt_e_data_ora_messaggio_ricevuta_e,
    export.cod_e_riferimento_messaggio_richiesta AS cod_e_riferimento_messaggio_richiesta_e,
    export.dt_e_riferimento_data_richiesta AS dt_e_riferimento_data_richiesta_e,
    export.cod_e_istit_att_id_univ_att_tipo_id_univoco AS cod_e_istit_att_id_univ_att_tipo_id_univoco_e,
    export.cod_e_istit_att_id_univ_att_codice_id_univoco AS cod_e_istit_att_id_univ_att_codice_id_univoco_e,
    export.de_e_istit_att_denominazione_attestante AS de_e_istit_att_denominazione_attestante_e,
    export.cod_e_istit_att_codice_unit_oper_attestante AS cod_e_istit_att_codice_unit_oper_attestante_e,
    export.de_e_istit_att_denom_unit_oper_attestante AS de_e_istit_att_denom_unit_oper_attestante_e,
    export.de_e_istit_att_indirizzo_attestante AS de_e_istit_att_indirizzo_attestante_e,
    export.de_e_istit_att_civico_attestante AS de_e_istit_att_civico_attestante_e,
    export.cod_e_istit_att_cap_attestante AS cod_e_istit_att_cap_attestante_e,
    export.de_e_istit_att_localita_attestante AS de_e_istit_att_localita_attestante_e,
    export.de_e_istit_att_provincia_attestante AS de_e_istit_att_provincia_attestante_e,
    export.cod_e_istit_att_nazione_attestante AS cod_e_istit_att_nazione_attestante_e,
    export.cod_e_ente_benef_id_univ_benef_tipo_id_univoco AS cod_e_ente_benef_id_univ_benef_tipo_id_univoco_e,
    export.cod_e_ente_benef_id_univ_benef_codice_id_univoco AS cod_e_ente_benef_id_univ_benef_codice_id_univoco_e,
    export.de_e_ente_benef_denominazione_beneficiario AS de_e_ente_benef_denominazione_beneficiario_e,
    export.cod_e_ente_benef_codice_unit_oper_beneficiario AS cod_e_ente_benef_codice_unit_oper_beneficiario_e,
    export.de_e_ente_benef_denom_unit_oper_beneficiario AS de_e_ente_benef_denom_unit_oper_beneficiario_e,
    export.de_e_ente_benef_indirizzo_beneficiario AS de_e_ente_benef_indirizzo_beneficiario_e,
    export.de_e_ente_benef_civico_beneficiario AS de_e_ente_benef_civico_beneficiario_e,
    export.cod_e_ente_benef_cap_beneficiario AS cod_e_ente_benef_cap_beneficiario_e,
    export.de_e_ente_benef_localita_beneficiario AS de_e_ente_benef_localita_beneficiario_e,
    export.de_e_ente_benef_provincia_beneficiario AS de_e_ente_benef_provincia_beneficiario_e,
    export.cod_e_ente_benef_nazione_beneficiario AS cod_e_ente_benef_nazione_beneficiario_e,
    export.cod_e_sogg_vers_id_univ_vers_tipo_id_univoco AS cod_e_sogg_vers_id_univ_vers_tipo_id_univoco_e,
    export.cod_e_sogg_vers_id_univ_vers_codice_id_univoco AS cod_e_sogg_vers_id_univ_vers_codice_id_univoco_e,
    export.cod_e_sogg_vers_anagrafica_versante AS cod_e_sogg_vers_anagrafica_versante_e,
    export.de_e_sogg_vers_indirizzo_versante AS de_e_sogg_vers_indirizzo_versante_e,
    export.de_e_sogg_vers_civico_versante AS de_e_sogg_vers_civico_versante_e,
    export.cod_e_sogg_vers_cap_versante AS cod_e_sogg_vers_cap_versante_e,
    export.de_e_sogg_vers_localita_versante AS de_e_sogg_vers_localita_versante_e,
    export.de_e_sogg_vers_provincia_versante AS de_e_sogg_vers_provincia_versante_e,
    export.cod_e_sogg_vers_nazione_versante AS cod_e_sogg_vers_nazione_versante_e,
    export.de_e_sogg_vers_email_versante AS de_e_sogg_vers_email_versante_e,
    export.cod_e_sogg_pag_id_univ_pag_tipo_id_univoco AS cod_e_sogg_pag_id_univ_pag_tipo_id_univoco_e,
    export.cod_e_sogg_pag_id_univ_pag_codice_id_univoco AS cod_e_sogg_pag_id_univ_pag_codice_id_univoco_e,
    export.cod_e_sogg_pag_anagrafica_pagatore AS cod_e_sogg_pag_anagrafica_pagatore_e,
    export.de_e_sogg_pag_indirizzo_pagatore AS de_e_sogg_pag_indirizzo_pagatore_e,
    export.de_e_sogg_pag_civico_pagatore AS de_e_sogg_pag_civico_pagatore_e,
    export.cod_e_sogg_pag_cap_pagatore AS cod_e_sogg_pag_cap_pagatore_e,
    export.de_e_sogg_pag_localita_pagatore AS de_e_sogg_pag_localita_pagatore_e,
    export.de_e_sogg_pag_provincia_pagatore AS de_e_sogg_pag_provincia_pagatore_e,
    export.cod_e_sogg_pag_nazione_pagatore AS cod_e_sogg_pag_nazione_pagatore_e,
    export.de_e_sogg_pag_email_pagatore AS de_e_sogg_pag_email_pagatore_e,
    export.cod_e_dati_pag_codice_esito_pagamento AS cod_e_dati_pag_codice_esito_pagamento_e,
    export.num_e_dati_pag_importo_totale_pagato AS num_e_dati_pag_importo_totale_pagato_e,
    export.cod_e_dati_pag_id_univoco_versamento AS cod_e_dati_pag_id_univoco_versamento_e,
    export.cod_e_dati_pag_codice_contesto_pagamento AS cod_e_dati_pag_codice_contesto_pagamento_e,
    export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato AS num_e_dati_pag_dati_sing_pag_singolo_importo_pagato_e,
    export.de_e_dati_pag_dati_sing_pag_esito_singolo_pagamento AS de_e_dati_pag_dati_sing_pag_esito_singolo_pagamento_e,
    export.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento AS dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento_e,
    export.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss AS cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss_e,
    export.de_e_dati_pag_dati_sing_pag_causale_versamento AS de_e_dati_pag_dati_sing_pag_causale_versamento_e,
    export.de_e_dati_pag_dati_sing_pag_dati_specifici_riscossione AS de_e_dati_pag_dati_sing_pag_dati_specifici_riscossione_e,
    export.cod_tipo_dovuto AS cod_tipo_dovuto_e,
    export.dt_acquisizione AS dt_acquisizione_e,
    export.indice_dati_singolo_pagamento AS indice_dati_singolo_pagamento_e,
    export.bilancio AS bilancio_e,
    rendicontazione.mygov_ente_id AS mygov_ente_id_r,
    rendicontazione.mygov_manage_flusso_id AS mygov_manage_flusso_id_r,
    rendicontazione.versione_oggetto AS versione_oggetto_r,
    rendicontazione.cod_identificativo_flusso AS cod_identificativo_flusso_r,
    rendicontazione.dt_data_ora_flusso AS dt_data_ora_flusso_r,
    rendicontazione.cod_identificativo_univoco_regolamento AS cod_identificativo_univoco_regolamento_r,
    rendicontazione.dt_data_regolamento AS dt_data_regolamento_r,
    rendicontazione.cod_ist_mitt_id_univ_mitt_tipo_identificativo_univoco AS cod_ist_mitt_id_univ_mitt_tipo_identificativo_univoco_r,
    rendicontazione.cod_ist_mitt_id_univ_mitt_codice_identificativo_univoco AS cod_ist_mitt_id_univ_mitt_codice_identificativo_univoco_r,
    rendicontazione.de_ist_mitt_denominazione_mittente AS de_ist_mitt_denominazione_mittente_r,
    rendicontazione.cod_ist_ricev_id_univ_ricev_tipo_identificativo_univoco AS cod_ist_ricev_id_univ_ricev_tipo_identificativo_univoco_r,
    rendicontazione.cod_ist_ricev_id_univ_ricev_codice_identificativo_univoco AS cod_ist_ricev_id_univ_ricev_codice_identificativo_univoco_r,
    rendicontazione.de_ist_ricev_denominazione_ricevente AS de_ist_ricev_denominazione_ricevente_r,
    rendicontazione.num_numero_totale_pagamenti AS num_numero_totale_pagamenti_r,
    rendicontazione.num_importo_totale_pagamenti AS num_importo_totale_pagamenti_r,
    rendicontazione.cod_dati_sing_pagam_identificativo_univoco_versamento AS cod_dati_sing_pagam_identificativo_univoco_versamento_r,
    rendicontazione.cod_dati_sing_pagam_identificativo_univoco_riscossione AS cod_dati_sing_pagam_identificativo_univoco_riscossione_r,
    rendicontazione.num_dati_sing_pagam_singolo_importo_pagato AS num_dati_sing_pagam_singolo_importo_pagato_r,
    rendicontazione.cod_dati_sing_pagam_codice_esito_singolo_pagamento AS cod_dati_sing_pagam_codice_esito_singolo_pagamento_r,
    rendicontazione.dt_dati_sing_pagam_data_esito_singolo_pagamento AS dt_dati_sing_pagam_data_esito_singolo_pagamento_r,
    rendicontazione.dt_acquisizione AS dt_acquisizione_r,
    rendicontazione.indice_dati_singolo_pagamento AS indice_dati_singolo_pagamento_r,
    COALESCE(tesoreria_iuf.mygov_ente_id, tesoreria_iuv.mygov_ente_id, tesoreria_f2k_iuf.mygov_ente_id, tesoreria_f2k_iuv.mygov_ente_id, tesoreria_f2k.mygov_ente_id) AS mygov_ente_id_t,
    COALESCE(tesoreria_iuf.mygov_manage_flusso_id, tesoreria_iuv.mygov_manage_flusso_id) AS mygov_manage_flusso_id_t,
    COALESCE(tesoreria_iuf.cod_abi, tesoreria_iuv.cod_abi, tesoreria_f2k_iuf.cod_abi, tesoreria_f2k_iuv.cod_abi, tesoreria_f2k.cod_abi) AS cod_abi_t,
    COALESCE(tesoreria_iuf.cod_cab, tesoreria_iuv.cod_cab, tesoreria_f2k_iuf.cod_cab, tesoreria_f2k_iuv.cod_cab, tesoreria_f2k.cod_cab) AS cod_cab_t,
    COALESCE(tesoreria_iuf.cod_conto, tesoreria_iuv.cod_conto, tesoreria_f2k_iuf.cod_conto, tesoreria_f2k_iuv.cod_conto, tesoreria_f2k.cod_conto) AS cod_conto_t,
    COALESCE(tesoreria_iuf.cod_divisa, tesoreria_iuv.cod_divisa) AS cod_divisa_t,
    COALESCE(tesoreria_iuf.dt_data_contabile, tesoreria_iuv.dt_data_contabile, tesoreria_f2k_iuf.dt_bolletta, tesoreria_f2k_iuv.dt_bolletta, tesoreria_f2k.dt_bolletta) AS dt_data_contabile_t,
    COALESCE(tesoreria_iuf.dt_data_valuta, tesoreria_iuv.dt_data_valuta, tesoreria_f2k_iuf.dt_data_valuta_regione, tesoreria_f2k_iuv.dt_data_valuta_regione, tesoreria_f2k.dt_data_valuta_regione) AS dt_data_valuta_t,
    COALESCE(tesoreria_iuf.num_importo, tesoreria_iuv.num_importo, tesoreria_f2k_iuf.num_ip_bolletta, tesoreria_f2k_iuv.num_ip_bolletta, tesoreria_f2k.num_ip_bolletta) AS num_importo_t,
    COALESCE(tesoreria_iuf.cod_segno, tesoreria_iuv.cod_segno) AS cod_segno_t,
    COALESCE(tesoreria_iuf.de_causale, tesoreria_iuv.de_causale, tesoreria_f2k_iuf.de_causale::text, tesoreria_f2k_iuv.de_causale::text, tesoreria_f2k.de_causale::text) AS de_causale_t,
    COALESCE(tesoreria_iuf.cod_numero_assegno, tesoreria_iuv.cod_numero_assegno) AS cod_numero_assegno_t,
    COALESCE(tesoreria_iuf.cod_riferimento_banca, tesoreria_iuv.cod_riferimento_banca) AS cod_riferimento_banca_t,
    COALESCE(tesoreria_iuf.cod_riferimento_cliente, tesoreria_iuv.cod_riferimento_cliente) AS cod_riferimento_cliente_t,
    COALESCE(tesoreria_iuf.dt_data_ordine, tesoreria_iuv.dt_data_ordine) AS dt_data_ordine_t,
    COALESCE(tesoreria_iuf.de_descrizione_ordinante, tesoreria_iuv.de_descrizione_ordinante, tesoreria_f2k_iuf.de_cognome::text, tesoreria_f2k_iuv.de_cognome::text, tesoreria_f2k.de_cognome::text) AS de_descrizione_ordinante_t,
    COALESCE(tesoreria_iuf.cod_bi2, tesoreria_iuv.cod_bi2) AS cod_bi2_t,
    COALESCE(tesoreria_iuf.cod_be1, tesoreria_iuv.cod_be1) AS cod_be1_t,
    COALESCE(tesoreria_iuf.cod_ib1, tesoreria_iuv.cod_ib1) AS cod_ib1_t,
    COALESCE(tesoreria_iuf.cod_ib2, tesoreria_iuv.cod_ib2) AS cod_ib2_t,
    COALESCE(tesoreria_iuf.cod_ib4, tesoreria_iuv.cod_ib4) AS cod_ib4_t,
    COALESCE(tesoreria_iuf.cod_tid, tesoreria_iuv.cod_tid) AS cod_tid_t,
    COALESCE(tesoreria_iuf.cod_dte, tesoreria_iuv.cod_dte) AS cod_dte_t,
    COALESCE(tesoreria_iuf.cod_dtn, tesoreria_iuv.cod_dtn) AS cod_dtn_t,
    COALESCE(tesoreria_iuf.cod_eri, tesoreria_iuv.cod_eri) AS cod_eri_t,
    COALESCE(tesoreria_iuf.cod_im2, tesoreria_iuv.cod_im2) AS cod_im2_t,
    COALESCE(tesoreria_iuf.cod_ma2, tesoreria_iuv.cod_ma2) AS cod_ma2_t,
    COALESCE(tesoreria_iuf.cod_ri3, tesoreria_iuv.cod_ri3) AS cod_ri3_t,
    COALESCE(tesoreria_iuf.cod_or1, tesoreria_iuv.cod_or1, tesoreria_f2k_iuf.de_cognome::text, tesoreria_f2k_iuv.de_cognome::text, tesoreria_f2k.de_cognome::text) AS cod_or1_t,
    COALESCE(tesoreria_iuf.cod_sc2, tesoreria_iuv.cod_sc2) AS cod_sc2_t,
    COALESCE(tesoreria_iuf.cod_tr1, tesoreria_iuv.cod_tr1) AS cod_tr1_t,
    COALESCE(tesoreria_iuf.cod_sec, tesoreria_iuv.cod_sec) AS cod_sec_t,
    COALESCE(tesoreria_iuf.cod_ior, tesoreria_iuv.cod_ior) AS cod_ior_t,
    COALESCE(tesoreria_iuf.cod_id_univoco_flusso, tesoreria_f2k_iuf.cod_id_univoco_flusso, tesoreria_f2k_iuv.cod_id_univoco_flusso, tesoreria_f2k.cod_id_univoco_flusso) AS cod_id_univoco_flusso_t,
    COALESCE(tesoreria_iuv.cod_id_univoco_versamento, tesoreria_f2k_iuf.cod_id_univoco_versamento, tesoreria_f2k_iuv.cod_id_univoco_versamento, tesoreria_f2k.cod_id_univoco_versamento) AS cod_id_univoco_versamento_t,
    COALESCE(tesoreria_iuf.dt_acquisizione::timestamp without time zone, tesoreria_iuv.dt_acquisizione::timestamp without time zone, tesoreria_f2k_iuf.dt_ultima_modifica, tesoreria_f2k_iuv.dt_ultima_modifica, tesoreria_f2k.dt_ultima_modifica) AS dt_acquisizione_t,
    COALESCE(tesoreria_f2k_iuf.de_anno_bolletta, tesoreria_f2k_iuv.de_anno_bolletta, tesoreria_f2k.de_anno_bolletta) AS de_anno_bolletta_t,
    COALESCE(tesoreria_f2k_iuf.cod_bolletta, tesoreria_f2k_iuv.cod_bolletta, tesoreria_f2k.cod_bolletta) AS cod_bolletta_t,
    COALESCE(tesoreria_f2k_iuf.cod_id_dominio, tesoreria_f2k_iuv.cod_id_dominio, tesoreria_f2k.cod_id_dominio) AS cod_id_dominio_t,
    COALESCE(tesoreria_f2k_iuf.dt_ricezione, tesoreria_f2k_iuv.dt_ricezione, tesoreria_f2k.dt_ricezione) AS dt_ricezione_t,
    COALESCE(tesoreria_f2k_iuf.de_anno_documento, tesoreria_f2k_iuv.de_anno_documento, tesoreria_f2k.de_anno_documento) AS de_anno_documento_t,
    COALESCE(tesoreria_f2k_iuf.cod_documento, tesoreria_f2k_iuv.cod_documento, tesoreria_f2k.cod_documento) AS cod_documento_t,
    COALESCE(tesoreria_f2k_iuf.de_ae_provvisorio, tesoreria_f2k_iuv.de_ae_provvisorio, tesoreria_f2k.de_ae_provvisorio) AS de_anno_provvisorio_t,
    COALESCE(tesoreria_f2k_iuf.cod_provvisorio, tesoreria_f2k_iuv.cod_provvisorio, tesoreria_f2k.cod_provvisorio) AS cod_provvisorio_t,
    classificazione.mygov_classificazione_codice AS classificazione_completezza,
    GREATEST(import.dt_acquisizione::timestamp without time zone, export.dt_acquisizione::timestamp without time zone, rendicontazione.dt_acquisizione::timestamp without time zone, COALESCE(tesoreria_iuf.dt_acquisizione::timestamp without time zone, tesoreria_iuv.dt_acquisizione::timestamp without time zone, tesoreria_f2k_iuf.dt_ultima_modifica, tesoreria_f2k_iuv.dt_ultima_modifica, tesoreria_f2k.dt_ultima_modifica)) AS dt_data_ultimo_aggiornamento
   FROM mygov_flusso_import import
     FULL JOIN mygov_flusso_export export ON import.mygov_ente_id = export.mygov_ente_id AND import.cod_iud::text = export.cod_iud::text
     FULL JOIN ( SELECT mygov_flusso_rendicontazione.version,
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
           FROM mygov_flusso_rendicontazione
          WHERE mygov_flusso_rendicontazione.cod_dati_sing_pagam_codice_esito_singolo_pagamento::text <> '3'::text) rendicontazione ON export.mygov_ente_id = rendicontazione.mygov_ente_id AND export.cod_rp_silinviarp_id_univoco_versamento::text = rendicontazione.cod_dati_sing_pagam_identificativo_univoco_versamento::text AND export.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss::text = rendicontazione.cod_dati_sing_pagam_identificativo_univoco_riscossione::text AND export.indice_dati_singolo_pagamento = rendicontazione.indice_dati_singolo_pagamento
     FULL JOIN ( SELECT mygov_flusso_tesoreria_iuf.mygov_ente_id,
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
            NULL::character varying AS cod_id_univoco_versamento,
            mygov_flusso_tesoreria_iuf.dt_acquisizione
           FROM mygov_flusso_tesoreria_iuf) tesoreria_iuf ON upper(tesoreria_iuf.cod_id_univoco_flusso) = upper(rendicontazione.cod_identificativo_flusso)
     FULL JOIN ( SELECT mygov_flusso_tesoreria_iuv.mygov_ente_id,
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
            NULL::character varying AS cod_id_univoco_flusso,
            mygov_flusso_tesoreria_iuv.cod_id_univoco_versamento,
            mygov_flusso_tesoreria_iuv.dt_acquisizione
           FROM mygov_flusso_tesoreria_iuv) tesoreria_iuv ON COALESCE(export.mygov_ente_id, rendicontazione.mygov_ente_id) = tesoreria_iuv.mygov_ente_id AND COALESCE(export.cod_rp_silinviarp_id_univoco_versamento, rendicontazione.cod_dati_sing_pagam_identificativo_univoco_versamento)::text = tesoreria_iuv.cod_id_univoco_versamento::text
     FULL JOIN ( SELECT mygov_flusso_tesoreria.de_anno_bolletta,
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
           FROM mygov_flusso_tesoreria
          WHERE mygov_flusso_tesoreria.cod_id_univoco_flusso IS NOT NULL AND mygov_flusso_tesoreria.cod_id_univoco_versamento IS NULL) tesoreria_f2k_iuf ON COALESCE(export.mygov_ente_id, rendicontazione.mygov_ente_id) = tesoreria_f2k_iuf.mygov_ente_id AND upper(rendicontazione.cod_identificativo_flusso) = upper(tesoreria_f2k_iuf.cod_id_univoco_flusso)
     FULL JOIN ( SELECT mygov_flusso_tesoreria.de_anno_bolletta,
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
           FROM mygov_flusso_tesoreria
          WHERE mygov_flusso_tesoreria.cod_id_univoco_flusso IS NULL AND mygov_flusso_tesoreria.cod_id_univoco_versamento IS NOT NULL) tesoreria_f2k_iuv ON COALESCE(export.mygov_ente_id, rendicontazione.mygov_ente_id) = tesoreria_f2k_iuv.mygov_ente_id AND COALESCE(export.cod_rp_silinviarp_id_univoco_versamento, rendicontazione.cod_dati_sing_pagam_identificativo_univoco_versamento)::text = tesoreria_f2k_iuv.cod_id_univoco_versamento::text
     FULL JOIN ( SELECT mygov_flusso_tesoreria.de_anno_bolletta,
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
           FROM mygov_flusso_tesoreria
          WHERE mygov_flusso_tesoreria.cod_id_univoco_flusso IS NULL AND mygov_flusso_tesoreria.cod_id_univoco_versamento IS NULL) tesoreria_f2k ON 1 = 0
     LEFT JOIN mygov_classificazione_completezza classificazione ON classificazione.mygov_classificazione_codice::text = 'IUD_RT_IUF_TES'::text AND import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento IS NOT NULL AND export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL AND import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento = export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato AND (rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NOT NULL AND export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato = rendicontazione.num_dati_sing_pagam_singolo_importo_pagato AND rendicontazione.num_importo_totale_pagamenti IS NOT NULL AND (tesoreria_iuf.num_importo IS NOT NULL AND rendicontazione.num_importo_totale_pagamenti = tesoreria_iuf.num_importo OR tesoreria_f2k_iuf.num_ip_bolletta IS NOT NULL AND rendicontazione.num_importo_totale_pagamenti = tesoreria_f2k_iuf.num_ip_bolletta) OR tesoreria_iuv.num_importo IS NOT NULL AND export.num_e_dati_pag_importo_totale_pagato = tesoreria_iuv.num_importo OR tesoreria_f2k_iuv.num_ip_bolletta IS NOT NULL AND export.num_e_dati_pag_importo_totale_pagato = tesoreria_f2k_iuv.num_ip_bolletta) OR classificazione.mygov_classificazione_codice::text = 'IUD_RT_IUF'::text AND import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento IS NOT NULL AND export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL AND import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento = export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato AND rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NOT NULL AND export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato = rendicontazione.num_dati_sing_pagam_singolo_importo_pagato OR classificazione.mygov_classificazione_codice::text = 'RT_IUF_TES'::text AND export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL AND rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NOT NULL AND export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato = rendicontazione.num_dati_sing_pagam_singolo_importo_pagato AND rendicontazione.num_importo_totale_pagamenti IS NOT NULL AND (tesoreria_iuf.num_importo IS NOT NULL AND rendicontazione.num_importo_totale_pagamenti = tesoreria_iuf.num_importo OR tesoreria_f2k_iuf.num_ip_bolletta IS NOT NULL AND rendicontazione.num_importo_totale_pagamenti = tesoreria_f2k_iuf.num_ip_bolletta) OR classificazione.mygov_classificazione_codice::text = 'RT_IUF'::text AND export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL AND rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NOT NULL AND export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato = rendicontazione.num_dati_sing_pagam_singolo_importo_pagato OR classificazione.mygov_classificazione_codice::text = 'RT_NO_IUF'::text AND export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL AND NOT (tesoreria_iuv.num_importo IS NOT NULL AND export.num_e_dati_pag_importo_totale_pagato = tesoreria_iuv.num_importo OR tesoreria_f2k_iuv.num_ip_bolletta IS NOT NULL AND export.num_e_dati_pag_importo_totale_pagato = tesoreria_f2k_iuv.num_ip_bolletta OR tesoreria_iuv.num_importo IS NOT NULL AND export.num_e_dati_pag_importo_totale_pagato <> tesoreria_iuv.num_importo OR tesoreria_f2k_iuv.num_ip_bolletta IS NOT NULL AND export.num_e_dati_pag_importo_totale_pagato <> tesoreria_f2k_iuv.num_ip_bolletta) AND (rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NULL OR rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NOT NULL AND export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato <> rendicontazione.num_dati_sing_pagam_singolo_importo_pagato) OR classificazione.mygov_classificazione_codice::text = 'IUF_NO_TES'::text AND rendicontazione.num_importo_totale_pagamenti IS NOT NULL AND tesoreria_iuf.num_importo IS NULL AND tesoreria_iuv.num_importo IS NULL AND tesoreria_f2k_iuf.num_ip_bolletta IS NULL AND tesoreria_f2k_iuv.num_ip_bolletta IS NULL OR classificazione.mygov_classificazione_codice::text = 'IUF_TES_DIV_IMP'::text AND rendicontazione.num_importo_totale_pagamenti IS NOT NULL AND (tesoreria_iuf.num_importo IS NOT NULL AND rendicontazione.num_importo_totale_pagamenti <> tesoreria_iuf.num_importo OR tesoreria_iuv.num_importo IS NOT NULL AND rendicontazione.num_importo_totale_pagamenti <> tesoreria_iuv.num_importo OR tesoreria_f2k_iuf.num_ip_bolletta IS NOT NULL AND rendicontazione.num_importo_totale_pagamenti <> tesoreria_f2k_iuf.num_ip_bolletta OR tesoreria_f2k_iuv.num_ip_bolletta IS NOT NULL AND rendicontazione.num_importo_totale_pagamenti <> tesoreria_f2k_iuv.num_ip_bolletta) OR classificazione.mygov_classificazione_codice::text = 'TES_NO_IUF_OR_IUV'::text AND (tesoreria_iuf.num_importo IS NOT NULL OR tesoreria_iuv.num_importo IS NOT NULL OR tesoreria_f2k_iuf.num_ip_bolletta IS NOT NULL AND tesoreria_f2k_iuf.cod_id_univoco_flusso IS NOT NULL AND tesoreria_f2k_iuf.cod_id_univoco_versamento IS NULL OR tesoreria_f2k_iuv.num_ip_bolletta IS NOT NULL AND tesoreria_f2k_iuv.cod_id_univoco_flusso IS NULL AND tesoreria_f2k_iuv.cod_id_univoco_versamento IS NOT NULL) AND rendicontazione.num_importo_totale_pagamenti IS NULL AND export.num_e_dati_pag_importo_totale_pagato IS NULL AND import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento IS NULL OR classificazione.mygov_classificazione_codice::text = 'IUV_NO_RT'::text AND rendicontazione.num_importo_totale_pagamenti IS NOT NULL AND export.num_e_dati_pag_importo_totale_pagato IS NULL OR classificazione.mygov_classificazione_codice::text = 'IUD_NO_RT'::text AND import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento IS NOT NULL AND (export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NULL OR export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL AND import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento <> export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato) OR classificazione.mygov_classificazione_codice::text = 'RT_NO_IUD'::text AND export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NOT NULL AND import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento IS NULL OR classificazione.mygov_classificazione_codice::text = 'TES_NO_MATCH'::text AND import.num_rp_dati_vers_dati_sing_vers_importo_singolo_versamento IS NULL AND export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato IS NULL AND rendicontazione.num_importo_totale_pagamenti IS NULL AND tesoreria_iuf.num_importo IS NULL AND tesoreria_iuv.num_importo IS NULL AND tesoreria_f2k_iuf.cod_id_univoco_flusso IS NULL AND tesoreria_f2k_iuf.cod_id_univoco_versamento IS NULL AND tesoreria_f2k_iuv.cod_id_univoco_flusso IS NULL AND tesoreria_f2k_iuv.cod_id_univoco_versamento IS NULL AND tesoreria_f2k.cod_id_univoco_flusso IS NULL AND tesoreria_f2k.cod_id_univoco_versamento IS NULL OR classificazione.mygov_classificazione_codice::text = 'RT_TES'::text AND export.num_e_dati_pag_importo_totale_pagato IS NOT NULL AND rendicontazione.num_dati_sing_pagam_singolo_importo_pagato IS NULL AND rendicontazione.num_importo_totale_pagamenti IS NULL AND (tesoreria_iuv.num_importo IS NOT NULL AND export.num_e_dati_pag_importo_totale_pagato = tesoreria_iuv.num_importo OR tesoreria_f2k_iuv.num_ip_bolletta IS NOT NULL AND export.num_e_dati_pag_importo_totale_pagato = tesoreria_f2k_iuv.num_ip_bolletta);


-- Funzione get_import_export_rend_tes_function: viene invocata nella ricerca delle riconciliazioni (risultato)


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
   AND   CASE WHEN (_identificativo_flusso_rendicontazione <> '') IS TRUE THEN upper(tes.identificativo_flusso_rendicontazione) like upper('%' || _identificativo_flusso_rendicontazione || '%') ELSE true END           
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


-- FUNZIONE get_count_import_export_rend_tes_function: viene invocata nella ricerca delle riconciliazioni (conta)

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
   AND   CASE WHEN (_identificativo_flusso_rendicontazione <> '') IS TRUE THEN upper(tes.identificativo_flusso_rendicontazione) like upper('%' || _identificativo_flusso_rendicontazione || '%') ELSE true END           
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



-- Funzione get_rendicontazione_subset_function (risultato)


CREATE OR REPLACE FUNCTION get_rendicontazione_subset_function(
    IN _codice_ipa_ente character varying,
    IN _identificativo_flusso_rendicontazione character varying,
    IN _identificativo_univoco_regolamento character varying,
    IN _dt_data_regolamento_da date,
    IN _dt_data_regolamento_a date,
    IN dt_data_ultimo_aggiornamento_da date,
    IN dt_data_ultimo_aggiornamento_a date,
    IN _classificazione_completezza character varying,
    IN _cod_tipo_dovuto character varying,
    IN _cod_fed_user_id character varying,
    IN _flagnascosto boolean,
    IN _page integer,
    IN _size integer)
  RETURNS TABLE(identificativo_flusso_rendicontazione character varying, codice_ipa_ente character varying, singolo_importo_commissione_carico_pa character varying, bilancio character varying, data_ora_flusso_rendicontazione character varying, identificativo_univoco_regolamento character varying, dt_data_regolamento date, de_data_regolamento character varying, importo_totale_pagamenti character varying, de_anno_bolletta character varying, cod_bolletta character varying, cod_id_dominio character varying, dt_ricezione timestamp without time zone, de_data_ricezione character varying, de_anno_documento character varying, cod_documento character varying, de_anno_provvisorio character varying, cod_provvisorio character varying, classificazione_completezza character varying, dt_data_ultimo_aggiornamento date, de_data_ultimo_aggiornamento character varying, indice_dati_singolo_pagamento integer, cod_iuf_key character varying) AS
$BODY$
   SELECT 
      DISTINCT (upper(tes.identificativo_flusso_rendicontazione)),tes.codice_ipa_ente,tes.singolo_importo_commissione_carico_pa,tes.bilancio,tes.data_ora_flusso_rendicontazione,tes.identificativo_univoco_regolamento,tes.dt_data_regolamento,tes.de_data_regolamento,tes.importo_totale_pagamenti,tes.de_anno_bolletta,tes.cod_bolletta,tes.cod_id_dominio,tes.dt_ricezione,tes.de_data_ricezione,tes.de_anno_documento,tes.cod_documento,tes.de_anno_provvisorio,tes.cod_provvisorio,tes.classificazione_completezza, MAX(tes.dt_data_ultimo_aggiornamento)as dt_data_ultimo_aggiornamento, to_char(MAX(tes.dt_data_ultimo_aggiornamento), 'DD-MM-YYYY') as de_data_ultimo_aggiornamento, tes.indice_dati_singolo_pagamento,tes.cod_iuf_key
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT mseg.*, ment.* FROM mygov_segnalazione as mseg INNER JOIN mygov_ente as ment ON mseg.mygov_ente_id = ment.mygov_ente_id WHERE mseg.flg_attivo = true AND mseg.classificazione_completezza = $8) as ms 
     ON ms.cod_ipa_ente = tes.codice_ipa_ente AND (ms.cod_iuf = tes.cod_iuf_key OR (ms.cod_iuf IS NULL and tes.cod_iuf_key IS NULL))
   WHERE  
         CASE WHEN $1 IS NOT NULL AND $1!='' THEN tes.codice_ipa_ente = $1 ELSE true END
     AND CASE WHEN $2 IS NOT NULL AND $2!='' THEN upper(tes.identificativo_flusso_rendicontazione) like upper('%' || $2 || '%') ELSE true END
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
   GROUP BY upper(tes.identificativo_flusso_rendicontazione),tes.codice_ipa_ente,tes.singolo_importo_commissione_carico_pa,tes.bilancio,tes.data_ora_flusso_rendicontazione,tes.identificativo_univoco_regolamento,tes.dt_data_regolamento,tes.de_data_regolamento,tes.importo_totale_pagamenti,tes.de_anno_bolletta,tes.cod_bolletta,tes.cod_id_dominio,tes.dt_ricezione,tes.de_data_ricezione,tes.de_anno_documento,tes.cod_documento,tes.de_anno_provvisorio,tes.cod_provvisorio,tes.classificazione_completezza, tes.indice_dati_singolo_pagamento,tes.cod_iuf_key
   ORDER BY dt_data_ultimo_aggiornamento DESC
   OFFSET CASE WHEN ($12 IS NOT NULL) THEN (($12 - 1)*$13) ELSE 0 END 
   LIMIT CASE WHEN ($13 IS NOT NULL) THEN $13 ELSE 5 END;
$BODY$
  LANGUAGE sql VOLATILE
  COST 100
  ROWS 1000;

-- Funzione get_count_rendicontazione_subset_function (conta)

CREATE OR REPLACE FUNCTION get_count_rendicontazione_subset_function(
    _codice_ipa_ente character varying,
    _identificativo_flusso_rendicontazione character varying,
    _identificativo_univoco_regolamento character varying,
    _dt_data_regolamento_da date,
    _dt_data_regolamento_a date,
    _dt_data_ultimo_aggiornamento_da date,
    _dt_data_ultimo_aggiornamento_a date,
    _classificazione_completezza character varying,
    _cod_tipo_dovuto character varying,
    _cod_fed_user_id character varying,
    _flagnascosto boolean)
  RETURNS bigint AS
$BODY$
   SELECT 
         COUNT( DISTINCT(upper(tes.identificativo_flusso_rendicontazione)))
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 
  LEFT OUTER JOIN (SELECT mseg.*, ment.* FROM mygov_segnalazione as mseg INNER JOIN mygov_ente as ment ON mseg.mygov_ente_id = ment.mygov_ente_id WHERE mseg.flg_attivo = true AND mseg.classificazione_completezza = $8) as ms 
     ON ms.cod_ipa_ente = tes.codice_ipa_ente AND (ms.cod_iuf = tes.cod_iuf_key OR (ms.cod_iuf IS NULL and tes.cod_iuf_key IS NULL))
   WHERE  
         CASE WHEN $1 IS NOT NULL AND $1!='' THEN tes.codice_ipa_ente = $1 ELSE true END
     AND CASE WHEN $2 IS NOT NULL AND $2!='' THEN upper(tes.identificativo_flusso_rendicontazione) like upper('%' || $2 || '%') ELSE true END
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

-- FUNCTION get_rendicontazione_tesoreria_subset_function (risultato)


CREATE OR REPLACE FUNCTION get_rendicontazione_tesoreria_subset_function(
    IN _codice_ipa_ente character varying,
    IN _identificativo_flusso_rendicontazione character varying,
    IN _identificativo_univoco_regolamento character varying,
    IN _dt_data_regolamento_da date,
    IN _dt_data_regolamento_a date,
    IN _dt_data_contabile_da date,
    IN _dt_data_contabile_a date,
    IN _dt_data_valuta_da date,
    IN _dt_data_valuta_a date,
    IN _dt_data_ultimo_aggiornamento_da date,
    IN _dt_data_ultimo_aggiornamento_a date,
    IN _de_causale_t text,
    IN _importo character varying,
    IN _conto character varying,
    IN _codor1 character varying,
    IN _cod_tipo_dovuto character varying,
    IN _is_cod_tipo_dovuto_present boolean,
    IN _cod_fed_user_id character varying,
    IN _flagnascosto boolean,
    IN _classificazione_completezza character varying,
    IN _page integer,
    IN _size integer)
  RETURNS TABLE(identificativo_flusso_rendicontazione character varying, codice_ipa_ente character varying, dt_data_esecuzione_pagamento date, de_data_esecuzione_pagamento character varying, singolo_importo_commissione_carico_pa character varying, cod_conto character varying, dt_data_contabile date, de_data_contabile character varying, dt_data_valuta date, de_data_valuta character varying, num_importo numeric, de_importo character varying, cod_or1 text, de_anno_bolletta character varying, cod_bolletta character varying, cod_id_dominio character varying, dt_ricezione timestamp without time zone, de_data_ricezione character varying, de_anno_documento character varying, cod_documento character varying, de_anno_provvisorio character varying, cod_provvisorio character varying, de_causale_t text, classificazione_completezza character varying, dt_data_ultimo_aggiornamento date, de_data_ultimo_aggiornamento character varying, data_ora_flusso_rendicontazione character varying, identificativo_univoco_regolamento character varying, dt_data_regolamento date, de_data_regolamento character varying, importo_totale_pagamenti character varying, cod_iuf_key character varying) AS
$BODY$
   SELECT 
        DISTINCT (upper(iert.identificativo_flusso_rendicontazione)),
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
   AND   CASE WHEN _identificativo_flusso_rendicontazione IS NOT NULL THEN upper(iert.identificativo_flusso_rendicontazione) like upper('%' || _identificativo_flusso_rendicontazione || '%') ELSE true END
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

-- get_count_rendicontazione_tesoreria_subset_function (conta)

CREATE OR REPLACE FUNCTION get_count_rendicontazione_tesoreria_subset_function(
    _codice_ipa_ente character varying,
    _identificativo_flusso_rendicontazione character varying,
    _identificativo_univoco_regolamento character varying,
    _dt_data_regolamento_da date,
    _dt_data_regolamento_a date,
    _dt_data_contabile_da date,
    _dt_data_contabile_a date,
    _dt_data_valuta_da date,
    _dt_data_valuta_a date,
    _dt_data_ultimo_aggiornamento_da date,
    _dt_data_ultimo_aggiornamento_a date,
    _de_causale_t text,
    _importo character varying,
    _conto character varying,
    _codor1 character varying,
    _cod_tipo_dovuto character varying,
    _is_cod_tipo_dovuto_present boolean,
    _cod_fed_user_id character varying,
    _flagnascosto boolean,
    _classificazione_completezza character varying)
  RETURNS bigint AS
$BODY$
   SELECT 
        count(DISTINCT(iert.codice_ipa_ente, upper(iert.identificativo_flusso_rendicontazione)))
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
   AND   CASE WHEN _identificativo_flusso_rendicontazione IS NOT NULL THEN upper(iert.identificativo_flusso_rendicontazione) like upper('%' || _identificativo_flusso_rendicontazione || '%') ELSE true END
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

-- FUNZIONI get_flusso_rendicontazione_function (risultato)

CREATE OR REPLACE FUNCTION get_flusso_rendicontazione_function(
    IN _mygov_ente_id bigint,
    IN _dt_data_regolamento_da date,
    IN _dt_data_regolamento_a date,
    IN _cod_iuf character varying,
    IN _identificativo_univoco_regolamento character varying,
    IN _page integer,
    IN _page_size integer)
  RETURNS TABLE(cod_identificativo_flusso character varying, mygov_ente_id bigint, mygov_manage_flusso_id bigint, identificativo_psp character varying, dt_data_ora_flusso timestamp without time zone, cod_identificativo_univoco_regolamento character varying, dt_data_regolamento date, cod_ist_mitt_id_univ_mitt_tipo_identificativo_univoco character, cod_ist_mitt_id_univ_mitt_codice_identificativo_univoco character varying, de_ist_mitt_denominazione_mittente character varying, cod_ist_ricev_id_univ_ricev_tipo_identificativo_univoco character, cod_ist_ricev_id_univ_ricev_codice_identificativo_univoco character varying, de_ist_ricev_denominazione_ricevente character varying, num_numero_totale_pagamenti numeric, num_importo_totale_pagamenti numeric, dt_acquisizione date, codice_bic_banca_di_riversamento character varying) AS
$BODY$
  SELECT 
    DISTINCT(upper(rend.cod_identificativo_flusso)),
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
  AND   CASE WHEN (_cod_iuf <> '') IS TRUE THEN upper(rend.cod_identificativo_flusso) like upper('%' || _cod_iuf || '%') ELSE true END
  AND   CASE WHEN (_identificativo_univoco_regolamento <> '') IS TRUE THEN rend.cod_identificativo_univoco_regolamento = _identificativo_univoco_regolamento ELSE true END
      
  ORDER BY rend.dt_data_regolamento DESC, rend.dt_data_ora_flusso DESC, upper(rend.cod_identificativo_flusso) DESC
  OFFSET CASE WHEN (_page IS NOT NULL) THEN ((_page - 1) * _page_size) ELSE 0 END 
  LIMIT CASE WHEN (_page_size IS NOT NULL) THEN _page_size ELSE 5 END;
$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;

-- FUNZIONI get_count_flusso_rendicontazione_function (conta)

CREATE OR REPLACE FUNCTION get_count_flusso_rendicontazione_function(
    _mygov_ente_id bigint,
    _dt_data_regolamento_da date,
    _dt_data_regolamento_a date,
    _cod_iuf character varying,
    _identificativo_univoco_regolamento character varying)
  RETURNS bigint AS
$BODY$
  SELECT count(DISTINCT(upper(rend.cod_identificativo_flusso)))
  FROM mygov_flusso_rendicontazione rend
     
  WHERE CASE WHEN _mygov_ente_id IS NOT NULL THEN rend.mygov_ente_id = _mygov_ente_id ELSE true END
  AND   CASE WHEN _dt_data_regolamento_da IS NOT NULL THEN rend.dt_data_regolamento >= _dt_data_regolamento_da ELSE true END
  AND   CASE WHEN _dt_data_regolamento_a IS NOT NULL THEN rend.dt_data_regolamento <= _dt_data_regolamento_a ELSE true END
  AND   CASE WHEN (_cod_iuf <> '') IS TRUE THEN upper(rend.cod_identificativo_flusso) like upper('%' || _cod_iuf || '%') ELSE true END
  AND   CASE WHEN (_identificativo_univoco_regolamento <> '') IS TRUE THEN rend.cod_identificativo_univoco_regolamento = _identificativo_univoco_regolamento ELSE true END;
$BODY$
  LANGUAGE sql STABLE
  COST 100;
