--
-- PostgreSQL database dump
--

-- Started on 2014-11-20 09:14:39

SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = off;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET escape_string_warning = off;

--
-- TOC entry 504 (class 2612 OID 6771883)
-- Name: plpgsql; Type: PROCEDURAL LANGUAGE; Schema: -; Owner: -
--

CREATE PROCEDURAL LANGUAGE plpgsql;


SET search_path = public, pg_catalog;

--
-- TOC entry 172 (class 1255 OID 6772056)
-- Dependencies: 504 3
-- Name: inserisci_richiesta_ente(bigint); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION inserisci_richiesta_ente(mygov_ente_id_n bigint) RETURNS void
    LANGUAGE plpgsql
    AS $$
    DECLARE
        pren_cursor		CURSOR FOR 
				SELECT pren.*
				FROM mygov_ente_prenotazione pren
				    ,mygov_anagrafica_stato stato				    
				WHERE pren.mygov_anagrafica_stato_id = stato.mygov_anagrafica_stato_id
				  AND stato.cod_stato <> 'ERROR_CHIEDI_STATO_EXPORT' 
				  AND stato.cod_stato <> 'ERROR_PRENOTAZIONE'
				  AND stato.cod_stato <> 'ERRORE_DOWNLOAD'
				  AND stato.de_tipo_stato = 'FLUSSO_EXPORT'
				  AND pren.mygov_ente_id = mygov_ente_id_n
				ORDER BY pren.dt_prenota_date_from;
				  
        pren_record		mygov_ente_prenotazione%ROWTYPE; 

        date_prec		date;
        differenza		integer;  
    
    BEGIN

	OPEN pren_cursor;

	LOOP
		FETCH pren_cursor INTO pren_record;
		EXIT WHEN NOT FOUND;
		
		IF date_prec IS NULL THEN
			date_prec := pren_record.dt_prenota_date_to;
			CONTINUE;
		END IF;
		differenza := pren_record.dt_prenota_date_from - (date_prec + 1);
		IF differenza > 0 THEN
			INSERT INTO mygov_ente_prenotazione(
					    mygov_ente_prenotazione_id, "version", mygov_ente_id, mygov_anagrafica_stato_id, 
					    dt_prenota_date_from, dt_prenota_date_to, cod_prenota_identificativo_tipo_dovuto,  
					    dt_creazione, dt_ultima_modifica)
				    VALUES (nextval('mygov_ente_prenotazione_mygov_ente_prenotazione_id_seq'), 0, mygov_ente_id_n, 
				              (SELECT mygov_anagrafica_stato_id 
				                 FROM mygov_anagrafica_stato
				                WHERE cod_stato = 'INSERITO'
				                  AND de_tipo_stato = 'FLUSSO_EXPORT')
				            , date_prec + 1, pren_record.dt_prenota_date_from - 1, 'ALL', 
					    current_timestamp, current_timestamp);
		END IF;
		date_prec := pren_record.dt_prenota_date_to;
	END LOOP;

	CLOSE pren_cursor;
	IF date_prec IS NULL THEN
		INSERT INTO mygov_ente_prenotazione(
				    mygov_ente_prenotazione_id, "version", mygov_ente_id, mygov_anagrafica_stato_id, 
				    dt_prenota_date_from, dt_prenota_date_to, cod_prenota_identificativo_tipo_dovuto,  
				    dt_creazione, dt_ultima_modifica)
			    VALUES (nextval('mygov_ente_prenotazione_mygov_ente_prenotazione_id_seq'), 0, mygov_ente_id_n, 
				      (SELECT mygov_anagrafica_stato_id 
					 FROM mygov_anagrafica_stato
					WHERE cod_stato = 'INSERITO'
					  AND de_tipo_stato = 'FLUSSO_EXPORT')
				    , '2014-01-01', current_date - 1, 'ALL', 
				    current_timestamp, current_timestamp);
	END IF;

	IF date_prec < current_date - 1 THEN
		INSERT INTO mygov_ente_prenotazione(
				    mygov_ente_prenotazione_id, "version", mygov_ente_id, mygov_anagrafica_stato_id, 
				    dt_prenota_date_from, dt_prenota_date_to, cod_prenota_identificativo_tipo_dovuto,  
				    dt_creazione, dt_ultima_modifica)
			    VALUES (nextval('mygov_ente_prenotazione_mygov_ente_prenotazione_id_seq'), 0, mygov_ente_id_n, 
				      (SELECT mygov_anagrafica_stato_id 
					 FROM mygov_anagrafica_stato
					WHERE cod_stato = 'INSERITO'
					  AND de_tipo_stato = 'FLUSSO_EXPORT')
				    , date_prec + 1, current_date - 1, 'ALL', 
				    current_timestamp, current_timestamp);
	END IF;

    END;
$$;


--
-- TOC entry 173 (class 1255 OID 6772057)
-- Dependencies: 3 504
-- Name: inserisci_richiesta_flusso_rendicontazione(bigint); Type: FUNCTION; Schema: public; Owner: -
--

