--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.0
-- Dumped by pg_dump version 9.6.0

-- Started on 2016-10-12 17:39:23 CEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;
SET role '__TAG_MYPROFILE_DB_USERNAME__';

-- SET search_path = public, pg_catalog;
SET search_path = '__TAG_MYPROFILE_DB_SCHEMA__';

SET default_with_oids = false;

--
-- TOC entry 185 (class 1259 OID 424447)
-- Name: application; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE application (
    id_application bigint NOT NULL,
    version integer NOT NULL,
    application_code character varying(250) NOT NULL,
    description character varying(500) NOT NULL
);


--
-- TOC entry 186 (class 1259 OID 424453)
-- Name: applications_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE applications_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 187 (class 1259 OID 424455)
-- Name: permission; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE permission (
    id_permission bigint NOT NULL,
    version integer NOT NULL,
    permissions character varying(500) NOT NULL,
    resource character varying(2500),
    id_role bigint NOT NULL
);


--
-- TOC entry 188 (class 1259 OID 424461)
-- Name: permissions_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE permissions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 189 (class 1259 OID 424463)
-- Name: role; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE role (
    id_role bigint NOT NULL,
    version integer NOT NULL,
    role_name character varying(250) NOT NULL
);


--
-- TOC entry 190 (class 1259 OID 424466)
-- Name: roles_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE roles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 191 (class 1259 OID 424468)
-- Name: tenant; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE tenant (
    id_tenant bigint NOT NULL,
    version integer NOT NULL,
    tenant_code character varying(250) NOT NULL,
    description character varying(500)
);


--
-- TOC entry 192 (class 1259 OID 424474)
-- Name: tenants_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE tenants_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 193 (class 1259 OID 424476)
-- Name: user; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE "user" (
    id_user bigint NOT NULL,
    version integer NOT NULL,
    user_code character varying(250) NOT NULL,
    user_last_name character varying(250) NOT NULL,
    user_name character varying(250) NOT NULL,
    user_tax_code character varying(16) NOT NULL,
    user_domain character varying(250) NOT NULL,
    user_mail character varying(250),
    user_address character varying(250)
);


--
-- TOC entry 194 (class 1259 OID 424482)
-- Name: user_role_appl_tenant; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE user_role_appl_tenant (
    id_user_role_appl_tenant bigint NOT NULL,
    version integer NOT NULL,
    id_user bigint NOT NULL,
    id_role bigint NOT NULL,
    id_application bigint NOT NULL,
    id_tenant bigint NOT NULL
);


--
-- TOC entry 195 (class 1259 OID 424485)
-- Name: userroleappltenant_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE userroleappltenant_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 196 (class 1259 OID 424487)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 197 (class 1259 OID 424489)
-- Name: v_user_role_appl_tenant; Type: VIEW; Schema: public; Owner: -
--

CREATE VIEW v_user_role_appl_tenant AS
 SELECT v.id_user_role_appl_tenant,
    v.id_user,
    v.id_application,
    v.id_role,
    v.id_tenant,
    u.user_code,
    a.application_code,
    t.description AS desc_tenant,
    r.role_name AS desc_role
   FROM ((((user_role_appl_tenant v
     JOIN application a ON ((a.id_application = v.id_application)))
     JOIN role r ON ((r.id_role = v.id_role)))
     JOIN tenant t ON ((t.id_tenant = v.id_tenant)))
     JOIN "user" u ON ((u.id_user = v.id_user)))
  ORDER BY v.id_user, v.id_application, v.id_tenant, v.id_role;


--
-- TOC entry 2219 (class 0 OID 424447)
-- Dependencies: 185
-- Data for Name: application; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO application (id_application, version, application_code, description) VALUES (1, 0, 'MY_PIVOT', 'MY PIVOT');
INSERT INTO application (id_application, version, application_code, description) VALUES (2, 0, 'MY_PAY', 'MY PAY');


--
-- TOC entry 2238 (class 0 OID 0)
-- Dependencies: 186
-- Name: applications_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('applications_id_seq', 2, true);


--
-- TOC entry 2221 (class 0 OID 424455)
-- Dependencies: 187
-- Data for Name: permission; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2239 (class 0 OID 0)
-- Dependencies: 188
-- Name: permissions_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('permissions_id_seq', 1, false);


--
-- TOC entry 2223 (class 0 OID 424463)
-- Dependencies: 189
-- Data for Name: role; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO role (id_role, version, role_name) VALUES (1, 0, 'ROLE_VISUAL');
INSERT INTO role (id_role, version, role_name) VALUES (2, 0, 'ROLE_ADMIN');


--
-- TOC entry 2240 (class 0 OID 0)
-- Dependencies: 190
-- Name: roles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('roles_id_seq', 2, true);


