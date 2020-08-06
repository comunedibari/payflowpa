SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;
SET SESSION AUTHORIZATION DEFAULT;
SET role '__TAG_MYPIVOT_DB_USERNAME__';

-- SET search_path = public, pg_catalog;
SET search_path = '__TAG_MYPIVOT_DB_SCHEMA__';

DROP MATERIALIZED VIEW vm_statistica_ente_anno_mese_giorno_uff_td;
DROP MATERIALIZED VIEW vm_statistica_ente_anno_mese_giorno_uff_td_cap;
DROP MATERIALIZED VIEW vm_statistica_ente_anno_mese_giorno_uff_td_cap_acc;

DROP MATERIALIZED VIEW vm_statistica_ente_anno_mese_uff_td;
DROP MATERIALIZED VIEW vm_statistica_ente_anno_mese_uff_td_cap;
DROP MATERIALIZED VIEW vm_statistica_ente_anno_mese_uff_td_cap_acc;


CREATE MATERIALIZED VIEW vm_statistica_ente_anno_mese_giorno_uff_td
 AS 
 SELECT subq.mygov_ente_id,
    subq.anno,
    subq.mese,
    subq.giorno,
    subq.cod_uff,
    coalesce(uff.de_ufficio, 'n/a') AS de_uff,
    subq.cod_td,
    td.de_tipo AS de_td,
    sum(subq.imp_pag) AS imp_pag,
    sum(subq.imp_rend) AS imp_rend,
    sum(subq.imp_inc) AS imp_inc
   FROM ( SELECT p.mygov_ente_id,
            date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer AS anno,
            date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer AS mese,
            date_part('day'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer AS giorno,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            sum(ad.num_importo) AS imp_pag,
            0 AS imp_rend,
            0 AS imp_inc
           FROM mygov_flusso_export p
             JOIN mygov_accertamento_dettaglio ad ON p.cod_iud::text = ad.cod_iud::text AND p.cod_tipo_dovuto::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND p.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text
          GROUP BY p.mygov_ente_id, date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer, date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer, date_part('day'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto
        UNION
         SELECT r.mygov_ente_id,
            date_part('years'::text, r.dt_data_regolamento_r)::integer AS anno,
            date_part('month'::text, r.dt_data_regolamento_r)::integer AS mese,
            date_part('day'::text, r.dt_data_regolamento_r)::integer AS giorno,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            0 AS imp_pag,
            sum(ad.num_importo) AS imp_rend,
            0 AS imp_inc
           FROM mygov_import_export_rendicontazione_tesoreria_completa r
             JOIN mygov_accertamento_dettaglio ad ON r.cod_iud_e::text = ad.cod_iud::text AND r.cod_tipo_dovuto_e::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND r.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text AND r.classificazione_completezza::text = 'RT_IUF'::text
          GROUP BY r.mygov_ente_id, date_part('years'::text, r.dt_data_regolamento_r)::integer, date_part('month'::text, r.dt_data_regolamento_r)::integer, date_part('day'::text, r.dt_data_regolamento_r)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto
        UNION
         SELECT r.mygov_ente_id,
            date_part('years'::text, r.dt_data_valuta_t)::integer AS anno,
            date_part('month'::text, r.dt_data_valuta_t)::integer AS mese,
            date_part('day'::text, r.dt_data_valuta_t)::integer AS giorno,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            0 AS imp_pag,
            0 AS imp_rend,
            sum(ad.num_importo) AS imp_inc
           FROM mygov_import_export_rendicontazione_tesoreria_completa r
             JOIN mygov_accertamento_dettaglio ad ON r.cod_iud_e::text = ad.cod_iud::text AND r.cod_tipo_dovuto_e::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND r.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text AND (r.classificazione_completezza::text = 'RT_IUF_TES'::text OR r.classificazione_completezza::text = 'RT_TES'::text)
          GROUP BY r.mygov_ente_id, date_part('years'::text, r.dt_data_valuta_t)::integer, date_part('month'::text, r.dt_data_valuta_t)::integer, date_part('day'::text, r.dt_data_valuta_t)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto) subq
     JOIN mygov_ente_tipo_dovuto td ON td.mygov_ente_id = subq.mygov_ente_id AND td.cod_tipo::text = subq.cod_td::text
     LEFT JOIN (select mygov_ente_id, cod_tipo_dovuto, cod_ufficio, de_anno_esercizio, max(de_ufficio) as de_ufficio
                from mygov_anagrafica_uff_cap_acc
                where      de_ufficio <> 'n/a'
                group by mygov_ente_id, cod_tipo_dovuto, cod_ufficio, de_anno_esercizio
               ) uff ON uff.mygov_ente_id = subq.mygov_ente_id
                                              AND uff.cod_tipo_dovuto::text = subq.cod_td::text
                                              AND uff.cod_ufficio::text = subq.cod_uff::text
                                              AND uff.de_anno_esercizio::text = subq.anno::text
  GROUP BY subq.mygov_ente_id, subq.anno, subq.mese, subq.giorno, subq.cod_uff, uff.de_ufficio, subq.cod_td, td.de_tipo
  ORDER BY subq.mygov_ente_id, subq.anno, subq.mese, subq.giorno, subq.cod_uff, uff.de_ufficio, subq.cod_td, td.de_tipo
WITH DATA;

CREATE MATERIALIZED VIEW vm_statistica_ente_anno_mese_uff_td AS 
 SELECT subq.mygov_ente_id,
   subq.anno,
   subq.mese,
   subq.cod_uff,
   coalesce(uff.de_ufficio, 'n/a') AS de_uff,
   subq.cod_td,
   td.de_tipo AS de_td,
   sum(subq.imp_pag) AS imp_pag,
   sum(subq.imp_rend) AS imp_rend,
   sum(subq.imp_inc) AS imp_inc
   FROM ( SELECT p.mygov_ente_id,
            date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer AS anno,
            date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer AS mese,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            sum(ad.num_importo) AS imp_pag,
            0 AS imp_rend,
            0 AS imp_inc
           FROM mygov_flusso_export p
             JOIN mygov_accertamento_dettaglio ad ON p.cod_iud::text = ad.cod_iud::text AND p.cod_tipo_dovuto::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND p.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text
          GROUP BY p.mygov_ente_id, date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer, date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto
        UNION
         SELECT r.mygov_ente_id,
            date_part('years'::text, r.dt_data_regolamento_r)::integer AS anno,
            date_part('month'::text, r.dt_data_regolamento_r)::integer AS mese,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            0 AS imp_pag,
            sum(ad.num_importo) AS imp_rend,
            0 AS imp_inc
           FROM mygov_import_export_rendicontazione_tesoreria_completa r
             JOIN mygov_accertamento_dettaglio ad ON r.cod_iud_e::text = ad.cod_iud::text AND r.cod_tipo_dovuto_e::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND r.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text AND r.classificazione_completezza::text = 'RT_IUF'::text
          GROUP BY r.mygov_ente_id, date_part('years'::text, r.dt_data_regolamento_r)::integer, date_part('month'::text, r.dt_data_regolamento_r)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto
        UNION
         SELECT r.mygov_ente_id,
            date_part('years'::text, r.dt_data_valuta_t)::integer AS anno,
            date_part('month'::text, r.dt_data_valuta_t)::integer AS mese,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            0 AS imp_pag,
            0 AS imp_rend,
            sum(ad.num_importo) AS imp_inc
           FROM mygov_import_export_rendicontazione_tesoreria_completa r
             JOIN mygov_accertamento_dettaglio ad ON r.cod_iud_e::text = ad.cod_iud::text AND r.cod_tipo_dovuto_e::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND r.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text AND (r.classificazione_completezza::text = 'RT_IUF_TES'::text OR r.classificazione_completezza::text = 'RT_TES'::text)
          GROUP BY r.mygov_ente_id, date_part('years'::text, r.dt_data_valuta_t)::integer, date_part('month'::text, r.dt_data_valuta_t)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto) subq
     JOIN mygov_ente_tipo_dovuto td ON td.mygov_ente_id = subq.mygov_ente_id AND td.cod_tipo::text = subq.cod_td::text
     LEFT JOIN (select mygov_ente_id, cod_tipo_dovuto, cod_ufficio, de_anno_esercizio, max(de_ufficio) as de_ufficio
                from mygov_anagrafica_uff_cap_acc
                where      de_ufficio <> 'n/a'
                group by mygov_ente_id, cod_tipo_dovuto, cod_ufficio, de_anno_esercizio
               ) uff ON uff.mygov_ente_id = subq.mygov_ente_id
                                              AND uff.cod_tipo_dovuto::text = subq.cod_td::text
                                              AND uff.cod_ufficio::text = subq.cod_uff::text
                                              AND uff.de_anno_esercizio::text = subq.anno::text
  GROUP BY subq.mygov_ente_id, subq.anno, subq.mese, subq.cod_uff, uff.de_ufficio, subq.cod_td, td.de_tipo
  ORDER BY subq.mygov_ente_id, subq.anno, subq.mese, subq.cod_uff, uff.de_ufficio, subq.cod_td, td.de_tipo
WITH DATA;

CREATE MATERIALIZED VIEW vm_statistica_ente_anno_mese_uff_td_cap AS 
 SELECT subq.mygov_ente_id,
    subq.anno,
    subq.mese,
    subq.cod_uff,
    coalesce(uff.de_ufficio, 'n/a') AS de_uff,
    subq.cod_td,
    td.de_tipo AS de_td,
    subq.cod_cap,
    coalesce(uff.de_capitolo, 'n/a') AS de_cap,
    sum(subq.imp_pag) AS imp_pag,
    sum(subq.imp_rend) AS imp_rend,
    sum(subq.imp_inc) AS imp_inc
   FROM ( SELECT p.mygov_ente_id,
            date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer AS anno,
            date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer AS mese,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            ad.cod_capitolo AS cod_cap,
            sum(ad.num_importo) AS imp_pag,
            0 AS imp_rend,
            0 AS imp_inc
           FROM mygov_flusso_export p
             JOIN mygov_accertamento_dettaglio ad ON p.cod_iud::text = ad.cod_iud::text AND p.cod_tipo_dovuto::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND p.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text
          GROUP BY p.mygov_ente_id, date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer, date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto, ad.cod_capitolo
        UNION
         SELECT r.mygov_ente_id,
            date_part('years'::text, r.dt_data_regolamento_r)::integer AS anno,
            date_part('month'::text, r.dt_data_regolamento_r)::integer AS mese,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            ad.cod_capitolo AS cod_cap,
            0 AS imp_pag,
            sum(ad.num_importo) AS imp_rend,
            0 AS imp_inc
           FROM mygov_import_export_rendicontazione_tesoreria_completa r
             JOIN mygov_accertamento_dettaglio ad ON r.cod_iud_e::text = ad.cod_iud::text AND r.cod_tipo_dovuto_e::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND r.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text AND r.classificazione_completezza::text = 'RT_IUF'::text
          GROUP BY r.mygov_ente_id, date_part('years'::text, r.dt_data_regolamento_r)::integer, date_part('month'::text, r.dt_data_regolamento_r)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto, ad.cod_capitolo
        UNION
         SELECT r.mygov_ente_id,
            date_part('years'::text, r.dt_data_valuta_t)::integer AS anno,
            date_part('month'::text, r.dt_data_valuta_t)::integer AS mese,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            ad.cod_capitolo AS cod_cap,
            0 AS imp_pag,
            0 AS imp_rend,
            sum(ad.num_importo) AS imp_inc
           FROM mygov_import_export_rendicontazione_tesoreria_completa r
             JOIN mygov_accertamento_dettaglio ad ON r.cod_iud_e::text = ad.cod_iud::text AND r.cod_tipo_dovuto_e::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND r.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text AND (r.classificazione_completezza::text = 'RT_IUF_TES'::text OR r.classificazione_completezza::text = 'RT_TES'::text)
          GROUP BY r.mygov_ente_id, date_part('years'::text, r.dt_data_valuta_t)::integer, date_part('month'::text, r.dt_data_valuta_t)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto, ad.cod_capitolo) subq
     JOIN mygov_ente_tipo_dovuto td ON td.mygov_ente_id = subq.mygov_ente_id AND td.cod_tipo::text = subq.cod_td::text
     LEFT JOIN (select mygov_ente_id, cod_tipo_dovuto, cod_ufficio, de_anno_esercizio, cod_capitolo, max(de_capitolo) as de_capitolo, max(de_ufficio) as de_ufficio
                from mygov_anagrafica_uff_cap_acc
                where      de_ufficio <> 'n/a' and de_capitolo <> 'n/a'
                group by mygov_ente_id, cod_tipo_dovuto, cod_ufficio, de_anno_esercizio, cod_capitolo
                ) uff ON uff.mygov_ente_id = subq.mygov_ente_id
                                              AND uff.cod_tipo_dovuto::text = subq.cod_td::text
                                              AND uff.cod_ufficio::text = subq.cod_uff::text
                                              AND uff.de_anno_esercizio::text = subq.anno::text
  GROUP BY subq.mygov_ente_id, subq.anno, subq.mese, subq.cod_uff, uff.de_ufficio, subq.cod_td, td.de_tipo, subq.cod_cap, uff.de_capitolo
  ORDER BY subq.mygov_ente_id, subq.anno, subq.mese, subq.cod_uff, uff.de_ufficio, subq.cod_td, td.de_tipo, subq.cod_cap, uff.de_capitolo
WITH DATA;


CREATE MATERIALIZED VIEW public.vm_statistica_ente_anno_mese_giorno_uff_td_cap_acc
AS
 SELECT subq.mygov_ente_id,
    subq.anno,
    subq.mese,
    subq.giorno,
    subq.cod_uff,
    uff.de_ufficio AS de_uff,
    subq.cod_td,
    td.de_tipo AS de_td,
    subq.cod_cap,
    uff.de_capitolo AS de_cap,
    subq.cod_acc,
    uff.de_accertamento AS de_acc,
    sum(subq.imp_pag) AS imp_pag,
    sum(subq.imp_rend) AS imp_rend,
    sum(subq.imp_inc) AS imp_inc
   FROM ( SELECT p.mygov_ente_id,
            date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer AS anno,
            date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer AS mese,
            date_part('day'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer AS giorno,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            ad.cod_capitolo AS cod_cap,
            ad.cod_accertamento AS cod_acc,
            sum(ad.num_importo) AS imp_pag,
            0 AS imp_rend,
            0 AS imp_inc
           FROM mygov_flusso_export p
             JOIN mygov_accertamento_dettaglio ad ON p.cod_iud::text = ad.cod_iud::text AND p.cod_tipo_dovuto::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND p.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text AND ad.cod_accertamento::text <> 'n/a'::text
          GROUP BY p.mygov_ente_id, date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer, date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer, date_part('day'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto, ad.cod_capitolo, ad.cod_accertamento
        UNION
         SELECT r.mygov_ente_id,
            date_part('years'::text, r.dt_data_regolamento_r)::integer AS anno,
            date_part('month'::text, r.dt_data_regolamento_r)::integer AS mese,
            date_part('day'::text, r.dt_data_regolamento_r)::integer AS giorno,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            ad.cod_capitolo AS cod_cap,
            ad.cod_accertamento AS cod_acc,
            0 AS imp_pag,
            sum(ad.num_importo) AS imp_rend,
            0 AS imp_inc
           FROM mygov_import_export_rendicontazione_tesoreria_completa r
             JOIN mygov_accertamento_dettaglio ad ON r.cod_iud_e::text = ad.cod_iud::text AND r.cod_tipo_dovuto_e::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND r.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text AND ad.cod_accertamento::text <> 'n/a'::text AND r.classificazione_completezza::text = 'RT_IUF'::text
          GROUP BY r.mygov_ente_id, date_part('years'::text, r.dt_data_regolamento_r)::integer, date_part('month'::text, r.dt_data_regolamento_r)::integer, date_part('day'::text, r.dt_data_regolamento_r)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto, ad.cod_capitolo, ad.cod_accertamento
        UNION
         SELECT r.mygov_ente_id,
            date_part('years'::text, r.dt_data_valuta_t)::integer AS anno,
            date_part('month'::text, r.dt_data_valuta_t)::integer AS mese,
            date_part('day'::text, r.dt_data_valuta_t)::integer AS giorno,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            ad.cod_capitolo AS cod_cap,
            ad.cod_accertamento AS cod_acc,
            0 AS imp_pag,
            0 AS imp_rend,
            sum(ad.num_importo) AS imp_inc
           FROM mygov_import_export_rendicontazione_tesoreria_completa r
             JOIN mygov_accertamento_dettaglio ad ON r.cod_iud_e::text = ad.cod_iud::text AND r.cod_tipo_dovuto_e::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND r.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text AND ad.cod_accertamento::text <> 'n/a'::text AND (r.classificazione_completezza::text = 'RT_IUF_TES'::text OR r.classificazione_completezza::text = 'RT_TES'::text)
          GROUP BY r.mygov_ente_id, date_part('years'::text, r.dt_data_valuta_t)::integer, date_part('month'::text, r.dt_data_valuta_t)::integer, date_part('day'::text, r.dt_data_valuta_t)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto, ad.cod_capitolo, ad.cod_accertamento) subq
     JOIN mygov_ente_tipo_dovuto td ON td.mygov_ente_id = subq.mygov_ente_id AND td.cod_tipo::text = subq.cod_td::text
     LEFT JOIN mygov_anagrafica_uff_cap_acc uff ON uff.mygov_ente_id = subq.mygov_ente_id AND uff.cod_tipo_dovuto::text = subq.cod_td::text AND uff.cod_ufficio::text = subq.cod_uff::text AND uff.cod_capitolo::text = subq.cod_cap::text AND uff.cod_accertamento::text = subq.cod_acc::text AND uff.de_anno_esercizio::text = subq.anno::text
  GROUP BY subq.mygov_ente_id, subq.anno, subq.mese, subq.giorno, subq.cod_uff, uff.de_ufficio, subq.cod_td, td.de_tipo, subq.cod_cap, uff.de_capitolo, subq.cod_acc, uff.de_accertamento
  ORDER BY subq.mygov_ente_id, subq.anno, subq.mese, subq.giorno, subq.cod_uff, uff.de_ufficio, subq.cod_td, td.de_tipo, subq.cod_cap, uff.de_capitolo, subq.cod_acc, uff.de_accertamento
WITH DATA;

    CREATE MATERIALIZED VIEW vm_statistica_ente_anno_mese_giorno_uff_td_cap AS 
 SELECT subq.mygov_ente_id,
    subq.anno,
    subq.mese,
    subq.giorno,
    subq.cod_uff,
    coalesce(uff.de_ufficio, 'n/a') AS de_uff,
    subq.cod_td,
    td.de_tipo AS de_td,
    subq.cod_cap,
   coalesce(uff.de_capitolo, 'n/a') AS de_cap,
    sum(subq.imp_pag) AS imp_pag,
    sum(subq.imp_rend) AS imp_rend,
    sum(subq.imp_inc) AS imp_inc
   FROM ( SELECT p.mygov_ente_id,
            date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer AS anno,
            date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer AS mese,
            date_part('day'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer AS giorno,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            ad.cod_capitolo AS cod_cap,
            sum(ad.num_importo) AS imp_pag,
            0 AS imp_rend,
            0 AS imp_inc
           FROM mygov_flusso_export p
             JOIN mygov_accertamento_dettaglio ad ON p.cod_iud::text = ad.cod_iud::text AND p.cod_tipo_dovuto::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND p.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text
          GROUP BY p.mygov_ente_id, date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer, date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer, date_part('day'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto, ad.cod_capitolo
        UNION
         SELECT r.mygov_ente_id,
            date_part('years'::text, r.dt_data_regolamento_r)::integer AS anno,
            date_part('month'::text, r.dt_data_regolamento_r)::integer AS mese,
            date_part('day'::text, r.dt_data_regolamento_r)::integer AS giorno,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            ad.cod_capitolo AS cod_cap,
            0 AS imp_pag,
            sum(ad.num_importo) AS imp_rend,
            0 AS imp_inc
           FROM mygov_import_export_rendicontazione_tesoreria_completa r
             JOIN mygov_accertamento_dettaglio ad ON r.cod_iud_e::text = ad.cod_iud::text AND r.cod_tipo_dovuto_e::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND r.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text AND r.classificazione_completezza::text = 'RT_IUF'::text
          GROUP BY r.mygov_ente_id, date_part('years'::text, r.dt_data_regolamento_r)::integer, date_part('month'::text, r.dt_data_regolamento_r)::integer, date_part('day'::text, r.dt_data_regolamento_r)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto, ad.cod_capitolo
        UNION
         SELECT r.mygov_ente_id,
            date_part('years'::text, r.dt_data_valuta_t)::integer AS anno,
            date_part('month'::text, r.dt_data_valuta_t)::integer AS mese,
            date_part('day'::text, r.dt_data_valuta_t)::integer AS giorno,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            ad.cod_capitolo AS cod_cap,
            0 AS imp_pag,
            0 AS imp_rend,
            sum(ad.num_importo) AS imp_inc
           FROM mygov_import_export_rendicontazione_tesoreria_completa r
             JOIN mygov_accertamento_dettaglio ad ON r.cod_iud_e::text = ad.cod_iud::text AND r.cod_tipo_dovuto_e::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND r.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text AND (r.classificazione_completezza::text = 'RT_IUF_TES'::text OR r.classificazione_completezza::text = 'RT_TES'::text)
          GROUP BY r.mygov_ente_id, date_part('years'::text, r.dt_data_valuta_t)::integer, date_part('month'::text, r.dt_data_valuta_t)::integer, date_part('day'::text, r.dt_data_valuta_t)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto, ad.cod_capitolo) subq
     JOIN mygov_ente_tipo_dovuto td ON td.mygov_ente_id = subq.mygov_ente_id AND td.cod_tipo::text = subq.cod_td::text
     LEFT JOIN (select mygov_ente_id, cod_tipo_dovuto, cod_ufficio, de_anno_esercizio, cod_capitolo, max(de_capitolo) as de_capitolo, max(de_ufficio) as de_ufficio
                from mygov_anagrafica_uff_cap_acc
                where      de_ufficio <> 'n/a' and de_capitolo <> 'n/a'
                group by mygov_ente_id, cod_tipo_dovuto, cod_ufficio, de_anno_esercizio, cod_capitolo
                ) uff ON uff.mygov_ente_id = subq.mygov_ente_id
                                              AND uff.cod_tipo_dovuto::text = subq.cod_td::text
                                              AND uff.cod_ufficio::text = subq.cod_uff::text
                                              AND uff.de_anno_esercizio::text = subq.anno::text
  GROUP BY subq.mygov_ente_id, subq.anno, subq.mese, subq.giorno, subq.cod_uff, uff.de_ufficio, subq.cod_td, td.de_tipo, subq.cod_cap, uff.de_capitolo
  ORDER BY subq.mygov_ente_id, subq.anno, subq.mese, subq.giorno, subq.cod_uff, uff.de_ufficio, subq.cod_td, td.de_tipo, subq.cod_cap, uff.de_capitolo
WITH DATA;

CREATE MATERIALIZED VIEW vm_statistica_ente_anno_mese_uff_td_cap_acc AS 
 SELECT subq.mygov_ente_id,
    subq.anno,
    subq.mese,
    subq.cod_uff,
    uff.de_ufficio AS de_uff,
    subq.cod_td,
    td.de_tipo AS de_td,
    subq.cod_cap,
    uff.de_capitolo AS de_cap,
    subq.cod_acc,
    uff.de_accertamento AS de_acc,
    sum(subq.imp_pag) AS imp_pag,
    sum(subq.imp_rend) AS imp_rend,
    sum(subq.imp_inc) AS imp_inc
   FROM ( SELECT p.mygov_ente_id,
            date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer AS anno,
            date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer AS mese,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            ad.cod_capitolo AS cod_cap,
            ad.cod_accertamento AS cod_acc,
            sum(ad.num_importo) AS imp_pag,
            0 AS imp_rend,
            0 AS imp_inc
           FROM mygov_flusso_export p
             JOIN mygov_accertamento_dettaglio ad ON p.cod_iud::text = ad.cod_iud::text AND p.cod_tipo_dovuto::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND p.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text AND ad.cod_accertamento::text <> 'n/a'::text
          GROUP BY p.mygov_ente_id, date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer, date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto, ad.cod_capitolo, ad.cod_accertamento
        UNION
         SELECT r.mygov_ente_id,
            date_part('years'::text, r.dt_data_regolamento_r)::integer AS anno,
            date_part('month'::text, r.dt_data_regolamento_r)::integer AS mese,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            ad.cod_capitolo AS cod_cap,
            ad.cod_accertamento AS cod_acc,
            0 AS imp_pag,
            sum(ad.num_importo) AS imp_rend,
            0 AS imp_inc
           FROM mygov_import_export_rendicontazione_tesoreria_completa r
             JOIN mygov_accertamento_dettaglio ad ON r.cod_iud_e::text = ad.cod_iud::text AND r.cod_tipo_dovuto_e::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND r.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text AND ad.cod_accertamento::text <> 'n/a'::text AND r.classificazione_completezza::text = 'RT_IUF'::text
          GROUP BY r.mygov_ente_id, date_part('years'::text, r.dt_data_regolamento_r)::integer, date_part('month'::text, r.dt_data_regolamento_r)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto, ad.cod_capitolo, ad.cod_accertamento
        UNION
         SELECT r.mygov_ente_id,
            date_part('years'::text, r.dt_data_valuta_t)::integer AS anno,
            date_part('month'::text, r.dt_data_valuta_t)::integer AS mese,
            ad.cod_ufficio AS cod_uff,
            ad.cod_tipo_dovuto AS cod_td,
            ad.cod_capitolo AS cod_cap,
            ad.cod_accertamento AS cod_acc,
            0 AS imp_pag,
            0 AS imp_rend,
            sum(ad.num_importo) AS imp_inc
           FROM mygov_import_export_rendicontazione_tesoreria_completa r
             JOIN mygov_accertamento_dettaglio ad ON r.cod_iud_e::text = ad.cod_iud::text AND r.cod_tipo_dovuto_e::text = ad.cod_tipo_dovuto::text
             JOIN mygov_accertamento a ON a.mygov_accertamento_id = ad.mygov_accertamento_id
             JOIN mygov_anagrafica_stato st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
             JOIN mygov_ente e ON e.cod_ipa_ente::text = ad.cod_ipa_ente::text AND r.mygov_ente_id = e.mygov_ente_id
          WHERE st.de_tipo_stato::text = 'ACCERTAMENTO'::text AND st.cod_stato::text = 'CHIUSO'::text AND ad.cod_accertamento::text <> 'n/a'::text AND (r.classificazione_completezza::text = 'RT_IUF_TES'::text OR r.classificazione_completezza::text = 'RT_TES'::text)
          GROUP BY r.mygov_ente_id, date_part('years'::text, r.dt_data_valuta_t)::integer, date_part('month'::text, r.dt_data_valuta_t)::integer, ad.cod_ufficio, ad.cod_tipo_dovuto, ad.cod_capitolo, ad.cod_accertamento) subq
     JOIN mygov_ente_tipo_dovuto td ON td.mygov_ente_id = subq.mygov_ente_id AND td.cod_tipo::text = subq.cod_td::text
     LEFT JOIN mygov_anagrafica_uff_cap_acc uff ON uff.mygov_ente_id = subq.mygov_ente_id AND uff.cod_tipo_dovuto::text = subq.cod_td::text AND uff.cod_ufficio::text = subq.cod_uff::text AND uff.cod_capitolo::text = subq.cod_cap::text AND uff.cod_accertamento::text = subq.cod_acc::text AND uff.de_anno_esercizio::text = subq.anno::text
  GROUP BY subq.mygov_ente_id, subq.anno, subq.mese, subq.cod_uff, uff.de_ufficio, subq.cod_td, td.de_tipo, subq.cod_cap, uff.de_capitolo, subq.cod_acc, uff.de_accertamento
  ORDER BY subq.mygov_ente_id, subq.anno, subq.mese, subq.cod_uff, uff.de_ufficio, subq.cod_td, td.de_tipo, subq.cod_cap, uff.de_capitolo, subq.cod_acc, uff.de_accertamento
WITH DATA;
