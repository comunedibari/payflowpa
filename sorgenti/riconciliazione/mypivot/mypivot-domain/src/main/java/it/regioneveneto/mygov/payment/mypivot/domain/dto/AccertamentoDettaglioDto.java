package it.regioneveneto.mygov.payment.mypivot.domain.dto;

/**
 * DTO per la presentazione lato Web delle informazioni caratterizzanti la RT in accertamento
 * (Tabelle coinvolte: mygov_accertamento_dettaglio, mygov_flusso_export).
 * 
 * @author Marianna Memoli
 */
public class AccertamentoDettaglioDto {
	
	/**
	 * Identificativo interno
	 */
	private String id;
	/**
	 * Identificativo dell'accertamento
	 */
	private String accertamentoId;
	/**
	 * 
	 */
	private String dtUltimaModifica;
	/**
	 * Dettaglio della RT inserita in accertamento
	 */
	private AccertamentoFlussoExportDto flussoExportDTO;
	/**
	 * Individua se la riga corrente è stata aggiunta o rimossa dall'accertamento.
	 */
	private Boolean selected;
	
	
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
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
	 * @return the dtUltimaModifica
	 */
	public String getDtUltimaModifica() {
		return dtUltimaModifica;
	}
	/**
	 * @param dtUltimaModifica the dtUltimaModifica to set
	 */
	public void setDtUltimaModifica(String dtUltimaModifica) {
		this.dtUltimaModifica = dtUltimaModifica;
	}
	/**
	 * @return the flussoExportDTO
	 */
	public AccertamentoFlussoExportDto getFlussoExportDTO() {
		return flussoExportDTO;
	}
	/**
	 * @param flussoExportDTO the flussoExportDTO to set
	 */
	public void setFlussoExportDTO(AccertamentoFlussoExportDto flussoExportDTO) {
		this.flussoExportDTO = flussoExportDTO;
	}
	/**
	 * @return the selected
	 */
	public Boolean getSelected() {
		return selected;
	}
	/**
	 * @param selected the selected to set
	 */
	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
}
