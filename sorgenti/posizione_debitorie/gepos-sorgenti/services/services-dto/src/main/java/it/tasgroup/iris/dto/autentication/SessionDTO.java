package it.tasgroup.iris.dto.autentication;

import java.io.Serializable;
import java.util.Date;

public class SessionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sessionId;
	private String corporate;
	private String corporateBankingChannel;
	private String officialLanguage;
	private String userName;
	private Date lastTransactionTime;
	private Date connectionTime;
	private String ipAddress;
	private String certificato;
	private String mac;
	private String password;
	private String stato;
	private String abi;
	private String indirizzo;
	private String comune;
	private String provincia;
	private String cap;
	private String codiceFiscale;
	private String lapl;
	private String sia;
	private String tipoIntestatario;
	private String abiAzienda;
	private String cab;
	private String abiAccentratore;
	private String ragioneSocialeAzienda;
	private String tipoSicurezza;
	private Double importoMax;
	private String selCorporate;

	public String getSessionId() {
		return this.sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getCorporate() {
		return this.corporate;
	}

	public void setCorporate(String corporate) {
		this.corporate = corporate;
	}

	public String getCorporateBankingChannel() {
		return this.corporateBankingChannel;
	}

	public void setCorporateBankingChannel(String corporateBankingChannel) {
		this.corporateBankingChannel = corporateBankingChannel;
	}

	public String getOfficialLanguage() {
		return this.officialLanguage;
	}

	public void setOfficialLanguage(String officialLanguage) {
		this.officialLanguage = officialLanguage;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getLastTransactionTime() {
		return this.lastTransactionTime;
	}

	public void setLastTransactionTime(Date lastTransactionTime) {
		this.lastTransactionTime = lastTransactionTime;
	}

	public Date getConnectionTime() {
		return this.connectionTime;
	}

	public void setConnectionTime(Date connectionTime) {
		this.connectionTime = connectionTime;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getCertificato() {
		return this.certificato;
	}

	public void setCertificato(String certificato) {
		this.certificato = certificato;
	}

	public String getMac() {
		return this.mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getStato() {
		return this.stato;
	}

	public void setStato(String stato) {
		this.stato = stato;
	}

	public String getAbi() {
		return this.abi;
	}

	public void setAbi(String abi) {
		this.abi = abi;
	}

	public String getIndirizzo() {
		return this.indirizzo;
	}

	public void setIndirizzo(String indirizzo) {
		this.indirizzo = indirizzo;
	}

	public String getComune() {
		return this.comune;
	}

	public void setComune(String comune) {
		this.comune = comune;
	}

	public String getProvincia() {
		return this.provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCap() {
		return this.cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}

	public String getCodiceFiscale() {
		return this.codiceFiscale;
	}

	public void setCodiceFiscale(String codiceFiscale) {
		this.codiceFiscale = codiceFiscale;
	}

	public String getLapl() {
		return this.lapl;
	}

	public void setLapl(String lapl) {
		this.lapl = lapl;
	}

	public String getSia() {
		return this.sia;
	}

	public void setSia(String sia) {
		this.sia = sia;
	}

	public String getTipoIntestatario() {
		return this.tipoIntestatario;
	}

	public void setTipoIntestatario(String tipoIntestatario) {
		this.tipoIntestatario = tipoIntestatario;
	}

	public String getAbiAzienda() {
		return this.abiAzienda;
	}

	public void setAbiAzienda(String abiAzienda) {
		this.abiAzienda = abiAzienda;
	}

	public String getCab() {
		return this.cab;
	}

	public void setCab(String cab) {
		this.cab = cab;
	}

	public String getAbiAccentratore() {
		return this.abiAccentratore;
	}

	public void setAbiAccentratore(String abiAccentratore) {
		this.abiAccentratore = abiAccentratore;
	}

	public String getRagioneSocialeAzienda() {
		return this.ragioneSocialeAzienda;
	}

	public void setRagioneSocialeAzienda(String ragioneSocialeAzienda) {
		this.ragioneSocialeAzienda = ragioneSocialeAzienda;
	}

	public String getTipoSicurezza() {
		return this.tipoSicurezza;
	}

	public void setTipoSicurezza(String tipoSicurezza) {
		this.tipoSicurezza = tipoSicurezza;
	}

	public Double getImportoMax() {
		return this.importoMax;
	}

	public void setImportoMax(Double importoMax) {
		this.importoMax = importoMax;
	}

	public String getSelCorporate() {
		return this.selCorporate;
	}

	public void setSelCorporate(String selCorporate) {
		this.selCorporate = selCorporate;
	}

}
