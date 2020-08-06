package it.regioneveneto.mygov.payment.mypivot.controller.command;

/**
 * Bean che mappa la form della statistica: Totale per Accertamenti.
 * 
 * @author Marianna Memoli
 */
public class CruscottoIncassiAccertamentiCommand extends CruscottoIncassiCommand {
	
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
	
	/**
	 * Indica l'operazione che lato server devo applicare a seconda di quanto è stato selezionato nella View.
	 */
	private OPERATION operation;
	
/* ==================================================== */
	
	/** 
	 * Valori possibili: 
	 *  1. "CH_SEL_DOVUTO" :  Il dovuto selezionato è cambiato. Applica la ricerca uffici.
	 *  2. "CH_SEL_UFFICIO" : L'ufficio selezionato è cambiato.  Applica la ricerca capitoli.
	 *  3. "RC" :	 		  Ricerca dati statistici.
	 *  4. "NO_OP" :		  Nessuna operazione.
	 */
	public static enum OPERATION { CH_SEL_DOVUTO, CH_SEL_UFFICIO, RC, NO_OP }
	
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

	/**
	 * @return the operation
	 */
	public OPERATION getOperation() {
		return operation;
	}

	/**
	 * @param operation the operation to set
	 */
	public void setOperation(OPERATION operation) {
		this.operation = operation;
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
		builder.append(", operation=");
		builder.append(operation);
		builder.append("]");
		return builder.toString();
	}
}