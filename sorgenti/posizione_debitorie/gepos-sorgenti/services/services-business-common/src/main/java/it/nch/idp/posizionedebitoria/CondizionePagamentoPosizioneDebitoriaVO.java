package it.nch.idp.posizionedebitoria;

import it.tasgroup.iris.constants.SharedConstants;
import it.tasgroup.iris.shared.util.enumeration.EnumStatoPagamentoCondizione;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class CondizionePagamentoPosizioneDebitoriaVO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String idCondizione;
	private String idPagamento;
	private String idPendenza;
	private String codCIP;
	private Date dataScadenza;
	private Date dataInizio;
	private Date dataFine;
	private BigDecimal importoBase;
	private BigDecimal maggiorazione;
	private BigDecimal totale;
	private String statoCondizione;
	/** lista degli importi dettagliati */
	private List<ImportoCondizionePagamentoPosizioneDebitoriaVO> dettaglioImporto;
	private List<AllegatoAvvisoPosDeb> allegati;
	private List<DistintaPosizioneDebitoriaVO> pagamenti;
	private String statoPagamento;
	private String statoIncasso;
	private String tipoPagamento;
	private Number numPagamenti;
	private Number numDocumenti;
	private String idDocumento;
	/** data decorrenza pagamento */
	private Timestamp dataDecorrenza;

	private Number idPagamentoAssociato;
	private Number idDistintaAssociata;
	private String fornitoreGateway;
	private Timestamp tsInserimentoDistinta;
	private String codPagamento;
	private String codPagante;

	private String causaleCondizione;

	// Dettaglio transazione comunicato dall'ente
	private Timestamp dataPagamento;
	private String canalePagamento;
	private String mezzoPagamento;
	private BigDecimal importoPagamento;
	private String notePagamento;

	// Importo del pagamento su Iris
	private BigDecimal importoPagamentoIris;

	private EnumStatoPagamentoCondizione statoPagamentoCondizione;
	
	private boolean inCarrello;
	
	private String intestatarioDDP;
	
	private String cfPaganteDDP;

	
	private List<PrenotaAvvisiDigitaliVO> prenotaAvvisiDigitali;
	
	public List<PrenotaAvvisiDigitaliVO> getPrenotaAvvisiDigitali() { 
		return prenotaAvvisiDigitali; 
	} 

	public void setPrenotaAvvisiDigitali(List<PrenotaAvvisiDigitaliVO> prenotaAvvisiDigitali) { 
		this.prenotaAvvisiDigitali = prenotaAvvisiDigitali; 
	} 
	 
	public String getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}

	public boolean isInCarrello() {
		return inCarrello;
	}

	public void setInCarrello(boolean inCarrello) {
		this.inCarrello = inCarrello;
	}

	public EnumStatoPagamentoCondizione getStatoPagamentoCondizione() {
		return statoPagamentoCondizione;
	}

	public void setStatoPagamentoCondizione(EnumStatoPagamentoCondizione statoPagamentoCondizione) {
		this.statoPagamentoCondizione = statoPagamentoCondizione;
	}

	public String getStatoIncasso() {
		return statoIncasso;
	}

	public void setStatoIncasso(String statoIncasso) {
		this.statoIncasso = statoIncasso;
	}

	public Number getNumDocumenti() {
		return numDocumenti;
	}

	public void setNumDocumenti(Number numDocumenti) {
		this.numDocumenti = numDocumenti;
	}

	public Date getDataFine() {
		return dataFine;
	}

	public String getCanalePagamento() {
		return canalePagamento;
	}

	public void setCanalePagamento(String canalePagamento) {
		this.canalePagamento = canalePagamento;
	}

	public Timestamp getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Timestamp dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public Date getDataInizio() {
		return dataInizio;
	}

	public Date getDataScadenza() {
		return dataScadenza;
	}
	
	public Date getDataScadenzaVideo() {
		if (dataScadenza.compareTo(SharedConstants.NO_EXPIRE) == 0)
			return null;
		else
			return dataScadenza;
	}

	public BigDecimal getImportoBase() {
		return importoBase;
	}

	public BigDecimal getMaggiorazione() {
		return maggiorazione;
	}

	public BigDecimal getTotale() {
		return totale;
	}

	public void setDataFine(Date date) {
		dataFine = date;
	}

	public void setDataInizio(Date date) {
		dataInizio = date;
	}

	public void setDataScadenza(Date date) {
		dataScadenza = date;
	}

	public void setImportoBase(BigDecimal d) {
		importoBase = d;
	}

	public void setMaggiorazione(BigDecimal d) {
		maggiorazione = d;
	}

	public void setTotale(BigDecimal d) {
		totale = d;
	}

	public List<AllegatoAvvisoPosDeb> getAllegati() {
		return allegati;
	}

	public void setAllegati(List<AllegatoAvvisoPosDeb> list) {
		allegati = list;
	}

	public String getIdCondizione() {
		return idCondizione;
	}

	public void setIdCondizione(String string) {
		idCondizione = string;
	}

	public List<ImportoCondizionePagamentoPosizioneDebitoriaVO> getDettaglioImporto() {
		return dettaglioImporto;
	}

	public void setDettaglioImporto(List<ImportoCondizionePagamentoPosizioneDebitoriaVO> list) {
		dettaglioImporto = list;
	}

	public String getStatoCondizione() {
		return statoCondizione;
	}

	public String getStatoPagamento() {
		return statoPagamento;
	}

	public void setStatoCondizione(String string) {
		statoCondizione = string;
	}

	public void setStatoPagamento(String string) {
		statoPagamento = string;
	}

	public String getIdPendenza() {
		return idPendenza;
	}

	public void setIdPendenza(String string) {
		idPendenza = string;
	}

	public String getTipoPagamento() {
		return tipoPagamento;
	}

	public void setTipoPagamento(String string) {
		tipoPagamento = string;
	}

	public String getIdPagamento() {
		return idPagamento;
	}

	public void setIdPagamento(String idPagamento) {
		this.idPagamento = idPagamento;
	}

	public List<DistintaPosizioneDebitoriaVO> getPagamenti() {
		return pagamenti;
	}

	public void setPagamenti(List<DistintaPosizioneDebitoriaVO> pagamenti) {
		this.pagamenti = pagamenti;
	}

	public Number getNumPagamenti() {
		return numPagamenti;
	}

	public void setNumPagamenti(Number numPagamenti) {
		this.numPagamenti = numPagamenti;
	}

	public Timestamp getDataDecorrenza() {
		return dataDecorrenza;
	}

	public void setDataDecorrenza(Timestamp dataDecorrenza) {
		this.dataDecorrenza = dataDecorrenza;
	}

	public String getCodCIP() {
		return codCIP;
	}

	public void setCodCIP(String codCIP) {
		this.codCIP = codCIP;
	}

	public boolean isDDPAssociated() {
		
		if(numDocumenti == null)
			return false;
		
		if (numDocumenti.intValue() > 1)
			throw new IllegalStateException("Puo' esistere un solo documento non annullato per ogni condizione di pagamento");

		return numDocumenti.intValue() == 1;
	}

	public Number getIdPagamentoAssociato() {
		return idPagamentoAssociato;
	}

	public void setIdPagamentoAssociato(Number idPagamentoAssociato) {
		this.idPagamentoAssociato = idPagamentoAssociato;
	}

	public Number getIdDistintaAssociata() {
		return idDistintaAssociata;
	}

	public void setIdDistintaAssociata(Number idDistintaAssociata) {
		this.idDistintaAssociata = idDistintaAssociata;
	}

	public String getCodPagamento() {
		return codPagamento;
	}

	public void setCodPagamento(String codPagamento) {
		this.codPagamento = codPagamento;
	}

	public String getCodPagante() {
		return codPagante;
	}

	public void setCodPagante(String codPagante) {
		this.codPagante = codPagante;
	}

	public String getCausaleCondizione() {
		return causaleCondizione;
	}

	public void setCausaleCondizione(String causaleCondizione) {
		this.causaleCondizione = causaleCondizione;
	}

	public String getMezzoPagamento() {
		return mezzoPagamento;
	}

	public void setMezzoPagamento(String mezzoPagamento) {
		this.mezzoPagamento = mezzoPagamento;
	}

	public BigDecimal getImportoPagamento() {
		return importoPagamento;
	}

	public void setImportoPagamento(BigDecimal importoPagamento) {
		this.importoPagamento = importoPagamento;
	}

	public String getNotePagamento() {
		return notePagamento;
	}

	public void setNotePagamento(String notePagamento) {
		this.notePagamento = notePagamento;
	}

	public BigDecimal getImportoPagamentoIris() {
		return importoPagamentoIris;
	}

	public void setImportoPagamentoIris(BigDecimal importoPagamentoIris) {
		this.importoPagamentoIris = importoPagamentoIris;
	}

	public String getIntestatarioDDP() {
		return intestatarioDDP;
	}

	public void setIntestatarioDDP(String intestatarioDDP) {
		this.intestatarioDDP = intestatarioDDP;
	}

	public String getCfPaganteDDP() {
		return cfPaganteDDP;
	}

	public void setCfPaganteDDP(String cfPaganteDDP) {
		this.cfPaganteDDP = cfPaganteDDP;
	}

	public String getFornitoreGateway() {
		return fornitoreGateway;
	}

	public void setFornitoreGateway(String fornitoreGateway) {
		this.fornitoreGateway = fornitoreGateway;
	}

	public Timestamp getTsInserimentoDistinta() {
		return tsInserimentoDistinta;
	}

	public void setTsInserimentoDistinta(Timestamp tsInserimentoDistinta) {
		this.tsInserimentoDistinta = tsInserimentoDistinta;
	}

}
