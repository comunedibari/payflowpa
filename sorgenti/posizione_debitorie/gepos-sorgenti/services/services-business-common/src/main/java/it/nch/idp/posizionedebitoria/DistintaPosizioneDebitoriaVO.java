/*
 * Created on 16-mag-09
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.idp.posizionedebitoria;

import it.nch.erbweb.common.FlussoWipVO;
import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.tasgroup.services.util.enumeration.EnumTipoModalitaPagamento;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * @author Simone
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DistintaPosizioneDebitoriaVO extends FlussoWipVO {


	private Timestamp tmbSpedizione;
	private String tracciato;
	private String ente;
	private String idFiscaleEnte;
	private String tipoTributo;
	private String coPagante;
	private String systemId;
	private String subsystemId;
	private String applicationId;
	private Number idFornitorePagamento;
	private Number idModalitaPagamento;
	private String modalitaPagamento;
	private String modelloVersamento;
	private String bundleModPag;
	private Date dataScadenza;
	private String idPendenza;
	private String idCondizione;
	private String extToken;
	private Number idPagamento;
	private String stPagamento;
	private boolean showAggiornaBtn;
	private List pendenze;
	private Date dataPagamento;
	private BigDecimal importoCommissioni;
	private BigDecimal importoPendenza;
	private String tipoSpontaneo;
	private String tiDebito;
	private String hasRendiconto;
	private String codPagamento;
	private String flagIncasso;
	
	private String tipoIdentificativoAttestante;
	private String identificativoAttestante;
	private String descrizioneAttestante;
	
	private boolean associatedDocAvailable=true;
	
	public String getTipoSpontaneo() {
		return tipoSpontaneo;
	}

	public void setTipoSpontaneo(String tipoSpontaneo) {
		this.tipoSpontaneo = tipoSpontaneo;
	}

	public BigDecimal getImportoPendenza() {
		return importoPendenza;
	}

	public void setImportoPendenza(BigDecimal importoPendenza) {
		this.importoPendenza = importoPendenza;
	}

	public boolean isShowAggiornaBtn() {
		return showAggiornaBtn;
	}
	
	public boolean isShowRicevutaBtn() {
		
//		return (getStato().equals(StatiPagamentiIris.ESEGUITO.getFludMapping()) || getStato().equals(StatiPagamentiIris.ESEGUITO_SBF.getFludMapping())) &&
//				(!EnumTipoModalitaPagamento.BONIFICOCODPRED.getChiaveBundle().equals(getBundleModPag())) ;
		return (getStato().equals(StatiPagamentiIris.ESEGUITO.getFludMapping()) || getStato().equals(StatiPagamentiIris.ESEGUITO_SBF.getFludMapping()));
		
	}

	// TODO: eliminare?
	public boolean isShowQuietanzaBtn() {
		
		return ((getStato().equals(StatiPagamentiIris.ESEGUITO.getFludMapping()) || getStato().equals(StatiPagamentiIris.ESEGUITO_SBF.getFludMapping())) && !getFlagIncasso().equals("0")) ;
	}
	
	public String getVideoMessage() {
		
		String videoMsg = "";
		
		if(EnumTipoModalitaPagamento.DDPATMBT.getChiaveBundle().equals(getBundleModPag())
				|| EnumTipoModalitaPagamento.BONIFICOCODPRED.getChiaveBundle().equals(getBundleModPag())){
			videoMsg = "posizionedebitoria.distinte.messageRicevutaValida."+getBundleModPag();
		} else {
			videoMsg = "posizionedebitoria.distinte.messageRicevutaValida";
		}
		return videoMsg;
	}
	
	public boolean isDRP_OFFLINE(){
		return EnumTipoModalitaPagamento.DDPATMBT.getChiaveBundle().equals(getBundleModPag())
				|| EnumTipoModalitaPagamento.BONIFICOCODPRED.getChiaveBundle().equals(getBundleModPag())
				|| EnumTipoModalitaPagamento.EUPAY.getChiaveBundle().equals(getBundleModPag());
	}
	
	public void setShowAggiornaBtn(boolean showAggiornaBtn) {
		this.showAggiornaBtn = showAggiornaBtn;
	}

	public boolean getShowAggiornaBtn() {
		return isShowAggiornaBtn();
	}

	public Number getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(Number idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getStPagamento() {
		return stPagamento;
	}

	public void setStPagamento(String stPagamento) {
		this.stPagamento = stPagamento;
	}

	/**
	 * @return
	 */
	public String getCoPagante() {
		return coPagante;
	}

	/**
	 * @return
	 */
	public Date getDataScadenza() {
		return dataScadenza;
	}

	/**
	 * @return
	 */
	public String getEnte() {
		return ente;
	}

	/**
	 * @return
	 */
	public String getModalitaPagamento() {
		return modalitaPagamento;
	}

	/**
	 * @return
	 */
	public String getTipoTributo() {
		return tipoTributo;
	}

	/**
	 * @return
	 */
	public Timestamp getTmbSpedizione() {
		return tmbSpedizione;
	}

	/**
	 * @return
	 */
	public String getTracciato() {
		return tracciato;
	}

	/**
	 * @param string
	 */
	public void setCoPagante(String string) {
		coPagante = string;
	}

	/**
	 * @param date
	 */
	public void setDataScadenza(Date date) {
		dataScadenza = date;
	}

	/**
	 * @param string
	 */
	public void setEnte(String string) {
		ente = string;
	}

	/**
	 * @param string
	 */
	public void setModalitaPagamento(String string) {
		modalitaPagamento = string;
	}

	/**
	 * @param string
	 */
	public void setTipoTributo(String string) {
		tipoTributo = string;
	}

	public String getCodPagamento() {
		return codPagamento;
	}

	public void setCodPagamento(String codPagamento) {
		this.codPagamento = codPagamento;
	}

	/**
	 * @param timestamp
	 */
	public void setTmbSpedizione(Timestamp timestamp) {
		tmbSpedizione = timestamp;
	}

	/**
	 * @param string
	 */
	public void setTracciato(String string) {
		tracciato = string;
	}

	public String getIdPendenza() {
		return idPendenza;
	}

	public void setIdPendenza(String string) {
		idPendenza = string;
	}

	public void setExtToken(String extToken) {
		this.extToken = extToken;
	}

	public String getExtToken() {
		return extToken;
	}

	public List getPendenze() {
		return pendenze;
	}

	public void setPendenze(List pendenze) {
		this.pendenze = pendenze;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getImportoCommissioni() {
		return importoCommissioni;
	}

	public void setImportoCommissioni(BigDecimal importoCommissioni) {
		this.importoCommissioni = importoCommissioni;
	}

	public String getTiDebito() {
		return tiDebito;
	}

	public void setTiDebito(String tiDebito) {
		this.tiDebito = tiDebito;
	}

	public String getHasRendiconto() {
		return hasRendiconto;
	}

	public void setHasRendiconto(String hasRendiconto) {
		this.hasRendiconto = hasRendiconto;
	}

	public String getIdCondizione() {
		return idCondizione;
	}

	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}

	public String getFlagIncasso() {
		return flagIncasso;
	}

	public void setFlagIncasso(String flagIncasso) {
		this.flagIncasso = flagIncasso;
	}
	
	public String getBundleModPag() {
		return bundleModPag;
	}

	public void setBundleModPag(String bundleModPag) {
		this.bundleModPag = bundleModPag;
	}

	public Number getIdFornitorePagamento() {
		return idFornitorePagamento;
	}

	public void setIdFornitorePagamento(Number idFornitorePagamento) {
		this.idFornitorePagamento = idFornitorePagamento;
	}

	public Number getIdModalitaPagamento() {
		return idModalitaPagamento;
	}

	public void setIdModalitaPagamento(Number idModalitaPagamento) {
		this.idModalitaPagamento = idModalitaPagamento;
	}

	public String getSystemId() {
		return systemId;
	}

	public void setSystemId(String systemId) {
		this.systemId = systemId;
	}

	public String getSubsystemId() {
		return subsystemId;
	}

	public void setSubsystemId(String subsystemId) {
		this.subsystemId = subsystemId;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getIdFiscaleEnte() {
		return idFiscaleEnte;
	}

	public void setIdFiscaleEnte(String idFiscaleEnte) {
		this.idFiscaleEnte = idFiscaleEnte;
	}

	public boolean isAssociatedDocAvailable() {
		return associatedDocAvailable;
	}

	public void setAssociatedDocAvailable(boolean associatedDocAvailable) {
		this.associatedDocAvailable = associatedDocAvailable;
	}

	public String getModelloVersamento() {
		return modelloVersamento;
	}

	public void setModelloVersamento(String modelloVersamento) {
		this.modelloVersamento = modelloVersamento;
	}
	

	public String getTipoIdentificativoAttestante() {
		return tipoIdentificativoAttestante;
	}

	public void setTipoIdentificativoAttestante(String tipoIdentificativoAttestante) {
		this.tipoIdentificativoAttestante = tipoIdentificativoAttestante;
	}

	public String getIdentificativoAttestante() {
		return identificativoAttestante;
	}

	public void setIdentificativoAttestante(String identificativoAttestante) {
		this.identificativoAttestante = identificativoAttestante;
	}

	public String getDescrizioneAttestante() {
		return descrizioneAttestante;
	}

	public void setDescrizioneAttestante(String descrizioneAttestante) {
		this.descrizioneAttestante = descrizioneAttestante;
	}

}
