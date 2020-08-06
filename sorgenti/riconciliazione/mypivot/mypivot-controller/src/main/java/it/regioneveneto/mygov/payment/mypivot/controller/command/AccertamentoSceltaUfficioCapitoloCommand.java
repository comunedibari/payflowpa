/**
 * 
 */
package it.regioneveneto.mygov.payment.mypivot.controller.command;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoFlussoExportDto;
import it.regioneveneto.mygov.payment.mypivot.domain.dto.AccertamentoUfficioCapitoloDto;

/**
 * Bean che mappa la form per la scelta dell'ufficio, del capitolo e dell'acertamento per la RT in accertamento.
 * Il bean mappa gli scenari di selezione del singolo pagamento e di molteplici pagamenti.
 * 
 * @author Marianna Memoli
 */
public class AccertamentoSceltaUfficioCapitoloCommand {
	
	/**
	 * Codice ufficio			(Obbligatorio)
	 */
	private String codUfficio;
	/**
	 * Codice capitolo			(Obbligatorio)
	 */
	private String codCapitolo;
	/**
	 * Anno di esercizio		(Obbligatorio)
	 */
	private String annoEsercizio;
	/**
	 * Codice accertamento 		(Facoltativo)
	 */
	private String codAccertamento;
	/**
	 * Indica la quota parte dell'importo da associare al capitolo ed accertamento contabile selezionati.
	 * Valorizzato solo se è stato selezionato un solo pagamento.
	 */
	private String importo;
	/**
	 * Indica l'operazione che lato server devo applicare a seconda di quanto è stato selezionato nella View.
	 */
	private OPERATION operation;

/* ==================================================== */

	/**
	 * Determina se è in gestione il caso di selezione di un singolo pagamento o di molteplici pagamanti.
	 * La grafica e il funzionamento della view sono customizzati in base al valore dell'attributo.
	 */
	private boolean multiple;
	/**
	 * Individua se abilitare o meno il pulsante per il salvataggio delle modifiche apportate.
	 */
	private boolean enableConfirmButton = false;
	/**
	 * Individua se abilitare o meno il form per definire capitoli.
	 * Quando l'importo totale dei dovuti e l'importo totale a capitoli coincidono non deve essere possibile definirne di nuovi.
	 */
	private boolean enableFormAdd = false;
	/**
	 * Individua se visualizzare o meno il messaggio di errore per righe duplicate.
	 */
	private boolean duplicateEntry;
	
 /* ==================================================== */
	
	/**
	 * Dettaglio struttura, capitolo ed accertamento contabile associati alle RT in accertamento.
	 */
	private List<AccertamentoUfficioCapitoloDto> ufficiTO;
	/**
	 * Importo totale dei dovuti nell'accertamento
	 */
	private BigDecimal totImportoDovuti;
	/**
	 * Importo totale dei dovuti nell'accertamento formattato come "€ <importo>"
	 */
	private String formatTotImportoDovuti;
	/**
	 * Importo totale assegnato a capitoli calcolato come somma degli importi
	 */
	private BigDecimal totImportoCapitoli;
	/**
	 * Importo totale assegnato a capitoli formattato come "€ <importo>"
	 */
	private String formatTotImportoCapitoli;
	/**
	 * Importo totale che resta da assegnare a capitoli formattato come "€ <importo>"
	 */
	private String formatTotImportoMancante;
	
/* ==================================================== */
	
	/**
	 * Identificativo dell'accertamento 		(equals)
	 */
	private String accertamentoId;
	/**
	 * Dettaglio delle RT inserite in accertamento
	 */
	private List<AccertamentoFlussoExportDto> flussiExportDTO;
	
/* ==================================================== */
		
	/** 
	 * Valori possibili: 
	 *  1. "CH_SEL_UFFICIO" :  L'ufficio selezionato è cambiato. Seleziona l'anno di esercizio di default e Applica la ricerca capitoli.
	 *  2. "CH_SEL_ANNO" : 	   L'anno di esercizio selezionato è cambiato. Applica la ricerca capitoli.
	 *  3. "CH_SEL_CAPITOLO" : Il capitolo selezionato è cambiato. Applica la ricerca accertamenti.
	 *  4. "ADD_ITEM" : 	   Aggiunge in lista la riga dell'ufficio, capitolo ed accertamento contabile selezionati
	 *  5. "DEL_ITEM" : 	   Elimina dalla lista la riga dell'ufficio, capitolo ed accertamento contabile selezionati
	 *  6. "SAVE" : 		   Salvataggio della form.
	 *  7. "NO_OP" :		   Nessuna operazione.
	 */
	public static enum OPERATION { CH_SEL_UFFICIO, CH_SEL_ANNO, CH_SEL_CAPITOLO, ADD_ITEM, DEL_ITEM, SAVE , NO_OP}
	/**
	 * Per il caso di OPERATION "DEL_ITEM", l'attributo indica l'indice da rimuovere nella lista dei capitoli e accertamenti.
	 */
	private String rmIndex;
	
/* ==================================================== */
	
	/**
	 * @return the ufficiTO
	 */
	public List<AccertamentoUfficioCapitoloDto> getUfficiTO() {
		if (ufficiTO == null) {
			ufficiTO = new ArrayList<AccertamentoUfficioCapitoloDto>();
        }
		return ufficiTO;
	}

	/**
	 * @return the codUfficio
	 */
	public String getCodUfficio() {
		return codUfficio;
	}

	/**
	 * @param codUfficio the codUfficio to set
	 */
	public void setCodUfficio(String codUfficio) {
		this.codUfficio = codUfficio;
	}

