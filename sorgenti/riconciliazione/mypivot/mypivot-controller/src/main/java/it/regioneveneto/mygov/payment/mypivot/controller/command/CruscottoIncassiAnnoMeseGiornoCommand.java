package it.regioneveneto.mygov.payment.mypivot.controller.command;

import java.util.List;

/**
 * Bean che mappa la form della statistica: Totale per Anno, Mese e Giorno.
 * 
 * @author Marianna Memoli
 */
public class CruscottoIncassiAnnoMeseGiornoCommand extends CruscottoIncassiCommand {
	
	/**
	 * Indica la tipologia di statistica richiesta in View.
	 */
	private TIPO_STATISTICHE tipologia;
	
	/**
	 * Elenco degli anni per cui estrarre il dato statistico.	(Formato: yyyy)
	 */
	private List<Integer> listaAnni;
	
	/**
	 * L'anno per cui estrarre il dato statistico.	(Formato: yyyy)
	 */
	private Integer anno; 
	
	/**
	 * Elenco dei mesi dell'anno (specificato nella property "anno") per cui estrarre il dato statistico.	(Formato: MM)
	 */
	private List<Integer> listaMesi;

	/**
	 * Giorno del mese e dell'anno da cui iniziare l'estrazione del dato statistico.   (Formato dd/MM/yyyy; >=)
	 */
	private String giornoDal;
	
	/**
	 * Giorno del mese e dell'anno in cui terminare l'estrazione del dato statistico.  (Formato dd/MM/yyyy; <)
	 */
	private String giornoAl;
 
/* ==================================================== */
	
	/** 
	 * Valori possibili: 
	 *  1. "ANNO" 	 	  : La statistica in gestione è quella "Totali per Anno"
	 *  2. "MESE" 	 	  : La statistica in gestione è quella "Totali per Mese"
	 *  3. "GIORNO" 	  : La statistica in gestione è quella "Totali per Giorno"
	 */
	public static enum TIPO_STATISTICHE {ANNO, MESE, GIORNO}

/* ==================================================== */
	
	/**
	 * @return the listaAnni
	 */
	public List<Integer> getListaAnni() {
		return listaAnni;
	}

	/**
	 * @return the tipologia
	 */
	public TIPO_STATISTICHE getTipologia() {
		return tipologia;
	}

	/**
	 * @param tipologia the tipologia to set
	 */
	public void setTipologia(TIPO_STATISTICHE tipologia) {
		this.tipologia = tipologia;
	}

	/**
	 * @param listaAnni the listaAnni to set
	 */
	public void setListaAnni(List<Integer> listaAnni) {
		this.listaAnni = listaAnni;
	}

	/**
	 * @return the anno
	 */
	public Integer getAnno() {
		return anno;
	}

	/**
	 * @param anno the anno to set
	 */
	public void setAnno(Integer anno) {
		this.anno = anno;
	}

	/**
	 * @return the listaMesi
	 */
	public List<Integer> getListaMesi() {
		return listaMesi;
	}

	/**
	 * @param listaMesi the listaMesi to set
	 */
	public void setListaMesi(List<Integer> listaMesi) {
		this.listaMesi = listaMesi;
	}

	/**
	 * @return the giornoDal
	 */
	public String getGiornoDal() {
		return giornoDal;
	}

	/**
	 * @param giornoDal the giornoDal to set
	 */
	public void setGiornoDal(String giornoDal) {
		this.giornoDal = giornoDal;
	}

	/**
	 * @return the giornoAl
	 */
	public String getGiornoAl() {
		return giornoAl;
	}

	/**
	 * @param giornoAl the giornoAl to set
	 */
	public void setGiornoAl(String giornoAl) {
		this.giornoAl = giornoAl;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final int maxLen = 10;
		StringBuilder builder = new StringBuilder();
		builder.append("CruscottoIncassiAnnoMeseGiornoCommand [tipologia=");
		builder.append(tipologia);
		builder.append(", listaAnni=");
		builder.append(listaAnni != null ? listaAnni.subList(0, Math.min(listaAnni.size(), maxLen)) : null);
		builder.append(", anno=");
		builder.append(anno);
		builder.append(", listaMesi=");
		builder.append(listaMesi != null ? listaMesi.subList(0, Math.min(listaMesi.size(), maxLen)) : null);
		builder.append(", giornoDal=");
		builder.append(giornoDal);
		builder.append(", giornoAl=");
		builder.append(giornoAl);
		builder.append("]");
		return builder.toString();
	}
}