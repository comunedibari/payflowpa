package it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi;

/**
 * DTO per la presentazione lato Web delle informazioni selezionate dalla ricerca statistica per:
 * 
 *   - TOTALI per UFFICI
 *   - TOTALI per TIPI DOVUTO
 *   - TOTALI per CAPITOLI (solo quando la ricerca è filtrata per Ufficio e Tipo Dovuto)
 *   - TOTALI per ACCERTAMENTI
 *     
 * @author Marianna Memoli
 */
public class VmStatisticaDto {

	/**
	 * Testo da mostrare come intestazione della riga della tabella. A seconda della statistica contiene la descrizione del: 
	 * 
	 *   - Tipo dovuto, se mancante viene mostrato il codice.
	 *   - Ufficio, se mancante viene mostrato il codice.
	 *   - Capitolo, se mancante viene mostrato il codice.
	 *   - Accertamento, se mancante viene mostrato il codice.
	 */
	private String desc;

	/**
	 * A seconda della statistica contiene il codice: 
	 * 
	 *   - Tipo dovuto
	 *   - Ufficio 
	 *   - Capitolo 
	 *   - Accertamento
	 */
	private String codice;
	
	/**
	 * Totale degli importi Pagati, Rendicontati e Incassati. 		(Formattato come "€ <importo>")
	 */
	private CruscottoIncassiImportiDto importiDTO;

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

	/**
	 * @return the codice
	 */
	public String getCodice() {
		return codice;
	}

	/**
	 * @param codice the codice to set
	 */
	public void setCodice(String codice) {
		this.codice = codice;
	}

	/**
	 * @return the importiDTO
	 */
	public CruscottoIncassiImportiDto getImportiDTO() {
		return importiDTO;
	}

	/**
	 * @param importiDTO the importiDTO to set
	 */
	public void setImportiDTO(CruscottoIncassiImportiDto importiDTO) {
		this.importiDTO = importiDTO;
	}
}