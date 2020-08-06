package it.tasgroup.idp.domain.rid;

public class AutorizzazioneRidModelPrinter {
	
	//RECORD TESTA
	
	private String codiceSiaRt; //Mittente
	
	private String codiceAbiBT; //Ricevente - Usata anche in  Record 12 come  Abi Banca Allineamento
	
	private String dataCreazione; //Current Time Stamp - Usata anche in Record 12
	
	private String nomeSupporto; //Unique 20
	
	
	//RECORD 12
	
	private String numeroProgressivo; //Usato per tutti i Record
	
	private String causale;
	
	private String codPaeseAddebito;
	
	private String checkDigitAddebito;
	
	//ABI BANCA ALLINEAMENTO RECORD 12 UTILIZZA AL MOMENTO CODICE ABI BT DI RECORD TEST - 01030
	
	private String cinAddebito;
	
	private String abiAddebito;
	
	private String cabAddebito;
	
	private String numeroCcAddebito;
	
	private String siaCreditore;
	
	private String tipoCodIndividuale;
	
	private String codIndividuale;
	
	//RECORD 30
	
	private String ragSocSott_indSott;
	
	private String idFiscaleSottoscrittore;
	
	//RECORD 40
	
	private String ragSocIntAddebito;
	
	//RECORD 70
	
	private String codRiferimento;
	
	//DA DOCUMENTO TAS NON VALORIZZARE:
	//Numero Rate
	//Importo Massimo
	//Scadenza Prima Rata
	//Scadenza Ultima Rata
	
	private String flagStorno;
	
	//RECORD CODA
	
	private String numeroRecord;
	
	
	
	
	
	//METODI GETTERS AND SETTERS------------------------------
	
	public String getCodiceAbiBT() {
		return codiceAbiBT;
	}

	public void setCodiceAbiBT(String codiceAbiBT) {
		this.codiceAbiBT = codiceAbiBT;
	}

	public String getDataCreazione() {
		return dataCreazione;
	}

	public void setDataCreazione(String dataCreazione) {
		this.dataCreazione = dataCreazione;
	}

	public String getNomeSupporto() {
		return nomeSupporto;
	}

	public void setNomeSupporto(String nomeSupporto) {
		this.nomeSupporto = nomeSupporto;
	}

	public String getNumeroProgressivo() {
		return numeroProgressivo;
	}

	public void setNumeroProgressivo(String numeroProgressivo) {
		this.numeroProgressivo = numeroProgressivo;
	}

	public String getCausale() {
		return causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}

	public String getCodPaeseAddebito() {
		return codPaeseAddebito;
	}

	public void setCodPaeseAddebito(String codPaeseAddebito) {
		this.codPaeseAddebito = codPaeseAddebito;
	}

	public String getCheckDigitAddebito() {
		return checkDigitAddebito;
	}

	public void setCheckDigitAddebito(String checkDigitAddebito) {
		this.checkDigitAddebito = checkDigitAddebito;
	}

	public String getCinAddebito() {
		return cinAddebito;
	}

	public void setCinAddebito(String cinAddebito) {
		this.cinAddebito = cinAddebito;
	}

	public String getAbiAddebito() {
		return abiAddebito;
	}

	public void setAbiAddebito(String abiAddebito) {
		this.abiAddebito = abiAddebito;
	}

	public String getCabAddebito() {
		return cabAddebito;
	}

	public void setCabAddebito(String cabAddebito) {
		this.cabAddebito = cabAddebito;
	}

	public String getNumeroCcAddebito() {
		return numeroCcAddebito;
	}

	public void setNumeroCcAddebito(String numeroCcAddebito) {
		this.numeroCcAddebito = numeroCcAddebito;
	}

	public String getSiaCreditore() {
		return siaCreditore;
	}

	public void setSiaCreditore(String siaCreditore) {
		this.siaCreditore = siaCreditore;
	}

	public String getTipoCodIndividuale() {
		return tipoCodIndividuale;
	}

	public void setTipoCodIndividuale(String tipoCodIndividuale) {
		this.tipoCodIndividuale = tipoCodIndividuale;
	}

	public String getCodIndividuale() {
		return codIndividuale;
	}

	public void setCodIndividuale(String codIndividuale) {
		this.codIndividuale = codIndividuale;
	}

	public String getRagSocSott_indSott() {
		return ragSocSott_indSott;
	}

	public void setRagSocSott_indSott(String ragSocSott_indSott) {
		this.ragSocSott_indSott = ragSocSott_indSott;
	}

	public String getIdFiscaleSottoscrittore() {
		return idFiscaleSottoscrittore;
	}

	public void setIdFiscaleSottoscrittore(String idFiscaleSottoscrittore) {
		this.idFiscaleSottoscrittore = idFiscaleSottoscrittore;
	}

	public String getRagSocIntAddebito() {
		return ragSocIntAddebito;
	}

	public void setRagSocIntAddebito(String ragSocIntAddebito) {
		this.ragSocIntAddebito = ragSocIntAddebito;
	}

	public String getCodRiferimento() {
		return codRiferimento;
	}

	public void setCodRiferimento(String codRiferimento) {
		this.codRiferimento = codRiferimento;
	}

	public String getFlagStorno() {
		return flagStorno;
	}

	public void setFlagStorno(String flagStorno) {
		this.flagStorno = flagStorno;
	}

	public String getNumeroRecord() {
		return numeroRecord;
	}

	public void setNumeroRecord(String numeroRecord) {
		this.numeroRecord = numeroRecord;
	}



	public String getCodiceSiaRt() {
		return codiceSiaRt;
	}

	public void setCodiceSiaRt(String codiceSiaRt) {
		this.codiceSiaRt = codiceSiaRt;
	} 
	
	

}
