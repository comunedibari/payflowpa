CREATE TABLE JLTTRPA (
  ID_TRIBUTO       varchar(50) NOT NULL, 
  DE_TRB           varchar(50), 
  CD_ADE           varchar(10), 
  TP_ENTRATA       varchar(50), 
  FL_PREDETERM     char(1), 
  FL_INIZIATIVA    char(1), 
  SOGG_ESCLUSI     varchar(50), 
  STATO            char(1) NOT NULL, 
  PR_VERSIONE      integer NOT NULL, 
  OP_INSERIMENTO   varchar(40) NOT NULL, 
  TS_INSERIMENTO   timestamp NOT NULL, 
  OP_AGGIORNAMENTO varchar(40), 
  TS_AGGIORNAMENTO timestamp, 
  PRIMARY KEY (ID_TRIBUTO));
  
CREATE TABLE JLTENTR (
  ID_ENTE          varchar(50) NOT NULL, 
  CD_TRB_ENTE      varchar(50) NOT NULL, 
  ID_TRIBUTO       varchar(50) NOT NULL, 
  ID_SYSTEM        varchar(35) NOT NULL, 
  DE_TRB           varchar(50), 
  FL_INIZIATIVA    char(1), 
  SOGG_ESCLUSI     varchar(50), 
  STATO            char(1) NOT NULL, 
  PR_VERSIONE      integer NOT NULL, 
  OP_INSERIMENTO   varchar(40) NOT NULL, 
  TS_INSERIMENTO   timestamp NOT NULL, 
  OP_AGGIORNAMENTO varchar(40), 
  TS_AGGIORNAMENTO timestamp,
  PRIMARY KEY (ID_ENTE, 
  CD_TRB_ENTE));
  
CREATE TABLE JLTENTP (
  TP_ENTE          varchar(50) NOT NULL, 
  DE_ENTE          varchar(30), 
  STATO            char(1) NOT NULL, 
  PR_VERSIONE      integer NOT NULL, 
  OP_INSERIMENTO   varchar(40) NOT NULL, 
  TS_INSERIMENTO   timestamp NOT NULL, 
  OP_AGGIORNAMENTO varchar(40), 
  TS_AGGIORNAMENTO timestamp, 
  PRIMARY KEY (TP_ENTE));
  
CREATE TABLE JLTTRTM (
  TP_ENTE          varchar(50) NOT NULL, 
  ID_TRIBUTO       varchar(50) NOT NULL, 
  DE_TRB           varchar(50), 
  STATO            char(1) NOT NULL, 
  PR_VERSIONE      integer NOT NULL, 
  OP_INSERIMENTO   varchar(40) NOT NULL, 
  TS_INSERIMENTO   timestamp, 
  OP_AGGIORNAMENTO varchar(40), 
  TS_AGGIORNAMENTO timestamp, 
  PRIMARY KEY (TP_ENTE, 
  ID_TRIBUTO));
  
CREATE TABLE JLTENTI (
  ID_ENTE          varchar(50) NOT NULL, 
  CD_ENTE          varchar(50) NOT NULL, 
  TP_ENTE          varchar(50) NOT NULL, 
  INTESTATARIO     varchar(20) NOT NULL, 
  DENOM            varchar(60), 
  PROVINCIA        char(2), 
  STATO            char(1) NOT NULL, 
  PR_VERSIONE      integer NOT NULL, 
  OP_INSERIMENTO   varchar(40) NOT NULL, 
  TS_INSERIMENTO   timestamp NOT NULL, 
  OP_AGGIORNAMENTO varchar(40), 
  TS_AGGIORNAMENTO timestamp, 
  PRIMARY KEY (ID_ENTE));
  
CREATE TABLE JLTSIL (
  ID_ENTE          varchar(50) NOT NULL, 
  ID_SYSTEM        varchar(35) NOT NULL, 
  DE_SYSTEM        varchar(250), 
  STATO            char(1) NOT NULL, 
  PR_VERSIONE      integer NOT NULL, 
  OP_INSERIMENTO   varchar(40) NOT NULL, 
  TS_INSERIMENTO   timestamp NOT NULL, 
  OP_AGGIORNAMENTO varchar(40), 
  TS_AGGIORNAMENTO timestamp, 
  PRIMARY KEY (ID_ENTE, 
  ID_SYSTEM));
  
CREATE INDEX JL1JLTENTR
  ON JLTENTR (ID_ENTE, ID_TRIBUTO);
  
CREATE UNIQUE INDEX JL1JLTENTI
  ON JLTENTI (INTESTATARIO);
  
CREATE UNIQUE INDEX JL2JLTENTI
  ON JLTENTI (CD_ENTE);