CREATE FUNCTION inserisci_richiesta_flusso_rendicontazione(mygov_entepsp_id_n bigint) RETURNS void
    LANGUAGE plpgsql
    AS $$
    DECLARE
        pren_cursor		CURSOR FOR 
				SELECT pren.*
				FROM mygov_prenotazione_flusso_rendicontazione pren		    
				WHERE pren.mygov_entepsp_id = mygov_entepsp_id_n
				ORDER BY pren.dt_date_from;
				  
        pren_record		mygov_prenotazione_flusso_rendicontazione%ROWTYPE; 

        date_prec		date;
        differenza		integer;  
    
    BEGIN

	OPEN pren_cursor;

	LOOP
		FETCH pren_cursor INTO pren_record;
		EXIT WHEN NOT FOUND;
		
		IF date_prec IS NULL THEN
			date_prec := pren_record.dt_date_to;
			CONTINUE;
		END IF;
		differenza := pren_record.dt_date_from - (date_prec + 1);
		IF differenza > 0 THEN
			INSERT INTO mygov_prenotazione_flusso_rendicontazione(
					    mygov_prenotazione_flusso_rendicontazione_id, "version", mygov_entepsp_id, 
					    mygov_anagrafica_stato_id, mygov_tipo_flusso_id, dt_date_from, 
					    dt_date_to, dt_creazione, dt_ultima_modifica)
				    VALUES (nextval('mygov_prenotazione_flusso_ren_mygov_prenotazione_flusso_ren_seq'), 0, mygov_entepsp_id_n, 
				              (SELECT mygov_anagrafica_stato_id 
				                 FROM mygov_anagrafica_stato
				                WHERE cod_stato = 'INSERITO'
				                  AND de_tipo_stato = 'PRENOTAZIONE_RENDICONTAZIONE')
				            , ( SELECT mygov_tipo_flusso_id
						  FROM mygov_tipo_flusso
						 WHERE cod_tipo = 'R'), date_prec + 1, pren_record.dt_date_from - 1, 
					    current_timestamp, current_timestamp);
		END IF;
		date_prec := pren_record.dt_date_to;
	END LOOP;

	CLOSE pren_cursor;
	IF date_prec IS NULL THEN
		INSERT INTO mygov_prenotazione_flusso_rendicontazione(
				    mygov_prenotazione_flusso_rendicontazione_id, "version", mygov_entepsp_id, 
					    mygov_anagrafica_stato_id, mygov_tipo_flusso_id, dt_date_from, 
					    dt_date_to, dt_creazione, dt_ultima_modifica)
			    VALUES (nextval('mygov_prenotazione_flusso_ren_mygov_prenotazione_flusso_ren_seq'), 0, mygov_entepsp_id_n, 
				      (SELECT mygov_anagrafica_stato_id 
					 FROM mygov_anagrafica_stato
					WHERE cod_stato = 'INSERITO'
					  AND de_tipo_stato = 'PRENOTAZIONE_RENDICONTAZIONE'), 
					  ( SELECT mygov_tipo_flusso_id
						  FROM mygov_tipo_flusso
						 WHERE cod_tipo = 'R')
				    , '2014-01-01', current_date - 1,  
				    current_timestamp, current_timestamp);
	END IF;

	IF date_prec < current_date - 1 THEN
		INSERT INTO mygov_prenotazione_flusso_rendicontazione(
				    mygov_prenotazione_flusso_rendicontazione_id, "version", mygov_entepsp_id, 
					    mygov_anagrafica_stato_id, mygov_tipo_flusso_id, dt_date_from, 
					    dt_date_to, dt_creazione, dt_ultima_modifica)
			    VALUES (nextval('mygov_prenotazione_flusso_ren_mygov_prenotazione_flusso_ren_seq'), 0, mygov_entepsp_id_n, 
				      (SELECT mygov_anagrafica_stato_id 
					 FROM mygov_anagrafica_stato
					WHERE cod_stato = 'INSERITO'
					  AND de_tipo_stato = 'PRENOTAZIONE_RENDICONTAZIONE'), 
					  ( SELECT mygov_tipo_flusso_id
						  FROM mygov_tipo_flusso
						 WHERE cod_tipo = 'R')
				    , date_prec + 1, current_date - 1, 
				    current_timestamp, current_timestamp);
	END IF;

    END;
$$;


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 140 (class 1259 OID 6771885)
-- Dependencies: 3
-- Name: mygov_anagrafica_stato; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE mygov_anagrafica_stato (
    mygov_anagrafica_stato_id bigint NOT NULL,
    version integer NOT NULL,
    cod_stato character varying(80) NOT NULL,
    de_stato character varying(100) NOT NULL,
    de_tipo_stato character varying(80) NOT NULL,
    dt_creazione timestamp without time zone NOT NULL,
    dt_ultima_modifica timestamp without time zone NOT NULL
);


--
-- TOC entry 141 (class 1259 OID 6771888)
-- Dependencies: 3 140
-- Name: mygov_anagrafica_stato_mygov_anagrafica_stato_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE mygov_anagrafica_stato_mygov_anagrafica_stato_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1895 (class 0 OID 0)
-- Dependencies: 141
-- Name: mygov_anagrafica_stato_mygov_anagrafica_stato_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE mygov_anagrafica_stato_mygov_anagrafica_stato_id_seq OWNED BY mygov_anagrafica_stato.mygov_anagrafica_stato_id;


--
-- TOC entry 1896 (class 0 OID 0)
-- Dependencies: 141
-- Name: mygov_anagrafica_stato_mygov_anagrafica_stato_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('mygov_anagrafica_stato_mygov_anagrafica_stato_id_seq', 24, true);


--
-- TOC entry 142 (class 1259 OID 6771890)
-- Dependencies: 3
-- Name: mygov_ente; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE mygov_ente (
    mygov_ente_id bigint NOT NULL,
    version integer NOT NULL,
    cod_ipa_ente character varying(80) NOT NULL,
    codice_fiscale_ente character varying(11) NOT NULL,
    de_nome_ente character varying(100) NOT NULL,
    email_amministratore character varying(50),
    cod_global_location_number character varying(13),
    dt_creazione timestamp without time zone NOT NULL,
    dt_ultima_modifica timestamp without time zone NOT NULL
);


--
-- TOC entry 143 (class 1259 OID 6771893)
-- Dependencies: 3
-- Name: mygov_ente_mygov_ente_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE mygov_ente_mygov_ente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1897 (class 0 OID 0)
-- Dependencies: 143
-- Name: mygov_ente_mygov_ente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('mygov_ente_mygov_ente_id_seq', 2, true);


--
-- TOC entry 144 (class 1259 OID 6771895)
-- Dependencies: 3
-- Name: mygov_ente_prenotazione; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE mygov_ente_prenotazione (
    mygov_ente_prenotazione_id bigint NOT NULL,
    version integer NOT NULL,
    mygov_ente_id bigint NOT NULL,
    mygov_anagrafica_stato_id bigint NOT NULL,
    dt_prenota_date_from date NOT NULL,
    dt_prenota_date_to date NOT NULL,
    cod_prenota_identificativo_tipo_dovuto character varying(64) NOT NULL,
    de_prenota_fault_fault_code character varying(42),
    de_prenota_fault_fault_string character varying(126),
    de_prenota_fault_id character varying(16),
    de_prenota_fault_description text,
    de_prenota_fault_serial integer,
    request_token character varying(64),
    de_chiedi_stato_fault_fault_code character varying(42),
    de_chiedi_stato_fault_fault_string character varying(126),
    de_chiedi_stato_fault_id character varying(16),
    de_chiedi_stato_fault_description text,
    de_chiedi_stato_fault_serial integer,
    de_chiedi_stato_stato character varying(64),
    de_chiedi_stato_download_url text,
    dt_creazione timestamp without time zone NOT NULL,
    dt_ultima_modifica timestamp without time zone NOT NULL
);


