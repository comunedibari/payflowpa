SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;
SET search_path = public, pg_catalog;
SET SESSION AUTHORIZATION DEFAULT;


-- DEFINIZIONE RUOLO ACCERTAMENTO
SELECT pg_catalog.setval('roles_id_seq', (select coalesce(max(id_role), 1) from role), (select count(1) > 0 from role));
INSERT INTO role( id_role, version, role_name) VALUES (nextval('roles_id_seq'), 0, 'ROLE_ACC');

