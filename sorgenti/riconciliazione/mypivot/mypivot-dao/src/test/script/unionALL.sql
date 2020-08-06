-- View: mygov_export_rendicontazione

-- DROP VIEW mygov_export_rendicontazione;

CREATE OR REPLACE VIEW mygov_export_rendicontazione AS 
         SELECT ente.cod_ipa_ente AS codice_ipa_ente
              , export.de_nome_flusso AS nome_flusso_import_ente
              , export.num_riga_flusso AS riga_flusso_import_ente
              , export.cod_iud AS codice_iud
              , COALESCE(export.cod_rp_silinviarp_id_univoco_versamento, ''::character varying) AS codice_iuv
              , export.de_e_versione_oggetto AS versione_oggetto
              , export.cod_e_dom_id_dominio AS identificativo_dominio
              , export.cod_e_dom_id_stazione_richiedente AS identificativo_stazione_richiedente
              , export.cod_e_id_messaggio_ricevuta AS identificativo_messaggio_ricevuta
              , export.dt_e_data_ora_messaggio_ricevuta AS data_ora_messaggio_ricevuta
              , export.cod_e_riferimento_messaggio_richiesta AS riferimento_messaggio_richiesta
              , export.dt_e_riferimento_data_richiesta AS riferimento_data_richiesta
              , export.cod_e_istit_att_id_univ_att_tipo_id_univoco AS tipo_identificativo_univoco_attestante
              , export.cod_e_istit_att_id_univ_att_codice_id_univoco AS codice_identificativo_univoco_attestante
              , COALESCE(export.de_e_istit_att_denominazione_attestante, ''::character varying) AS denominazione_attestante
              , export.cod_e_istit_att_codice_unit_oper_attestante AS codice_unit_oper_attestante
              , export.de_e_istit_att_denom_unit_oper_attestante AS denom_unit_oper_attestante
              , export.de_e_istit_att_indirizzo_attestante AS indirizzo_attestante
              , export.de_e_istit_att_civico_attestante AS civico_attestante
              , export.cod_e_istit_att_cap_attestante AS cap_attestante
              , export.de_e_istit_att_localita_attestante AS localita_attestante
              , export.de_e_istit_att_provincia_attestante AS provincia_attestante
              , export.cod_e_istit_att_nazione_attestante AS nazione_attestante
              , export.cod_e_ente_benef_id_univ_benef_tipo_id_univoco AS tipo_identificativo_univoco_beneficiario
              , export.cod_e_ente_benef_id_univ_benef_codice_id_univoco AS codice_identificativo_univoco_beneficiario
              , export.de_e_ente_benef_denominazione_beneficiario AS denominazione_beneficiario
              , export.cod_e_ente_benef_codice_unit_oper_beneficiario AS codice_unit_oper_beneficiario
              , export.de_e_ente_benef_denom_unit_oper_beneficiario AS denom_unit_oper_beneficiario
              , export.de_e_ente_benef_indirizzo_beneficiario AS indirizzo_beneficiario
              , export.de_e_ente_benef_civico_beneficiario AS civico_beneficiario
              , export.cod_e_ente_benef_cap_beneficiario AS cap_beneficiario
              , export.de_e_ente_benef_localita_beneficiario AS localita_beneficiario
              , export.de_e_ente_benef_provincia_beneficiario AS provincia_beneficiario
              , export.cod_e_ente_benef_nazione_beneficiario AS nazione_beneficiario
              , export.cod_e_sogg_vers_id_univ_vers_tipo_id_univoco AS tipo_identificativo_univoco_versante
              , COALESCE(export.cod_e_sogg_vers_id_univ_vers_codice_id_univoco, ''::character varying) AS codice_identificativo_univoco_versante
              , COALESCE(export.cod_e_sogg_vers_anagrafica_versante, ''::character varying) AS anagrafica_versante
              , export.de_e_sogg_vers_indirizzo_versante AS indirizzo_versante
              , export.de_e_sogg_vers_civico_versante AS civico_versante
              , export.cod_e_sogg_vers_cap_versante AS cap_versante
              , export.de_e_sogg_vers_localita_versante AS localita_versante
              , export.de_e_sogg_vers_provincia_versante AS provincia_versante
              , export.cod_e_sogg_vers_nazione_versante AS nazione_versante
              , export.de_e_sogg_vers_email_versante AS email_versante
              , export.cod_e_sogg_pag_id_univ_pag_tipo_id_univoco AS tipo_identificativo_univoco_pagatore
              , COALESCE(export.cod_e_sogg_pag_id_univ_pag_codice_id_univoco, ''::character varying) AS codice_identificativo_univoco_pagatore
              , COALESCE(export.cod_e_sogg_pag_anagrafica_pagatore, ''::character varying) AS anagrafica_pagatore
              , export.de_e_sogg_pag_indirizzo_pagatore AS indirizzo_pagatore, export.de_e_sogg_pag_civico_pagatore AS civico_pagatore
              , export.cod_e_sogg_pag_cap_pagatore AS cap_pagatore, export.de_e_sogg_pag_localita_pagatore AS localita_pagatore
              , export.de_e_sogg_pag_provincia_pagatore AS provincia_pagatore, export.cod_e_sogg_pag_nazione_pagatore AS nazione_pagatore
              , export.de_e_sogg_pag_email_pagatore AS email_pagatore, export.cod_e_dati_pag_codice_esito_pagamento AS codice_esito_pagamento
              , export.num_e_dati_pag_importo_totale_pagato AS importo_totale_pagato
              , export.cod_e_dati_pag_id_univoco_versamento AS identificativo_univoco_versamento
              , export.cod_e_dati_pag_codice_contesto_pagamento AS codice_contesto_pagamento
              , export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato AS singolo_importo_pagato
              , export.de_e_dati_pag_dati_sing_pag_esito_singolo_pagamento AS esito_singolo_pagamento
              , export.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento AS data_esito_singolo_pagamento
              , export.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss AS identificativo_univoco_riscossione
              , COALESCE(export.de_e_dati_pag_dati_sing_pag_causale_versamento, ''::character varying) AS causale_versamento
              , export.de_e_dati_pag_dati_sing_pag_dati_specifici_riscossione AS dati_specifici_riscossione
              , export.cod_tipo_dovuto AS tipo_dovuto
              , COALESCE(rendicontazione.cod_identificativo_flusso, 'n/a'::character varying) AS identificativo_flusso_rendicontazione
              , rendicontazione.dt_data_ora_flusso AS data_ora_flusso_rendicontazione
              , COALESCE(rendicontazione.cod_identificativo_univoco_regolamento, 'n/a'::character varying) AS identificativo_univoco_regolamento
              , CASE
                    WHEN rendicontazione.dt_data_regolamento IS NOT NULL THEN rendicontazione.dt_data_regolamento
                    ELSE ( SELECT export.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento + ente.num_giorni_pagamento_presunti
                       FROM mygov_ente ente
                      WHERE ente.mygov_ente_id = export.mygov_ente_id)
                END AS data_regolamento
              , COALESCE(rendicontazione.num_numero_totale_pagamenti, 0::numeric) AS numero_totale_pagamenti
              , COALESCE(rendicontazione.num_importo_totale_pagamenti, 0::numeric) AS importo_totale_pagamenti
              , rendicontazione.dt_acquisizione AS data_acquisizione
           FROM mygov_flusso_export export
      JOIN mygov_ente ente ON export.mygov_ente_id = ente.mygov_ente_id
   JOIN mygov_flusso_rendicontazione rendicontazione ON export.cod_e_istit_att_id_univ_att_codice_id_univoco::text = rendicontazione.identificativo_psp::text AND export.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss::text = rendicontazione.cod_dati_sing_pagam_identificativo_univoco_riscossione::text
