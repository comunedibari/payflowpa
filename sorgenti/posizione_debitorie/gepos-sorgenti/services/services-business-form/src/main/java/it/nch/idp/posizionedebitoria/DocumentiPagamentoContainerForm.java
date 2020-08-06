package it.nch.idp.posizionedebitoria;

import it.tasgroup.services.util.enumeration.EnumStatoDDP;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;

import org.apache.struts.validator.ValidatorForm;

public class DocumentiPagamentoContainerForm extends ValidatorForm {

	private String filtroDataDaGG;
	private String filtroDataDaMM;
	private String filtroDataDaYY;
	private String filtroDataAGG;
	private String filtroDataAMM;
	private String filtroDataAYY;
	
	private String filtroId;
	
	private String filtroTipoDocumento;
	private String filtroStatoDocumento;
	
	private EnumStatoDDP enumStato;
	
	private String codPagante;
	
	private String emailPagante;
	
	private EnumTipoDDP enumTipo;
	
	
	private String selectedIdTreeTable;
	
	private String importoDa;
	private String importoA;
	
	// selezione da checkbox
	private String[] selectedIds;
	
	public String[] getSelectedIds() {
		return selectedIds;
	}
	public void setSelectedIds(String[] selectedIds) {
		this.selectedIds = selectedIds;
	}
	public String getFiltroTipoDocumento() {
		return filtroTipoDocumento;
	}
	public void setFiltroTipoDocumento(String filtroTipoDocumento) {
		this.filtroTipoDocumento = filtroTipoDocumento;
		this.enumTipo = EnumTipoDDP.getByKey(filtroTipoDocumento);
	}
	
	public String getFiltroStatoDocumento() {
		return filtroStatoDocumento;
	}
	
	public void setFiltroStatoDocumento(String filtroStatoDocumento) {
		this.filtroStatoDocumento = filtroStatoDocumento;
		
		this.enumStato = EnumStatoDDP.getByKey(filtroStatoDocumento);
	}
		
	public String getFiltroDataDaGG() {
		return filtroDataDaGG;
	}
	public void setFiltroDataDaGG(String filtroDataDaGG) {
		this.filtroDataDaGG = filtroDataDaGG;
	}
	public String getFiltroDataDaMM() {
		return filtroDataDaMM;
	}
	public void setFiltroDataDaMM(String filtroDataDaMM) {
		this.filtroDataDaMM = filtroDataDaMM;
	}
	public String getFiltroDataDaYY() {
		return filtroDataDaYY;
	}
	public void setFiltroDataDaYY(String filtroDataDaYY) {
		this.filtroDataDaYY = filtroDataDaYY;
	}
	public String getFiltroDataAGG() {
		return filtroDataAGG;
	}
	public void setFiltroDataAGG(String filtroDataAGG) {
		this.filtroDataAGG = filtroDataAGG;
	}
	public String getFiltroDataAMM() {
		return filtroDataAMM;
	}
	public void setFiltroDataAMM(String filtroDataAMM) {
		this.filtroDataAMM = filtroDataAMM;
	}
	public String getFiltroDataAYY() {
		return filtroDataAYY;
	}
	public void setFiltroDataAYY(String filtroDataAYY) {
		this.filtroDataAYY = filtroDataAYY;
	}
	
	public String getFiltroId() {
		return filtroId;
	}
	public void setFiltroId(String filtroId) {
		this.filtroId = filtroId;
	}
	public String getSelectedIdTreeTable() {
		return selectedIdTreeTable;
	}
	public void setSelectedIdTreeTable(String selectedIdTreeTable) {
		this.selectedIdTreeTable = selectedIdTreeTable;
	}
	public String getCodPagante() {
		return codPagante;
	}
	public void setCodPagante(String codPagante) {
		this.codPagante = codPagante;
	}
	public String getEmailPagante() {
		return emailPagante;
	}
	public void setEmailPagante(String emailPagante) {
		this.emailPagante = emailPagante;
	}
	public EnumStatoDDP getEnumStato() {
		return enumStato;
	}
	public void setEnumStato(EnumStatoDDP enumStato) {
		this.enumStato = enumStato;
	}
	public EnumTipoDDP getEnumTipo() {
		return enumTipo;
	}
	public void setEnumTipo(EnumTipoDDP enumTipo) {
		this.enumTipo = enumTipo;
	}
	public String getImportoDa() {
		return importoDa;
	}
	public void setImportoDa(String importoDa) {
		this.importoDa = importoDa;
	}
	public String getImportoA() {
		return importoA;
	}
	public void setImportoA(String importoA) {
		this.importoA = importoA;
	}
	
}
