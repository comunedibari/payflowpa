/**
 * 
 */
package it.tasgroup.iris.dto;

import it.tasgroup.iris.dto.anagrafica.EnteDTO;
import it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author pazzik
 *
 */
public class CondizionePagamentoDTO implements Serializable{
	
	private String idCondizione;
	
	private String cdTrbEnte;
	
	private String coCip;
	
	private String deCanalepag;
	
	private Date dtFinevalidita;
	
	private Date dtIniziovalidita;
	
	private Timestamp dtPagamento;
	
	private Date dtScadenza;
	
	private String idPagamento;
	
	private String idPendenza;
	
	private String idPendenzaEnte;
	
	private String deNotePendenza;
	
	private BigDecimal imTotale;
	
	private String stPagamento;
	
	private String stRiga;
	
	private String tiCip;
	
	private String tiPagamento;
	
	private Timestamp tsDecorrenza;
	
	private String causalePagamento;
	
	private String ibanBeneficiario;

	private BigDecimal imPagamento;
	
	private String deNotePagamento;
	
	private String deMezzoPagamento;
	
	private EnumStatoPagamentoCondizione statoPagamentoCalcolato;
	
	private String causalePendenza;
	
	private String codiceFiscaleDebitore;
	
	private String descrizioneCreditore;
	
	private String descrizioneDebito;
	
	private EnteDTO ente;
	
	private AllegatiPendenzaDTO allegato;
	
	
	public String getIdCondizione() {
		return idCondizione;
	}
	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}
	public String getCdTrbEnte() {
		return cdTrbEnte;
	}
	public void setCdTrbEnte(String cdTrbEnte) {
		this.cdTrbEnte = cdTrbEnte;
	}
	public String getCoCip() {
		return coCip;
	}
	public void setCoCip(String coCip) {
		this.coCip = coCip;
	}
	public String getDeCanalepag() {
		return deCanalepag;
	}
	public void setDeCanalepag(String deCanalepag) {
		this.deCanalepag = deCanalepag;
	}
	public Date getDtFinevalidita() {
		return dtFinevalidita;
	}
	public void setDtFinevalidita(Date dtFinevalidita) {
		this.dtFinevalidita = dtFinevalidita;
	}
	public Date getDtIniziovalidita() {
		return dtIniziovalidita;
	}
	public void setDtIniziovalidita(Date dtIniziovalidita) {
		this.dtIniziovalidita = dtIniziovalidita;
	}
	public Timestamp getDtPagamento() {
		return dtPagamento;
	}
	public void setDtPagamento(Timestamp dtPagamento) {
		this.dtPagamento = dtPagamento;
	}
	public Date getDtScadenza() {
		return dtScadenza;
	}
	public void setDtScadenza(Date dtScadenza) {
		this.dtScadenza = dtScadenza;
	}
	public String getIdPagamento() {
		return idPagamento;
	}
	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}
	public BigDecimal getImTotale() {
		return imTotale;
	}
	public void setImTotale(BigDecimal imTotale) {
		this.imTotale = imTotale;
	}
	public String getStPagamento() {
		return stPagamento;
	}
	public void setStPagamento(String stPagamento) {
		this.stPagamento = stPagamento;
	}
	public String getStRiga() {
		return stRiga;
	}
	public void setStRiga(String stRiga) {
		this.stRiga = stRiga;
	}
	public String getTiCip() {
		return tiCip;
	}
	public void setTiCip(String tiCip) {
		this.tiCip = tiCip;
	}
	public String getTiPagamento() {
		return tiPagamento;
	}
	public void setTiPagamento(String tiPagamento) {
		this.tiPagamento = tiPagamento;
	}
	public Timestamp getTsDecorrenza() {
		return tsDecorrenza;
	}
	public void setTsDecorrenza(Timestamp tsDecorrenza) {
		this.tsDecorrenza = tsDecorrenza;
	}
	public String getCausalePagamento() {
		return causalePagamento;
	}
	public void setCausalePagamento(String causalePagamento) {
		this.causalePagamento = causalePagamento;
	}
	public String getIbanBeneficiario() {
		return ibanBeneficiario;
	}
	public void setIbanBeneficiario(String ibanBeneficiario) {
		this.ibanBeneficiario = ibanBeneficiario;
	}	
	public BigDecimal getImPagamento() {
		return imPagamento;
	}
	public void setImPagamento(BigDecimal imPagamento) {
		this.imPagamento = imPagamento;
	}
	public String getDeNotePagamento() {
		return deNotePagamento;
	}
	public void setDeNotePagamento(String deNotePagamento) {
		this.deNotePagamento = deNotePagamento;
	}
	public String getDeMezzoPagamento() {
		return deMezzoPagamento;
	}
	public void setDeMezzoPagamento(String deMezzoPagamento) {
		this.deMezzoPagamento = deMezzoPagamento;
	}
	public EnumStatoPagamentoCondizione getStatoPagamentoCalcolato() {
		return statoPagamentoCalcolato;
	}
	public void setStatoPagamentoCalcolato(EnumStatoPagamentoCondizione statoPagamentoCalcolato) {
		this.statoPagamentoCalcolato = statoPagamentoCalcolato;
	}
	public String getCausalePendenza() {
		return causalePendenza;
	}
	public void setCausalePendenza(String causalePendenza) {
		this.causalePendenza = causalePendenza;
	}
	public String getCodiceFiscaleDebitore() {
		return codiceFiscaleDebitore;
	}
	public void setCodiceFiscaleDebitore(String codiceFiscaleDebitore) {
		this.codiceFiscaleDebitore = codiceFiscaleDebitore;
	}
	public String getDescrizioneCreditore() {
		return descrizioneCreditore;
	}
	public void setDescrizioneCreditore(String descrizioneCreditore) {
		this.descrizioneCreditore = descrizioneCreditore;
	}
	public String getDescrizioneDebito() {
		return descrizioneDebito;
	}
	public void setDescrizioneDebito(String descrizioneDebito) {
		this.descrizioneDebito = descrizioneDebito;
	}
	public AllegatiPendenzaDTO getAllegato() {
		return allegato;
	}
	public void setAllegato(AllegatiPendenzaDTO allegato) {
		this.allegato = allegato;
	}
	public EnteDTO getEnte() {
		return ente;
	}
	public void setEnte(EnteDTO ente) {
		this.ente = ente;
	}
	
	public String getIdPendenza() {
		return idPendenza;
	}
	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}
	public String getIdPendenzaEnte() {
		return idPendenzaEnte;
	}
	public void setIdPendenzaEnte(String idPendenzaEnte) {
		this.idPendenzaEnte = idPendenzaEnte;
	}
	public String getDeNotePendenza() {
		return deNotePendenza;
	}
	public void setDeNotePendenza(String deNotePendenza) {
		this.deNotePendenza = deNotePendenza;
	}
	
	
}
