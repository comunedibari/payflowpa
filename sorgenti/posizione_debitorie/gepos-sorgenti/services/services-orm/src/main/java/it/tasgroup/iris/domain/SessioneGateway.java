package it.tasgroup.iris.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the SESSIONE_GATEWAY database table.
 * 
 */
@Entity
@Table(name="IRIS_GATEWAY_SESSION")
public class SessioneGateway extends BaseEntity {
	private static final long serialVersionUID = 1L;
	private String token;
	private CfgIrisGatewayClient gtwClient;
	private String opAggiornamento;
	private String opInserimento;
	private String operatore;
	private String intestatario;
	private String sessionId;
	private Integer usata;
	private Timestamp tsAggiornamento;
	private Timestamp tsInserimento;
	private BigDecimal imTotale;
	private String flagOpposizione730;
	private Set<CarrelloGateway> carrelloGateways;
	
	private String urlBack;
	private String urlCancel;
	private Integer offlinePaymentMethods = 1;
	
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
	
	private String redirectOnly;

    public SessioneGateway() {
    }


	@Id
	@Column(name="TOKEN")
	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Column(name="OP_AGGIORNAMENTO")
	public String getOpAggiornamento() {
		return this.opAggiornamento;
	}

	public void setOpAggiornamento(String opAggiornamento) {
		this.opAggiornamento = opAggiornamento;
	}


	@Column(name="OP_INSERIMENTO")
	public String getOpInserimento() {
		return this.opInserimento;
	}

	public void setOpInserimento(String opInserimento) {
		this.opInserimento = opInserimento;
	}


	@Column(name="SESSION_ID")
	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@Column(name="USATA")
	public Integer getUsata() {
		return this.usata;
	}

	public void setUsata(Integer usata) {
		this.usata = usata;
	}
	
	@Transient
	public boolean isUsed() {
		return this.usata > 0;
	}


	@Column(name="TS_AGGIORNAMENTO")
	public Timestamp getTsAggiornamento() {
		return this.tsAggiornamento;
	}

	public void setTsAggiornamento(Timestamp tsAggiornamento) {
		this.tsAggiornamento = tsAggiornamento;
	}


	@Column(name="TS_INSERIMENTO")
	public Timestamp getTsInserimento() {
		return this.tsInserimento;
	}

	public void setTsInserimento(Timestamp tsInserimento) {
		this.tsInserimento = tsInserimento;
	}

	//bi-directional many-to-one association to CarrelloGateway
	@OneToMany(mappedBy="sessioneGateway", cascade={CascadeType.ALL})
	public Set<CarrelloGateway> getCarrelloGateways() {
		return this.carrelloGateways;
	}

	public void setCarrelloGateways(Set<CarrelloGateway> carrelloGateways) {
		this.carrelloGateways = carrelloGateways;
	}
	
	/**
	 * @return the imTotale
	 */
	@Column(name="IM_TOTALE")
	public BigDecimal getImTotale() {
		return imTotale;
	}


	/**
	 * @param imTotale the imTotale to set
	 */
	public void setImTotale(BigDecimal imTotale) {
		this.imTotale = imTotale;
	}

    @Column(name="FLAG_OPPOSIZIONE_730")
    public String getFlagOpposizione730() {
        return flagOpposizione730;
    }

    public void setFlagOpposizione730(String flagOpposizione730) {
        this.flagOpposizione730 = flagOpposizione730;
    }


	/**
	 * @return the operatore
	 */
	@Column(name="OPERATORE")
	public String getOperatore() {
		return operatore;
	}


