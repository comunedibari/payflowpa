CREATE TABLE PENDENZE_CART (
  E2EMSGID            char(44) NOT NULL, 
  SENDERID            char(35) NOT NULL, 
  SENDERSYS           char(35) NOT NULL, 
  RECEIVERID          char(35) NOT NULL, 
  RECEIVERSYS         char(35) NOT NULL, 
  MESSAGGIO_XML       blob(100000000) NOT NULL, 
  TIMESTAMP_RICEZIONE timestamp NOT NULL, 
  NUM_PENDENZE        integer, 
  STATO               char(20) NOT NULL, 
  PR_VERSIONE         integer , 
  OP_INSERIMENTO      varchar(40) , 
  TS_INSERIMENTO      timestamp , 
  OP_AGGIORNAMENTO    varchar(40), 
  TS_AGGIORNAMENTO    timestamp, 
  PRIMARY KEY (E2EMSGID, 
  SENDERID, 
  SENDERSYS));
  
CREATE TABLE ESITI_PENDENZA (
  ID_ESITO_PENDENZA char(20) NOT NULL, 
  E2EMSGID          char(44) NOT NULL, 
  SENDERID          char(35) NOT NULL, 
  SENDERSYS         char(35) NOT NULL, 
  ID_PENDENZA       char(20), 
  ESITO_PENDENZA    char(20) NOT NULL, 
  DESCRIZIONE_ESITO char(200), 
  ID_PENDENZA_ENTE  char(35) NOT NULL, 
  STATO             char(8), 
  PR_VERSIONE       integer , 
  OP_INSERIMENTO    varchar(40) , 
  TS_INSERIMENTO    timestamp , 
  OP_AGGIORNAMENTO  varchar(40), 
  TS_AGGIORNAMENTO  timestamp, 
  PRIMARY KEY (ID_ESITO_PENDENZA));
  
CREATE TABLE ESITI_CART (
  E2EMSGID         char(44) NOT NULL, 
  SENDERID         char(35) NOT NULL, 
  SENDERSYS        char(35) NOT NULL, 
  ESITO_XML        blob(100000000) NOT NULL, 
  TIMESTAMP_INVIO  timestamp NOT NULL, 
  STATO            char(20) NOT NULL, 
  PR_VERSIONE      integer , 
  OP_INSERIMENTO   varchar(40) , 
  TS_INSERIMENTO   timestamp , 
  OP_AGGIORNAMENTO varchar(40), 
  TS_AGGIORNAMENTO timestamp, 
  PRIMARY KEY (E2EMSGID, 
  SENDERID, 
  SENDERSYS));
  
CREATE TABLE ERRORI_IDP (
  E2EMSGID          char(44) NOT NULL, 
  SENDERID          char(35) NOT NULL, 
  SENDERSYS         char(35) NOT NULL, 
  STATO             char(20) NOT NULL, 
  RECEIVERID        char(35) NOT NULL, 
  RECEIVERSYS       char(35) NOT NULL, 
  ESITO_XML         blob(100000000) NOT NULL, 
  DESCRIZIONE_STATO varchar(3000), 
  PR_VERSIONE       integer , 
  OP_INSERIMENTO    varchar(40) , 
  TS_INSERIMENTO    timestamp , 
  PRIMARY KEY (E2EMSGID, 
  SENDERID, 
  SENDERSYS, 
  STATO));
  
CREATE TABLE ERRORI_ESITI_PENDENZA (
  ID_ERRORE          char(20) NOT NULL, 
  ID_ESITO_PENDENZA  char(20) NOT NULL, 
  ID_PENDENZA        char(20), 
  ID_PENDENZA_ENTE   char(35) NOT NULL, 
  CODICE             char(128) NOT NULL, 
  DESCRIZIONE_ERRORE varchar(3000), 
  PR_VERSIONE        integer , 
  OP_INSERIMENTO     varchar(40) , 
  TS_INSERIMENTO     timestamp , 
  PRIMARY KEY (ID_ERRORE));
  
CREATE TABLE ERRORI_CART (
  ID_ERRORE_CART varchar(20) NOT NULL, 
  MESSAGGIO_XML  blob(100000000) NOT NULL, 
  TIPO_MESSAGGIO char(40) NOT NULL, 
  STATO_ERRORE   char(20) NOT NULL, 
  PR_VERSIONE    integer , 
  OP_INSERIMENTO varchar(40) , 
  TS_INSERIMENTO timestamp , 
  PRIMARY KEY (ID_ERRORE_CART));
  
CREATE TABLE MONITORING (
  TIMESTAMP_ESECUZIONE timestamp NOT NULL, 
  PROCESSO             char(150) NOT NULL, 
  METODO               char(150), 
  TEMPO_ESECUZIONE     integer NOT NULL, 
  E2EMSGID             char(44), 
  SENDERID             char(35), 
  SENDERSYS            char(35), 
  RECEIVERID           char(35), 
  RECEIVERSYS          char(35), 
  PR_VERSIONE          integer , 
  OP_INSERIMENTO       varchar(40) , 
  TS_INSERIMENTO       timestamp , 
  PRIMARY KEY (TIMESTAMP_ESECUZIONE));
  
  
CREATE UNIQUE INDEX JL1ESITI_PENDENZA 
  ON ESITI_PENDENZA (E2EMSGID, SENDERID, SENDERSYS, ID_ESITO_PENDENZA);
CREATE UNIQUE INDEX JL2ESITI_PENDENZA 
  ON ESITI_PENDENZA (ID_PENDENZA, ID_ESITO_PENDENZA);
CREATE UNIQUE INDEX JL1ERRORI_ESITI_PENDENZA 
  ON ERRORI_ESITI_PENDENZA (ID_ESITO_PENDENZA, ID_ERRORE);
CREATE UNIQUE INDEX JL2ERRORI_ESITI_PENDENZA 
  ON ERRORI_ESITI_PENDENZA (ID_PENDENZA, ID_ERRORE);
CREATE UNIQUE INDEX JL1MONITORING 
  ON MONITORING (E2EMSGID, SENDERID, SENDERSYS, TIMESTAMP_ESECUZIONE);

