package it.regioneveneto.mygov.payment.mypivot.domain.dto.cruscottoIncassi;

import java.util.Map;

/**
 * DTO per la presentazione lato Web delle informazioni derivate dalla ricerca statistica:
 *  
 *    - TOTALI per CAPITOLI (solo quando la ricerca è filtrata per Ufficio o per Tipo Dovuto)
 *    
 * @author Marianna Memoli
 */
public class CruscottoIncassiMultiRowCol {
	
	/**
	 * Descrizione dell'ufficio o del Tipo dovuto selezionato come filtro per la ricerca statistica: TOTALI per CAPITOLI.
	 * Se la descrizione è mancante viene mostrato il codice ufficio.
	 */
	private String descFiltered;
	
	/**
	 * Mappa nella quale salvo l'elenco di capitoli estratti dalla query e da usare come intestazione delle colonne della tabella
	 * da presentare lato web.
	 * 
	 * La mappa ha come chiave il codice capitolo e valore la descrizione da usare come label.
	 * Se la descrizione del capitolo è mancante, è usato il codice.
	 */
	private Map<String, String> headerColumns;
	
	/**
	 * Mappa nella quale salvo l'elenco di dovuti estratti dalla query e da usare come intestazione delle righe della tabella
	 * da presentare lato web.
	 * 
	 * La mappa ha come chiave il codice del tipo dovuto e valore la descrizione da usare come label.
	 * Se la descrizione del tipo dovuto è mancante, è usato il codice.
	 * 
	 * 				oppure 
	 * 
	 * La mappa ha come chiave il codice dell'ufficio e valore la descrizione da usare come label.
	 * Se la descrizione dell'ufficio è mancante, è usato il codice.
	 */
	private Map<String, String> headerRows;
	
	/**
	 * Mappa nella quale salvo le associazioni dovuto/capitoli estratti dalla query con relativi importi (Formattato come "€ <importo>").
	 * 
	 * La mappa ha come chiave una stringa cosi fatta "<codice dovuto>_<codice capitolo>" e valore il bean con gli importi.
	 * 
	 * 				oppure
	 * 
	 * La mappa ha come chiave una stringa cosi fatta "<codice ufficio>_<codice capitolo>" e valore il bean con gli importi.
	 */
	private Map<String, CruscottoIncassiImportiDto> rowcolCell;
	
	/**
	 * Mappa nella quale salvo il totale delle somme degli importi Pagati, Rendicontati e Incassati per colonna, quindi per capitolo.
	 * 
	 * La mappa ha come chiave il codice capitolo e valore il bean con gli importi.
	 */
	private Map<String, CruscottoIncassiImportiDto> totColumns;
	
	/**
	 * Mappa nella quale salvo il totale delle somme degli importi Pagati, Rendicontati e Incassati per riga, quindi per tipo dovuto.
	 * 
	 * La mappa ha come chiave il codice del tipo dovuto e valore il bean con gli importi.
	 * 
	 * 				oppure
	 * 
	 * La mappa ha come chiave il codice dell'ufficio e valore il bean con gli importi.
	 */
	private Map<String, CruscottoIncassiImportiDto> totRows;

	/**
	 * Totale delle somme degli importi totali ottenuti per riga.
	 */
	private CruscottoIncassiImportiDto footerTotColumns;
	
	
	/**
	 * @return the descFiltered
	 */
	public String getDescFiltered() {
		return descFiltered;
	}

	/**
	 * @param descFiltered the descFiltered to set
	 */
	public void setDescFiltered(String descFiltered) {
		this.descFiltered = descFiltered;
	}

	/**
	 * @return the headerColumns
	 */
	public Map<String, String> getHeaderColumns() {
		return headerColumns;
	}

	/**
	 * @param headerColumns the headerColumns to set
	 */
	public void setHeaderColumns(Map<String, String> headerColumns) {
		this.headerColumns = headerColumns;
	}

	/**
	 * @return the headerRows
	 */
	public Map<String, String> getHeaderRows() {
		return headerRows;
	}

	/**
	 * @param headerRows the headerRows to set
	 */
	public void setHeaderRows(Map<String, String> headerRows) {
		this.headerRows = headerRows;
	}

	/**
	 * @return the rowcolCell
	 */
	public Map<String, CruscottoIncassiImportiDto> getRowcolCell() {
		return rowcolCell;
	}

	/**
	 * @param rowcolCell the rowcolCell to set
	 */
	public void setRowcolCell(Map<String, CruscottoIncassiImportiDto> rowcolCell) {
		this.rowcolCell = rowcolCell;
	}

	/**
	 * @return the totColumns
	 */
	public Map<String, CruscottoIncassiImportiDto> getTotColumns() {
		return totColumns;
	}

	/**
	 * @param totColumns the totColumns to set
	 */
	public void setTotColumns(Map<String, CruscottoIncassiImportiDto> totColumns) {
		this.totColumns = totColumns;
	}

	/**
	 * @return the totRows
	 */
	public Map<String, CruscottoIncassiImportiDto> getTotRows() {
		return totRows;
	}

	/**
	 * @param totRows the totRows to set
	 */
	public void setTotRows(Map<String, CruscottoIncassiImportiDto> totRows) {
		this.totRows = totRows;
	}

	/**
	 * @return the footerTotColumns
	 */
	public CruscottoIncassiImportiDto getFooterTotColumns() {
		return footerTotColumns;
	}

	/**
	 * @param footerTotColumns the footerTotColumns to set
	 */
	public void setFooterTotColumns(CruscottoIncassiImportiDto footerTotColumns) {
		this.footerTotColumns = footerTotColumns;
	}
}