--
-- TOC entry 145 (class 1259 OID 6771901)
-- Dependencies: 144 3
-- Name: mygov_ente_prenotazione_mygov_ente_prenotazione_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE mygov_ente_prenotazione_mygov_ente_prenotazione_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1898 (class 0 OID 0)
-- Dependencies: 145
-- Name: mygov_ente_prenotazione_mygov_ente_prenotazione_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE mygov_ente_prenotazione_mygov_ente_prenotazione_id_seq OWNED BY mygov_ente_prenotazione.mygov_ente_prenotazione_id;


--
-- TOC entry 1899 (class 0 OID 0)
-- Dependencies: 145
-- Name: mygov_ente_prenotazione_mygov_ente_prenotazione_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('mygov_ente_prenotazione_mygov_ente_prenotazione_id_seq', 1, false);


--
-- TOC entry 146 (class 1259 OID 6771903)
-- Dependencies: 3
-- Name: mygov_entepsp; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE mygov_entepsp (
    mygov_entepsp_id bigint NOT NULL,
    version integer NOT NULL,
    mygov_ente_id bigint NOT NULL,
    identificativo_psp character varying(35) NOT NULL
);


--
-- TOC entry 147 (class 1259 OID 6771906)
-- Dependencies: 3
-- Name: mygov_entepsp_flusso_rendicontazione; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE mygov_entepsp_flusso_rendicontazione (
    mygov_entepsp_flusso_rendicontazione_id bigint NOT NULL,
    version integer NOT NULL,
    mygov_entepsp_id bigint NOT NULL,
    mygov_anagrafica_stato_id bigint NOT NULL,
    cod_lista_ipa_ente character varying(80) NOT NULL,
    flg_lista_tipo_flusso character(1) NOT NULL,
    identificativo_lista_psp character varying(35) NOT NULL,
    dt_lista_date_from date NOT NULL,
    dt_lista_date_to date NOT NULL,
    cod_lista_identificativo_flusso character varying(256) NOT NULL,
    dt_lista_data_ora_flusso timestamp without time zone NOT NULL,
    de_lista_fault_fault_code text,
    de_lista_fault_fault_string text,
    de_lista_fault_id text,
    de_lista_fault_description text,
    num_lista_fault_serial integer,
    cod_chiedi_ipa_ente character varying(80),
    flg_chiedi_tipo_flusso character(1),
    identificativo_chiedi_psp character varying(35),
    cod_chiedi_identificativo_flusso character varying(256),
    dt_chiedi_data_ora_flusso timestamp without time zone,
    de_chiedi_fault_fault_code text,
    de_chiedi_fault_fault_string text,
    de_chiedi_fault_id text,
    de_chiedi_fault_description text,
    num_chiedi_fault_serial integer,
    de_chiedi_stato character varying(64),
    de_chiedi_download_url text,
    dt_creazione timestamp without time zone NOT NULL,
    dt_ultima_modifica timestamp without time zone NOT NULL
);


--
-- TOC entry 148 (class 1259 OID 6771912)
-- Dependencies: 3 147
-- Name: mygov_entepsp_flusso_rendicon_mygov_entepsp_flusso_rendicon_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE mygov_entepsp_flusso_rendicon_mygov_entepsp_flusso_rendicon_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1900 (class 0 OID 0)
-- Dependencies: 148
-- Name: mygov_entepsp_flusso_rendicon_mygov_entepsp_flusso_rendicon_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE mygov_entepsp_flusso_rendicon_mygov_entepsp_flusso_rendicon_seq OWNED BY mygov_entepsp_flusso_rendicontazione.mygov_entepsp_flusso_rendicontazione_id;


--
-- TOC entry 1901 (class 0 OID 0)
-- Dependencies: 148
-- Name: mygov_entepsp_flusso_rendicon_mygov_entepsp_flusso_rendicon_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('mygov_entepsp_flusso_rendicon_mygov_entepsp_flusso_rendicon_seq', 1, false);


--
-- TOC entry 149 (class 1259 OID 6771914)
-- Dependencies: 146 3
-- Name: mygov_entepsp_mygov_entepsp_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE mygov_entepsp_mygov_entepsp_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1902 (class 0 OID 0)
-- Dependencies: 149
-- Name: mygov_entepsp_mygov_entepsp_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE mygov_entepsp_mygov_entepsp_id_seq OWNED BY mygov_entepsp.mygov_entepsp_id;


--
-- TOC entry 1903 (class 0 OID 0)
-- Dependencies: 149
-- Name: mygov_entepsp_mygov_entepsp_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('mygov_entepsp_mygov_entepsp_id_seq', 6, true);


