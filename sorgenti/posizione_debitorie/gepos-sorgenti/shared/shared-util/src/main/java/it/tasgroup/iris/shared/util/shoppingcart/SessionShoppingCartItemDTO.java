package it.tasgroup.iris.shared.util.shoppingcart;

import it.tasgroup.iris.constants.SharedConstants;
import it.tasgroup.iris.shared.util.configuration.ConfigurationPropertyLoader;
import it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author pazzik
 *
 */
public class SessionShoppingCartItemDTO implements ISessionShoppingCartItem{ 
	
	private static final long serialVersionUID = 1L;

	private String idCondizione;
	private String idPendenza;
	private String idPagamentoEnte;
	private Integer idPagamento;
	private String idDistinta;
	private boolean cartellaPagamento;
	private int numeroPagamentiCartella;
	private int progressivoPagamentoCartella;
	private String idCondizioniCartella;
	private List<BigDecimal> importiCartella;
	private String itemOpposizione730 = "0";
	private String iuv;
	
	private Date decorrenza;
	private Date scadenza;
	
	private String idTributo;
	private String idEnte;
	private String idFiscaleEnte;
	private String idTributoEnte;
	private String cdPlugin;

	private String ente;
	private String tributo;	

	private BigDecimal importo;
	private String tipoPagamento;
	private String tiDebito;	
	private String tipoCausalePagamento;
	private String causaleCondizione;
	private String causale;
	private String causaleDecorata;  // a cura del front end
	private String codFiscaleDebitore;
	private String stato;
	private String tipoSpontaneo;
	
	private String ibanBeneficiario;
	
	private Object spontaneoVO;
	
	private EnumStatoPagamentoCondizione statoPagamentoCondizione;
	
	// per ora utilizzato solo dal Nodo dei Pagamenti SPC
	private String ibanAppoggio;
	
	private String idMittentePend;
	private String deMittentePend;
	private String tipoSoggettoVers; 
	private String codFiscaleVers; 
	private String anagraficaVers;
	private String indirizzoVers; 
	private String numeroCivicoVers; 
	private String capVers; 
	private String localitaVers; 
	private String provinciaVers; 
	private String nazioneVers; 
	private String emailVers;
	
	private String anagraficaDebitore;
	private String indirizzoDebitore;
	private String numeroCivicoDebitore;
	private String capDebitore;
	private String localitaDebitore;
	private String provinciaDebitore;
	private String nazioneDebitore;
	private String emailDebitore;	
	private String tipoSoggettoDebitore;
	
	private String authenticationType;
	
	private String datiSpecificiRiscossione;
	// marca bollo digitale
	private String marcaBolloTipoBollo;
	private String marcaBolloProvResidenzaDebitore;
	private String marcaBolloHashDocumento;
	private boolean ibanBeneficiarioCondizione;
		
	public SessionShoppingCartItemDTO() {}
	
	@Override
	public String getItemId() {
		
		return idCondizione;
	}

	@Override
	public void setItemId(String itemId) {
		
		setIdCondizione(itemId);
		
	}

	@Override
	public BigDecimal getItemAmount() {
		
		return importo;
	}

	@Override
	public void setItemAmount(BigDecimal itemAmount) {
		setImporto(itemAmount);
		
	}

	public String getEnte() {
		return ente;
	}

	public void setEnte(String ente) {
		this.ente = ente;
	}

	public String getTributo() {
		return tributo;
	}

	public void setTributo(String tributo) {
		this.tributo = tributo;
	}

	public Date getScadenza() {
		return scadenza;
	}

	public void setScadenza(Date scadenza) {
		this.scadenza = scadenza;
	}

	public Date getScadenzaVideo() {
		if (scadenza.compareTo(SharedConstants.NO_EXPIRE) == 0)
			return null;
		else
			return scadenza;
	}

	public BigDecimal getImporto() {
		return importo;
	}

	public void setImporto(BigDecimal importo) {
		this.importo = importo;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}

	public String getCausale() {
		return causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}

	public String getCodFiscaleDebitore() {
		return codFiscaleDebitore;
	}

	public void setCodFiscaleDebitore(String codFiscaleDebitore) {
		this.codFiscaleDebitore = codFiscaleDebitore;
	}
	
