/**
 * 
 */
package it.tasgroup.iris.dto.gateway;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author pazzik
 *
 */
public class SessioneGatewayDTO implements Serializable{ 
	private String token;	
	private String sessionId;
	private String intestatario;
	private String operatore;
	private Integer usata;
	private BigDecimal imTotale;
	private List<CartItemDTO> cartItemDTOs;
	private IrisGatewayClientDTO irisGTWClient;

	private String urlBack;
	private String urlCancel;
	private Integer offlinePaymentMethods;
	
	private String flagOpposizione730;

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
	
	private static final String IS_REDIRECT_ONLY = "Y";
	
	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}
	/**
	 * @param token the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the usata
	 */
	public Integer getUsata() {
		return usata;
	}
	/**
	 * @param usata the usata to set
	 */
	public void setUsata(Integer usata) {
		this.usata = usata;
	}
	/**
	 * @return the imTotale
	 */
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
	 * @return the flagOpposizione730
	 */
	public String getFlagOpposizione730() {
		return flagOpposizione730;
	}
	/**
	 * @param imTotale the flagOpposizione730 to set
	 */
	public void setFlagOpposizione730(String flagOpposizione730) {
		this.flagOpposizione730 = flagOpposizione730;
	}
	/**
	 * @return the carrelloGateways
	 */
	public List<CartItemDTO> getCartItemDTOs() {
		return cartItemDTOs;
	}
	/**
	 * @param cartItemDTOs the carrelloGateways to set
	 */
	public void setCartItemDTOs(List<CartItemDTO> cartItemDTOs) {
		this.cartItemDTOs = cartItemDTOs;
	}
	
	public IrisGatewayClientDTO getIrisGTWClient() {
		return irisGTWClient;
	}
	
	public void setIrisGTWClient(IrisGatewayClientDTO irisGTWClient) {
		this.irisGTWClient = irisGTWClient;
	}
	
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	public String getIntestatario() {
		return intestatario;
	}
	public void setIntestatario(String intestatario) {
		this.intestatario = intestatario;
	}
	public String getOperatore() {
		return operatore;
	}
	public void setOperatore(String operatore) {
		this.operatore = operatore;
	}
	public String getUrlBack() {
		return urlBack;
	}
	public void setUrlBack(String urlBack) {
		this.urlBack = urlBack;
	}
	public String getUrlCancel() {
		return urlCancel;
	}
	public void setUrlCancel(String urlCancel) {
		this.urlCancel = urlCancel;
	}
	public Integer getOfflinePaymentMethods() {
		return offlinePaymentMethods;
	}
	public void setOfflinePaymentMethods(Integer offlinePaymentMethods) {
		this.offlinePaymentMethods = offlinePaymentMethods;
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
	public String getEmailVers() {
		return emailVers;
	}
	public void setEmailVers(String emailVers) {
		this.emailVers = emailVers;
	}
	public String getRedirectOnly() {
		return redirectOnly;
	}
	public void setRedirectOnly(String redirectOnly) {
		this.redirectOnly = redirectOnly;
	}
	
	public Boolean isToRedirect() {
		if (this.redirectOnly != null && this.redirectOnly.equals(IS_REDIRECT_ONLY) )
			return new Boolean(true);
		else
			return new Boolean(false);
	}
	
}
