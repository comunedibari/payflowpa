package it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi;

/**
 * Bean che mappa la form della statistica: Totale per Accertamenti.
 * 
 */
public class CruscottoIncassiAccertamentiInputDto extends CruscottoIncassiInputDto {
	
	/**
	 * L'anno per cui estrarre il dato statistico.			(Formato: yyyy)
	 */
	private String anno; 
	
	/**
	 * Il mese dell'anno(specificato nella property "anno") per cui estrarre il dato statistico. 	(Formato: MM/yyyy)
	 */
	private String mese;

	/**
	 * Il giorno nel mese dell'anno per cui estrarre il dato statistico. 	(Formato: dd/MM/yyyy) 
	 */
	private String giorno;

	/**
	 * Codice del Tipo dovuto.
	 */
	private String codiceTipoDovuto;
	
	/**
	 * Codice ufficio.
	 */
	private String codiceUfficio;
	
	/**
	 * Codice capitolo.
	 */
	private String codiceCapitolo;
	
/* ==================================================== */

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
	 * @return the codiceTipoDovuto
	 */
	public String getCodiceTipoDovuto() {
		return codiceTipoDovuto;
	}

	/**
	 * @param codiceTipoDovuto the codiceTipoDovuto to set
	 */
	public void setCodiceTipoDovuto(String codiceTipoDovuto) {
		this.codiceTipoDovuto = codiceTipoDovuto;
	}

	/**
	 * @return the codiceUfficio
	 */
	public String getCodiceUfficio() {
		return codiceUfficio;
	}

	/**
	 * @param codiceUfficio the codiceUfficio to set
	 */
	public void setCodiceUfficio(String codiceUfficio) {
		this.codiceUfficio = codiceUfficio;
	}

	/**
	 * @return the codiceCapitolo
	 */
	public String getCodiceCapitolo() {
		return codiceCapitolo;
	}

	/**
	 * @param codiceCapitolo the codiceCapitolo to set
	 */
	public void setCodiceCapitolo(String codiceCapitolo) {
		this.codiceCapitolo = codiceCapitolo;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CruscottoIncassiAccertamentiCommand [anno=");
		builder.append(anno);
		builder.append(", mese=");
		builder.append(mese);
		builder.append(", giorno=");
		builder.append(giorno);
		builder.append(", codiceTipoDovuto=");
		builder.append(codiceTipoDovuto);
		builder.append(", codiceUfficio=");
		builder.append(codiceUfficio);
		builder.append(", codiceCapitolo=");
		builder.append(codiceCapitolo);
		builder.append("]");
		return builder.toString();
	}
}