package it.tasgroup.idp.domain.rid;

public class RevocaRidModelPrinter {
	
	//RECORD TESTA - AL	
	private String codiceSiaRt;
	private String codiceAbiBT;
	private String dataCreazione; //Usato anche da Record 12
	private String nomeSupporto;
	
	//RECORD 12 - ABI BANCA ALLINEAMENTO RECORD 12 UTILIZZA AL MOMENTO CODICE ABI BT DI RECORD TEST - 01030
	private String numeroProgressivo; //Usato anche per gli altri Record della Disposizione
	private String causale;
	private String codPaeseAddebito;
	private String checkDigitAddebito;	
	private String cinAddebito;
	private String abiAddebito;	
	private String cabAddebito;	
	private String numeroCcAddebito;	
	private String siaCreditore;	
	private String tipoCodIndividuale;	
	private String codIndividuale;
	
	//RECORD 70
	private String codRiferimento;
	
	//RECORD CODA
	private String numeroRecord;

	public String getCodiceSiaRt() {
		return codiceSiaRt;
	}

	public void setCodiceSiaRt(String codiceSiaRt) {
		this.codiceSiaRt = codiceSiaRt;
	}

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

	public String getCodRiferimento() {
		return codRiferimento;
	}

	public void setCodRiferimento(String codRiferimento) {
		this.codRiferimento = codRiferimento;
	}

	public String getNumeroRecord() {
		return numeroRecord;
	}

	public void setNumeroRecord(String numeroRecord) {
		this.numeroRecord = numeroRecord;
	}
	
	
	
	
	

}
