package it.tasgroup.iris.dto.anagrafica;

import java.io.Serializable;


/**
 * The DTO class for the CONFIG_NOTIFICHE database table.
 * 
 */
public class CfgNotificaPagamentoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String cdTrbEnte;
	private String consegnaNotifica;
	private String formatoNotifica;
	private String freqNotifica;
	private String idEnte;
	private String tipoNotifica;
	
	private String opInserimento;
	private String opAggiornamento;
	

	public CfgNotificaPagamentoDTO() {
	}

	public String getCdTrbEnte() {
		return this.cdTrbEnte;
	}

	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}

	public String getConsegnaNotifica() {
		return this.consegnaNotifica;
	}

	public void setConsegnaNotifica(String consegnaNotifica) {
		this.consegnaNotifica = consegnaNotifica;
	}

	public String getFormatoNotifica() {
		return this.formatoNotifica;
	}

	public void setFormatoNotifica(String formatoNotifica) {
		this.formatoNotifica = formatoNotifica;
	}

	public String getFreqNotifica() {
		return this.freqNotifica;
	}

	public void setFreqNotifica(String freqNotifica) {
		this.freqNotifica = freqNotifica;
	}

	public String getIdEnte() {
		return this.idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	public String getTipoNotifica() {
		return this.tipoNotifica;
	}

	public void setTipoNotifica(String tipoNotifica) {
		this.tipoNotifica = tipoNotifica;
	}

	public String getOpInserimento() {
		return opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}

	public String getOpAggiornamento() {
		return opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}

}