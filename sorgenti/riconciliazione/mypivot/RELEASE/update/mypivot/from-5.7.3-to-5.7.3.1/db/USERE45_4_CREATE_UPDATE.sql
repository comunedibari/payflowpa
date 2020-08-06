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


-- FUNZIONE get_count_tesoreria_subset_function


CREATE OR REPLACE FUNCTION get_count_tesoreria_subset_function(
    _codice_ipa_ente character varying,
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
    _flagnascosto boolean,
    _classificazione_completezza character varying,
    _cod_iuv character varying,
    _cod_iuf character varying)
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
   AND   CASE WHEN (_cod_iuf <> '') IS TRUE THEN upper(tes.identificativo_flusso_rendicontazione) like upper('%' || _cod_iuf || '%') ELSE true END;
$BODY$
  LANGUAGE sql STABLE
  COST 100;

-- FUNZIONE get_tesoreria_subset_function

CREATE OR REPLACE FUNCTION get_tesoreria_subset_function(
    IN _codice_ipa_ente character varying,
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
    IN _flagnascosto boolean,
    IN _classificazione_completezza character varying,
    IN _cod_iuv character varying,
    IN _cod_iuf character varying,
    IN _page integer,
    IN _size integer)
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
   AND   CASE WHEN (_cod_iuf <> '') IS TRUE THEN upper(tes.identificativo_flusso_rendicontazione) like upper('%' || _cod_iuf || '%') ELSE true END
      
   ORDER BY tes.dt_data_valuta, tes.identificativo_flusso_rendicontazione, tes.codice_iuv
   OFFSET CASE WHEN (_page IS NOT NULL) THEN ((_page - 1)*_size) ELSE 0 END 
   LIMIT CASE WHEN (_size IS NOT NULL) THEN _size ELSE 5 END;
$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;