--
-- TOC entry 2225 (class 0 OID 424468)
-- Dependencies: 191
-- Data for Name: tenant; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2241 (class 0 OID 0)
-- Dependencies: 192
-- Name: tenants_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('tenants_id_seq', 184, true);


--
-- TOC entry 2227 (class 0 OID 424476)
-- Dependencies: 193
-- Data for Name: user; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2228 (class 0 OID 424482)
-- Dependencies: 194
-- Data for Name: user_role_appl_tenant; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2242 (class 0 OID 0)
-- Dependencies: 195
-- Name: userroleappltenant_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('userroleappltenant_id_seq', 1553, true);


--
-- TOC entry 2243 (class 0 OID 0)
-- Dependencies: 196
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('users_id_seq', 405, true);


--
-- TOC entry 2078 (class 2606 OID 424495)
-- Name: application application_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY application
    ADD CONSTRAINT application_pkey PRIMARY KEY (id_application);


--
-- TOC entry 2083 (class 2606 OID 424497)
-- Name: permission permission_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT permission_pkey PRIMARY KEY (id_permission);


--
-- TOC entry 2086 (class 2606 OID 424499)
-- Name: role role_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id_role);


--
-- TOC entry 2089 (class 2606 OID 424501)
-- Name: tenant tenant_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY tenant
    ADD CONSTRAINT tenant_pkey PRIMARY KEY (id_tenant);


--
-- TOC entry 2092 (class 2606 OID 424503)
-- Name: user user_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY "user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (id_user);


--
-- TOC entry 2095 (class 2606 OID 424505)
-- Name: user_role_appl_tenant user_role_appl_tenant_pkey; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_role_appl_tenant
    ADD CONSTRAINT user_role_appl_tenant_pkey PRIMARY KEY (id_user_role_appl_tenant);


--
-- TOC entry 2079 (class 1259 OID 424506)
-- Name: idx_application_code; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX idx_application_code ON application USING btree (application_code);


--
-- TOC entry 2080 (class 1259 OID 424507)
-- Name: idx_resource; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_resource ON permission USING btree (resource);


--
-- TOC entry 2081 (class 1259 OID 424508)
-- Name: idx_role; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_role ON permission USING btree (id_role);


--
-- TOC entry 2084 (class 1259 OID 424509)
-- Name: idx_role_name; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX idx_role_name ON role USING btree (role_name);


--
-- TOC entry 2087 (class 1259 OID 424510)
-- Name: idx_tenant_code; Type: INDEX; Schema: public; Owner: -
--

CREATE UNIQUE INDEX idx_tenant_code ON tenant USING btree (tenant_code);


--
-- TOC entry 2090 (class 1259 OID 424511)
-- Name: idx_user_code; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_user_code ON "user" USING btree (user_code);


--
-- TOC entry 2093 (class 1259 OID 424512)
-- Name: idx_userapptenant; Type: INDEX; Schema: public; Owner: -
--

CREATE INDEX idx_userapptenant ON user_role_appl_tenant USING btree (id_user, id_application, id_tenant);


--
-- TOC entry 2096 (class 2606 OID 424513)
-- Name: permission fk_549km6l9xlsct03kx9dno8n9a; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY permission
    ADD CONSTRAINT fk_549km6l9xlsct03kx9dno8n9a FOREIGN KEY (id_role) REFERENCES role(id_role);


--
-- TOC entry 2097 (class 2606 OID 424518)
-- Name: user_role_appl_tenant fk_8hslxayh62hwvttjmp4pg28f1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_role_appl_tenant
    ADD CONSTRAINT fk_8hslxayh62hwvttjmp4pg28f1 FOREIGN KEY (id_user) REFERENCES "user"(id_user);


--
-- TOC entry 2098 (class 2606 OID 424523)
-- Name: user_role_appl_tenant fk_f00la7a33ql8dh3wae8gtnyqu; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_role_appl_tenant
    ADD CONSTRAINT fk_f00la7a33ql8dh3wae8gtnyqu FOREIGN KEY (id_role) REFERENCES role(id_role);


--
-- TOC entry 2099 (class 2606 OID 424528)
-- Name: user_role_appl_tenant fk_h5wvtl1bbvfnxibey1tsrrgl1; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_role_appl_tenant
    ADD CONSTRAINT fk_h5wvtl1bbvfnxibey1tsrrgl1 FOREIGN KEY (id_application) REFERENCES application(id_application);


--
-- TOC entry 2100 (class 2606 OID 424533)
-- Name: user_role_appl_tenant fk_iy6fpvqignrkxxjgo2x201j2j; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY user_role_appl_tenant
    ADD CONSTRAINT fk_iy6fpvqignrkxxjgo2x201j2j FOREIGN KEY (id_tenant) REFERENCES tenant(id_tenant);


-- Completed on 2016-10-12 17:39:24 CEST

--
-- PostgreSQL database dump complete
--

