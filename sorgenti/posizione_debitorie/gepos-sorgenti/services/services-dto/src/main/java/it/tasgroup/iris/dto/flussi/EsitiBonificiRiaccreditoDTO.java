package it.tasgroup.iris.dto.flussi;

import it.tasgroup.services.util.enumeration.EnumTipoAnomaliaEBR;
import it.tasgroup.services.util.enumeration.EnumTipoCodiceRiferimentoEBR;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class EsitiBonificiRiaccreditoDTO implements Serializable{
	
	private final static String EMPTY_STRING = "";
	
	private String causale;
	private BigDecimal importo;
	private String tipoAnomalia;
	private Timestamp dataValutaOrdinante;
	private Timestamp dataValutaBeneficiario;
	private Timestamp dataOrdine;
	private Timestamp dataContabileAddebito;
	private Timestamp dataEsecuzione;
	private String tipoCodiceRiferimento;
	private String codiceRiferimento;
	
	private String tipoCodiceRiferimentoHTML = EMPTY_STRING;
	private String tipoAnomaliaHTML;
	
	private RendicontazioniDTO rendicontazioni;
	
	public RendicontazioniDTO getRendicontazioni() {
		return rendicontazioni;
	}
	public void setRendicontazioni(RendicontazioniDTO rendicontazioni) {
		this.rendicontazioni = rendicontazioni;
	}
	
	public String getCausale() {
		return causale;
	}
	public void setCausale(String causale) {
		this.causale = causale;
	}
	public BigDecimal getImporto() {
		return importo;
	}
	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}
	public String getTipoAnomalia() {
		return tipoAnomalia;
	}
	public void setTipoAnomalia(String tipoAnomalia) {
		this.tipoAnomalia = tipoAnomalia;
	}
	public Timestamp getDataValutaOrdinante() {
		return dataValutaOrdinante;
	}
	public void setDataValutaOrdinante(Timestamp dataValutaOrdinante) {
		this.dataValutaOrdinante = dataValutaOrdinante;
	}
	public Timestamp getDataValutaBeneficiario() {
		return dataValutaBeneficiario;
	}
	public void setDataValutaBeneficiario(Timestamp dataValutaBeneficiario) {
		this.dataValutaBeneficiario = dataValutaBeneficiario;
	}
	public Timestamp getDataOrdine() {
		return dataOrdine;
	}
	public void setDataOrdine(Timestamp dataOrdine) {
		this.dataOrdine = dataOrdine;
	}
	public Timestamp getDataContabileAddebito() {
		return dataContabileAddebito;
	}
	public void setDataContabileAddebito(Timestamp dataContabileAddebito) {
		this.dataContabileAddebito = dataContabileAddebito;
	}
	public Timestamp getDataEsecuzione() {
		return dataEsecuzione;
	}
	public void setDataEsecuzione(Timestamp dataEsecuzione) {
		this.dataEsecuzione = dataEsecuzione;
	}
	public String getTipoCodiceRiferimento() {
		return tipoCodiceRiferimento;
	}
	public void setTipoCodiceRiferimento(String tipoCodiceRiferimento) {
		this.tipoCodiceRiferimento = tipoCodiceRiferimento;
	}
	public String getCodiceRiferimento() {
		return codiceRiferimento;
	}
	public void setCodiceRiferimento(String codiceRiferimento) {
		this.codiceRiferimento = codiceRiferimento;
	}

	public String getTipoCodiceRiferimentoHTML() {
		
		if(getTipoCodiceRiferimento() != null && getTipoCodiceRiferimento().equals(EnumTipoCodiceRiferimentoEBR.CIB.getChiave())){
			
			this.tipoCodiceRiferimentoHTML = EnumTipoCodiceRiferimentoEBR.CIB.getDescrizione();
			
		} else if(getTipoCodiceRiferimento() != null && getTipoCodiceRiferimento().equals(EnumTipoCodiceRiferimentoEBR.CRO.getChiave())){
			
			this.tipoCodiceRiferimentoHTML = EnumTipoCodiceRiferimentoEBR.CRO.getDescrizione();
			
		}
		
		return tipoCodiceRiferimentoHTML;
	}
	
	public void setTipoCodiceRiferimentoHTML(String tipoCodiceRiferimentoHTML) {
		this.tipoCodiceRiferimentoHTML = tipoCodiceRiferimentoHTML;
	}
	
	public String getTipoAnomaliaHTML() {
		
		if(getTipoAnomalia() == null || getTipoAnomalia().equals(EnumTipoAnomaliaEBR.DISPES.getChiave())){
			
			this.tipoAnomaliaHTML = EnumTipoAnomaliaEBR.DISPES.getDescrizione();
			
		} else if(getTipoAnomalia() != null && getTipoAnomalia().equals(EnumTipoAnomaliaEBR.DISPNOTES.getChiave())){
			
			this.tipoAnomaliaHTML = EnumTipoAnomaliaEBR.DISPNOTES.getDescrizione();
			
		}
		
		return tipoAnomaliaHTML;
	}
	
	public void setTipoAnomaliaHTML(String tipoAnomaliaHTML) {
		this.tipoAnomaliaHTML = tipoAnomaliaHTML;
	}
	
}