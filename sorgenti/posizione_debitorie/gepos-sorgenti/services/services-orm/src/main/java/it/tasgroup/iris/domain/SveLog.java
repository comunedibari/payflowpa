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
		  name="findSveLogByNomeProcesso", 
		  query="SELECT log FROM SveLog log "
		  		+ "WHERE DATE(dataOraOperazione) >= "
		  		+ "DATE((SELECT MAX(dataOraOperazione) FROM SveLog WHERE nomeProcesso = :nomeProcesso)) "
		  		+ "AND nomeProcesso = :nomeProcesso "
		  		+ "ORDER BY dataOraOperazione DESC, recordId DESC")
})
@Entity
@Table(name="sve_log")

public class SveLog implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String nomeProcesso;
	private String nomeTabella;
	private Long recordId;
	private Timestamp  dataOraOperazione;
	private String infoOperazione;
	private Character tipoMessaggio;
	private Long id;
		
	public SveLog() {
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO,generator="sve_log_pk_gen")
	@SequenceGenerator(
		    name="sve_log_pk_gen",
		    sequenceName="SVE_LOG_ID_SEQ",
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

	@Column(name="NOME_TABELLA")
	public String getNomeTabella() {
		return nomeTabella;
	}
	public void setNomeTabella(String nomeTabella) {
		this.nomeTabella = nomeTabella;
	}
	@Column(name="RECORD_ID")
	public Long getRecordId() {
		return recordId;
	}
	public void setRecordId(Long recordId) {
		this.recordId = recordId;
	}
	@Column(name="DATA_ORA_OPERAZIONE")
	public Timestamp getDataOraOperazione() {
		return dataOraOperazione;
	}
	public void setDataOraOperazione(Timestamp dataOraOperazione) {
		this.dataOraOperazione = dataOraOperazione;
	}
	@Column(name="INFO_OPERAZIONE")
	public String getInfoOperazione() {
		return infoOperazione;
	}
	public void setInfoOperazione(String infoOperazione) {
		this.infoOperazione = infoOperazione;
	}
	@Column(name="TIPO_MESSAGGIO")
	public Character getTipoMessaggio() {
		return tipoMessaggio;
	}
	public void setTipoMessaggio(Character tipoMessaggio) {
		this.tipoMessaggio = tipoMessaggio;
	}
	
	
}