package it.tasgroup.idp.rs.model.creditore;

import it.tasgroup.idp.rs.enums.EnumStatoCondizione;
import it.tasgroup.idp.rs.enums.EnumStatoIncasso;
import it.tasgroup.idp.rs.enums.EnumStatoPagamento;
import it.tasgroup.idp.rs.enums.EnumStatoPagamentoCondizione;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Contiene le informazioni che rappresentano lo stato di un pagamento di una condizione.di pagamento.
 * Ovvero se è stata o meno pagata, se è stata pagata sulla piattaforma o esternamente.
 * Nel caso in cui la condizione di pagamento sia stata pagata nella piattaforma, questa classe contiene le informazioni
 * del pagamento andato a buon fine sulla piattaforma.
 *
 * Poiché è possibile che esistano più tentativi associati ad una stessa condizione di pagamento, è possibile risalire alla lista di
 * tutti i tentativi efffettuatu utilizzando l'opportuno endpoint /pagamenti/getTentativi (passando l'id fisico della condizione).
 *
 */
@XmlRootElement
public class InformazioniPagamentoCondizione {

    // -----------------------------------
    // Informazioni pagamento condizione
    // -----------------------------------

    private String identificativoUnivocoVersamento;
    private EnumStatoPagamentoCondizione statoPagamentoCondizione;
    private Boolean pagatoIdp;

    private String BundleKeyCanalePagamento;
	private String descrizioneCanalePagamento;
    private String descrizioneCircuito;
    private Date dataOraPagamento;
    private String notePagamento;
    private BigDecimal importoPagato;


    private Pagamento pagamentoEseguito;

	private boolean flagPagamentoMultiplo;

	private EnumStatoCondizione statoCondizione;
	private Long idPagamento;
	private String identificativoFiscaleCreditore;
	private String codTransazionePSP;
	private String modalitaPagamento;
	private String codFiscaleVersante;
	private String emailVersante;
	
	private String statoCondizioneCP;
	private String statoPagamentoCP;
	
	/**
	 * IUV AgID che identifica univocamente questo pagamento. Univoco a livello di creditore.
	 */
	public String getIdentificativoUnivocoVersamento() {
		return identificativoUnivocoVersamento;
	}


	public void setIdentificativoUnivocoVersamento(
			String identificativoUnivocoVersamento) {
		this.identificativoUnivocoVersamento = identificativoUnivocoVersamento;
	}

	/**
	 * Stato pagamento della condizione. Permette di capire se la condizione e' stata o meno pagata, al di la' dell'esito del singolo tentativo di pagamento effettuato
	 */
	public EnumStatoPagamentoCondizione getStatoPagamentoCondizione() {
		return statoPagamentoCondizione;
	}


	public void setStatoPagamentoCondizione(
			EnumStatoPagamentoCondizione statoPagamentoCondizione) {
		this.statoPagamentoCondizione = statoPagamentoCondizione;
	}


	/**
	 * Vale true se la condizione e' stata pagata sulla piattaforma, false se è stata pagata esternamente
	 * Presente solo se la condizione risulta pagata
	 */
	public Boolean getPagatoIdp() {
		return pagatoIdp;
	}


	public void setPagatoIdp(Boolean pagatoIdp) {
		this.pagatoIdp = pagatoIdp;
	}
	
	/**
	 * Descrizione del circuito
	 */
	public String getDescrizioneCircuito() {
		return descrizioneCircuito;
	}


	public void setDescrizioneCircuito(String descrizioneCircuito) {
		this.descrizioneCircuito = descrizioneCircuito;
	}

	/**
	 * Descrizione del canale di pagamento. (Presente solo se la condizione risulta pagata)
	 */
	public String getDescrizioneCanalePagamento() {
		return descrizioneCanalePagamento;
	}


	public void setDescrizioneCanalePagamento(String descrizioneCanalePagamento) {
		this.descrizioneCanalePagamento = descrizioneCanalePagamento;
	}

