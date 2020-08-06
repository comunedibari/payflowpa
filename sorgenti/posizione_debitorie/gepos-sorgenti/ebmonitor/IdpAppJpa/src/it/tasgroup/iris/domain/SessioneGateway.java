package it.tasgroup.iris.domain;

import it.tasgroup.idp.domain.BaseEntity;

import java.math.BigDecimal;
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
import javax.persistence.Version;


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
	
	private String operatore;
	private String intestatario;
	private String sessionId;
	private Integer usata;
	private BigDecimal imTotale;
	private Set<CarrelloGateway> carrelloGateways;
	
	private String urlBack;
	private String urlCancel;
	private int offlinePaymentMethods = 1;
	
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
	
	/*** Check Property Optimistic Locking***/
	private Long version;
	
	/**not mapped by column**/
	private String e2eMsgId;
	private String senderId;
	private String senderSys;
	

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
	public int getOfflinePaymentMethods() {
		return offlinePaymentMethods;
	}


	public void setOfflinePaymentMethods(int offlinePaymentMethods) {
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


	@Version
	public Long getVersion() {
		return this.version;
	}
	public void setVersion(Long version) {
		this.version = version;
	}
	
	
	@Transient
	public String getE2eMsgId() {
		return e2eMsgId;
	}


	public void setE2eMsgId(String e2eMsgId) {
		this.e2eMsgId = e2eMsgId;
	}

	@Transient
	public String getSenderId() {
		return senderId;
	}


	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	@Transient
	public String getSenderSys() {
		return senderSys;
	}


	public void setSenderSys(String senderSys) {
		this.senderSys = senderSys;
	}	
	
}