	/**
	 * @return the codCapitolo
	 */
	public String getCodCapitolo() {
		return codCapitolo;
	}

	/**
	 * @param codCapitolo the codCapitolo to set
	 */
	public void setCodCapitolo(String codCapitolo) {
		this.codCapitolo = codCapitolo;
	}

	/**
	 * @return the annoEsercizio
	 */
	public String getAnnoEsercizio() {
		return annoEsercizio;
	}

	/**
	 * @param annoEsercizio the annoEsercizio to set
	 */
	public void setAnnoEsercizio(String annoEsercizio) {
		this.annoEsercizio = annoEsercizio;
	}

	/**
	 * @return the codAccertamento
	 */
	public String getCodAccertamento() {
		return codAccertamento;
	}

	/**
	 * @param codAccertamento the codAccertamento to set
	 */
	public void setCodAccertamento(String codAccertamento) {
		this.codAccertamento = codAccertamento;
	}

	/**
	 * @return the importo
	 */
	public String getImporto() {
		return importo;
	}

	/**
	 * @param importo the importo to set
	 */
	public void setImporto(String importo) {
		this.importo = importo;
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

	/**
	 * @return the multiple
	 */
	public boolean isMultiple() {
		return multiple;
	}

	/**
	 * @param multiple the multiple to set
	 */
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}

	/**
	 * @return the enableConfirmButton
	 */
	public boolean isEnableConfirmButton() {
		return enableConfirmButton;
	}

	/**
	 * @param enableConfirmButton the enableConfirmButton to set
	 */
	public void setEnableConfirmButton(boolean enableConfirmButton) {
		this.enableConfirmButton = enableConfirmButton;
	}

	/**
	 * @return the enableFormAdd
	 */
	public boolean isEnableFormAdd() {
		return enableFormAdd;
	}

	/**
	 * @param enableFormAdd the enableFormAdd to set
	 */
	public void setEnableFormAdd(boolean enableFormAdd) {
		this.enableFormAdd = enableFormAdd;
	}

	/**
	 * @return the duplicateEntry
	 */
	public boolean isDuplicateEntry() {
		return duplicateEntry;
	}

	/**
	 * @param duplicateEntry the duplicateEntry to set
	 */
	public void setDuplicateEntry(boolean duplicateEntry) {
		this.duplicateEntry = duplicateEntry;
	}

	/**
	 * @return the totImportoDovuti
	 */
	public BigDecimal getTotImportoDovuti() {
		return totImportoDovuti;
	}

	/**
	 * @param totImportoDovuti the totImportoDovuti to set
	 */
	public void setTotImportoDovuti(BigDecimal totImportoDovuti) {
		this.totImportoDovuti = totImportoDovuti;
	}

	/**
	 * @return the formatTotImportoDovuti
	 */
	public String getFormatTotImportoDovuti() {
		return formatTotImportoDovuti;
	}

	/**
	 * @param formatTotImportoDovuti the formatTotImportoDovuti to set
	 */
	public void setFormatTotImportoDovuti(String formatTotImportoDovuti) {
		this.formatTotImportoDovuti = formatTotImportoDovuti;
	}

	/**
	 * @return the totImportoCapitoli
	 */
	public BigDecimal getTotImportoCapitoli() {
		return totImportoCapitoli;
	}

	/**
	 * @param totImportoCapitoli the totImportoCapitoli to set
	 */
	public void setTotImportoCapitoli(BigDecimal totImportoCapitoli) {
		this.totImportoCapitoli = totImportoCapitoli;
	}

	/**
	 * @return the formatTotImportoCapitoli
	 */
	public String getFormatTotImportoCapitoli() {
		return formatTotImportoCapitoli;
	}

	/**
	 * @param formatTotImportoCapitoli the formatTotImportoCapitoli to set
	 */
	public void setFormatTotImportoCapitoli(String formatTotImportoCapitoli) {
		this.formatTotImportoCapitoli = formatTotImportoCapitoli;
	}

	/**
	 * @return the formatTotImportoMancante
	 */
	public String getFormatTotImportoMancante() {
		return formatTotImportoMancante;
	}

	/**
	 * @param formatTotImportoMancante the formatTotImportoMancante to set
	 */
	public void setFormatTotImportoMancante(String formatTotImportoMancante) {
		this.formatTotImportoMancante = formatTotImportoMancante;
	}

	/**
	 * @return the accertamentoId
	 */
	public String getAccertamentoId() {
		return accertamentoId;
	}

	/**
	 * @param accertamentoId the accertamentoId to set
	 */
	public void setAccertamentoId(String accertamentoId) {
		this.accertamentoId = accertamentoId;
	}

	/**
	 * @return the flussiExportDTO
	 */
	public List<AccertamentoFlussoExportDto> getFlussiExportDTO() {
		return flussiExportDTO;
	}

	/**
	 * @param flussiExportDTO the flussiExportDTO to set
	 */
	public void setFlussiExportDTO(List<AccertamentoFlussoExportDto> flussiExportDTO) {
		this.flussiExportDTO = flussiExportDTO;
	}

	/**
	 * @return the rmIndex
	 */
	public String getRmIndex() {
		return rmIndex;
	}

	/**
	 * @param rmIndex the rmIndex to set
	 */
	public void setRmIndex(String rmIndex) {
		this.rmIndex = rmIndex;
	}

	/**
	 * @param ufficiTO the ufficiTO to set
	 */
	public void setUfficiTO(List<AccertamentoUfficioCapitoloDto> ufficiTO) {
		this.ufficiTO = ufficiTO;
	}
}