	public String getCodFiscaleDebitoreView() {
		String cfDefaultvalue = ConfigurationPropertyLoader.getInstance( "iris-fe.properties").getProperty("iris.codice.fiscale.anonymous");
		String result = codFiscaleDebitore;
		if(codFiscaleDebitore.equals(cfDefaultvalue) ) {
			 ResourceBundle rb = ResourceBundle.getBundle("messages.public.AnonymousMessages");
			 result = rb.getString("unipg.dott.nessuncodicefiscale");
		} 	
		return result;
	}
	
	public String getIdCondizione() {
		return idCondizione;
	}

	public void setIdCondizione(String idCondizione) {
		this.idCondizione = idCondizione;
	}

	public String getTiDebito() {
		return tiDebito;
	}

	public void setTiDebito(String tiDebito) {
		this.tiDebito = tiDebito;
	}

	public String getIdPendenza() {
		return idPendenza;
	}

	public void setIdPendenza(String idPendenza) {
		this.idPendenza = idPendenza;
	}

	public boolean isSpontaneo() {
		// TODO PAZZIK implementare
		return false;
	}

	public Integer getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(Integer idPagamento) {
		this.idPagamento = idPagamento;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public Date getDecorrenza() {
		return decorrenza;
	}

	public void setDecorrenza(Date decorrenza) {
		this.decorrenza = decorrenza;
	}

	public String getIdTributoEnte() {
		return idTributoEnte;
	}

	public void setIdTributoEnte(String idTributoEnte) {
		this.idTributoEnte = idTributoEnte;
	}

	public String getTipoSpontaneo() {
		return tipoSpontaneo;
	}

	public void setTipoSpontaneo(String tipoSpontaneo) {
		this.tipoSpontaneo = tipoSpontaneo;
	}

	public String getIdTributo() {
		return idTributo;
	}

	public void setIdTributo(String idTributo) {
		this.idTributo = idTributo;
	}

	public String getIdEnte() {
		return idEnte;
	}

	public void setIdEnte(String idEnte) {
		this.idEnte = idEnte;
	}

	public String getIdFiscaleEnte() {
		return idFiscaleEnte;
	}

	public void setIdFiscaleEnte(String idFiscaleEnte) {
		this.idFiscaleEnte = idFiscaleEnte;
	}

	public Object getSpontaneoVO() {
		return spontaneoVO;
	}

	public void setSpontaneoVO(Object spontaneoVO) {
		this.spontaneoVO = spontaneoVO;
	}

	public void setIdPagamentoEnte(String idPagamento) {
		idPagamentoEnte = idPagamento;
		
	}

	public String getIdPagamentoEnte() {
		return idPagamentoEnte;
	}

	
	
	public String getCausaleDecorata() {
		return causaleDecorata;
	}

	public void setCausaleDecorata(String causaleDecorata) {
		this.causaleDecorata = causaleDecorata;
	}

	
	public boolean isCartellaPagamento() {
		return cartellaPagamento;
	}

	public void setCartellaPagamento(boolean cartellaPagamento) {
		this.cartellaPagamento = cartellaPagamento;
	}

	public int getNumeroPagamentiCartella() {
		return numeroPagamentiCartella;
	}

	public void setNumeroPagamentiCartella(int numeroPagamentiCartella) {
		this.numeroPagamentiCartella = numeroPagamentiCartella;
	}

	public int getProgressivoPagamentoCartella() {
		return progressivoPagamentoCartella;
	}

	public void setProgressivoPagamentoCartella(int progressivoPagamentoCartella) {
		this.progressivoPagamentoCartella = progressivoPagamentoCartella;
	}

	public String getIdCondizioniCartella() {
		return idCondizioniCartella;
	}

	public void setIdCondizioniCartella(String idCondizioniCartella) {
		this.idCondizioniCartella = idCondizioniCartella;
	}

	public List<BigDecimal> getImportiCartella() {
		return importiCartella;
	}

	public void setImportiCartella(List<BigDecimal> importiCartella) {
		this.importiCartella = importiCartella;
	}

	@Override
	public String getDebtor() {
		
		return codFiscaleDebitore;
	}

	@Override
	public void setDebtor(String debtor) {
		
		setCodFiscaleDebitore(debtor);
		
	}

	public String getCausaleCondizione() {
		
		return causaleCondizione;
		
	}

	public void setCausaleCondizione(String causaleCondizione) {
		
		this.causaleCondizione = causaleCondizione;
		
	}

	public EnumStatoPagamentoCondizione getStatoPagamentoCondizione() {
		
		return statoPagamentoCondizione;
	}

	public void setStatoPagamentoCondizione(EnumStatoPagamentoCondizione statoPagamentoCondizione) {
		
		this.statoPagamentoCondizione = statoPagamentoCondizione;
		
	}

	public boolean isDaPagare(){
		
		return getStatoPagamentoCondizione().equals(EnumStatoPagamentoCondizione.DA_PAGARE);
				
	}
	
	public boolean isInCorso(){
		
		return getStatoPagamentoCondizione().equals(EnumStatoPagamentoCondizione.IN_CORSO);
				
	}

	public String getIdDistinta() {
		return idDistinta;
	}

	public void setIdDistinta(String idDistinta) {
		this.idDistinta = idDistinta;
	}

	public String getIbanBeneficiario() {
		return ibanBeneficiario;
	}

	public void setIbanBeneficiario(String ibanBeneficiario) {
		this.ibanBeneficiario = ibanBeneficiario;
	}

	public String getIbanAppoggio() {
		return ibanAppoggio;
	}

	public void setIbanAppoggio(String ibanAppoggio) {
		this.ibanAppoggio = ibanAppoggio;
	}

	public String getIdMittentePend() {
		return idMittentePend;
	}

	public void setIdMittentePend(String idMittentePend) {
		this.idMittentePend = idMittentePend;
	}

	public String getDeMittentePend() {
		return deMittentePend;
	}

	public void setDeMittentePend(String deMittentePend) {
		this.deMittentePend = deMittentePend;
	}

		@Override
	public String getItemOpposizione730() {
		return itemOpposizione730;
	}

	@Override
	public void setItemOpposizione730(String itemOpposizione730) {
		this.itemOpposizione730 = itemOpposizione730;
	}
	
	public boolean isCategoria013(){
    	return "Categoria013".equals(idTributo);
    }

	@Override
	public String getEmailVers() {
		
		return emailVers;
	}

	@Override
	public void setEmailVers(String emailVers) {
		this.emailVers=emailVers;
		
	}

	@Override
	public String getCodFiscaleVers() {
		return codFiscaleVers;
	}

	@Override
	public void setCodFiscaleVers(String codFiscaleVers) {
		this.codFiscaleVers=codFiscaleVers;
	}

	@Override
	public String getTipoSoggettoVers() {
		// TODO Auto-generated method stub
		return tipoSoggettoVers;
	}

	@Override
	public void setTipoSoggettoVers(String tipoSoggettoVers) {
		this.tipoSoggettoVers=tipoSoggettoVers;
		
	}

	@Override
	public String getAnagraficaVers() {
		return this.anagraficaVers;
	}

	@Override
	public void setAnagraficaVers(String anagraficaVers) {
		this.anagraficaVers=anagraficaVers;
		
	}

	@Override
	public String getIndirizzoVers() {
		
		return indirizzoVers;
	}

	@Override
	public void setIndirizzoVers(String indirizzoVers) {
		this.indirizzoVers=indirizzoVers;
		
	}

	@Override
	public String getNumeroCivicoVers() {		
		return this.numeroCivicoVers;
	}

	@Override
	public void setNumeroCivicoVers(String numeroCivicoVers) {
		this.numeroCivicoVers=numeroCivicoVers;
		
	}

	@Override
	public String getCapVers() {		
		return this.capVers;
	}

	@Override
	public void setCapVers(String capVers) {
		this.capVers=capVers;		
	}

	@Override
	public String getLocalitaVers() {		
		return localitaVers;
	}

	@Override
	public void setLocalitaVers(String localitaVers) {
		this.localitaVers=localitaVers;		
	}

	@Override
	public String getProvinciaVers() {		
		return provinciaVers;
	}

	@Override
	public void setProvinciaVers(String provinciaVers) {
		this.provinciaVers=provinciaVers;
		
	}

	@Override
	public String getNazioneVers() {		
		return nazioneVers;
	}

	@Override
	public void setNazioneVers(String nazioneVers) {
		this.nazioneVers=nazioneVers;
		
	}

	public String getIndirizzoDebitore() {
		return indirizzoDebitore;
	}

	public void setIndirizzoDebitore(String indirizzoDebitore) {
		this.indirizzoDebitore = indirizzoDebitore;
	}

	public String getNumeroCivicoDebitore() {
		return numeroCivicoDebitore;
	}

	public void setNumeroCivicoDebitore(String numeroCivicoDebitore) {
		this.numeroCivicoDebitore = numeroCivicoDebitore;
	}

	public String getCapDebitore() {
		return capDebitore;
	}

	public void setCapDebitore(String capDebitore) {
		this.capDebitore = capDebitore;
	}

	public String getLocalitaDebitore() {
		return localitaDebitore;
	}

	public void setLocalitaDebitore(String localitaDebitore) {
		this.localitaDebitore = localitaDebitore;
	}

	public String getProvinciaDebitore() {
		return provinciaDebitore;
	}

	public void setProvinciaDebitore(String provinciaDebitore) {
		this.provinciaDebitore = provinciaDebitore;
	}

	public String getNazioneDebitore() {
		return nazioneDebitore;
	}

	public void setNazioneDebitore(String nazioneDebitore) {
		this.nazioneDebitore = nazioneDebitore;
	}

	public String getEmailDebitore() {
		return emailDebitore;
	}

	public void setEmailDebitore(String emailDebitore) {
		this.emailDebitore = emailDebitore;
	}

	public String getTipoSoggettoDebitore() {
		return tipoSoggettoDebitore;
	}

	public void setTipoSoggettoDebitore(String tipoSoggettoDebitore) {
		this.tipoSoggettoDebitore = tipoSoggettoDebitore;
	}

	public String getAnagraficaDebitore() {
		return anagraficaDebitore;
	}

	public void setAnagraficaDebitore(String anagraficaDebitore) {
		this.anagraficaDebitore = anagraficaDebitore;
	}

	public String getDatiSpecificiRiscossione() {
		return datiSpecificiRiscossione;
	}

	public void setDatiSpecificiRiscossione(String datiSpecificiRiscossione) {
		this.datiSpecificiRiscossione = datiSpecificiRiscossione;
	}

	public String getMarcaBolloTipoBollo() {
		return marcaBolloTipoBollo;
	}

	public void setMarcaBolloTipoBollo(String marcaBolloTipoBollo) {
		this.marcaBolloTipoBollo = marcaBolloTipoBollo;
	}

	public String getMarcaBolloProvResidenzaDebitore() {
		return marcaBolloProvResidenzaDebitore;
	}

	public void setMarcaBolloProvResidenzaDebitore(String marcaBolloProvResidenzaDebitore) {
		this.marcaBolloProvResidenzaDebitore = marcaBolloProvResidenzaDebitore;
	}

	public String getMarcaBolloHashDocumento() {
		return marcaBolloHashDocumento;
	}

	public void setMarcaBolloHashDocumento(String marcaBolloHashDocumento) {
		this.marcaBolloHashDocumento = marcaBolloHashDocumento;
	}

	public String getTipoCausalePagamento() {
		return tipoCausalePagamento;
	}

	public void setTipoCausalePagamento(String tipoCausalePagamento) {
		this.tipoCausalePagamento = tipoCausalePagamento;
	}

	public String getAuthenticationType() {
		return authenticationType;
	}
	public void setAuthenticationType(String authenticationType) {
		this.authenticationType = authenticationType;
	}

	public String getCdPlugin() {
		return cdPlugin;
	}

	public void setCdPlugin(String cdPlugin) {
		this.cdPlugin = cdPlugin;
	}
		
		/**
		 * true se l'iban proviene dalla condizione (� specificato direttamente sulla condizione) 
		 * @param ibanBeneficiarioCondizione
		 */
		public void setIbanBeneficiarioCondizione(boolean ibanBeneficiarioCondizione) {
				this.ibanBeneficiarioCondizione = ibanBeneficiarioCondizione;
		}
		
		public boolean isIbanBeneficiarioCondizione() {
				return ibanBeneficiarioCondizione;
		}
		
		public boolean isRenderedCartellaPagamento() {
			return isCartellaPagamento() && getProgressivoPagamentoCartella() == 1;
		}
		
		
		public String getIuv() {
			return iuv;
		}

		public void setIuv(String iuv) {
			this.iuv = iuv;
		}

		
}
