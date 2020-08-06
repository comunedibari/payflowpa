package it.tasgroup.idp.rs.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.List;

/**
 * Processo di pagamento immediato
 */
@XmlRootElement
public class InfoAperturaPagamento implements Serializable {

	public InfoAperturaPagamento(String codPrestatoreServizio, List<String> idCondizioni, String codFiscaleVersante, String emailVersante, String descrizioneVersante, String ibanAddebito) {
		this.codPrestatoreServizio = codPrestatoreServizio;
		this.idCondizioni = idCondizioni;
		this.codFiscaleVersante = codFiscaleVersante;
		this.emailVersante = emailVersante;
		this.descrizioneVersante = descrizioneVersante;
		this.ibanAddebito = ibanAddebito;
	}

	public InfoAperturaPagamento() {


    }

    private String codPrestatoreServizio;

    private List<String> idCondizioni;

    private String codFiscaleVersante;

    private String emailVersante;

    private String descrizioneVersante;

    private String ibanAddebito;

	private List<OpzionePagamentoCondizione> opzioniPagamento;



    /**
     * codice univoco dello strumento di pagamento selezionato
     */
    @XmlElement(required=true)
    public String getCodPrestatoreServizio() {
        return codPrestatoreServizio;
    }

    public void setCodPrestatoreServizio(String codPrestatoreServizio) {
        this.codPrestatoreServizio = codPrestatoreServizio;
    }

    /**
     * Lista di identificativi delle condizioni da pagare (carrello).
     * Deve contenere almeno un elemento.
     */
    @XmlElement(required=true)
    public List<String> getIdCondizioni() {
        return idCondizioni;
    }

    public void setIdCondizioni(List<String> idCondizioni) {
        this.idCondizioni = idCondizioni;
    }

    /**
     * Email di chi effettua il pagamento.
     */
    @XmlElement(required=true)
	public String getEmailVersante() {
		return emailVersante;
	}

	public void setEmailVersante(String emailVersante) {
		this.emailVersante = emailVersante;
	}

	/**
	 * Codice Fiscale di chi effettua il pagamento (può non coincidere con l'intestatario del debito)
	 */
	 @XmlElement(required=true)
	public String getCodFiscaleVersante() {
		return codFiscaleVersante;
	}

	public void setCodFiscaleVersante(String codFiscaleVersante) {
		this.codFiscaleVersante = codFiscaleVersante;
	}

	/**
	 * Descrizione (nome, cognome) di chi effettua il pagamento
	 */
	public String getDescrizioneVersante() {
		return descrizioneVersante;
	}

	public void setDescrizioneVersante(String descrizioneVersante) {
		this.descrizioneVersante = descrizioneVersante;
	}

	/**
	 * IBAN addebito (solo pagamenti differiti, quando previsto)
	 */
	public String getIbanAddebito() {
		return ibanAddebito;
	}

	public void setIbanAddebito(String ibanAddebito) {
		this.ibanAddebito = ibanAddebito;
	}


	/**
	 * Opzioni associate alla lista di condizioni che si intende pagare
	 * @return
     */
	@XmlElement(required=true)
	public List<OpzionePagamentoCondizione> getOpzioniPagamento() {
		return opzioniPagamento;
	}

	public void setOpzioniPagamento(List<OpzionePagamentoCondizione> opzioniPagamento) {
		this.opzioniPagamento = opzioniPagamento;
	}
}
