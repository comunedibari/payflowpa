/**
 * 
 */
package it.tasgroup.iris.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author pazzik
 *
 */
public class RichiestaAUPPerListaCodiciDTO implements Serializable{
	
	private String codiceFiscaleCreditore;

	private String  codiceDebitoCreditore;
	
	private List<IdentificativoUnivocoVersamentoDTO>  idVersamenti = new ArrayList<IdentificativoUnivocoVersamentoDTO>();
	
	
	public String getCodiceDebitoCreditore() {
		return codiceDebitoCreditore;
	}

	public void setCodiceDebitoCreditore(String codiceDebitoCreditore) {
		this.codiceDebitoCreditore = codiceDebitoCreditore;
	}

	public List<IdentificativoUnivocoVersamentoDTO> getIdVersamenti() {
		return idVersamenti;
	}

	public void setIdVersamenti(List<IdentificativoUnivocoVersamentoDTO> idVersamenti) {
		this.idVersamenti = idVersamenti;
	}

	public String getCodiceFiscaleCreditore() {
		return codiceFiscaleCreditore;
	}

	public void setCodiceFiscaleCreditore(String codiceFiscaleCreditore) {
		this.codiceFiscaleCreditore = codiceFiscaleCreditore;
	}

	@Override
	public String toString() {
		return "RichiestaAUPPerListaCodiciDTO [codiceFiscaleCreditore="
				+ codiceFiscaleCreditore + ", codiceDebitoCreditore="
				+ codiceDebitoCreditore + ", idVersamenti=" + idVersamenti
				+ "]";
	}

}
