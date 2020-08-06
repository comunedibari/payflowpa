package it.regioneveneto.mygov.payment.mypivot.domain.dto;

import it.regioneveneto.mygov.payment.mypivot.domain.to.AnagraficaStatoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.EnteTipoDovutoTO;
import it.regioneveneto.mygov.payment.mypivot.domain.to.UtenteTO;

/**
 * DTO per la presentazione lato Web delle informazioni caratterizzanti l'accertamento (Tabelle coinvolte: mygov_accertamento).
 * 
 * @author Marianna Memoli
 */
public class AccertamentoNuovoDto {

	private String codIpaEnte;
	
	/**
	 * Anagrafica del tipo dovuto 
	 */
	private String codTipoDovuto;
	/**
	 * Testo descrittivo dell'accertamento
	 */
	private String deNomeAccertamento;
	
	
	public void setCodIpaEnteString(String codIpaEnte) {
		this.codIpaEnte = codIpaEnte;
	}

	public String getCodIpaEnte() {
		return codIpaEnte;
	}
	 
	public void setCodTipoDovuto(String codTipoDovuto) {
		this.codTipoDovuto = codTipoDovuto;
	}

	public String getCodTipoDovuto() {
		return codTipoDovuto;
	}

	public void setDeNomeAccertamento(String deNomeAccertamento) {
		this.deNomeAccertamento = deNomeAccertamento;
	}

	public String getDeNomeAccertamento() {
		return deNomeAccertamento;
	}
}
