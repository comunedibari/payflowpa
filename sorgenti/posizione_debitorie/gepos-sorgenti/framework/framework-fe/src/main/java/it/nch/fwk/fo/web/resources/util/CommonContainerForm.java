/*
 * Creato il 16-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.fwk.fo.web.resources.util;

import it.nch.fwk.fo.common.constants.CommonConstants;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.ValidatorActionForm;

/**
 * @author EE10800
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class CommonContainerForm extends ValidatorActionForm 
{
	private static final long serialVersionUID = 1L;
	private String directing;
	private String backward;
	private String option;	
	private String service;
	private String funct;
	
	private int numRisultati = CommonConstants.RESULTS_PER_PAGE;
	private int pagingSavingRecordPosition;	
	private int pagingCurrentPageRecords;
	private int pagingLastRecordPosition;	
	private int pagingRecordPosition;	
	private String pagingDirection;
	private int pagingTotRecords;	
	private int pagingTotPages;
	private int pagingNumPagesNext;
	private int pagingNumRecordsNext;	
	private int pagingNumPagesPrev;
	private int pagingNumRecordsPrev;
	private int lastResultOfPage;
	private Collection pageNumbers;
	private Collection viewForm = new ArrayList();
	private int numNextPage;
	private int numCurrentPage;
	private String paginationSmart = new String("false");

	private String[] checkElenco;
	
	private String orderBy = null;
	private String orderDirection = null;

	//
	private String nomeProvincia;
	
	private String cleanFlag;
	
	private String password;
	
	/**
	 * @return Restituisce il valore viewForm.
	 */
	public Collection getViewForm() {
		return viewForm;
	}
	/**
	 * @param viewForm Il valore viewForm da impostare.
	 */
	public void setViewForm(Collection viewForm) {
		this.viewForm = viewForm;
	}	
	

	/**
	 * @return Restituisce il valore pagingSavingRecordPosition.
	 */
	public int getPagingSavingRecordPosition() {
		return pagingSavingRecordPosition;
	}
	/**
	 * @param pagingSavingRecordPosition Il valore pagingSavingRecordPosition da impostare.
	 */
	public void setPagingSavingRecordPosition(int pagingSavingRecordPosition) {
		this.pagingSavingRecordPosition = pagingSavingRecordPosition;
	}
	/**
	 * @return Restituisce il valore pagingLastRecordPosition.
	 */
	public int getPagingLastRecordPosition() {
		return pagingLastRecordPosition;
	}
	/**
	 * @param pagingLastRecordPosition Il valore pagingLastRecordPosition da impostare.
	 */
	public void setPagingLastRecordPosition(int pagingLastRecordPosition) {
		this.pagingLastRecordPosition = pagingLastRecordPosition;
	}
	/**
	 * @return Restituisce il valore pageNumbers.
	 */
	public Collection getPageNumbers() {
		return pageNumbers;
	}
	/**
	 * @param pageNumbers Il valore pageNumbers da impostare.
	 */
	public void setPageNumbers(Collection pageNumbers) {
		this.pageNumbers = pageNumbers;
	}
	/**
	 * @return Restituisce il valore pagingDirection.
	 */
	public String getPagingDirection() {
		return pagingDirection;
	}
	/**
	 * @param pagingDirection Il valore pagingDirection da impostare.
	 */
	public void setPagingDirection(String pagingDirection) {
		this.pagingDirection = pagingDirection;
	}
	/**
	 * @return Restituisce il valore checkElenco.
	 */
	public String[] getCheckElenco() {
		return checkElenco;
	}
	/**
	 * @param checkElenco Il valore checkElenco da impostare.
	 */
	public void setCheckElenco(String[] checkElenco) {
		this.checkElenco = checkElenco;
	}
	/**
	 * @return Restituisce il valore backward.
	 */
	public String getBackward() {
		return backward;
	}
	/**
	 * @param backward Il valore backward da impostare.
	 */
	public void setBackward(String backward) {
		this.backward = backward;
	}
	/**
	 * @return Restituisce il valore directing.
	 */
	public String getDirecting() {
		return directing;
	}
	/**
	 * @param directing Il valore directing da impostare.
	 */
	public void setDirecting(String directing) {
		this.directing = directing;
	}
	/**
	 * @return Restituisce il valore funct.
	 */
	public String getFunct() {
		return funct;
	}
	/**
	 * @param funct Il valore funct da impostare.
	 */
	public void setFunct(String funct) {
		this.funct = funct;
	}
	/**
	 * @return Restituisce il valore option.
	 */
	public String getOption() {
		return option;
	}
	/**
	 * @param option Il valore option da impostare.
	 */
	public void setOption(String option) {
		this.option = option;
	}
	/**
	 * @return Restituisce il valore service.
	 */
	public String getService() {
		return service;
	}
	/**
	 * @param service Il valore service da impostare.
	 */
	public void setService(String service) {
		this.service = service;
	}
	
	public void reset(ActionMapping actionMapping,
			HttpServletRequest httpServletRequest) {
		this.checkElenco = new String[0];
//		this.pagingRecordPosition=0;
//		this.pageNumbers=new ArrayList();
//		this.pagingDirection="";
	}	


	/**
	 * @return Restituisce il valore pagingNumPagesNext.
	 */
	public int getPagingNumPagesNext() {
		return pagingNumPagesNext;
	}
	/**
	 * @param pagingNumPagesNext Il valore pagingNumPagesNext da impostare.
	 */
	public void setPagingNumPagesNext(int pagingNumPagesNext) {
		this.pagingNumPagesNext = pagingNumPagesNext;
	}
	/**
	 * @return Restituisce il valore pagingNumPagesPrev.
	 */
	public int getPagingNumPagesPrev() {
		return pagingNumPagesPrev;
	}
	/**
	 * @param pagingNumPagesPrev Il valore pagingNumPagesPrev da impostare.
	 */
	public void setPagingNumPagesPrev(int pagingNumPagesPrev) {
		this.pagingNumPagesPrev = pagingNumPagesPrev;
	}
	/**
	 * @return Restituisce il valore pagingNumRecordsNext.
	 */
	public int getPagingNumRecordsNext() {
		return pagingNumRecordsNext;
	}
	/**
	 * @param pagingNumRecordsNext Il valore pagingNumRecordsNext da impostare.
	 */
	public void setPagingNumRecordsNext(int pagingNumRecordsNext) {
		this.pagingNumRecordsNext = pagingNumRecordsNext;
	}
	/**
	 * @return Restituisce il valore pagingNumRecordsPrev.
	 */
	public int getPagingNumRecordsPrev() {
		return pagingNumRecordsPrev;
	}
	/**
	 * @param pagingNumRecordsPrev Il valore pagingNumRecordsPrev da impostare.
	 */
	public void setPagingNumRecordsPrev(int pagingNumRecordsPrev) {
		this.pagingNumRecordsPrev = pagingNumRecordsPrev;
	}
	/**
	 * @return Restituisce il valore pagingTotPages.
	 */
	public int getPagingTotPages() {
		return pagingTotPages;
	}
	/**
	 * @param pagingTotPages Il valore pagingTotPages da impostare.
	 */
	public void setPagingTotPages(int pagingTotPages) {
		this.pagingTotPages = pagingTotPages;
	}

	/**
	 * @return Restituisce il valore pagingTotRecords.
	 */
	public int getPagingTotRecords() {
		return pagingTotRecords;
	}
	/**
	 * @param pagingTotRecords Il valore pagingTotRecords da impostare.
	 */
	public void setPagingTotRecords(int pagingTotRecords) {
		this.pagingTotRecords = pagingTotRecords;
	}
	/**
	 * @return Restituisce il valore pagingRecordPosition.
	 */
	public int getPagingRecordPosition() {
		return pagingRecordPosition;
	}
	/**
	 * @param pagingRecordPosition Il valore pagingRecordPosition da impostare.
	 */
	public void setPagingRecordPosition(int pagingRecordPosition) {
		this.pagingRecordPosition = pagingRecordPosition;
	}
	public int getLastResultOfPage() {
		return lastResultOfPage;
	}
	public void setLastResultOfPage(int lastResultOfPage) {
		this.lastResultOfPage = lastResultOfPage;
	}
	public int getNumRisultati() {
		return numRisultati;
	}
	public void setNumRisultati(int numRisultati) {
		this.numRisultati = numRisultati;
	}
	//
	public String getNomeProvincia() 
	{
		return this.nomeProvincia; 
	}
	//
	public void setNomeProvincia(String nomeProvincia) 
	{
		this.nomeProvincia = nomeProvincia;
	}
	public String getCleanFlag() {
		return cleanFlag;
	}
	public void setCleanFlag(String cleanFlag) {
		this.cleanFlag = cleanFlag;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getPagingCurrentPageRecords() {
		return pagingCurrentPageRecords;
	}

	public void setPagingCurrentPageRecords(int pagingCurrentPageRecords) {
		this.pagingCurrentPageRecords = pagingCurrentPageRecords;
	}
	
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getOrderDirection() {
		return orderDirection;
	}

	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}

	public int getNumNextPage() {
		return numNextPage;
	}

	public void setNumNextPage(int numNextPage) {
		this.numNextPage = numNextPage;
	}

	public int getNumCurrentPage() {
		return numCurrentPage;
	}

	public void setNumCurrentPage(int numCurrentPage) {
		this.numCurrentPage = numCurrentPage;
	}

	public String getPaginationSmart() {
		return paginationSmart;
	}

	public void setPaginationSmart(String paginationSmart) {
		this.paginationSmart = paginationSmart;
	}

	//
}//end CommenContainerForm
