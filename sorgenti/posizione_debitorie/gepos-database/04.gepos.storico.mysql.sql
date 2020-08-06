--
-- CREAZIONE DELLE TABELLE_S PER LO SVECCHIAMENTO E LO STORICO
-- N.B.: QUESTE DDL DEVONO ESSERE ESEGUITE SIA SUL DATABASE MASTER (idp) 
-- CHE SUL DATABASE DELLO STORICO (idp_history)
--

-- 
-- 01/10/2018: Aggiunta la gestione delle tabelle SSIL
-- 21/02/2019: Aggiunta la gestione delle tabelle NOTIFICHE_CART e CONFERME_CART
--

--
-- TABELLE SVECCHIAMENTO MESSAGGI
--
DROP TABLE IF EXISTS `PENDENZE_CART_S`;
CREATE TABLE `PENDENZE_CART_S` (
  `E2EMSGID` varchar(44) COLLATE utf8_bin NOT NULL,
  `SENDERID` varchar(35) COLLATE utf8_bin NOT NULL,
  `SENDERSYS` varchar(35) COLLATE utf8_bin NOT NULL,
  `RECEIVERID` varchar(35) COLLATE utf8_bin NOT NULL,
  `RECEIVERSYS` varchar(35) COLLATE utf8_bin NOT NULL,
  `MESSAGGIO_XML` longblob NOT NULL,
  `TIMESTAMP_RICEZIONE` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `NUM_PENDENZE` int(10) unsigned DEFAULT NULL,
  `STATO` varchar(20) COLLATE utf8_bin NOT NULL,
  `PR_VERSIONE` int(10) unsigned DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime(3) DEFAULT NULL,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `TIPO_MESSAGGIO` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `TIPO_TRIBUTO` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `NUM_PEND_DELETED` int(10) unsigned NOT NULL DEFAULT '0',
  `VERSION` int(10) unsigned NOT NULL DEFAULT '0',
  `TIPO_OPERAZIONE` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `TRT_SENDERID` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `TRT_SENDERSYS` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`E2EMSGID`,`SENDERID`,`SENDERSYS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `ESITI_CART_S`;
CREATE TABLE `ESITI_CART_S` (
  `E2EMSGID` varchar(44) COLLATE utf8_bin NOT NULL,
  `SENDERID` varchar(35) COLLATE utf8_bin NOT NULL,
  `SENDERSYS` varchar(35) COLLATE utf8_bin NOT NULL,
  `ESITO_XML` longblob NOT NULL,
  `TIMESTAMP_INVIO` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `STATO` varchar(20) COLLATE utf8_bin NOT NULL,
  `PR_VERSIONE` int(10) unsigned DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime(3) DEFAULT NULL,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL DEFAULT '0',
  `TRT_SENDERID` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `TRT_SENDERSYS` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `TENTATIVI` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`E2EMSGID`,`SENDERID`,`SENDERSYS`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `ESITI_PENDENZA_S`;  
CREATE TABLE `ESITI_PENDENZA_S` (
  `ID_ESITO_PENDENZA` varchar(20) COLLATE utf8_bin NOT NULL,
  `E2EMSGID` varchar(44) COLLATE utf8_bin NOT NULL,
  `SENDERID` varchar(35) COLLATE utf8_bin NOT NULL,
  `SENDERSYS` varchar(35) COLLATE utf8_bin NOT NULL,
  `ID_PENDENZA` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `ESITO_PENDENZA` varchar(20) COLLATE utf8_bin NOT NULL,
  `DESCRIZIONE_ESITO` tinytext COLLATE utf8_bin,
  `ID_PENDENZA_ENTE` varchar(35) COLLATE utf8_bin NOT NULL,
  `STATO` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `PR_VERSIONE` int(10) unsigned DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime(3) DEFAULT NULL,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID_ESITO_PENDENZA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `ERRORI_ESITI_PENDENZA_S`;
CREATE TABLE `ERRORI_ESITI_PENDENZA_S` (
  `ID_ERRORE` varchar(20) COLLATE utf8_bin NOT NULL,
  `ID_ESITO_PENDENZA` varchar(20) COLLATE utf8_bin NOT NULL,
  `ID_PENDENZA` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `ID_PENDENZA_ENTE` varchar(35) COLLATE utf8_bin NOT NULL,
  `CODICE` tinytext COLLATE utf8_bin NOT NULL,
  `DESCRIZIONE_ERRORE` text COLLATE utf8_bin,
  `PR_VERSIONE` int(10) unsigned DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID_ERRORE`)
  ) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