	/**
	 * Data ed ora nella quale e' iniziata la transazione di pagamento. (Presente solo se la condizione risulta associata ad un pagamento sulla piattaforma)
	 */
	public Date getDataOraPagamento() {
		return dataOraPagamento;
	}


	public void setDataOraPagamento(Date dataOraPagamento) {
		this.dataOraPagamento = dataOraPagamento;
	}

	/**
	 * Note sul dettaglio del pagamento. (Presente solo se la condizione risulta pagata)
	 */
	public String getNotePagamento() {
		return notePagamento;
	}


	public void setNotePagamento(String notePagamento) {
		this.notePagamento = notePagamento;
	}

	/**
	 * Importo effettivamente pagato. (Presente solo se la condizione risulta pagata)
	 */
	public BigDecimal getImportoPagato() {
		return importoPagato;
	}


	public void setImportoPagato(BigDecimal importoPagato) {
		this.importoPagato = importoPagato;
	}


	/**
	 * Pagamento Eseguito. Presente solo se la posizione è stata pagata, ed è stata pagata
	 * sulla piattaforma
	 */
	public Pagamento getPagamentoEseguito() {
		return pagamentoEseguito;
	}


	public void setPagamentoEseguito(Pagamento pagamentoEseguito) {
		this.pagamentoEseguito = pagamentoEseguito;
	}

	/**
	 * Vale true se alla condizione sono associati pagamenti multipli
	 * @return
	 */
	public boolean isFlagPagamentoMultiplo() {
		return flagPagamentoMultiplo;
	}

	public void setFlagPagamentoMultiplo(boolean flagPagamentoMultiplo) {
		this.flagPagamentoMultiplo = flagPagamentoMultiplo;
	}

	public EnumStatoCondizione getStatoCondizione() {
		return statoCondizione;
	}

	public void setStatoCondizione(EnumStatoCondizione statoCondizione) {
		this.statoCondizione = statoCondizione;
	}
	
	public void setIdPagamento(Long idPagamento) {
		this.idPagamento = idPagamento;
	}
	
	public Long getIdPagamento() {
		return idPagamento;
	}
	
	public void setIdentificativoFiscaleCreditore(String identificativoFiscaleCreditore) {
		this.identificativoFiscaleCreditore = identificativoFiscaleCreditore;
	}
	
	public String getIdentificativoFiscaleCreditore() {
		return identificativoFiscaleCreditore;
	}
	
	public void setCodTransazionePSP(String codTransazionePSP) {
		this.codTransazionePSP = codTransazionePSP;
	}
	
	public String getCodTransazionePSP() {
		return codTransazionePSP;
	}
 
	public String getBundleKeyCanalePagamento() {
		return BundleKeyCanalePagamento;
	}

	public void setBundleKeyCanalePagamento(String bundleKeyCanalePagamento) {
		BundleKeyCanalePagamento = bundleKeyCanalePagamento;
	}


	public String getModalitaPagamento() {
		return modalitaPagamento;
	}

	public void setModalitaPagamento(String modalitaPagamento) {
		this.modalitaPagamento = modalitaPagamento;
	}


	public String getCodFiscaleVersante() {
		return codFiscaleVersante;
	}


	public void setCodFiscaleVersante(String codFiscaleVersante) {
		this.codFiscaleVersante = codFiscaleVersante;
	}


	public String getEmailVersante() {
		return emailVersante;
	}


	public void setEmailVersante(String emailVersante) {
		this.emailVersante = emailVersante;
	}

	public String getStatoCondizioneCP() {
		return statoCondizioneCP;
	}


	public void setStatoCondizioneCP(String statoCondizioneCP) {
		this.statoCondizioneCP = statoCondizioneCP;
	}

	public String getStatoPagamentoCP() {
		return statoPagamentoCP;
	}


	public void setStatoPagamentoCP(String statoPagamentoCP) {
		this.statoPagamentoCP = statoPagamentoCP;
	}



}
