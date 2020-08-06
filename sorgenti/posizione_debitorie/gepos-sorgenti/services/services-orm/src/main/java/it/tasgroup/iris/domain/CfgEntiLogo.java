package it.tasgroup.iris.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the ENTI_LAYOUT database table.
 * 
 */
@Entity
@Table(name="CFG_ENTI_LOGO")

public class CfgEntiLogo extends BaseIdEntity {
	
	private static final long serialVersionUID = 1L;
	private String idEnte;
	private byte[] logoBlob;
	private String nomeFileLogo;
	private String opAggiornamento;
	private String opInserimento;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;

	public CfgEntiLogo() {
	}


	@Id
	@Column(name="ID_ENTE")
	public String getIdEnte() {
		return this.idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}


	@Lob
	@Column(name="LOGO_BLOB")
	public byte[] getLogoBlob() {
		return this.logoBlob;
	}

	public void setLogoBlob(byte[] logoBlob) {
		this.logoBlob = logoBlob;
	}


	@Column(name="NOME_FILE_LOGO")
	public String getNomeFileLogo() {
		return this.nomeFileLogo;
	}

	public void setNomeFileLogo(String nomeFileLogo) {
		this.nomeFileLogo = nomeFileLogo;
	}


	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}


	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}


	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}


	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

}