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
