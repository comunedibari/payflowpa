CREATE TABLE mygov_ente_tipo_dovuto
(
  mygov_ente_tipo_dovuto_id bigint NOT NULL,
  mygov_ente_id bigint NOT NULL,
  cod_tipo character varying(64),
  de_tipo character varying(256),
  CONSTRAINT mygov_ente_tipo_dovuto_pkey PRIMARY KEY (mygov_ente_tipo_dovuto_id),
  CONSTRAINT mygov_ente_tipo_dovuto_mygov_ente_fkey FOREIGN KEY (mygov_ente_id)
      REFERENCES mygov_ente (mygov_ente_id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
  OIDS=FALSE
);
ALTER TABLE mygov_ente_tipo_dovuto
  OWNER TO "USERE45_4O";

-- Index: fki_mygov_ente_tipo_dovuto_mygov_ente_fkey

-- DROP INDEX fki_mygov_ente_tipo_dovuto_mygov_ente_fkey;

CREATE INDEX fki_mygov_ente_tipo_dovuto_mygov_ente_fkey
  ON mygov_ente_tipo_dovuto
  USING btree
  (mygov_ente_id);


 CREATE SEQUENCE mygov_ente_tipo_dovuto_mygov_ente_tipo_dovuto_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE mygov_ente_tipo_dovuto_mygov_ente_tipo_dovuto_id_seq
  OWNER TO "USERE45_4O";