DROP TABLE IF EXISTS `SSIL_MESSAGGI_S`;
CREATE TABLE `SSIL_MESSAGGI_S` (
	`ID` int unsigned NOT NULL AUTO_INCREMENT,
	`TIPO` varchar(255) COLLATE utf8_bin NOT NULL,
	`SOGGETTO` varchar(255) COLLATE utf8_bin NOT NULL,
	`SIL` varchar(255) COLLATE utf8_bin NOT NULL,
	`MSG_ID` varchar(255) COLLATE utf8_bin NOT NULL,
	`DATA_CREAZIONE` datetime NOT NULL ,
	`DATA_ULTIMA_CONSEGNA_RICHIESTA` datetime DEFAULT NULL,
	`FL_RICHIESTA_CONSEGNATA` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'N', 
	`DATA_ULTIMA_CONSEGNA_ESITO` datetime DEFAULT NULL,
	`FL_ESITO_CONSEGNATO` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'N', 
	`OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
	`TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
	`TS_AGGIORNAMENTO` datetime DEFAULT NULL,
	`VERSION` int unsigned NOT NULL DEFAULT '0',
	PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
DROP TABLE IF EXISTS `SSIL_GESTIONI_S`;
CREATE TABLE `SSIL_GESTIONI_S` (
	`ID` int unsigned NOT NULL AUTO_INCREMENT,
	`ID_MESSAGGIO` int unsigned NOT NULL,
	`DATA_INIZIO_GESTIONE` datetime NOT NULL ,
	`TIPO` varchar(255) COLLATE utf8_bin NOT NULL,
	`TEMPO_GESTIONE` bigint(20),
	`ID_EGOV` varchar(255) COLLATE utf8_bin DEFAULT NULL,
	`COD_ERRORE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
	`DESCR_ERRORE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
	`COMPONENTE_RESPONSABILE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
	`HTTP_RESPONSE_CODE` int unsigned, 
	`HTTP_HEADERS` varchar(2048) COLLATE utf8_bin DEFAULT NULL,
	`OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
	`TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
	`TS_AGGIORNAMENTO` datetime DEFAULT NULL,
	`VERSION` int unsigned NOT NULL DEFAULT '0',
	PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
DROP TABLE IF EXISTS `SSIL_MSG_NON_GESTITI_S`;
CREATE TABLE `SSIL_MSG_NON_GESTITI_S` (
	`ID` int unsigned NOT NULL AUTO_INCREMENT,
	`TIPO` varchar(255) COLLATE utf8_bin NOT NULL,
	`DATA_RICEZIONE` datetime NOT NULL ,
	`TIPO_MITTENTE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
	`MITTENTE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
	`ID_EGOV` varchar(255) COLLATE utf8_bin DEFAULT NULL,
	`COD_ERRORE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
	`DESCR_ERRORE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
	`OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
	`TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
	`TS_AGGIORNAMENTO` datetime DEFAULT NULL,
	`VERSION` int unsigned NOT NULL DEFAULT '0',
	PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `NOTIFICHE_CART_S`;
CREATE TABLE `NOTIFICHE_CART_S` (
  `E2EMSGID` varchar(44) COLLATE utf8_bin NOT NULL,
  `SENDERID` varchar(35) COLLATE utf8_bin NOT NULL,
  `SENDERSYS` varchar(35) COLLATE utf8_bin NOT NULL,
  `RECEIVERID` varchar(35) COLLATE utf8_bin NOT NULL,
  `RECEIVERSYS` varchar(35) COLLATE utf8_bin NOT NULL,
  `NOTIFICA_XML` longblob NOT NULL,
  `TIMESTAMP_INVIO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `STATO` varchar(20) COLLATE utf8_bin NOT NULL,
  `PR_VERSIONE` int unsigned DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime DEFAULT NULL,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  `TIPO_NOTIFICA` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `TRT_RECEIVERID` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `TRT_RECEIVERSYS` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `ID_ENTE` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `CD_TRB_ENTE` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `TENTATIVI` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`E2EMSGID`,`RECEIVERID`,`RECEIVERSYS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `CONFERME_CART_S`;
CREATE TABLE `CONFERME_CART_S` (
  `E2EMSGID` varchar(44) COLLATE utf8_bin NOT NULL,
  `SENDERID` varchar(35) COLLATE utf8_bin NOT NULL,
  `SENDERSYS` varchar(35) COLLATE utf8_bin NOT NULL,
  `RECEIVERID` varchar(35) COLLATE utf8_bin NOT NULL,
  `RECEIVERSYS` varchar(35) COLLATE utf8_bin NOT NULL,
  `MESSAGGIO_XML` longblob NOT NULL,
  `TIMESTAMP_RICEZIONE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `STATO` varchar(20) COLLATE utf8_bin NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  `TRT_SENDERID` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `TRT_SENDERSYS` varchar(35) COLLATE utf8_bin DEFAULT NULL,  
  PRIMARY KEY (`E2EMSGID`,`SENDERID`,`SENDERSYS`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `ERRORI_IDP_S`;
CREATE TABLE `ERRORI_IDP_S` (
  `E2EMSGID` varchar(44) COLLATE utf8_bin NOT NULL,
  `SENDERID` varchar(35) COLLATE utf8_bin NOT NULL,
  `SENDERSYS` varchar(35) COLLATE utf8_bin NOT NULL,
  `SERVICENAME` varchar(60) COLLATE utf8_bin DEFAULT NULL,
  `STATO` varchar(20) COLLATE utf8_bin NOT NULL,
  `RECEIVERID` varchar(35) COLLATE utf8_bin NOT NULL,
  `RECEIVERSYS` varchar(35) COLLATE utf8_bin NOT NULL,
  `ESITO_XML` longblob NOT NULL,
  `DESCRIZIONE_STATO` text COLLATE utf8_bin,
  `PR_VERSIONE` int unsigned DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime DEFAULT NULL,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  `TRT_SENDERID` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `TRT_SENDERSYS` varchar(35) COLLATE utf8_bin DEFAULT NULL,  
  PRIMARY KEY (`E2EMSGID`,`SENDERID`,`SENDERSYS`,`STATO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- TABELLE SVECCHIAMENTO POSIZIONI
--
DROP TABLE IF EXISTS `jltpend_s` ;
CREATE TABLE `jltpend_s` (
  `ID_PENDENZA` varchar(20) COLLATE utf8_bin NOT NULL,
  `TS_DECORRENZA` datetime(3) NOT NULL,
  `ID_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
  `CD_TRB_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
  `ID_TRIBUTO` varchar(50) COLLATE utf8_bin NOT NULL,
  `ID_PENDENZAENTE` varchar(35) COLLATE utf8_bin NOT NULL,
  `ID_SYSTEM` varchar(35) COLLATE utf8_bin NOT NULL,
  `TS_CREAZIONEENTE` datetime(3) NOT NULL,
  `TS_EMISSIONEENTE` datetime(3) NOT NULL,
  `TS_PRESCRIZIONE` datetime(3) NOT NULL,
  `TS_MODIFICAENTE` datetime(3) DEFAULT NULL,
  `CO_RISCOSSORE` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `RIFERIMENTO` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `ANNO_RIFERIMENTO` int(10) unsigned NOT NULL,
  `NOTE` text COLLATE utf8_bin,
  `DV_RIFERIMENTO` varchar(3) COLLATE utf8_bin NOT NULL,
  `IM_TOTALE` decimal(15,2) NOT NULL,
  `CO_ABI` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `ID_MITTENTE` varchar(35) COLLATE utf8_bin NOT NULL,
  `DE_MITTENTE` varchar(70) COLLATE utf8_bin DEFAULT NULL,
  `DE_CAUSALE` tinytext COLLATE utf8_bin NOT NULL,
  `ST_PENDENZA` varchar(2) COLLATE utf8_bin NOT NULL,
  `FL_MOD_PAGAMENTO` char(1) COLLATE utf8_bin DEFAULT NULL,
  `ST_RIGA` char(1) COLLATE utf8_bin NOT NULL,
  `PR_VERSIONE` int(10) unsigned NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `OP_ANNULLAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_ANNULLAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL DEFAULT '0',
  `ID_TRIBUTO_STRUTTURATO` int(10) unsigned DEFAULT NULL,
  `CARTELLA_PAGAMENTO` int(10) unsigned DEFAULT '0',
  `TS_ANNULLAMENTO_MILLIS` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID_PENDENZA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
  DROP TABLE IF EXISTS `pagamenti_s` ;
  CREATE TABLE `pagamenti_s` (
  `ID` int(10) unsigned NOT NULL ,
  `TS_DECORRENZA` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `ID_PENDENZA` char(20) COLLATE utf8_bin DEFAULT NULL,
  `ID_CONDIZIONE` varchar(20) COLLATE utf8_bin NOT NULL,
  `ID_TRIBUTO` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `ID_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
  `CD_TRB_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
  `ID_DISTINTE_PAGAMENTO` int(10) unsigned NOT NULL,
  `ID_PENDENZAENTE` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `CO_PAGANTE` varchar(20) COLLATE utf8_bin NOT NULL,
  `DT_SCADENZA` date NOT NULL,
  `TI_PAGAMENTO` char(1) COLLATE utf8_bin NOT NULL,
  `ST_PAGAMENTO` char(2) COLLATE utf8_bin NOT NULL,
  `STATO_NOTIFICA` varchar(15) COLLATE utf8_bin DEFAULT NULL,
  `IM_PAGATO` decimal(10,3) NOT NULL,
  `TIPOSPONTANEO` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `DISTINTA` varchar(20) COLLATE utf8_bin NOT NULL,
  `TS_ORDINE` datetime(3) NOT NULL,
  `TI_DEBITO` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `FLAG_INCASSO` char(1) COLLATE utf8_bin NOT NULL,
  `ST_RIGA` char(1) COLLATE utf8_bin NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned DEFAULT '0',
  `ID_DOCUMENTO_REPOSITORY` int(10) unsigned DEFAULT NULL,
  `PAGAMENTO_DELEGATO` int(10) unsigned DEFAULT '0',
  `NOTIFICA_ESEGUITO` datetime(3) DEFAULT NULL,
  `NOTIFICA_REGOLATO` datetime(3) DEFAULT NULL,
  `NOTIFICA_INCASSO` datetime(3) DEFAULT NULL,
  `ID_RISCOSSIONE_PSP` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `NOTE_PAGAMENTO` varchar(1024) COLLATE utf8_bin DEFAULT NULL,
  `TS_STORNO` datetime(3) DEFAULT NULL,
  `DATA_ACCREDITO_CONTOTECNICO` date DEFAULT NULL,
  `DATA_ACCREDITO_ENTE` date DEFAULT NULL,
  `DATA_REGOLAMENTO` date DEFAULT NULL,
  `IDENTIFICATIVO_FLUSSO` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `TRN` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `DATA_ESECUZIONE` date DEFAULT NULL,
  `COD_RENDICONTAZIONE_INCASSO` varchar(4) COLLATE utf8_bin DEFAULT NULL,
  `MITT_RENDICONTAZIONE_INCASSO` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `TOTALE_TRANSAZIONE_INCASSO` decimal(15,2) DEFAULT NULL,
  `ID_DOCUMENTO_EXT` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `FLAG_OPPOSIZIONE_730` smallint(6) NOT NULL DEFAULT '0',
  `COMMISSIONI_PSP` decimal(12,2) DEFAULT NULL,
  `TIPO_ALLEGATO_REPOSITORY` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `ID_ALLEGATO_REPOSITORY` int(10) unsigned DEFAULT NULL,
  `BIC_BANCA_RIVERSAMENTO` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `RIFERIMENTO_CONTABILE` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `DATA_CONTABILE` date DEFAULT NULL,
  `IMPORTO_CONTABILE` decimal(15,2) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `jltcopd_s` ;
CREATE TABLE `jltcopd_s` (
  `ID_PENDENZA` varchar(20) COLLATE utf8_bin NOT NULL,
  `ID_CONDIZIONE` varchar(20) COLLATE utf8_bin NOT NULL,
  `TS_DECORRENZA` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `TI_PAGAMENTO` varchar(1) COLLATE utf8_bin NOT NULL,
  `ID_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
  `CD_TRB_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
  `ID_PAGAMENTO` varchar(35) COLLATE utf8_bin NOT NULL,
  `TI_CIP` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `CO_CIP` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `DT_SCADENZA` date NOT NULL,
  `DT_INIZIOVALIDITA` date DEFAULT NULL,
  `DT_FINEVALIDITA` date NOT NULL,
  `IM_TOTALE` decimal(15,2) NOT NULL,
  `ST_PAGAMENTO` char(1) COLLATE utf8_bin NOT NULL,
  `DT_PAGAMENTO` datetime(3) DEFAULT NULL,
  `DE_CANALEPAG` varchar(140) COLLATE utf8_bin DEFAULT NULL,
  `ST_RIGA` char(1) COLLATE utf8_bin NOT NULL,
  `PR_VERSIONE` int(10) unsigned NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `OP_ANNULLAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_ANNULLAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL DEFAULT '0',
  `IBAN_BENEFICIARIO` varchar(27) COLLATE utf8_bin DEFAULT NULL,
  `RAGIONE_SOCIALE_BENEFICIARIO` varchar(90) COLLATE utf8_bin DEFAULT NULL,
  `CAUSALE_PAGAMENTO` tinytext COLLATE utf8_bin,
  `IM_PAGAMENTO` decimal(15,2) DEFAULT NULL,
  `DE_NOTEPAGAMENTO` tinytext COLLATE utf8_bin,
  `DE_MEZZOPAGAMENTO` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `TS_ANNULLAMENTO_MILLIS` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID_CONDIZIONE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `jltvopd_s` ;
CREATE TABLE `jltvopd_s` (
  `ID_PENDENZA` varchar(20) COLLATE utf8_bin NOT NULL,
  `ID_CONDIZIONE` varchar(20) COLLATE utf8_bin NOT NULL,
  `ID_VOCE` varchar(20) COLLATE utf8_bin NOT NULL,
  `TS_DECORRENZA` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `TI_VOCE` varchar(35) COLLATE utf8_bin NOT NULL,
  `CO_VOCE` varchar(35) COLLATE utf8_bin NOT NULL,
  `DE_VOCE` tinytext COLLATE utf8_bin NOT NULL,
  `IM_VOCE` decimal(15,2) NOT NULL,
  `CO_CAPBILANCIO` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `CO_ACCERTAMENTO` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `ST_RIGA` char(1) COLLATE utf8_bin NOT NULL,
  `PR_VERSIONE` int(10) unsigned NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID_VOCE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `jltalpe_s` ;
CREATE TABLE `jltalpe_s` (
  `ID_PENDENZA` varchar(20) COLLATE utf8_bin NOT NULL,
  `ID_ALLEGATO` varchar(20) COLLATE utf8_bin NOT NULL,
  `TS_DECORRENZA` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `ID_CONDIZIONE` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `FL_CONTESTO` char(1) COLLATE utf8_bin NOT NULL,
  `TITOLO` varchar(70) COLLATE utf8_bin DEFAULT NULL,
  `TI_CODIFICA_BODY` varchar(4) COLLATE utf8_bin DEFAULT NULL,
  `DATI_BODY` mediumblob NOT NULL,
  `TI_ALLEGATO` varchar(10) COLLATE utf8_bin NOT NULL,
  `ID_ANTIFALSIFIC` text COLLATE utf8_bin,
  `ST_RIGA` char(1) COLLATE utf8_bin NOT NULL,
  `PR_VERSIONE` int(10) unsigned NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID_ALLEGATO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `jltdepd_s`; 
  CREATE TABLE `jltdepd_s` (
  `ID_PENDENZA` varchar(20) COLLATE utf8_bin NOT NULL,
  `ID_DESTINATARIO` varchar(20) COLLATE utf8_bin NOT NULL,
  `TS_DECORRENZA` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `CO_DESTINATARIO` varchar(35) COLLATE utf8_bin NOT NULL,
  `DE_DESTINATARIO` varchar(70) COLLATE utf8_bin DEFAULT NULL,
  `TI_DESTINATARIO` varchar(2) COLLATE utf8_bin NOT NULL,
  `ST_RIGA` char(1) COLLATE utf8_bin NOT NULL,
  `PR_VERSIONE` int(10) unsigned NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL DEFAULT '0',
  `TIPO_SOGGETTO_DEST` CHAR(1) COLLATE utf8_bin DEFAULT NULL,
  `ANAGRAFICA_SOGGETTO_DEST` VARCHAR(70) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_DEST` VARCHAR(256) COLLATE utf8_bin DEFAULT NULL,
  `INDIRIZZO_DEST` VARCHAR(70) COLLATE utf8_bin DEFAULT NULL,
  `NUMERO_CIVICO_DEST` VARCHAR(16) COLLATE utf8_bin DEFAULT NULL,
  `CAP_DEST` VARCHAR(16) COLLATE utf8_bin DEFAULT NULL,
  `LOCALITA_DEST` VARCHAR(35) COLLATE utf8_bin DEFAULT NULL,
  `PROVINCIA_DEST` VARCHAR(35) COLLATE utf8_bin DEFAULT NULL,
  `NAZIONE_DEST` VARCHAR(2) COLLATE utf8_bin DEFAULT NULL,
  `DATA_NASCITA_DEST` DATE DEFAULT NULL,
  `LUOGO_NASCITA_DEST` VARCHAR(35) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID_DESTINATARIO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `tributo_strutturato_s` ;  
CREATE TABLE `tributo_strutturato_s`
(   `ID`                 int(10) UNSIGNED NOT NULL ,
   `TIPO_TRIBUTO`       varchar(255) COLLATE utf8_bin DEFAULT NULL,
   `ID_PENDENZA`        varchar(20) COLLATE utf8_bin NOT NULL,
   `CF_SOGG_VERSANTE`   varchar(16) COLLATE utf8_bin DEFAULT NULL,
   `NOTE_VERSANTE`      text COLLATE utf8_bin,
   `EMAIL_VERSANTE`     tinytext COLLATE utf8_bin,
   `OP_INSERIMENTO`     varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`     datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  `ST_RIGA` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `t_bollo_auto_s`;
CREATE TABLE `t_bollo_auto_s`
(   `ID`                int(10) UNSIGNED NOT NULL ,
   `ID_ENTE`           varchar(100) COLLATE utf8_bin NOT NULL,
   `CF_SOGG_PASSIVO`   varchar(16) COLLATE utf8_bin NOT NULL,
   `IMPORTO`           decimal(15, 2) NOT NULL,
   `ANNO_RIF`          int(10) UNSIGNED NOT NULL,
   `TARGA`             varchar(20) COLLATE utf8_bin NOT NULL,
   `TIPO_VEICOLO`      varchar(1) COLLATE utf8_bin NOT NULL,
   `ST_RIGA`           varchar(1) COLLATE utf8_bin DEFAULT NULL,
   `MESI_VALIDITA`     int(10) UNSIGNED NOT NULL DEFAULT '12',
   `DATA_DECORRENZA`   date DEFAULT NULL,
   `PAG_FRAZIONATO`    char(1) COLLATE utf8_bin NOT NULL DEFAULT 'N',
   `OP_INSERIMENTO`    varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`    datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `t_conf_disc_s`;
CREATE TABLE `t_conf_disc_s`
(   `ID`                       int(10) UNSIGNED NOT NULL ,
   `ID_ENTE`                  varchar(100) COLLATE utf8_bin NOT NULL,
   `CF_SOGG_PASSIVO`          varchar(16) COLLATE utf8_bin NOT NULL,
   `IMPORTO`                  decimal(15, 2) NOT NULL,
   `ANNO_RIF`                 int(10) UNSIGNED NOT NULL,
   `TRIMESTRE_RIF`            char(1) COLLATE utf8_bin NOT NULL,
   `DENOM_IMPIANTO`           tinytext COLLATE utf8_bin NOT NULL,
   `DENOMINAZIONE_DEBITORE`   varchar(60) COLLATE utf8_bin DEFAULT NULL,
   `OP_INSERIMENTO`           varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`           datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS`t_pesca_s` ;
CREATE TABLE `t_pesca_s`
(   `ID`                       int(10) UNSIGNED NOT NULL ,
   `ID_ENTE`                  varchar(200) COLLATE utf8_bin NOT NULL,
   `CF_SOGG_PASSIVO`          varchar(32) COLLATE utf8_bin NOT NULL,
   `IMPORTO`                  decimal(15, 2) NOT NULL,
   `ANNO_RIF`                 int(10) UNSIGNED NOT NULL,
   `DENOMINAZIONE_DEBITORE`   varchar(60) COLLATE utf8_bin DEFAULT NULL,
   `INDIRIZZO_DEBITORE`       varchar(100) COLLATE utf8_bin DEFAULT NULL,
   `DATA_NASCITA_DEBITORE`    date DEFAULT NULL,
   `LUOGO_NASCITA_DEBITORE`   varchar(40) COLLATE utf8_bin DEFAULT NULL,
   `DESCRIZIONE`              varchar(200) COLLATE utf8_bin DEFAULT NULL,
   `OP_INSERIMENTO`           varchar(80) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`           datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(80) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `t_caccia_vagante_s`;
CREATE TABLE `t_caccia_vagante_s`
(   `ID`                       int(10) UNSIGNED NOT NULL ,
   `ID_ENTE`                  varchar(100) COLLATE utf8_bin NOT NULL,
   `CF_SOGG_PASSIVO`          varchar(16) COLLATE utf8_bin NOT NULL,
   `IMPORTO`                  decimal(15, 2) NOT NULL,
   `ANNO_RIF`                 int(10) UNSIGNED NOT NULL,
   `NUM_LICENZA`              varchar(100) COLLATE utf8_bin NOT NULL,
   `DATA_RILASCIO_LICENZA`    date NOT NULL,
   `TIPO_FUCILE`              varchar(2) COLLATE utf8_bin NOT NULL,
   `DENOMINAZIONE_DEBITORE`   varchar(60) COLLATE utf8_bin DEFAULT NULL,
   `INDIRIZZO_DEBITORE`       varchar(100) COLLATE utf8_bin DEFAULT NULL,
   `OP_INSERIMENTO`           varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`           datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `t_caccia_fisso_s` ;
CREATE TABLE `t_caccia_fisso_s`
(   `ID`                       int(10) UNSIGNED NOT NULL ,
   `ID_ENTE`                  varchar(100) COLLATE utf8_bin NOT NULL,
   `CF_SOGG_PASSIVO`          varchar(16) COLLATE utf8_bin NOT NULL,
   `IMPORTO`                  decimal(15, 2) NOT NULL,
   `ANNO_RIF`                 int(10) UNSIGNED NOT NULL,
   `PROVINCIA_RIF`            varchar(2) COLLATE utf8_bin NOT NULL,
   `NUM_APPOSTAMENTO`         varchar(255) COLLATE utf8_bin NOT NULL,
   `DENOMINAZIONE_DEBITORE`   varchar(60) COLLATE utf8_bin DEFAULT NULL,
   `INDIRIZZO_DEBITORE`       varchar(100) COLLATE utf8_bin DEFAULT NULL,
   `OP_INSERIMENTO`           varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`           datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `t_addiz_accisa_gas_s`;
CREATE TABLE `t_addiz_accisa_gas_s`
(  `ID`                       int(10) UNSIGNED NOT NULL ,
   `ID_ENTE`                  varchar(100) COLLATE utf8_bin NOT NULL,
   `CF_SOGG_PASSIVO`          varchar(16) COLLATE utf8_bin NOT NULL,
   `IMPORTO`                  decimal(15, 2) NOT NULL,
   `ANNO_RIF`                 int(10) UNSIGNED NOT NULL,
   `TIPO_VERSAMENTO`          varchar(1) COLLATE utf8_bin NOT NULL,
   `MESE_RIF`                 int(10) UNSIGNED DEFAULT NULL,
   `DENOMINAZIONE_DEBITORE`   varchar(60) COLLATE utf8_bin DEFAULT NULL,
   `OP_INSERIMENTO`           varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`           datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `t_demanio_marittimo_s`;
CREATE TABLE `t_demanio_marittimo_s`
(   `ID`                       int(10) UNSIGNED NOT NULL ,
   `ID_ENTE`                  varchar(100) COLLATE utf8_bin NOT NULL,
   `CF_SOGG_PASSIVO`          varchar(16) COLLATE utf8_bin NOT NULL,
   `IMPORTO`                  decimal(15, 2) NOT NULL,
   `ANNO_RIF`                 int(10) UNSIGNED NOT NULL,
   `ENTE_CONCESSIONARIO`      int(10) UNSIGNED NOT NULL,
   `DENOMINAZIONE_DEBITORE`   varchar(60) COLLATE utf8_bin DEFAULT NULL,
   `OP_INSERIMENTO`           varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`           datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `t_demanio_minerario_s`;
CREATE TABLE `t_demanio_minerario_s`
(
   `ID`                       int(10) UNSIGNED NOT NULL ,
   `ID_ENTE`                  varchar(100) COLLATE utf8_bin NOT NULL,
   `CF_SOGG_PASSIVO`          varchar(16) COLLATE utf8_bin NOT NULL,
   `IMPORTO`                  decimal(15, 2) NOT NULL,
   `ANNO_RIF`                 int(10) UNSIGNED NOT NULL,
   `TIPO_IMPOSTA`             int(10) UNSIGNED NOT NULL,
   `NUM_DECRETO`              varchar(30) COLLATE utf8_bin NOT NULL,
   `DENOMINAZIONE`            varchar(255) COLLATE utf8_bin NOT NULL,
   `DENOMINAZIONE_DEBITORE`   varchar(60) COLLATE utf8_bin DEFAULT NULL,
   `OP_INSERIMENTO`           varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`           datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `t_multa_s`;
CREATE TABLE `t_multa_s`
(
   `ID`                int(10) UNSIGNED NOT NULL ,
   `ID_ENTE`           varchar(100) COLLATE utf8_bin NOT NULL,
   `CF_SOGG_PASSIVO`   varchar(16) COLLATE utf8_bin NOT NULL,
   `IMPORTO`           decimal(15, 2) NOT NULL,
   `ANNO_RIF`          int(10) UNSIGNED NOT NULL,
   `TARGA`             varchar(20) COLLATE utf8_bin NOT NULL,
   `NUM_VERBALE`       varchar(20) COLLATE utf8_bin NOT NULL,
   `DATA_VERBALE`      datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `SERIE_VERBALE` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `t_bollo_auto_bonario_s` ;
CREATE TABLE `t_bollo_auto_bonario_s`
(
   `ID`                int(10) UNSIGNED NOT NULL ,
   `ID_ENTE`           varchar(50) COLLATE utf8_bin NOT NULL,
   `CF_SOGG_PASSIVO`   varchar(16) COLLATE utf8_bin NOT NULL,
   `IMPORTO`           decimal(15, 2) NOT NULL,
   `ANNO_RIF`          int(10) UNSIGNED NOT NULL,
   `NUMERO_AVVISO`     varchar(16) COLLATE utf8_bin NOT NULL,
   `TARGA`             varchar(20) COLLATE utf8_bin NOT NULL,
   `OP_INSERIMENTO`    varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`    datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `t_servizi_ricarica_s`;
CREATE TABLE `t_servizi_ricarica_s`
(
   `ID`                int(10) UNSIGNED NOT NULL ,
   `ID_ENTE`           varchar(100) COLLATE utf8_bin NOT NULL,
   `CF_SOGG_PASSIVO`   varchar(16) COLLATE utf8_bin NOT NULL,
   `IMPORTO`           decimal(15, 2) NOT NULL,
   `ANNO_RIF`          int(10) UNSIGNED NOT NULL,
   `ID_CONSUMATORE`    varchar(35) COLLATE utf8_bin NOT NULL,
   `TIPO_RICARICA`     varchar(35) COLLATE utf8_bin NOT NULL,
   `OP_INSERIMENTO`    varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`    datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `t_raccolta_funghi_s`;
CREATE TABLE `t_raccolta_funghi_s`
(   `ID`                       int(10) UNSIGNED NOT NULL ,
   `ID_ENTE`                  varchar(50) COLLATE utf8_bin NOT NULL,
   `CF_SOGG_PASSIVO`          varchar(32) COLLATE utf8_bin NOT NULL,
   `IMPORTO`                  decimal(15, 2) NOT NULL,
   `ANNO_RIF`                 int(10) UNSIGNED NOT NULL,
   `DENOMINAZIONE_DEBITORE`   varchar(200) COLLATE utf8_bin NOT NULL,
   `LUOGO_NASCITA_DEBITORE`   varchar(200) COLLATE utf8_bin NOT NULL,
   `DATA_NASCITA_DEBITORE`    date NOT NULL,
   `INDIRIZZO_DEBITORE`       text COLLATE utf8_bin,
   `DATA_INIZIO_VALIDITA`     date DEFAULT NULL,
   `OP_INSERIMENTO`           varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`           datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `t_sanzione_amministrativa_s`;
CREATE TABLE `t_sanzione_amministrativa_s`
(
   `ID`                          int(10) UNSIGNED NOT NULL,
   `ID_ENTE`                     varchar(50) COLLATE utf8_bin NOT NULL,
   `CF_SOGG_PASSIVO`             varchar(32) COLLATE utf8_bin NOT NULL,
   `IMPORTO`                     decimal(15, 2) NOT NULL,
   `ANNO_RIF`                    int(10) UNSIGNED NOT NULL,
   `TIPO_SANZIONE`               varchar(4) COLLATE utf8_bin NOT NULL,
   `NUMERO_SANZIONE`             varchar(40) COLLATE utf8_bin NOT NULL,
   `DATA_SANZIONE`               date NOT NULL,
   `NOME_COGNOME_TRASGRESSORE`   varchar(250) COLLATE utf8_bin DEFAULT NULL,
   `OP_INSERIMENTO`              varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`              datetime(3)    NOT NULL      DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `t_atto_accertamento_s`;
CREATE TABLE `t_atto_accertamento_s`
(
   `ID`                  int(10) UNSIGNED NOT NULL ,
   `ID_ENTE`             varchar(50) COLLATE utf8_bin NOT NULL,
   `CF_SOGG_PASSIVO`     varchar(32) COLLATE utf8_bin NOT NULL,
   `IMPORTO`             decimal(15, 2) NOT NULL,
   `ANNO_RIF`            int(10) UNSIGNED NOT NULL,
   `NUMERO_AVVISO`       varchar(40) COLLATE utf8_bin NOT NULL,
   `TIPO_ACCERTAMENTO`   varchar(40) COLLATE utf8_bin NOT NULL,
   `OP_INSERIMENTO`      varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`      datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `t_raccolta_tartufi_s`;
CREATE TABLE `t_raccolta_tartufi_s`
(
   `ID`                       int(10) UNSIGNED NOT NULL ,
   `ID_ENTE`                  varchar(50) COLLATE utf8_bin NOT NULL,
   `CF_SOGG_PASSIVO`          varchar(32) COLLATE utf8_bin NOT NULL,
   `IMPORTO`                  decimal(15, 2) NOT NULL,
   `ANNO_RIF`                 int(10) UNSIGNED NOT NULL,
   `DENOMINAZIONE_DEBITORE`   varchar(200) COLLATE utf8_bin NOT NULL,
   `LUOGO_NASCITA_DEBITORE`   varchar(200) COLLATE utf8_bin NOT NULL,
   `DATA_NASCITA_DEBITORE`    date NOT NULL,
   `INDIRIZZO_DEBITORE`       text COLLATE utf8_bin,
   `OP_INSERIMENTO`           varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`           datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `t_bollettino_bianco_evoluto_s`;
CREATE TABLE `t_bollettino_bianco_evoluto_s`
(   `ID`                  int(10) UNSIGNED NOT NULL ,
   `ID_ENTE`             varchar(100) COLLATE utf8_bin NOT NULL,
   `CD_TRB_ENTE`         varchar(50) COLLATE utf8_bin NOT NULL,
   `CF_SOGG_PASSIVO`     varchar(16) COLLATE utf8_bin NOT NULL,
   `IMPORTO`             decimal(15, 2) NOT NULL,
   `CAUSALE`             varchar(40) COLLATE utf8_bin NOT NULL,
   `ANNO_RIF`            int(10) UNSIGNED NOT NULL,
   `DENOMINAZIONE_DEB`   varchar(70) COLLATE utf8_bin DEFAULT NULL,
   `INDIRIZZO_DEB`       varchar(70) COLLATE utf8_bin DEFAULT NULL,
   `NUMERO_CIVICO_DEB`   varchar(16) COLLATE utf8_bin DEFAULT NULL,
   `CAP_DEB`             varchar(16) COLLATE utf8_bin DEFAULT NULL,
   `LOCALITA_DEB`        varchar(35) COLLATE utf8_bin DEFAULT NULL,
   `PROVINCIA_DEB`       varchar(35) COLLATE utf8_bin DEFAULT NULL,
   `NAZIONE_DEB`         varchar(2) COLLATE utf8_bin DEFAULT NULL,
   `EMAIL_DEB`           varchar(256) COLLATE utf8_bin DEFAULT NULL,
   `TESTO_1`             varchar(256) COLLATE utf8_bin DEFAULT NULL,
   `TESTO_2`             varchar(256) COLLATE utf8_bin DEFAULT NULL,
   `TESTO_3`             varchar(256) COLLATE utf8_bin DEFAULT NULL,
   `TESTO_4`             varchar(256) COLLATE utf8_bin DEFAULT NULL,
   `TESTO_5`             varchar(256) COLLATE utf8_bin DEFAULT NULL,
   `DATA_1`              date DEFAULT NULL,
   `DATA_2`              date DEFAULT NULL,
   `DATA_3`              date DEFAULT NULL,
   `DATA_4`              date DEFAULT NULL,
   `DATA_5`              date DEFAULT NULL,
   `IMPORTO_1`           decimal(15, 2) DEFAULT NULL,
   `IMPORTO_2`           decimal(15, 2) DEFAULT NULL,
   `IMPORTO_3`           decimal(15, 2) DEFAULT NULL,
   `IMPORTO_4`           decimal(15, 2) DEFAULT NULL,
   `IMPORTO_5`           decimal(15, 2) DEFAULT NULL,
   `NUMERO_1`            int(11) DEFAULT NULL,
   `NUMERO_2`            int(11) DEFAULT NULL,
   `NUMERO_3`            int(11) DEFAULT NULL,
   `NUMERO_4`            int(11) DEFAULT NULL,
   `NUMERO_5`            int(11) DEFAULT NULL,
   `OP_INSERIMENTO`      varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`      datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `t_demanio_idrico_suolo_s`;
CREATE TABLE `t_demanio_idrico_suolo_s` (
  `ID` int(10) UNSIGNED NOT NULL ,
  `ID_ENTE` varchar(100) COLLATE utf8_bin NOT NULL,
  `CF_SOGG_PASSIVO` varchar(16) COLLATE utf8_bin NOT NULL,
  `IMPORTO` decimal(15,2) NOT NULL,
  `TI_DOVUTO` char(2) COLLATE utf8_bin NOT NULL,
  `ANNO_RIF` int(10) UNSIGNED NOT NULL,
  `DENOM_DEBITORE` varchar(70) COLLATE utf8_bin NOT NULL,
  `NUM_CONCESSIONE` varchar(35) COLLATE utf8_bin NOT NULL,
  `COMUNE_COMP` varchar(60) COLLATE utf8_bin NOT NULL,
  `PROV_COMP` char(2) COLLATE utf8_bin NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `t_demanio_idrico_acqua_s`;
CREATE TABLE `t_demanio_idrico_acqua_s` (
  `ID` int(10) UNSIGNED NOT NULL ,
  `ID_ENTE` varchar(100) COLLATE utf8_bin NOT NULL,
  `CF_SOGG_PASSIVO` varchar(16) COLLATE utf8_bin NOT NULL,
  `IMPORTO` decimal(15,2) NOT NULL,
  `TI_DOVUTO` char(2) COLLATE utf8_bin NOT NULL,
  `ANNO_RIF` int(10) UNSIGNED NOT NULL,
  `DENOM_DEBITORE` varchar(70) COLLATE utf8_bin NOT NULL,
  `NUM_CONCESSIONE` varchar(35) COLLATE utf8_bin NOT NULL,
  `COMUNE_COMP` varchar(60) COLLATE utf8_bin NOT NULL,
  `PROV_COMP` char(2) COLLATE utf8_bin NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int(10) UNSIGNED NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `condizioni_documento_s`;
CREATE TABLE `condizioni_documento_s`
(   `ID`               int(10) UNSIGNED NOT NULL ,
   `ID_CONDIZIONE`    varchar(20) COLLATE utf8_bin DEFAULT NULL,
   `ID_DOCUMENTO`     varchar(41) COLLATE utf8_bin DEFAULT NULL,
   `OP_INSERIMENTO`   varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`   datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `OP_ANNULLAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_ANNULLAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
DROP TABLE IF EXISTS`documenti_pagamento_s`;
CREATE TABLE `documenti_pagamento_s`
(   `ID`                         varchar(41) COLLATE utf8_bin NOT NULL,
   `ID_CFG_GATEWAY_PAGAMENTO`   int(10) UNSIGNED NOT NULL,
   `TS_EMISSIONE`               datetime(3) NOT NULL     DEFAULT CURRENT_TIMESTAMP( 3),
  `STATO` char(1) COLLATE utf8_bin NOT NULL,
  `INTESTATARIO` varchar(20) COLLATE utf8_bin NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `OP_ANNULLAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_ANNULLAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL DEFAULT '0',
  `ID_DISTINTA_PAGAMENTO` int(10) unsigned DEFAULT NULL,
  `IMPORTO` decimal(15,2) DEFAULT NULL,
  `IMPORTO_COMMISSIONI` decimal(15,2) DEFAULT NULL,
  `ID_DOCUMENTO_REPOSITORY` int(10) unsigned DEFAULT NULL,
  `DT_SCADENZA_DOC` date DEFAULT NULL,
  `EMAIL_VERSANTE` tinytext COLLATE utf8_bin NOT NULL,
  `CO_PAGANTE` varchar(16) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
DROP TABLE IF EXISTS`documenti_repository_s`;
CREATE TABLE `documenti_repository_s`
(   `ID`               int(10) UNSIGNED NOT NULL ,
   `NOME_FILE`        tinytext COLLATE utf8_bin,
   `DIMENSIONE`       int(10) UNSIGNED DEFAULT NULL,
   `OP_INSERIMENTO`   varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`   datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL DEFAULT '0',
  `DOCUMENTO` mediumblob NOT NULL,
  `ACK_DOWNLOAD` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
DROP TABLE IF EXISTS `iris_gateway_client_shop_cart_s`;
CREATE TABLE `iris_gateway_client_shop_cart_s`
(   `ID`               int(10) UNSIGNED NOT NULL ,
   `SESSION_ID`       varchar(128) COLLATE utf8_bin NOT NULL,
   `GTW_CLIENT_ID`    int(10) UNSIGNED NOT NULL,
   `ID_CONDIZIONE`    varchar(20) COLLATE utf8_bin NOT NULL,
   `IM_TOTALE`        decimal(15, 2) NOT NULL DEFAULT '0.00',
   `TI_SPONTANEO`     char(1) COLLATE utf8_bin NOT NULL,
   `OP_INSERIMENTO`   varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`   datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `iris_gateway_shop_cart_s`;
CREATE TABLE `iris_gateway_shop_cart_s`
(   `TOKEN`            varchar(32) COLLATE utf8_bin NOT NULL,
   `ID_CONDIZIONE`    varchar(20) COLLATE utf8_bin NOT NULL,
   `IM_TOTALE`        decimal(15, 2) NOT NULL DEFAULT '0.00',
   `OP_INSERIMENTO`   varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`   datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  `FLAG_OPPOSIZIONE_730` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`TOKEN`,`ID_CONDIZIONE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
DROP TABLE IF EXISTS `iris_gateway_session_s`;
CREATE TABLE `iris_gateway_session_s`
(   `TOKEN`            varchar(32) COLLATE utf8_bin NOT NULL,
   `SESSION_ID`       varchar(128) COLLATE utf8_bin NOT NULL,
   `GTW_CLIENT_ID`    int(10) UNSIGNED NOT NULL,
   `USATA`            smallint(6) NOT NULL DEFAULT '0',
   `IM_TOTALE`        decimal(15, 2) NOT NULL DEFAULT '0.00',
   `OPERATORE`        varchar(80) COLLATE utf8_bin NOT NULL,
   `INTESTATARIO`     varchar(40) COLLATE utf8_bin NOT NULL,
   `OP_INSERIMENTO`   varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`   datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL,
  `URL_BACK` text COLLATE utf8_bin,
  `URL_CANCEL` text COLLATE utf8_bin,
  `OFFLINE_PAYMENT_METHODS` int(10) unsigned NOT NULL DEFAULT '1',
  `FLAG_OPPOSIZIONE_730` smallint(6) NOT NULL DEFAULT '0',
  `TIPO_SOGGETTO_VERS` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `COD_FISCALE_VERS` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `ANAGRAFICA_VERS` varchar(70) COLLATE utf8_bin DEFAULT NULL,
  `INDIRIZZO_VERS` varchar(70) COLLATE utf8_bin DEFAULT NULL,
  `NUMERO_CIVICO_VERS` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `CAP_VERS` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `LOCALITA_VERS` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `PROVINCIA_VERS` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `NAZIONE_VERS` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_VERS` varchar(256) COLLATE utf8_bin DEFAULT NULL,
  `REDIRECT_ONLY` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`TOKEN`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
DROP TABLE IF EXISTS `iuvmap_s`;
CREATE TABLE `iuvmap_s`
(   `ID_ENTE`          varchar(50) COLLATE utf8_bin NOT NULL,
   `CD_TRB_ENTE`      varchar(50) COLLATE utf8_bin NOT NULL,
   `ID_PAGAMENTO`     varchar(35) COLLATE utf8_bin NOT NULL,
   `IUV`              varchar(35) COLLATE utf8_bin NOT NULL,
   `ST_RIGA`          varchar(2) COLLATE utf8_bin NOT NULL DEFAULT 'V',
   `OP_INSERIMENTO`   varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`   datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `OP_ANNULLAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_ANNULLAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL DEFAULT '0',
  UNIQUE KEY `IDX_IUVMAP_S2` (`ID_ENTE`,`IUV`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `distinte_pagamento_s`;
CREATE TABLE `distinte_pagamento_s`
(   `ID`                    int(10) UNSIGNED NOT NULL ,
   `STATO`                 varchar(25) COLLATE utf8_bin NOT NULL,
   `IMPORTO`               decimal(15, 2) NOT NULL,
   `IMPORTO_COMMISSIONI`   decimal(15, 2) NOT NULL,
   `DATA_CREAZIONE`        datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `DATA_SPEDIZIONE` datetime(3) DEFAULT NULL,
  `UTENTE_CREATORE` varchar(20) COLLATE utf8_bin NOT NULL,
  `NUMERO_DISPOSIZIONI` int(10) unsigned NOT NULL,
  `DIVISA` varchar(3) COLLATE utf8_bin NOT NULL,
  `COD_TRANSAZIONE` varchar(35) COLLATE utf8_bin NOT NULL,
  `ID_CFG_GATEWAY_PAGAMENTO` int(10) unsigned NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL DEFAULT '0',
  `DATA_PAGAMENTO` datetime(3) DEFAULT NULL,
  `ID_DOCUMENTO_REPOSITORY` int(10) unsigned DEFAULT NULL,
  `COD_PAGAMENTO` varchar(18) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_VERSANTE` tinytext COLLATE utf8_bin NOT NULL,
  `COD_TRANSAZIONE_PSP` varchar(70) COLLATE utf8_bin DEFAULT NULL,
  `AUTENTICAZIONE_SOGGETTO` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `TIPO_FIRMA` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `IUV` varchar(35) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `ID_FISCALE_CREDITORE` varchar(16) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `ID_GRUPPO` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `IBAN_ADDEBITO` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL_RECEIPT_SENT` smallint(6) NOT NULL DEFAULT '0',
  `TIPO_SOGGETTO_VERS` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `COD_FISCALE_VERS` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `ANAGRAFICA_VERS` varchar(70) COLLATE utf8_bin DEFAULT NULL,
  `INDIRIZZO_VERS` varchar(70) COLLATE utf8_bin DEFAULT NULL,
  `NUMERO_CIVICO_VERS` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `CAP_VERS` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `LOCALITA_VERS` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `PROVINCIA_VERS` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `NAZIONE_VERS` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `LOCALE` char(20) COLLATE utf8_bin DEFAULT NULL,
  `TIPO_IDENTIFICATIVO_ATTESTANTE` varchar(10)  COLLATE utf8_bin,
  `IDENTIFICATIVO_ATTESTANTE` varchar(35)  COLLATE utf8_bin,
  `DESCRIZIONE_ATTESTANTE` varchar(100)  COLLATE utf8_bin,
  `ID_PSP_MOD3`           VARCHAR(35) COLLATE utf8_bin DEFAULT NULL,
  `ID_INTERMEDIARIO_MOD3` VARCHAR(35) COLLATE utf8_bin DEFAULT NULL,
  `ID_CANALE_MOD3`        VARCHAR(35) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
DROP TABLE IF EXISTS `pagamenti_online_s`;
CREATE TABLE `pagamenti_online_s`
(   `ID`                      int(10) UNSIGNED NOT NULL ,
   `ID_DISTINTE_PAGAMENTO`   int(10) UNSIGNED DEFAULT NULL,
   `COD_AUTORIZZAZIONE`      varchar(41) COLLATE utf8_bin DEFAULT NULL,
   `SESSION_ID_SISTEMA`      varchar(5) COLLATE utf8_bin NOT NULL,
   `SESSION_ID_TERMINALE`    varchar(20) COLLATE utf8_bin NOT NULL,
   `SESSION_ID_TOKEN`        varchar(255) COLLATE utf8_bin NOT NULL,
   `SESSION_ID_TIMBRO`       varchar(20) COLLATE utf8_bin NOT NULL,
   `TS_OPERAZIONE`           datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `TI_OPERAZIONE` varchar(30) COLLATE utf8_bin NOT NULL,
  `NUM_OPERAZIONE` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `ID_OPERAZIONE` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `DE_OPERAZIONE` tinytext COLLATE utf8_bin NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned DEFAULT '0',
  `APPLICATION_ID` varchar(100) COLLATE utf8_bin NOT NULL DEFAULT 'IRIS',
  `SYSTEM_ID` varchar(70) COLLATE utf8_bin NOT NULL DEFAULT 'IRIS',
  `ESITO` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT 'OK',
  `COD_ERRORE` varchar(8) COLLATE utf8_bin NOT NULL DEFAULT '',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
DROP TABLE IF EXISTS `gde_documenti_ndp_s`;
CREATE TABLE `gde_documenti_ndp_s`
(   `ID`                               int(10) UNSIGNED NOT NULL ,
   `ID_DOMINIO`                       varchar(35) COLLATE utf8_bin NOT NULL,
   `ID_UNIVOCO_VERSAMENTO`            varchar(35) COLLATE utf8_bin NOT NULL,
   `CODICE_CONTESTO_PAGAMENTO`        varchar(35) COLLATE utf8_bin NOT NULL,
   `TENTATIVO`                        int(4) NOT NULL,
   `TIPO`                             char(2) COLLATE utf8_bin DEFAULT NULL,
   `DIMENSIONE`                       int(11) DEFAULT NULL,
   `DOCUMENTO`                        mediumblob NOT NULL,
   `ACK_DOWNLOAD`                     varchar(1) COLLATE utf8_bin DEFAULT NULL,
   `ID_PRESTATORESERVIZI_PAGAMENTO`   varchar(35) COLLATE utf8_bin DEFAULT NULL,
   `OP_INSERIMENTO`                   varchar(40) COLLATE utf8_bin NOT NULL,
   `TS_INSERIMENTO`                   datetime(3)     NOT NULL     DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin ;
  
DROP TABLE IF EXISTS`gde_eventi_s`;
CREATE TABLE `gde_eventi_s`
(
   `dt_evento`                        datetime(3) NOT NULL,
   `id_dominio`                       varchar(35) COLLATE utf8_bin NOT NULL,
   `id_univoco_versamento`            varchar(35) COLLATE utf8_bin NOT NULL,
   `codice_contesto_pagamento`        varchar(35) COLLATE utf8_bin NOT NULL,
   `id_prestatoreservizi_pagamento`   varchar(35) COLLATE utf8_bin NOT NULL,
   `tipo_versamento`                  varchar(4) COLLATE utf8_bin NOT NULL,
   `componente`                       varchar(35) COLLATE utf8_bin NOT NULL,
   `categoria_evento`                 varchar(255) COLLATE utf8_bin NOT NULL,
   `tipo_evento`                      varchar(50) COLLATE utf8_bin NOT NULL,
   `sotto_tipo_evento`                varchar(35) COLLATE utf8_bin NOT NULL,
   `id_fruitore`                      varchar(35) COLLATE utf8_bin NOT NULL,
   `id_erogatore`                     varchar(35) COLLATE utf8_bin NOT NULL,
   `id_stazione_intermediario_pa`     varchar(35)     COLLATE utf8_bin     DEFAULT NULL,
   `canale_pagamento`                 varchar(35)     COLLATE utf8_bin     DEFAULT NULL,
   `param_specifici_interfaccia`      varchar(255)     COLLATE utf8_bin     DEFAULT NULL,
   `esito`                            varchar(35)     COLLATE utf8_bin     DEFAULT NULL,
   `id_egov`                          varchar(255)     COLLATE utf8_bin     DEFAULT NULL,
   `FAULT_ID`                         varchar(255)     COLLATE utf8_bin     DEFAULT NULL,
   `FAULT_CODE`                       varchar(255)      COLLATE utf8_bin      DEFAULT NULL,
   `FAULT_STRING`                     varchar(255)     COLLATE utf8_bin     DEFAULT NULL,
   `FAULT_DESCRIPTION`                varchar(255)       COLLATE utf8_bin       DEFAULT NULL,
   `FAULT_SERIAL`                     int(10) UNSIGNED DEFAULT NULL,
   `OP_INSERIMENTO`                   varchar(40)    COLLATE utf8_bin    NOT NULL    DEFAULT 'OP_DEFAULT',
   `TS_INSERIMENTO`                   datetime(3)     NOT NULL     DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned NOT NULL DEFAULT '0',
  `ID` int(10) unsigned NOT NULL ,
  `ORI_FAULT_CODE`        VARCHAR(255) COLLATE utf8_bin,
  `ORI_FAULT_STRING`      VARCHAR(255) COLLATE utf8_bin,
  `ORI_FAULT_DESCRIPTION` VARCHAR(255) COLLATE utf8_bin,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
DROP TABLE IF EXISTS`gde_infospcoop_s`;
CREATE TABLE `gde_infospcoop_s` (
  `id_egov` varchar(255) COLLATE utf8_bin NOT NULL,
  `tipo_soggetto_erogatore` varchar(255) COLLATE utf8_bin NOT NULL,
  `soggetto_erogatore` varchar(255) COLLATE utf8_bin NOT NULL,
  `tipo_soggetto_fruitore` varchar(255) COLLATE utf8_bin NOT NULL,
  `soggetto_fruitore` varchar(255) COLLATE utf8_bin NOT NULL,
  `tipo_servizio` varchar(255) COLLATE utf8_bin NOT NULL,
  `servizio` varchar(255) COLLATE utf8_bin NOT NULL,
  `azione` varchar(255) COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id_egov`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
DROP TABLE IF EXISTS`dati_anagrafici_dest_s`;
CREATE TABLE `dati_anagrafici_dest_s`(
  `ID` int(10) UNSIGNED NOT NULL ,
  `ID_DESTINATARIO` varchar(20) binary NOT NULL,
  `TIPO_SOGGETTO` char(1) binary NOT NULL,
  `ANAGRAFICA` varchar(70) binary DEFAULT NULL,
  `DATA_NASCITA` date DEFAULT NULL,
  `LUOGO_NASCITA` varchar(35) binary DEFAULT NULL,
  `INDIRIZZO` varchar(70) binary DEFAULT NULL,
  `NUMERO_CIVICO` varchar(16) binary DEFAULT NULL,
  `CAP` varchar(16) binary DEFAULT NULL,
  `LOCALITA` varchar(35) binary DEFAULT NULL,
  `PROVINCIA` varchar(35) binary DEFAULT NULL,
  `NAZIONE` char(2) binary DEFAULT NULL,
  `EMAIL` varchar(256) binary DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) binary NOT NULL,
  `TS_INSERIMENTO` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
  `OP_AGGIORNAMENTO` varchar(40) binary DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (ID),
  UNIQUE INDEX IDX_DATI_ANAGRAFICI_DEST_S (ID_DESTINATARIO)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 262
CHARACTER SET utf8
COLLATE utf8_bin
ROW_FORMAT = DYNAMIC;  

DROP TABLE IF EXISTS `bozze_bonifici_riaccredito_s` ;
CREATE TABLE `bozze_bonifici_riaccredito_s` (
  `ID` int(10) UNSIGNED NOT NULL ,
  `IMPORTO` decimal(13, 2) NOT NULL,
  `IBAN_BENEFICIARIO` varchar(27) binary DEFAULT NULL,
  `RAG_SOCIALE_BENEFICIARIO` tinytext binary NOT NULL,
  `FLAG_ELABORAZIONE` smallint(6) NOT NULL,
  `ID_ENTE` varchar(50) binary DEFAULT NULL,
  `ID_ESITO_ORIGINE` int(10) UNSIGNED NOT NULL,
  `ID_BONIFICI_RIACCREDITO` int(10) UNSIGNED DEFAULT NULL,
  `ID_CONDIZIONE` varchar(255) binary NOT NULL,
  `OP_INSERIMENTO` varchar(40) binary DEFAULT NULL,
  `TS_INSERIMENTO` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP (3),
  `OP_AGGIORNAMENTO` varchar(40) binary DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) UNSIGNED DEFAULT 0,
  `TIPO_ESITO` char(2) binary DEFAULT NULL,
  `ID_PAG_COND_ENTE` varchar(35) binary DEFAULT NULL,
  `CODFISC_DEBITORE` varchar(16) binary DEFAULT NULL,
  PRIMARY KEY (ID),
  UNIQUE INDEX INDXID_CONDIZ_BOZZE_S (ID_CONDIZIONE)
)
ENGINE = INNODB
AVG_ROW_LENGTH = 247
CHARACTER SET utf8
COLLATE utf8_bin
ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `notifiche_pagamenti_s` ;
CREATE TABLE `notifiche_pagamenti_s`
(   `ID_NOTIFICA`        varchar(20) COLLATE utf8_bin NOT NULL,
   `ID_PAGAMENTO`       int(10) UNSIGNED NOT NULL,
   `ID_PENDENZA`        varchar(20) COLLATE utf8_bin DEFAULT NULL,
   `ID_PENDENZAENTE`    varchar(35) COLLATE utf8_bin DEFAULT NULL,
   `ID_PAGAMENTOENTE`   varchar(35)   COLLATE utf8_bin   NOT NULL   DEFAULT 'Non presente',
   `CD_ENTE`            varchar(50) COLLATE utf8_bin NOT NULL,
   `CD_TRB_ENTE`        varchar(50) COLLATE utf8_bin NOT NULL,
   `TS_DECORRENZA`      datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
   `DT_SCADENZA` date NOT NULL,
   `TMBSPEDIZIONE` datetime(3) DEFAULT NULL,
   `TS_OPERAZIONE` datetime(3) DEFAULT NULL,
   `TS_ORDINE` datetime(3) NOT NULL,
   `IM_TOTALE` decimal(10,3) NOT NULL,
   `TOTIMPORTIPOSITIVI` decimal(15,2) DEFAULT NULL,
   `STATO_NOTIFICA` varchar(15) COLLATE utf8_bin DEFAULT NULL,
   `STATO_PAGAMENTO` varchar(15) COLLATE utf8_bin DEFAULT NULL,
   `CO_PAGANTE` varchar(20) COLLATE utf8_bin NOT NULL,
   `TIPOSERVIZIO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
   `TIPOSPONTANEO` varchar(5) COLLATE utf8_bin NOT NULL,
   `DE_CAUSALE` tinytext COLLATE utf8_bin,
   `MSGID` varchar(35) COLLATE utf8_bin DEFAULT NULL,
   `TI_DEBITO` varchar(35) COLLATE utf8_bin DEFAULT NULL,
   `ID_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
   `ID_SYSTEM` varchar(35) COLLATE utf8_bin NOT NULL,
   `E2EMSGID` varchar(44) COLLATE utf8_bin DEFAULT NULL,
   `RECEIVERID` varchar(35) COLLATE utf8_bin DEFAULT NULL,
   `RECEIVERSYS` varchar(35) COLLATE utf8_bin DEFAULT NULL,
   `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
   `TS_INSERIMENTO` datetime(3) DEFAULT NULL,
   `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
   `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
   `VERSION` int(10) unsigned NOT NULL DEFAULT '0',
   `DATA_ACCREDITO_CONTOTECNICO` date DEFAULT NULL,
   `DATA_ACCREDITO_ENTE` date DEFAULT NULL,
   `TRT_RECEIVERID` varchar(35) COLLATE utf8_bin DEFAULT NULL,
   `TRT_RECEIVERSYS` varchar(35) COLLATE utf8_bin DEFAULT NULL,
   PRIMARY KEY (`ID_NOTIFICA`)   
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
 
DROP TABLE IF EXISTS`casellario_dispo_s`;
CREATE TABLE `casellario_dispo_s`
(
  `ID`                        int(10) UNSIGNED NOT NULL ,
  `FLAG_ELABORAZIONE`         smallint(6) DEFAULT NULL,
  `DATA_ELABORAZIONE`         datetime(3) DEFAULT NULL,
  `TIPO_FLUSSO`               char(2) COLLATE utf8_bin DEFAULT NULL,
  `NOME_SUPPORTO`             varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `NUMERO_RECORD`             int(10) UNSIGNED DEFAULT NULL,
  `DIMENSIONE`                int(10) UNSIGNED DEFAULT NULL,
  `TIPO_ERRORE`               smallint(6) DEFAULT NULL,
  `DESC_ERRORE`               mediumtext COLLATE utf8_bin,
  `FLUSSO_CBI`                mediumblob,
  `ID_DISTINTE_PAGAMENTO`     int(10) UNSIGNED DEFAULT NULL,
  `ID_DISTINTE_RIACCREDITO`   int(10) UNSIGNED DEFAULT NULL,
  `OP_INSERIMENTO`            varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO`            datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP( 3),
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime(3) DEFAULT NULL,
  `VERSION` int(10) unsigned DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
DROP TABLE IF EXISTS `revoche_pagamento_s`;
CREATE TABLE `revoche_pagamento_s` 
(  
  `ID`                          INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `ID_PAGAMENTI`                INT UNSIGNED NOT NULL , 
  `STATO_REVOCA`                VARCHAR(1)   COLLATE utf8_bin,
  `ESITO_REVOCA_INVIATO`        VARCHAR(1)   COLLATE utf8_bin,
  `TIPO_REVOCA_RICHIESTA`       VARCHAR(1)   COLLATE utf8_bin,
  `CAUSALE_REVOCA_RICHIESTA`    VARCHAR(140) COLLATE utf8_bin,
  `DATI_AGG_REVOCA_RICHIESTA`   VARCHAR(140) COLLATE utf8_bin,
  `ID_MESSAGGIO_REVOCA`         VARCHAR(35)  COLLATE utf8_bin,
  `TS_MESSAGGIO_REVOCA`         DATETIME DEFAULT NULL,
  `CAUSALE_ESITO_REVOCA`        VARCHAR(140) COLLATE utf8_bin,
  `DATI_AGG_ESITO_REVOCA`       VARCHAR(140) COLLATE utf8_bin,
  `OP_INSERIMENTO`              VARCHAR(40)  COLLATE utf8_bin NOT NULL, 
  `TS_INSERIMENTO`              DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, 
  `OP_AGGIORNAMENTO`            VARCHAR(40)  COLLATE utf8_bin,
  `TS_AGGIORNAMENTO`            DATETIME DEFAULT NULL,
  `VERSION`                     INT UNSIGNED DEFAULT 0 NOT NULL,
  PRIMARY KEY (`ID`) 
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `prenota_avvisi_digitali_s`;
CREATE TABLE `prenota_avvisi_digitali_s`  
(  
  `ID`                          INT UNSIGNED NOT NULL AUTO_INCREMENT,
  `ID_ENTE`                     VARCHAR(50) COLLATE utf8_bin NOT NULL ,
  `ID_CONDIZIONE`               VARCHAR(20) COLLATE utf8_bin NOT NULL ,  
  `ID_PAGAMENTO`                VARCHAR(35) COLLATE utf8_bin NOT NULL ,
  `CODICE_AVVISO`               VARCHAR(40) COLLATE utf8_bin NOT NULL ,
  `TIPO_OPERAZIONE_ORIG`        CHAR(1) COLLATE utf8_bin NOT NULL,
  `TIPO_OPERAZIONE_AVVISO`      CHAR(1) COLLATE utf8_bin ,
  `TIPO_PROCESSO`               CHAR(2) COLLATE utf8_bin NOT NULL,  
  `ID_RICHIESTA_AVVISO`         VARCHAR(140) COLLATE utf8_bin ,
  `STATO_AVVISO`                CHAR(1) COLLATE utf8_bin NOT NULL,
  `DESCR_STATO_AVVISO`          VARCHAR(100) COLLATE utf8_bin ,
  `NUM_TENTATIVI_AVVISO`        INT UNSIGNED  DEFAULT 0 NOT NULL ,  
  `ID_FILE_AVVISATURA`          VARCHAR(140) COLLATE utf8_bin ,
  `ID_EVENTO_SMS`               INT UNSIGNED ,
  `ID_EVENTO_EMAIL`             INT UNSIGNED ,
  `OP_INSERIMENTO`              VARCHAR(40) COLLATE utf8_bin NOT NULL , 
  `TS_INSERIMENTO`              DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, 
  `OP_AGGIORNAMENTO`            VARCHAR(40) COLLATE utf8_bin,
  `TS_AGGIORNAMENTO`            DATETIME DEFAULT NULL, 
  `VERSION`                     INT UNSIGNED DEFAULT 0 NOT NULL,
  `ID_EVENTO_AVV`               INT UNSIGNED,
  PRIMARY KEY (`ID`) 
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- TABELLE SVECCHIAMENTO PER SINGOLO ENTE
--
DROP TABLE IF EXISTS `JLTENTI_S`;
CREATE TABLE `JLTENTI_S` (
  `ID_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
  `CD_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
  `TP_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
  `INTESTATARIO` varchar(20) COLLATE utf8_bin NOT NULL,
  `DENOM` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `PROVINCIA` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `STATO` char(1) COLLATE utf8_bin NOT NULL,
  `MAX_NUM_TRIBUTI` int unsigned NOT NULL,
  `PR_VERSIONE` int unsigned NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  `CD_AZIENDA_SANITARIA` varchar(6),
  `GLN` varchar(13) COLLATE utf8_bin DEFAULT NULL,
  `FL_NDP` char(1) COLLATE utf8_bin  DEFAULT 'S', 
  `FL_NDP_MODELLO3` char(1) COLLATE utf8_bin  DEFAULT 'N', 
  `FL_BANCA_TESORIERA` char(1) COLLATE utf8_bin  DEFAULT 'N', 
  `FL_BFL` char(1) COLLATE utf8_bin  DEFAULT 'N', 
  `NDP_COD_STAZ_PA` varchar(4) COLLATE utf8_bin DEFAULT NULL, 
  `NDP_AUX_DIGIT` varchar(4) COLLATE utf8_bin DEFAULT NULL,
  `INFO_HOME_BO` LONGTEXT,  
  `AUTORIZZ_STAMPA_DDP` VARCHAR(100) COLLATE utf8_bin,
  `NDP_COD_SEGR`     VARCHAR(2),
  PRIMARY KEY (`ID_ENTE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `JLTENTR_S`;
CREATE TABLE `JLTENTR_S` (
  `ID_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
  `CD_TRB_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
  `ID_TRIBUTO` varchar(50) COLLATE utf8_bin NOT NULL,
  `ID_SYSTEM` varchar(35) COLLATE utf8_bin NOT NULL,
  `DE_TRB` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `FL_INIZIATIVA` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'N',
  `FL_PREDETERM` char(1) COLLATE utf8_bin DEFAULT NULL,
  `SOGG_ESCLUSI` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `IBAN` varchar(35) COLLATE utf8_bin NOT NULL,
  `IBAN_CCP` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `STATO` char(1) COLLATE utf8_bin NOT NULL,
  `PR_VERSIONE` int unsigned NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  `FL_NOTIFICA_PAGAMENTO` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'Y',
  `INFO_TRIBUTO` text COLLATE utf8_bin,
  `URL_UPD_SERVICE` text COLLATE utf8_bin,
  `URL_INFO_SERVICE` text COLLATE utf8_bin,
  `FL_RICEVUTA_ANONIMO` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'Y',
  `FL_NASCOSTO_FE` char(1) COLLATE utf8_bin DEFAULT NULL,
  `ISTRUZIONI_PAGAMENTO` text COLLATE utf8_bin,
  `IBAN_CONTO_TECNICO` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `NDP_COD_STAZ_PA` varchar(2),
  `NDP_AUX_DIGIT` varchar(2),   
  `FL_NDP_IUV_GENERATO` varchar(1) COLLATE utf8_bin NOT NULL DEFAULT '0',
  `NDP_IUV_START_NUM` bigint(20),
  `IBAN_MYBANK` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `FL_ADD_RT_VERIFICA_PAG` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'N',
  `FL_REPLACE_OTF` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'N',
  `FL_NDP` char(1) COLLATE utf8_bin DEFAULT 'S',
  `FL_NDP_MODELLO3` char(1) COLLATE utf8_bin DEFAULT 'N',
  `FL_BANCA_TESORIERA` char(1) COLLATE utf8_bin DEFAULT 'N',
  `FL_BFL` char(1) COLLATE utf8_bin DEFAULT 'N',
  `SRV_DETTAGLIO_PD` varchar(15) DEFAULT NULL,
  `URL_SRV_DETTAGLIO_PD` varchar(512) DEFAULT NULL,
  `SRV_ATTUALIZZA_PD` varchar(15) DEFAULT NULL,
  `URL_SRV_ATTUALIZZA_PD` varchar(512) DEFAULT NULL,
  `SRV_GEN_RICEVUTA` varchar(15) DEFAULT NULL,
  `URL_SRV_GEN_RICEVUTA` varchar(512) DEFAULT NULL,
  `TIPO_GESTIONE_RICH_REVOCA` VARCHAR(3)  DEFAULT 'KO' NOT NULL,
  `UOA_COMPETENTE` VARCHAR(50) COLLATE utf8_bin,
  `INTESTAZIONE_CCP` VARCHAR(256) COLLATE utf8_bin,
  `NDP_COD_SEGR` VARCHAR(2),
  `IUV_GEN_SEQ_TYPE` CHAR(1) DEFAULT 'T' NOT NULL,
  `URL_INFO_PRIVACY` VARCHAR(300) COLLATE utf8_bin,
  `AUTORIZZ_STAMPA_BP` VARCHAR(100) COLLATE utf8_bin,
  `CFG_TSR_CODICE_ENTE` VARCHAR(35),
  `CFG_TSR_CONTO_ENTE` VARCHAR(35),
  PRIMARY KEY (`ID_ENTE`,`CD_TRB_ENTE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `JLTSIL_S`;
CREATE TABLE `JLTSIL_S` (
  `ID_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
  `ID_SYSTEM` varchar(35) COLLATE utf8_bin NOT NULL,
  `DE_SYSTEM` varchar(250) COLLATE utf8_bin DEFAULT NULL,
  `STATO` char(1) COLLATE utf8_bin NOT NULL,
  `PR_VERSIONE` int unsigned NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  `TRT_ID` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `TRT_SYSTEM` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `FL_SSIL` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'N',
  `USER_ID` VARCHAR(50)COLLATE utf8_bin DEFAULT NULL,
  `AUTH_ID` VARCHAR(50)COLLATE utf8_bin DEFAULT NULL,
   CONSTRAINT CHK_JLTSIL_1 CHECK (FL_SSIL IN ('Y','N')),
  PRIMARY KEY (`ID_ENTE`,`ID_SYSTEM`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `IBAN_ENTI_S`;
CREATE TABLE `IBAN_ENTI_S` (
	`ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`ID_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,  
	`IBAN` varchar(35) COLLATE utf8_bin NOT NULL,  
	`DATA_CENSIMENTO` date DEFAULT NULL,
	`DATA_ATTIVAZIONE` date DEFAULT NULL,
	`ST_RIGA` char(1) COLLATE utf8_bin NOT NULL DEFAULT 'V',
	`OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
	`TS_INSERIMENTO` datetime DEFAULT NULL,
	`OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
	`TS_AGGIORNAMENTO` datetime DEFAULT NULL,
	`VERSION` int unsigned NOT NULL DEFAULT '0',
	PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `TRIBUTI_OPER_S`;
CREATE TABLE `TRIBUTI_OPER_S` (
  `INTESTATARIO` varchar(40) COLLATE utf8_bin NOT NULL,
  `OPERATORE` varchar(40) COLLATE utf8_bin NOT NULL,
   `TP_OPERATORE` varchar(2) COLLATE utf8_bin NOT NULL,
  `ID_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
  `CD_TRB_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime DEFAULT NULL,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`INTESTATARIO`,`OPERATORE`,`TP_OPERATORE`,`ID_ENTE`,`CD_TRB_ENTE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `STAT_POSIZIONI_MESE_S`;
CREATE TABLE STAT_POSIZIONI_MESE_S (
   `MESE_ESTRAZIONE`         DATE            NOT NULL,
   `ID_ENTE`                 VARCHAR(50)     NOT NULL,
   `ID_SYSTEM`               VARCHAR(35)     NOT NULL,
   `ID_TRIBUTO`              VARCHAR(50)     NOT NULL, 
   `CD_TRB_ENTE`             VARCHAR(50)     NOT NULL,
   `TIPO_MESSAGGIO`          VARCHAR(15)     NOT NULL, 
   `NUMERO_POSIZIONI`        INT UNSIGNED    NOT NULL,  
   `IMPORTO`                 DECIMAL(13,3)   NOT NULL,  
   `OP_INSERIMENTO`          VARCHAR(40)     NOT NULL,
   `TS_INSERIMENTO`          DATETIME(3)     DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
   `VERSION`                 INT UNSIGNED    DEFAULT 0 NOT NULL,
   `OP_AGGIORNAMENTO`        VARCHAR(40),
   `TS_AGGIORNAMENTO`        DATETIME        DEFAULT NULL,
   PRIMARY KEY (`MESE_ESTRAZIONE`, `ID_ENTE`, `ID_SYSTEM`, `ID_TRIBUTO`, `CD_TRB_ENTE`, `TIPO_MESSAGGIO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `STAT_PAGAMENTI_MESE_S`;
CREATE TABLE STAT_PAGAMENTI_MESE_S (
   `MESE_ESTRAZIONE`         DATE            NOT NULL,
   `ID_ENTE`                 VARCHAR(50)     NOT NULL,
   `ID_SYSTEM`               VARCHAR(35)     NOT NULL,
   `ID_TRIBUTO`              VARCHAR(50)     NOT NULL, 
   `CD_TRB_ENTE`             VARCHAR(50)     NOT NULL,
   `ATTESO`                  CHAR(1)         NOT NULL,
   `CIRCUITO`                INT UNSIGNED    NOT NULL,
   `MODALITA`                INT UNSIGNED    NOT NULL,
   `INCASSO`                 CHAR(1)         NOT NULL,   
   `NUMERO_PAGAMENTI`        INT UNSIGNED    NOT NULL,  
   `IMPORTO`                 DECIMAL(13,3)   NOT NULL,
   `OP_INSERIMENTO`          VARCHAR(40)     NOT NULL,
   `TS_INSERIMENTO`          DATETIME(3)     DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
   `VERSION`                 INT UNSIGNED    DEFAULT 0 NOT NULL,
   `OP_AGGIORNAMENTO`        VARCHAR(40),
   `TS_AGGIORNAMENTO`        DATETIME        DEFAULT NULL,
   PRIMARY KEY (`MESE_ESTRAZIONE`, `ID_ENTE`, `ID_SYSTEM`, `ID_TRIBUTO`, `CD_TRB_ENTE`, `ATTESO`, `CIRCUITO`, `MODALITA`, `INCASSO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
DROP TABLE IF EXISTS `SESSIONI_S`;
CREATE TABLE `SESSIONI_S` (
  `AZIENDA` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `CANALE` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `COLLEGAMENTO` double DEFAULT NULL,
  `INDIRIZZOIP` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `LINGUA` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `MAC` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  `SESSIONID` varchar(128) COLLATE utf8_bin NOT NULL,
  `ULTIMAOPERAZIONE` double DEFAULT NULL,
  `USERNAME` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `AZIENDASEL` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `CERTIFICATO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `STATO` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `ABI` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `INDIRIZZO` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `COMUNE` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PROVINCIA` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `CAP` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `CODICEFISCALE` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `LAPL` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `SIA` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `TIPOINTESTATARIO` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `ABIAZIENDA` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `CAB` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `ABIACCENTRATORE` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `RAGIONESOCIALEAZIENDA` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `TIPOSICUREZZA` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `IMPORTOMAX` double DEFAULT NULL,
  `PR_VERSIONE` int unsigned DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime DEFAULT NULL,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`SESSIONID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `SEQ_IUV_S`;
CREATE TABLE `SEQ_IUV_S` (
  `ID_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
  `CD_TRB_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
  `LAST_SEQ_IUV` bigint(20) NOT NULL,
  `ST_RIGA` varchar(2) COLLATE utf8_bin NOT NULL DEFAULT 'V',
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `RENDICONTAZIONI_S`;
CREATE TABLE `RENDICONTAZIONI_S` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `FLAG_ELABORAZIONE` smallint(6) NOT NULL,
  `COD_RENDICONTAZIONE` varchar(4) COLLATE utf8_bin NOT NULL,
  `STATO` varchar(20) COLLATE utf8_bin NOT NULL,
  `IMPORTO` decimal(15,2) NOT NULL,
  `DATA_CREAZIONE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `DATA_RICEZIONE` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UTENTE_CREATORE` varchar(20) COLLATE utf8_bin NOT NULL,
  `NUMERO_ESITI` int unsigned NOT NULL,
  `NUM_ESITI_PAGATO` int unsigned DEFAULT NULL,
  `NUM_ESITI_INSOLUTO` int unsigned DEFAULT NULL,
  `DIVISA` varchar(3) COLLATE utf8_bin NOT NULL,
  `ID_CASELLARIO_INFO` int unsigned DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned DEFAULT '0',
  `ID_FLUSSO` varchar(35),
  `ID_REGOLAMENTO` varchar(35),
  `DATA_REGOLAMENTO` date,
  `BIC_BANCA_RIVERSAMENTO` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
DROP TABLE IF EXISTS `INTEST_OPER_S`;
CREATE TABLE `INTEST_OPER_S` (
  `INTESTATARIO` varchar(40) COLLATE utf8_bin NOT NULL,
  `OPERATORE` varchar(40) COLLATE utf8_bin NOT NULL,
  `TP_OPERATORE` varchar(2) COLLATE utf8_bin NOT NULL,
  `BLOCCATO` smallint(6) NOT NULL DEFAULT '0',
  `PR_VERSIONE` int unsigned DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime DEFAULT NULL,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`INTESTATARIO`,`OPERATORE`,`TP_OPERATORE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `OPERATORI_S`;
CREATE TABLE `OPERATORI_S` (
  `OPERATORE` varchar(40) COLLATE utf8_bin NOT NULL,
  `INTESTATARIO` varchar(40) COLLATE utf8_bin NOT NULL,
  `CELLULARE` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `CODICEFIRMATARIO` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `DESCRIZIONE` varchar(255) COLLATE utf8_bin DEFAULT NULL,
  `NULL_COLL_FALL` int unsigned DEFAULT NULL,
  `ULTIMOCOLLEGAMENTO` datetime DEFAULT NULL,
  `BLOCCATO` smallint(6) DEFAULT NULL,
  `FLAGABILITATO` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `NOME` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORD` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `USERNAME` varchar(20) COLLATE utf8_bin NOT NULL,
  `DATASCADENZA` date DEFAULT NULL,
  `DATA_COLL_FALL` datetime DEFAULT NULL,
  `DATABLOCCO` datetime DEFAULT NULL,
  `ABILRAPP` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `PASSWORDDISPO` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `NUMCOLLFALLITIDISP` int unsigned DEFAULT NULL,
  `BLOCCATODISP` smallint(6) DEFAULT NULL,
  `SCADPSWDISP` datetime DEFAULT NULL,
  `FUNZIONIABILITATE` varchar(7600) COLLATE utf8_bin DEFAULT NULL,
  `PROFILAZIONEESTESA` smallint(6) DEFAULT NULL,
  `CODICEFISCALE` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `COGNOME` varchar(30) COLLATE utf8_bin DEFAULT NULL,
  `PR_VERSIONE` int unsigned DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime DEFAULT NULL,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`OPERATORE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `JLTPREF_S`;
CREATE TABLE `JLTPREF_S` (
  `PROPRIETA` varchar(255) COLLATE utf8_bin NOT NULL,
  `VALORE` varchar(20) COLLATE utf8_bin NOT NULL,
  `TIPOSERVIZIO` varchar(10) COLLATE utf8_bin NOT NULL,
  `INTESTATARIO` varchar(20) COLLATE utf8_bin NOT NULL,
  `OPERATORE` varchar(20) COLLATE utf8_bin NOT NULL,
  `CATEGORIA` varchar(4) COLLATE utf8_bin NOT NULL,
  `LOCKED` char(1) COLLATE utf8_bin DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`PROPRIETA`,`INTESTATARIO`,`OPERATORE`,`CATEGORIA`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `PRENOTAZIONI_S`;
CREATE TABLE `PRENOTAZIONI_S` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `INTESTATARIO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `OPERATORE_RICH` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `COD_RICH` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `TIPOSERVIZIO` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `TIMESTAMP_BEGIN` datetime DEFAULT NULL,
  `TIMESTAMP_END` datetime DEFAULT NULL,
  `STATO` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL,
  `TIPO_ESPORTAZIONE` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
DROP TABLE IF EXISTS `intestatari_s`;
CREATE TABLE `INTESTATARI_S` (
  `ABI` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `ABIACCENTRATORE` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `BOLLOVIRTUALE` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `CAB` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `CHIAVESSB` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `CODICEPOSTEL` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `CODICESOFTWARE` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `DENOMINAZIONE` varchar(50) COLLATE utf8_bin DEFAULT NULL,
  `FUNZIONIABILITATE` text COLLATE utf8_bin,
  `GRUPPO` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `ID_INDIRIZZIPOSTALI` int unsigned DEFAULT NULL,
  `INTESTATARIO` varchar(20) COLLATE utf8_bin NOT NULL,
  `LAPL` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `RAGIONESOCIALE` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `RAPL` varchar(12) COLLATE utf8_bin DEFAULT NULL,
  `RCZ` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `RECORDLOCK` smallint(6) DEFAULT NULL,
  `SIA` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `STATO` int unsigned DEFAULT NULL,
  `TIPOINTESTATARIO` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `TIPOSICUREZZA` varchar(3) COLLATE utf8_bin DEFAULT NULL,
  `UFFPOSTALE` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `AZIENDAMIGRATA` char(1) COLLATE utf8_bin DEFAULT NULL,
  `IMPORTOMAXFLUSSO` decimal(15,2) DEFAULT NULL,
  `NONRESIDENTE` char(1) COLLATE utf8_bin DEFAULT NULL,
  `TMB_PRIMA_ATT` datetime DEFAULT NULL,
  `TMB_ULTIMA_ATT` datetime DEFAULT NULL,
  `CODICECUC` varchar(8) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `ISSR` varchar(4) COLLATE utf8_bin DEFAULT NULL,
  `CATEGORIA` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `SOTTOCATEGORIA` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `PR_VERSIONE` int unsigned DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime DEFAULT NULL,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  `FLAG_COMUNICAZIONI` char(1) COLLATE utf8_bin DEFAULT NULL,
  `FLAG_ENROLLMENT_AVVISATURA` CHAR(1) COLLATE utf8_bin DEFAULT NULL,
  `PRIVACY` varchar(1000) COLLATE utf8_bin DEFAULT NULL, 
  `SIA_CBI` varchar(5) COLLATE utf8_bin DEFAULT NULL,  
  PRIMARY KEY (`INTESTATARIO`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `indirizzipostali_s`;
CREATE TABLE `INDIRIZZIPOSTALI_S` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `CAP` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `CASELLAPOSTALE` varchar(5) COLLATE utf8_bin DEFAULT NULL,
  `CODICEFISCALE` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `COMUNE` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `FAX` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `FLAGRESIDENTE` varchar(1) COLLATE utf8_bin DEFAULT NULL,
  `INDIRIZZO` varchar(100) COLLATE utf8_bin DEFAULT NULL,
  `NAZIONE` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `PARTITAIVA` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `PROVINCIA` varchar(2) COLLATE utf8_bin DEFAULT NULL,
  `RECORDLOCK` smallint(6) DEFAULT NULL,
  `TELEFONO` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `TELEFONOCELLULARE` varchar(20) COLLATE utf8_bin DEFAULT NULL,
  `EMAIL` tinytext COLLATE utf8_bin,
  `NUMEROCIVICO` varchar(16) COLLATE utf8_bin DEFAULT NULL,
  `PR_VERSIONE` int unsigned DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime DEFAULT NULL,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=163 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `ESPORTAZIONI_S`;
CREATE TABLE `ESPORTAZIONI_S` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `ID_PRENOTAZIONE` int unsigned DEFAULT NULL,
  `NOME_FILE` tinytext COLLATE utf8_bin NOT NULL,
  `FORMATO` varchar(3) COLLATE utf8_bin NOT NULL,
  `COMPRESSIONE` char(1) COLLATE utf8_bin NOT NULL,
  `LEN_DATI` int unsigned NOT NULL,
  `DATI` mediumblob NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `FUNZIONIOPERATORI_S`;
CREATE TABLE `FUNZIONIOPERATORI_S` (
  `CODFUNZIONE` varchar(20) COLLATE utf8_bin NOT NULL,
  `INTESTATARIO` varchar(40) COLLATE utf8_bin NOT NULL,
  `OPERATORE` varchar(40) COLLATE utf8_bin NOT NULL,
  `PR_VERSIONE` int unsigned DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime DEFAULT NULL,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`CODFUNZIONE`,`INTESTATARIO`,`OPERATORE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `FUN_INTEST_S`;
CREATE TABLE `FUN_INTEST_S` (
  `INTESTATARIO` varchar(20) COLLATE utf8_bin NOT NULL,
  `CODFUNZIONE` varchar(20) COLLATE utf8_bin NOT NULL,
  `PR_VERSIONE` int unsigned DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime DEFAULT NULL,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`INTESTATARIO`,`CODFUNZIONE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `INFO_PRENOTAZIONI_S`;
CREATE TABLE `INFO_PRENOTAZIONI_S` (
	`ID` int unsigned NOT NULL AUTO_INCREMENT,
	`ID_ORIGINE_DATI` INT UNSIGNED DEFAULT NULL,
	`ID_PRENOTAZIONE` INT UNSIGNED NOT NULL,
	`TIPO_PROCESSO` varchar(4) COLLATE utf8_bin NOT NULL,
	`ID_PROCESSO` varchar(35) COLLATE utf8_bin NOT NULL,
	`ID_RICHIEDENTE` varchar(35) COLLATE utf8_bin NOT NULL,
	`OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
	`TS_INSERIMENTO` datetime(3)  DEFAULT CURRENT_TIMESTAMP(3) NOT NULL,
	`OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
	`TS_AGGIORNAMENTO` datetime (3) DEFAULT NULL,
	`VERSION` int unsigned NOT NULL DEFAULT '0',
	PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `FLUSSI_NDP_S`;
CREATE TABLE `FLUSSI_NDP_S` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `IDENTIFICATIVO_FLUSSO` varchar(35) COLLATE utf8_bin NOT NULL,
  `DATA_ORA_FLUSSO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `IDENTIFICATIVO_PSP` varchar(35),
  `STATO` varchar(20) COLLATE utf8_bin NOT NULL,
  `ANNO` int unsigned  DEFAULT '0',
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime DEFAULT NULL,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  `ID_DOMINIO` VARCHAR(20) COLLATE utf8_bin,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `ESITI_NDP_S`;
CREATE TABLE `ESITI_NDP_S` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `ID_RICONCILIAZIONE` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `FLAG_RICONCILIAZIONE` smallint(6) DEFAULT NULL,
  `IMPORTO` decimal(15,2) NOT NULL,
  `SEGNO` char(1) COLLATE utf8_bin NOT NULL,
  `ID_RISCOSSIONE` varchar(35) COLLATE utf8_bin NOT NULL,
  `DATA_PAGAMENTO` date NOT NULL,
  `ESITO_PAGAMENTO` varchar(35) COLLATE utf8_bin NOT NULL,
  `ID_BOZZE_BONIFICI_RIACCREDITO` int unsigned NOT NULL,
  `ID_RENDICONTAZIONI` int unsigned NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime DEFAULT NULL,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  `COD_ANOMALIA` varchar(35),
  `INDICE_PAGAMENTO` int unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
DROP TABLE IF EXISTS `CFG_TRIBUTIENTE_PLUGIN_S`;
CREATE TABLE `CFG_TRIBUTIENTE_PLUGIN_S` (
   `ID_ENTE`            varchar(50) COLLATE utf8_bin    NOT NULL,
   `CD_TRB_ENTE`        varchar(50) COLLATE utf8_bin    NOT NULL,
   `CD_PLUGIN`          varchar(50) COLLATE utf8_bin    NOT NULL,
   `DATI`               mediumblob,
   `OP_INSERIMENTO`     varchar(40) COLLATE utf8_bin    NOT NULL,
   `TS_INSERIMENTO`     datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
   `OP_AGGIORNAMENTO`   varchar(40) COLLATE utf8_bin ,
   `TS_AGGIORNAMENTO`   datetime (3) DEFAULT NULL,
   `VERSION`            int unsigned    NOT NULL DEFAULT 0,
PRIMARY KEY (`ID_ENTE`, `CD_TRB_ENTE`)    
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;
  
  DROP TABLE IF EXISTS `CFG_TRIBUTIENTE_GATEWAY_S`;
CREATE TABLE `CFG_TRIBUTIENTE_GATEWAY_S` (
	`ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`ID_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
	`CD_TRB_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
	`ID_CFG_FORNITORE_GATEWAY` int unsigned NOT NULL,
	`ID_CFG_GATEWAY_PAGAMENTO` int unsigned DEFAULT NULL,
	`MODELLO_VERSAMENTO` varchar(2) COLLATE utf8_bin DEFAULT NULL,
	`TIPO_VERSAMENTO` varchar(15) COLLATE utf8_bin DEFAULT NULL,
	`OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
	`TS_INSERIMENTO` datetime DEFAULT NULL,
	`OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
	`TS_AGGIORNAMENTO` datetime DEFAULT NULL,
	`VERSION` int unsigned NOT NULL DEFAULT '0',
PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `CFG_NOTIFICA_PAGAMENTO_S`;
CREATE TABLE `CFG_NOTIFICA_PAGAMENTO_S` (
	`ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`FREQ_NOTIFICA` varchar(15) COLLATE utf8_bin NOT NULL,
	`FORMATO_NOTIFICA` varchar(15) COLLATE utf8_bin NOT NULL,
	`CONSEGNA_NOTIFICA` varchar(15) COLLATE utf8_bin NOT NULL,
	`TIPO_NOTIFICA` varchar(15) COLLATE utf8_bin NOT NULL,
	`ID_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
	`CD_TRB_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
	`OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
	`TS_INSERIMENTO` datetime DEFAULT NULL,
	`OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
	`TS_AGGIORNAMENTO` datetime DEFAULT NULL,
	`VERSION` int unsigned NOT NULL DEFAULT '0',
	`URL_SRV_NOTIFICA` varchar(512) DEFAULT NULL,
	PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `CFG_ENTI_LOGO_S`;
CREATE TABLE `CFG_ENTI_LOGO_S` (
	`ID_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
	`NOME_FILE_LOGO` varchar(255) COLLATE utf8_bin DEFAULT NULL,
	`LOGO_BLOB` mediumblob,
	`OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
	`TS_INSERIMENTO` datetime DEFAULT NULL,
	`OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
	`TS_AGGIORNAMENTO` datetime DEFAULT NULL,
	`VERSION` int unsigned NOT NULL DEFAULT '0',
PRIMARY KEY (`ID_ENTE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `CFG_ENTI_TEMA_S`;
CREATE TABLE `CFG_ENTI_TEMA_S` (
	`NOME_IMG_LOGO` varchar(255) COLLATE utf8_bin DEFAULT NULL,
	`LOGO_BLOB` mediumblob,
	`NOME_IMG_HEADER` varchar(255) COLLATE utf8_bin DEFAULT NULL,
	`HEADER_BLOB` mediumblob,
	`INFORMAZIONI` varchar(2048) COLLATE utf8_bin DEFAULT NULL,
	`OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
	`TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
	`OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
	`TS_AGGIORNAMENTO` datetime DEFAULT NULL,
	`VERSION` int unsigned NOT NULL,
	`ID_TEMA` varchar(30) COLLATE utf8_bin DEFAULT NULL,
	`CD_ENTE` varchar(50) COLLATE utf8_bin NOT NULL,
	 PRIMARY KEY (`CD_ENTE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `CASELLARIO_INFO_S`;
CREATE TABLE `CASELLARIO_INFO_S` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `FLAG_ELABORAZIONE` smallint(6) DEFAULT NULL,
  `DATA_ELABORAZIONE` datetime DEFAULT NULL,
  `TIPO_FLUSSO` char(4) COLLATE utf8_bin DEFAULT NULL,
  `NOME_SUPPORTO` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `NUMERO_RECORD` int unsigned DEFAULT NULL,
  `DIMENSIONE` int unsigned DEFAULT NULL,
  `TIPO_ERRORE` smallint(6) DEFAULT NULL,
  `DESC_ERRORE` text COLLATE utf8_bin,
  `FLUSSO_CBI` mediumblob NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  `ID_SUPPORTO` varchar(70) COLLATE utf8_bin NOT NULL DEFAULT '-',
  `MITTENTE` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  `RICEVENTE` varchar(35) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `CASELLARIO_INFO_BLOCCHI_S`;
CREATE TABLE `CASELLARIO_INFO_BLOCCHI_S` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `ID_MITTENTE` varchar(35) COLLATE utf8_bin NOT NULL,
  `SISTEMA_MITTENTE` varchar(35) COLLATE utf8_bin NOT NULL,
  `ID_RICEVENTE` varchar(35) COLLATE utf8_bin NOT NULL,
  `TIPO_FLUSSO` varchar(35) COLLATE utf8_bin NOT NULL,
  `NOME_SUPPORTO` varchar(35) COLLATE utf8_bin NOT NULL,
  `DATA_RIFERIMENTO` date NOT NULL,
  `DATA_FLUSSO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `BLOCCHI_TOTALI` int unsigned NOT NULL,
  `BLOCCO_CORRENTE` int unsigned DEFAULT NULL,
  `BLOCK` mediumblob NOT NULL,
  `ZIPPED` int unsigned DEFAULT NULL,
  `CENTROSERVIZI_MITTENTE` varchar(35) COLLATE utf8_bin NOT NULL,
  `SET_CARATTERI` varchar(35) COLLATE utf8_bin NOT NULL,
  `STATO` varchar(20) COLLATE utf8_bin NOT NULL,
  `ID_CASELLARIO_INFO` int unsigned DEFAULT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- TABELLE SVECCHIAMENTO PER SINGOLO UTENTE
--
DROP TABLE IF EXISTS `GEC_UTENTI_CANALI_S`;
CREATE TABLE `GEC_UTENTI_CANALI_S` (
  `ID_UTENTE` varchar(50) COLLATE utf8_bin NOT NULL,
  `ID_CANALE` int unsigned NOT NULL,
  `IS_ANONYMOUS` char(1) COLLATE utf8_bin DEFAULT NULL,
  `STATO` varchar(20) COLLATE utf8_bin NOT NULL,
  `CONFIGURAZIONE` text COLLATE utf8_bin,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL,
  PRIMARY KEY (`ID_UTENTE`,`ID_CANALE`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `GEC_LOG_INVIO_MESSAGGI_S`;
CREATE TABLE `GEC_LOG_INVIO_MESSAGGI_S` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `ID_MESSAGGI_LOGICI` int unsigned DEFAULT NULL,
  `ID_CANALE` int unsigned NOT NULL,
  `STATO` varchar(20) COLLATE utf8_bin NOT NULL,
  `MESSAGGIO_FISICO` text COLLATE utf8_bin NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `ID_UTENTE_IRIS` tinytext COLLATE utf8_bin NOT NULL,
  `VERSION` int unsigned NOT NULL,
  `SCOPO_MESSAGGIO` varchar(10) COLLATE utf8_bin DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

DROP TABLE IF EXISTS `GEC_MESSAGGI_LOGICI_S`;
CREATE TABLE `GEC_MESSAGGI_LOGICI_S` (
  `ID` int unsigned NOT NULL AUTO_INCREMENT,
  `OGGETTO` tinytext COLLATE utf8_bin NOT NULL,
  `MESSAGGIO_LOGICO` text COLLATE utf8_bin NOT NULL,
  `OP_INSERIMENTO` varchar(40) COLLATE utf8_bin NOT NULL,
  `TS_INSERIMENTO` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `OP_AGGIORNAMENTO` varchar(40) COLLATE utf8_bin DEFAULT NULL,
  `TS_AGGIORNAMENTO` datetime DEFAULT NULL,
  `VERSION` int unsigned NOT NULL,
  `TIPO_MESSAGGIO` int unsigned DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


--
-- Aggiornamento informazioni DB
--
INSERT INTO APPLICATION_INFO (PRODUCT_NAME,PRODUCT_DB_VERSION,TS_AGGIORNAMENTO,VERSION)
VALUES (
	'INIT.STRUCT',
	'INIT_DDL_TABELLE_S', 
	CURRENT_TIMESTAMP,
	0
	);

COMMIT;

 
