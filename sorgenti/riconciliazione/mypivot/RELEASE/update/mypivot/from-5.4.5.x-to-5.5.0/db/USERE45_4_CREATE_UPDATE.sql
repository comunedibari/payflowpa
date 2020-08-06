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