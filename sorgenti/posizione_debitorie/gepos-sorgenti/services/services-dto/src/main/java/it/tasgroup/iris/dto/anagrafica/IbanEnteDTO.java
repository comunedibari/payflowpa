package it.tasgroup.iris.dto.anagrafica;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

public class IbanEnteDTO implements Serializable {

	private String idEnte;
    private String iban;    
	private Date dataCensimento;    
	private Date dataAttivazione;   
	private String stRiga;  
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private String opAggiornamento;
    private String opInserimento;
   
    private Long id;

	public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public Date getDataCensimento() {
		return dataCensimento;
	}

	public void setDataCensimento(Date dataCensimento) {
		this.dataCensimento = dataCensimento;
	}

	public Date getDataAttivazione() {
		return dataAttivazione;
	}

	public void setDataAttivazione(Date dataAttivazione) {
		this.dataAttivazione = dataAttivazione;
	}

	public String getStRiga() {
		return stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}

	public Timestamp getTsAggiornamento() {
		return tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}

	public Timestamp getTsInserimento() {
		return tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	public String getOpAggiornamento() {
		return opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

	public String getOpInserimento() {
		return opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
    
    
    
	
}
