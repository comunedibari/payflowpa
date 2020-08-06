SHOW search_path;
CREATE SCHEMA sol_install
  AUTHORIZATION sol_install;

GRANT ALL ON SCHEMA sol_install TO sol_install;
GRANT USAGE ON SCHEMA sol_install TO role_sol_appl;

---------------------------------------

Mi devo collegare sul database di default
create schema pa_schema AUTHORIZATION "mypay.pa";
grant all|usage on pa_schema to "mypay.pa";

Nota : lo USE di oracle e' equivalente a:
set search_path to pa_schema;

Dentro una sessione pgsql e' possibile connettersi ad un altro database come:
\connect mybox;
