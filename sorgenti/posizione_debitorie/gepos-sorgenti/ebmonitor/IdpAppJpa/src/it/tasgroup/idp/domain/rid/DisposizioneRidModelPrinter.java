package it.tasgroup.idp.domain.rid;

public class DisposizioneRidModelPrinter {
	
	private String siaCreditore; //Mittente
	
	private String abiBancaDomiciliataria; //Ricevente
	
	private String dataCreazione; //Current Time Stamp (documento TAS)
	
	private String nomeSupporto; //Generato Unique 20 (documento TAS)
	
	private String tipoIncassoRid;
	
	private String codiceDivisa; //Assume il valore fisso (Euro).

	private String numeroRecord;
	
	private String numeroProgressivo;
	
	private String dataDecorrenza;
	
	private String dataScadenza;
	
	private String causale;
	
	private String importo;
	
	private String segno;
	
	private String abiBancaAssuntrice;
	
	private String tipoCodIndividuale;
	
	private String codIndividuale;
	
	private String ibanOrdinante;
	
	private String ragSocCreditore;
	
	private String ragSocDebitore;
	
	private String indirizzoDebitore;
	
	private String riferimentiDebito;
	
	private String codRiferimento;
	
	private String flagStorno;
	
	private String importoCoda;

	public String getSiaCreditore() {
		return siaCreditore;
	}

	public void setSiaCreditore(String siaCreditore) {
		this.siaCreditore = siaCreditore;
	}

	public String getAbiBancaDomiciliataria() {
		return abiBancaDomiciliataria;
	}

	public void setAbiBancaDomiciliataria(String abiBancaDomiciliataria) {
		this.abiBancaDomiciliataria = abiBancaDomiciliataria;
	}

	public String getTipoIncassoRid() {
		return tipoIncassoRid;
	}

	public void setTipoIncassoRid(String tipoIncassoRid) {
		this.tipoIncassoRid = tipoIncassoRid;
	}

	public String getCodiceDivisa() {
		return codiceDivisa;
	}

	public void setCodiceDivisa(String codiceDivisa) {
		this.codiceDivisa = codiceDivisa;
	}

	public String getNumeroRecord() {
		return numeroRecord;
	}

	public void setNumeroRecord(String numeroRecord) {
		this.numeroRecord = numeroRecord;
	}

	public String getNumeroProgressivo() {
		return numeroProgressivo;
	}

	public void setNumeroProgressivo(String numeroProgressivo) {
		this.numeroProgressivo = numeroProgressivo;
	}

	public String getDataDecorrenza() {
		return dataDecorrenza;
	}

	public void setDataDecorrenza(String dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}

	public String getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(String dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getCausale() {
		return causale;
	}

	public void setCausale(String causale) {
		this.causale = causale;
	}

	public String getImporto() {
		return importo;
	}

	public void setImporto(String importo) {
		this.importo = importo;
	}

	public String getSegno() {
		return segno;
	}

	public void setSegno(String segno) {
		this.segno = segno;
	}

	public String getAbiBancaAssuntrice() {
		return abiBancaAssuntrice;
	}

	public void setAbiBancaAssuntrice(String abiBancaAssuntrice) {
		this.abiBancaAssuntrice = abiBancaAssuntrice;
	}

	public String getTipoCodIndividuale() {
		return tipoCodIndividuale;
	}

	public void setTipoCodIndividuale(String tipoCodIndividuale) {
		this.tipoCodIndividuale = tipoCodIndividuale;
	}

	public String getIbanOrdinante() {
		return ibanOrdinante;
	}

	public void setIbanOrdinante(String ibanOrdinante) {
		this.ibanOrdinante = ibanOrdinante;
	}

	public String getCodIndividuale() {
		return codIndividuale;
	}

	public void setCodIndividuale(String codIndividuale) {
		this.codIndividuale = codIndividuale;
	}

	public String getRagSocCreditore() {
		return ragSocCreditore;
	}

	public void setRagSocCreditore(String ragSocCreditore) {
		this.ragSocCreditore = ragSocCreditore;
	}

	public String getRagSocDebitore() {
		return ragSocDebitore;
	}

	public void setRagSocDebitore(String ragSocDebitore) {
		this.ragSocDebitore = ragSocDebitore;
	}

	public String getIndirizzoDebitore() {
		return indirizzoDebitore;
	}

	public void setIndirizzoDebitore(String indirizzoDebitore) {
		this.indirizzoDebitore = indirizzoDebitore;
	}

	public String getRiferimentiDebito() {
		return riferimentiDebito;
	}

	public void setRiferimentiDebito(String riferimentiDebito) {
		this.riferimentiDebito = riferimentiDebito;
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

	public String getImportoCoda() {
		return importoCoda;
	}

	public void setImportoCoda(String importoCoda) {
		this.importoCoda = importoCoda;
	}
	
	

}
