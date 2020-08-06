package it.tasgroup.iris.dto;

import it.tasgroup.services.util.enumeration.EnumStatoDDP;
import it.tasgroup.services.util.enumeration.EnumTipoDDP;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author FormosaF
 *
 */
public class ListaDocumentiInputDTO implements Serializable {

	
	private Date startDateFilter;
	private Date endDateFilter;
	
	private String docIdFilter;
	private String codPagante;
	private String emailPagante;
	
	private EnumTipoDDP docTypeFilter;
	private EnumStatoDDP docStateFilter;

	private String sortingField;
	private String sortingDir;
	
	private String docIdSelected;
	
	private BigDecimal importoDa;
	private BigDecimal importoA;
   	

	
	public Date getStartDateFilter() {
		return startDateFilter;
	}
	public void setStartDateFilter(Date startDateFilter) {
		this.startDateFilter = startDateFilter;
	}
	public Date getEndDateFilter() {
		return endDateFilter;
	}
	public void setEndDateFilter(Date endDateFilter) {
		this.endDateFilter = endDateFilter;
	}
	public String getDocIdFilter() {
		return docIdFilter;
	}
	public void setDocIdFilter(String docIdFilter) {
		this.docIdFilter = docIdFilter;
	}
	public EnumTipoDDP getDocTypeFilter() {
		return docTypeFilter;
	}
	public void setDocTypeFilter(EnumTipoDDP docTypeFilter) {
		this.docTypeFilter = docTypeFilter;
	}
	public EnumStatoDDP getDocStateFilter() {
		return docStateFilter;
	}
	public void setDocStateFilter(EnumStatoDDP docStateFilter) {
		this.docStateFilter = docStateFilter;
	}
	public String getSortingField() {
		return sortingField;
	}
	public void setSortingField(String sortingField) {
		this.sortingField = sortingField;
	}
	public String getSortingDir() {
		return sortingDir;
	}
	public void setSortingDir(String sortingDir) {
		this.sortingDir = sortingDir;
	}
	public String getDocIdSelected() {
		return docIdSelected;
	}
	public void setDocIdSelected(String docIdSelected) {
		this.docIdSelected = docIdSelected;
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
	public BigDecimal getImportoDa() {
		return importoDa;
	}
	public void setImportoDa(BigDecimal importoDa) {
		this.importoDa = importoDa;
	}
	public BigDecimal getImportoA() {
		return importoA;
	}
	public void setImportoA(BigDecimal importoA) {
		this.importoA = importoA;
	}
	
}