--
-- TOC entry 150 (class 1259 OID 6771916)
-- Dependencies: 3
-- Name: mygov_flusso_export; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE mygov_flusso_export (
    version integer NOT NULL,
    dt_creazione timestamp without time zone NOT NULL,
    dt_ultima_modifica timestamp without time zone NOT NULL,
    mygov_ente_id bigint NOT NULL,
    de_nome_flusso character varying(50) NOT NULL,
    num_riga_flusso integer NOT NULL,
    cod_iud character varying(35) NOT NULL,
    cod_rp_silinviarp_id_univoco_versamento character varying(35),
    de_e_versione_oggetto character varying(16),
    cod_e_dom_id_dominio character varying(35),
    cod_e_dom_id_stazione_richiedente character varying(35),
    cod_e_id_messaggio_ricevuta character varying(35),
    dt_e_data_ora_messaggio_ricevuta timestamp without time zone,
    cod_e_riferimento_messaggio_richiesta character varying(35),
    dt_e_riferimento_data_richiesta date,
    cod_e_istit_att_id_univ_att_tipo_id_univoco character(1),
    cod_e_istit_att_id_univ_att_codice_id_univoco character varying(35) NOT NULL,
    de_e_istit_att_denominazione_attestante character varying(70),
    cod_e_istit_att_codice_unit_oper_attestante character varying(35),
    de_e_istit_att_denom_unit_oper_attestante character varying(70),
    de_e_istit_att_indirizzo_attestante character varying(70),
    de_e_istit_att_civico_attestante character varying(16),
    cod_e_istit_att_cap_attestante character varying(16),
    de_e_istit_att_localita_attestante character varying(35),
    de_e_istit_att_provincia_attestante character varying(35),
    cod_e_istit_att_nazione_attestante character varying(2),
    cod_e_ente_benef_id_univ_benef_tipo_id_univoco character(1),
    cod_e_ente_benef_id_univ_benef_codice_id_univoco character varying(35),
    de_e_ente_benef_denominazione_beneficiario character varying(70),
    cod_e_ente_benef_codice_unit_oper_beneficiario character varying(35),
    de_e_ente_benef_denom_unit_oper_beneficiario character varying(70),
    de_e_ente_benef_indirizzo_beneficiario character varying(70),
    de_e_ente_benef_civico_beneficiario character varying(16),
    cod_e_ente_benef_cap_beneficiario character varying(16),
    de_e_ente_benef_localita_beneficiario character varying(35),
    de_e_ente_benef_provincia_beneficiario character varying(2),
    cod_e_ente_benef_nazione_beneficiario character varying(2),
    cod_e_sogg_vers_id_univ_vers_tipo_id_univoco character(1),
    cod_e_sogg_vers_id_univ_vers_codice_id_univoco character varying(35),
    cod_e_sogg_vers_anagrafica_versante character varying(70),
    de_e_sogg_vers_indirizzo_versante character varying(70),
    de_e_sogg_vers_civico_versante character varying(16),
    cod_e_sogg_vers_cap_versante character varying(16),
    de_e_sogg_vers_localita_versante character varying(35),
    de_e_sogg_vers_provincia_versante character varying(35),
    cod_e_sogg_vers_nazione_versante character varying(2),
    de_e_sogg_vers_email_versante character varying(256),
    cod_e_sogg_pag_id_univ_pag_tipo_id_univoco character(1),
    cod_e_sogg_pag_id_univ_pag_codice_id_univoco character varying(35),
    cod_e_sogg_pag_anagrafica_pagatore character varying(70),
    de_e_sogg_pag_indirizzo_pagatore character varying(70),
    de_e_sogg_pag_civico_pagatore character varying(16),
    cod_e_sogg_pag_cap_pagatore character varying(16),
    de_e_sogg_pag_localita_pagatore character varying(35),
    de_e_sogg_pag_provincia_pagatore character varying(35),
    cod_e_sogg_pag_nazione_pagatore character varying(2),
    de_e_sogg_pag_email_pagatore character varying(256),
    cod_e_dati_pag_codice_esito_pagamento character(1),
    num_e_dati_pag_importo_totale_pagato numeric(12,2),
    cod_e_dati_pag_id_univoco_versamento character varying(35),
    cod_e_dati_pag_codice_contesto_pagamento character varying(35),
    num_e_dati_pag_dati_sing_pag_singolo_importo_pagato numeric(12,2),
    de_e_dati_pag_dati_sing_pag_esito_singolo_pagamento character varying(35),
    dt_e_dati_pag_dati_sing_pag_data_esito_singolo_pagamento date,
    cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss character varying(35) NOT NULL,
    de_e_dati_pag_dati_sing_pag_causale_versamento character varying(140),
    de_e_dati_pag_dati_sing_pag_dati_specifici_riscossione character varying(140)
);


--
-- TOC entry 151 (class 1259 OID 6771922)
-- Dependencies: 3
-- Name: mygov_flusso_rendicontazione; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE mygov_flusso_rendicontazione (
    version integer NOT NULL,
    dt_creazione timestamp without time zone NOT NULL,
    dt_ultima_modifica timestamp without time zone NOT NULL,
    mygov_ente_id bigint NOT NULL,
    mygov_manage_flusso_id bigint NOT NULL,
    identificativo_psp character varying(35) NOT NULL,
    versione_oggetto character varying(16) NOT NULL,
    cod_identificativo_flusso character varying(35) NOT NULL,
    dt_data_ora_flusso timestamp without time zone NOT NULL,
    cod_identificativo_univoco_regolamento character varying(35) NOT NULL,
    dt_data_regolamento date NOT NULL,
    cod_ist_mitt_id_univ_mitt_tipo_identificativo_univoco character(1) NOT NULL,
    cod_ist_mitt_id_univ_mitt_codice_identificativo_univoco character varying(35) NOT NULL,
    de_ist_mitt_denominazione_mittente character varying(70),
    cod_ist_ricev_id_univ_ricev_tipo_identificativo_univoco character(1),
    cod_ist_ricev_id_univ_ricev_codice_identificativo_univoco character varying(35),
    de_ist_ricev_denominazione_ricevente character varying(70),
    num_numero_totale_pagamenti numeric(15,0) NOT NULL,
    num_importo_totale_pagamenti numeric(18,2) NOT NULL,
    cod_dati_sing_pagam_identificativo_univoco_versamento character varying(35),
    cod_dati_sing_pagam_identificativo_univoco_riscossione character varying(35) NOT NULL,
    num_dati_sing_pagam_singolo_importo_pagato numeric(12,2) NOT NULL,
    cod_dati_sing_pagam_codice_esito_singolo_pagamento character varying(1) NOT NULL,
    dt_dati_sing_pagam_data_esito_singolo_pagamento date NOT NULL
);


--
-- TOC entry 152 (class 1259 OID 6771925)
-- Dependencies: 3
-- Name: mygov_manage_flusso; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE mygov_manage_flusso (
    mygov_manage_flusso_id bigint NOT NULL,
    version integer NOT NULL,
    mygov_ente_id bigint NOT NULL,
    identificativo_psp character varying(35),
    cod_identificativo_flusso character varying(35),
    dt_data_ora_flusso timestamp without time zone,
    cod_identificativo_univoco_regolamento character varying(35),
    dt_data_regolamento date,
    num_numero_totale_pagamenti numeric(18,2),
    cod_dati_sing_pagam_codice_esito_singolo_pagamento character varying(1),
    mygov_tipo_flusso_id bigint NOT NULL,
    mygov_utente_id bigint,
    mygov_anagrafica_stato_id bigint NOT NULL,
    de_percorso_file character varying(256),
    de_nome_file character varying(256),
    num_dimensione_file_scaricato bigint,
    cod_request_token text,
    dt_creazione timestamp without time zone NOT NULL,
    dt_ultima_modifica timestamp without time zone NOT NULL
);


--
-- TOC entry 153 (class 1259 OID 6771931)
-- Dependencies: 3 152
-- Name: mygov_manage_flusso_mygov_manage_flusso_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE mygov_manage_flusso_mygov_manage_flusso_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1904 (class 0 OID 0)
-- Dependencies: 153
-- Name: mygov_manage_flusso_mygov_manage_flusso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE mygov_manage_flusso_mygov_manage_flusso_id_seq OWNED BY mygov_manage_flusso.mygov_manage_flusso_id;


