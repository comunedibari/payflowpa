package it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi;

import java.util.List;

/**
 * DTO per la presentazione lato Web delle informazioni derivate dalla ricerca statistica:
 *  
 *    - TOTALI per ANNO
 *    - TOTALI per MESE
 *    - TOTALI per GIORNO
 *  
 * @author Marianna Memoli
 */
public class CruscottoIncassiAnnoMeseGiornoDto {
	
	/**
	 * Totale delle somme dei Numero pagamenti (RT).
	 */
	private String totNumPagamenti;
	
	/**
	 * Totale delle somme degli importi Pagati, Rendicontati e Incassati. 		(Formattato come "€ <importo>")
	 */
	private CruscottoIncassiImportiDto totImportiDTO;

	/**
	 * Risultato della ricerca.
	 */
	private List<VmStatisticaAnnoMeseGiornoDto> result;

	
	
	/**
	 * @return the totNumPagamenti
	 */
	public String getTotNumPagamenti() {
		return totNumPagamenti;
	}

	/**
	 * @param totNumPagamenti the totNumPagamenti to set
	 */
	public void setTotNumPagamenti(String totNumPagamenti) {
		this.totNumPagamenti = totNumPagamenti;
	}

	/**
	 * @return the totImportiDTO
	 */
	public CruscottoIncassiImportiDto getTotImportiDTO() {
		return totImportiDTO;
	}

	/**
	 * @param totImportiDTO the totImportiDTO to set
	 */
	public void setTotImportiDTO(CruscottoIncassiImportiDto totImportiDTO) {
		this.totImportiDTO = totImportiDTO;
	}

	/**
	 * @return the result
	 */
	public List<VmStatisticaAnnoMeseGiornoDto> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(List<VmStatisticaAnnoMeseGiornoDto> result) {
		this.result = result;
	}
}