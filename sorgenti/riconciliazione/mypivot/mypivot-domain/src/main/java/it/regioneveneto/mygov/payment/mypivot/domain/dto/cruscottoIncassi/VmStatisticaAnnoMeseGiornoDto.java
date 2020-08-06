package it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi;

/**
 * DTO per la presentazione lato Web delle informazioni selezionate dalla ricerca statistica per: ANNO, MESE o GIORNO. 
 * 
 * @author Marianna Memoli
 */
public class VmStatisticaAnnoMeseGiornoDto {

	/**
	 * Anno di riferimento
	 */
	private String anno;
	
	/**
	 * Mese nell'anno espresso come numero.
	 */
	private String mese;
	
	/**
	 * Mese nell'anno espresso come nome.
	 */
	private String labelMese;
	
	/**
	 * Giorno nel mese
	 */
	private String giorno;

	/**
	 * Numero pagamenti (RT).
	 */
	private String numPagamenti;
	
	/**
	 * Totale degli importi Pagati, Rendicontati e Incassati. 		(Formattato come "€ <importo>")
	 */
	private CruscottoIncassiImportiDto importiDTO;

	
	
	/**
	 * @return the anno
	 */
	public String getAnno() {
		return anno;
	}

	/**
	 * @param anno the anno to set
	 */
	public void setAnno(String anno) {
		this.anno = anno;
	}

	/**
	 * @return the mese
	 */
	public String getMese() {
		return mese;
	}

	/**
	 * @param mese the mese to set
	 */
	public void setMese(String mese) {
		this.mese = mese;
	}

	/**
	 * @return the labelMese
	 */
	public String getLabelMese() {
		return labelMese;
	}

	/**
	 * @param labelMese the labelMese to set
	 */
	public void setLabelMese(String labelMese) {
		this.labelMese = labelMese;
	}

	/**
	 * @return the giorno
	 */
	public String getGiorno() {
		return giorno;
	}

	/**
	 * @param giorno the giorno to set
	 */
	public void setGiorno(String giorno) {
		this.giorno = giorno;
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

	/**
	 * @return the numPagamenti
	 */
	public String getNumPagamenti() {
		return numPagamenti;
	}

	/**
	 * @param numPagamenti the numPagamenti to set
	 */
	public void setNumPagamenti(String numPagamenti) {
		this.numPagamenti = numPagamenti;
	}
}