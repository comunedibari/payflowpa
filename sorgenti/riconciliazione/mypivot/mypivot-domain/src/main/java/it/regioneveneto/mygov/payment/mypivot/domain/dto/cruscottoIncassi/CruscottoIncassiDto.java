package it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi;

import java.util.List;

/**
 * DTO per la presentazione lato Web delle informazioni derivate dalla ricerca statistica:
 *  
 *    - TOTALI per UFFICI
 *    - TOTALI per TIPI DOVUTO
 *    - TOTALI per CAPITOLI (solo quando la ricerca è filtrata sia per Ufficio che per Tipo Dovuto)
 *    - TOTALI per ACCERTAMENTI
 *    
 * @author Marianna Memoli
 */
public class CruscottoIncassiDto {
	
	/**
	 * Descrizione dell'ufficio selezionato come filtro per la ricerca statistica: TOTALI per CAPITOLI e TOTALI per ACCERTAMENTI.
	 * Se la descrizione è mancante viene mostrato il codice ufficio.
	 */
	private String deUfficioFiltered;
	
	/**
	 * Descrizione del tipo dovuto selezionato come filtro per la ricerca statistica: TOTALI per CAPITOLI e TOTALI per ACCERTAMENTI.
	 * Se la descrizione è mancante viene mostrato il codice del tipo dovuto.
	 */
	private String deTipoDovutoFiltered;
	
	/**
	 * Descrizione del capitolo selezionato come filtro per la ricerca statistica: TOTALI per ACCERTAMENTI.
	 * Se la descrizione è mancante viene mostrato il codice capitolo.
	 */
	private String deCapitoloFiltered;


	/**
	 * Totale delle somme degli importi Pagati, Rendicontati e Incassati. 		(Formattato come "€ <importo>")
	 */
	private CruscottoIncassiImportiDto totImportiDTO;

	/**
	 * Risultato della ricerca.
	 */
	private List<VmStatisticaDto> result;

	
	
	/**
	 * @return the deUfficioFiltered
	 */
	public String getDeUfficioFiltered() {
		return deUfficioFiltered;
	}

	/**
	 * @param deUfficioFiltered the deUfficioFiltered to set
	 */
	public void setDeUfficioFiltered(String deUfficioFiltered) {
		this.deUfficioFiltered = deUfficioFiltered;
	}

	/**
	 * @return the deTipoDovutoFiltered
	 */
	public String getDeTipoDovutoFiltered() {
		return deTipoDovutoFiltered;
	}

	/**
	 * @param deTipoDovutoFiltered the deTipoDovutoFiltered to set
	 */
	public void setDeTipoDovutoFiltered(String deTipoDovutoFiltered) {
		this.deTipoDovutoFiltered = deTipoDovutoFiltered;
	}

	/**
	 * @return the deCapitoloFiltered
	 */
	public String getDeCapitoloFiltered() {
		return deCapitoloFiltered;
	}

	/**
	 * @param deCapitoloFiltered the deCapitoloFiltered to set
	 */
	public void setDeCapitoloFiltered(String deCapitoloFiltered) {
		this.deCapitoloFiltered = deCapitoloFiltered;
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
	public List<VmStatisticaDto> getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(List<VmStatisticaDto> result) {
		this.result = result;
	}
}