UNION ALL 
         SELECT ente.cod_ipa_ente AS codice_ipa_ente, export.de_nome_flusso AS nome_flusso_import_ente, export.num_riga_flusso AS riga_flusso_import_ente, export.cod_iud AS codice_iud, COALESCE(export.cod_rp_silinviarp_id_univoco_versamento, ''::character varying) AS codice_iuv, export.de_e_versione_oggetto AS versione_oggetto, export.cod_e_dom_id_dominio AS identificativo_dominio, export.cod_e_dom_id_stazione_richiedente AS identificativo_stazione_richiedente, export.cod_e_id_messaggio_ricevuta AS identificativo_messaggio_ricevuta, export.dt_e_data_ora_messaggio_ricevuta AS data_ora_messaggio_ricevuta, export.cod_e_riferimento_messaggio_richiesta AS riferimento_messaggio_richiesta, export.dt_e_riferimento_data_richiesta AS riferimento_data_richiesta, export.cod_e_istit_att_id_univ_att_tipo_id_univoco AS tipo_identificativo_univoco_attestante, export.cod_e_istit_att_id_univ_att_codice_id_univoco AS codice_identificativo_univoco_attestante, COALESCE(export.de_e_istit_att_denominazione_attestante, ''::character varying) AS denominazione_attestante, export.cod_e_istit_att_codice_unit_oper_attestante AS codice_unit_oper_attestante, export.de_e_istit_att_denom_unit_oper_attestante AS denom_unit_oper_attestante, export.de_e_istit_att_indirizzo_attestante AS indirizzo_attestante, export.de_e_istit_att_civico_attestante AS civico_attestante, export.cod_e_istit_att_cap_attestante AS cap_attestante, export.de_e_istit_att_localita_attestante AS localita_attestante, export.de_e_istit_att_provincia_attestante AS provincia_attestante, export.cod_e_istit_att_nazione_attestante AS nazione_attestante, export.cod_e_ente_benef_id_univ_benef_tipo_id_univoco AS tipo_identificativo_univoco_beneficiario, export.cod_e_ente_benef_id_univ_benef_codice_id_univoco AS codice_identificativo_univoco_beneficiario, export.de_e_ente_benef_denominazione_beneficiario AS denominazione_beneficiario, export.cod_e_ente_benef_codice_unit_oper_beneficiario AS codice_unit_oper_beneficiario, export.de_e_ente_benef_denom_unit_oper_beneficiario AS denom_unit_oper_beneficiario, export.de_e_ente_benef_indirizzo_beneficiario AS indirizzo_beneficiario, export.de_e_ente_benef_civico_beneficiario AS civico_beneficiario, export.cod_e_ente_benef_cap_beneficiario AS cap_beneficiario, export.de_e_ente_benef_localita_beneficiario AS localita_beneficiario, export.de_e_ente_benef_provincia_beneficiario AS provincia_beneficiario, export.cod_e_ente_benef_nazione_beneficiario AS nazione_beneficiario, export.cod_e_sogg_vers_id_univ_vers_tipo_id_univoco AS tipo_identificativo_univoco_versante, COALESCE(export.cod_e_sogg_vers_id_univ_vers_codice_id_univoco, ''::character varying) AS codice_identificativo_univoco_versante, COALESCE(export.cod_e_sogg_vers_anagrafica_versante, ''::character varying) AS anagrafica_versante, export.de_e_sogg_vers_indirizzo_versante AS indirizzo_versante, export.de_e_sogg_vers_civico_versante AS civico_versante, export.cod_e_sogg_vers_cap_versante AS cap_versante, export.de_e_sogg_vers_localita_versante AS localita_versante, export.de_e_sogg_vers_provincia_versante AS provincia_versante, export.cod_e_sogg_vers_nazione_versante AS nazione_versante, export.de_e_sogg_vers_email_versante AS email_versante, export.cod_e_sogg_pag_id_univ_pag_tipo_id_univoco AS tipo_identificativo_univoco_pagatore, COALESCE(export.cod_e_sogg_pag_id_univ_pag_codice_id_univoco, ''::character varying) AS codice_identificativo_univoco_pagatore, COALESCE(export.cod_e_sogg_pag_anagrafica_pagatore, ''::character varying) AS anagrafica_pagatore, export.de_e_sogg_pag_indirizzo_pagatore AS indirizzo_pagatore, export.de_e_sogg_pag_civico_pagatore AS civico_pagatore, export.cod_e_sogg_pag_cap_pagatore AS cap_pagatore, export.de_e_sogg_pag_localita_pagatore AS localita_pagatore, export.de_e_sogg_pag_provincia_pagatore AS provincia_pagatore, export.cod_e_sogg_pag_nazione_pagatore AS nazione_pagatore, export.de_e_sogg_pag_email_pagatore AS email_pagatore, export.cod_e_dati_pag_codice_esito_pagamento AS codice_esito_pagamento, export.num_e_dati_pag_importo_totale_pagato AS importo_totale_pagato, export.cod_e_dati_pag_id_univoco_versamento AS identificativo_univoco_versamento, export.cod_e_dati_pag_codice_contesto_pagamento AS codice_contesto_pagamento, export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato AS singolo_importo_pagato, export.de_e_dati_pag_dati_sing_pag_esito_singolo_pagamento AS esito_singolo_pagamento, export.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento AS data_esito_singolo_pagamento, export.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss AS identificativo_univoco_riscossione, COALESCE(export.de_e_dati_pag_dati_sing_pag_causale_versamento, ''::character varying) AS causale_versamento, export.de_e_dati_pag_dati_sing_pag_dati_specifici_riscossione AS dati_specifici_riscossione, export.cod_tipo_dovuto AS tipo_dovuto, 'n/a'::character varying AS identificativo_flusso_rendicontazione, NULL::unknown AS data_ora_flusso_rendicontazione, 'n/a'::character varying AS identificativo_univoco_regolamento, ( SELECT export.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento + ente.num_giorni_pagamento_presunti
                   FROM mygov_ente ente
                  WHERE ente.mygov_ente_id = export.mygov_ente_id) AS data_regolamento, 0::numeric AS numero_totale_pagamenti, 0::numeric AS importo_totale_pagamenti, export.dt_acquisizione AS data_acquisizione
           FROM mygov_flusso_export export
      JOIN mygov_ente ente ON export.mygov_ente_id = ente.mygov_ente_id
     WHERE NOT (EXISTS ( SELECT 1
              FROM mygov_flusso_rendicontazione rendicontazione
             WHERE export.cod_e_istit_att_id_univ_att_codice_id_univoco::text = rendicontazione.identificativo_psp::text AND export.cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss::text = rendicontazione.cod_dati_sing_pagam_identificativo_univoco_riscossione::text));



