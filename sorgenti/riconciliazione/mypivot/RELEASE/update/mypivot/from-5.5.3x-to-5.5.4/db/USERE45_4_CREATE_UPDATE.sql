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
  
      