--
-- TOC entry 1905 (class 0 OID 0)
-- Dependencies: 153
-- Name: mygov_manage_flusso_mygov_manage_flusso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('mygov_manage_flusso_mygov_manage_flusso_id_seq', 1, false);


--
-- TOC entry 154 (class 1259 OID 6771933)
-- Dependencies: 3
-- Name: mygov_prenotazione_flusso_rendicontazione; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE mygov_prenotazione_flusso_rendicontazione (
    mygov_prenotazione_flusso_rendicontazione_id bigint NOT NULL,
    version integer NOT NULL,
    mygov_entepsp_id bigint NOT NULL,
    mygov_anagrafica_stato_id bigint NOT NULL,
    mygov_tipo_flusso_id bigint NOT NULL,
    dt_date_from date NOT NULL,
    dt_date_to date NOT NULL,
    dt_creazione timestamp without time zone NOT NULL,
    dt_ultima_modifica timestamp without time zone NOT NULL
);


--
-- TOC entry 155 (class 1259 OID 6771936)
-- Dependencies: 3 154
-- Name: mygov_prenotazione_flusso_ren_mygov_prenotazione_flusso_ren_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE mygov_prenotazione_flusso_ren_mygov_prenotazione_flusso_ren_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1906 (class 0 OID 0)
-- Dependencies: 155
-- Name: mygov_prenotazione_flusso_ren_mygov_prenotazione_flusso_ren_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE mygov_prenotazione_flusso_ren_mygov_prenotazione_flusso_ren_seq OWNED BY mygov_prenotazione_flusso_rendicontazione.mygov_prenotazione_flusso_rendicontazione_id;


--
-- TOC entry 1907 (class 0 OID 0)
-- Dependencies: 155
-- Name: mygov_prenotazione_flusso_ren_mygov_prenotazione_flusso_ren_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('mygov_prenotazione_flusso_ren_mygov_prenotazione_flusso_ren_seq', 1, false);


--
-- TOC entry 156 (class 1259 OID 6771938)
-- Dependencies: 3
-- Name: mygov_tipo_flusso; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE mygov_tipo_flusso (
    mygov_tipo_flusso_id bigint NOT NULL,
    version integer NOT NULL,
    cod_tipo character(1) NOT NULL,
    de_tipo character varying(100) NOT NULL,
    dt_creazione timestamp without time zone NOT NULL,
    dt_ultima_modifica timestamp without time zone NOT NULL
);


--
-- TOC entry 157 (class 1259 OID 6771941)
-- Dependencies: 156 3
-- Name: mygov_tipo_flusso_mygov_tipo_flusso_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE mygov_tipo_flusso_mygov_tipo_flusso_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1908 (class 0 OID 0)
-- Dependencies: 157
-- Name: mygov_tipo_flusso_mygov_tipo_flusso_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE mygov_tipo_flusso_mygov_tipo_flusso_id_seq OWNED BY mygov_tipo_flusso.mygov_tipo_flusso_id;


--
-- TOC entry 1909 (class 0 OID 0)
-- Dependencies: 157
-- Name: mygov_tipo_flusso_mygov_tipo_flusso_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('mygov_tipo_flusso_mygov_tipo_flusso_id_seq', 4, true);


--
-- TOC entry 158 (class 1259 OID 6771943)
-- Dependencies: 1835 1836 3
-- Name: mygov_utente; Type: TABLE; Schema: public; Owner: -; Tablespace: 
--

CREATE TABLE mygov_utente (
    mygov_utente_id bigint NOT NULL,
    version integer NOT NULL,
    cod_fed_user_id character varying(128) NOT NULL,
    cod_codice_fiscale_utente character varying(16) NOT NULL,
    flg_fed_authorized boolean DEFAULT false NOT NULL,
    de_email_address character varying(256) NOT NULL,
    de_firstname character varying(64),
    de_lastname character varying(64),
    de_fed_legal_entity character varying(16) DEFAULT 'fisica'::character varying NOT NULL,
    dt_ultimo_login timestamp without time zone NOT NULL,
    indirizzo character varying(70),
    civico character varying(16),
    cap character varying(16),
    comune_id bigint,
    provincia_id bigint,
    nazione_id bigint
);


--
-- TOC entry 159 (class 1259 OID 6771951)
-- Dependencies: 158 3
-- Name: mygov_utente_mygov_utente_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE mygov_utente_mygov_utente_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MAXVALUE
    NO MINVALUE
    CACHE 1;


--
-- TOC entry 1910 (class 0 OID 0)
-- Dependencies: 159
-- Name: mygov_utente_mygov_utente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE mygov_utente_mygov_utente_id_seq OWNED BY mygov_utente.mygov_utente_id;


--
-- TOC entry 1911 (class 0 OID 0)
-- Dependencies: 159
-- Name: mygov_utente_mygov_utente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('mygov_utente_mygov_utente_id_seq', 1, true);


