package it.tasgroup.iris.dto.flussi;

import it.tasgroup.services.util.enumeration.EnumStatoPagamenti;
import it.tasgroup.services.util.enumeration.EnumTipoPagamenti;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class BozzeBonificiRiaccreditoDTO implements Serializable{
	
	private final static String EMPTY_STRING = "";

	private Long id;
	private String tipologiapagamento;
	private String ente;
	private BigDecimal importo;
	private String stato;
	private String debitore;
	private String tipodebito;
	private Timestamp dataPagamento;
	private String idPagamento;
	
	private String idRiconciliazione;
	
	private String statoHTML = EMPTY_STRING;
	private String tipologiapagamentoHTML = EMPTY_STRING;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTipodebito() {
		return tipodebito;
	}
	public void setTipodebito(String tipodebito) {
		this.tipodebito = tipodebito;
	}
	public String getTipologiapagamento() {
		return tipologiapagamento;
	}
	public void setTipologiapagamento(String tipologiapagamento) {
		this.tipologiapagamento = tipologiapagamento;
	}
	public String getEnte() {
		return ente;
	}
	public void setEnte(String ente) {
		this.ente = ente;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public String getDebitore() {
		return debitore;
	}
	public void setDebitore(String debitore) {
		this.debitore = debitore;
	}
	public Timestamp getDataPagamento() {
		return dataPagamento;
	}
	public void setDataPagamento(Timestamp dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	public String getTipologiapagamentoHTML() {
		
		if(getTipologiapagamento() != null && getTipologiapagamento().equals(EnumTipoPagamenti.SU.getChiave())){
			
			this.tipologiapagamentoHTML = EnumTipoPagamenti.SU.getDescrizione();
			
		} else if(getTipologiapagamento() != null && getTipologiapagamento().equals(EnumTipoPagamenti.RA.getChiave())){
			
			this.tipologiapagamentoHTML = EnumTipoPagamenti.RA.getDescrizione();
			
		}
		
		return tipologiapagamentoHTML;
	}
	public void setTipologiapagamentoHTML(String tipologiapagamentoHTML) {
		this.tipologiapagamentoHTML = tipologiapagamentoHTML;
	}
	public String getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}
	public String getStatoHTML() {
		if(getStato() != null && getStato().equals(EnumStatoPagamenti.ESE.getChiave())){
			
			this.statoHTML = EnumStatoPagamenti.ESE.getDescrizione();
		}
		
		return statoHTML;
	}
	public void setStatoHTML(String statoHTML) {
		this.statoHTML = statoHTML;
	}
	public void setIdRiconciliazione(String idRiconciliazione) {
		this.idRiconciliazione = idRiconciliazione;
	}
	public String getIdRiconciliazione() {
		return idRiconciliazione;
	}
	
}