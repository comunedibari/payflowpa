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


-- Materialized View: vm_statistica_ente_anno_mese

CREATE MATERIALIZED VIEW vm_statistica_ente_anno_mese AS 
   SELECT 
	subq.mygov_ente_id,
	subq.anno,
	subq.mese,
	sum(subq.num_pag) AS num_pag,
	sum(subq.imp_pag) AS imp_pag,
	sum(subq.imp_rend) AS imp_rend,
	sum(subq.imp_inc) AS imp_inc
   FROM ( 
	-- PAGATI
	  SELECT 
		mygov_flusso_export.mygov_ente_id,
		date_part('years'::text, mygov_flusso_export.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS anno,
		date_part('month'::text, mygov_flusso_export.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS mese,
		count(*) AS num_pag,
		sum(mygov_flusso_export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato) AS imp_pag,
		0 AS imp_rend,
		0 AS imp_inc
          FROM 
		mygov_flusso_export
          GROUP BY 
		mygov_ente_id, anno, mese
        UNION
	-- RENDICONTATI
          SELECT 
		mygov_import_export_rendicontazione_tesoreria_completa.mygov_ente_id,
		date_part('years'::text, mygov_import_export_rendicontazione_tesoreria_completa.dt_data_regolamento_r)::int AS anno,
		date_part('month'::text, mygov_import_export_rendicontazione_tesoreria_completa.dt_data_regolamento_r)::int AS mese,
		0 AS num_pag,
		0 AS imp_pag,
		sum(mygov_import_export_rendicontazione_tesoreria_completa.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato_e) AS imp_rend,
		0 AS imp_inc
          FROM 
		mygov_import_export_rendicontazione_tesoreria_completa
          WHERE 
		mygov_import_export_rendicontazione_tesoreria_completa.classificazione_completezza::text = 'RT_IUF'::text
          GROUP BY 
		mygov_ente_id, anno, mese
        UNION
	-- INCASSATI
          SELECT 
		mygov_import_export_rendicontazione_tesoreria_completa.mygov_ente_id,
		date_part('years'::text, mygov_import_export_rendicontazione_tesoreria_completa.dt_data_valuta_t)::int AS anno,
		date_part('month'::text, mygov_import_export_rendicontazione_tesoreria_completa.dt_data_valuta_t)::int AS mese,
		0 AS num_pag,
		0 AS imp_pag,
		0 AS imp_rend,
		sum(mygov_import_export_rendicontazione_tesoreria_completa.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato_e) AS imp_inc
          FROM 
		mygov_import_export_rendicontazione_tesoreria_completa
          WHERE 
		mygov_import_export_rendicontazione_tesoreria_completa.classificazione_completezza::text = 'RT_IUF_TES'::text OR 
		mygov_import_export_rendicontazione_tesoreria_completa.classificazione_completezza::text = 'RT_TES'::text
          GROUP BY 
		mygov_ente_id, anno, mese
  ) subq
  GROUP BY subq.mygov_ente_id, subq.anno, subq.mese
  ORDER BY subq.mygov_ente_id, subq.anno, subq.mese
WITH NO DATA;




-- Materialized View: vm_statistica_ente_anno_mese_giorno

CREATE MATERIALIZED VIEW vm_statistica_ente_anno_mese_giorno AS 
   SELECT 
	subq.mygov_ente_id,
	subq.anno,
	subq.mese,
	subq.giorno,
	sum(subq.num_pag) AS num_pag,
	sum(subq.imp_pag) AS imp_pag,
	sum(subq.imp_rend) AS imp_rend,
	sum(subq.imp_inc) AS imp_inc
   FROM ( 
	-- PAGATI
	  SELECT 
		mygov_flusso_export.mygov_ente_id,
		date_part('years'::text, mygov_flusso_export.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS anno,
		date_part('month'::text, mygov_flusso_export.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS mese,
		date_part('day'::text, dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS giorno,
		count(*) AS num_pag,
		sum(mygov_flusso_export.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato) AS imp_pag,
		0 AS imp_rend,
		0 AS imp_inc
          FROM 
		mygov_flusso_export
          GROUP BY 
		mygov_ente_id, anno, mese, giorno
        UNION
	-- RENDICONTATI
          SELECT 
		mygov_import_export_rendicontazione_tesoreria_completa.mygov_ente_id,
		date_part('years'::text, mygov_import_export_rendicontazione_tesoreria_completa.dt_data_regolamento_r)::int AS anno,
		date_part('month'::text, mygov_import_export_rendicontazione_tesoreria_completa.dt_data_regolamento_r)::int AS mese,
		date_part('day'::text, dt_data_regolamento_r)::int AS giorno,
		0 AS num_pag,
		0 AS imp_pag,
		sum(mygov_import_export_rendicontazione_tesoreria_completa.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato_e) AS imp_rend,
		0 AS imp_inc
          FROM 
		mygov_import_export_rendicontazione_tesoreria_completa
          WHERE 
		mygov_import_export_rendicontazione_tesoreria_completa.classificazione_completezza::text = 'RT_IUF'::text
          GROUP BY 
		mygov_ente_id, anno, mese, giorno
        UNION
	-- INCASSATI
          SELECT 
		mygov_import_export_rendicontazione_tesoreria_completa.mygov_ente_id,
		date_part('years'::text, mygov_import_export_rendicontazione_tesoreria_completa.dt_data_valuta_t)::int AS anno,
		date_part('month'::text, mygov_import_export_rendicontazione_tesoreria_completa.dt_data_valuta_t)::int AS mese,
		date_part('day'::text, dt_data_valuta_t)::int AS giorno,
		0 AS num_pag,
		0 AS imp_pag,
		0 AS imp_rend,
		sum(mygov_import_export_rendicontazione_tesoreria_completa.num_e_dati_pag_dati_sing_pag_singolo_importo_pagato_e) AS imp_inc
          FROM 
		mygov_import_export_rendicontazione_tesoreria_completa
          WHERE 
		mygov_import_export_rendicontazione_tesoreria_completa.classificazione_completezza::text = 'RT_IUF_TES'::text OR 
		mygov_import_export_rendicontazione_tesoreria_completa.classificazione_completezza::text = 'RT_TES'::text
          GROUP BY 
		mygov_ente_id, anno, mese, giorno
  ) subq
  GROUP BY subq.mygov_ente_id, subq.anno, subq.mese, subq.giorno
  ORDER BY subq.mygov_ente_id, subq.anno, subq.mese, subq.giorno
WITH NO DATA;




-- Materialized View: vm_statistica_ente_anno_mese_giorno_uff_td
 
CREATE MATERIALIZED VIEW vm_statistica_ente_anno_mese_giorno_uff_td AS 
  SELECT 
      	subq.mygov_ente_id,
	subq.anno,
	subq.mese,
	subq.giorno,
	subq.cod_uff,
        uff.de_ufficio AS de_uff,
	subq.cod_td,
        td.de_tipo AS de_td,
	sum(subq.imp_pag) AS imp_pag,
	sum(subq.imp_rend) AS imp_rend,
	sum(subq.imp_inc) AS imp_inc
FROM
     (
      -- PAGATI
	SELECT 
	      p.mygov_ente_id,
	      date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS anno,
	      date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS mese,
	      date_part('day'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS giorno,
	      ad.cod_ufficio AS cod_uff,    
	      ad.cod_tipo_dovuto AS cod_td,
	      sum(ad.num_importo) AS imp_pag,
	      0 AS imp_rend,
	      0 AS imp_inc
	FROM 
	    mygov_flusso_export AS p 
	    
	    INNER JOIN mygov_accertamento_dettaglio AS ad ON p.cod_iud = ad.cod_iud AND p.cod_tipo_dovuto = ad.cod_tipo_dovuto

	    INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id

	    INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
	    
	    INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND p.mygov_ente_id = e.mygov_ente_id   
	WHERE 
	     st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO'
         GROUP BY 
		p.mygov_ente_id, anno, mese, giorno, cod_uff, cod_td
        
      UNION
    
      -- RENDICONTATI
	SELECT 
	      r.mygov_ente_id,
	      date_part('years'::text, r.dt_data_regolamento_r)::int AS anno,
	      date_part('month'::text, r.dt_data_regolamento_r)::int AS mese,
	      date_part('day'::text, r.dt_data_regolamento_r)::int AS giorno,
	      ad.cod_ufficio AS cod_uff,
	      ad.cod_tipo_dovuto AS cod_td,
	      0 AS imp_pag,
	      sum(ad.num_importo) AS imp_rend,
	      0 AS imp_inc
	FROM
	      mygov_import_export_rendicontazione_tesoreria_completa AS r 

	      INNER JOIN mygov_accertamento_dettaglio AS ad ON r.cod_iud_e = ad.cod_iud AND r.cod_tipo_dovuto_e = ad.cod_tipo_dovuto

	      INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id 

	      INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id 

	      INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND r.mygov_ente_id = e.mygov_ente_id 
	WHERE 
	     st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO' AND r.classificazione_completezza = 'RT_IUF' 
	GROUP BY 
	     r.mygov_ente_id, anno, mese, giorno, cod_uff, cod_td
     
      UNION 
     
      -- INCASSATI 
	SELECT 
	      r.mygov_ente_id,
	      date_part('years'::text, r.dt_data_valuta_t)::int AS anno,
	      date_part('month'::text, r.dt_data_valuta_t)::int AS mese,
	      date_part('day'::text, r.dt_data_valuta_t)::int AS giorno,
	      ad.cod_ufficio AS cod_uff,
	      ad.cod_tipo_dovuto AS cod_td,
	      0 AS imp_pag,
	      0 AS imp_rend,
	      sum(ad.num_importo) AS imp_inc
	FROM
	      mygov_import_export_rendicontazione_tesoreria_completa AS r 

	      INNER JOIN mygov_accertamento_dettaglio AS ad ON r.cod_iud_e = ad.cod_iud AND r.cod_tipo_dovuto_e = ad.cod_tipo_dovuto
		
	      INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id

	      INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
	    
	      INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND r.mygov_ente_id = e.mygov_ente_id 
	WHERE 
	    st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO' AND 
	    (r.classificazione_completezza = 'RT_IUF_TES' OR r.classificazione_completezza = 'RT_TES')
	GROUP BY 
	    r.mygov_ente_id, anno, mese, giorno, cod_uff, cod_td
	
     ) as subq

     INNER JOIN mygov_ente_tipo_dovuto AS td ON td.mygov_ente_id = subq.mygov_ente_id AND td.cod_tipo = subq.cod_td

     LEFT JOIN mygov_anagrafica_uff_cap_acc AS uff ON 
			uff.mygov_ente_id = subq.mygov_ente_id AND uff.cod_tipo_dovuto = subq.cod_td AND uff.cod_ufficio = subq.cod_uff
	    
  GROUP BY subq.mygov_ente_id, subq.anno, subq.mese, subq.giorno, subq.cod_uff, de_uff, subq.cod_td, de_td
  ORDER BY subq.mygov_ente_id, subq.anno, subq.mese, subq.giorno, subq.cod_uff, de_uff, subq.cod_td, de_td
WITH NO DATA;




-- Materialized View: vm_statistica_ente_anno_mese_uff_td

CREATE MATERIALIZED VIEW vm_statistica_ente_anno_mese_uff_td AS 
  SELECT 
      	subq.mygov_ente_id,
	subq.anno,
	subq.mese,
	subq.cod_uff,
        uff.de_ufficio AS de_uff,
	subq.cod_td,
        td.de_tipo AS de_td,
	sum(subq.imp_pag) AS imp_pag,
	sum(subq.imp_rend) AS imp_rend,
	sum(subq.imp_inc) AS imp_inc
FROM
     (
      -- PAGATI
	SELECT 
	      p.mygov_ente_id,
	      date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS anno,
	      date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS mese,
	      ad.cod_ufficio AS cod_uff,    
	      ad.cod_tipo_dovuto AS cod_td,
	      sum(ad.num_importo) AS imp_pag,
	      0 AS imp_rend,
	      0 AS imp_inc
	FROM 
	    mygov_flusso_export AS p 
	    
	    INNER JOIN mygov_accertamento_dettaglio AS ad ON p.cod_iud = ad.cod_iud AND p.cod_tipo_dovuto = ad.cod_tipo_dovuto

	    INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id

	    INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
	    
	    INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND p.mygov_ente_id = e.mygov_ente_id   
	WHERE 
	     st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO'
         GROUP BY 
		p.mygov_ente_id, anno, mese, cod_uff, cod_td
        
      UNION
    
      -- RENDICONTATI
	SELECT 
	      r.mygov_ente_id,
	      date_part('years'::text, r.dt_data_regolamento_r)::int AS anno,
	      date_part('month'::text, r.dt_data_regolamento_r)::int AS mese,
	      ad.cod_ufficio AS cod_uff,
	      ad.cod_tipo_dovuto AS cod_td,
	      0 AS imp_pag,
	      sum(ad.num_importo) AS imp_rend,
	      0 AS imp_inc
	FROM
	      mygov_import_export_rendicontazione_tesoreria_completa AS r 

	      INNER JOIN mygov_accertamento_dettaglio AS ad ON r.cod_iud_e = ad.cod_iud AND r.cod_tipo_dovuto_e = ad.cod_tipo_dovuto

	      INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id 

	      INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id 

	      INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND r.mygov_ente_id = e.mygov_ente_id 
	WHERE 
	     st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO' AND r.classificazione_completezza = 'RT_IUF' 
	GROUP BY 
	     r.mygov_ente_id, anno, mese, cod_uff, cod_td
     
      UNION 
     
      -- INCASSATI
	SELECT 
	      r.mygov_ente_id,
	      date_part('years'::text, r.dt_data_valuta_t)::int AS anno,
	      date_part('month'::text, r.dt_data_valuta_t)::int AS mese,
	      ad.cod_ufficio AS cod_uff,
	      ad.cod_tipo_dovuto AS cod_td,
	      0 AS imp_pag,
	      0 AS imp_rend,
	      sum(ad.num_importo) AS imp_inc
	FROM
	      mygov_import_export_rendicontazione_tesoreria_completa AS r 

	      INNER JOIN mygov_accertamento_dettaglio AS ad ON r.cod_iud_e = ad.cod_iud AND r.cod_tipo_dovuto_e = ad.cod_tipo_dovuto
		
	      INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id

	      INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
	    
	      INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND r.mygov_ente_id = e.mygov_ente_id 
	WHERE 
	    st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO' AND 
	    (r.classificazione_completezza = 'RT_IUF_TES' OR r.classificazione_completezza = 'RT_TES')
	GROUP BY 
	    r.mygov_ente_id, anno, mese, cod_uff, cod_td
	
     ) as subq

     INNER JOIN mygov_ente_tipo_dovuto AS td ON td.mygov_ente_id = subq.mygov_ente_id AND td.cod_tipo = subq.cod_td

     LEFT JOIN mygov_anagrafica_uff_cap_acc AS uff ON 
		uff.mygov_ente_id = subq.mygov_ente_id AND uff.cod_tipo_dovuto = subq.cod_td AND uff.cod_ufficio = subq.cod_uff
	    
  GROUP BY subq.mygov_ente_id, subq.anno, subq.mese, subq.cod_uff, de_uff, subq.cod_td, de_td
  ORDER BY subq.mygov_ente_id, subq.anno, subq.mese, subq.cod_uff, de_uff, subq.cod_td, de_td
WITH NO DATA;





-- Materialized View: vm_statistica_ente_anno_mese_giorno_uff_td_cap
 
CREATE MATERIALIZED VIEW vm_statistica_ente_anno_mese_giorno_uff_td_cap AS 
  SELECT 
      	subq.mygov_ente_id,
	subq.anno,
	subq.mese,
	subq.giorno,
	subq.cod_uff,
        uff.de_ufficio AS de_uff,
	subq.cod_td,
        td.de_tipo AS de_td,
        subq.cod_cap,
        uff.de_capitolo AS de_cap,
	sum(subq.imp_pag) AS imp_pag,
	sum(subq.imp_rend) AS imp_rend,
	sum(subq.imp_inc) AS imp_inc
FROM
     (
      -- PAGATI
	SELECT 
	      p.mygov_ente_id,
	      date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS anno,
	      date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS mese,
	      date_part('day'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS giorno,
	      ad.cod_ufficio AS cod_uff,    
	      ad.cod_tipo_dovuto AS cod_td,
	      ad.cod_capitolo AS cod_cap,
	      sum(ad.num_importo) AS imp_pag,
	      0 AS imp_rend,
	      0 AS imp_inc
	FROM 
	    mygov_flusso_export AS p 
	    
	    INNER JOIN mygov_accertamento_dettaglio AS ad ON p.cod_iud = ad.cod_iud AND p.cod_tipo_dovuto = ad.cod_tipo_dovuto

	    INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id

	    INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
	    
	    INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND p.mygov_ente_id = e.mygov_ente_id 
	          
	WHERE 
	     st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO'
        GROUP BY 
	     p.mygov_ente_id, anno, mese, giorno, cod_uff, cod_td, cod_cap
        
      UNION
    
      -- RENDICONTATI
	SELECT 
	      r.mygov_ente_id,
	      date_part('years'::text, r.dt_data_regolamento_r)::int AS anno,
	      date_part('month'::text, r.dt_data_regolamento_r)::int AS mese,
	      date_part('day'::text, r.dt_data_regolamento_r)::int AS giorno,
	      ad.cod_ufficio AS cod_uff,
	      ad.cod_tipo_dovuto AS cod_td,
	      ad.cod_capitolo AS cod_cap,
	      0 AS imp_pag,
	      sum(ad.num_importo) AS imp_rend,
	      0 AS imp_inc
	FROM
	      mygov_import_export_rendicontazione_tesoreria_completa AS r 

	      INNER JOIN mygov_accertamento_dettaglio AS ad ON r.cod_iud_e = ad.cod_iud AND r.cod_tipo_dovuto_e = ad.cod_tipo_dovuto

	      INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id 

	      INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id 

	      INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND r.mygov_ente_id = e.mygov_ente_id 
	WHERE 
	     st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO' AND r.classificazione_completezza = 'RT_IUF' 
	GROUP BY 
	     r.mygov_ente_id, anno, mese, giorno, cod_uff, cod_td, cod_cap
     
      UNION 
     
      -- INCASSATI 
	SELECT 
	      r.mygov_ente_id,
	      date_part('years'::text, r.dt_data_valuta_t)::int AS anno,
	      date_part('month'::text, r.dt_data_valuta_t)::int AS mese,
	      date_part('day'::text, r.dt_data_valuta_t)::int AS giorno,
	      ad.cod_ufficio AS cod_uff,
	      ad.cod_tipo_dovuto AS cod_td,
	      ad.cod_capitolo AS cod_cap,
	      0 AS imp_pag,
	      0 AS imp_rend,
	      sum(ad.num_importo) AS imp_inc
	FROM
	      mygov_import_export_rendicontazione_tesoreria_completa AS r 

	      INNER JOIN mygov_accertamento_dettaglio AS ad ON r.cod_iud_e = ad.cod_iud AND r.cod_tipo_dovuto_e = ad.cod_tipo_dovuto
		
	      INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id

	      INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
	    
	      INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND r.mygov_ente_id = e.mygov_ente_id 
	WHERE 
	    st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO' AND 
	    (r.classificazione_completezza = 'RT_IUF_TES' OR r.classificazione_completezza = 'RT_TES')
	GROUP BY 
	    r.mygov_ente_id, anno, mese, giorno, cod_uff, cod_td, cod_cap
	
     ) as subq

     INNER JOIN mygov_ente_tipo_dovuto AS td ON td.mygov_ente_id = subq.mygov_ente_id AND td.cod_tipo = subq.cod_td

     LEFT JOIN mygov_anagrafica_uff_cap_acc AS uff ON 
		 uff.mygov_ente_id = subq.mygov_ente_id AND uff.cod_ufficio = subq.cod_uff AND uff.cod_tipo_dovuto = subq.cod_td AND 
		 uff.cod_capitolo = subq.cod_cap
	    
  GROUP BY subq.mygov_ente_id, subq.anno, subq.mese, subq.giorno, subq.cod_uff, de_uff, subq.cod_td, de_td, subq.cod_cap, de_cap
  ORDER BY subq.mygov_ente_id, subq.anno, subq.mese, subq.giorno, subq.cod_uff, de_uff, subq.cod_td, de_td, subq.cod_cap, de_cap
WITH NO DATA;





-- Materialized View: vm_statistica_ente_anno_mese_uff_td_cap
 
CREATE MATERIALIZED VIEW vm_statistica_ente_anno_mese_uff_td_cap AS 
  SELECT 
      	subq.mygov_ente_id,
	subq.anno,
	subq.mese,
	subq.cod_uff,
        uff.de_ufficio AS de_uff,
	subq.cod_td,
        td.de_tipo AS de_td,
        subq.cod_cap,
        uff.de_capitolo AS de_cap,
	sum(subq.imp_pag) AS imp_pag,
	sum(subq.imp_rend) AS imp_rend,
	sum(subq.imp_inc) AS imp_inc
FROM
     (
      -- PAGATI
	SELECT 
	      p.mygov_ente_id,
	      date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS anno,
	      date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS mese,
	      ad.cod_ufficio AS cod_uff,    
	      ad.cod_tipo_dovuto AS cod_td,
	      ad.cod_capitolo AS cod_cap,
	      sum(ad.num_importo) AS imp_pag,
	      0 AS imp_rend,
	      0 AS imp_inc
	FROM 
	    mygov_flusso_export AS p 
	    
	    INNER JOIN mygov_accertamento_dettaglio AS ad ON p.cod_iud = ad.cod_iud AND p.cod_tipo_dovuto = ad.cod_tipo_dovuto

	    INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id

	    INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
	    
	    INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND p.mygov_ente_id = e.mygov_ente_id 
	          
	WHERE 
	     st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO'
        GROUP BY 
	     p.mygov_ente_id, anno, mese, cod_uff, cod_td, cod_cap
        
      UNION
    
      -- RENDICONTATI
	SELECT 
	      r.mygov_ente_id,
	      date_part('years'::text, r.dt_data_regolamento_r)::int AS anno,
	      date_part('month'::text, r.dt_data_regolamento_r)::int AS mese,
	      ad.cod_ufficio AS cod_uff,
	      ad.cod_tipo_dovuto AS cod_td,
	      ad.cod_capitolo AS cod_cap,
	      0 AS imp_pag,
	      sum(ad.num_importo) AS imp_rend,
	      0 AS imp_inc
	FROM
	      mygov_import_export_rendicontazione_tesoreria_completa AS r 

	      INNER JOIN mygov_accertamento_dettaglio AS ad ON r.cod_iud_e = ad.cod_iud AND r.cod_tipo_dovuto_e = ad.cod_tipo_dovuto

	      INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id 

	      INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id 

	      INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND r.mygov_ente_id = e.mygov_ente_id 
	WHERE 
	     st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO' AND r.classificazione_completezza = 'RT_IUF' 
	GROUP BY 
	     r.mygov_ente_id, anno, mese, cod_uff, cod_td, cod_cap
     
      UNION 
     
      -- INCASSATI
	SELECT 
	      r.mygov_ente_id,
	      date_part('years'::text, r.dt_data_valuta_t)::int AS anno,
	      date_part('month'::text, r.dt_data_valuta_t)::int AS mese,
	      ad.cod_ufficio AS cod_uff,
	      ad.cod_tipo_dovuto AS cod_td,
	      ad.cod_capitolo AS cod_cap,
	      0 AS imp_pag,
	      0 AS imp_rend,
	      sum(ad.num_importo) AS imp_inc
	FROM
	      mygov_import_export_rendicontazione_tesoreria_completa AS r 

	      INNER JOIN mygov_accertamento_dettaglio AS ad ON r.cod_iud_e = ad.cod_iud AND r.cod_tipo_dovuto_e = ad.cod_tipo_dovuto
		
	      INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id

	      INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
	    
	      INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND r.mygov_ente_id = e.mygov_ente_id 
	WHERE 
	    st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO' AND 
	    (r.classificazione_completezza = 'RT_IUF_TES' OR r.classificazione_completezza = 'RT_TES')
	GROUP BY 
	    r.mygov_ente_id, anno, mese, cod_uff, cod_td, cod_cap
	
     ) as subq

     INNER JOIN mygov_ente_tipo_dovuto AS td ON td.mygov_ente_id = subq.mygov_ente_id AND td.cod_tipo = subq.cod_td

     LEFT JOIN mygov_anagrafica_uff_cap_acc AS uff ON 
		 uff.mygov_ente_id = subq.mygov_ente_id AND uff.cod_tipo_dovuto = subq.cod_td AND uff.cod_ufficio = subq.cod_uff AND 
		 uff.cod_capitolo = subq.cod_cap
	    
  GROUP BY subq.mygov_ente_id, subq.anno, subq.mese, subq.cod_uff, de_uff, subq.cod_td, de_td, subq.cod_cap, de_cap
  ORDER BY subq.mygov_ente_id, subq.anno, subq.mese, subq.cod_uff, de_uff, subq.cod_td, de_td, subq.cod_cap, de_cap
WITH NO DATA;





-- Materialized View: vm_statistica_ente_anno_mese_giorno_uff_td_cap_acc 

CREATE MATERIALIZED VIEW vm_statistica_ente_anno_mese_giorno_uff_td_cap_acc AS 
  SELECT 
      	subq.mygov_ente_id,
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
FROM
     (
      -- PAGATI
	SELECT 
	      p.mygov_ente_id,
	      date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS anno,
	      date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS mese,
	      date_part('day'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS giorno,
	      ad.cod_ufficio AS cod_uff,    
	      ad.cod_tipo_dovuto AS cod_td,
	      ad.cod_capitolo AS cod_cap,
	      ad.cod_accertamento AS cod_acc,
	      sum(ad.num_importo) AS imp_pag,
	      0 AS imp_rend,
	      0 AS imp_inc
	FROM 
	    mygov_flusso_export AS p 
	    
	    INNER JOIN mygov_accertamento_dettaglio AS ad ON p.cod_iud = ad.cod_iud AND p.cod_tipo_dovuto = ad.cod_tipo_dovuto

	    INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id

	    INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
	    
	    INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND p.mygov_ente_id = e.mygov_ente_id 
	          
	WHERE 
	     st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO' AND ad.cod_accertamento <> 'n/a'
        GROUP BY 
	     p.mygov_ente_id, anno, mese, giorno, cod_uff, cod_td, cod_cap, cod_acc
        
      UNION
    
      -- RENDICONTATI
	SELECT 
	      r.mygov_ente_id,
	      date_part('years'::text, r.dt_data_regolamento_r)::int AS anno,
	      date_part('month'::text, r.dt_data_regolamento_r)::int AS mese,
	      date_part('day'::text, r.dt_data_regolamento_r)::int AS giorno,
	      ad.cod_ufficio AS cod_uff,
	      ad.cod_tipo_dovuto AS cod_td,
	      ad.cod_capitolo AS cod_cap,
	      ad.cod_accertamento AS cod_acc,
	      0 AS imp_pag,
	      sum(ad.num_importo) AS imp_rend,
	      0 AS imp_inc
	FROM
	      mygov_import_export_rendicontazione_tesoreria_completa AS r 

	      INNER JOIN mygov_accertamento_dettaglio AS ad ON r.cod_iud_e = ad.cod_iud AND r.cod_tipo_dovuto_e = ad.cod_tipo_dovuto

	      INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id 

	      INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id 

	      INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND r.mygov_ente_id = e.mygov_ente_id 
	WHERE 
	     st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO' AND ad.cod_accertamento <> 'n/a' AND 
	     r.classificazione_completezza = 'RT_IUF' 
	GROUP BY 
	     r.mygov_ente_id, anno, mese, giorno, cod_uff, cod_td, cod_cap, cod_acc
     
      UNION 
     
      -- INCASSATI
	SELECT 
	      r.mygov_ente_id,
	      date_part('years'::text, r.dt_data_valuta_t)::int AS anno,
	      date_part('month'::text, r.dt_data_valuta_t)::int AS mese,
	      date_part('day'::text, r.dt_data_valuta_t)::int AS giorno,
	      ad.cod_ufficio AS cod_uff,
	      ad.cod_tipo_dovuto AS cod_td,
	      ad.cod_capitolo AS cod_cap,
	      ad.cod_accertamento AS cod_acc,
	      0 AS imp_pag,
	      0 AS imp_rend,
	      sum(ad.num_importo) AS imp_inc
	FROM
	      mygov_import_export_rendicontazione_tesoreria_completa AS r 

	      INNER JOIN mygov_accertamento_dettaglio AS ad ON r.cod_iud_e = ad.cod_iud AND r.cod_tipo_dovuto_e = ad.cod_tipo_dovuto
		
	      INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id

	      INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
	    
	      INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND r.mygov_ente_id = e.mygov_ente_id 
	WHERE 
	    st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO' AND ad.cod_accertamento <> 'n/a' AND 
	    (r.classificazione_completezza = 'RT_IUF_TES' OR r.classificazione_completezza = 'RT_TES')
	GROUP BY 
	    r.mygov_ente_id, anno, mese, giorno, cod_uff, cod_td, cod_cap, cod_acc
	
     ) as subq

     INNER JOIN mygov_ente_tipo_dovuto AS td ON td.mygov_ente_id = subq.mygov_ente_id AND td.cod_tipo = subq.cod_td

     LEFT JOIN mygov_anagrafica_uff_cap_acc AS uff ON 
		 uff.mygov_ente_id = subq.mygov_ente_id AND uff.cod_tipo_dovuto = subq.cod_td AND
		 uff.cod_ufficio = subq.cod_uff AND uff.cod_capitolo = subq.cod_cap AND uff.cod_accertamento = subq.cod_acc
	    
  GROUP BY subq.mygov_ente_id, subq.anno, subq.mese, subq.giorno, subq.cod_uff, de_uff, subq.cod_td, de_td, subq.cod_cap, de_cap, subq.cod_acc, de_acc
  ORDER BY subq.mygov_ente_id, subq.anno, subq.mese, subq.giorno, subq.cod_uff, de_uff, subq.cod_td, de_td, subq.cod_cap, de_cap, subq.cod_acc, de_acc
WITH NO DATA;





-- Materialized View: vm_statistica_ente_anno_mese_uff_td_cap_acc

CREATE MATERIALIZED VIEW vm_statistica_ente_anno_mese_uff_td_cap_acc AS 
  SELECT 
      	subq.mygov_ente_id,
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
FROM
     (
      -- PAGATI
	SELECT 
	      p.mygov_ente_id,
	      date_part('years'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS anno,
	      date_part('month'::text, p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::int AS mese,
	      ad.cod_ufficio AS cod_uff,    
	      ad.cod_tipo_dovuto AS cod_td,
	      ad.cod_capitolo AS cod_cap,
	      ad.cod_accertamento AS cod_acc,
	      sum(ad.num_importo) AS imp_pag,
	      0 AS imp_rend,
	      0 AS imp_inc
	FROM 
	    mygov_flusso_export AS p 
	    
	    INNER JOIN mygov_accertamento_dettaglio AS ad ON p.cod_iud = ad.cod_iud AND p.cod_tipo_dovuto = ad.cod_tipo_dovuto

	    INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id

	    INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
	    
	    INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND p.mygov_ente_id = e.mygov_ente_id 
	          
	WHERE 
	     st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO' AND ad.cod_accertamento <> 'n/a'
        GROUP BY 
	     p.mygov_ente_id, anno, mese, cod_uff, cod_td, cod_cap, cod_acc
        
      UNION
    
      -- RENDICONTATI
	SELECT 
	      r.mygov_ente_id,
	      date_part('years'::text, r.dt_data_regolamento_r)::int AS anno,
	      date_part('month'::text, r.dt_data_regolamento_r)::int AS mese,
	      ad.cod_ufficio AS cod_uff,
	      ad.cod_tipo_dovuto AS cod_td,
	      ad.cod_capitolo AS cod_cap,
	      ad.cod_accertamento AS cod_acc,
	      0 AS imp_pag,
	      sum(ad.num_importo) AS imp_rend,
	      0 AS imp_inc
	FROM
	      mygov_import_export_rendicontazione_tesoreria_completa AS r 

	      INNER JOIN mygov_accertamento_dettaglio AS ad ON r.cod_iud_e = ad.cod_iud AND r.cod_tipo_dovuto_e = ad.cod_tipo_dovuto

	      INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id 

	      INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id 

	      INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND r.mygov_ente_id = e.mygov_ente_id 
	WHERE 
	     st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO' AND ad.cod_accertamento <> 'n/a' AND 
	     r.classificazione_completezza = 'RT_IUF' 
	GROUP BY 
	     r.mygov_ente_id, anno, mese, cod_uff, cod_td, cod_cap, cod_acc
     
      UNION 
     
      -- INCASSATI
	SELECT 
	      r.mygov_ente_id,
	      date_part('years'::text, r.dt_data_valuta_t)::int AS anno,
	      date_part('month'::text, r.dt_data_valuta_t)::int AS mese,
	      ad.cod_ufficio AS cod_uff,
	      ad.cod_tipo_dovuto AS cod_td,
	      ad.cod_capitolo AS cod_cap,
	      ad.cod_accertamento AS cod_acc,
	      0 AS imp_pag,
	      0 AS imp_rend,
	      sum(ad.num_importo) AS imp_inc
	FROM
	      mygov_import_export_rendicontazione_tesoreria_completa AS r 

	      INNER JOIN mygov_accertamento_dettaglio AS ad ON r.cod_iud_e = ad.cod_iud AND r.cod_tipo_dovuto_e = ad.cod_tipo_dovuto
		
	      INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id

	      INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
	    
	      INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND r.mygov_ente_id = e.mygov_ente_id 
	WHERE 
	    st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO' AND ad.cod_accertamento <> 'n/a' AND 
	    (r.classificazione_completezza = 'RT_IUF_TES' OR r.classificazione_completezza = 'RT_TES')
	GROUP BY 
	    r.mygov_ente_id, anno, mese, cod_uff, cod_td, cod_cap, cod_acc
	
     ) as subq

     INNER JOIN mygov_ente_tipo_dovuto AS td ON td.mygov_ente_id = subq.mygov_ente_id AND td.cod_tipo = subq.cod_td

     LEFT JOIN mygov_anagrafica_uff_cap_acc AS uff ON 
		 uff.mygov_ente_id = subq.mygov_ente_id AND uff.cod_tipo_dovuto = subq.cod_td AND
		 uff.cod_ufficio = subq.cod_uff AND uff.cod_capitolo = subq.cod_cap AND
		 uff.cod_accertamento = subq.cod_acc
	    
  GROUP BY subq.mygov_ente_id, subq.anno, subq.mese, subq.cod_uff, de_uff, subq.cod_td, de_td, subq.cod_cap, de_cap, subq.cod_acc, de_acc
  ORDER BY subq.mygov_ente_id, subq.anno, subq.mese, subq.cod_uff, de_uff, subq.cod_td, de_td, subq.cod_cap, de_cap, subq.cod_acc, de_acc
WITH NO DATA;





-- Function: get_dettaglio_pagamenti_cruscotto

CREATE OR REPLACE FUNCTION get_dettaglio_pagamenti_cruscotto(_anno integer, _mese integer, _giorno integer, _cod_ufficio character varying, _cod_dovuto character varying, _cod_capitolo character varying, _ente_id bigint, _cod_accertamento character varying)
  RETURNS SETOF character varying AS
$BODY$

SELECT DISTINCT(iud) as iud   	 
FROM
     (
       -- PAGATI
	SELECT 
	     DISTINCT(p.cod_iud) as iud
	FROM 
	    mygov_flusso_export AS p 
	    
	    INNER JOIN mygov_accertamento_dettaglio AS ad ON p.cod_iud = ad.cod_iud AND p.cod_tipo_dovuto = ad.cod_tipo_dovuto

	    INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id

	    INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
	    
	    INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND p.mygov_ente_id = e.mygov_ente_id
	WHERE 
	     /* ACCERTAMENTI CHIUSI */
	     st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO' AND 

	     /* ANNO */
	     CASE WHEN ($1 IS NOT NULL) THEN EXTRACT(YEAR FROM p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer = $1 ELSE true END AND	

	     /* MESE */
	     CASE WHEN ($2 IS NOT NULL) THEN EXTRACT(MONTH FROM p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer = $2 ELSE true END AND	

	     /* GIORNO */
	     CASE WHEN ($3 IS NOT NULL) THEN EXTRACT(DAY FROM p.dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento)::integer = $3 ELSE true END AND	
	      
	     /* CODICE UFFICIO */
	     CASE WHEN ($4 IS NOT NULL) THEN ad.cod_ufficio = $4 ELSE true END AND
	     
	     /* CODICE DOVUTO */
	     CASE WHEN ($5 IS NOT NULL) THEN p.cod_tipo_dovuto = $5 AND ad.cod_tipo_dovuto = $5 ELSE true END AND

	     /* CODICE CAPITOLO */
	     CASE WHEN ($6 IS NOT NULL) THEN ad.cod_capitolo = $6 ELSE true END AND
	     
	     /* ENTE ID */
	     CASE WHEN ($7 IS NOT NULL) THEN p.mygov_ente_id = $7 AND e.mygov_ente_id = $7 ELSE true END AND

	     /* CODICE ACCERTAMENTO */
	     CASE WHEN ($8 IS NOT NULL) THEN ad.cod_accertamento = $8 ELSE true END 
        
      UNION
    
      -- RENDICONTATI
	SELECT 
	     DISTINCT(r.cod_iud_e) AS iud
	FROM
	     mygov_import_export_rendicontazione_tesoreria_completa AS r 

	     INNER JOIN mygov_accertamento_dettaglio AS ad ON r.cod_iud_e = ad.cod_iud AND r.cod_tipo_dovuto_e = ad.cod_tipo_dovuto

	     INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id 

	     INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id 

	     INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND r.mygov_ente_id = e.mygov_ente_id 
	WHERE 
	     /* ACCERTAMENTI CHIUSI */
	     st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO' AND 
	     
	     /* CLASSIFICAZIONE */
	     r.classificazione_completezza = 'RT_IUF' AND
	     
	     /* ANNO */
	     CASE WHEN ($1 IS NOT NULL) THEN EXTRACT(YEAR FROM r.dt_data_regolamento_r)::integer = $1 ELSE true END AND	

	     /* MESE */
	     CASE WHEN ($2 IS NOT NULL) THEN EXTRACT(MONTH FROM r.dt_data_regolamento_r)::integer = $2 ELSE true END AND	

	     /* GIORNO */
	     CASE WHEN ($3 IS NOT NULL) THEN EXTRACT(DAY FROM r.dt_data_regolamento_r)::integer = $3 ELSE true END AND	
	      
	     /* CODICE UFFICIO */
	     CASE WHEN ($4 IS NOT NULL) THEN ad.cod_ufficio = $4 ELSE true END AND
	     
	     /* CODICE DOVUTO */
	     CASE WHEN ($5 IS NOT NULL) THEN r.cod_tipo_dovuto_e = $5 AND ad.cod_tipo_dovuto = $5 ELSE true END AND

	     /* CODICE CAPITOLO */
	     CASE WHEN ($6 IS NOT NULL) THEN ad.cod_capitolo = $6 ELSE true END AND
	     
	     /* ENTE ID */
	     CASE WHEN ($7 IS NOT NULL) THEN e.mygov_ente_id = $7 AND e.mygov_ente_id = $7 ELSE true END AND

	     /* CODICE ACCERTAMENTO */
	     CASE WHEN ($8 IS NOT NULL) THEN ad.cod_accertamento = $8 ELSE true END 
      UNION 
     
      -- INCASSATI 
	SELECT 
	      DISTINCT(r.cod_iud_e) AS iud
	FROM
	     mygov_import_export_rendicontazione_tesoreria_completa AS r 

	     INNER JOIN mygov_accertamento_dettaglio AS ad ON r.cod_iud_e = ad.cod_iud AND r.cod_tipo_dovuto_e = ad.cod_tipo_dovuto
		
	     INNER JOIN mygov_accertamento AS a ON a.mygov_accertamento_id = ad.mygov_accertamento_id

	     INNER JOIN mygov_anagrafica_stato AS st ON st.mygov_anagrafica_stato_id = a.mygov_anagrafica_stato_id
	    
	     INNER JOIN mygov_ente e ON e.cod_ipa_ente = ad.cod_ipa_ente AND r.mygov_ente_id = e.mygov_ente_id 
	WHERE 
	    /* ACCERTAMENTI CHIUSI */
	    st.de_tipo_stato = 'ACCERTAMENTO' AND st.cod_stato = 'CHIUSO' AND 

	    /* CLASSSIFICAZIONE */
	    (r.classificazione_completezza = 'RT_IUF_TES' OR r.classificazione_completezza = 'RT_TES') AND

	    /* ANNO */
	    CASE WHEN ($1 IS NOT NULL) THEN EXTRACT(YEAR FROM r.dt_data_valuta_t)::integer = $1 ELSE true END AND	

	    /* MESE */
	    CASE WHEN ($2 IS NOT NULL) THEN EXTRACT(MONTH FROM r.dt_data_valuta_t)::integer = $2 ELSE true END AND	

	    /* GIORNO */
	    CASE WHEN ($3 IS NOT NULL) THEN EXTRACT(DAY FROM r.dt_data_valuta_t)::integer = $3 ELSE true END AND	
	      
	    /* CODICE UFFICIO */
	    CASE WHEN ($4 IS NOT NULL) THEN ad.cod_ufficio = $4 ELSE true END AND
	     
	    /* CODICE DOVUTO */
	    CASE WHEN ($5 IS NOT NULL) THEN r.cod_tipo_dovuto_e = $5 AND ad.cod_tipo_dovuto = $5 ELSE true END AND

	    /* CODICE CAPITOLO */
	    CASE WHEN ($6 IS NOT NULL) THEN ad.cod_capitolo = $6 ELSE true END AND
	     
	    /* ENTE ID */
	    CASE WHEN ($7 IS NOT NULL) THEN r.mygov_ente_id = $7 AND e.mygov_ente_id = $7 ELSE true END AND

	    /* CODICE ACCERTAMENTO */
	    CASE WHEN ($8 IS NOT NULL) THEN ad.cod_accertamento = $8 ELSE true END 
	     
     ) as subq
 ORDER BY subq.iud	 
   
$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;




