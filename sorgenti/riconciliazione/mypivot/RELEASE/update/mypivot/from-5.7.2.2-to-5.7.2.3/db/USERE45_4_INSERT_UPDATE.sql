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

update mygov_manage_flusso
set de_percorso_file = replace(de_percorso_file, 'TESORERIA_FLUSSI_MP', 'TESORERIA_FLUSSI_MP_ELABORATI')
where mygov_tipo_flusso_id in (select mygov_tipo_flusso_id from mygov_tipo_flusso where cod_tipo in ('T', 'C', 'O'))      
and de_percorso_file is not null;
