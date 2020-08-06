--update mygov_flusso_export set rt_send = False;
--update mygov_flusso_export set to_send = False;

--SELECT * FROM get_flusso_export_to_send_gepos_function(1);
--SELECT * FROM get_import_export_rend_tes_to_send_gepos_function();


ALTER TABLE mygov_flusso_export ADD COLUMN blb_rt_payload text;
ALTER TABLE mygov_flusso_export ADD COLUMN rt_send boolean DEFAULT false;
ALTER TABLE mygov_flusso_export ADD COLUMN rpp_send boolean DEFAULT false;
ALTER TABLE mygov_flusso_export ADD COLUMN gdc_send boolean DEFAULT false;

CREATE OR REPLACE FUNCTION public.get_flusso_export_to_send_gepos_function(_id_ente bigint)
  RETURNS SETOF mygov_flusso_export AS
$BODY$
   SELECT 
      exp.*
   FROM 
      mygov_flusso_export as exp

   
   WHERE 
   --CASE WHEN _id_ente IS NOT NULL THEN exp.mygov_ente_id = _id_ente ELSE true END 

   --AND  
   exp.cod_tipo_dovuto in (
	SELECT cod_tipo FROM mygov_ente_tipo_dovuto AS dv
	WHERE 
	      CASE WHEN _id_ente IS NOT NULL THEN dv.mygov_ente_id = _id_ente ELSE true END 
	)

   AND   exp.rt_send = False
   

   --OFFSET CASE WHEN (_page IS NOT NULL) THEN ((_page - 1)*_size) ELSE 0 END 
   --LIMIT CASE WHEN (_size IS NOT NULL) THEN _size ELSE 5 END;
$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_flusso_export_to_send_gepos_function(bigint)
  OWNER TO mypivot;
GRANT EXECUTE ON FUNCTION public.get_flusso_export_to_send_gepos_function(bigint) TO public;
GRANT EXECUTE ON FUNCTION public.get_flusso_export_to_send_gepos_function(bigint) TO mypivot;
GRANT EXECUTE ON FUNCTION public.get_flusso_export_to_send_gepos_function(bigint) TO postgres;



CREATE OR REPLACE FUNCTION public.get_import_export_rend_tes_to_send_gepos_function()
    --(_codice_ipa_ente character varying)
  RETURNS SETOF mygov_import_export_rendicontazione_tesoreria AS
$BODY$
   SELECT 
      tes.*
   FROM 
      mygov_import_export_rendicontazione_tesoreria as tes 

   FULL JOIN mygov_ente as en ON en.cod_ipa_ente = tes.codice_ipa_ente
   FULL JOIN mygov_flusso_export as  fe ON fe.mygov_ente_id = en.mygov_ente_id AND tes.cod_iud_key::text = fe.cod_iud::text
   
   WHERE 
   --CASE WHEN (_codice_ipa_ente <> '') IS TRUE THEN tes.codice_ipa_ente = _codice_ipa_ente ELSE true END 

   --AND
   tes.classificazione_completezza in ('RT_IUF', 'RT_IUF_TES','RT_TES', 'IUD_RT_IUF')

   AND   (fe.rpp_send = False OR fe.gdc_send = False)
   
   ORDER BY dt_data_esito_singolo_pagamento, codice_iuv, codice_iud 

   --OFFSET CASE WHEN (_page IS NOT NULL) THEN ((_page - 1)*_size) ELSE 0 END 
   --LIMIT CASE WHEN (_size IS NOT NULL) THEN _size ELSE 5 END;
$BODY$
  LANGUAGE sql STABLE
  COST 100
  ROWS 1000;
ALTER FUNCTION public.get_import_export_rend_tes_to_send_gepos_function()
  OWNER TO mypivot;
GRANT EXECUTE ON FUNCTION public.get_import_export_rend_tes_to_send_gepos_function() TO public;
GRANT EXECUTE ON FUNCTION public.get_import_export_rend_tes_to_send_gepos_function() TO mypivot;
GRANT EXECUTE ON FUNCTION public.get_import_export_rend_tes_to_send_gepos_function() TO postgres;


