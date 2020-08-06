package it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi;

/**
 * Proprietà in comune alla form del Cruscotto Incassi.
 * 
 * @author Marianna Memoli
 */
public class CruscottoIncassiInputDto {
	
	/**
	 * Abilita/Disabilita la selezione dell'importo pagato
	 */
	private Boolean pagatiCheck;
	/**
	 * Abilita/Disabilita la selezione dell'importo rendicontato
	 */
	private Boolean rendicontatiCheck;
	/** 
	 * Abilita/Disabilita la selezione dell'importo incassato
	 */
	private Boolean incassatiCheck;
	
	/**
	 * Individua se è attivo il filtro per anno.
	 */
	private Boolean annoCheck;
	/**
	 * Individua se è attivo il filtro per anno/mese.
	 */
	private Boolean annoMeseCheck;
	/**
	 * Individua se è attivo il filtro per anno/mese/giorno.
	 */
	private Boolean annoMeseGiornoCheck;
	
	
	private String codIpa;
	
	
	public String getCodIpa() {
		return codIpa;
	}

	public void setCodIpa(String codIpa) {
		this.codIpa = codIpa;
	}
	
	/**
	 * @return the pagatiCheck
	 */
	public Boolean getPagatiCheck() {
		return pagatiCheck;
	}
	/**
	 * @param pagatiCheck the pagatiCheck to set
	 */
	public void setPagatiCheck(Boolean pagatiCheck) {
		this.pagatiCheck = pagatiCheck;
	}
	/**
	 * @return the rendicontatiCheck
	 */
	public Boolean getRendicontatiCheck() {
		return rendicontatiCheck;
	}
	/**
	 * @param rendicontatiCheck the rendicontatiCheck to set
	 */
	public void setRendicontatiCheck(Boolean rendicontatiCheck) {
		this.rendicontatiCheck = rendicontatiCheck;
	}
	/**
	 * @return the incassatiCheck
	 */
	public Boolean getIncassatiCheck() {
		return incassatiCheck;
	}
	/**
	 * @param incassatiCheck the incassatiCheck to set
	 */
	public void setIncassatiCheck(Boolean incassatiCheck) {
		this.incassatiCheck = incassatiCheck;
	}
	/**
	 * @return the annoCheck
	 */
	public Boolean getAnnoCheck() {
		return annoCheck;
	}
	/**
	 * @param annoCheck the annoCheck to set
	 */
	public void setAnnoCheck(Boolean annoCheck) {
		this.annoCheck = annoCheck;
	}
	/**
	 * @return the annoMeseCheck
	 */
	public Boolean getAnnoMeseCheck() {
		return annoMeseCheck;
	}
	/**
	 * @param annoMeseCheck the annoMeseCheck to set
	 */
	public void setAnnoMeseCheck(Boolean annoMeseCheck) {
		this.annoMeseCheck = annoMeseCheck;
	}
	/**
	 * @return the annoMeseGiornoCheck
	 */
	public Boolean getAnnoMeseGiornoCheck() {
		return annoMeseGiornoCheck;
	}
	/**
	 * @param annoMeseGiornoCheck the annoMeseGiornoCheck to set
	 */
	public void setAnnoMeseGiornoCheck(Boolean annoMeseGiornoCheck) {
		this.annoMeseGiornoCheck = annoMeseGiornoCheck;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CruscottoIncassiCommand [pagatiCheck=");
		builder.append(pagatiCheck);
		builder.append(", rendicontatiCheck=");
		builder.append(rendicontatiCheck);
		builder.append(", incassatiCheck=");
		builder.append(incassatiCheck);
		builder.append(", annoCheck=");
		builder.append(annoCheck);
		builder.append(", annoMeseCheck=");
		builder.append(annoMeseCheck);
		builder.append(", annoMeseGiornoCheck=");
		builder.append(annoMeseGiornoCheck);
		builder.append("]");
		return builder.toString();
	}
}