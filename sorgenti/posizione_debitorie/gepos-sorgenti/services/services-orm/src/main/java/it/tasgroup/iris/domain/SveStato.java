package it.tasgroup.iris.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


@NamedQueries ({
	@NamedQuery (
		  name="findAllSveStato", 
		  query="SELECT s FROM SveStato s order by s.dataUltimaOperazione")
})
@Entity
@Table(name="SVE_STATO")

public class SveStato implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String nomeProcesso;
	private Integer stato;
	private Timestamp  dataUltimaOperazione;
	private Timestamp  dataUltimoDump;
	private String nomeFileDump;
	private Timestamp  dataUltimoImport;
	private String parametri;
	
	public SveStato() {
	}
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="sve_stato_pk_gen")
	@SequenceGenerator(
		    name="sve_stato_pk_gen",
		    sequenceName="SVE_STATO_ID_SEQ",
		    allocationSize=1
		)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	@Column(name="NOME_PROCESSO")
	public String getNomeProcesso() {
		return nomeProcesso;
	}

	public void setNomeProcesso(String nomeProcesso) {
		this.nomeProcesso = nomeProcesso;
	}
	@Column(name="STATO_SVE")
	public Integer getStato() {
		return stato;
	}

	public void setStato(Integer stato) {
		this.stato = stato;
	}
	@Column(name="DATA_ORA_SVE")
	public Timestamp getDataUltimaOperazione() {
		return dataUltimaOperazione;
	}

	public void setDataUltimaOperazione(Timestamp dataUltimaOperazione) {
		this.dataUltimaOperazione = dataUltimaOperazione;
	}
	@Column(name="DATA_ORA_DUMP")
	public Timestamp getDataUltimoDump() {
		return dataUltimoDump;
	}

	public void setDataUltimoDump(Timestamp dataUltimoDump) {
		this.dataUltimoDump = dataUltimoDump;
	}
	@Column(name="NOME_FILE_DUMP")
	public String getNomeFileDump() {
		return nomeFileDump;
	}

	public void setNomeFileDump(String nomeFileDump) {
		this.nomeFileDump = nomeFileDump;
	}
	@Column(name="DATA_ORA_IMPORT")
	public Timestamp getDataUltimoImport() {
		return dataUltimoImport;
	}

	public void setDataUltimoImport(Timestamp dataUltimoImport) {
		this.dataUltimoImport = dataUltimoImport;
	}
	@Column(name="INFO_SVE")
	public String getParametri() {
		return parametri;
	}

	public void setParametri(String parametri) {
		this.parametri = parametri;
	}
	
}