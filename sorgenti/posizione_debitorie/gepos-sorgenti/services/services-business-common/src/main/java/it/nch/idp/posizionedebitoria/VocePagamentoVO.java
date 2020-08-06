package it.nch.idp.posizionedebitoria;

import it.tasgroup.services.util.IVocePagamento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class VocePagamentoVO implements IVocePagamento, Serializable{

	private static final long serialVersionUID = 1L;
	
	private String idPendenza;
	private String idCondizione;
	private String idVoce;
	private Timestamp tsDecorrenza;
	private String tiVoce;
	private String coVoce;
	private String deVoce;
	private BigDecimal imVoce;
	private String coCapBilancio;
	private String coAccertamento;
	private String stRiga;

	public VocePagamentoVO() {
		
	}

	
	public String getIdPendenza() {
		return idPendenza;
	}

	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}


	public String getIdCondizione() {
		return idCondizione;
	}

	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}

	
	public String getIdVoce() {
		return idVoce;
	}

	public void setIdVoce(String idVoce) {
		this.idVoce = idVoce;
	}

	
	public Timestamp getTsDecorrenza() {
		return tsDecorrenza;
	}

	public void setTsDecorrenza(Timestamp tsDecorrenza) {
		this.tsDecorrenza = tsDecorrenza;
	}

	
	public String getTiVoce() {
		return tiVoce;
	}

	public void setTiVoce(String tiVoce) {
		this.tiVoce = tiVoce;
	}

	
	public String getCoVoce() {
		return coVoce;
	}

	public void setCoVoce(String coVoce) {
		this.coVoce = coVoce;
	}

	
	public String getDeVoce() {
		return deVoce;
	}

	public void setDeVoce(String deVoce) {
		this.deVoce = deVoce;
	}

	
	public BigDecimal getImVoce() {
		return imVoce;
	}

	public void setImVoce(BigDecimal imVoce) {
		this.imVoce = imVoce;
	}

	
	public String getCoCapBilancio() {
		return coCapBilancio;
	}

	public void setCoCapBilancio(String coCapBilancio) {
		this.coCapBilancio = coCapBilancio;
	}

	
	public String getCoAccertamento() {
		return coAccertamento;
	}

	public void setCoAccertamento(String coAccertamento) {
		this.coAccertamento = coAccertamento;
	}

	
	public String getStRiga() {
		return stRiga;
	}

	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}
	
}