--
-- TOC entry 1879 (class 0 OID 6771885)
-- Dependencies: 140
-- Data for Name: mygov_anagrafica_stato; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (1, 0, 'PRENOTATO', 'File export prenotato a pa', 'FLUSSO_EXPORT', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (2, 0, 'ERROR_CHIEDI_STATO_EXPORT', 'Errori chiedi stato export', 'FLUSSO_EXPORT', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (3, 0, 'CHIEDI_STATO_EXPORT', 'Chiedi stato export', 'FLUSSO_EXPORT', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (4, 0, 'INSERITO', 'Inserita richiesta range date per per ente', 'FLUSSO_EXPORT', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (5, 0, 'ERROR_PRENOTAZIONE', 'Il ws di prenotazione ha ritornato errore', 'FLUSSO_EXPORT', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (7, 0, 'CHIEDI_STATO_EXPORT_NO_FILE', 'Nessun file prodotto', 'FLUSSO_EXPORT', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (8, 0, 'ERRORE_DOWNLOAD', 'Errore fase download file mybox', 'FLUSSO_EXPORT', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (6, 0, 'DOWNLOAD', 'Scaricato il file da mybox', 'FLUSSO_EXPORT', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (9, 0, 'ATTESA_PRODUZIONE_FILE', 'In attesa produzione file batch pa', 'FLUSSO_EXPORT', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (12, 0, 'FILE_SCARICATO', 'File scaricato su file system', 'MANAGE', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (13, 0, 'FILE_IN_CARICAMENTO', 'File preso in carico dal batch di caricamento', 'MANAGE', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (11, 0, 'FILE_IN_DOWNLOAD', 'File preso in carico dal batch di download', 'MANAGE', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (14, 0, 'FILE_CARICATO', 'File caricato in batabase', 'MANAGE', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (16, 0, 'ERROR_LOAD', 'Errori caricamento file', 'MANAGE', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (15, 0, 'ERROR_DOWNLOAD', 'Errori download file', 'MANAGE', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (17, 0, 'CHIEDI_NOME_FLUSSI', 'Chiesto l''elenco a pa con ritorno lista presenti', 'FLUSSO_RENDICONTAZIONE', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (18, 0, 'CHIEDI_DOWNLOAD', 'Chiedi download url per avere il file', 'FLUSSO_RENDICONTAZIONE', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (19, 0, 'ERRORE_CHIEDI_NOME_FLUSSI', 'Errori richiesta lista', 'FLUSSO_RENDICONTAZIONE', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (20, 0, 'ERROR_CHIEDI_DOWNLOAD', 'Errore richiesta link download', 'FLUSSO_RENDICONTAZIONE', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (21, 0, 'DOWNLOAD', 'Download del flusso eseguito correttamente', 'FLUSSO_RENDICONTAZIONE', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (22, 0, 'ERROR_DOWNLOAD', 'Errore download del flusso', 'FLUSSO_RENDICONTAZIONE', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (23, 0, 'INSERITO', 'Inserita richiesta range date ente - psp', 'PRENOTAZIONE_RENDICONTAZIONE', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (24, 0, 'PRENOTATO', 'Prenotato range date ente - psp', 'PRENOTAZIONE_RENDICONTAZIONE', '2014-10-16 00:00:00', '2014-10-16 00:00:00');
INSERT INTO mygov_anagrafica_stato (mygov_anagrafica_stato_id, version, cod_stato, de_stato, de_tipo_stato, dt_creazione, dt_ultima_modifica) VALUES (10, 0, 'FLUSSO_OBSOLETO', 'Presentato flusso con data successiva', 'MANAGE', '2014-10-16 00:00:00', '2012-10-16 00:00:00');


--
-- TOC entry 1880 (class 0 OID 6771890)
-- Dependencies: 142
-- Data for Name: mygov_ente; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO mygov_ente (mygov_ente_id, version, cod_ipa_ente, codice_fiscale_ente, de_nome_ente, email_amministratore, cod_global_location_number, dt_creazione, dt_ultima_modifica) VALUES (1, 0, 'C_D530', '00133880252', 'FELTRE', 'supporto.mypay@eng.it', '1111111', '2012-09-21 10:00:00', '2012-09-21 10:00:00');
INSERT INTO mygov_ente (mygov_ente_id, version, cod_ipa_ente, codice_fiscale_ente, de_nome_ente, email_amministratore, cod_global_location_number, dt_creazione, dt_ultima_modifica) VALUES (2, 0, 'R_VENETO', '80007580279', 'REGIONE VENETO', 'igortamiazzo@libero.it', '2222222', '2014-10-23 00:00:00', '2014-10-23 00:00:00');


--
-- TOC entry 1881 (class 0 OID 6771895)
-- Dependencies: 144
-- Data for Name: mygov_ente_prenotazione; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1882 (class 0 OID 6771903)
-- Dependencies: 146
-- Data for Name: mygov_entepsp; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO mygov_entepsp (mygov_entepsp_id, version, mygov_ente_id, identificativo_psp) VALUES (1, 0, 1, 'BPPIITRRXXX');
INSERT INTO mygov_entepsp (mygov_entepsp_id, version, mygov_ente_id, identificativo_psp) VALUES (2, 0, 1, 'BCITITMM');
INSERT INTO mygov_entepsp (mygov_entepsp_id, version, mygov_ente_id, identificativo_psp) VALUES (3, 0, 1, '01-MYBANK');
INSERT INTO mygov_entepsp (mygov_entepsp_id, version, mygov_ente_id, identificativo_psp) VALUES (4, 0, 2, 'BPPIITRRXXX');
INSERT INTO mygov_entepsp (mygov_entepsp_id, version, mygov_ente_id, identificativo_psp) VALUES (5, 0, 2, 'BCITITMM');
INSERT INTO mygov_entepsp (mygov_entepsp_id, version, mygov_ente_id, identificativo_psp) VALUES (6, 0, 2, '01-MYBANK');


--
-- TOC entry 1883 (class 0 OID 6771906)
-- Dependencies: 147
-- Data for Name: mygov_entepsp_flusso_rendicontazione; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1884 (class 0 OID 6771916)
-- Dependencies: 150
-- Data for Name: mygov_flusso_export; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1885 (class 0 OID 6771922)
-- Dependencies: 151
-- Data for Name: mygov_flusso_rendicontazione; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1886 (class 0 OID 6771925)
-- Dependencies: 152
-- Data for Name: mygov_manage_flusso; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1887 (class 0 OID 6771933)
-- Dependencies: 154
-- Data for Name: mygov_prenotazione_flusso_rendicontazione; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 1888 (class 0 OID 6771938)
-- Dependencies: 156
-- Data for Name: mygov_tipo_flusso; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO mygov_tipo_flusso (mygov_tipo_flusso_id, version, cod_tipo, de_tipo, dt_creazione, dt_ultima_modifica) VALUES (1, 0, 'E', 'Flusso dovuti pagati', '2014-10-28 00:00:00', '2014-10-28 00:00:00');
INSERT INTO mygov_tipo_flusso (mygov_tipo_flusso_id, version, cod_tipo, de_tipo, dt_creazione, dt_ultima_modifica) VALUES (3, 0, 'P', 'Flusso rendicontazione poste', '2014-10-28 00:00:00', '2014-10-28 00:00:00');
INSERT INTO mygov_tipo_flusso (mygov_tipo_flusso_id, version, cod_tipo, de_tipo, dt_creazione, dt_ultima_modifica) VALUES (2, 0, 'R', 'Flusso rendicontazione standard', '2014-10-28 00:00:00', '2014-10-28 00:00:00');
INSERT INTO mygov_tipo_flusso (mygov_tipo_flusso_id, version, cod_tipo, de_tipo, dt_creazione, dt_ultima_modifica) VALUES (4, 0, 'Q', 'Flusso quadratura', '2014-10-28 00:00:00', '2014-10-28 00:00:00');


--
-- TOC entry 1889 (class 0 OID 6771943)
-- Dependencies: 158
-- Data for Name: mygov_utente; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO mygov_utente (mygov_utente_id, version, cod_fed_user_id, cod_codice_fiscale_utente, flg_fed_authorized, de_email_address, de_firstname, de_lastname, de_fed_legal_entity, dt_ultimo_login, indirizzo, civico, cap, comune_id, provincia_id, nazione_id) VALUES (1, 0, 'USER_JAVA@RV', 'USER_JAVA', false, 'supporto.mypay@eng.it', NULL, NULL, 'fisica', '2014-11-04 16:51:06.05', NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 1838 (class 2606 OID 6771954)
-- Dependencies: 140 140
-- Name: mygov_anagrafica_stato_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mygov_anagrafica_stato
    ADD CONSTRAINT mygov_anagrafica_stato_pkey PRIMARY KEY (mygov_anagrafica_stato_id);


--
-- TOC entry 1840 (class 2606 OID 6771956)
-- Dependencies: 142 142
-- Name: mygov_ente_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mygov_ente
    ADD CONSTRAINT mygov_ente_pkey PRIMARY KEY (mygov_ente_id);


--
-- TOC entry 1844 (class 2606 OID 6771958)
-- Dependencies: 144 144
-- Name: mygov_ente_prenotazione_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mygov_ente_prenotazione
    ADD CONSTRAINT mygov_ente_prenotazione_pkey PRIMARY KEY (mygov_ente_prenotazione_id);


--
-- TOC entry 1842 (class 2606 OID 6771960)
-- Dependencies: 142 142
-- Name: mygov_ente_ukey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mygov_ente
    ADD CONSTRAINT mygov_ente_ukey UNIQUE (cod_ipa_ente);


--
-- TOC entry 1848 (class 2606 OID 6771962)
-- Dependencies: 147 147
-- Name: mygov_entepsp_flusso_rendicontazione_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mygov_entepsp_flusso_rendicontazione
    ADD CONSTRAINT mygov_entepsp_flusso_rendicontazione_pkey PRIMARY KEY (mygov_entepsp_flusso_rendicontazione_id);


--
-- TOC entry 1850 (class 2606 OID 6771964)
-- Dependencies: 147 147 147 147 147 147
-- Name: mygov_entepsp_flusso_rendicontazione_ukey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mygov_entepsp_flusso_rendicontazione
    ADD CONSTRAINT mygov_entepsp_flusso_rendicontazione_ukey UNIQUE (cod_lista_ipa_ente, flg_lista_tipo_flusso, identificativo_lista_psp, cod_lista_identificativo_flusso, dt_lista_data_ora_flusso);


--
-- TOC entry 1846 (class 2606 OID 6771966)
-- Dependencies: 146 146
-- Name: mygov_entepsp_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mygov_entepsp
    ADD CONSTRAINT mygov_entepsp_pkey PRIMARY KEY (mygov_entepsp_id);


--
-- TOC entry 1852 (class 2606 OID 6771968)
-- Dependencies: 150 150 150
-- Name: mygov_flusso_export_mypay_psp_iur_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mygov_flusso_export
    ADD CONSTRAINT mygov_flusso_export_mypay_psp_iur_pkey PRIMARY KEY (cod_e_istit_att_id_univ_att_codice_id_univoco, cod_e_dati_pag_dati_sing_pag_id_univoco_riscoss);


--
-- TOC entry 1855 (class 2606 OID 6771970)
-- Dependencies: 151 151 151
-- Name: mygov_flusso_rendicontazione_mypay_psp_iur_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mygov_flusso_rendicontazione
    ADD CONSTRAINT mygov_flusso_rendicontazione_mypay_psp_iur_pkey PRIMARY KEY (identificativo_psp, cod_dati_sing_pagam_identificativo_univoco_riscossione);


--
-- TOC entry 1857 (class 2606 OID 6771972)
-- Dependencies: 152 152
-- Name: mygov_manage_flusso_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mygov_manage_flusso
    ADD CONSTRAINT mygov_manage_flusso_pkey PRIMARY KEY (mygov_manage_flusso_id);


--
-- TOC entry 1859 (class 2606 OID 6771974)
-- Dependencies: 154 154
-- Name: mygov_prenotazione_flusso_rendicontazione_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mygov_prenotazione_flusso_rendicontazione
    ADD CONSTRAINT mygov_prenotazione_flusso_rendicontazione_pkey PRIMARY KEY (mygov_prenotazione_flusso_rendicontazione_id);


--
-- TOC entry 1861 (class 2606 OID 6771976)
-- Dependencies: 156 156
-- Name: mygov_tipo_flusso_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mygov_tipo_flusso
    ADD CONSTRAINT mygov_tipo_flusso_pkey PRIMARY KEY (mygov_tipo_flusso_id);


--
-- TOC entry 1863 (class 2606 OID 6771978)
-- Dependencies: 158 158
-- Name: mygov_utente_pkey; Type: CONSTRAINT; Schema: public; Owner: -; Tablespace: 
--

ALTER TABLE ONLY mygov_utente
    ADD CONSTRAINT mygov_utente_pkey PRIMARY KEY (mygov_utente_id);


--
-- TOC entry 1853 (class 1259 OID 6771979)
-- Dependencies: 151
-- Name: fki_mygov_flusso_rendicontazione_mygov_manage_flusso_id_fkey; Type: INDEX; Schema: public; Owner: -; Tablespace: 
--

CREATE INDEX fki_mygov_flusso_rendicontazione_mygov_manage_flusso_id_fkey ON mygov_flusso_rendicontazione USING btree (mygov_manage_flusso_id);


--
-- TOC entry 1864 (class 2606 OID 6771980)
-- Dependencies: 1837 144 140
-- Name: mygov_ente_prenotazione_mygov_anagrafica_stato_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mygov_ente_prenotazione
    ADD CONSTRAINT mygov_ente_prenotazione_mygov_anagrafica_stato_id_fkey FOREIGN KEY (mygov_anagrafica_stato_id) REFERENCES mygov_anagrafica_stato(mygov_anagrafica_stato_id);


--
-- TOC entry 1865 (class 2606 OID 6771985)
-- Dependencies: 142 144 1839
-- Name: mygov_ente_prenotazione_mygov_ente_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mygov_ente_prenotazione
    ADD CONSTRAINT mygov_ente_prenotazione_mygov_ente_id_fkey FOREIGN KEY (mygov_ente_id) REFERENCES mygov_ente(mygov_ente_id);


--
-- TOC entry 1867 (class 2606 OID 6771990)
-- Dependencies: 147 1837 140
-- Name: mygov_entepsp_flusso_rendicontaz_mygov_anagrafica_stato_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mygov_entepsp_flusso_rendicontazione
    ADD CONSTRAINT mygov_entepsp_flusso_rendicontaz_mygov_anagrafica_stato_id_fkey FOREIGN KEY (mygov_anagrafica_stato_id) REFERENCES mygov_anagrafica_stato(mygov_anagrafica_stato_id);


--
-- TOC entry 1868 (class 2606 OID 6771995)
-- Dependencies: 146 1845 147
-- Name: mygov_entepsp_flusso_rendicontazione_mygov_entepsp_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mygov_entepsp_flusso_rendicontazione
    ADD CONSTRAINT mygov_entepsp_flusso_rendicontazione_mygov_entepsp_id_fkey FOREIGN KEY (mygov_entepsp_id) REFERENCES mygov_entepsp(mygov_entepsp_id);


--
-- TOC entry 1866 (class 2606 OID 6772000)
-- Dependencies: 146 142 1839
-- Name: mygov_entepsp_mygov_ente_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mygov_entepsp
    ADD CONSTRAINT mygov_entepsp_mygov_ente_id_fkey FOREIGN KEY (mygov_ente_id) REFERENCES mygov_ente(mygov_ente_id);


--
-- TOC entry 1869 (class 2606 OID 6772005)
-- Dependencies: 142 150 1839
-- Name: mygov_flusso_export_mygov_ente_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mygov_flusso_export
    ADD CONSTRAINT mygov_flusso_export_mygov_ente_id_fkey FOREIGN KEY (mygov_ente_id) REFERENCES mygov_ente(mygov_ente_id);


--
-- TOC entry 1870 (class 2606 OID 6772010)
-- Dependencies: 142 1839 151
-- Name: mygov_flusso_rendicontazione_mygov_ente_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mygov_flusso_rendicontazione
    ADD CONSTRAINT mygov_flusso_rendicontazione_mygov_ente_id_fkey FOREIGN KEY (mygov_ente_id) REFERENCES mygov_ente(mygov_ente_id);


--
-- TOC entry 1871 (class 2606 OID 6772015)
-- Dependencies: 151 1856 152
-- Name: mygov_flusso_rendicontazione_mygov_manage_flusso_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mygov_flusso_rendicontazione
    ADD CONSTRAINT mygov_flusso_rendicontazione_mygov_manage_flusso_id_fkey FOREIGN KEY (mygov_manage_flusso_id) REFERENCES mygov_manage_flusso(mygov_manage_flusso_id);


--
-- TOC entry 1872 (class 2606 OID 6772020)
-- Dependencies: 140 152 1837
-- Name: mygov_manage_flusso_mygov_anagrafica_stato_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mygov_manage_flusso
    ADD CONSTRAINT mygov_manage_flusso_mygov_anagrafica_stato_fkey FOREIGN KEY (mygov_anagrafica_stato_id) REFERENCES mygov_anagrafica_stato(mygov_anagrafica_stato_id);


--
-- TOC entry 1873 (class 2606 OID 6772025)
-- Dependencies: 1839 152 142
-- Name: mygov_manage_flusso_mygov_ente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mygov_manage_flusso
    ADD CONSTRAINT mygov_manage_flusso_mygov_ente_fkey FOREIGN KEY (mygov_ente_id) REFERENCES mygov_ente(mygov_ente_id);


--
-- TOC entry 1874 (class 2606 OID 6772030)
-- Dependencies: 1860 152 156
-- Name: mygov_manage_flusso_mygov_tipo_flusso_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mygov_manage_flusso
    ADD CONSTRAINT mygov_manage_flusso_mygov_tipo_flusso_fkey FOREIGN KEY (mygov_tipo_flusso_id) REFERENCES mygov_tipo_flusso(mygov_tipo_flusso_id);


--
-- TOC entry 1875 (class 2606 OID 6772035)
-- Dependencies: 152 158 1862
-- Name: mygov_manage_flusso_mygov_utente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mygov_manage_flusso
    ADD CONSTRAINT mygov_manage_flusso_mygov_utente_fkey FOREIGN KEY (mygov_utente_id) REFERENCES mygov_utente(mygov_utente_id);


--
-- TOC entry 1876 (class 2606 OID 6772040)
-- Dependencies: 154 140 1837
-- Name: mygov_prenotazione_flusso_rendic_mygov_anagrafica_stato_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mygov_prenotazione_flusso_rendicontazione
    ADD CONSTRAINT mygov_prenotazione_flusso_rendic_mygov_anagrafica_stato_id_fkey FOREIGN KEY (mygov_anagrafica_stato_id) REFERENCES mygov_anagrafica_stato(mygov_anagrafica_stato_id);


--
-- TOC entry 1877 (class 2606 OID 6772045)
-- Dependencies: 1860 154 156
-- Name: mygov_prenotazione_flusso_rendicontaz_mygov_tipo_flusso_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mygov_prenotazione_flusso_rendicontazione
    ADD CONSTRAINT mygov_prenotazione_flusso_rendicontaz_mygov_tipo_flusso_id_fkey FOREIGN KEY (mygov_tipo_flusso_id) REFERENCES mygov_tipo_flusso(mygov_tipo_flusso_id);


--
-- TOC entry 1878 (class 2606 OID 6772050)
-- Dependencies: 1845 154 146
-- Name: mygov_prenotazione_flusso_rendicontazione_mygov_entepsp_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY mygov_prenotazione_flusso_rendicontazione
    ADD CONSTRAINT mygov_prenotazione_flusso_rendicontazione_mygov_entepsp_id_fkey FOREIGN KEY (mygov_entepsp_id) REFERENCES mygov_entepsp(mygov_entepsp_id);


--
-- TOC entry 1894 (class 0 OID 0)
-- Dependencies: 3
-- Name: public; Type: ACL; Schema: -; Owner: -
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM postgres;
GRANT ALL ON SCHEMA public TO postgres;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2014-11-20 09:14:40

--
-- PostgreSQL database dump complete
--