	/**
	 * @param operatore the operatore to set
	 */
	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}


	/**
	 * @return the intestatario
	 */
	@Column(name="INTESTATARIO")
	public String getIntestatario() {
		return intestatario;
	}


	/**
	 * @param intestatario the intestatario to set
	 */
	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}


	/**
	 * @return the gtwClient
	 */
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "GTW_CLIENT_ID")
	public CfgIrisGatewayClient getCfgIrisGatewayClient() {
		return gtwClient;
	}


	/**
	 * @param gtwClient the gtwClient to set
	 */
	public void setCfgIrisGatewayClient(CfgIrisGatewayClient gtwClient) {
		this.gtwClient = gtwClient;
	}


	@Column(name="URL_BACK")
	public String getUrlBack() {
		return urlBack;
	}


	public void setUrlBack(String urlBack) {
		this.urlBack = urlBack;
	}


	@Column(name="URL_CANCEL")
	public String getUrlCancel() {
		return urlCancel;
	}


	public void setUrlCancel(String urlCancel) {
		this.urlCancel = urlCancel;
	}


	@Column(name="OFFLINE_PAYMENT_METHODS")
	public Integer getOfflinePaymentMethods() {
		return offlinePaymentMethods;
	}


	public void setOfflinePaymentMethods(Integer offlinePaymentMethods) {
		this.offlinePaymentMethods = offlinePaymentMethods;
	}
	
	@Column(name="TIPO_SOGGETTO_VERS")
	public String getTipoSoggettoVers() {
		return tipoSoggettoVers;
	}


	public void setTipoSoggettoVers(String tipoSoggettoVers) {
		this.tipoSoggettoVers = tipoSoggettoVers;
	}

	@Column(name="COD_FISCALE_VERS")
	public String getCodFiscaleVers() {
		return codFiscaleVers;
	}


	public void setCodFiscaleVers(String codFiscaleVers) {
		this.codFiscaleVers = codFiscaleVers;
	}

	@Column(name="ANAGRAFICA_VERS")
	public String getAnagraficaVers() {
		return anagraficaVers;
	}


	public void setAnagraficaVers(String anagraficaVers) {
		this.anagraficaVers = anagraficaVers;
	}

	@Column(name="INDIRIZZO_VERS")
	public String getIndirizzoVers() {
		return indirizzoVers;
	}


	public void setIndirizzoVers(String indirizzoVers) {
		this.indirizzoVers = indirizzoVers;
	}

	@Column(name="NUMERO_CIVICO_VERS")
	public String getNumeroCivicoVers() {
		return numeroCivicoVers;
	}


	public void setNumeroCivicoVers(String numeroCivicoVers) {
		this.numeroCivicoVers = numeroCivicoVers;
	}

	@Column(name="CAP_VERS")
	public String getCapVers() {
		return capVers;
	}


	public void setCapVers(String capVers) {
		this.capVers = capVers;
	}

	@Column(name="LOCALITA_VERS")
	public String getLocalitaVers() {
		return localitaVers;
	}


	public void setLocalitaVers(String localitaVers) {
		this.localitaVers = localitaVers;
	}

	@Column(name="PROVINCIA_VERS")
	public String getProvinciaVers() {
		return provinciaVers;
	}


	public void setProvinciaVers(String provinciaVers) {
		this.provinciaVers = provinciaVers;
	}

	@Column(name="NAZIONE_VERS")
	public String getNazioneVers() {
		return nazioneVers;
	}


	public void setNazioneVers(String nazioneVers) {
		this.nazioneVers = nazioneVers;
	}

	@Column(name="EMAIL_VERS")
	public String getEmailVers() {
		return emailVers;
	}


	public void setEmailVers(String emailVers) {
		this.emailVers = emailVers;
	}

	@Column(name="REDIRECT_ONLY")
	public String getRedirectOnly() {
		return redirectOnly;
	}


	public void setRedirectOnly(String redirectOnly) {
		this.redirectOnly = redirectOnly;
	}

	@Transient
	public Boolean isToRedirect() {
		if(redirectOnly != null && redirectOnly.equals("Y"))
			return new Boolean(true);
		else
			return new Boolean(false);
	}
	
	public void setToRedirect(Boolean toRedirect) {
		// do nothing.
	}
}