--
-- PostgreSQL database dump
--

-- Dumped from database version 9.6.0
-- Dumped by pg_dump version 9.6.0

-- Started on 2016-10-12 17:32:22 CEST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;
SET role '__TAG_MYBOX_DB_USERNAME__';

-- SET search_path = public, pg_catalog;
SET search_path = '__TAG_MYBOX_DB_SCHEMA__';

SET default_with_oids = false;

--
-- TOC entry 185 (class 1259 OID 424408)
-- Name: mybox_authorization; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE mybox_authorization (
    mybox_authorization_id bigint NOT NULL,
    version integer DEFAULT 0 NOT NULL,
    mybox_client_key character varying(256) NOT NULL,
    authorization_token character varying(256) NOT NULL,
    "timestamp" timestamp without time zone DEFAULT now() NOT NULL
);


--
-- TOC entry 186 (class 1259 OID 424416)
-- Name: mybox_authorization_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE mybox_authorization_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 187 (class 1259 OID 424418)
-- Name: mybox_client; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE mybox_client (
    mybox_client_id bigint NOT NULL,
    version integer DEFAULT 0 NOT NULL,
    mybox_client_key character varying(256) NOT NULL,
    client_secret character varying(256) NOT NULL,
    upload_home character varying(256) NOT NULL,
    download_home character varying(256) NOT NULL,
    locked boolean DEFAULT false NOT NULL
);


--
-- TOC entry 188 (class 1259 OID 424426)
-- Name: mybox_client_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE mybox_client_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2179 (class 0 OID 424408)
-- Dependencies: 185
-- Data for Name: mybox_authorization; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2190 (class 0 OID 0)
-- Dependencies: 186
-- Name: mybox_authorization_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('mybox_authorization_id_seq', 146, true);


--
-- TOC entry 2181 (class 0 OID 424418)
-- Dependencies: 187
-- Data for Name: mybox_client; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2191 (class 0 OID 0)
-- Dependencies: 188
-- Name: mybox_client_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('mybox_client_id_seq', 184, true);


--
-- TOC entry 2052 (class 2606 OID 424429)
-- Name: mybox_authorization mybox_authorization_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mybox_authorization
    ADD CONSTRAINT mybox_authorization_pk PRIMARY KEY (mybox_authorization_id);


--
-- TOC entry 2054 (class 2606 OID 424431)
-- Name: mybox_authorization mybox_authorization_uq_authorization_token; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mybox_authorization
    ADD CONSTRAINT mybox_authorization_uq_authorization_token UNIQUE (authorization_token);


--
-- TOC entry 2056 (class 2606 OID 424433)
-- Name: mybox_authorization mybox_authorization_uq_mybox_client_key; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mybox_authorization
    ADD CONSTRAINT mybox_authorization_uq_mybox_client_key UNIQUE (mybox_client_key);


--
-- TOC entry 2058 (class 2606 OID 424435)
-- Name: mybox_client mybox_client_pk; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mybox_client
    ADD CONSTRAINT mybox_client_pk PRIMARY KEY (mybox_client_id);


--
-- TOC entry 2060 (class 2606 OID 424437)
-- Name: mybox_client mybox_client_uq; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mybox_client
    ADD CONSTRAINT mybox_client_uq UNIQUE (mybox_client_key);


--
-- TOC entry 2061 (class 2606 OID 424438)
-- Name: mybox_authorization mybox_authorization_mybox_client_fk; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mybox_authorization
    ADD CONSTRAINT mybox_authorization_mybox_client_fk FOREIGN KEY (mybox_client_key) REFERENCES mybox_client(mybox_client_key);


-- Completed on 2016-10-12 17:32:22 CEST

--
-- PostgreSQL database dump complete
--

