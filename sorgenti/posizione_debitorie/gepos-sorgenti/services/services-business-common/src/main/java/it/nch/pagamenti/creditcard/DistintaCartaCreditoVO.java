/*
 * Created on 9-giu-09
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.pagamenti.creditcard;

import it.nch.is.fo.stati.pagamenti.StatiPagamentiIris;
import it.nch.pagamenti.MezzoDiPagamentoVO;
import it.tasgroup.services.util.enumeration.EnumTipoModalitaPagamento;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
/**
 * @author Simone
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DistintaCartaCreditoVO implements Serializable {
	
	private List disposizioni = new ArrayList();
	private BigDecimal totale = new BigDecimal(0);

	private String tipoCarta;
	private String numeroCarta;
	private String codiceSicurezza;
	private String meseScadenza;
	private String annoScadenza;
	private BigDecimal totImportiPositivi;
	private BigDecimal importoCommissioni;
	private String idPendenzaEnte;
	private Number idFlusso;
    private String idFiscaleCreditore;
	/**
	 * Forma di pagamento che arriva dal form di scelta pagamento: CREDICART, RID.  
	 * @see {@link MezzoDiPagamentoVO}
	 */
	private Number mezzoPagamentoKey;
	private String mezzoPagamento;
    private String causaleDistinta;
    private String idCfgGateway;

    private String emailVersante;

    //Parametri che arrivano(servono) dal(al) gateway PPG
    
    //token tornato dalla prima chiamata alla banca
	private String token;
	private String flusso;
    //campo op_nmsg di ritorno dal negozio (CO_NOP tabella MPCC)
	private String coNop;
	//Contine la stringa che poi viene utilizzata dal Decorator
	private String esitoPagamento;
	//Stato del pagamento con il risultato della query al negozio.
	private StatiPagamentiIris statoPagamento;
	// autorizzazione della transazione di pagamento, valorizzato in caso di esito positivo della stessa
	private String idAutorizzazione;
	// autorizzazione della transazione di pagamento, valorizzato in caso di esito positivo della stessa
	private String codTransazione;
	private String codTransazionePSP;
	
	private String IUV;

	// codice pagamento da mettere nelle ricevute
	private String codPagamento;
	
	// Forma di pagamento [ 0-Carta di credito ; 1-Bonifico ] usato per il gateway BT
	private String formaPagamento;
	//campo op_data in ritorno dal negozio
	private Date dataTransazione;
	//per ora � costante
    private String canaleRichiesta = "Web";
    
    // legame carrello
    private String idDistinta;
    private Date dataOrdine;

    private String forwardMapping;
    
    private String idGruppo;
    
    private boolean associatedDocAvailable=true;
	
    //
    private String tipoSoggettoVers; 
	private String codFiscaleVers; 
	private String anagraficaVers;
	private String indirizzoVers; 
	private String numeroCivicoVers; 
	private String capVers; 
	private String localitaVers; 
	private String provinciaVers; 
	private String nazioneVers;
	private String locale;
    
	public Date getDataOrdine() {
		return dataOrdine;
	}

	public void setDataOrdine(Date dataOrdine) {
		this.dataOrdine = dataOrdine;
	}

	public String getIdDistinta() {
		return idDistinta;
	}

	public void setIdDistinta(String idDistinta) {
		this.idDistinta = idDistinta;
	}

    public String getCausaleDistinta() {
		return causaleDistinta;
	}

	public void setCausaleDistinta(String causaleDistinta) {
		this.causaleDistinta = causaleDistinta;
	}

	public String getMezzoPagamento() {
		return mezzoPagamento;
	}

	public void setMezzoPagamento(String mezzoPagamento) {
		this.mezzoPagamento = mezzoPagamento;
	}
    
	public String getCanaleRichiesta() {
		return canaleRichiesta;
	}

	public void setCanaleRichiesta(String canaleRichiesta) {
		this.canaleRichiesta = canaleRichiesta;
	}

	public String getCoNop() {
		return coNop;
	}

	public void setCoNop(String coNop) {
		this.coNop = coNop;
	}
	public String getIdAutorizzazione() {
		return idAutorizzazione;
	}

	public void setIdAutorizzazione(String idAutorizzazione) {
		this.idAutorizzazione = idAutorizzazione;
	}

	public String getFormaPagamento() {
		if(this.mezzoPagamento.equals(EnumTipoModalitaPagamento.CARTADICREDITO.getChiave())) {
			this.formaPagamento = "0"; // 2 --> 0
		}else if (this.mezzoPagamento.equals(EnumTipoModalitaPagamento.BONIFICOONLINE.getChiave()))  {
			this.formaPagamento = "1"; // 5 --> 1
		}
		return this.formaPagamento;
	}

	public Date getDataTransazione() {
		return dataTransazione;
	}

	public void setDataTransazione(Date dataOraTransazione) {
		this.dataTransazione = dataOraTransazione;
	}	
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return
	 */
	public List<DisposizioneCartaCreditoVO> getDisposizioni() {
		return disposizioni;
	}

	/**
	 * @param list
	 */
	public void setDisposizioni(List list) {
		disposizioni = list;
	}

	/**
	 * @param o
	 * @return
	 */
	public boolean addDisposizione(DisposizioneCartaCreditoVO disposizione) {
		disposizione.setDistinta(this);
		return disposizioni.add(disposizione);
	}

	/**
	 * @return
	 */
	public BigDecimal getTotale() {
		return totale;
	}

	/**
	 * @param decimal
	 */
	public void setTotale(BigDecimal decimal) {
		totale = decimal;
	}

	/**
	 * @return
	 */
	public String getAnnoScadenza() {
		return annoScadenza;
	}

	/**
	 * @return
	 */
	public String getCodiceSicurezza() {
		return codiceSicurezza;
	}

	/**
	 * @return
	 */
	public String getMeseScadenza() {
		return meseScadenza;
	}

	/**
	 * @return
	 */
	public String getNumeroCarta() {
		return numeroCarta;
	}

	/**
	 * @return
	 */
	public String getTipoCarta() {
		return tipoCarta;
	}

	/**
	 * @param string
	 */
	public void setAnnoScadenza(String string) {
		annoScadenza = string;
	}

	/**
	 * @param string
	 */
	public void setCodiceSicurezza(String string) {
		codiceSicurezza = string;
	}

	/**
	 * @param string
	 */
	public void setMeseScadenza(String string) {
		meseScadenza = string;
	}

	/**
	 * @param string
	 */
	public void setNumeroCarta(String string) {
		numeroCarta = string;
	}

	/**
	 * @param string
	 */
	public void setTipoCarta(String string) {
		tipoCarta = string;
	}

	/**
	 * @return
	 */
	public String getFlusso() {
		return flusso;
	}

	/**
	 * @param string
	 */
	public void setFlusso(String string) {
		flusso = string;
	}

	/**
	 * @return
	 */
	public String getEsitoPagamento() {
		if(esitoPagamento == null && statoPagamento != null)
			return statoPagamento.getFludMapping();
		return esitoPagamento;
	}

	/**
	 * @param string
	 */
	public void setEsitoPagamento(String esito) {
		esitoPagamento = esito;
	}

	/**
	 * @return
	 */
	public StatiPagamentiIris getStatoPagamento() {
		return statoPagamento;
	}
	
	/**
	 * @param StatiIris
	 */
	public void setStatoPagamento(StatiPagamentiIris statoPagamento) {
		this.statoPagamento = statoPagamento;
	}

	public void setStatoPagamentoString(String statoPagamentoString) {
		this.statoPagamento = StatiPagamentiIris.getStatiPagamentiIrisFromFlud(statoPagamentoString);
	}

	/**
	 * Importo formattato con Locale USA
	 * @return
	 */
	public String getTotImportiPositiviFormatted(){
		DecimalFormat f = (DecimalFormat)NumberFormat.getNumberInstance(Locale.US);
		f.applyPattern("#0.00");
		return f.format(this.totImportiPositivi);
	}
	
	public BigDecimal getTotImportiPositivi() {
		return totImportiPositivi;
	}

	public void setTotImportiPositivi(BigDecimal totImportiPositivi) {
		this.totImportiPositivi = totImportiPositivi;
	}

	public BigDecimal getImportoCommissioni() {
		return importoCommissioni;
	}

	public void setImportoCommissioni(BigDecimal importoCommissioni) {
		this.importoCommissioni = importoCommissioni;
	}

	public String getIdPendenzaEnte() {
		return idPendenzaEnte;
	}

	public void setIdPendenzaEnte(String idPendenzaEnte) {
		this.idPendenzaEnte = idPendenzaEnte;
	}

	public String getIdCfgGateway() {
		return idCfgGateway;
	}

	public void setIdCfgGateway(String idCfgGateway) {
		this.idCfgGateway = idCfgGateway;
	}

	public void setForwardMapping(String forwardMapping) {
		this.forwardMapping = forwardMapping;
	}

	public String getForwardMapping() {
		return forwardMapping;
	}
	
	public String getCodTransazione() {
		return codTransazione;
	}

	public void setCodTransazione(String codTransazione) {
		this.codTransazione = codTransazione;
	}
	
	public Number getIdFlusso() {
		return idFlusso;
	}

	public String getCodTransazionePSP() {
		return codTransazionePSP;
	}

	public void setCodTransazionePSP(String codTransazionePSP) {
		this.codTransazionePSP = codTransazionePSP;
	}

	public void setIdFlusso(Number idFlusso) {
		this.idFlusso = idFlusso;
		this.flusso = idFlusso.toString();
	}

	public Number getMezzoPagamentoKey() {
		return mezzoPagamentoKey;
	}

	public void setMezzoPagamentoKey(Number mezzoPagamentoKey) {
		this.mezzoPagamentoKey = mezzoPagamentoKey;
	}

	public String getCodPagamento() {
		return codPagamento;
	}

	public void setCodPagamento(String codPagamento) {
		this.codPagamento = codPagamento;
	}

	public String getEmailVersante() {
		return emailVersante;
	}

	public void setEmailVersante(String emailVersante) {
		this.emailVersante = emailVersante;
	}

	public String getIdFiscaleCreditore() {
		return idFiscaleCreditore;
	}

	public void setIdFiscaleCreditore(String idFiscaleCreditore) {
		this.idFiscaleCreditore = idFiscaleCreditore;
	}

	public String getIdGruppo() {
		return idGruppo;
	}

	public void setIdGruppo(String idGruppo) {
		this.idGruppo = idGruppo;
	}

	public boolean isAssociatedDocAvailable() {
		return associatedDocAvailable;
	}

	public void setAssociatedDocAvailable(boolean associatedDocAvailable) {
		this.associatedDocAvailable = associatedDocAvailable;
	}

	public String getTipoSoggettoVers() {
		return tipoSoggettoVers;
	}

	public void setTipoSoggettoVers(String tipoSoggettoVers) {
		this.tipoSoggettoVers = tipoSoggettoVers;
	}

	public String getCodFiscaleVers() {
		return codFiscaleVers;
	}

	public void setCodFiscaleVers(String codFiscaleVers) {
		this.codFiscaleVers = codFiscaleVers;
	}

	public String getAnagraficaVers() {
		return anagraficaVers;
	}

	public void setAnagraficaVers(String anagraficaVers) {
		this.anagraficaVers = anagraficaVers;
	}

	public String getIndirizzoVers() {
		return indirizzoVers;
	}

	public void setIndirizzoVers(String indirizzoVers) {
		this.indirizzoVers = indirizzoVers;
	}

	public String getNumeroCivicoVers() {
		return numeroCivicoVers;
	}

	public void setNumeroCivicoVers(String numeroCivicoVers) {
		this.numeroCivicoVers = numeroCivicoVers;
	}

	public String getCapVers() {
		return capVers;
	}

	public void setCapVers(String capVers) {
		this.capVers = capVers;
	}

	public String getLocalitaVers() {
		return localitaVers;
	}

	public void setLocalitaVers(String localitaVers) {
		this.localitaVers = localitaVers;
	}

	public String getProvinciaVers() {
		return provinciaVers;
	}

	public void setProvinciaVers(String provinciaVers) {
		this.provinciaVers = provinciaVers;
	}

	public String getNazioneVers() {
		return nazioneVers;
	}

	public void setNazioneVers(String nazioneVers) {
		this.nazioneVers = nazioneVers;
	}
	
	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getIUV() {
		return IUV;
	}

	public void setIUV(String iUV) {
		IUV = iUV;
	}
	
	
}
